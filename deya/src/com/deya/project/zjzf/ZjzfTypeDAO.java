package com.deya.project.zjzf;

import com.deya.wcm.db.DBManager;

import java.util.List;
import java.util.Map;

public class ZjzfTypeDAO {
    public static List<ZjzfTypeBean> getZjzfTypeList(Map<String, String> m) {
        return DBManager.queryFList("getZjzfTypeList",m);
    }

    public static ZjzfTypeBean getZjzfTypeBean(int id) {
        return (ZjzfTypeBean)DBManager.queryFObj("getZjzfTypeBean", id);
    }

    public static boolean insertZjzfType(ZjzfTypeBean zjzf) {
        return DBManager.insert("insert_zjzftype", zjzf);
    }

    public static boolean deleteZjzfType(Map<String, String> m) {
        return DBManager.delete("delete_zjzftype", m);
    }

    public static boolean updateZjzfType(ZjzfTypeBean zjzf) {
        return DBManager.update("update_zjzftype", zjzf);
    }
}
