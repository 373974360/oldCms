<%@ page contentType="text/html; charset=utf-8"%>
<%
	String dept_id=request.getParameter("dept_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户注册</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/cpUser.js"></script>
<script type="text/javascript">
//第一次访问页面时得到菜单管理中的参数dept_id
var dept_id = "<%=dept_id%>";
var jsonData;
var chold_jData;
var appList;

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	showCpDeptTree();
	setLeftTreeHeight();	
	//得到应用信息
	//getAppList();
	
	//初始化表格
	initTable();	
	reloadCpUserList();
	treeNodeSelected(dept_id);
});


function initMenuTree()
{
	$('#leftMenuTree').tree({		
		onClick:function(node){			
				changeDeptListTable(node.id);            
		}
	});
}

//点击树节点,修改菜单列表数据
function changeDeptListTable(o_id){
	dept_id = o_id;
	reloadCpUserList();
}

//打开关联业务窗口
function openSelectCQModel(title,handl_name)
{
	top.OpenModalWindow(title,"/sys/appeal/model/select_model.jsp?handl_name="+handl_name,450,500);
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
				<input id="btn3" name="btn3" type="button" onclick="openSelectUserPage('添加用户','saveDeptUser')" value="添加用户" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'user_id','openSelectCQModel(\'关联业务\',\'saveModelUser\')')" value="关联业务" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'user_id','openSelectRolePage(\'关联角色\',\'saveRoleUser\',\'appeal\',\'\')')" value="关联角色" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'user_id','deleteCpDept()');" value="删除" />
			</td>
		</tr>
	</table>
	 </td>
	</tr>
</table>
</div>
</body>
</html>