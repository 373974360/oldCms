package com.deya.wcm.services.org.role;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.role.RoleAppBean;
import com.deya.wcm.bean.org.role.RoleBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.org.role.RoleDAO;
import com.deya.wcm.server.LicenseCheck;
import com.deya.wcm.services.cms.workflow.WorkFlowManager;

/**
 *  组织机构角色管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 组织机构角色管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class RoleManager implements ISyncCatch{
	private static TreeMap<String,RoleBean> role_map = new TreeMap<String, RoleBean>();	//角色缓存
	
	private static TreeMap<String,RoleAppBean> role_app_map = new TreeMap<String, RoleAppBean>();	//角色应用系统关联缓存
	private static String not_show_role_ids = JconfigUtilContainer.systemRole().getProperty("role_ids", "", "not_show_role");//不需要显示在页面上的角色ID
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
		List<RoleAppBean> role_app_list = RoleDAO.getAllRoleAppList();
		//System.out.println("RoleManager reloadRoleApp+++++++++++++++++=="+role_app_list);
		role_app_map.clear();
		if (role_app_list != null && role_app_list.size() > 0) {
			for (RoleAppBean rab : role_app_list) {
				if(LicenseCheck.isHaveApp(rab.getApp_id()))
					role_app_map.put(rab.getRole_app_id() + "", rab);				
			}
		}
		
		List<RoleBean> role_list = RoleDAO.getAllRoleList();
		//System.out.println("RoleManager reloadRole+++++++++++++++++=="+role_list);
		role_map.clear();
		if (role_list != null && role_list.size() > 0) {
			for (RoleBean rb : role_list) {
				if(LicenseCheck.isHaveApp(rb.getApp_id()))
				{
					role_map.put(rb.getRole_id() + "", rb);
					role_map.get(rb.getRole_id() + "").setA_app_id(getAppIDSByRoleID(rb.getRole_id() + ""));		
				}
			}
		}	
	}
	
	/**
	 * 初始加载角色信息
	 * 
	 * @param
	 * @return
	 */
	public static void reloadRole() {
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.role.RoleManager");
	}
	
	/**
	 * 初始加载角色应用系统关联缓存
	 * 
	 * @param
	 * @return
	 */
	public static void reloadRoleApp()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.role.RoleManager");
	}
	
	/**
	 * 获取所有的角色列表，用于登录时超级管理员拥有所有的角色
	 * 
	 * @param
	 * @return
	 */
	public static List<RoleBean> getAllRoleList()
	{
		List<RoleBean> l = new ArrayList<RoleBean>();
		Set<String> set = role_map.keySet();
		for(String s : set)
		{
			l.add(role_map.get(s));
		}
		return l;
	}
	
	/**
	 * 得到角色MAP
	 * 
	 * @param
	 * @return
	 */
	public static Map<String,RoleBean> getRoleMap()
	{
		return role_map;
	}
	
	
	
	
	/**
	 * 根据应用系统ID和站点ID得到角色列表(用于缓存读取)
	 * 
	 * @param String app_id
	 * @param String site_id
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<RoleBean> getRoleListByAPPAndSite(String app_id,String site_id)
	{
		List<RoleBean> role_list = new ArrayList<RoleBean>();

		Iterator iter = role_app_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();	
			RoleAppBean rab = role_app_map.get((String) entry.getKey());
			//if(!(","+not_show_role_ids+",").contains(","+rab.getRole_id()+","))
			//{
				if(site_id == null || "".equals(site_id))
				{
					if(app_id.equals(rab.getApp_id()))
						role_list.add(getRoleBeanByRoleID(rab.getRole_id()+""));	
				}else
				{
					if(app_id.equals(rab.getApp_id()) && ("".equals(rab.getSite_id()) || site_id.equals(rab.getSite_id())))
						role_list.add(getRoleBeanByRoleID(rab.getRole_id()+""));
				}		
			//}
		}	
		return role_list;
	}
	
	/**
	 * 根据应用系统ID和站点ID得到角色列表(从数据库中查询，用于在页面上列出角色列表)
	 * 
	 * @param Map<String,String> m (app_id,site_id,start_num,page_size,con_name,con_value)
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<RoleBean> getRoleListForDB(Map<String,String> m)
	{			
		//if(not_show_role_ids != null && !"".equals(not_show_role_ids.trim()) && !"system".equals(m.get("app_id")))
			//m.put("not_show_role_ids", not_show_role_ids);		
		
		return RoleDAO.getRoleListByDB(m);
	}
	
	/**
	 * 根据应用系统ID和站点ID得到角色总数(用于在页面上列出角色列表总数)
	 * 
	 * @param String app_id
	 * @param String site_id
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static String getRoleCount(Map<String,String> m)
	{
		String app_id = m.get("app_id");
		String site_id = m.get("site_id");
		int count = 0;
		Iterator iter = role_app_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();	
			RoleAppBean rab = role_app_map.get((String) entry.getKey());
			if(app_id.equals(rab.getApp_id()) && ("".equals(site_id) || site_id.equals(rab.getSite_id())))
				count += 1;			
		}	
		return count+"";
	}
	
	/**
	 * 根据应用系统ID和站点ID得到角色总数(用于在页面上列出角色列表总数)
	 * 
	 * @param String app_id
	 * @param String site_id
	 * @return String
	 */	
	public static String getRoleCountForDB(Map<String,String> m)
	{			
		if(not_show_role_ids != null && !"".equals(not_show_role_ids) && !"system".equals(m.get("app_id")))
			m.put("not_show_role_ids", not_show_role_ids);
		return RoleDAO.getRoleCountForDB(m);
	}
	
	/**
	 * 根据角色ID得到它所关联的应用系统（排除本身的所添加的应用系统）　用于在综合管理中修改角色时取出它的应用范围
	 * 
	 * @param String role_id
	 * 
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static String getAppIDSByRoleID(String role_id)
	{
		String app_ids = "";
		RoleBean rb = getRoleBeanByRoleID(role_id);
		if(rb != null)
		{
			Iterator iter = role_app_map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();	
				RoleAppBean rab = role_app_map.get((String) entry.getKey());
				if(role_id.equals(rab.getRole_id() + "") && !rb.getApp_id().equals(rab.getApp_id()))
					app_ids += ","+rab.getApp_id();			
			}
			if(app_ids != null && !"".equals(app_ids))
				app_ids = app_ids.substring(1);
		}	
		return app_ids;
	}
	
	/**
	 * 根据角色ID得到角色对象
	 * 
	 * @param String role_id
	 *            
	 * @return RoleBean
	 */
	public static RoleBean getRoleBeanByRoleID(String role_id)
	{
		if(role_map.containsKey(role_id))
		{
			return role_map.get(role_id);
		}
		else
		{
			RoleBean rb = RoleDAO.getRoleBeanByRoleID(role_id);
			if(rb != null && LicenseCheck.isHaveApp(rb.getApp_id()))
			{
				role_map.put(role_id, rb);
			}
			return rb;
		}
	}
	
	/**
	 * 根据多个角色ID得到角色名称
	 * 
	 * @param String role_ids
	 *            
	 * @return String
	 */
	public static String getRoleNamesbyRoleIDS(String role_ids)
	{
		String names = "";
		if(role_ids != null && !"".equals(role_ids))
		{
			String[] tempA = role_ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				RoleBean rb = getRoleBeanByRoleID(tempA[i]);
				if(rb != null)
					names += ","+rb.getRole_name();
			}
			if(names != null && !"".equals(names.trim()))
				names = names.substring(1);
		}
		return names;
	}
	
	/**
     * 插入角色信息
     * @param RoleBean rb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertRole(RoleBean rb,SettingLogsBean stl){
		if(RoleDAO.insertRole(rb, stl))
		{
			reloadRole();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改角色信息
     * @param RoleBean rb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateRole(RoleBean rb,SettingLogsBean stl){
		if(RoleDAO.updateRole(rb, stl))
		{
			reloadRole();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除角色信息
     * @param String role_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteRole(String role_ids,SettingLogsBean stl){
		if(RoleDAO.deleteRole(role_ids, stl))
		{			
			reloadRole();		
			//删除角色与权限关联
			RoleOptManager.deleteOptReleRoleByRoleID(role_ids);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除角色前判断角色是否跟用户，用户组，工作流有关联，有的话不让删除
     * @param String role_ids
     * @param String app_id
     * @param String site_id
     * @return String
     * */
	public static String deleteRoleBeforeChecked(String role_id,String app_id,String site_id)
	{
		String msg = "";
		
		if(RoleUserManager.getUserListByRole(role_id,app_id,site_id).size() > 0)
		{
			msg += "user,";
		}
		if(RoleUGroupManager.getGroupListByRole(role_id,app_id,site_id).size() > 0)
		{			
			msg += "group,";
		}
		if(WorkFlowManager.getStepCountByRoleID(role_id) > 0)
		{
			msg += "workflow,";
		}		
		return msg;
	}
		
	
	public static void main(String[] args)
	{
		//insertRoleTest();
		//updateRoleTest();
		//deleteRole("3,4",new SettingLogsBean());
		
		System.out.println(getRoleListByAPPAndSite("zwgk",""));
	}
	
	public static void insertRoleTest()
	{
		RoleBean rb = new RoleBean();
	
		rb.setA_app_id("");
		rb.setApp_id("system");
		rb.setRele_shared(0);
		rb.setRole_memo("role_memo");
		rb.setRole_name("role_name");
		rb.setSite_id("");
		
		insertRole(rb,new SettingLogsBean());
	}
	
	public static void updateRoleTest()
	{
		RoleBean rb = new RoleBean();
		rb.setRole_id(1);
		rb.setA_app_id("control,cms");
		
		rb.setApp_id("system");
		rb.setRele_shared(1);
		rb.setRole_memo("role_memo1");
		rb.setRole_name("role_name1");
		rb.setSite_id("site_id1");
		updateRole(rb,new SettingLogsBean());
	}
}
