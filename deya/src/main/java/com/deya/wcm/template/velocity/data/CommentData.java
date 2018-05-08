package com.deya.wcm.template.velocity.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.appeal.sq.SQBean;
import com.deya.wcm.bean.system.comment.CommentBean;
import com.deya.wcm.bean.template.TurnPageBean;
import com.deya.wcm.services.appeal.sq.SQManager;
import com.deya.wcm.services.system.comment.CommentManager;

public class CommentData {
	private static int cur_page  = 1;
	private static int page_size  = 10;
	
	public static TurnPageBean getCommentCount(String params)
	{
		TurnPageBean tpb = new TurnPageBean();
		tpb.setCount(Integer.parseInt(CommentManager.getCommentListCnt(getCommentSearchCon(params))));
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
	
	public static List<CommentBean> getCommentList(String params) {
		String current_page = "1";
		String page_sizes = JconfigUtilContainer.velocityTemplateConfig().getProperty("num", "15", "show_list_num");	
		Map<String,String> m = getCommentSearchCon(params);//拼查询条件
		String[] tempA = params.split(";");
		for(int i=0;i<tempA.length;i++)
		{				
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
		m.put("search", "");
		m.put("page_size", page_sizes);
		m.put("start_num", ((Integer.parseInt(current_page)-1)*Integer.parseInt(page_sizes)+""));
		
		return CommentManager.getCommentList(m);
	}
	
	public static Map<String,String> getCommentSearchCon(String params)
	{
		Map<String,String> m = new HashMap<String,String>();
		if(params != null && !"".equals(params))
		{
			String[] tempA = params.split(";");
			for (int i=0;i<tempA.length;i++)
			{				
				if(tempA[i].toLowerCase().startsWith("size="))
				{
					String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
					if(!"".equals(ps)  && !ps.startsWith("$size") && FormatUtil.isNumeric(ps))	
						page_size = Integer.parseInt(ps);
					
				}else
				{
					if(tempA[i].toLowerCase().startsWith("cur_page="))
					{
						String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
						if(!"".equals(cp) && !cp.startsWith("$cur_page") && FormatUtil.isNumeric(cp))					
							cur_page = Integer.parseInt(cp);
					}
					else
					{
						int index_num = tempA[i].indexOf("=");
						String vals = tempA[i].substring(index_num+1);	
						if(!"".equals(FormatUtil.formatNullString(vals)))
							m.put(tempA[i].substring(0,index_num), vals);
					}
				}
			}			
		}
		m.put("is_deleted", "0");
		m.put("cmt_status", "1");		
		return m;
	}
	
	public static void main(String[] arg)
	{		
		
	}
}
