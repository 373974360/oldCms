package com.yinhai.service;

import com.yinhai.dao.CategorySourceDAO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CategorySourceManager {


    /**
     * 添加分类和渠道的关系
     *
     * @param c_id
     * @param sources
     * @return
     */
    public static boolean insertCategorySource(int c_id, String sources) {
        try {
            //先删除栏目所有的渠道，然后重新添加渠道
            CategorySourceDAO.deleteCategorySourceByCatId(c_id);
            for (String s : sources.split(",")) {
                if (StringUtils.isNotBlank(s)) {
                    CategorySourceDAO.insertCategorySource(c_id, s);
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getCategorySource(int c_id) {
        return CategorySourceDAO.getCategorySource(c_id);
    }
}
