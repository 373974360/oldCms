package com.deya.wcm.services.org.operate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.operate.MenuBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.org.operate.MenuDAO;

public class MenuManager implements ISyncCatch{
	private static TreeMap<String,MenuBean> menu_map = new TreeMap<String,MenuBean>();
	private static String menu_list_path = JconfigUtilContainer.managerPagePath().getProperty("menu_list", "", "m_org_path");
	public static int ROOT_MENU_ID = 1;
	public static int CMS_MENU_ID = 6;
	public static int GKNODE_MENU_ID = 8;//公开节点管理
	public static int ZWGK_MENU_ID = 4;//信息公开管理
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
		List<MenuBean> menu_List = MenuDAO.getAllMenuList();
		//System.out.println("MenuManager reloadMenu+++++++++++++++++=="+menu_List);
		menu_map.clear();
		if (menu_List != null && menu_List.size() > 0) {
			for (int i = 0; i < menu_List.size(); i++) {				
				menu_map.put(menu_List.get(i).getMenu_id()+"", menu_List.get(i));
			}
		}
	}
	
	/**
	 * 初始加载菜单信息
	 * 
	 * @param
	 * @return
	 */	
	public static void reloadMenu()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.operate.MenuManager");
	}	
	
	/**
     * 得到菜单ID,用于添加页面
     * @param
     * @return String
     * */
	public static int getMenuID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.MENU_TABLE_NAME);
	}
	
	/**
     * 得到所有菜单,并组织为json数据
     * @param
     * @return String
     * */
	public static String getMenuTreeJsonStr()
	{		
		MenuBean mb = getMenuBean(ROOT_MENU_ID);
		if(mb != null)
		{
			String json_str = "[{\"id\":"+ROOT_MENU_ID+",\"text\":\""+mb.getMenu_name()+"\",\"attributes\":{\"url\":\""
				+menu_list_path+"?menuID="+ROOT_MENU_ID+"\"}";
			String child_str = getMenuTreeJsonStrHandl(getChildMenuListByDeep(ROOT_MENU_ID));
			if(child_str != null && !"".equals(child_str))
				json_str += ",\"children\":["+child_str+"]";
			json_str += "}]";
			return json_str;
		}else
			return "[]";
	}
	
	/**
     * 根据菜单集合,组织json字符串
     * @param
     * @return String
     * */
	public static String getMenuTreeJsonStrHandl(List<MenuBean> all_menu_list)
	{		
		String json_str = "";
		if(all_menu_list != null && all_menu_list.size() > 0)
		{
			for(int i=0;i<all_menu_list.size();i++)
			{				
				json_str += "{";
				json_str += "\"id\":"+all_menu_list.get(i).getMenu_id()+",\"text\":\""+all_menu_list.get(i).getMenu_name()+"\",\"attributes\":{\"url\":\""+menu_list_path+"?menuID="+all_menu_list.get(i).getMenu_id()+"\"}";
				List<MenuBean> child_m_list = all_menu_list.get(i).getChild_menu_list();
				if(child_m_list != null && child_m_list.size() > 0)
					json_str += ",\"children\":["+getMenuTreeJsonStrHandl(child_m_list)+"]";
				json_str += "}";
				if(i+1 != all_menu_list.size())
					json_str += ",";				
			}
		}
		return json_str;
	}
	
	/**
     * 根据菜单ID得到对象
     * @param String menu_id
     * @return MenuBean
     * */
	public static MenuBean getMenuBean(int menu_id)
	{
		if(menu_map.containsKey(menu_id+""))
		{
			return menu_map.get(menu_id+"");
		}else
		{
			MenuBean mb = MenuDAO.getMenuBean(menu_id);
			if(mb != null)
			{
				menu_map.put(menu_id+"", mb);
				return mb;
			}else
				return null;
		}
	}
	
	/**
     * 根据权限ID得到菜单列表（因为一个权限可以对应到多个菜单，所以这里返回是list）
     * @param String opt_id
     * @return List
     * */
	public static List<MenuBean> getMenuBeanByOptID(String opt_id)
	{
		List<MenuBean> menu_list = new ArrayList<MenuBean>();
		Iterator<Entry<String, MenuBean>> iter = menu_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, MenuBean> entry = iter.next();
			String key = entry.getKey();			
			if (opt_id.equals(menu_map.get(key).getOpt_id()+"")) {
				menu_list.add(entry.getValue());
			}
		}
		return null;
	}
		
	/**
	 * 根据菜单节点ID得到此节点下的子节点个数,用于页面管理
	 * String menu_id
	 * @param
	 * @return String
	 */
	public static String getChildMenuCount(String menu_id)
	{
		int count = 0;
		Iterator<Entry<String, MenuBean>> iter = menu_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, MenuBean> entry = iter.next();
			String key = entry.getKey();			
			if (menu_id.equals(menu_map.get(key).getParent_id()+"")) {
				count += 1;
			}
		}
		return count+"";
	}
		
	/**
	 * 根据菜单节点ID得到此节点下的子节点列表deep+1,
	 * String menu_id
	 * @param
	 * @return List
	 */
	public static List<MenuBean> getChildMenuList(String menu_id)
	{
		List<MenuBean> m_List = new ArrayList<MenuBean>();
		Iterator<Entry<String, MenuBean>> iter = menu_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, MenuBean> entry = iter.next();
			MenuBean mb = entry.getValue();			
			if (menu_id.equals(mb.getParent_id()+"")) {
				m_List.add(entry.getValue());
			}
		}
		Collections.sort(m_List,new MenuComparator());
		return m_List;
	}
	
	/**
	 * 根据菜单节点ID得到此节点下的子节点列表,包含其所有的子节点，有层级的deep+n,
	 * String menu_id
	 * @param
	 * @return List
	 */
	public static List<MenuBean> getChildMenuListByDeep(int menu_id)
	{
		List<MenuBean> m_List = new ArrayList<MenuBean>();
		Iterator<Entry<String, MenuBean>> iter = menu_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, MenuBean> entry = iter.next();
			MenuBean mb = entry.getValue();			
			if (menu_id == mb.getParent_id()) {
				mb.setChild_menu_list(getChildMenuListByDeep(mb.getMenu_id()));
				m_List.add(entry.getValue());
			}
		}
		Collections.sort(m_List,new MenuComparator());
		return m_List;
	}
	
	/**
	 * 根据菜单节点ID得到此节点下的子节点列表deep+n,
	 * @param String menu_id
	 * @return List
	 */
	public static List<MenuBean> getALLChildMenuListByID(int menu_id)
	{
		MenuBean mb = getMenuBean(menu_id);
		if(mb != null)
			return getALLChildMenuListByID(mb);
		else
			return null;
	}
	
	/**
	 * 根据菜单节点ID得到此节点下的子节点ID deep+n(可输入多个菜单ID,一般用于删除子节点),
	 * @param String menu_id
	 * @return List
	 */
	public static String getALLChildMenuIDSByID(String menu_ids)
	{
		String m_ids = "";
		if(menu_ids != null && !"".equals(menu_ids))
		{
			String[] menu_a = menu_ids.split(",");
			for(int i=0;i<menu_a.length;i++)
			{
				List<MenuBean> m_List = getALLChildMenuListByID(Integer.parseInt(menu_a[i]));
				if(m_List != null && m_List.size() > 0)
				{
					for(int j=0;j<m_List.size();j++)
					{
						m_ids += ","+m_List.get(j).getMenu_id();
					}
				}
			}
		}
		return m_ids;
	}
	
	/**
	 * 根据菜单节点ID得到此节点下的子节点列表deep+n,
	 * @param MenuBean mb
	 * @return List
	 */
	public static List<MenuBean> getALLChildMenuListByID(MenuBean mb)
	{
		String menu_position = mb.getMenu_position();
		List<MenuBean> m_List = new ArrayList<MenuBean>();
		Iterator<Entry<String, MenuBean>> iter = menu_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, MenuBean> entry = iter.next();
			String key = entry.getKey();			
			if (menu_map.get(key).getMenu_position().startsWith(menu_position) && !menu_map.get(key).getMenu_position().equals(menu_position)) {
				m_List.add(entry.getValue());
			}
		}
		Collections.sort(m_List,new MenuComparator());
		return m_List;
	}
	
	/**
     * 插入菜单信息
     * @param MenuBean mb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertMenu(MenuBean mb,SettingLogsBean stl)
	{		
		mb.setMenu_position(getMenuBean(mb.getParent_id()).getMenu_position());
		if(MenuDAO.insertMenu(mb, stl))
		{
			reloadMenu();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改菜单信息
     * @param MenuBean mb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateMenu(MenuBean mb,SettingLogsBean stl)
	{
		if(MenuDAO.updateMenu(mb, stl))
		{
			reloadMenu();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 保存菜单排序
     * @param String menu_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean saveMenuSort(String menu_id,SettingLogsBean stl)
	{
		if(MenuDAO.saveMenuSort(menu_id, stl))
		{
			reloadMenu();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 移动菜单
     * @param String parent_id
     * @param String menu_ids
     * @return boolean
     * */
	public static boolean moveMenu(String parent_id,String menu_ids,SettingLogsBean stl)
	{
		String parent_tree_position = getMenuBean(Integer.parseInt(parent_id)).getMenu_position();
		
		if(menu_ids != null && !"".equals(menu_ids))
		{
			try{
				String[] tempA = menu_ids.split(",");
				for(int i=0;i<tempA.length;i++)
				{					
					moveMenuHandl(tempA[i],parent_id,parent_tree_position,stl);
				}
				reloadMenu();
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public static void moveMenuHandl(String menu_id,String parent_id,String menu_position,SettingLogsBean stl)
	{
		String position = menu_position+menu_id+"$";
		Map<String,String> new_m = new HashMap<String,String>();
		new_m.put("menu_id", menu_id);
		new_m.put("parent_id", parent_id);
		new_m.put("menu_position", position);
		if(MenuDAO.moveMenu(new_m,stl)){
			//该节点下的子节点一并转移
			List<MenuBean> m_list = getChildMenuList(menu_id);
			if(m_list != null && m_list.size() > 0)
			{
				for(int i=0;i<m_list.size();i++)
				{
					moveMenuHandl(m_list.get(i).getMenu_id()+"",menu_id,position,stl);
				}
			}
		}
		
	}
	
	/**
     * 删除菜单信息
     * @param String menu_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteMenu(String menu_id,SettingLogsBean stl)
	{
		menu_id = menu_id+getALLChildMenuIDSByID(menu_id);
		if(MenuDAO.deleteMenu(menu_id, stl))
		{
			reloadMenu();
			return true;
		}
		else
			return false;
	}
	
	/************************** 用于计算登录人员所具有的菜单权限 开始 *************************************/
	
	/**
     * 根据多个权限ID得到菜单列表（用于用户登录进根据权限ID得到菜单）
     * @param String opt_ids
     * @param boolean is_cmszwgk 判断是内容管理所用的菜单　true表示取有站点的，false取其它类型的应用
     * 		像组织机构或综合管理只有一套，但内容管理却是分站点的，所以这块要区别对待 
     * @return List
     * */
	public static List<MenuBean> getMenuListByOptID(String opt_ids,boolean is_cmszwgk,int menu_id)
	{
		Set<MenuBean> m_set = getMenuSetOptID(opt_ids);	
		
		List<MenuBean> menu_List = new ArrayList<MenuBean>();
		if(is_cmszwgk == false)
			getMenuChildByParentForSet(menu_List,m_set,menu_id,is_cmszwgk);
		else
			getCMSMenuChildList(menu_List,m_set,menu_id);
		return menu_List;
	}
		
	/**
     * 根据菜单集合，循环按父节点ID组成菜单列表
     * @param String opt_ids
     * @return List
     * */
	public static void getMenuChildByParentForSet(List<MenuBean> menu_List,Set<MenuBean> menu_set,int parent_id,boolean is_cms)
	{			
		Iterator<MenuBean> it = menu_set.iterator();
		while(it.hasNext()){
			MenuBean mb = it.next().clone();
			
			if(parent_id == mb.getParent_id())
			{	//在第一级的时候开始判断此菜单节点是否为站点类型应用			
				if(mb.getParent_id() > 1 || (mb.getParent_id() == 1))
				{
					//如果不是内容管理,加入子节点
					if(menuIDISCms(mb.getMenu_id()) == false)
					{
						//得到子节点列表
						List<MenuBean> child_list = new ArrayList<MenuBean>();
						getMenuChildByParentForSet(child_list,menu_set,mb.getMenu_id(),is_cms);
						mb.setChild_menu_list(child_list);
					}
					menu_List.add(mb);
				}
			}
		}
		//排下序再扔过去
		Collections.sort(menu_List,new MenuComparator());	
	}
	
	public static void getCMSMenuChildList(List<MenuBean> menu_List,Set<MenuBean> menu_set,int parent_id)
	{
		Iterator<MenuBean> it = menu_set.iterator();
		while(it.hasNext()){
			MenuBean mb = it.next().clone();
			
			if(parent_id == mb.getParent_id())
			{	//在第一级的时候开始判断此菜单节点是否为站点类型应用			
				if(mb.getParent_id() > 6 || (mb.getParent_id() == 6))
				{
						//得到子节点列表
						List<MenuBean> child_list = new ArrayList<MenuBean>();
						getCMSMenuChildList(child_list,menu_set,mb.getMenu_id());
						mb.setChild_menu_list(child_list);
						menu_List.add(mb);
										
				}
			}
		}
		//排下序再扔过去
		Collections.sort(menu_List,new MenuComparator());	
	}
	
	/**
     * 判断是否是内容管理应用
     * @param int menu_id
     * @return boolean
     * */
	public static boolean menuIDISCms(int menu_id)
	{
		return (menu_id == 6);
	}
	
	/**
     * 根据多个权限ID得到菜单集合（用于用户登录根据权限ID得到菜单）
     * @param String opt_ids
     * @return Set
     * */
	public static Set<MenuBean> getMenuSetOptID(String opt_ids)
	{
		opt_ids = ","+opt_ids+",";
		//使用set来排重
		Set<MenuBean> menu_set = new HashSet<MenuBean>();
		Iterator<Entry<String, MenuBean>> iter = menu_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, MenuBean> entry = iter.next();
			String key = entry.getKey();
			//System.out.println(opt_ids+"|"+menu_map.get(key).getOpt_id()+menu_map.get(key).getMenu_name());
			if (opt_ids.contains(","+menu_map.get(key).getOpt_id()+",")) {					
				menu_set.add(menu_map.get(key));
				setMenuByMenuPosition(menu_set,menu_map.get(key).getMenu_position());
			}
		}		
		//System.out.println("menu_set--"+menu_set);
		return menu_set;
	}
	
	/**
     * 根据menu_position,将它的所有上级节点也加入到菜单集合中
     * @param Set menu_set
     * @param String menu_position
     * @return List
     * */
	public static void setMenuByMenuPosition(Set<MenuBean> menu_set,String menu_position)
	{
		String[] tempA = menu_position.split("\\$");
		
		//从第2位开始，如$1$2$3$　第一位$1为根节点，不用取，最后一位	$3为自身，也不用取，取中间的2就可以了	
		
		for(int i=2;i<tempA.length-1;i++)
		{
			if(tempA[i] != null && !"".equals(tempA[i]))
				menu_set.add(menu_map.get(tempA[i]));
		}
	}
	
	/**
     * 得到我的平台特殊菜单节点
     * @return String
     * */
	public static List<MenuBean> getMyPlatform()
	{
		List<MenuBean> l = getChildMenuList(ROOT_MENU_ID+"");
		if(l != null && l.size() > 0)
		{
			MenuBean mb = l.get(l.size()-1);
			
			mb.setChild_menu_list(getChildMenuListByDeep(mb.getMenu_id()));
			l.clear();
			l.add(mb);
			return l;
		}else
			return null;
	}
	/************************** 用于计算登录人员所具有的菜单权限 结束 *************************************/	
	
	static class MenuComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			MenuBean mb1 = (MenuBean) o1;
			MenuBean mb2 = (MenuBean) o2;
		    if (mb1.getMenu_sort() > mb2.getMenu_sort()) {
		     return 1;
		    } else {
		     if (mb1.getMenu_sort() == mb2.getMenu_sort()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}
	
	
	public static void main(String args[])
	{		
		//insertMenuTest();
		//updateMenuTest();
		//deleteMenu("104",new SettingLogsBean());
		System.out.println("--------"+getMyPlatform());
		//saveMenuSort("103,102,101",new SettingLogsBean());
		
		
		
	}	
	
}
