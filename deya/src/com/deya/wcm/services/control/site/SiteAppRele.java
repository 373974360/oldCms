package com.deya.wcm.services.control.site;

import java.util.ArrayList;
import java.util.List;
import com.deya.wcm.bean.control.SiteAppBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.control.SiteConfigDAO;

/**
 *  站点与应用的关联管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 站点与应用的关联管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SiteAppRele implements ISyncCatch{
	private static List<SiteAppBean> site_app_list = new ArrayList<SiteAppBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		site_app_list.clear();
		site_app_list = SiteConfigDAO.getSiteAppReleList();
	}
	
	public static void reloadSiteAppRele()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.control.site.SiteAppRele");
	}
	
	public static SiteAppBean getSiteAppBean(String app_id)
	{
		if(site_app_list != null && site_app_list.size() > 0)
		 {
			 for(int i=0;i<site_app_list.size();i++)
			 {
				 if(app_id.equals(site_app_list.get(i).getApp_id()))
				 {
					 return site_app_list.get(i);
				 }
			 }
		 }
		return null;
	}
	
	/**
	 * 根据应用ID得到站点ID
	 * @param String app_id
	 * @return String site_id
	 */
	 public static String getSiteIDByAppID(String app_id)
	 {
		 String site_id = "";
		 if(site_app_list != null && site_app_list.size() > 0)
		 {
			 for(int i=0;i<site_app_list.size();i++)
			 {
				 if(app_id.equals(site_app_list.get(i).getApp_id()))
				 {
					 return site_app_list.get(i).getSite_id();
				 }
			 }
		 }
		 return site_id;
	 }
	 
	 /**
	 * 根据站点ID得到应用ID
	 * @param String site_id
	 * @return String app_id
	 */
	 public static String getSiteReleAppIDS(String site_id)
	 {
		 String app_id = "";
		 if(site_app_list != null && site_app_list.size() > 0)
		 {
			 for(int i=0;i<site_app_list.size();i++)
			 {
				 if(site_id.equals(site_app_list.get(i).getSite_id()))
				 {
					 app_id += "," + site_app_list.get(i).getApp_id();
				 }
			 }
			 if(app_id.length() > 0)
				 app_id = app_id.substring(1);
		 }
		 return app_id;
	 }
	 
	 /**
     * 插入站点与应用的关联关系
     * @param 
     * @return List
     * */
	public static boolean insertSiteReleApp(String site_id,String app_id)
	{
		if(SiteConfigDAO.insertSiteReleApp(site_id, app_id))
		{
			reloadSiteAppRele();
			return true;
		}else
			return false;
	}
	
	/**
     * 插入站点与应用的关联关系
     * @param 
     * @return List
     * */
	public static boolean insertSiteReleApp(SiteAppBean sab)
	{
		if(SiteConfigDAO.insertSiteReleApp(sab))
		{
			reloadSiteAppRele();
			return true;
		}else
			return false;
	}
	 
	 public static void main(String args[])
	 {
		 String ss = "/img.site.com:9126/ABD01/201109/201109061000052.png";
		 System.out.println(ss.replaceAll("(.*)([:][0-9]*)(.*?)", "$1"));
	 }
}
