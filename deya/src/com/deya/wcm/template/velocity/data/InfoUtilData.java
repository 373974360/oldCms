package com.deya.wcm.template.velocity.data;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.cms.category.CateCurPositionBean;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.category.SMCategoryBean;
import com.deya.wcm.bean.cms.count.CmsCountBean;
import com.deya.wcm.bean.cms.count.InfoAccessBean;
import com.deya.wcm.bean.cms.count.InfoCountBean;
import com.deya.wcm.bean.cms.count.SiteCountBean;
import com.deya.wcm.bean.cms.count.SiteInfoTuisongBean;
import com.deya.wcm.bean.cms.info.ArticleBean;
import com.deya.wcm.bean.cms.info.GKFbsznBean;
import com.deya.wcm.bean.cms.info.GKInfoBean;
import com.deya.wcm.bean.cms.info.GKResFileBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.cms.info.RelatedInfoBean;
import com.deya.wcm.bean.cms.info.VideoBean;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.template.TurnPageBean;
import com.deya.wcm.bean.zwgk.appcatalog.AppCatalogBean;
import com.deya.wcm.bean.zwgk.node.GKNodeCategory;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkBean;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkListBean;
import com.deya.wcm.dao.zwgk.ysqgk.YsqgkInfoDAO;
import com.deya.wcm.services.browserapi.BrowserAPIService;
import com.deya.wcm.services.cms.category.CategoryBrowserTreeUtil;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.category.CategoryUtil;
import com.deya.wcm.services.cms.count.AccessCountManager;
import com.deya.wcm.services.cms.count.TuisongCountManager;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.cms.info.ModelUtil;
import com.deya.wcm.services.cms.rss.RssReaderManager;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.model.WcmZykFile;
import com.deya.wcm.services.model.services.WcmZykInfoService;
import com.deya.wcm.services.session.OnlineCounter;
import com.deya.wcm.services.system.formodel.ModelManager;
import com.deya.wcm.services.zwgk.appcatalog.AppCatalogManager;
import com.deya.wcm.services.zwgk.info.GKInfoManager;
import com.deya.wcm.services.zwgk.node.GKNodeCateManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;
import com.deya.wcm.services.zwgk.ysqgk.YsqgkInfoManager;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoUtilData
{
    public static List<InfoBean> removeHTMLTag(List<InfoBean> l)
    {
        if ((l != null) && (l.size() > 0)) {
            for (InfoBean info : l) {
                info.setTitle(info.getTitle().replaceAll("<[Bb][Rr]/?>", ""));
            }
        }
        return l;
    }

    public static List<GKInfoBean> removeHTMLTagForGK(List<GKInfoBean> l)
    {
        if ((l != null) && (l.size() > 0)) {
            for (InfoBean info : l) {
                info.setTitle(info.getTitle().replaceAll("<[Bb][Rr]/?>", ""));
            }
        }
        return l;
    }

    public static List<InfoBean> getInfoList(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "cms");
        return removeHTMLTag(InfoBaseManager.getBroInfoList(con_map));
    }

    public static List<InfoBean> getInfoHotList(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "cms");
        con_map.put("current_page", "1");
        return removeHTMLTag(InfoBaseManager.getBroInfoList(con_map));
    }

    public static TurnPageBean getInfoCount(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "cms");

        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(Integer.parseInt(InfoBaseManager.getBroInfoCount(con_map)));
        int cur_page = Integer.parseInt((String)con_map.get("current_page"));
        int page_size = Integer.parseInt((String)con_map.get("page_size"));

        tpb.setCur_page(cur_page);
        tpb.setPage_size(page_size);
        tpb.setPage_count(tpb.getCount() / tpb.getPage_size() + 1);
        if ((tpb.getCount() % tpb.getPage_size() == 0) && (tpb.getPage_count() > 1)) {
            tpb.setPage_count(tpb.getPage_count() - 1);
        }
        if (cur_page > 1) {
            tpb.setPrev_num(cur_page - 1);
        }
        tpb.setNext_num(tpb.getPage_count());
        if (cur_page < tpb.getPage_count()) {
            tpb.setNext_num(cur_page + 1);
        }
        if (tpb.getPage_count() > 10) {
            if (cur_page > 5) {
                if (cur_page > tpb.getPage_count() - 4) {
                    tpb.setCurr_start_num(tpb.getPage_count() - 6);
                } else {
                    tpb.setCurr_start_num(cur_page - 2);
                }
            }
        }
        return tpb;
    }

    public static List<InfoBean> getInfoListByStatus(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "cms");
        return removeHTMLTag(InfoBaseManager.getInfoList(con_map));
    }

    public static TurnPageBean getInfoListByStatusCount(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "cms");

        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(InfoBaseManager.getInfoCount(con_map));
        int cur_page = Integer.parseInt((String)con_map.get("current_page"));
        int page_size = Integer.parseInt((String)con_map.get("page_size"));

        tpb.setCur_page(cur_page);
        tpb.setPage_size(page_size);
        tpb.setPage_count(tpb.getCount() / tpb.getPage_size() + 1);
        if ((tpb.getCount() % tpb.getPage_size() == 0) && (tpb.getPage_count() > 1)) {
            tpb.setPage_count(tpb.getPage_count() - 1);
        }
        if (cur_page > 1) {
            tpb.setPrev_num(cur_page - 1);
        }
        tpb.setNext_num(tpb.getPage_count());
        if (cur_page < tpb.getPage_count()) {
            tpb.setNext_num(cur_page + 1);
        }
        if (tpb.getPage_count() > 10) {
            if (cur_page > 5) {
                if (cur_page > tpb.getPage_count() - 4) {
                    tpb.setCurr_start_num(tpb.getPage_count() - 6);
                } else {
                    tpb.setCurr_start_num(cur_page - 2);
                }
            }
        }
        return tpb;
    }

    public static List<InfoBean> getFWInfoList(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "ggfw");
        return removeHTMLTag(InfoBaseManager.getBroInfoList(con_map));
    }

    public static List<InfoBean> getFWInfoHotList(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "ggfw");
        con_map.put("current_page", "1");
        return removeHTMLTag(InfoBaseManager.getBroInfoList(con_map));
    }

    public static String getCategoryTree(String site_id, String cat_id)
    {
        return CategoryBrowserTreeUtil.getBroCategoryTreeByCategoryID(Integer.parseInt(cat_id), site_id);
    }

    public static List<CategoryBean> getZTCategoryList(String zt_cat_id, String site_id)
    {
        return CategoryManager.getZTCategoryListBySiteAndType(Integer.parseInt(zt_cat_id), site_id);
    }

    public static String getSharedCategoryTree(String cat_id)
    {
        if ((cat_id != null) && (!"".equals(cat_id))) {
            return CategoryBrowserTreeUtil.getBroCategoryTreeByCategoryID(Integer.parseInt(cat_id), "");
        }
        System.out.println("InfoUtilData getSharedCategoryTree cat_id is null");
        return "";
    }

    public static String getFWCategoryTree(String cat_id)
    {
        if ((cat_id != null) && (!"".equals(cat_id))) {
            return CategoryBrowserTreeUtil.getBroCategoryTreeByCategoryID(Integer.parseInt(cat_id), "ggfw");
        }
        System.out.println("InfoUtilData getFWCategoryTree cat_id is null");
        return "";
    }

    public static List<GKInfoBean> getGKInfoListForSharedCate(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "zwgk");

        return GKInfoManager.getBroGKInfoListForSharedCategory(con_map);
    }

    public static TurnPageBean getGKInfoCountForSharedCate(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "zwgk");

        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(Integer.parseInt(GKInfoManager.getBroGKInfoCountForSharedCategory(con_map)));
        int cur_page = Integer.parseInt((String)con_map.get("current_page"));
        int page_size = Integer.parseInt((String)con_map.get("page_size"));
        tpb.setCur_page(cur_page);
        tpb.setPage_size(page_size);
        tpb.setPage_count(tpb.getCount() / tpb.getPage_size() + 1);
        if ((tpb.getCount() % tpb.getPage_size() == 0) && (tpb.getPage_count() > 1)) {
            tpb.setPage_count(tpb.getPage_count() - 1);
        }
        if (cur_page > 1) {
            tpb.setPrev_num(cur_page - 1);
        }
        tpb.setNext_num(tpb.getPage_count());
        if (cur_page < tpb.getPage_count()) {
            tpb.setNext_num(cur_page + 1);
        }
        return tpb;
    }

    public static TurnPageBean getFWInfoCount(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "ggfw");

        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(Integer.parseInt(InfoBaseManager.getBroInfoCount(con_map)));
        int cur_page = Integer.parseInt((String)con_map.get("current_page"));
        int page_size = Integer.parseInt((String)con_map.get("page_size"));
        tpb.setCur_page(cur_page);
        tpb.setPage_size(page_size);
        tpb.setPage_count(tpb.getCount() / tpb.getPage_size() + 1);
        if ((tpb.getCount() % tpb.getPage_size() == 0) && (tpb.getPage_count() > 1)) {
            tpb.setPage_count(tpb.getPage_count() - 1);
        }
        if (cur_page > 1) {
            tpb.setPrev_num(cur_page - 1);
        }
        tpb.setNext_num(tpb.getPage_count());
        if (cur_page < tpb.getPage_count()) {
            tpb.setNext_num(cur_page + 1);
        }
        return tpb;
    }

    public static List<GKInfoBean> getGKInfoList(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "cms");

        return GKInfoManager.getBroGKInfoList(con_map);
    }

    public static List<GKInfoBean> getGKInfoHotList(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "cms");
        con_map.put("current_page", "1");

        return GKInfoManager.getBroGKInfoList(con_map);
    }

    public static List<GKFbsznBean> getGKBSZNInfoHotList(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "zwgk");
        con_map.put("current_page", "1");

        return GKInfoManager.getBroGKBSZNInfoList(con_map);
    }

    public static List<GKFbsznBean> getGKBSZNInfoList(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "zwgk");

        return GKInfoManager.getBroGKBSZNInfoList(con_map);
    }

    public static void addInfoHits(String info_id, String lastHit_date)
    {
        InfoBaseManager.addInfoHits(info_id, "1", lastHit_date);
    }

    public static TurnPageBean getGKInfoCount(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "zwgk");

        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(Integer.parseInt(GKInfoManager.getBroGKInfoCount(con_map)));
        int cur_page = Integer.parseInt((String)con_map.get("current_page"));
        int page_size = Integer.parseInt((String)con_map.get("page_size"));
        tpb.setCur_page(cur_page);
        tpb.setPage_size(page_size);
        tpb.setPage_count(tpb.getCount() / tpb.getPage_size() + 1);
        if ((tpb.getCount() % tpb.getPage_size() == 0) && (tpb.getPage_count() > 1)) {
            tpb.setPage_count(tpb.getPage_count() - 1);
        }
        if (cur_page > 1) {
            tpb.setPrev_num(cur_page - 1);
        }
        tpb.setNext_num(tpb.getPage_count());
        if (cur_page < tpb.getPage_count()) {
            tpb.setNext_num(cur_page + 1);
        }
        return tpb;
    }

    public static void getInfoSearchCon(String params, Map<String, String> con_map, String app_id)
    {
        int cur_page = 1;
        int page_size = 15;
        String is_shared = "";
        String cat_id = "";
        String node_id = "";
        String orderby = "ci.released_dtime desc";
        boolean interval = false;
        String[] tempA = params.split(";");
        for (int i = 0; i < tempA.length; i++)
        {
            if (tempA[i].toLowerCase().startsWith("catalog_id="))
            {
                String catalog_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(catalog_id)) && (!catalog_id.startsWith("$catalog_id")) && (FormatUtil.isValiditySQL(catalog_id))) {
                    try
                    {
                        String catalog_sql = AppCatalogManager.getAppCatalogBean(Integer.parseInt(catalog_id)).getC_sql();
                        if ((catalog_sql != null) && (!"".equals(catalog_sql))) {
                            con_map.put("appcatalog_sql", catalog_sql);
                        } else {
                            con_map.put("appcatalog_sql", " 1=2 ");
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            if (tempA[i].toLowerCase().startsWith("kw="))
            {
                String kw = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(kw)) && (!kw.startsWith("$kw")) && (FormatUtil.isValiditySQL(kw)))
                {
                    con_map.put("kw", kw);
                    interval = true;
                }
            }
            if (tempA[i].toLowerCase().startsWith("start_time="))
            {
                String start_time = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(start_time)) && (!start_time.startsWith("$start_time")) && (FormatUtil.isValiditySQL(start_time)))
                {
                    con_map.put("start_time", start_time);
                    interval = true;
                }
            }
            if (tempA[i].toLowerCase().startsWith("end_time="))
            {
                String end_time = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(end_time)) && (!end_time.startsWith("$end_time")) && (FormatUtil.isValiditySQL(end_time)))
                {
                    con_map.put("end_time", end_time);
                    interval = true;
                }
            }
            if (tempA[i].toLowerCase().startsWith("month_day="))
            {
                String month_day = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(month_day)) && (!month_day.startsWith("$month_day")) && (FormatUtil.isValiditySQL(month_day))) {
                    con_map.put("month_day", month_day);
                }
            }
            if (tempA[i].toLowerCase().startsWith("node_id="))
            {
                node_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(node_id)) && (!node_id.startsWith("$node_id")) && (FormatUtil.isValiditySQL(node_id))) {
                    con_map.put("node_id", node_id);
                }
            }
            if (tempA[i].toLowerCase().startsWith("site_id="))
            {
                String site_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(site_id)) && (!site_id.startsWith("$site_id")) && (FormatUtil.isValiditySQL(site_id))) {
                    con_map.put("site_id", site_id);
                }
            }
            if (tempA[i].toLowerCase().startsWith("is_show_thumb="))
            {
                String is_show_thumb = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ("true".equals(is_show_thumb)) {
                    con_map.put("thumb_url", "true");
                }
            }
            if (tempA[i].toLowerCase().startsWith("istop="))
            {
                String istop = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(istop)) && (!istop.startsWith("$istop")) && (FormatUtil.isValiditySQL(istop))) {
                    con_map.put("istop", istop);
                }
            }
            if (tempA[i].toLowerCase().startsWith("cat_id=")) {
                cat_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
            }
            if (tempA[i].toLowerCase().startsWith("tag="))
            {
                String tag = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(tag)) && (!tag.startsWith("$tag")) && (FormatUtil.isValiditySQL(tag)))
                {
                    String tag_sql = "";
                    String[] tagA = tag.split(",");
                    for (int k = 0; k < tagA.length; k++)
                    {
                        if (k > 0) {
                            tag_sql = tag_sql + "or";
                        }
                        tag_sql = tag_sql + " ci.tags like '%" + tagA[k] + "%' ";
                    }
                    con_map.put("tag_sql", "(" + tag_sql + ")");
                }
            }
            if (tempA[i].toLowerCase().startsWith("orderby="))
            {
                String o_by = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(o_by)) && (!o_by.startsWith("$orderby")) && (FormatUtil.isValiditySQL(o_by))) {
                    orderby = o_by;
                }
            }
            if (tempA[i].toLowerCase().startsWith("weight="))
            {
                String weights = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(weights)) && (!weights.startsWith("$weight")) && (FormatUtil.isValiditySQL(weights))) {
                    if (weights.contains(","))
                    {
                        String weight = weights.substring(0, weights.indexOf(","));
                        String weight_end = weights.substring(weights.indexOf(",") + 1);
                        String w_cons = "";
                        if ((weight != null) && (!"".equals(weight.trim()))) {
                            w_cons = " and ci.weight >= " + weight;
                        }
                        if ((weight_end != null) && (!"".equals(weight_end.trim()))) {
                            w_cons = w_cons + " and ci.weight <= " + weight_end;
                        }
                        con_map.put("weight_con", w_cons);
                    }
                    else
                    {
                        con_map.put("weight", weights);
                    }
                }
            }
            if (tempA[i].toLowerCase().startsWith("model_id="))
            {
                String model_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(model_id)) && (!model_id.startsWith("$model_id")) && (FormatUtil.isNumeric(model_id))) {
                    con_map.put("model_id", model_id);
                }
            }
            if (tempA[i].toLowerCase().startsWith("size="))
            {
                String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(ps)) && (!ps.startsWith("$size")) && (FormatUtil.isNumeric(ps))) {
                    page_size = Integer.parseInt(ps);
                }
            }
            if (tempA[i].toLowerCase().startsWith("cur_page="))
            {
                String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(cp)) && (!cp.startsWith("$cur_page")) && (FormatUtil.isNumeric(cp))) {
                    cur_page = Integer.parseInt(cp);
                }
            }
            if (tempA[i].toLowerCase().startsWith("gk_index="))
            {
                String gk_index = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(gk_index)) && (!gk_index.startsWith("$gk_index")) && (FormatUtil.isValiditySQL(gk_index))) {
                    con_map.put("gk_index", gk_index);
                }
            }
            if (tempA[i].toLowerCase().startsWith("doc_no="))
            {
                String doc_no = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(doc_no)) && (!doc_no.startsWith("$doc_no")) && (FormatUtil.isValiditySQL(doc_no))) {
                    con_map.put("doc_no", doc_no);
                }
            }
            if (tempA[i].toLowerCase().startsWith("title="))
            {
                String title = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(title)) && (!title.startsWith("$title")) && (FormatUtil.isValiditySQL(title))) {
                    con_map.put("title", title);
                }
            }
            if (tempA[i].toLowerCase().startsWith("sub_title="))
            {
                String sub_title = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(sub_title)) && (!sub_title.startsWith("$sub_title")) && (FormatUtil.isValiditySQL(sub_title))) {
                    con_map.put("sub_title", sub_title);
                }
            }
            if (tempA[i].toLowerCase().startsWith("top_title="))
            {
                String top_title = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(top_title)) && (!top_title.startsWith("$top_title")) && (FormatUtil.isValiditySQL(top_title))) {
                    con_map.put("top_title", top_title);
                }
            }
            if (tempA[i].toLowerCase().startsWith("gk_duty_dept="))
            {
                String gk_duty_dept = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(gk_duty_dept)) && (!gk_duty_dept.startsWith("$gk_duty_dept")) && (FormatUtil.isValiditySQL(gk_duty_dept))) {
                    con_map.put("gk_duty_dept", gk_duty_dept);
                }
            }
            if (tempA[i].toLowerCase().startsWith("gk_input_dept="))
            {
                String gk_input_dept = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(gk_input_dept)) && (!gk_input_dept.startsWith("$gk_input_dept")) && (FormatUtil.isValiditySQL(gk_input_dept))) {
                    con_map.put("gk_input_dept", gk_input_dept);
                }
            }
            if (tempA[i].toLowerCase().startsWith("description="))
            {
                String description = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(description)) && (!description.startsWith("$description")) && (FormatUtil.isValiditySQL(description))) {
                    con_map.put("description", description);
                }
            }
            if (tempA[i].toLowerCase().startsWith("topic_key="))
            {
                String topic_key = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(topic_key)) && (!topic_key.startsWith("$topic_key")) && (FormatUtil.isValiditySQL(topic_key))) {
                    con_map.put("topic_key", topic_key);
                }
            }
            if (tempA[i].toLowerCase().startsWith("gen_st="))
            {
                String gen_st = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(gen_st)) && (!gen_st.startsWith("$gen_st")) && (FormatUtil.isValiditySQL(gen_st))) {
                    con_map.put("gen_st", gen_st);
                }
            }
            if (tempA[i].toLowerCase().startsWith("gen_et="))
            {
                String gen_et = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(gen_et)) && (!gen_et.startsWith("$gen_et")) && (FormatUtil.isValiditySQL(gen_et))) {
                    con_map.put("gen_et", gen_et);
                }
            }
            if (tempA[i].toLowerCase().startsWith("is_shared=")) {
                is_shared = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
            }
            if (tempA[i].toLowerCase().startsWith("cat_class_id="))
            {
                String cat_class_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(cat_class_id)) && (!cat_class_id.startsWith("$cat_class_id")) && (FormatUtil.isValiditySQL(cat_class_id))) {
                    if (cat_class_id.indexOf(",") > -1) {
                        con_map.put("cat_sql", " ca.cat_class_id in (" + cat_class_id + ")");
                    } else {
                        con_map.put("cat_sql", " ca.cat_class_id = " + cat_class_id);
                    }
                }
            }
            if (tempA[i].toLowerCase().startsWith("input_user="))
            {
                String input_user = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(input_user)) && (!input_user.startsWith("$input_user")) && (FormatUtil.isValiditySQL(input_user))) {
                    con_map.put("input_user", input_user);
                }
            }
            if (tempA[i].toLowerCase().startsWith("info_status="))
            {
                String info_status = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(info_status)) && (!info_status.startsWith("$info_status")) && (FormatUtil.isValiditySQL(info_status))) {
                    con_map.put("info_status", info_status);
                }
            }
            if (tempA[i].toLowerCase().startsWith("is_host="))
            {
                String is_host = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(is_host)) && (!is_host.startsWith("$is_host")) && (FormatUtil.isValiditySQL(is_host))) {
                    con_map.put("is_host", is_host);
                }
            }
            if (tempA[i].toLowerCase().startsWith("sort_name="))
            {
                String sort_name = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(sort_name)) && (!sort_name.startsWith("$sort_name")) && (FormatUtil.isValiditySQL(sort_name))) {
                    con_map.put("sort_name", sort_name);
                }
            }
            if (tempA[i].toLowerCase().startsWith("sort_type="))
            {
                String sort_type = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(sort_type)) && (!sort_type.startsWith("$sort_type")) && (FormatUtil.isValiditySQL(sort_type))) {
                    con_map.put("sort_type", sort_type);
                }
            }
        }
        if ((!"".equals(cat_id)) && (!"0".equals(cat_id)) && (!cat_id.startsWith("$cat_id")) && (FormatUtil.isValiditySQL(cat_id))) {
            if ((!"".equals(node_id)) && (!"true".equals(is_shared))) {
                getCategorySearchSql(cat_id, con_map, app_id);
            } else if ("zwgk".equals(app_id))
            {
                if (cat_id.indexOf(",") > -1) {
                    con_map.put("cat_sql", "(gk.topic_id in (" + cat_id + ") or gk.theme_id in (" + cat_id + ") or gk.serve_id in (" + cat_id + ") or ca.cat_class_id in (" + cat_id + ") or gk.gkbzh_id in (" + cat_id + ") or gk.gkhj_id in (" + cat_id + "))");
                } else {
                    con_map.put("cat_sql", "(gk.topic_id=" + cat_id + " or gk.theme_id=" + cat_id + " or gk.serve_id=" + cat_id + " or ca.cat_class_id = " + cat_id + " or gk.gkbzh_id = " + cat_id + " or gk.gkhj_id = " + cat_id+")");
                }
            }
            else {
                getCategorySearchSql(cat_id, con_map, app_id);
            }
        }
        if (interval == true) {
            con_map.remove("month_day");
        }
        con_map.put("page_size", page_size + "");
        con_map.put("current_page", cur_page + "");
        con_map.put("start_num", (cur_page - 1) * page_size + "");
        con_map.put("orderby", orderby);
    }

    public static void getCategorySearchSql(String cat_id, Map<String, String> con_map, String app_id)
    {
        if (("10".equals(cat_id)) || ("11".equals(cat_id)) || ("12".equals(cat_id)))
        {
            con_map.put("cat_sql", "ci.cat_id = " + cat_id);
            if (con_map.containsKey("node_id")) {
                con_map.put("site_id", con_map.get("node_id"));
            } else {
                con_map.remove("site_id");
            }
        }
        else
        {
            String site_id = (String)con_map.get("site_id");
            if ("zwgk".equals(app_id)) {
                site_id = (String)con_map.get("node_id");
            }
            if ("ggfw".equals(app_id))
            {
                site_id = "ggfw";
                con_map.put("site_id", site_id);
            }
            String constr = "";
            if ((!"zwgk".equals(app_id)) && (!"ggfw".equals(app_id))) {
                constr = " and site_id='" + site_id + "'";
            }
            String cat_sql = "";
            String[] tempA = cat_id.split(",");
            for (int i = 0; i < tempA.length; i++)
            {
                CategoryBean cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(tempA[i]), site_id);
                if (cb != null)
                {
                    if (!"".equals(cat_sql)) {
                        cat_sql = cat_sql + " or ";
                    }
                    cat_sql = cat_sql + " ca.cat_id in ( select cat_id from cs_info_category where cat_position like '" + cb.getCat_position() + "%' " + constr + " ) ";
                }
            }
            if ((cat_sql != null) && (!"".equals(cat_sql))) {
                con_map.put("cat_sql", cat_sql);
            }
        }
    }

    public static List<RelatedInfoBean> getRelatedInfoList(String info_id)
    {
        Map<String, String> m = new HashMap();
        m.put("info_id", info_id);
        List<RelatedInfoBean> l = InfoBaseManager.getBroRelatedInfoList(m);
        if ((l != null) && (l.size() > 0)) {
            for (RelatedInfoBean info : l) {
                info.setTitle(info.getTitle().replaceAll("<[Bb][Rr]/?>", ""));
            }
        }
        return l;
    }

    public static List<GKResFileBean> getGKResourceFile(String info_id)
    {
        return GKInfoManager.getGKResFileList(info_id);
    }

    public static String getSiteDomain(String site_id)
    {
        return "http://" + SiteDomainManager.getSiteDomainBySiteID(site_id);
    }

    public static List<CateCurPositionBean> getCategoryPosition(String cat_id, String site_id, String node_id, String page_type)
    {
        if ((node_id != null) && (!"".equals(node_id)) && (!"$node_id".equals(node_id))) {
            site_id = node_id;
        }
        return CategoryUtil.getCategoryPosition(cat_id, site_id, page_type);
    }

    public static CategoryBean getCategoryObject(String cat_id, String site_id, String node_id)
    {
        if ((node_id != null) && (!"".equals(node_id)) && (!"$node_id".equals(node_id))) {
            site_id = node_id;
        }
        try
        {
            CategoryBean cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(cat_id), site_id);
            if (cb != null) {
                cb.setIs_sub(CategoryManager.isHasChildNode(cb.getCat_id(), cb.getSite_id()));
            }
            return cb;
        }
        catch (Exception e) {}
        return null;
    }



    public static CategoryBean getCategoryObject(String cat_id)
    {
        try
        {
            CategoryBean cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(cat_id));
            if (cb != null) {
                cb.setIs_sub(CategoryManager.isHasChildNode(cb.getCat_id()));
            }
            return cb;
        }
        catch (Exception e) {}
        return null;
    }

    public static List<CategoryBean> getChildCategoryList(String cat_id, String site_id)
    {
        return CategoryManager.getChildCategoryListForBrowser(Integer.parseInt(cat_id), site_id);
    }

    public static List<CategoryBean> getChildCategoryList(String cat_id)
    {
        return CategoryManager.getChildCategoryListForBrowser(Integer.parseInt(cat_id));
    }

    public static List<AppCatalogBean> getChildGKAppCatalogList(String cata_id)
    {
        return AppCatalogManager.getChildCatalogList(Integer.parseInt(cata_id));
    }

    public static List<SMCategoryBean> getGKCategoryList(String node_id)
    {
        CategoryBean cb = CategoryManager.getCategoryBeanCatID(0, node_id);
        return CategoryManager.getAllChildForSMCategoryBean(cb).getSm_list();
    }

    public static String getGKNodeName(String node_id)
    {
        return GKNodeManager.getNodeNameByNodeID(node_id);
    }

    public static String getGKCategoryTree(String node_id)
    {
        List<CategoryBean> list = CategoryManager.getCategoryListBySiteID(node_id, 0);
        return "[" + CategoryBrowserTreeUtil.getBroCategoryTreeJsonStrHandl(list) + "]";
    }

    public static List<GKNodeCategory> getGKNodeList(String cate_id)
    {
        int cat_id = 0;
        if ((cate_id != null) && (!"".equals(cate_id.trim()))) {
            cat_id = Integer.parseInt(cate_id);
        }
        return GKNodeCateManager.getNodeListForCatID(cat_id);
    }

    public static String getGKAppCatalogTree(String cata_id)
    {
        return AppCatalogManager.getAppCatalogTree(Integer.parseInt(cata_id));
    }

    public static ArticleBean getArticleObject(String info_id)
    {
        return (ArticleBean)ModelUtil.select(info_id, "", "article");
    }

    public static Map getArticleCustomObject(String info_id)
    {
        return (Map)ModelUtil.select(info_id, "", "article_custom");
    }

    public static Object getInfoObject(String info_id)
    {
        InfoBean ib = InfoBaseManager.getInfoById(info_id);
        if (ib != null) {
            return ModelUtil.select(info_id, ib.getSite_id(), ModelManager.getModelEName(ib.getModel_id()));
        }
        return null;
    }

    public static String getSiteName(String site_id)
    {
        if ((site_id != null) && (!"".equals(site_id)))
        {
            SiteBean sb = SiteManager.getSiteBeanBySiteID(site_id);
            if (sb != null) {
                return sb.getSite_name();
            }
            return "";
        }
        return "";
    }

    public static List<InfoCountBean> getGKPubInfoCount(int row_count)
    {
        Map<String, String> m = new HashMap();
        return InfoBaseManager.getInfoTotalForApp(row_count, "zwgk", m);
    }

    public static List<InfoCountBean> getGKPubInfoCount(String params)
    {
        Map<String, String> m = new HashMap();
        int row_count = getPubInfoCountConMap(params, m);
        return InfoBaseManager.getInfoTotalForApp(row_count, "zwgk", m);
    }

    public static int getPubInfoCountConMap(String params, Map<String, String> m)
    {
        int row_count = 10;
        String[] tempA = params.split(";");
        for (int i = 0; i < tempA.length; i++)
        {
            if (tempA[i].toLowerCase().startsWith("row_count="))
            {
                String row_counts = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(row_counts)) && (!row_counts.startsWith("$row_count")) && (FormatUtil.isValiditySQL(row_counts))) {
                    row_count = Integer.parseInt(row_counts);
                }
            }
            if (tempA[i].toLowerCase().startsWith("count_type="))
            {
                String count_type = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(count_type)) && (!count_type.startsWith("$count_type")) && (FormatUtil.isValiditySQL(count_type)))
                {
                    if ("ultimo".equals(count_type))
                    {
                        String dt = "";
                        String d = DateUtil.getCurrentDate();
                        String[] dateA = d.split("-");
                        int year = Integer.parseInt(dateA[0]);
                        int month = Integer.parseInt(dateA[1]);
                        if (month == 1)
                        {
                            year -= 1;
                            month = 12;
                        }
                        else
                        {
                            month -= 1;
                        }
                        if (month < 10) {
                            dt = year + "-0" + month;
                        } else {
                            dt = year + "-" + month;
                        }
                        m.put("count_month", dt);
                    }
                    if ("instant".equals(count_type)) {
                        m.put("count_month", DateUtil.getCurrentDateTime("yyyy-MM"));
                    }
                }
            }
        }
        return row_count;
    }

    public static List<InfoCountBean> getSitePubInfoCount(int row_count)
    {
        Map<String, String> m = new HashMap();
        return InfoBaseManager.getInfoTotalForApp(row_count, "cms", m);
    }

    public static List<InfoCountBean> getSitePubInfoCount(String params)
    {
        Map<String, String> m = new HashMap();
        int row_count = getPubInfoCountConMap(params, m);
        return InfoBaseManager.getInfoTotalForApp(row_count, "cms", m);
    }

    public static List<SiteCountBean> getSiteInfoCountList(String param)
    {
        String[] tempA = param.split(";");
        Map<String, String> m = new HashMap();
        for (int i = 0; i < tempA.length; i++)
        {
            if (tempA[i].toLowerCase().startsWith("site_id="))
            {
                String site_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(site_id)) && (!site_id.startsWith("$site_id")) && (FormatUtil.isValiditySQL(site_id))) {
                    m.put("site_ids", site_id);
                }
            }
            if (tempA[i].toLowerCase().startsWith("start_time="))
            {
                String start_time = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(start_time)) && (!start_time.startsWith("$start_time")) && (FormatUtil.isValiditySQL(start_time))) {
                    m.put("start_day", start_time);
                }
            }
            if (tempA[i].toLowerCase().startsWith("end_time="))
            {
                String end_time = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(end_time)) && (!end_time.startsWith("$end_time")) && (FormatUtil.isValiditySQL(end_time))) {
                    m.put("end_day", end_time);
                }
            }
            if (tempA[i].toLowerCase().startsWith("row_count="))
            {
                String row_count = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(row_count)) && (!row_count.startsWith("$row_count")) && (FormatUtil.isValiditySQL(row_count))) {
                    m.put("num", row_count);
                }
            }
            if (tempA[i].toLowerCase().startsWith("count_type="))
            {
                String count_type = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(count_type)) && (!count_type.startsWith("$count_type")) && (FormatUtil.isValiditySQL(count_type)))
                {
                    if ("ultimo".equals(count_type)) {
                        m.put("atype", "lastmonth");
                    }
                    if ("instant".equals(count_type)) {
                        m.put("atype", "currmonth");
                    }
                }
            }
        }
        if (!m.containsKey("num")) {
            m.put("num", "20");
        }
        return BrowserAPIService.getSiteCountListByMap(m);
    }

    public static List<SiteCountBean> getSiteCountListForDept(String params)
    {
        int row_count = 10;
        String[] tempA = params.split(";");
        Map<String, String> m = new HashMap();
        for (int i = 0; i < tempA.length; i++)
        {
            if (tempA[i].toLowerCase().startsWith("site_id="))
            {
                String site_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(site_id)) && (!site_id.startsWith("$site_id")) && (FormatUtil.isValiditySQL(site_id))) {
                    m.put("site_ids", site_id);
                }
            }
            if (tempA[i].toLowerCase().startsWith("start_day="))
            {
                String start_day = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(start_day)) && (!start_day.startsWith("$start_day")) && (FormatUtil.isValiditySQL(start_day))) {
                    m.put("start_day", start_day);
                }
            }
            if (tempA[i].toLowerCase().startsWith("end_day="))
            {
                String end_day = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(end_day)) && (!end_day.startsWith("$end_day")) && (FormatUtil.isValiditySQL(end_day))) {
                    m.put("end_day", end_day);
                }
            }
            if (tempA[i].toLowerCase().startsWith("row_count="))
            {
                String row_counts = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(row_counts)) && (!row_counts.startsWith("$row_count")) && (FormatUtil.isValiditySQL(row_counts))) {
                    row_count = Integer.parseInt(row_counts);
                }
            }
            if (tempA[i].toLowerCase().startsWith("count_type="))
            {
                String count_type = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(count_type)) && (!count_type.startsWith("$count_type")) && (FormatUtil.isValiditySQL(count_type)))
                {
                    if ("ultimo".equals(count_type)) {
                        m.put("atype", "lastmonth");
                    }
                    if ("instant".equals(count_type)) {
                        m.put("atype", "currmonth");
                    }
                }
            }
            if (tempA[i].toLowerCase().startsWith("cate_ids="))
            {
                String cate_ids = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(cate_ids)) && (!cate_ids.startsWith("$cate_ids")) && (FormatUtil.isValiditySQL(cate_ids))) {
                    m.put("cate_ids", cate_ids);
                }
            }
        }
        m.put("num", row_count + "");

        return BrowserAPIService.getDeptNameListByMap(m);
    }

    public static List<InfoCountBean> getSiteAccessStatistics(String item_name, int row_count)
    {
        return InfoBaseManager.getSiteAccessStatistics(item_name, row_count, "cms");
    }

    public static List<InfoCountBean> getGKAccessStatistics(String item_name, int row_count)
    {
        return InfoBaseManager.getSiteAccessStatistics(item_name, row_count, "zwgk");
    }

    public static List<InfoCountBean> getInfoTotalForSiteUser(String site_id, int row_count)
    {
        return InfoBaseManager.getInfoTotalForSiteUser(site_id, row_count);
    }

    public static String getGKPublishStatistics(String type)
    {
        return GKInfoManager.getGKPublishStatistics(type);
    }

    public static String getYsqStatistics()
    {
        Map<String, String> m = new HashMap();
        return YsqgkInfoManager.getYsqStatistics(m);
    }

    public static List<InfoBean> getRssInfoList(String params)
    {
        String[] tempA = params.split(";");
        String rss_url = "";
        int cur_page = 1;
        int size = 10;
        for (int i = 0; i < tempA.length; i++)
        {
            if (tempA[i].toLowerCase().startsWith("rss_url="))
            {
                String urlTemp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(urlTemp)) && (!rss_url.startsWith("$rss_url"))) {
                    rss_url = urlTemp;
                }
            }
            if (tempA[i].toLowerCase().startsWith("cur_page="))
            {
                String cur_pageStr = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(cur_pageStr)) && (!cur_pageStr.startsWith("$cur_page"))) {
                    cur_page = Integer.parseInt(cur_pageStr);
                }
            }
            if (tempA[i].toLowerCase().startsWith("size="))
            {
                String sizeStr = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(sizeStr)) && (!sizeStr.startsWith("$size"))) {
                    size = Integer.parseInt(sizeStr);
                }
            }
        }
        return RssReaderManager.getRssInfoList(rss_url, cur_page, size);
    }

    public static TurnPageBean getRssInfoCount(String params)
    {
        String[] tempA = params.split(";");
        String rss_url = "";
        int cur_page = 1;
        int size = 10;
        for (int i = 0; i < tempA.length; i++)
        {
            if (tempA[i].toLowerCase().startsWith("rss_url="))
            {
                String urlTemp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(urlTemp)) && (!rss_url.startsWith("$rss_url"))) {
                    rss_url = urlTemp;
                }
            }
            if (tempA[i].toLowerCase().startsWith("cur_page="))
            {
                String cur_pageStr = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(cur_pageStr)) && (!cur_pageStr.startsWith("$cur_page"))) {
                    cur_page = Integer.parseInt(cur_pageStr);
                }
            }
            if (tempA[i].toLowerCase().startsWith("size="))
            {
                String sizeStr = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(sizeStr)) && (!sizeStr.startsWith("$size"))) {
                    size = Integer.parseInt(sizeStr);
                }
            }
        }
        int count = RssReaderManager.getRssInfoCount(rss_url);
        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(count);
        tpb.setCur_page(cur_page);
        tpb.setPage_size(size);
        tpb.setPage_count(tpb.getCount() / tpb.getPage_size() + 1);
        if ((tpb.getCount() % tpb.getPage_size() == 0) && (tpb.getPage_count() > 1)) {
            tpb.setPage_count(tpb.getPage_count() - 1);
        }
        if (cur_page > 1) {
            tpb.setPrev_num(cur_page - 1);
        }
        tpb.setNext_num(tpb.getPage_count());
        if (cur_page < tpb.getPage_count()) {
            tpb.setNext_num(cur_page + 1);
        }
        return tpb;
    }

    public static List<YsqgkListBean> getYsqgkList(String params)
    {
        Map<String, String> con_map = new HashMap();
        getYsqgkSeachCon(params, con_map);
        return YsqgkInfoDAO.getYsqgkLists(con_map);
    }

    public static TurnPageBean getYsqgkCount(String params)
    {
        Map<String, String> con_map = new HashMap();
        getYsqgkSeachCon(params, con_map);

        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(YsqgkInfoDAO.getYsqgkListsCount(con_map));
        int cur_page = Integer.parseInt((String)con_map.get("current_page"));
        int page_size = Integer.parseInt((String)con_map.get("page_size"));
        tpb.setCur_page(cur_page);
        tpb.setPage_size(page_size);
        tpb.setPage_count(tpb.getCount() / tpb.getPage_size() + 1);
        if ((tpb.getCount() % tpb.getPage_size() == 0) && (tpb.getPage_count() > 1)) {
            tpb.setPage_count(tpb.getPage_count() - 1);
        }
        if (cur_page > 1) {
            tpb.setPrev_num(cur_page - 1);
        }
        tpb.setNext_num(tpb.getPage_count());
        if (cur_page < tpb.getPage_count()) {
            tpb.setNext_num(cur_page + 1);
        }
        return tpb;
    }

    public static YsqgkBean getYsqObject(String ysq_id)
    {
        return YsqgkInfoManager.getYsqgkBean(ysq_id);
    }

    public static YsqgkBean getYsqgkObject(String ysq_code, String query_code)
    {
        return YsqgkInfoManager.getYsqgkBeanForQuery(ysq_code, query_code);
    }

    public static void getYsqgkSeachCon(String params, Map<String, String> con_map)
    {
        int cur_page = 1;
        int page_size = 15;
        String orderby = "ysq_id desc";
        String[] tempA = params.split(";");
        for (int i = 0; i < tempA.length; i++)
        {
            if (tempA[i].toLowerCase().startsWith("orderby="))
            {
                String o_by = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(o_by)) && (!o_by.startsWith("$orderby")) && (FormatUtil.isValiditySQL(o_by))) {
                    orderby = o_by;
                }
            }
            if (tempA[i].toLowerCase().startsWith("kw="))
            {
                String kw = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(kw)) && (!kw.startsWith("$kw")) && (FormatUtil.isValiditySQL(kw))) {
                    con_map.put("kw", kw);
                }
            }
            if (tempA[i].toLowerCase().startsWith("node_id="))
            {
                String node_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(node_id)) && (!node_id.startsWith("$node_id")) && (FormatUtil.isValiditySQL(node_id))) {
                    con_map.put("node_id", node_id);
                }
            }
            if (tempA[i].toLowerCase().startsWith("ysq_code="))
            {
                String ysq_code = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(ysq_code)) && (!ysq_code.startsWith("$ysq_code")) && (FormatUtil.isValiditySQL(ysq_code))) {
                    con_map.put("ysq_code", ysq_code);
                }
            }
            if (tempA[i].toLowerCase().startsWith("name="))
            {
                String name = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(name)) && (!name.startsWith("$name")) && (FormatUtil.isValiditySQL(name))) {
                    con_map.put("name", name);
                }
            }
            if (tempA[i].toLowerCase().startsWith("publish_state="))
            {
                String publish_state = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(publish_state)) && (!publish_state.startsWith("$publish_state")) && (FormatUtil.isValiditySQL(publish_state))) {
                    con_map.put("publish_state", publish_state);
                }
            }
            if (tempA[i].toLowerCase().startsWith("do_state="))
            {
                String do_state = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(do_state)) && (!do_state.startsWith("$do_state")) && (FormatUtil.isValiditySQL(do_state))) {
                    con_map.put("do_state", do_state);
                }
            }
            if (tempA[i].toLowerCase().startsWith("start_time="))
            {
                String start_time = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(start_time)) && (!start_time.startsWith("$start_time")) && (FormatUtil.isValiditySQL(start_time))) {
                    con_map.put("start_time", start_time);
                }
            }
            if (tempA[i].toLowerCase().startsWith("end_time="))
            {
                String end_time = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(end_time)) && (!end_time.startsWith("$end_time")) && (FormatUtil.isValiditySQL(end_time))) {
                    con_map.put("end_time", end_time);
                }
            }
            if (tempA[i].toLowerCase().startsWith("cur_page="))
            {
                String cur_pageStr = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(cur_pageStr)) && (!cur_pageStr.startsWith("$cur_page"))) {
                    cur_page = Integer.parseInt(cur_pageStr);
                }
            }
            if (tempA[i].toLowerCase().startsWith("size="))
            {
                String sizeStr = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(sizeStr)) && (!sizeStr.startsWith("$size"))) {
                    page_size = Integer.parseInt(sizeStr);
                }
            }
            if (tempA[i].toLowerCase().startsWith("site_id="))
            {
                String site_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(site_id)) && (!site_id.startsWith("site_id")) && (FormatUtil.isValiditySQL(site_id))) {
                    con_map.put("site_id", site_id);
                }
            }
        }
        con_map.put("final_status", "0");
        con_map.put("start_num", (cur_page - 1) * page_size + "");
        con_map.put("page_size", page_size + "");
        con_map.put("current_page", cur_page + "");
        String[] oy = orderby.split(" ");
        con_map.put("sort_name", oy[0]);
        con_map.put("sort_type", oy[1]);
    }

    public static List<SiteInfoTuisongBean> getTuiSongCountsList(String params)
    {
        Map<String, String> con_map = new HashMap();
        String[] tempA = params.split(";");
        for (int i = 0; i < tempA.length; i++)
        {
            if (tempA[i].toLowerCase().startsWith("app_id="))
            {
                String app_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(app_id)) && (!app_id.startsWith("$app_id")) && (FormatUtil.isValiditySQL(app_id))) {
                    con_map.put("app_id", app_id);
                }
            }
            if (tempA[i].toLowerCase().startsWith("site_id="))
            {
                String site_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(site_id)) && (!site_id.startsWith("$site_id")) && (FormatUtil.isValiditySQL(site_id))) {
                    con_map.put("site_id", site_id);
                }
            }
            if (tempA[i].toLowerCase().startsWith("start_time="))
            {
                String start_time = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(start_time)) && (!start_time.startsWith("$start_time")) && (FormatUtil.isValiditySQL(start_time))) {
                    con_map.put("start_time", start_time);
                }
            }
            if (tempA[i].toLowerCase().startsWith("end_time="))
            {
                String end_time = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(end_time)) && (!end_time.startsWith("$end_time")) && (FormatUtil.isValiditySQL(end_time))) {
                    con_map.put("end_time", end_time);
                }
            }
            if (tempA[i].toLowerCase().startsWith("count_type="))
            {
                String count_type = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(count_type)) && (!count_type.startsWith("$count_type")) && (FormatUtil.isValiditySQL(count_type)))
                {
                    if ("ultimo".equals(count_type)) {
                        con_map.put("atype", "lastmonth");
                    }
                    if ("instant".equals(count_type)) {
                        con_map.put("atype", "currmonth");
                    }
                }
            }
        }
        return TuisongCountManager.getOneSiteTuisCounts(con_map);
    }

    public static List<WcmZykFile> getZyFileListByInfoId(String info_id, String fieldName)
    {
        return WcmZykInfoService.getZykinfoFileListByInfoId(info_id, fieldName);
    }

    public static List<CmsCountBean> getSiteCountListForSource(String params)
    {
        int row_count = 10;
        String[] tempA = params.split(";");
        Map<String, String> m = new HashMap();
        for (int i = 0; i < tempA.length; i++)
        {
            if (tempA[i].toLowerCase().startsWith("site_id="))
            {
                String site_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(site_id)) && (!site_id.startsWith("$site_id")) && (FormatUtil.isValiditySQL(site_id))) {
                    m.put("site_ids", site_id);
                }
            }
            if (tempA[i].toLowerCase().startsWith("start_day="))
            {
                String start_day = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(start_day)) && (!start_day.startsWith("$start_day")) && (FormatUtil.isValiditySQL(start_day))) {
                    m.put("start_day", start_day);
                }
            }
            if (tempA[i].toLowerCase().startsWith("end_day="))
            {
                String end_day = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(end_day)) && (!end_day.startsWith("$end_day")) && (FormatUtil.isValiditySQL(end_day))) {
                    m.put("end_day", end_day);
                }
            }
            if (tempA[i].toLowerCase().startsWith("row_count="))
            {
                String row_counts = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(row_counts)) && (!row_counts.startsWith("$row_count")) && (FormatUtil.isValiditySQL(row_counts))) {
                    row_count = Integer.parseInt(row_counts);
                }
            }
            if (tempA[i].toLowerCase().startsWith("count_type="))
            {
                String count_type = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(count_type)) && (!count_type.startsWith("$count_type")) && (FormatUtil.isValiditySQL(count_type)))
                {
                    if ("ultimo".equals(count_type)) {
                        m.put("atype", "lastmonth");
                    }
                    if ("instant".equals(count_type)) {
                        m.put("atype", "currmonth");
                    }
                }
            }
            if (tempA[i].toLowerCase().startsWith("cate_ids="))
            {
                String cate_ids = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(cate_ids)) && (!cate_ids.startsWith("$cate_ids")) && (FormatUtil.isValiditySQL(cate_ids))) {
                    m.put("cate_ids", cate_ids);
                }
            }
        }
        m.put("num", row_count + "");

        return BrowserAPIService.getSourceNameListByMap(m);
    }

    public static List<InfoAccessBean> getOrderInfoListsForBor(String param)
    {
        String[] tempA = param.split(";");
        Map<String, String> m = new HashMap();
        for (int i = 0; i < tempA.length; i++)
        {
            if (tempA[i].toLowerCase().startsWith("site_id="))
            {
                String site_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(site_id)) && (!site_id.startsWith("$site_id")) && (FormatUtil.isValiditySQL(site_id))) {
                    m.put("site_id", site_id);
                }
            }
            if (tempA[i].toLowerCase().startsWith("start_time="))
            {
                String start_time = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(start_time)) && (!start_time.startsWith("$start_time")) && (FormatUtil.isValiditySQL(start_time))) {
                    m.put("start_day", start_time);
                }
            }
            if (tempA[i].toLowerCase().startsWith("end_time="))
            {
                String end_time = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(end_time)) && (!end_time.startsWith("$end_time")) && (FormatUtil.isValiditySQL(end_time))) {
                    m.put("end_day", end_time);
                }
            }
            if (tempA[i].toLowerCase().startsWith("row_count="))
            {
                String row_count = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(row_count)) && (!row_count.startsWith("$row_count")) && (FormatUtil.isValiditySQL(row_count))) {
                    m.put("row_count", row_count);
                }
            }
            if (tempA[i].toLowerCase().startsWith("type="))
            {
                String type = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(type)) && (!type.startsWith("$type")) && (FormatUtil.isValiditySQL(type))) {
                    m.put("type", type);
                }
            }
        }
        if (((String)m.get("type")).equals("cate")) {
            return AccessCountManager.getCatOrderListByCat_id(m);
        }
        return AccessCountManager.getInfoOrderListByInfo_id(m);
    }

    public static VideoBean getVideoBeanByInfoId(String info_id, String site_id)
    {
        return (VideoBean)ModelUtil.select(info_id, site_id, "video");
    }

    public static Map<String, String> getDayAccessCountList(String site_id, String constant)
    {
        return AccessCountManager.getDayAccessCountList(site_id, constant);
    }

    public static long getOnline(String site_id)
    {
        return OnlineCounter.getOnline(site_id);
    }

    public static List<RelatedInfoBean> getRelatedInfoListByID(String info_id)
    {
        Map<String, String> m = new HashMap();
        m.put("info_id", info_id);
        List<RelatedInfoBean> l = InfoBaseManager.getRelatedInfoList(m);
        if ((l != null) && (l.size() > 0)) {
            for (RelatedInfoBean info : l) {
                info.setTitle(info.getTitle().replaceAll("<[Bb][Rr]/?>", ""));
            }
        }
        return l;
    }

    public static List getAllInfoList(String params)
    {
        Map<String, String> con_map = new HashMap();
        getInfoSearchCon(params, con_map, "cms");
        List allInfo = new ArrayList();
        List<InfoBean> infos = removeHTMLTag(InfoBaseManager.getBroInfoList(con_map));
        String gk_index = (String)con_map.get("gk_index");
        if ((infos != null) && (infos.size() > 0)) {
            for (int i = 0; i < infos.size(); i++)
            {
                InfoBean infoBean = (InfoBean)infos.get(i);
                if ((gk_index != null) && (!"".equals(gk_index)))
                {
                    if (infoBean.getModel_id() == 14)
                    {
                        GKInfoBean gkInfoBean = GKInfoManager.getGKInfoBean(infoBean.getInfo_id() + "");
                        if (gkInfoBean.getGk_index().contains(gk_index)) {
                            allInfo.add(gkInfoBean);
                        }
                    }
                }
                else if (infoBean.getModel_id() == 14)
                {
                    GKInfoBean gkInfoBean = GKInfoManager.getGKInfoBean(infoBean.getInfo_id() + "");
                    allInfo.add(gkInfoBean);
                }
                else
                {
                    allInfo.add(infoBean);
                }
            }
        }
        return allInfo;
    }

    public static void main(String[] args)
    {
        System.out.println(getGKPubInfoCount("count_type=ultimo"));
    }
}
