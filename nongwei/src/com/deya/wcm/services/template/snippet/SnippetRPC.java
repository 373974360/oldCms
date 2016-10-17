package com.deya.wcm.services.template.snippet;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.app.AppBean;
import com.deya.wcm.bean.template.snippet.SnippetBean;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.org.app.AppManager;

 

/**
 *  代码片断接口类.
 * <p>Title: CicroDate</p>
 * <p>Description:  代码片断接口类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 * * 
 */
public class SnippetRPC {
	
	/**
	 * 得到代码片断List
	 * @return list
	 */
	public static List<SnippetBean> getSnippetList()
	{
		return  SnippetManager.getAllSnippetList();
	}
	
	/**
     * 得到代码片断总数
     * @param
     * @return String
     * */
	public static String getSnippetCount()
	{
		return SnippetManager.getSnippetCount();
	}
	/**
	 * 根据id得到对象
	 * @param ca_id
	 * @return SnippetBean
	 */
	public static SnippetBean getSnippetBean(int sni_id)
	{
		return SnippetManager.getSnippetBean(sni_id);
 	}
	/**
     * 得到新增ID,用于添加记录
     * @param
     * @return String
     * */
	public static int getInsertID()
	{
		return SnippetManager.getInsertID();
	}
	
	/**
	 * 插入代码片断信息
	 * @param mcb	代码片断信息
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean insertSnippet(SnippetBean bean, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SnippetManager.insertSnippet(bean, stl);
		}
		else
		{
			return false;
		}
	}
	/**
	 * 修改代码片断信息
	 * @param mcb	代码片断信息
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean updateSnippet(SnippetBean bean, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SnippetManager.updateSnippet(bean, stl);
		}
		else
		{
			return false;
		}
	}
	/**
	 * 删除代码片断信息
	 * @param mp	删除条件
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteSnippet(Map<String, String> mp, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SnippetManager.deleteSnippet(mp, stl);
		}
		else
		{
			return false;
		}
	}
	/**
	 * 得到应用系统列表
	 * 
	 * @param
	 * @return List
	 */	
	public static List<AppBean> getAppList(){
		return AppManager.getAppList();
	}
	public static void main(String[] args)
	{
		System.out.println(getAppList());
	}
}
