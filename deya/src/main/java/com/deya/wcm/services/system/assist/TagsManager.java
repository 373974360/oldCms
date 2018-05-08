package com.deya.wcm.services.system.assist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.assist.TagsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.system.assist.TagsDAO;

/**
 * 
 * @author 符江波
 * @date   2011-3-21
 */
public class TagsManager implements ISyncCatch{
	private static Map<Integer,TagsBean> tag_map = new HashMap<Integer, TagsBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		List<TagsBean> tag_list = TagsDAO.getAllTagsList();
		tag_map.clear();
		if(tag_list != null && tag_list.size() > 0)
		{
			for(int i=0;i<tag_list.size();i++){
				tag_map.put(tag_list.get(i).getTag_id(), tag_list.get(i));
			}
		}
	}
	
	public static void reloadTags()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.assist.TagsManager");
	}
	
	/**
     * 根据ID得到Tags信息
     * @param int tag_id
     * @return TagsBean
     * */
	public static TagsBean getTagsBean(int tag_id)
	{
		if(tag_map.containsKey(tag_id))
		{
			return tag_map.get(tag_id);
		}else
		{
			TagsBean tagb = TagsDAO.getTagsBean(tag_id);
			if(tagb != null)
			{
				tag_map.put(tag_id, tagb);
				return tagb;
			}else
				return null;
		}
	}
	
	/**
     * 得到Tags总数
     * @param 
     * @return String
     * */
	public static String getTagsCount(Map<String,String> con_map)
	{
		return TagsDAO.getTagsCount(con_map);
	}
	
	/**
	 * 修改Tags
	 * @param tag
	 * @return boolean
	 */
	public static boolean updateTags(TagsBean tag, SettingLogsBean stl){
		if(TagsDAO.updateTags(tag, stl)){
			tag_map.remove(tag.getTag_id());
			tag_map.put(tag.getTag_id(), tag);
			reloadTags();
			return true;
		}
		return false;
	}
	
	/**
	 * 添加Tags
	 * @param tag
	 * @return boolean
	 */
	public static boolean addTags(TagsBean tag, SettingLogsBean stl){
		if(TagsDAO.addTags(tag,stl)){
			tag_map.put(tag.getTag_id(), tag);
			reloadTags();
			return true;
		}
		return false;
	}
	
	/**
	 * 根据应用ID和站点ID得到Tags列表
	 * @param String app_id
	 * @param String site_id
	 * @return List<TagsBean>
	 */
	public static List<TagsBean> getTagListByAPPSite(String app_id,String site_id)
	{
		Set<Integer> set = tag_map.keySet();
		List<TagsBean> list = new ArrayList<TagsBean>();
		for(int i : set){
			TagsBean tb = tag_map.get(i);
			if(app_id.equals(tb.getApp_id()) && (site_id.equals(tb.getSite_id()) || site_id == null || "".equals(site_id)))
				list.add(tag_map.get(i));
		}
		return list;
	}
	
	/**
	 * 根据条件查询Tags信息
	 * @param conName
	 * @param conValue
	 * @param sortName
	 * @param sortType
	 * @param pageSize
	 * @param startNum
	 * @return List<TagsBean>
	 */
	public static List<TagsBean> getAllTagsList()
	{
		Set<Integer> set = tag_map.keySet();
		List<TagsBean> list = new ArrayList<TagsBean>();
		for(int i : set){
			list.add(tag_map.get(i));
		}
		return list;
	}
	
	/**
	 * 得到Tags列表
	 * @param con_map
	 * @return List<TagsBean>
	 */
	public static List<TagsBean> getTagsListForDB(Map<String,String> con_map)
	{
		return TagsDAO.getTagsListForDB(con_map);
	}
	
	/**
	 * 删除Tags
	 * @param tag_id
	 * @return boolean
	 */
	public static boolean delTagsById(String tag_ids, SettingLogsBean stl){
		if(TagsDAO.delTags(tag_ids, stl)){
			if(tag_ids != null){
				if(tag_ids.indexOf(",") != -1){
					for(String id : tag_ids.split(",")){
						tag_map.remove(Integer.parseInt(id));
					}
				}else{
					tag_map.remove(Integer.parseInt(tag_ids));
				}
			}
			reloadTags();
			return true;
		}
		return false;
	}
}
