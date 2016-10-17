package com.deya.wcm.services.control.site;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.control.SiteDomainBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.role.RoleUserBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.control.SiteDAO;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import com.deya.wcm.services.minlu.MingLuManager;
import com.deya.wcm.services.org.role.RoleUserManager;
import com.deya.wcm.services.org.siteuser.SiteUserManager;

/**
 *  站点管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 站点管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SiteManager implements ISyncCatch{
	private static List<SiteBean> site_list = new ArrayList<SiteBean>();
	private static Map<String,SiteBean> site_map = new HashMap<String, SiteBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		site_map.clear();
		site_list.clear();
		site_list = SiteDAO.getAllSiteList();
		//System.out.println("=========" + site_list.size());
		if(site_list != null && site_list.size() > 0)
		{
			for(int i=0;i<site_list.size();i++)
			{
				site_list.get(i).setSite_domain(SiteDomainManager.getSiteDomainBySiteID(site_list.get(i).getSite_id()));
			    site_list.get(i).setSite_manager(RoleUserManager.getUsersByRole(JconfigUtilContainer.systemRole().getProperty("control", "", "role_id"),"control",site_list.get(i).getSite_id()));
				site_map.put(site_list.get(i).getSite_id(), site_list.get(i));
				
			}
		}
	}
	
	/**
	 * 初始加载站点信息
	 * 
	 * @param
	 * @return
	 */
	public static void reloadSiteList()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.control.site.SiteManager");
	}
	
	//得到网站访问端口
	public static String getSitePort()
	{
		String port = JconfigUtilContainer.bashConfig().getProperty("port", "", "site_port");
		if(port != null && !"".equals(port))
			return ":"+port;
		else
			return "";
	}
	
	/**
	 * 获取所有站点列表
	 * @param 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SiteBean> getSiteList()
	{
		List<SiteBean> site_list = new ArrayList<SiteBean>();
		Iterator iter = site_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();	
			site_list.add((SiteBean)entry.getValue());
		}
		Collections.sort(site_list,new SiteComparator());
		return site_list;
	}
	
	/**
	 * 根据服务器ID获取站点列表
	 * 
	 * @param String server_id
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SiteBean> getSiteListByServerID(String server_id)
	{
		List<SiteBean> site_list = new ArrayList<SiteBean>();
		Iterator iter = site_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			SiteBean sb = (SiteBean)entry.getValue();
			if(server_id.equals(sb.getServer_id()))
				site_list.add(sb);
		}
		return site_list;
	}
	
	/**
	 * 根据站点ID获取站点对象
	 * 
	 * @param String site_id
	 * @return List
	 */
	public static SiteBean getSiteBeanBySiteID(String site_id)
	{
		if(site_map.containsKey(site_id))
			return site_map.get(site_id);
		else
		{
			SiteBean sb = SiteDAO.getSiteBean(site_id);
			if(sb != null)
			{
				site_map.put(site_id, sb);
				return sb;
			}else
				return null;
		}
	}
	
	/**
	 * 判断site_id是否存在
	 * @param String site_id
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static boolean siteIDISExist(String site_id)
	{
		Iterator iter = site_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			SiteBean sb = (SiteBean)entry.getValue();
				if(site_id.equals(sb.getSite_id()))
					return true;
		}		
		return false;
	}
	
	/**
	 * 添加站点操作
	 * @param SiteBean
	 * @return boolean
	 */
	public static boolean insertSite(SiteBean sb,SettingLogsBean stl)
	{
		try{
			//得到tree_position的
			SiteBean siteBeanParent = getSiteBeanBySiteID(sb.getParent_id());
			sb.setSite_position(siteBeanParent.getSite_position()+"$"+sb.getSite_id());
			
			//得到添加时间
			String add_time = DateUtil.getCurrentDateTime();
			sb.setSite_createtime(add_time);			
			
			//第一步:写入库
			if(SiteDAO.insertSite(sb, stl))
			{	
//				//第二步：添加站点管理员，向站群管理角色中添加人员关联			
//				RoleUserBean rab = new RoleUserBean();
//				rab.setApp_id("control");
//				rab.setRole_id(JconfigUtilContainer.systemRole().getProperty("control", "", "role_id"));
//				rab.setSite_id(sb.getSite_id());
//				rab.setUser_id(sb.getSite_manager());
//				RoleUserManager.insertRoleUserByRole(rab,stl);
				
				//添加域名表
				SiteDomainBean siteDomainBean = new SiteDomainBean();
				siteDomainBean.setSite_id(sb.getSite_id());
				siteDomainBean.setSite_domain(sb.getSite_domain());
				siteDomainBean.setIs_default(1); 
				siteDomainBean.setIs_host(1);
				SiteDomainManager.insertSiteDomain(siteDomainBean, stl);
				
				reloadSiteList();
				return true;
			}  
			else
				return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	/**
	 * 添加站点/节点管理员，向站群管理角色中添加人员关联
	 * @param String app_id
	 * @param String site_id
	 * @param String insert_user_ids 需要添加的用户
	 * @param String delete_user_ids 需要删除的用户
	 * @param HttpServletRequest request
	 * @return boolean
	 */
	public static boolean insertSiteUserManager(String app_id,String site_id,String insert_user_ids,String delete_user_ids,SettingLogsBean stl){
		try{			
			//第二步：添加站点管理员，向站群管理角色中添加人员关联			
			RoleUserBean rab = new RoleUserBean();
			rab.setApp_id(app_id);//
			rab.setRole_id(JconfigUtilContainer.systemRole().getProperty(app_id, "", "role_id"));
			rab.setSite_id(site_id);
			rab.setUser_id(insert_user_ids);
			RoleUserManager.insertRoleUserByRole(rab,delete_user_ids,stl);
			//将管理员添加到站点用户关联表中 这里不删除用户
			SiteUserManager.linkSiteUser(insert_user_ids,"", site_id, app_id, stl);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	
	/**
     * 根据角色ID，应用ID，站点ID得到用户ID字符串
     * @param String app_id 
     * @param String site_id 
     * @return String
     * */
	public static String getUsersBySiteId(String app_id,String site_id)
	{
		try{
			String role_id = JconfigUtilContainer.systemRole().getProperty(app_id, "", "role_id");			
			return RoleUserManager.getUsersByRole(role_id, app_id, site_id);
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}

	
	
	/**
	 * 添加站点操作
	 * @param SiteBean
	 * @return boolean
	 */
	public static boolean insertSiteInit(SiteBean sb)
	{
		try{
			//第一步:写入库
			if(SiteDAO.insertSite(sb,null))
			{	
				reloadSiteList();
				return true;
			}  
			else
				return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * 根据节点ID得到该节点下排序的值
	 * @param String id
	 * @return int
	 */
	public static int getSiteSortByID(String site_id){
		try{
			List<SiteBean> list = SiteManager.getSiteChildListByID(site_id);
			if(list!=null && list.size()>0){
				return list.get(list.size()-1).getSite_sort();
			}else{
				return 0;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	/**
	 * 修改站点操作
	 * @param SiteBean
	 * @return boolean
	 */
	public static boolean updateSite(SiteBean sb,SettingLogsBean stl)
	{
		//第一步:写入库
		if(SiteDAO.updateSite(sb, stl))
		{	
			
			//修改主域名
			SiteDomainManager.updateSiteDomainByName(sb.getSite_domain(), sb.getSite_id());
			
//			第二步：添加站点管理员，向站群管理角色中添加人员关联			
//			RoleUserBean rab = new RoleUserBean();
//			rab.setApp_id("control");
//			rab.setRole_id(JconfigUtilContainer.systemRole().getProperty("control", "", "role_id"));
//			rab.setSite_id(sb.getSite_id()); 
//			rab.setUser_id(sb.getSite_manager());
//			RoleUserManager.insertRoleUserByRole(rab,stl);
			reloadSiteList();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改站点状态信息
     * @param SiteBean sb
     * @param 
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateSiteStatus(String site_id,int site_status,SettingLogsBean stl)
	{
		if(SiteDAO.updateSiteStatus(site_id, site_status, stl))
		{
			reloadSiteList();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 站点排序操作
	 * 
	 * @param String site_ids
	 * @return List
	 */
	public static boolean saveSiteSort(String site_ids,SettingLogsBean stl)
	{
		if(SiteDAO.saveSiteSort(site_ids,stl))
		{
			reloadSiteList();
			return true;
		}else
		{
			return false;
		}
	}
	
	/**
     * 删除站点(只能一个一个删除)
     * @param String site_id  
     * @param SettingLogsBean stl
     * @return boolean
     * */
     public static boolean deleteSite(String site_id,SettingLogsBean stl)
     {
    	 if(SiteDAO.deleteSite(site_id,stl))
 		{    	    		 
    		 //删除站点管理员
    		 String user_ids = getUsersBySiteId("cms",site_id);
    		 String role_id = JconfigUtilContainer.systemRole().getProperty("cms", "", "role_id");
    		 
    		 RoleUserManager.deleteRoleUserByUserRoleSite(user_ids, role_id, site_id);
    		 MingLuManager.deleteMingLuTemplate(site_id);
 			reloadSiteList();
 			return true;
 		}else
 		{
 			return false;
 		}
     }
	
	
	/**
	 * 站点排序
	 * 
	 * @param String server_id
	 * @return List
	 */
	static class SiteComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			SiteBean sb1 = (SiteBean) o1;
			SiteBean sb2 = (SiteBean) o2;
		    if (sb1.getSite_sort() > sb2.getSite_sort()) {
		     return 1;
		    } else {
		     if (sb1.getSite_sort() == sb2.getSite_sort()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}
	
	
	
	/**
	 * 根据节点ID得到该节点下的站点列表信息(得到子节点列表)
	 * @param String id
	 * @return List
	 */
	public static List<SiteBean> getSiteChildListByID(String site_id)
	{
		List<SiteBean> l = new ArrayList<SiteBean>();
		if(site_list != null && site_list.size() > 0)
		{
			for(int i=0;i<site_list.size();i++)
			{   
				if(site_id.trim().equals(site_list.get(i).getParent_id()+"")){
					l.add(site_list.get(i));
				}
			}
		}
		return l;
	}
	
	
	/**
	 * 得到站点tree的最大的节点
	 * @param String id
	 * @return List
	 */
	public static SiteBean getSiteRoot(String site_id)
	{
		return SiteManager.getSiteChildListByID(site_id).get(0);
	}
	
	
	/**
     * 得到所有站点信息,并组织为json数据
     * @param
     * @return String
     * */
	public static String getSiteTreeJsonStr()
	{		
		try{
			SiteBean siteBean = getSiteRoot("0");
			if(siteBean != null)
			{
				String json_str = "[{\"id\":\""+siteBean.getSite_id()+"\",\"text\":\""+siteBean.getSite_name()+"\",\"attributes\":{\"url\":\""
					+siteBean.getSgroup_id()+"\"}";
				String child_str = getSiteTreeJsonStrHandl(getChildSiteListByDeep(siteBean.getSite_id()));
				if(child_str != null && !"".equals(child_str))
					json_str += ",\"children\":["+child_str+"]";
				json_str += "}]";
				return json_str;
			}else
				return "[]";
		}catch (Exception e) {
			e.printStackTrace();
			return "[]";
		}
		
	}
	
	
	/**
     * 根据节点集合,组织json字符串
     * @param
     * @return String
     * */
	public static String getSiteTreeJsonStrHandl(List<SiteBean> all_site_list)
	{		
		String json_str = "";
		if(all_site_list != null && all_site_list.size() > 0)
		{
			for(int i=0;i<all_site_list.size();i++)
			{				
				json_str += "{";
				json_str += "\"id\":\""+all_site_list.get(i).getSite_id()+"\",\"text\":\""+all_site_list.get(i).getSite_name()+"\",\"attributes\":{\"url\":\""
				             +all_site_list.get(i).getSite_id()+"\"}";
				List<SiteBean> child_m_list = all_site_list.get(i).getChild_list();
				if(child_m_list != null && child_m_list.size() > 0)
					json_str += ",\"children\":["+getSiteTreeJsonStrHandl(child_m_list)+"]";
				json_str += "}";
				if(i+1 != all_site_list.size())
					json_str += ",";				
			}
		}
		return json_str;
	}
	
	/**
	 * 根据站群ID得到此节点下的子节点列表,包含其所有的子节点，有层级的deep+n,
	 * @param String site_id
	 * @return List
	 */
	public static List<SiteBean> getChildSiteListByDeep(String site_id)
	{
		List<SiteBean> g_List = new ArrayList<SiteBean>();
		Iterator<Entry<String, SiteBean>> iter = site_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, SiteBean> entry = iter.next();
			SiteBean gb = entry.getValue();			
			if (site_id.equals(gb.getParent_id()+"")) {
				gb.setChild_list(getChildSiteListByDeep(gb.getSite_id()+""));
				g_List.add(entry.getValue());
			}
		} 
		Collections.sort(g_List,new SiteComparator());
		return g_List;
	}
	
	/**
	 * 根据站点ID得到模板的存储路径
	 * @param String site_id
	 * @return String
	 */
	public static String getSiteTempletPath(String site_id)
	{
		String path = "";
		SiteBean sb = getSiteBeanBySiteID(site_id);
		if(sb != null)
		{
			path = sb.getSite_path();
			path = FormatUtil.formatPath(path + JconfigUtilContainer.velocityTemplateConfig().getProperty("path", "", "templet_path"));
		}
		return path;
	}
	
	/**
	 * 根据站点ID得到站点路径
	 * @param String site_id
	 * @return String
	 */
	public static String getSitePath(String site_id)
	{
		String path = "";
		SiteBean sb = getSiteBeanBySiteID(site_id);
		if(sb != null)
		{
			return sb.getSite_path();			
		}
		return path;
	}
	
	/**
	 * 根据域名得到站点ID
	 * @param String site_domain
	 * @return String
	 */
	public static String getSiteByDomain(String site_domain)
	{
		return SiteDomainManager.getSiteByDomain(site_domain);
	}
	
	public static void main(String[] args)
	{		
		//System.out.println(getSiteBeanBySiteID("11111ddd").getSite_id());
		String ss = "he per capita GDP will exceed US$ 4,500 by 2011, doubling that in 2005, assuring the well-off";
		System.out.println(getChildSiteListByDeep("0"));
	}    
		
}
