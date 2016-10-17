package com.deya.wcm.services.control.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.control.SiteConfigBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.control.SiteConfigDAO;

/**
 *  站点配置管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 站点配置管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SiteConfigManager implements ISyncCatch{
	@SuppressWarnings("unchecked")
	private static Map<String,List> config_map = new HashMap<String,List>();
	private static List<SiteConfigBean> config_list = null;
	
	/**
	 * 初始加载站点配置信息
	 * 
	 * @param
	 * @return
	 */
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	@SuppressWarnings("unchecked")
	public static void reloadCatchHandl()
	{
		config_map.clear();
		config_list = SiteConfigDAO.getAllSiteConfigList();
		if(config_list != null && config_list.size() > 0)
		{
			for(int i=0;i<config_list.size();i++)
			{
				if(config_map.containsKey(config_list.get(i).getSite_id()))
				{
					config_map.get(config_list.get(i).getSite_id()).add(config_list.get(i));
				}else
				{
					List<SiteConfigBean> c_list = new ArrayList();
					c_list.add(config_list.get(i));
					config_map.put(config_list.get(i).getSite_id(), c_list);
				}
			}
		}
	}
	
	/**
	 * 初始加载站点配置信息
	 * 
	 * @param
	 * @return
	 */	
	public static void reloadSiteConfigList()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.control.config.SiteConfigManager");
	}
	
	/**
	 * 根据站点ID获取配置列表
	 * 
	 * @param String site_id
	 * @return List
	 */ 
	@SuppressWarnings("unchecked")
	public static List getConfigListBySiteID(String site_id)
	{
		try{
			List list = null;
			list = config_map.get(site_id);
			if(list==null){
				list = new ArrayList();
			}
			return list;
		}catch (Exception e) { 
			e.printStackTrace();
			return new ArrayList();
		}	
	}
	
	/**
	 * 根据站点ID和key值得到value
	 * @param String site_id
	 * @param String key
	 * @return SiteConfigBean
	 */ 
	@SuppressWarnings("unchecked")
	public static SiteConfigBean getConfigValues(String site_id,String key)
	{
		List<SiteConfigBean> l = getConfigListBySiteID(site_id);
		if(l != null && l.size() > 0)
		{
			for(SiteConfigBean scfb : l)
			{
				if(key.equals(scfb.getConfig_key()))
					return scfb;
			}
		}
		return null;
	}
	
	
	/**
	 * 根据主键ID获取配置
	 * @param String site_id
	 * @return List
	 */ 
	public static SiteConfigBean getConfigByConfigID(String config_id)
	{
		SiteConfigBean configBean = new SiteConfigBean();
		try{
			for(int i=0;i<config_list.size();i++)
			{
				configBean = config_list.get(i);
				//System.out.println(config_id+"===="+configBean.getConfig_id());
				if(config_id.equals(configBean.getConfig_id()+"")){
					return configBean;
				}
			}
			return new SiteConfigBean();
		}catch (Exception e) { 
			e.printStackTrace();
			return new SiteConfigBean();
		}	
	}
	
	/**
     * 插入站点配置信息
     * @param SiteConfigBean scb
     * @return boolean
     * */
	public static boolean insertSiteConfig(List<SiteConfigBean> l,SettingLogsBean stl)
	{
		if(SiteConfigDAO.insertSiteConfig(l, stl))
		{
			reloadSiteConfigList();
			return true;
		}
		else
			return false;
	}	
	
	/**
     * 插入站点配置信息
     * @param SiteConfigBean scb
     * @return boolean
     * */
	public static boolean insertSiteConfig(SiteConfigBean scb)
	{
		if(SiteConfigDAO.insertSiteConfigHandl(scb))
		{
			reloadSiteConfigList();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改站点配置信息
     * @param SiteConfigBean scb
     * @return boolean
     * */
	public static boolean updateSiteConfig(SiteConfigBean scb)
	{
		SiteConfigDAO.updateSiteConfig(scb); 
		reloadSiteConfigList();
		return true;
	}
	
	/**
     * 删除站点配置信息
     * @param String config_ids
     * @return boolean
     * */
	public static boolean deleteSiteConfig(String config_ids)
	{
		try{
			
			SiteConfigDAO.deleteSiteConfig(config_ids);
			reloadSiteConfigList();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	public static void main(String args[])
	{
		//testInsert();
		System.out.println(config_map.get("nx"));
	}
	
	public static void testInsert()
	{
		List<SiteConfigBean> c_list = new ArrayList<SiteConfigBean>();
		SiteConfigBean scb = new SiteConfigBean();
		scb.setSite_id("nx");
		scb.setConfig_key("path1");
		scb.setConfig_value("path_value1");
		
		SiteConfigBean scb2 = new SiteConfigBean();
		scb2.setSite_id("nx");
		scb2.setConfig_key("name1");
		scb2.setConfig_value("name_value1");
		
		SiteConfigBean scb3 = new SiteConfigBean();
		scb3.setSite_id("nx");
		scb3.setConfig_key("ssss1");
		scb3.setConfig_value("ssss_value1");
		c_list.add(scb);
		c_list.add(scb2);
		c_list.add(scb3);
		
		insertSiteConfig(c_list,new SettingLogsBean());
	}
}
