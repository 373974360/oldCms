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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>站点信息访问量统计</title>
<meta name="generator" content="cicro-Builder"/>
<meta name="author" content="cicro"/>
<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<style type="text/css">
.checkBox_mid{ vertical-align:middle; margin-right:10px;};
#v_type{height:50px;}
.checkBox_text{ vertical-align:text-top}
.span_left{margin-left:14px;}
.cateOrderList td{width:1000px;height:30px}
.td_style{width:1000px;height:30px}
.hidden{display:none;}
</style>
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/AccessCountList.js"></script>
<script type="text/javascript" src="/sys/js/open-flash-chart/js/json2.js"></script>
<script type="text/javascript" src="/sys/js/open-flash-chart/js/swfobject.js"></script>
<script type="text/javascript">
var cat_type ="0";
var site_id = "<%=site_id%>";
var app_id = "<%=app_id%>";//应用ID
var cat_id = "<%=cat_id%>";
var class_id = 0;
var jsonData;
var chold_jData;
var zt_jdata;
var new_jdata;

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	//得到权限信息
	
	setLeftTreeHeight();
	initTreeInfos();
});

function initTreeInfos(){
	showCategoryTree();
	initCategoryTree();
}

function showCategoryTree()
{
	if(cat_type == "0")
	{
		json_data = CategoryRPC.getCategoryTreeBySiteID(site_id);
	}else
	{
		json_data = CategoryRPC.getCategoryTreeByCategoryID(cat_id,site_id);
	}	
	 zt_jdata = "{\"id\":-1,\"iconCls\":\"icon-category\",\"text\":\"专题栏目\",\"attributes\":{\"url\":\"\",\"handl\":\"\"},\"children\":"+jsonrpc.CategoryRPC.getZTCategoryTreeJsonStr(site_id)+"}";
		
	if(zt_jdata != "" && zt_jdata != null)
	{
		json_data = json_data.substring(1,json_data.length-1);
		zt_jdata = zt_jdata.substring(0,zt_jdata.length);

		new_jdata = eval("["+json_data+","+zt_jdata+"]");
	}else{
		new_jdata = eval(json_data);			
	}
	setLeftMenuTreeJsonData(new_jdata);
	treeNodeSelected(cat_id);
}

function showType(value)
{
	if(value == 1)
	{
		$("#count3").hide();
		$("#count2").hide();
		$("#leftMenuBox").show();
		$("#count").show();
		$("#row_count_div").css("display","none");
	}else if(value == 2)
	{
		$("#leftMenuBox").hide();
		$("#count").hide();
		$("#count3").hide();
		$("#count2").show();
		$("#row_count_div").css("display","block");
    }else if(value == 3){
    	$("#leftMenuBox").hide();
		$("#count").hide();
		$("#count3").show();
		$("#row_count_div").css("display","block"); 
    }
}
</script>
</head>
<body>
<table style="width:100%" border="0" cellpadding="0" cellspacing="0" >
	<tr>
    	<td width="70px" height="27px"  algin="left" class="search_td fromTabs">
        	<span class="f_red">*</span>时间范围:
        </td>
    	<td algin="left" class="fromTabs">
        	<div style="height:auto; border:#A00 2px;">
            <div id="defauleTime">
            <input class="Wdate" type="text" name="start_day" id="start_day" size="11" style="height:16px;line-height:16px;" 
					value="<%=start_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true" />
		         --
			    <input class="Wdate" type="text" name="end_day" id="end_day" size="11" style="height:16px;line-height:16px;" 
					 value="<%=end_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true"/> &nbsp;&nbsp;
		</td>
		<td width="700px" algin="left" class="fromTabs">
		    <div id="test" style="float:left;">
			    <span class="f_red">*</span><span>统计方式：</span>	
				<input type="radio" checked="checked" name="tj_type" value="1" onChange="showType(this.value)"/>默认
	    		<input type="radio" name="tj_type" value="2" onChange="showType(this.value)"/>按栏目排行
	    		<input type="radio" name="tj_type" value="3" onChange="showType(this.value)"/>按信息排行 &nbsp;&nbsp;
    		</div>
    		<div id="row_count_div" style="float:left; display:none " >
    		  	<span class="f_red">*</span><span>显示条数(默认显示10条)：</span>
    			<input type="text" id="row_count" size="5" value="10"/>
    		</div>
            </div>
            </div>
        </td>
        <td class="search_td fromTabs" algin="right">
	        <div style="float:right">
	        	<input type="button" id="searchBtn"  onclick="searchBtn()" value="统计"/>
	        	<input type="button" id="outFileBtn" onclick="outFileBtn()" value="导出"/>
	        </div> 
        </td>
    </tr>
</table>
<span class="blank3"></span>
<table width="100%" id="cateTreeList">
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

<table width="100%" id="catorderList">
     <tr>
		 <td class="width10"></td>
		 <td valign="top">
	     		<table width="100%">
			       <tr valign="top">
				     	<td>
					       <div id="count2">
					       <table id="cateOrderList_data" class="treeTableCSS table_border"  border="0" cellpadding="0" cellspacing="0">
					       </table>
					       </div>
				       </td>
			       </tr>
			       <tfoot><tr><td colspan="3"></td><td></td></tr></tfoot>
	       </table>
		 </td>
	</tr>
</table>


<table width="100%">
     <tr>
		 <td class="width10"></td>
		 <td valign="top">
	     		<table width="100%">
			       <tr valign="top">
				     	<td>
					       <div id="count3" style="width:100%">
					       <table id="infoOrderList_data" class="treeTableCSS table_border"  border="0" cellpadding="0" cellspacing="0">
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
