package com.deya.wcm.services.sendInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.sendInfo.ReceiveCatConf;
import com.deya.wcm.bean.sendInfo.ReceiveConfigBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.sendInfo.ReceiveConfigDAO;
import com.deya.wcm.rmi.file.FileRmiFactory;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import com.deya.wcm.services.control.site.SiteManager;

public class ReceiveConfigManager implements ISyncCatch{
	private static String receive_info_page = JconfigUtilContainer.managerPagePath().getProperty("receive_info_page", "", "m_org_path");
	private static String receive_count_page = JconfigUtilContainer.managerPagePath().getProperty("receive_count_page", "", "m_org_path");
	
	private static List<ReceiveConfigBean> recConfig_list = new ArrayList<ReceiveConfigBean>();
	private static Map<String,List<ReceiveCatConf>> rec_cat_map = new HashMap<String,List<ReceiveCatConf>>();
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		try{
			recConfig_list.clear();
			rec_cat_map.clear();
			recConfig_list = ReceiveConfigDAO.getReceiveConfigList();
			List<ReceiveCatConf> l = ReceiveConfigDAO.getReceiveCatConfList();
			if(l != null && l.size() > 0)
			{
				for(ReceiveCatConf rcc : l)
				{
					if(rec_cat_map.containsKey(rcc.getSite_id()))
					{
						rec_cat_map.get(rcc.getSite_id()).add(rcc);
					}else
					{
						List<ReceiveCatConf> rcfl = new ArrayList<ReceiveCatConf>();
						rcfl.add(rcc);
						rec_cat_map.put(rcc.getSite_id(), rcfl);
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
     * 初始化方法
     * @param
     * @return 
     * */
	public static void reloadReceiveConfig()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.sendInfo.ReceiveConfigManager");
	}
	
	/**
     * 得到接收站点设置列表
     * @param 
     * @return List
     * */
	public static List<ReceiveConfigBean> getReceiveConfigList()
	{
		if(recConfig_list != null && recConfig_list.size() > 0)
		{
			for(int i=0;i<recConfig_list.size();i++)
			{
				try{
					recConfig_list.get(i).setSite_name(SiteManager.getSiteBeanBySiteID(recConfig_list.get(i).getSite_id()).getSite_name());
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return recConfig_list;
	}
	
	/**
     * 根据ＩＤ得到配置对象
     * @param String site_id
     * @return ReceiveConfigBean
     * */
	public static ReceiveConfigBean getReceiveConfigForID(int id)
	{
		if(recConfig_list != null && recConfig_list.size() > 0)
		{
			for(ReceiveConfigBean rcb : recConfig_list)
			{
				if(rcb.getId() == id)
					return rcb;
			}
		}
		return null;
	}
	
	/**
     * 根据站点ＩＤ得到配置对象
     * @param String site_id
     * @return ReceiveConfigBean
     * */
	public static ReceiveConfigBean getReceiveConfigForSiteID(String site_id)
	{
		if(recConfig_list != null && recConfig_list.size() > 0)
		{
			for(ReceiveConfigBean rcb : recConfig_list)
			{
				if(site_id.equals(rcb.getSite_id()))
					return rcb;
			}
		}
		return null;
	}
	
	/**
     * 插入接收站点设置
     * @param ReceiveConfigBean rcf
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertReceiveConfig(ReceiveConfigBean rcf,List<ReceiveCatConf> l,SettingLogsBean stl)
	{
		rcf.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.RECEIVE_CONFIG_TABLE_NAME));
		if(ReceiveConfigDAO.insertReceiveConfig(rcf, stl))
		{
			ReceiveConfigDAO.insertReceiveCatConf(l, stl);
			saveReceiveCateConfigFile(l,rcf.getSite_id());
			reloadReceiveConfig();
			return true;
		}else
			return false;
	}
	
	/**
     * 修改接收站点设置
     * @param ReceiveConfigBean rcf
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateReceiveConfig(ReceiveConfigBean rcf,List<ReceiveCatConf> l,SettingLogsBean stl)
	{
		if(ReceiveConfigDAO.updateReceiveConfig(rcf, stl))
		{
			ReceiveConfigDAO.updateReceiveCatConf(rcf.getSite_id(), l, stl);
			saveReceiveCateConfigFile(l,rcf.getSite_id());
			reloadReceiveConfig();
			return true;
		}else
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
		if(ReceiveConfigDAO.updateReceiveConfigStatus(m, stl))
		{
			if("0".equals(m.get("receive_status")))
			{
				//启用，生成配置文件
				String[] arr = m.get("ids").split(",");
				for(int i=0;i<arr.length;i++)
				{
					ReceiveConfigBean rcf = getReceiveConfigForID(Integer.parseInt(arr[i]));
					saveReceiveCateConfigFile(getReceiveCatConfList(rcf.getSite_id()),rcf.getSite_id());
				}
			}else
			{
				//停用，删除配置文件
				String[] arr = m.get("ids").split(",");
				for(int i=0;i<arr.length;i++)
				{
					ReceiveConfigBean rcf = getReceiveConfigForID(Integer.parseInt(arr[i]));
					deleteReceiveCateConfigFile(rcf.getSite_id());
				}
			}
			reloadReceiveConfig();
			return true;
		}else
			return false;
	}
	
	/**
     * 删除接收站点设置
     * @param String site_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteReceiveConfig(String site_ids,SettingLogsBean stl)
	{
		if(ReceiveConfigDAO.deleteReceiveConfig(site_ids, stl))
		{
			String[] arr = site_ids.split(",");
			for(int i=0;i<arr.length;i++)
			{
				deleteReceiveCateConfigFile(arr[i]);
			}
			reloadReceiveConfig();
			return true;
		}else
			return false;
	}
	
	/**
     * 根据站点ＩＤ得到允许报送的栏目列表
     * @param String site_id
     * @return List
     * */
	public static List<ReceiveCatConf> getReceiveCatConfList(String site_id)
	{
		if(rec_cat_map.containsKey(site_id))
			return rec_cat_map.get(site_id);
		else
			return null;
	}
	
	/**
     * 删除站点配置文件
     * @param String site_id
     * @return List
     * */
	public static void deleteReceiveCateConfigFile(String site_id)
	{
		FileRmiFactory.delFile(site_id, SiteManager.getSitePath(site_id)+"/sendConfig/sendConfig");
	}
	
	/**
     * 保存站点配置文件
     * @param List<ReceiveCatConf> l
     * @param String site_id
     * */
	public static void saveReceiveCateConfigFile(List<ReceiveCatConf> l,String site_id)
	{
		String xml = "";
		if(l != null && l.size() > 0)
		{
			xml = "[{\"id\":\""+site_id+"\",\"text\":\""+SiteManager.getSiteBeanBySiteID(site_id).getSite_name()+"\",\"children\":";
			xml += "[";			
			xml += getJSONForReceiveCatList(l,site_id);
			xml += "]";
			xml += "}]";
			String savePath = SiteManager.getSitePath(site_id)+"/sendConfig";
			FileRmiFactory.saveFile(site_id, savePath, "sendConfig",xml);
			
			/*
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
			xml += "<cicro>";
			xml += "<site_id>"+site_id+"</site_id>";
			xml += "<site_name>"+SiteManager.getSiteBeanBySiteID(site_id).getSite_name()+"</site_name>";
			xml += "<category_list>";
			for(ReceiveCatConf rcf : l)
			{
				try{
					xml += "<category>";
					xml += "<category_id>"+rcf.getCat_id()+"</category_id>";
					xml += "<category_name><![CDATA["+rcf.getCat_id()+"]]></category_name>";
					xml += "</category>";
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			xml += "</category_list>";
			xml += "</cicro>";
			
			String savePath = SiteManager.getSitePath(site_id)+"/sendConfig";
			FileRmiFactory.saveFile(site_id, savePath, "sendConfig.xml",xml);
			*/
		}
	}
	
	//根据报送栏目列表得到json字符串
	public static String getJSONForReceiveCatList(List<ReceiveCatConf> l,String site_id)
	{
		String json = "";
		for(ReceiveCatConf rcf : l)
		{
			try{
				if(rcf.getSort_id() > 0)
					json += ",";
				json += "{\"id\":\""+rcf.getCat_id()+"\",\"text\":\""+CategoryManager.getCategoryBeanCatID(rcf.getCat_id(), site_id).getCat_cname()+"\"}";	
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return json;
	}
	
	/**
     * 得到该站点下所有可以报送的站点和栏目,webservice接口用
     * @param List<ReceiveCatConf> l
     * @param String site_id
     * @return Str
     * */
	public static String getReceiveConfigForJSON()
	{
		String json = "";
		if(recConfig_list != null && recConfig_list.size() > 0)
		{
			for(ReceiveConfigBean rcf : recConfig_list)
			{
				try{
					if(rcf.getReceive_status() == 0)
					{
						List<ReceiveCatConf> l = getReceiveCatConfList(rcf.getSite_id());
						if( l != null && l.size() > 0)
							json += ",{\"id\":\""+rcf.getSite_id()+"\",\"text\":\""+SiteManager.getSiteBeanBySiteID(rcf.getSite_id()).getSite_name()+"\",\"domain\":\""+SiteDomainManager.getDefaultSiteDomainBySiteID(rcf.getSite_id())+SiteManager.getSitePort()+"\",\"children\":["+getJSONForReceiveCatList(l,rcf.getSite_id())+"]}";
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			json = "["+json.substring(1)+"]";
		}
		return json;
	}
	
	/**
     * 得到该站点下所有可以报送的站点和栏目,webservice接口用
     * @param List<ReceiveCatConf> l
     * @param String site_id
     * @return Str
     * */
	public static String getReceiveConfigForXML()
	{
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		xml += "<cicro>";
		if(recConfig_list != null && recConfig_list.size() > 0)
		{
			xml += "<site_list>";
			for(ReceiveConfigBean rcf : recConfig_list)
			{
				try{
					if(rcf.getReceive_status() == 0)
					{
						List<ReceiveCatConf> l = getReceiveCatConfList(rcf.getSite_id());
						if( l != null && l.size() > 0)
						{
							xml += "<site>";
							xml += "<site_id>"+rcf.getSite_id()+"</site_id>";
							xml += "<site_name>"+SiteManager.getSiteBeanBySiteID(rcf.getSite_id()).getSite_name()+"</site_name>";
							xml += "<site_domain>"+SiteDomainManager.getDefaultSiteDomainBySiteID(rcf.getSite_id())+SiteManager.getSitePort()+"</site_domain>";
							xml += "<category_list>";							
							for(ReceiveCatConf rcc : l)
							{								
									xml += "<category>";
									xml += "<category_id>"+rcc.getCat_id()+"</category_id>";
									xml += "<category_name><![CDATA["+CategoryManager.getCategoryBeanCatID(rcc.getCat_id(), rcf.getSite_id()).getCat_cname()+"]]></category_name>";
									xml += "</category>";						
							}
							xml += "</category_list>";
							xml += "</site>";							
						}
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			xml += "</site_list>";
		}
		xml += "</cicro>";
		return xml;
	}
	
	/**
     * 得到该服务器下的接收站树
     * @param String type 不为空的话，给出统计的页面
     * @return String
     * */
	public static String getRecieveSiteJSONTree(String type)
	{
		String json = "";
		json = "[{\"id\":0,\"text\":\"接收站点\",\"attributes\":{\"url\":\"\"},\"children\":[";
		List<ReceiveConfigBean> l = getReceiveConfigList();
		if(l != null && l.size() > 0)
		{
			int i=1;
			for(ReceiveConfigBean rcf : l)
			{
				if(i > 1)
					json += ",";
				if(type != null && !"".equals(type))
					json += "{\"id\":"+i+",\"text\":\""+SiteManager.getSiteBeanBySiteID(rcf.getSite_id()).getSite_name()+"\",\"attributes\":{\"url\":\""+receive_count_page+"?type="+type+"&site_id="+rcf.getSite_id()+"\"}}";
				else
					json += "{\"id\":"+i+",\"text\":\""+SiteManager.getSiteBeanBySiteID(rcf.getSite_id()).getSite_name()+"\",\"attributes\":{\"url\":\""+receive_info_page+"?site_id="+rcf.getSite_id()+"\"}}";
				i++;
			}
		}
		json += "]}]";
		return json;
	}
	
	public static void main(String[] args)
	{
		
	}
}
