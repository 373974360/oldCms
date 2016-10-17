package com.deya.wcm.services.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.page.PageBean;
import com.deya.wcm.services.Log.LogManager;

public class PageRPC {
	/**
     * 得到新的页面ID
     * @param
     * @return int
     * */
	public static int getNewPageID()
	{
		return PageManager.getNewPageID();
	}
	
	/**
     * 得到页面ID树字符串
     * @param String app_id
     * @param String site_id
     * @return String
     * */
	public static String getPageJSONTreeStr(String app_id,String site_id)
	{
		return PageManager.getPageJSONTreeStr(app_id, site_id);
	}
	
	/**
     * 根据app_id，site_id得到页面列表
     * @param String app_id
     * @param String site_id
     * @param int parent_id
     * @return int
     * */
	public static List<PageBean> getPageList(String app_id,String site_id,int parent_id)
	{
		return PageManager.getPageList(app_id, site_id, parent_id);
	}
	
	/**
     * 根据存储路径，英文名，站点ID判断该目录下是否有页面或目录存在    
     * @param String  page_path
     * @param String page_ename
     * @param String app_id     
     * @return boolean true表示已存在相同的页面
     * */
	public static boolean pageIsExist(String page_path,String page_ename,String site_id)
	{
		if(PageManager.pageFileIsExist(page_path, page_ename, site_id) == true || PageManager.pageIsExist( page_path, page_ename,site_id) == true)
			return true;
		else
			return false;
	}	
	
	/**
     * 生面静态页面　
     * @param int id
     * @return boolean
	 * @throws IOException 
     * */
	public static boolean createHtmlPage(int id) throws IOException
	{
		return PageManager.createHtmlPage(id);
	}
	
	/**
     * 得到页面对象
     * @param int id
     * @return int
     * */
	public static PageBean getPageBean(int id)
	{
		return PageManager.getPageBean(id);
	}
	
	public static boolean insertPage(PageBean pb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return PageManager.insertPage(pb,stl);
		}else
			return false;
	}
	
	public static boolean updatePage(PageBean pb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return PageManager.updatePage(pb,stl);
		}else
			return false;
	}
	
	public static boolean deletePage(int id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return PageManager.deletePage(id,stl);
		}else
			return false;
	}
}
