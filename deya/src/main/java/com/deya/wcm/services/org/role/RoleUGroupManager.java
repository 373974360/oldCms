package com.deya.wcm.services.org.role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.group.GroupBean;
import com.deya.wcm.bean.org.role.RoleBean;
import com.deya.wcm.bean.org.role.RoleUGroupBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.org.role.RoleUGroupDAO;
import com.deya.wcm.services.org.group.GroupManager;

/**
 *  角色与用户组关联逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 角色与用户组关联逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class RoleUGroupManager implements ISyncCatch{
	private static TreeMap<String,RoleUGroupBean> role_uGroup_map = new TreeMap<String, RoleUGroupBean>();
	
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
		List<RoleUGroupBean> role_ugroup_list = RoleUGroupDAO.getAllRoleUGroupList();
		//System.out.println("RoleUGroupManager reloadRoleUGroup+++++++++++++++++=="+role_ugroup_list);
		role_uGroup_map.clear();
		if (role_ugroup_list != null && role_ugroup_list.size() > 0) {
			for (int i = 0; i < role_ugroup_list.size(); i++) {						
				role_uGroup_map.put(role_ugroup_list.get(i).getGroup_role_id() + "", role_ugroup_list
						.get(i));				
			}
		}
	}
	
	/**
	 * 初始加载角色与用户组关联信息
	 * 
	 * @param
	 * @return
	 */	
	public static void reloadRoleUGroup()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.role.RoleUGroupManager");
	}
	
	/**
     * 根据用户组ID得到所有的应用系统ID集合(可多个用户组ID)
     * @param String group_id 
     * @return Set
     * */
	@SuppressWarnings("unchecked")
	public static Set getGroupAppIDS(String group_id)
	{
		Set app_s = new HashSet();
		group_id = ","+group_id+",";
		Iterator iter = role_uGroup_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUGroupBean rub = (RoleUGroupBean) entry.getValue();			
			if(group_id.contains(","+rub.getGroup_id()+","))
			{
				app_s.add(rub.getApp_id()+"");
			}
		}
		
		return app_s;
	}
	
	/**
     * 根据用户组ID得到所有的站点ID(可多个用户组ID)
     * @param String group_id 
     * @return Set
     * */
	@SuppressWarnings("unchecked")
	public static Set getGroupSiteIDS(String group_id)
	{
		Set site_s = new HashSet();
		group_id = ","+group_id+",";
		Iterator iter = role_uGroup_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUGroupBean rub = (RoleUGroupBean) entry.getValue();
			
			if(group_id.contains(","+rub.getGroup_id()+",") && !"".equals(rub.getSite_id()) && !"null".equals(rub.getSite_id().toLowerCase()))
			{
				site_s.add(rub.getSite_id());
			}
		}	
		
		return site_s;
	}
	
	/**
     * 根据角色ID，应用ID，站点ID得到用户组列表
     * @param String role_id 
     * @param String app_id 
     * @param String site_id 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getGroupListByRole(String role_id,String app_id,String site_id)
	{
		List<GroupBean> user_list = new ArrayList<GroupBean>();

		Iterator iter = role_uGroup_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUGroupBean rugb = (RoleUGroupBean) entry.getValue();
			
			if(role_id.equals(rugb.getRole_id()) && app_id.equals(rugb.getApp_id()) && ("".equals(site_id) || site_id.equals(rugb.getSite_id())))
			{
					user_list.add(GroupManager.getGroupBeanByID(rugb.getGroup_id()));	
			}
		}
		return user_list;
	}
	
	/**
     * 根据角色ID，应用ID，站点ID得到用户组id
     * @param String role_id 
     * @param String app_id 
     * @param String site_id 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static String getGroupsByRole(String role_id,String app_id,String site_id)
	{
		String group_ids = "";
		Iterator iter = role_uGroup_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUGroupBean rugb = (RoleUGroupBean) entry.getValue();
			
			if(role_id.equals(rugb.getRole_id()) && app_id.equals(rugb.getApp_id()) && ("".equals(site_id) || site_id.equals(rugb.getSite_id())))
			{
					group_ids += ","+rugb.getGroup_id();
			}
		}
		if(group_ids != null && !"".equals(group_ids))
			group_ids = group_ids.substring(1);
		return group_ids;
	}
			
	/**
     * 根据用户组ID得到角色集合(可多个用户组ID.用于登录时查到此用户所有角色)
     * @param String group_id 
     * @return Set
     * */
	@SuppressWarnings("unchecked")
	public static Set getRoleSetByUGroupID(String group_id)
	{
		group_id = ","+group_id+",";
		Set role_s = new HashSet();
		Iterator iter = role_uGroup_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUGroupBean rub = (RoleUGroupBean) entry.getValue();
			
			if(group_id.contains(","+rub.getGroup_id()+","))
			{
				RoleBean rb = RoleManager.getRoleBeanByRoleID(rub.getRole_id());
				if(rb != null)
					role_s.add(rb);
			}
		}
		return role_s;
	}
	
	
	
	/**
     * 根据用户组ID，应用系统ID，站点ID得到角色ID(可多个用户组ID)
     * @param String group_id 
     * @return String
     * */
	@SuppressWarnings("unchecked")
	public static String getRoleIDSByUGroupID(String group_id)
	{
		group_id = ","+group_id+",";
		String role_ids = "";
		Iterator iter = role_uGroup_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUGroupBean rub = (RoleUGroupBean) entry.getValue();
			
			if(group_id.contains(","+rub.getGroup_id()+","))
			{
				role_ids += "," + rub.getRole_id();	
			}
		}
		
		if(role_ids != null && !"".equals(role_ids))
			role_ids = role_ids.substring(1);
		return role_ids;
	}
	
	/**
     * 根据用户组ID，应用系统ID，站点ID得到角色列表
     * @param String group_id 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getRoleIDSByUGroupAPP(String group_id,String app_id,String site_id)
	{
		group_id = ","+group_id+",";
		List<RoleBean> role_list = new ArrayList<RoleBean>();
		Iterator iter = role_uGroup_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			RoleUGroupBean rub = (RoleUGroupBean) entry.getValue();
			
			if(group_id.contains(","+rub.getGroup_id()+",") && (app_id.equals(rub.getApp_id()) || "system".equals(rub.getApp_id())) && ("".equals(site_id) || site_id.equals(rub.getSite_id())))
			{
				RoleBean r = RoleManager.getRoleBeanByRoleID(rub.getRole_id());
				if(r != null)
					role_list.add(r);
			}
		}
		return role_list;
	}
	
	
	/**
     * 插入角色与用户组关联信息(用于从角色管理页面添加，１个角色对应多个用户组)
     * @param RoleUGroupBean rugb
     * @param String delete_group_ids 要删除的用户组id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRoleUGroupByRole(RoleUGroupBean rugb,String delete_group_ids,SettingLogsBean stl)
	{		
		//首先删除该角色下对应的人员
		if(RoleUGroupDAO.deleteRoleUGroupByRole(delete_group_ids,rugb.getRole_id(), rugb.getApp_id(), rugb.getSite_id()))
		{
			try{
				if(rugb.getGroup_id() != null && !"".equals(rugb.getGroup_id().trim()))
				{
					String[] tempA = rugb.getGroup_id().split(",");
					for(int i=0;i<tempA.length;i++)
					{
						rugb.setGroup_id(tempA[i]);
						RoleUGroupDAO.insertRoleUGroup(rugb, stl);
					}
				}
				reloadRoleUGroup();
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}			
		}else
			return false;
	}
	
	/**
     * 插入角色与用户组关联信息(用于从用户组管理页面添加角色，１个用户组对应多个角色)
     * @param RoleUGroupBean rugb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRoleUserByUGroup(RoleUGroupBean rugb,SettingLogsBean stl)
	{
		//首先删除该用户组下对应的角色
		if(RoleUGroupDAO.deleteRoleUGroupByGroup(rugb.getGroup_id()))
		{
			try{
				if(rugb.getRole_id() != null)
				{
					String[] tempA = rugb.getRole_id().split(",");
					for(int i=0;i<tempA.length;i++)
					{
						rugb.setRole_id(tempA[i]);
						RoleUGroupDAO.insertRoleUGroup(rugb, stl);
					}
				}
				reloadRoleUGroup();
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}			
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
		if(RoleUGroupDAO.deleteRoleUGroupByRoleID(role_id))
		{
			reloadRoleUGroup();
			return true;
		}else
			return false;
	}
	
	/**
     * 根据用户组删除角色跟用户组关联数据
     * @param String group_id 
     * @return boolean
     * */
	public static boolean deleteRoleUGroupByGroup(String group_id)
	{		
		if(RoleUGroupDAO.deleteRoleUGroupByGroup(group_id))
		{
			reloadRoleUGroup();
			return true;
		}else
			return false;
	}
	
	public static void main(String args[])
	{
		
		System.out.println(getGroupAppIDS("3"));
	}
	
	
}
