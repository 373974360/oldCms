<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模块管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<link type="text/module" rel="stylesheet" href="/sys/styles/uploadify.module" />
<script language="javascript" src="/sys/js/jquery.uploadify.js"></script>
<script language="javascript" src="/sys/js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="/sys/js/uploadTools.js"></script>
<script type="text/javascript" src="js/public.js"></script>
<script type="text/javascript" src="js/module.js"></script>

<script type="text/javascript">
var site_id = "";
var module_id = "${param.module_id}";
var app_id = "${param.app_id}";
var top_index = ${param.top_index};

var defaultBean;
var current_obj;

$(document).ready(function(){
	initButtomStyle();	
	loadStyleList();
	publicUploadDesignThumbButtom("uploadify","savePicUrl");

	//得到属性设置列表
	getDesignAttrList();

	if(module_id != "" && module_id != "null" && module_id != null)
	{		
		defaultBean = DesignRPC.getDesignModuleBean(module_id);
		if(defaultBean)
		{
			$("#module_table").autoFill(defaultBean);
			$("#module_ename").attr("readOnly",true);
		
			if(defaultBean.style_ids != "" &&  defaultBean.style_ids != null)
			{
				var tempA = defaultBean.style_ids.split(",");
				for(var i=0;i<tempA.length;i++)
				{
					$("#style_id_checkbox[value='"+tempA[i]+"']").attr("checked",true);
				}
			}
			if(defaultBean.att_ids != "" &&  defaultBean.attr_ids != null)
			{
				var tempA = defaultBean.attr_ids.split(",");
				for(var i=0;i<tempA.length;i++)
				{
					$("#design_attr_checkbox[value='"+tempA[i]+"']").attr("checked",true);
				}
			}
		}

		$("#addButton").click(updateModule);
	}
	else
	{
		$("#addButton").click(addModule);
	}

	init_input();
});
function savePicUrl(url)
{
	$("#thumb_url").val(url);
}

function loadStyleList()
{
	var m = new Map();
	m.put("start_num", 0);	
	m.put("page_size", 1000);
	var styleList = DesignRPC.getDesignStyleList(m);
	styleList = List.toJSList(styleList);
	if(styleList != null && styleList.size() > 0)
	{
		for(var i=0;i<styleList.size();i++)
		{
			$("#style_list").append('<li style="float:none;height:20px"><input type="checkbox" id="style_id_checkbox" value="'+styleList.get(i).style_id+'"><label>'+styleList.get(i).style_name+'</label></li>');
		}
	}

}

function getDesignAttrList()
{
	for(var i=0;i<attr_box.length;i++)
	{

		$("#attr_list").append('<li style="float:none;height:20px"><input type="checkbox" id="design_attr_checkbox" value="'+attr_box[i][0]+'"><label>'+attr_box[i][1]+'</label></li>');
	}
	//attr_list
}

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="module_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>模块名称：</th>
			<td>
				<input id="module_name" name="module_name" type="text" class="width300" value="" onblur="checkInputValue('module_name',false,80,'目录名称','')"/>				
			</td>			
		</tr>		
		<tr>
			<th>权重：</th>
			<td>
				<input id="weight" name="weight" type="text" style="width:50px;" value="60" maxlength="2" onblur="checkInputValue('weight',true,2,'权重','checkNumber')"/>默认值：60，取值范围（0-99）
			</td>			
		</tr>
		<tr>
			<th>数据类型：</th>
			<td>
				<ul>
					<li><input type="radio" id="datasouce_type" name="datasouce_type" value="0" checked="true"><label>列表类型</label></li>
					<li><input type="radio" id="datasouce_type" name="datasouce_type" value="1"><label>文本类型</label></li>
				</ul>
			</td>			
		</tr>
		<tr>
			<th>缩略图：</th>
			<td>
				<div style="float:left;margin:auto;"><input id="thumb_url" name="thumb_url" type="text" class="width300" value="" /></div>
				<div style="float:left"><input type="file" name="uploadify" id="uploadify"/></div>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">代码：</th>
			<td>				
				<textarea id="module_content" name="module_content" style="width:620px;height:200px;"></textarea>	
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">替换代码：</th>
			<td>				
				<textarea id="v_code" name="v_code" style="width:620px;height:200px;"></textarea>	
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">内容样式：</th>
			<td>				
				<div style="width:500px;height:200px;overflow:auto;background:#FFFFFF;" class="border_color">
					<ul id="style_list" style="margin:10px">
					</ul>
				</div>	
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">属性选项：</th>
			<td>				
				<div style="width:500px;height:200px;overflow:auto;background:#FFFFFF;" class="border_color">
					<ul id="attr_list" style="margin:10px">
					</ul>
				</div>	
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('module_table',module_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex);" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
