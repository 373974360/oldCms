package com.deya.wcm.services.cms.info;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.services.cms.category.*;
import com.deya.wcm.services.model.services.BeanToMapUtil;
import com.deya.wcm.services.org.role.RoleUserManager;
import com.yinhai.model.InfoWorkStep;
import org.apache.commons.beanutils.BeanUtils;

import com.deya.util.DateUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.category.SyncBean;
import com.deya.wcm.bean.cms.count.InfoAccessBean;
import com.deya.wcm.bean.cms.count.InfoCountBean;
import com.deya.wcm.bean.cms.info.GKInfoBean;
import com.deya.wcm.bean.cms.info.GKResFileBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.cms.info.RelatedInfoBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.bean.org.user.UserRegisterBean;
import com.deya.wcm.bean.system.formodel.ModelBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.cms.info.InfoDAO;
import com.deya.wcm.dao.zwgk.info.GKInfoDAO;
import com.deya.wcm.services.cms.count.AccessCountManager;
import com.deya.wcm.services.cms.workflow.WorkFlowManager;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import com.deya.wcm.services.control.site.SiteAppRele;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.org.user.UserManager;
import com.deya.wcm.services.system.formodel.ModelManager;
import com.deya.wcm.services.zwgk.index.IndexManager;
import com.deya.wcm.services.zwgk.info.GKInfoManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;
import com.deya.wcm.template.velocity.impl.VelocityInfoContextImp;

/**
 * @author zhuliang
 * @version 1.0
 * @date 2011-6-15 上午10:18:04
 */
public class InfoBaseManager {
    protected static String LINK_MODEL_ENAME = "link";
    public static String ARTICLE_MODEL_ENAME = "article";
    public static String GKTYGS_MODEL_ENAME = "gkftygs";

    public static InfoBean getInfoById(String info_id, String site_id) {
        return InfoDAO.getInfoById(info_id, site_id);
    }

    public static InfoBean getInfoById(String info_id) {
        return InfoDAO.getInfoById(info_id);
    }

    /**
     * 根据条件得到信息列表（前台使用）
     *
     * @param Map *
     * @return List<UserRegisterBean>
     */
    public static List<InfoBean> getBroInfoList(Map<String, String> map) {
        int start_page = Integer.parseInt(map.get("current_page"));
        int page_size = Integer.parseInt(map.get("page_size"));
        map.put("start_num", ((start_page - 1) * page_size) + "");
        map.put("page_size", page_size + "");
        return InfoDAO.getBroInfoList(map);
    }

    /**
     * 根据条件得到信息总数（前台使用）
     *
     * @param Map *
     * @return String
     */
    public static String getBroInfoCount(Map<String, String> map) {
        return InfoDAO.getBroInfoCount(map);
    }

    /**
     * 得到公开指南或报报的汇总信息
     *
     * @param Map
     * @return List
     */
    public static List<InfoBean> getGKZNInfoList(Map<String, String> map) {
        return InfoDAO.getGKZNInfoList(map);
    }


    /**
     * 得到公开指南或报报的汇总总数
     *
     * @param Map *
     * @return String
     */
    public static String getGKZNInfoCount(Map<String, String> map) {
        return InfoDAO.getGKZNInfoCount(map);
    }

    /**
     * 批量发布静态内容页,根据栏目,站点和发布时间得到信息
     *
     * @param map
     * @return boolean
     */
    public static boolean batchPublishContentHtml(Map<String, String> map) {
        List<InfoBean> l = InfoDAO.batchPublishContentHtml(map);

        if (l != null && l.size() > 0) {
            try {
                return InfoPublishManager.createContentHTML(l);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        } else
            return true;
    }

    /**
     * 查询列表
     *
     * @param map
     * @return List<InfoBean>
     */
    public static List<InfoBean> getInfoList(Map<String, String> map) {
        getSearchSQL(map);
        List<InfoBean> infoBeanList = InfoDAO.getInfoBeanList(map);
        List<InfoBean> result = new ArrayList<InfoBean>();
        if (infoBeanList != null && infoBeanList.size() > 0) {
            Map<String, UserBean> userMap = UserManager.getUserMap();
            for (InfoBean bean : infoBeanList) {
                if (bean.getInput_user() != 0) {
                    if (userMap.get(bean.getInput_user() + "") != null) {
                        bean.setInput_user_name(userMap.get(bean.getInput_user() + "").getUser_realname());
                    }
                }
                if (bean.getModify_user() != 0) {
                    if (userMap.get(bean.getModify_user() + "") != null) {
                        bean.setModify_user_name(userMap.get(bean.getModify_user() + "").getUser_realname());
                    }
                }
                result.add(bean);
            }
        }
        return result;
    }

    public static String getTimeStr(String searchTime) {
        String time = "";
        if (searchTime.trim().equals("searchType_1b")) {
            time = time(0, "yyyy-MM-dd") + " 00:00:00";
        } else if (searchTime.trim().equals("searchType_2b")) {
            time = time(1L, "yyyy-MM-dd") + " 00:00:00";
        } else if (searchTime.trim().equals("searchType_3b")) {
            time = time(7L, null);
        } else if (searchTime.trim().equals("searchType_4b")) {
            time = time(30L, null);
        }
        return time;
    }

    /**
     * 根据条件得到所有录入人信息
     *
     * @param Map
     * @param stl
     * @return List<UserRegisterBean>
     */
    public static List<UserRegisterBean> getAllInuptUserID(Map<String, String> m) {
        return InfoDAO.getAllInuptUserID(m);
    }

    /**
     * 计算距离当前时间n天的时间，以天为单位，
     *
     * @param n
     * @param pattern
     * @return
     */
    public static String time(long n, String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd hh:mm:ss";
        }
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date date2 = new Date(date.getTime() - 24 * 60 * 60 * 1000 * n);
        return DateUtil.getString(date2, pattern);
    }

    /**
     * @param map
     * @return
     */
    public static void getSearchSQL(Map<String, String> map) {
        String user_id = map.get("user_id") + "";
        String category_ids = map.get("search_steps");
        String app_id = map.get("app_id");
        String site_id = map.get("site_id");
        String searchTime = map.get("searchString");
        String time = "";
        String modelStr = map.get("modelString") == null ? "" : (map.get("modelString") + " and ");
        if (searchTime != null && searchTime.trim().startsWith("searchType_")) {
            map.remove("searchString");
            time = getTimeStr(searchTime);
            map.put("searchString", modelStr + " ci.released_dtime > '" + time + "' ");
        } else {
            if (modelStr != null && !modelStr.trim().equals("")) {
                map.put("searchString", modelStr.replaceAll(" and ", ""));
            }
        }
        String cids = "";
        if (map.containsKey("cat_ids")) {
            cids = map.get("cat_ids");
            String resultIds = "";
            String ids = com.deya.wcm.services.cms.category.CategoryManager.getAllChildCategoryIDS(cids, map.get("site_id"));
            //String ids = CategoryReleManager.getCategoryIDSByUser(0,Integer.parseInt(user_id),map.get("site_id"));
            if (ids != null && ids.length() > 0) {
                String[] idArr = ids.split(",");
                for (String temp : idArr) {
                    if (RoleUserManager.isSiteManager(user_id + "", app_id, site_id) || CategoryReleManager.isCategoryManagerByUser(Integer.parseInt(user_id), map.get("site_id"), Integer.parseInt(temp))) {
                        resultIds = resultIds + temp + ",";
                    }
                }
                if (!"".equals(resultIds)) {
                    resultIds = resultIds.substring(0, resultIds.length() - 1);
                }
                map.put("cat_ids", resultIds);
                map.remove("cat_id");
            } else {
                if(cids.indexOf(",") >= 0){
                    map.put("cat_ids", cids);
                    map.remove("cat_id");
                }else{
                    map.remove("cat_ids");
                    map.put("cat_id", cids);
                }
            }
        }
        //不是取录入员自己待审中的信息走下面
        if (!map.containsKey("input_user")) {
            String sql = "";
            if (category_ids == null || category_ids.trim().equals("")) {
                if (map.get("step_id") != null && !map.get("step_id").trim().equals("")) {
                    sql = " and ci.step_id=" + map.get("step_id");
                }
            } else {
                if (category_ids.trim().startsWith(",")) {
                    category_ids = category_ids.trim().substring(1);
                }
                if (category_ids.trim().endsWith(",")) {
                    category_ids = category_ids.trim().substring(0, category_ids.length() - 1);
                }
                sql = CategoryManager.getSearchSQLByCategoryIDS(user_id, category_ids, app_id, site_id);
                map.remove("cat_id");
            }
            map.put("searchString2", sql);
        }
    }

    /**
     * 得到信息条数
     *
     * @param cat_id
     * @param site_id
     * @return int
     */
    public static int getInfoCount(Map<String, String> map) {
        getSearchSQL(map);
        return InfoDAO.getInfoCount(map);
    }

    /**
     * 回归
     *
     * @param List<InfoBean> l
     * @param stl
     * @return boolean
     */
    public static boolean goBackInfo(List<InfoBean> l, SettingLogsBean stl) {
        String info_ids = "";
        for (InfoBean ib : l) {
            info_ids += "," + ib.getInfo_id();
            if (ib.getInfo_status() == 8)
                InfoPublishManager.publishAfterEvent(ib);
        }
        info_ids = info_ids.substring(1);
        return InfoDAO.updateInfoStatus(info_ids, "0", stl);
    }

    /**
     * 归档
     *
     * @param infoIds
     * @param stl
     * @return boolean
     */
    public static boolean backInfo(String infoIds, SettingLogsBean stl) {
        return InfoDAO.updateInfoStatus(infoIds, "1", stl);
    }

    /**
     * 修改状态(发布或撤消)
     *
     * @param infoIds
     * @param status
     * @param stl
     * @return boolean
     */
    public static boolean updateInfoStatus(List<InfoBean> l, String status, SettingLogsBean stl) {
        String info_ids = "";
        String site_id = l.get(0).getSite_id();
        Set<Integer> cat_ids = new HashSet<Integer>();//删除的信息有可能是多个栏目下的，所以这里用set来排重
        List<InfoBean> publish_info_list = new ArrayList<InfoBean>();
        try {
            for (InfoBean ib : l) {
                info_ids += "," + ib.getInfo_id();
                cat_ids.add(ib.getCat_id());
                if ("8".equals(status)) {//发布，生成页面
                    ib.setInfo_status(8);
                    ib.setModify_user(stl.getUser_id());
                    updateInfo(ib, stl);
                    if (ib.getReleased_dtime() == null || "".equals(ib.getReleased_dtime()))
                        ib.setReleased_dtime(DateUtil.getCurrentDateTime());
                    publish_info_list.add(ib);
                }
                if ("3".equals(status)) {//撤消
                    ib.setInfo_status(3);
                    //ib.setReleased_dtime("");//清空发布时间

                    //一下代码是撤销已发布信息的时候，同时撤销该信息的引用信息
                    List<InfoBean> quoteInfoList = getQuoteInfoList(ib.getInfo_id() + "");
                    if (quoteInfoList != null && quoteInfoList.size() > 0) {
                        for (InfoBean ib2 : quoteInfoList) {
                            ib2.setInfo_status(3);
                            publish_info_list.add(ib2);
                        }
                    }

                    publish_info_list.add(ib);
                }
                InfoDAO.updateInfoStatus2(ib, status, stl);
            }
            if ("8".equals(status))
                InfoPublishManager.publishAfterEvent(publish_info_list, cat_ids, site_id);//发布需要生成页面
            if ("3".equals(status)) {
                InfoPublishManager.cancelAfterEvent(publish_info_list, cat_ids, site_id);//撤消需要删除页面和索引
                InfoPublishManager.createRelaContentHTML(l);
            }
            //InfoPublishManager.resetCategoryPage(cat_ids, site_id);//更新栏目
            info_ids = info_ids.substring(1);
            if (stl != null)
                PublicTableDAO.insertSettingLogs("修改", "主信息信息状态更改为" + status + "", info_ids, stl);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 转移信息
     *
     * @param List<InfoBean> l
     * @param int            to_cat_id 要转移的目标栏目ID
     * @param String         site_id
     * @return boolean
     */
    public static boolean MoveInfo(List<InfoBean> l, int to_cat_id, String app_id, String site_id, SettingLogsBean stl) {
        try {
            String info_ids = "";
            int wf_id = CategoryManager.getWFIDByCatID(to_cat_id, site_id);
            int info_status = l.get(0).getInfo_status();
            String cat_save_path = CategoryUtil.getFoldePathByCategoryID(to_cat_id, app_id, site_id);
            Set<Integer> cat_ids = new HashSet<Integer>();//如果是发布状态，转移需要刷新原栏目
            for (InfoBean ib : l) {
                if (info_status == 8) {//如果是发布状态，需要删除原有页面
                    InfoPublishManager.cancelAfterEvent(ib);
                    cat_ids.add(ib.getCat_id());
                }
                //判断信息是否在审核状态中，如果是，步骤ID重置为0
                if (info_status == 2) {
                    ib.setStep_id(0);
                }
                //判断内容模型是否是链接类型，如果不是，修改内容页路径到新地址
                if (ib.getModel_id() != ModelManager.getModelBeanByEName(LINK_MODEL_ENAME).getModel_id()) {
                    ib.setContent_url(cat_save_path + ib.getInfo_id() + ".htm");
                }
                ib.setCat_id(to_cat_id);
                ib.setWf_id(wf_id);
                InfoDAO.MoveInfo(ib);
                info_ids += "," + ib.getInfo_id();

                if ("zwgk".equals(app_id)) {
                    //如果是政务公开的东东，需要修改索引码
                    //得到年份
                    String gk_year = GKInfoManager.getGKYearStr(ib.getInfo_id() + "");
                    Map<String, String> m = IndexManager.getIndex(site_id, to_cat_id + "", gk_year + "-01", "");
                    if (m != null) {
                        GKInfoDAO.updateGKIndex(ib.getInfo_id() + "", m.get("gk_index"), m.get("gk_num"));
                    }
                }
            }
            info_ids = info_ids.substring(1);
            PublicTableDAO.insertSettingLogs("转移", "信息", info_ids, stl);
            //如果是发布状态，重新生成页面
            if (info_status == 8) {
                cat_ids.add(to_cat_id);//发布信息转移过去还是发布状态，也要更新那个栏目
                //InfoPublishManager.resetCategoryPage(cat_ids, site_id);//更新栏目
                InfoPublishManager.publishAfterEvent(l, cat_ids, site_id);
            }
            InfoPublishManager.createRelaContentHTML(l);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 审核信息通过
     *
     * @param List<InfoBean> info_list
     * @param stl
     * @return boolean
     */
    public static boolean passInfoStatus(List<InfoBean> info_list, String auto_desc, String user_id, SettingLogsBean stl) {
        try {
            String site_id = info_list.get(0).getSite_id();
            String ids = "";
            Set<Integer> cat_ids = new HashSet<Integer>();//删除的信息有可能是多个栏目下的，所以这里用set来排重
            List<InfoBean> publish_info_list = new ArrayList<InfoBean>();
            for (InfoBean info : info_list) {
                info.setModify_user(stl.getUser_id());
                updateInfo(info, stl);
                CategoryBean cb = CategoryManager.getCategoryBeanCatID(info.getCat_id(), info.getSite_id());
                int wf_id = CategoryManager.getCategoryBean(info.getCat_id()).getWf_id();
                int stepId = WorkFlowManager.getMaxStepIDByUserID(wf_id, user_id, info.getApp_id(), info.getSite_id());
                String info_status = "2";

                if (wf_id == 0 || stepId == 100) {//步骤ID为100，直接审核通过，改为待发状态
                    info_status = "4";
                    if (cb.getIs_wf_publish() == 1) {//如果栏目允许审核直接发布，那就改为发布状态,并生成页面
                        info_status = "8";
                        info.setInfo_status(8);
                        //发布时判断，原发布时间为空的话，写入发布时间
                        if (info.getReleased_dtime() == null || "".equals(info.getReleased_dtime()))
                            info.setReleased_dtime(DateUtil.getCurrentDateTime());
                        publish_info_list.add(info);
                        cat_ids.add(info.getCat_id());
                    }
                }
                InfoWorkStep infoWorkStep = new InfoWorkStep();
                int id = PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_WORK_STEP);
                infoWorkStep.setId(id);
                infoWorkStep.setInfo_id(info.getInfo_id());
                infoWorkStep.setStep_id(stepId);
                infoWorkStep.setUser_id(stl.getUser_id());
                infoWorkStep.setUser_name(stl.getUser_name());
                infoWorkStep.setDescription(auto_desc);
                infoWorkStep.setPass_status(1);
                infoWorkStep.setWork_time(DateUtil.getCurrentDateTime());
                InfoDAO.insertInfoWorkStep(infoWorkStep);
                InfoDAO.passInfoStatus(info.getInfo_id() + "", info_status, stepId + "", info.getReleased_dtime());
            }
            InfoPublishManager.publishAfterEvent(publish_info_list, cat_ids, site_id);
            //InfoPublishManager.resetCategoryPage(cat_ids, site_id);//更新栏目
            PublicTableDAO.insertSettingLogs("审核", "信息状态为通过", ids, stl);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 审核信息不通过
     *
     * @param String info_ids
     * @param String auto_desc 退回意见
     * @param stl
     * @return boolean
     */
    public static boolean noPassInfoStatus(String info_ids, String step_id, String auto_desc, SettingLogsBean stl) {
        InfoWorkStep infoWorkStep = new InfoWorkStep();
        int id = PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_WORK_STEP);
        infoWorkStep.setId(id);
        infoWorkStep.setInfo_id(Integer.parseInt(info_ids));
        infoWorkStep.setStep_id(Integer.parseInt(step_id));
        infoWorkStep.setUser_id(stl.getUser_id());
        infoWorkStep.setUser_name(stl.getUser_name());
        infoWorkStep.setDescription(auto_desc);
        infoWorkStep.setPass_status(0);
        infoWorkStep.setWork_time(DateUtil.getCurrentDateTime());
        InfoDAO.insertInfoWorkStep(infoWorkStep);
        return InfoDAO.noPassInfoStatus(info_ids, auto_desc, stl);
    }

    /**
     * 审核中信息撤回
     *
     * @param String info_ids
     * @param String auto_desc 退回意见
     * @param stl
     * @return boolean
     */
    public static boolean backPassInfoStatus(String info_id, String status, SettingLogsBean stl) {
        String info_status = "2";
        String step_id = "0";
        if (status != null && status.length() > 0) {
            if (Integer.parseInt(status) == 0) {
                info_status = "0";
            } else {
                InfoBean infoById = getInfoById(info_id);
                if (infoById.getStep_id() >= 1) {
                    step_id = (infoById.getStep_id() - 1) + "";
                }
            }
        }
        return InfoDAO.backPassInfoStatus(info_id, info_status, step_id, stl);
    }

    /**
     * add BaseInfomation Operate
     *
     * @param info
     * @param stl
     * @return boolean
     */
    public static boolean addInfo(Object ob, SettingLogsBean stl) {
        InfoBean info = null;
        //如果ob是java.util.HashMap的则说明 是自定义表的数据  不处理
        if (ob instanceof java.util.HashMap) {
            try {
                info = (InfoBean) BeanToMapUtil.convertMap(InfoBean.class, (HashMap) ob);
            } catch (Exception e) {
                //System.out.println("信息类型转换错误！！！！！！！");
                e.printStackTrace();
            }
        } else {
            info = (InfoBean) ob;
        }
        if (info.getId() == 0) {
            int id = PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_TABLE_NAME);
            info.setId(id);
            info.setInfo_id(id);
        }
        info.setInput_dtime(DateUtil.getCurrentDateTime());
        changeInfoStatus(info);

        return InfoDAO.addInfo(info, stl);
    }

    /**
     * update BaseInfomation Operate
     *
     * @param info
     * @param stl
     * @return boolean
     */
    public static boolean updateInfo(Object ob, SettingLogsBean stl) {
        InfoBean info = (InfoBean) ob;
        info.setModify_dtime(DateUtil.getCurrentDateTime());
        info.setOpt_dtime(DateUtil.getCurrentDateTime());
        changeInfoStatus(info);
        return InfoDAO.updateInfo(info, stl);
    }

    /**
     * 纯修改信息（用于修改引用信息的主表内容）
     *
     * @param info
     * @param stl
     * @return boolean
     */
    public static boolean updateInfoEvent(InfoBean info, SettingLogsBean stl) {
        if (updateInfo(info, stl)) {
            if (info.getInfo_status() == 8) {//发布后要处理的事情
                InfoPublishManager.publishAfterEvent(info);
            }
            return true;
        } else
            return false;
    }

    /**
     * 信息添加时设置信息状态
     *
     * @param InfoBean info
     * @return
     */
    public static void changeInfoStatus(InfoBean info) {
        if (info.getInfo_status() == 4) {//终审通过(待发)状态也要更新步骤ID
            info.setStep_id(100);
            //判断栏目是否允许审核通过后直接发布

            CategoryBean cb = CategoryManager.getCategoryBeanCatID(info.getCat_id(), info.getSite_id());

            if (cb.getIs_wf_publish() == 1) {//可以直接发布，状态设置为8
                info.setInfo_status(8);
                if (info.getReleased_dtime() == null || "".equals(info.getReleased_dtime()))
                    info.setReleased_dtime(DateUtil.getCurrentDateTime());
            }
        }
        if (info.getInfo_status() == 8) {
            if (info.getReleased_dtime() == null || "".equals(info.getReleased_dtime()))
                info.setReleased_dtime(DateUtil.getCurrentDateTime());
            info.setStep_id(100);
        }
        if (info.getContent_url() == null || "".equals(info.getContent_url()))
            info.setContent_url(CategoryUtil.getFoldePathByCategoryID(info.getCat_id(), info.getApp_id(), info.getSite_id()) + info.getInfo_id() + ".htm");
    }

    /**
     * 根据信息ID得到被引用列表
     *
     * @param String info_id
     * @return List
     */
    public static List<InfoBean> getQuoteInfoList(String info_id) {
        return InfoDAO.getQuoteInfoList(info_id);
    }

    /**
     * 同时发布到其它栏目，添加时用
     *
     * @param int             Object ob
     * @param String          to_cat_ids 要报送到的目标栏目
     * @param SettingLogsBean stl
     * @return boolean
     */
    public static boolean insertInfoToOtherCat(Object o, String to_cat_ids, SettingLogsBean stl) {
        try {
            InfoBean from_bean = (InfoBean) o;
            InfoBean new_bean = from_bean;

            String[] tempA = to_cat_ids.split(",");
            for (int i = 0; i < tempA.length; i++) {
                new_bean.setId(getNextInfoId());
                new_bean.setInfo_id(new_bean.getId());
                new_bean.setCat_id(Integer.parseInt(tempA[i]));
                new_bean.setFrom_id(from_bean.getFrom_id());
                new_bean.setIs_host(2);
                //得到目标栏目所使用的流程ID
                new_bean.setWf_id(CategoryManager.getWFIDByCatID(new_bean.getCat_id(), new_bean.getSite_id()));
                if (new_bean.getWf_id() == 0) {//没有审核流程，信息状态设为待发布
                    new_bean.setInfo_status(4);
                    new_bean.setStep_id(100);
                } else {//否则改为待审状态
                    new_bean.setInfo_status(2);
                    new_bean.setStep_id(0);
                }
                //内容模型使用链接模型
                new_bean.setModel_id(ModelManager.getModelBeanByEName(LINK_MODEL_ENAME).getModel_id());
                InfoDAO.addInfo(new_bean, stl);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除
     *
     * @param List<InfoBean> l
     * @param stl
     * @return boolean
     */
    public static boolean deleteInfo(List<InfoBean> l, SettingLogsBean stl) {
        //进行删除时需要删除静态页面，并更新栏目
        String info_ids = "";
        String site_id = l.get(0).getSite_id();
        Set<Integer> cat_id = new HashSet<Integer>();//删除的信息有可能是多个栏目下的，所以这里用set来排重
        for (InfoBean ib : l) {
            info_ids += "," + ib.getInfo_id();
            //发布状态下要删除页面,必须不是链接类型的
            if (ib.getInfo_status() == 8 && !"LINK_MODEL_ENAME".equals(ModelManager.getModelEName(ib.getModel_id()))) {
                //删除页面 这里要根据信息ID和栏目ID来拼出页面，不能直接使用content_url中的地址，避免删除链接类型中的页面，这里没办法知道它是什么样的内容模型
                InfoPublishManager.cancelAfterEvent(ib);

                cat_id.add(ib.getCat_id());
            }
        }
        info_ids = info_ids.substring(1);
        if (InfoDAO.updateInfoStatus(info_ids, "-1", stl)) {
            //在此删除信息的时候，需要删除所有被它引用的信息
            List<InfoBean> from_list = InfoDAO.getFromInfoListByIDS(info_ids);
            if (from_list != null && from_list.size() > 0) {
                for (InfoBean ib : l) {
                    cat_id.add(ib.getCat_id());
                    //发布状态下要删除页面,必须不是链接类型的
                    if (ib.getInfo_status() == 8 && !"LINK_MODEL_ENAME".equals(ModelManager.getModelEName(ib.getModel_id()))) {
                        //删除页面 这里要根据信息ID和栏目ID来拼出页面，不能直接使用content_url中的地址，避免删除链接类型中的页面，这里没办法知道它是什么样的内容模型
                        InfoPublishManager.cancelAfterEvent(ib);

                        cat_id.add(ib.getCat_id());
                    }
                }
                realDeleteInfo(from_list, stl);
            }
        }
        //这里还需要更新栏目静态页面
        InfoPublishManager.resetCategoryPage(cat_id, site_id);

        //这里还要删除统计信息表里面的数据
        Map m = new HashMap();
        m.put("info_id", info_ids);
        AccessCountManager.deleteAccessCountInfos(m);

        //InfoPublishManager.createRelaContentHTML(l);

        return true;
    }

    /**
     * 彻底删除信息
     *
     * @param String delete_ids
     * @param stl
     * @return
     */
    public static boolean realDeleteInfo(List<InfoBean> l, SettingLogsBean stl) {
        String info_ids = "";
        //辅助表map,用于删除副表的内容，这里是不同副表的集合
        try {
            if (l == null || l.size() == 0)
                return true;

            Map<Integer, String> m = new HashMap<Integer, String>();
            for (InfoBean ib : l) {
                info_ids += "," + ib.getInfo_id();
                //判断这个内容模型是否已经有了，没有新增，有的累加info_id
                if (m.containsKey(ib.getModel_id())) {
                    m.put(ib.getModel_id(), m.get(ib.getModel_id() + "") + "," + ib.getInfo_id());
                } else {
                    m.put(ib.getModel_id(), ib.getInfo_id() + "");
                }
            }
            info_ids = info_ids.substring(1);
            Map<String, String> info_map = new HashMap<String, String>();
            info_map.put("info_ids", info_ids);
            InfoDAO.deleteInfo(info_map, stl);//删除主表
            //得到副表的信息，删除副表
            Map<String, String> model_map = new HashMap<String, String>();
            Set<Integer> s = m.keySet();
            for (int i : s) {
                com.deya.wcm.bean.system.formodel.ModelBean mb = ModelManager.getModelBean(i);
                if (mb.getTable_name() != null && !"".equals(mb.getTable_name()) && !"infoLink".equals(mb.getTable_name())) {
                    model_map.put("table_name", mb.getTable_name());
                    model_map.put("info_ids", m.get(i));
                    InfoDAO.deleteInfoModel(model_map);
                }
				/*
				if(mb.getTable_name().contains("gk"))
				{//删除公开公用表
					model_map.put("table_name", "cs_gk_info");
					model_map.put("info_ids", info_ids);
					GKInfoDAO.deleteGKInfo(info_ids);
				}
				*/
                //删除公开公用表
                model_map.put("table_name", "cs_gk_info");
                model_map.put("info_ids", info_ids);
                GKInfoDAO.deleteGKInfo(info_ids);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过cat_id,site_id清空回收站
     *
     * @param String cat_ids
     * @param String site_id
     * @param stl
     * @return
     */
    public static boolean clearTrach(String cat_ids, String app_id, String site_id, SettingLogsBean stl) {
        return InfoDAO.clearTrach(cat_ids, app_id, site_id, stl);
    }


    /**
     * 得到内容页面通过模板生成的信息，用于后台查看内容页
     *
     * @param String info_id
     *               String site_id
     * @return String
     */
    public static String getInfoTemplateContent(String info_id, String site_id) {
        String content = "";
        InfoBean ib = getInfoById(info_id, site_id);
        if (ib != null) {
            String temp_site_id = "";
            if (!ib.getApp_id().equals("cms")) {
                //如果不是内容管理的应用，需要取得该应用同内容管理关联的站点
                temp_site_id = SiteAppRele.getSiteIDByAppID(ib.getApp_id());
            } else
                temp_site_id = ib.getSite_id();
            String model_ename = ModelManager.getModelEName(ib.getModel_id());
            int cat_id = ib.getCat_id();
            int model_id = ib.getModel_id();
            String template_id = CategoryModelManager.getTemplateID(cat_id + "", ib.getSite_id(), model_id);
            VelocityInfoContextImp vici = new VelocityInfoContextImp(ib.getInfo_id() + "", temp_site_id, template_id, model_ename);
            content = vici.parseTemplate();
        }
        return content;
    }

    /**
     * 推送信息操作 (目录增加链接和引用的信息也能被再次推送，操作方法只是把信息再添加一份，实体不变，只是ID，site_id,app_id,cat_id变下)
     *
     * @param List<InfoBean> l 要得到的信息ID
     * @param String         s_site_id
     * @param String         s_app_id
     * @param String         cat_ids
     * @param int            get_type 获取方式 0 克隆  1引用  2 链接
     * @param String         is_publish
     * @throws boolean
     */
    public static boolean infoTo(List<InfoBean> l, String s_site_id, String s_app_id, String cat_ids, int get_type, boolean is_publish) {
        if (l != null && l.size() > 0) {
            String[] tempA = cat_ids.split(",");
            for (InfoBean info : l) {
                try {
                    String model_ename = ModelManager.getModelEName(info.getModel_id());
                    Object o = ModelUtil.select(info.getInfo_id() + "", info.getSite_id(), model_ename);
                    BeanUtils.setProperty(o, "site_id", s_site_id);
                    BeanUtils.setProperty(o, "app_id", s_app_id);
                    //info.getIs_host() == 0  推送方式只对主信息有用
                    if (get_type == 2 && info.getIs_host() == 0) {//链接的话
                        BeanUtils.setProperty(o, "model_id", ModelManager.getModelBeanByEName(LINK_MODEL_ENAME).getModel_id());
                        BeanUtils.setProperty(o, "from_id", info.getInfo_id());
                        BeanUtils.setProperty(o, "is_host", get_type);
                        String content_url = BeanUtils.getProperty(o, "content_url");
                        if (!s_site_id.equals(info.getSite_id())) {
                            content_url = "http://" + SiteDomainManager.getDefaultSiteDomainBySiteID(info.getSite_id()) + content_url;
                        }
                        BeanUtils.setProperty(o, "content_url", content_url);
                    }

                    for (int i = 0; i < tempA.length; i++) {
                        int id = getNextInfoId();
                        BeanUtils.setProperty(o, "id", id);
                        BeanUtils.setProperty(o, "info_id", id);
                        int cat_id = Integer.parseInt(tempA[i]);

                        CategoryBean cb = CategoryManager.getCategoryBeanCatID(cat_id, s_site_id);
                        //判断目标栏目是否有审核流程，如果有，进审核流程，没有审核流程，进待发

                        BeanUtils.setProperty(o, "cat_id", cat_id);
                        if (is_publish) {
                            BeanUtils.setProperty(o, "info_status", 8);
                            BeanUtils.setProperty(o, "step_id", 100);
                        } else if (cb.getWf_id() != 0) {
                            BeanUtils.setProperty(o, "info_status", 2);
                            BeanUtils.setProperty(o, "step_id", 0);
                            BeanUtils.setProperty(o, "wf_id", cb.getWf_id());
                        } else {
                            BeanUtils.setProperty(o, "info_status", 4);
                            BeanUtils.setProperty(o, "step_id", 100);
                        }

                        if (get_type == 0 && info.getIs_host() == 0) {//推送方式：克隆只要重新加下url　　只对主信息有效
                            BeanUtils.setProperty(o, "content_url", CategoryUtil.getFoldePathByCategoryID(cat_id, s_app_id, s_site_id) + id + ".htm");
                        }
                        if (get_type == 1 && info.getIs_host() == 0) {//推送方式：引用的话
                            BeanUtils.setProperty(o, "from_id", info.getInfo_id());
                            BeanUtils.setProperty(o, "is_host", get_type);
                            BeanUtils.setProperty(o, "content_url", CategoryUtil.getFoldePathByCategoryID(cat_id, s_app_id, s_site_id) + id + ".htm");
                        }
                        if (info.getIs_host() == 1) {
                            //如果被推送的信息是引用信息，这里需要所URL重新生成一次
                            BeanUtils.setProperty(o, "content_url", CategoryUtil.getFoldePathByCategoryID(cat_id, s_app_id, s_site_id) + id + ".htm");
                        }
                        if (info.getIs_host() == 2) {
                            //如果获取的信息是链接的信息，首先判断是否在同一站点中，不同的站点需加域名
                            if (!s_site_id.equals(info.getSite_id())) {
                                //再判断url中是否以http开头，如果是，不加域名
                                String sourch_content_url = BeanUtils.getProperty(o, "content_url");
                                if (!sourch_content_url.startsWith("http://")) {
                                    String temp_site_id = info.getSite_id();
                                    if (temp_site_id.startsWith("GK")) {
                                        temp_site_id = SiteAppRele.getSiteIDByAppID(info.getApp_id());
                                    }
                                    sourch_content_url = "http://" + SiteDomainManager.getDefaultSiteDomainBySiteID(temp_site_id) + sourch_content_url;
                                    BeanUtils.setProperty(o, "content_url", sourch_content_url);
                                }
                            }

                        }
                        ModelUtil.insert(o, model_ename, null);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();

                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else
            return true;
    }

    /**
     * &#x6839;&#x636e;&#x5e94;&#x7528;ID&#x5f97;&#x5230;&#x5b83;&#x6240;&#x5173;&#x8054;&#x7684;&#x7ad9;&#x70b9;ID&#xff0c;&#x5982;&#x5e94;&#x7528;ID&#x4e0d;&#x662f;&#x5185;&#x5bb9;&#x7ba1;&#x7406;&#xff0c;&#x90a3;&#x4e48;&#x5c06;&#x5b83;&#x7684;&#x7ad9;&#x70b9;ID&#x8f6c;&#x6362;&#x6210;&#x5e94;&#x7528;&#x6240;&#x5173;&#x8054;&#x7684;&#x7ad9;&#x70b9;ID
     *
     * @param String site_id
     * @param String app_id
     * @throws String
     */
    public static String getInfoReleSiteID(String site_id, String app_id) {
        String n_site_id = "";
        if (!app_id.equals("cms")) {
            //如果不是内容管理的应用，需要取得该应用同内容管理关联的站点
            n_site_id = SiteAppRele.getSiteIDByAppID(app_id);
        } else
            n_site_id = site_id;

        return n_site_id;
    }

    /**
     * 获取信息操作
     *
     * @param List<InfoBean> l 要得到的信息ID
     * @param String         s_site_id
     * @param String         s_app_id
     * @param int            cat_id
     * @param int            get_type 获取方式 0 克隆  1引用  2 链接
     * @param String         is_publish
     * @param String         user_id
     * @throws boolean
     */
    public static boolean infoGet(List<InfoBean> l, String s_site_id, String s_app_id, int cat_id, int get_type, boolean is_publish, int user_id) {
        if (l != null && l.size() > 0) {
            CategoryBean cb = CategoryManager.getCategoryBeanCatID(cat_id, s_site_id);
            int step = 0;
            if (cb.getWf_id() != 0) {
                step = WorkFlowManager.getMaxStepIDByUserID(cb.getWf_id(), user_id + "", s_app_id, s_site_id);
            }
            for (InfoBean info : l) {
                try {
                    int id = getNextInfoId();

                    String model_ename = ModelManager.getModelEName(info.getModel_id());
                    Object o = ModelUtil.select(info.getInfo_id() + "", info.getSite_id(), model_ename);
                    //从政务公开获取数据时，需要处理下附件里的info_id
					/*
					if("zwgk".equals(BeanUtils.getProperty(o, "app_id")))
					{
						GKInfoBean gkinfo = (GKInfoBean)o;
						List<GKResFileBean> file_list = gkinfo.getFile_list();
						if(file_list != null && file_list.size() > 0)
						{
							for(int i=0;i<file_list.size();i++)
								BeanUtils.setProperty(o, "file_list["+i+"].info_id", id);
						}
					}
					*/
                    if (model_ename.contains("gk")) {
                        GKInfoBean gkinfo = (GKInfoBean) o;
                        List<GKResFileBean> file_list = gkinfo.getFile_list();
                        if (file_list != null && file_list.size() > 0) {
                            for (int i = 0; i < file_list.size(); i++)
                                BeanUtils.setProperty(o, "file_list[" + i + "].info_id", id);
                        }
                    }

                    if (user_id != 0)
                        BeanUtils.setProperty(o, "input_user", user_id);
                    BeanUtils.setProperty(o, "site_id", s_site_id);
                    BeanUtils.setProperty(o, "cat_id", cat_id);
                    BeanUtils.setProperty(o, "app_id", s_app_id);
                    BeanUtils.setProperty(o, "id", id);
                    BeanUtils.setProperty(o, "info_id", id);

                    if (is_publish == true) {
                        BeanUtils.setProperty(o, "info_status", 8);
                        BeanUtils.setProperty(o, "step_id", 100);
                    } else {
                        BeanUtils.setProperty(o, "cat_id", cat_id);
                        if (cb.getWf_id() != 0) {
                            BeanUtils.setProperty(o, "info_status", 2);
                            BeanUtils.setProperty(o, "step_id", step);
                            BeanUtils.setProperty(o, "wf_id", cb.getWf_id());
                        } else {
                            BeanUtils.setProperty(o, "info_status", 4);
                            BeanUtils.setProperty(o, "step_id", 100);
                        }
                    }
                    if (get_type == 0 && info.getIs_host() == 0) {//获取方式（只对主信息有效）：克隆只要重新加下url
                        BeanUtils.setProperty(o, "content_url", CategoryUtil.getFoldePathByCategoryID(cat_id, s_app_id, s_site_id) + id + ".htm");
                    }
                    if (get_type == 1 && info.getIs_host() == 0) {//获取方式（只对主信息有效）：引用的话
                        BeanUtils.setProperty(o, "from_id", info.getInfo_id());
                        BeanUtils.setProperty(o, "is_host", get_type);
                        BeanUtils.setProperty(o, "content_url", CategoryUtil.getFoldePathByCategoryID(cat_id, s_app_id, s_site_id) + id + ".htm");
                    }
                    if (get_type == 2 && info.getIs_host() == 0) {//获取方式（只对主信息有效）：链接的话
                        BeanUtils.setProperty(o, "model_id", ModelManager.getModelBeanByEName(LINK_MODEL_ENAME).getModel_id());
                        BeanUtils.setProperty(o, "from_id", info.getInfo_id());
                        BeanUtils.setProperty(o, "is_host", get_type);
                        String content_url = BeanUtils.getProperty(o, "content_url");

                        if (!s_site_id.equals(info.getSite_id())) {
                            String temp_site_id = info.getSite_id();
                            if (temp_site_id.startsWith("GK")) {
                                temp_site_id = SiteAppRele.getSiteIDByAppID(info.getApp_id());
                            }
                            content_url = "http://" + SiteDomainManager.getDefaultSiteDomainBySiteID(temp_site_id) + content_url;
                            //content_url = "${InfoUtilData.getSiteDomain("+info.getSite_id()+")}"+content_url;
                        }
                        BeanUtils.setProperty(o, "content_url", content_url);
                    }

                    if (info.getIs_host() == 1) {
                        //如果获取的信息是引用信息，这里需要所URL重新生成一次
                        BeanUtils.setProperty(o, "content_url", CategoryUtil.getFoldePathByCategoryID(cat_id, s_app_id, s_site_id) + id + ".htm");
                    }
                    if (info.getIs_host() == 2) {
                        //如果获取的信息是链接的信息，首先判断是否在同一站点中，不同的站点需加域名
                        if (!s_site_id.equals(info.getSite_id())) {
                            //再判断url中是否以http开头，如果是，不加域名
                            String sourch_content_url = BeanUtils.getProperty(o, "content_url");
                            if (!sourch_content_url.startsWith("http://")) {
                                String temp_site_id = info.getSite_id();
                                if (temp_site_id.startsWith("GK")) {
                                    temp_site_id = SiteAppRele.getSiteIDByAppID(info.getApp_id());
                                }
                                sourch_content_url = "http://" + SiteDomainManager.getDefaultSiteDomainBySiteID(temp_site_id) + sourch_content_url;
                                BeanUtils.setProperty(o, "content_url", sourch_content_url);
                            }
                        }

                    }
                    ModelUtil.insert(o, model_ename, null);

                    Thread.sleep(1000);
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return true;
        } else
            return false;
    }

    /**
     * 同步信息到指定目录
     *
     * @param InfoBean ib
     * @throws boolean
     */
    public static boolean syncInfoToSite(List<InfoBean> l) {
        try {
            if (l != null && l.size() > 0) {
                for (InfoBean ib : l) {
                    syncInfoToSite(ib);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 同步信息到指定目录
     *
     * @param InfoBean ib
     * @throws boolean
     */
    public static boolean syncInfoToSite(InfoBean info) {
        //不是主信息不能同步
		/*
		if(info.getIs_host() > 0)
			return true;*/
        InfoBean new_infoBean = info.clone();

        //同步操作
        List<SyncBean> li = SyncManager.getSyncCatListBySiteCatID(new_infoBean.getSite_id(), new_infoBean.getCat_id());

        if (li != null && li.size() > 0) {
            syncInfoToSiteHandl(li, info, 0);
        }

        //推送操作
        List<SyncBean> to_li = SyncManager.getToInfoCategoryList(new_infoBean.getSite_id(), new_infoBean.getCat_id());
        if (to_li != null && to_li.size() > 0) {
            syncInfoToSiteHandl(to_li, info, 1);
        }
        return true;
    }

    /**
     * 自动同步或推送操作
     *
     * @param List<SyncBean> l
     * @param InfoBean       info
     * @param int            orientation 同步类型　0同步　1推送
     * @return boolean
     */
    public static boolean syncInfoToSiteHandl(List<SyncBean> li, InfoBean info, int orientation) {
        try {
            String model_ename = ModelManager.getModelEName(info.getModel_id());
            Object o = ModelUtil.select(info.getInfo_id() + "", info.getSite_id(), model_ename);

            for (SyncBean sb : li) {
                String s_site_id = "";
                int s_cat_id = 0;
                if (orientation == 0) {
                    s_site_id = sb.getS_site_id();
                    s_cat_id = sb.getS_cat_id();
                }
                if (orientation == 1) {
                    s_site_id = sb.getT_site_id();
                    s_cat_id = sb.getT_cat_id();
                }
                /*加判断，最终逻辑是，一个栏目中一条信息只能被引用一次，如果该信息是引用的信息，用它的源ID进行判断*/
                int temp_old_info_id = info.getInfo_id();
                if (info.getFrom_id() != 0)
                    temp_old_info_id = info.getFrom_id();
                //没有被引用过的才走下面
                if ("0".equals(InfoDAO.getQuoteInfoCount(temp_old_info_id, s_cat_id, s_site_id))) {
                    int is_publish = sb.getIs_auto_publish();//是否直接发布
                    int type = sb.getCite_type();//同步方式，0为克隆，2为引用 1 为引用  这里默认为引用吧,就不判断了
                    int id = getNextInfoId();
                    String s_app_id = "cms";
                    if (s_site_id.startsWith("GK"))
                        s_app_id = "zwgk";
                    BeanUtils.setProperty(o, "site_id", s_site_id);
                    BeanUtils.setProperty(o, "cat_id", s_cat_id);
                    BeanUtils.setProperty(o, "app_id", s_app_id);
                    BeanUtils.setProperty(o, "id", id);
                    BeanUtils.setProperty(o, "info_id", id);
                    BeanUtils.setProperty(o, "wf_id", CategoryManager.getWFIDByCatID(s_cat_id, s_site_id));
                    //如果此信息是主信息
                    if (info.getIs_host() == 0) {
                        BeanUtils.setProperty(o, "from_id", info.getInfo_id());
                        BeanUtils.setProperty(o, "is_host", 1);
                        BeanUtils.setProperty(o, "content_url", CategoryUtil.getFoldePathByCategoryID(s_cat_id, s_app_id, s_site_id) + id + ".htm");
                    }
                    //如果此信息是引用信息
                    if (info.getIs_host() == 1) {
                        BeanUtils.setProperty(o, "content_url", CategoryUtil.getFoldePathByCategoryID(s_cat_id, s_app_id, s_site_id) + id + ".htm");
                    }
                    //如果此信息是引用信息
                    if (info.getIs_host() == 2) {
                        //如果获取的信息是链接的信息，首先判断是否在同一站点中，不同的站点需加域名
                        if (!s_site_id.equals(info.getSite_id())) {
                            //再判断url中是否以http开头，如果是，不加域名
                            String sourch_content_url = info.getContent_url();
                            if (!sourch_content_url.startsWith("http://")) {
                                String temp_site_id = info.getSite_id();
                                if (temp_site_id.startsWith("GK")) {
                                    temp_site_id = SiteAppRele.getSiteIDByAppID(info.getApp_id());
                                }
                                sourch_content_url = "http://" + SiteDomainManager.getDefaultSiteDomainBySiteID(temp_site_id) + sourch_content_url;
                                BeanUtils.setProperty(o, "content_url", sourch_content_url);
                            }
                        }
                    }

                    if (is_publish == 1) {
                        BeanUtils.setProperty(o, "info_status", 8);
                        BeanUtils.setProperty(o, "step_id", 100);
                    } else {
                        BeanUtils.setProperty(o, "info_status", 2);
                        BeanUtils.setProperty(o, "step_id", 0);
                    }
                    ModelUtil.insert(o, model_ename, null);
                }
            }
            return true;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 同步信息到指定目录
     * @param InfoBean ib
     * @throws boolean

    public static boolean syncInfoToSite(InfoBean info)
    {
    //不是主信息不能同步
    if(info.getIs_host() > 0)
    return true;
    InfoBean new_infoBean = info.clone();
    List<SyncBean> li = SyncManager.getSyncCatListBySiteCatID(new_infoBean.getSite_id(),new_infoBean.getCat_id());
    if(li != null && li.size() > 0)
    {
    for(SyncBean sb : li)
    {
    String s_site_id = sb.getS_site_id();
    int s_cat_id = sb.getS_cat_id();
    int is_publish = sb.getIs_auto_publish();//是否直接发布
    int type = sb.getCite_type();//同步方式，0为克隆，2为引用
    if(type == 2)
    {
    //以链接类型保存
    new_infoBean.setCat_id(s_cat_id);
    new_infoBean.setSite_id(s_site_id);

    new_infoBean.setId(getNextInfoId());
    new_infoBean.setInfo_id(new_infoBean.getId());
    new_infoBean.setFrom_id(info.getInfo_id());
    new_infoBean.setIs_host(2);
    new_infoBean.setWf_id(CategoryManager.getWFIDByCatID(s_cat_id,s_site_id));
    if(is_publish == 1)
    {
    new_infoBean.setInfo_status(8);
    new_infoBean.setStep_id(100);
    }
    else
    {
    new_infoBean.setInfo_status(4);
    new_infoBean.setStep_id(100);
    }

    new_infoBean.setModel_id(ModelManager.getModelBeanByEName(LINK_MODEL_ENAME).getModel_id());//模型ID为链接型的
    //判断获取的信息是否是同一站点的
    if(!info.getSite_id().equals((new_infoBean.getSite_id())))
    {
    //判断内容页地址是否以/（相对路径）开始，如果是，需要加上原站点ID的域名
    if(info.getContent_url().indexOf("/") == 0)
    {
    info.setContent_url(SiteRPC.getSiteDomain(info.getSite_id())+info.getContent_url());
    }
    }
    }
    ModelUtil.insert(new_infoBean, LINK_MODEL_ENAME, null);
    }
    return true;
    }else
    return false;
    }	*/

    /**
     * getNextInfoId
     *
     * @return int
     */
    public static int getNextInfoId() {
        return PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_TABLE_NAME);
    }

    public static int getReleInfoID() {
        return PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_INFO_TABLE_NAME);
    }

    //相关信息处理
    public static boolean addRelatedInfo(RelatedInfoBean rinfo, SettingLogsBean stl) {
        //int id = PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_INFO_TABLE_NAME);
        //rinfo.setInfo_id(id);
        return InfoDAO.addRelatedInfo(rinfo, stl);
    }

    public static boolean updateRelatedInfo(RelatedInfoBean rinfo, SettingLogsBean stl) {
        return InfoDAO.updateRelatedInfo(rinfo, stl);
    }

    public static boolean deleteRelatedInfo(Map<String, String> map, SettingLogsBean stl) {
        return InfoDAO.deleteRelatedInfo(map, stl);
    }

    public static List<RelatedInfoBean> getRelatedInfoList(Map<String, String> map) {
        return InfoDAO.getRelatedInfoList(map);
    }

    //得到相关链接，用于前台页面
    public static List<RelatedInfoBean> getBroRelatedInfoList(Map<String, String> map) {
        List<RelatedInfoBean> l = InfoDAO.getRelatedInfoList(map);
        if (l != null && l.size() > 0) {
            return l;
        } else {
            //没有手动添加相关链接，这里自动处理,根据关键字去获取
            InfoBean ib = getInfoById(map.get("info_id"));

            if (ib != null) {
                String info_count = JconfigUtilContainer.bashConfig().getProperty("count", "", "autoReleInfoCount");
                ;
                if (info_count == null || "".equals(info_count))
                    info_count = "10";
                map.put("site_id", ib.getSite_id());
                map.put("page_size", info_count);
                map.put("start_num", "0");
                GKInfoBean gk = GKInfoManager.getGKInfoBean(map.get("info_id"));
                if (gk != null && !"".equals(gk.getTopic_key())) {
                    map.put("keyword_con", getKeyWordConSQL(gk.getTopic_key(), "gk.topic_key"));
                    return GKInfoDAO.getReleGKInfoListForAuto(map);
                }
                /*
                if("cms".equals(ib.getApp_id()))
                {
                    if(!"".equals(ib.getTags().trim()))
                    {
                        map.put("keyword_con", getKeyWordConSQL(ib.getTags(),"tags"));
                        return getReleInfoListForAuto(map,Integer.parseInt(info_count));
                    }
                }else
                {
                    GKInfoBean gk = GKInfoManager.getGKInfoBean(map.get("info_id"));
                    if(gk != null && !"".equals(gk.getTopic_key()))
                    {
                        map.put("keyword_con", getKeyWordConSQL(gk.getTopic_key(),"gk.topic_key"));
                        return GKInfoDAO.getReleGKInfoListForAuto(map);
                    }
                }
                */
            }
            return null;
        }
    }

    //得到相关链接，用于前台页面
    public static List<RelatedInfoBean> getRelatedInfoListForDelete(Map<String, String> map) {
        List<RelatedInfoBean> l = InfoDAO.getRelatedInfoList(map);
        if (l != null && l.size() > 0) {
            return l;
        } else {
            //没有手动添加相关链接，这里自动处理,根据关键字去获取
            InfoBean ib = getInfoById(map.get("info_id"));

            if (ib != null) {
                map.put("site_id", ib.getSite_id());
                map.put("page_size", "30");
                map.put("start_num", "0");
                GKInfoBean gk = GKInfoManager.getGKInfoBean(map.get("info_id"));
                if (gk != null && !"".equals(gk.getTopic_key())) {
                    map.put("keyword_con", getKeyWordConSQL(gk.getTopic_key(), "gk.topic_key"));
                    return GKInfoDAO.getReleGKInfoListForAutoDelete(map);
                }
                /*
                if("cms".equals(ib.getApp_id()))
                {
                    if(!"".equals(ib.getTags().trim()))
                    {
                        System.out.println("********生成相关信息静态页4*******"+map);
                        map.put("keyword_con", getKeyWordConSQL(ib.getTags(),"tags"));
                        return getReleInfoListForAutoDelete(map,Integer.parseInt(info_count));
                    }
                }else
                {
                    GKInfoBean gk = GKInfoManager.getGKInfoBean(map.get("info_id"));
                    if(gk != null && !"".equals(gk.getTopic_key()))
                    {
                        map.put("keyword_con", getKeyWordConSQL(gk.getTopic_key(),"gk.topic_key"));
                        System.out.println("********生成相关信息静态页5*******"+map);
                        return GKInfoDAO.getReleGKInfoListForAutoDelete(map);
                    }
                }
                */
            }
            return null;
        }
    }

    //得到内容管理的相关链接，需要对标题进行排重,使用map对齐进行排重
    public static List<RelatedInfoBean> getReleInfoListForAutoDelete(Map<String, String> map, int info_count) {
        List<RelatedInfoBean> r_l = new ArrayList<RelatedInfoBean>();
        List<RelatedInfoBean> l = InfoDAO.getReleInfoListForAutoDelete(map);
        Map<String, RelatedInfoBean> riMap = new HashMap<String, RelatedInfoBean>();
        if (l != null && l.size() > 0) {
            for (RelatedInfoBean rib : l) {
                riMap.put(rib.getTitle(), rib);
                if (riMap.size() == info_count)
                    break;
            }

            Set<String> set = riMap.keySet();
            String ids = "";
            for (String t : set) {
                //r_l.add(riMap.get(t));
                ids += "," + riMap.get(t).getInfo_id();
            }//对获取到的相关信息按时间排序,国为使用了map进行排重，导致排序不正确
            if (!"".equals(ids)) {
                r_l = InfoDAO.orderByReleInfoList(ids.substring(1));
            }
        }
        return r_l;
    }


    //得到内容管理的相关链接，需要对标题进行排重,使用map对齐进行排重
    public static List<RelatedInfoBean> getReleInfoListForAuto(Map<String, String> map, int info_count) {
        List<RelatedInfoBean> r_l = new ArrayList<RelatedInfoBean>();
        List<RelatedInfoBean> l = InfoDAO.getReleInfoListForAuto(map);
        Map<String, RelatedInfoBean> riMap = new HashMap<String, RelatedInfoBean>();
        if (l != null && l.size() > 0) {
            for (RelatedInfoBean rib : l) {
                riMap.put(rib.getTitle(), rib);
                if (riMap.size() == info_count)
                    break;
            }

            Set<String> set = riMap.keySet();
            String ids = "";
            for (String t : set) {
                //r_l.add(riMap.get(t));
                ids += "," + riMap.get(t).getInfo_id();
            }//对获取到的相关信息按时间排序,国为使用了map进行排重，导致排序不正确
            if (!"".equals(ids)) {
                r_l = InfoDAO.orderByReleInfoList(ids.substring(1));
            }
        }
        return r_l;
    }

    public static String getKeyWordConSQL(String keyword, String item_name) {
        String str = "";
        String[] tempA = keyword.split(" ");
        if (tempA != null && tempA.length > 0) {
            for (int i = 0; i < tempA.length; i++) {
                str += "or " + item_name + " like '%" + tempA[i] + "%'";
            }
            if (str.length() > 0)
                str = str.substring(2);
        }
        return "(" + str + ")";
    }

    public static RelatedInfoBean getRelatedInfoBean(String id, String related_id) {
        return InfoDAO.getRelatedInfoBean(id, related_id);
    }

    /**
     * 添加内容的点击次数
     *
     * @param String info_id
     * @param String num 递增增加数
     * @return boolean
     */
    public static void addInfoHits(String info_id, String num, String lastHit_date) {
        Calendar cal = GregorianCalendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar currentCal = GregorianCalendar.getInstance();
        try {
            Date date = sdf.parse(lastHit_date);
            cal.setTime(date);
        } catch (ParseException e) {
            // 解析失败后,默认最后修改时间为当前时间
            cal.setTime(currentCal.getTime());
        }

        Map<String, String> m = new HashMap<String, String>();
        m.put("info_id", info_id);
        m.put("num", num); // 总点击增加数目

        String month_num = num; // 月点击设置
        if (currentCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) {
            month_num = "month_hits + " + num;
        }
        m.put("m_num", month_num);

        String week_num = num; //周点击设置
        if (currentCal.get(Calendar.WEEK_OF_YEAR) == cal.get(Calendar.WEEK_OF_YEAR)) {
            week_num = "week_hits + " + num;
        }
        m.put("w_num", week_num);

        String day_num = num; // 日点击设置
        if (currentCal.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)) {
            day_num = "day_hits + " + num;
        }
        m.put("d_num", day_num);
        m.put("lasthit_dtime", DateUtil.getDateTimeString(currentCal.getTime()));
        InfoDAO.addInfoHits(m);
    }

    /**
     * 添加内容的点击次数
     *
     * @param String info_id
     * @param String num 递增增加数
     * @return boolean
     */
    public static void addWxInfoHits(String info_id, String num, String lastHit_date) {
        Calendar cal = GregorianCalendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar currentCal = GregorianCalendar.getInstance();
        try {
            Date date = sdf.parse(lastHit_date);
            cal.setTime(date);
        } catch (ParseException e) {
            // 解析失败后,默认最后修改时间为当前时间
            cal.setTime(currentCal.getTime());
        }

        Map<String, String> m = new HashMap<String, String>();
        m.put("info_id", info_id);
        m.put("num", num); // 总点击增加数目

        String month_num = num; // 月点击设置
        if (currentCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) {
            month_num = "month_hits + " + num;
        }
        m.put("m_num", month_num);

        String week_num = num; //周点击设置
        if (currentCal.get(Calendar.WEEK_OF_YEAR) == cal.get(Calendar.WEEK_OF_YEAR)) {
            week_num = "week_hits + " + num;
        }
        m.put("w_num", week_num);

        String day_num = num; // 日点击设置
        if (currentCal.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)) {
            day_num = "day_hits + " + num;
        }
        m.put("d_num", day_num);
        m.put("lasthit_dtime", DateUtil.getDateTimeString(currentCal.getTime()));
        InfoDAO.addWxInfoHits(m);
    }

    /**
     * 添加内容的点击次数
     *
     * @param String info_id
     * @param String num 递增增加数
     * @return boolean
     */
    public static void addAppInfoHits(String info_id, String num, String lastHit_date) {
        Calendar cal = GregorianCalendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar currentCal = GregorianCalendar.getInstance();
        try {
            Date date = sdf.parse(lastHit_date);
            cal.setTime(date);
        } catch (ParseException e) {
            // 解析失败后,默认最后修改时间为当前时间
            cal.setTime(currentCal.getTime());
        }

        Map<String, String> m = new HashMap<String, String>();
        m.put("info_id", info_id);
        m.put("num", num); // 总点击增加数目

        String month_num = num; // 月点击设置
        if (currentCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) {
            month_num = "month_hits + " + num;
        }
        m.put("m_num", month_num);

        String week_num = num; //周点击设置
        if (currentCal.get(Calendar.WEEK_OF_YEAR) == cal.get(Calendar.WEEK_OF_YEAR)) {
            week_num = "week_hits + " + num;
        }
        m.put("w_num", week_num);

        String day_num = num; // 日点击设置
        if (currentCal.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)) {
            day_num = "day_hits + " + num;
        }
        m.put("d_num", day_num);
        m.put("lasthit_dtime", DateUtil.getDateTimeString(currentCal.getTime()));
        InfoDAO.addAppInfoHits(m);
    }

    /**
     * 根据站点删除所有信息
     *
     * @param String site_id
     * @param String delete_cat_id 要删除的栏目ID，用于根据栏目ID删除信息
     * @return boolean
     */
    public static boolean deleteInfoBySite(String site_id, String delete_cat_id) {
        try {
            Map<String, String> m = new HashMap<String, String>();
            m.put("site_id", site_id);
            if (delete_cat_id != null && !"".equals(delete_cat_id)) {
                m.put("delete_cat_id", delete_cat_id);
            }
            //首先删除付表中的信息(所有的内容模型)
            List<ModelBean> ml = ModelManager.getModelList();
            for (ModelBean mb : ml) {
                try {
                    if (!mb.getModel_ename().equals(LINK_MODEL_ENAME)) {
                        m.put("table_name", mb.getTable_name());
                        InfoDAO.deleteInfoModelBySite(m);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //再删除公开附件表
            GKInfoDAO.clearGKResFile(m);
            //再次删除公开主表
            GKInfoDAO.clearGKInfo(m);
            //最后删除信息主表啦
            InfoDAO.deleteInfo(m);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据栏目和站点ID删除该栏目下的信息索引
     *
     * @param String site_id
     * @param String delete_cat_id 要删除的栏目ID，用于根据栏目ID删除信息
     * @return
     */
    public static void clearInfoSearchByCatID(String site_id, String delete_cat_id) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("site_id", site_id);
        m.put("delete_cat_id", delete_cat_id);
        List<InfoBean> l = InfoDAO.getPublishInfoByCateID(m);
        if (l != null && l.size() > 0) {
            if (site_id.startsWith("GK") || "ggfw".equals(site_id)) {//信息公开的需要把页面也删除掉

                InfoPublishManager.cancelInfoSearchAndPage(l);
            } else
                InfoPublishManager.cancelInfoSearch(l);
        }
    }

    /**
     * 根据信息ID得到点击次数
     *
     * @param String info_id
     * @return String
     */
    public static String getInfoClickCount(String info_id) {
        return InfoDAO.getInfoClickCount(info_id);
    }

    /**
     * 根据信息ID得到点击次数
     *
     * @param String info_id
     * @return String
     */
    public static String getWxInfoClickCount(String info_id) {
        return InfoDAO.getWxInfoClickCount(info_id);
    }

    /**
     * 根据信息ID得到点击次数
     *
     * @param String info_id
     * @return String
     */
    public static String getAppInfoClickCount(String info_id) {
        return InfoDAO.getAppInfoClickCount(info_id);
    }

    /**
     * 得到各部门节点信息量统计(已发布的)
     *
     * @param int row_count	 显示的条数
     * @return List<UserRegisterBean>
     */
    public static List<InfoCountBean> getInfoTotalForApp(int row_count, String app_id, Map<String, String> m) {
        m.put("app_id", app_id);
        List<InfoCountBean> c_l = new ArrayList<InfoCountBean>();
        List<InfoCountBean> l = InfoDAO.getInfoTotalForApp(m);
        processTotalList(l, c_l, app_id, row_count);
        return c_l;
    }

    /**
     * 得到各站点，部门所有内容的访问量统计排行
     *
     * @param String item_name 统计类型 hits week_hits day_hits month_hits
     * @param int    row_count	 显示的条数
     * @return List<InfoCountBean>
     */
    public static List<InfoCountBean> getSiteAccessStatistics(String item_name, int row_count, String app_id) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("app_id", app_id);
        m.put("item_name", item_name);
        List<InfoCountBean> c_l = new ArrayList<InfoCountBean>();
        List<InfoCountBean> l = InfoDAO.getSiteAccessStatistics(m);
        processTotalList(l, c_l, app_id, row_count);
        return c_l;
    }

    public static void processTotalList(List<InfoCountBean> l, List<InfoCountBean> c_l, String app_id, int row_count) {
        if (row_count == 0)
            row_count = 10;
        if (l != null && l.size() > 0) {
            if (l.size() < row_count) row_count = l.size();

            int i = 1;
            for (InfoCountBean icb : l) {
                if (i > row_count)
                    break;
                try {
                    if ("zwgk".equals(app_id)) {
                        icb.setSite_name(GKNodeManager.getNodeNameByNodeID(icb.getSite_id()));
                    }
                    if ("cms".equals(app_id)) {
                        icb.setSite_name(SiteManager.getSiteBeanBySiteID(icb.getSite_id()).getSite_name());
                    }
                    c_l.add(icb);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }

    /**
     * 得到站点按人员统计信息发布量
     *
     * @param String site_id
     * @return List<InfoCountBean>
     */
    public static List<InfoCountBean> getInfoTotalForSiteUser(String site_id, int row_count) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("site_id", site_id);
        List<InfoCountBean> c_l = new ArrayList<InfoCountBean>();
        List<InfoCountBean> l = InfoDAO.getInfoTotalForSiteUser(m);
        if (row_count == 0)
            row_count = 10;
        if (l != null && l.size() > 0) {
            if (l.size() < row_count) row_count = l.size();

            int i = 1;
            for (InfoCountBean icb : l) {
                if (i > row_count)
                    break;
                icb.setUser_realname(UserManager.getUserRealName(icb.getUser_id() + ""));
                c_l.add(icb);
            }
        }
        return c_l;
    }

    /*
     * 增加访问信息
     * map
     */
    public static void insertAccessInfo(String info_id, String info_tile, int cat_id, String app_id, String site_id, String access_url, HttpServletRequest request) {
        String access_time = com.deya.util.DateUtil.getCurrentDateTime();
        String access_ip = com.deya.util.FormatUtil.getIpAddr(request);
        String site_domain = request.getServerName();
		/*
		System.out.println("info_tile===="+info_tile +"======info_id===="+info_id);
		System.out.println("access_ip===="+access_ip +"======cat_id===="+cat_id);
		System.out.println("site_id===="+site_id+"======access_url===="+access_url);
		System.out.println("site_domain===="+site_domain);
		*/
        String temp_time = "";
        if (access_time != "") {
            try {
                temp_time = DateUtil.formatToString(access_time, "yyyy-MM-dd");
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        int id = PublicTableDAO.getIDByTableName(PublicTableDAO.AccessInfoCount_TABLE_NAME);
        InfoAccessBean accbean = new InfoAccessBean();
        accbean.setId(id);
        accbean.setInfo_id(Integer.parseInt(info_id));
        accbean.setCat_id(cat_id);
        accbean.setInfo_title(info_tile);
        accbean.setAccess_day(temp_time.substring(8, 10));
        accbean.setAccess_month(temp_time.substring(5, 7));
        accbean.setAccess_year(temp_time.substring(0, 4));
        accbean.setApp_id(app_id);
        if (access_ip != "" && access_ip != null) {
            accbean.setAccess_ip(access_ip);
        } else {
            accbean.setAccess_ip("");
        }
        accbean.setAccess_time(access_time);
        accbean.setAccess_url(access_url);
        accbean.setSite_domain(site_domain);
        accbean.setSite_id(site_id);

        InfoDAO.insertAccessInfo(accbean);
    }

    public static String getInfoByUrl(String url) {
        return InfoDAO.getInfoByUrl(url);
    }

    /**
     * 根据信息id获取信息的最新审核步骤信息
     *
     * @param info_id
     * @return
     */
    public static InfoWorkStep getInfoWorkStepByInfoId(String info_id, String pass_status) {
        List<InfoWorkStep> infoWorkStepByInfoId = InfoDAO.getInfoWorkStepByInfoId(info_id, pass_status);
        if (infoWorkStepByInfoId != null && infoWorkStepByInfoId.size() > 0) {
            return infoWorkStepByInfoId.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据信息id获取信息的所有审核步骤信息
     *
     * @param info_id
     * @return
     */
    public static List<InfoWorkStep> getAllInfoWorkStepByInfoId(String info_id, String pass_status) {
        return InfoDAO.getInfoWorkStepByInfoId(info_id, pass_status);
    }

    public static void main(String[] args) {
        List<InfoCountBean> c_l = getInfoTotalForSiteUser("HIWCMdemo", 3);
        System.out.println(c_l.get(0).getUser_realname() + "  " + c_l.get(0).getPublish_count());

    }
}