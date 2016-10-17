package com.deya.wcm.dao.system.assist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.assist.AuthorBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  作者数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 作者数据处理类,sql在 assist.xml 中</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author 符江波
 * @version 1.0
 * * 
 */
public class AuthorDAO {

		/**
	     * 得到所有作者信息
	     * @param 
	     * @return List
	     * */	
		@SuppressWarnings("unchecked")
		public static List<AuthorBean> getAllAuthorList()
		{
			return DBManager.queryFList("getAllAuthorList", "");
		}
		
		/**
	     * 根据ID得到作者信息
	     * @param int author_id
	     * @return AuthorBean
	     * */
		public static AuthorBean getAuthorBean(int author_id)
		{
			return (AuthorBean)DBManager.queryFObj("getAuthorBean", author_id);
		}
		
		/**
	     * 得到作者总数
	     * @param 
	     * @return String
	     * */
		public static String getAuthorCount(Map<String,String> con_map)
		{
			return DBManager.getString("getAuthorCount", con_map);
		}
		
		/**
	     * 根据条件查询作者信息
	     * @param Map<String,String> con_map
	     * @return List<AuthorBean>
	     * */
		@SuppressWarnings("unchecked")
		public static List<AuthorBean> getAuthorListForDB(Map<String,String> con_map)
		{
			return DBManager.queryFList("getAuthorListForDB", con_map);
		}
		
		/**
		 * 修改作者
		 * @param author
		 * @return boolean
		 */
		public static boolean updateAuthor(AuthorBean author, SettingLogsBean stl){
			if(DBManager.update("updateAuthor", author))
			{
				PublicTableDAO.insertSettingLogs("修改","作者",author.getAuthor_id()+"",stl);
				return true;
			}else
				return false;
		}
		
		/**
		 * 添加Author
		 * @param hw
		 * @return boolean
		 */
		public static boolean addAuthor(AuthorBean author, SettingLogsBean stl){
			int authorId = PublicTableDAO.getIDByTableName(PublicTableDAO.AUTHOR_TABLE_NAME);
			author.setAuthor_id(authorId);
			if(DBManager.insert("insertAuthor", author))
			{
				PublicTableDAO.insertSettingLogs("添加","作者",authorId+"",stl);
				return true;
			}else
				return false;
		}
		
		/**
		 * 删除作者
		 * @param author_ids
		 * @param stl
		 * @return boolean
		 */
		public static boolean delAuthor(String author_ids, SettingLogsBean stl){
			Map<String,String> map = new HashMap<String,String>();
			map.put("author_ids", author_ids);
			if(DBManager.delete("deleteAuthor", map))
			{
				PublicTableDAO.insertSettingLogs("删除","作者",author_ids,stl);
				return true;
			}else
				return false;
		}
		
}
