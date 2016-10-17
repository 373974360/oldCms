<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主题风格管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
<script language="javascript" src="/sys/js/jquery.uploadify.js"></script>
<script language="javascript" src="/sys/js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="/sys/js/uploadTools.js"></script>
<script type="text/javascript" src="js/public.js"></script>
<script type="text/javascript" src="js/css.js"></script>

<script type="text/javascript">
var site_id = "";
var css_id = "${param.css_id}";
var app_id = "${param.app_id}";

var defaultBean;
var current_obj;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	
	publicUploadDesignThumbButtom("uploadify","savePicUrl");

	if(css_id != "" && css_id != "null" && css_id != null)
	{		
		defaultBean = DesignRPC.getDesignCssBean(css_id);
		if(defaultBean)
		{
			$("#css_table").autoFill(defaultBean);
			$("#css_ename").attr("readOnly",true);
		}

		$("#addButton").click(updateCss);
	}
	else
	{
		$("#addButton").click(addCss);
	}


});
function savePicUrl(url)
{
	$("#thumb_url").val(url);
}

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="css_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>风格名称：</th>
			<td>
				<input id="css_name" name="css_name" type="text" class="width300" value="" onblur="checkInputValue('css_name',false,80,'目录名称','')"/>				
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>英文名称：</th>
			<td>
				<input id="css_ename" name="css_ename" type="text" class="width300" value="" onblur="checkInputValue('css_ename',false,15,'英文名称','checkLower')"/>				
			</td>			
		</tr>
		<tr>
			<th>权重：</th>
			<td>
				<input id="weight" name="weight" type="text" style="width:50px;" value="60" maxlength="2" onblur="checkInputValue('weight',true,2,'权重','checkNumber')"/>默认值：60，取值范围（0-99）
			</td>			
		</tr>
		<tr>
			<th>缩略图：</th>
			<td>
				<div style="float:left;margin:auto;"><input id="thumb_url" name="thumb_url" type="text" style="width:250px;" value="" /></div>
				<div style="float:left"><input type="file" name="uploadify" id="uploadify"/></div>
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('css_table',css_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
