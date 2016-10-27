<%@ page contentType="text/html; charset=utf-8"%>
<%
	String app_id = request.getParameter("app_id");
	String site_id = request.getParameter("site_id");
	String parent_id = request.getParameter("parent_id");
	String id = request.getParameter("id");
	String top_index_id = request.getParameter("cur_indexid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>页面维护</title>


<jsp:include page="../include/include_tools.jsp"/>
<script id="src-dict" type="text/javascript" src="../js/pinyin/pinyin.dict.src.js"></script>
<script type="text/javascript" src="../js/pinyin/pinyin.js"></script>
<script type="text/javascript" src="js/pageList.js"></script>
<script type="text/javascript">
var top_index_id = "<%=top_index_id%>";
var parent_id = "<%=parent_id%>";
var app_id = "<%=app_id%>";
var site_id = "<%=site_id%>";
if(site_id == null || site_id == "null")
	site_id = "";

var id = "<%=id%>";
if(id == null || id == "null")
	id = "";

var defaultBean;

$(document).ready(function(){	

	initButtomStyle();
	init_input();

	if(id != "" && id != "null" && id != null)
	{		
		defaultBean = PageRPC.getPageBean(id);
		if(defaultBean)
		{
			$("#page_table").autoFill(defaultBean);		
			$("#page_path").attr("readOnly","readOnly");
			initTemplateValue();
		}
		$("#addButton").click(updatePage);
	}
	else
	{
		$("#addButton").click(addPage);
	}

	$("#page_cname").blur(function(){		
		if($("#page_ename").val() == "")
		{
			$("#page_ename").val(pinyin(this.value, true, "").toLowerCase());
		}
	});
});

function initTemplateValue()
{
	$("#template_name").val(getTemplateName(defaultBean.template_id));
}


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="page_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>中文名称：</th>
			<td class="width250">
				<input id="page_cname" name="page_cname" type="text" class="width300" value="" onblur="checkInputValue('page_cname',false,80,'中文名称','')"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>英文名称：</th>
			<td class="width250">
				<input id="page_ename" name="page_ename" type="text" class="width300" value="" onblur="checkInputValue('page_ename',false,20,'英文名称','checkLetter')"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>关联模板：</th>
			<td class="width250">
				<input id="template_name" name="template_name" type="text" class="width200" readOnly="readOnly" onblur="checkInputValue('template_name',false,80,'关联模板','')"/><input type="hidden" id="template_id" value="0" class="width200"><input type="button" value="选择" onclick="openSelectTemplate('template_id','template_name',site_id)"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>存储路径：</th>
			<td class="width250">
				<input id="page_path" name="page_path" type="text" class="width300" value="/" onblur="checkInputValue('page_path',false,80,'存储路径','')"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>更新频率：</th>
			<td class="width250">
				<input id="page_interval" name="page_interval" type="text" class="width50" value="0" onblur="checkInputValue('page_interval',false,10,'更新频率','checkInt')"/>秒
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">页面描述：</th>
			<td colspan="3">
				<textarea id="page_memo" name="page_memo" style="width:300px;;height:50px;" onblur="checkInputValue('page_memo',true,900,'页面描述','')"></textarea>		
			</td>
		</tr>
	</tbody>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('page_table',id);initTemplateValue()" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex);" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
