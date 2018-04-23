package com.deya.wcm.services.cms.category;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.deya.util.FormatUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;
import org.apache.commons.lang3.StringUtils;

public class CategoryTreeUtil {
    public static HashMap<String, String> uset_cate_map = new HashMap<String, String>();
    private static String article_page_path = JconfigUtilContainer.managerPagePath().getProperty("article_page", "", "m_org_path");
    private static String fw_page_path = JconfigUtilContainer.managerPagePath().getProperty("fw_page", "", "m_org_path");
    private static String gkbzh_article_page_path = JconfigUtilContainer.managerPagePath().getProperty("gkbzh_article_page", "", "m_org_path");

    //CategoryManager类调用此方法刷新
    public static void reloadMap() {
        uset_cate_map.clear();
    }

    /**
     * 根据站点ID,人员ID得到有权限管理的目录树
     *
     * @param String site_id
     * @param int    user_id
     * @param int    cat_type 目录类型
     * @return String
     */
    public static String getInfoCategoryTreeByUserID(String site_id, int uesr_id, int cat_type) {
        String key = uesr_id + "_" + site_id + "_" + cat_type;
        if (uset_cate_map.containsKey(key)) {
            return uset_cate_map.get(key);
        } else {
            String json_str = "";
            String roo_name = "";
            if (cat_type == 2)
                roo_name = "服务目录";
            else {
                if (site_id.startsWith("GK")) {
                    roo_name = GKNodeManager.getNodeNameByNodeID(site_id);
                } else {
                    SiteBean sb = SiteManager.getSiteBeanBySiteID(site_id);
                    roo_name = sb.getSite_name();
                    if (CategoryManager.haveCategoryManagementAuthority(CategoryManager.ROOT_ID, site_id, uesr_id)) {
                        json_str = getCategoryTreeBySiteID(site_id, 0);
                        uset_cate_map.put(key, json_str);
                        return json_str;
                    }
                }
            }

            json_str = "[{\"id\":0,\"text\":\"" + roo_name + "\",\"attributes\":{\"url\":\"\",\"handl\":\"\"}";
            List<CategoryBean> list = CategoryManager.getCategoryListBySiteID(site_id, cat_type);
            //System.out.println(list.size());
            if (list != null && list.size() > 0) {
                json_str += ",\"children\":[";
                for (int i = 0; i < list.size(); i++) {
                    String str = getCategoryTreeByUser(list.get(i).getCat_id(), site_id, uesr_id);

                    if (str != null && !"".equals(str)) {
                        if (json_str.endsWith("}"))
                            json_str += ",";
                        json_str += str;
                    }
                }
                json_str += "" + "]";
            }
            json_str += "}]";

            uset_cate_map.put(key, json_str);
            return json_str;
        }
    }

    /**
     * 根据站点ID,人员ID得到有权限管理的目录树
     *
     * @param String site_id
     * @param int    cat_type 目录类型
     * @return String
     */
    public static String getInfoCategoryTreeByUserID(String site_id, int cat_type) {
        String json_str = "";
        String roo_name = "";
        if (cat_type == 2)
            roo_name = "服务目录";
        else {
            if (site_id.startsWith("GK")) {
                GKNodeBean gnb = GKNodeManager.getGKNodeBeanByNodeID(site_id);
                roo_name = gnb.getNode_name();
            } else {
                SiteBean sb = SiteManager.getSiteBeanBySiteID(site_id);
                roo_name = sb.getSite_name();
            }
        }

        json_str = "[";
        List<CategoryBean> list = CategoryManager.getCategoryListBySiteID(site_id, cat_type);

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String str = getCategoryTreeByCategoryID(list.get(i).getCat_id(), site_id);
                if (str != null && !"".equals(str)) {
                    str = str.substring(1, str.length() - 1);
                    if (json_str.endsWith("}"))
                        json_str += ",";
                    json_str += str;
                }
            }
        }
        json_str += "]";

        return json_str;
    }

    /**
     * 根据站点或节点ID获得目录树
     *
     * @param String site_id
     * @return String
     */
    public static String getCategoryTreeBySiteID(String site_id, int cat_type) {
        String json_str = "";
        if (site_id.startsWith("GK")) {
            GKNodeBean gnb = GKNodeManager.getGKNodeBeanByNodeID(site_id);
            if (gnb != null) {
                json_str = "[{\"id\":0,\"text\":\"" + gnb.getNode_name() + "\",\"attributes\":{\"url\":\"\",\"handl\":\"\"}";
            }
        } else {
            SiteBean sb = SiteManager.getSiteBeanBySiteID(site_id);
            if (sb != null) {
                json_str = "[{\"id\":0,\"text\":\"" + sb.getSite_name() + "\",\"attributes\":{\"url\":\"\",\"handl\":\"\"}";
            }
        }
        List<CategoryBean> list = CategoryManager.getCategoryListBySiteID(site_id, cat_type);
        if (list != null && list.size() > 0) {
            json_str += ",\"children\":[" + getCategoryTreeJsonStrHandl(list) + "]";
        }
        json_str += "}]";
        return json_str;
    }

    /**
     * 根据分类方式ID得目录树节字符串
     *
     * @param int class_id
     * @return String
     */
    public static String getCategoryTreeByClassID(int class_id) {
        CategoryBean cbg = CategoryManager.getCategoryBeanByClassID(class_id);
        if (cbg != null) {
            return getCategoryTreeByCategory(cbg);
        } else
            return "[]";
    }

    /**
     * 根据目录ID得目录树节字符串
     *
     * @param int    class_id
     * @param String site_id
     * @return String
     */
    public static String getCategoryTreeByCategoryID(int cat_id, String site_id) {
        CategoryBean cbg = CategoryManager.getCategoryBeanCatID(cat_id, site_id);
        //对象不为空且，归档状态为0
        if (cbg != null && cbg.getIs_archive() == 0) {
            return getCategoryTreeByCategory(cbg);
        } else
            return "[]";
    }

    /**
     * 根据cat_id，site_id和user_id得到它能管理的目录节点树
     *
     * @param int    cat_id
     * @param String site_id
     * @param int    user_id
     * @return CategoryBean
     */
    public static String getCategoryTreeByUser(int cat_id, String site_id, int user_id) {
        //首先判断该人员是否是站点管理员或该级目录的管理权限,如果是，给出所有的节点
        String str = "";
        if (CategoryManager.haveCategoryManagementAuthority(cat_id, site_id, user_id)) {
            str = getCategoryTreeByCategoryID(cat_id, site_id);
            if (str != null && !"".equals(str))
                str = str.substring(1, str.length() - 1);
        } else {
            str = getCategoryTreeByUserHandl(cat_id, site_id, user_id);
        }

        return str;
    }

    /**
     * 根据cat_id，site_id和user_id得到它能管理的目录节点树
     *
     * @param int    cat_id
     * @param String site_id
     * @param int    user_id
     * @return CategoryBean
     */
    public static String getCategoryTreeByUserHandl(int cat_id, String site_id, int user_id) {
        //根据节点ID得到它所有的子节点列表
        Set<CategoryBean> s = new HashSet<CategoryBean>();
        List<CategoryBean> l = CategoryManager.getAllChildCategoryList(cat_id, site_id);
        if (l != null && l.size() > 0) {//循环列表，判断该人员是否有节点管理权限
            for (int i = 0; i < l.size(); i++) {
                if (l.get(i).getIs_archive() == 0 && CategoryReleManager.isCategoryManagerByUser(user_id, l.get(i).getSite_id(), l.get(i).getCat_id())) {
                    s.add(l.get(i));
                    //根据该节点的cat_position,得到它所有的父级节点对象
                    CategoryManager.setCategoryByPosition(s, l.get(i).getCat_position(), l.get(i).getSite_id());
                }
            }
            StringBuffer sb = new StringBuffer();
            getCategoryTreeStrBySet(s, cat_id, site_id, user_id, sb);
            String temp_str = sb.toString();
            if (temp_str.endsWith(","))
                temp_str = temp_str.substring(0, temp_str.length() - 1);
            return temp_str;
        } else
            return "";
    }

    public static void getCategoryTreeStrBySet(Set<CategoryBean> set, int cat_id, String site_id, int user_id, StringBuffer sb) {
        if (set != null && set.size() > 0) {
            CategoryBean cbg = CategoryManager.getCategoryBeanCatID(cat_id, site_id);
            String manager_page = article_page_path;
            if ("ggfw".equals(cbg.getApp_id()))//公共服务的信息列表不一样
                manager_page = fw_page_path;
            if (cbg.getIs_archive() == 0) {
                if (sb.toString().endsWith("}"))
                    sb.append(",");
                //判断是否有子节点
                if (CategoryManager.isHasChildNode(cat_id, site_id)) {//判断该用户是否有此枝节点的管理权限，如果有展现出下面所有的子节点，否则往下继续查找
                    if (CategoryReleManager.isCategoryManagerByUser(user_id, site_id, cat_id)) {
                        String str = getCategoryTreeByCategoryID(cat_id, site_id);
                        if (str != null && !"".equals(str))
                            str = str.substring(1, str.length() - 1);
                        sb.append(str);
                    } else {
                        sb.append("{\"id\":" + cbg.getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(cbg.getCat_cname()) + "\",\"attributes\":{\"url\":\""
                                + manager_page + "?app_id=" + cbg.getApp_id() + "&cat_id=" + cbg.getCat_id() + "\",\"handl\":\"\"}");
                        sb.append(",\"children\":[");
						/*
						Iterator<CategoryBean> it = set.iterator();						
						while(it.hasNext()){
							CategoryBean cb = it.next();
							if(cb.getIs_archive() == 0 && cb.getParent_id() == cat_id)
								getCategoryTreeStrBySet(set,cb.getCat_id(),site_id,user_id,sb);
						}*/
                        //得到该节点的子节点列表
                        List<CategoryBean> child_list = CategoryManager.getChildCategoryList(cbg.getCat_id(), site_id);
                        for (int i = 0; i < child_list.size(); i++) {//判断此节点是否在有权限的集合中
                            if (set.contains(child_list.get(i))) {
                                getCategoryTreeStrBySet(set, child_list.get(i).getCat_id(), site_id, user_id, sb);
                            }
                        }
                        sb.append("]}");
                    }
                } else
                    sb.append("{\"id\":" + cbg.getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(cbg.getCat_cname()) + "\",\"attributes\":{\"url\":\""
                            + manager_page + "?app_id=" + cbg.getApp_id() + "&cat_id=" + cbg.getCat_id() + "\",\"handl\":\"\"}}");
            }
        }

    }

    /**
     * 根据目录对象得目录树节字符串
     *
     * @param CategoryBean cbg
     * @return String
     */
    public static String getCategoryTreeByCategory(CategoryBean cbg) {
        String json_str = "";
        try {
            String manager_page = article_page_path;
            if ("ggfw".equals(cbg.getApp_id()))//公共服务的信息列表不一样
                manager_page = fw_page_path;

            List<CategoryBean> child_list = CategoryManager.getChildCategoryList(cbg.getCat_id(), cbg.getSite_id());
            json_str = "[{\"id\":" + cbg.getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(cbg.getCat_cname()) + "\",\"attributes\":{\"url\":\""
                    + manager_page + "?app_id=" + cbg.getApp_id() + "&cat_id=" + cbg.getCat_id() + "\",\"handl\":\"\"}";
            if (child_list != null && child_list.size() > 0) {
                json_str += ",\"state\":'closed',\"children\":[" + getCategoryTreeJsonStrHandl(child_list) + "]";
            }

            json_str += "}]";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json_str;
    }

    /**
     * 根据目录列表得目录树节字符串
     *
     * @param CategoryBean cbg
     * @return String
     */
    public static String getCategoryTreeJsonStrHandl(List<CategoryBean> child_list) {
        String json_str = "";
        if (child_list != null && child_list.size() > 0) {
            String manager_page = article_page_path;
            if ("ggfw".equals(child_list.get(0).getApp_id()))//公共服务的信息列表不一样
                manager_page = fw_page_path;

            for (int i = 0; i < child_list.size(); i++) {
                json_str += "{";
                List<CategoryBean> child_m_list = CategoryManager.getChildCategoryList(child_list.get(i).getCat_id(), child_list.get(i).getSite_id());
                if (child_m_list != null && child_m_list.size() > 0) {
                    json_str += "\"id\":" + child_list.get(i).getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(child_list.get(i).getCat_cname()) + "\",\"attributes\":{\"url\":\""
                            + manager_page + "?app_id=" + child_list.get(i).getApp_id() + "&cat_id=" + child_list.get(i).getCat_id() + "\",\"handl\":\"\"}";
                    json_str += ",\"state\":'closed'";
                    json_str += ",\"children\":[" + getCategoryTreeJsonStrHandl(child_m_list) + "]";
                } else
                    json_str += "\"id\":" + child_list.get(i).getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(child_list.get(i).getCat_cname()) + "\",\"attributes\":{\"url\":\""
                            + manager_page + "?app_id=" + child_list.get(i).getApp_id() + "&cat_id=" + child_list.get(i).getCat_id() + "\",\"handl\":\"\"}";
                json_str += "}";
                if (i + 1 != child_list.size())
                    json_str += ",";
            }
        }
        return json_str;
    }

    /**
     * 根据专题分类ID,站点ID得到它所能管理的目录节点
     *
     * @param int    zt_cat_id 专题分类ID
     * @param String site_id
     * @return List<CategoryBean>
     */
    public static String getZTCategoryTreeStr(int zt_cat_id, String site_id) {
        String str = "";
        //首先得到该专题分类下的目录列表
        List<CategoryBean> l = CategoryManager.getZTCategoryListBySiteAndType(zt_cat_id, site_id);
        if (l != null && l.size() > 0) {
            for (int i = 0; i < l.size(); i++) {
                String temp_str = getCategoryTreeByCategoryID(l.get(i).getCat_id(), site_id);

                if (temp_str != null && !"".equals(temp_str)) {
                    temp_str = temp_str.substring(1, temp_str.length() - 1);
                    str += "," + temp_str;
                }
            }
            if (str.length() > 0)
                str = str.substring(1);
            return str;
        } else
            return "";
    }

    /**
     * 根据专题分类ID,站点ID和UserID得到它所能管理的目录节点
     *
     * @param int    zt_cat_id 专题分类ID
     * @param String site_id
     * @return List<CategoryBean>
     */
    public static String getZTCategoryTreeStr(int zt_cat_id, String site_id, int user_id) {
        String str = "";
        //首先得到该专题分类下的目录列表
        List<CategoryBean> l = CategoryManager.getZTCategoryListBySiteAndType(zt_cat_id, site_id);
        if (l != null && l.size() > 0) {
            for (int i = 0; i < l.size(); i++) {
                String temp_str = getCategoryTreeByUser(l.get(i).getCat_id(), site_id, user_id);
                if (temp_str != null && !"".equals(temp_str))
                    str += "," + temp_str;
            }
            if (str.length() > 0)
                str = str.substring(1);
            return str;
        } else
            return "";
    }

    /**
     * 得到服务的所有目录树
     *
     * @param
     * @return String
     */
    public static String getAllFWTreeJSONStr() {
        List<CategoryBean> l = CategoryManager.getCategoryListBySiteID("ggfw", 2);
        if (l != null && l.size() > 0) {
            return "[" + getCategoryTreeJsonStrHandl(l) + "]";
        } else
            return "";
    }


    /*************************************栏目异步树结构**************************************************/

    /**
     * 根据站点ID,人员ID得到有权限管理的目录树
     *
     * @param String site_id
     * @param int    user_id
     * @param int    cat_type 目录类型
     * @return String
     */
    public static String getInfoCategoryTreeByUserIDSync(String site_id, int uesr_id, int pid,String type) {
        String key = uesr_id + "_" + site_id + "_p" + pid;
        if (uset_cate_map.containsKey(key)) {
            return uset_cate_map.get(key);
        } else {
            String json_str = "";
            String roo_name = "";
            List<CategoryBean> list = null;
            if (StringUtils.isNotEmpty(site_id)) {
                SiteBean sb = SiteManager.getSiteBeanBySiteID(site_id);
                roo_name = sb.getSite_name();
                list = CategoryManager.getCategoryListBySiteIDPid(site_id, pid);
            } else {
                roo_name = CategoryManager.getCategoryBean(pid).getCat_cname();
                list = CategoryManager.getCategoryListByPid(pid);
            }
            //System.out.println("****************pid**************" + pid + "***********子节点数：**********" + list.size());
            if (pid == 0 || pid == 384) {
                json_str = "[{\"id\":0,\"text\":\"" + roo_name + "\",\"attributes\":{\"url\":\"\",\"handl\":\"\"}";
                if (list != null && list.size() > 0) {
                    json_str += ",\"children\":[";
                    for (int i = 0; i < list.size(); i++) {
                        String str = "";
                        if(type.equals("info")){
                            str = isCategoryOprate(list.get(i).getCat_id(), site_id, uesr_id);
                        }else{
                            str = isCategoryOprate(list.get(i).getCat_id(), uesr_id);
                        }
                        if (str != null && !"".equals(str)) {
                            if (json_str.endsWith("}"))
                                json_str += ",";
                            json_str += str;
                        }
                    }
                    json_str += "" + "]";
                }
                json_str += "}]";
            } else {
                json_str = "[";
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        String str = "";
                        if(type.equals("info")){
                            str = isCategoryOprate(list.get(i).getCat_id(), site_id, uesr_id);
                        }else{
                            str = isCategoryOprate(list.get(i).getCat_id(), uesr_id);
                        }
                        if (str != null && !"".equals(str)) {
                            if (json_str.endsWith("}"))
                                json_str += ",";
                            json_str += str;
                        }
                    }
                }
                json_str += "" + "]";
            }

            uset_cate_map.put(key, json_str);
            return json_str;
        }
    }


    /**
     * 根据cat_id，site_id和user_id得到它能管理的目录节点树
     *
     * @param int    cat_id
     * @param String site_id
     * @param int    user_id
     * @return CategoryBean
     */
    public static String isCategoryOprate(int cat_id, String site_id, int user_id) {
        String json_str = "";
        CategoryBean cbg = CategoryManager.getCategoryBeanCatID(cat_id, site_id);
        String manager_page = article_page_path;
        if (user_id == 0) {
            if (CategoryManager.isHasChildNode(cat_id, site_id)) {
                json_str = "{\"id\":" + cbg.getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(cbg.getCat_cname()) + "\",\"state\":\"closed\",\"attributes\":{\"url\":\""
                        + manager_page + "?app_id=" + cbg.getApp_id() + "&cat_id=" + cbg.getCat_id() + "\",\"handl\":\"\"}}";
            } else {
                json_str = "{\"id\":" + cbg.getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(cbg.getCat_cname()) + "\",\"attributes\":{\"url\":\""
                        + manager_page + "?app_id=" + cbg.getApp_id() + "&cat_id=" + cbg.getCat_id() + "\",\"handl\":\"\"}}";
            }
        } else {
            //判断该用户是否有此枝节点的管理权限，如果有显示出来并且添加到tree
            if (CategoryManager.haveCategoryManagementAuthority(cat_id, site_id, user_id)) {
                if (CategoryManager.isHasChildNode(cat_id, site_id)) {
                    json_str = "{\"id\":" + cbg.getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(cbg.getCat_cname()) + "\",\"state\":\"closed\",\"attributes\":{\"url\":\""
                            + manager_page + "?app_id=" + cbg.getApp_id() + "&cat_id=" + cbg.getCat_id() + "\",\"handl\":\"\"}}";
                } else {
                    json_str = "{\"id\":" + cbg.getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(cbg.getCat_cname()) + "\",\"attributes\":{\"url\":\""
                            + manager_page + "?app_id=" + cbg.getApp_id() + "&cat_id=" + cbg.getCat_id() + "\",\"handl\":\"\"}}";
                }
            } else {
                if (CategoryReleManager.isCategoryManagerByUser(user_id, site_id, cat_id)) {
                    if (CategoryManager.isHasChildNode(cat_id, site_id)) {
                        json_str = "{\"id\":" + cbg.getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(cbg.getCat_cname()) + "\",\"state\":\"closed\",\"attributes\":{\"url\":\""
                                + manager_page + "?app_id=" + cbg.getApp_id() + "&cat_id=" + cbg.getCat_id() + "\",\"handl\":\"\"}}";
                    } else {
                        json_str = "{\"id\":" + cbg.getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(cbg.getCat_cname()) + "\",\"attributes\":{\"url\":\""
                                + manager_page + "?app_id=" + cbg.getApp_id() + "&cat_id=" + cbg.getCat_id() + "\",\"handl\":\"\"}}";
                    }
                }
            }
        }

        return json_str;
    }

    /**
     * 根据cat_id，site_id和user_id得到它能管理的目录节点树
     *
     * @param int    cat_id
     * @param String site_id
     * @param int    user_id
     * @return CategoryBean
     */
    public static String isCategoryOprate(int cat_id,int user_id) {
        String json_str = "";
        CategoryBean cbg = CategoryManager.getCategoryBeanCatID(cat_id);
        String manager_page = gkbzh_article_page_path;
        if (user_id == 0) {
            if (CategoryManager.isHasChildNode(cat_id)) {
                json_str = "{\"id\":" + cbg.getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(cbg.getCat_cname()) + "\",\"state\":\"closed\",\"attributes\":{\"url\":\""
                        + manager_page + "?app_id=cms&cat_id=" + cbg.getCat_id() + "\",\"handl\":\"\"}}";
            } else {
                json_str = "{\"id\":" + cbg.getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(cbg.getCat_cname()) + "\",\"attributes\":{\"url\":\""
                        + manager_page + "?app_id=cms&cat_id=" + cbg.getCat_id() + "\",\"handl\":\"\"}}";
            }

        } else {
            if (CategoryManager.isHasChildNode(cat_id)) {
                json_str = "{\"id\":" + cbg.getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(cbg.getCat_cname()) + "\",\"state\":\"closed\",\"attributes\":{\"url\":\""
                        + manager_page + "?app_id=cms&cat_id=" + cbg.getCat_id() + "\",\"handl\":\"\"}}";
            } else {
                json_str = "{\"id\":" + cbg.getCat_id() + ",\"text\":\"" + FormatUtil.formatJsonStr(cbg.getCat_cname()) + "\",\"attributes\":{\"url\":\""
                        + manager_page + "?app_id=cms&cat_id=" + cbg.getCat_id() + "\",\"handl\":\"\"}}";
            }
        }

        return json_str;
    }

    /*************************************栏目异步树结构**************************************************/


    public static void main(String[] args) {
        System.out.println(getCategoryTreeByUserHandl(0, "HIWCMdemo", 193));
    }
}
