package com.deya.wcm.dao.org.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.role.RoleAppBean;
import com.deya.wcm.bean.org.role.RoleBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  组织机构角色管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 组织机构角色管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class RoleDAO {
	/**
     * 得到所有角色列表
     * @param String site_id
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<RoleBean> getRoleListBySiteID(String site_id)
	{
		return DBManager.queryFList("getRoleListBySiteID", site_id);
	}
	
	/**
     * 得到所有角色列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<RoleBean> getAllRoleList()
	{
		return DBManager.queryFList("getAllRoleList", "");
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
		return DBManager.getString("getRoleCountForDB", m);
	}
	
	/**
	 * 根据应用系统ID和站点ID得到角色列表(从数据库中查询，用于在页面上列出角色列表)
	 * 
	 * @param String app_id
	 * @param String site_id
	 * @param int start_num
	 * @param int page_size
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List getRoleListByDB(Map m)
	{
		return DBManager.queryFList("getRoleListByDB", m);
	}
	
	/**
     * 根据ID返回角色对象
     * @param String id
     * @return UserBean
     * */
	public static RoleBean getRoleBeanByRoleID(String id)
	{
		return (RoleBean)DBManager.queryFObj("getRoleBeanByRoleID", id);
	}
	
	/**
     * 插入角色信息
     * @param RoleBean rb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRole(RoleBean rb,SettingLogsBean stl){
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.ROLE_TABLE_NAME);
		rb.setRole_id(id);
		if(DBManager.insert("insert_role", rb))
		{	
			//如果角色可应用的系统ID不为空，在关联表中加入此记录
			insertRoleApp(id,rb.getApp_id()+","+rb.getA_app_id(),rb.getSite_id());
			if(stl != null)
				PublicTableDAO.insertSettingLogs("添加","角色",id+"",stl);
			return true;
		}else
			return false; 
	}
	
	/**
     * 修改角色信息
     * @param RoleBean rb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateRole(RoleBean rb,SettingLogsBean stl){
		if(DBManager.update("update_role", rb))
		{				
			updateRoleApp(rb.getRole_id(),rb.getApp_id()+","+rb.getA_app_id(),rb.getSite_id());
			PublicTableDAO.insertSettingLogs("修改","角色",rb.getRole_id()+"",stl);
			return true;
		}else
			return false; 
	}
	
	/**
     * 删除角色信息
     * @param String role_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteRole(String role_ids,SettingLogsBean stl){
		Map<String, String> m = new HashMap<String, String>();
		m.put("role_id", role_ids);
		if(DBManager.delete("delete_role", m))
		{		
			//删除角色应用关联表
			deleteRoleApp(role_ids);
			//删除角色权限关联表
			RoleOptDAO.deleteOptReleRoleByRoleID(role_ids);			
			PublicTableDAO.insertSettingLogs("删除","角色",role_ids+"",stl);
			return true;
		}else
			return false; 
	}
	
	@SuppressWarnings("unchecked")
	public static List getAllRoleAppList()
	{
		return DBManager.queryFList("getAllRoleAppList", "");
	}
	
	/**
     * 插入角色与应用系统的关联信息
     * @param int role_id
     * @param String app_ids 应用系统ID
     * @param String site_id
     * @return boolean
     * */
	public static boolean insertRoleApp(int role_id,String app_ids,String site_id){
		try
		{		
			RoleAppBean rab = new RoleAppBean();
			rab.setRole_id(role_id);
			rab.setSite_id(site_id);
			
			String[] tempA = app_ids.split(",");			
			for(int i=0;i<tempA.length;i++)
			{
				if(tempA[i] != null && !"".equals(tempA[i]))
				{				
					rab.setRole_app_id(PublicTableDAO.getIDByTableName(PublicTableDAO.ROLEAPP_TABLE_NAME));					
					rab.setApp_id(tempA[i]);
					
					DBManager.insert("insert_role_app", rab);
				}
			}			
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
     * 插入角色与应用系统的关联信息
     * @param int role_id
     * @param String app_ids 应用系统ID
     * @param String site_id
     * @return boolean
     * */
	public static boolean updateRoleApp(int role_id,String app_ids,String site_id){
		//先删除再添加
		if(deleteRoleApp(role_id+""))
		{
			return insertRoleApp(role_id,app_ids,site_id);
		}else
			return false;
	}

	/**
     * 插入角色与应用系统的关联信息
     * @param String role_id
     * @return boolean
     * */
	public static boolean deleteRoleApp(String role_id){
		Map<String, String> m = new HashMap<String, String>();
		m.put("role_id", role_id);
		return DBManager.delete("delete_role_app", m);
	}	
	
}
