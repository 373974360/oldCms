<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String handl_name = request.getParameter("handl_name");
	String app_id = request.getParameter("app_id");
	String class_type = request.getParameter("class_type");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择共享目录</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="../../styles/themes/icon.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript">

var json_data;
var handl_name = "<%=handl_name%>";

$(document).ready(function(){
	initButtomStyle();
	getCateClass();
	
	initButtomStyle();
	init_input();	
});

function getCateClass()
{
	var CategoryRPC = jsonrpc.CategoryRPC;
	var class_list;
	if(class_type == 1)
		class_list = CategoryRPC.getCateClassListByApp("<%=app_id%>");
	else
		class_list = CategoryRPC.getBasisCateClassList();

	class_list = List.toJSList(class_list);
	if(class_list != null && class_list.size() > 0)
	{
		getCategoryTree(class_list.get(0).class_id);
		$("#class_id").addOptions(class_list,"class_id","class_cname");
	}
}

function getCategoryTree(class_id)
{
	var CategoryRPC = jsonrpc.CategoryRPC;
	json_data = eval(CategoryRPC.getCategoryTreeByClassID(class_id));		
	setLeftMenuTreeJsonData(json_data);
}

function returnCatID()
{
	var node = $('#leftMenuTree').tree('getSelected');
	if(node == null)
	{
		top.msgWargin(WCMLang.selected_category);
		return;
	}else
	{			
		eval("top.getCurrentFrameObj()."+handl_name+"('"+node.id+"','"+node.text+"')");
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
		 <td>
		  <select id="class_id" class="width300" onchange="getCategoryTree(this.value)">
		  </select>
		  <span class="blank3"></span>
		 </td>
		</tr>
		<tr>			
			<td align="center" >
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft" style="height:360px;overflow:auto;border:1px solid #828790;background:#ffffff">
						<ul id="leftMenuTree" class="easyui-tree" animate="true" >
						</ul>
					</div>
				</div>
			</td>		
			<td></td>
		</tr>		
	</tbody>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="returnCatID()" value="保存" />
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>