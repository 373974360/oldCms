package com.deya.wcm.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.bean.org.dept.DeptLevelBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.bean.org.user.UserLevelBean;
import com.deya.wcm.bean.org.user.UserRegisterBean;

public interface IOrgRmi extends Remote{
	/**
     * 得到所有部门列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllDeptList()throws RemoteException;
	
	/**
     * 得到部门ID,用于添加部门时给出ID值
     * @param 
     * @return List
     * */
	public int getDeptID()throws RemoteException;
	
	/**
	 * 根据部门ID及查询条件从库中得到下级部门列表对象deep+1,参数有 dept_id,con_name,con_value
	 * 
	 * @param Map
	 *            con_m
	 * @return List
	 */
	public List<DeptBean> getChildDeptListForDB(Map<String, String> conM)throws RemoteException;
	
	/**
     * 根据ID返回部门对象
     * @param String id
     * @return DeptBean
     * */
	public DeptBean getDeptBeanByID(String id)throws RemoteException;
	
	/**
     * 插入部门信息
     * @param DeptBean db
     * @return boolean
     * */
	public  boolean insertDept(DeptBean db,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 修改部门信息
     * @param DeptBean db
     * @return boolean
     * */
	public  boolean updateDept(DeptBean db,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 移动部门
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public  boolean moveDept(Map<String,String> m,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 删除部门信息
     * @param String　ids
     * @return boolean
     * */
	public  boolean deleteDept(String ids,SettingLogsBean stl)throws RemoteException;
	
	/**
	 * 保存部门排序
	 * 
	 * @param String
	 *            ids 部门ID
	 * @return 
	 */
	public  boolean saveDeptSort(String ids,SettingLogsBean stl)throws RemoteException;
	
	/* **********************部门管理员管理　开始******************************** */
	/**
	 * 得到部门管理员列表
	 * 
	 * @param
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getAllDeptManagerList()throws RemoteException;
	
	/**
	 * 添加部门管理员
	 * 
	 * @param String
	 *            user_ids 所选为管理员的人员ID
	 * @param int
	 * 			  dept_id 部门ID	            
	 * @return boolean
	 */
	public boolean insertDetpManager(String user_ids,String dept_id,SettingLogsBean stl)throws RemoteException;
	
	/**
	 * 修改部门管理员
	 * 
	 * @param String
	 *            user_ids 所选为管理员的人员ID
	 * @param int
	 * 			  dept_id 部门ID	            
	 * @return boolean
	 */
	public boolean updateDetpManager(String user_ids,String dept_id,SettingLogsBean stl)throws RemoteException;
	
	/**
	 * 删除部门管理员
	 * 	 
	 * @param int
	 * 			  dept_ids 部门ID	            
	 * @return boolean
	 */
	public boolean deleteDeptManager(String user_ids,String dept_ids,SettingLogsBean stl)throws RemoteException;
	
	/* **********************部门管理员管理　结束******************************** */
	
	/* **********************部门级别管理　开始******************************** */
	
	/**
     * 得到所有部门级别列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllDeptLevelList()throws RemoteException;
	
	/**
	 * 根据部门ID得到部门信息
	 * 
	 * @param String 
	 *            id
	 * @return DeptLevelBean
	 */
	public DeptLevelBean getDeptLevelBeanByID(String id)throws RemoteException;
	
	/**
     * 插入部门级别信息
     * @param DeptLevelBean dlb
     * @return boolean
     * */
	public boolean insertDeptLevel(DeptLevelBean dlb,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 修改部门级别信息
     * @param DeptLevelBean dlb
     * @return boolean
     * */
	public boolean updateDeptLevel(DeptLevelBean dlb,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 删除部门级别信息
     * @param String　ids
     * @return boolean
     * */
	public boolean deleteDeptLevel(String ids,SettingLogsBean stl)throws RemoteException;
	/* **********************部门级别管理　结束******************************** */
	
	/* **********************人员管理　开始******************************** */
	/**
     * 得到所有人员列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllUserList()throws RemoteException;
	
	/**
     * 根据部门ID得到下面人员列表（用于用户管理界面）
     * @param Map conn_m　传递参数，包含dept_id,start_num,page_size,sort_name,sort_type,conn_name,conn_value
     * @return List
     * */
	public List<UserBean> getUserListByDeptIDForDB(Map<String,String> conM)throws RemoteException;
	
	/**
     * 根据部门ID得到下面人员总数（用于用户管理界面）
     * @param Map conn_m　传递参数，包含dept_id,con_name,con_value
     * @return List
     * */
	public String getUserCountByDeptIDForDB(Map<String, String> conM)throws RemoteException;
	
	/**
     * 根据ID返回人员对象
     * @param String id
     * @return UserBean
     * */
	public UserBean getUserBeanByID(String id)throws RemoteException;
	
	/**
     * 插入人员信息
     * @param UserBean ub
     * @param UserRegisterBean urb 帐号对象
     * @param boolean isAddReg　是否添加帐号
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean insertUser(UserBean ub,UserRegisterBean urb,boolean isAddReg,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 修改人员信息
     * @param UserBean ub
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateUser(UserBean ub,SettingLogsBean stl)throws RemoteException;
	
	/**
	 * 保存用户排序
	 * 
	 * @param String
	 *            ids 用户ID
	 * @param HttpServletRequest request	           
	 * @return 
	 */
	public boolean saveUserSort(String ids,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 移动用户
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean moveUser(Map<String,String> m,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 修改人员状态
     * @param int user_status
     * @param String user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateUserStatus(int user_status,String user_ids,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 删除人员信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteUser(String ids,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 根据部门删除人员信息
     * @param String　detp_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteUserByDeptID(String detp_ids,SettingLogsBean stl)throws RemoteException;
	/* **********************人员管理　结束******************************** */
	
	/* **********************人员级别管理　开始******************************** */
	/**
     * 得到所有人员级别列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllUserLevelList()throws RemoteException;
	
	/**
     * 根据ID返回人员级别对象
     * @param String id
     * @return UserBean
     * */
	public UserLevelBean getUserLevelBeanByID(String id)throws RemoteException;
	
	/**
     * 插入人员级别信息
     * @param UserLevelBean ulb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean insertUserLevel(UserLevelBean ulb,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 修改人员级别信息
     * @param UserLevelBean ulb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateUserLevel(UserLevelBean ulb,SettingLogsBean stl)throws RemoteException;
	
	
	/**
     * 删除人员级别信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteUserLevel(String ids,SettingLogsBean stl)throws RemoteException;
	/* **********************人员级别管理　结束******************************** */
	
	/* **********************帐号管理　开始******************************** */
	/**
     * 得到所有帐号列表
     * @param 
     * @return List
     * */
	public List<UserRegisterBean> getAllUserRegister()throws RemoteException;
	
	/**
     * 得到所有人员帐号总数(用于后台管理列表)
     * @param Map<String,String> con_m
     * @return List
     * */
	public String getUserRegisterCount(Map<String,String> con_m)throws RemoteException;
	
	/**
     * 得到所有人员帐号列表(用于后台管理列表)
     * @param Map<String,String> con_m
     * @return List
     * */	
	public List<UserRegisterBean> getAllUserRegisterForDB(Map<String,String> con_m)throws RemoteException;
	
	/**
	 * 根据帐号名得到帐号对象
	 * 
	 * @param String user_name
	 *            
	 * @return UserRegisterBean
	 */
	public UserRegisterBean getUserRegisterBeanByUname(String user_name)throws RemoteException;
	
	/**
     * 插入帐号信息
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean insertRegister(UserRegisterBean urb,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 修改帐号信息
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateRegister(UserRegisterBean urb,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 修改帐号状态
     * @param int reg_status 
     * @param String reg_ids
     * @return boolean
     * */
	public boolean updateRegisterStatus(int reg_status,String reg_ids,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 删除帐号信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteRegisterByRID(String reg_ids,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 根据人员ID删除帐号信息
     * @param String　user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteRegisterByUserID(String user_ids,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 根据部门ID删除帐号信息
     * @param String　user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteRegisterByDeptID(String dept_ids,SettingLogsBean stl)throws RemoteException;
	
	/**
     * 根据人员ID批量修改人员密码
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updatePasswordByUserID(UserRegisterBean urb,SettingLogsBean stl)throws RemoteException;

		
	/* **********************帐号管理　结束******************************** */
	
	
	// 外部调用 使用  ----- 开始 
	public List<UserBean> getUserByOrgid(Long orgid) throws RemoteException;//得到该部门id下面的直属人员列表信息
	public UserBean getUserByUserid(Long userid) throws RemoteException;//通过人员id得到该人员信息
	public UserBean CheckUserLoginReturnUser(String username,String password) throws RemoteException;//登录并返回用户信息
	public DeptBean getOrgByUserid(Long userid) throws RemoteException;//通过用户ID得到用户所属部门
	public DeptBean getOrgByOrgid(long orgid) throws RemoteException;//通过部门ID得到部门对象
	public List<DeptBean> getChildrenOrg(long orgid) throws RemoteException;//通过部门ID得到下级部门
	public UserBean getUserByUname(String uname) throws RemoteException;//通过用户登录名得到用户对象
	public boolean updatePassword(Long userid, String password) throws RemoteException;//修改密码
	public Long getUserId(String username) throws RemoteException;//通过用户登录名得到用户ID
	// 外部调用 使用  ----- 结束


	/* **********************同步银海组织机构 开始******************************** */

    /**
     * 同步银海部门信息
     *
     * @param List<DeptBean>
     *            deptList
     * @return boolean
     */
    public boolean inserSynctDept(List<DeptBean> deptList) throws RemoteException;

    /**
     * 同步银海人员信息
     * @param List<UserBean> ubList
     * @param List<UserRegisterBean> urbList 帐号对象
     * @return boolean
     * */
    public boolean insertSyncUser(List<UserBean> ubList, List<UserRegisterBean> urbList) throws RemoteException;

	/**
	 * 增量同步用户获取最大id
	 * @return
	 */
	public int getMaxUserId();

	/**
	 * 增量同步组织机构获取最大id
	 * @return
	 */
	public int getMaxDeptId();

	/* **********************同步银海组织机构 结束******************************** */

}
