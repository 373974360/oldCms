<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.bean.cms.info.InfoBean"%>
<%@page import="com.deya.wcm.template.velocity.data.InfoUtilData,net.sf.json.*"%>
<%@page import="com.deya.wcm.bean.template.TurnPageBean"%>
<%
    String jsoncallback = (String)request.getParameter("callback");
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
	String is_show_thumb = request.getParameter("is_show_thumb");//每页条数
	if(is_show_thumb==null)
	{
		is_show_thumb="";
	}
	/**
	http://www.nxszs.gov.cn/jsp/brow/infoListCount.jsp?cat_id=10045&cur_page=1&size=2
		cat_id 新闻栏目  如果是多个栏目 可以用逗号分隔 例如“123,123,4”
		cur_page 当前页数
		size 每页条数
		is_show_thumb=true (表示只显示有缩略图的信息)   例如：http://www.nxszs.gov.cn/jsp/brow/infoListCount.jsp?cat_id=10045&cur_page=1&size=2&is_show_thumb=true
	返回数据格式：
	[{"count":928,"cur_page":1,"next_num":2,"page_count":464,"page_size":2,"prev_num":1}] 
	*/
	String site_id = com.deya.wcm.services.control.domain.SiteDomainManager.getSiteIDByDomain(request.getLocalName());
	
	String infoParam1 = "site_id="+site_id+";cat_id="+cat_id+";cur_page="+cur_page+";size="+size+";is_show_thumb="+is_show_thumb+";orderby=ci.released_dtime desc";
	//System.out.println("infoParam1 == " + infoParam1);
	TurnPageBean listResult = InfoUtilData.getInfoCount(infoParam1);
	
	JSONArray listResultJo = JSONArray.fromObject(listResult);
    String listResultStr = listResultJo.toString();
    out.println(jsoncallback+"("+listResultStr+")");
%>
