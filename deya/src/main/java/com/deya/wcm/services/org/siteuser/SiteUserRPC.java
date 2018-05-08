package com.deya.wcm.services.org.siteuser;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.siteuser.SiteUserBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.services.Log.LogManager;

public class SiteUserRPC {
	
	/**
	 * 根据站点和应用ID得到人员总数
	 * @return	站点用户总数
	 */
	public static String getSiteUserListCount(Map<String,String> m )
	{
		return SiteUserManager.getSiteUserListCount(m);
	}
	
	/**
	 * 根据站点和应用ID得到人员列表(读库的)
	 * @return	站点用户信息列表
	 */
	public static List<SiteUserBean> getSiteUserListForDB(Map<String,String> m )
	{
		return SiteUserManager.getSiteUserListForDB(m);
	}

	/**
	 * 得到所有站点用户信息列表
	 * @return
	 */
	public static List<SiteUserBean> getSiteUserList(String site_id, String app_id)
	{
		return SiteUserManager.getSiteUserList(app_id, site_id);
	}
	
	public static List<UserBean> getUserBeanBySite(String app_id, String site_id)
	{
		return SiteUserManager.getUserBeanBySite(app_id, site_id);
	}
	
	/**
	 * 根据应用ID和站点ID得到用户map
	 * @param app_id	应用ID
	 * @param site_id	站点ID
	 * @return	List
	 */
	public static List<UserBean> getSiteUserListBySiteAppID(String app_id, String site_id)
	{
		return SiteUserManager.getSiteUserListBySiteAppID(app_id,site_id);
	}
	
	/**
	 * 得到站点用户的信息
	 * @param lt	站点用户对象列表
	 * @return	map key=user_id+"_"+app_id;
	 * 		value = list(包含user_name,dept_name,app_name)
	 */
	public static Map<String, List<String>> getSiteUserInfo(List<SiteUserBean> lt)
	{
		return SiteUserManager.getSiteUserInfo(lt);
	}
	
	/**
	 * 取得站点，应用下的用户ID
	 * @param site_id	站点ID
	 * @param app_id	应用ID
	 * @return	用户IDS，多个用户之间使用","隔开
	 */
	public static String getUserIDS(String site_id, String app_id)
	{
		return SiteUserManager.getUserIDS(site_id, app_id);
	}
	
	/**
	 * 关联用户
	 * @param insert_user_ids	需要添加的用户IDS，多个用户之间使用","分隔
	 * @param delete_user_ids 需要删除的用户IDS
	 * @param site_id	站点ID
	 * @param app_id	应用ID
	 * @param request	
	 * @return	true 成功| false 失败
	 */
	public static boolean linkSiteUser(String insert_user_ids,String delete_user_ids, String site_id, String app_id, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SiteUserManager.linkSiteUser(insert_user_ids,delete_user_ids, site_id, app_id, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 添加用户
	 * @param user_ids	需要添加的用户IDS，多个用户之间使用","分隔
	 * @param site_id	站点ID
	 * @param app_id	应用ID
	 * @param stl	
	 * @return	true 成功| false 失败
	 */
	public static boolean addSiteUser(String insert_user_ids,String site_id, String app_id, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SiteUserManager.addSiteUser(insert_user_ids,site_id, app_id, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除站点用户信息
	 * @param user_id	用户ID
	 * @param site_id	站点ID
	 * @param app_id	应用ID
	 * @param stl	
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteSiteUser(String user_ids, String site_id, String app_id, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SiteUserManager.deleteSiteUser(user_ids, site_id, app_id, stl);
		}
		else
		{
			return false;
		}
	}
	
}
