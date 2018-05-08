package com.deya.wcm.services.org.dept;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.bean.org.dept.DeptLevelBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.org.OrgDAOFactory;
import com.deya.wcm.dao.org.dept.IDeptDAO;

/**
 * 组织机构部门级别管理逻辑处理类.   
 * <p>
 * Title: CicroDate
 * </p>
 * <p>
 * Description: 组织机构部门级别管理逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: Cicro
 * </p>
 * @author zhuliang
 * @version 1.0 *
 */

public class DeptLevelManager implements ISyncCatch{
	private static TreeMap<String, DeptLevelBean> deptLevel_Map = new TreeMap<String, DeptLevelBean>();	
	
	private static IDeptDAO deptDao = OrgDAOFactory.getDeptDao();
	
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
		deptLevel_Map.clear();
		List<DeptLevelBean> deptLevel_list = deptDao.getAllDeptLevelList();
		
		if (deptLevel_list != null && deptLevel_list.size() > 0) {
			for (int i = 0; i < deptLevel_list.size(); i++) {
				deptLevel_Map.put(deptLevel_list.get(i).getDeptlevel_id() + "", deptLevel_list
						.get(i));				
			}
		}
	}
	
	/**
	 * 初始加载部门级别信息
	 * 
	 * @param
	 * @return
	 */
	
	public static void reloadDeptLevel() {
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.dept.DeptLevelManager");
	}
	
	/**
	 * 得到所有部门级别总数
	 * 
	 * @param 
	 *            
	 * @return String
	 */
	public static String getDeptLevelCount()
	{
		return deptLevel_Map.size()+"";
	}
	
	/**
	 * 得到所有部门级别信息列表
	 * 
	 * @param 
	 *            
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<DeptLevelBean> getDeptLevelList()
	{
		List<DeptLevelBean> deptLevel_list = new ArrayList<DeptLevelBean>();
//		定义一个临时的TreeMap，因为部门要按Deptlevel_value级别值排序，所以先把得到的部门级别值做为key值，让其自动排序，再循环组织成列表
		TreeMap<Integer, DeptLevelBean> temp_dept_map = new TreeMap();
		
		Iterator iter = deptLevel_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			DeptLevelBean dbl = deptLevel_Map.get((String) entry.getKey());
			temp_dept_map.put(dbl.getDeptlevel_value(), dbl);		
		}
		
		Iterator iter2 = temp_dept_map.entrySet().iterator();
		while (iter2.hasNext()) {
			Map.Entry entry = (Map.Entry) iter2.next();
			deptLevel_list.add((DeptLevelBean)entry.getValue());
		}
		
		return deptLevel_list;
	}
	
	/**
	 * 得到所有部门级别信息集合
	 * 
	 * @param 
	 *            
	 * @return Map
	 */
	public static Map<String, DeptLevelBean> getDeptLevelMap()
	{
		return deptLevel_Map;
	}
	
	/**
	 * 根据部门级别ID得到部门级别信息
	 * 
	 * @param String 
	 *            id 部门级别ID
	 * @return DeptLevelBean
	 * 			  部门级别Bean
	 */
	public static DeptLevelBean getDeptLevelBeanByID(String id) {
		
		if (deptLevel_Map.containsKey(id)) {
			return deptLevel_Map.get(id);
		} else {
			DeptLevelBean dlb = deptDao.getDeptLevelBeanByID(id);
			if (dlb != null)
				deptLevel_Map.put(id, dlb);
			return dlb;
		}
	}
	
	/**
	 * 插入部门级别信息
	 * 
	 * @param DeptLevelBean
	 *            db
	 * @param SettingLogsBean
	 *            stl 操作日志对象
	 * @return boolean
	 */
	public static boolean insertDeptLevel(DeptLevelBean dlb,SettingLogsBean stl) {
		//首先判断该级别是否已经存在
		if(getDeptLevelCountByLevel(0,dlb.getDeptlevel_value()))
		{
			System.out.println("updateDeptLevel : this deptLevel value is exist");
			return false;
		}else{
			if (deptDao.insertDeptLevel(dlb,stl)) {
				reloadDeptLevel();
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
     * 修改部门级别信息
     * @param DeptLevelBean dlb
     * @return boolean
     * */
	public static boolean updateDeptLevel(DeptLevelBean dlb,SettingLogsBean stl) {		
//		首先判断该级别是否已经存在
		if(getDeptLevelCountByLevel(dlb.getDeptlevel_id(),dlb.getDeptlevel_value()))
		{
			System.out.println("updateDeptLevel : this deptLevel value is exist");
			return false;
		}
		else
		{
			if (deptDao.updateDeptLevel(dlb,stl)) {
				reloadDeptLevel();
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
     * 删除部门级别信息
     * @param String　ids
     * @return boolean
     * */
	public static boolean deleteDeptLevel(String ids,SettingLogsBean stl) {		
		if (deptDao.deleteDeptLevel(ids,stl)) {
			reloadDeptLevel();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 删除部门级别，如果部门级别下面关联有部门不能删除
	 * @param ids
	 * 		部门级别ID,多个ID用","分隔
	 * @param stl
	 * @return
	 */
	public static boolean deleteVoidDeptLevel(String ids,SettingLogsBean stl)
	{
		boolean flg = true;
		String[] arrIDS = ids.split(",");
		for(int i=0; i<arrIDS.length; i++)
		{
			// 检查是否有部门和部门级别关联
			if(isExistUser(arrIDS[i]) != 0)
			{
				flg = false;
			}
		}
		if(flg)
		{
			flg = deleteDeptLevel(ids, stl);
		}
		return flg;
	}
	
	/**
	 * 查看deptlevel关联的用户数
	 * @param id
	 * 		userlevel_id
	 * @return
	 * 		deptlvevl关联的用户个数
	 */
	public static int isExistUser(String id)
	{
		String value = deptLevel_Map.get(id).getDeptlevel_value()+"";
		int cnt =0;
		Map<String, DeptBean> map = DeptManager.getDeptMap();
		Iterator<DeptBean> it = map.values().iterator();
		while(it.hasNext())
		{
			DeptBean ub = it.next();
			if(value.equals(ub.getDeptlevel_value()))
			{
				cnt++;
			}
		}
		return cnt;
	}
	
	/**
     * 根据该级别查到此级别的个数
     * 用于判断部门级别value是否重复
     * @param int level_value
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean getDeptLevelCountByLevel(int id,int level_value)
	{		
		Iterator iter = deptLevel_Map.entrySet().iterator();
		int count = 0;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			DeptLevelBean dlb = deptLevel_Map.get((String) entry.getKey());			
			if(dlb.getDeptlevel_value() == level_value && (id == 0 || dlb.getDeptlevel_id() != id))
				count += 1;	
		}		
		return count > 0;
	}
	
	public static void main(String args[]) {
		//insertDeptLevelTest();
		//updateDeptLevelTest();
		//deleteDeptLevelTest();
		List<DeptLevelBean> l = getDeptLevelList();
		for(int i=0;i<l.size();i++)
		{
			System.out.println(l.get(i).getDeptlevel_id());
		}
		
	}
	
	public static void insertDeptLevelTest(){
		DeptLevelBean dlb = new DeptLevelBean();
		dlb.setDeptlevel_value(2);
		dlb.setDeptlevel_name("第一级");
		dlb.setDeptlevel_memo("第一级备注");
		insertDeptLevel(dlb,new SettingLogsBean());
	}
	
	public static void updateDeptLevelTest(){
		DeptLevelBean dlb = new DeptLevelBean();
		dlb.setDeptlevel_id(6);
		dlb.setDeptlevel_value(2);
		dlb.setDeptlevel_name("第二级");
		dlb.setDeptlevel_memo("第二级备注");
		updateDeptLevel(dlb,new SettingLogsBean());
	}
	
	public static void deleteDeptLevelTest()
	{
		deleteDeptLevel("1,6",new SettingLogsBean());
	}
}
