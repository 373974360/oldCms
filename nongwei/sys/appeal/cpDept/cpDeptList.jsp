<%@ page contentType="text/html; charset=utf-8"%>
<%
	String dept_id=request.getParameter("dept_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>机构注册</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/cpDept.js"></script>
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
	//展现树 
	showCpDeptTree();
	setLeftTreeHeight();

	//初始化表格
	initTable();
	reloadCpDeptList();
	
});

//设置点击节点时的动作为重载列表
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
	reloadCpDeptList();
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
				<input id="btn1" name="btn1" type="button" onclick="openAddCpDeptPage();" value="新建机构" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'dept_id','openUpdateCpDeptPage()');" value="修改" />
				<input id="btn2" name="btn2" type="button" onclick="saveDeptSort();" value="保存排序" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'dept_id','deleteCpDept()');" value="删除" />
			</td>
		</tr>
	</table>
	</div>
	 </td>
	</tr>
</table>

</body>
</html>