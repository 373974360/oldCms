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
<title>站群部门统计</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
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
<script type="text/javascript" src="js/cmsAssessList.js"></script>
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
	if(cat_str!="" && cat_str.length>1){
		selected_ids = cat_str.replace("HIWCMcgroup,","");
		var t_ids = selected_ids.split(",");
		selected_ids = "";
		for(var i=0;i<t_ids.length;i++){
             if(t_ids[i]!=null && t_ids[i]!=""){
                 if(i==t_ids.length-1){
                	 selected_ids += "'"+ t_ids[i] + "'";
                 }else{
                	 selected_ids += "'"+ t_ids[i] + "',";
                 }
             }
		}	
	}
}

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	//得到权限信息

});


// 弹出选择栏目的页面
function openSelectPage(){
	OpenModalWindow("选择站点","/sys/control/count/siteSelect.jsp",318,480);
}

</script>
</head>
<body>
<table style="width:100%" border="0" cellpadding="0" cellspacing="0" >
	<tr>
    	<td width="80px" height="27px">
        	<span class="f_red">*</span>时间范围:
        </td>
    	<td>
        	<div style="height:auto; border:#A00 2px;">
            <div id="defauleTime">
            <input class="Wdate" type="text" name="start_day" id="start_day" size="11" style="height:16px;line-height:16px;" 
					value="<%=start_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true" />
		         --
			    <input class="Wdate" type="text" name="end_day" id="end_day" size="11" style="height:16px;line-height:16px;" 
					 value="<%=end_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true"/>
            </div>
            </div>
        </td>
    </tr>
    <tr>
    	<td>
        	<span class="f_red">*</span>选择站点: 
        </td>
        <td>
        <div style="float:left">
        	<input id="all_cat_ids" type="checkbox" class="checkBox_mid" onclick="allSelected()"/>全选
            <input id="selectedCateNames" readonly="readonly" />
            <input id="getCateIDS" type="button" onclick="openSelectPage()" value="选择" />
        </div>
        <div style="float:right">
        	<input type="button" id="searchBtn"  onclick="showList()" value="统计"/>
        	<input type="button" id="outFileBtn" style="display:none" onclick="outFileBtn()" value="导出"/>
        </div> 
        </td>
    </tr>
   <tr>
    	<td class="search_td fromTabs">
        	<span class="span_left">统计方式:</span>
  </td>
        <td class="search_td fromTabs">
        	<input id="dept_info"  type="radio" name="info_scope" value="dept" checked="checked" /><span class="checkBox_text">按部门</span>
            <input id="user_info" type="radio" name="info_scope" value="user" /><span class="checkBox_text">按人员</span>
      </td>
    </tr>
</table>
<span class="blank3"></span>

<table width="100%">
    <tr valign="top">
     	<td>
<table id="treeTableCount" class="treeTableCSS table_border"  border="0" cellpadding="0" cellspacing="0">

</table>

</body>
</html>