<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String handl_name = request.getParameter("handl_name");	
	String show_tree_type = request.getParameter("stt");	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择公开节点分类</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="../../styles/themes/icon.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>

<script type="text/javascript">

var json_data;
var handl_name = "<%=handl_name%>";
var show_tree_type = "<%=show_tree_type%>";
$(document).ready(function(){
	initButtomStyle();
	if(show_tree_type == "root")
	{		
		json_data = eval(jsonrpc.GKNodeRPC.getGKNodeCategroyJSONROOTTreeStr());
	}
	else
		json_data = eval(jsonrpc.GKNodeRPC.getGKNodeCategroyJSONTreeStr());
	
	setLeftMenuTreeJsonData(json_data);
	getSelectedIDS();
});

//得到父窗口中已选过的节点，然后隐藏
function getSelectedIDS()
{
	if(show_tree_type == "root")
	{
		var ids = ","+top.getCurrentFrameObj().table.getSelecteCheckboxValue("nodcat_id")+",";
		$("#leftMenuTree div[node-id]").each(function(){
			if(ids.indexOf(","+$(this).attr("node-id")+",") > -1)
				$(this).parent().remove();
		})
	}
}

function returnDeptID()
{				
	var node = $('#leftMenuTree').tree('getSelected');
	if(node == null)
	{
		top.msgWargin(WCMLang.selected_dept);
		return;
	}else
	{			
		eval("top.getCurrentFrameObj()."+handl_name+"('"+node.id+"')");
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
			<td align="center" >
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft" style="height:390px;overflow:auto;border:1px solid #828790;background:#ffffff">
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
			<input id="addButton" name="btn1" type="button" onclick="returnDeptID()" value="保存" />
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>