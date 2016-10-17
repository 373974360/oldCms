<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.wcm.services.cms.info.InfoBaseManager"%>
<%@ page import="com.deya.wcm.bean.cms.info.InfoBean" %>
<%
	String info_id = request.getParameter("info_id");
    InfoBean ifb = InfoBaseManager.getInfoById(info_id);
    if("1".equals(ifb.getIsIpLimit()))
    {
        String ip = "192.168.2";
        String clientIp = request.getHeader("x-forwarded-for");
        if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        out.println("IP限制策略："+ip);
        out.println("客户端IP地址："+clientIp);
        if(clientIp.indexOf(ip) > -1)
        {
            out.println("<script>alert('IP限制！');</script>");
        }
        else
        {
            out.println("<script>alert('IP不限制！');</script>");
        }
    }
	String siteid = request.getParameter("site_id");	
	//out.println(InfoBaseManager.getInfoTemplateContent(info_id,siteid));
%>
