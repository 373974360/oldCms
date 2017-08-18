package com.deya.wcm.services.org.dept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.bean.org.dept.DeptManBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.org.OrgDAOFactory;
import com.deya.wcm.dao.org.dept.IDeptDAO;
import com.deya.wcm.services.org.user.UserManager;

/**
 * 组织机构部门管理逻辑处理类.
 * <p>
 * Title: CicroDate
 * </p>
 * <p>
 * Description: 组织机构部门管理逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: Cicro
 * </p>
 * 
 * @author zhuliang
 * @version 1.0 *
 */

public class DeptManager implements ISyncCatch{

	private static TreeMap<String, DeptBean> dept_Map = new TreeMap<String, DeptBean>();	
	private static TreeMap<String, String> main_Map = new TreeMap<String, String>();//键　部门ID，值　人员ID组合字符串","+用户ID+","号分隔，可多个　如　,12,13,
	private static String dept_manager_path = JconfigUtilContainer.managerPagePath().getProperty("detp_list", "", "m_org_path");
	private static IDeptDAO deptDao = OrgDAOFactory.getDeptDao();
	
	private static int root_node_id = 1;

	static {
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	@SuppressWarnings("unchecked")
	public static void reloadCatchHandl()
	{
		List<DeptManBean> manager_list = deptDao.getAllDeptManagerList();
		main_Map.clear();
		if (manager_list != null && manager_list.size() > 0) {
			for (DeptManBean dmb : manager_list) {
				String dept_id = dmb.getDept_id() + "";
				String user_id = dmb.getUser_id() + "";
				if(main_Map.containsKey(dept_id))
				{
					main_Map.put(dept_id, main_Map.get(dept_id) + user_id + ",");	
				}
				else
					main_Map.put(dept_id, "," + user_id + ",");				
			}
		}		
		
		List<DeptBean> dept_list = deptDao.getAllDeptList();
		dept_Map.clear();
		if (dept_list != null && dept_list.size() > 0) {
			for (int i = 0; i < dept_list.size(); i++) {
				//暂不需要把管理员放进dept对象
				//dept_list.get(i).setManager_ids(getManagerIDSByDeptID(dept_list.get(i).getDept_id() + ""));	
				
				dept_Map.put(dept_list.get(i).getDept_id() + "", dept_list
						.get(i));				
			}
		}
	}

	/**
	 * 初始加载部门信息
	 * 
	 * @param
	 * @return
	 */
	
	public static void reloadDept() {
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.dept.DeptManager");
	}	
	
	
	
	/**
	 * 初始加载部门管理员信息
	 * 
	 * @param
	 * @return
	 */
	public static void reloadDeptManager()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.dept.DeptManager");
	}
	
	public static TreeMap<String, String> getDeptMUserMap()
	{
		return main_Map;
	}
	
	public static String getDeptName(String dept_id)
	{
		DeptBean db = getDeptBeanByID(dept_id);
		if(db != null)
			return db.getDept_name();
		else
			return "";
	}
	
	/**
     * 得到部门ID,用于添加部门时给出ID值
     * @param 
     * @return List
     * */
	public static int getDeptID(){
		return deptDao.getDeptID();
	}
	
	/**
     * 返回部门列表页面地址
     * @param 
     * @return List
     * */
	public static String getDeptListPage()
	{
		return dept_manager_path;
	}
	
	/**
	 * 根据人员ID得到其能管理的部门列表
	 * 
	 * @param String
	 *            user_id  
	 * @return List
	 */
	public static List<DeptBean> getDeptListByUserManager(String user_id)
	{
		List<DeptBean> dept_list = new ArrayList<DeptBean>();
		String dept_ids = getDeptByUserManager(user_id);
		if(dept_ids != null && !"".equals(dept_ids))
		{
			String[] d_arr = dept_ids.split(",");
			for(int i=0;i<d_arr.length;i++)
			{
				DeptBean db = getDeptBeanByID(d_arr[i]);
				if(db != null)
					dept_list.add(db);
			}
			Collections.sort(dept_list,new DeptComparator());
		}
		return dept_list;
	}
	
	/**
	 * 根据人员ID得到其能管理的部门ID
	 * 
	 * @param String
	 *            user_id  
	 * @return String dept_ids　部门ID  
	 */
	@SuppressWarnings("unchecked")
	public static String getDeptByUserManager(String user_id)
	{
		String dept_ids = "";
		Iterator iter = main_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();	
			String user_ids = main_Map.get((String) entry.getKey());
			if(user_ids.contains(","+user_id+","))
				dept_ids += ","+(String) entry.getKey();
		}
		if(dept_ids != null && !"".equals(dept_ids))
			dept_ids = dept_ids.substring(1);
		
		return dept_ids;
	}
	
	/**
	 * 根据人员ID得到其能管理的部门,并组织成字符串
	 * 
	 * @param String
	 *            user_id  
	 * @return String
	 */
	public static String getDeptTreeByUser(String user_id)
	{
		String str = "[]";
		List<DeptBean> dept_list = getDeptListByUserManager(user_id);
		if(dept_list != null && dept_list.size() > 0){
			str = "["+deptListToStrHandl(dept_list,"one")+"]";
		}
		return str;
	}
	
	/**
	 * 得到所有部门信息列表
	 * 
	 * @param 
	 *            
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<DeptBean> getDeptList()
	{
		List<DeptBean> dept_list = new ArrayList<DeptBean>();

		Iterator iter = dept_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();									
			dept_list.add(dept_Map.get((String) entry.getKey()));			
		}
		return dept_list;
	}
	
	/**
	 * 根据部门ID及查询条件从库中得到下级部门列表对象deep+1
	 * 
	 * @param Map
	 *            con_m
	 * @return List
	 */
	public static List<DeptBean> getChildDeptListForDB(Map<String,String> con_m) {
		return deptDao.getChildDeptListForDB(con_m);
	}
	
	/**
     * 根据多个部门ID返回部门对象列表
     * @param String dept_ids
     * @return List
     * */
	public static List<DeptBean> getDeptListByDeptIDS(String dept_ids)
	{
		if(dept_ids != null && !"".equals(dept_ids))
		{
			List<DeptBean> dept_list = new ArrayList<DeptBean>();
			String[] tempA = dept_ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{				
				DeptBean db = getDeptBeanByID(tempA[i]);
				if(db != null)
					dept_list.add(db);
			}
			return dept_list;
		}
		else
			return null;
	}
	
	/**
	 * 得到部门根节点ID
	 * 
	 * @param 
	 *            
	 * @return int
	 */
	public static int getRootDeptID()
	{
		return root_node_id;
	}
	
	/**
	 * 得到部门根节点对象
	 * 
	 * @param 
	 *            
	 * @return DeptBean
	 */
	public static DeptBean getRootDeptBean()
	{
		return getDeptBeanByID(root_node_id+"");
	}
	
	/**
	 * 得到所有部门信息集合
	 * 
	 * @param 
	 *            
	 * @return Map
	 */
	public static Map<String, DeptBean> getDeptMap()
	{
		return dept_Map;
	}

	/**
	 * 插入部门信息
	 * 
	 * @param DeptBean
	 *            db
	 * @param SettingLogsBean
	 *            stl 操作日志对象
	 * @return boolean
	 */
	public static boolean insertDept(DeptBean db,SettingLogsBean stl) {
        DeptBean deptBean = getDeptBeanByID(db.getParent_id() + "");
        if(deptBean != null){
            db.setTree_position(deptBean.getTree_position());
        }
		if (deptDao.insertDept(db,stl)) {
			reloadDept();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改部门信息
	 * 
	 * @param DeptBean
	 *            db
	 * @param SettingLogsBean
	 *            stl 操作日志对象            
	 * @return boolean
	 */
	public static boolean updateDept(DeptBean db,SettingLogsBean stl) {
		if (OrgDAOFactory.getDeptDao().updateDept(db,stl)) {			
			
			reloadDept();
			return true;
		} else {
			return false;
		}
	}
	
	/**
     * 移动部门
     * @param String parent_id
     * @param String dept_ids
     * @return boolean
     * */
	public static boolean moveDept(String parent_id,String dept_ids,SettingLogsBean stl) {		
		String parent_tree_position = getDeptBeanByID(parent_id).getTree_position();
		if(dept_ids != null && !"".equals(dept_ids))
		{
			try{
				String[] tempA = dept_ids.split(",");
				for(int i=0;i<tempA.length;i++)
				{					
					moveDeptHandl(tempA[i],parent_id,parent_tree_position,stl);
				}
				reloadDept();
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public static void moveDeptHandl(String dept_id,String parent_id,String tree_position,SettingLogsBean stl)
	{
		String position = tree_position+dept_id+"$";
		Map<String,String> new_m = new HashMap<String,String>();
		new_m.put("dept_id", dept_id);
		new_m.put("parent_id", parent_id);
		new_m.put("tree_position", position);
		if(OrgDAOFactory.getDeptDao().moveDept(new_m,stl)){
			//该节点下的子节点一并转移
			List<DeptBean> d_list = getChildDeptListByID(dept_id);
			if(d_list != null && d_list.size() > 0)
			{
				for(int i=0;i<d_list.size();i++)
				{
					moveDeptHandl(d_list.get(i).getDept_id()+"",dept_id,position,stl);
				}
			}
		}
		
	}

	/**
	 * 根据部门ID得到部门信息
	 * 
	 * @param String
	 *            id
	 * @return DeptBean
	 */
	public static DeptBean getDeptBeanByID(String id) {
		if(id == null || "".equals(id))
			return null;
		
		if (dept_Map.containsKey(id)) {
			return dept_Map.get(id);
		} else {
			DeptBean db = deptDao.getDeptBeanByID(id);
			if (db != null)
			{
				//db.setManager_ids(getManagerIDSByDeptID(id));
				dept_Map.put(id, db);
			}
			return db;
		}
	}

	/**
	 * 根据部门ID得到上级部门信息
	 * 
	 * @param String
	 *            id
	 * @return DeptBean
	 */
	public static DeptBean getParentDeptBeanByID(String id) {
		DeptBean db = getDeptBeanByID(id);
		if (db != null) {
			return getDeptBeanByID(db.getParent_id() + "");
		} else {
			return null;
		}
	}
	
	/**
	 * 根据部门ID得到下级部门个数
	 * 
	 * @param String
	 *            dept_id
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static String getChildDeptCountByID(String dept_id)
	{
		int count = 0;
		Iterator iter = dept_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();			
			if (dept_id.equals(dept_Map.get(key).getParent_id()+"")) {
				count +=1;
			}
		}
		return count + "";
	}
	
	/**
	 * 根据部门ID此部门下所有节点对象json字符串(包含当前部门)
	 * 
	 * @param String dept_id
	 * @return String
	 */
	public static String deptListToJsonStrByUserID(String dept_id)
	{
		DeptBean db = getDeptBeanByID(dept_id);		
		if(db != null)
		{
			String json_str = "[{\"id\":"+dept_id+",\"text\":\""+db.getDept_name()+"\",\"attributes\":{\"url\":\""
				+dept_manager_path+"?deptID="+dept_id+"\"}";
			String child_str = deptListToStrHandl(getChildDeptListByID(dept_id),"");
			if(child_str != null && !"".equals(child_str))
				json_str += ",\"children\":["+child_str+"]";
			json_str += "}]";
			return json_str;
		}else
			return "[]";		
	}
	
	/**
	 * 递归处理list转json字符串
	 * 
	 * @param String List<MenuBean> ml
	 * @param String loop_type 循环类型,表示是第一次进入循环
	 * @return String
	 */
	public static String deptListToStrHandl(List<DeptBean> dl,String loop_type)
	{
		String json_str = "";
		String icon_str = "\"iconCls\":\"icon-user\",";
		String child_state = "";
		if(dl != null && dl.size() > 0)
		{			
			for(DeptBean db : dl)
			{
				if(db.getDept_id() == root_node_id)
				{
					icon_str = "\"iconCls\":\"icon-org\"";	
				}else
					icon_str = "\"iconCls\":\"icon-dept\"";	
				
				json_str += ",{";
				List<DeptBean> child_d_list = getChildDeptListByID(db.getDept_id()+"");
				if(child_d_list != null && child_d_list.size() > 0 && !"one".equals(loop_type))
					child_state = "\"state\":'closed',";
				else
					child_state = "";
				
				json_str += "\"id\":"+db.getDept_id()+","+icon_str+","+child_state+"\"text\":\""+db.getDept_name()+"\",\"attributes\":{\"url\":\""
					+dept_manager_path+"?deptID="+db.getDept_id()+"\"}";
				
				if(child_d_list != null && child_d_list.size() > 0)
					json_str += ",\"children\":["+deptListToStrHandl(child_d_list,"")+"]";
				json_str += "}";				
			}
			if(json_str != null && !"".equals(json_str))
				json_str = json_str.substring(1);
		}
		return json_str;
	}

	/**
	 * 根据部门ID得到下级部门列表对象deep+1
	 * 
	 * @param String
	 *            dept_id
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<DeptBean> getChildDeptListByID(String dept_id) {
		List<DeptBean> child_dept_list = new ArrayList<DeptBean>();
				
		Iterator iter = dept_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			DeptBean temp_db = dept_Map.get(key);
			if (dept_id.equals(temp_db.getParent_id()+"")) {
				//temp_dept_map.put(temp_db.getDept_sort(), temp_db);				
				child_dept_list.add(temp_db);
			}
		}
		Collections.sort(child_dept_list,new DeptComparator());
		
		return child_dept_list;
	}
	
	/**
	 * 根据部门ID得到下级部门列表对象deep+1(允许发布的部门)
	 * 
	 * @param String
	 *            dept_id
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<DeptBean> getChildDeptListForPublish(String dept_id) {
		List<DeptBean> child_dept_list = new ArrayList<DeptBean>();
		
		Iterator iter = dept_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			DeptBean temp_db = dept_Map.get(key);
			if (dept_id.equals(temp_db.getParent_id()+"") && temp_db.getIs_publish() == 1) {				
				child_dept_list.add(temp_db);
			}
		}
		Collections.sort(child_dept_list,new DeptComparator());
		
		return child_dept_list;
	}

	/**
	 * 根据部门对象得到下级部门deep+1
	 * 
	 * @param DeptBean
	 *            db
	 * @return List
	 */
	public static List<DeptBean> getChildDeptListByBean(DeptBean db) {		
		return getChildDeptListByID(db.getDept_id()+"");
	}
	
	/**
	 * 根据部门对象得到下级部门iddeep+1
	 * 
	 * @param DeptBean
	 *            db
	 * @return String 以，号隔的ID号
	 */
	public static String getChildDeptIDSByBean(DeptBean db) {		
		return getChildDeptIDSByID(db.getDept_id()+"");
	}

	/**
	 * 根据部门ID得到下级部门IDdeep+1
	 * 
	 * @param DeptBean
	 *            db
	 * @return String 以，号隔的ID号
	 */
	@SuppressWarnings("unchecked")
	public static String getChildDeptIDSByID(String id)
	{
		String ids = "";

		Iterator iter = dept_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			DeptBean temp_db = dept_Map.get(key);
			if (id.equals(temp_db.getParent_id()+"")) {
				ids += ","+temp_db.getDept_id();
			}
		}
		if(!"".equals(ids))
			ids = ids.substring(1);
		return ids;
	}
	
	/**
	 * 根据部门ID得到所有的下级部门deep+n
	 * 
	 * @param String
	 *            id
	 * @return List
	 */
	public static List<DeptBean> getAllChildDeptListByID(String id) {
		DeptBean db = getDeptBeanByID(id);
		if (db != null) {			
			return getAllChildDeptListByBean(db);
		} else {
			return null;
		}
	}

	/**
	 * 根据部门对象得到所有的下级部门deep+n
	 * 
	 * @param DeptBean
	 *            db
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<DeptBean> getAllChildDeptListByBean(DeptBean db) {
		List<DeptBean> child_dept_list = new ArrayList<DeptBean>();
		
		Iterator iter = dept_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			DeptBean temp_db = dept_Map.get(key);
			if (temp_db.getTree_position().startsWith(db.getTree_position()) && !temp_db.getTree_position().equals(db.getTree_position())) {
				child_dept_list.add(temp_db);
			}
		}
		return child_dept_list;
	}
	
	/**
	 * 根据部门对象得到所有的下级部门iddeep+n
	 * 
	 * @param DeptBean
	 *            db
	 * @return String 以，号隔的ID号
	 */
	@SuppressWarnings("unchecked")
	public static String getAllChildDeptIDSByBean(DeptBean db) {
		String ids = "";

		Iterator iter = dept_Map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			DeptBean temp_db = dept_Map.get(key);
			if (temp_db.getTree_position().startsWith(db.getTree_position()) && !temp_db.getTree_position().equals(db.getTree_position())) {
				ids += ","+temp_db.getDept_id();
			}
		}
		if(!"".equals(ids))
			ids = ids.substring(1);
		
		return ids;
	}
	
	/**
	 * 根据部门ID得到所有的下级部门deep+n
	 * 
	 * @param DeptBean
	 *            db
	 * @return String 以，号隔的ID号
	 */
	public static String getAllChildDeptIDSByID(String id) {
		DeptBean db = getDeptBeanByID(id);
		
		if (db != null) {
			return getAllChildDeptIDSByBean(db);
		} else {
			return null;
		}
	}
	
	/**
	 * 删除部门
	 * 
	 * @param String
	 *            ids
	 * @param SettingLogsBean
	 *            stl 操作日志对象            
	 * @return boolean
	 */
	public static boolean deleteDept(String ids,SettingLogsBean stl)
	{
		String delete_ids = "";
		if(ids != null && !"".equals(ids))
		{
			String[] tempA = ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{//得到所有的下级部门
				String all_child_dept = getAllChildDeptIDSByID(tempA[i]);
				if(!"".equals(all_child_dept))
					all_child_dept = ","+all_child_dept;
				delete_ids += ","+tempA[i]+all_child_dept;
			}
			delete_ids = delete_ids.substring(1);
			if(deptDao.deleteDept(delete_ids,stl))
			{
				//刷新用户缓存
				UserManager.reloadUser();
				
				reloadDept();
				return true;
			}else
			{
				return false;
			}
			
		}else
		{
			return true;
		}
	}

	
	/**
	 * 保存部门排序
	 * 
	 * @param String
	 *            ids 部门ID
	 * @param SettingLogsBean
	 * 			  stl	           
	 * @return 
	 */
	public static boolean saveDeptSort(String dept_ids,SettingLogsBean stl)
	{
		if(dept_ids != null && !"".equals(dept_ids))
		{
			if(deptDao.saveDeptSort(dept_ids,stl))
			{
				reloadDept();
				return true;
			}else
				return false;			
		}else
			return true;
	}	
	
	/**
	 * 根据部门ID得到下面所属的人员对象列表 可多个部门ID
	 * 
	 * @param String
	 *            dept_id 部门ID
	 * @return List
	 */
	public static List<UserBean> getUserListByDeptID(String dept_id)
	{
		return UserManager.getUserListByDeptID(dept_id);
	}
	
	/**
	 * 根据部门ID得到下面所有部门的人员对象列表
	 * 
	 * @param String
	 *            dept_id 部门ID
	 * @return List
	 */
	public static List<UserBean> getAllChildUserByDeptID(String dept_id)
	{
		String child_dept_ids = getAllChildDeptIDSByID(dept_id);
		if(child_dept_ids == null || "".equals(child_dept_ids))
		{
			child_dept_ids = dept_id;
		}else
			child_dept_ids = dept_id+","+child_dept_ids;
		
		return  getUserListByDeptID(dept_id+","+child_dept_ids);
	}
	
	static class DeptComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			DeptBean db1 = (DeptBean) o1;
			DeptBean db2 = (DeptBean) o2;
		    if (db1.getDept_sort() > db2.getDept_sort()) {
		     return 1;
		    } else {
		     if (db1.getDept_sort() == db2.getDept_sort()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}


    /**
     * 同步银海部门信息
     *
     * @param DeptBean
     *            db
     * @param SettingLogsBean
     *            stl 操作日志对象
     * @return boolean
     */
    public static boolean inserSynctDept(List<DeptBean> deptList) {
        DeptBean parent = null;
        Map<Integer,DeptBean> map = new HashMap<Integer,DeptBean>();
        for (DeptBean bean : deptList) {
            map.put(bean.getDept_id(),bean);
        }
        for (DeptBean bean : deptList) {
            parent = map.get(bean.getParent_id());
            if(parent != null){
                bean.setTree_position(parent.getTree_position());
            }
        }

        if (deptDao.inserSynctDept(deptList)) {
            reloadDept();
            return true;
        } else {
            return false;
        }
    }
	
		
	public static void main(String args[]) {
		//insertTest();
		//updateTest();
		//reloadDept();
		//deleteDept("11,12");
		//deleteDeptToMapByIDS("11,12");
		/*
		System.out.println(DateUtil.dateToTimestamp());
		List<DeptBean> l = getChildDeptListByID("1");		
		
		for(int i=0;i<l.size();i++)
		{
			System.out.println(l.get(i).getDept_id()+"   "+l.get(i).getDept_sort());
		}
		System.out.println(DateUtil.dateToTimestamp());
		*/
		
		//saveDeptSort("10084,10083",new SettingLogsBean());
		//List<DeptBean> l = getChildDeptListByID("1");
		
		//System.out.println(getChildDeptCountByID("1"));
		//saveDeptSort(s.substring(1),new SettingLogsBean());
		//System.out.println(getChildDeptListByID("1"));
		System.out.println(getDeptTreeByUser("1"));
	}

	

	
}
