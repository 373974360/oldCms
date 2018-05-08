/**
 * 
 */
package com.deya.wcm.services.appeal.cpDept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.wcm.bean.appeal.cpDept.CpDeptBean;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appeal.cpDept.CpDeptDAO;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.services.appeal.cpUser.CpUserManager;

/**
 * @author Administrator
 *
 */
public class CpDeptManager implements ISyncCatch{

	private static TreeMap<String,CpDeptBean> dept_map = new TreeMap<String,CpDeptBean>();
	private static int root_dept_id = 1;
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		//查得所有
		List<CpDeptBean> dept_list = CpDeptDAO.getAllCpDeptBySort();
		dept_map.clear();
		if(dept_list != null && dept_list.size() > 0){
			for(int i = 0; i < dept_list.size(); i++){
				dept_map.put(dept_list.get(i).getDept_id()+"", dept_list.get(i));
			}
		}
	}
	
	/**
	 * 载入所有 注册机构 放入 map
	 */
	public static void loadCpdept(){
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appeal.cpDept.CpDeptManager");
	}
	
	/**
	 * 按照 parent_id, sort_id asc的   所有注册机构
	 * @return
	 */
	public static List<CpDeptBean> getAllCpDeptBySort(){
		return CpDeptDAO.getAllCpDeptBySort();
	}
	
	/**
	 * 新增  注册机构
	 * @param dept
	 * @return
	 */
	public static boolean insertCpdept(CpDeptBean dept){
		int deptId = PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_DEPT_TABLE_NAME);
		dept.setDept_id(deptId);
		dept = setLevelAndPosition(dept);
		if( CpDeptDAO.insertCpdept(dept)){
			loadCpdept();
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 为当前部门追溯节点深度 和 节点路径 
	 * @param dept
	 * @return
	 */
	public static CpDeptBean setLevelAndPosition(CpDeptBean dept){
		
		String deptPosition = "$"+dept.getDept_id()+"$";
		int deptLevel = 0;
		
		int p_id = dept.getParent_id();
		while(p_id!=0){
			CpDeptBean pDept = getCpDeptBean(p_id);
			p_id = pDept.getParent_id();
				
			//计算深度
			deptLevel++;
			//计算路径
			deptPosition = "$"+pDept.getDept_id()+deptPosition;
			
		}
		dept.setDept_level(deptLevel);
		dept.setDept_position(deptPosition);
		return dept;
	}
	
	/**
	 * 删除  注册机构
	 * @param dept_ids 以","号分隔的id字符串
	 * @return
	 */
	public static boolean deleteCpdept(String dept_ids){
		//包含子部门的ID字符串
		String allDeptIDs = "";
		
		//有可能多选 故需要切割
		String[] ids = dept_ids.split(",");
		
		for(int i = 0 ; i < ids.length ; i++){
			
			allDeptIDs += getChildDeptIds(ids[i]);
			if(i<ids.length-1){
				allDeptIDs += ",";
			}
		}
	
		if( CpDeptDAO.deleteCpdept(allDeptIDs)){
			loadCpdept();
			//删除关联用户
			String user_ids = CpUserManager.getUserIdsByDeptID(dept_ids);
			if(user_ids != null && !"".equals(user_ids))
				CpUserManager.deleteCpUser(dept_ids, user_ids, null);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改注册机构
	 * @param dept
	 * @return
	 */
	public static boolean updateCpDept(CpDeptBean dept){
		if( CpDeptDAO.updateCpDept(dept)){
			loadCpdept();
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 保存排序
	 * @param deptIds
	 * @return
	 */
	public static boolean saveDeptSort(String deptIds) {
		if( CpDeptDAO.saveDeptSort(deptIds)){
			loadCpdept();
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 按父ID与排序id排序的
	 * @return
	 */
	public static List<CpDeptBean> getAllCpDeptList(){
		return CpDeptDAO.getCpDeptList();
	}
	
	/**
	 * 按条件 分页 展示所有注册机构 （没用到）
	 * @param m    分页参数
	 * @return
	 */
	public static List<CpDeptBean> getCpDeptList(){
		return CpDeptDAO.getCpDeptList();
	}
	
	/**
	 * 按条件 分页 得到所有注册机构的总数 （没用到）
	 * @param m
	 * @return
	 */
	public static String getCpDeptCount(Map<String,String> m){
		return CpDeptDAO.getCpDeptCount(m);
	}
	
	/**
	 * 根据部门ID得到部门名称
	 * 
	 * @param String
	 *            id
	 * @return String
	 */
	public static String getCpDeptName(int id)
	{
		CpDeptBean cdb = getCpDeptBean(id);
		if(cdb != null)
		{
			return cdb.getDept_name();
		}
		else
			return "";
	}
	
	/**
	 * 根据部门ID得到部门信息
	 * 
	 * @param String
	 *            id
	 * @return DeptBean
	 */
	public static CpDeptBean getCpDeptBean(int id) {
		String dept_id=id+"";
		if(dept_id == null || "".equals(dept_id))
			return null;
		
		if (dept_map.containsKey(dept_id)) {
			return dept_map.get(dept_id);
		}
		// map中没有则取数据中数据
		else 
		{
			CpDeptBean dept = CpDeptDAO.getCpDeptBean(dept_id);
			if (dept != null)
			{
				dept_map.put(dept_id, dept);
			}
			return dept;
		}
	}
	
	/**
	 * 拼写 机构树 json字符串  （ 暂时没有用node参数）
	 * @param node
	 * @return
	 */
	public static String getDeptTreeJsonStr(int node){
		String jsonStr= "["+getDeptTreeJsonElement(root_dept_id)+"]";
		return jsonStr;
	}
	
	/**
	 * 递归得到指定部门节点下树型的json字符串  【树形】
	 * @param root_dept_id
	 * @return
	 */
	public static String getDeptTreeJsonElement(int root_dept_id){
		
		String jsonStr = "";
		CpDeptBean dept = getCpDeptBean(root_dept_id);
		if(dept != null){
			jsonStr =  "{\"id\":" + dept.getDept_id() 
					  +",\"text\":\""+dept.getDept_name()+"\"";
			
			List<CpDeptBean> childList = getChildDeptList(dept.getDept_id() + "");

			//如果该元素有子节点  递归子节点
			if(childList != null && childList.size() > 0){
				jsonStr += ",\"children\":[";
				for(int i=0; i < childList.size();i++){
					jsonStr += getDeptTreeJsonElement(childList.get(i).getDept_id());
					
					// 如果不是最后一个子元素 则添加分隔符“,”
					if(i < childList.size()-1){
						jsonStr +=",";
					}
				}
				jsonStr += "]";
			}
			jsonStr += "}";
			
			return jsonStr;
		}else{
			return "[]";
		}
		
	}
	
	
	/**
	 * 递归得到指定部门节点下所有节点（包含当前部门）id的字符串  【删除当前部门用】
	 * @param root_dept_id
	 * @return
	 */
	public static String getChildDeptIds(String dept_id){
		
		String idStr = "";
		
		//得到子
		List<CpDeptBean> childList = getChildDeptList(dept_id + "");
		
		//如果有子节点  累计子节点的部门ID
		if(childList != null && childList.size() > 0){
			
			for(int i=0; i < childList.size();i++){
				idStr += getChildDeptIds(childList.get(i).getDept_id()+"")+", ";
			}
		}
		// 最后添加
		idStr += dept_id;
		
		return idStr;
	}
	
	
	
	/**
	 * 排序后展现指定节点下所有子节点    
	 * @param dept_id   指定节点
	 * @return
	 */
	public static List<CpDeptBean> getChildDeptList(String dept_id){
		List<CpDeptBean> childList = new ArrayList<CpDeptBean>();
		
		//迭代Map中每一个注册机构，得到父ID为当前ID的所有子对象。
		Iterator iter = dept_map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry =(Map.Entry) iter.next();
			String key = (String) entry.getKey();
			if(dept_id.equals(dept_map.get(key).getParent_id()+"")){
				childList.add((CpDeptBean)entry.getValue());
			}
		}
		
		//对于无序的集合需要排序
		Collections.sort(childList,new CpDeptComparator());
		return childList;
	}
	
	/**
	 * 比较器
	 * @author 王磊
	 *
	 */
	public static class CpDeptComparator implements Comparator<Object>{
		public int compare(Object o1,Object o2){
			CpDeptBean dept1 = (CpDeptBean)o1;
			CpDeptBean dept2 = (CpDeptBean)o2;
			if(dept1.getSort_id() > dept2.getSort_id()){
				return 1;
			}else{
				if(dept1.getSort_id() == dept2.getSort_id()){
					return 0;
				}else{
					return -1;
				}
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		deleteCpdept();

	}
	

}
