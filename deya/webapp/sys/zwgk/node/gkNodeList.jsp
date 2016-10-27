<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分开节点管理</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/gkNodeList.js"></script>
<script type="text/javascript">

var nodcat_id = 0;
var app_id = "zwgk";
var jsonData;
var chold_jData;

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	setLeftTreeHeight();
	showMenuTree();	
	initTable();
	loadMenuTable();
	treeNodeSelected(nodcat_id);
});

function loadMenuTable()
{	
	showList();
	showTurnPage();	
	Init_InfoTable(table.table_name);	
}

function showMenuTree()
{
	json_data = eval(GKNodeRPC.getGKNodeCategroyJSONTreeStr());
	nodcat_id = json_data[0].id;
	setLeftMenuTreeJsonData(json_data);
	initMenuTree();
}

function initMenuTree()
{
	$('#leftMenuTree').tree({
		//url: 'data/tree_data_tongji.json',
		//url: jsonData,
		onClick:function(node){
			if(node.attributes!=undefined){   
				changeListTable(node.id); 
            }  
		}
	});

}

//点击树节点,修改菜单列表数据
function changeListTable(nc_id){
	nodcat_id = nc_id;
	loadMenuTable();
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
				<input id="btn1" name="btn1" type="button" onclick="openAddNodePage();" value="新建节点" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'nodcat_id','openUpdateNodePage()');" value="修改" />
				<input id="btn4" name="btn4" type="button" onclick="publicSelectSinglCheckbox(table,'node_id','openSelectUserPage(\'选择用户\',\'saveUser\')')" value="设置管理员" />
				<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox(table,'nodcat_id','openSelectGKNodeCategory(\'选择公开节点分类\',\'moveGKNode\',\'\')');" value="移动节点" />
				<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox(table,'id','updateNodeStatus(1)');" value="停用" />
				<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox(table,'id','updateNodeStatus(0)');" value="启用" />
				<input id="btn3" name="btn3" type="button" onclick="sortRecord(table,'id','sortNode()');" value="保存排序" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'id','deleteNode()');" value="删除" />
			</td>
		</tr>
	</table>
	 </td>
	</tr>
</table>
</div>
</body>
</html>
