package com.deya.wcm.services.sendInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.sendInfo.SendConfigBean;
import com.deya.wcm.bean.sendInfo.SendRecordBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.sendInfo.SendConfigDAO;
import com.deya.wcm.services.control.site.SiteManager;

public class SendConfigManager implements ISyncCatch{
	private static String send_record_page = JconfigUtilContainer.managerPagePath().getProperty("send_record_page", "", "m_org_path");
	private static String send_count_page = JconfigUtilContainer.managerPagePath().getProperty("send_count_page", "", "m_org_path");
	private static Map<String,SendConfigBean> s_conf_map = new HashMap<String,SendConfigBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		s_conf_map.clear();
		try{
			List<SendConfigBean> l = SendConfigDAO.getSendConfigList();
			if(l != null && l.size() > 0)
			{
				for(SendConfigBean scf : l)
				{
					s_conf_map.put(scf.getSite_id(), scf);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void reloadSendConfig()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.sendInfo.SendConfigManager");
	}
	
	public static List<SendConfigBean> getSendConfigList()
	{
		List<SendConfigBean> l = new ArrayList<SendConfigBean>();
		Set<String> set = s_conf_map.keySet();
		if(set != null && set.size() > 0)
		{
			for(String s : set)
			{
				l.add(s_conf_map.get(s));
			}
		}
		return l;
	}
	
	public static SendConfigBean getSendConfigBean(String site_id)
	{
		return s_conf_map.get(site_id);
	}
	
	/**
     * 插入报送站点设置
     * @param SendConfigBean scb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertSendConfig(List<SendConfigBean> l,SettingLogsBean stl)
	{                               
		
		if(SendConfigDAO.insertSendConfig(l, stl))
		{
			reloadSendConfig();
			return true;
		}else
			return false;
	}
	
	/**
     * 修改报送站点设置
     * @param SendConfigBean scb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateSendConfig(SendConfigBean scb,SettingLogsBean stl)
	{
		if(SendConfigDAO.updateSendConfig(scb, stl))
		{
			reloadSendConfig();
			return true;
		}else
			return false;
	}
	
	/**
     * 删除接收站点设置
     * @param String ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteSendConfig(Map<String,String> m,SettingLogsBean stl)
	{
		if(SendConfigDAO.deleteSendConfig(m, stl))
		{
			reloadSendConfig();
			return true;
		}else
			return false;
	}
	
	/**
     * 得到该服务器下的报送过信息的站点树
     * @param string type
     * @return String
     * */
	public static String getSendSiteJSONTree(String type)
	{
		String json = "";
		json = "[{\"id\":0,\"text\":\"报送站点\",\"attributes\":{\"url\":\"\"},\"children\":[";
		List<SendRecordBean> l = SendConfigDAO.getSendSiteList();
		if(l != null && l.size() > 0)
		{
			int i=1;
			for(SendRecordBean scfb : l)
			{
				if(i > 1)
					json += ",";
				if(type != null && !"".equals(type))
					json += "{\"id\":"+i+",\"text\":\""+SiteManager.getSiteBeanBySiteID(scfb.getSend_site_id()).getSite_name()+"\",\"attributes\":{\"url\":\""+send_count_page+"?type="+type+"&site_id="+scfb.getSend_site_id()+"\"}}";
				else
					json += "{\"id\":"+i+",\"text\":\""+SiteManager.getSiteBeanBySiteID(scfb.getSend_site_id()).getSite_name()+"\",\"attributes\":{\"url\":\""+send_record_page+"?site_id="+scfb.getSend_site_id()+"\"}}";
				i++;
			}
		}
		json += "]}]";
		return json;
	}
}
