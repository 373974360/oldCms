package com.deya.wcm.services.org.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.bean.org.user.SMUserBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.bean.org.user.UserRegisterBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.org.OrgDAOFactory;
import com.deya.wcm.dao.org.user.IUserDAO;
import com.deya.wcm.dao.org.user.UserDAODBImpl;
import com.deya.wcm.services.org.dept.DeptManager;
import com.deya.wcm.services.org.desktop.DesktopManager;
import com.deya.wcm.services.org.group.GroupManager;
import com.deya.wcm.services.org.role.RoleUserManager;

/**
 *  组织机构人员管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 组织机构人员管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class UserManager implements ISyncCatch{
	private static Map<String, UserBean> user_Map = new HashMap<String, UserBean>();	
	
	private static IUserDAO userDao = OrgDAOFactory.getUserDao();

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
		List<UserBean> user_list = userDao.getAllUserList();
		//System.out.println("UserManager reloadUser+++++++++++++++++=="+user_list.size());
		user_Map.clear();
		if (user_list != null && user_list.size() > 0) {
			for (int i = 0; i < user_list.size(); i++) {				
				user_Map.put(user_list.get(i).getUser_id() + "", user_list
						.get(i));				
			}
		}
	}
	
	/**
	 * 初始加载人员信息
	 * 
	 * @param
	 * @return
	 */	
	public static void reloadUser()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.user.UserManager");
	}
	
	/**
	 * 根据用户ID取得部门名称
	 * @param paraList
	 * 		List<UserRegisterBean>
	 * @return Map<integer, String> 
	 * 		key=userID,value=部门名称
	 */
	public static Map<Integer ,String> getUserDeptList(List<UserRegisterBean> paraList)
	{
		Map<Integer ,String> retMap = new HashMap<Integer, String>();
		for(UserRegisterBean u : paraList)
		{
			UserBean ub = getUserBeanByID(u.getUser_id()+"");
			if(ub != null)
			{
				DeptBean dbean = DeptManager
					.getDeptBeanByID(String.valueOf(ub.getDept_id()));
				retMap.put(u.getUser_id(), dbean.getDept_name());
			} 
			else
			{
				retMap.put(u.getUser_id(), "");
			}
		}
		return retMap;
	}
	
	/**
	 * 得到所有人员信息列表
	 * 
	 * @param 
	 *            
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List getUserList()
	{
		List<UserBean> user_list = new ArrayList<UserBean>();

		Iterator iter = user_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();									
			user_list.add(user_Map.get((String) entry.getKey()));			
		}
		return user_list;
	}
	
	/**
	 * 得到所有人员信息集合
	 * 
	 * @param 
	 *            
	 * @return Map
	 */
	public static Map<String, UserBean> getUserMap()
	{
		return user_Map;
	}
	
	/**
	 * 得到精简的用户信息
	 * 
	 * @param 
	 *            
	 * @return Map
	 */
	public static Map<String, SMUserBean> getSimpleUserMap()
	{
		Map<String, SMUserBean> s_map = new HashMap<String, SMUserBean>();
		Set<String> set = user_Map.keySet();
		for(String i : set){
			SMUserBean sub = new SMUserBean();			
			UserBean u = user_Map.get(i);
			DeptBean db = DeptManager.getDeptBeanByID(u.getDept_id()+"");			
			if(db != null)
			{
				sub.setDept_id(u.getDept_id());
				sub.setUser_id(u.getUser_id());
				sub.setUser_realname(u.getUser_realname());
				sub.setDept_treeposition(db.getTree_position());
				s_map.put(i, sub);
			}			
		}
		return s_map;
	}
	
	/**
	 * 保存用户排序
	 * 
	 * @param String
	 *            ids 用户ID
	 * @param HttpServletRequest request	           
	 * @return 
	 */
	public static boolean saveUserSort(String user_ids,SettingLogsBean stl)
	{
		if(user_ids != null && !"".equals(user_ids))
		{
			if(userDao.saveUserSort(user_ids,stl))
			{
				reloadUser();
				return true;
			}else
				return false;			
		}else
			return true;
	}	
	
	/**
     * 根据部门ID得到下面人员列表（用于用户管理界面）
     * @param Map conn_m　传递参数，包含dept_id,start_num,page_size,sort_name,sort_type,conn_name,conn_value
     * @return List
     * */
	public static List<UserBean> getUserListByDeptIDForDB(Map<String,String> con_m)
	{
		return userDao.getUserListByDeptIDForDB(con_m);
	}
	
	/**
     * 根据部门ID得到下面人员总数（用于用户管理界面）
     * @param Map conn_m　传递参数，包含dept_id,con_name,con_value
     * @return List
     * */
	public static String getUserCountByDeptIDForDB(Map<String, String> con_m)
	{
		return userDao.getUserCountByDeptIDForDB(con_m);
	}
	
	/**
	 * 根据部门ID得到下面所有的人员个数,可输入多个部门（用于列表显示个数,可输入多个部门ID匹配）
	 * 
	 * @param String
	 *            dept_id 部门ID   
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static String getUserCountByDeptID(String dept_id)
	{
		dept_id = ","+dept_id+",";
		int count = 0;
		Iterator iter = user_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();	
			UserBean ub = user_Map.get((String) entry.getKey());			
			if(dept_id.contains(","+ub.getDept_id()+","))
			{
				count += 1;
			}
		}
		return count + "";
	}
	
	/**
	 * 根据部门ID得到下面所有的人员对象列表
	 * 
	 * @param String
	 *            dept_id 部门ID 
	 * @param String   
	 * 			  start_num 起始条数
	 * @param String   
	 * 			  page_site 第页显示条数           
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<UserBean> getUserListByDeptID(String dept_id)
	{
		List<UserBean> user_list = new ArrayList<UserBean>();
		dept_id = ","+dept_id+",";
		Iterator iter = user_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();	
			UserBean ub = user_Map.get((String) entry.getKey());
			
			if(dept_id.contains(","+ub.getDept_id()+","))
			{
				user_list.add(ub);	
			}
		}				
		return user_list;
	}
	
	/**
	 * 根据部门ID得到下面所有的人员对象列表(已发布的)
	 * 
	 * @param String
	 *            dept_id 部门ID 
	 * @param String   
	 * 			  start_num 起始条数
	 * @param String   
	 * 			  page_site 第页显示条数           
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<UserBean> getUserListForPublishByDeptID(String dept_id)
	{
		List<UserBean> user_list = new ArrayList<UserBean>();
		dept_id = ","+dept_id+",";
		Iterator iter = user_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();	
			UserBean ub = user_Map.get((String) entry.getKey());
			
			if(ub.getIs_publish() == 1 && dept_id.contains(","+ub.getDept_id()+","))
			{
				user_list.add(ub);	
			}
		}
		Collections.sort(user_list,new UserComparator());
		return user_list;
	}
	
	/**
	 * 根据部门ID得到下面所有的人员ID
	 * 
	 * @param String
	 *            dept_id 部门ID
	 * @return String
	 */
	@SuppressWarnings({ "unchecked" })
	public static String getUserIDSByDeptID(String dept_id)
	{
		String user_ids = "";
		dept_id = ","+dept_id+",";
		Iterator iter = user_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();	
			UserBean ub = user_Map.get((String) entry.getKey());
			
			if(dept_id.contains(","+ub.getDept_id()+","))
			{
				user_ids += "," + ub.getUser_id();
			}
		}
		if(user_ids != null && !"".equals(user_ids))
			user_ids = user_ids.substring(1);
		return user_ids;
	}
	
	/**
	 * 根据人员ID得到其所在的部门ID
	 * 
	 * @param String
	 *            user_id 人员ID   
	 * @return List
	 */
	public static String getDeptIDByUserID(String user_id)
	{
		UserBean ub = getUserBeanByID(user_id);
		if(ub != null)
		{
			return ub.getDept_id()+"";
		}
		else
			return "";
	}
	
	/**
	 * 根据人员ID得到其能管理的部门
	 * 
	 * @param String
	 *            user_id 部门ID   
	 * @return String dept_ids
	 */
	public static String getDeptByUserManager(String user_id)
	{
		return getDeptByUserManager(user_id);
	}
	
	/**
     * 根据ID返回所有人员对象信息,用于人员管理中取得用户所有资料
     * @param String id
     * @return UserBean
     * */
	public static UserBean getAllUserInfoByID(String id){
		return userDao.getUserBeanByID(id);
	}
	
	/**
     * 根据ID返回精简人员对象（有些用到用户的地方不需要全部的内容，只需要 用户ID，用户名，部门ID，级别，用户真实姓名，别名，user_status）
     * 　　一般在显示用户列表的地方用到
     * @param String id
     * @return UserBean
     * */
	public static UserBean getUserBeanByID(String id){
		if(id == null || "".equals(id))
			return null;
		
		if (user_Map.containsKey(id)) {
			return user_Map.get(id);
		} else {
			UserBean ub = getAllUserInfoByID(id);
			if (ub != null)
				user_Map.put(id, ub);
			return ub;
		}
	}
	
	/**
     * 根据用户ID返回用户真实姓名
     * @param String id
     * @return String
     * */
	public static String getUserRealName(String id)
	{
		UserBean ub = getUserBeanByID(id);
		if(ub != null)
			return ub.getUser_realname();
		else
			return "";
	}
	
	
	/**
     * 根据多个人员ID返回人员对象列表
     * @param String user_ids
     * @return List
     * */
	public static List<UserBean> getUserListByUserIDS(String user_ids)
	{
		if(user_ids != null && !"".equals(user_ids))
		{
			List<UserBean> user_list = new ArrayList<UserBean>();
			String[] tempA = user_ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{				
				UserBean ub = getUserBeanByID(tempA[i]);
				if(ub != null)
					user_list.add(ub);
			}
			return user_list;
		}
		else
			return null;
	}
	
	/**
     * 插入人员信息
     * @param UserBean ub
     * @param UserRegisterBean urb 帐号对象
     * @param boolean isAddReg　是否添加帐号
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertUser(UserBean ub,UserRegisterBean urb,boolean isAddReg,SettingLogsBean stl)
	{
		if(userDao.insertUser(ub,urb,isAddReg,stl))
		{
			reloadUser();
			if(isAddReg)
				UserRegisterManager.reloadUserRegister();
			return true;
		}else
			return false;
	}
	
	/**
     * 修改人员信息
     * @param UserBean ub
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateUser(UserBean ub,SettingLogsBean stl){
		if(userDao.updateUser(ub, stl))
		{
			reloadUser();
			return true;
		}else
			return false;
	}
	
	/**
     * 修改人员状态
     * @param int user_status
     * @param String user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateUserStatus(int user_status,String user_ids,SettingLogsBean stl){
		if(userDao.updateUserStatus(user_status, user_ids, stl))
		{
			reloadUser();
			return true;
		}else
			return false;
	}
	
	/**
     * 移动用户
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean moveUser(Map<String,String> m,SettingLogsBean stl){
		if(userDao.moveUser(m, stl))
		{
			reloadUser();
			return true;
		}else
			return false;
	}
	
	
	/**
     * 删除人员信息
     * @param String　user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteUser(String user_ids,SettingLogsBean stl){
		if(userDao.deleteUser(user_ids, stl))
		{
			//同时删除帐号表
			UserRegisterManager.deleteRegisterByUserID(user_ids, stl);
			deleteOtherRelaUser(user_ids);
			reloadUser();
			//删除用户桌面设置信息
			DesktopManager.deleteUserDesktop(user_ids);
			return true;
		}else
			return false;
	}
	
	/**
     * 根据部门删除人员信息
     * @param String　dept_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteUserByDeptID(String dept_ids,SettingLogsBean stl){
		//先得到部门下所有的人员
		String user_ids = getUserIDSByDeptID(dept_ids);
		if(userDao.deleteUserByDeptID(dept_ids, stl))
		{
			reloadUser();
//			同时删除帐号表
			UserRegisterManager.deleteRegisterByDeptID(dept_ids, stl);
			//删除其它关联人员的信息
			deleteOtherRelaUser(user_ids);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除其它关联表中的人员信息，如用户组，角色表
     * @param String　user_ids
     * @return boolean
     * */
	public static void deleteOtherRelaUser(String user_id)
	{
		//删除用户组表中的用户信息
		GroupManager.deleteGroupUserByUserID(user_id);
		//删除角色表中的用户信息
		RoleUserManager.deleteRoleUserByUser(user_id);
	}
	
	static class UserComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			UserBean u1 = (UserBean) o1;
			UserBean u2 = (UserBean) o2;
		    if (u1.getSort() > u2.getSort()) {
		     return 1;
		    } else {
		     if (u1.getSort() == u2.getSort()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}

	/* **********************同步银海组织机构 开始******************************** */

	/**
	 * 插入同步人员信息
	 * @param UserBean ub
	 * @param UserRegisterBean urb 帐号对象
	 * @param boolean isAddReg　是否添加帐号
	 * @param SettingLogsBean stl
	 * @return boolean
	 * */
	public static boolean insertSyncUser(List<UserBean> ubList, List<UserRegisterBean> urbList)
	{
		if(userDao.insertSyncUser(ubList,urbList))
		{
			reloadUser();
			UserRegisterManager.reloadUserRegister();
			return true;
		}else
			return false;
	}

	public static int getMaxUserId(){
		return userDao.getMaxUserId();
	}

	/* **********************同步银海组织机构 结束******************************** */


	public static void main(String[] args)
	{
		//insertUserTest();
		//updateUserTest();
		//deleteUser("8,9,10",new SettingLogsBean());
		//deleteUserByDeptID("10090,10074",new SettingLogsBean());
		//System.out.println(deleteUser("9",new SettingLogsBean()));
		//updateUserStatus(1,"11",new SettingLogsBean());
		System.out.println(getSimpleUserMap());
	}
	
	
	
	
}
