package com.deya.wcm.services.control.group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.deya.wcm.bean.control.SiteGroupBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.control.SiteGroupDAO;

/**
 *  网站群管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 网站群管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SiteGroupManager implements ISyncCatch{
	private static List<SiteGroupBean> group_list;
	private static TreeMap<String, SiteGroupBean> group_map = new TreeMap<String, SiteGroupBean>();
	
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
		group_list = SiteGroupDAO.getSiteGroupList();
		group_map.clear();
		if (group_list != null && group_list.size() > 0) {
			for (int i = 0; i < group_list.size(); i++) {				
				group_map.put(group_list.get(i).getSgroup_id()+"", group_list.get(i));
			}
		}
	}
	
	/**
	 * 初始加载站群信息
	 *   
	 * @param
	 * @return
	 */	
	public static void reloadGroupList()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.control.group.SiteGroupManager");
	}
	  
	/**
	 * 根据ID得到站群信息
	 * 
	 * @param String 
	 *            id
	 * @return SiteGroupBean
	 */
	public static SiteGroupBean getSGroupBeanByID(String sgroup_id)
	{
		SiteGroupBean sgb = new SiteGroupBean();
		if(group_list != null && group_list.size() > 0)
		{
			for(int i=0;i<group_list.size();i++)
			{
				if(sgroup_id.equals(group_list.get(i).getSgroup_id()))
					sgb = group_list.get(i);
			}
			
		}
		return sgb;
	}
	
	
	/**
     * 得到所有站群信息,并组织为json数据
     * @param
     * @return String
     * */
	public static String getGroupTreeJsonStr()
	{		
		try{
			SiteGroupBean siteGroupBean = getSGroupRoot("0");
			if(siteGroupBean != null)
			{
				String json_str = "[{\"id\":\""+siteGroupBean.getSgroup_id()+"\",\"text\":\""+siteGroupBean.getSgroup_name()+"\",\"attributes\":{\"url\":\""
					+siteGroupBean.getSgroup_id()+"\"}";
				String child_str = getGroupTreeJsonStrHandl(getChildGroupListByDeep(siteGroupBean.getSgroup_id()));
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
	public static String getGroupTreeJsonStrHandl(List<SiteGroupBean> all_group_list)
	{		
		String json_str = "";
		if(all_group_list != null && all_group_list.size() > 0)
		{
			for(int i=0;i<all_group_list.size();i++)
			{				
				json_str += "{";
				json_str += "\"id\":\""+all_group_list.get(i).getSgroup_id()+"\",\"text\":\""+all_group_list.get(i).getSgroup_name()+"\",\"attributes\":{\"url\":\""
				             +all_group_list.get(i).getSgroup_id()+"\"}";
				List<SiteGroupBean> child_m_list = all_group_list.get(i).getChild_menu_list();
				if(child_m_list != null && child_m_list.size() > 0)
					json_str += ",\"children\":["+getGroupTreeJsonStrHandl(child_m_list)+"]";
				json_str += "}";
				if(i+1 != all_group_list.size())
					json_str += ",";				
			}
		}
		return json_str;
	}
	
	/**
	 * 根据站群ID得到此节点下的子节点列表,包含其所有的子节点，有层级的deep+n,
	 * @param String group_id
	 * @return List
	 */
	public static List<SiteGroupBean> getChildGroupListByDeep(String group_id)
	{
		List<SiteGroupBean> g_List = new ArrayList<SiteGroupBean>();
		Iterator<Entry<String, SiteGroupBean>> iter = group_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, SiteGroupBean> entry = iter.next();
			SiteGroupBean gb = entry.getValue();			
			if (group_id.equals(gb.getParent_id()+"")) {
				gb.setChild_menu_list(getChildGroupListByDeep(gb.getSgroup_id()+""));
				g_List.add(entry.getValue());
			}
		} 
		Collections.sort(g_List,new GroupComparator());
		return g_List;
	}
	
	
	/**
	 * 根据节点ID得到该节点下的站群列表信息(得到子节点列表)
	 * @param String id
	 * @return List
	 */
	public static List<SiteGroupBean> getSGroupChildListByID(String sgroup_id)
	{
		List<SiteGroupBean> l = new ArrayList<SiteGroupBean>();
		if(group_list != null && group_list.size() > 0)
		{
			for(int i=0;i<group_list.size();i++)
			{
				if(sgroup_id.equals(group_list.get(i).getParent_id()))
					l.add(group_list.get(i));
			}
		}
		return l;
	}
	
	/**
	 * 根据节点ID得到该节点下的站群列表信息(得到子节点列表)
	 * 
	 * @param String 
	 *            id
	 * @return List
	 */
	public static List<SiteGroupBean> getSGroupAllChildListByID(String sgroup_id)
	{
		List<SiteGroupBean> l = new ArrayList<SiteGroupBean>();
		if(group_list != null && group_list.size() > 0)
		{
			for(int i=0;i<group_list.size();i++)
			{
				if(sgroup_id.equals(group_list.get(i).getParent_id()))
				{
					l.add(group_list.get(i));
					List<SiteGroupBean> cl = getSGroupAllChildListByID(group_list.get(i).getSgroup_id());
					if(cl != null && cl.size() > 0)
						l.addAll(cl);
				}
			}
		}
		return l;
	}
	
	
	/**
     * 插入网站群信息
     * @param SiteGroupBean sgb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertSiteGroup(SiteGroupBean sgb,SettingLogsBean stl)
	{
		if(SiteGroupDAO.insertSiteGroup(sgb, stl))
		{
			reloadGroupList();
			return true;
		}else{
			return false;
		}
			
	}
	
	/**
     * 修改网站群信息
     * @param SiteGroupBean sgb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateSiteGroup(SiteGroupBean sgb,SettingLogsBean stl)
	{
		if(SiteGroupDAO.updateSiteGroup(sgb, stl))
		{
			reloadGroupList();
			return true;
		}else
			return false;
	}
	
	/**
     * 保存网站群排序
     * @param String sgroup_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean saveSGroupSort(String sgroup_ids,SettingLogsBean stl)
	{
		if(SiteGroupDAO.saveSGroupSort(sgroup_ids, stl))
		{
			reloadGroupList();
			return true;
		}else
			return false;
	}
	
	/**
     * 删除网站群信息
     * @param String sgroup_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteSiteGroup(String sgroup_ids,SettingLogsBean stl)
	{
		try{
			String[] s_ids = sgroup_ids.trim().split(",");
			for(int i=0;i<s_ids.length;i++){
				SiteGroupDAO.deleteSiteGroup(s_ids[i], stl);
			}
			reloadGroupList();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 得到本站群的站群配置信息
	 * @param String id
	 * @return List
	 */
	public static SiteGroupBean getSGroupRoot(String sgroup_id)
	{
		return SiteGroupManager.getSGroupChildListByID(sgroup_id).get(0);
	}
	
	/**
	 * 根据节点ID得到该节点下排序的值
	 * @param String id
	 * @return int
	 */
	public static int getSGroupSortByID(String sgroup_id){
		try{
			List<SiteGroupBean> list = SiteGroupManager.getSGroupChildListByID(sgroup_id);
			if(list!=null && list.size()>0){
				return list.get(list.size()-1).getSgroup_sort();
			}else{
				return 0;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	
	
	//用于节点的排序
	static class GroupComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			SiteGroupBean mb1 = (SiteGroupBean) o1;
			SiteGroupBean mb2 = (SiteGroupBean) o2;
		    if (mb1.getSgroup_sort() > mb2.getSgroup_sort()) {
		     return 1;
		    } else {
		     if (mb1.getSgroup_sort() == mb2.getSgroup_sort()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}
	
	
	
	public static void main(String[] args)
	{
		//insertSiteGroup();
		String str = getGroupTreeJsonStr();
		System.out.println(str);
	}
	
	public static void insertSiteGroup()
	{
		SiteGroupBean sgb = new SiteGroupBean();
		sgb.setParent_id("0");
		sgb.setSgroup_id("11111");
		sgb.setSgroup_ip("192.168.12.18");
		sgb.setSgroup_memo("西安市站群");
		sgb.setSgroup_name("西安市站群");
		sgb.setSgroup_prot("8080");
		
		
		SettingLogsBean slb = new SettingLogsBean();
		slb.setIp("192.168.12.78");
		slb.setUser_id(1);
		slb.setUser_name("李苏培");
		
		insertSiteGroup(sgb,slb);
	}
}
