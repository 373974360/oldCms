package com.deya.wcm.dao.sendInfo;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.sendInfo.SendConfigBean;
import com.deya.wcm.bean.sendInfo.SendRecordBean;
import com.deya.wcm.dao.LogUtil;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class SendConfigDAO {
	/**
     * 得到报送站点设置列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<SendConfigBean> getSendConfigList()
	{
		return DBManager.queryFList("getSendConfigList", "");
	}
	
	/**
     * 得到报送站点设置列表
     * @param 
     * @return List
     * */
	public static SendConfigBean getSendConfigBean(String site_id)
	{
		return (SendConfigBean)DBManager.queryFObj("getSendConfigBean", site_id);
	}
	
	/**
     * 插入报送站点设置
     * @param SendConfigBean scb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertSendConfig(List<SendConfigBean> l,SettingLogsBean stl)
	{
		String site_ids = "";
		if(l != null && l.size() > 0)
		{
			try{
				for(SendConfigBean scf : l)
				{
					scf.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.SEND_CONFIG_TABLE_NAME));
					site_ids += ","+scf.getSite_id();
					DBManager.update("insert_send_conf", scf);
				}
				PublicTableDAO.insertSettingLogs(LogUtil.ADD,"报送站点设置",site_ids.substring(1),stl);
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else
			return true;
	}
	
	/**
     * 修改报送站点设置
     * @param SendConfigBean scb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateSendConfig(SendConfigBean scb,SettingLogsBean stl)
	{
		if(DBManager.update("update_send_conf", scb))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"报送站点设置",scb.getId()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除报送站点设置
     * @param Map
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteSendConfig(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_send_conf", m))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.DELETE,"报送站点设置",m.get("ids"),stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 从报送记录表中得到已报送的站点
     * @param 
     * @return List<SendConfigBean>
     * */
	@SuppressWarnings("unchecked")
	public static List<SendRecordBean> getSendSiteList()
	{
		return DBManager.queryFList("getSendSiteList", "");
	}
}
