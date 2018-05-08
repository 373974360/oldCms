package com.yinhai.dao;

import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 栏目和渠道的关系，渠道就是source
 */
public class CategorySourceDAO {

    /**
     * 删除栏目的所有渠道
     *
     * @param catId
     * @return
     */
    public static boolean deleteCategorySourceByCatId(int catId) {


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("catId", catId);
        boolean deleteCategorySourceByCatId = DBManager.update("deleteCategorySourceByCatId", map);
        return deleteCategorySourceByCatId;
    }

    /**
     * 添加栏目的渠道
     *
     * @param catId
     * @param source
     * @return
     */
    public static boolean insertCategorySource(int catId, String source) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("catId", catId);
        map.put("source", source);
        boolean insertCategorySource = DBManager.update("insertCategorySource", map);
        return insertCategorySource;
    }

    /**
     * 查询栏目的渠道
     * @param catId
     * @return
     */
    public static List<String> getCategorySource(int catId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("catId", catId);
        return DBManager.queryFList("getCategorySource",map);
    }
}
