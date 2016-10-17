package com.deya.wcm.dao.sendInfo;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.sendInfo.ReceiveInfoBean;
import com.deya.wcm.dao.LogUtil;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class ReceiveInfoDAO {
	/**
     * 得到报送到该站点的信息总数
     * @param 
     * @return String
     * */
	public static String getReceiveInfoCount(Map<String,String> m)
	{
		return DBManager.getString("getReceiveInfoCount", m);
	}
	
	/**
     * 
     * @param 
     * @return String
     * */
	public static ReceiveInfoBean getReceiveInfoBean(String id)
	{
		return (ReceiveInfoBean)DBManager.queryFObj("getReceiveInfoBean", id);
	}
	
	/**
     * 得到报送到该站点的信息列表
     * @param Map<String,String> m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<ReceiveInfoBean> getReceiveInfoList(Map<String,String> m)
	{
		return DBManager.queryFList("getReceiveInfoList", m);
	}
	
	/**
     * 得到报送到该站点的信息列表
     * @param Map<String,String> m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<ReceiveInfoBean> getReceiveInfoListForIDS(Map<String,String> m)
	{
		return DBManager.queryFList("getReceiveInfoListForIDS", m);
	}
	
	/**
     * 插入报送信息
     * @param ReceiveInfoBean rib
     * @return boolean
     * */
	public static boolean insertReceiveInfo(ReceiveInfoBean rib)
	{
		return DBManager.insert("insert_receive_info", rib);
	}
	
	/**
     * 采用报送信息操作
     * @param ReceiveInfoBean rib
     * @return boolean
     * */
	public static boolean adoptReceiveInfo(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("adopt_receive_info", m))
		{
			if("1".equals(m.get("adopt_status")))
			{
				PublicTableDAO.insertSettingLogs(LogUtil.DEAL,"采用报送信息",m.get("ids"),stl);
			}else
				PublicTableDAO.insertSettingLogs(LogUtil.DEAL,"不采用报送信息",m.get("ids"),stl);
			
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除报送信息
     * @param ReceiveInfoBean rib
     * @return boolean
     * */
	public static boolean deleteReceiveInfo(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("delete_receive_info", m))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.DELETE,"报送信息",m.get("ids"),stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 得到接收的栏目列表
     * @param String site_id
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<ReceiveInfoBean> getReceiveCateInfoList(String site_id)
	{
		return DBManager.queryFList("getReceiveCateInfoList", site_id);
	}
}
