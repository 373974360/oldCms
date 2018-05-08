package com.deya.wcm.services.org.role;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.role.RoleBean;
import com.deya.wcm.bean.org.role.RoleUGroupBean;
import com.deya.wcm.bean.org.role.RoleUserBean;
import com.deya.wcm.services.Log.LogManager;

public class RoleRPC {
		
	/**
     * 根据用户ID，应用系统ID，站点ID得到角色ID(用于应用系统下的用户管理)
     * @param String user_id 
     * @return String
     * */	
	public static String getRoleIDSByUserAPP(String user_id,String app_id,String site_id)
	{
		return RoleUserManager.getRoleIDSByUserAPP(user_id,app_id,site_id);
	}
	
	
	/**
	 * 得到角色MAP
	 * 
	 * @param
	 * @return
	 */
	public static Map<String,RoleBean> getRoleMap(){
		return RoleManager.getRoleMap();
	}
	
	/**
	 * 根据应用系统ID和站点ID得到角色列表(从数据库中查询，用于在页面上列出角色列表)
	 * 
	 * @param Map<String,String> m (app_id,site_id,start_num,page_size,con_name,con_value,sort_name,sort_type)
	 * @return List
	 */
	public static List<RoleBean> getRoleListForDB(Map<String,String> m)
	{
		return RoleManager.getRoleListForDB(m);
	}
	
	/**
	 * 根据应用系统ID和站点ID得到角色总数(用于在页面上列出角色列表总数)
	 * 
	 * @param String app_id
	 * @param String site_id
	 * @return String
	 */
	public static String getRoleCount(Map<String,String> m)
	{
		return RoleManager.getRoleCount(m);
	}	
	
	/**
	 * 根据应用系统ID和站点ID得到角色总数(用于在页面上列出角色列表总数)
	 * 
	 * @param String app_id
	 * @param String site_id
	 * @return String
	 */	
	public static String getRoleCountForDB(Map<String,String> m)
	{
		return RoleManager.getRoleCountForDB(m);
	}
	
	/**
	 * 根据角色ID得到角色对象
	 * 
	 * @param String role_id
	 *            
	 * @return RoleBean
	 */
	public static RoleBean getRoleBeanByID(String role_id)
	{
		return RoleManager.getRoleBeanByRoleID(role_id);
	}
	
	/**
	 * 根据应用系统ID和站点ID得到角色列表(用于缓存读取)
	 * 
	 * @param String app_id
	 * @param String site_id
	 * @return List
	 */
	public static List<RoleBean> getRoleListByAPPAndSite(String app_id,String site_id)
	{
		return RoleManager.getRoleListByAPPAndSite(app_id,site_id);
	}
	
	/**
     * 插入角色信息
     * @param RoleBean rb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRole(RoleBean rb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return RoleManager.insertRole(rb,stl);
		}else
			return false;
	}
	
	/**
     * 修改角色信息
     * @param RoleBean rb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateRole(RoleBean rb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return RoleManager.updateRole(rb,stl);
		}else
			return false;
	}
	
	/**
     * 删除角色前判断角色是否跟用户，用户组，工作流有关联，有的话不让删除
     * @param String role_ids
     * @param String app_id
     * @param String site_id
     * @return String
     * */
	public static String deleteRoleBeforeChecked(String role_id,String app_id,String site_id)
	{
		return RoleManager.deleteRoleBeforeChecked(role_id,app_id,site_id);
	}
	
	/**
     * 删除角色信息
     * @param String role_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteRole(String role_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return RoleManager.deleteRole(role_ids,stl);
		}else
			return false;
	}
	
	/**
     * 根据角色ID，应用ID，站点ID得到用户ID字符串
     * @param String role_id 
     * @param String app_id 
     * @param String site_id 
     * @return String
     * */
	public static String getUsersByRole(String role_id,String app_id,String site_id)
	{
		return RoleUserManager.getUsersByRole(role_id,app_id,site_id);
	}
	
	/**
     * 根据角色ID，应用ID，站点ID得到用户ID字符串
     * @param String role_id 
     * @param String app_id 
     * @param String site_id 
     * @return String
     * */
	public static String getGroupsByRole(String role_id,String app_id,String site_id)
	{
		return RoleUGroupManager.getGroupsByRole(role_id,app_id,site_id);
	}
	
	/**
     * 插入角色与用户关联信息(用于从角色管理页面添加人，１个角色对应多个人)
     * @param RoleUserBean rab
     * @param String delete_user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRoleUserByRole(RoleUserBean rub,String delete_user_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return RoleUserManager.insertRoleUserByRole(rub,delete_user_ids,stl);
		}else
			return false;
	}
	
	/**
     * 插入角色与用户关联信息(用于从用户管理页面添加角色，１个用户对应多个角色)
     * @param RoleUserBean rab
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRoleUserByUser(RoleUserBean rub,HttpServletRequest request)
	{			
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return RoleUserManager.insertRoleUserByUser(rub,stl);
		}else
			return false;
	}
	
	/**
     * 插入角色与用户组关联信息(用于从角色管理页面添加，１个角色对应多个用户组)
     * @param RoleUGroupBean rugb
     * @param String delete_group_ids 要删除的用户组id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRoleUGroupByRole(RoleUGroupBean rugb,String delete_group_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return RoleUGroupManager.insertRoleUGroupByRole(rugb,delete_group_ids,stl);
		}else
			return false;		
	}
	
	/**
     * 插入角色与用户组关联信息(用于从角色管理页面添加，１个用户组对应多个角色)
     * @param RoleUGroupBean rugb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRoleUGroupByGroup(RoleUGroupBean rugb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return RoleUGroupManager.insertRoleUserByUGroup(rugb,stl);
		}else
			return false;
	}
	
	/**
	 * 根据角色ID得到它所关联的权限ID
	 * 
	 * @param String role_id
	 * @return String
	 */	
	public static String getOptIDSByRoleID(String role_id)
	{
		return RoleOptManager.getOptIDSByRoleID(role_id);
	}
	
	/**
     * 插入角色与权限关联(1个角色对应多个权限ID)
     * @param String role_id
     * @param String opt_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertOptReleRole(String role_id,String opt_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return RoleOptManager.insertOptReleRole(role_id,opt_ids,stl);
		}else
			return false;
	}
	
}
