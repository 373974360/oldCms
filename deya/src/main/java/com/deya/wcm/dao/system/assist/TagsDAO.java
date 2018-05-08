package com.deya.wcm.dao.system.assist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.assist.TagsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  Tags数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: Tags数据处理类,sql在 assist.xml 中</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author 符江波
 * @version 1.0
 * * 
 */
public class TagsDAO {

		/**
	     * 得到所有Tags信息
	     * @param 
	     * @return List
	     * */	
		@SuppressWarnings("unchecked")
		public static List<TagsBean> getAllTagsList()
		{
			return DBManager.queryFList("getAllTagsList", "");
		}
		
		/**
	     * 根据ID得到Tags信息
	     * @param int tag_id
	     * @return TagsBean
	     * */
		public static TagsBean getTagsBean(int tag_id)
		{
			return (TagsBean)DBManager.queryFObj("getTagsBean", tag_id);
		}
		
		/**
	     * 得到Tags总数
	     * @param 
	     * @return String
	     * */
		public static String getTagsCount(Map<String,String> con_map)
		{
			return DBManager.getString("getTagsCount", con_map);
		}
		
		/**
	     * 根据条件查询Tags信息
	     * @param Map<String,String> con_map
	     * @return List<TagsBean>
	     * */
		@SuppressWarnings("unchecked")
		public static List<TagsBean> getTagsListForDB(Map<String,String> con_map)
		{
			return DBManager.queryFList("getTagsListForDB", con_map);
		}
		
		/**
		 * 修改Tags
		 * @param tag
		 * @return boolean
		 */
		public static boolean updateTags(TagsBean tag, SettingLogsBean stl){
			if(DBManager.update("updateTags", tag))
			{
				PublicTableDAO.insertSettingLogs("修改","Tag",tag.getTag_id()+"",stl);
				return true;
			}else
				return false;
		}
		
		/**
		 * 添加Tags
		 * @param hw
		 * @return boolean
		 */
		public static boolean addTags(TagsBean tag, SettingLogsBean stl){
			int tagId = PublicTableDAO.getIDByTableName(PublicTableDAO.TAGS_TABLE_NAME);
			tag.setTag_id(tagId);
			if(DBManager.insert("insertTags", tag))
			{
				PublicTableDAO.insertSettingLogs("添加","Tag",tagId+"",stl);
				return true;
			}else
				return false;
		}
		
		/**
		 * 删除Tags
		 * @param tag_ids
		 * @param stl
		 * @return boolean
		 */
		public static boolean delTags(String tag_ids, SettingLogsBean stl){
			Map<String,String> map = new HashMap<String,String>();
			map.put("tag_ids", tag_ids);
			if(DBManager.delete("deleteTags", map))
			{
				PublicTableDAO.insertSettingLogs("删除","Tag",tag_ids,stl);
				return true;
			}else
				return false;
		}
		
}
