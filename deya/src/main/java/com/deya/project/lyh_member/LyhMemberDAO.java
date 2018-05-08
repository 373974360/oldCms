package com.deya.project.lyh_member;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.project.lyh_member.LyhMemberBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LyhMemberDAO {
    public LyhMemberDAO() {
    }

    public static String getLyhMemberCount(Map<String, String> m) {
        return DBManager.getString("getLyhMemberCount", m);
    }

    public static List<LyhMemberBean> getLyhMemberList(Map<String, String> m) {
        return DBManager.queryFList("getLyhMemberList", m);
    }

    public static LyhMemberBean getLyhMemberBean(String id, boolean is_browser) {
        HashMap m = new HashMap();
        m.put("id", id);
        if(is_browser) {
            m.put("is_browser", "1");
        }

        //System.out.println(DBManager.queryFObj("getLyhMemberBean", m) + "****************************");
        return (LyhMemberBean)DBManager.queryFObj("getLyhMemberBean", m);
    }

    public static List<LyhMemberBean> getAllMemberList() {
        return DBManager.queryFList("getAllLyhMemberList", "");
    }

    public static boolean insertLyhMember(LyhMemberBean mb) {
        return DBManager.insert("insert_lyhMember", mb);
    }

    public static boolean updateLyhMember(LyhMemberBean mb, SettingLogsBean stl) {
        if(DBManager.update("update_lyhMember", mb)) {
            PublicTableDAO.insertSettingLogs("修改", "会员信息", String.valueOf(mb.getId()), stl);
            return true;
        } else {
            return false;
        }
    }

    public static boolean publishLyhMember(Map<String, String> m, SettingLogsBean stl) {
        if(DBManager.update("publish_lyhMember", m)) {
            PublicTableDAO.insertSettingLogs("发布设置", "会员信息", (String)m.get("ids"), stl);
            return true;
        } else {
            return false;
        }
    }

    public static boolean deleteLyhMember(Map<String, String> m, SettingLogsBean stl) {
        if(DBManager.delete("delete_lyhMember", m)) {
            PublicTableDAO.insertSettingLogs("删除", "会员信息", (String)m.get("ids"), stl);
            return true;
        } else {
            return false;
        }
    }
}
