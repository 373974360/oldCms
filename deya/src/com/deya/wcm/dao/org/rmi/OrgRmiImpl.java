package com.deya.wcm.dao.org.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.bean.org.dept.DeptLevelBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.bean.org.user.UserLevelBean;
import com.deya.wcm.bean.org.user.UserRegisterBean;
import com.deya.wcm.dao.org.OrgDAOFactory;
import com.deya.wcm.rmi.IOrgRmi;
import com.deya.wcm.services.org.user.UserRegisterManager;

public class OrgRmiImpl extends UnicastRemoteObject implements IOrgRmi{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7888454141880733613L;

	public OrgRmiImpl() throws RemoteException {
		super();
	}	
	
	/**
     * 得到部门ID,用于添加部门时给出ID值
     * @param 
     * @return List
     * */
	public int getDeptID()throws RemoteException
	{
		return OrgDAOFactory.getDeptDao().getDeptID();
	}
	
	/**
     * 得到所有部门列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllDeptList()throws RemoteException
	{
		return OrgDAOFactory.getDeptDao().getAllDeptList();
	}		
	
	/**
	 * 根据部门ID及查询条件从库中得到下级部门列表对象deep+1,参数有 dept_id,con_name,con_value
	 * 
	 * @param Map
	 *            con_m
	 * @return List
	 */
	public List<DeptBean> getChildDeptListForDB(Map<String,String> con_m)throws RemoteException
	{
		return OrgDAOFactory.getDeptDao().getChildDeptListForDB(con_m);
	}
	
	/**
     * 根据ID返回部门对象
     * @param String id
     * @return DeptBean
     * */
	public DeptBean getDeptBeanByID(String id)throws RemoteException
	{
		return OrgDAOFactory.getDeptDao().getDeptBeanByID(id);
	}
	
	/**
     * 插入部门信息
     * @param DeptBean db
     * @return boolean
     * */
	public boolean insertDept(DeptBean db,SettingLogsBean stl)throws RemoteException
	{
		return OrgDAOFactory.getDeptDao().insertDept(db,stl);
	}
	
	/**
     * 修改部门信息
     * @param DeptBean db
     * @return boolean
     * */
	public boolean updateDept(DeptBean db,SettingLogsBean stl)throws RemoteException
	{
		return OrgDAOFactory.getDeptDao().updateDept(db,stl);
	}
	
	/**
     * 移动部门
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public  boolean moveDept(Map<String,String> m,SettingLogsBean stl)throws RemoteException
	{
		return OrgDAOFactory.getDeptDao().moveDept(m,stl);
	}
	
	/**
     * 删除部门信息
     * @param String　ids
     * @return boolean
     * */
	public boolean deleteDept(String ids,SettingLogsBean stl)throws RemoteException
	{
		return OrgDAOFactory.getDeptDao().deleteDept(ids,stl);
	}
	
	/**
	 * 保存部门排序
	 * 
	 * @param String
	 *            ids 部门ID
	 * @return 
	 */
	public boolean saveDeptSort(String ids,SettingLogsBean stl)throws RemoteException
	{
		return OrgDAOFactory.getDeptDao().saveDeptSort(ids,stl);
	}
	
	/* **********************部门管理员管理　开始******************************** */
	/**
	 * 得到部门管理员列表
	 * 
	 * @param
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getAllDeptManagerList()throws RemoteException
	{
		return OrgDAOFactory.getDeptDao().getAllDeptManagerList();
	}
	
	/**
	 * 添加部门管理员
	 * 
	 * @param String
	 *            user_ids 所选为管理员的人员ID
	 * @param int
	 * 			  dept_id 部门ID	            
	 * @return boolean
	 */
	public boolean insertDetpManager(String user_ids,String dept_id,SettingLogsBean stl)throws RemoteException
	{
		return OrgDAOFactory.getDeptDao().insertDetpManager(user_ids,dept_id,stl);
	}
	
	/**
	 * 修改部门管理员
	 * 
	 * @param String
	 *            user_ids 所选为管理员的人员ID
	 * @param int
	 * 			  dept_id 部门ID	            
	 * @return boolean
	 */
	public boolean updateDetpManager(String user_ids,String dept_id,SettingLogsBean stl)throws RemoteException
	{
		return OrgDAOFactory.getDeptDao().updateDetpManager(user_ids,dept_id,stl);
	}
	
	/**
	 * 删除部门管理员
	 * 	 
	 * @param int
	 * 			  dept_ids 部门ID	            
	 * @return boolean
	 */
	public boolean deleteDeptManager(String user_ids,String dept_ids,SettingLogsBean stl)throws RemoteException
	{		
		return OrgDAOFactory.getDeptDao().deleteDeptManager(user_ids,dept_ids,stl);		
	}
	
	/* **********************部门管理员管理　结束******************************** */
	
/* **********************部门级别管理　开始******************************** */
	
	/**
     * 得到所有部门级别列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllDeptLevelList()throws RemoteException{
		return OrgDAOFactory.getDeptDao().getAllDeptLevelList();
	}
	
	/**
	 * 根据部门ID得到部门信息
	 * 
	 * @param String 
	 *            id
	 * @return DeptLevelBean
	 */
	public DeptLevelBean getDeptLevelBeanByID(String id)throws RemoteException{
		return OrgDAOFactory.getDeptDao().getDeptLevelBeanByID(id);
	}
	
	/**
     * 插入部门级别信息
     * @param DeptLevelBean dlb
     * @return boolean
     * */
	public boolean insertDeptLevel(DeptLevelBean dlb,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getDeptDao().insertDeptLevel(dlb,stl);
	}
	
	/**
     * 修改部门级别信息
     * @param DeptLevelBean dlb
     * @return boolean
     * */
	public boolean updateDeptLevel(DeptLevelBean dlb,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getDeptDao().updateDeptLevel(dlb,stl);
	}
	
	/**
     * 删除部门级别信息
     * @param String　ids
     * @return boolean
     * */
	public boolean deleteDeptLevel(String ids,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getDeptDao().deleteDeptLevel(ids,stl);
	}
	/* **********************部门级别管理　结束******************************** */
	
	/* **********************人员管理　开始******************************** */
	/**
     * 得到所有人员列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllUserList()throws RemoteException{
		return OrgDAOFactory.getUserDao().getAllUserList();
	}
	
	/**
     * 根据部门ID得到下面人员列表（用于用户管理界面）
     * @param Map conn_m　传递参数，包含dept_id,start_num,page_size,sort_name,sort_type,conn_name,conn_value
     * @return List
     * */	
	@SuppressWarnings("unchecked")
	public List getUserListByDeptIDForDB(Map<String,String> conn_m)throws RemoteException{
		return OrgDAOFactory.getUserDao().getUserListByDeptIDForDB(conn_m);
	}
	
	/**
     * 根据部门ID得到下面人员总数（用于用户管理界面）
     * @param Map conn_m　传递参数，包含dept_id,con_name,con_value
     * @return List
     * */
	public String getUserCountByDeptIDForDB(Map<String, String> conn_m)throws RemoteException
	{
		return OrgDAOFactory.getUserDao().getUserCountByDeptIDForDB(conn_m);
	}
	
	/**
     * 根据ID返回人员对象
     * @param String id
     * @return UserBean
     * */
	public UserBean getUserBeanByID(String id)throws RemoteException{
		return OrgDAOFactory.getUserDao().getUserBeanByID(id);
	}
	
	/**
	 * 保存用户排序
	 * 
	 * @param String
	 *            ids 用户ID
	 * @param HttpServletRequest request	           
	 * @return 
	 */
	public boolean saveUserSort(String user_ids,SettingLogsBean stl)
	{
		return OrgDAOFactory.getUserDao().saveUserSort(user_ids,stl);
	}
	
	/**
     * 插入人员信息
     * @param UserBean ub
     * @param UserRegisterBean urb 帐号对象
     * @param boolean isAddReg　是否添加帐号
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean insertUser(UserBean ub,UserRegisterBean urb,boolean isAddReg,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().insertUser(ub,urb,isAddReg,stl);
	}
	
	/**
     * 修改人员信息
     * @param UserBean ub
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateUser(UserBean ub,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().updateUser(ub,stl);
	}
	
	/**
     * 移动用户
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean moveUser(Map<String,String> m,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().moveUser(m,stl);
	}
	
	/**
     * 修改人员状态
     * @param int user_status
     * @param String user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateUserStatus(int user_status,String user_ids,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().updateUserStatus(user_status,user_ids,stl);
	}
	
	/**
     * 删除人员信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteUser(String ids,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().deleteUser(ids,stl);
	}
	
	/**
     * 根据部门删除人员信息
     * @param String　detp_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteUserByDeptID(String detp_ids,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().deleteUserByDeptID(detp_ids,stl);
	}
	/* **********************人员管理　结束******************************** */
	
	/* **********************人员级别管理　开始******************************** */
	/**
     * 得到所有人员级别列表
     * @param 
     * @return List
     * */
	public List<UserLevelBean> getAllUserLevelList()throws RemoteException{
		return OrgDAOFactory.getUserDao().getAllUserLevelList();
	}
	
	/**
     * 根据ID返回人员级别对象
     * @param String id
     * @return UserBean
     * */
	public UserLevelBean getUserLevelBeanByID(String id)throws RemoteException{
		return OrgDAOFactory.getUserDao().getUserLevelBeanByID(id);
	}
	
	/**
     * 插入人员级别信息
     * @param UserLevelBean ulb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean insertUserLevel(UserLevelBean ulb,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().insertUserLevel(ulb,stl);
	}
	
	/**
     * 修改人员级别信息
     * @param UserLevelBean ulb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateUserLevel(UserLevelBean ulb,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().updateUserLevel(ulb,stl);
	}
	
	
	/**
     * 删除人员级别信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteUserLevel(String ids,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().deleteUserLevel(ids,stl);
	}
	/* **********************人员级别管理　结束******************************** */
	
	/* **********************帐号管理　开始******************************** */
	/**
     * 得到所有帐号列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List<UserRegisterBean> getAllUserRegister()throws RemoteException{
		return OrgDAOFactory.getUserDao().getAllUserRegister();
	}
	
	/**
     * 得到所有人员帐号总数(用于后台管理列表)
     * @param Map<String,String> con_m
     * @return List
     * */
	public String getUserRegisterCount(Map<String,String> con_m){
		return OrgDAOFactory.getUserDao().getUserRegisterCount(con_m);
	}
	
	/**
     * 得到所有人员帐号列表(用于后台管理列表)
     * @param 
     * @return List
     * */	
	public List<UserRegisterBean> getAllUserRegisterForDB(Map<String,String> con_m)
	{
		return OrgDAOFactory.getUserDao().getAllUserRegisterForDB(con_m);
	}
	
	/**
	 * 根据帐号名得到帐号对象
	 * 
	 * @param String user_name
	 *            
	 * @return UserRegisterBean
	 */
	public UserRegisterBean getUserRegisterBeanByUname(String user_name)throws RemoteException{
		return OrgDAOFactory.getUserDao().getUserRegisterBeanByUname(user_name);
	}
	
	/**
     * 插入帐号信息
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean insertRegister(UserRegisterBean urb,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().insertRegister(urb,stl);
	}
	
	/**
     * 修改帐号信息
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateRegister(UserRegisterBean urb,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().updateRegister(urb,stl);
	}
	
	/**
     * 修改帐号状态
     * @param int reg_status 
     * @param String reg_ids
     * @return boolean
     * */
	public boolean updateRegisterStatus(int reg_status,String reg_ids,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().updateRegisterStatus(reg_status,reg_ids,stl);
	}
	
	/**
     * 删除帐号信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteRegisterByRID(String reg_ids,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().deleteRegisterByRID(reg_ids,stl);
	}
	
	/**
     * 根据人员ID删除帐号信息
     * @param String　user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteRegisterByUserID(String user_ids,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().deleteRegisterByUserID(user_ids,stl);
	}
	
	/**
     * 根据部门ID删除帐号信息
     * @param String　user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteRegisterByDeptID(String dept_ids,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().deleteRegisterByDeptID(dept_ids,stl);
	}
	
	/**
     * 根据人员ID批量修改人员密码
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updatePasswordByUserID(UserRegisterBean urb,SettingLogsBean stl)throws RemoteException{
		return OrgDAOFactory.getUserDao().updatePasswordByUserID(urb,stl);
	}
	/* **********************帐号管理　结束******************************** */

	
	// 外部调用 使用  ----- 开始
	
	//得到该部门id下面的直属人员列表信息
	public List<UserBean> getUserByOrgid(Long orgid) throws RemoteException {
		List<UserBean> list = new ArrayList<UserBean>();
		try{
			Map<String,String> conn_m = new HashMap<String,String>();
			conn_m.put("dept_id", orgid.toString()); 
			list = getUserListByDeptIDForDB(conn_m);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	//通过人员id得到该人员信息
	public UserBean getUserByUserid(Long userid) throws RemoteException {
		return getUserBeanByID(userid.toString());
	}

	//登录并返回用户信息
	public UserBean CheckUserLoginReturnUser(String username, String password) throws RemoteException {
		try{
			String ms = UserRegisterManager.checkUserLogin(username,password);
			if("0".equals(ms))
			{//登录成功
				UserRegisterBean userRegisterBean = getUserRegisterBeanByUname(username);
				return getUserBeanByID(userRegisterBean.getUser_id()+"");
			}else{
				return null;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
			
		
	}

	//通过用户ID得到用户所属部门
	public DeptBean getOrgByUserid(Long userid) throws RemoteException {
		DeptBean deptBean = null ;
		try{
			UserBean userBean = getUserBeanByID(userid.toString());
			deptBean = getDeptBeanByID(userBean.getDept_id()+"");
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return deptBean;
		}
	}

	//通过部门ID得到部门对象
	public DeptBean getOrgByOrgid(long orgid) throws RemoteException {
		return  getDeptBeanByID(orgid+"");
	}

	//通过部门ID得到下级部门
	public List<DeptBean> getChildrenOrg(long orgid) throws RemoteException {
		List<DeptBean> list = new ArrayList<DeptBean>();
		try{
			Map<String, String> conM = new HashMap<String, String>();
			conM.put("dept_id", orgid+""); 
			 list = getChildDeptListForDB(conM);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
		    return list;	
		}
	}

	//通过用户登录名得到用户对象
	public UserBean getUserByUname(String uname) throws RemoteException {
		UserRegisterBean userRegisterBean = getUserRegisterBeanByUname(uname);
		return getUserBeanByID(userRegisterBean.getUser_id()+"");
	}

	//修改密码
	public boolean updatePassword(Long userid, String password)
			throws RemoteException {
		boolean result = false;
		try{
			SettingLogsBean stl= null;
			UserRegisterBean urb = new UserRegisterBean();
			urb.setUser_id(Integer.parseInt(userid+""));
			urb.setPassword(password);
			result = updatePasswordByUserID(urb,stl);
		}catch (Exception e) {
			e.printStackTrace();
			result = false;
		}finally{
			return result;
		}
	}

	//通过用户登录名得到用户ID
	public Long getUserId(String username) throws RemoteException {
		// TODO Auto-generated method stub
		return Long.valueOf((getUserRegisterBeanByUname(username)).getUser_id());
	}

    // 外部调用 使用  ----- 结束

	/* **********************同步银海组织机构 开始******************************** */

    @Override
    public boolean inserSynctDept(List<DeptBean> deptList) throws RemoteException {
        return OrgDAOFactory.getDeptDao().inserSynctDept(deptList);
    }

    @Override
    public boolean insertSyncUser(List<UserBean> ubList, List<UserRegisterBean> urbList) throws RemoteException {
        return OrgDAOFactory.getUserDao().insertSyncUser(ubList,urbList);
    }

	@Override
	public int getMaxUserId() {
		return OrgDAOFactory.getUserDao().getMaxUserId();
	}

	@Override
	public int getMaxDeptId() {
		return OrgDAOFactory.getDeptDao().getMaxDeptId();
	}

	/* **********************同步银海组织机构 结束******************************** */
}
