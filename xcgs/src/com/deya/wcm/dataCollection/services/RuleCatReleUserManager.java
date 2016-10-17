package com.deya.wcm.dataCollection.services;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.ware.WareCategoryBean;
import com.deya.wcm.bean.system.ware.WareReleUser;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.system.ware.WareReleUserDAO;
import com.deya.wcm.dataCollection.bean.RuleCatReleUser;
import com.deya.wcm.dataCollection.bean.RuleCategoryBean;
import com.deya.wcm.dataCollection.dao.RuleCatReleUserDAO;
import com.deya.wcm.services.org.group.GroupManager;
import com.deya.wcm.services.system.ware.WareCategoryManager;

import java.util.*;

/**
 * 采集规则分类与人员关联逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 采集规则分类与人员关联逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 *
 * @author zhuliang
 * @version 1.0
 *          *
 */

public class RuleCatReleUserManager implements ISyncCatch {
    private static Map<Integer, RuleCatReleUser> wru_map = new HashMap<Integer, RuleCatReleUser>();

    static {
        reloadCatchHandl();
    }

    public void reloadCatch() {
        reloadCatchHandl();
    }

    public static void reloadCatchHandl() {
        wru_map.clear();
        List<RuleCatReleUser> rcru_list = RuleCatReleUserDAO.getRuleReleUserList();
        if (rcru_list != null && rcru_list.size() > 0) {
            for (int i = 0; i < rcru_list.size(); i++) {
                wru_map.put(rcru_list.get(i).getId(), rcru_list.get(i));
            }
        }
    }

    public static void reloadRuleCatReleUser() {
        SyncCatchHandl.reladCatchForRMI("com.deya.wcm.dataCollection.services.RuleCatReleUserManager");
    }

    /**
     * 根据采集规则分类ID得到关联的用户和用户组列表
     *
     * @param int wcat_id
     * @return list
     */
    public static List<RuleCatReleUser> getRuleCatReleUserListByCat(int wcat_id, String site_id) {
        List<RuleCatReleUser> r_list = new ArrayList<RuleCatReleUser>();
        Set<Integer> rSet = wru_map.keySet();
        for (int i : rSet) {
            RuleCatReleUser wru = wru_map.get(i);
            if (wru.getRcat_id() == wcat_id && site_id.equals(wru.getSite_id())) {
                r_list.add(wru);
            }
        }
        return r_list;
    }

    /**
     * 根据用户ID，站点ID，应用ID得以它所能管理的采集规则分类ID集合
     *
     * @param String user_id
     * @param String site_id
     * @param String app_id
     * @return Set
     */
    public static Set<RuleCategoryBean> getRCatIDByUser(int user_id, String site_id) {
        String grup_ids = GroupManager.getGroupIDSByUserID(user_id + "");//该用户所在的用户组
        if (grup_ids != null && !"".equals(grup_ids))
            grup_ids = "," + grup_ids + ",";
        Set<RuleCategoryBean> c_set = new HashSet<RuleCategoryBean>();
        Set<Integer> rSet = wru_map.keySet();

        for (int i : rSet) {
            RuleCatReleUser wru = wru_map.get(i);

            if (wru.getPriv_type() == 0 && user_id == wru.getPrv_id() && site_id.equals(wru.getSite_id())) {
                c_set.add(RuleCategoryManager.getRuleCteBeanByCID(wru.getRcat_id() + "", site_id));
            }
            if (wru.getPriv_type() == 1 && grup_ids.contains(wru.getPrv_id() + "") && site_id.equals(wru.getSite_id())) {
                c_set.add(RuleCategoryManager.getRuleCteBeanByCID(wru.getRcat_id() + "", site_id));
            }
        }
        return c_set;
    }

    /**
     * 插入采集规则分类与人员的关联(以分类为主)
     *
     * @param int             wcat_id
     * @param String          usre_ids
     * @param String          group_ids
     * @param String          app_id
     * @param String          site_id
     * @param SettingLogsBean stl
     * @return boolean
     */
    public static boolean insertRuleCatReleUserByCat(int wcat_id, String usre_ids, String group_ids, String app_id, String site_id, SettingLogsBean stl) {
        if (RuleCatReleUserDAO.insertRuleReleUserByCat(wcat_id, usre_ids, group_ids, app_id, site_id, stl)) {
            reloadRuleCatReleUser();
            return true;
        } else
            return false;
    }

    /**
     * 根据采集规则分类ID删除关联信息
     *
     * @param String wcat_id
     * @return boolean
     */
    public static boolean deleteRCRUByCat(String wcat_id, String site_id) {
        if (RuleCatReleUserDAO.deleteRRUByCat(wcat_id, site_id)) {
            reloadRuleCatReleUser();
            return true;
        } else
            return false;
    }

    public static void main(String[] args) {
    }
}
