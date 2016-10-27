<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>维护配置</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/siteConfigList.js"></script>
<script type="text/javascript">

var config_id = "${param.config_id}"; 
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(config_id != "" && config_id != "null" && config_id != null)
	{	 
		defaultBean = SiteConfigRPC.getConfigByConfigID(config_id);
		//alert(defaultBean.config_id);
		if(defaultBean)
		{ 
			$("#config_table").autoFill(defaultBean);					
		} 
		$("#addButton").click(funUpdate);
	}
	else
	{
		$("#addButton").click(funAdd);
	}
});


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="config_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>属性名：</th>
			<td class="width250">
				<input id="config_key" name="config_key" type="text" class="width300" value="" onblur="checkInputValue('config_key',false,100,'属性名','')"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>属性值：</th>
			<td class="width250">
				<input id="config_value" name="config_value" type="text" class="width300" value="" onblur="checkInputValue('config_value',false,200,'属性值','')"/>
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('server_table',server_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
