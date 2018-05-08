package com.yinhai.rpc;

import com.yinhai.restful.Category;
import com.yinhai.service.CategorySourceManager;

import java.util.List;

/**
 *  分类渠道
 */
public class CategorySourceRPC {


    /**
     * 设置分类的渠道
     * @param c_id
     * @param sources
     * @return
     */
    public static boolean insertCategorySource(int c_id, String sources){
        return CategorySourceManager.insertCategorySource(c_id, sources);
    }

    /**
     * 删除分类的渠道
     * @param c_id
     * @return
     */
    public static List<String> getCategorySource(int c_id) {
        return CategorySourceManager.getCategorySource(c_id);
    }
}
