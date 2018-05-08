package com.deya.wcm.services.appCom.guestbook;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.appCom.guestbook.GuestBookBean;
import com.deya.wcm.bean.appCom.guestbook.GuestBookCategory;
import com.deya.wcm.bean.appCom.guestbook.GuestBookClass;
import com.deya.wcm.bean.appCom.guestbook.GuestBookCount;
import com.deya.wcm.bean.appCom.guestbook.GuestBookSub;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

public class GuestBookRPC {
	/********************* 留言板分类管理　开始 *************************/
	public static List<GuestBookCategory> getGuestBookCategoryList(String site_id)
	{
		return GuestBookCategoryManager.getGuestBookCategoryList(site_id);
	}
	
	public static GuestBookCategory getGuestBookCategoryBean(int cat_id)
	{
		return GuestBookCategoryManager.getGuestBookCategoryBean(cat_id);
	}
	
	public static boolean insertGuestBookCategory(GuestBookCategory cat,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookCategoryManager.insertGuestBookCategory(cat, stl);
		}else
			return false;		
	}
	
	public static boolean updateGuestBookCategory(GuestBookCategory cat,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookCategoryManager.updateGuestBookCategory(cat, stl);
		}else
			return false;		
	}
	
	public static boolean publishGuestBookCategory(Map<String,String> m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookCategoryManager.publishGuestBookCategory(m, stl);
		}else
			return false;		
	}
	
	public static boolean sortGuestBookCategory(String cat_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookCategoryManager.sortGuestBookCategory(cat_ids, stl);
		}else
			return false;		
	}
	
	/**
     * 删除留言分类
     * @param Map m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteGuestBookCategory(Map<String,String> m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookCategoryManager.deleteGuestBookCategory(m, stl);
		}else
			return false;	
	}
	/********************* 留言板分类管理　结束 *************************/
	
	/********************* 留言板分类管理用户　开始 *************************/
	/**
     * 根据分类ID和站点ID得到管理人员ID
     * @param int cat_id
     * @param String site_id
     * @return String
     * */
	public static String getUserIDForGBCat(int cat_id,String site_id)
	{
		return GBookReleUserManager.getUserIDForGBCat(cat_id, site_id);
	}
	
	public static boolean insertGuestBookReleUser(int cat_id,String user_ids,String site_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GBookReleUserManager.insertGuestBookReleUser(cat_id,user_ids,site_id,stl);
		}else
			return false;	
	}
	/********************* 留言板分类管理用户　结束 *************************/
	
	/********************* 留言板类别管理　开始 *************************/
	public static List<GuestBookClass> getGuestBookClassList(String site_id,int cat_id)
	{
		return GuestBookClassManager.getGuestBookClassList(site_id,cat_id);
	}
	
	public static GuestBookClass getGuestBookClassBean(int cat_id)
	{
		return GuestBookClassManager.getGuestBookClassBean(cat_id);
	}
	
	public static boolean insertGuestBookClass(GuestBookClass cat,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookClassManager.insertGuestBookClass(cat, stl);
		}else
			return false;		
	}
	
	public static boolean updateGuestBookClass(GuestBookClass cat,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookClassManager.updateGuestBookClass(cat, stl);
		}else
			return false;		
	}
	
	public static boolean publishGuestBookClass(Map<String,String> m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookClassManager.publishGuestBookClass(m, stl);
		}else
			return false;		
	}
	
	public static boolean sortGuestBookClass(String cat_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookClassManager.sortGuestBookClass(cat_ids, stl);
		}else
			return false;		
	}
	
	/**
     * 删除留言类别
     * @param Map m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteGuestBookClass(Map<String,String> m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookClassManager.deleteGuestBookClass(m, stl);
		}else
			return false;	
	}
	/********************* 留言板类别管理　结束 *************************/
	
	
	public static List<GuestBookSub> getGuestBookSubListForDB(Map<String,String> m)
	{
		return GuestBookSubManager.getGuestBookSubListForDB(m);
	}
	
	/**
     * 得到留言板主题总数 
     * @param 
     * @return List
     * */
	public static String getGuestBookSubCount(Map<String,String> m)
	{
		return GuestBookSubManager.getGuestBookSubCount(m);
	}
	
	/**
     * 得到留言板主题对象 
     * @param 
     * @return List
     * */
	public static GuestBookSub getGuestBookSub(int sub_id)
	{
		return GuestBookSubManager.getGuestBookSub(sub_id);
	}
	
	/**
     * 添加留言板主题
     * @param GuestBookSub gbs
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertGuestBookSub(GuestBookSub gbs,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookSubManager.insertGuestBookSub(gbs, stl);
		}
		else
			return false;
	}
		
	/**
     * 修改留言板主题
     * @param GuestBookSub gbs
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateGuestBookSub(GuestBookSub gbs,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookSubManager.updateGuestBookSub(gbs, stl);
		}
		else
			return false;
	}
	
	/**
     * 修改留言板发布状态
     * @param String sub_ids
     * @param String publish_status
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean publishGuestBookSub(String sub_ids,String publish_status,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookSubManager.publishGuestBookSub(sub_ids,publish_status, stl);
		}
		else
			return false;
	}
	
	/**
     * 删除留言板主题
     * @param String sub_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteGuestBookSub(String sub_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookSubManager.deleteGuestBookSub(sub_ids, stl);
		}
		else
			return false;
	}
	
	/**
     * 根据条件得到留言内容总数
     * @param Map m
     * @return String
     * */
	public static String getGuestbookCount(Map<String,String> m)
	{
		return GuestBookManager.getGuestbookCount(m);
	}
	
	/**
     * 根据条件得到留言内容列表
     * @param Map m
     * @return list
     * */		
	public static List<GuestBookBean> getGuestbookList(Map<String,String> m)
	{
		return GuestBookManager.getGuestbookList(m);
	}
	
	/**
     * 得到留言板对象  
     * @param String id
     * @return GuestBookBean
     * */
	public static GuestBookBean getGuestBookBean(String id)
	{
		return GuestBookManager.getGuestBookBean(id);
	}
	
	/**
     * 修改留言内容
     * @param GuestBookBean gb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateGuestBook(GuestBookBean gb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookManager.updateGuestBook(gb, stl);
		}
		else
			return false;
	}
	
	/**
     * 回复留言内容
     * @param GuestBookBean gb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean replayGuestBook(GuestBookBean gb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookManager.replayGuestBook(gb, stl);
		}
		else
			return false;
	}
	
	/**
     * 修改留言板发布状态
     * @param String ids
     * @param String publish_status
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean publishGuestBook(String ids,String publish_status,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookManager.publishGuestBook(ids,publish_status, stl);
		}
		else
			return false;
	}
	
	public static boolean clickGuestBook(String id)
	{
		return GuestBookManager.clickGuestBook(id);
	}
	
	/**
     * 删除留言内容
     * @param String ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteGuestBook(String ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return GuestBookManager.deleteGuestBook(ids, stl);
		}
		else
			return false;
	}
	
	/**
     * 得到统计列表
     * @param Map<String,String> m
     * @return List
     * */	
	public static List<GuestBookCount> getGuestbookStatistics(Map<String,String> m)
	{
		return GuestBookManager.getGuestbookStatistics(m);
	}
	
	/**
     * 得到分类统计列表
     * @param Map<String,String> m
     * @return List
     * */	
	public static List<GuestBookCount> getGBCategoryStatistics(Map<String,String> m)
	{
		return GuestBookManager.getGBCategoryStatistics(m);
	}
}
