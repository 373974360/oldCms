<%@ page contentType="text/html; charset=utf-8"%>
<%
	String model_id = request.getParameter("model_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>元数据维护</title>   


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/modelList.js"></script>
<script type="text/javascript">

var model_id = "<%=model_id%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(model_id != "" && model_id != "null" && model_id != null)
	{		
		defaultBean = ModelRPC.getModelBean(model_id);
		if(defaultBean)
		{
			$("#model_table").autoFill(defaultBean);	
			$("#table_name").attr("readonly","readonly");	
			$("#span_txt").html("表名不可修改");		
		}
		$("#addButton").click(updateModel);
	}
	else
	{
		$("#addButton").click(addModel);
	}
});


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="model_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>		
		<tr>
			<th><span class="f_red">*</span>中文名称：</th>
			<td >
				<input id="model_name" name="model_name" type="text" class="width300" value="" onblur="checkInputValue('model_name',false,240,'中文名称','')"/>
				<input type="hidden" name="model_id" id="model_id" value="0">
			</td>			
		</tr>	
		<tr>
			<th><span class="f_red">*</span>英文名称：</th>
			<td >
				<input id="model_ename" name="model_ename" type="text" class="width300" value="" onblur="checkInputValue('model_ename',false,80,'英文名称','checkLowerLetter')"/><span id="span_txt">注：英文名称只能是小写字母</span>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>对应表名：</th>
			<td >
				<input id="table_name" name="table_name" type="text"  class="width300" value="" onblur="checkInputValue('table_name',false,80,'对应表名','checkLowerLetter')"/><span id="span_txt">注：对应表名只能是小写字母</span>
			</td>			
		</tr>
		<tr class="hidden">
			<th>默认列表页模板：</th>
			<td >
				<input id="template_list" name="template_list" type="text" class="width300" value="" onblur="checkInputValue('template_list',true,240,'列表页模板','')"/>
			</td>			
		</tr>
		<tr class="hidden">
			<th>默认内容页模板：</th>
			<td >
				<input id="template_show" name="template_show" type="text" class="width300" value="" onblur="checkInputValue('template_show',true,240,'列表页模板','')"/>
			</td>			
		</tr>
		<tr class="hidden">
			<th>标识图例：</th>
			<td >
				<input id="model_icon" name="model_icon" type="text" class="width300" value="ico_custom" onblur="checkInputValue('model_icon',true,240,'标识图例','')"/>
			</td>			
		</tr>
		<tr class="hidden">
			<th>添加页：</th>
			<td >
				<input id="add_page" name="add_page" type="text" class="width300" value="m_article_custom.jsp" onblur="checkInputValue('add_page',true,240,'添加页','')"/>
			</td>			
		</tr>
		<tr class="hidden">
			<th>预览页：</th>
			<td >
				<input id="view_page" name="view_page" type="text" class="width300" value="" onblur="checkInputValue('view_page',true,240,'预览页','')"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>所属应用：</th>
			<td>
				<!-- 
				<input id="app_id" name="app_id" type="text" class="width300" value="" onblur="checkInputValue('app_id',false,240,'所属应用','')"/>
			    -->
			    <select id="app_id" name="app_id">
			       <option value="cms">内容管理</option>
			       <option value="zwgk">信息公开</option>
			    </select>
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">模板描述：</th>
			<td colspan="3">
				<textarea id="model_memo" name="model_memo" style="width:585px;;height:100px;" onblur="checkInputValue('model_memo',true,1000,'模板描述','')"></textarea>		
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">模型状态：</th>
			<td colspan="3">
				<input id="disabled" name="disabled" type="radio" value="0" checked="true"/><label>启用</label>
				<input id="disabled" name="disabled" type="radio" value="1"/><label>停用</label>
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('metadata_table',model_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="window.history.go(-1);" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
