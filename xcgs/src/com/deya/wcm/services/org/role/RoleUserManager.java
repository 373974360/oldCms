package com.deya.wcm.services.org.role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.app.AppBean;
import com.deya.wcm.bean.org.role.RoleBean;
import com.deya.wcm.bean.org.role.RoleUserBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.org.role.RoleUserDAO;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.org.app.AppManager;
import com.deya.wcm.services.org.group.GroupManager;
import com.deya.wcm.services.org.user.UserLogin;
import com.deya.wcm.services.org.user.UserManager;

/**
 *  角色与用户,用户组关联逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 角色与用户关联逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class RoleUserManager implements ISyncCatch{
	private static TreeMap<String,RoleUserBean> role_user_map = new TreeMap<String, RoleUserBean>();
	
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
		List<RoleUserBean> role_user_list = RoleUserDAO.getAllRoleUserList();
		//System.out.println("RoleRelaManager reloadRoleUser+++++++++++++++++=="+role_user_list);
		role_user_map.clear();
		if (role_user_list != null && role_user_list.size() > 0) {
			for (RoleUserBean rub : role_user_list) {
				role_user_map.put(rub.getUser_role_id() + "", rub);				
			}
		}
	}
	
	/**
	 * 初始加载角色与用户关联信息
	 * 
	 * @param
	 * @return
	 */	
	public static void reloadRoleUser()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.role.RoleUserManager");
	}
	
	/**
     * 根据用户ID得到所有的角色,判断角色是否是某个应用系统的管理员
     * @param String user_id 
     * @return boolean
     * */
	public static boolean isAppSuperManager(String user_id,String app_id)
	{
		String admin_role_id = JconfigUtilContainer.systemRole().getProperty(app_id, "", "role_id");
		List<RoleBean> role_l = getAllUserRoleList(user_id);
		if(role_l != null && role_l.size() > 0)
		{
			for(RoleBean rb : role_l)
			{
				if(admin_role_id.equals(rb.getRole_id()+""))
					return true;
			}
			return false;
		}else
			return false;
	}
	
	/**
     * 根据用户ID得到所有的角色,判断角色是否是某个站点的管理员
     * @param String user_id 
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean isSiteManager(String user_id,String app_id,String site_id)
	{
		String admin_role_id = JconfigUtilContainer.systemRole().getProperty(app_id, "", "role_id");
			
		Iterator iter = role_user_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUserBean rub = (RoleUserBean) entry.getValue();
			//ggfw是个特殊的站点ID，用于服务的，没有实体数据	app_id加上system判断是因为它有可能是在数据模型管理中关联的用户，那这个角色就可能是system的		
			if(admin_role_id.equals(rub.getRole_id()) && user_id.equals(rub.getUser_id()) && (app_id.equals(rub.getApp_id()) || "system".equals(rub.getApp_id())) && (("ggfw".equals(site_id) && "ggfw".equals(app_id)) || "".equals(site_id) || site_id.equals(rub.getSite_id())))
			{
				return true;	
			}
		}
		return false;
	}
	
	
	/**
     * 根据用户ID得到所有的角色集合包括该用户所在的用户组(用于登录时查到此用户所有角色)
     * @param String user_id 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<RoleBean> getAllUserRoleList(String user_id)
	{
		Set<RoleBean> role_s = new HashSet<RoleBean>();
		Iterator iter = role_user_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUserBean rub = (RoleUserBean) entry.getValue();
			
			if(user_id.equals(rub.getUser_id()))
			{
				RoleBean rb = RoleManager.getRoleBeanByRoleID(rub.getRole_id());
				if(rb != null)
					role_s.add(rb);					
			}
		}
//		加入所有用户组的角色
		String user_group_ids = GroupManager.getGroupIDSByUserID(user_id);
		role_s.addAll(RoleUGroupManager.getRoleSetByUGroupID(user_group_ids));
		
		if(role_s != null && role_s.size() > 0)
		{
			List<RoleBean> role_list = new ArrayList<RoleBean>();
			role_list.addAll(role_s);			
			return role_list;
		}else
			return null;
	}
	
	/**
     * 根据用户ID得到所有的角色ID包括该用户所在的用户组(用于登录时查到此用户所有角色)
     * @param String user_id 
     * @return List
     * */
	public static String getAllUserRoleIDS(String user_id)
	{
		String s = "";
		List<RoleBean> l = getAllUserRoleList(user_id);
		if( l != null && l.size() > 0)
		{
			for(RoleBean ab : l)
			{
				s += ","+ab.getRole_id();
			}
			s = s.substring(1);
		}
		return s;
	}
	
	/**
     * 根据用户ID,应用系统ID,站点ID 得到角色集合包括该用户所在的用户组(用于登录时查到此用户所有角色)
     * @param String user_id 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<RoleBean> getRoleListByUserAppSite(String user_id,String app_id,String site_id)
	{
		List<RoleBean> role_list = new ArrayList<RoleBean>();
		//首先判断此人是否是超级管理员或站点,节点管理员
		if(("cms".equals(app_id) || "zwgk".equals(app_id)) && UserLogin.isSiteManager(user_id,app_id,site_id))
		{
			//如果是，给出此站点或节点管理员角色
			role_list.add(RoleManager.getRoleBeanByRoleID(JconfigUtilContainer.systemRole().getProperty(app_id, "", "role_id")));
		}
		else
		{
			Set<RoleBean> role_s = new HashSet<RoleBean>();
			role_s.addAll(getRoleListByUserAPP(user_id,app_id,site_id));
			
			//加入所有用户组的角色
			String user_group_ids = GroupManager.getGroupIDSByUserID(user_id);
			role_s.addAll(RoleUGroupManager.getRoleIDSByUGroupAPP(user_group_ids,app_id,site_id));
			if(role_s != null && role_s.size() > 0)
			{				
				role_list.addAll(role_s);
			}else
				return null;
		}
		return role_list;
		
	}
	
	/**
     * 根据用户ID得到所有的应用系统列表包括该用户所在的用户组(用于登录时查到此用户所有应用系统)
     * @param String user_id 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<AppBean> getAllUserAppList(String user_id)
	{
		Set<String> app_s = new HashSet<String>();
		
		Iterator iter = role_user_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUserBean rub = (RoleUserBean) entry.getValue();
			
			if(user_id.equals(rub.getUser_id()))
			{
				app_s.add(rub.getApp_id());
			}
		}
		String user_group_ids = GroupManager.getGroupIDSByUserID(user_id);
		app_s.addAll(RoleUGroupManager.getGroupAppIDS(user_group_ids));		
		
		if(app_s != null && app_s.size() > 0)
		{
			return AppManager.getAppListByIDS(app_s.toString().substring(1,app_s.toString().length()-1).replaceAll(" ", ""));
		}else
			return null;
	}
	
	
	
	/**
     * 根据用户ID得到所有的站点/节点ID包括该用户所在的用户组(用于登录时查到此用户所有应用系统)
     * @param String user_id 
     * @param String app_id
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<String> getAllUserSiteList(String user_id,String app_id)
	{
		Set<String> site_s = new HashSet<String>();
		
		Iterator iter = role_user_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUserBean rub = (RoleUserBean) entry.getValue();
			
			if(user_id.equals(rub.getUser_id()) && app_id.equals(rub.getApp_id()) && !"".equals(rub.getSite_id()) && !"null".equals(rub.getSite_id().toLowerCase()))
			{
				site_s.add(rub.getSite_id());
			}
		}
		String user_group_ids = GroupManager.getGroupIDSByUserID(user_id);
		site_s.addAll(RoleUGroupManager.getGroupSiteIDS(user_group_ids));		
		
		if(site_s != null && site_s.size() > 0)
		{
			List<String> site_list = new ArrayList<String>();
			site_list.addAll(site_s);
			return site_list;
		}else
			return null;
	}
	
	/**
     * 根据用户ID得到角色集合,去重复ID并用treemap排序
     * @param String user_id 
     * @return Map
     * */
	@SuppressWarnings("unchecked")
	public static Map<Integer, RoleBean> getRoleMapByUserID(String user_id)
	{
		TreeMap<Integer,RoleBean> role_map = new TreeMap<Integer, RoleBean>();
		Iterator iter = role_user_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUserBean rub = (RoleUserBean) entry.getValue();
			
			if(user_id.equals(rub.getUser_id()))
			{
				RoleBean rb = RoleManager.getRoleBeanByRoleID(rub.getRole_id());
				if(rb != null)
					role_map.put(Integer.parseInt(rub.getRole_id()),rb );					
			}
		}
		return role_map;
	}
	
	/**
     * 根据用户ID得到角色列表,去掉了重复的角色ID(在综合平台中查询用户角色)
     * @param String user_id 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<RoleBean> getRoleListByUserID(String user_id)
	{
		List<RoleBean> role_list = new ArrayList<RoleBean>();
		//从集合中取，用于排去重复的角色
		Iterator iter = getRoleMapByUserID(user_id).entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			role_list.add((RoleBean) entry.getValue());	
			
		}
		return role_list;
	}
	
	/**
     * 根据用户ID得到角色ID,去掉了重复的角色列表(在综合平台中查询用户角色)
     * @param String user_id 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<RoleBean> getRoleIDSByUserID(String user_id)
	{
		List<RoleBean> role_list = new ArrayList<RoleBean>();
		Iterator iter = role_user_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUserBean rub = (RoleUserBean) entry.getValue();
			
			if(user_id.equals(rub.getUser_id()))
			{
				role_list.add(RoleManager.getRoleBeanByRoleID(rub.getRole_id()));	
			}
		}
		
		return role_list;
	}
	
	/**
     * 根据用户ID，应用系统ID，站点ID得到角色列表(用于应用系统下的用户管理)
     * @param String user_id 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<RoleBean> getRoleListByUserAPP(String user_id,String app_id,String site_id)
	{
		List<RoleBean> role_list = new ArrayList<RoleBean>();
		Iterator iter = role_user_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUserBean rub = (RoleUserBean) entry.getValue();
			
			if(user_id.equals(rub.getUser_id()) && (app_id.equals(rub.getApp_id()) || "system".equals(rub.getApp_id())) && ("".equals(site_id) || site_id.equals(rub.getSite_id())))
			{
				RoleBean r = RoleManager.getRoleBeanByRoleID(rub.getRole_id());
				if(r != null)
					role_list.add(r);	
			}
		}
		return role_list;
	}
	
	/**
     * 根据用户ID，应用系统ID，站点ID得到角色ID(用于应用系统下的用户管理)
     * @param String user_id 
     * @return String
     * */
	@SuppressWarnings("unchecked")
	public static String getRoleIDSByUserAPP(String user_id,String app_id,String site_id)
	{
		String role_ids = "";
		Iterator iter = role_user_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUserBean rub = (RoleUserBean) entry.getValue();
			
			if(user_id.equals(rub.getUser_id()) && app_id.equals(rub.getApp_id()) && ("".equals(site_id) || site_id.equals(rub.getSite_id())))
			{
				role_ids += "," + rub.getRole_id();	
			}
		}
		if(role_ids != null && !"".equals(role_ids))
			role_ids = role_ids.substring(1);
		return role_ids;
	}
	
	/**
     * 根据角色ID，应用ID，站点ID得到用户列表
     * @param String role_id 
     * @param String app_id 
     * @param String site_id 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<UserBean> getUserListByRole(String role_id,String app_id,String site_id)
	{
		List<UserBean> user_list = new ArrayList<UserBean>();

		Iterator iter = role_user_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUserBean rub = (RoleUserBean) entry.getValue();
			
			if(role_id.equals(rub.getRole_id()) && app_id.equals(rub.getApp_id()) && ("".equals(site_id) || site_id.equals(rub.getSite_id())))
			{
					user_list.add(UserManager.getUserBeanByID(rub.getUser_id()));	
			}
		}
		return user_list;
	}
	
	/**
     * 根据角色ID，应用ID，站点ID得到用户ID字符串
     * @param String role_id 
     * @param String app_id 
     * @param String site_id 
     * @return String
     * */
	@SuppressWarnings("unchecked")
	public static String getUsersByRole(String role_id,String app_id,String site_id)
	{
		String user_ids = "";

		Iterator iter = role_user_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUserBean rub = (RoleUserBean) entry.getValue();
			
			if(role_id.equals(rub.getRole_id()) && app_id.equals(rub.getApp_id()) && ("".equals(site_id) || site_id.equals(rub.getSite_id())))
			{
				user_ids += ","+rub.getUser_id();					
			}
		}
		if(user_ids != null && !"".equals(user_ids))
			user_ids = user_ids.substring(1);
		return user_ids;
	}
	
	/**
     * 插入角色与用户关联信息(用于从角色管理页面添加人，１个角色对应多个人)
     * @param RoleUserBean rab
     * @param String delete_user_ids 需要删除的用户
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRoleUserByRole(RoleUserBean rub,String delete_user_ids,SettingLogsBean stl)
	{	
		if(insertRoloUserHandl(rub,stl))
		{
			//删除该角色下对应的人员	
			RoleUserDAO.deleteRoleUserByUserAppSite(rub.getRole_id()+"",delete_user_ids,rub.getApp_id(),rub.getSite_id());
			reloadRoleUser();
			return 	true;
		}else
			return false;
	}
		
	/**
     * 插入角色与用户关联信息
     * @param RoleUserBean rub 
     * @return boolean
     * */
	public static boolean insertRoloUserHandl(RoleUserBean rub,SettingLogsBean stl)
	{
		try{
			if(rub.getUser_id() != null)
			{
				if(rub.getUser_id() != null && !"".equals(rub.getUser_id()))
				{
					String[] tempA = rub.getUser_id().split(",");				
					for(int i=0;i<tempA.length;i++)
					{
						if(tempA[i] != null && !"".equals(tempA[i]))
						{
							rub.setUser_id(tempA[i]);
							RoleUserDAO.insertRoleUser(rub, stl);
						}
					}
				}
				reloadRoleUser();
			}			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
		
	/**
     * 插入角色与用户关联信息(用于从用户管理页面添加角色，１个用户对应多个角色)
     * @param RoleUserBean rab
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRoleUserByUser(RoleUserBean rab,SettingLogsBean stl)
	{			
		//首先删除该用户下对应的角色
		if(RoleUserDAO.deleteRoleUserByUserAppSite("",rab.getUser_id(),rab.getApp_id(),rab.getSite_id()))
		{
			try{
				if(rab.getRole_id() != null)
				{
					String[] tempA = rab.getRole_id().split(",");
					for(int i=0;i<tempA.length;i++)
					{
						rab.setRole_id(tempA[i]);
						RoleUserDAO.insertRoleUser(rab, stl);
					}
				}
				reloadRoleUser();
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}			
		}else
			return false;
	}
	
	/**
     * 根据角色ID删除角色跟用户关联数据 (用于删除角色时删除关联表)
     * @param String role_id 
     * @return boolean
     * */
	public static boolean deleteRoleUserByRoleID(String role_id)
	{
		if(RoleUserDAO.deleteRoleUserByRoleID(role_id))
		{
			reloadRoleUser();
			return true;
		}else
			return false;
	}
	
	/**
     * 根据用户删除角色跟用户关联数据
     * @param String user_id 
     * @return boolean
     * */
	public static boolean deleteRoleUserByUser(String user_id)
	{		
		if(RoleUserDAO.deleteRoleUserByUser(user_id))
		{
			reloadRoleUser();
			return true;
		}else
			return false;
	}
	
		
	/**
     * 根据用户ID和角色ID删除角色跟用户关联数据(一般用于删除系统默认角色和用户的关联)
     * @param String user_ids 
     * @param String role_id 
     * @return boolean
     * */
	public static boolean deleteRoleUserByUserAndRoleID(String user_ids,String role_id)
	{
		if(RoleUserDAO.deleteRoleUserByUserAndRoleID(user_ids,role_id))
		{
			reloadRoleUser();
			return true;
		}else
			return false;
	}
	
	/**
     * 根据用户ID和角色ID,站点ID删除角色跟用户关联数据
     * @param String user_ids 
     * @param String role_id 
     * @param String site_id
     * @return boolean
     * */
	public static boolean deleteRoleUserByUserRoleSite(String user_ids,String app_id,String site_id)
	{
		if(RoleUserDAO.deleteRoleUserByUserAppSite("",user_ids,app_id,site_id))
		{
			reloadRoleUser();
			return true;
		}else
			return false;
	}
	
	public static void main(String args[])
	{
		//getAllUserRoleList("72");
		//List<UserBean> l = getUserListByRole("6","cms","xa");
		/*
		List<RoleBean> l = getRoleListByUserAppSite("132","appeal","");
		System.out.println(l.size());
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				System.out.println(l.get(i).getRole_name());
			}
		}*/
		System.out.println(getAllUserSiteList("72","zwgk"));
		
	}
	
	public static void insertRoleUserByRoleTest()
	{
		RoleUserBean rab = new RoleUserBean();
		rab.setApp_id("system");
		rab.setRole_id("1,2,3,4,5,6");
		rab.setUser_id("1");
		rab.setSite_id("zg");
		//insertRoleUserByRole(rab,new SettingLogsBean());
		insertRoleUserByUser(rab,new SettingLogsBean());
	}
}
