<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 

	<title>Nested Layouts</title> 
	<link rel="stylesheet" type="text/css" href="../style/menu/easyui.css">
	<link rel="stylesheet" type="text/css" href="../style/list.css">
	<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/java.js"></script>
	<script type="text/javascript" src="../js/jsonrpc.js"></script>
	<script type="text/javascript" src="../js/jquery.c.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>	
	<script type="text/javascript" src="../js/util.js"></script>	
	<script type="text/javascript" src="../js/loginCheck.js"></script>
	<script src="js/statisticsList.js"></script>

	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var div_height = 0;		

		$(document).ready(function () {	
			setContentAreaHeight();
			initTable();
			showTurnPage();
			showList();		
			
		}); 
			
		function setContentAreaHeight()
		{
			setDivHeightAndListNum();//设置列表DIV的自适应高度及显示列表条数 util.js　中
			$("#contents_list").css("height",div_height);
		}
		
	//-->
	</SCRIPT>	
</head> 
<body> 
	<input type="hidden" id="handleId" name="handleId" value="H32001">
	<table border="0" cellpadding="0" cellspacing="0" width="99%" class="right_body">
	  <tr>
	   <td class="b_top_l"></td>
	   <td class="b_top_c">
	    <!-- 当前位置区域　开始 -->
		<div class="blankW3"></div>
		<div class="cur_position_img"></div>
		<div class="cur_position_text">当前位置：问卷调查 >> 问卷调查管理 >> 数据统计</div>
		<!-- 当前位置区域　结束 -->
	   </td>
	   <td class="b_top_r"></td>
	  </tr>
	  <tr>
	   <td class="b_center_l"></td>
	   <td class="b_center_c" align="right" >
	   <!-- 功能按钮区域　开始 -->
	    <table border="0" cellpadding="0" cellspacing="0" height="28px" >
		 <tr>
		  <td >&nbsp;</td>
		 </tr>
		</table>	
		<!-- 列表区域 开始 -->
		<div id="table"></div>
		<!-- 列表区域 结束 -->
	   </td>
	   <td class="b_center_r"></td>
	  </tr>
	  <tr>
	   <td class="b_bottom_l"></td>
	   <td class="b_bottom_c" >
	    <!-- 翻页区域 开始 -->
		 <div id="turn"></div>
		<!-- 翻页区域 结束 -->
	   </td>
	   <td class="b_bottom_r"></td>
	  </tr>
	 </table> 
<!-- 操作菜单区域　开始 -->
<div id="menu" class="easyui-menu" style="width:120px;">
		<div onclick="openSubject()">数据统计</div>
		<div onclick="openAnswer()">所有答卷</div>
		<div class="menu-sep"></div>
		<div>关闭</div>
	</div>	
<!-- 操作菜单区域　结束 -->

</body> 

</html> 

