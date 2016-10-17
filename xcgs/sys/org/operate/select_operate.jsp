<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String handl_name = request.getParameter("handl_name");	
	String update_status = request.getParameter("update_status");//是否允许修改的标识状态，为false时不允许修改，如站群中共享的角色在站点中不允许修改权限
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择权限</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="../../styles/themes/icon.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/operateList.js"></script>
<script type="text/javascript">

var json_data;
var handl_name = "<%=handl_name%>";
var update_status = "<%=update_status%>";

$(document).ready(function(){
	initButtomStyle();
	var app_ids = top.getCurrentFrameObj().app_id;
	if(app_ids == "control")
		app_ids += ",cms";
	json_data = eval(OperateRPC.getOperateTreeJsonStr(app_ids));
	initMenuTree();
	setLeftMenuTreeJsonData(json_data);
	setSelectedRoleIDS();

	if(update_status == "false")
	{
		$("#addButton").hide();
		$("#userAddCancel").val("关闭");
	}
});

function initMenuTree()
{
	$('#leftMenuTree').tree({	
		checkbox: true		
	});
}

//调用父窗口方法,取到已选择过的权限ID,并让树节点选中
function setSelectedRoleIDS()
{
	var opt_ids = top.getCurrentFrameObj().getSelectedRoleIDS();
	if(opt_ids != "" && opt_ids != null)
	{
		var tempA = opt_ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			$("div[node-id='"+tempA[i]+"'] .tree-checkbox").removeClass("tree-checkbox0").addClass("tree-checkbox1");			
		}		
	}
}

function returnDeptID()
{				
	eval("top.getCurrentFrameObj()."+handl_name+"('"+getLeftMenuChecked()+"')");
	top.CloseModalWindow();	
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