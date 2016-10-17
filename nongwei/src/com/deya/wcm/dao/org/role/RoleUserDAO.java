package com.deya.wcm.dao.org.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.role.RoleUserBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  角色与用户关联数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 角色与用户关联数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class RoleUserDAO {
	/**
     * 得到所有角色与用户关联信息
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getAllRoleUserList()
	{
		return DBManager.queryFList("getAllRoleUserList", "");
	}
	
	/**
     * 插入角色与用户关联信息
     * @param RoleUserBean rab
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRoleUser(RoleUserBean rab,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.ROLEUSER_TABLE_NAME);
		rab.setUser_role_id(id);
		if(DBManager.insert("insert_role_user", rab))
		{			
			PublicTableDAO.insertSettingLogs("添加","角色与用户关联",id+"",stl);
			return true;
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
		Map<String, String> m = new HashMap<String, String>();
		m.put("role_id",role_id);
		return DBManager.delete("delete_role_userByRoleID", m);
	}
	
	/**
     * 根据角色,应用删除角色跟用户关联数据
     * @param String role_id 
     * @param String app_id 
     * @param String site_id 
     * @return boolean
     * */
	public static boolean deleteRoleUserByRole(String role_id,String app_id,String site_id)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("role_id",role_id);
		m.put("app_id",app_id);
		if(site_id != null && !"".equals(site_id))
			m.put("site_id",site_id);
		return DBManager.delete("delete_role_userByRoleAndAppID", m);
	}
	
	/**
     * 根据角色,应用删除角色跟用户关联数据
     * @param String role_id 
     * @param String user_ids 
     * @return boolean
     * */
	public static boolean deleteRoleUserByUserAndRoleID(String user_ids,String role_id)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("user_ids",user_ids);
		m.put("role_id",role_id);
		return DBManager.delete("delete_role_userByUserAndRoleID", m);
	}
	
	/**
     * 根据角色ID，用户ID和角色ID,站点ID删除角色跟用户关联数据
     * @param String role_id 
     * @param String user_ids 
     * @param String app_id 
     * @param String site_id
     * @return boolean
     * */
	public static boolean deleteRoleUserByUserAppSite(String role_id,String user_ids,String app_id,String site_id)
	{
		if(user_ids == null || "".equals(user_ids.trim()))
			return true;
		Map<String, String> m = new HashMap<String, String>();
		m.put("user_ids",user_ids);
		m.put("app_id",app_id);
		if(site_id != null && !"".equals(site_id.trim()))
			m.put("site_id",site_id);
		if(role_id != null && !"".equals(role_id.trim()))
			m.put("role_id",role_id);
		return DBManager.delete("delete_role_userByUserAndAppSite", m);
	}
	
	/**
     * 根据用户删除角色跟用户关联数据
     * @param String user_id 
     * @return boolean
     * */
	public static boolean deleteRoleUserByUser(String user_id)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("user_id",user_id);
		return DBManager.delete("delete_role_userByUser", m);
	}
}
