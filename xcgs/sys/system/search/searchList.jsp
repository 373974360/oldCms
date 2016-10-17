<%@ page contentType="text/html; charset=utf-8"%>

<%

String app_id = request.getParameter("app");

if(app_id == null){

	app_id = "0";

}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>搜索管理</title>





<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/searchList.js"></script>

<script type="text/javascript">



var site_id = "0";

var app = "<%=app_id%>";





$(document).ready(function(){	

	initButtomStyle();

	init_FromTabsStyle();

	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();

	reloadList();	

});
</script>

<script type="text/javascript">
  var nameDb='';

  function doCreateConfirm(dbname){
	  top.msgConfirm("该操作会导致前台搜索功能暂不可用，<br/>确定要继续吗？","doCreate('"+dbname+"')");
  }
  
  function doCreate(dbname){
	     if(nameDb!=""){
	    	top.msgAlert("由于效率问题，一次只能对一个索引库进行操作");
	        var imag = '<img width="12px" height="12px" src="../../images/loading.gif"/>';
	        $("#"+nameDb).html(imag + "正在建索引");
	        return;
	     }
	     //alert(dbname);
	     var imag = '<img width="12px" height="12px" src="../../images/loading.gif"/>';
	     $("#"+dbname).html(imag + "正在建索引");
	     var url = "ajaxUtil.jsp?action=create&dbname="+dbname;
	     nameDb = dbname;
	     $.get(url,null,callBack);
  }
  function callBack(data){ 
	    if($.trim(data)=='1'){
	      top.msgAlert("创建索引成功！");
	      if(nameDb!='ALL'){
	    	  $("#"+nameDb).html("已建索引");
		  }else{
			  $("#"+nameDb).html("");
		  }
	    }else{
	      top.msgAlert("创建索引失败");
	      if(nameDb!='ALL'){
	    	  $("#"+nameDb).html("<font color='red'>未建索引</font>");
		  }else{
			  $("#"+nameDb).html("");
		  }  
	    }
	    nameDb = "";
	    //var result = "<font color='red'>"+data+"</font>"
	    //
  }


  function doDeleteConfirm(dbname){
	  top.msgConfirm("该操作会导致前台搜索功能不可用，<br/>确定删除吗？","doDelete('"+dbname+"')");
  }
  
  function doDelete(dbname){
	     if(nameDb!=""){
	    	top.msgAlert("由于效率问题，一次只能对一个索引库进行操作");
	        return;
	     }
	     //alert(dbname);
	     var imag = '<img width="12px" height="12px" src="../../images/loading.gif"/>';
	     $("#"+dbname).html(imag + "正在删除索引");
	     var url = "ajaxUtil.jsp?action=delete&dbname="+dbname;
	     nameDb = dbname;
	     $.get(url,null,doDeleteCallBack);
  }
  function doDeleteCallBack(data){ 
	    if($.trim(data)=='1'){
	      top.msgAlert("删除索引成功！");
	      if(nameDb!='ALL'){
	    	  $("#"+nameDb).html("<font color='red'>未建索引</font>");
		  }else{
			  $("#"+nameDb).html("");
		  }
	    }else{
	      top.msgAlert("删除索引失败");
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

</head>



<body>

<div>

	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >

		<tr>		

			<td align="left" valign="middle" id="dept_search" class="search_td fromTabs" >

				<input id="btn1" name="btn1" type="button" onclick="doCreateConfirm('ALL')" value="创建全部索引" />

				<input id="btn1" name="btn1" type="button" onclick="doDeleteConfirm('ALL')" value="删除全部索引" />		
                &nbsp;&nbsp;&nbsp;<span id="ALL"></span>
				<span class="blank3"></span>

			</td>		

		</tr>

	</table>

	<span class="blank3"></span>

	<div id="table"></div>

	<div id="turn"></div>

	<table class="table_option" border="0" cellpadding="0" cellspacing="0">

	<tr>

		<td align="left" valign="middle">

			<input id="btn1" name="btn1" type="button" onclick="doCreateConfirm('ALL')" value="创建全部索引" />

			<input id="btn1" name="btn1" type="button" onclick="doDeleteConfirm('ALL')" value="删除全部索引" />			

		</td>

	</tr>

   </table>	

</div>

</body>

</html>