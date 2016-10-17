<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限注册</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/operateList.js"></script>
<script type="text/javascript">

var opt_id = "1";

var jsonData;
var chold_jData;
var appList;

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	setLeftTreeHeight();
	showOperateTree();	
	//得到应用信息
	getAppList();
	initTable();
	loadOperateTable();
	treeNodeSelected(opt_id);
});

function loadOperateTable()
{	
	showTurnPage();
	showList();
	Init_InfoTable(table.table_name);
}

function showOperateTree()
{
	json_data = eval(OperateRPC.getOperateTreeJsonStr(""));
	setLeftMenuTreeJsonData(json_data);
	initMenuTree();
}

function initMenuTree()
{
	$('#leftMenuTree').tree({		
		onClick:function(node){			
				changeOperateListTable(node.id);            
		}
	});
}

//点击树节点,修改菜单列表数据
function changeOperateListTable(o_id){
	opt_id = o_id;
	loadOperateTable();
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
		<div id="table"></div>
		<div id="turn"></div>
		<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="openAddOperatePage();" value="新建权限" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'opt_id','openUpdateOperatePage()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'opt_id','openSelectSinglOperatePage(\'选择权限\',\'moveOperate\')')" value="移动权限" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'opt_id','deleteOperate()');" value="删除" />
			</td>
		</tr>
	</table>
	 </td>
	</tr>
</table>
</div>
</body>
</html>
