/**
 * 机构注册的DAO类
 */
package com.deya.wcm.dao.appeal.cpDept;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.cpDept.CpDeptBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * @author 王磊
 *
 */
public class CpDeptDAO {

	/**
	 * 新增  注册机构
	 * @param dept
	 * @return
	 */
	public static boolean insertCpdept(CpDeptBean dept){
		return DBManager.insert("insert_cp_dept", dept);
	}
	
	/**
	 * 删除  注册机构
	 * @param dept_ids
	 * @return
	 */
	public static boolean deleteCpdept(String deptIds){
		Map<String,String> m = new HashMap<String,String>();
		m.put("dept_ids", deptIds);
		return DBManager.delete("delete_cp_dept", m);
	}
	
	/**
	 * 修改注册机构
	 * @param dept
	 * @return
	 */
	public static boolean updateCpDept(CpDeptBean dept){
		return DBManager.update("update_cp_dept", dept); 
	}
	
	/**
	 * 保存部门排序
	 * 
	 * @param String  dept_ids 部门ID
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public static boolean saveDeptSort(String dept_ids)
	{
		String[] tempA = dept_ids.split(",");
		Map m = new HashMap();
		
		try
		{
			for(int i=0;i<tempA.length;i++)
			{
				m.put("dept_id", tempA[i]);
				m.put("sort_id", i+1);
				DBManager.update("savesort_cpDept", m);
			}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}		 
	}
	
	/**
	 * 得到指定ID的机构
	 * @param dept_id
	 * @return
	 */
	public static CpDeptBean getCpDeptBean(String dept_id){
		return (CpDeptBean)DBManager.queryFObj("getCpDeptBean",dept_id+"");
	}
	
	/**
	 * 展示  所有注册机构
	 * @param m
	 * @return
	 */
	public static List<CpDeptBean> getCpDeptList(){
		return DBManager.queryFList("getCpDeptList","");
	}
	
	/**
	 * 得到所有注册机构的总数
	 * @param m
	 * @return
	 */
	public static String getCpDeptCount(Map<String,String> m){
		return DBManager.getString("getCpDeptCount", m);
	}
	
	/**
	 * 按照 parent_id, sort_id asc的   所有注册机构
	 * @param m
	 * @return
	 */
	public static List<CpDeptBean> getAllCpDeptBySort(){
		return DBManager.queryFList("getAllCpDeptBySort","");
	}

}
