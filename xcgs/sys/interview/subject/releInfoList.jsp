<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>相关信息管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script src="js/releInfoList.js"></script>	
<script type="text/javascript">
var div_height = 0;	
var sub_id = request.getParameter("sub_id");
var tn = request.getParameter("tn");
var subm_sta = request.getParameter("ss");

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	showTurnPage();
	showList();	
});


</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>	
			<td class="fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="addReleInfo()" value="添加" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'id','fnUpdateReleInfo()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'id','batchPublishSubReleInfo(1,\'发布\')')" value="发布" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'id','batchPublishSubReleInfo(-1,\'撤销\')')" value="撤销" />
				<input id="btn3" name="btn3" type="button" onclick="saveSort()" value="保存排序" />				
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','batchDelSubReleInfos()');" value="删除" />	
				<span class="blank3"></span>
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
				
			</td>		
		</tr>
	</table>
	<span class="blank3"></span>
	<div id="table"></div><!-- 列表DIV -->
	<div id="turn"></div><!-- 翻页DIV -->
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn1" name="btn1" type="button" onclick="addReleInfo()" value="添加" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'id','fnUpdateReleInfo()');" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'id','batchPublishSubReleInfo(1,\'发布\')')" value="发布" />
			<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'id','batchPublishSubReleInfo(-1,\'撤销\')')" value="撤销" />
			<input id="btn3" name="btn3" type="button" onclick="saveSort()" value="保存排序" />				
			<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','batchDelSubReleInfos()');" value="删除" />			
		</td>
	</tr>
   </table>	
</div>
</body>
</html>