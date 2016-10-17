<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%
	String site_id = request.getParameter("site_id");
	String app_id = request.getParameter("app_id");
	String cat_id = request.getParameter("cat_id");
	if(cat_id == null || "".equals(cat_id) || "null".equals(cat_id))
		cat_id = "0";
	if(site_id == null || "".equals(site_id) || "null".equals(site_id))
		site_id = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>目录管理</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css" />
<style type="text/css">
.checkBox_mid{ vertical-align:middle; margin-right:10px;};
#v_type{height:50px;}
.checkBox_text{ vertical-align:text-top}
.span_left{ margin-left:14px;}
</style>
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/cmsUpdateList.js"></script>
<script type="text/javascript" src="/sys/js/open-flash-chart/js/json2.js"></script>
<script type="text/javascript" src="/sys/js/open-flash-chart/js/swfobject.js"></script>
<script type="text/javascript">
var cat_type = "0";
var site_id = "<%=site_id%>";
var app_id = "<%=app_id%>";//应用ID
var cat_id = "<%=cat_id%>";
var class_id = 0;
var jsonData;

// 调用catSelect.jsp需要的字段和函数
var cat_str = "";

var selected_ids = ""; // 查询语句用的的字段,会去除站点根节点.
function setCatNames(catNames){
	$("#selectedCateNames").val(catNames);
	
	// 协助处理选中的id去掉根节点id
	selected_ids = cat_str;
	if(cat_str!="" && cat_str.length>1 && cat_str.substr(0,1) == "0"){
		selected_ids = cat_str.substr(2);
	}
}

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
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
	treeNodeSelected(cat_id);
}

// 弹出选择栏目的页面
function openSelectPage(){
	top.OpenModalWindow("选择站点下的栏目","/sys/cms/cmsCount/cateSelect.jsp?site_id="+site_id,318,480);
}
</script>
</head>
<body>
<table style="width:500px;margin:0px;" border="0" cellpadding="0" cellspacing="0" >
    <tr>
    	<td>
        	<span class="f_red">*</span>网站栏目: 
        </td>
        <td>
        <div style="float:left">
            <input id="selectedCateNames" readonly="readonly" />
            <input id="getCateIDS" type="button" onclick="openSelectPage()" value="选择" />
        </div>
        <div style="float:left;margin-left:8px;">
        	<input type="button" id="searchBtn"  onclick="showList()" value="统计"/>
        	<input type="button" id="outFileBtn" style="display:none" onclick="outFileBtn()" value="导出"/>
        </div> 
        </td>
    </tr>
</table>
<span class="blank3"></span>

<table style="width:500px;margin:0px;">
    <tr valign="top" >
    	<td>
    	<div id="chart">
       	
        </div>
        </td>
    </tr>
    <tr valign="top">
     	<td>
       <div id="count">
       		<table id="countList" class="treeTableCSS table_border" style="width:500px;margin:0px;"  border="0" cellpadding="0" cellspacing="0">
       		</table>
       </div>
       </td>
     </tr>
</table>

</body>
</html>