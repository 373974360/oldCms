package com.deya.wcm.services.control.site;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.control.SiteAppBean;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.server.LicenseCheck;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.control.domain.SiteDomainManager;

/**
 *  站点管理逻辑处理类 js调用.
 * <p>Title: CicroDate</p>
 * <p>Description: 站点管理逻辑处理类 js调用</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class SiteRPC 
{		
	
	private static String site_port = JconfigUtilContainer.bashConfig().getProperty("port", "", "site_port");
	
	public static String isCreateSite()
	{
		int s_n = LicenseCheck.LICENSE_SITE_NUM - 1;
		if (!LicenseCheck.checkSiteCount(s_n))
		{
			return "true";
		}
		return LicenseCheck.LICENSE_SITE_NUM +"";
	}
	
	/**
	 * 根据站点ID得到它的默认域名名称()
	 * 
	 * @param String site_id
	 * @return String
	 */
	public static String getDefaultSiteDomainBySiteID(String site_id)
	{
			if(site_port != null && !"".equals(site_port.trim()))
			{
				return "http://"+SiteDomainManager.getDefaultSiteDomainBySiteID(site_id)+":"+site_port;
			}else{
				return "http://"+SiteDomainManager.getDefaultSiteDomainBySiteID(site_id);
			}
	}
		
	//根据站点ID得到主域名
	public static String getSiteDomain(String site_id)
	{
		return "http://"+SiteDomainManager.getSiteDomainBySiteID(site_id);
	}
	
	/**
	 * 根据应用ID得到站点ID
	 * @param String app_id
	 * @return String site_id
	 */
	 public static String getSiteIDByAppID(String app_id)
	 {
		 return SiteAppRele.getSiteIDByAppID(app_id);
	 }
	 
	 public static SiteAppBean getSiteAppBean(String app_id)
	{
		 return SiteAppRele.getSiteAppBean(app_id);
	}
	 
	 /**
     * 插入站点与应用的关联关系
     * @param 
     * @return List
     * */
	public static boolean insertSiteReleApp(String site_id,String app_id)
	{
		return SiteAppRele.insertSiteReleApp(site_id, app_id);
	}
	
	/**
     * 插入站点与应用的关联关系
     * @param 
     * @return List
     * */
	public static boolean insertSiteReleApp(SiteAppBean sab)
	{
		return SiteAppRele.insertSiteReleApp(sab);
	}
	
	/**
	 * 根据节点ID得到该节点下的站点列表信息(得到子节点列表)
	 * @param String id
	 * @return List
	 */
	public static List<SiteBean> getSiteChildListByID(String site_id)
	{
		return SiteManager.getSiteChildListByID(site_id);
	}
	
	/**
     * 得到所有站点信息,并组织为json数据
     * @param
     * @return String
     * */
	public static String getSiteTreeJsonStr()
	{	
		return SiteManager.getSiteTreeJsonStr();
	}
	
	/**
	 * 根据节点ID得到该节点下排序的值
	 * @param String id
	 * @return int
	 */
	public static int getSiteSortByID(String site_id){
		return SiteManager.getSiteSortByID(site_id);
	}
	
	/**
	 * 判断site_id是否存在
	 * @param String site_id
	 * @return List
	 */
	public static boolean siteIDISExist(String site_id){
		return SiteManager.siteIDISExist(site_id);
	}
	
	/**
	 * 添加站点操作
	 * @param SiteBean
	 * @return boolean
	 */
	public static boolean insertSite(SiteBean sb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{ 
			if(SiteOperationFactory.addSite(sb, stl)){
				return true;
			}else
				return false;
		}else{
			return false;
		}
	}
	
	/**
     * 删除站点(只能一个一个删除)
     * @param String site_id
     * @param 
     * @param SettingLogsBean stl
     * @return boolean
     * */
     public static boolean deleteSite(String site_id,HttpServletRequest request)
 	{
 		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
 		if(stl != null)
 		{ 
 			if(SiteOperationFactory.deleteSite(site_id, stl)){
				return true;
			}else
				return false;
 		}else{
 			return false;
 		}
 	}
     
     
 	/**
 	 * 站点排序操作
 	 * 
 	 * @param String site_ids
 	 * @return List
 	 */
 	public static boolean saveSiteSort(String site_ids,HttpServletRequest request)
 	{
 		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
 		if(stl != null)
 		{ 
 			if(SiteManager.saveSiteSort(site_ids, stl)){
				return true;
			}else
				return false;
 		}else{
 			return false;
 		}
 	}
 	
	/**
     * 修改站点状态信息
     * @param SiteBean sb
     * @param 
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateSiteStatus(String site_id,int site_status,HttpServletRequest request)
 	{
 		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
 		if(stl != null) 
 		{ 
 			if(SiteOperationFactory.updateSiteStatus(site_id, site_status, stl)){
				return true;
			}else
				return false;
 		}else{
 			return false;
 		}
 	}
	
	/**
	 * 根据站点ID获取站点对象
	 * @param String site_id
	 * @return List
	 */
	public static SiteBean getSiteBeanBySiteID(String site_id)
	{   
		return SiteManager.getSiteBeanBySiteID(site_id);
	}
	
	/**
	 * 修改站点操作 
	 * @param SiteBean
	 * @return boolean
	 */
	public static boolean updateSite(SiteBean sb,HttpServletRequest request)
 	{
 		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
 		if(stl != null) 
 		{ 
 			System.out.println("SiteRPC		updateSite		begin	1111111			");
 			if(SiteOperationFactory.updateSite(sb, stl)){
				return true; 
			}else
				return false;
 		}else{
 			return false;
 		}
 	}
	
	
	/**
	 * 添加站点/节点管理员，向站群管理角色中添加人员关联
	 * @param String app_id
	 * @param String site_id
	 * @param String insert_user_ids　需要添加的用户
	 * @param String delete_user_ids 需要删除的用户
	 * @param HttpServletRequest request
	 * @return boolean
	 */
	public static boolean insertSiteUserManager(String app_id,String site_id,String insert_user_ids,String delete_user_ids,HttpServletRequest request)
 	{
 		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
 		if(stl != null) 
 		{ 
 			if(SiteManager.insertSiteUserManager(app_id,site_id, insert_user_ids,delete_user_ids, stl)){
				return true; 
			}else
				return false;
 		}else{
 			return false;
 		}
 	}
	
	/**
     * 根据角色ID，应用ID，站点ID得到用户ID字符串 
     * @param String app_id 
     * @param String site_id 
     * @return String
     * */
	public static String getUsersBySiteId(String app_id,String site_id)
	{
		return SiteManager.getUsersBySiteId(app_id,site_id);
	}
	
	/**
	 * 获取所有站点列表
	 * @param 
	 * @return List
	 */
	public static List<SiteBean> getSiteList()
	{
		return SiteManager.getSiteList();
	}
	
	/**
	 * 获取站点访问量
	 * @param 
	 * @return int
	 */
	public static int getSiteHits(String hit_type,HttpServletRequest request)
	{
		return SiteVisitCountManager.getHit(hit_type, request);
	}
	
	/**
	 * 添加计数器
	 * @param 
	 * @return int
	 */
	public static void addSiteHits(HttpServletRequest request)
	{
		SiteVisitCountManager.addSiteHits(request);
	}
	
	/**
	 * 根据站点修改步长
	 * @param String site_id
	 * @param int step
	 * @return 
	 */
	public static boolean updateStep(String site_id,int step)
	{
		return SiteVisitCountManager.updateStep(site_id, step);
	}
	
	/**
	 * 根据站点修改总点击次数
	 * @param String hit_type
	 * @param HttpServletRequest request
	 * @return int
	 */
	public static boolean updateHitForSite(String site_id,int count)
	{
		return SiteOperationFactory.updateHitForSite(site_id, count);
	}
}
