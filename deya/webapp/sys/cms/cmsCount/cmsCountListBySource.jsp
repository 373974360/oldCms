<%@ page contentType="text/html; charset=utf-8"%>
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
   String start_day = request.getParameter("start_day");
   String end_day = request.getParameter("end_day");

   String now = DateUtil.getCurrentDate();
   String now1 = now.substring(0,7)+"-01";
   if(start_day == null || "".equals(start_day)) {
   		start_day = now1;
   }
   if(end_day == null || "".equals(end_day)) {
   		end_day = now;
   }	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>目录管理</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<style type="text/css">
.checkBox_mid{ vertical-align:middle};
#v_type{height:50px;}
</style>

<link type="text/css" rel="stylesheet" href="/sys/styles/main.css" />
<link type="text/css" rel="stylesheet" href="/sys/styles/sub.css" />
<script type="text/javascript" src="/sys/js/jquery.js"></script>

<script type="text/javascript" src="/sys/js/jsonrpc.js"></script>

<script type="text/javascript" src="/sys/js/java.js"></script>
<script type="text/javascript" src="/sys/js/extend.js"></script>
<script type="text/javascript" src="/sys/js/jquery.c.js"></script>
<script type="text/javascript" src="/sys/js/common.js"></script>
<script type="text/javascript" src="/sys/js/validator.js"></script>
<script type="text/javascript" src="/sys/js/lang/zh-cn.js"></script>
<script type="text/javascript" src="/sys/js/sysUI.js"></script>


<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/cmsCountListBySource.js"></script>
<script type="text/javascript" src="/sys/js/open-flash-chart/js/json2.js"></script>
<script type="text/javascript" src="/sys/js/open-flash-chart/js/swfobject.js"></script>
<script type="text/javascript">
var cat_type = "0";
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
});

function showCategoryTree()
{  
	if(cat_type == "0")
	{
		json_data = eval(jsonrpc.CategoryRPC.getCategoryTreeBySiteID(site_id));
	}
	else
	{
		json_data = eval(jsonrpc.CategoryRPC.getCategoryTreeByCategoryID(cat_id,site_id));
	}
	
	json_data = eval(jsonrpc.CategoryRPC.getCategoryTreeBySiteID(site_id));
	setLeftMenuTreeJsonData(json_data);
	treeNodeSelected(cat_id);
}
</script>
</head>
<body>
<table style="width:100%" border="0" cellpadding="0" cellspacing="0" >
	<tr>
    	<td colspan="3"  class="search_td fromTabs">
        	<div style="height:auto; border:#A00 2px;">
            <div id="defauleTime" style="float:left;">
            <span class="f_red">*</span>时间范围:
				<input class="Wdate" type="text" name="start_day" id="start_day" size="11" style="height:16px;line-height:16px;" 
					value="<%=start_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
		         --
			    <input class="Wdate" type="text" name="end_day" id="end_day" size="11" style="height:16px;line-height:16px;" 
					 value="<%=end_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            </div>
            <div style="float:right;">        
                     <input type="button" id="searchBtn"  onclick="searchBtn()" value="统计"/>
                     <input type="button" id="outFileBtn" style="display:none" onclick="outFileBtn()" value="导出"/>
            </div>
            </div>
        </td>
    </tr>
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
     		<table width="100%">
			
			<tr valign="top">
			<td>
		   <div id="chart">
			
		   </div>
		   </td>
		   </tr>

       <tr valign="top">
     	<td>
       <div id="count">
       <table id="countList" class="treeTableCSS table_border"  border="0" cellpadding="0" cellspacing="0">
       </table>
       </div>
       </td>
       </tr>
       <tfoot><tr><td colspan="3"></td><td></td></tr></tfoot>
       </table>
	 </td>
	</tr>
</table>
</body>
</html>
