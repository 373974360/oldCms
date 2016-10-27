<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的权限</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="../../styles/themes/icon.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/operateList.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	initButtomStyle();
	
	var json_data = eval(jsonrpc.UserManRPC.getAppJSONStrByUserID(top.LoginUserBean.user_id));
	initMenuTree();
	setLeftMenuTreeJsonData(json_data);
	initOperateTree();	
});

function initMenuTree()
{
	$('#leftMenuTree').tree({
		onClick:function(node){
				showOperateTree(node.attributes.state,node.attributes.real_id);
		}
	});	
}

//调用父窗口方法,取到已选择过的权限ID,并让树节点选中
function setSelectedRoleIDS()
{
	var opt_ids = "";
	if(opt_ids != "" && opt_ids != null)
	{
		var tempA = opt_ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			$("div[node-id='"+tempA[i]+"'] .tree-checkbox").removeClass("tree-checkbox0").addClass("tree-checkbox1");			
		}		
	}
}

function showOperateTree(state,s_id)
{
	var json_data;
	$("#leftMenuTree2").empty();
	if(state == "is_sub")
	{
		if(s_id != "cms")
		{
			json_data = eval(OperateRPC.getOperateTreeJsonStr2(s_id));			
			setAppointTreeJsonData("leftMenuTree2",json_data);
			setSelectedRoleIDS(top.getOptIDSByUser(s_id,""));
		}
	}
	else
	{
		if(s_id.indexOf("GK") > -1)
		{
			json_data = eval(OperateRPC.getOperateTreeJsonStr2("zwgk"));	
			setAppointTreeJsonData("leftMenuTree2",json_data);
			setSelectedRoleIDS(top.getOptIDSByUser("zwgk",s_id));
		}else
		{
			json_data = eval(OperateRPC.getOperateTreeJsonStr2("cms"));			
			setAppointTreeJsonData("leftMenuTree2",json_data);
			setSelectedRoleIDS(top.getOptIDSByUser("cms",s_id));
		}
	}	
}

function initOperateTree()
{
	$('#leftMenuTree2').tree({	
		checkbox: true		
	});
}

function setSelectedRoleIDS(opt_ids)
{	
	if(opt_ids != "" && opt_ids != null)
	{
		var tempA = opt_ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			$("div[node-id='"+tempA[i]+"'] .tree-checkbox").removeClass("tree-checkbox0").addClass("tree-checkbox1");			
		}		
	}	
	
	$(".tree-checkbox0").each(function(){
		$(this).parent().remove();
	})

}

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="table" class="" border="0" cellpadding="0" cellspacing="0" style="width:94%">
	<tbody>
		<tr>			
			<td align="center" class="width200">
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft" style="width:200px;height:390px;overflow:auto;border:1px solid #828790;background:#ffffff">
						<ul id="leftMenuTree" class="easyui-tree" animate="true" >
						</ul>
					</div>
				</div>
			</td>		
			<td class="width10"></td>
			<td align="center" width="300px">				
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft"style="width:290px;height:390px;overflow:auto;border:1px solid #828790;background:#ffffff">
						<ul id="leftMenuTree2" class="easyui-tree" animate="true" >
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
			<input id="userAddCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex);" value="关闭" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>