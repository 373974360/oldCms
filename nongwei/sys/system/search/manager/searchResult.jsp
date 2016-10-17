<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.deya.wcm.services.search.search.SearchManager"%>
<%@page import="com.deya.util.Encode"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
		String path = request.getContextPath();
		//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
		String basePath = request.getScheme()+"://"+request.getServerName();
		pageContext.setAttribute("path",basePath);
		
		String q = com.deya.wcm.services.search.search.util.SearchUtil.getXmlParam(request);
		
        com.deya.wcm.bean.search.SearchResult searchResult = SearchManager.search(q);
        pageContext.setAttribute("result",searchResult); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html> 
  <head>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>搜索结果</title>  
	<LINK href="style/style1.css" type=text/css rel=stylesheet>
	<script type="text/javascript" src="/search/js/jquery-1.4.2.min.js"></script>   
	<script type="text/javascript" src="/search/js/page.js"></script> 
	<script type="text/javascript" src="files/head.js"></script> 
	<script type="text/javascript"> 
	    function submitForm(){
		    $("#form1").submit();
		}
	</script>
  </head>

  <body background="http://www.yplib.org.cn/structure/index.files/bj.gif">
	<!-- 当前位置　开始 -->
	<!-- 当前位置　结束 -->
	<!-- 主体区　开始 -->
	<table cellspacing=0 cellpadding=0 width=952 border=0 align="center" class="table_border" >
	 <!-- 表单区　开始 -->
	 <tr>
	  <td class="td_border" width="100px"><img src="images/search_flag.gif" width="100" border="0" alt="" height="47px"></td>
	  <td class="td_border" valign="middle" width="852px">
	  <form action="searchResult.jsp" name="form1" id="form1" method="get">
	     <span id="search_form_span" style="display:none"></span>
	     <div style="clear:both; height:8px; overflow:hidden; display:block;"></div>
	     <div style="float:left;">
		 <input type="text" id="q2" name="q2" value="" size="31"/> 
		  </div><div style="float:left;margin-top:2px;margin-left:12px;"><img src="images/submit2.gif" width="59" height="20" border="0" alt="" onclick="submitForm()" style="cursor:pointer"></div>
	   </form>
	  </td>
	 </tr>
	 <!-- 表单区　结束 -->
	 <tr>
	  <td colspan="2" align="left" height="10"> </td>
	 <tr>
	 <tr>
	  <td colspan="2" align="left">
	  <div  style="font-size:14px;margin-left:20px">搜索关键字"<font color="#CC0033">${result.key}</font>",用时<font color="#CC0033">${result.time}</font>秒</div>
	  </td>
	 <tr>
	  <td colspan="2" align="center">
	  <!-- 结果列表区　开始 -->
	   <div style="width:890px;margin-top:0px;margin-bottom:20px;">
<c:if test="${result==null}">
   <div  style="font-size:14px;"><font color="#CC0033">没有要搜索的结果！</font></div>
</c:if>
<c:if test="${result!=null}">
	<c:if test="${fn:length(result.items)==0}">
	   <div  style="font-size:14px;"><font color="#CC0033">没有要搜索的结果！</font></div>
	</c:if>
</c:if>

            	<div style="font-size:1px; height:5px;"></div>
            	<div style="font-size:1px; height:15px;"></div>
 <c:forEach var="item" items="${result.items}" varStatus="status" begin="0" step="1">        
            <div style="text-align:left; line-height:20px;"><a href='${item.url}' style="font-size:16px;color:#3266cc;" target="_blank">${item.title}</a></div>
			<div style="font-size:1px; height:5px;"></div>
			<div  style="text-align:left;line-height:20px;font-size:12px;color:#4D4D4D">
				 <span>时间：${item.time}</span>
			</div>
			<div  style="text-align:left; line-height:20px; font-size:14px;color:#000000;">${item.content}</div>
			<div  style="text-align:left;line-height:15px;font-size:12px;">
				<span>${item.url}</span>
			</div>  
			<div style="font-size:1px; height:15px;"></div>
 </c:forEach>      
      </div>
	  </td>
	 </tr>
	 <tr style="display:">
	  <td height="30px" colspan="2" align="center" class="current_pos">共　${result.pageControl.maxRowCount}　条信息　共　${result.pageControl.maxPage}　页　第　${result.pageControl.curPage}　页　<a href="#" class="CicroP846XZ_3443_2868_yplib_25_Local_page_a"  onclick="javascript:fnTurnPage('first')" hidefocus="true">首页</a>　<a class="CicroP846XZ_3443_2868_yplib_25_Local_page_a" href="#" onclick="javascript:fnTurnPage('previous')" hidefocus="true">上一页</a>　<a class="CicroP846XZ_3443_2868_yplib_25_Local_page_a"  href="#" onclick="javascript:fnTurnPage('next')" hidefocus="true">下一页</a>　<a class="CicroP846XZ_3443_2868_yplib_25_Local_page_a" href="#" onclick="javascript:fnTurnPage('last')" hidefocus="true">末页</a>　</td>
	 </tr>
	 <tr>
      <td colspan="2" align="center">
	   <!-- table cellspacing=0 cellpadding=0 width=910 border=0 align="center" class="current_pos" bgcolor="#EEE2CC" height="40px">
	    <tr>
		 <td width="150px" align="center" style="font-size:14px"><strong>相关搜索</strong></td>
		 <td>杨浦区图书馆　　杨浦区图书馆地址　　上海杨浦区图书馆</td>		 
		</tr>		
	   </table -->
	  </td>
	 </tr>
	 <tr>
	  <td colspan="2">&nbsp;</td>
	 <tr>
	</table>
	<!-- 主体区　结束 -->
	<script language="JavaScript">
	<!--
	//document.write(getFootStr());
	//-->
	</script>
<!--------翻页所用到的js--------->  
<script language="JavaScript" type="text/JavaScript">
function returnFun(type_P){
    fnTurnPage(type_P);
}
 function fnTurnPage(str)
{
	
 var currentPage = ${result.pageControl.curPage}*1;
 var totalPage = ${result.pageControl.maxPage}*1;
 var pagesize = ${result.pageControl.rowsPerPage};
 
 var pageNum = 1;
 if(str == "first")
 {
  pageNum = 1;
 }
 if(str == "previous")
 {
  if(currentPage > 1) pageNum = currentPage-1;
 }
 if(str == "next")
 {
  if(currentPage < totalPage) 
     pageNum = currentPage + 1;
  else
     pageNum = totalPage; 
 }
 if(str == "last")
 {
  pageNum = totalPage;
 } 
 var url = window.location.href;
 
 var indexN2 = url.indexOf("p=");
 if(indexN2>-1){
	 url = url.substring(0,indexN2+2);
	 url += pageNum;
 }else{
     if(url.indexOf("?")>-1){
    	 url += "&p="+pageNum;
     } else{
    	 url += "?p="+pageNum;
     }
 }
 //alert(url);
 window.location = url;

}

/////////////////////////////////////////
function gotoPage(){ 
  var currentPage = ${result.pageControl.curPage}*1;
  var totalPage = ${result.pageControl.maxPage}*1;
  var pagesize = ${result.pageControl.rowsPerPage};
  var gotoPage1 = document.getElementById("goPage").value;
  if(trim(gotoPage1)==""){
     alert("请输入页数");
	 document.getElementById("goPage").select();
	 return;
  }else{
	  if(!checkint(gotoPage1))
	  {
		 alert("只能输入整数!");   	 
		 document.getElementById("goPage").select();
		 return;
	  }
		if(gotoPage1 < 1 || gotoPage1 > totalPage)
	   {
		   alert("你输入的页数超出范围,请重新输入!");
           document.getElementById("goPage").select();
		   return;
	   }else{
		   //alert(gotoPage1);
	         var url = window.location.href; 

	         var indexN2 = url.indexOf("p=");
	         if(indexN2>-1){
	        	 url = url.substring(0,indexN2+2);
	        	 url += gotoPage1;
	         }else{
	             if(url.indexOf("?")>-1){
	            	 url += "&p="+gotoPage1;
	             } else{
	            	 url += "?p="+gotoPage1;
	             } 
	         }
	         //alert(url);
	         window.location = url;
	         
	         /*
			 var indexN2 = url.indexOf("?");
			 if(indexN2>-1)
			 {
			   url = url.substring(0,indexN2)
			 }
			 //alert(url);
			 url += "?treeId="+treeId+"&ps="+gotoPage1;
			 window.location = url;
			 */
	   }
  }
  
}
function checkint(svalue){
	  var zhengshu = /^(\s)*[0-9]*(\s)*$/;
	  if(zhengshu.test(svalue)){
	      return true;
	   }else{
	     return false;
	  }
}
function trim(str){
	 str = str.toString()
	 var index = str.indexOf(" ")
	 if(index == -1 || str.length == 0) 
	  return str
	 //去掉头部空格
	 if(index == 0){
	  while(index == 0)
	  {
	   str = str.replace(" ","")
	   index = str.indexOf(" ")
	  }
	 }
	 return str;
} 
 </script>
  </body>
</html>