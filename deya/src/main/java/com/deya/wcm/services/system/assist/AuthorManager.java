package com.deya.wcm.services.system.assist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.assist.AuthorBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.system.assist.AuthorDAO;

/**
 * 
 * @author 符江波
 * @date   2011-3-21
 */
public class AuthorManager implements ISyncCatch{
	private static Map<Integer,AuthorBean> author_map = new HashMap<Integer, AuthorBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		List<AuthorBean> author_list = AuthorDAO.getAllAuthorList();
		author_map.clear();
		if(author_list != null && author_list.size() > 0)
		{
			for(int i=0;i<author_list.size();i++){
				author_map.put(author_list.get(i).getAuthor_id(), author_list.get(i));
			}
		}
	}
	
	public static void reloadAuthor()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.assist.AuthorManager");
	}
	
	/**
     * 根据ID得到作者信息
     * @param int author_id
     * @return AuthorBean
     * */
	public static AuthorBean getAuthorBean(int author_id)
	{
		if(author_map.containsKey(author_id))
		{
			return author_map.get(author_id);
		}else
		{
			AuthorBean authorb = AuthorDAO.getAuthorBean(author_id);
			if(authorb != null)
			{
				author_map.put(author_id, authorb);
				return authorb;
			}else
				return null;
		}
	}
	
	/**
     * 得到作者总数
     * @param 
     * @return String
     * */
	public static String getAuthorCount(Map<String,String> con_map)
	{
		return AuthorDAO.getAuthorCount(con_map);
	}
	
	/**
	 * 修改作者
	 * @param author
	 * @return boolean
	 */
	public static boolean updateAuthor(AuthorBean author, SettingLogsBean stl){
		if(AuthorDAO.updateAuthor(author, stl)){
			author_map.remove(author.getAuthor_id());
			author_map.put(author.getAuthor_id(), author);
			reloadAuthor();
			return true;
		}
		return false;
	}
	
	/**
	 * 添加作者
	 * @param author
	 * @return boolean
	 */
	public static boolean addAuthor(AuthorBean author, SettingLogsBean stl){
		if(AuthorDAO.addAuthor(author,stl)){
			author_map.put(author.getAuthor_id(), author);
			reloadAuthor();
			return true;
		}
		return false;
	}
	
	/**
	 * 根据条件查询作者信息
	 * @return List<AuthorBean>
	 */
	public static List<AuthorBean> getAllAuthorList()
	{
		Set<Integer> set = author_map.keySet();
		List<AuthorBean> list = new ArrayList<AuthorBean>();
		for(int i : set){
			list.add(author_map.get(i));
		}
		return list;
	}
	
	/**
	 * 得到作者列表
	 * @param con_map
	 * @return List<AuthorBean>
	 */
	public static List<AuthorBean> getAuthorListForDB(Map<String,String> con_map)
	{
		return AuthorDAO.getAuthorListForDB(con_map);
	}
	
	/**
	 * 删除作者
	 * @param author_id
	 * @return boolean
	 */
	public static boolean delAuthorById(String author_ids, SettingLogsBean stl){
		if(AuthorDAO.delAuthor(author_ids, stl)){
			if(author_ids != null){
				if(author_ids.indexOf(",") != -1){
					for(String id : author_ids.split(",")){
						author_map.remove(Integer.parseInt(id));
					}
				}else{
					author_map.remove(Integer.parseInt(author_ids));
				}
			}
			reloadAuthor();
			return true;
		}
		return false;
	}
}
