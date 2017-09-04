package com.deya.wcm.services.cms.info;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.deya.wcm.bean.cms.info.RelatedInfoBean;
import com.deya.wcm.dao.PublicTableDAO;
import org.apache.commons.beanutils.BeanUtils;

import com.deya.util.FormatUtil;
import com.deya.util.io.FileOperation;
import com.deya.util.labelUtil.AutoPagerHandl;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.category.CategoryModelManager;
import com.deya.wcm.services.cms.category.CategoryUtil;
import com.deya.wcm.services.control.site.SiteAppRele;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.system.assist.HotWordManager;
import com.deya.wcm.services.system.formodel.ModelManager;
import com.deya.wcm.template.velocity.impl.VelocityInfoContextImp;

/**
 * @author
 * @version 1.0
 * @title 信息发布处理类
 * @date 2011-6-15 上午10:18:04
 */

public class InfoPublishManager {
    public static void cancelAfterEvent(InfoBean ib) {
        try {

            deleteHtmlPage(ib);
            //这里还需要更新栏目页
            resetCategoryPage(ib.getCat_id(), ib.getSite_id());
            //删除引擎
            com.deya.wcm.services.search.SearchInnerManager.infoSetDel(ib.getInfo_id() + "");

            //调用资源目录事件接口
            if (ib.getSite_id().startsWith("GK")) {
                ZYMLEventHandl.removeFormData(ib.getInfo_id() + "");
            }
            //deleteQuoteInfoList(ib);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量撤消信息处理
     *
     * @param List<InfoBean> l
     * @param Set<Integer>   cat_ids
     * @throws IOException
     */
    public static void cancelAfterEvent(List<InfoBean> l, Set<Integer> cat_ids, String site_id) {
        for (InfoBean ib : l) {
            try {

                deleteHtmlPage(ib);
                //这里还需要更新栏目页
                resetCategoryPage(cat_ids, site_id);
                //删除引擎
                com.deya.wcm.services.search.SearchInnerManager.infoSetDel(ib.getInfo_id() + "");
                deleteQuoteInfoList(ib);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //调用资源目录事件接口
        if (site_id.startsWith("GK"))
            ZYMLEventHandl.removeFormData(l);
    }

    /**
     * 批量删除搜索引擎
     *
     * @param String info_ids
     * @throws
     */
    public static void cancelInfoSearch(List<InfoBean> l) {
        for (InfoBean ib : l) {
            try {
                com.deya.wcm.services.search.SearchInnerManager.infoSetDel(ib.getInfo_id() + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 批量删除搜索引擎和信息页面
     *
     * @param String info_ids
     * @throws
     */
    public static void cancelInfoSearchAndPage(List<InfoBean> l) {
        for (InfoBean ib : l) {
            try {
                deleteHtmlPage(ib);
                deleteQuoteInfoList(ib);
                com.deya.wcm.services.search.SearchInnerManager.infoSetDel(ib.getInfo_id() + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 单条发布处理事件，需要刷新栏目
     *
     * @param List<InfoBean> l
     * @param Set<Integer>   cat_ids
     * @throws IOException
     */
    public static boolean publishAfterEvent(InfoBean info) {
        //生成页面
        try {
            createContentHTML(info);
            //这里还需要更新栏目页
            resetCategoryPage(info.getCat_id(), info.getSite_id());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //同步信息处理
        InfoBaseManager.syncInfoToSite(info);

        //调用资源目录事件接口
        if (info.getSite_id().startsWith("GK"))
            ZYMLEventHandl.saveCMSFormData(info);
        return true;
    }

    /**
     * 多条发布处理事件
     *
     * @param List<InfoBean> l
     * @param Set<Integer>   cat_ids
     * @param String         site_id
     * @throws IOException
     */
    public static boolean publishAfterEvent(List<InfoBean> l, Set<Integer> cat_ids, String site_id) {
        try {
            createContentHTML(l);
            //这里还需要更新栏目页
            resetCategoryPage(cat_ids, site_id);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //同步信息处理
        InfoBaseManager.syncInfoToSite(l);

        //调用资源目录事件接口
        if (site_id.startsWith("GK"))
            ZYMLEventHandl.saveCMSFormData(l, cat_ids);
        return true;
    }

    /**
     * 批量生成内容页
     *
     * @param List<InfoBean> l
     * @param Set<Integer>   cat_ids
     * @throws IOException
     */
    public static boolean createContentHTML(List<InfoBean> l) throws IOException {
        int index = 1;
        for (InfoBean ib : l) {
            try {
                System.out.println("总共需要生成" + l.size() + "条信息，当前第" + index + "条，" + "info_id为：" + ib.getInfo_id());
                createContentHTML(ib);
                index = index + 1;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean createContentHTML(InfoBean ib) throws IOException {
        try {
            if (ib != null) {
                String model_ename = ModelManager.getModelEName(ib.getModel_id());

                //发布状态下才生成页面,必须不是链接类型的
                if (ib.getInfo_status() == 8 && !InfoBaseManager.LINK_MODEL_ENAME.equals(model_ename)) {
                    String temp_site_id = "";
                    if (!ib.getApp_id().equals("cms")) {
                        //如果不是内容管理的应用，需要取得该应用同内容管理关联的站点
                        temp_site_id = SiteAppRele.getSiteIDByAppID(ib.getApp_id());
                    } else
                        temp_site_id = ib.getSite_id();

                    int cat_id = ib.getCat_id();
                    int model_id = ib.getModel_id();
                    String template_id = CategoryModelManager.getTemplateID(cat_id + "", ib.getSite_id(), model_id);

                    if ((InfoBaseManager.ARTICLE_MODEL_ENAME.equals(model_ename) || InfoBaseManager.GKTYGS_MODEL_ENAME.equals(model_ename)) && ib.getPage_count() > 1) {
                        //手动翻页
                        //文章内容模型有翻页的
                        Object o = ModelUtil.select(ib.getInfo_id() + "", temp_site_id, model_ename);
                        String item_name = "";//内容字段的名称
                        if (InfoBaseManager.ARTICLE_MODEL_ENAME.equals(model_ename))
                            item_name = "info_content";
                        if (InfoBaseManager.GKTYGS_MODEL_ENAME.equals(model_ename))
                            item_name = "gk_content";
                        String content = BeanUtils.getProperty(o, item_name);
                        String savePath = "";
                        if (ib.getIs_am_tupage() == 1) {
                            //自动翻页
                            for (int i = 0; i < ib.getPage_count(); i++) {
                                BeanUtils.setProperty(o, item_name, AutoPagerHandl.splitContent(content, i + 1, ib.getTupage_num()));
                                VelocityInfoContextImp vici = new VelocityInfoContextImp(o, temp_site_id, template_id, (i + 1));
                                String vol_content = vici.parseTemplate();
                                String content_url = ib.getContent_url();
                                if ((content_url == "") || (content_url == null) || (content_url == "null") || ("".equals(content_url))) {
                                    content_url = CategoryUtil.getFoldePathByCategoryID(ib.getCat_id(), ib.getApp_id(), ib.getSite_id());
                                    content_url = content_url + ib.getInfo_id() + ".htm";

                                    Map m = new HashMap();
                                    m.put("sql", "update cs_info set content_url ='" + content_url + "' where info_id=" + ib.getInfo_id());
                                    PublicTableDAO.createSql(m);
                                }
                                if (i > 0)
                                    content_url = content_url.substring(0, content_url.indexOf(".htm")) + "_" + (i + 1) + ".htm";
                                savePath = FormatUtil.formatPath(SiteManager.getSitePath(temp_site_id) + content_url);
                                FileOperation.writeStringToFile(savePath, vol_content, false, "utf-8");
                            }
                        } else {
                            //替换热词
                            content = HotWordManager.replaceContentHotWord(content);
                            String[] tempA = content.split("<p class=\"ke-pageturning\">.*?</p>");
                            for (int i = 0; i < tempA.length; i++) {
                                BeanUtils.setProperty(o, item_name, tempA[i]);
                                VelocityInfoContextImp vici = new VelocityInfoContextImp(o, temp_site_id, template_id, (i + 1));
                                String vol_content = vici.parseTemplate();
                                String content_url = ib.getContent_url();
                                if ((content_url == "") || (content_url == null) || (content_url == "null") || ("".equals(content_url))) {
                                    content_url = CategoryUtil.getFoldePathByCategoryID(ib.getCat_id(), ib.getApp_id(), ib.getSite_id());
                                    content_url = content_url + ib.getInfo_id() + ".htm";

                                    Map m = new HashMap();
                                    m.put("sql", "update cs_info set content_url ='" + content_url + "' where info_id=" + ib.getInfo_id());
                                    PublicTableDAO.createSql(m);
                                }
                                if (i > 0)
                                    content_url = content_url.substring(0, content_url.indexOf(".htm")) + "_" + (i + 1) + ".htm";
                                savePath = FormatUtil.formatPath(SiteManager.getSitePath(temp_site_id) + content_url);
                                FileOperation.writeStringToFile(savePath, vol_content, false, "utf-8");
                            }
                        }
                    } else {
                        String content_url = ib.getContent_url();
                        if ((content_url == "") || (content_url == null) || (content_url == "null") || ("".equals(content_url))) {
                            content_url = CategoryUtil.getFoldePathByCategoryID(ib.getCat_id(), ib.getApp_id(), ib.getSite_id());
                            content_url = content_url + ib.getInfo_id() + ".htm";
                            Map m = new HashMap();
                            m.put("sql", "update cs_info set content_url ='" + content_url + "' where info_id=" + ib.getInfo_id());
                            PublicTableDAO.createSql(m);
                        }
                        VelocityInfoContextImp vici = new VelocityInfoContextImp(ib.getInfo_id() + "", temp_site_id, template_id, model_ename);
                        String content = vici.parseTemplate();
                        String savePath = FormatUtil.formatPath(SiteManager.getSitePath(temp_site_id) + content_url);
                        FileOperation.writeStringToFile(savePath, content, false, "utf-8");
                    }
                }
            }
            //搜索引擎
            com.deya.wcm.services.search.SearchInnerManager.infoSetAdd(ib.getInfo_id() + "");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 单条刷新栏目页
     *
     * @param int    cat_id
     * @param String site_id
     * @return
     */
    public static void resetCategoryPage(int cat_id, String site_id) {
        CategoryBean cb = CategoryManager.getCategoryBeanCatID(cat_id, site_id);
        if (cb != null) {
            resetCategoryPageHandl(cb, site_id);
        }
    }

    /**
     * 多条刷新栏目页
     *
     * @param Set<Integer> cat_ids
     * @param String       site_id
     * @return
     */
    public static void resetCategoryPage(Set<Integer> cat_ids, String site_id) {
        if (cat_ids != null) {
            Iterator<Integer> it = cat_ids.iterator();
            while (it.hasNext()) {
                resetCategoryPage(it.next(), site_id);
            }

        }
    }

    /**
     * 刷新栏目页
     *
     * @param Set<Integer> cat_ids
     * @param String       site_id
     * @return
     */
    public static void resetCategoryPageHandl(CategoryBean cb, String site_id) {
        String[] tempA = cb.getCat_position().split("\\$");//$0$1$2$3$
        for (int i = 2; i < tempA.length; i++)//0为根目录，不刷新，中间还有个空格，所以从2开始
        {
            if (tempA[i] != null && !"".equals(tempA[i])) {//得到栏目的层级，判断所有父级是否需要生成页面
                CategoryBean bean = CategoryManager.getCategoryBeanCatID(Integer.parseInt(tempA[i]), site_id);
                if (bean != null) {
                    if (bean.getIs_generate_index() == 1 && "cms".equals(bean.getApp_id())) {
                        //cb.getIs_generate_index() == 1　生成栏目首页 内容管理的才生成静态页面
                        createCategoryIndexPage(bean, site_id);
                    }
                }
            }
        }
    }

    /**
     * 生成栏目首页
     *
     * @param CategoryBean bean
     * @return
     */
    public static boolean createCategoryIndexPage(CategoryBean cb, String site_id) {
        int template_id = cb.getTemplate_index();
        if (template_id == 0) {
            String root_tree_id = cb.getCat_position().split("\\$")[2];
            CategoryBean root_cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(root_tree_id), site_id);
            //如果是专题栏目，查找根节点对象
            if (root_cb.getCat_type() == 1) {
                template_id = root_cb.getTemplate_index();
            }
        }
        VelocityInfoContextImp v_index = new VelocityInfoContextImp(cb.getCat_id(), site_id, template_id + "");
        String html = v_index.parseTemplate();
        String savePath = FormatUtil.formatPath(SiteManager.getSitePath(site_id) + CategoryUtil.getFoldePathByCategoryID(cb.getCat_id(), "cms", site_id) + "index.htm");
        //System.out.println(SiteManager.getSitePath(site_id)+CategoryUtil.getFoldePathByCategoryID(cb.getCat_id(),"cms",site_id)+"index.htm");
        try {
            FileOperation.writeStringToFile(savePath, html, false, "utf-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 删除内容页
     *
     * @param InfoBean ib
     * @throws IOException
     */
    public static void deleteHtmlPage(InfoBean ib) {
        int info_id = ib.getInfo_id();
        int cat_id = ib.getCat_id();
        String app_id = ib.getApp_id();
        String site_id = ib.getSite_id();
        if (!ib.getApp_id().equals("cms")) {
            //如果不是内容管理的应用，需要取得该应用同内容管理关联的站点
            site_id = SiteAppRele.getSiteIDByAppID(app_id);
        }
        String savePath = FormatUtil.formatPath(SiteManager.getSitePath(site_id) + CategoryUtil.getFoldePathByCategoryID(cat_id, app_id, ib.getSite_id()) + info_id);

        if (ib.getPage_count() > 1) {//判断内容页是否是多页
            for (int i = 0; i < ib.getPage_count(); i++) {
                String temp_path = "";
                if (i > 0) {
                    temp_path = savePath + "_" + (i + 1) + ".htm";
                } else {
                    temp_path = savePath + ".htm";
                }
                deleteHtmlPageHandl(temp_path);
            }
        } else {
            savePath = savePath + ".htm";
            deleteHtmlPageHandl(savePath);
        }
        deleteQuoteInfoList(ib);
    }

    /**
     * 删除信息之后，重新生成该信息的相关页面  根据该信息的发布时间，删除前后三十条信息
     *
     * @param String savePath
     * @throws IOException
     */
    public static void createRelaContentHTML(List<InfoBean> l) {
        if (l != null && l.size() > 0) {
            for (InfoBean ib : l) {
                if (ib.getInfo_status() == 8) {
                    /*删除信息之后，重新生成该信息的相关页面  根据该信息的发布时间，删除前后三十条信息*/
                    Map<String, String> m = new HashMap<String, String>();
                    m.put("info_id", ib.getInfo_id() + "");
                    m.put("released_dtime", ib.getReleased_dtime());
                    List<RelatedInfoBean> list = InfoBaseManager.getRelatedInfoListForDelete(m);
                    if (list != null && list.size() > 0) {
                        for (RelatedInfoBean rb : list) {
                            try {
                                InfoPublishManager.createContentHTML(InfoBaseManager.getInfoById(rb.getInfo_id() + ""));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 删除内容页
     *
     * @param String savePath
     * @throws IOException
     */
    public static void deleteHtmlPageHandl(String savePath) {
        File f = new File(savePath);
        if (f.exists())
            f.delete();
    }

    private static void deleteQuoteInfoList(InfoBean ib){
        List<InfoBean> quoteInfoList = InfoBaseManager.getQuoteInfoList(ib.getInfo_id() + "");
        if (quoteInfoList != null && quoteInfoList.size() > 0) {
            for (InfoBean infoBean : quoteInfoList) {
                deleteHtmlPage(infoBean);
                //这里还需要更新栏目页
                resetCategoryPage(infoBean.getCat_id(), infoBean.getSite_id());
                //删除引擎
                com.deya.wcm.services.search.SearchInnerManager.infoSetDel(infoBean.getInfo_id() + "");

                //调用资源目录事件接口
                if (ib.getSite_id().startsWith("GK")) {
                    ZYMLEventHandl.removeFormData(infoBean.getInfo_id() + "");
                }
            }
        }
    }

    public static void main(String[] args) {
        //String str = "<p>第一页：</p><p>AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA</p><p>&nbsp;</p><p class=\"ke-pageturning\">翻页分隔</p><p>第二页：</p><p>BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB</p><p>&nbsp;</p><p class=\"ke-pageturning\">翻页分隔</p><p>第三页：</p><p>CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC</p><p>&nbsp;</p>";
        String content_url = "/bendigk/1072.htm";
        content_url = content_url.substring(0, content_url.indexOf(".htm")) + "_1.htm";
        System.out.println(content_url);

    }
}
