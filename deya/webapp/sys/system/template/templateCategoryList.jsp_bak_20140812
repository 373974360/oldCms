<%@ page contentType="text/html; charset=utf-8"%>
<%

	String tcid = request.getParameter("tid");
	if(tcid == null || tcid.equals("")){
		tcid = "-1";
	}
	String pid = request.getParameter("pid");
	if(pid == null || pid.equals("")){
		pid = tcid;
	}
	String lab_num = request.getParameter("labNum");
	String app_id = request.getParameter("app");
	if(app_id == null){
		app_id = "0";
	}
	String site_id = request.getParameter("site_id");
	if(site_id == null){
		site_id = "0";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模板目录管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
<script language="javascript" src="/sys/js/jquery.uploadify.js"></script>
<script language="javascript" src="/sys/js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="/sys/js/uploadTools.js"></script>
<script type="text/javascript" src="/sys/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/sys/js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/template.js"></script>
<script type="text/javascript" src="js/templateCategory.js"></script>
<script type="text/javascript">

var tc_id = "<%=tcid%>";
var parent_id = "<%=pid%>";
var load_template = false;
var load_admin = false;
var load_templateCategory = false;
var load_templateFile = false;
var lab_num = "<%=lab_num%>";
var f_type = "";
var site_id = "<%=site_id%>";
var app = "<%=app_id%>";

$(document).ready(function(){
	//让树节点选中
	top.treeNodeSelected(tc_id);
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	reloadTemplateDataList();
	
	if(lab_num != "" && lab_num != "null" && lab_num != null)
	{
		//top.treeNodeSelected(parent_id);
		//changeLabShowList(lab_num);
		$(".fromTabs li:nth-child("+lab_num+") .tab_right").click();
		$(".fromTabs li:nth-child("+lab_num+")").click();		
	}

	initTemplateUpLoad();
});


//用于标签切换时显示列表数据，页面加载时，只加载了第一个标签中的列表内容（部门列表），切换到其它标签时，如果是第一次，需要加载数据
function changeLabShowList(labname)
{
	$(".infoListTable").hide();
	if(labname == "t")
	{
		if(load_template == false)
		{
			showTurnPage();
			initTable();
			showList();
			Init_InfoTable(table.table_name);
			load_template = true;
		}
		$("#uploadify_div").hide();
		$("#listTable_0").show();
	}
	if(labname == "tc")
	{
		if(load_templateCategory == false)
		{
			initTemplateCategoryTable();
			showTemplateCategoryList();	
			showTemplateCategoryTurnPage();
			Init_InfoTable(tc_table.table_name);
			load_templateCategory = true;
		}
		$("#uploadify_div").hide();
		$("#listTable_1").show();
	}
	if(labname == "file")
	{
		if(load_templateFile == false)
		{			
			f_type = "file";			
			initTemplateFileTable();
			initTemplateFolder();		
			Init_InfoTable(tf_table.table_name);	
			load_templateFile = true;
		}
		$("#uploadify_div").show();
		$("#listTable_2").show();
	}
	if(labname == "img")
	{
		f_type = "img";
		$("#uploadify_div").show();
		setTimeout("showImageList()",100);		
	}
	//$(".search_td").hide();
	//$("#"+labname+"_search").show();
}

function showImageList()
{
	$("#listTable_2").show();
	initTemplateFileTable();
	showTemplateFileList("img");	
	showTemplateFileTurnPage();
	Init_InfoTable(tf_table.table_name);
}


</script>
</head>

<body>
<div>
<table class="table_option" border="0"  align="left" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="left" class="fromTabs width10" style="">	
			
			<span class="blank3"></span>
		</td>
		<td align="left" width="50%">
			<ul class="fromTabs">
				<li class="list_tab list_tab_cur">
					<div class="tab_left">
						<div class="tab_right" onclick="changeLabShowList('t')">模板列表</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" onclick="changeLabShowList('tc')">模板目录列表</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" onclick="changeLabShowList('file')">资源文件</div>
					</div>
				</li>
				<li class="list_tab hidden">
					<div class="tab_left">
						<div class="tab_right" onclick="changeLabShowList('img')">资源图片</div>
					</div>
				</li>
			</ul>
		</td> 
		<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
		&nbsp;
			<span class="blank3"></span>
		</td>
		<td align="right" valign="middle" id="user_search" class="search_td fromTabs" >
			<div id="uploadify_div" class="hidden"><input type="file" name="uploadify" id="uploadify" /></div>
			<span class="blank3"></span>
		</td>
		<td align="right" valign="middle" id="manager_search" class="search_td fromTabs" width="10px">			
			&nbsp;<span class="blank3"></span>
		</td>
	</tr>
</table>
</div>
<span class="blank3"></span>
<!--模板列表区域　开始 -->
<div class="infoListTable" id="listTable_0">
	<div id="template_table"></div>
	<div id="template_turn"></div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="openAddTemplatePage();" value="添加模板" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'t_id','openUpdateTemplateDataPage()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="deleteSinglRecord(table,'t_id','deleteTemplateData()');"  value="删除" />
				&nbsp;&nbsp;&nbsp;&nbsp;  
				<input id="btn3" name="btn3" type="button" onclick="updateRecord(table,'t_id','publishTemplate()');" value="发布" />	
				<input id="btn3" name="btn3" type="button" onclick="updateRecord(table,'t_id','cancelTemplate()');" value="撤销" />			
				<span class="blank3"></span>
			</td>		
		</tr>
	</table>
</div>
<!-- 模板列表区域　结束 -->

<!-- 模板目录列表区域　开始 -->
<div class="infoListTable hidden" id="listTable_1">
	<div id="templateCategory_table"></div>
	<div id="templateCategory_turn"></div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="openAddTemplateCategoryPage();" value="添加模板目录" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(tc_table,'tcat_id','openUpdateTemplateCategoryDataPage()');" value="修改" />
				<input id="btn1" name="btn1" type="button" onclick="sortTemplateCategory()" value="保存排序" />
				<input id="btn3" name="btn3" type="button" onclick="deleteSinglRecord(tc_table,'tcat_id','deleteTemplateCategoryData()');" value="删除" />
				<span class="blank3"></span>
			</td>		
		</tr>
	</table>
</div>
<!-- 模板目录列表区域　结束 -->

<!-- 模板目录列表区域　开始 -->
<div class="infoListTable hidden" id="listTable_2">	
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
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
			<td align="left" valign="top">		
				<div id="file_table"></div>
				<span class="blank12"></span>
				<div><input id="btn1" name="btn1" type="button" onclick="openTemplateResourcesFolder();" value="新建目录" /></div>
				<div id="file_turn"></div>
			</td>		
		</tr>
	</table>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">		
				<div id="fileQueue"></div>
			</td>		
		</tr>
	</table>
</div>
<!-- 模板目录列表区域　结束 -->

<!-- 模板目录列表区域　开始 -->
<div class="infoListTable hidden" id="listTable_3">
	
</div>
<!-- 模板目录列表区域　结束 -->
</body>
</html>
