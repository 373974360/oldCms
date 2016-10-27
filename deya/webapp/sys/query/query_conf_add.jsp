<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8"/>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<title>自定义查询</title>
<meta name="generator" content="cicro-Builder"/>
<meta name="author" content="cicro"/>
<link type="text/css" rel="stylesheet" href="../../styles/sq.css"/>
<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript" src="js/queryConf.js"></script>
<script type="text/javascript">
var conf_id = "<%=request.getParameter("conf_id")%>";
var site_id ="<%=request.getParameter("site_id")%>";
var flag = "";
$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(conf_id != "" && conf_id != "null" && conf_id != null)
	{		
		defaultBean = QueryConfRPC.getQueryConfBean(conf_id);
		if(defaultBean)
		{
			$("#QueryConf_table").autoFill(defaultBean);
			$("#t_list_name").val(getTemplateName(defaultBean.t_list_id));
			$("#t_content_name").val(getTemplateName(defaultBean.t_content_id));
		}			
		flag="update";
	}
	else
	{
		initData();
		flag="add";
	}
});

//初始加载数据
function initData()
{
	defaultBean = BeanUtil.getCopy(QueryConfBean);
	defaultBean.t_list_id ="";
	defaultBean.t_content_id ="";
	$("#QueryConf_table").autoFill(defaultBean);
}
function SelectTemplate(template_id,template_name,site_id)
{
	temp_template_id = template_id;	
	temp_template_name = template_name;
	top.OpenModalWindow("选择模板","/sys/system/template/select_template.jsp?site_id="+site_id+"&handl_name=setSelectTemplate",520,500);
}
function saveOrUpdate()
{
	if(flag =="add")
	{
		addQueryConf(site_id);
	}else{
		updateQueryConf(conf_id);
	}
}
</script>
</head>
<body>
<span class="blank6"></span>
<span class="blank6"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="QueryConf_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>业务名称：</th>
			<td >
				<input id="conf_title" name="conf_title" type="text" class="width300" value="" onblur="checkInputValue('conf_title',false,60,'业务名称','')"/>
			</td>
		</tr>
	</tbody>
	<tbody>
		<tr>
			<th><span class="f_red">*</span>列表页模板：</th>
			<td>
				<input type="text" id="t_list_name" class="width200" readOnly="readOnly">
				<input type="hidden" id="t_list_id" class="width200"/>
				<input type="button" value="选择" onclick="SelectTemplate('t_list_id','t_list_name',site_id)"/>
			</td>
		</tr>
	</tbody>
	<tbody>
		<tr>
			<th><span class="f_red">*</span>内容页模板：</th>
			<td>
				<input type="text" id="t_content_name" class="width200" readOnly="readOnly">
				<input type="hidden" id="t_content_id" class="width200"/>
				<input type="button" value="选择" onclick="SelectTemplate('t_content_id','t_content_name',site_id)"/>
			</td>
		</tr>
	</tbody>
	<tbody>
		<tr>
			<th>业务描述：</th>
			<td>
				<textarea id="conf_description" name="conf_description" style="width:400px;height:80px">
				</textarea>
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
			<input id="saveBtn" name="saveBtn" type="button" onclick="saveOrUpdate()" value="保存"/>
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('QueryConf_table',conf_id);initData()" value="重置"/>
			<input id="btn2" name="btn2" type="button" onclick="window.history.go(-1)" value="取消"/>
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
<span class="blank12"></span>
</body>
</html>
