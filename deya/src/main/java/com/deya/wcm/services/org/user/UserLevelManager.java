package com.deya.wcm.services.org.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.bean.org.user.UserLevelBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.org.OrgDAOFactory;
import com.deya.wcm.dao.org.user.IUserDAO;

/**
 *  组织机构人员级别管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 组织机构人员级别管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class UserLevelManager implements ISyncCatch{
	private static Map<String, UserLevelBean> userLevel_Map = new HashMap<String, UserLevelBean>();	
	
	private static IUserDAO userDao = OrgDAOFactory.getUserDao();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		List<UserLevelBean> userLevel_list = userDao.getAllUserLevelList();
		//System.out.println("UserLevelManager reloadUserLevel+++++++++++++++++=="+userLevel_list);
		userLevel_Map.clear();
		if (userLevel_list != null && userLevel_list.size() > 0) {
			for (int i = 0; i < userLevel_list.size(); i++) {
				userLevel_Map.put(userLevel_list.get(i).getUserlevel_id() + "", userLevel_list
						.get(i));				
			}
		}
	}
	
	/**
	 * 初始加载人员级别信息
	 * 
	 * @param
	 * @return
	 */	
	public static void reloadUserLevel()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.user.UserLevelManager");
	}
	
	/**
	 * 得到所有人员级别总数
	 * 
	 * @param 
	 *            
	 * @return String
	 */
	public static String getUserLevelCount()
	{
		return userLevel_Map.size()+"";
	}
	
	/**
	 * 得到所有人员级别信息列表
	 * 
	 * @param 
	 *            
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<UserLevelBean> getUserLevelList()
	{
		List<UserLevelBean> userLevel_list = new ArrayList<UserLevelBean>();
		
//		定义一个临时的TreeMap，因为人员要按Deptlevel_value级别值排序，所以先把得到的人员级别值做为key值，让其自动排序，再循环组织成列表
		TreeMap<Integer, UserLevelBean> temp_dept_map = new TreeMap<Integer, UserLevelBean>();
		
		Iterator iter = userLevel_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();	
			UserLevelBean ulb = userLevel_Map.get((String) entry.getKey());
			temp_dept_map.put(ulb.getUserlevel_value(), ulb);		
		}
		
		Iterator iter2 = temp_dept_map.entrySet().iterator();
		while (iter2.hasNext()) {
			Map.Entry entry = (Map.Entry) iter2.next();
			userLevel_list.add((UserLevelBean)entry.getValue());
		}
		return userLevel_list;
	}
	
	/**
	 * 得到所有人员级别信息集合
	 * 
	 * @param 
	 *            
	 * @return Map
	 */
	public static Map<String, UserLevelBean> getUserMap()
	{
		return userLevel_Map;
	}
	
	/**
     * 根据ID返回人员级别对象
     * @param String id
     * @return UserBean
     * */
	public static UserLevelBean getUserLevelBeanByID(String id){
		if (userLevel_Map.containsKey(id)) {
			return userLevel_Map.get(id);
		} else {
			UserLevelBean ulb = userDao.getUserLevelBeanByID(id);
			if (ulb != null)
				userLevel_Map.put(id, ulb);
			return ulb;
		}
	}
	
	/**
     * 插入人员级别信息
     * @param UserLevelBean ulb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertUserLevel(UserLevelBean ulb,SettingLogsBean stl){
//		首先判断该级别是否已经存在
		if(getUserLevelCountByLevel(0,ulb.getUserlevel_value()))
		{
			System.out.println("insertUserLevel : this userLevel value is exist");
			return false;
		}else{
			if (userDao.insertUserLevel(ulb, stl)) {
				reloadUserLevel();
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
     * 修改人员级别信息
     * @param UserLevelBean ulb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateUserLevel(UserLevelBean ulb,SettingLogsBean stl){
//		首先判断该级别是否已经存在		
		if(getUserLevelCountByLevel(ulb.getUserlevel_id(),ulb.getUserlevel_value()))
		{
			System.out.println("updateUserLevel : this userLevel value is exist");
			return false;
		}
		else
		{
			if (userDao.updateUserLevel(ulb, stl)) {
				reloadUserLevel();
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
     * 删除人员级别信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteUserLevel(String ids,SettingLogsBean stl)
	{
		if(userDao.deleteUserLevel(ids, stl))
		{
			reloadUserLevel();
			return true;
		}else
			return false;
	}
	
	/**
	 * 删除人员级别，如果人员级别下面关联有用户不能删除
	 * @param ids
	 * 		用户级别ID,多个ID用","分隔
	 * @param stl
	 * @return
	 */
	public static boolean deleteVoidUserLevel(String ids,SettingLogsBean stl)
	{/*
		boolean flg = true;
		String[] arrIDS = ids.split(",");
		for(int i=0; i<arrIDS.length; i++)
		{
			// 检查是否有用户和用户级别关联
			if(isExistUser(arrIDS[i]) != 0)
			{
				flg = false;
			}
		}
		if(flg)
		{
			flg = deleteUserLevel(ids, stl);
		}*/
		
		return deleteUserLevel(ids, stl);
	}
	
	/**
	 * 查看userlevel关联的用户数
	 * @param id
	 * 		userlevel_id
	 * @return
	 * 		userlvevl关联的用户个数
	 */
	public static int isExistUser(String id)
	{
		String value = userLevel_Map.get(id).getUserlevel_value()+"";
		int cnt =0;
		Map<String, UserBean> map = UserManager.getUserMap();
		Iterator<UserBean> it = map.values().iterator();
		while(it.hasNext())
		{
			UserBean ub = it.next();
			if(value.equals(ub.getUserlevel_value()))
			{
				cnt++;
			}
		}
		return cnt;
	}
	
	
	/**
     * 根据该级别查到此级别的个数
     * 用于判断用户级别value是否重复
     * @param int level_value
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean getUserLevelCountByLevel(int id,int level_value)
	{		
		Iterator iter = userLevel_Map.entrySet().iterator();
		int count = 0;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			UserLevelBean ulb = userLevel_Map.get((String) entry.getKey());	
			//System.out.println(ulb.getUserlevel_value() + " " +level_value+"  "+id+"  "+ulb.getUserlevel_id());
			if(ulb.getUserlevel_value() == level_value && (id == 0 || ulb.getUserlevel_id() != id))
				count += 1;	
		}		
		return count > 0;
	}
	
	public static void main(String[] args)
	{
		//insertUserLevelTest();
		//updateUserLevelTest();
		//deleteUserLevel("7",new SettingLogsBean());
		
		List<UserLevelBean> l = getUserLevelList();
		for(int i=0;i<l.size();i++)
		{
			System.out.println(l.get(i).getUserlevel_id());
		}
	}
	
	public static void insertUserLevelTest()
	{
		UserLevelBean ulb = new UserLevelBean();
		ulb.setUserlevel_memo("第一级用户");
		ulb.setUserlevel_name("第一级");
		ulb.setUserlevel_value(1);
		insertUserLevel(ulb,new SettingLogsBean());
	}
	
	public static void updateUserLevelTest()
	{
		UserLevelBean ulb = new UserLevelBean();
		ulb.setUserlevel_id(9);
		ulb.setUserlevel_memo("第二２级用户");
		ulb.setUserlevel_name("第二２级");
		ulb.setUserlevel_value(1);
		updateUserLevel(ulb,new SettingLogsBean());
	}
}
