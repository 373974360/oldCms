<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%  
         String fileName = "我的Word文档";
         fileName = request.getParameter("title").toString();//获取标题作为文件名称
        // String fileCNName = new String(fileName.getBytes("utf-8"),"gb2312");//转为文件名支持中文(用来支持中文的文件名)
         response.setHeader("Content-Disposition","attachment;filename="+fileName+".doc"); //设置页面样式word格式
         String htmlData = request.getParameter("htmlData").toString();//取得内容
         // System.out.println(htmlData);  
%>                      
  <body>
  <%
    out.println(htmlData);
  %>
  </body>