<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>场景式服务主题维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/serCateList.js"></script>
<script type="text/javascript">
var top_index = "${param.top_index}";
var ser_id = "${param.s_id}";
var parent_id = "${param.parent_id}"
var app_id = "ggfw";
//var site_id =  SiteRPC.getSiteIDByAppID(app_id);
//var real_site_id = SiteRPC.getSiteIDByAppID(app_id);
var SiteRPC = jsonrpc.SiteRPC;
$(document).ready(function(){	
	initButtomStyle();
	init_input();

	if(ser_id != "" && ser_id != null && ser_id != "null")
	{
		defaultBean = SerRPC.getSerCategoryBean(ser_id);
		if(defaultBean)
		{
			$("#add_table").autoFill(defaultBean);
			if(defaultBean.cat_type == "leaf")
				$("#url_tr").show();
		}
		$("#addButton").click(updateSerCate);
	}
	else
	{
		$("#addButton").click(addSerCate);
	}
});

function showURLTr(obj)
{
	if($(obj).is(':checked'))
	{
		$("#url_tr").show();
	}else{
		$("#url_tr").hide();
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
			<th><span class="f_red">*</span>导航名称：</th>
			<td >
				<input id="cat_name" name="cat_name" type="text" class="width300" value="" onblur="checkInputValue('site_name',false,80,'导航名称','')"/>
			</td>			
		</tr>
		<tr>		 
		  <th>节点类型：</th>
		  <td >
			<ul>
				<li><input type="checkbox" id="cat_type" name="cat_type" value="leaf" onclick="showURLTr(this)"><label>未级节点</label></li>							
			</ul>			
		  </td>
		 </tr>
		<tr>
			<th>本级描述：</th>
			<td >
				<textarea id="description" name="description" style="width:400px;;height:100px;" onblur="checkInputValue('description',true,900,'本级描述','')"></textarea>		
			</td>			
		</tr>
		<tr>
			<th>下一级描述：</th>
			<td >
				<input id="next_desc" name="next_desc" type="text" class="width300" value="" onblur="checkInputValue('site_name',true,80,'下一级描述','')"/>
			</td>			
		</tr>
		<tr id="url_tr" class="hidden">
			<th>链接地址：</th>
			<td>				
				<input id="url" name="url" type="text" class="width300" value="" onblur="checkInputValue('url',true,80,'链接地址','')"/><input type="button" value="选择" onclick="selectInfoUrl()"/>
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
