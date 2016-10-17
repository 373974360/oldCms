package com.deya.wcm.services.org.dept;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.bean.org.dept.DeptLevelBean;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.org.user.UserLogin;

public class DeptRPC {
	public static String getDeptName(String dept_id)
	{
		return DeptManager.getDeptName(dept_id);
	}
	
	/**
     * 得到部门ID,用于添加部门时给出ID值
     * @param 
     * @return List
     * */
	public static int getDeptID(){
		return DeptManager.getDeptID();
	}
	
	/**
     * 返回部门列表页面地址
     * @param 
     * @return List
     * */
	public static String getDeptListPage()
	{
		return DeptManager.getDeptListPage();
	}
	
	/**
	 * 得到所有部门节点json字符串，用于显示部门管理树
	 * 
	 * @param 
	 *            
	 * @return String
	 */
	public static String getAllDeptTreeJsonStr()
	{
		return DeptManager.deptListToJsonStrByUserID(DeptManager.getRootDeptID()+"");
	}
	
	/**
	 * 根据登录用户ID得到其能管理的部门,并组织成字符串
	 * 
	 * @param String
	 *            user_id  
	 * @return String
	 */
	public static String getDeptTreeByUser(HttpServletRequest request)
	{
		LoginUserBean lub = UserLogin.getUserBySession(request);		
		return DeptManager.getDeptTreeByUser(lub.getUser_id()+"");		
	}
	
	/**
	 * 根据部门ID得到部门信息
	 * 
	 * @param String
	 *            id
	 * @return DeptBean
	 */
	public static DeptBean getDeptBeanByID(String id) {
		return DeptManager.getDeptBeanByID(id);
	}
	
	/**
	 * 根据部门ID得到下级部门列表对象deep+1
	 * 
	 * @param String
	 *            dept_id
	 * @return List
	 */
	public static List<DeptBean> getChildDeptListByID(String dept_id) {
		return DeptManager.getChildDeptListByID(dept_id);
	}
	
	/**
	 * 根据部门ID及查询条件从库中得到下级部门列表对象deep+1,参数有 dept_id,con_name,con_value
	 * 
	 * @param Map
	 *            con_m
	 * @return List
	 */
	public static List<DeptBean> getChildDeptListForDB(Map<String,String> con_m) {
		return DeptManager.getChildDeptListForDB(con_m);
	}
	
	/**
	 * 插入部门信息
	 * 
	 * @param DeptBean
	 *            db
	 * @param HttpServletRequest request
	 * @return boolean
	 */
	public static boolean insertDept(DeptBean db,HttpServletRequest request) {
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return DeptManager.insertDept(db,stl);
		}else
			return false;
	}
	
	/**
	 * 修改部门信息
	 * 
	 * @param DeptBean
	 *            db
	 * @param HttpServletRequest request 
	 * @return boolean
	 */
	public static boolean updateDept(DeptBean db,HttpServletRequest request) {
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return DeptManager.updateDept(db,stl);
		}else
			return false;
	}
	
	/**
     * 移动部门
     * @param String parent_id
     * @param String dept_ids
     * @return boolean
     * */
	public static boolean moveDept(String parent_id,String dept_ids,HttpServletRequest request) {
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return DeptManager.moveDept(parent_id,dept_ids,stl);
		}else
			return false;
	}
	
	/**
	 * 保存部门排序
	 * 
	 * @param String
	 *            ids 部门ID
	 * @param HttpServletRequest request	           
	 * @return 
	 */
	public static boolean saveDeptSort(String dept_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return DeptManager.saveDeptSort(dept_ids,stl);
		}else
			return false;
	}
	
	
	
	/**
	 * 删除部门
	 * 
	 * @param String
	 *            ids
	 * @param HttpServletRequest request            
	 * @return boolean
	 */
	public static boolean deleteDept(String ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{		
			return DeptManager.deleteDept(ids,stl);
		}else
			return false;
	}
	/************************* 部门管理员 开始 ********************************************/
	/**
	 * 得到部门管理员列表
	 * 
	 * @param　String dept_id
	 * @return List 
	 */
	public static List<UserBean> getDeptManagerUserList(String dept_id)
	{
		return DeptManUser.getDeptManagerUserList(dept_id);
	}
	
	/**
	 * 根据部门ID得到此部门所有的管理人员ID
	 * 
	 * @param　String dept_id
	 * @return String 部门管理员，","+用户ID+","号分隔，可多个　如　,12,13,
	 */
	public static String getManagerIDSByDeptID(String dept_id)
	{
		return DeptManUser.getManagerIDSByDeptID(dept_id);
	}
		
	/**
	 * 添加部门管理员(此方法提供给单独添加部门管理员的方法，在添加部门时无需再调用此方法)
	 * 
	 * @param String
	 *            user_ids 所选为管理员的人员ID
	 * @param int
	 * 			  dept_id 部门ID	            
	 * @return boolean
	 */
	public static boolean insertDetpManager(String user_ids,String dept_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return DeptManUser.insertDetpManager(user_ids,dept_id,stl);
		}else
			return false;
	}	
	
	/**
	 * 修改部门管理员,用于添加管理员的弹出窗口,选删除所有管理员,再添加 
	 * 
	 * @param String
	 *            user_ids 所选为管理员的人员ID
	 * @param String
	 *            old_dept_manager 原有的部门字管理员ID
	 * @param int
	 * 			  dept_id 部门ID	            
	 * @return boolean
	 */
	public static boolean updateDetpManager(String user_ids,String old_dept_manager,String dept_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return DeptManUser.updateDetpManager(user_ids,old_dept_manager,dept_id,stl);
		}else
			return false;
		
	}
	
	/**
	 * 删除部门管理员
	 * 	 
	 * @param int
	 * 			  dept_ids 部门ID	            
	 * @return boolean
	 */
	public static boolean deleteDeptManager(String user_ids,String dept_id,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return DeptManUser.deleteDeptManager(user_ids,dept_id,stl);
		}else
			return false;
	}
	
	/************************* 部门管理员 结束 ********************************************/
	
	/************************* 部门级别处理 开始******************************************/
	
	/**
	 * 取得部门级别列表
	 */
	public static List<DeptLevelBean> getDeptLevelList()
	{
		return DeptLevelManager.getDeptLevelList();
	}
	
	/**
	 * 根据部门级别ID取得部门级别Bean
	 * @param	id
	 * 		部门级别ID
	 * @return	DeptLevelBean
	 * 		 部门级别Bean
	 */
	public static DeptLevelBean getDeptLevelBeanByID(String id) 
	{
		return DeptLevelManager.getDeptLevelBeanByID(id);
	}
	
	/**
	 * 新建部门级别，如果部门级别value冲突，新建失败
	 * @param dlb
	 * 		部门级别Bean
	 * @param request
	 * @return true 成功| false 失败
	 */
	public static boolean insertDeptLevel(DeptLevelBean dlb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return DeptLevelManager.insertDeptLevel(dlb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 更新部门级别
	 * @param dlb	部门级别Bean
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean updateDeptLevel(DeptLevelBean dlb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return DeptLevelManager.updateDeptLevel(dlb, stl);
		}
		else
		{
			return false;
		}
	}

	/**
	 * 删除部门级别
	 * 如果部门级别下面关联有部门，此条数据不能删除
	 * 多个部门级别，只有所有的部门级别都没有关联部门才能删除
	 * @param ids
	 * 		部门级别ID,多个用","分隔
	 * @param request
	 * @return
	 * 		true 删除成功 |false 删除失败
	 */
	public static boolean deleteDeptLevel(String ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return DeptLevelManager.deleteVoidDeptLevel(ids, stl);
		}
		else
		{
			return false;
		}
	}
	
	/************************* 部门级别处理结束*******************************************/
}
