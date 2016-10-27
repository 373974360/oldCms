<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分开节点管理</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="../../styles/themes/icon.css">
<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/gkUserList.js"></script>
<script type="text/javascript">

var node_id = "";
var app_id = "zwgk";
var jsonData;
var chold_jData;
var user_type = "";//打开人员选择窗口的类型，是选人员还是选管理员

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	setLeftTreeHeight();
	showMenuTree();	
	initTable();
	//loadMenuTable();
	selectedNodes();
});

function loadMenuTable()
{	
	showList();
	showTurnPage();	
	Init_InfoTable(table.table_name);	
}

function showMenuTree()
{
	json_data = eval(GKNodeRPC.getGKNodeTreebyCateID(0));
	setLeftMenuTreeJsonData(json_data);
	initMenuTree();
}

function initMenuTree()
{
	$('#leftMenuTree').tree({
		//url: 'data/tree_data_tongji.json',
		//url: jsonData,
		onClick:function(node){
			if(node.attributes.t_node_id != undefined){   
				changeListTable(node.attributes.t_node_id); 
            }  
		}
	});

}

//点击树节点,修改菜单列表数据
function changeListTable(nd_id){
	node_id = nd_id;
	loadMenuTable();
}

//选中第一个节点
function selectedNodes()
{
	$(".icon-gknode").each(function(i){
		if(i == 0)
		{
			//treeNodeSelected($(this).parent().attr("node-id"));
			$(this).click();
		}
	})
}



</script>
</head>

<body>
<table style="width:100%" border="0" cellpadding="0" cellspacing="0" >
	<tr>
	 <td width="200px" valign="top">
		<div id="leftMenuBox">
			<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
				<ul id="leftMenuTree" class="easyui-tree" animate="true">
				</ul>
			</div>
		</div>
	 </td>
	 <td class="width10"></td>
	 <td valign="top">
	   <div>
		<!--table class="table_option" border="0" cellpadding="0" cellspacing="0" >
			<tr>		
				<td align="right" valign="middle" id="dept_search" class="search_td" >
					&nbsp;		
				</td>		
			</tr>
		</table-->
		<div id="table"></div>
		<div id="turn"></div>
		<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="user_type='user';openSelectUserPage('选择用户','saveSiteUser');" value="关联用户" />				
				<input id="btn4" name="btn4" type="button" onclick="user_type='admin';openSelectUserPage('选择用户','saveUser')" value="设置管理员" />
				<input id="btnSetGruopRole" name="btnSetGruopRole" type="button" 
					onclick="publicSelectSinglCheckbox(table,'user_id','openSelectRolePage(\'选择角色\',\'saveRoleUser\',app_id,node_id)')" value="关联角色" />
				<input id="btnDelete" name="btnDelete" type="button" onclick="deleteRecord( table, 'user_id', 'deleteSiteUser()')" value="删除用户" />
			</td>
		</tr>
	</table>
	 </td>
	</tr>
</table>
</div>
</body>
</html>
