package com.deya.project.dz_message;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDAO {

    public static List<MessageBean> getMessageInfoList(Map<String, String> map) {
        return DBManager.queryFList("getMessageInfoList", map);
    }

    public static String getMessageInfoCount(Map<String, String> map) {
        return DBManager.getString("getMessageInfoCount", map);
    }


    public static MessageBean getMessage(String id) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("id",id);
        return (MessageBean)DBManager.queryFObj("getMessage", map);
    }

    public static boolean insertMessage(MessageBean Message, SettingLogsBean stl) {
        if (DBManager.insert("insertMessage", Message)) {
            PublicTableDAO.insertSettingLogs("添加", "通知信息", Message.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean updateMessage(MessageBean Message, SettingLogsBean stl) {
        if (DBManager.update("updateMessage", Message)) {
            PublicTableDAO.insertSettingLogs("编辑", "通知信息", Message.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteMessage(List<MessageBean> list, SettingLogsBean stl) {
        if (list != null && list.size() > 0) {
            String ids = "";
            for(MessageBean b:list){
                ids+=b.getId()+",";
            }
            ids = ids.substring(0,ids.length()-1);
            Map<String,String> map = new HashMap<String,String>();
            map.put("ids",ids);
            if (DBManager.delete("deleteMessage", map)) {
                PublicTableDAO.insertSettingLogs("删除", "通知信息", ids, stl);
                return true;
            }
        }
        return false;
    }

    public static boolean updateInfoStatus(List<MessageBean> list, String status, SettingLogsBean stl) {
        if (list != null && list.size() > 0) {
            String ids = "";
            for(MessageBean b:list){
                ids+=b.getId()+",";
            }
            ids = ids.substring(0,ids.length()-1);
            Map<String,String> map = new HashMap<String,String>();
            map.put("ids",ids);
            map.put("info_status",status);
            if (DBManager.delete("updateMessageInfoStatus", map)) {
                PublicTableDAO.insertSettingLogs("修改通知信息状态", "通知信息", ids, stl);
                return true;
            }
        }
        return false;
    }
}
