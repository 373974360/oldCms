<%@page language="java" import="java.io.PrintWriter"%>
<%@ page import="com.deya.wcm.bean.cms.info.InfoBean" %>
<%@ page import="com.deya.wcm.services.cms.info.InfoBaseRPC" %>
<%@ page import="com.deya.wcm.services.media.Blog" %>
<%
    String infoId=request.getParameter("id");
    String token="2.00LxGKPE1Lk4tDc80789af550ft_wy";
    InfoBean bean= InfoBaseRPC.getInfoById(infoId);
    String content_url = bean.getContent_url();
    if(!content_url.substring(0,4).equals("http")){
        content_url="http://www.itl.gov.cn"+content_url;
    }
    Blog.updateStatus(bean.getTitle()+" "+content_url,token);
    PrintWriter outs=response.getWriter();
    outs.println("{\"data\":\"ok\"}");
%>
