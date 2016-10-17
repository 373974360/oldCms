package com.deya.wcm.template.velocity.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.appCom.guestbook.GuestBookBean;
import com.deya.wcm.bean.appCom.guestbook.GuestBookCategory;
import com.deya.wcm.bean.appCom.guestbook.GuestBookClass;
import com.deya.wcm.bean.appCom.guestbook.GuestBookSub;
import com.deya.wcm.bean.template.TurnPageBean;
import com.deya.wcm.services.appCom.guestbook.GuestBookCategoryManager;
import com.deya.wcm.services.appCom.guestbook.GuestBookClassManager;
import com.deya.wcm.services.appCom.guestbook.GuestBookManager;
import com.deya.wcm.services.appCom.guestbook.GuestBookSubManager;

public class GuestBookData {
	private static int cur_page  = 1;
	private static int page_size  = 10;
	
	public static Map<String, String> getSearchMap(String params)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("publish_status", "1");//发布的才出来
		m.put("sort_name", "add_time");
		m.put("sort_type", "desc");
		String current_page = "1";
		String page_sizes = JconfigUtilContainer.velocityTemplateConfig().getProperty("num", "15", "show_list_num");
		String[] tempA = params.split(";");
		for(int i=0;i<tempA.length;i++)
		{		
			if(tempA[i].toLowerCase().startsWith("gbs_id="))
			{
				String gbs_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(gbs_id) && !gbs_id.startsWith("$gbs_id") && FormatUtil.isValiditySQL(gbs_id))
				{
					m.put("gbs_id", gbs_id);
					GuestBookSub gbs = GuestBookSubManager.getGuestBookSub(Integer.parseInt(gbs_id));
					if(gbs != null)
					{
						//判断此主题是否仅回复后显示
						if(gbs.getIs_receive_show() == 1)
						{
							m.put("reply_status", "1");
						}
					}
					if(gbs.getPublish_status() == 0)
					{
						//如果该主题未发布，gbs_id设置为0，直接返回
						m.put("gbs_id", "0");
						return m;
					}
				}				
			}
			if(tempA[i].toLowerCase().startsWith("orderby="))
			{
				String ob = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(ob) && !ob.startsWith("$orderby") && FormatUtil.isValiditySQL(ob))
				{
					String[] o = ob.split(" ");
					if(o.length > 1)
					{
						m.put("sort_name", o[0]);
						m.put("sort_type", o[1]);
					}else
						m.put("sort_name", ob);
				}
			}
			if(tempA[i].toLowerCase().startsWith("reply_status="))
			{
				String reply_status = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(reply_status) && !reply_status.startsWith("$reply_status") && FormatUtil.isValiditySQL(reply_status))
				{
					m.put("reply_status", reply_status);
				}
			}
			if(tempA[i].toLowerCase().startsWith("keyword="))
			{
				String keyword = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(keyword) && !keyword.startsWith("$keyword") && FormatUtil.isValiditySQL(keyword))
				{
					m.put("keyword", keyword);
				}
			}
			if(tempA[i].toLowerCase().startsWith("start_time="))
			{
				String start_time = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(start_time) && !start_time.startsWith("$start_time") && FormatUtil.isValiditySQL(start_time))
				{
					m.put("start_day", start_time+" 00:00:00");
				}
			}
			if(tempA[i].toLowerCase().startsWith("end_time="))
			{
				String end_time = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(end_time) && !end_time.startsWith("$end_time") && FormatUtil.isValiditySQL(end_time))
				{
					m.put("end_day", end_time+" 23:59:59");
				}
			}
			if(tempA[i].toLowerCase().startsWith("size="))
			{
				String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(ps)  && !ps.startsWith("$size") && FormatUtil.isNumeric(ps))
					page_sizes = ps;
			}
			if(tempA[i].toLowerCase().startsWith("cur_page="))
			{
				String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(cp) && !cp.startsWith("$cur_page") && FormatUtil.isNumeric(cp))
					current_page = cp;
			}	
		}
		m.put("page_size", page_sizes);	
		m.put("cur_page", current_page);
		cur_page = Integer.parseInt(current_page);
		return m;
	}
	
	public static TurnPageBean getGuestBookCount(String params)
	{
		TurnPageBean tpb = new TurnPageBean();
		Map<String,String> m = getSearchMap(params);
		
		tpb.setCount(Integer.parseInt(GuestBookManager.getGuestbookCount(m)));
		tpb.setCur_page(cur_page);
		tpb.setPage_size(page_size);
		tpb.setPage_count(tpb.getCount()/tpb.getPage_size()+1);
		
		if(tpb.getCount()%tpb.getPage_size() == 0 && tpb.getPage_count() > 1)
			tpb.setPage_count(tpb.getPage_count()-1);
		
		if(cur_page > 1)
			tpb.setPrev_num(cur_page-1);
		
		tpb.setNext_num(tpb.getPage_count());
		if(cur_page < tpb.getPage_count())
			tpb.setNext_num(cur_page+1);
		return tpb;
	}
	
	public static List<GuestBookBean> getGuestBookList(String params)
	{
		Map<String,String> m = getSearchMap(params);
		String current_page = m.get("cur_page");
		String page_sizes = m.get("page_size");
		int start_page = 1;
		if(current_page != null && !"".equals(current_page) && !"0".equals(current_page))
		{
			try{
				start_page = Integer.parseInt(current_page);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(page_sizes != null && !"".equals(page_sizes))
		{
			try{
				page_size = Integer.parseInt(page_sizes);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}	
		m.put("start_num", ((start_page-1)*page_size)+"");
		return GuestBookManager.getGuestbookList(m);
	}
	
	//得到主题对象
	public static GuestBookSub getGBSubject(String gbs_id)
	{
		if(!"".equals(gbs_id) && gbs_id != null)
			return GuestBookSubManager.getGuestBookSub(Integer.parseInt(gbs_id));
		else
			return null;
	}
	
	//得到分类对象
	public static GuestBookCategory getGBCategory(String cat_id)
	{
		if(!"".equals(cat_id) && cat_id != null)
			return GuestBookCategoryManager.getGuestBookCategoryBean(Integer.parseInt(cat_id));
		else
			return null;
	}
	
	//得到分类列表
	public static List<GuestBookCategory> getGuestBookCategoryList(String site_id)
	{
		List<GuestBookCategory> l = new ArrayList<GuestBookCategory>();
		List<GuestBookCategory> all_list = GuestBookCategoryManager.getGuestBookCategoryList(site_id);
		if(all_list != null && all_list.size() > 0)
		{
			for(GuestBookCategory cat : all_list)
			{
				if(cat.getPublish_status() == 1)
					l.add(cat);
			}
		}
		return l;
	}
	
	public static Map<String, String> getSearchSubjectMap(String params)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("publish_status", "1");//发布的才出来
		m.put("sort_name", "id");
		m.put("sort_type", "desc");
		String current_page = "1";
		String page_sizes = JconfigUtilContainer.velocityTemplateConfig().getProperty("num", "15", "show_list_num");
		String[] tempA = params.split(";");
		for(int i=0;i<tempA.length;i++)
		{		
			if(tempA[i].toLowerCase().startsWith("cat_id="))
			{
				String cat_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(cat_id) && !cat_id.startsWith("$cat_id") && FormatUtil.isValiditySQL(cat_id))
				{
					m.put("cat_id", cat_id);					
				}				
			}
			if(tempA[i].toLowerCase().startsWith("site_id="))
			{
				String site_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(site_id) && !site_id.startsWith("$site_id") && FormatUtil.isValiditySQL(site_id))
				{
					m.put("site_id", site_id);					
				}				
			}
			if(tempA[i].toLowerCase().startsWith("orderby="))
			{
				String ob = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(ob) && !ob.startsWith("$orderby") && FormatUtil.isValiditySQL(ob))
				{
					String[] o = ob.split(" ");
					if(o.length > 1)
					{
						m.put("sort_name", o[0]);
						m.put("sort_type", o[1]);
					}else
						m.put("sort_name", ob);
				}
			}
			if(tempA[i].toLowerCase().startsWith("size="))
			{
				String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(ps)  && !ps.startsWith("$size") && FormatUtil.isNumeric(ps))
					page_sizes = ps;
			}
			if(tempA[i].toLowerCase().startsWith("cur_page="))
			{
				String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(cp) && !cp.startsWith("$cur_page") && FormatUtil.isNumeric(cp))
					current_page = cp;
			}	
		}
		m.put("page_size", page_sizes);	
		m.put("cur_page", current_page);
		cur_page = Integer.parseInt(current_page);
		return m;
	}
	
	public static TurnPageBean getGBSubjectCount(String params)
	{
		TurnPageBean tpb = new TurnPageBean();
		Map<String,String> m = getSearchSubjectMap(params);
		
		tpb.setCount(Integer.parseInt(GuestBookSubManager.getGuestBookSubCount(m)));
		tpb.setCur_page(cur_page);
		tpb.setPage_size(page_size);
		tpb.setPage_count(tpb.getCount()/tpb.getPage_size()+1);
		
		if(tpb.getCount()%tpb.getPage_size() == 0 && tpb.getPage_count() > 1)
			tpb.setPage_count(tpb.getPage_count()-1);
		
		if(cur_page > 1)
			tpb.setPrev_num(cur_page-1);
		
		tpb.setNext_num(tpb.getPage_count());
		if(cur_page < tpb.getPage_count())
			tpb.setNext_num(cur_page+1);
		return tpb;
	}
	
	public static List<GuestBookSub> getGBSubjectList(String params)
	{
		Map<String,String> m = getSearchSubjectMap(params);
		String current_page = m.get("cur_page");
		String page_sizes = m.get("page_size");
		int start_page = 1;
		if(current_page != null && !"".equals(current_page) && !"0".equals(current_page))
		{
			try{
				start_page = Integer.parseInt(current_page);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(page_sizes != null && !"".equals(page_sizes))
		{
			try{
				page_size = Integer.parseInt(page_sizes);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}	
		m.put("start_num", ((start_page-1)*page_size)+"");
		
		return GuestBookSubManager.getGuestBookSubListForDB(m);
	}
	
	//得到类别列表
	public static List<GuestBookClass> getGuestBookClassList(String site_id,String cat_id)
	{
		if(cat_id != null && !"".equals(cat_id))
			return GuestBookClassManager.getGuestBookClassList(site_id, Integer.parseInt(cat_id));
		else
			return null;
	}
	
	//得到留言对象
	public static GuestBookBean getGuestBookObject(String id)
	{
		if(!"".equals(id) && id != null)
			return GuestBookManager.getGuestBookBean(id);
		else
			return null;
	}
	
	public static void main(String args[])
	{
		System.out.println(getGBSubjectCount("site_id=CMStest;cat_id=2;size=10;cur_page=1"));
	}
}
