package com.deya.wcm.services.zwgk.ysqgk;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.ysqgk.*;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class YsqgkRPC {

    //依申请公开配置 开始*************************************

    /**
     * 返回依申请公开配置对象
     *
     * @return YsqgkConfigBean
     */
//    public static YsqgkConfigBean getYsqgkConfigBean() {
//        return YsqgkConfigManager.getYsqgkConfigBean();
//    }


    public static YsqgkConfigBean getYsqgkConfigBeanBySiteId(String site_id) {

        return YsqgkConfigManager.getYsqgkConfigBeanBySiteId(site_id);
    }

    /**
     * 插入依申请公开配置对象
     *
     * @param YsqgkConfigBean ysqgk
     * @param SettingLogsBean stl
     * @return boolean
     */
    public static boolean insertYsqgkConfig(YsqgkConfigBean ysqgk, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return YsqgkConfigManager.insertYsqgkConfig(ysqgk, stl);
        } else
            return false;
    }
    //依申请公开配置 结束****************************************


    //依申请公开信息开始****************************************

    /**
     * 插入依申请公开信息
     *
     * @param YsqgkBean       ysqgk
     * @param SettingLogsBean stl
     * @return boolean
     */
    public static Map<String, String> insertYsqgkInfo(YsqgkBean ysqgk, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return YsqgkInfoManager.insertYsqgkInfo(ysqgk, stl);
        } else
            return null;
    }

    /**
     * 插入依申请公开信息
     *
     * @param YsqgkBean       ysqgk
     * @param SettingLogsBean stl
     * @return boolean
     */
    public static Map<String, String> insertYsqgkInfoForBro(YsqgkBean ysqgk) {
        return YsqgkInfoManager.insertYsqgkInfo(ysqgk, null);
    }

    /**
     * 返回依申请公开信息对象
     *
     * @return YsqgkBean
     */
    public static YsqgkBean getYsqgkBean(String ysq_id) {
        return YsqgkInfoManager.getYsqgkBean(ysq_id);
    }

    /**
     * 得到依申请公开信息列表
     *
     * @param Map m
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static List<YsqgkListBean> getYsqgkLists(Map<String, String> m) {
        return YsqgkInfoManager.getYsqgkLists(m);
    }

    /**
     * 得到依申请公开信息总数
     *
     * @param map
     * @return int
     */
    public static int getYsqgkListsCount(Map<String, String> m) {
        return YsqgkInfoManager.getYsqgkListsCount(m);
    }


    /**
     * 处理依申请公开信息总数
     *
     * @param map
     * @return ture
     */
    public static boolean updateStatus(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return YsqgkInfoManager.updateStatus(m, stl);
        } else
            return false;
    }

    /**
     * 修改依申请公开信息
     *
     * @param String ysq_id
     * @return boolean
     */
    public static boolean updateYsqgkInfo(YsqgkBean ysqgk, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            YsqgkInfoManager.updateYsqgkInfo(ysqgk, stl);
            return true;
        } else
            return false;
    }

    /**
     * 修改依申请公开信息为删除状态
     *
     * @param String ysq_id
     * @return boolean
     */
    public static boolean setDeleteState(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            YsqgkInfoManager.setDeleteState(m, stl);
            return true;
        } else
            return false;
    }

    /**
     * 还原依申请公开信息
     *
     * @param String ysq_id
     * @return boolean
     */
    public static boolean reBackInfos(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            YsqgkInfoManager.reBackInfos(m, stl);
            return true;
        } else
            return false;
    }

    /**
     * 删除依申请公开信息
     *
     * @param SettingLogsBean stl
     * @return boolean
     */
    @SuppressWarnings("unchecked")
    public static boolean deleteYsqgkInfo(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            YsqgkInfoManager.deleteYsqgkInfo(m, stl);
            return true;
        } else
            return false;
    }

    /**
     * 删除依申请公开回收站信息
     *
     * @param SettingLogsBean stl
     * @return boolean
     */
    @SuppressWarnings("unchecked")
    public static boolean clearDeleteYsqgkInfos(HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            YsqgkInfoManager.clearDeleteYsqgkInfos(stl);
            return true;
        } else
            return false;
    }
    //依申请公开信息结束********************************************


    //依申请公开常用语 开始******************************************

    /**
     * 返回依申请公开常用语对象
     *
     * @return YsqgkPhrasalBean
     */
    public static YsqgkPhrasalBean getYsqgkPhrasalBean(String gph_id) {
        return YsqgkPhrasaManager.getYsqgkPhrasalById(gph_id);
    }

    /**
     * 返回依申请公开常用语列表
     *
     * @return YsqgkPhrasalBean
     */
    public static List<YsqgkPhrasalBean> getYsqgkPhrasalLists() {
        return YsqgkPhrasaManager.getYsqgkPhrasaList();
    }

    /**
     * 根据类型返回依申请公开常用语列表
     *
     * @return YsqgkPhrasalBean
     */
    public static List<YsqgkPhrasalBean> getYsqgkPhrasaListByType(int gph_type) {
        return YsqgkPhrasaManager.getYsqgkPhrasaListByType(gph_type);
    }

    /**
     * 得到依申请公开常用语id
     *
     * @return String
     */
    public static String getGph_id() {
        return YsqgkPhrasaManager.getGph_id();
    }

    /**
     * 插入依申请公开常用语
     *
     * @param YsqgkPhrasalBean ypb
     * @param SettingLogsBean  stl
     * @return boolean
     */
    public static boolean insertYsqgkPhrasalBean(YsqgkPhrasalBean ypb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return YsqgkPhrasaManager.insertYsqgkPhrasal(ypb, stl);
        } else
            return false;
    }

    /**
     * 修改依申请公开常用语
     *
     * @param YsqgkPhrasalBean ypb
     * @param SettingLogsBean  stl
     * @return boolean
     */
    public static boolean updateYsqgkPhrasalBean(YsqgkPhrasalBean ypb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return YsqgkPhrasaManager.updateYsqgkPhrasal(ypb, stl);
        } else
            return false;
    }

    /**
     * 删除依申请公开常用语
     *
     * @param m,SettingLogsBean stl
     * @return boolean
     */
    @SuppressWarnings("unchecked")
    public static boolean deleteYsqgkPhrasal(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            YsqgkPhrasaManager.deletePhrasalBeans(m, stl);
            return true;
        } else
            return false;
    }

    /**
     * 保存常用语排序
     *
     * @param String          gph_id
     * @param SettingLogsBean stl
     * @return boolean
     */
    public static boolean saveYsqgkPhrasalSort(String gph_id, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return YsqgkPhrasaManager.savePhrasalSort(gph_id, stl);
        } else {
            return false;
        }
    }
    //依申请公开常用语结束****************************************s




    public static List<YsqgkProcessBean> getYsqgkProessList(String ysq_id)
    {
        Map<String,String> map = new HashMap<>();
        map.put("ysq_id",ysq_id);
        return YsqgkInfoManager.getYsqgkProessList(map);
    }
}