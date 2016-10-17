<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String gph_type = request.getParameter("gph_type");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>依申请公开常用语</title>
<meta http-equiv="X-UA-Compatible" content="IE=8"/>


<link type="text/css" rel="stylesheet" href="../../../styles/themes/default/tree.css" />
<link type="text/css" rel="stylesheet" href="../../../styles/sq.css" />
<meta name="generator" content="cicro-Builder"/>
<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/phrasal.js"></script>
<script type="text/javascript">
var app_id = "zwgk";
var gph_type ="<%=gph_type%>";
$(document).ready(function(){
	init_input();
	initButtomStyle();
	init_FromTabsStyle();
    if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	initPage();
	reloadList();
});

function initPage()
{
	if(gph_type== null || gph_type == "" || gph_type == "null")
	{
		gph_type = "all";
	}
	$(".customLable li").click(function(){
		$(".customLable li").removeClass("cur");
		$(this).addClass("cur");
		getDataList($(this).attr("vname"));
		});	
	eval('$(".customLable li[vname=\''+gph_type+'\']").click()');
}
function getDataList(vname)
{
	gph_type = vname;
	reloadList();
}
function reloadList()
{
	initTable();
	showList(gph_type);
}
</script>
<style>
.customLable{}
.customLable li{float:left; padding-right:12px; color:#333; cursor:pointer; text-decoration:underline;}
.customLable li.cur{color:#F00;}
</style>
</head>
</head>
<body>
<div>
	<table class="table_option fromTabs" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="50" align="right">分类：</td>
			<td align="left" valign="middle">
				<ul class="customLable">
					<li vname="all" class="cur">全部</li>
					<li vname="0">登记回执</li>
					<li vname="1">全部公开</li>
					<li vname="2">部分公开</li>
					<li vname="3">不予公开</li>
					<li vname="4">非本单位信息</li>
					<li vname="5">信息不存在</li>
				</ul>
				<span class="blank3"></span>
			</td>
		</tr>
    </table>
    <span class="blank6"></span>
	<div id="list_table" name="list_table"></div>
	<span class="blank6"></span>
	
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn1" name="btn1" type="button" onclick="addPhrasalRecord()" value="添加" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(list_table,'gph_id','updatePhrasalRecord(gph_type)');" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="deletePhrasal(gph_type)" value="删除" />
			<input id="btn1" name="btn1" type="button" onclick="sortPhrasal(gph_type)" value="保存排序" />			
		</td>
	</tr>
   </table>	
</div>
</body>
</html>