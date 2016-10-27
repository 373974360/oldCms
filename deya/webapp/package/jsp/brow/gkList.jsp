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
	String cat_id = request.getParameter("cat_id"); //新闻栏目  如果是多个栏目 可以用逗号分隔 例如“123,123,4”
	if(cat_id==null)
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
	String node_id = request.getParameter("node_id");
	if(node_id==null)
	{
		node_id="";
	}
	

	String site_id = com.deya.wcm.services.control.domain.SiteDomainManager.getSiteIDByDomain(request.getLocalName());
	
	String infoParam1 = "node_id="+node_id+";site_id="+site_id+";cat_id="+cat_id+";cur_page="+cur_page+";size="+size+";orderby=ci.released_dtime desc";
	//System.out.println("infoParam1 == " + infoParam1);
	
	List<com.deya.wcm.bean.cms.info.GKInfoBean> listResult = InfoUtilData.getGKInfoList(infoParam1);
	 
	JSONArray listResultJo = JSONArray.fromObject(listResult);
    String listResultStr = listResultJo.toString();
    if(jsoncallback.equals("")){
		out.println(listResultStr);
	}else{
		out.println(jsoncallback+"("+listResultStr+")");
	}
    
	/**
	http://www.nxszs.gov.cn/jsp/brow/gkList.jsp?node_id=GKjljcwyh&cat_id=10045&cur_page=1&size=2
		node_id 公开节点ID
		cat_id 新闻栏目  如果是多个栏目 可以用逗号分隔 例如“123,123,4” 为空就是全部信息
		cur_page 当前页数
		size 每页条数
	返回数据格式：
	[{"aboli_dtime":"","app_id":"zwgk","author":"纪检委","auto_desc":"","carrier_type":"纸质","cat_cname":"部门文件","cat_id":11253,
		"comment_num":0,"content_url":"/gk/fgwj/bmwj/30301.htm","day_hits":0,"description":"","doc_no":"","down_dtime":"","editor":"纪检委",
		"effect_dtime":"","file_head":"","file_list":[],"final_status":0,"from_id":0,"generate_dtime":"2012-07-19",
		"gk_duty_dept":"纪律检查委员会","gk_file":"","gk_format":"html","gk_index":"640200102/2012-000010","gk_input_dept":"纪律检查委员会",
		"gk_no_reason":"","gk_num":10,"gk_proc":"","gk_range":"面向社会","gk_signer":"","gk_time_limit":"长期公开","gk_type":0,
		"gk_validity":"有效","gk_way":"政府网站","gk_year":"2012","hits":0,"i_ver":0,"id":30301,"info_description":"","info_id":30301,
		"info_keywords":"","info_status":8,"input_dtime":"2012-07-19 15:25:29","input_user":26,"is_allow_comment":0,"is_am_tupage":0,
		"is_auto_down":0,"is_auto_up":0,"is_host":0,"is_pic":0,"language":"汉语","lasthit_dtime":"","model_id":14,"modify_dtime":"",
		"modify_user":0,"month_hits":0,"opt_dtime":"2012-07-19 15:25:29","page_count":1,"place_key":"","pre_title":"市纪委",
		"released_dtime":"2012-07-19 15:25:29","serve_id":0,"serve_name":"","site_id":"GKjljcwyh","source":"石嘴山网","step_id":100,
		"subtitle":"","tags":"加强 纪检监察 勤政 的通知 ","theme_id":0,"theme_name":"","thumb_url":"","title":"关于加强全市纪检监察机关勤政建设的通知",
		"title_color":"","title_hashkey":0,"top_title":"","topic_id":0,"topic_key":"加强 纪检监察 勤政 的通知 ","topic_name":"","tupage_num":1000,
		"up_dtime":"","week_hits":0,"weight":60,"wf_id":0},{"aboli_dtime":"","app_id":"zwgk","author":"纪检委","auto_desc":"",
			"carrier_type":"纸质","cat_cname":"部门文件","cat_id":11253,"comment_num":0,"content_url":"/gk/fgwj/bmwj/30299.htm",
			"day_hits":0,"description":"","doc_no":"","down_dtime":"","editor":"纪检委","effect_dtime":"","file_head":"",
			"file_list":[],"final_status":0,"from_id":0,"generate_dtime":"2012-07-19","gk_duty_dept":"纪律检查委员会","gk_file":"",
			"gk_format":"html","gk_index":"640200102/2012-000009","gk_input_dept":"纪律检查委员会","gk_no_reason":"","gk_num":9,"gk_proc":"",
			"gk_range":"面向社会","gk_signer":"","gk_time_limit":"长期公开","gk_type":0,"gk_validity":"有效","gk_way":"政府网站","gk_year":"2012",
			"hits":0,"i_ver":0,"id":30299,"info_description":"","info_id":30299,"info_keywords":"","info_status":8,
			"input_dtime":"2012-07-19 15:22:21","input_user":26,"is_allow_comment":0,"is_am_tupage":0,"is_auto_down":0,"is_auto_up":0,
			"is_host":0,"is_pic":0,"language":"汉语","lasthit_dtime":"","model_id":14,"modify_dtime":"","modify_user":0,"month_hits":0,
			"opt_dtime":"2012-07-19 15:22:21","page_count":1,"place_key":"","pre_title":"市纪委","released_dtime":"2012-07-19 15:22:21",
			"serve_id":0,"serve_name":"","site_id":"GKjljcwyh","source":"石嘴山网","step_id":100,"subtitle":"",
			"tags":"党员干部 开展 主题教育活动 实施方案 ","theme_id":0,"theme_name":"","thumb_url":"",
			"title":"关于在全市党员干部中开展“保持党的纯洁性发扬优良作风”主题教育活动的实施方案","title_color":"","title_hashkey":0,"top_title":"",
			"topic_id":0,"topic_key":"党员干部 开展 主题教育活动 实施方案 ","topic_name":"","tupage_num":1000,"up_dtime":"","week_hits":0,
			"weight":60,"wf_id":0}] 
	*/

%>
