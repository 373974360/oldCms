<%@ page contentType="text/html; charset=utf-8"%>
<%
	String ph_type = request.getParameter("ph_type");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>常用语管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/commonLangList.js"></script>
<script type="text/javascript">

var ph_type = "<%=ph_type%>";
$(document).ready(function(){
	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	initTable();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initPage();
	reloadCommonLangList();
	
});

function initPage()
{
	if(ph_type== null || ph_type == "" || ph_type == "null")
	{
		ph_type = "all";
	}
	
	$(".customLable li").click(function(){
		$(".customLable li").removeClass("cur");
		$(this).addClass("cur");
		getDataList($(this).attr("vname"));
		});
		
	eval('$(".customLable li[vname=\''+ph_type+'\']").click()');
}

function getDataList(vname)
{
	ph_type = vname;
	reloadCommonLangList();
	//alert("ph_type = "+vname);
}
</script>
<style>
.customLable{}
.customLable li{float:left; padding-right:12px; color:#333; cursor:pointer; text-decoration:underline;}
.customLable li.cur{color:#F00;}
</style>
</head>

<body>

<table class="table_option fromTabs" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="50" align="right">分类：</td>
		<td align="left" valign="middle">
			<ul class="customLable">
				<li vname="all" class="cur">全部</li>
				<li vname="0">受理</li>
				<li vname="1">回复</li>
				<li vname="2">转办</li>
				<li vname="3">交办</li>
				<li vname="4">呈办</li>
				<li vname="5">重复件</li>
				<li vname="7">不予受理</li>
				<li vname="8">申请延时</li>
				<li vname="9">延时通过</li>
				<li vname="10">延时打回</li>
				<li vname="11">审核通过</li>
				<li vname="12">审核打回</li>
				<li vname="13">督办</li>
			</ul>
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
			<input id="btn1" name="btn1" type="button" onclick="addCommonLang()" value="添加" />
			<input id="btn2" name="btn2" type="button" onclick="updateRecord(table,'ph_id','updateCommonLang()')" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'ph_id','deleteCommonLang()')" value="删除" />
			<input id="btn6" name="btn6" type="button" onclick="saveSort()" value="保存排序" />
		</td>
	</tr>
</table>

</body>
</html>

