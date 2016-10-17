package com.deya.wcm.services.template.snippet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.template.snippet.SnippetBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.template.snippet.SnippetDAO;



/**
 *  代码片断逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description:  代码片断逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 * * 
 */
public class SnippetManager implements ISyncCatch{
	
    private static TreeMap<Integer,SnippetBean> sni_map = new TreeMap<Integer,SnippetBean>();
    
    static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		sni_map.clear();
		List<SnippetBean> lt = SnippetDAO.getAllSnippetList();
		if (lt != null && lt.size() > 0) 
		{
			for (int i = 0; i < lt.size(); i++) 
			{				
				sni_map.put(lt.get(i).getSni_id(), lt.get(i));  
			}  
		}
	}
	
	/**
	 * 初始化
	 * @param
	 * @return
	 */
	public static void reloadSnippet() 
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.template.snippet.SnippetManager");
	}
	/**
	 * 得到代码片断列表
	 * @return	list代码片断列表
	 */
	public static List<SnippetBean> getAllSnippetList()
	{
		List<SnippetBean> lt = new ArrayList<SnippetBean>();
		Iterator<SnippetBean> it = sni_map.values().iterator();
			while(it.hasNext())
			{
				lt.add(it.next());
			}
			return lt;
	}
	/**
     * 得到代码片断总数
     * @param
     * @return String
     * */
	public static String getSnippetCount()
	{
		return SnippetDAO.getSnippetCount();
	}
	/**
	 * 根据代码片断id得到对象
	 * @param ca_id
	 * @return SnippetBean <代码片断Bean>
	 */
	public static SnippetBean getSnippetBean(int ca_id)
	{
		if(sni_map.containsKey(ca_id))
		{
			return sni_map.get(ca_id);
		}else
		{
			SnippetBean ob = SnippetDAO.getSnippetBean(ca_id);  
			sni_map.put(ca_id, ob);
			return ob; 
		}
	}
	/**
     * 得到ID,用于添加记录
     * @param
     * @return String
     * */
	public static int getInsertID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_SNIPPET_TABLE_NAME);
	}
	/**
	 * 添加代码片断
	 * @return	true 成功| false 失败
	 */
	public static boolean insertSnippet(SnippetBean bean, SettingLogsBean stl)
	{
		if(SnippetDAO.insertSnippet(bean, stl))
		{ 
			reloadSnippet();
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * 修改代码片断信息
	 * @param bean	代码片断对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateSnippet(SnippetBean bean, SettingLogsBean stl)
	{
		if(SnippetDAO.updateSnippet(bean, stl))
		{
			reloadSnippet();
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * 删除代码片断信息
	 * @param mp	key=mcat_ids,values=代码片断IDS
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteSnippet(Map<String, String> mp, SettingLogsBean stl)
	{
		if(SnippetDAO.deleteSnippet(mp, stl))
		{
			reloadSnippet();
			return true;
		}
		else
		{
			return false;
		}
	}
    
    
    
    
}
