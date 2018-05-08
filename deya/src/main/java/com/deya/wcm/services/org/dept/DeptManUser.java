package com.deya.wcm.services.org.dept;

import java.util.ArrayList;
import java.util.List;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.role.RoleUserBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.org.OrgDAOFactory;
import com.deya.wcm.dao.org.dept.IDeptDAO;
import com.deya.wcm.services.org.role.RoleUserManager;
import com.deya.wcm.services.org.user.UserManager;

/**
 * 组织机构部门管理员逻辑处理类.
 * <p>
 * Title: CicroDate
 * </p>
 * <p>
 * Description: 组织机构部门管理员逻辑处理类
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

public class DeptManUser {
	
	private static IDeptDAO deptDao = OrgDAOFactory.getDeptDao();
		
	/**
	 * 得到部门管理员列表
	 * 
	 * @param　String dept_id
	 * @return List 
	 */
	public static List<UserBean> getDeptManagerUserList(String dept_id)
	{
		String user_ids = getManagerIDSByDeptID(dept_id);
		List<UserBean> user_list = new ArrayList<UserBean>();
		if(user_ids != null && !"".equals(user_ids))
		{
			String[] user_a = user_ids.split(",");
			for(int i=0;i<user_a.length;i++)
			{
				try{
					UserBean ub = UserManager.getUserBeanByID(user_a[i]);
					if(ub != null){
						ub.setDept_name(DeptManager.getDeptBeanByID(ub.getDept_id()+"").getDept_name());
						user_list.add(ub);
					}
				}catch(Exception e)
				{
					e.printStackTrace();
					continue;
				}
			}
		}
		return user_list;
	}
	
	/**
	 * 根据部门ID得到此部门所有的管理人员ID
	 * 
	 * @param　String dept_id
	 * @return String 部门管理员，","+用户ID+","号分隔，可多个　如　,12,13,
	 */
	public static String getManagerIDSByDeptID(String dept_id)
	{
		return (String)DeptManager.getDeptMUserMap().get(dept_id);
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
	public static boolean insertDetpManager(String user_ids,String dept_id,SettingLogsBean stl)
	{		
		if(deptDao.insertDetpManager(user_ids, dept_id,stl))
		{
			saveDeptManagerRoleUser(user_ids,"",stl);
			DeptManager.reloadDept();			
			return true;
		}			
		else
		{
			return false;
		}
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
	public static boolean updateDetpManager(String user_ids,String old_dept_manager,String dept_id,SettingLogsBean stl)
	{
		if(deptDao.updateDetpManager(user_ids,dept_id,stl))
		{
			DeptManager.reloadDept();
			saveDeptManagerRoleUser(user_ids,old_dept_manager,stl);		
			return true;
		}			
		else
		{
			return false;
		}
	}
	
	
	/**
	 * 删除部门管理员
	 * 	 
	 * @param int
	 * 			  dept_ids 部门ID	            
	 * @return boolean
	 */
	public static boolean deleteDeptManager(String user_ids,String dept_id,SettingLogsBean stl){
		if(deptDao.deleteDeptManager(user_ids,dept_id,stl))
		{
			DeptManager.reloadDept();
			//删除部门管理员与角色的关联用户
			deleteDeptManagerRoleUser(user_ids);
			return true;
		}			
		else
		{
			return false;
		}
	}
	
	/**
	 * 操作部门管理员写角色用户的关联信息
	 * 	 
	 * @param String
	 * 			  new_user_ids 新增加的部门管理员ID
	 * @param String 
	 * 			  old_dept_manager	原有的部门管理员ID
	 * @param SettingLogsBean stl            
	 * @return boolean
	 */
	public static boolean saveDeptManagerRoleUser(String new_user_ids,String old_dept_manager,SettingLogsBean stl)
	{
		try{
			deleteDeptManagerRoleUser(old_dept_manager);
			//添加部门管理员与角色的关联关系			
			RoleUserBean rub = new RoleUserBean();
			rub.setApp_id(PublicTableDAO.APP_ORG);
			rub.setRole_id(JconfigUtilContainer.systemRole().getProperty("dept_manager", "", "role_id"));
			rub.setUser_id(new_user_ids);			
			RoleUserManager.insertRoloUserHandl(rub,stl);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除管理员与角色用户的关联信息
	 * 	 
	 * @param String
	 * 			  user_ids 部门管理员ID	
	 * @param SettingLogsBean stl            
	 * @return boolean
	 */
	public static void deleteDeptManagerRoleUser(String user_ids)
	{
		//得到老的管理员ID,判断它是否还是别的部门管理员,如果不是,从role_user关联表中删除它与部门管理员角色的关联
		if(user_ids != null && !"".equals(user_ids))
		{
			String u_ids = "";
			String[] tempA = user_ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				String dept_ids = DeptManager.getDeptByUserManager(tempA[i]);				
				if(dept_ids == null || "".equals(dept_ids.trim()))
					u_ids += ","+tempA[i];	
			}				
			if(!"".equals(u_ids))
			{	
				u_ids = u_ids.replaceAll("^\\D+", "");
				//删除部门管理员与角色的关联用户
				RoleUserManager.deleteRoleUserByUserAndRoleID(u_ids,JconfigUtilContainer.systemRole().getProperty("dept_manager", "", "role_id"));
			}
		}
	}
}
