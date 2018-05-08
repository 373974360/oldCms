package com.deya.wcm.dao.org.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.role.RoleOptBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  角色与权限关联逻辑数据类.
 * <p>Title: CicroDate</p>
 * <p>Description: 角色与权限关联数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class RoleOptDAO {
	/**
     * 得到所有权限角色关联信息列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getAllRoleReleOptList()
	{
		return DBManager.queryFList("getAllRoleReleOptList", "");
	}
	
	/**
     * 根据角色ID得到权限列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getOptListByRoleID()
	{
		return DBManager.queryFList("getOptListByRoleID", "");
	}
	
	/**
     * 插入角色与权限关联
     * @param String role_id
     * @param String opt_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertOptReleRole(String role_id,String opt_ids,SettingLogsBean stl) 
	{
		if(deleteOptReleRoleByRoleID(role_id))
		{
			try
			{
				String[] tempA = opt_ids.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					if(tempA[i] != null && !"".equals(tempA[i]))
					{
						RoleOptBean rob = new RoleOptBean();
						rob.setRole_opt_id(PublicTableDAO.getIDByTableName(PublicTableDAO.ROLEOPT_TABLE_NAME));
						rob.setRole_id(Integer.parseInt(role_id));
						rob.setOpt_id(Integer.parseInt(tempA[i]));
						DBManager.insert("insert_optReleRole", rob);
					}
				}
				if(stl != null)
					PublicTableDAO.insertSettingLogs("修改","角色与权限关联",role_id+"",stl);
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
     * 根据关联ID删除角色与权限信息关联
     * @param String role_opt_id
     * @return boolean
     * */
	public static boolean deleteOptReleRoleByReleID(String role_opt_id)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("role_opt_id", role_opt_id);
		return DBManager.delete("delete_optReleRole_byReleID", m);
	}
	
	/**
     * 根据角色ID删除角色与权限信息关联
     * @param String role_id
     * @return boolean
     * */
	public static boolean deleteOptReleRoleByRoleID(String role_id)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("role_id", role_id);
		return DBManager.delete("delete_optReleRole_byRoleID", m);
	}
	
	/**
     * 根据权限ID删除角色与权限信息关联
     * @param String opt_id
     * @return boolean
     * */
	public static boolean deleteOptReleRoleByOptID(String opt_id)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("opt_id", opt_id);
		return DBManager.delete("delete_optReleRole_byOptID", m);
	}
}
