package com.deya.wcm.services.system.assist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.assist.SourceBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.system.assist.SourceDAO;

/**
 * 
 * @author 符江波
 * @date   2011-3-21
 */
public class SourceManager implements ISyncCatch{
	private static Map<Integer,SourceBean> source_map = new HashMap<Integer, SourceBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		List<SourceBean> source_list = SourceDAO.getAllSourceList();
		source_map.clear();
		if(source_list != null && source_list.size() > 0)
		{
			for(int i=0;i<source_list.size();i++){
				source_map.put(source_list.get(i).getSource_id(), source_list.get(i));
			}
		}
	}
	
	public static void reloadSource()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.assist.SourceManager");
	}
	
	/**
     * 根据ID得到 来源信息
     * @param int source_id
     * @return SourceBean
     * */
	public static SourceBean getSourceBean(int source_id)
	{
		if(source_map.containsKey(source_id))
		{
			return source_map.get(source_id);
		}else
		{
			SourceBean sourceb = SourceDAO.getSourceBean(source_id);
			if(sourceb != null)
			{
				source_map.put(source_id, sourceb);
				return sourceb;
			}else
				return null;
		}
	}
	
	/**
     * 得到 来源总数
     * @param 
     * @return String
     * */
	public static String getSourceCount(Map<String,String> con_map)
	{
		return SourceDAO.getSourceCount(con_map);
	}
	
	/**
	 * 修改 来源
	 * @param source
	 * @return boolean
	 */
	public static boolean updateSource(SourceBean source, SettingLogsBean stl){
		if(SourceDAO.updateSource(source, stl)){
			source_map.remove(source.getSource_id());
			source_map.put(source.getSource_id(), source);
			reloadSource();
			return true;
		}
		return false;
	}
	
	/**
	 * 添加 来源
	 * @param source
	 * @return boolean
	 */
	public static boolean addSource(SourceBean source, SettingLogsBean stl){
		if(SourceDAO.addSource(source,stl)){
			source_map.put(source.getSource_id(), source);
			reloadSource();
			return true;
		}
		return false;
	}
	
	/**
	 * 根据条件查询 来源信息
	 * @return List<SourceBean>
	 */
	public static List<SourceBean> getAllSourceList()
	{
		Set<Integer> set = source_map.keySet();
		List<SourceBean> list = new ArrayList<SourceBean>();
		for(int i : set){
			list.add(source_map.get(i));
		}
		return list;
	}
	
	/**
	 * 得到 来源列表
	 * @param con_map
	 * @return List<SourceBean>
	 */
	public static List<SourceBean> getSourceListForDB(Map<String,String> con_map)
	{
		return SourceDAO.getSourceListForDB(con_map);
	}
	
	/**
	 * 删除 来源
	 * @param source_id
	 * @return boolean
	 */
	public static boolean delSourceById(String source_ids, SettingLogsBean stl){
		if(SourceDAO.delSource(source_ids, stl)){
			if(source_ids != null){
				if(source_ids.indexOf(",") != -1){
					for(String id : source_ids.split(",")){
						source_map.remove(Integer.parseInt(id));
					}
				}else{
					source_map.remove(Integer.parseInt(source_ids));
				}
			}
			reloadSource();
			return true;
		}
		return false;
	}
}
