<%@ page contentType="text/html; charset=utf-8"%>
<%
	String class_id = request.getParameter("class_id");
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
var cate_type = "share";
var app_id = "system";
var class_id = "<%=class_id%>";
var cat_id = 0;
var jsonData;
var chold_jData;
var site_id = "";

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
	json_data = eval(CategoryRPC.getCategoryTreeByClassID(class_id));
	cat_id = json_data[0].id;
	setLeftMenuTreeJsonData(json_data);
	initCategoryTree();
	treeNodeSelected(cat_id);
}

function openAddCategoryPage()
{
	top.addTab(true,"/sys/cms/category/category_share_add.jsp?top_index="+top.curTabIndex+"&class_id="+class_id+"&parentID="+cat_id,"维护目录");
}

function viewCategory(c_id)
{
	top.addTab(true,'/sys/cms/category/category_share_view.jsp?cat_id='+c_id,'查看目录');
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
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'class_id','openUpdateCategoryPage()');" value="修改" />	
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'cat_id','openSelectSingleCategory(\'选择目录\',\'moveCategory\',site_id)')" value="移动目录" />
				<input id="btn3" name="btn3" type="button" onclick="sortRecord(table,'class_id','sortCategory()');" value="保存排序" />
				<!-- <input id="btn3" name="btn3" type="button" onclick="openSelectSingleCateClass('选择类目','copyBasisCategory')" value="快速创建" /> -->
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'class_id','deleteCategory()');" value="删除" />
			</td>
		</tr>
	</table>
	 </td>
	</tr>
</table>
</div>
</body>
</html>
