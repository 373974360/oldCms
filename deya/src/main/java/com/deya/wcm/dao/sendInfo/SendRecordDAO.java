package com.deya.wcm.dao.sendInfo;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.sendInfo.SendRecordBean;
import com.deya.wcm.dao.LogUtil;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class SendRecordDAO {
	/**
     * 得到报送记录总数
     * @param Map<String,String> m
     * @return String
     * */
	public static String getSendRecordCount(Map<String,String> m)
	{
		return DBManager.getString("getSendRecordCount", m);
	}
	
	/**
     * 得到报送记录列表
     * @param Map<String,String> m
     * @return String
     * */
	@SuppressWarnings("unchecked")
	public static List<SendRecordBean> getSendRecordList(Map<String,String> m)
	{
		return DBManager.queryFList("getSendRecordList", m);
	}
	
	/**
     * 插入报送记录
     * @param List<SendRecordBean> l
     * @return String
     * */
	public static boolean insertSendRecord(List<SendRecordBean> l)
	{
		try{
			for(SendRecordBean srb : l)
			{
				DBManager.insert("insert_send_record", srb);
			}
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
     * 修改报送记录，用于返回是否采用信息
     * @param Map<String,String> m
     * @return String
     * */
	public static boolean updateSendRecord(Map<String,String> m)
	{
		return DBManager.update("update_send_record", m);
	}
	
	/**
     * 删除报送记录
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return String
     * */
	public static boolean deleteSendRecord(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_send_record", m))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.DELETE,"报送记录信息",m.get("ids"),stl);
			return true;
		}
		else
			return false;
	}
}
