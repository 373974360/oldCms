package com.deya.wcm.dao.org.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.group.GroupBean;
import com.deya.wcm.bean.org.group.GroupUserBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  组织机构用户组管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 组织机构用户组管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class GroupDAO {
	/**
     * 得到所有用户组列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<GroupBean> getAllGroupList()
	{
		return DBManager.queryFList("getAllGroupList", "");
	}
	
	/**
	 * 从数据库取得分页用户组列表
	 * @param mp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<GroupBean> getAllGroupListForDB(Map mp)
	{
		return DBManager.queryFList("getAllGroupListForDB", mp);
	}
	
	/**
	 * 从数据库取得符合Map中条件的用户组总数
	 * @param mp
	 * @return
	 * 		用户组条数
	 */
	public static int getAllGroupCount(Map<String,String> mp)
	{
		String cnt = (String)DBManager.queryFObj("getAllGroupCount", mp);
		return Integer.valueOf(cnt);
	}
	/**
     * 根据ID返回用户组对象
     * @param String id
     * @return UserBean
     * */
	public static GroupBean getGroupBeanByID(String id)
	{
		return (GroupBean)DBManager.queryFObj("getGroupBeanByID", id);
	}
	
	/**
     * 插入用户组信息
     * @param GroupBean gb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertGroup(GroupBean gb,SettingLogsBean stl){
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.GROUP_TABLE_NAME);
		gb.setGroup_id(id);
		if(DBManager.insert("insert_group", gb))
		{			
			PublicTableDAO.insertSettingLogs("添加","用户组",id+"",stl);
			return true;
		}else
			return false; 
	}
	
	/**
     * 修改用户组信息
     * @param GroupBean gb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateGroup(GroupBean gb,SettingLogsBean stl){
		if(DBManager.update("update_group", gb))
		{			
			PublicTableDAO.insertSettingLogs("修改","用户组",gb.getGroup_id()+"",stl);
			return true;
		}else
			return false; 
	}
	
	/**
     * 删除用户组信息
     * @param String group_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean deleteGroup(String group_ids,SettingLogsBean stl){
		Map mp = new HashMap<String, String>();
		mp.put("group_id", group_ids);
		if(DBManager.delete("delete_group", mp))
		{
			deleteGroupUserByGroupID(group_ids);
			PublicTableDAO.insertSettingLogs("删除","用户组",group_ids+"",stl);
			return true;
		}else
			return false; 
	}
	
	/* **********************用户组人员关联管理　开始******************************** */
	/**
     * 得到所有用户组和人员关联信息列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getGroupUserList()
	{
		return DBManager.queryFList("getGroupUserList", "");
	}
	
	/**
     * 插入用户组和用户关联信息
     * @param GroupBean gb
     * @return boolean
     * */
	public static boolean insertGroupUser(GroupBean gb,SettingLogsBean stl){
		GroupUserBean gub = new GroupUserBean();
		gub.setGroup_id(gb.getGroup_id());
		gub.setSite_id(gb.getSite_id());
		gub.setApp_id(gb.getApp_id());
		
		try{
			if(gb.getUser_ids() != null && !"".equals(gb.getUser_ids()))
			{
				String[] tempA = gb.getUser_ids().split(",");
				for(int i=0;i<tempA.length;i++)
				{
					if(tempA[i] != null && !"".equals(tempA[i]))
					{
						gub.setGroup_user_id(PublicTableDAO.getIDByTableName(PublicTableDAO.GROUPUSER_TABLE_NAME));
						gub.setUser_id(Integer.parseInt(tempA[i]));
						DBManager.insert("insert_group_user", gub);
					}
				}
			}
			PublicTableDAO.insertSettingLogs("添加","用户组用户关联ID",gb.getGroup_id()+"",stl);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}			
	}
		
	/**
     * 根据用户组ID删除用户组用户关联信息
     * @param String group_id     
     * @return boolean
     * */
	public static boolean deleteGroupUserByGroupID(String group_id)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("group_id", group_id);		
		return DBManager.delete("delete_group_user_byGroupID", m);
	}
	
	/**
     * 根据用户组用户关联ID删除信息
     * @param String group_user_id     
     * @return boolean
     * */
	public static boolean deleteGroupUserByGroupID(String group_user_id,SettingLogsBean stl)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("group_id", group_user_id);		
		if(DBManager.delete("delete_group_user", m))
		{
			PublicTableDAO.insertSettingLogs("删除","用户组用户关联ID",group_user_id+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 根据用户ID删除用户组关联表信息
     * @param String user_id     
     * @return boolean
     * */
	public static boolean deleteGroupUserByUserID(String user_id)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("user_id", user_id);		
		return DBManager.delete("delete_group_user", m);
	
	}
	
	/**
     * 根据用户ID删除用户组关联表信息
     * @param String user_id     
     * @return boolean
     * */
	public static boolean deleteGroupUser(String user_id,String group_id)
	{
		if(user_id == null || "".equals(user_id))
			return true;
		Map<String, String> m = new HashMap<String, String>();
		m.put("user_id", user_id);		
		m.put("group_id", group_id);
		return DBManager.delete("delete_group_user", m);
	
	}
	/* **********************用户组人员关联管理　结束******************************** */
}
