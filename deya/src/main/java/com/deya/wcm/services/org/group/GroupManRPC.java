package com.deya.wcm.services.org.group;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.group.GroupBean;
import com.deya.wcm.services.Log.LogManager;

/**
 * 
 * @author liqi
 * @version 1.0
 */

public class GroupManRPC {
	
	/**
	 * 取得用户组列表
	 * @return
	 *    List<GroupBean> 用户组列表
	 */
	@SuppressWarnings("unchecked")
	public static List getGroupListForDB(Map map)
	{
		return GroupManager.getGroupListForDB(map);
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
		return GroupManager.getGroupListByAppSiteID(app_id,site_id);
	}
	
	/**
	 * 取得用户组数目
	 * @return
	 * 		int 用户组数目
	 */
	@SuppressWarnings("unchecked")
	public static int getGroupCount(Map mp)
	{
		return GroupManager.getGroupCount(mp);
	}
	
	/**
	 * 根据用户组ID得到所有的人员ID
	 * @param id 用户组ID
	 * @return  String 人员ID，多个人员间","号分隔，如　,12,13,
	 */
	public static String getUserIDSByGroupID(String id)
	{
		return GroupManager.getUserIDSByGroupID(id);
	}
	
	/**
	 * 根据用户ID得到用户组ID
	 * @param userId 用户ID
	 * @return
	 * 		用户组ID，多个之间用","分割	如:12,1,2
	 */
	public static String getGroupIDSByUserID(String userId)
	{
		return GroupManager.getGroupIDSByUserID(userId);
	}
	
	/**
	 * 得到所有用户组信息Map
	 * @return
	 * 		Map :key=GroupId,value=GroupBean;
	 */
	@SuppressWarnings("unchecked")
	public static Map getGroupMap()
	{
		return GroupManager.getGroupMap();
	}
	
	/**
	 * 根据用户组ID得到用户组Bean
	 * @param group_id
	 * @return GroupBean 用户组Bean
	 */
	public static GroupBean getGroupBeanByID(String group_id)
	{
		try{
			return GroupManager.getGroupBeanByID(group_id);
		}catch(Throwable tex)
		{
			GroupBean gb = new GroupBean();
			gb.setGroup_name("");
			gb.setGroup_memo("");
			return gb;
		}
	}
	
	/**
	 * 根据用户组ID取得用户名
	 * @param group_id 用户组ID
	 * @return String
	 * 		用户名，多个用户之间使用","分隔
	 */
	public static String getUserNameByGroupID(String group_id)
	{
		try{
			return GroupManager.getUserNameByGroupID(group_id);
		}catch(Throwable tex)
		{
			return "";
		}
	}
	
	/**
	 * 根据用户组ID取得角色名称
	 * @param group_id 用户组ID
	 * @param app_id  应用系统ID
	 * @param site_id 站点/节点ID
	 * @return String
	 * 		多个角色之间使用","分隔
	 */
	public static String getRolesByGroupID(String group_id, String app_id, String site_id)
	{
			return GroupManager.getRolesByGroupID(group_id, app_id, site_id);
	}
	
	/**
	 * 根据用户组ID取得角色ID
	 * @param group_id 用户组ID
	 * @param app_id  应用系统ID
	 * @param site_id 站点/节点ID
	 * @return String
	 * 		多个角色ID直接使用","分隔
	 */
	public static String getRoleIDSByGroupID(String group_id, String app_id, String site_id)
	{
		return GroupManager.getRoleIDSByGroupID(group_id, app_id, site_id);
	}
	
	/**
	 * 插入用户组信息
	 * @param gb 用户组对象
	 * @param request
	 * @return 成功返回True，失败返回false
	 */
	public static boolean insertGroup(GroupBean gb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GroupManager.insertGroup(gb,stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 更改用户组信息-如果对应的用户IDS不为空同时更新用户组和用户关联表
	 * @param gb 用户组对象	 * 
	 * @param request
	 * @return 成功返回True，失败返回false
	 */
	public static boolean updateGroup(GroupBean gb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GroupManager.updateGroup(gb,stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 更改角色与用户组关联信息
	 * @param request
	 * @return
	 */
	public static boolean insertRoleUserByUGroup(String roleIDS, GroupBean gb, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return GroupManager.insertRoleUserByUGroup(roleIDS, gb, stl);
		}
		else
		{
			return false;
		}
	}	
	
	/**
	 * 删除用户组对象
	 * @param group_ids 用户组ID，多个之间用","隔开。
	 * @param String site_id
	 * @param request
	 * @return 成功返回True，失败返回false
	 */
	public static boolean deleteGroup(String group_ids,String site_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GroupManager.deleteGroup(group_ids,site_id,stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 插入用户组和用户关联信息
	 * @param gb
	 * @param String delete_user_ids
	 * @return
	 * 		成功返回True，失败返回false
	 */
	public static boolean insertGroupUser(GroupBean gb,String delete_user_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GroupManager.insertGroupUser(gb,delete_user_ids,stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 根据用户组ID删除用户组和用户关联信息
	 * @param group_id  用户组ID
	 * @return
	 * 		成功返回True，失败返回false
	 */
	public static boolean deleteGroupUserByGroupID(String group_id)
	{
		return GroupManager.deleteGroupUserByGroupID(group_id);
	}
	
	/**
	 * 根据用户组ID删除用户和用户关联信息
	 * @param user_id 用户ID
	 * @return 
	 * 		成功返回True，失败返回false
	 */
	public static boolean deleteGroupUserByUserID(String user_id)
	{
		return GroupManager.deleteGroupUserByUserID(user_id);
	}
}
