<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>场景式服务主题修改</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/serList.js"></script>
<script type="text/javascript">
var top_index = "${param.top_index}"
var ser_id = "${param.ser_id}"
var app_id = "ggfw";
var site_id =  SiteRPC.getSiteIDByAppID(app_id);
//var real_site_id = SiteRPC.getSiteIDByAppID(app_id);
var SiteRPC = jsonrpc.SiteRPC;
$(document).ready(function(){	
	initButtomStyle();
	init_input();
	addDictList();

	if(ser_id != "" && ser_id != null && ser_id != "null")
	{
		defaultBean = SerRPC.getSerCategoryBean(ser_id);
		if(defaultBean)
		{
			$("#add_table").autoFill(defaultBean);
			if(defaultBean.res_flag == 1)
				$("#dict_tr").show();

			$("#template_index_name").val(getTemplateName(defaultBean.template_index));
			$("#template_list_name").val(getTemplateName(defaultBean.template_list));
			$("#template_content_name").val(getTemplateName(defaultBean.template_content));
		}
		$("#addButton").click(updateSerTopic);
	}
	else
	{
		$("#addButton").click(addSerTopic);
	}
});

function addDictList()
{
	var l = jsonrpc.DataDictRPC.getDictCategoryForSyS(0);
	l =List.toJSList(l);
	$("#dict_id").addOptions(l,"dict_cat_id","dict_cat_name");
}

function showDictList(obj)
{
	if($(obj).is(':checked'))
	{
		$("#dict_tr").show();
	}else{
		$("#dict_tr").hide();
		$("#dict_id option").eq(0).attr("selected","true");
	}
}
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="add_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>		
		<tr>
			<th><span class="f_red">*</span>主题名称：</th>
			<td >
				<input id="cat_name" name="cat_name" type="text" class="width300" value="" onblur="checkInputValue('cat_name',false,80,'主题名称','')"/>
			</td>			
		</tr>
		<tr>
			<th>主题描述：</th>
			<td >
				<textarea id="description" name="description" style="width:400px;;height:100px;" onblur="checkInputValue('description',true,900,'主题描述','')"></textarea>		
			</td>			
		</tr>
		<tr>
			<th>首页模板：</th>
			<td>				
				<input id="template_index_name" name="template_index_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_index" class="width200" value="0"/><input type="button" value="选择" onclick="openSelectTemplate('template_index','template_index_name',site_id)"/>
			</td>			
		</tr>
		<tr>
			<th>列表页模板：</th>
			<td>				
				<input id="template_list_name" name="template_list_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_list" class="width200" value="0"/><input type="button" value="选择" onclick="openSelectTemplate('template_list','template_list_name',site_id)"/>
			</td>			
		</tr>
		<tr>
			<th>内容页模板：</th>
			<td>				
				<input id="template_content_name" name="template_content_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_content" class="width200" value="0"/><input type="button" value="选择" onclick="openSelectTemplate('template_content','template_content_name',site_id)"/>
			</td>			
		</tr>
		<tr>		 
		  <th>启用资源分类：</th>
		  <td >
			<ul>
				<li><input type="checkbox" id="res_flag" name="res_flag" value="1" onclick="showDictList(this)"><label>是</label></li>				
			</ul>
		  </td>
		 </tr>
		 <tr id="dict_tr" class="hidden">		 
		  <th>资源分类：</th>
		  <td >
			<select id="dict_id" name="dict_id" class="width200">
			 <option value=""></option>
			</select>
		  </td>
		 </tr>
		 <tr>		 
		  <th>发布状态：</th>
		  <td >
			<ul>
				<li><input type="radio" id="publish_status" name="publish_status" value="1"><label>已发布</label></li>
				<li><input type="radio" id="publish_status" name="publish_status" value="0" checked="checked"><label>未发布</label></li>				
			</ul>
			<input type="hidden" id="publish_time" name="publish_time" value="">
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('add_table',ser_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex);" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
