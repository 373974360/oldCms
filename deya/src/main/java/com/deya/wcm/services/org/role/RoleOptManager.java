package com.deya.wcm.services.org.role;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.operate.OperateBean;
import com.deya.wcm.bean.org.role.RoleOptBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.org.role.RoleOptDAO;
import com.deya.wcm.server.LicenseCheck;
import com.deya.wcm.services.org.operate.OperateManager;

/**
 *  角色与权限关联逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 角色与权限关联逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class RoleOptManager implements ISyncCatch{
	@SuppressWarnings("unchecked")
	private static TreeMap<String,List> role_opt_map = new TreeMap<String,List>();//角色写权限关联缓存 key为角色ID 　list为权限集合
		
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
		List<RoleOptBean> role_opt_list = RoleOptDAO.getAllRoleReleOptList();
		//System.out.println("RoleManager reloadRoleOpt+++++++++++++++++=="+role_opt_list);
		role_opt_map.clear();
		if (role_opt_list != null && role_opt_list.size() > 0) {
			for (int i = 0; i < role_opt_list.size(); i++) {
				RoleOptBean rob = role_opt_list.get(i);
				OperateBean ob = OperateManager.getOperateBean(rob.getOpt_id()+"");
				if(role_opt_map.containsKey(rob.getRole_id()+""))
				{	
					if(ob != null)
						role_opt_map.get(rob.getRole_id()+"").add(ob);
				}else
				{
					if(ob != null)
					{
						List<OperateBean> oL = new ArrayList<OperateBean>();
						oL.add(OperateManager.getOperateBean(rob.getOpt_id()+""));
						role_opt_map.put(rob.getRole_id()+"", oL);
					}
				}
			}
		}
	}
	
	/**
	 * 初始加载角色与权限关联缓存 健为角色ID，值为权限对象集合列表
	 * 
	 * @param
	 * @return
	 */
	
	public static void reloadRoleOpt()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.role.RoleOptManager");
	}
	
	
	
	/**
	 * 根据角色ID得到它所关联的权限列表
	 * 
	 * @param String role_id
	 * @return List
	 */	
	@SuppressWarnings("unchecked")
	public static List<OperateBean> getOptListByRoleID(String role_id)
	{
		if(role_opt_map.containsKey(role_id))
		{
			return role_opt_map.get(role_id);
		}else
		{
			List<RoleOptBean> l = new ArrayList<RoleOptBean>();
			List<OperateBean> oL = new ArrayList<OperateBean>();
			if(l != null && l.size() > 0)
			{
				for(int i=0;i<l.size();i++)
				{
					oL.add(OperateManager.getOperateBean(l.get(i).getOpt_id()+""));
				}
			}
			role_opt_map.put(role_id, oL);
			return oL;
		}
	}
	
	/**
	 * 根据角色ID得到它所关联的权限ID
	 * 
	 * @param String role_id
	 * @return String
	 */	
	@SuppressWarnings("unchecked")
	public static String getOptIDSByRoleID(String role_id)
	{		
		String opt_ids = "";
		if(role_opt_map.containsKey(role_id))
		{
			List<OperateBean> l = role_opt_map.get(role_id);
			if(l != null && l.size() > 0)
			{
				for(OperateBean ob : l)
				{				
					if(LicenseCheck.isHaveApp(ob.getApp_id()))
					{
						opt_ids += ","+ob.getOpt_id();
					}
				}
			}
		}else
		{
			List<RoleOptBean> l = new ArrayList<RoleOptBean>();
			if(l != null && l.size() > 0)
			{
				for(int i=0;i<l.size();i++)
				{
					opt_ids += ","+l.get(i).getOpt_id();					
				}
			}			
		}
		
		if(opt_ids != null && !"".equals(opt_ids))
			opt_ids = opt_ids.substring(1);

		return opt_ids;
	}
	
	/**
     * 插入角色与权限关联(1个角色对应多个权限ID)
     * @param String role_id
     * @param String opt_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertOptReleRole(String role_id,String opt_ids,SettingLogsBean stl) 
	{
		if(RoleOptDAO.insertOptReleRole(role_id,opt_ids,stl))
		{
			reloadRoleOpt();
			return true;
		}else
			return false;
	}
	
	/**
     * 根据角色ID删除角色与权限信息关联
     * @param String role_id
     * @return boolean
     * */
	public static boolean deleteOptReleRoleByRoleID(String role_id){
		if(RoleOptDAO.deleteOptReleRoleByRoleID(role_id))
		{
			reloadRoleOpt();
			return true;
		}else
			return false;
	}
	
	public static void main(String args[])
	{
		insertOptReleRole("3","2,3,4,5,6,7,8",new SettingLogsBean());
		//System.out.println(getOptIDSByRoleID("5"));
	}
}
