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
	String q = request.getParameter("q"); //新闻栏目  如果是多个栏目 可以用逗号分隔 例如“123,123,4”
	if(q==null)
	{
		q="";
	}
	String p = request.getParameter("p");//当前页数
	if(p==null)
	{
		p="1";
	} 
	/**
	http://www.qhdzzl.gov.cn/search/searchResultGJ.jsp&q=%E4%BF%A1%E6%81%AF&p=2
	*/
	String site_id = com.deya.wcm.services.control.domain.SiteDomainManager.getSiteIDByDomain(request.getLocalName());
	com.deya.wcm.bean.search.SearchResult result = com.deya.wcm.services.search.search.SearchManager.searchGJ(request);
	JSONArray listResultJo = JSONArray.fromObject(result.getItems());
    String listResultStr = listResultJo.toString();
    if(jsoncallback.equals("")){
		out.println(listResultStr);
	}else{
		out.println(jsoncallback+"("+listResultStr+")");
	}
%>
