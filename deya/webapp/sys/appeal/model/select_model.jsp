<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String handl_name = request.getParameter("handl_name");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择业务</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="../../styles/themes/icon.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript">

var json_data;
var handl_name = "<%=handl_name%>";

$(document).ready(function(){
	initButtomStyle();
	getModelList();
	getSelectedModelID();
});

function getModelList()
{
	var SQModelRPC = jsonrpc.SQModelRPC;
	var model_list = SQModelRPC.getAllSQModelList();
	model_list = List.toJSList(model_list);
	
	if(model_list != null && model_list.size() > 0)
	{
		for(var i=0;i<model_list.size();i++)
		{	
			$("#model_list").append('<li style="float:none;height:20px"><input type="checkbox" id="model_id" value="'+model_list.get(i).model_id+'"><label>'+model_list.get(i).model_cname+'</label></li>');
		}
	}
	init_input();
}

function getSelectedModelID()
{
	var model_ids = top.getCurrentFrameObj().getSelectedModelID();
	
	if(model_ids != "" && model_ids != null)
	{
		var tempA = model_ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			$(":checkbox[value="+tempA[i]+"]").attr("checked",true);
		}
	}
}

function returnModelID()
{
	var model_ids = "";	
	$(":checkbox[checked]").each(function(){
		model_ids += ","+$(this).val();
	});
	if(model_ids != "")
		model_ids = model_ids.substring(1);

	eval("top.getCurrentFrameObj()."+handl_name+"('"+model_ids+"')");
	top.CloseModalWindow();
}

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:94%">
	<tbody>
		<tr>			
			<td align="center" >
				<div style="width:100%;height:395px;overflow:auto;background:#FFFFFF;">
					<ul id="model_list" style="margin:10px">
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
			<input id="addButton" name="btn1" type="button" onclick="returnModelID()" value="保存" />
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>