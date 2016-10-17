package com.deya.wcm.template.velocity.data;

import java.util.List;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.interview.SubjectBean;
import com.deya.wcm.bean.survey.SurveyBean;
import com.deya.wcm.bean.template.TurnPageBean;
import com.deya.wcm.services.interview.SubjectServices;
import com.deya.wcm.services.survey.SurveyService;

public class SurveyData {
	private static int cur_page  = 1;
	private static int page_size  = 10;
	
	public static TurnPageBean getSurveyCount(String params)
	{
		TurnPageBean tpb = new TurnPageBean();
		tpb.setCount(Integer.parseInt(SurveyService.getSurveyListCountBrowser(getSurveySearchCon(params))));
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
	
	@SuppressWarnings("unchecked")
	public static List<SurveyBean> getSurveyList(String params)
	{
		int current_page = 1;
		int page_sizes = Integer.parseInt(JconfigUtilContainer.velocityTemplateConfig().getProperty("num", "15", "show_list_num"));
		String order_by = "sub.id desc";
		String con = getSurveySearchCon(params);//拼查询条件
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
		return SurveyService.getSurveyListBrowser(con,((current_page-1)*page_sizes),page_sizes,order_by);
	}
	
	public static String getSurveySearchCon(String params)
	{
		String con = "";//拼查询条件
		String[] tempA = params.split(";");
		for(int i=0;i<tempA.length;i++)
		{	
			if(tempA[i].toLowerCase().startsWith("cat_id="))
			{
				String cid = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				
				if(!"".equals(cid) && !cid.startsWith("$cat_id") && FormatUtil.isValiditySQL(cid))
				{	
					if(cid.indexOf(",") > 0)
						cid = cid.replaceAll(",", "','");
					con += " and cs.category_id in ('"+cid+"')";
				}
			}
			if(tempA[i].toLowerCase().startsWith("site_id="))
			{				
				con += " and cs.site_id = '"+FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1))+"'";
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
			if(tempA[i].toLowerCase().startsWith("is_end="))
			{
				String is_end = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if("true".equals(is_end))
				{
					//已结束的
					con += " and cs.end_time < '"+DateUtil.getCurrentDate()+"'";
				}
				if("false".equals(is_end))
				{
					con += " and (cs.end_time = '' or cs.end_time is null or cs.end_time >= '"+DateUtil.getCurrentDate()+"')";
				}
			}
		}		
		return con;
	}
	
	public static TurnPageBean getSurveyRecommendCount(String params)
	{
		TurnPageBean tpb = new TurnPageBean();
		tpb.setCount(Integer.parseInt(SurveyService.getSurveyRecommendListCount(getSurveySearchCon(params))));
		tpb.setCur_page(cur_page);
		tpb.setPage_size(page_size);
		tpb.setPage_count(tpb.getCount()/tpb.getPage_size()+1);
		if(tpb.getCount()/tpb.getPage_size() == 0 && tpb.getPage_count() > 1)
			tpb.setPage_count(tpb.getPage_count()-1);
		
		if(cur_page > 1)
			tpb.setPrev_num(cur_page-1);
		
		tpb.setNext_num(tpb.getPage_count());
		if(cur_page < tpb.getPage_count())
			tpb.setNext_num(cur_page+1);
		return tpb;
	}
	
	@SuppressWarnings("unchecked")
	public static List<SurveyBean> getSurveyRecommendList(String params)
	{
		int current_page = 1;
		int page_sizes = Integer.parseInt(JconfigUtilContainer.velocityTemplateConfig().getProperty("num", "15", "show_list_num"));
		String order_by = "sub.id desc";
		String con = getSurveySearchCon(params);//拼查询条件
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
		return SurveyService.getSurveyRecommendList(con,((current_page-1)*page_sizes),page_sizes);
	}
	
	public static void main(String[] args)
	{
		//System.out.println(getSRecommendList("size=10").get(0).getS_name());
	}
}
