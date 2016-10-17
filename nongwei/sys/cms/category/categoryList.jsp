<%@ page contentType="text/html; charset=utf-8"%>
<%
	String site_id = request.getParameter("site_id");
	String app_id = request.getParameter("app_id");
	String cat_id = request.getParameter("cat_id");
	if(cat_id == null || "".equals(cat_id) || "null".equals(cat_id))
		cat_id = "0";
	String cat_type = request.getParameter("cat_type");
	if(cat_type == null || "".equals(cat_type) || "null".equals(cat_type))
		cat_type = "0";
	if(site_id == null || "".equals(site_id) || "null".equals(site_id))
		site_id = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>目录管理</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/categoryList.js"></script>
<script type="text/javascript">
var cate_type = "";
var cat_type = "<%=cat_type%>";//目录类型：0：普通栏目1：专题2：服务应用目录
var site_id = "<%=site_id%>";
var app_id = "<%=app_id%>";//应用ID
var cat_id = "<%=cat_id%>";
var class_id = 0;
var jsonData;
var chold_jData;

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	//得到权限信息
	
	setLeftTreeHeight();
	showCategoryTree();	
	initTable();
	loadCategoryTable();
});

function showCategoryTree()
{
	if(cat_type == "0")
	{
		json_data = eval(CategoryRPC.getCategoryTreeBySiteID(site_id));
	}
	else
	{
		json_data = eval(CategoryRPC.getCategoryTreeByCategoryID(cat_id,site_id));
	}	
	
	setLeftMenuTreeJsonData(json_data);
	initCategoryTree();
	treeNodeSelected(cat_id);
}

</script>
</head>

<body>
<table style="width:100%" border="0" cellpadding="0" cellspacing="0" >
	<tr>
	 <td width="200px" valign="top">
		<div id="leftMenuBox">
			<div id="leftMenu" class="contentBox6 textLeft width200" style="overflow:auto">
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
				<input id="btn1" name="btn1" type="button" onclick="openAddCategoryPage();" value="新建目录" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'id','openUpdateCategoryPage()');" value="修改" />	
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'cat_id','openSelectSingleCategory(\'选择目录\',\'moveCategory\',site_id)')" value="移动目录" />
				<input id="btn3" name="btn3" type="button" onclick="sortRecord(table,'cat_id','sortCategory()');" value="保存排序" />
				<input id="btn3" name="btn3" type="button" onclick="openSelectShareCateClass('选择类目','copyShareCategory',app_id)" value="快速创建" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'cat_id','deleteCategory()');" value="删除" />
			</td>
		</tr>
	</table>
	 </td>
	</tr>
</table>
</div>
</body>
</html>