package com.deya.wcm.services.org.user;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.org.app.AppBean;
import com.deya.wcm.bean.org.operate.MenuBean;
import com.deya.wcm.bean.org.operate.OperateBean;
import com.deya.wcm.bean.org.role.RoleBean;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.bean.org.user.UserBean;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.bean.zwgk.node.GKNodeCategory;
import com.deya.wcm.jsonlistener.MySessionContext;
import com.deya.wcm.jsonlistener.MySessionListener;
import com.deya.wcm.server.LicenseCheck;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.org.operate.MenuManager;
import com.deya.wcm.services.org.operate.OperateManager;
import com.deya.wcm.services.org.role.RoleManager;
import com.deya.wcm.services.org.role.RoleOptManager;
import com.deya.wcm.services.org.role.RoleUserManager;
import com.deya.wcm.services.zwgk.node.GKNodeCateManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;

/**
 *  处理用户登录类.
 * <p>Title: CicroDate</p>
 * <p>Description: 处理用户登录类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class UserLogin {
	/**
	 * 判断帐号密码是否一致，用于登录
	 * 
	 * @param String user_name
	 * @param String pass_word   
     * @param HttpServletRequest request     
	 * @return boolean
	 */
	public static String checkUserLogin(String user_name,String pass_word,HttpServletRequest request)
	{
		String ms = UserRegisterManager.checkUserLogin(user_name,pass_word);
		
		if("0".equals(ms))
		{
			LoginUserBean lub = UserRegisterManager.getLoginUserBeanByUname(user_name);
			lub.setIp(request.getRemoteAddr());
			setWCmSession(lub,request);	
			LogManager.insertLoginLog(lub);
		}
		return ms;
	}
	
	/**
	 * 注销登录
	 * 
     * @param HttpServletRequest request     
	 * @return boolean
	 */
	public static boolean loginOut(HttpServletRequest request){
		LogManager.insertLogoutLog(getUserBySession(request));
		SessionManager.remove(request, "cicro_wcm_user");		
		return true;
	}
	
	/**
	 * 登录成功，将用户对象写入session
	 * 
	 * @param UserBean ub
	 * @param HttpServletRequest request           
	 * @return boolean
	 */
	public static void setWCmSession(LoginUserBean lub,HttpServletRequest request)
	{
        boolean hasLogin = MySessionListener.checkIfHasLogin(lub);
        HttpSession session = request.getSession();
        if (hasLogin)
            MySessionListener.removeUserSession(lub.getUser_id());
        MySessionListener.addUserSession(session);

		SessionManager.set(request, "cicro_wcm_user", lub);
	}
	
	/**
	 * 从session中判断用户是否已经登录
	 * 
	 * @param HttpServletRequest request           
	 * @return boolean
	 */
	public static boolean checkLoginBySession(HttpServletRequest request)
	{
		if(getUserBySession(request) == null)
			return false;
		else
			return true;
	}
	
	/**
	 * 从session得到用户对象
	 * 
	 * @param HttpServletRequest request           
	 * @return boolean
	 */
	public static LoginUserBean getUserBySession(HttpServletRequest request)
	{
		return (LoginUserBean)SessionManager.get(request, "cicro_wcm_user");
	}
	
	/**
	 * 根据用户ID得到他能管理的所有菜单对象列表
	 * 
	 * @param String user_id
	 * @return List
	 *
	public static List<MenuBean> getMenuListByUserID(String user_id)
	{		
		List<MenuBean> menu_list;
		//判断此用户 是具有超级管理员的角色,超级管理员拥有所有角色,admin为超级管理员
		if(RoleUserManager.isAppSuperManager(user_id,"admin"))
		{
			menu_list = MenuManager.getChildMenuListByDeep(1);
			for(int i=0;i<menu_list.size();i++)
			{
				List<MenuBean> ml = menu_list.get(i).getChild_menu_list();
				if(ml != null && ml.size() > 0)
				{
					for(int j=0;j<ml.size();j++)
					{
						if(ml.get(j).getMenu_id() == MenuManager.CMS_MENU_ID)
						{//将所能管理的站点写入到栏目中
							setSiteInfoInMenuList(ml.get(j),user_id);
						}
						if(ml.get(j).getMenu_id() == MenuManager.ZWGK_MENU_ID)
						{//将所能管理的站点写入到栏目中					
							setZWGKInfoInMenuList(ml.get(j),user_id);
						}
					}
				}
			}			
		}else
		{		
			//第一步：获取该人员所拥有的角色列表
			List<RoleBean> role_list = RoleUserManager.getAllUserRoleList(user_id);
			//第二步：根据角色列表得到该人员所能看到的菜单（这里主要列表除内容管理外的所有菜单）
			menu_list = getMenuListByRole(role_list,false,MenuManager.ROOT_MENU_ID);	
			//循环菜单列表,得到内容管理或公开的目录
			if(menu_list != null && menu_list.size() > 0)
			{
				for(int i=0;i<menu_list.size();i++)
				{					
					List<MenuBean> ml = menu_list.get(i).getChild_menu_list();
					if(ml != null && ml.size() > 0)
					{
						for(int j=0;j<ml.size();j++)
						{
							if(ml.get(j).getMenu_id() == MenuManager.CMS_MENU_ID)
							{//将所能管理的站点写入到栏目中
								setSiteInfoInMenuList(ml.get(j),user_id);
							}
							if(ml.get(j).getMenu_id() == MenuManager.ZWGK_MENU_ID)
							{//将所能管理的站点写入到栏目中					
								setZWGKInfoInMenuList(ml.get(j),user_id);
							}
						}
					}
				}		
			}
		}
		return menu_list;
	}
	*/
	
	/**
	 * 根据用户ID得到他能管理的所有菜单对象列表
	 * 
	 * @param String user_id
	 * @return List
	 */
	public static List<MenuBean> getMenuListByUserID(String user_id)
	{		
		List<RoleBean> role_list = new ArrayList<RoleBean>();
		List<MenuBean> menu_list;
		//判断此用户 是具有超级管理员的角色,超级管理员拥有所有角色,admin为超级管理员
		if(RoleUserManager.isAppSuperManager(user_id,"admin"))
		{
			role_list = RoleManager.getAllRoleList();
		}else
		{		
			//获取该人员所拥有的角色列表
			role_list = RoleUserManager.getAllUserRoleList(user_id);
		}
		//根据角色列表得到该人员所能看到的菜单（这里主要列表除内容管理外的所有菜单）
		menu_list = getMenuListByRole(role_list,false,MenuManager.ROOT_MENU_ID);	
		//循环菜单列表,得到内容管理或公开的目录
		if(menu_list != null && menu_list.size() > 0)
		{
			for(int i=0;i<menu_list.size();i++)
			{					
				List<MenuBean> ml = menu_list.get(i).getChild_menu_list();
				if(ml != null && ml.size() > 0)
				{
					for(int j=0;j<ml.size();j++)
					{
						if(ml.get(j).getMenu_id() == MenuManager.CMS_MENU_ID)
						{//将所能管理的站点写入到栏目中
							setSiteInfoInMenuList(ml.get(j),user_id);
						}
						if(ml.get(j).getMenu_id() == MenuManager.ZWGK_MENU_ID)
						{//将所能管理的站点写入到栏目中					
							setZWGKInfoInMenuList(ml.get(j),user_id);
						}
					}
				}
			}		
		}
		
		return menu_list;
	}
	
	/**
	 * 将所能管理的站点写入到栏目中
	 * @param MenuBean mb 菜单对象	 
	 * @param String user_id
	 * @return List
	 */
	public static void setSiteInfoInMenuList(MenuBean mb,String user_id)
	{
		//根据用户ID得到所能管理的站点ID
		List<String> site_list = getAllUserSiteList(user_id,"cms");
		
		List<MenuBean> child_menu_list = new ArrayList<MenuBean>();
		if(site_list != null && site_list.size() > 0)
		{
			for(int i=0;i<site_list.size();i++)
			{
				String site_id = (String)site_list.get(i);
				if(!"".equals(site_id.trim())){
					MenuBean cmb = new MenuBean();
					//站点为String,不能写入到menu_id,特用1000000往上累加,以免重复
					cmb.setMenu_id(1000000+i);
					//得到站点对象
					SiteBean sb = SiteManager.getSiteBeanBySiteID((String)site_list.get(i));
					if(sb != null)
					{
						//将站点名称写对菜单名称中
						cmb.setMenu_name(sb.getSite_name());
						//写死站点菜单的触发事件showSiteMenu(站点ID),此事件在页面中
						cmb.setHandls("showSiteMenu('"+site_id+"')");	
						
						List<RoleBean> cms_role_list = RoleUserManager.getRoleListByUserAppSite(user_id,"cms",site_id);
						/*
						//根据用户ID判断它是否站点管理员
						//if(RoleUserManager.isAppSuperManager(user_id,"cms"))
						if(RoleUserManager.isSiteManager(user_id,"cms",site_id))
						{//如果是,给写入站点管理员的角色
							cms_role_list.add(RoleManager.getRoleBeanByRoleID(JconfigUtilContainer.systemRole().getProperty("cms", "", "role_id")));
						}else
						{
							//如果不是,根据站点ID和应用ID查找到相应具有角色列表
							cms_role_list = RoleUserManager.getRoleListByUserAppSite(user_id,"cms",site_id);
						}*/				
						
						List<MenuBean> m_l = getMenuListByRole(cms_role_list,true,MenuManager.CMS_MENU_ID);//6表示内容管理						
						cmb.setChild_menu_list(m_l);
						
						child_menu_list.add(cmb);
					}
				}
			}
		}
		mb.setChild_menu_list(child_menu_list);
	}
	
	/**
	 * 将所能管理的节点写入到栏目中
	 * @param MenuBean mb 菜单对象	 
	 * @param String user_id
	 * @return List
	 */
	public static void setZWGKInfoInMenuList(MenuBean parent_mb,String user_id)
	{
		MenuBean mb = null;
		for(int i=0;i<parent_mb.getChild_menu_list().size();i++)
		{
			if(parent_mb.getChild_menu_list().get(i).getMenu_id() == MenuManager.GKNODE_MENU_ID)
			{
				mb = parent_mb.getChild_menu_list().get(i);
			}
		}
		if(mb != null)
		{
			//根据用户ID得到所能管理的站点ID
			List<String> node_list = getAllUserSiteList(user_id,"zwgk");
			List<MenuBean> child_menu_list = new ArrayList<MenuBean>();
			
			if(node_list != null && node_list.size() > 0)
			{
				for(int i=0;i<node_list.size();i++)
				{
					String node_id = (String)node_list.get(i);
					if(!"".equals(node_id.trim())){
						MenuBean cmb = new MenuBean();
						//站点为String,不能写入到menu_id,特用2000000往上累加,以免重复
						cmb.setMenu_id(2000000+i);
						//得到节点对象
						GKNodeBean gnb = GKNodeManager.getGKNodeBeanByNodeID(node_id);
						
						if(gnb != null)
						{
							//将站点名称写对菜单名称中
							cmb.setMenu_name(gnb.getNode_name());
							//写死站点菜单的触发事件showSiteMenu(站点ID),此事件在页面中
							cmb.setHandls("showSiteMenu('"+node_id+"')");
							
							List<RoleBean> cms_role_list = RoleUserManager.getRoleListByUserAppSite(user_id,"zwgk",node_id);
							/*
							//根据用户ID判断它是否节点管理员gk_admin 这个是公开的管理员,zwgk这个标识给节点管理员了：（
							//if(RoleUserManager.isAppSuperManager(user_id,"gk_admin"))
							if(RoleUserManager.isSiteManager(user_id,"gk_admin",node_id))
							{//如果是,给写入站点管理员的角色
								cms_role_list.add(RoleManager.getRoleBeanByRoleID(JconfigUtilContainer.systemRole().getProperty("zwgk", "", "role_id")));
							}else
							{
								//如果不是,根据站点ID和应用ID查找到相应具有角色列表
								cms_role_list = RoleUserManager.getRoleListByUserAppSite(user_id,"zwgk",node_id);
							}*/				
							
							List<MenuBean> m_l = getMenuListByRole(cms_role_list,true,MenuManager.GKNODE_MENU_ID);//6表示内容管理						
							cmb.setChild_menu_list(m_l);
							
							child_menu_list.add(cmb);
						}					
					}
				}
			}
			mb.setChild_menu_list(child_menu_list);
		}
	}
	
	/**
	 * 根据用户ID得到他能管理的应用系统
	 * 
	 * @param String user_id
	 * @return List
	 */
	public static List<AppBean> getAppListByUserID(String user_id)
	{
		return RoleUserManager.getAllUserAppList(user_id);		
	}
	
	/**
     * 根据用户ID得到所有的站点对象包括该用户所在的用户组
     * @param String user_id 
     * @param String app_id
     * @return List
     * */
	public static List<SiteBean> getAllUserSiteObjList(String user_id,String app_id)
	{
		List<String> l = getAllUserSiteList(user_id,app_id);
		
		if(l != null && l.size() > 0)
		{
			List<SiteBean> site_list = new ArrayList<SiteBean>();
			for(String s : l)
			{
				SiteBean sb = SiteManager.getSiteBeanBySiteID(s);
				if(sb != null)
					site_list.add(sb);
			}
			
			return site_list;
		}else
			return null;
	}
	
	/**
     * 根据用户ID得到所有的节点对象包括该用户所在的用户组
     * @param String user_id 
     * @param String app_id
     * @return List
     * */
	public static List<GKNodeBean> getAllUserGKNodeObjList(String user_id,String app_id)
	{
		List<String> l = getAllUserSiteList(user_id,app_id);
		
		if(l != null && l.size() > 0)
		{
			List<GKNodeBean> node_list = new ArrayList<GKNodeBean>();
			for(String s : l)
			{
				GKNodeBean node = GKNodeManager.getGKNodeBeanByNodeID(s);
				if(node != null)
					node_list.add(node);
			}
			
			return node_list;
		}else
			return null;
	}
	
	/**
	 * 根据用户ID得到他能管理的站点
	 * 
	 * @param String user_id
	 * @param String app_id
	 * @return List
	 */
	public static List<String> getAllUserSiteList(String user_id,String app_id)
	{
		String site_ids = "";
		List<String> l = RoleUserManager.getAllUserSiteList(user_id,app_id);
		
		if(l != null && l.size() > 0)
		{
			for(String s:l)
			{
				site_ids += ","+s;
			}
			l.clear();
			//进行排序
			sortSite(l,site_ids,app_id,"0");
			return l;
		}
		return null;
	}
	
	public static void sortSite(List<String> l,String site_ids,String app_id,String parent_siteid)
	{
		site_ids = site_ids+",";
		if("cms".equals(app_id))
		{
			List<SiteBean> sl = SiteManager.getChildSiteListByDeep(parent_siteid);
			if(sl != null && sl.size() > 0)
			{
				for(SiteBean s : sl)
				{
					if(site_ids.contains(","+s.getSite_id()+","))
					{
						l.add(s.getSite_id());
					}
					sortSite(l,site_ids,app_id,s.getSite_id());
				}
			}
		}
		if("zwgk".equals(app_id))
		{
			List<GKNodeCategory> gkCategoryList = GKNodeCateManager.getChildCategoryList(Integer.parseInt(parent_siteid));
			if(gkCategoryList != null && gkCategoryList.size() > 0)
			{
				for(GKNodeCategory gc : gkCategoryList)
				{
					List<GKNodeBean> nodeList =  GKNodeManager.getGKNodeListByCateID(gc.getNodcat_id());
					if(nodeList != null && nodeList.size() > 0)
					{
						for(GKNodeBean node : nodeList)
						{
							if(site_ids.contains(","+node.getNode_id()+","))
							{
								l.add(node.getNode_id());
							}
						}
					}
					sortSite(l,site_ids,app_id,gc.getNodcat_id()+"");
				}
			}
		}
	}
	
	/**
	 * 根据用户ID,应用ID,站点ID判断登录人是否是站点管理员或超级管理员
	 * 
	 * @param String user_id
	 * @param String app_id
	 * @param String site_id
	 * @return boolean
	 */
	public static boolean isSiteManager(String user_id,String app_id,String site_id)
	{
		String temp_app = app_id;
		if(app_id.equals("zwgk"))
		{//根据用户ID判断它是否节点管理员gk_admin 这个是公开的管理员,zwgk这个标识给节点管理员了：（
			temp_app = "gk_admin";
		}
		return RoleUserManager.isAppSuperManager(user_id,"admin") || RoleUserManager.isSiteManager(user_id,temp_app,site_id);
	}
	
	
	/**
	 * 根据用户ID,应用ID得到他能管理的权限ID
	 * 
	 * @param String user_id
	 * @param String app_id
	 * @param String site_id
	 * @return String
	 */
	public static String getOptIDSByUserAPPSite(String user_id,String app_id,String site_id)
	{		
		List<RoleBean> role_list = RoleUserManager.getRoleListByUserAppSite(user_id,app_id,site_id);
		
		if(role_list != null && role_list.size() > 0)
		{
			return getOptIDSByRole(role_list);
		}
		else
			return "";
		
	}
	
	/**
	 * 根据用户ID得到他能管理的权限ID
	 * 
	 * @param String user_id
	 * @return String
	 */
	public static String getOptIDSByUserID(String user_id)
	{
		//首先得到角色列表
		List<RoleBean> role_list = RoleUserManager.getAllUserRoleList(user_id);
		if(role_list != null && role_list.size() > 0)
		{
			return getOptIDSByRole(role_list);
		}
		else
			return "";
	}
	
	/**
	 * 根据用户ID得到他能管理的权限对象列表
	 * 
	 * @param String user_id
	 * @return List
	 */
	public static List<OperateBean> getOptListByUserID(String user_id)
	{
		List<OperateBean> opt_list = new ArrayList<OperateBean>();
		//首先得到角色列表
		List<RoleBean> role_list = RoleUserManager.getAllUserRoleList(user_id);
		if(role_list != null && role_list.size() > 0)
		{
			for(int i=0;i<role_list.size();i++)
			{//根据角色列表取得权限信息
				opt_list.addAll(RoleOptManager.getOptListByRoleID(role_list.get(i).getRole_id()+""));
			}
		}
		return opt_list;
	}
	
	/**
	 * 根据角色列表得到他能管理的权限ID
	 * 
	 * @param String user_id
	 * @return String
	 */
	public static String getOptIDSByRole(List<RoleBean> role_list)
	{
		String opt_ids = "";	
		
		if(role_list != null && role_list.size() > 0)
		{
			for(int i=0;i<role_list.size();i++)
			{
				//根据角色列表取得权限信息
				if(role_list.get(i) != null)
				{
					String ids = RoleOptManager.getOptIDSByRoleID(role_list.get(i).getRole_id()+"");
					if(ids != null && !"".equals(ids))
						opt_ids += ","+ids;
				}
			}
		}
		
		if(opt_ids != null && !"".equals(opt_ids))
			opt_ids = opt_ids.substring(1);
		return opt_ids;
	}
	
	/**
	 * 根据角色列表得到他能管理的所有菜单对象列表
	 * 
	 * @param String user_id
	 * @param boolean is_cmszwgk
	 * @return List
	 */
	public static List<MenuBean> getMenuListByRole(List<RoleBean> role_list,boolean is_cms,int menu_id)
	{
		//首先得到权限ID
		String opt_ids = getOptIDSByRole(role_list);
		//System.out.println("---"+opt_ids);
		if(opt_ids != null && !"".equals(opt_ids))
		{			
			return MenuManager.getMenuListByOptID(opt_ids,is_cms,menu_id);			
		}
		else
			return null;
	}	
	
	/**
	 * 根据用户ID得到他能管理的所有菜单对象json字符串
	 * 
	 * @param String user_id 用户ID
	 * @return String menu_id 要展示的第一级菜单ID,如果展示所有的,输入空
	 * @return String
	 */
	public static String menuListToJsonStrByUserID(String user_id,String menu_id)
	{
		//System.out.println("menuListToJsonStrByUserID------------start");
		String result = "";
		if(menu_id != null && !"".equals(menu_id.trim()))
		{
			//System.out.println("result===" + result);
			result = "["+menuListToStrHandl(getMenuListByUserID(user_id),menu_id)+"]";
			if(result.endsWith(",]")){
				result = result.substring(0,result.length()-2)+"]";
			}
			return result;
		}
		else{
			//System.out.println("result else ===" + result);
			result = "["+menuListToStrHandl(getMenuListByUserID(user_id))+"]";
			if(result.endsWith(",]")){
				result = result.substring(0,result.length()-2)+"]";
			}
			return result;
		}
	}
	
	/**
	 * 递归处理list转json字符串
	 * 
	 * @param String List<MenuBean> ml
	 * @return String menu_id 要展示的第一级菜单ID,如果展示所有的,输入空
	 * @return String
	 */
	public static String menuListToStrHandl(List<MenuBean> ml)
	{		
		int ml_size = ml.size();
		String json_str = "";
		if(ml != null && ml.size() > 0)
		{
			for(int i=0;i<ml.size();i++)
			{			
				//4 是 政务公开系统
				//397 是政务构件库
				//等于397说明是 政务构件库  菜单
				if(ml.get(i).getMenu_id()==397){
					if(!LicenseCheck.isHaveApp("zwgk") && !LicenseCheck.isHaveApp("appeal") && !LicenseCheck.isHaveApp("ggfw")){
						ml_size=ml_size-1;
						continue;
					}
				}
				//等于4说明是 政务公开系统 菜单
				if(ml.get(i).getMenu_id()==4){
					if(!LicenseCheck.isHaveApp("zwgk")){
						ml_size=ml_size-1;
						continue;
					}
				}
				
				
				json_str += "{";
				String app_id = "";			
				
				OperateBean ob = OperateManager.getOperateBean(ml.get(i).getOpt_id()+"");
				if(ob != null)
					app_id = ob.getApp_id();
				
				json_str += "\"id\":"+ml.get(i).getMenu_id()+",\"text\":\""+ml.get(i).getMenu_name()+"\",\"attributes\":{\"app_id\":\""+app_id+"\",\"url\":\""+ml.get(i).getMenu_url()+"\",\"handls\":\""+ml.get(i).getHandls()+"\"}";
				List<MenuBean> child_m_list = ml.get(i).getChild_menu_list();
				if(child_m_list != null && child_m_list.size() > 0)
					json_str += ",\"children\":["+menuListToStrHandl(child_m_list)+"]";
				json_str += "}";
				//if(i+1 != ml.size())
				if(i+1 != ml_size)
					json_str += ",";				
			}
		}
		return json_str;
	}
		
	public static String menuListToStrHandl(List<MenuBean> ml,String menu_id)
	{
		String json_str = "";
		if(ml != null && ml.size() > 0)
		{
			for(int i=0;i<ml.size();i++)
			{
				List<MenuBean> menu_l = ml.get(i).getChild_menu_list();
				if(menu_l != null && menu_l.size() > 0)
				{
					for(int j=0;j<menu_l.size();j++)
					{
						if(menu_id.equals(menu_l.get(j).getMenu_id()+"") || menu_id.equals(""))
						{
							json_str += "{";
							String app_id = "";			
							
							OperateBean ob = OperateManager.getOperateBean(menu_l.get(j).getOpt_id()+"");
							if(ob != null){
								app_id = ob.getApp_id();
							}
							json_str += "\"id\":"+menu_l.get(j).getMenu_id()+",\"text\":\""+menu_l.get(j).getMenu_name()+"\",\"attributes\":{\"app_id\":\""+app_id+"\",\"url\":\""+menu_l.get(j).getMenu_url()+"\",\"handls\":\""+menu_l.get(j).getHandls()+"\"}";
							List<MenuBean> child_m_list = menu_l.get(j).getChild_menu_list();
							if(child_m_list != null && child_m_list.size() > 0)
								json_str += ",\"children\":["+menuListToStrHandl(child_m_list)+"]";
							json_str += "}";
							if(i+1 != menu_l.size())
								json_str += ",";
						}
					}
				}				
			}
		}
		return json_str;
	}
	
	/**
     * 得到我的平台特殊菜单节点
     * @return String
     * */
	public static String getMyPlatformTreeStr()
	{
		return "["+menuListToStrHandl(MenuManager.getMyPlatform())+"]";
	}

	/**
	 * 用于单点登录用户验证，只要匹配数据库中的用户名即可
	 *
	 * @param String user_name
	 * @param String pass_word
	 * @param HttpServletRequest request
	 * @return boolean
	 */
	public static boolean checkUserLogin(String user_name,HttpServletRequest request)
	{
		UserBean userBean = UserRegisterManager.getUserBeanByUname(user_name);
		if(userBean != null)
		{
			LoginUserBean lub = UserRegisterManager.getLoginUserBeanByUname(user_name);
			lub.setIp(request.getRemoteAddr());
			setWCmSession(lub,request);
			LogManager.insertLoginLog(lub);
			return true;
		}else{
			return false;
		}
	}
	
	public static void main(String args[])
	{	
		//System.out.println(getOptIDSByUserAPPSite("172","cms",""));
		//System.out.println(getAllUserSiteList("1","zwgk"));
		System.out.println(menuListToJsonStrByUserID("1",""));
		//System.out.println(RoleOptManager.getOptIDSByRoleID("22"));
		//System.out.println(getAllUserSiteList("721","cms"));	
		//System.out.println(getAllUserSiteList("116"));
		//System.out.println(getMyPlatformTreeStr());
		//System.out.println(RoleUserManager.getAllUserRoleList("187"));
		//System.out.println(getAllUserGKNodeObjList("1","zwgk"));
		
	}
}
