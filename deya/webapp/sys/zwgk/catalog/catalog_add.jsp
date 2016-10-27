<%@ page contentType="text/html; charset=utf-8"%>
<%
	String parent_id = request.getParameter("parentID");
	String cata_id = request.getParameter("cata_id");
	String top_index = request.getParameter("top_index");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>目录维护</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/sys/js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/catalogList.js"></script>
<script type="text/javascript">
var top_index = "<%=top_index%>";
var parent_id = "<%=parent_id%>";
var cata_id = "<%=cata_id%>";
var app_id = "zwgk";
var defaultBean;

$(document).ready(function(){

	initButtomStyle();
	init_input();

	if(cata_id != "" && cata_id != "null" && cata_id != null)
	{		
		defaultBean = AppCatalogRPC.getAppCatalogBean(cata_id);
		if(defaultBean)
		{
			$("#catalog_table").autoFill(defaultBean);
			$("#template_index_name").val(getTemplateNameByReleApp(defaultBean.template_index));
			$("#template_list_name").val(getTemplateNameByReleApp(defaultBean.template_list));		
		}
		$("#addButton").click(updateCatalog);
	}
	else
	{
		if(parent_id != 0)
		{//有些数据需从父级继承过来
			defaultBean = AppCatalogRPC.getAppCatalogBean(parent_id);	
			defaultBean.cata_cname = "";
			defaultBean.is_mutilpage = 0;
			defaultBean.jump_url = "";
			$("#catalog_table").autoFill(defaultBean);
			$("#template_index_name").val(getTemplateNameByReleApp(defaultBean.template_index));
			$("#template_list_name").val(getTemplateNameByReleApp(defaultBean.template_list));
		}
		$("#addButton").click(addCatalog);
	}	
});

function openTemplate(n1,n2)
{		
	openSelectTemplate(n1,n2,jsonrpc.SiteRPC.getSiteIDByAppID(app_id));
}
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="catalog_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>目录名称：</th>
			<td>
				<input id="cata_cname" name="cata_cname" type="text" class="width300" value="" onblur="checkInputValue('cata_cname',false,80,'目录名称','')"/>				
			</td>			
		</tr>		
		<tr class="cms_page_templage">
			<th>栏目首页模板：</th>
			<td>				
				<input id="template_index_name" name="template_index_name" type="text" class="width200" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_index" class="width200" value="0"/><input type="button" value="选择" onclick="openTemplate('template_index','template_index_name')"/>
			</td>			
		</tr>
		<tr>
			<th>列表页模板：</th>
			<td>				
				<input id="template_list_name" name="template_list_name" type="text" class="width200" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_list" class="width200" value="0"/><input type="button" value="选择" onclick="openTemplate('template_list','template_list_name')"/>
			</td>			
		</tr>
		<tr>
			<th>单页信息目录：</th>
			<td>
				<input id="is_mutilpage" name="is_mutilpage" type="radio" value="1" /><label>是</label>
				<input id="is_mutilpage" name="is_mutilpage" type="radio" value="0" checked="true"/><label>否</label>
			</td>			
		</tr>
		<tr>
			<th>目录跳转地址：</th>
			<td>
				<input id="jump_url" name="jump_url" type="text" class="width300" onblur="checkInputValue('jump_url',true,200,'类目编号','')"/>
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">关键词：</th>
			<td colspan="3">
				<textarea id="cat_keywords" name="cat_keywords" style="width:585px;;height:50px;" onblur="checkInputValue('cat_keywords',true,1000,'关键词','')"></textarea>		
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">描述：</th>
			<td colspan="3">
				<textarea id="cat_description" name="cat_description" style="width:585px;;height:50px;" onblur="checkInputValue('cat_description',true,1000,'描述','')"></textarea>		
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">备注：</th>
			<td colspan="3">
				<textarea id="cat_memo" name="cat_memo" style="width:585px;;height:50px;" onblur="checkInputValue('cat_memo',true,1000,'备注','')"></textarea>		
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('catalog_table',id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex);" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
