package com.deya.project.dz_message;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class MessageManager {

    public static List<MessageBean> getMessageInfoList(Map<String, String> map) {
        return MessageDAO.getMessageInfoList(map);
    }

    public static String getMessageInfoCount(Map<String, String> map) {
        return MessageDAO.getMessageInfoCount(map);
    }

    public static MessageBean getMessage(String id) {
        return MessageDAO.getMessage(id);
    }

    public static boolean insertMessage(MessageBean message, SettingLogsBean stl) {
        message.setId(PublicTableDAO.getIDByTableName("dz_message"));
        message.setInput_dtime(DateUtil.getCurrentDateTime());
        if (StringUtils.isEmpty(message.getReleased_dtime())) {
            message.setReleased_dtime(DateUtil.getCurrentDateTime());
        }
        return MessageDAO.insertMessage(message, stl);
    }

    public static boolean updateMessage(MessageBean message, SettingLogsBean stl) {
        message.setModify_dtime(DateUtil.getCurrentDateTime());
        if (StringUtils.isEmpty(message.getReleased_dtime())) {
            message.setReleased_dtime(DateUtil.getCurrentDateTime());
        }
        return MessageDAO.updateMessage(message, stl);
    }

    public static boolean deleteMessage(List<MessageBean> list, SettingLogsBean stl) {
        return MessageDAO.deleteMessage(list, stl);
    }

    public static boolean updateInfoStatus(List<MessageBean> list, String status, SettingLogsBean stl){
        return MessageDAO.updateInfoStatus(list, status, stl);
    }

}
