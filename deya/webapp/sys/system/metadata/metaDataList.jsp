<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>元数据管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/metaDataList.js"></script>
<script type="text/javascript">

var site_id = "";

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	reloadMetaDataList();	
});


</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>	
			<td align="left" valign="middle" class="fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="openAddMetaDataPage();" value="添加元数据" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'meta_id','openUpdateMetaDataPage()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'meta_id','deleteMetaData()');" value="删除" />
				<span class="blank3"></span>
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
				<select id="searchFields" class="input_select width70" >
					<option selected="selected" value="meta_cname">中文名称</option>
					<option selected="selected" value="meta_ename">英文名称</option>
					<option selected="selected" value="meta_sname">短名</option>
				</select>
				<input id="searchkey" type="text" class="input_text" value=""  /><input id="btnSearch" type="button" class="btn x2" value="搜索" onclick="metaDataSearchHandl(this)"/>	
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
			<input id="btn1" name="btn1" type="button" onclick="openAddMetaDataPage();" value="添加元数据" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'meta_id','openUpdateMetaDataPage()');" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="deleteSinglRecord(table,'meta_id','deleteMetaData()');" value="删除" />			
		</td>
	</tr>
   </table>	
</div>
</body>
</html>