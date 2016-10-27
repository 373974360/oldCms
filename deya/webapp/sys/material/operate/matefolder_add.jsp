<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>素材库自定义目录维护</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css" />
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../js/mateList.js"></script>
<script type="text/javascript">
var site_id ="<%=request.getParameter("site_id")%>";
var f_id = "<%=request.getParameter("f_id")%>";
var user_id = top.LoginUserBean.user_id;
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	showSelectDiv("cname","CustomFollder_tree_div",115);
	showCustomFolderTree();

	if(f_id != "" && f_id != "null" && f_id != null && f_id != "0")
	{	
		defaultBean = MateFolderRPC.getMateFolderBean(f_id);
		$("#p_id").val(f_id);
	}
	else
	{
		MateInfoBean.f_id = "0";
		MateInfoBean.cname= "我的素材库";
		$("#p_id").val(f_id);
		defaultBean = MateInfoBean;	
	}
	if(defaultBean)
	{
		$("#MateFolder_table").autoFill(defaultBean);					
	}
	$("#addButton").click(addMateFolder);	
});
function showCustomFolderTree()
{
	var json_data = eval(MateFolderRPC.getMFTreeJsonStrForCustom(site_id,user_id));
	setLeftMenuTreeJsonData(json_data);
	CustomFolderTree();
	treeNodeSelected(f_id);
}
function CustomFolderTree()
{
	$('#leftMenuTree').tree({		
		onClick:function(node){			
			selectedCustomFollder(node.id,node.text);            
		}
	});
}
//点击树节点,修改菜单列表数据 内容
function selectedCustomFollder(id,text){
	$("#cname").val(text);
	$("#p_id").val(id);
	$("#CustomFollder_tree_div").hide();
}
</script>
</head>
<body>
<span class="blank12"></span>
<span class="blank3"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="MateFolder_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>上级目录：</th>
			<td >
				<input type="text" id="cname" value="" class="width200" readonly="readonly"/>
				<input type="hidden" id="p_id" name="p_id" value=""/>
				<div id="CustomFollder_tree_div" class="select_div tip hidden border_color">
					<div id="leftMenuBox">
						<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
							<ul id="leftMenuTree" class="easyui-tree" animate="true">
							</ul>
						</div>
					</div>
				</div>
			</td>			
		</tr>	
		<tr>
			<th><span class="f_red">*</span>目录名称：</th>
			<td class="width250">
				<input id="subcname" name="subcname" type="text" class="width200" value="" onblur="checkInputValue('subcname',false,80,'目录名称','')"/>
			</td>			
		</tr>	
	</tbody>
</table>
<span class="blank12"></span>
<span class="blank12"></span>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank12"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('MateFolder_table',f_id)" value="重置"/>	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>