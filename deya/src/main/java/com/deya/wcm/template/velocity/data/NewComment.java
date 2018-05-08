package com.deya.wcm.template.velocity.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.comment.CommentBean;
import com.deya.wcm.bean.template.TurnPageBean;
import com.deya.wcm.services.comment.CommentService;

public class NewComment {
	private static int cur_page  = 1;
	private static int page_size  = 10;
	
	public static TurnPageBean getCommentCount(String param)
	{
		TurnPageBean tpb = new TurnPageBean();
		tpb.setCount(Integer.parseInt(CommentService.getCommentMainCountForBrowser(getSearchMap(param))));
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
	
	public static Map<String,String> getSearchMap(String params)
	{
		Map<String,String> m = new HashMap<String,String>();
		if(params != null && !"".equals(params))
		{
			String[] tempA = params.split(";");
			for (int i=0;i<tempA.length;i++)
			{		
				if(tempA[i].toLowerCase().startsWith("site_id="))
				{
					String site_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
					if(!"".equals(site_id)  && !site_id.startsWith("$site_id") && FormatUtil.isValiditySQL(site_id))	
						m.put("site_id", site_id);
					
				}
				if(tempA[i].toLowerCase().startsWith("size="))
				{
					String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
					if(!"".equals(ps)  && !ps.startsWith("$size") && FormatUtil.isNumeric(ps))	
						page_size = Integer.parseInt(ps);
					
				}
				if(tempA[i].toLowerCase().startsWith("cur_page="))
				{
					String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
					if(!"".equals(cp) && !cp.startsWith("$cur_page") && FormatUtil.isNumeric(cp))					
						cur_page = Integer.parseInt(cp);
				}
				if(tempA[i].toLowerCase().startsWith("id="))
				{
					String id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
					if(!"".equals(id) && !id.startsWith("$id") && FormatUtil.isNumeric(id))					
						m.put("id", id);
				}	
				if(tempA[i].toLowerCase().startsWith("type="))
				{
					String type = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
					if(!"".equals(type) && !type.startsWith("$type") && FormatUtil.isValiditySQL(type))
					{
						if("info".equals(type))
							m.put("info_type", "1");
						if("survey".equals(type))
						{
							m.put("survey", "2");
							m.put("info_type", "2");
						}
						if("appeal".equals(type))
							m.put("info_type", "3");
					}
				}
			}	
			m.put("page_size", page_size+"");	
			m.put("current_page", cur_page+"");
		}			
		return m;
	}
	
	public static Map<String,Object> getCommentMap(String param)
	{
		Map<String,String> con_m = getSearchMap(param);
		int start_page = Integer.parseInt(con_m.get("current_page"));
		int page_size = Integer.parseInt(con_m.get("page_size"));
		con_m.put("start_num", ((start_page-1)*page_size)+"");
		con_m.put("page_size", page_size+"");
		
		if("1".equals(con_m.get("info_type")))
		{
			return CommentService.getCommentMainListBrowserForInfo(con_m);
		}
		return null;
	}
	
	/**
     * 取得评论信息最多的新闻
     * @param Map<String,String> m
     * @return CommentBean 
     * */
	public static List<CommentBean> getHotCommentInfo(String param)
	{
		return CommentService.getHotCommentInfo(getSearchMap(param));
	}
	
	public static void main(String[] args)
	{
		Map m = getCommentMap("size=5;type=info;id=6951;site_id=HIWCMdemo");
		
		System.out.println(getHotCommentInfo("size=5;type=info;id=6951;site_id=HIWCMdemo").get(0).getCount());
		
		
		
		//System.out.println(l.get(0).getParent_comment().getId());
	}
}
