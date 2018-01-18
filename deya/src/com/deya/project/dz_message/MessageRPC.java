package com.deya.project.dz_message;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class MessageRPC {

    public static List<MessageBean> getMessageInfoList(Map<String, String> map) {
        return MessageManager.getMessageInfoList(map);
    }

    public static int getMessageInfoCount(Map<String, String> map) {
        return Integer.parseInt(MessageManager.getMessageInfoCount(map));
    }

    public static MessageBean getMessage(String id) {
        return MessageManager.getMessage(id);
    }

    public static boolean insertMessage(MessageBean message, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return MessageManager.insertMessage(message, stl);
        }
        return false;
    }

    public static boolean updateMessage(MessageBean message, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return MessageManager.updateMessage(message, stl);
        }
        return false;
    }

    public static boolean deleteMessage(List<MessageBean> list, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return MessageManager.deleteMessage(list, stl);
        }
        return false;
    }

    public static boolean updateInfoStatus(List<MessageBean> list, String status, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return MessageManager.updateInfoStatus(list, status, stl);
        }
        return false;
    }
}
