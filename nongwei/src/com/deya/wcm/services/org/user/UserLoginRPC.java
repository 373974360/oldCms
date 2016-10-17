package com.deya.wcm.services.org.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.org.operate.MenuBean;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;

public class UserLoginRPC {
	
	  public static Map<String, String> getAllUserSiteList(String user_id, String app_ids)
	  {
	    Map m = new HashMap();
	    String[] tempA = app_ids.split(",");
	    for (int i = 0; i < tempA.length; ++i)
	    {
	      List<String> site_ids = UserLogin.getAllUserSiteList(user_id, tempA[i]);

	      if ((site_ids == null) || (site_ids.size() <= 0))
	        continue; 
	      for (String s : site_ids)
	      {
	        if (tempA[i].equals("cms"))
	        {
	          SiteBean sb = SiteManager.getSiteBeanBySiteID(s);
	          if (sb != null)
	            m.put(s, sb.getSite_name());
	        }
	        if (!(tempA[i].equals("zwgk")))
	          continue;
	        GKNodeBean gk = GKNodeManager.getGKNodeBeanByNodeID(s);
	        if (gk != null) {
	          m.put(s, gk.getNode_name());
	        }
	      }
	    }

	    return m;
	  }
	
	/**
	 * 得到当前sessionid
	 * 
     * @param HttpServletRequest request     
	 * @return String
	 */
	public static String getSessionID(HttpServletRequest request)
	{
		return request.getSession().getId();
	}
	
	/**
	 * 判断帐号密码是否一致，用于登录
	 * 
	 * @param String user_name
	 * @param String pass_word           
	 * @return boolean
	 */
	public static String checkUserLogin(String user_name,String pass_word,String auth_code,HttpServletRequest request)
	{
		String codeSession = (String)request.getSession().getAttribute("valiCode");
		if(!codeSession.equals(auth_code))
		{
			return "auth_code_error";
		}
		return UserLogin.checkUserLogin(user_name,pass_word,request);
	}
	
	/**
	 * 注销登录
	 * 
     * @param HttpServletRequest request     
	 * @return boolean
	 */
	public static boolean loginOut(HttpServletRequest request){
		return UserLogin.loginOut(request);
	}
	
	/**
	 * 根据用户ID得到他能管理的所有菜单对象列表
	 * 
	 * @param String user_id
	 * @return List
	 */
	public static List<MenuBean> getMenuListByUserID(String user_id)
	{
		return UserLogin.getMenuListByUserID(user_id);
	}
	
	/**
	 * 根据用户ID得到他能管理的所有菜单对象json字符串
	 * 
	 * @param String user_id 用户ID
	 * @return String menu_id 要展示的第一级菜单ID,如果展示所有的,输入空
	 * @return List
	 */
	public static String menuListToJsonStrByUserID(String user_id,String menu_id)
	{
		return UserLogin.menuListToJsonStrByUserID(user_id,menu_id);
	}
	
	/**
	 * 从session得到用户对象
	 * 
	 * @param HttpServletRequest request           
	 * @return boolean
	 */
	public static LoginUserBean getUserBySession(HttpServletRequest request)
	{
		return UserLogin.getUserBySession(request);
	}
	
	/**
	 * 根据用户ID得到他能管理的权限ID
	 * 
	 * @param String user_id
	 * @return String
	 */
	public static String getOptIDSByUserID(String user_id)
	{
		return UserLogin.getOptIDSByUserID(user_id);
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
		return UserLogin.getOptIDSByUserAPPSite(user_id,app_id,site_id);
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
		return UserLogin.isSiteManager(user_id,app_id,site_id);
	}
	
	/**
     * 得到我的平台特殊菜单节点
     * @return String
     * */
	public static String getMyPlatformTreeStr()
	{
		return UserLogin.getMyPlatformTreeStr();
	}
}
