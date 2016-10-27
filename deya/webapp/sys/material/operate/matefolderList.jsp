<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>素材库目录</title>
<meta name="generator" content="cicro-Builder"/>
<meta name="author" content="cicro"/>
<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../js/mateFolderList.js"></script>
<script type="text/javascript">
var f_id = "1";
var parent_id ="0";
var jsonData;
var chold_jData;
var appList;
var site_id ="<%=request.getParameter("site_id")%>";
var user_id = top.LoginUserBean.user_id;
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	setLeftTreeHeight();
	showMateFolderTree();	
	//得到应用信息
	getAppList();
	initTable();
	loadMateFolderTable();
	treeNodeSelected(f_id);
});
function loadMateFolderTable()
{	
	showTurnPage();
	showList();
	Init_InfoTable(table.table_name);
}
function showMateFolderTree()
{
	json_data = eval(MateFolderRPC.getMateFolderTreeJsonStr(f_id,site_id,user_id));
	setLeftMenuTreeJsonData(json_data);
	initMenuTree();
}
function initMenuTree()
{
	$('#leftMenuTree').tree({		
		onClick:function(node){			
				changeMateFolderListTable(node.id);            
		}
	});
}
//点击树节点,修改菜单列表数据
function changeMateFolderListTable(o_id){
	f_id = o_id;
	loadMateFolderTable();
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
				<input id="btn1" name="btn1" type="button" onclick="openAddMateFolderPage();" value="新建文件夹" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'f_id','openUpdateMateFolderPage()');" value="修改" />
				<!--input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'f_id','openSelectSinglMateFolderPage(\'选择文件夹\',\'moveMateFolder\')')" value="移动文件夹" /-->
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'f_id','deleteMateFolder()');" value="删除" />
			</td>
		</tr>
	</table>
	 </td>
	</tr>
</table>
</div>
</body>
</html>
