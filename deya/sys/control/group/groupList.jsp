<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>站群注册</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/groupList.js"></script>
<script type="text/javascript">

var jsonData;
var chold_jData;
var sgroup_id = "11111";  //站群ID 要初始化进去的数据
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	setLeftTreeHeight();
	showMenuTree();	
	initTable();
	loadMenuTable();
	treeNodeSelected(sgroup_id);
});

function loadMenuTable()
{	
	showList();
	showTurnPage();
	Init_InfoTable(table.table_name);
}

function showMenuTree()
{
	json_data = eval(SiteGroupRPC.getGroupTreeJsonStr());
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
				changeMemuListTable(node.id); 
            }  
		}
	});

}

//点击树节点,修改菜单列表数据
function changeMemuListTable(m_id){
	sgroup_id = m_id;
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
				<input id="btn1" name="btn1" type="button" onclick="openAddGroupPage();" value="新建站群" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'sgroup_id','openUpdateGroupPage()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="sortRecord(table,'sgroup_id','funSort()');" value="保存排序" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'sgroup_id','deleteGroup()');" value="删除" />
			</td>
		</tr> 
	</table>
	 </td>
	</tr>
</table>
</div>
</body>
</html>
