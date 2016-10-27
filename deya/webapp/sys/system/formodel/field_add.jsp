<%@ page contentType="text/html; charset=utf-8"%>
<%
	String field_id = request.getParameter("field_id");
	String model_id = request.getParameter("model_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>内容模型字段</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/fieldList.js"></script>
<script type="text/javascript">

var field_id = "<%=field_id%>";
var model_id = "<%=model_id%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	getMetaData();

	if(field_id != "" && field_id != "null" && field_id != null)
	{		
		defaultBean = ModelRPC.getModelFieldBean(field_id);
		if(defaultBean)
		{
			$("#field_table").autoFill(defaultBean);					
		}
		$("#field_ename").attr("disabled",true);
		$("#addButton").click(updateModelField);
	}
	else
	{
		$("#addButton").click(addModelField);
	}
});

function getMetaData()
{	
	var values = meta_map.values();
	for(var i=0;i<values.length;i++){
		$("#meta_id").addOptionsSingl(values[i].meta_id,values[i].meta_cname);
	}
}


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="field_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>		
		<tr>
			<th><span class="f_red">*</span>中文名称：</th>
			<td >
				<input id="field_cname" name="field_cname" type="text" class="width300" value="" onblur="checkInputValue('field_cname',false,240,'中文名称','')"/>
				<input type="hidden" name="field_id" id="field_id" value="0">
			</td>			
		</tr>	
		<tr>
			<th><span class="f_red">*</span>英文名称：</th>
			<td >
				<input id="field_ename" name="field_ename" type="text" class="width300" value="" onblur="checkInputValue('field_ename',false,20,'英文名称','checkLetter')"/>
			</td>			
		</tr>
		<tr>
			<th>对应元数据字段：</th>
			<td >
				<select id="meta_id" name="meta_id" class="width305">
					<option value="0"></option>
				</select>
			</td>			
		</tr>
		<tr>
			<th>是否展示：</th>
			<td >
				<input id="is_show" name="is_show" type="radio" value="0" checked="true"/><label>展示</label>
				<input id="is_show" name="is_show" type="radio" value="1" /><label>不展示</label>
			</td>			
		</tr>		
		<tr>
			<th style="vertical-align:top;">字段描述：</th>
			<td colspan="3">
				<textarea id="field_memo" name="field_memo" style="width:300px;;height:60px;" onblur="checkInputValue('field_memo',true,1000,'模板描述','')"></textarea>		
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('field_table',field_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
