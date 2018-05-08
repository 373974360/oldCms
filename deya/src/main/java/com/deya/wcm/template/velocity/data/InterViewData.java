package com.deya.wcm.template.velocity.data;

import java.util.List;

import com.deya.util.FormatUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.interview.SubReleInfo;
import com.deya.wcm.bean.interview.SubjectActor;
import com.deya.wcm.bean.interview.SubjectBean;
import com.deya.wcm.bean.template.TurnPageBean;
import com.deya.wcm.services.interview.SubReleInfoServices;
import com.deya.wcm.services.interview.SubjectActorServices;
import com.deya.wcm.services.interview.SubjectServices;

public class InterViewData {
	private static int cur_page  = 1;
	private static int page_size  = 10;
	
	public static TurnPageBean getInterViewCount(String params)
	{
		TurnPageBean tpb = new TurnPageBean();
		tpb.setCount(Integer.parseInt(SubjectServices.getSubjectBrowserListHandlCount(getInterViewSearchCon(params))));
		tpb.setCur_page(cur_page);
		tpb.setPage_size(page_size);
		tpb.setPage_count(Integer.parseInt((tpb.getCount()/tpb.getPage_size())+"")+1);
		
		if(tpb.getCount()%tpb.getPage_size() == 0 && tpb.getPage_count() > 1)
			tpb.setPage_count(tpb.getPage_count()-1);
		
		if(cur_page > 1)
			tpb.setPrev_num(cur_page-1);
		
		tpb.setNext_num(tpb.getPage_count());
		if(cur_page < tpb.getPage_count())
			tpb.setNext_num(cur_page+1);
		return tpb;
	}
	
	public static List<SubjectBean> getInterViewList(String params)
	{
		int current_page = 1;
		int page_sizes = Integer.parseInt(JconfigUtilContainer.velocityTemplateConfig().getProperty("num", "15", "show_list_num"));
		String order_by = "sub.id desc";
		String con = getInterViewSearchCon(params);//拼查询条件
		String[] tempA = params.split(";");
		for(int i=0;i<tempA.length;i++)
		{			
			if(tempA[i].toLowerCase().startsWith("orderby="))
			{
				String ob = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				//判断不为空，且不是替换字符,避免参数没有被替换掉，直接显示到sql
				if(!"".equals(ob) && !ob.startsWith("$orderby"))
					order_by = ob;
			}
			if(tempA[i].toLowerCase().startsWith("size="))
			{
				String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(ps)  && !ps.startsWith("$size") && FormatUtil.isNumeric(ps))
					page_sizes = Integer.parseInt(ps);
			}
			if(tempA[i].toLowerCase().startsWith("cur_page="))
			{
				String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(cp) && !cp.startsWith("$cur_page") && FormatUtil.isNumeric(cp))
					current_page = Integer.parseInt(cp);
			}			
		}
		return SubjectServices.getSubjectBrowserList(con,((current_page-1)*page_sizes),page_sizes,order_by);
	}
	
	public static String getInterViewSearchCon(String params)
	{	
		String con = "";//拼查询条件
		String[] tempA = params.split(";");
		for(int i=0;i<tempA.length;i++)
		{	
			if(tempA[i].toLowerCase().startsWith("site_id="))
			{
				con += " and sc.site_id = '"+FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1))+"'";
			}
			if(tempA[i].toLowerCase().startsWith("cat_id="))
			{
				String cid = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				
				if(!"".equals(cid) && !cid.startsWith("$cat_id") && FormatUtil.isValiditySQL(cid))
				{	
					if(cid.indexOf(",") > 0)
						cid = cid.replaceAll(",", "','");
					con += " and sc.category_id in ('"+cid+"')";
				}
			}
			if(tempA[i].toLowerCase().startsWith("status="))
			{
				String st = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				//判断是否不为空，且查询条件是正确的，不含特殊字符的				
				if(!"".equals(st) && !st.startsWith("$status") && FormatUtil.isValiditySQL(st))
				{
					if(st.indexOf(",") > -1)
						con += " and sub.status in ("+st+")";
					else
						con += " and sub.status = "+st;
				}
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
		}
		
		return con;
	}
	
	/**
	 * 得到嘉宾列表
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static List<SubjectActor> getInterViewActorList(String sub_id)
	{
		return SubjectActorServices.getActorList("",sub_id);
	}
	
	@SuppressWarnings("unchecked")
	public static List<SubReleInfo> getInterViewInfoList(String params)
	{
		String[] tempA = params.split(";");
		String sub_id = "";
		String con = " and publish_flag = 1";
		int page_sizes = Integer.parseInt(JconfigUtilContainer.velocityTemplateConfig().getProperty("num", "15", "show_list_num"));
		int current_page = 1;
		for(int i=0;i<tempA.length;i++)
		{	
			if(tempA[i].toLowerCase().startsWith("sub_id="))
			{
				String sid = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				
				if(!"".equals(sid) && !sid.startsWith("$cat_id") && FormatUtil.isValiditySQL(sid))
				{						
					sub_id = sid;
				}
			}
			if(tempA[i].toLowerCase().startsWith("size="))
			{
				String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(ps)  && !ps.startsWith("$size") && FormatUtil.isNumeric(ps))
					page_sizes = Integer.parseInt(ps);
			}
			if(tempA[i].toLowerCase().startsWith("cur_page="))
			{
				String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(cp) && !cp.startsWith("$cur_page") && FormatUtil.isNumeric(cp))
					current_page = Integer.parseInt(cp);
			}
			
		}	
		return SubReleInfoServices.getReleInfoListBySub_id(con,((current_page-1)*page_sizes),page_sizes,sub_id);
	}
	
	//得到推荐列表
	@SuppressWarnings("unchecked")
	public static List<SubjectBean> getInterViewRecommendList(String params)
	{
		int current_page = 1;
		int page_sizes = Integer.parseInt(JconfigUtilContainer.velocityTemplateConfig().getProperty("num", "15", "show_list_num"));
		String[] tempA = params.split(";");
		for(int i=0;i<tempA.length;i++)
		{						
			if(tempA[i].toLowerCase().startsWith("size="))
			{
				String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(ps)  && !ps.startsWith("$size") && FormatUtil.isNumeric(ps))
					page_sizes = Integer.parseInt(ps);
			}
			if(tempA[i].toLowerCase().startsWith("cur_page="))
			{
				String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(cp) && !cp.startsWith("$cur_page") && FormatUtil.isNumeric(cp))
					current_page = Integer.parseInt(cp);
			}			
		}
		return SubjectServices.getSubjectRecommendList(getInterViewSearchCon(params),((current_page-1)*page_sizes),page_sizes);
	}
	
	//得到信息内容
	public static SubReleInfo getInterViewInfoContent(int id)
	{
		return SubReleInfoServices.getSubReleInfo(id);
	}
	
	public static void main(String[] args)
	{
		System.out.println(getInterViewCount("status=0;size=5;cur_page=2;"));		
	}
}
