package com.deya.wcm.services.appeal.cpUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.deya.wcm.bean.appeal.cpDept.CpDeptBean;
import com.deya.wcm.bean.appeal.cpUser.CPUserReleInfo;
import com.deya.wcm.bean.appeal.cpUser.CpUserBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.appeal.cpUser.CpUserDAO;
import com.deya.wcm.services.appeal.cpDept.CpDeptManager;
import com.deya.wcm.services.appeal.model.ModelManager;
import com.deya.wcm.services.org.role.RoleManager;
import com.deya.wcm.services.org.role.RoleUserManager;
import com.deya.wcm.services.org.user.UserManager;

public class CpUserManager implements ISyncCatch{
	private static List<CpUserBean> user_list = new ArrayList<CpUserBean>(); 
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		user_list.clear();
		user_list = CpUserDAO.getAllCpUserList();
	}
	
	public static void reloadCpUserDept()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appeal.cpUser.CpUserManager");
	}
	
	/**
	 * 根据选择的用户ID判断此用户是否被添加过
	 * @param m
	 * @return
	 */
	public static String userISExist(String user_ids)
	{
		String names = "";
		user_ids = ","+user_ids+",";
		if(user_list != null && user_list.size() > 0)
		{
			for(int i=0;i<user_list.size();i++)
			{
				if(user_ids.contains(","+user_list.get(i).getUser_id()+","))
				{
					names += ","+UserManager.getUserBeanByID(user_list.get(i).getUser_id()+"").getUser_realname();
				}
			}
			if(names != null && !"".equals(names.trim()))
				names = names.substring(1);
		}
		return names;
	}
	
	/**
	 * 得到所有已关联的用户
	 * @param m
	 * @return
	 */
	public static Map<Integer,CPUserReleInfo> getAllReleUserMap()
	{
		Map<Integer,CPUserReleInfo> m = new HashMap<Integer,CPUserReleInfo>();
		if(user_list != null && user_list.size() > 0)
		{
			for(int i=0;i<user_list.size();i++)
			{
				try{
					UserBean ub = UserManager.getUserBeanByID(user_list.get(i).getUser_id()+"");
					if(ub != null)
					{
						CPUserReleInfo curi = new CPUserReleInfo();	
						curi.setUser_id(ub.getUser_id());
						curi.setUser_realname(ub.getUser_realname());
						curi.setDept_id(user_list.get(i).getDept_id());
						curi.setDept_treeposition(CpDeptManager.getCpDeptBean(curi.getDept_id()).getDept_position());
						m.put(ub.getUser_id(), curi);
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return m;
	}
	
	/**
	 * 展现 [指定部门]下的所有用户
	 * @param m
	 * @return
	 */
	public static List<CPUserReleInfo> getUserListByDept(int dept_id){
		List<CPUserReleInfo> u_list = new ArrayList<CPUserReleInfo>(); 
		if(user_list != null && user_list.size() > 0)
		{
			for(int i=0;i<user_list.size();i++)
			{
				if(user_list.get(i).getDept_id() == dept_id)
				{
					UserBean ub = UserManager.getUserBeanByID(user_list.get(i).getUser_id()+"");
					CPUserReleInfo curi = new CPUserReleInfo();
					String model_ids = "";
					String role_ids = "";
					if(ub != null)
					{	
						curi.setUser_id(user_list.get(i).getUser_id());
						curi.setUser_realname(ub.getUser_realname());
						model_ids = ModelManager.getModelIDSByUserID(user_list.get(i).getUser_id());
						curi.setModel_ids(model_ids);
						curi.setModel_names(ModelManager.getModelNamebyIDS(model_ids));
						role_ids = RoleUserManager.getRoleIDSByUserAPP(user_list.get(i).getUser_id()+"","appeal","");	
						curi.setRole_ids(role_ids);
						curi.setRole_names(RoleManager.getRoleNamesbyRoleIDS(role_ids));						
						u_list.add(curi);
					}
				}	
			}
		}
		return u_list;
	}
	
	/**
	 * 根据多个部门ID得到它下面所关联的人员
	 * @param String dept_id
	 * @return String
	 */
	public static String getUserIdsByDeptID(String dept_ids)
	{
		String user_ids = "";
		dept_ids = ","+dept_ids+",";		
		if(user_list != null && user_list.size() > 0)
		{
			for(int i=0;i<user_list.size();i++)
			{
				if(dept_ids.contains(","+user_list.get(i).getDept_id()+","))
				{
					user_ids += ","+user_list.get(i).getUser_id();
				}
			}
		}
		if(user_ids != null && !"".equals(user_ids))
			user_ids = user_ids.substring(1);
		
		return user_ids;
	}
	
	/**
	 * 根据用户ID得到所能管理的部门id
	 * @param int user_id
	 * @return String dept_ids
	 */
	public static int getSQDeptIDbyUserID(int user_id)
	{
		int dept_id = 0;
		if(user_list != null && user_list.size() > 0)
		{
			for(int i=0;i<user_list.size();i++)
			{
				if(user_list.get(i).getUser_id() == user_id)
					dept_id = user_list.get(i).getDept_id();
			}			
		}
		return dept_id;
	}
		
	/**
	 * 根据用户ID所到它所在部门的平级单位列表(转办)
	 * @param int user_id
	 * @return String dept_id
	 */
	public static List<CpDeptBean> getBrotherDeptListByUserID(int user_id)
	{
		CpDeptBean deptB = CpDeptManager.getCpDeptBean(getSQDeptIDbyUserID(user_id));
		if(deptB != null)
		{
			return CpDeptManager.getChildDeptList(deptB.getParent_id()+"");
		}
		else
			return null;
	}
	
	/**
	 * 根据用户ID所到它所在部门的下级单位列表(交办)
	 * @param int user_id
	 * @return String dept_id
	 */
	public static List<CpDeptBean> getChildDeptListByUserID(int user_id)
	{		
		return CpDeptManager.getChildDeptList(getSQDeptIDbyUserID(user_id)+"");		
	}
	
	/**
	 * 根据用户ID所到它所在部门的父级单位列表(呈办　目前只取它的父节点)
	 * @param int user_id
	 * @return String dept_id
	 */
	public static List<CpDeptBean> getParentDeptListByUserID(int user_id)
	{		
		List<CpDeptBean> l = new ArrayList<CpDeptBean>();
		CpDeptBean cdb = CpDeptManager.getCpDeptBean(getSQDeptIDbyUserID(user_id));
		if(cdb != null)
			l.add(CpDeptManager.getCpDeptBean(cdb.getParent_id()));
		return l;		
	}
	
	/**
	 * 新建注册用户
	 * @param user
	 * @return
	 */
	public static boolean insertCpUser(int dept_id,String user_ids,SettingLogsBean stl){		
		if(CpUserDAO.insertCpUser(dept_id,user_ids,stl))
		{
			reloadCpUserDept();
			return true;
		}else
			return false;
	}
	
	/**
	 * 删除注册用户
	 * @param userIds
	 * @return
	 */
	public static boolean deleteCpUser(String dept_id,String userIds,SettingLogsBean stl){
		if(CpUserDAO.deleteCpUser(dept_id,userIds,stl))
		{	
			reloadCpUserDept();
			//删除业务与人员关联
			ModelManager.deleteModelReleUser(userIds);
			//删除人员与该应用的角色关联
			RoleUserManager.deleteRoleUserByUserRoleSite(userIds,"appeal","");
			return true;
		}else
			return false;			
	}	
	
	/**
	 * 
	 * 根据用户ID所到它所在部门的父级及父级兄弟节点列表(宁夏修改)
	 * @param int user_id
	 * @return String dept_id
	 */
	public static List<CpDeptBean> getParenAndBrotherDeptListByUserID(int user_id)
	{		
		List<CpDeptBean> l = new ArrayList<CpDeptBean>();
		CpDeptBean cdb = CpDeptManager.getCpDeptBean(getSQDeptIDbyUserID(user_id));
		CpDeptBean p_cdb = CpDeptManager.getCpDeptBean(cdb.getParent_id());
			
		if(CpDeptManager.getCpDeptBean(p_cdb.getParent_id()) != null)
		{
			return CpDeptManager.getChildDeptList(p_cdb.getParent_id()+"");
		}
		else
			return null;
	}
	
	public static void main(String[] args)
	{
		System.out.println(getAllReleUserMap());
	}
}