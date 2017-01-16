package com.deya.wcm.dao.org.dept;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.bean.org.dept.DeptLevelBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.org.user.IUserDAO;
import com.deya.wcm.dao.org.user.UserDAODBImpl;
import com.deya.wcm.db.DBManager;
import org.apache.commons.lang3.StringUtils;

/**
 *  组织机构部门管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 组织机构部门管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class DeptDAODBImpl implements IDeptDAO{
	private static IUserDAO userDAO = new UserDAODBImpl();	
	/**
     * 得到所有部门列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllDeptList()
	{	
		return DBManager.queryFList("getAllDeptList", "");
	}		
	
	/**
	 * 根据部门ID及查询条件从库中得到下级部门列表对象deep+1,参数有 dept_id,con_name,con_value
	 * 
	 * @param Map
	 *            con_m
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<DeptBean> getChildDeptListForDB(Map<String,String> con_m)
	{
		return DBManager.queryFList("getChildDeptListForBD", con_m);
	}
	
	/**
     * 根据ID返回部门对象
     * @param String id
     * @return DeptBean
     * */
	public DeptBean getDeptBeanByID(String id)
	{
		return (DeptBean)DBManager.queryFObj("getDeptBeanByID", id);
	}
	
	/**
     * 得到部门ID,用于添加部门时给出ID值
     * @param 
     * @return List
     * */
	public int getDeptID(){
		return PublicTableDAO.getIDByTableName(PublicTableDAO.DEPT_TABLE_NAME);
	}
	
	/**
     * 插入部门信息
     * @param DeptBean db
     * @return boolean
     * */
	public boolean insertDept(DeptBean db,SettingLogsBean stl)
	{
        if(db.getTree_position() != null && !"".equals(db.getTree_position())){
            db.setTree_position(db.getTree_position()+ db.getDept_id() + "$");
        }else{
            db.setTree_position("$" + db.getDept_id() + "$");
        }

		if(DBManager.insert("insert_dept", db))
		{
			//添加管理员(暂时不要,加部门时不加管理员)
			//insertDetpManager(db.getManager_ids(),db.getDept_id()+"");
			PublicTableDAO.insertSettingLogs("添加","部门",db.getDept_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 修改部门信息
     * @param DeptBean db
     * @return boolean
     * */
	public boolean updateDept(DeptBean db,SettingLogsBean stl)
	{
		if(DBManager.update("update_dept", db))
		{
			//updateDetpManager(db.getManager_ids(),db.getDept_id()+"");
			PublicTableDAO.insertSettingLogs("修改","部门",db.getDept_id()+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 移动部门
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public  boolean moveDept(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("move_table", m))
		{
			PublicTableDAO.insertSettingLogs("移动","部门",m.get("dept_ids"),stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除部门信息
     * @param String　ids
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public boolean deleteDept(String ids,SettingLogsBean stl)
	{
		Map m = new HashMap();
		m.put("ids", ids);
		if(DBManager.update("delete_dept", m))
		{
			//删除部门管理员
			deleteDeptManager("",ids,stl);
			//删除部门下所有的用户
			userDAO.deleteUserByDeptID(ids,stl);
			PublicTableDAO.insertSettingLogs("删除","部门",ids,stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 保存部门排序
	 * 
	 * @param String
	 *            ids 部门ID
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public boolean saveDeptSort(String ids,SettingLogsBean stl)
	{
		String[] tempA = ids.split(",");
		Map m = new HashMap();
		try
		{
			for(int i=0;i<tempA.length;i++)
			{
				m.put("dept_id", tempA[i]);
				m.put("dept_sort", i+1);
				DBManager.update("savesort_dept", m);
			}
			PublicTableDAO.insertSettingLogs("保存排序","部门",ids+"",stl);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}		 
	}	
	
	/* **********************部门管理员管理　开始******************************** */
	/**
	 * 得到部门管理员列表
	 * 
	 * @param
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getAllDeptManagerList()
	{
		return DBManager.queryFList("getAllDeptManagerList", "");
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
	@SuppressWarnings("unchecked")
	public boolean insertDetpManager(String user_ids,String dept_id,SettingLogsBean stl)
	{
		if(user_ids == null || "".equals(user_ids))
			return true;
		Map<String, Comparable> m = new HashMap<String, Comparable>();
		try
		{
			String[] tempA = user_ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				if(tempA[i] != null && !"".equals(tempA[i]))
				{				
					m.put("dept_manager_id", PublicTableDAO.getIDByTableName(PublicTableDAO.DEPTMANAGER_TABLE_NAME));
					m.put("dept_id", dept_id);
					m.put("user_id", tempA[i]);
					DBManager.insert("insert_detp_manager", m);
				}
			}
			PublicTableDAO.insertSettingLogs("添加","部门管理员 部门",dept_id+" 人员ID为 "+user_ids,stl);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
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
	public boolean updateDetpManager(String user_ids,String dept_id,SettingLogsBean stl)
	{
		//先删除，再添加
		if(deleteDeptManager("",dept_id,stl))
		{
			return insertDetpManager(user_ids,dept_id,stl);
		}else
			return false;
	}
	/**
	 * 根据部门ID删除部门管理
	 * 	 
	 * @param int
	 * 			  dept_ids 部门ID	            
	 * @return boolean
	 */
	public boolean deleteDeptManager(String user_ids,String dept_id,SettingLogsBean stl)
	{
		Map<String, String> m = new HashMap<String, String>();
		
		m.put("detp_ids", dept_id);
		if(user_ids != null && !"".equals(user_ids))
			m.put("user_ids", user_ids);
		if(DBManager.delete("delete_detp_manager_byDeptID", m))
		{
			PublicTableDAO.insertSettingLogs("删除","部门管理员 部门",dept_id+" 人员ID为 "+user_ids,stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 根据人员ID删除部门管理
	 * 	 
	 * @param int
	 * 			  user_ids 人员ID	            
	 * @return boolean
	 */
	public static boolean deleteDeptManagerByUserID(String user_ids,SettingLogsBean stl)
	{
		Map<String, String> m = new HashMap<String, String>();
		
		m.put("ids", user_ids);
		if(DBManager.delete("delete_detp_manager_byUserID", m))	
		{
			PublicTableDAO.insertSettingLogs("删除","部门管理员 人员",user_ids,stl);
			return true;
		}
		else
			return false;
	}
	/* **********************部门管理员管理　结束******************************** */
	
	/* **********************部门级别管理　开始******************************** */
	/**
     * 得到所有部门级别列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public List getAllDeptLevelList()
	{
		return DBManager.queryFList("getAllDeptLevelList", "");		
	}
	
	/**
     * 插入部门级别信息
     * @param DeptLevelBean dlb
     * @return boolean
     * */
	public boolean insertDeptLevel(DeptLevelBean dlb,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.DEPTLEVEL_TABLE_NAME);
		dlb.setDeptlevel_id(id);
		if(DBManager.insert("insert_deptLevel", dlb))
		{
			PublicTableDAO.insertSettingLogs("添加","部门级别",id+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 根据部门ID得到部门信息
	 * 
	 * @param String 
	 *            id
	 * @return DeptLevelBean
	 */
	public DeptLevelBean getDeptLevelBeanByID(String id)
	{
		return (DeptLevelBean)DBManager.queryFObj("getDeptLevelBeanByID", id);
	}
	
	/**
     * 修改部门级别信息
     * @param DeptLevelBean dlb
     * @return boolean
     * */
	public boolean updateDeptLevel(DeptLevelBean dlb,SettingLogsBean stl)
	{		
		if(DBManager.update("update_deptLevel", dlb))
		{
			PublicTableDAO.insertSettingLogs("修改","部门级别",dlb.getDeptlevel_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除部门级别信息
     * @param String　ids
     * @return boolean
     * */
	public boolean deleteDeptLevel(String ids,SettingLogsBean stl)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("ids", ids);
		if(DBManager.update("delete_deptLevel", m))
		{
			PublicTableDAO.insertSettingLogs("删除","部门级别",ids,stl);
			return true;
		}else
			return false;
	}

	/* **********************部门级别管理　结束******************************** */



	public boolean inserSynctDept(List<DeptBean> deptList) {
		if(DBManager.delete("deleteDept",null)){
			if(DBManager.insert("insertDeptBatch", deptList))
			{
				System.out.println("**********************同步银海部门成功***********************");
				return true;
			}
		}
		return false;
	}

	public int getMaxId(){
		String maxId = DBManager.getString("selectMaxDeptId",null);
		if(StringUtils.isNotEmpty(maxId)){
			return Integer.parseInt(maxId);
		}
		return 0;
	}
}
