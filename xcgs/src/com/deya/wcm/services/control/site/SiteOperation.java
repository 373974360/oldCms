package com.deya.wcm.services.control.site;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.deya.util.FormatUtil;
import com.deya.util.JarManager;
import com.deya.util.io.FileOperation;
import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.control.SiteDomainBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.server.ApacheConfigFactory;
import com.deya.wcm.server.IApacheConfig;
import com.deya.wcm.server.IServerConfig;
import com.deya.wcm.server.ServerConfigFactory;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import com.deya.wcm.services.system.template.TemplateCategoryManager;

/**
 *  站点操作管理类
 * <p>Title: CicroDate</p>
 * <p>Description: 站点操作管理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SiteOperation {
		
	/**
	 * 添加站点操作
	 * @param SiteBean sb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean addSite(SiteBean sb,SettingLogsBean stl)
	{
		sb.setSite_domain(sb.getSite_domain().trim());
		String site_path = FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "hostRoot_path")+"/"+sb.getSite_domain());
		System.out.println("site_path----"+site_path);
		System.out.println("site_path----"+FormatUtil.formatPath(site_path+"/ROOT"));
		sb.setSite_path(FormatUtil.formatPath(site_path+"/ROOT"));
		//第一步:入库
		if(SiteManager.insertSite(sb, stl))
		{				
			//第二步:添加目录
			if(createDirectory(site_path))
			{
				Map<String, String> site_infos = new HashMap<String, String>();
				site_infos.put("site_id", sb.getSite_id());
				site_infos.put("site_domain", sb.getSite_domain());			  
				site_infos.put("site_path", sb.getSite_path());
				//第三步:tomcat中添加Host
				addSiteInfoToServer(site_infos);
				//第四步:apache中添加Host
				addSiteInfoToApache(site_infos,"_config");
				//第五步：克隆站点
				if(sb.getClone_site_id() != null && !"".equals(sb.getClone_site_id()))
					cloneSite(sb.getSite_id(),sb.getClone_site_id());
				//第六步：其它需要添加的
				addOtherConfig(sb.getSite_id());
				//第七步：更改crossdomain.xml文件
				CreateFlashConfigFile.CreateFlashFile();
				return true;
			}else
			{
				return false;
			}
		}
		else
		{
			System.out.println("site insert db error!");
			return false;
		}
	}
	
	/**
	 * 添加其它配置
	 * @param String site_id
	 * @return boolean
	 */
	public static boolean addOtherConfig(String site_id)
	{
		try{
			TemplateCategoryManager.addDefaultZTCategory(site_id);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("addOtherConfig error");
			return false;
		}
	}
	
	/**
	 * 修改站点操作
	 * @param SiteBean sb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSite(SiteBean sb,SettingLogsBean stl)
	{
		sb.setSite_domain(sb.getSite_domain().trim());
		//第一步:修改站点信息
		if(SiteManager.updateSite(sb, stl))
		{
			String old_domain = SiteDomainManager.getSiteDomainBySiteID(sb.getSite_id());
			//第二步:修改域名(判断域名是否修改过,如果没修改过,后面不执行)
			int domain_flag = SiteDomainManager.updateSiteDomainByName(sb.getSite_domain(), sb.getSite_id());
			
			//如果为1,表示修改了域名
			if(domain_flag == 1)
			{				
				Map<String, String> site_infos = new HashMap<String, String>();
				site_infos.put("old_site_domain", old_domain);
				site_infos.put("new_site_domain", sb.getSite_domain());
				//第三步:tomcat中添加Host
				updateSiteInfoToServer(site_infos);
				//第四步:apache中添加Host
				updateSiteInfoToApache(site_infos);
				//第五步：更改crossdomain.xml文件
				CreateFlashConfigFile.CreateFlashFile();
				return true;
			}
			if(domain_flag == -1)
			{
				return false;
			}
			return true;
		}else
			return false;
	}
	
	/**
	 * 暂停,恢复站点
	 * @param String site_id
	 * @param String site_status 站点类型,1 停止,0 恢复
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSiteStatus(String site_id,int site_status,SettingLogsBean stl)
	{
		if(site_id != null && !"".equals(site_id))
		{
			SiteBean sb = SiteManager.getSiteBeanBySiteID(site_id);
			if(sb != null)
			{
				if(SiteManager.updateSiteStatus(site_id, site_status, stl))
				{
					Map<String, String> site_infos = new HashMap<String, String>();
					site_infos.put("site_id", sb.getSite_id());						  
					site_infos.put("site_path", sb.getSite_path());	
					site_infos.put("site_domain", sb.getSite_domain());
					if(site_status == 1)
						return stopSiteInfoToApache(site_infos,"_config_stop");
					if(site_status == 0)
						return stopSiteInfoToApache(site_infos,"_config");											
				}
				else
					return false;
			}
			else
				return false;
		}
		return true;		
	}
	
	/**
	 * 删除站点
	 * @param String site_id
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteSite(String site_id,SettingLogsBean stl)
	{
		if(site_id != null && !"".equals(site_id))
		{
			SiteBean sb = SiteManager.getSiteBeanBySiteID(site_id);
			if(sb != null)
			{
				if(SiteManager.deleteSite(site_id, stl))
				{
					Map<String, String> site_infos = new HashMap<String, String>();
					site_infos.put("site_id", sb.getSite_id());
					site_infos.put("site_domain", sb.getSite_domain());			  
					site_infos.put("site_path", sb.getSite_path());
					delSiteInfoByServer(site_infos);
					delSiteByApache(site_infos);
					//删除域名 这个得放到后面来删,要不然删除不了apache里的东西
		    		 SiteDomainManager.deleteSiteDomainBySiteID(site_id, stl);
		    		//更改crossdomain.xml文件
					CreateFlashConfigFile.CreateFlashFile();
					return true;
				}
				else
					return false;
			}
			else
				return false;
		}
		return true;		
	}
	
	/**
	 * 添加站点多域名操作
	 * @param SiteDomainBean sdb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean addAlias(SiteDomainBean sdb,SettingLogsBean stl)
	{
		sdb.setSite_domain(sdb.getSite_domain().trim());
		//第一步:域名入库
		if(SiteDomainManager.insertSiteDomain(sdb, stl))
		{
			try{
				//第二步:tomcat中添加Alias
				Map<String, String> site_infos = new HashMap<String, String>();
				site_infos.put("site_id", sdb.getSite_id());
				site_infos.put("site_domain", sdb.getSite_domain());
				site_infos.put("site_path", SiteManager.getSiteBeanBySiteID(sdb.getSite_id()).getSite_path());
				addAliasToServer(site_infos);
				//第三步:apache中添加Alias
				addSiteInfoToApache(site_infos,"_config");
				//第五步：更改crossdomain.xml文件
				CreateFlashConfigFile.CreateFlashFile();
				return true;
			}catch(Exception e)
			{
				return false;
			}
		}else
			return false;
	}
	
	/**
	 * 修改站点多域名操作
	 * @param SiteDomainBean sdb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateAlias(SiteDomainBean sdb,SettingLogsBean stl)
	{
		sdb.setSite_domain(sdb.getSite_domain().trim());
		//第一步:域名入库
		//首先根据域名ID得到原始的对象
		SiteDomainBean sdb_old = SiteDomainManager.getSiteDomainBeanByID(sdb.getDomain_id()+"");
		//判断域名是否一致,如果一致,不修改
		if(!sdb.getSite_domain().equals(sdb_old.getSite_domain()))
		{
			if(SiteDomainManager.updateSiteDomain(sdb, stl))
			{
				Map<String, String> site_infos = new HashMap<String, String>();
				site_infos.put("old_site_domain", sdb_old.getSite_domain());
				site_infos.put("new_site_domain", sdb.getSite_domain());
				//第三步:tomcat中修改Host或Alias
				updateSiteInfoToServer(site_infos);
				//第四步:apache中修改Host
				updateSiteInfoToApache(site_infos);
				//第五步：更改crossdomain.xml文件
				CreateFlashConfigFile.CreateFlashFile();
				return true;
			}else
				return false;
		}else
			return true;
	}
	
	/**
	 * 删除站点多域名操作
	 * @param String domain_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteAlias(String domain_ids,SettingLogsBean stl)
	{
		if(domain_ids != null && !"".equals(domain_ids))
		{
			try{
				String[] tempA = domain_ids.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					SiteDomainBean sdb = SiteDomainManager.getSiteDomainBeanByID(tempA[i]);
					Map<String, String> site_infos = new HashMap<String, String>();
					site_infos.put("site_domain", sdb.getSite_domain());
					site_infos.put("site_id", sdb.getSite_id());
					
					if(SiteDomainManager.deleteSiteDomainByID(tempA[i], stl))
					{
						deleteAliasToServer(site_infos);
						delSiteByApache(site_infos);
					}
				}
				//第五步：更改crossdomain.xml文件
				CreateFlashConfigFile.CreateFlashFile();
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
			
		}
		return true;
	}
	
	/**
	 * 生成站点路径
	 * @param String site_path
	 * @return boolean
	 */
	public static boolean createDirectory(String site_path)
	{
		try
		{	
			File f = new File(site_path);
			if(!f.exists())
			{
				f.mkdir();
			}
			//解压资源文件到目录下
			String resource_file = FormatUtil.formatPath(JconfigUtilContainer.bashConfig().getProperty("path", "", "resource_file"));
			JarManager.decompress(resource_file,site_path);
			return true;
		}
		catch(Exception e)
		{
			System.out.println("create site directory error!");
			return false;
		}
	}	
	
	/**
	 * 在素材库服务器上添加站点
	 * @param String site_id
	 * @return boolean
	 */
	public static boolean addSiteInResourceServer(String site_id)
	{
		String site_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "resource_server");
		if(site_path != null && !"".equals(site_path))
		{
			try{
				site_path = FormatUtil.formatPath(site_path+"/"+site_id);
				File f = new File(site_path);
				if(!f.exists())
				{
					f.mkdirs();
				}
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}else
			return false;
	}
	
	/**
	 * 添加站点到应用服务器
	 * @param Map site_infos
	 * @return boolean
	 */
	public static boolean addSiteInfoToServer(Map<String, String> site_infos)
	{
		try{
			IServerConfig isconfig = ServerConfigFactory.getServerConfig();
			isconfig.addSite(site_infos);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;			
		}
	}
	
	/**
	 * 修改站点到应用服务器
	 * @param Map site_infos
	 * @return boolean
	 */
	public static boolean updateSiteInfoToServer(Map<String, String> site_infos)
	{
		try{
			IServerConfig isconfig = ServerConfigFactory.getServerConfig();
			isconfig.updateSite(site_infos);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;			
		}
	}
	
	/**
	 * 删除站点到应用服务器
	 * @param Map site_infos
	 * @return boolean
	 */
	public static boolean delSiteInfoByServer(Map<String, String> site_infos)
	{
		try{
			IServerConfig isconfig = ServerConfigFactory.getServerConfig();
			isconfig.delSite(site_infos);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;			
		}
	}
	
	/**
	 * 添加站点到apache
	 * @param Map site_infos
	 * @param String apacheConfig_name 添加站点的apache配置文件名称
	 * @return boolean
	 */
	public static boolean addSiteInfoToApache(Map<String, String> site_infos,String apacheConfig_name)
	{
		try{
			IApacheConfig iaconfig = ApacheConfigFactory.getApacheConfig();
			iaconfig.addSite(site_infos,apacheConfig_name);
			iaconfig.reset();
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;			
		}
	}
	
	/**
	 * 修改站点到apache
	 * @param Map site_infos
	 * @return boolean
	 */
	public static boolean updateSiteInfoToApache(Map<String, String> site_infos)
	{
		try{
			IApacheConfig iaconfig = ApacheConfigFactory.getApacheConfig();
			iaconfig.updateSite(site_infos);
			iaconfig.reset();
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;			
		}
	}
	
	/**
	 * 暂停一个站点到apache操作
	 * @param Map site_infos
	 * @param String apacheConfig_name 停止站点的apache配置文件名称
	 * @return boolean
	 */
	public static boolean stopSiteInfoToApache(Map<String, String> site_infos,String apacheConfig_name) 
	{
		try{
			IApacheConfig iaconfig = ApacheConfigFactory.getApacheConfig();
			//第一步：删除原站点配置
			iaconfig.delMultiVhost(site_infos.get("site_id"));
			//第二步：添加停止站点的配置
			iaconfig.addMultiVhost(site_infos,apacheConfig_name);
			iaconfig.reset();
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;			
		}
	}
	
	/**
	 * 删除一个站点到apache操作
	 * @param Map site_infos
	 * @return boolean
	 */
	public static boolean delSiteByApache(Map<String, String> site_infos)
	{
		try{
			IApacheConfig iaconfig = ApacheConfigFactory.getApacheConfig();
			iaconfig.delVhost(site_infos);
			iaconfig.reset();
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;			
		}
	}
	
	/**
	 * 添加域名别名到应用服务器
	 * @param Map site_infos
	 * @return boolean
	 */
	public static void addAliasToServer(Map<String, String> site_infos)
	{
		try{
			IServerConfig isconfig = ServerConfigFactory.getServerConfig();
			isconfig.addAlias(site_infos);			
		}catch(Exception e)
		{
			e.printStackTrace();
						
		}
	}
	
	/**
	 * 删除域名别名到应用服务器
	 * @param Map site_infos
	 * @return boolean
	 */
	public static void deleteAliasToServer(Map<String, String> site_infos)
	{
		try{
			IServerConfig isconfig = ServerConfigFactory.getServerConfig();			
			isconfig.delAlias(site_infos);
		}catch(Exception e)
		{
			e.printStackTrace();
						
		}
	}
	
	/**
	 * 将root目录下的images,js,styles目录打包，读取，返回byte
	 * @param String s_site_id
	 * @return byte
	 */
	public static byte[] copySiteResource(String s_site_id)
	{
		SiteBean s_sb = SiteManager.getSiteBeanBySiteID(s_site_id);
		String copy_dir = FormatUtil.formatPath(s_sb.getSite_path()+"/images")+","+FormatUtil.formatPath(s_sb.getSite_path()+"/js")+","+FormatUtil.formatPath(s_sb.getSite_path()+"/styles");
		JarManager.compress("resource.jar",copy_dir,FormatUtil.formatPath(s_sb.getSite_path()+"/ROOT"),s_sb.getSite_path());
		try{
			byte[] b = FileOperation.readFileToBytes(FormatUtil.formatPath(s_sb.getSite_path()+"/resource.jar"));
			File f = new File(FormatUtil.formatPath(FormatUtil.formatPath(s_sb.getSite_path()+"/resource.jar")));
			f.delete();
			return b;
		}catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 克隆站点
	 * @param String site_id 新站点ID
	 * @param String s_site_id 克隆的目标站点ID
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static void cloneSite(String site_id,String s_site_id)
	{
		JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("startListener");
		String[] class_arr = bc.getPropertyNamesByCategory("clonesiteclass");
		if(class_arr != null && class_arr.length > 0)
    	{			 
			for(int i=class_arr.length;i > 0;i--)
    		{
				String class_path = bc.getProperty(class_arr[i-1], "", "clonesiteclass");
				try {
					Class c = Class.forName(class_path);
					ICloneSite ics = (ICloneSite)c.newInstance();
					ics.cloneSite(site_id, s_site_id);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
	}
	
	public static void main(String[] args)
	{
		JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("startListener");
		String[] class_arr = bc.getPropertyNamesByCategory("clonesiteclass");
		if(class_arr != null && class_arr.length > 0)
    	{			 
			for(int i=class_arr.length;i > 0;i--)
    		{
				String class_path = bc.getProperty(class_arr[i-1], "", "clonesiteclass");
				System.out.println(class_arr[i-1]+"   "+bc.getProperty(class_arr[i-1], "", "clonesiteclass"));
    		}
    	}
		/*
		try {
			Class c = Class.forName("com.deya.wcm.services.page.PageTimerImpl");
			ICloneSite ics = (ICloneSite)c.newInstance();
			ics.cloneSite("111", "222");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
