package com.deya.wcm.services.org.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.desktop.DeskTopBean;
import com.deya.wcm.bean.org.user.SMUserBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.bean.org.user.UserLevelBean;
import com.deya.wcm.bean.org.user.UserRegisterBean;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.server.LicenseCheck;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.cms.info.InfoDesktop;
import com.deya.wcm.services.org.dept.DeptManager;
import com.deya.wcm.services.org.desktop.DesktopManager;

public class UserManRPC {
	/**
     * 得到所具有的应用系统
     * @param List<OperateBean> all_oper_list
     * @return String
     * */
	public static String getAppForLicense()
	{
		return LicenseCheck.getAppForLicense();
	}
	
	/**
	 * 根据用户ID得到此用户所能管理的应用系统树
	 * 
	 * @param int user_id
	 * @return
	 */
	public static String getAppJSONStrByUserID(String user_id)
	{
		return UserOptManager.getAppJSONStrByUserID(user_id);
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
	public static List<UserBean> getUserListByDeptID(String dept_id)
	{
		return UserManager.getUserListByDeptID(dept_id);
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
		return UserRegisterManager.checkUserLogin2(user_name, pass_word);
	}
	
	/**
     * 根据人员ID批量修改人员密码
     * @param String　user_id
     * @param String　password
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updatePasswordByUserID(int user_id,String password,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{	
			return UserRegisterManager.updatePasswordByUserID(user_id,password,stl);
		}else
			return false;
	}
	
	/**
	 * 得到精简的用户信息
	 * 
	 * @param 
	 *            
	 * @return Map
	 */
	public static Map<String, SMUserBean> getSimpleUserMap(){
		return UserManager.getSimpleUserMap();
	}
	
	/**
     * 根据用户ID返回用户真实姓名
     * @param String id
     * @return String
     * */
	public static String getUserRealName(String id)
	{
		return UserManager.getUserRealName(id);
	}
	
	/**
     * 根据部门ID得到下面人员列表（用于用户管理界面）
     * @param Map conn_m　传递参数，包含dept_id,start_num,page_size,sort_name,sort_type,con_name,con_value
     * @return List
     * */
	public static List<UserBean> getUserListByDeptIDForDB(Map<String,String> con_m)
	{
		return UserManager.getUserListByDeptIDForDB(con_m);			
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
		return UserManager.getUserMap();		
	}
	
	/**
	 * 根据用户ID取得部门名称
	 * @param paraList
	 * 		List<UserRegisterBean>
	 * @return Map<integer, String>
	 * 		key=userID,value=部门名称
	 */
	public static Map<Integer, String> getUserDeptList(List<UserRegisterBean> paraList)
	{
		return UserManager.getUserDeptList(paraList);
	}
	
	/**
     * 根据部门ID得到下面人员总数（用于用户管理界面）
     * @param Map conn_m　传递参数，包含dept_id,con_name,con_value
     * @return List
     * */
	public static String getUserCountByDeptIDForDB(Map<String, String> con_m)
	{
		return UserManager.getUserCountByDeptIDForDB(con_m);
	}
	
	/**
	 * 根据部门ID得到下面所有的人员个数,可输入多个部门（用于列表显示个数）
	 * 
	 * @param String
	 *            dept_id 部门ID   
	 * @return String
	 */
	public static String getUserCountByDeptID(String dept_id)
	{
		return UserManager.getUserCountByDeptID(dept_id);
	}
	
	/**
     * 根据ID返回所有人员对象信息,用于人员管理中取得用户所有资料
     * @param String id
     * @return UserBean
     * */
	public static UserBean getAllUserInfoByID(String user_id){
		return UserManager.getAllUserInfoByID(user_id);
	}
	
	/**
     * 插入人员信息
     * @param UserBean ub
     * @param UserRegisterBean urb 帐号对象
     * @param boolean isAddReg　是否添加帐号
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertUser(UserBean ub,UserRegisterBean urb,boolean isAddReg,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{			
			return UserManager.insertUser(ub,urb,isAddReg,stl);
		}else
			return false;
	}
	
	/**
     * 修改人员信息
     * @param UserBean ub
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateUser(UserBean ub,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{			
			return UserManager.updateUser(ub,stl);
		}else
			return false;
	}
	
	/**
     * 移动用户
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean moveUser(Map<String,String> m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{			
			return UserManager.moveUser(m,stl);
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
	public static boolean updateUserStatus(int user_status,String user_ids,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{			
			return UserManager.updateUserStatus(user_status,user_ids,stl);
		}else
			return false;
	}
	
	/**
     * 删除人员信息
     * @param String　user_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteUser(String user_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{			
			return UserManager.deleteUser(user_ids,stl);
		}else
			return false;
	}
	
	/**
	 * 根据人员ID得到帐号列表
	 * 
	 * @param 
	 *            
	 * @return List
	 */	
	public static List<UserRegisterBean> getUserRegisterListByUserID(int user_id)
	{
		return UserRegisterManager.getUserRegisterListByUserID(user_id);
	}
	
	/**
	 * 判断帐号名是否存在
	 * 
	 * @param user_name
	 *            
	 * @return boolean
	 */	
	public static boolean registerISExist(String user_name,int reg_id)
	{
		return UserRegisterManager.registerISExist(user_name,reg_id);
	}
	
	/**
	 * 保存用户排序
	 * 
	 * @param String
	 *            ids 用户ID
	 * @param HttpServletRequest request	           
	 * @return 
	 */
	public static boolean saveUserSort(String user_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return UserManager.saveUserSort(user_ids,stl);
		}else
			return false;
	}
	
	/**
     * 得到所有人员帐号总数(用于后台管理列表)
     * @param Map<String,String> con_m
     * @return List
     * */
	public static String getUserRegisterCount(Map<String,String> con_m)
	{
		return UserRegisterManager.getUserRegisterCount(con_m);
	}
	
	/**
     * 得到所有人员级别列表(用于后台管理列表)
     * @param Map<String,String> con_m
     * @return List
     * */	
	public List<UserRegisterBean> getAllUserRegisterForDB(Map<String,String> con_m)
	{
		return UserRegisterManager.getAllUserRegisterForDB(con_m);
	}
	
	/**
     * 插入帐号信息
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRegister(UserRegisterBean urb,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return UserRegisterManager.insertRegister(urb,stl);
		}else
			return false;
	}
	
	/**
     * 修改帐号信息
     * @param UserRegisterBean urb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateRegister(UserRegisterBean urb,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return UserRegisterManager.updateRegister(urb,stl);
		}else
			return false;
	}
	
	/**
     * 修改帐号状态
     * @param int reg_status 
     * @param String reg_ids
     * @return boolean
     * */
	public static boolean updateRegisterStatus(int reg_status,String reg_ids,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return UserRegisterManager.updateRegisterStatus(reg_status,reg_ids,stl);
		}else
			return false;
	}
	
	/**
     * 删除帐号信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteRegisterByRID(String reg_ids,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return UserRegisterManager.deleteRegisterByRID(reg_ids,stl);
		}else
			return false;
	}
	
	/**
	 * 取得所有用户级别
	 * @return
	 * 		List 包含用户级别Bean的List
	 */
	public static List<UserLevelBean> getUserLevelList()
	{
		return UserLevelManager.getUserLevelList();
	}
	
	/**
	 * 取得用户级别条数
	 * @return
	 * 		String 用户级别条数
	 */
	public static String getUserLevelCount()
	{
		return UserLevelManager.getUserLevelCount();
	}
	
	/**
	 * 新建用户级别
	 * @param ulb 用户级别Bean
	 * @param request
	 * @return
	 * 		true 删除成功 |false 删除失败
	 */
	public static boolean insertUserLevel(UserLevelBean ulb,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return UserLevelManager.insertUserLevel(ulb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 更新用户级别表
	 * @param ulb
	 * 		用户级别Bean
	 * @param request
	 * @return
	 * 		true 删除成功 |false 删除失败
	 */
	public static boolean updateUserLevel(UserLevelBean ulb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return UserLevelManager.updateUserLevel(ulb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除用户级别--根据用户级别ID(可有多个)
	 * 如果用户级别下面关联有用户，此条数据不能删除
	 * 多个ID的情况，只有所有用户级别都没有关联用户才能删除
	 * @param ids
	 * 		用户级别ID 多个之间用","隔开
	 * @param request
	 * @return
	 * 		true 删除成功 |false 删除失败
	 */
	public static boolean deleteUserLevel(String ids, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return UserLevelManager.deleteVoidUserLevel(ids, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 根据用户级别ID取得用户级别Bean
	 * @param id
	 * 		用户级别ID
	 * @return
	 * 		用户级别Bean
	 */
	public static UserLevelBean getUserLevelBeanByID(String id)
	{
		return UserLevelManager.getUserLevelBeanByID(id);
	}
	
	/**
     * 根据该级别查到此级别的个数
     * 用于判断用户级别value是否重复
     * @param int level_value
     * @return boolean
     * */	
	public static boolean getUserLevelCountByLevel(int id,int level_value)
	{
		return UserLevelManager.getUserLevelCountByLevel(id,level_value);
	}
	
	/**************************** 用户桌面设置 开始  *****************************************/
	/**
     * 根据用户ID得到配置列表
     * @param int user_id
     * @return List
     * */
	public static List<DeskTopBean> getUserDesktop(int user_id)
	{
		return DesktopManager.getUserDesktop(user_id);
	}
	
	/**
     * 插入配置信息
     * @param  String user_id
     * @param  List<DeskTopBean> l
     * @return boolean
     * */
	public static boolean insertUserDesktop(String user_id,List<DeskTopBean> l)
	{
		return DesktopManager.insertUserDesktop(user_id, l);
	}
	
	/**
     * 根据用户ID得到所有的站点对象包括该用户所在的用户组
     * @param String user_id 
     * @param String app_id
     * @return List
     * */
	public static List<SiteBean> getAllUserSiteObjList(String user_id,String app_id)
	{
		return UserLogin.getAllUserSiteObjList(user_id, app_id);
	}
	
	/**
     * 根据用户ID得到所有的节点对象包括该用户所在的用户组
     * @param String user_id 
     * @param String app_id
     * @return List
     * */
	public static List<GKNodeBean> getAllUserGKNodeObjList(String user_id,String app_id)
	{
		return UserLogin.getAllUserGKNodeObjList(user_id, app_id);
	}
	
	
	/**************************** 用户桌面设置 结束  *****************************************/
	
	public static void main(String args[])
	{
		System.out.println(getUserDesktop(1));
	}
}
