package com.deya.wcm.dataCollection.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dataCollection.bean.CollRuleBean;
import com.deya.wcm.dataCollection.bean.RuleCategoryBean;
import com.deya.wcm.dataCollection.dao.RuleCategoryDAO;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.org.role.RoleUserManager;

public class RuleCategoryManager implements ISyncCatch{

    private static Map<String, RuleCategoryBean> ruleCate_map = new HashMap<String, RuleCategoryBean>();
    static{
        reloadCatchHandl();
    }

    public void reloadCatch()
    {
        reloadCatchHandl();
    }

    public static void reloadCatchHandl()
    {
        List<RuleCategoryBean> lt = RuleCategoryDAO.getRuleCategoryList();
        ruleCate_map.clear();
        if(lt != null)
        {
            for(int i=0; i < lt.size(); i++)
            {
                RuleCategoryBean rcb = lt.get(i);
                String key = rcb.getId();
                ruleCate_map.put(key, rcb);
            }
        }
    }

    /**
     * 初始化加载采集规则信息
     */
    public static void reloadMap()
    {
        SyncCatchHandl.reladCatchForRMI("com.deya.wcm.dataCollection.services.RuleCategoryManager");
    }

    /**
     * 根据站点ID得到所有的分类list
     * @param String site_id
     * @return list
     */
    public static List<RuleCategoryBean> getRCategoryListBySite(String site_id)
    {
        List<RuleCategoryBean> l = new ArrayList<RuleCategoryBean>();
        Set<String> s = ruleCate_map.keySet();
        for(String i:s)
        {
            RuleCategoryBean rcb = ruleCate_map.get(i);
            if(site_id.equals(rcb.getSite_id()))
            {
                l.add(rcb);
            }
        }
        return l;
    }

    /**
     * 根据登录用户得到它能管理的标签分类树节点
     * @param int user_id
     * @param String site_id
     * @return String
     */
    public static String getJSONTreeBySiteUser(int user_id,String site_id)
    {
        List<RuleCategoryBean> rcb_list = new ArrayList<RuleCategoryBean>();
        //System.out.println(RoleUserManager.isSiteManager(user_id+"","cms",site_id));
        //判断该人员是否站点管理员，如果是，给出全部的分类
        if(RoleUserManager.isSiteManager(user_id+"","cms",site_id)) {
            rcb_list = getRCategoryListBySite(site_id);
        }
        else
        {
            rcb_list = getRCategoryListBySiteUsers(user_id,site_id);
        }
        SiteBean stb = SiteManager.getSiteBeanBySiteID(site_id);

        String rootName = "root";
        if(stb != null)
        {
            rootName = stb.getSite_name();
        }
        String child_str = "";
        String json_str = "[{\"id\":0,\"text\":\""+rootName+"\"";
        child_str = getChildStrBySpecificList(getChildListBySpecificList("0", rcb_list),rcb_list);
        if(child_str != null && !"".equals(child_str))
        {
            json_str += ",\"children\":["+child_str+"]";
        }
        json_str += "}]";
        return json_str;
    }

    /**
     * 从指写的列表中取得Json数据的子目录
     * @param lt
     * @param mp
     * @return
     */
    private static String getChildStrBySpecificList(List<RuleCategoryBean> lt ,List<RuleCategoryBean> sp_list)
    {
        String json_str = "";
        if(lt != null && lt.size() > 0)
        {
            for(int i=0;i<lt.size();i++)
            {
                List<RuleCategoryBean> child_o_list = getChildListBySpecificList(lt.get(i).getRcat_id(), sp_list);
                json_str += "{";
                json_str += "\"id\":"+lt.get(i).getRcat_id()+",\"text\":\""+lt.get(i).getRcat_name()+"\"";
                if((child_o_list != null && child_o_list.size() > 0))
                {
                    String children_str = getChildStrBySpecificList(child_o_list, sp_list);
                    if(children_str != null && !"".equals(children_str))
                    {
                        json_str += ",\"state\":'closed',\"children\":["+children_str+"]";
                    }
                }
                json_str += "}";
                if(i+1 != lt.size())
                    json_str += ",";
            }
        }
        if(json_str.endsWith(","))
            json_str = json_str.substring(0,json_str.length()-1);
        return json_str;
    }

    public static String getRuleJsonStr(Map<String,String> m)
    {
        List<CollRuleBean> wl = CollectionDataManager.getRuleList(m);
        String url = "/sys/dataCollection/add_rule.jsp?type=update";
        if(wl != null && wl.size() > 0)
        {
            String str = "";
            for(int i=0;i<wl.size();i++)
            {
                str += ",{";
                str += "\"id\":"+wl.get(i).getRule_id()+",\"text\":\""+wl.get(i).getTitle()+"\",\"attributes\":{\"url\":\""
                        +url+"&id="+wl.get(i).getRule_id()+"&site_id="+wl.get(i).getSite_id() + "\"}";
                str += "}";
            }
            if(str != null && !"".equals(str))
                str = str.substring(1);
            return str;
        }
        return null;
    }

    /**
     * 从指写的列表中得它子节点对象
     * @param String parent_id
     * @param List<RuleCategoryBean> sp_list
     * @return	List<RuleCategoryBean>
     */
    public static List<RuleCategoryBean> getChildListBySpecificList(String parent_id,List<RuleCategoryBean> sp_list)
    {
        List<RuleCategoryBean> child_list = new ArrayList<RuleCategoryBean>();
        if(sp_list != null && sp_list.size() > 0)
        {
            for(int i=0;i<sp_list.size();i++)
            {
                if(parent_id.equals(sp_list.get(i).getParent_id()))
                    child_list.add(sp_list.get(i));
            }
        }
        Collections.sort(child_list, new RuleCateComparator());
        return child_list;
    }

    /**
     * 根据用户ID和站点ID得到它所能管理的分类列表
     * @param int wcat_id
     * @param String site_id
     * @return	RuleCategoryBean
     */
    public static List<RuleCategoryBean> getRCategoryListBySiteUsers(int user_id,String site_id)
    {
        List<RuleCategoryBean> wcb_list = new ArrayList<RuleCategoryBean>();
        Set<RuleCategoryBean> wcat_set = RuleCatReleUserManager.getRCatIDByUser(user_id, site_id);

        Iterator<RuleCategoryBean> it = wcat_set.iterator();
        while (it.hasNext()) {
            RuleCategoryBean wcb = it.next();
            wcb_list.add(wcb);
            String position = wcb.getRcat_position();
            position = position.substring(1,position.length()-1);
            String[] tempA = position.split("\\$");
            //截取分类路径，把该分类所有的上级分类写入到list
            for(int i=0;i<tempA.length-1;i++)
            {
                RuleCategoryBean w_bean =  getRuleCteBeanByCID(tempA[i], site_id);
                if(w_bean != null && !wcb_list.contains(w_bean))
                {
                    wcb_list.add(w_bean);
                }
            }
        }
        return wcb_list;
    }

    /**
     * 根据wcat_id和site_id得到分类对象
     * @param int wcat_id
     * @param String site_id
     * @return	RuleCategoryBean
     */
    public static RuleCategoryBean getRuleCteBeanByCID(String rcat_id,String site_id)
    {
        Set<String> s = ruleCate_map.keySet();
        for(String i : s)
        {
            RuleCategoryBean wcb = ruleCate_map.get(i);
            if(rcat_id.equals(wcb.getRcat_id()) && site_id.equals(wcb.getSite_id()))
            {
                return wcb;
            }
        }
        return null;
    }

    /**
     * 通过ID，site_id，app_id取得采集规则分类列表
     * @param id	采集规则ID
     * @param mp	site_id，app_id
     * @return	采集规则列表
     */
    public static List<RuleCategoryBean> getRuleCateList(String id, Map<String, String> mp)
    {
        return getChildList(id, mp);
    }

    /**
     * 根据ID取得采集规则分类
     * @param id 采集规则ID
     * @return	采集规则分类对象
     */
    public static RuleCategoryBean getRuleCategoryByID(String id)
    {
        RuleCategoryBean wcb = ruleCate_map.get(id);
        if(wcb == null)
        {
            reloadMap();
            wcb = ruleCate_map.get(id);
        }
        return wcb;
    }

    /**
     * 插入采集规则分类信息
     * @param wcb	采集规则分类信息
     * @param stl
     * @return	true 成功| false 失败
     */
    public static boolean insertRuleCate(RuleCategoryBean wcb, SettingLogsBean stl)
    {
        RuleCategoryBean parentBean = ruleCate_map.get(wcb.getParent_id());
        if(parentBean != null)
        {
            // 子节点层级为父节点+1，如果父节点不存在，使用默认值
            wcb.setRcat_level(parentBean.getRcat_level()+1);
            // 自身位置在DAO类中添加
            wcb.setRcat_position(parentBean.getRcat_position());
        }
        else if(Integer.parseInt(wcb.getParent_id()) == 0)
        {
            // 子节点层级为父节点+1，如果父节点不存在，使用默认值
            wcb.setRcat_level(1);
            // 自身位置在DAO类中添加
            wcb.setRcat_position("$0$");
        }

        if(RuleCategoryDAO.insertRuleCate(wcb, stl))
        {
            reloadMap();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 修改采集规则分类信息
     * @param wcb	采集规则分类信息
     * @param stl
     * @return	true 成功| false 失败
     */
    public static boolean updateRuleCategory(RuleCategoryBean wcb, SettingLogsBean stl)
    {
        if(RuleCategoryDAO.updateRuleCate(wcb, stl))
        {
            reloadMap();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 保存采集规则分类排序
     * @param ids	采集规则分类IDS，多个直接用“,”分隔
     * @param stl
     * @return	true 成功| false 失败
     */
    public static boolean saveSort(String ids, SettingLogsBean stl)
    {
        boolean flg = true;
        String arrIDS[] = ids.split(",");
        RuleCategoryBean wcb = new RuleCategoryBean();
        for(int i=0; i<arrIDS.length; i++)
        {
            wcb.setId(arrIDS[i]);
            wcb.setSort_id(i);
            if(!RuleCategoryDAO.saveRuleCateSort(wcb, stl))
            {
                flg = false;
            }
        }
        reloadMap();
        return flg;
    }

    /**
     * 根据多个分类ID得到它所有的子级ID
     * @param mp	删除条件
     * @param stl
     * @return	true 成功| false 失败
     */
    public static String getAllChildCateIDS(Map<String, String> mp)
    {
        String old_ids = mp.get("id");
        String[] arrIDS = old_ids.split(",");
        String ids = "";
        for(int i=0; i<arrIDS.length; i++)
        {
            ids += getAllChildIDS(arrIDS[i], mp);
        }
        // 去掉首位的","号
        if(ids.startsWith(","))
        {
            ids = ids.substring(1);
        }
        return ids;
    }



    /**
     * 删除采集规则分类
     * @param mp	删除条件
     * @param stl
     * @return	true 成功| false 失败
     */
    public static boolean deleteRuleCategory(Map<String, String> mp, SettingLogsBean stl)
    {

        String ids = getAllChildCateIDS(mp);
        mp.put("id", ids);

        if(RuleCategoryDAO.deleteRuleCate(mp, stl))
        {
            reloadMap();
            // 删除相关联的标签信息
            CollectionDataManager.deleteRuleByRuleCatId(mp);
            //删除分类与人员关联
            RuleCatReleUserManager.deleteRCRUByCat(mp.get("id"), mp.get("site_id"));
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 通过ID将一个目录移动到另一个下面
     * @param id	要移动的目录ID
     * @param parent_id	移动后的父节点ID
     * @param stl
     * @return	true 成功| false 失败
     */
    public static boolean moveCategory(String id, String parent_id ,SettingLogsBean stl)
    {
        boolean flg = true;
        RuleCategoryBean bean = getRuleCategoryByID(id);
        RuleCategoryBean parentBean = getRuleCategoryByID(parent_id);

        bean.setParent_id(parent_id);
        bean.setRcat_level(parentBean.getRcat_level()+1);
        bean.setRcat_position(parentBean.getRcat_position()+"$"+bean.getRcat_id());

        if( RuleCategoryDAO.updateRuleCate(bean, stl))
        {
            Map<String, String> mp = new HashMap<String, String>();
            mp.put("site_id", bean.getSite_id());
            mp.put("app_id", bean.getApp_id());
            flg = moveChildList(bean, mp, stl) ? flg: false;
        }
        reloadMap();
        return flg;
    }

    /**
     * 更新指定分类下的所有子分类的信息
     * @param parentBean	指定分类对象
     * @param mp
     * @param stl
     * @return	true 成功| false 失败
     */
    private static boolean moveChildList(RuleCategoryBean parentBean, Map<String, String> mp, SettingLogsBean stl)
    {
        boolean flg = true;
        List<RuleCategoryBean> lt = getChildList(parentBean.getId(), mp);
        if(lt != null && lt.size() > 0)
        {
            for(int i=0; i<lt.size(); i++)
            {
                RuleCategoryBean wcb = lt.get(i);
                wcb.setRcat_level(parentBean.getRcat_level() + 1);
                wcb.setRcat_position(parentBean.getRcat_position()+"$"+wcb.getId());
                flg = RuleCategoryDAO.updateRuleCate(wcb, stl) ? flg : false;
                flg = moveChildList(wcb, mp, stl) ? flg : false;
            }
        }
        return flg;
    }

    private static String getAllChildIDS(String id, Map<String, String> mp)
    {
        String ret = ","+id;
        List<RuleCategoryBean> lt = getChildList(id, mp);
        if(lt != null && lt.size() > 0)
        {
            for(int i=0; i<lt.size(); i++)
            {
                ret += getAllChildIDS(lt.get(i).getId(), mp);
            }
        }
        return ret;
    }

    /**
     * 取得JsonTree字符串
     * @return
     */
    public static String getJSONTreeStr(Map<String, String> mp)
    {
        String site_id = mp.get("site_id")== null ? "" : mp.get("site_id");
        SiteBean stb = SiteManager.getSiteBeanBySiteID(site_id);
        //SiteBean stb = null;
        String rootName = "root";
        if(stb != null)
        {
            rootName = stb.getSite_name();
        }

        String child_str = "";
        String json_str = "[{\"id\":0,\"text\":\""+rootName+"\"";
        child_str = getJSONTreeChildStr(getChildList("0", mp), mp);
        if(child_str != null && !"".equals(child_str))
        {
            json_str += ",\"children\":["+child_str+"]";
        }
        json_str += "}]";
        return json_str;
    }

    /**
     * 取得Json数据的子目录
     * @param lt
     * @param mp
     * @return
     */
    private static String getJSONTreeChildStr(List<RuleCategoryBean> lt ,Map<String, String> mp)
    {
        String json_str = "";
        if(lt != null && lt.size() > 0)
        {
            for(int i=0;i<lt.size();i++)
            {
                json_str += "{";
                json_str += "\"id\":"+lt.get(i).getRcat_id()+",\"text\":\""+lt.get(i).getRcat_name()+"\"";
                List<RuleCategoryBean> child_o_list = getChildList(lt.get(i).getRcat_id(), mp);
                if(child_o_list != null && child_o_list.size() > 0)
                    json_str += ",\"children\":["+getJSONTreeChildStr(child_o_list, mp)+"]";
                json_str += "}";
                if(i+1 != lt.size())
                    json_str += ",";
            }
        }
        return json_str;
    }

    /**
     * 根据id取得子分类列表
     * @param id 采集规则分类ID
     * @return	自列表ID
     */
    private static List<RuleCategoryBean> getChildList(String id, Map<String, String> mp)
    {
        List<RuleCategoryBean> retList = new ArrayList<RuleCategoryBean>();
        Iterator<RuleCategoryBean> it = ruleCate_map.values().iterator();
        while(it.hasNext())
        {
            RuleCategoryBean wcb = it.next();
            if(id.equals(wcb.getParent_id()) && isSameAppAndSite(mp, wcb))
            {
                retList.add(wcb);
            }
        }
        Collections.sort(retList, new RuleCateComparator());
        return retList;
    }

    /**
     * 判断是App_id，Site_id是否相等，如果site_id为“”，则不进行比较
     * @param site_id	站点ID
     * @param app_id	应用ID
     * @return	true 相同| false 不相同
     */
    private static boolean isSameAppAndSite(Map<String, String> mp, RuleCategoryBean wcb)
    {
        boolean sflg = false;
        boolean aflg = false;
        String site_id = mp.get("site_id");
        String app_id = mp.get("app_id");

        // 判断站点ID，为空直接按相同处理
        if("".equals(site_id))
        {
            sflg = true;
        }
        else if(site_id.equals(wcb.getSite_id()))
        {
            sflg = true;
        }

        // 判断应用id，应用ID必须相同
        if(app_id.equals(wcb.getApp_id()))
        {
            aflg = true;
        }
        else
        {
            aflg = false;
        }
        aflg = true;//不分应用，只分站点
        return sflg && aflg;
    }

    static class RuleCateComparator implements Comparator<RuleCategoryBean>{

        @Override
        public int compare(RuleCategoryBean o1, RuleCategoryBean o2) {
            int flg = 0;
            if(o1.getSort_id() > o2.getSort_id())
            {
                flg = 1;
            }
            else if(o1.getSort_id() == o2.getSort_id())
            {
                flg = 0;
            }
            else
            {
                flg = -1;
            }
            return flg;
        }
    }



    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(getJSONTreeBySiteUser(1,"HIWCM8888"));
        //System.out.println(getJSONTreeStr(1,"11111ddd"));
    }

}
