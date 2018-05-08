package com.deya.wcm.dataCollection.dao;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dataCollection.bean.RuleCategoryBean;
import com.deya.wcm.db.DBManager;

import java.util.List;
import java.util.Map;

public class RuleCategoryDAO {

    /**
     * 根据站点ID得到标签分类，用于克隆站点
     *
     * @param String site_id
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static List<RuleCategoryBean> getRuleCataListBySiteID(String site_id) {
        return DBManager.queryFList("getRuleCataListBySiteID", site_id);
    }

    /**
     * 取得采集规则分类列表
     *
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static List<RuleCategoryBean> getRuleCategoryList() {
        return DBManager.queryFList("getRuleCataList", "");
    }

    /**
     * 克隆采集规则分类
     *
     * @param wcb 采集规则分类对象
     * @return true 成功| false 失败
     */
    public static boolean cloneRuleCate(RuleCategoryBean wcb) {
        String id = PublicTableDAO.getIDByTableName("cs_rule_category") + "";
        wcb.setId(id);
        return DBManager.insert("insertRuleCategory", wcb);
    }

    /**
     * 插入采集规则分类
     *
     * @param wcb 采集规则分类对象
     * @param stl
     * @return true 成功| false 失败
     */
    public static boolean insertRuleCate(RuleCategoryBean wcb, SettingLogsBean stl) {
        String id = PublicTableDAO.getIDByTableName("cs_rule_category") + "";
        wcb.setId(id);
        wcb.setRcat_id(id);
        wcb.setRcat_position(wcb.getRcat_position() + id + "$");
        if (DBManager.insert("insertRuleCategory", wcb)) {
            PublicTableDAO.insertSettingLogs("添加", "采集规则分类", id, stl);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改采集规则分类
     *
     * @param wcb 采集规则分类对象
     * @param stl
     * @return true 成功| false 失败
     */
    public static boolean updateRuleCate(RuleCategoryBean wcb, SettingLogsBean stl) {
        if (DBManager.update("updateRuleCategory", wcb)) {
            PublicTableDAO.insertSettingLogs("修改", "采集规则分类", wcb.getId(), stl);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 保存排序
     *
     * @param wcb 采集规则分类对象
     * @param stl
     * @return true 成功| false 失败
     */
    public static boolean saveRuleCateSort(RuleCategoryBean wcb, SettingLogsBean stl) {
        if (DBManager.update("saveRuleCateSort", wcb)) {
            PublicTableDAO.insertSettingLogs("修改", "采集规则分类排序", wcb.getId(), stl);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除采集规则分类
     *
     * @param mp
     * @param stl
     * @return true 成功| false 失败
     */
    public static boolean deleteRuleCate(Map<String, String> mp, SettingLogsBean stl) {
        if (DBManager.update("deleteRuleCategory", mp)) {
            PublicTableDAO.insertSettingLogs("删除", "采集规则分类", mp.get("id"), stl);
            return true;
        } else {
            return false;
        }
    }

    public static void main(String aeg[]) {
        List<RuleCategoryBean> lt = getRuleCategoryList();
        System.out.println(lt.size());
    }

}
