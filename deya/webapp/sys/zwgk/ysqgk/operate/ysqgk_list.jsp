<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>依申请公开信息</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />


<link type="text/css" rel="stylesheet" href="../../../styles/themes/default/tree.css" />
<link type="text/css" rel="stylesheet" href="../../../styles/sq.css" />


<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../../js/indexjs/indexList.js"></script>
<script type="text/javascript" src="../../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../js/ysqgk.js"></script>
<script type="text/javascript">
var node_id ="<%=request.getParameter("site_id")%>";
var app_id = "zwgk";
var snum = "<%=request.getParameter("snum")%>";
if(snum == null || snum =="")
{
	snum = "0";
}
$(document).ready(function(){
	init_input();
	initButtomStyle();
	initTabAndStatus();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable(node_id);
	reloadInfoDataList();
});
</script>
</head>
<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" > 
	<tr> 
		<td align="left" class="fromTabs" width="130"> 
			<select id="ysq_type" name="ysq_type" class="input_select width60" onchange="changeYsqType(this.value)"> 
				<option value="-1" selected="selected">全部</option>
				<option value="0">公民</option>
				<option value="1">法人或者其他组织</option>

			</select> 
			<select id="put_dtime" class="input_select width60" onchange="changePutTime(this.value)"> 
				<option selected="selected" value="0">日期</option> 
				<option value="day">今日</option> 
				<option value="yestetoday">昨日</option> 
                <option value="week">一周内</option> 
				<option value="month">一月内</option> 
			</select> 
		</td> 
		<td align="left" class="fromTabs" width="80"> 
			<input id="btn1" name="btn1" type="button" onclick="openAddPage(node_id)" value="手动添加"/>					
			<span class="blank3"></span> 
		</td> 
		<td align="left" width=""> 
			<ul class="fromTabs"> 
				<li class="list_tab list_tab_cur"> 
					<div class="tab_left"> 
						<div class="tab_right">待处理</div> 
					</div> 
				</li> 
				<li class="list_tab"> 
					<div class="tab_left"> 
						<div class="tab_right">已受理</div> 
					</div> 
				</li> 
				<li class="list_tab"> 
					<div class="tab_left"> 
						<div class="tab_right">已处理</div> 
					</div> 
				</li> 
                <li class="list_tab"> 
					<div class="tab_left">
						<div class="tab_right">无效申请</div> 
					</div> 
				</li> 
                <li class="list_tab"> 
					<div class="tab_left"> 
						<div class="tab_right">回收站</div> 
					</div> 
				</li> 
             </ul> 
		</td> 
		<td align="right" valign="middle" class="fromTabs">
		    <select id="searchType" class="input_select"> 
				<option value="1" selected="selected">申请单编号</option>
				<option value="2">内容描述</option> 
			</select> 
			<input id="searchkey"  name="searchkey" type="text" class="input_text" style="width:120px;" value=""/>
			<input id="btn" type="button" value="搜索" onclick="searchInfo()"/>
			<select id="orderByFields" class="input_select"  onchange="changeTimeSort(this.value)"> 
				<option selected="selected" value="1">时间倒序</option> 
				<option value="2">时间正序</option> 
			</select> 
			<span class="blank3"></span> 
		</td> 
	</tr> 
</table> 
<span class="blank3"></span>
<div id="listInfo_table"></div>
<div id="turn"></div>
	<div class="infoListTable" id="listTable_0"> 
	<table class="table_option" border="0" cellpadding="0" cellspacing="0"> 
		<tr> 
			<td align="left" valign="middle"> 
			    <input id="btn" name="btn6" type="button" onclick="updateRecord(table,'ysq_id','openUpdatePage(node_id)')" value="修改" />
				<!--input id="btn307" name="btn5" type="button" onclick="portOut()" value="导出"/--> 
				<input id="btn301" name="btn4" type="button" onclick="deleteRecord(table,'ysq_id','deleteInfoData(-1)');" value="删除" />
				<input id="btn" name="btn6" type="button" onclick="openHighSearchPage(node_id)" value="高级搜索" />			
			</td> 
		</tr> 
	</table> 
	</div> 
 
	<div class="infoListTable hidden" id="listTable_1"> 
	<table class="table_option" border="0" cellpadding="0" cellspacing="0"> 
		<tr> 
			<td align="left" valign="middle"> 
				<!--input id="btn307" name="btn5" type="button" onclick="portOut()" value="导出" /--> 
				<input id="btn301" name="btn4" type="button" onclick="deleteRecord(table,'ysq_id','deleteInfoData(-1)');" value="删除" />
				<input id="btn" name="btn6" type="button" onclick="openHighSearchPage(node_id)" value="高级搜索" />
			</td> 
		</tr> 
	</table> 
	</div> 
 
	<div class="infoListTable hidden" id="listTable_2"> 
	<table class="table_option" border="0" cellpadding="0" cellspacing="0"> 
		<tr> 
			<!--input id="btn307" name="btn5" type="button" onclick="portOut()" value="导出" /--> 
			<input id="btn301" name="btn4" type="button" onclick="deleteRecord(table,'ysq_id','deleteInfoData(-1)');" value="删除" />
			<input id="btn" name="btn6" type="button" onclick="openHighSearchPage(node_id)" value="高级搜索" /> 
		</tr> 
	</table> 
	</div> 
 
	<div class="infoListTable hidden" id="listTable_3"> 
	<table class="table_option" border="0" cellpadding="0" cellspacing="0"> 
		<tr> 
			<!--input id="btn307" name="btn5" type="button" onclick="portOut()" value="导出" /-->  
			<input id="btn301" name="btn4" type="button" onclick="deleteRecord(table,'ysq_id','deleteInfoData(-1)');" value="删除" />
			<input id="btn" name="btn6" type="button" onclick="openHighSearchPage(node_id)" value="高级搜索" /> 
		</tr> 
	</table> 
	</div> 
 
	<div class="infoListTable hidden" id="listTable_4"> 
	<table class="table_option" border="0" cellpadding="0" cellspacing="0"> 
		<tr> 
			<input id="btn307" name="btn5" type="button" onclick="reBackInfos()" value="还原" /> 
			<input id="btn301" name="btn4" type="button" onclick="deleteRecord(table,'ysq_id','deleteInfoData(-2)');" value="彻底删除" />
			<input id="btn" name="btn6" type="button" onclick="deleteInfoData(-3);" value="清空回收站" /-->  
		</tr> 
	</table> 
	</div> 
</div>
</body>
</html>