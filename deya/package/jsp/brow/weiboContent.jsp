<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.bean.cms.info.InfoBean"%>
<%@page import="com.deya.wcm.template.velocity.data.InfoUtilData,net.sf.json.*"%>
<%@page import="com.deya.project.app.nx.WeiBoService"%>
<%@page import="com.deya.project.app.nx.WeiBoBean"%>
<%
   
response.setContentType("application/json");//这个一定要加

    String jsoncallback = (String)request.getParameter("callback");
    if(jsoncallback==null){
    	jsoncallback = "";
    }
	String id = request.getParameter("id"); //当前页数
	if(id==null)
	{
		id="1";
	}
	/**
	http://www.nx.gov.cn/jsp/brow/weiboContent.jsp?id=3570752697839410
	*/
	String file_name = "/cicro/wcm/shared/classes/nxdata.xls";
	WeiBoService.readWeiboListAll(file_name);
    WeiBoBean  weiBoBean = WeiBoService.readWeiboByCode(id);  
    JSONObject listResultJo = JSONObject.fromObject(weiBoBean);
    String listResultStr = listResultJo.toString();
    if(jsoncallback.equals("")){
		out.println("["+listResultStr+"]");
	}else{
		out.println(jsoncallback+"(["+listResultStr+"])");
	}
%>
