package com.deya.wcm.services.org.user;

import java.util.List;

import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.org.app.AppBean;
import com.deya.wcm.bean.org.role.RoleBean;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.org.operate.OperateManager;
import com.deya.wcm.services.org.role.RoleUserManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;

/**
 *  用户权限管理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 用户权限管理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class UserOptManager {
	/**
	 * 根据用户ID得到此用户所能管理的应用系统树
	 * 
	 * @param int user_id
	 * @return
	 */
	public static String getAppJSONStrByUserID(String user_id)
	{
		String json_str = "[";
		String role_ids = RoleUserManager.getAllUserRoleIDS(user_id);	
		List<AppBean> l = OperateManager.getOptAppListbyRoleID(role_ids);
		
		int i=0;
		if(l != null && l.size() > 0)
		{
			for(AppBean ab : l)
			{
				if(i > 0)
					json_str +=",";
				json_str += "{\"id\":"+i+",\"text\":\""+ab.getApp_name()+"\",\"attributes\":{\"state\":\"is_sub\",\"real_id\":\""+ab.getApp_id()+"\"}";
				if("cms".equals(ab.getApp_id()))
				{
					json_str += getSiteJSONStrByUserID(user_id);
				}
				if("zwgk".equals(ab.getApp_id()))
				{
					json_str += getZWGKJSONStrByUserID(user_id);
				}
				i += 1;
				json_str += "}";
			}
		}
		json_str += "]";
		return json_str;
	}
	
	public static String getSiteJSONStrByUserID(String user_id)
	{
		String json_str = "";
		List<String> site_list = RoleUserManager.getAllUserSiteList(user_id,"cms");
		if(site_list != null && site_list.size() > 0)
		{
			json_str += ",\"children\":[";
			int i=0;
			for(String s : site_list)
			{
				SiteBean sb = SiteManager.getSiteBeanBySiteID(s);
				if(sb != null)
				{
					if(i > 0)
						json_str += ",";
					json_str += "{\"id\":"+(10000+i)+",\"text\":\""+sb.getSite_name()+"\",\"attributes\":{\"state\":\"is_leaf\",\"real_id\":\""+s+"\"}}";
					i++;
				}				
			}
			json_str += "]";
		}
		return json_str;
	}
	
	public static String getZWGKJSONStrByUserID(String user_id)
	{
		String json_str = "";
		List<String> site_list = RoleUserManager.getAllUserSiteList(user_id,"zwgk");
		if(site_list != null && site_list.size() > 0)
		{
			json_str += ",\"children\":[";
			int i=0;
			for(String s : site_list)
			{
				GKNodeBean gnb = GKNodeManager.getGKNodeBeanByNodeID(s);
				if(gnb != null)
				{
					if(i > 0)
						json_str += ",";
					json_str += "{\"id\":"+(100000+i)+",\"text\":\""+gnb.getNode_name()+"\",\"attributes\":{\"state\":\"is_leaf\",\"real_id\":\""+s+"\"}}";
					i++;
				}				
			}
			json_str += "]";
		}
		return json_str;
	}
	
	public static void main(String args[])
	{
		
		System.out.println(getAppJSONStrByUserID("72"));
	}
}
