<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.search.SearchForManager"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
       List<Map> list = SearchForManager.getSiteList();
       System.out.println("list====" + list);
       pageContext.setAttribute("list",list);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>index</title>
    <link rel="stylesheet" type="text/css" href="style/index.css"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>  
  <style type="text/css">
<!--
input.FormText1{
font-size: 12px;
line-height: 16px;
    BORDER-RIGHT: #929392 1px solid;
    BORDER-TOP: #929392 1px solid;    
    BORDER-LEFT: #929392 1px solid;
    BORDER-BOTTOM: #929392 1px solid;
    BACKGROUND-COLOR: #f1f5fa

height:25px;
}
-->
</style>
  <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
  <script type="text/javascript">
  var nameDb='';
  function doCreate(dbname){
	     if(nameDb!=""){
	        alert("由于效率问题，一次只能对一个索引库进行操作");
	        var imag = '<img width="12px" height="12px" src="images/z1.gif"/>';
	        $("#"+nameDb).html(imag + "正在建索引");
	        return;
	     }
	     //alert(dbname);
	     var imag = '<img width="12px" height="12px" src="images/z1.gif"/>';
	     $("#"+dbname).html(imag + "正在建索引");
	     var url = "ajaxUtil.jsp?action=create&dbname="+dbname;
	     nameDb = dbname;
	     $.get(url,null,callBack);
  }
  function callBack(data){ 
	    if($.trim(data)=='1'){
	      alert("创建索引成功！");
	      if(nameDb!='ALL'){
	    	  $("#"+nameDb).html("已建索引");
		  }else{
			  $("#"+nameDb).html("");
		  }
	    }else{
	      alert("创建索引失败");
	      if(nameDb!='ALL'){
	    	  $("#"+nameDb).html("未建索引");
		  }else{
			  $("#"+nameDb).html("");
		  }  
	    }
	    nameDb = "";
	    //var result = "<font color='red'>"+data+"</font>"
	    //
  }

  function doDelete(dbname){
	     if(nameDb!=""){
	        alert("由于效率问题，一次只能对一个索引库进行操作");
	        return;
	     }
	     //alert(dbname);
	     var imag = '<img width="12px" height="12px" src="images/z1.gif"/>';
	     $("#"+dbname).html(imag + "正在删除索引");
	     var url = "ajaxUtil.jsp?action=delete&dbname="+dbname;
	     nameDb = dbname;
	     $.get(url,null,doDeleteCallBack);
  }
  function doDeleteCallBack(data){ 
	    if($.trim(data)=='1'){
	      alert("删除索引成功！");
	      if(nameDb!='ALL'){
	    	  $("#"+nameDb).html("未建索引");
		  }else{
			  $("#"+nameDb).html("");
		  }
	    }else{
	      alert("删除索引失败");
	      if(nameDb!='ALL'){
	    	  $("#"+nameDb).html("已建索引");
		  }else{
			  $("#"+nameDb).html("");
		  }  
	    }
	    nameDb = "";
	    //var result = "<font color='red'>"+data+"</font>"
	    //
   }
  </script>
  <body>
	     <table id="tables" border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
		  <tr id="lable_tr">
		   <td width="100%" height="26px" align="center" id="jcxx" colspan="2"><span style="font-size:16px"><strong>索引信息</strong></span></td>
		  </tr>
		  <tr id="lable_tr">
		   <td width="95%" height="20px" align="right" id="jcxx" ><span id="ALL"></span>
		   <input type="button" class="FormText1" value="创建全部索引" onclick="doCreate('ALL')">&nbsp;&nbsp;
		   <input type="button" class="FormText1" value="删除全部索引" onclick="doDelete('ALL')">
		   </td>
		   <td width="5%" height="20px" align="right" id="jcxx" ></td>
		  </tr>
		 </table>      
		 <!-- 表单区域 开始--> 
		 <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">	
		  <tr>
		   <td id="content_area" colspan="4" valign="top">
		   		<span id="errorSpan" style="width:99%;text-align:center"></span>
		    <table id="row" border="0" cellpadding="0" cellspacing="0" width="100%" >
			 <tr>
			  <td align="center" height="5px" width="28px" class="content_td"></td>
			  <td align="center" width="90px" class="content_td"></td>
			 </tr> 

			 <tr id='takeDepTR'>
			       <td align='center' class='content_tdtop' width='10%' height='30px'>序号</td>
			       <td align='center' class='content_tdtop' width='20%'>索引库标示</td>
			       <td align='center' class='content_tdtop' width='30%'>索引库名字</td>
			       <td align='center' class='content_tdtop' width='10%'>状态</td>
			       <td align='center' class='content_tdtop2' width='30%'>操作</td>
			 </tr>
			 <c:forEach var="bean" items="${list}" varStatus="status" begin="0" step="1">
				 <tr id='takeDepTR'>
				       <td align='center' class='content_td' width='10%' height='30px'>${status.index+1}</td>
				       <td align='center' class='content_td' width='20%'>${bean.site_id}</td>
				       <td align='center' class='content_td' width='30%'>${bean.site_name}</td>
				       <td align='center' class='content_td' width='10%'>
				       <span id="${bean.site_id}">
				       <c:choose>
				          <c:when test="${bean.state=='1'}">
				                                     已建索引
				          </c:when>
				          <c:otherwise>
				             	<font color="red">未建索引</font>
				          </c:otherwise>
				       </c:choose>
				       </span>
				       </td>
				       <td align='center' class='content_td2' width='30%'><input type="button" class="FormText1" value="创建索引" onclick="doCreate('${bean.site_id}')">
				       &nbsp;&nbsp;&nbsp;<input type="button" class="FormText1" value="删除索引" onclick="doDelete('${bean.site_id}')"></td>
				 </tr>
			 </c:forEach>
			</table>
		   </td>
		  </tr>
		 </table>
	   <!-- 表单区域 结束-->
  </body>
</html>
