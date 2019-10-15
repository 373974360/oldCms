<%@ page contentType="text/html; charset=utf-8"%>
<%
	String domain = request.getServerName();
	String tm_id=request.getParameter("tm_id");
	String cat_id=request.getParameter("cat_id");
	String model_id=request.getParameter("model_id");
	out.println("<script>");
	out.println("location.href='http://sfrz.shaanxi.gov.cn/sysauthserver/authorize?client_id=000000069&response_type=code&redirect_uri=http://"+domain+"/appeal/formAuthLogin.jsp?tm_id="+tm_id+"&model_id="+model_id+"&cat_id="+cat_id+"'");
	out.println("</script>");
%>