package com.deya.wcm.dao.system.assist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.assist.SourceBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  来源数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 来源数据处理类,sql在 assist.xml 中</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author 符江波
 * @version 1.0
 * * 
 */
public class SourceDAO {

		/**
	     * 得到所有来源信息
	     * @param 
	     * @return List
	     * */	
		@SuppressWarnings("unchecked")
		public static List<SourceBean> getAllSourceList()
		{
			return DBManager.queryFList("getAllSourceList", "");
		}
		
		/**
	     * 根据ID得到来源信息
	     * @param int source_id
	     * @return SourceBean
	     * */
		public static SourceBean getSourceBean(int source_id)
		{
			return (SourceBean)DBManager.queryFObj("getSourceBean", source_id);
		}
		
		/**
	     * 得到来源总数
	     * @param 
	     * @return String
	     * */
		public static String getSourceCount(Map<String,String> con_map)
		{
			return DBManager.getString("getSourceCount", con_map);
		}
		
		/**
	     * 根据条件查询来源信息
	     * @param Map<String,String> con_map
	     * @return List<SourceBean>
	     * */
		@SuppressWarnings("unchecked")
		public static List<SourceBean> getSourceListForDB(Map<String,String> con_map)
		{
			return DBManager.queryFList("getSourceListForDB", con_map);
		}
		
		/**
		 * 修改来源
		 * @param source
		 * @return boolean
		 */
		public static boolean updateSource(SourceBean source, SettingLogsBean stl){
			if(DBManager.update("updateSource", source))
			{
				PublicTableDAO.insertSettingLogs("修改","来源",source.getSource_id()+"",stl);
				return true;
			}else
				return false;
		}
		
		/**
		 * 添加来源
		 * @param hw
		 * @return boolean
		 */
		public static boolean addSource(SourceBean source, SettingLogsBean stl){
			int sourceId = PublicTableDAO.getIDByTableName(PublicTableDAO.SOURCE_TABLE_NAME);
			source.setSource_id(sourceId);
			if(DBManager.insert("insertSource", source))
			{
				PublicTableDAO.insertSettingLogs("添加","来源",sourceId+"",stl);
				return true;
			}else
				return false;
		}
		
		/**
		 * 删除来源
		 * @param source_ids
		 * @param stl
		 * @return boolean
		 */
		public static boolean delSource(String source_ids, SettingLogsBean stl){
			Map<String,String> map = new HashMap<String,String>();
			map.put("source_ids", source_ids);
			if(DBManager.delete("deleteSource", map))
			{
				PublicTableDAO.insertSettingLogs("删除","来源",source_ids,stl);
				return true;
			}else
				return false;
		}
		
}
