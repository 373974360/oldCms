<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>共享资源管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
<script language="javascript" src="/sys/js/jquery.uploadify.js"></script>
<script language="javascript" src="/sys/js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="js/template.js"></script>
<script type="text/javascript" src="js/templateCategory.js"></script>
<script type="text/javascript" src="/sys/js/uploadTools.js"></script>
<script type="text/javascript">
var tc_id = 0;
var site_id = "shared_res"
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	initTemplateUpLoad();
	changeLabShowList("file");
});


//用于标签切换时显示列表数据，页面加载时，只加载了第一个标签中的列表内容（部门列表），切换到其它标签时，如果是第一次，需要加载数据
function changeLabShowList(labname)
{	
	if(labname == "file")
	{
		$("#listTable_2").show();
		f_type = "file"
		$("#uploadify_div").show();
		initTemplateFileTable();
		showTemplateFileList();	
		showTemplateFileTurnPage();
		Init_InfoTable(tf_table.table_name);		
	}
	if(labname == "img")
	{
		f_type = "img";
		$("#uploadify_div").show();
		setTimeout("showImageList()",100);		
	}
}

function showImageList()
{
	$("#listTable_0").show();
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
						<div class="tab_right" onclick="changeLabShowList('file')">资源文件</div>
					</div>
				</li>
				<li class="list_tab">
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
<!-- 模板目录列表区域　开始 -->
<div class="infoListTable" id="listTable_0">
	<div id="file_table"></div>
	<div id="file_turn"></div>
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
<div class="infoListTable hidden" id="listTable_1">
	
</div>
<!-- 模板目录列表区域　结束 -->
</body>
</html>
