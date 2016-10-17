package com.deya.wcm.services.org.group;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.group.GroupBean;
import com.deya.wcm.bean.org.group.GroupUserBean;
import com.deya.wcm.bean.org.role.RoleBean;
import com.deya.wcm.bean.org.role.RoleUGroupBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.org.group.GroupDAO;
import com.deya.wcm.services.cms.category.CategoryReleManager;
import com.deya.wcm.services.org.role.RoleUGroupManager;
import com.deya.wcm.services.org.user.UserManager;

/**
 *  组织机构用户组管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 组织机构用户组管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
@SuppressWarnings("unchecked")
public class GroupManager implements ISyncCatch{
	private static TreeMap<String,GroupBean> group_map = new TreeMap<String, GroupBean>();	//用户组缓存
	
	private static TreeMap<String,String> group_user_map = new TreeMap<String, String>();//用户组，人员关联缓存　键　用户组ID，值　人员ID组合字符串","+用户ID+","号分隔，可多个　如　,12,13,

	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		List<GroupUserBean> group_user_list = GroupDAO.getGroupUserList();
		group_user_map.clear();
		if (group_user_list != null && group_user_list.size() > 0) {
			for (int i = 0; i < group_user_list.size(); i++) {
				String group_id = group_user_list.get(i).getGroup_id() + "";
				String user_id = group_user_list.get(i).getUser_id() + "";
				if(group_user_map.containsKey(group_id))
				{
					group_user_map.put(group_id, group_user_map.get(group_id) + user_id + ",");	
				}
				else
					group_user_map.put(group_id, "," + user_id + ",");						
			}
		}
		
		group_map.clear();
		List<GroupBean> group_list = GroupDAO.getAllGroupList();		
		if (group_list != null && group_list.size() > 0) {
			for (int i = 0; i < group_list.size(); i++) {
				group_list.get(i).setUser_ids(getUserIDSByGroupIDReload(group_list.get(i).getGroup_id() + ""));				
				group_map.put(group_list.get(i).getGroup_id() + "", group_list
						.get(i));				
			}
		}
		
		
	}
	
	/**
	 * 初始加载用户组信息
	 * 
	 * @param
	 * @return
	 */
	public static void reloadGroup() {
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.group.GroupManager");		
	}
	
	/**
	 * 初始加载用户组人员关联信息
	 * 
	 * @param
	 * @return
	 */
	public static void reloadGroupUser()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.group.GroupManager");	
	}
	
	/**
	 * 根据用户组ID得到此用户组所有的人员ID(用于初始加载)
	 * 
	 * @param　String group_id
	 * @return String 人员ID，","+用户ID+","号分隔，可多个　如　,12,13,
	 */
	public static String getUserIDSByGroupIDReload(String group_id)
	{		
		if(group_user_map.containsKey(group_id))
			return group_user_map.get(group_id);
		else 
			return "";
	}
	
	/**
	 * 根据应用和站点ID得到所有用户组列表
	 * @param String app_id
	 * @param String site_id
	 * @return List<GroupBean>
	 * 		
	 */
	public static List<GroupBean> getGroupListByAppSiteID(String app_id,String site_id)
	{
		List<GroupBean> list = new ArrayList<GroupBean>();
		Set<String> set = group_map.keySet();
		for(String i : set){
			GroupBean gb = group_map.get(i);			
			if((site_id.equals(gb.getSite_id()) || "".equals(site_id)) && app_id.equals(gb.getApp_id()))
			{
				list.add(gb);
			}	
		}
		return list;
	}
	
	/**
	 * 取得用户组数目
	 * @return
	 * 		int 用户组数目
	 */
	public static int getGroupCount(Map mp)
	{
		return GroupDAO.getAllGroupCount(mp);
	}
	
	/**
	 * 根据用户组ID得到所有的人员ID
	 * 
	 * @param　String group_id
	 * @return String 人员ID，","+用户ID+","号分隔，可多个　如　,12,13,
	 */
	public static String getUserIDSByGroupID(String group_id)
	{		
		if(group_user_map.containsKey(group_id))
			return group_user_map.get(group_id);
		else 
		{
			GroupBean gb = getGroupBeanByID(group_id);
			if(gb != null)
				return gb.getUser_ids();
			else
				return "";
		}
	}
	
	/**
	 * 根据用户组ID取得用户名称
	 * @return
	 */
	public static String getUserNameByGroupID(String group_id)
	{
		String ret = "";
		String userIDS = getUserIDSByGroupID(group_id);
		
		// 去掉两端多余的空格和","
		userIDS = userIDS.trim();
		int firstIndex = 0;
		int endIndex = userIDS.length();
		if(userIDS.startsWith(","))
		{
			firstIndex = 1;
		}
		if(userIDS.endsWith(","))
		{
			endIndex = userIDS.length() - 1;
		}
		
		userIDS = userIDS.substring(firstIndex, endIndex);
		List<UserBean> ltUserBean = UserManager.getUserListByUserIDS(userIDS);
		
		if(ltUserBean != null && ltUserBean.size() > 0)
		{
			for(int i=0; i < ltUserBean.size(); i++)
			{
				ret += "," + ltUserBean.get(i).getUser_realname();
			}
			// 处理掉开始的","
			ret = ret.substring(1);
		}
		return ret.toString();
	}
	
	/**
	 * 根据用户组ID取得角色名称
	 * @param group_id 用户组ID
	 * @param app_id  应用系统ID
	 * @param site_id 站点/节点ID
	 * @return String
	 * 		角色名称，多个角色之间用","分隔
	 */
	public static String getRolesByGroupID(String group_id, String app_id, String site_id)
	{
		String sb = "";
		List roleList = RoleUGroupManager.getRoleIDSByUGroupAPP(group_id, app_id, site_id);
		System.out.println(roleList);
		RoleBean rb ;
		if(roleList !=null && roleList.size() > 0)
		{
			for(int i=0; i<roleList.size(); i++)
			{
				rb = (RoleBean)roleList.get(i);
				if(rb != null)
				{
					sb += "," ; 
					sb += rb.getRole_name();
				}					
			}
			sb = sb.substring(1);
		}
		return sb.toString();
	}
	
	/**
	 * 根据用户组ID取得角色ID
	 * @param group_id 用户组ID
	 * @param app_id  应用系统ID
	 * @param site_id 站点/节点ID
	 * @return String
	 * 		角色ID，多个角色之间用","分隔
	 */
	public static String getRoleIDSByGroupID(String group_id, String app_id, String site_id)
	{
		String sb = "";
		List roleList = RoleUGroupManager.getRoleIDSByUGroupAPP(group_id, app_id, site_id);
		RoleBean rb ;
		if(roleList !=null && roleList.size() > 0)
		{
			for(int i=0; i<roleList.size(); i++)
			{
				rb = (RoleBean)roleList.get(i);
				if(rb != null)
				{					
					sb += "," ; 
					sb += rb.getRole_id();
				}
			}
			sb = sb.substring(1);
		}
		return sb.toString();
	}
	
	
	/**
	 * 根据人员ID得到其所在的用户组ID
	 * 
	 * @param String
	 *            user_id 部门ID   
	 * @return String group_ids
	 */
	public static String getGroupIDSByUserID(String user_id)
	{
		String group_ids = "";
		Iterator iter = group_user_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();	
			String user_ids = group_user_map.get((String) entry.getKey());
			if(user_ids.contains(","+user_id+","))
				group_ids += ","+(String) entry.getKey();
		}
		if(group_ids != null && !"".equals(group_ids))
			group_ids = group_ids.substring(1);
		
		return group_ids;
	}
	
	/**
	 * 得到所有用户组信息列表
	 * 
	 * @param 
	 *            
	 * @return List
	 */
	public static List getGroupList()
	{
		List<GroupBean> group_list = new ArrayList<GroupBean>();

		Iterator iter = group_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();									
			group_list.add(group_map.get((String) entry.getKey()));			
		}
		return group_list;
	}
	
	/**
	 * 从数据库取得用户组信息
	 * @param mp
	 * @return
	 */
	public static List getGroupListForDB(Map mp)
	{
		List<GroupBean> group_list = GroupDAO.getAllGroupListForDB(mp);
		return group_list;
	}
	
	/**
	 * 得到所有用户组信息集合
	 * 
	 * @param 
	 *            
	 * @return Map
	 */
	public static Map getGroupMap()
	{
		return group_map;
	}
	
	/**
	 * 根据用户组ID得到用户组对象
	 * 
	 * @param String group_id
	 *            
	 * @return GroupBean
	 */
	public static GroupBean getGroupBeanByID(String group_id)
	{
		if(group_map.containsKey(group_id))
		{
			return group_map.get(group_id);
		}
		else
		{
			GroupBean gb = GroupDAO.getGroupBeanByID(group_id);
			if(gb != null)
			{
				gb.setUser_ids(getUserIDSByGroupIDReload(group_id));
				group_map.put(group_id, gb);
			}
			return gb;
		}
	}
	
	/**
     * 插入用户组信息
     * @param GroupBean gb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertGroup(GroupBean gb,SettingLogsBean stl){
		if(GroupDAO.insertGroup(gb, stl))
		{
			reloadGroup();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改用户组信息
     * @param GroupBean gb
     * @param String delete_user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateGroup(GroupBean gb,SettingLogsBean stl){
		if(GroupDAO.updateGroup(gb, stl))
		{			
			reloadGroup();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 更改角色与用户组关联信息
	 * @param request
	 * @return
	 */
	public static boolean insertRoleUserByUGroup(String roleIDS, GroupBean gb, SettingLogsBean stl)
	{
		RoleUGroupBean rugb = new RoleUGroupBean();
		rugb.setApp_id(gb.getApp_id());
		rugb.setSite_id(gb.getSite_id());
		rugb.setGroup_id(String.valueOf(gb.getGroup_id()));
		rugb.setRole_id(roleIDS);
		if(stl != null){
			return RoleUGroupManager.insertRoleUserByUGroup(rugb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
     * 删除用户组信息
     * @param String group_ids
     * @param String site_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteGroup(String group_ids,String site_id,SettingLogsBean stl){
		if(GroupDAO.deleteGroup(group_ids, stl))
		{			
			reloadGroup();
			RoleUGroupManager.deleteRoleUGroupByGroup(group_ids);
			CategoryReleManager.deleteCateReleByPrv(1,group_ids,site_id);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 插入用户组和用户关联信息
     * @param GroupBean gb
     * @param String delete_user_ids
     * @return boolean
     * */
	public static boolean insertGroupUser(GroupBean gb,String delete_user_ids,SettingLogsBean stl)
	{
		if(GroupDAO.insertGroupUser(gb,stl))
		{
			if(delete_user_ids != null && !"".equals(delete_user_ids))
				GroupDAO.deleteGroupUser(delete_user_ids,gb.getGroup_id()+"");
			reloadGroup();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 根据用户组ID删除用户组用户关联信息
     * @param String group_id     
     * @return boolean
     * */
	public static boolean deleteGroupUserByGroupID(String group_id)
	{
		if(GroupDAO.deleteGroupUserByGroupID(group_id))
		{
			reloadGroup();
			//删除用户组和角色关联
			RoleUGroupManager.deleteRoleUGroupByGroup(group_id);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 根据用户组用户关联ID删除信息
     * @param String group_user_id     
     * @return boolean
     * */
	public static boolean deleteGroupUserByGroupID(String group_user_id,SettingLogsBean stl)
	{
		if(GroupDAO.deleteGroupUserByGroupID(group_user_id,stl))
		{
			reloadGroup();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 根据用户ID删除用户组用户关联表信息
     * @param String user_id     
     * @return boolean
     * */
	public static boolean deleteGroupUserByUserID(String user_id){
		if(GroupDAO.deleteGroupUserByUserID(user_id))
		{
			reloadGroup();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println(getRolesByGroupID("57","cms","11111ddd"));
		//System.out.println(getUserIDSByGroupID("57"));
	}

	
}
