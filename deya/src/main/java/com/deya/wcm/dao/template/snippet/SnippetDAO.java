package com.deya.wcm.dao.template.snippet;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.template.snippet.SnippetBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;



/**
 *  代码片断处理类.
 * <p>Title: CicroDate</p>
 * <p>Description:  代码片断处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 * * 
 */
public class SnippetDAO {
	/**
	 * 得到代码片断列表
	 * @param
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<SnippetBean> getAllSnippetList()
	{
		return DBManager.queryFList("getAllSnippet", "");
	}
	
	/**
	 * 根据代码片断id得到对象
	 * @param ca_id
	 * @return SnippetBean
	 */
	public static SnippetBean getSnippetBean(int sni_id)
	{
		return (SnippetBean)DBManager.queryFObj("getSnippetBean", sni_id);
 	}
	
	public static String  getSnippetCount()
	{
		return DBManager.getString("getSnippetCount","");
	}
	/**
	 * 新增代码片断
	 * @param calebean
	 * @param stl
	 * @return  boolean
	 */
	public static boolean insertSnippet(SnippetBean bean,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_Snippet", bean)){
			PublicTableDAO.insertSettingLogs("添加","代码片断",bean.getSni_id()+"",stl);
		    return true;
		}else{
			return false;
		}
	}
	/**
	 * 修改代码片断信息
	 * @param bean
	 * @param stl
	 * @return boolean 
	 */
	public static boolean updateSnippet(SnippetBean bean,SettingLogsBean stl)
	{
		if(DBManager.update("update_Snippet", bean)){
			PublicTableDAO.insertSettingLogs("修改","代码片断",bean.getSni_id()+"",stl);
		    return true;
		}else{
		    return false;	
		}
	}
	/**
	 * 删除代码片断信息
	 * @param sni_id:代码片断ID
	 * @param stl
	 * @return boolean
	 */
	public static boolean deleteSnippet(Map<String, String> mp,SettingLogsBean stl){
		 
		 
		if(DBManager.delete("delete_Snippet_ids", mp)){
			PublicTableDAO.insertSettingLogs("删除","代码片断",mp.get("sni_id"),stl);
			return true;
		}else
			return false;
	}
}
