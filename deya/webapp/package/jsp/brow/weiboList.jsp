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
	String p = request.getParameter("p"); //当前页数
	if(p==null)
	{
		p="1";
	}
	String size = request.getParameter("size");//当前页面条数
	if(size==null)
	{
		size="10";
	} 
	
	/**
	http://www.nx.gov.cn/jsp/brow/weiboList.jsp?p=1&size=10
		p 当前页数
		size 每页条数
	*/
	String file_name = "/cicro/wcm/shared/classes/nxdata.xls";
	WeiBoService.readWeiboListAll(file_name);
	List<WeiBoBean> listResult = WeiBoService.readWeiboList(Integer.valueOf(p),Integer.valueOf(size));

	
	List<WeiBoBean> listResult2 = new ArrayList<WeiBoBean>();
	for(WeiBoBean boBean : listResult){
		String time_c = boBean.getTime().trim();
		//System.out.println("time_c----" + time_c);
		boBean.setTime(time_c.substring(5));
		listResult2.add(boBean);
	}
	
	JSONArray listResultJo = JSONArray.fromObject(listResult2);
    String listResultStr = listResultJo.toString();
    if(jsoncallback.equals("")){
		out.println(listResultStr);
	}else{
		out.println(jsoncallback+"("+listResultStr+")");
	}
%>
