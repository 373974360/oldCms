<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@page import="com.deya.util.DateUtil"%>
<%
String siteid = request.getParameter("site_id");
String app_id = request.getParameter("app_id");
if(app_id == null || app_id.trim().equals("")){
	app_id = "zwgk";
}

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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//CN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>政务公开站点工作量统计</title>



<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
<script type="text/javascript">

var GKCountRPC = jsonrpc.GKCountRPC;

var site_id = "<%=siteid%>";
var app_id = "<%=app_id%>";
var start_day = "<%=start_day%>";
var end_day = "<%=end_day%>";
var beanList = null;

var last_node_ids; // 上次选中的ids
var node_ids = ""; // 选中的节点
var is_all_node = false; // 是否全选节点
var all_node_ids = ""; // 全选时的node_ids值

var pieJsonData = "";

$(document).ready(function(){

	initButtomStyle();
	init_input();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	$("#countList").hide();
	// searchSiteCount();
});

// 打开选择节点的弹出窗口
function openPage() {
	OpenModalWindow("选择公开节点","/sys/zwgk/count/nodeSelected.jsp",725,525);
}

// 设置选中的节点名称
function setNodeName(node_name)
{
	$("#cat_tree").val(node_name);
}

function setAllNode() {
	is_all_node = $("#setAll").is(':checked');
	if(is_all_node) {
		$("#cat_tree").hide();
		$("#openPage").hide();
		last_node_ids = node_ids;
		getAllNode_ids();
	} else {
		node_ids = last_node_ids;
		$("#cat_tree").show();
		$("#openPage").show();
	}	
}

function getAllNode_ids()
{
	if(all_node_ids == ""){
		var lt_node = jsonrpc.GKNodeRPC.getAllGKNodeList(); // 取得所有的公开节点列表;
		lt_node = List.toJSList(lt_node);
		for(var i=0;i<lt_node.size();i++) {
			var bean = lt_node.get(i);
			all_node_ids += ",'" + bean.node_id + "'";
		}
		all_node_ids = all_node_ids.substring(1);
	}
	node_ids = all_node_ids;
}

function searchSiteCount()
{
	start_day = $("#start_day").val();
	end_day = $("#end_day").val();
	if(start_day > end_day){
	   msgWargin("结束时间不能在开始时间前!");
	   return ;
	}
	if(start_day=="" || start_day==null || start_day=="null")
	{
		msgWargin("开始时间不能为空!");
		return;
	}
	if(end_day=="" || end_day==null || end_day=="null")
	{
		msgWargin("结束时间不能为空!");
		return;
	}
	end_day = end_day + " 23:59:59";
	if(!is_all_node)
	{
		if(node_ids == "") {
			msgWargin("公开节点不能为空,请选择公开节点!");
			return;
		}
	} 
	$("#countList").show();
	//$("#treeTableCount").empty();
	$("#countList").removeAttr("src");
	$("#countList").attr("src","treetable.jsp?start_day="+start_day+"&end_day="+end_day+"&type=allsite");

	$("#outFileBtn").show();
}


var beanListResult = null;
function outFileBtn(){
	if(beanListResult.size() != 0) {
	    beanListResult = List.toJSList(beanListResult);
		var listHeader = new List();
		listHeader.add("节点名称");
		listHeader.add("信息总数");
		listHeader.add("主动公开数目");
		listHeader.add("依申请公开数目");
		listHeader.add("不公开数目"); 
		var url = GKCountRPC.gkInfoOutExcel(beanListResult,listHeader);
		//alert(url);
		location.href=url;  
	}
	//alert(beanListResult);
}

</script>
</head>
<body>
<div>
<table class="table_option fromTabs" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" width="80px">
			<span class="f_red">*</span>时间范围：
		</td>
        <td>
        	<input class="Wdate" type="text" name="start_day" id="start_day" size="11" style="height:16px;line-height:16px;" 
					value="<%=start_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true"/>
		         --
			    <input class="Wdate" type="text" name="end_day" id="end_day" size="11" style="height:16px;line-height:16px;" 
					 value="<%=end_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true"/>
			<span class="blank3"></span>
        </td>
        <td></td>
     </tr>
     <tr>
        <td align="left" valign="middle">
        	<span class="f_red">*</span>公开站点：
           
        </td>
        <td>
        	<input id="setAll" type="checkbox" onclick="setAllNode()" value="全选">
            <label align="left" valign="middle">全部节点</label>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="text" id="cat_tree" value="" style="width:190px; height:18px; overflow:hidden;" readonly="readonly"/>
            <input id="openPage" name="openPage" type="button" onclick="openPage()" value="选择节点" />	
            <span id="meg" class="checkBox_text" style="margin-left:20px; color:#A0A0A4;" >&nbsp;&nbsp;&nbsp;&nbsp;注:点击节点名称可以查看详细信息</span>
        </td>
        <td align="right" valign="middle">
         		<div>
				 <input id="addButton" name="btn1" type="button" onclick="searchSiteCount()" value="统计" />	
				 <input type="button" id="outFileBtn" style="display:none" onclick="outFileBtn()" value="导出"/>
                 </div>
				 <span class="blank3"></span>
		</td>
    </tr>
</table>
<span class="blank3"></span>
</div>
<iframe src="" id="countList" name="countList" width="100%"	height="380" frameborder="0"  scrolling="auto">

</iframe>
<span class="blank9"></span>
</div>
</body>
</html>