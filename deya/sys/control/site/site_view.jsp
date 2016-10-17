<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>站群维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/siteList.js"></script>
<script type="text/javascript">

var parent_id = "${param.parentID}";
var site_id = "${param.site_id}"; 
var tab_index = "${param.tab_index}"; 
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	
	//初始化服务器下拉框的数据
	initServerList();
	
	if(site_id != "" && site_id != "null" && site_id != null)
	{	 
		defaultBean = SiteRPC.getSiteBeanBySiteID(site_id);
		//alert(defaultBean.site_id);
		//defaultBean.site_id = defaultBean.site_id.substring(5,defaultBean.site_id.length);
		if(defaultBean)
		{
			$("#site_table").autoFill(defaultBean);
			var deptBean = DeptRPC.getDeptBeanByID(defaultBean.dept_id);
			if(deptBean == null)
				$("#dept_id").val("");
			else
				$("#dept_id").val(deptBean.dept_name);
			
		}
		$("#addButton").click(updateSite);
		$("#site_id").attr("disabled","disabled");
	}
	else
	{
		$("#addButton").click(addSite);
	}
});


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="site_table" class="table_form" border="0" cellpadding="0" cellspacing="0">   
	<tbody>
		<tr>
			<th><span class="f_red">*</span>站点标识：</th>
			<td >
				<input id="site_id" name="site_id" type="text" class="width300" value="" disabled="disabled" onblur="checkInputValue('site_id',false,6,'站点标识','')"/>
				<input id="parent_id" name="parent_id" type="hidden" class="width300" value="${param.parentID}" />
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>服务器名称：</th>
			<td >
			    <select id="server_id" name="server_id" class="width305" disabled="disabled">
			    </select>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>所属机构：</th>
			<td >
				<input id="dept_id" name="dept_id" type="text" class="width300" value="" disabled="disabled" onblur="checkInputValue('dept_id',false,30,'所属机构','')"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>站点名称：</th>
			<td >
				<input id="site_name" name="site_name" type="text" class="width300" value="" disabled="disabled" onblur="checkInputValue('site_name',false,30,'站点名称','')"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>站点域名：</th>
			<td >
				<input id="site_domain" name="site_domain" type="text" class="width300" value="" disabled="disabled" onblur="checkInputValue('site_domain',false,30,'站点域名','')"/>
			</td>			
		</tr>
		<tr style="display:none">
			<th><span class="f_red">*</span>站点CDK：</th>
			<td >
				<input id="site_cdkey" name="site_cdkey" type="text" class="width300" value="" disabled="disabled" onblur=""/>
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">备注：</th>
			<td colspan="3">
				<textarea id="site_demo" name="site_demo" style="width:300px;;height:50px;" disabled="disabled" onblur="checkInputValue('site_demo',true,900,'备注','')"></textarea>		
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
			<input id="userAddCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex);" value="关闭" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
