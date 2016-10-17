/**
 * 
 */
package com.deya.wcm.services.appeal.cpUser;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.appeal.cpDept.CpDeptBean;
import com.deya.wcm.bean.appeal.cpUser.CPUserReleInfo;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.appeal.cpUser.CpUserManager;

/**
 * @author 王磊
 *
 */
public class CpUserRPC {
	/**
	 * 根据选择的用户ID判断此用户是否被添加过
	 * @param m
	 * @return
	 */
	public static String userISExist(String user_ids){
		return CpUserManager.userISExist(user_ids);
	}
	
	/**
	 * 得到所有已关联的用户
	 * @param m
	 * @return
	 */
	public static Map<Integer,CPUserReleInfo> getAllReleUserMap()
	{
		return CpUserManager.getAllReleUserMap();
	}
	
	/**
	 * 新建注册用户
	 * @param user
	 * @return
	 */
	public static boolean insertCpUser(int dept_id,String user_ids,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CpUserManager.insertCpUser(dept_id,user_ids,stl);
		}else
			return false;
	}
	
	/**
	 * 删除注册用户
	 * @param userIds
	 * @return
	 */
	public static boolean deleteCpUser(String dept_id,String userIds,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CpUserManager.deleteCpUser(dept_id,userIds,stl);
		}else
			return false;
	}
	
	/**
	 * 展现 [指定部门]下的所有用户
	 * @param m
	 * @return
	 */
	public static List<CPUserReleInfo> getUserListByDept(int dept_id){
		return CpUserManager.getUserListByDept(dept_id);
	}	
	
	/**
	 * 根据用户ID得到所能管理的部门
	 * @param int user_id
	 * @return String dept_ids
	 */
	public static int getSQDeptIDbyUserID(int user_id)
	{
		return CpUserManager.getSQDeptIDbyUserID(user_id);
	}	
	
	/**
	 * 根据用户ID所到它所在部门的平级单位列表
	 * @param int user_id
	 * @return String dept_id
	 */
	public static List<CpDeptBean> getBrotherDeptListByUserID(int user_id)
	{
		return CpUserManager.getBrotherDeptListByUserID(user_id);
	}
	
	/**
	 * 根据用户ID所到它所在部门的下级单位列表(交办)
	 * @param int user_id
	 * @return String dept_id
	 */
	public static List<CpDeptBean> getChildDeptListByUserID(int user_id)
	{
		return CpUserManager.getChildDeptListByUserID(user_id);
	}
	
	/**
	 * 根据用户ID所到它所在部门的父级单位列表(呈办　目前只取它的父节点)
	 * @param int user_id
	 * @return String dept_id
	 */
	public static List<CpDeptBean> getParentDeptListByUserID(int user_id)
	{	
		return CpUserManager.getParentDeptListByUserID(user_id);
	}
	
	/**
	 * 根据用户ID所到它所在部门的父级节点及父级列表
	 * @param int user_id
	 * @return String dept_id
	 */
	public static List<CpDeptBean> getParenAndBrotherDeptListByUserID(int user_id)
	{
		return CpUserManager.getParenAndBrotherDeptListByUserID(user_id);
	}
}