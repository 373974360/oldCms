package com.deya.wcm.dao.org.user;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.bean.org.user.UserLevelBean;
import com.deya.wcm.bean.org.user.UserRegisterBean;
import com.deya.wcm.dao.org.rmi.GetOrgRmi;

public class UserDAORMIImpl implements IUserDAO{
	/**
     * 得到所有人员列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllUserList(){
		try{
			return GetOrgRmi.getorgRmi().getAllUserList();
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return null;
		}
	}
	
	/**
     * 根据部门ID得到下面人员列表（用于用户管理界面）
     * @param Map conn_m　传递参数，包含dept_id,start_num,page_size,sort_name,sort_type,con_name,con_value
     * @return List
     * */	
	public List<UserBean> getUserListByDeptIDForDB(Map<String,String> con_m){
		try{
			return GetOrgRmi.getorgRmi().getUserListByDeptIDForDB(con_m);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return null;
		}
	}
	
	/**
     * 根据部门ID得到下面人员总数（用于用户管理界面）
     * @param Map conn_m　传递参数，包含dept_id,con_name,con_value
     * @return List
     * */
	public String getUserCountByDeptIDForDB(Map<String, String> con_m)
	{
		try{
			return GetOrgRmi.getorgRmi().getUserCountByDeptIDForDB(con_m);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return null;
		}
	}
	
	/**
     * 根据ID返回人员对象
     * @param String id
     * @return UserBean
     * */
	public UserBean getUserBeanByID(String id){
		try{
			return GetOrgRmi.getorgRmi().getUserBeanByID(id);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return null;
		}
	}
	
	/**
     * 插入人员信息
     * @param UserBean ub
     * @param UserRegisterBean urb 帐号对象
     * @param boolean isAddReg　是否添加帐号
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean insertUser(UserBean ub,UserRegisterBean urb,boolean isAddReg,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().insertUser(ub,urb,isAddReg,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}
	}
	
	/**
     * 修改人员信息
     * @param UserBean ub
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateUser(UserBean ub,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().updateUser(ub,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 保存用户排序
	 * 
	 * @param String
	 *            ids 用户ID
	 * @param HttpServletRequest request	           
	 * @return 
	 */
	public boolean saveUserSort(String ids,SettingLogsBean stl)
	{
		try{
			return GetOrgRmi.getorgRmi().saveUserSort(ids,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}
	}
	
	/**
     * 移动用户
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean moveUser(Map<String,String> m,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().moveUser(m,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}
	}
	
	/**
     * 修改人员状态
     * @param int user_status
     * @param String user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateUserStatus(int user_status,String user_ids,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().updateUserStatus(user_status,user_ids,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}
	}
	
	/**
     * 删除人员信息
     * @param String　user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteUser(String user_ids,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().deleteUser(user_ids,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}
	}
	
	/**
     * 根据部门删除人员信息
     * @param String　detp_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteUserByDeptID(String detp_ids,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().deleteUserByDeptID(detp_ids,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}
	}
	
	/* **********************人员级别管理　开始******************************** */
	/**
     * 得到所有人员级别列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllUserLevelList(){
		try{
			return GetOrgRmi.getorgRmi().getAllUserLevelList();
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return null;
		}
	}
	
	/**
     * 根据ID返回人员级别对象
     * @param String id
     * @return UserBean
     * */
	public UserLevelBean getUserLevelBeanByID(String id){
		try{
			return GetOrgRmi.getorgRmi().getUserLevelBeanByID(id);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return null;
		}
	}
	
	/**
     * 插入人员级别信息
     * @param UserLevelBean ulb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean insertUserLevel(UserLevelBean ulb,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().insertUserLevel(ulb,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}		
	}
	
	/**
     * 修改人员级别信息
     * @param UserLevelBean ulb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateUserLevel(UserLevelBean ulb,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().updateUserLevel(ulb,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}	
	}
	
	
	/**
     * 删除人员级别信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteUserLevel(String ids,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().deleteUserLevel(ids,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}
	}
	/* **********************人员级别管理　结束******************************** */
	
	/* **********************帐号管理　开始******************************** */
	/**
     * 得到所有帐号列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllUserRegister(){
		try{
			return GetOrgRmi.getorgRmi().getAllUserRegister();
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return null;
		}
	}
	
	/**
     * 得到所有人员帐号总数(用于后台管理列表)
     * @param Map<String,String> con_m
     * @return List
     * */
	public String getUserRegisterCount(Map<String,String> con_m){
		try{
			return GetOrgRmi.getorgRmi().getUserRegisterCount(con_m);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return null;
		}
	}
	
	/**
     * 得到所有人员帐号列表(用于后台管理列表)
     * @param Map<String,String> con_m
     * @return List
     * */	
	public List<UserRegisterBean> getAllUserRegisterForDB(Map<String,String> con_m)
	{
		try{
			return GetOrgRmi.getorgRmi().getAllUserRegisterForDB(con_m);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据帐号名得到帐号对象
	 * 
	 * @param String user_name
	 *            
	 * @return UserRegisterBean
	 */
	public UserRegisterBean getUserRegisterBeanByUname(String user_name)
	{
		try{
			return GetOrgRmi.getorgRmi().getUserRegisterBeanByUname(user_name);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return null;
		}
	}
	
	/**
     * 插入帐号信息
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean insertRegister(UserRegisterBean urb,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().insertRegister(urb,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}			
	}
	
	/**
     * 修改帐号信息
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateRegister(UserRegisterBean urb,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().updateRegister(urb,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}
	}
	
	/**
     * 修改帐号状态
     * @param int reg_status 
     * @param String reg_ids
     * @return boolean
     * */
	public boolean updateRegisterStatus(int reg_status,String reg_ids,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().updateRegisterStatus(reg_status,reg_ids,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}
	}
	
	/**
     * 删除帐号信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteRegisterByRID(String reg_ids,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().deleteRegisterByRID(reg_ids,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}
	}
	
	/**
     * 根据人员ID删除帐号信息
     * @param String　user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteRegisterByUserID(String user_ids,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().deleteRegisterByUserID(user_ids,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}
	}
	
	/**
     * 根据部门ID删除帐号信息
     * @param String　dept_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteRegisterByDeptID(String dept_ids,SettingLogsBean stl){
		try{
			return GetOrgRmi.getorgRmi().deleteRegisterByDeptID(dept_ids,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}
	}
	
	/**
     * 根据人员ID批量修改人员密码
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updatePasswordByUserID(UserRegisterBean urb,SettingLogsBean stl)
	{
		try{
			return GetOrgRmi.getorgRmi().updatePasswordByUserID(urb,stl);
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return false;
		}
	}

	/* **********************帐号管理　结束******************************** */


	/* **********************同步银海组织机构 开始******************************** */

    @Override
    public boolean insertSyncUser(List<UserBean> ubList, List<UserRegisterBean> urbList,boolean isAdd) {
        try{
            return GetOrgRmi.getorgRmi().insertSyncUser(ubList,urbList,isAdd);
        }catch(RemoteException re)
        {
            re.printStackTrace();
            return false;
        }
    }

	@Override
	public int getMaxUserId() {
		try{
			return GetOrgRmi.getorgRmi().getMaxUserId();
		}catch(RemoteException re)
		{
			re.printStackTrace();
			return 0;
		}
    }

    /* **********************同步银海组织机构 结束******************************** */


}
