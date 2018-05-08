package com.deya.wcm.dao.sendInfo;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.sendInfo.ReceiveInfoBean;
import com.deya.wcm.bean.sendInfo.SendRecordBean;
import com.deya.wcm.bean.sendInfo.SendRecordCount;
import com.deya.wcm.db.DBManager;

public class SendCountDAO {
	/**
     * 根据条件得到人员报送统计
     * @param Map<String,String> m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<SendRecordCount> getSendUserListForRecord(Map<String,String> m)
	{
		return DBManager.queryFList("getSendUserListForRecord", m);
	}
	
	/**
     * 根据条件得到栏目报送统计
     * @param Map<String,String> m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<SendRecordCount> getSendCateListForRecord(Map<String,String> m)
	{
		return DBManager.queryFList("getSendCateListForRecord", m);
	}
	
	/**O
     * 根据条件得到报送过的站点列表
     * @param Map<String,String> m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<SendRecordBean> getReceiveSiteListForRecord(String site_id)
	{
		return DBManager.queryFList("getReceiveSiteListForRecord", site_id);
	}
	
	/**
     * 根据条件得到栏目接收统计
     * @param Map<String,String> m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<SendRecordCount> getReceiveCateListForRecord(Map<String,String> m)
	{
		return DBManager.queryFList("getReceiveCateListForRecord", m);
	}
	
	/**
     * 根站点得到所有报送到此站点的列表
     * @param Map<String,String> m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<SendRecordCount> getSendSiteCountForReceive(Map<String,String> m)
	{
		return DBManager.queryFList("getSendSiteCountForReceive", m);		
	}
	
	/**
     * 根站点得到所有报送到此站点的列表
     * @param Map<String,String> m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<ReceiveInfoBean> getSendSiteList(String site_id)
	{
		return DBManager.queryFList("getSendSiteListForCount", site_id);		
	}
	
	
}
