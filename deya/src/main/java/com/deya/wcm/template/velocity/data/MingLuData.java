package com.deya.wcm.template.velocity.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.services.org.dept.DeptManager;
import com.deya.wcm.services.org.user.UserManager;

public class MingLuData {
	public static DeptBean getDeptObject(String dept_id) {
		return DeptManager.getDeptBeanByID(dept_id);
	}
	
	public static String getDeptName(String dept_id)
	{
		return DeptManager.getDeptName(dept_id);
	}
	
	/**
     * 根据部门ID得到用户列表
     * @param 
     * @return List
     * */
	public static List<UserBean> getUserListForDept(String dept_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("dept_id", dept_id);
		m.put("sort_name", "sort");
		m.put("sort_type", "asc");
		m.put("is_publish", "1");
		return UserManager.getUserListByDeptIDForDB(m);
	}
	
	/**
     * 根据用户ID得到用户信息
     * @param 
     * @return UserBean
     * */
	public static UserBean getUserObject(String user_id)
	{
		return UserManager.getAllUserInfoByID(user_id);
	}
		
	/**
     * 根据部门ID得到允许发布的下级部门列表
     * @param String dept_id
     * @return List
     * */
	public static List<DeptBean> getDeptChildList(String dept_id)
	{
		DeptBean db = DeptManager.getDeptBeanByID(dept_id);
		if(db != null)
		{
			return DeptManager.getChildDeptListForPublish(dept_id);
		}else
			return null;
	}
	
	/**
     * 根据部门ID得到允许发布的部门树
     * @param String dept_id
     * @return String
     * */
	public static String getDeptTree(String dept_id)
	{		
		if(dept_id == null || "".equals(dept_id))
			dept_id = "1";
		
		DeptBean db = DeptManager.getDeptBeanByID(dept_id);
		if(db != null)
		{
			String json_str = "[{\"id\":\"dept_"+dept_id+"\",\"text\":\""+db.getDept_name()+"\"";
			String child_str = deptListToStrHandl(DeptManager.getChildDeptListForPublish(dept_id),false);
			if(child_str != null && !"".equals(child_str))
				json_str += ",\"children\":["+child_str+"]";
			json_str += "}]";
			return json_str;
		}else
			return "[]";		
	}
	
	/**
     * 根据部门ID得到允许发布的部门人员结合树
     * @param String dept_id
     * @return String
     * */
	public static String getDeptUserTree(String dept_id)
	{
		if(dept_id == null || "".equals(dept_id))
			dept_id = "1";
		
		DeptBean db = DeptManager.getDeptBeanByID(dept_id);
		if(db != null)
		{
			String json_str = "[{\"id\":\"dept_"+dept_id+"\",\"text\":\""+db.getDept_name()+"\"";
			String child_str = deptListToStrHandl(DeptManager.getChildDeptListForPublish(dept_id),true);
			String user_str = UserTreeHandl(dept_id);
			if(child_str != null && !"".equals(child_str))
			{
				if(user_str != null && !"".equals(user_str))
					child_str += ","+user_str;
				json_str += ",\"children\":["+child_str+"]";
			}else
			{
				if(user_str != null && !"".equals(user_str))
				{
					json_str += ",\"children\":["+user_str+"]";
				}
			}			
			json_str += "}]";
			return json_str;
		}else
			return "[]";
	}
	
	public static String deptListToStrHandl(List<DeptBean> dl,boolean add_user_str)
	{
		String json_str = "";	
		if(dl != null && dl.size() > 0)
		{			
			for(DeptBean db : dl)
			{				
				json_str += ",{";				
				json_str += "\"id\":\"dept_"+db.getDept_id()+"\",\"text\":\""+db.getDept_name()+"\",\"icon\":\"/cms.files/images/ico_dept.gif\"";
				List<DeptBean> child_d_list = DeptManager.getChildDeptListForPublish(db.getDept_id()+"");
				if(child_d_list != null && child_d_list.size() > 0)
				{
					String child_str = deptListToStrHandl(child_d_list,add_user_str);
					if(add_user_str)
					{
						String user_str = UserTreeHandl(db.getDept_id()+"");
						if(user_str != null && !"".equals(user_str))
							child_str += ","+user_str;
					}
					json_str += ",\"children\":["+child_str+"]";
				}else
				{
					if(add_user_str)
					{
						String user_str = UserTreeHandl(db.getDept_id()+"");
						if(user_str != null && !"".equals(user_str))
						{
							json_str += ",\"children\":["+user_str+"]";
						}
					}
				}
				json_str += "}";
			}	
			if(json_str != null && !"".equals(json_str))
				json_str = json_str.substring(1);
		}
		return json_str;
	}
	
	public static String UserTreeHandl(String dept_id)
	{
		String json_str = "";	
		List<UserBean> l = UserManager.getUserListForPublishByDeptID(dept_id);
		if(l != null && l.size() > 0)
		{
			for(UserBean ub : l)
			{
				json_str += ",{";	
				json_str += "\"id\":\"user_"+ub.getUser_id()+"\",\"text\":\""+ub.getUser_realname()+"\",\"icon\":\"/cms.files/images/ico_user.gif\"";
				json_str += "}";
			}
			if(json_str != null && !"".equals(json_str))
				json_str = json_str.substring(1);			
		}
		return json_str;
	}
	
	public static void main(String args[])
	{
		System.out.println(getDeptUserTree("1"));
		//System.out.println(UserManager.getUserListForPublishByDeptID("129"));
	}
}
