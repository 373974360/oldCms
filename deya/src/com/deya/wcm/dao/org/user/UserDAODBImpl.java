package com.deya.wcm.dao.org.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.CryptoTools;
import com.deya.util.RandomStrg;
import com.deya.util.SM4Utils;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.bean.org.user.UserLevelBean;
import com.deya.wcm.bean.org.user.UserRegisterBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.org.dept.DeptDAODBImpl;
import com.deya.wcm.db.DBManager;

/**
 *  组织机构用户管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 组织机构用户管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * *
 */

public class UserDAODBImpl implements IUserDAO{

	/**
     * 得到所有用户列表
     * @param
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllUserList(){
		return DBManager.queryFList("getAllUserList", "");
	}

	/**
     * 根据部门ID得到下面用户列表（用于用户管理界面）
     * @param Map conn_m　传递参数，包含dept_id,start_num,page_size,sort_name,sort_type,conn_name,conn_value
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List<UserBean> getUserListByDeptIDForDB(Map conn_m)
	{
		return DBManager.queryFList("getAllUserListByDeptID", conn_m);
	}


	/**
     * 根据部门ID得到下面用户总数（用于用户管理界面）
     * @param Map conn_m　传递参数，包含dept_id,con_name,con_value
     * @return List
     * */
	public String getUserCountByDeptIDForDB(Map<String, String> con_m)
	{
		return DBManager.getString("getUserCountByDeptIDForDB", con_m);
	}

	/**
     * 根据ID返回用户对象
     * @param String id
     * @return UserBean
     * */
	public UserBean getUserBeanByID(String id){
		return (UserBean)DBManager.queryFObj("getUserBeanByID", id);
	}

	/**
     * 插入用户信息
     * @param UserBean ub
     * @param UserRegisterBean urb 帐号对象
     * @param boolean isAddReg　是否添加帐号
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean insertUser(UserBean ub,UserRegisterBean urb,boolean isAddReg,SettingLogsBean stl){
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.USER_TABLE_NAME);
		ub.setUser_id(id);
		if(DBManager.insert("insert_user", ub))
		{
			PublicTableDAO.insertSettingLogs("添加","用户",id+"",stl);
			if(isAddReg)
			{
				urb.setUser_id(id);
				insertRegister(urb,stl);
			}
			return true;
		}else
			return false;
	}

	/**
     * 修改用户信息
     * @param UserBean ub
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateUser(UserBean ub,SettingLogsBean stl){
		if(DBManager.update("update_user", ub))
		{
			PublicTableDAO.insertSettingLogs("修改","用户",ub.getUser_id()+"",stl);
			return true;
		}
		else
			return false;
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
		String[] tempA = ids.split(",");
		Map<String,String> m = new HashMap<String,String>();
		try
		{
			for(int i=0;i<tempA.length;i++)
			{
				m.put("user_id", tempA[i]+"");
				m.put("sort", i+1+"");
				DBManager.update("sort_user", m);
			}
			PublicTableDAO.insertSettingLogs("保存排序","用户",ids+"",stl);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
		if(DBManager.update("move_user", m))
		{
			PublicTableDAO.insertSettingLogs("移动","用户",m.get("user_ids"),stl);
			return true;
		}
		else
			return false;
	}

	/**
     * 修改用户状态
     * @param int user_status
     * @param String user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public boolean updateUserStatus(int user_status,String user_ids,SettingLogsBean stl){
		Map<String, Comparable> m = new HashMap<String, Comparable>();
		m.put("user_status", user_status);
		m.put("ids", user_ids);
		if(DBManager.update("update_userStatus", m))
		{
			PublicTableDAO.insertSettingLogs("修改","用户状态",user_ids,stl);
			return true;
		}
		else
			return false;
	}

	/**
     * 删除用户信息
     * @param String　user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteUser(String user_ids,SettingLogsBean stl){
		Map<String, String> m = new HashMap<String, String>();
		m.put("ids", user_ids);
		if(DBManager.update("delete_user", m))
		{
			//删除部门管理员
			DeptDAODBImpl.deleteDeptManagerByUserID(user_ids,stl);
			PublicTableDAO.insertSettingLogs("删除","用户",user_ids,stl);
			return true;
		}else
			return false;
	}

	/**
     * 根据部门ID删除用户信息
     * @param String　detp_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteUserByDeptID(String detp_ids,SettingLogsBean stl){
		Map<String, String> m = new HashMap<String, String>();
		m.put("ids", detp_ids);
		if(DBManager.update("delete_userByDeptID", m))
		{
			PublicTableDAO.insertSettingLogs("删除","部门 用户",detp_ids,stl);
			return true;
		}else
			return false;
	}

	/* **********************用户级别管理　开始******************************** */
	/**
     * 得到所有用户级别列表
     * @param
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllUserLevelList(){
		return DBManager.queryFList("getAllUserLevelList", "");
	}

	/**
     * 根据ID返回用户级别对象
     * @param String id
     * @return UserBean
     * */
	public UserLevelBean getUserLevelBeanByID(String id){
		return (UserLevelBean)DBManager.queryFObj("getUserLevelBeanByID", id);
	}

	/**
     * 插入用户级别信息
     * @param UserLevelBean ulb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean insertUserLevel(UserLevelBean ulb,SettingLogsBean stl){
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.USERLEVEL_TABLE_NAME);
		ulb.setUserlevel_id(id);
		if(DBManager.insert("insert_userLevel", ulb))
		{
			PublicTableDAO.insertSettingLogs("添加","用户级别",id+"",stl);
			return true;
		}else
			return false;
	}

	/**
     * 修改用户级别信息
     * @param UserLevelBean ulb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateUserLevel(UserLevelBean ulb,SettingLogsBean stl){
		if(DBManager.update("update_userLevel", ulb))
		{
			PublicTableDAO.insertSettingLogs("修改","用户级别",ulb.getUserlevel_id()+"",stl);
			return true;
		}
		else
			return false;
	}


	/**
     * 删除用户级别信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteUserLevel(String ids,SettingLogsBean stl){
		Map<String, String> m = new HashMap<String, String>();
		m.put("ids", ids);
		if(DBManager.update("delete_userLevel", m))
		{
			PublicTableDAO.insertSettingLogs("删除","用户级别",ids,stl);
			return true;
		}else
			return false;
	}
	/* **********************用户级别管理　结束******************************** */

	/* **********************帐号管理　开始******************************** */
	/**
     * 得到所有帐号列表
     * @param
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllUserRegister(){
		return DBManager.queryFList("getAllUserRegister", "");
	}

	/**
     * 得到所有用户帐号总数(用于后台管理列表)
     * @param Map<String,String> con_m
     * @return List
     * */
	public String getUserRegisterCount(Map<String,String> con_m){
		return DBManager.getString("getUserRegisterCount", con_m);
	}

	/**
     * 得到所有用户帐号列表(用于后台管理列表)
     * @param
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List<UserRegisterBean> getAllUserRegisterForDB(Map<String,String> con_m)
	{
		return DBManager.queryFList("getAllUserRegisterForDB", con_m);
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
		return (UserRegisterBean)DBManager.queryFObj("getUserRegisterBeanByUname", user_name);
	}

	/**
     * 插入帐号信息
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean insertRegister(UserRegisterBean urb,SettingLogsBean stl){
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.USERREGISTER_TABLE_NAME);
		urb.setRegister_id(id);
		urb.setPassword(SM4Utils.encryptEcb(urb.getPassword()));
		if(DBManager.insert("insert_register", urb))
		{
			PublicTableDAO.insertSettingLogs("添加","帐号",id+"",stl);
			return true;
		}else
			return false;
	}

	/**
     * 修改帐号信息
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateRegister(UserRegisterBean urb,SettingLogsBean stl){
		urb.setPassword(SM4Utils.encryptEcb(urb.getPassword()));
		if(DBManager.update("update_register", urb))
		{
			PublicTableDAO.insertSettingLogs("修改","帐号",urb.getRegister_id()+"",stl);
			return true;
		}
		else
			return false;
	}

	/**
     * 修改帐号状态
     * @param int reg_status
     * @param String reg_ids
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public boolean updateRegisterStatus(int reg_status,String reg_ids,SettingLogsBean stl){
		Map<String, Comparable> m = new HashMap<String, Comparable>();
		m.put("register_status", reg_status);
		m.put("ids", reg_ids);
		if(DBManager.update("update_registerStatus", m))
		{
			PublicTableDAO.insertSettingLogs("修改","帐号",reg_ids,stl);
			return true;
		}
		else
			return false;
	}

	/**
     * 删除帐号信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteRegisterByRID(String reg_ids,SettingLogsBean stl){
		Map<String, String> m = new HashMap<String, String>();
		m.put("ids", reg_ids);
		if(DBManager.update("delete_registerByRID", m))
		{
			PublicTableDAO.insertSettingLogs("删除","帐号",reg_ids,stl);
			return true;
		}else
			return false;
	}

	/**
     * 根据用户ID删除帐号信息
     * @param String　user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteRegisterByUserID(String user_ids,SettingLogsBean stl){
		Map<String, String> m = new HashMap<String, String>();
		m.put("user_ids", user_ids);
		if(DBManager.update("delete_registerByUserID", m))
		{
			PublicTableDAO.insertSettingLogs("删除","帐号 用户",user_ids,stl);
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
	public boolean deleteRegisterByDeptID(String dept_ids,SettingLogsBean stl){
		Map<String, String> m = new HashMap<String, String>();
		m.put("dept_ids", dept_ids);
		if(DBManager.update("delete_registerByDeptID", m))
		{
			PublicTableDAO.insertSettingLogs("删除","帐号 部门",dept_ids,stl);
			return true;
		}else
			return false;
	}

	/**
     * 根据用户ID批量修改用户密码
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updatePasswordByUserID(UserRegisterBean urb,SettingLogsBean stl)
	{
		if(DBManager.update("update_passwordByUserID", urb))
		{
			PublicTableDAO.insertSettingLogs("修改","帐号密码 用户",urb.getUser_id()+"",stl);
			return true;
		}else
			return false;
	}
	/* **********************帐号管理　结束******************************** */

	public boolean insertSyncUser(List<UserBean> ubList, List<UserRegisterBean> urbList) {
        if(DBManager.delete("deleteUser",null)){
            if(DBManager.insert("insertUserBatch", ubList)){
                if(DBManager.delete("deleteRegisterUser",null)){
                    if(DBManager.insert("insertUserRegisterBatch", urbList))
                    {
                        System.out.println("**********************同步银海用户成功***********************");
                        return true;
                    }
                }
            }
        }
		return false;
	}
}
