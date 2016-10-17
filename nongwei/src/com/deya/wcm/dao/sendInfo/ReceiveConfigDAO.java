package com.deya.wcm.dao.sendInfo;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.sendInfo.ReceiveCatConf;
import com.deya.wcm.bean.sendInfo.ReceiveConfigBean;
import com.deya.wcm.dao.LogUtil;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class ReceiveConfigDAO {
	/**
     * 得到接收站点设置列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<ReceiveConfigBean> getReceiveConfigList()
	{
		return DBManager.queryFList("getReceiveConfigList", "");
	}
	
	/**
     * 插入接收站点设置
     * @param ReceiveConfigBean rcf
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertReceiveConfig(ReceiveConfigBean rcf,SettingLogsBean stl)
	{
		if( DBManager.insert("insert_receive_config", rcf))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.ADD,"接收站点设置",rcf.getId()+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改接收站点设置
     * @param ReceiveConfigBean rcf
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateReceiveConfig(ReceiveConfigBean rcf,SettingLogsBean stl)
	{
		if(DBManager.update("update_receive_config", rcf))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"接收站点设置",rcf.getId()+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改接收站点设置状态，停用，启用
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateReceiveConfigStatus(Map<String,String> m,SettingLogsBean stl)
	{
		if( DBManager.update("update_receive_config_status", m))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"接收站点设置状态",m.get("ids"),stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除接收站点设置
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteReceiveConfig(String site_id,SettingLogsBean stl)
	{
		String[] ids = site_id.split(",");
		if(ids != null && ids.length > 0)
		{
			for(int i=0;i<ids.length;i++)
			{
				DBManager.delete("delete_receive_config", ids[i]);
				DBManager.delete("delete_receive_cat_conf", ids[i]);
			}
			PublicTableDAO.insertSettingLogs(LogUtil.DELETE,"接收站点设置",site_id,stl);
			return true;
		}
		return true;		
	}
	
	/**
     * 得到接收栏目设置列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<ReceiveCatConf> getReceiveCatConfList()
	{
		return DBManager.queryFList("getReceiveCatConfList", "");
	}
	
	/**
     * 插入接收栏目设置列表
     * @param 
     * @return List
     * */
	public static boolean insertReceiveCatConf(List<ReceiveCatConf> l,SettingLogsBean stl)
	{
		if(l != null && l.size() > 0)
		{
			if(insertRCFHandl(l))
			{
				PublicTableDAO.insertSettingLogs(LogUtil.ADD,"接收站点栏目设置",l.get(0).getSite_id(),stl);
				return true;
			}else
				return false;
		}
		return true;
	}
	
	/**
     * 修改接收栏目设置列表
     * @param String site_id
     * @param List<ReceiveCatConf> l
     * @param SettingLogsBean stl
     * @return List
     * */
	public static boolean updateReceiveCatConf(String site_id,List<ReceiveCatConf> l,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_receive_cat_conf", site_id))
		{
			if(insertRCFHandl(l))
			{
				PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"接收站点栏目设置",site_id,stl);
				return true;
			}else
				return false;
		}
			return false;
	}
	
	public static boolean insertRCFHandl(List<ReceiveCatConf> l)
	{
		try{
			for(ReceiveCatConf rcc : l)
			{
				rcc.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.RECEIVE_CAT_TABLE_NAME));
				DBManager.insert("insert_receive_cat_conf", rcc);
			}
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
