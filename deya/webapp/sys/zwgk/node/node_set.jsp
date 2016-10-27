<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公开系统配置</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/gkNodeList.js"></script>
<script type="text/javascript">
var app_id = "zwgk";
var node_id = "GK";
var site_id = "";
var SiteRPC = jsonrpc.SiteRPC;
$(document).ready(function(){	
	initButtomStyle();
	init_input();
	initZWGKConfig();
	//$("#rela_site_id").val(SiteRPC.getSiteIDByAppID(app_id));
	//$("#rela_site_name").val(SiteRPC.getSiteBeanBySiteID(SiteRPC.getSiteIDByAppID(app_id)).site_name);

	var defaultBean = SiteRPC.getSiteAppBean(app_id);
	if(defaultBean != null)
	{		
		site_id = defaultBean.site_id;
		$("#rela_site_id").val(defaultBean.site_id);
		$("#rela_site_name").val(SiteRPC.getSiteBeanBySiteID(defaultBean.site_id).site_name);
	}
});

function openTemplate2(n1,n2)
{		
	openSelectTemplate(n1,n2,$("#rela_site_id").val());
}
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="add_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>		
		<tr>
			<th><span class="f_red">*</span>关联站点：</th>
			<td >
				<input id="rela_site_name" name="rela_site_name" 
					type="text" class="width300" value="" onblur="checkInputValue('rela_site_name',false,80,'关联站点','')" readOnly="readOnly"/>
				<input id="rela_site_id" name="rela_site_id" type="hidden" value=""/>
				<input id="bution4" name="bution4" type="button" onclick="openSelectSingleSite('选择关联站点','getRelaSite')" value="选择" />
			</td>			
		</tr>
		<tr>
			<th>公开指引列表模板：</th>
			<td>				
				<input id="gkzy_list_template_name" name="gkzy_list_template_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="gkzy_list_template_id" class="width200" value="0"/><input type="button" value="选择" onclick="openTemplate2('gkzy_list_template_id','gkzy_list_template_name')"/>
			</td>			
		</tr>
		<tr>
			<th>公开指引内容模板：</th>
			<td>				
				<input id="gkzy_template_name" name="gkzy_template_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="gkzy_template_id" class="width200" value=""/><input type="button" value="选择" onclick="openTemplate2('gkzy_template_id','gkzy_template_name')"/>
			</td>			
		</tr>
		<tr>
			<th>公开指南列表模板：</th>
			<td>				
				<input id="gkzn_list_template_name" name="gkzn_list_template_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="gkzn_list_template_id" class="width200" value="0"/><input type="button" value="选择" onclick="openTemplate2('gkzn_list_template_id','gkzn_list_template_name')"/>
			</td>			
		</tr>
		<tr>
			<th>公开指南内容模板：</th>
			<td>				
				<input id="gkzn_template_name" name="gkzn_template_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="gkzn_template_id" class="width200" value=""/><input type="button" value="选择" onclick="openTemplate2('gkzn_template_id','gkzn_template_name')"/>
			</td>			
		</tr>
		<tr>
			<th>公开年报列表模板：</th>
			<td>				
				<input id="gknb_list_template_name" name="gknb_list_template_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="gknb_list_template_id" class="width200" value="0"/><input type="button" value="选择" onclick="openTemplate2('gknb_list_template_id','gknb_list_template_name')"/>
			</td>			
		</tr>
		<tr>
			<th>公开年报内容模板：</th>
			<td>				
				<input id="gknb_template_name" name="gknb_template_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="gknb_template_id" class="width200" value=""/><input type="button" value="选择" onclick="openTemplate2('gknb_template_id','gknb_template_name')"/>
			</td>			
		</tr>
		<tr>
			<th>基础目录列表页模板：</th>
			<td>				
				<input id="template_list_name" name="template_list_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_list" class="width200" value=""/><input type="button" value="选择" onclick="openTemplate2('template_list','template_list_name')"/>
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
			<input id="addButton" name="btn1" type="button" onclick="updateZWGKConfig()" value="保存" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
