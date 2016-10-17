package com.deya.wcm.services.org.siteuser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.role.RoleBean;
import com.deya.wcm.bean.org.siteuser.SiteUserBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.org.siteuser.SiteUserDAO;
import com.deya.wcm.services.cms.category.CategoryReleManager;
import com.deya.wcm.services.org.dept.DeptManager;
import com.deya.wcm.services.org.role.RoleUserManager;
import com.deya.wcm.services.org.user.UserManager;

public class SiteUserManager implements ISyncCatch{
	
	private static List<SiteUserBean> siteUser_lt = new ArrayList<SiteUserBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		siteUser_lt.clear();
		siteUser_lt.addAll(SiteUserDAO.getAllSiteUserList());
	}
	
	public static void reloadSiteuser()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.siteuser.SiteUserManager");
	}
	
	/**
	 * 根据应用ID和站点ID得到用户map
	 * @param app_id	应用ID
	 * @param site_id	站点ID
	 * @return	List
	 */
	public static List<UserBean> getSiteUserListBySiteAppID(String app_id, String site_id)
	{
		List<UserBean> u_list = new ArrayList<UserBean>();
		List<SiteUserBean> lt = getSiteUserList(app_id,site_id);
		
		if(lt != null && lt.size() > 0)
		{
			for(int i=0;i<lt.size();i++)
			{
				if(app_id.equals(lt.get(i).getApp_id()) && (site_id.equals(lt.get(i).getSite_id()) || "".equals(site_id)))
				{
					UserBean u = UserManager.getUserBeanByID(lt.get(i).getUser_id());
					if(u != null)
						u_list.add(u);
				}					
			}
		}
		return u_list;
	}
	
	public static List<UserBean> getUserBeanBySite(String app_id, String site_id)
	{
		List<SiteUserBean> lt = getSiteUserList(app_id,site_id);
		List<UserBean> userB = new ArrayList<UserBean>();
		
		if(lt != null && lt.size() > 0)
		{
			for(int i=0;i<lt.size();i++)
			{
				userB.add(UserManager.getUserBeanByID(lt.get(i).getUser_id()));
			}			
		}
		return userB;
	}
	
	/**
	 * 根据站点和应用ID得到人员列表(读库的)
	 * @return	站点用户信息列表
	 */
	public static List<SiteUserBean> getSiteUserListForDB(Map<String,String> m )
	{
		List<SiteUserBean> lt = SiteUserDAO.getSiteUserListForDB(m);
		if(lt != null && lt.size() > 0)
		{
			for(SiteUserBean sub : lt)
			{
				List<RoleBean> role_l = RoleUserManager.getRoleListByUserAPP(sub.getUser_id(),sub.getApp_id(),sub.getSite_id());
				if(role_l != null && role_l.size() > 0)
				{				
					String role_names = "";
					for(int j=0;j<role_l.size();j++)
					{
						if(role_l.get(j) != null)
							role_names += ","+role_l.get(j).getRole_name();
					}
					if(role_names != null && !"".equals(role_names))
					{						
						role_names = role_names.substring(1);
					}					
					sub.setRole_names(role_names);
				}
				sub.setDept_name(DeptManager.getDeptBeanByID(sub.getDept_id()+"").getDept_name());				
			}
		}
		return lt;
	}
	
	/**
	 * 根据站点和应用ID得到人员总数
	 * @return	站点用户总数
	 */
	public static String getSiteUserListCount(Map<String,String> m )
	{
		return SiteUserDAO.getSiteUserListCount(m);
	}
	
	/**
	 * 得到站点用户列表
	 * @param app_id	应用ID
	 * @param site_id	站点ID
	 * @return	站点用户列表
	 */
	public static List<SiteUserBean> getSiteUserList(String app_id, String site_id)
	{
		List<SiteUserBean> lt = new ArrayList<SiteUserBean>();
		if(site_id == null || "".equals(site_id))
		{
			lt.addAll(siteUser_lt);
			return lt;
		}
		
		if(app_id == null || "".equals(app_id))
		{
			for(SiteUserBean ub : siteUser_lt)
			{
				if(site_id.equals(ub.getSite_id()))
				{
					lt.add(ub);
				}
			}
		}
		else 
		{
			for(SiteUserBean ub : siteUser_lt)
			{
				if(app_id.equals(ub.getApp_id()) && site_id.equals(ub.getSite_id()))
				{
					UserBean u = UserManager.getUserBeanByID(ub.getUser_id());
					if(u != null)
						lt.add(ub);
				}
			}
		}
		
		if(lt != null && lt.size() > 0)
		{
			for(int i=0;i<lt.size();i++)
			{
				List<RoleBean> role_l = RoleUserManager.getRoleListByUserAPP(lt.get(i).getUser_id(),lt.get(i).getApp_id(),lt.get(i).getSite_id());
				if(role_l != null && role_l.size() > 0)
				{				
					String role_names = "";
					for(int j=0;j<role_l.size();j++)
					{
						if(role_l.get(j) != null)
							role_names += ","+role_l.get(j).getRole_name();
					}
					if(role_names != null && !"".equals(role_names))
					{						
						role_names = role_names.substring(1);
					}					
					lt.get(i).setRole_names(role_names);
				}
			}
		}
		
		return lt;
	}
	
	/**
	 * 得到站点用户列表
	 * @param site_id	站点ID
	 * @return	站点用户列表
	 */
	public static List<SiteUserBean> getSiteUserList(String site_id)
	{
		String app_id = null;
		return getSiteUserList(app_id, site_id);
	}
	
	/**
	 * 得到所有站点用户列表
	 * @return	站点用户列表
	 */
	public static List<SiteUserBean> getSiteUserList()
	{
		List<SiteUserBean> lt = new ArrayList<SiteUserBean>();
		lt.addAll(siteUser_lt);
		return lt;
	}
	
	/**
	 * 取得站点用户信息对应名称
	 * @param ub	站点用户对象
	 * @return	map key=user_id+"_"+app_id;
	 * 			value = list(包含user_name,dept_name,app_name)
	 */
	public static Map<String, List<String>> getSiteUserInfo(SiteUserBean ub)
	{
		Map<String, List<String>> retMap = new HashMap<String, List<String>>();
		List<String> lt = new ArrayList<String>();
		try{
			String user_id = ub.getUser_id();
			String app_id = ub.getApp_id();
			
			String user_name = UserManager.getUserBeanByID(user_id).getUser_realname();
			String dept_id = UserManager.getUserBeanByID(user_id).getDept_id()+"";
			String dept_name = DeptManager.getDeptBeanByID(dept_id).getDept_name();
			lt.add(user_name);
			lt.add(dept_name);
			
			String key = user_id+"_"+app_id;
			retMap.put(key, lt);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return retMap;
	}
	
	/**
	 * 取得站点用户信息对应名称
	 * @param lt	站点用户对象列表
	 * @return	map key=user_id+"_"+app_id;
	 * 			value = list(包含user_name,dept_name,app_name)
	 */
	public static Map<String, List<String>> getSiteUserInfo(List<SiteUserBean> lt)
	{
		Map<String, List<String>> retMap = new HashMap<String, List<String>>();
		for(SiteUserBean ub : lt)
		{
			try{
				retMap.putAll(getSiteUserInfo(ub));
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}	
		return retMap;
	}
	
	/**
	 * 取得站点，应用下的用户ID
	 * @param site_id	站点ID
	 * @param app_id	应用ID
	 * @return	用户IDS，多个用户之间使用","隔开
	 */
	public static String getUserIDS(String site_id, String app_id)
	{
		String ret = "";
		 List<SiteUserBean> lt = getSiteUserList(app_id, site_id);
		 for(SiteUserBean ub : lt)
		 {
			 ret += "," + ub.getUser_id();
		 }
		 if(ret.startsWith(","))
		 {
			 ret.substring(1);
		 }
		 
		 return ret;
	}
	
	/**
	 * 插入站点用户信息
	 * @param ub	站点用户对象
	 * @param stl
	 * @return	
	 */
	public static boolean insertSiteUser(SiteUserBean ub, SettingLogsBean stl)
	{
		if(SiteUserDAO.insertSiteUser(ub, stl))
		{
			reloadSiteuser();
			return true;
		}
		return false;
	}
	
	/**
	 * 关联用户
	 * @param insert_user_ids	需要添加的用户IDS，多个用户之间使用","分隔
	 * @param delete_user_ids 需要删除的用户IDS
	 * @param site_id	站点ID
	 * @param app_id	应用ID
	 * @param stl	
	 * @return	true 成功| false 失败
	 */
	public static boolean linkSiteUser(String insert_user_ids,String delete_user_ids, String site_id, String app_id, SettingLogsBean stl)
	{
		SiteUserBean ub = new SiteUserBean();
		ub.setSite_id(site_id);
		ub.setApp_id(app_id);
		if(deleteSiteUser(delete_user_ids,site_id, app_id ,stl))
		{
			if(insert_user_ids == null || "".equals(insert_user_ids))
				return true;
			String ids[] = insert_user_ids.split(",");
			for(int i=0; i<ids.length; i++)
			{
				ub.setUser_id(ids[i]);
				if(!insertSiteUser(ub, stl))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 添加用户
	 * @param user_ids	需要添加的用户IDS，多个用户之间使用","分隔
	 * @param site_id	站点ID
	 * @param app_id	应用ID
	 * @param stl	
	 * @return	true 成功| false 失败
	 */
	public static boolean addSiteUser(String insert_user_ids,String site_id, String app_id, SettingLogsBean stl)
	{
		SiteUserBean ub = new SiteUserBean();
		ub.setSite_id(site_id);
		ub.setApp_id(app_id);
		Map<String,String> m = new HashMap<String,String>();
		m.put("site_id", site_id);
		m.put("app_id", app_id);
		
		if(insert_user_ids == null || "".equals(insert_user_ids))
			return true;
		String ids[] = insert_user_ids.split(",");
		for(int i=0; i<ids.length; i++)
		{
			m.put("user_id", ids[i]);
			if("0".equals(getSiteUserListCount(m)))
			{//判断没有此用户的话，添加
				ub.setUser_id(ids[i]);
				if(!insertSiteUser(ub, stl))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 删除站点用户信息
	 * @param site_id	站点ID
	 * @param app_id	应用ID
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteSiteUser(String site_id, String app_id, SettingLogsBean stl)
	{
		String user_id = null;
		return deleteSiteUser(user_id, site_id, app_id, stl);
	}
		
	/**
	 * 删除站点用户信息
	 * @param user_id	用户ID
	 * @param site_id	站点ID
	 * @param app_id	应用ID
	 * @param stl	
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteSiteUser(String user_ids, String site_id, String app_id, SettingLogsBean stl)
	{
		if(user_ids == null || "".equals(user_ids))
			return true;
		Map<String, String> map = new HashMap<String, String>();
		String userValue = "".equals(user_ids)? null : user_ids;
		map.put("user_ids", userValue);
		String siteValue = "".equals(site_id)? null : site_id;
		map.put("site_id", siteValue);
		String appValue = "".equals(app_id)? null : app_id;
		map.put("app_id", appValue);
		
		if(SiteUserDAO.deleteSiteUser(map, stl))
		{
			reloadSiteuser();
			RoleUserManager.deleteRoleUserByUserRoleSite(user_ids,app_id,site_id);
			CategoryReleManager.deleteCateReleByPrv(0,user_ids,site_id);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void main(String args[])
	{
		System.out.println(getUserBeanBySite("ggfw","ggfw"));
	}

}
