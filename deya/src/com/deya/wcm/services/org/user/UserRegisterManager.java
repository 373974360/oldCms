package com.deya.wcm.services.org.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.deya.util.CryptoTools;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.bean.org.user.UserRegisterBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.org.OrgDAOFactory;
import com.deya.wcm.dao.org.user.IUserDAO;

/**
 *  组织机构人员帐号管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 组织机构人员帐号管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class UserRegisterManager implements ISyncCatch{
	private static Map<String, UserRegisterBean> userRegister_Map = new HashMap<String, UserRegisterBean>();	
	
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
		List<UserRegisterBean> userRegister_list = userDao.getAllUserRegister();
		//System.out.println("UserRegisterManager reloadUserRegister+++++++++++++++++=="+userRegister_list);
		userRegister_Map.clear();
		if (userRegister_list != null && userRegister_list.size() > 0) {
			for (int i = 0; i < userRegister_list.size(); i++) {
				userRegister_Map.put(userRegister_list.get(i).getUsername() + "", userRegister_list.get(i));				
			}
		}
	}
	
	/**
	 * 初始加载人员帐号信息
	 * 
	 * @param
	 * @return
	 */	
	public static void reloadUserRegister()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.user.UserRegisterManager");
	}
	
	/**
	 * 判断帐号名是否存在
	 * 
	 * @param user_name
	 *            
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static boolean registerISExist(String user_name,int reg_id)
	{
		boolean isExeist = false;
		Iterator iter = userRegister_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();	
			UserRegisterBean urb = userRegister_Map.get((String) entry.getKey());
			if(user_name.equals(urb.getUsername()))
			{
				if(reg_id == 0)
				{
					isExeist = true;
				}
				else
				{
					if(urb.getRegister_id() != reg_id)
						isExeist = true;
				}	
			}
		}
		return isExeist;
	}
	
	/**
	 * 得到所有人员帐号列表
	 * 
	 * @param 
	 *            
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<UserRegisterBean> getUserRegisterList()
	{
		List<UserRegisterBean> userRegister_list = new ArrayList<UserRegisterBean>();
		Iterator iter = userRegister_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();									
			userRegister_list.add(userRegister_Map.get((String) entry.getKey()));			
		}
		return userRegister_list;
	}
	
	/**
     * 得到所有人员帐号总数(用于后台管理列表)
     * @param Map<String,String> con_m
     * @return List
     * */
	public static String getUserRegisterCount(Map<String,String> con_m)
	{
		return userDao.getUserRegisterCount(con_m);
	}
	
	/**
     * 得到所有人员级别列表(用于后台管理列表)
     * @param Map<String,String> con_m
     * @return List
     * */	
	public static List<UserRegisterBean> getAllUserRegisterForDB(Map<String,String> con_m)
	{
		List<UserRegisterBean> userRegister_list = userDao.getAllUserRegisterForDB(con_m);
		if(userRegister_list != null && userRegister_list.size() > 0)
		{
			for(int i=0;i<userRegister_list.size();i++)
			{
				try{
					userRegister_list.get(i).setUser_realname(UserManager.getUserBeanByID(userRegister_list.get(i).getUser_id()+"").getUser_realname());
				}catch(Exception e)
				{
					//System.out.println("getAllUserLevelListForDB: user is null "+userRegister_list.get(i).getUser_id());
					e.printStackTrace();
				}
			}
		}
		return userRegister_list;
	}
	
	/**
	 * 根据人员ID得到帐号列表
	 * 
	 * @param 
	 *            
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<UserRegisterBean> getUserRegisterListByUserID(int user_id)
	{
		List<UserRegisterBean> userLevel_list = new ArrayList<UserRegisterBean>();
		Iterator iter = userRegister_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();	
			UserRegisterBean ulbo = userRegister_Map.get((String) entry.getKey());	
			UserRegisterBean new_ub = new UserRegisterBean();			
			if(user_id == ulbo.getUser_id())
			{
				CryptoTools ct = new CryptoTools();
				new_ub.setPassword(ct.decode(ulbo.getPassword()));
				new_ub.setRegister_id(ulbo.getRegister_id());
				new_ub.setRegister_status(ulbo.getRegister_status());
				new_ub.setUser_id(ulbo.getUser_id());
				new_ub.setUser_realname(ulbo.getUser_realname());
				new_ub.setUsername(ulbo.getUsername());
				userLevel_list.add(new_ub);				
			}
			
		}
		return userLevel_list;
	}
	
	/**
	 * 得到所有人员帐号信息集合
	 * 
	 * @param 
	 *            
	 * @return Map
	 */
	public static Map<String, UserRegisterBean> getUserRegisterMap()
	{
		return userRegister_Map;
	}
	
	/**
	 * 根据帐号名得到帐号对象
	 * 
	 * @param String user_name
	 *            
	 * @return UserRegisterBean
	 */
	public static UserRegisterBean getUserRegisterBeanByUname(String user_name)
	{
		if(userRegister_Map.containsKey(user_name))
		{
			return userRegister_Map.get(user_name);
		}else
			return userDao.getUserRegisterBeanByUname(user_name);
	}
	
	/**
	 * 根据帐号名得到用户对象
	 * 
	 * @param String user_name
	 *            
	 * @return LoginUserBean
	 */
	public static UserBean getUserBeanByUname(String user_name)
	{
		UserRegisterBean urb = getUserRegisterBeanByUname(user_name);		
		if(urb != null)
			return UserManager.getUserBeanByID(urb.getUser_id()+"");
		else
			return null;
	}
	
	/**
	 * 根据帐号名得到登录用户对象
	 * 
	 * @param String user_name
	 *            
	 * @return LoginUserBean
	 */
	public static LoginUserBean getLoginUserBeanByUname(String user_name)
	{		
		UserBean ub =  getUserBeanByUname(user_name);
		if(ub != null)
		{
			LoginUserBean lub = new LoginUserBean();
			lub.setDept_id(ub.getDept_id());
			lub.setUser_aliasname(ub.getUser_aliasname());
			lub.setUser_id(ub.getUser_id());
			lub.setUser_name(user_name);			
			lub.setUser_realname(ub.getUser_realname());
			lub.setUserlevel_value(ub.getUserlevel_value());
			return lub;
		}else
			return null;
		
	}
	
	/**
	 * 判断帐号密码是否一致，用于修改密码判断原始密码
	 * 
	 * @param String user_name
	 * @param String pass_word           
	 * @return boolean
	 */
	public static boolean checkUserLogin2(String user_name,String pass_word)
	{
		UserRegisterBean urb = getUserRegisterBeanByUname(user_name);
		CryptoTools ct = new CryptoTools();		
		pass_word = ct.encode(pass_word);		
		return pass_word.equals(urb.getPassword());
	}
	
	/**
	 * 判断帐号密码是否一致，用于登录
	 * 
	 * @param String user_name
	 * @param String pass_word           
	 * @return String 
	 * 				0    成功 
	 * 				-1 帐号未开通  
	 * 				1   帐号被停用 
	 * 				2  用户被停用
	 * 				3  用户名不正确   
	 * 				4  用户名密码不正确
	 * 				5  用户不存在
	 */
	public static String checkUserLogin(String user_name,String pass_word)
	{
		UserRegisterBean urb = getUserRegisterBeanByUname(user_name);
		if(urb == null)
		{
			return "3";
		}
		else
		{
			if(urb.getRegister_status() == -1)
			{
				return "-1";
			}
			
			if(urb.getRegister_status() == 1)
			{
				return "1";
			}
			
			CryptoTools ct = new CryptoTools();
			//System.out.println("pass_word---11--"+pass_word);
			pass_word = ct.encode(pass_word);
			//System.out.println("pass_word---22--"+pass_word);
			//System.out.println("urb.getPassword()-----"+urb.getPassword());
			if(pass_word.equals(urb.getPassword()))
			{
				UserBean ub = getUserBeanByUname(user_name);
				if(ub != null)
				{
					if(ub.getUser_status() == 1)
					{
						return "2";
					}else
						return "0";
				}else
				{
					return "5";
				}
			}
			else
			{
				return "4";
			}
		}
	}
	
	/**
	 * 判断帐号密码是否一致，用于登录
	 * 
	 * @param String user_name
	 * @param String pass_word           
	 * @return boolean
	 */
	public static boolean checkUserLoginUP(String user_name,String pass_word)
	{
		if("ok".equals(checkUserLogin(user_name,pass_word)))
			return true;
		else
			return false;
	}
	
	/**
     * 插入帐号信息
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRegister(UserRegisterBean urb,SettingLogsBean stl){
		if(!userRegister_Map.containsKey(urb.getUsername()))
		{			
			if(userDao.insertRegister(urb, stl))
			{
				reloadUserRegister();
				return true;
			}else
				return false;
		}
		else
		{			
			System.out.println("insertRegister username is exist");
			return false;
		}
	}
	
	/**
     * 修改帐号信息
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateRegister(UserRegisterBean urb,SettingLogsBean stl){
		
		if(userDao.updateRegister(urb, stl))
		{
			reloadUserRegister();
			return true;
		}else
			return false;		
	}
	
	/**
     * 修改帐号状态
     * @param int reg_status 
     * @param String reg_ids
     * @return boolean
     * */
	public static boolean updateRegisterStatus(int reg_status,String reg_ids,SettingLogsBean stl){
		if(userDao.updateRegisterStatus(reg_status, reg_ids, stl))
		{
			reloadUserRegister();
			return true;
		}else
			return false;		
	}
	
	/**
     * 删除帐号信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteRegisterByRID(String reg_ids,SettingLogsBean stl){
		if(userDao.deleteRegisterByRID(reg_ids, stl))
		{
			reloadUserRegister();
			return true;
		}else
			return false;
	}
	
	/**
     * 根据人员ID删除帐号信息
     * @param String　user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteRegisterByUserID(String user_ids,SettingLogsBean stl){
		if(userDao.deleteRegisterByUserID(user_ids, stl))
		{
			reloadUserRegister();
			return true;
		}else
			return false;		
	}
	
	/**
     * 根据部门ID删除帐号信息
     * @param String　dept_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteRegisterByDeptID(String dept_ids,SettingLogsBean stl){
		if(userDao.deleteRegisterByDeptID(dept_ids, stl))
		{
			reloadUserRegister();
			return true;
		}else
			return false;	
	}
	
	/**
     * 根据人员ID批量修改人员密码
     * @param String　user_id
     * @param String　password
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updatePasswordByUserID(int user_id,String password,SettingLogsBean stl)
	{
		UserRegisterBean urb = new UserRegisterBean();
		CryptoTools ct = new CryptoTools();
		password = ct.encode(password);
		urb.setPassword(password);
		urb.setUser_id(user_id);
		
		if(userDao.updatePasswordByUserID(urb, stl))
		{
			reloadUserRegister();
			return true;
		}else
			return false;	
	}
	
	public static void main(String args[])
	{
		
		System.out.println(getUserRegisterListByUserID(1));
		//System.out.println(getUserRegisterCount(m));
		//updateRegisterTest();
		//deleteRegisterByUserID("8",new SettingLogsBean());
		//deleteRegisterByDeptID("8",new SettingLogsBean());
		//updatePasswordByUserID(10090,"a",new SettingLogsBean());
		//updateRegisterStatus(0,"6",new SettingLogsBean());
		//System.out.println(isUserLogin("system","111"));
	}
	
	public static void insertRegisterTest()
	{
		UserRegisterBean urb = new UserRegisterBean();
		urb.setPassword("1");
		urb.setUsername("1");
		urb.setUser_id(1);
		insertRegister(urb,new SettingLogsBean());
	}
	
	public static void updateRegisterTest()
	{
		UserRegisterBean urb = new UserRegisterBean();
		urb.setRegister_id(1);
		urb.setPassword("111");
		urb.setUsername("username");
		urb.setRegister_status(1);
		updateRegister(urb,new SettingLogsBean());
	}
}
