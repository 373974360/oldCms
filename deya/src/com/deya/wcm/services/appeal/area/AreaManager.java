package com.deya.wcm.services.appeal.area;

 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.wcm.bean.appeal.area.AreaBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appeal.area.AreaDAO;


 

/**
 *  诉求地区分类管理处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求地区分类管理处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 * * 
 */

public class AreaManager implements ISyncCatch{
	private static TreeMap<Integer,AreaBean> area_map = new TreeMap<Integer, AreaBean>();	//分类缓存
	private static int root_area_id = 1;	 
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		List<AreaBean> area_list = AreaDAO.getAllAreaList(); 
		area_map.clear();
		if (area_list != null && area_list.size() > 0) {
			for (int i = 0; i < area_list.size(); i++) {				
			  area_map.put(area_list.get(i).getArea_id(), area_list.get(i));  
			}  
		}
	}
	
	/**
	 * 初始化
	 * @param
	 * @return
	 */
	public static void reloadArea() {
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appeal.area.AreaManager");
	}
	/**
     * 得到所有地区,并组织为json数据  展现 ‘树’
     * @param
     * @return String
     * */
	public static String getAreaTreeJsonStr()
	{		
		AreaBean areabean = getAreaBean(root_area_id);
		if(areabean != null)
		{
			String json_str = "[{\"id\":"+root_area_id+",\"text\":\""+areabean.getArea_cname()+"\"";
			String child_str = getAreaTreeJsonStrHandl(getChildAreaList(root_area_id));
			if(child_str != null && !"".equals(child_str))
				json_str += ",\"children\":["+child_str+"]";
			json_str += "}]";
			return json_str;
		}else
			return "[]";
	}
	/**
     * 根据地区集合,组织json字符串
     * @param List<AreaBean> all_area_list
     * @return String
     * */
	public static String getAreaTreeJsonStrHandl(List<AreaBean> all_area_list)
	{		
		String json_str = "";
		if(all_area_list != null && all_area_list.size() > 0)
		{
			for(int i=0;i<all_area_list.size();i++)
			{				
				json_str += "{";
				json_str += "\"id\":"+all_area_list.get(i).getArea_id()+",\"text\":\""+all_area_list.get(i).getArea_cname()+"\"";
				List<AreaBean> child_o_list = getChildAreaList(all_area_list.get(i).getArea_id());
				if(child_o_list != null && child_o_list.size() > 0)
					json_str += ",\"state\":'closed',\"children\":["+getAreaTreeJsonStrHandl(child_o_list)+"]";
				json_str += "}";
				if(i+1 != all_area_list.size())
					json_str += ",";				
			}
		}
		return json_str;
	}
	/**
     * 根据ID得到此节点下的节点列表deep+1
     * @param String area_id
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<AreaBean> getChildAreaList(int area_id)
	{
		List<AreaBean> oL = new ArrayList<AreaBean>();
		Iterator iter = area_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			int key = (Integer) entry.getKey();				
			if (area_id==area_map.get(key).getParent_id()) {
				oL.add((AreaBean)entry.getValue());
			}
		}		
		 Collections.sort(oL,new AreaComparator());//排序
		return oL;
	}
	/**
     * 根据ID得到此节点下的节点个数
     * @param String opt_id
     * @return String
     * */
	@SuppressWarnings("unchecked")
	public static String getChildAreaCount(int area_id)
	{
		int count = 0;
		Iterator iter = area_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();			
			if (area_id==area_map.get(key).getParent_id()) {
				count += 1;
			}
		}		
		return count+"";
	}
	/**
     * 得到单条记录
     * @param String opt_id
     * @return AreaBean
     * */
	public static AreaBean getAreaBean(int area_id)
	{
		if(area_map.containsKey(area_id))
		{
			return area_map.get(area_id);
		}else
		{
			AreaBean ob = AreaDAO.getAreaBean(area_id);  
			if(ob != null)
			{
				area_map.put(area_id, ob);
				return ob;
			}
			return null; 
		}
	}
	/**
     * 得到地区ID,用于添加页面
     * @param
     * @return String
     * */
	public static int getAreaID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_AREA_TABLE_NAME);
	}
	/**
     * 插入地区信息
     * @param AreaBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertArea(AreaBean ob,SettingLogsBean stl)
	{ 
		ob.setArea_position(getAreaBean(ob.getParent_id()).getArea_position());
		if(AreaDAO.insertArea(ob, stl))
		{
			reloadArea();
			return true;
		}else{
			return false;
		}
	}
	/**
     * 修改地区信息
     * @param AreaBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateArea(AreaBean ob,SettingLogsBean stl)
	{
		if(AreaDAO.updateArea(ob, stl))
		{
			reloadArea();
			return true;
		}else{
			return false;
		}
	}
	/**
     * 删除地区信息
     * @param int area_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteArea(String area_id,SettingLogsBean stl)
	{
		 
		if(AreaDAO.deleteArea(area_id, stl))
		{
			reloadArea();
			return true;
		}else{
			return false;
		}
	}
	/**
     * 保存菜单排序
     * @param String area_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean saveAreaSort(String area_id,SettingLogsBean stl)
	{
		if(AreaDAO.saveAreaSort(area_id, stl))
		{
			reloadArea();
			return true;
		}
		else
			return false;
	} 
	static class AreaComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			AreaBean db1 = (AreaBean) o1;
			AreaBean db2 = (AreaBean) o2;
		    if (db1.getSort_id()> db2.getSort_id()) {
		     return 1;
		    } else {
		     if (db1.getSort_id() == db2.getSort_id()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}
}
