package com.deya.wcm.dao.sendInfo;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.sendInfo.AdoptRecordBean;
import com.deya.wcm.dao.LogUtil;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class AdoptRecordDAO {
	/**
     * 得到采用记录总数
     * @param Map<String,String> m
     * @return String
     * */
	public static String getAdoptRecordCount(Map<String,String> m)
	{
		return DBManager.getString("getAdoptRecordCount", m);
	}
	
	/**
     * 得到采用记录列表
     * @param Map<String,String> m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<AdoptRecordBean> getAdoptRecordList(Map<String,String> m)
	{
		return DBManager.queryFList("getAdoptRecordList", m);
	}
	
	/**
     * 插入采用记录
     * @param AdoptRecordBean arb
     * @param SettingLogsBean stl
     * @return List
     * */
	public static boolean insertAdoptRecord(AdoptRecordBean arb,SettingLogsBean stl)
	{
		if( DBManager.insert("insert_adopt_record", arb))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.ADD,"接收信息采用记录",arb.getId()+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除采用记录
     * @param AdoptRecordBean arb
     * @param SettingLogsBean stl
     * @return List
     * */
	public static boolean deleteAdoptRecord(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_adopt_record", stl))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.DELETE,"接收信息采用记录",m.get("ids"),stl);
			return true;
		}else
			return false;
	}
}
