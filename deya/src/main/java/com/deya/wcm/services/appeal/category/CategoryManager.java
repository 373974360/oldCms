package com.deya.wcm.services.appeal.category;

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
import com.deya.wcm.bean.appeal.category.*;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appeal.category.CategoryDao;


public class CategoryManager implements ISyncCatch{
    private static TreeMap<String,CategoryBean> category_map = new TreeMap<String,CategoryBean>();
	public static int ROOT_CAT_ID = 1;
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	public static void reloadCatchHandl()
	{
		List<CategoryBean> appCate_List = CategoryDao.getAllApp_categroyList();		
		category_map.clear();
		if (appCate_List != null && appCate_List.size() > 0) {
			for (int i = 0; i < appCate_List.size(); i++) {				
				category_map.put(appCate_List.get(i).getCat_id()+"", appCate_List.get(i));
			}
		}
	}
	
	/**
	 * 初始加载菜单信息
	 * 
	 * @param
	 * @return
	 */
	
	public static void reloadAppCate()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appeal.category.CategoryManager");
	}	
	
	/**
     * 得到菜单ID,用于添加页面
     * @param
     * @return String
     * */
	public static int getAppealCategoryID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_CATEGORY_TABLE_NAME);
	}
	
	/**
     * 得到所有菜单,并组织为json数据
     * @param
     * @return String
     * */
	public static String getAppCateTreeJsonStr()
	{		
		CategoryBean mb = getApp_categoryBean(ROOT_CAT_ID);
		if(mb != null)
		{
			String json_str = "[{\"id\":"+ROOT_CAT_ID+",\"text\":\""+mb.getCat_cname()+"\" ";
				   //System.out.println("json_str================"+json_str);
			String child_str = getAppCateTreeJsonStrHandl(getChildAppCateList(ROOT_CAT_ID+""));
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
	
    public static String getAppCateTreeJsonStrHandl(List<CategoryBean> all_AppCate_list)
	{		
		String json_str = "";
		if(all_AppCate_list != null && all_AppCate_list.size() > 0)
		{
			for(int i=0;i<all_AppCate_list.size();i++)
			{				
				json_str += "{";				
				json_str += "\"id\":"+all_AppCate_list.get(i).getCat_id()+",\"text\":\""+all_AppCate_list.get(i).getCat_cname()+"\",\"attributes\":{\"url\":\""+JconfigUtilContainer.managerPagePath().getProperty("AppCate_list", "", "m_org_path")+"?AppCateID="+all_AppCate_list.get(i).getCat_id()+"\"}";
				List<CategoryBean> child_m_list = getChildAppCateList(all_AppCate_list.get(i).getCat_id()+"");
				if(child_m_list != null && child_m_list.size() > 0)
					json_str += ",\"state\":'closed',\"children\":["+getAppCateTreeJsonStrHandl(child_m_list)+"]";
				json_str += "}";
				if(i+1 != all_AppCate_list.size())
					json_str += ",";				
			}
		}
		return json_str;
	}
	
	/**
     * 根据菜单ID得到对象
     * @param String AppCate_id
     * @return app_categoryBean
     * */
	public static CategoryBean getApp_categoryBean(int cat_id)
	{
		if(category_map.containsKey(cat_id+""))
		{
			return category_map.get(cat_id+"");
		}else
		{
			CategoryBean mb = CategoryDao.getapp_categoryBean(cat_id+"");
			if(mb != null)
			{
				category_map.put(cat_id+"", mb);
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
	
	public static List<CategoryBean> getapp_categoryBeanByOptID(String cat_id)
	{
		List<CategoryBean> AppCate_list = new ArrayList<CategoryBean>();
		Iterator<Entry<String, CategoryBean>> iter = category_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, CategoryBean> entry = iter.next();
			String key = entry.getKey();			
			if (cat_id.equals(category_map.get(key).getCat_id()+"")) {
				AppCate_list.add(entry.getValue());
			}
		}
		return null;
	}
		
	/**
	 * 根据菜单节点ID得到此节点下的子节点个数,用于页面管理
	 * String AppCate_id
	 * @param
	 * @return String
	 */
	public static String getChildAppCateCount(String AppCate_id)
	{
		int count = 0;
		Iterator<Entry<String, CategoryBean>> iter = category_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, CategoryBean> entry = iter.next();
			String key = entry.getKey();			
			if (AppCate_id.equals(category_map.get(key).getParent_id()+"")) {
				count += 1;
			}
		}
		return count+"";
	}
		
	/**
	 * 根据菜单节点ID得到此节点下的子节点列表deep+1,
	 * String AppCate_id
	 * @param
	 * @return List
	 */
	public static List<CategoryBean> getChildAppCateList(String cat_id)
	{
		List<CategoryBean> m_List = new ArrayList<CategoryBean>();
		Iterator<Entry<String, CategoryBean>> iter = category_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, CategoryBean> entry = iter.next();
			CategoryBean mb = entry.getValue();			
			if (cat_id.equals(mb.getParent_id()+"")) {
				m_List.add(entry.getValue());
			}
		}
		Collections.sort(m_List,new AppCateComparator());
		return m_List;
	}
	
	/**
	 * 根据菜单节点ID得到此节点下的子节点列表,包含其所有的子节点，有层级的deep+n,
	 * String AppCate_id
	 * @param
	 * @return List
	 
	public static List<CategoryBean> getChildAppCateListByDeep(int cat_id)
	{
		List<CategoryBean> m_List = new ArrayList<CategoryBean>();
		Iterator<Entry<String, CategoryBean>> iter = category_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, CategoryBean> entry = iter.next();
			CategoryBean mb = entry.getValue();			
			if (cat_id == mb.getParent_id()) {
				mb.setChild_AppCate_list(getChildAppCateListByDeep(mb.getCat_id()));
				m_List.add(entry.getValue());
			}
		}
		Collections.sort(m_List,new AppCateComparator());
		return m_List;
	}*/
	
	/**
	 * 根据菜单节点ID得到此节点下的子节点列表deep+n,
	 * @param String AppCate_id
	 * @return List
	 */
	public static List<CategoryBean> getALLChildAppCateListByID(int cat_id)
	{
		CategoryBean mb = getApp_categoryBean(cat_id);
		if(mb != null)
			return getALLChildAppCateListByID(mb);
		else
			return null;
	}
	
	/**
	 * 根据菜单节点ID得到此节点下的子节点ID deep+n(可输入多个菜单ID,一般用于删除子节点),
	 * @param String AppCate_id
	 * @return List
	 */
	public static String getALLChildAppCateIDSByID(String cat_ids)
	{
		String m_ids = "";
		if(cat_ids != null && !"".equals(cat_ids))
		{
			String[] AppCate_a = cat_ids.split(",");
			for(int i=0;i<AppCate_a.length;i++)
			{
				List<CategoryBean> m_List = getALLChildAppCateListByID(Integer.parseInt(AppCate_a[i]));
				if(m_List != null && m_List.size() > 0)
				{
					for(int j=0;j<m_List.size();j++)
					{
						m_ids += ","+m_List.get(j).getCat_id();
					}
				}
			}
		}
		return m_ids;
	}
	
	/**
	 * 根据菜单节点ID得到此节点下的子节点列表deep+n,
	 * @param CategoryBean mb
	 * @return List
	 */
	public static List<CategoryBean> getALLChildAppCateListByID(CategoryBean mb)
	{
		String AppCate_position = mb.getCat_position();
		List<CategoryBean> m_List = new ArrayList<CategoryBean>();
		Iterator<Entry<String, CategoryBean>> iter = category_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, CategoryBean> entry = iter.next();
			String key = entry.getKey();			
			if (category_map.get(key).getCat_position().startsWith(AppCate_position) && !category_map.get(key).getCat_position().equals(AppCate_position)) {
				m_List.add(entry.getValue());
			}
		}
		Collections.sort(m_List,new AppCateComparator());
		return m_List;
	}
	
	/**
     * 插入菜单信息
     * @param CategoryBean mb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertAppCate(CategoryBean mb,SettingLogsBean stl)
	{		
		mb.setCat_position(getApp_categoryBean(mb.getParent_id()).getCat_position());
		if(CategoryDao.insertApp_categroy(mb, stl))
		{
			reloadAppCate();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改菜单信息
     * @param CategoryBean mb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateAppCate(CategoryBean mb,SettingLogsBean stl)
	{
		if(CategoryDao.updateApp_categroy(mb, stl))
		{
			reloadAppCate();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 保存菜单排序
     * @param String AppCate_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean saveAppCateSort(String cat_id,SettingLogsBean stl)
	{
		if(CategoryDao.saveApp_categroySort(cat_id, stl))
		{
			reloadAppCate();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 移动菜单
     * @param String parent_id
     * @param String AppCate_ids
     * @return boolean
     * */
	public static boolean moveAppCate(String parent_id,String AppCate_ids,SettingLogsBean stl)
	{
		String parent_tree_position = getApp_categoryBean(Integer.parseInt(parent_id)).getCat_position();
		
		if(AppCate_ids != null && !"".equals(AppCate_ids))
		{
			try{
				String[] tempA = AppCate_ids.split(",");
				for(int i=0;i<tempA.length;i++)
				{					
					moveAppCateHandl(tempA[i],parent_id,parent_tree_position,stl);
				}
				reloadAppCate();
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public static void moveAppCateHandl(String cat_id,String parent_id,String AppCate_position,SettingLogsBean stl)
	{
		String position = AppCate_position+cat_id+"$";
		Map<String,String> new_m = new HashMap<String,String>();
		new_m.put("cat_id", cat_id);
		new_m.put("parent_id", parent_id);
		new_m.put("cat_position", position);
		if(CategoryDao.moveApp_categroy(new_m,stl)){
			//该节点下的子节点一并转移
			List<CategoryBean> m_list = getChildAppCateList(cat_id);
			if(m_list != null && m_list.size() > 0)
			{
				for(int i=0;i<m_list.size();i++)
				{
					moveAppCateHandl(m_list.get(i).getCat_id()+"",cat_id,position,stl);
				}
			}
		}
		
	}
	
	/**
     * 删除菜单信息
     * @param String AppCate_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteAppCate(String ca_id,SettingLogsBean stl)
	{
		ca_id = ca_id+getALLChildAppCateIDSByID(ca_id);
		if(CategoryDao.deleteApp_categroy(ca_id, stl))
		{
			reloadAppCate();
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
     * 
	public static List<CategoryBean> getAppCateListByOptID(String opt_ids,boolean is_cmszwgk,int AppCate_id)
	{
		Set<CategoryBean> m_set = getAppCateSetOptID(opt_ids);	
		
		List<CategoryBean> AppCate_List = new ArrayList<CategoryBean>();
		if(is_cmszwgk == false)
			getAppCateChildByParentForSet(AppCate_List,m_set,AppCate_id,is_cmszwgk);
		else
			getCMSAppCateChildList(AppCate_List,m_set,AppCate_id);
		return AppCate_List;
	}*/
		
	/**
     * 根据菜单集合，循环按父节点ID组成菜单列表
     * @param String opt_ids
     * @return List
     * 
	public static void getAppCateChildByParentForSet(List<CategoryBean> AppCate_List,Set<CategoryBean> AppCate_set,int parent_id,boolean is_cms)
	{			
		Iterator<CategoryBean> it = AppCate_set.iterator();
		while(it.hasNext()){
			CategoryBean mb = it.next();
			
			if(parent_id == mb.getParent_id())
			{	//在第一级的时候开始判断此菜单节点是否为站点类型应用			
				if(mb.getParent_id() > 1 || (mb.getParent_id() == 1))
				{
					//如果不是内容管理,加入子节点
					if(AppCateIDISCms(mb.getCat_id()) == false)
					{
						//得到子节点列表
						List<CategoryBean> child_list = new ArrayList<CategoryBean>();
						getAppCateChildByParentForSet(child_list,AppCate_set,mb.getCat_id(),is_cms);
						mb.setChild_AppCate_list(child_list);
					}
					AppCate_List.add(mb);
				}
			}
		}
		//排下序再扔过去
		Collections.sort(AppCate_List,new AppCateComparator());	
	}
	
	public static void getCMSAppCateChildList(List<CategoryBean> AppCate_List,Set<CategoryBean> AppCate_set,int parent_id)
	{
		Iterator<CategoryBean> it = AppCate_set.iterator();
		while(it.hasNext()){
			CategoryBean mb = it.next();
			
			if(parent_id == mb.getParent_id())
			{	//在第一级的时候开始判断此菜单节点是否为站点类型应用			
				if(mb.getParent_id() > 6 || (mb.getParent_id() == 6))
				{
						//得到子节点列表
						List<CategoryBean> child_list = new ArrayList<CategoryBean>();
						getCMSAppCateChildList(child_list,AppCate_set,mb.getAppCate_id());
						mb.setChild_AppCate_list(child_list);
						AppCate_List.add(mb);
										
				}
			}
		}
		//排下序再扔过去
		Collections.sort(AppCate_List,new AppCateComparator());	
	}*/
		
	/**
     * 根据多个权限ID得到菜单集合（用于用户登录根据权限ID得到菜单）
     * @param String opt_ids
     * @return Set
     * */
	public static Set<CategoryBean> getAppCateSetOptID(String opt_ids)
	{
		opt_ids = ","+opt_ids+",";
		//使用set来排重
		Set<CategoryBean> AppCate_set = new HashSet<CategoryBean>();
		Iterator<Entry<String, CategoryBean>> iter = category_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, CategoryBean> entry = iter.next();
			String key = entry.getKey();
			//System.out.println(opt_ids+"|"+category_map.get(key).getOpt_id()+category_map.get(key).getAppCate_name());
			if (opt_ids.contains(","+category_map.get(key).getCat_id()+",")) {					
				AppCate_set.add(category_map.get(key));
				setAppCateByAppCatePosition(AppCate_set,category_map.get(key).getCat_position());
			}
		}		
		//System.out.println("AppCate_set--"+AppCate_set);
		return AppCate_set;
	}
	
	/**
     * 根据AppCate_position,将它的所有上级节点也加入到菜单集合中
     * @param Set AppCate_set
     * @param String AppCate_position
     * @return List
     * */
	public static void setAppCateByAppCatePosition(Set<CategoryBean> AppCate_set,String AppCate_position)
	{
		String[] tempA = AppCate_position.split("\\$");
		
		//从第2位开始，如$1$2$3$　第一位$1为根节点，不用取，最后一位	$3为自身，也不用取，取中间的2就可以了	
		
		for(int i=2;i<tempA.length-1;i++)
		{
			if(tempA[i] != null && !"".equals(tempA[i]))
				AppCate_set.add(category_map.get(tempA[i]));
		}
	}
	/************************** 用于计算登录人员所具有的菜单权限 结束 *************************************/	
	
	static class AppCateComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			CategoryBean mb1 = (CategoryBean) o1;
			CategoryBean mb2 = (CategoryBean) o2;
		    if (mb1.getSort_id() > mb2.getSort_id()) {
		     return 1;
		    } else {
		     if (mb1.getSort_id() == mb2.getSort_id()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}
	
	
	public static void main(String args[])
	{		
		//insertAppCateTest();
		//updateAppCateTest();
		//deleteAppCate("104",new SettingLogsBean());
		//System.out.println("--------"+getAppCateSetOptID("104,102,103").size());
		//saveAppCateSort("103,102,101",new SettingLogsBean());
	}
	
	public static void insertAppCateTest()
	{
//		app_categoryBean mb = new app_categoryBean();
//		//mb.setAppCate_level(1);
//		mb.setAppCate_memo("AppCate_memo");
//		mb.setAppCate_name("维护站点");
//		mb.setAppCate_position("$1$5$102$");		
//		mb.setAppCate_url("AppCate_url");
//		mb.setOpt_id(5);
//		mb.setParent_id(101);
//		insertAppCate(mb,new SettingLogsBean());
	}
	
	public static void updateAppCateTest()
	{
//		app_categoryBean mb = new app_categoryBean();
//		mb.setAppCate_id(102);
//		//mb.setAppCate_level(1);
//		mb.setAppCate_memo("AppCate_memo");
//		mb.setAppCate_name("人员管理");
//		mb.setAppCate_position("$1$2$");
//		mb.setAppCate_sort(2);
//		mb.setAppCate_url("AppCate_url");
//		mb.setOpt_id(103);
//		mb.setParent_id(2);
//		updateAppCate(mb,new SettingLogsBean());
	}
}
