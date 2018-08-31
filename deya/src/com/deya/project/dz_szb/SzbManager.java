package com.deya.project.dz_szb;

import com.deya.wcm.db.DBManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 电子报管理
 * Created by yangyan on 2016/12/6.
 */
public class SzbManager {



    /**
     * 查询电子报列表
     *
     * @param params
     * @return
     */
    public static List<SzbBean> getSzbList(Map<String, Object> params) {
        return DBManager.queryFList("getSzbList", params);
    }

    /**
     * 默认添加的是未发布状态的
     *
     * @param params
     * @return
     */
    public static boolean addSzb(Map<String, Object> params) {
        params.put("status", 0);// 0 = 未发布，1=已发布
        return DBManager.insert("addSzb", params);
    }

    public static boolean updateSzb(Map<String, Object> params) {
        return DBManager.update("updateSzb", params);
    }

    public static boolean updateStatus(Map<String, Object> params) {
        return DBManager.update("updateSzbStatus", params);
    }

    public static String getSzbCount(Map<String, Object> params) {
        return DBManager.getString("getSzbCount", params);
    }

    public static SzbBean getSzb(Map<String, String> params) {
        Object o = DBManager.queryFObj("getSzb", params);
        if (o != null) {
            return (SzbBean) o;
        }
        return null;
    }

    public static boolean deleteSzb(Map<String, Object> params) {
        params.put("status", -1);
        return updateStatus(params);
    }

    public static boolean publishSzb(Map<String, Object> params) {
        params.put("status", 1);
        return updateStatus(params);
    }

    public static SzbBean getNewestSzb() {
        Object obj = DBManager.queryFObj("getNewestSzb", Collections.emptyMap());
        if (obj != null) {
            return (SzbBean) obj;
        }
        return null;
    }
    public static SzbBean getPrevSzb(int id){

        Map<String,Object> params = new HashMap();
        params.put("id", id);
        Object obj = DBManager.queryFObj("getPrevSzb", params);
        if (obj != null) {
            return (SzbBean) obj;
        }
        return null;
    }
    public static SzbBean getNextSzb(int id){
        Map<String,Object> params = new HashMap();
        params.put("id", id);
        Object obj = DBManager.queryFObj("getNextSzb", params);
        if (obj != null) {
            return (SzbBean) obj;
        }
        return null;
    }
}
