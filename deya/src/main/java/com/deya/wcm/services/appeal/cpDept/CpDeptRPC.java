/**
 * 
 */
package com.deya.wcm.services.appeal.cpDept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.cpDept.CpDeptBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appeal.cpDept.CpDeptDAO;
import com.deya.wcm.services.appeal.cpDept.CpDeptManager.CpDeptComparator;

/**
 * @author 王磊
 *
 */
public class CpDeptRPC {
	/**
	 * 根据部门ID得到部门名称
	 * 
	 * @param String
	 *            id
	 * @return String
	 */
	public static String getCpDeptName(int id)
	{
		return CpDeptManager.getCpDeptName(id);
	}

	/**
	 * 新增  注册机构
	 * @param dept
	 * @return
	 */
	public static boolean insertCpdept(CpDeptBean dept){
		return CpDeptManager.insertCpdept(dept);
	}
	
	/**
	 * 删除  注册机构
	 * @param dept_ids
	 * @return
	 */
	public static boolean deleteCpdept(String dept_ids){
		return CpDeptManager.deleteCpdept(dept_ids);
	}
	
	/**
	 * 修改注册机构
	 * @param dept
	 * @return
	 */
	public static boolean updateCpDept(CpDeptBean dept){
		return CpDeptManager.updateCpDept(dept);
	}
	
	public static boolean saveDeptSort(String dept_ids){
		return CpDeptManager.saveDeptSort(dept_ids);
	}
	
	/**
	 * 所有注册机构
	 * @param m
	 * @return
	 */
	public static List<CpDeptBean> getAllCpDeptList(){
		return CpDeptManager.getCpDeptList();
	}
	
	/**
	 * 得到所有注册机构的总数
	 * @param m
	 * @return
	 */
	public static String getCpDeptCount(Map<String,String> m){
		return CpDeptManager.getCpDeptCount(m);
	}
	
	/**
	 * 得到指定ID的机构
	 * @param dept_id
	 * @return
	 */
	public static CpDeptBean getCpDeptBean(int dept_id){

		return CpDeptManager.getCpDeptBean(dept_id);
	}
	
	/**
	 * 拼写注册机构树的Json格式
	 * @return
	 */
	public static String getDeptTreeJsonStr(int node){
		return CpDeptManager.getDeptTreeJsonStr(node);
	}
	
	/**
	 * 得到指定节点下所有子节点 并排序    （以列表展现）
	 * @param dept_id
	 * @return
	 */
	public static List<CpDeptBean> getChildDeptList(String dept_id){
        //System.out.println("91 >>>>>>>>>"+ dept_id);
		return CpDeptManager.getChildDeptList(dept_id);
	}
}
