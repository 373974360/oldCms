<%@page contentType="text/html;charset=utf-8"%>
<%@page import="com.deya.wcm.bean.sendInfo.ReceiveInfoBean,java.util.*,com.deya.wcm.dao.sendInfo.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.regex.Matcher,java.util.regex.Matcher,java.util.regex.Pattern;"%>
<%!
public static String delHTMLTag(String htmlStr){ 
    String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
    String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
    String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式
    Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
    Matcher m_script=p_script.matcher(htmlStr); 
    htmlStr = m_script.replaceAll("");//过滤script标签
    Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
    Matcher m_style=p_style.matcher(htmlStr); 
    htmlStr =m_style.replaceAll(""); //过滤style标签
    Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
    Matcher m_html=p_html.matcher(htmlStr); 
    htmlStr =m_html.replaceAll(""); //过滤html标签
    return htmlStr.trim(); //返回文本字符串
} 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>报送数据</title>
<link type="text/css" rel="stylesheet" href="../../styles/themes/default/tree.css"/>
<link type="text/css" rel="stylesheet" href="../../styles/sq.css"/>
<jsp:include page="../../include/include_tools.jsp"/>


<script type="text/javascript"></script>
</head>
<body>
<div>
<%
	String id = request.getParameter("id");
    ReceiveInfoBean b = ReceiveInfoDAO.getReceiveInfoBean(id);
    if(b != null){
%>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" style="line-height:21px;" >
	<tr>
		<td align="left" valign="middle" class="fromTabs">
		    标题：<%=b.getTitle()%><br/>
		</td>
   </tr>
   <tr>
		<td align="left" valign="middle" class="fromTabs">
		    报送时间：<%=b.getS_send_dtime()%><br/>
		</td>
  </tr>
  <tr>
		<td align="left" valign="middle" class="fromTabs">
		    来源：<%=b.getSource()%><br/>
		</td>
  </tr>
  <tr>
		<td align="left" valign="middle" class="fromTabs" >  
	          <div id="content" name="content" style="width:96%;height:auto;line-height:24px;">
	                  内容：&nbsp;&nbsp;&nbsp;&nbsp;<%=b.getContent()%>
	          </div>
	          <br/>
	          <br/>	 
		</td>
  </tr>
</table>
<%
    }
%>
</div>
</body>
</html>
