<%@ page contentType="text/html; charset=utf-8"%>
<%
	String site_id = request.getParameter("site_id");
	String app_id = request.getParameter("app_id");
	if(site_id == null || "".equals(site_id) || "null".equals(site_id))
		site_id = "";
		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择部门</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="../../styles/themes/icon.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/deptSelect.js"></script>
<script type="text/javascript">

<%-- *********************
/**
* 统计页面选取栏目列表时的弹出页, 
* 通过cat_str,setCatNames(catNames)和调用页面进行交互
* 调用此页需要准备cat_str,存放选中的cat_ids
* 和setCatNames(catNames),选中栏目后通过此方法在调用页处理选中的栏目名称
**/
****************************** --%>

var site_id = "<%=site_id%>";

var root_id = "";

var dept_json = "";

var dept_str = top.getCurrentFrameObj().dept_str;

$(document).ready(function(){
	initButtomStyle();

    dept_json = eval(jsonrpc.DeptRPC.getAllDeptTreeJsonStr());

    root_id = dept_json[0].id; // 处理虚拟根目录

	initMenuTree();
	setLeftMenuTreeJsonData(dept_json);// 添加目录树
	setSelectedID(); // 将已选中的类目打勾
});

// 初始化目录树
function initMenuTree()
{
	$("#leftMenuTree").tree({	
		checkbox: true		
	});
}

</script>
</head>
<body>
<table id="tree_table" class="" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
		 <td style="text-align:center" align="center" class="fromTabs">
			<div id="cat_tree_div1" class="border_color" style="margin:0 auto;width:300px; height:390px; overflow:hidden;" >
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
						<ul id="leftMenuTree" class="easyui-tree" animate="true" style="width:300px; height:390px;">
						</ul>
					</div>
				</div>
			</div>
		 </td>
		</tr>
	</tbody>
</table>
<span class="blank3"></span>

<input type="button" onclick="saveDeptIDS()" value="保存" />
<input type="button" onclick="cancle()" value="取消" />

</body>
</html>
