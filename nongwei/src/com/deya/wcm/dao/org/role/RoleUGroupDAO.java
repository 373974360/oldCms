package com.deya.wcm.dao.org.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.role.RoleUGroupBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  角色与用户组关联数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 角色与用户组关联数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class RoleUGroupDAO {
	/**
     * 得到所有角色与用户组关联信息
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getAllRoleUGroupList()
	{
		return DBManager.queryFList("getAllRoleUGroupList", "");
	}
	
	/**
     * 插入角色与用户组关联信息
     * @param RoleUGroupBean rugb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRoleUGroup(RoleUGroupBean rugb,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.ROLEGROUP_TABLE_NAME);
		rugb.setGroup_role_id(id);
		if(DBManager.insert("insert_role_uGroup", rugb))
		{			
			PublicTableDAO.insertSettingLogs("添加","角色与用户组关联",id+"",stl);
			return true;
		}else
			return false; 
	}
	
	/**
     * 根据角色ID删除角色跟用户组关联数据 (用于删除角色时删除关联表)
     * @param String role_id 
     * @return boolean
     * */
	public static boolean deleteRoleUGroupByRoleID(String role_id)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("role_id",role_id);
		return DBManager.delete("delete_role_uGroupByRoleID", m);
	}
	
	/**
     * 根据角色删除角色跟用户组关联数据
     * @param String group_ids
     * @param String role_id 
     * @param String app_id 
     * @param String site_id 
     * @return boolean
     * */
	public static boolean deleteRoleUGroupByRole(String group_ids,String role_id,String app_id,String site_id)
	{		
		if(group_ids == null || "".equals(group_ids.trim()))
		{
			return true;
		}
		Map<String, String> m = new HashMap<String, String>();
		m.put("group_ids",group_ids);		
		m.put("role_id",role_id);
		m.put("app_id",app_id);
		if(site_id != null && !"".equals(site_id))
			m.put("site_id",site_id);		
		return DBManager.delete("delete_role_uGroupByRole", m);
	}
	
	/**
     * 根据用户组删除角色跟用户组关联数据
     * @param String group_id 
     * @return boolean
     * */
	public static boolean deleteRoleUGroupByGroup(String group_id)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("group_id",group_id);
		return DBManager.delete("delete_role_uGroupByGroup", m);
	}
}
