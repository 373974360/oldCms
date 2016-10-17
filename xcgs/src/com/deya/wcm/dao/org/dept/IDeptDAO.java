package com.deya.wcm.dao.org.dept;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.bean.org.dept.DeptLevelBean;

public interface IDeptDAO {
	/**
     * 得到所有部门列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllDeptList();
	
	/**
     * 得到部门ID,用于添加部门时给出ID值
     * @param 
     * @return List
     * */
	public int getDeptID();
	
	/**
	 * 根据部门ID及查询条件从库中得到下级部门列表对象deep+1,参数有 dept_id,con_name,con_value
	 * 
	 * @param Map
	 *            con_m
	 * @return List
	 */
	public List<DeptBean> getChildDeptListForDB(Map<String,String> con_m);
	
	/**
     * 根据ID返回部门对象
     * @param String id
     * @return DeptBean
     * */
	public DeptBean getDeptBeanByID(String id);
	
	/**
     * 插入部门信息
     * @param DeptBean db
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public  boolean insertDept(DeptBean db,SettingLogsBean stl);
	
	/**
     * 修改部门信息
     * @param DeptBean db
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public  boolean updateDept(DeptBean db,SettingLogsBean stl);
	
	/**
     * 移动部门
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public  boolean moveDept(Map<String,String> m,SettingLogsBean stl);
	
	/**
     * 删除部门信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public  boolean deleteDept(String ids,SettingLogsBean stl);
	
	/**
	 * 保存部门排序
	 * 
	 * @param String
	 *            ids 部门ID
	 * @param SettingLogsBean stl           
	 * @return 
	 */
	public  boolean saveDeptSort(String ids,SettingLogsBean stl);
	
	/* **********************部门管理员管理　开始******************************** */
	/**
	 * 得到部门管理员列表
	 * 
	 * @param
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getAllDeptManagerList();
	
	/**
	 * 添加部门管理员
	 * 
	 * @param String
	 *            user_ids 所选为管理员的人员ID
	 * @param int
	 * 			  dept_id 部门ID	            
	 * @return boolean
	 */
	public boolean insertDetpManager(String user_ids,String dept_id,SettingLogsBean stl);
	
	/**
	 * 修改部门管理员
	 * 
	 * @param String
	 *            user_ids 所选为管理员的人员ID
	 * @param int
	 * 			  dept_id 部门ID	            
	 * @return boolean
	 */
	public boolean updateDetpManager(String user_ids,String dept_id,SettingLogsBean stl);
	
	/**
	 * 删除部门管理员
	 * 	 
	 * @param int
	 * 			  dept_ids 部门ID	            
	 * @return boolean
	 */
	public boolean deleteDeptManager(String user_ids,String dept_id,SettingLogsBean stl);
	
	/* **********************部门管理员管理　结束******************************** */
	
	/* **********************部门级别管理　开始******************************** */
	/**
     * 得到所有部门级别列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllDeptLevelList();
	
	/**
	 * 根据部门ID得到部门信息
	 * 
	 * @param String 
	 *            id
	 * @return DeptLevelBean
	 */
	public DeptLevelBean getDeptLevelBeanByID(String id);
	
	/**
     * 插入部门级别信息
     * @param DeptLevelBean dlb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean insertDeptLevel(DeptLevelBean dlb,SettingLogsBean stl);
	
	/**
     * 修改部门级别信息
     * @param DeptLevelBean dlb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean updateDeptLevel(DeptLevelBean dlb,SettingLogsBean stl);
	
	/**
     * 删除部门级别信息
     * @param String　ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public boolean deleteDeptLevel(String ids,SettingLogsBean stl);

		
	/* **********************部门级别管理　结束******************************** */
	
}
