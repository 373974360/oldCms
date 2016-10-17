<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.bean.cms.info.InfoBean"%>
<%@page import="com.deya.wcm.template.velocity.data.InfoUtilData,net.sf.json.*"%>
<%
   
response.setContentType("application/json");//这个一定要加

    String jsoncallback = (String)request.getParameter("callback");
    if(jsoncallback==null){
    	jsoncallback = "";
    }
    String model_id = request.getParameter("model_id"); //内容模型 id 11为文章
	if(model_id==null)
	{
		model_id="";
	}
	String cat_id = request.getParameter("cat_id"); //新闻栏目  如果是多个栏目 可以用逗号分隔 例如“123,123,4”
	if(cat_id==null || cat_id.equals("1"))
	{
		cat_id="";
	}
	String cur_page = request.getParameter("cur_page");//当前页数
	if(cur_page==null)
	{
		cur_page="1";
	} 
	String size = request.getParameter("size");//每页条数
	if(size==null)
	{
		size="5";
	}
	String is_show_thumb = request.getParameter("is_show_thumb");//每页条数
	if(is_show_thumb==null)
	{
		is_show_thumb="";
	}
	
	String weight = request.getParameter("weight");//权重
	if(weight==null)
	{
		weight="";
	}
	
	if(cat_id.equals("")){
		is_show_thumb="true";
		cat_id = "2090";
	}
	/**
	http://www.nxszs.gov.cn/jsp/brow/infoList.jsp?cat_id=10045&cur_page=1&size=2
		cat_id 新闻栏目  如果是多个栏目 可以用逗号分隔 例如“123,123,4”
		cur_page 当前页数
		size 每页条数
		is_show_thumb=true (表示只显示有缩略图的信息)   例如：http://www.nxszs.gov.cn/jsp/brow/infoList.jsp?cat_id=10045&cur_page=1&size=2&is_show_thumb=true

		http://www.xasoftpark.com/jsp/brow/infoList.jsp?cat_id=10012,10013,10014,10015,10085,10087,10086,10088,10093,10094,10095,10096,10089,10090	,10091&cur_page=1&size=100

	返回数据格式：
	[{"app_id":"","author":"吕芹","auto_desc":"","cat_cname":"百姓关注","cat_id":10045,"comment_num":0,"content_url":"/xinwendongtai/bxgz/35375.htm",
		"day_hits":0,"description":"","down_dtime":"","editor":"吕芹","final_status":0,"from_id":0,"hits":4,"i_ver":0,"id":0,"info_description":"",
		"info_id":35375,"info_keywords":"","info_status":0,"input_dtime":"","input_user":0,"is_allow_comment":0,"is_am_tupage":0,"is_auto_down":0,
		"is_auto_up":0,"is_host":0,"is_pic":0,"lasthit_dtime":"","model_id":11,"modify_dtime":"","modify_user":0,"month_hits":4,"opt_dtime":"",
		"page_count":1,"pre_title":"","released_dtime":"2012-09-20 17:20:40","site_id":"CMSCMSszs","source":"石嘴山日报","step_id":0,"subtitle":"",
		"tags":"","thumb_url":"http://www.nxszs.gov.cn/CMSCMSszs/201209/201209200522048.jpg","title":"“机械化部队”开进平罗玉米地","title_color":"",
		"title_hashkey":0,"top_title":"","tupage_num":1000,"up_dtime":"","week_hits":4,"weight":60,"wf_id":0},{"app_id":"","author":"吕芹",
			"auto_desc":"","cat_cname":"百姓关注","cat_id":10045,"comment_num":0,"content_url":"/xinwendongtai/bxgz/35374.htm","day_hits":0,
			"description":"","down_dtime":"","editor":"吕芹","final_status":0,"from_id":0,"hits":3,"i_ver":0,"id":0,"info_description":"",
			"info_id":35374,"info_keywords":"","info_status":0,"input_dtime":"","input_user":0,"is_allow_comment":0,"is_am_tupage":0,
			"is_auto_down":0,"is_auto_up":0,"is_host":0,"is_pic":0,"lasthit_dtime":"","model_id":11,"modify_dtime":"","modify_user":0,
			"month_hits":3,"opt_dtime":"","page_count":1,"pre_title":"","released_dtime":"2012-09-20 17:11:45","site_id":"CMSCMSszs",
			"source":"石嘴山日报","step_id":0,"subtitle":"","tags":"","thumb_url":"","title":"平罗打响“双节”供电“保卫战”","title_color":"",
			"title_hashkey":0,"top_title":"","tupage_num":1000,"up_dtime":"","week_hits":3,"weight":60,"wf_id":0}] 
	*/
	String site_id = com.deya.wcm.services.control.domain.SiteDomainManager.getSiteIDByDomain(request.getLocalName());
	
	String infoParam1 = "site_id="+site_id+";cat_id="+cat_id+";weight="+weight+";cur_page="+cur_page+";size="+size+";is_show_thumb="+is_show_thumb+";orderby=ci.released_dtime desc";
	if(!model_id.equals("")){
		infoParam1 += ";model_id="+model_id;
	}
	//System.out.println("infoParam1 == " + infoParam1);
	List<InfoBean> listResult = InfoUtilData.getInfoList(infoParam1);
	 
	JSONArray listResultJo = JSONArray.fromObject(listResult);
    String listResultStr = listResultJo.toString();
    if(jsoncallback.equals("")){
		out.println(listResultStr);
	}else{
		out.println(jsoncallback+"("+listResultStr+")");
	}
%>
