<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公务员名录配置</title>


<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript">
var MingLuRPC = jsonrpc.MingLuRPC;
var app_id = "cms";
var site_id = "${param.site_id}";
var defaultBean;
var MingLuBean = new Bean("com.deya.wcm.bean.minlu.MingLuBean",true);
$(document).ready(function(){	
	initButtomStyle();
	init_input();
	
	defaultBean = MingLuRPC.getMingLuBean(site_id);
	if(defaultBean != null)
	{
		$("#add_table").autoFill(defaultBean);	
		$("#addButton").click(updateMingLuTemplate);
		
		$("#template_index_name").val(getTemplateName(defaultBean.template_index));
		$("#template_list_name").val(getTemplateName(defaultBean.template_list));
		$("#template_content_name").val(getTemplateName(defaultBean.template_content));
		$("#reinfo_temp_list_name").val(getTemplateName(defaultBean.reinfo_temp_list));
		$("#reinfo_temp_content_name").val(getTemplateName(defaultBean.reinfo_temp_content));
		$("#reinfo_temp_pic_list_name").val(getTemplateName(defaultBean.reinfo_temp_pic_list));
		$("#reinfo_temp_pic_content_name").val(getTemplateName(defaultBean.reinfo_temp_pic_content));
	}else
	{
		$("#addButton").click(insertMingLuTemplate);
	}
});

function insertMingLuTemplate()
{
	var bean = BeanUtil.getCopy(MingLuBean);	
	$("#add_table").autoBind(bean);
	bean.app_id = app_id;
	bean.site_id = site_id;
	if(MingLuRPC.insertMingLuTemplate(bean))
	{
		top.msgAlert("公务员名录"+WCMLang.Set_success);		
	}else
		top.msgWargin("公务员名录"+WCMLang.Set_fail);
}

function updateMingLuTemplate()
{
	var bean = BeanUtil.getCopy(MingLuBean);	
	$("#add_table").autoBind(bean);
	bean.id = defaultBean.id;
	if(MingLuRPC.updateMingLuTemplate(bean))
	{
		top.msgAlert("公务员名录"+WCMLang.Set_success);		
	}else
		top.msgWargin("公务员名录"+WCMLang.Set_fail);
}

function openTemplate(n1,n2)
{
	openSelectTemplate(n1,n2,site_id);
}
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="add_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>		
		<tr>
			<th>公务员名录首页模板：</th>
			<td>				
				<input id="template_index_name" name="template_index_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_index" class="width200" value="0"/><input type="button" value="选择" onclick="openTemplate('template_index','template_index_name')"/>
			</td>			
		</tr>
		<tr>
			<th>公务员-列表模板：</th>
			<td>				
				<input id="template_list_name" name="reinfo_temp_pic_content_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_list" class="width200" value="0"/><input type="button" value="选择" onclick="openTemplate('template_list','template_list_name')"/>
			</td>			
		</tr>
		<tr>
			<th>公务员-内容页模板：</th>
			<td>				
				<input id="template_content_name" name="template_content_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_content" class="width200" value="0"/><input type="button" value="选择" onclick="openTemplate('template_content','template_content_name')"/>
			</td>			
		</tr>
		<tr>
			<th>相关信息-文章类列表模板：</th>
			<td>				
				<input id="reinfo_temp_list_name" name="reinfo_temp_list_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="reinfo_temp_list" class="width200" value="0"/><input type="button" value="选择" onclick="openTemplate('reinfo_temp_list','reinfo_temp_list_name')"/>
			</td>			
		</tr>
		<tr>
			<th>相关信息-文章类内容页模板：</th>
			<td>				
				<input id="reinfo_temp_content_name" name="reinfo_temp_content_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="reinfo_temp_content" class="width200" value="0"/><input type="button" value="选择" onclick="openTemplate('reinfo_temp_content','reinfo_temp_content_name')"/>
			</td>			
		</tr>
		<tr>
			<th>相关信息-组图类列表模板：</th>
			<td>				
				<input id="reinfo_temp_pic_list_name" name="reinfo_temp_pic_list_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="reinfo_temp_pic_list" class="width200" value="0"/><input type="button" value="选择" onclick="openTemplate('reinfo_temp_pic_list','reinfo_temp_pic_list_name')"/>
			</td>			
		</tr>
		<tr>
			<th>相关信息-组图类内容页模板：</th>
			<td>				
				<input id="reinfo_temp_pic_content_name" name="reinfo_temp_pic_content_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="reinfo_temp_pic_content" class="width200" value="0"/><input type="button" value="选择" onclick="openTemplate('reinfo_temp_pic_content','reinfo_temp_pic_content_name')"/>
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
			<input id="addButton" name="btn1" type="button" value="保存" />
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('add_table',site_id);reloadTemplate();" value="重置" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
