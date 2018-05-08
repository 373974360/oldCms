package com.deya.wcm.dao.appCom.guestbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appCom.guestbook.GBookReleUser;
import com.deya.wcm.bean.appCom.guestbook.GuestBookBean;
import com.deya.wcm.bean.appCom.guestbook.GuestBookCategory;
import com.deya.wcm.bean.appCom.guestbook.GuestBookClass;
import com.deya.wcm.bean.appCom.guestbook.GuestBookCount;
import com.deya.wcm.bean.appCom.guestbook.GuestBookSub;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.LogUtil;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * @title 留言板数据处理类 
 * @author 
 * @version 1.0
 * @date   2011-6-14 下午04:29:47
 */

public class GuestBookDAO {
	/********************* 留言板分类管理　开始 *************************/
	/**
     * 得到留言分类列表 
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<GuestBookCategory> getAllGuestBookCategoryList()
	{
		return DBManager.queryFList("getAllGuestBookCategoryList", "");
	}
	
	public static boolean insertGuestBookCategory(GuestBookCategory cat,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_guestbook_category", cat))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.ADD,"留言分类",cat.getCat_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	public static boolean updateGuestBookCategory(GuestBookCategory cat,SettingLogsBean stl)
	{
		if(DBManager.update("update_guestbook_category", cat))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"留言分类",cat.getCat_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 修改留言分类发布状态
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean publishGuestBookCategory(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("publish_guestbook_category", m))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.PHPSICALLY_DEL,"留言分类",m.get("cat_ids"),stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 留言分类排序
     * @param String cat_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean sortGuestBookCategory(String cat_ids,SettingLogsBean stl)
	{
		if(cat_ids != null && !"".equals(cat_ids))
		{
			try{
				String[] tempA = cat_ids.split(",");
				Map<String,String> m = new HashMap<String,String>();
				for(int i=0;i<tempA.length;i++)
				{
					m.put("sort_id",(i+1)+"");
					m.put("cat_id",tempA[i]);
					DBManager.update("sort_guestbook_category", m);
				}
				PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"留言分类排序",m.get("cat_ids"),stl);
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return true;		
	}
	
	/**
     * 删除留言分类
     * @param Map m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteGuestBookCategory(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_guestbook_category", m))
		{
			DBManager.insert("delete_guestbook_user", m);
			PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"留言分类",m.get("cat_ids"),stl);
			return true;
		}else
			return false;
	}
	/********************* 留言板分类管理　结束 *************************/
	
	/********************* 留言板分类管理用户　开始 *************************/
	@SuppressWarnings("unchecked")
	public static List<GBookReleUser> getGuestBookReleUserList()
	{
		return DBManager.queryFList("getGuestBookReleUserList", "");
	}
	
	public static boolean insertGuestBookReleUser(int cat_id,String user_ids,String site_id,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("cat_ids", cat_id+"");
		DBManager.insert("delete_guestbook_user", m);
		if(user_ids != null && !"".equals(user_ids))
		{
			try{
				String[] tempA = user_ids.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					GBookReleUser gbuser = new GBookReleUser();
					gbuser.setId(PublicTableDAO.getIDByTableName("cs_guestbook_user"));
					gbuser.setCat_id(cat_id);
					gbuser.setSite_id(site_id);
					gbuser.setUser_id(Integer.parseInt(tempA[i]));
					DBManager.insert("insert_guestbook_user", gbuser);
				}
				PublicTableDAO.insertSettingLogs(LogUtil.ADD,"留言分类管理员",cat_id+"",stl);
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else
			return true;
	}
	/********************* 留言板分类管理用户　结束 *************************/
	
	/********************* 留言板类别管理　开始 *************************/
	/**
     * 得到留言类别列表 
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<GuestBookClass> getAllGuestBookClassList()
	{
		return DBManager.queryFList("getAllGuestBookClassList", "");
	}
	
	public static boolean insertGuestBookClass(GuestBookClass cat,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_guestbook_class", cat))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.ADD,"留言类别",cat.getCat_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	public static boolean updateGuestBookClass(GuestBookClass cat,SettingLogsBean stl)
	{
		if(DBManager.update("update_guestbook_class", cat))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"留言类别",cat.getCat_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 修改留言类别发布状态
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean publishGuestBookClass(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("publish_guestbook_class", m))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.PHPSICALLY_DEL,"留言类别",m.get("cat_ids"),stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 留言类别排序
     * @param String cat_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean sortGuestBookClass(String class_ids,SettingLogsBean stl)
	{
		if(class_ids != null && !"".equals(class_ids))
		{
			try{
				String[] tempA = class_ids.split(",");
				Map<String,String> m = new HashMap<String,String>();
				for(int i=0;i<tempA.length;i++)
				{
					m.put("sort_id",(i+1)+"");
					m.put("class_id",tempA[i]);
					DBManager.update("sort_guestbook_class", m);
				}
				PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"留言类别排序",class_ids,stl);
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return true;		
	}
	
	/**
     * 删除留言类别
     * @param Map m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteGuestBookClass(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_guestbook_class", m))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"留言类别",m.get("cat_ids"),stl);
			return true;
		}else
			return false;
	}
	/********************* 留言板类别管理　结束 *************************/
	
	/********************* 留言板主题管理　开始 *************************/		
	/**
     * 得到留言板主题列表 
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<GuestBookSub> getAllGuestBookSubList()
	{
		return DBManager.queryFList("getAllGuestBookSubList", "");
	}
	
	/**
     * 得到留言板主题列表 
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<GuestBookSub> getGuestBookSubListForDB(Map<String,String> m)
	{
		return DBManager.queryFList("getGuestBookSubListForDB", m);
	}
	
	public static String getGuestBookSubCount(Map<String,String> m)
	{
		return DBManager.getString("getGuestBookSubCount", m);
	}
	
	/**
     * 添加留言板主题
     * @param GuestBookSub gbs
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertGuestBookSub(GuestBookSub gbs,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_guestbook_sub", gbs))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.ADD,"留言板主题",gbs.getGbs_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 修改留言板主题
     * @param GuestBookSub gbs
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateGuestBookSub(GuestBookSub gbs,SettingLogsBean stl)
	{
		if(DBManager.update("update_guestbook_sub", gbs))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"留言板主题",gbs.getGbs_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 修改留言板发布状态
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean publishGuestBookSub(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("publish_guestbook_sub", m))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.PHPSICALLY_DEL,"留言板主题发布状态",m.get("gbs_ids"),stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除留言板主题
     * @param Map m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteGuestBookSub(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_guestbook_sub", m))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"留言板主题",m.get("gbs_ids"),stl);
			return true;
		}else
			return false;
	}
	/********************* 留言板主题管理　结束 *************************/
	
	/********************* 留言板内容管理　开始 *************************/
	/**
     * 得到留言板总数  
     * @param Map m
     * @return List
     * */
	public static String getGuestbookCount(Map<String,String> m)
	{
		return DBManager.getString("getGuestbookCount", m);
	}
	
	/**
     * 得到留言板列表  
     * @param Map m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<GuestBookBean> getGuestbookList(Map<String,String> m)
	{
		return DBManager.queryFList("getGuestbookList", m);
	}
	
	/**
     * 得到留言板对象  
     * @param String id
     * @return GuestBookBean
     * */
	public static GuestBookBean getGuestBookBean(String id)
	{
		return (GuestBookBean)DBManager.queryFObj("getGuestBookBean", id);
	}
	
	/**
     * 添加留言内容
     * @param GuestBookBean gb
     * @return boolean
     * */
	public static boolean insertGuestBook(GuestBookBean gb)
	{
		return DBManager.insert("insert_guestbook", gb);
	}
	
	/**
     * 修改留言内容
     * @param GuestBookBean gb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateGuestBook(GuestBookBean gb,SettingLogsBean stl)
	{
		if(DBManager.update("update_guestbook", gb))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.UPDATE,"留言板内容",gb.getId()+"",stl);
			return true;
		}else
			return false;		
	}
	
	/**
     * 修改留言发布状态
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean publishGuestBook(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("publish_guestbook", m))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.PHPSICALLY_DEL,"留言板发布状态",m.get("ids"),stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 回复留言内容
     * @param GuestBookBean gb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean replayGuestBook(GuestBookBean gb,SettingLogsBean stl)
	{
		if(DBManager.update("replay_guestbook", gb))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.DEAL,"留言板内容",gb.getId()+"",stl);
			return true;
		}else
			return false;		
	}
	
	public static boolean clickGuestBook(String id)
	{
		return DBManager.update("click_guestbook", id);
	}
	
	/**
     * 删除留言内容
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteGuestBook(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("delete_guestbook", m))
		{
			PublicTableDAO.insertSettingLogs(LogUtil.DEAL,"留言板内容",m.get("id"),stl);
			return true;
		}else
			return false;		
	}
	
	/********************* 留言板内容管理　结束 *************************/
	
	/********************* 留言板统计　开始 *************************/
	/**
     * 得到统计列表
     * @param Map<String,String> m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<GuestBookCount> getGuestbookStatistics(Map<String,String> m)
	{
		return DBManager.queryFList("getGuestbookStatistics", m);
	}
	
	/**
     * 得到分类统计总数
     * @param Map<String,String> m
     * @return List
     * */	
	public static String getGBCategoryStatistics(Map<String,String> m)
	{
		return DBManager.getString("getGuestbookCount_catgory", m);
	}
	/********************* 留言板统计　结束 *************************/
}
