package com.deya.wcm.services.appCom.guestbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.appCom.guestbook.GuestBookBean;
import com.deya.wcm.bean.appCom.guestbook.GuestBookCategory;
import com.deya.wcm.bean.appCom.guestbook.GuestBookCount;
import com.deya.wcm.bean.appCom.guestbook.GuestBookSub;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appCom.guestbook.GuestBookDAO;

public class GuestBookManager {
	/**
     * 根据条件得到留言内容总数
     * @param Map m
     * @return String
     * */
	public static String getGuestbookCount(Map<String,String> m)
	{
		return GuestBookDAO.getGuestbookCount(m);
	}
	
	/**
     * 根据条件得到留言内容列表
     * @param Map m
     * @return list
     * */		
	public static List<GuestBookBean> getGuestbookList(Map<String,String> m)
	{
		return GuestBookDAO.getGuestbookList(m);
	}
	
	/**
     * 得到留言板对象  
     * @param String id
     * @return GuestBookBean
     * */
	public static GuestBookBean getGuestBookBean(String id)
	{
		return GuestBookDAO.getGuestBookBean(id);
	}
	
	/**
     * 添加留言内容
     * @param GuestBookBean gb
     * @return boolean
     * */
	public static boolean insertGuestBook(GuestBookBean gb)
	{
		gb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.GUESTBOOK__TABLE_NAME));
		gb.setAdd_time(DateUtil.getCurrentDateTime());
		return GuestBookDAO.insertGuestBook(gb);
	}
	
	/**
     * 修改留言内容
     * @param GuestBookBean gb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateGuestBook(GuestBookBean gb,SettingLogsBean stl)
	{
		if(gb.getReply_status() == 1 && "".equals(gb.getReply_time()))
			gb.setReply_time(DateUtil.getCurrentDateTime());
		return GuestBookDAO.updateGuestBook(gb, stl);
	}
	
	/**
     * 回复留言内容
     * @param GuestBookBean gb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean replayGuestBook(GuestBookBean gb,SettingLogsBean stl)
	{
		return GuestBookDAO.replayGuestBook(gb, stl);
	}
	
	/**
     * 修改留言发布状态
     * @param String ids
     * @param String publish_status
     * @return boolean
     * */
	public static boolean publishGuestBook(String ids,String publish_status,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("publish_status", publish_status);
		return GuestBookDAO.publishGuestBook(m,stl);
	}
	
	/**
     * 删除留言内容
     * @param String ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteGuestBook(String ids,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		return GuestBookDAO.deleteGuestBook(m,stl);
	}
	
	public static boolean clickGuestBook(String id)
	{
		return GuestBookDAO.clickGuestBook(id);
	}
	
	
	/**
     * 得到统计列表
     * @param Map<String,String> m
     * @return List
     * */	
	public static List<GuestBookCount> getGuestbookStatistics(Map<String,String> m)
	{
		List<GuestBookCount> count_list = new ArrayList<GuestBookCount>();
		List<GuestBookSub> sub_list = GuestBookSubManager.getGuestBookSubListForIDS(m.get("site_id"),m.get("gbs_ids"));
		if(sub_list != null && sub_list.size() > 0)
		{			
			for(GuestBookSub gbs : sub_list)
			{				
				GuestBookCount gbc = new GuestBookCount();
				gbc.setGbs_id(gbs.getGbs_id());
				gbc.setTitle(gbs.getTitle());
				Map<String,String> c_m = new HashMap<String,String>();
				c_m.put("gbs_id", gbc.getGbs_id()+"");
				if(m.containsKey("start_day"))
					c_m.put("start_day", m.get("start_day"));
				if(m.containsKey("end_day"))
					c_m.put("end_day", m.get("end_day"));
				//得到留言总数
				gbc.setCount(Integer.parseInt(getGuestbookCount(c_m)));
				
				//得到已发布总数
				c_m.put("publish_status", "1");					
				gbc.setPublish_count(Integer.parseInt(getGuestbookCount(c_m)));
				
				//得到已回复总数
				c_m.put("reply_status", "1");
				c_m.remove("publish_status");
				gbc.setReply_count(Integer.parseInt(getGuestbookCount(c_m)));
				
				count_list.add(gbc);				
			}
		}
		return count_list;
	}
	
	/**
     * 得到分类统计列表
     * @param Map<String,String> m
     * @return List
     * */	
	public static List<GuestBookCount> getGBCategoryStatistics(Map<String,String> m)
	{
		List<GuestBookCount> count_list = new ArrayList<GuestBookCount>();
		List<GuestBookCategory> cat_list = GuestBookCategoryManager.getGuestBookCategoryListForIDS(m.get("site_id"),m.get("cat_ids"));
		if(cat_list != null && cat_list.size() > 0)
		{			
			for(GuestBookCategory cat : cat_list)
			{				
				GuestBookCount gbc = new GuestBookCount();
				gbc.setCat_id(cat.getCat_id());
				gbc.setTitle(cat.getTitle());
				Map<String,String> c_m = new HashMap<String,String>();
				c_m.put("site_id", m.get("site_id"));
				c_m.put("cat_id", gbc.getCat_id()+"");
				if(m.containsKey("start_day"))
					c_m.put("start_day", m.get("start_day"));
				if(m.containsKey("end_day"))
					c_m.put("end_day", m.get("end_day"));
				//得到留言总数			
				gbc.setCount(Integer.parseInt(GuestBookDAO.getGBCategoryStatistics(c_m)));
				
				//得到已发布总数
				c_m.put("publish_status", "1");					
				gbc.setPublish_count(Integer.parseInt(GuestBookDAO.getGBCategoryStatistics(c_m)));
				
				//得到已回复总数
				c_m.put("reply_status", "1");
				c_m.remove("publish_status");
				gbc.setReply_count(Integer.parseInt(GuestBookDAO.getGBCategoryStatistics(c_m)));
				
				count_list.add(gbc);				
			}
		}
		return count_list;
	}
	
	public static void main(String args[])
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("site_id", "HIWCMdemo");
		m.put("cat_ids", "16,17");
		System.out.println(getGBCategoryStatistics(m));
	}
}
