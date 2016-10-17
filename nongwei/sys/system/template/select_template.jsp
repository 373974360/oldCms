<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String handl_name = request.getParameter("handl_name");	
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择模板</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="../../styles/themes/icon.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript">
var site_id = "<%=site_id%>";
var json_data;
var handl_name = "<%=handl_name%>";
var TemplateRPC = jsonrpc.TemplateRPC;
$(document).ready(function(){
	initButtomStyle();
	
	json_data = eval(TemplateRPC.templateCategoryListToJsonStr("0",site_id,""));
	setLeftMenuTreeJsonData(json_data);	
	initMenuTree();
});

function initMenuTree()
{
	$('#leftMenuTree').tree({
		onClick:function(node){
			setTemplateList(node.id);
		}
	});
}

var m = new Map();
m.put("site_id", site_id);
m.put("start_num", 0);	
m.put("page_size", 999);
m.put("sort_name", "t_id");
m.put("sort_type", "desc");
function setTemplateList(c_id)
{
	m.put("tcat_id", c_id);
	var beanList = TemplateRPC.getTemplateEditList(m);
	beanList = List.toJSList(beanList);
	$("#template_list").empty();
	if(beanList != null && beanList.size() > 0)
	{
		for(var i=0;i<beanList.size();i++)
		{
			$("#template_list").append('<li style="float:none;height:20px"><input type="radio" id="template_id" name="template_id" value="'+beanList.get(i).t_id+'" ><label>'+beanList.get(i).t_cname+'</label></li>');
		}
	}
	init_input();
}

function returnDeptID()
{				
	var t_id = $(":checked").val();
	var t_name = $(":checked").next().text();
	
	if(t_id == null || t_id == "")
	{
		top.msgWargin(WCMLang.selected_template);
		return;
	}else
	{			
		eval("top.getCurrentFrameObj()."+handl_name+"('"+t_id+"','"+t_name+"')");
		top.CloseModalWindow();
	}	
}
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="table" class="" border="0" cellpadding="0" cellspacing="0" style="width:94%">
	<tbody>
		<tr>			
			<td class="width250" align="center" >
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft" style="height:388px;overflow:auto;border:1px solid #828790;background:#ffffff">
						<ul id="leftMenuTree" class="easyui-tree" animate="true" >
						</ul>
					</div>
				</div>
			</td>		
			<td class="width10"></td>
			<td class="width250" align="top" >
				<div style="width:250px;height:400px;overflow:auto;" class="border_color">
					<ul id="template_list" style="margin:10px">
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
			<input id="addButton" name="btn1" type="button" onclick="returnDeptID()" value="保存" />
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>