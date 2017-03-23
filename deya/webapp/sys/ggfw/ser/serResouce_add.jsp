<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>服务资源维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/serCateList.js"></script>
<script type="text/javascript" src="js/serResouceList.js"></script>
<script type="text/javascript">
var top_index = "${param.top_index}";
var ser_id = "${param.ser_id}";
var res_id = "${param.res_id}"
var app_id = "cms";
var site_id = "${param.site_id}";
var contentId = "content";
//var real_site_id = SiteRPC.getSiteIDByAppID(app_id);
var SiteRPC = jsonrpc.SiteRPC;
$(document).ready(function(){	
	initButtomStyle();
	init_input();
    initUeditor(contentId);
	
	//根据ser_id的根节点判断是否需要资源分类
	isResouceType();

	if(res_id != "" && res_id != null && res_id != "null")
	{
		defaultBean = SerRPC.getSerResouceBean(res_id);
		if(defaultBean)
		{
			$("#add_table").autoFill(defaultBean);
            setV(contentId,defaultBean.content);
		}
		$("#addButton").click(updateSerResouce);
	}
	else
	{
		$("#addButton").click(addSerResouce);
	}
});

function isResouceType()
{
	var dict_list = SerRPC.getDataDictList(ser_id);//数据字典对应的列表,如果为空就不需要分类
	
	if(dict_list != null)
	{
		dict_list =List.toJSList(dict_list);
		if(dict_list.size() > 0)
		{			
			$("#res_status").empty();
			$("#dict_tr").show();
			$("#res_status").addOptions(dict_list,"dict_id","dict_name");
		}
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
			<th><span class="f_red">*</span>标题：</th>
			<td >
				<input id="title" name="title" type="text" class="width300" value="" onblur="checkInputValue('title',false,80,'标题','')"/>
			</td>			
		</tr>
		<tr id="dict_tr" class="hidden">		 
		  <th>资源分类：</th>
		  <td >
			<select id="res_status" name="res_status" class="width200">
			 <option value="0"></option>
			</select>
		  </td>
		 </tr>
		 <tr id="url_tr">
			<th>链接地址：</th>
			<td>				
				<input id="url" name="url" type="text" class="width300" value="" onblur="checkInputValue('url',true,80,'链接地址','')"/><input type="button" value="选择" onclick="selectInfoUrl()"/>
			</td>			
		</tr>
		<tr>
			<th>内容：</th>
			<td >
                <script id="content" type="text/plain" style="width:700px;height:300px;"></script>
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
