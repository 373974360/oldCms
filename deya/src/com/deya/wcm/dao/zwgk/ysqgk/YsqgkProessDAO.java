package com.deya.wcm.dao.zwgk.ysqgk;

import com.deya.wcm.bean.zwgk.ysqgk.YsqgkProcessBean;
import com.deya.wcm.db.DBManager;

import java.util.List;
import java.util.Map;

public class YsqgkProessDAO {

    public static boolean insertYsqgkProess(Map<String, String> map)
    {
        return DBManager.insert("insertYsqgkProess", map);
    }
    public static List<YsqgkProcessBean> getYsqgkProessList(Map<String,String> m)
    {
        return DBManager.queryFList("getYsqgkProessList", m);
    }
}
