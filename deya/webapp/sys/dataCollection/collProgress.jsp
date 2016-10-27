<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String ruleID = request.getParameter("rule_id");
String pageType=request.getParameter("pageType");
%>
<html>
<head> 
<title>采集进度</title> 
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript" src=/sys/js/tools.js></script>
<script type="text/javascript" src="js/collProgress.js"></script>
<script type="text/javascript">
var rule_id = "<%=ruleID%>";
var page = "<%=pageType%>";
var clearTime = "";

$(document).ready(function(){
	initProgressState();
	initRuleName();
});

function initProgressState(){
	if(page=="ruleList"){  //区分是从开始采集过来的还是点击别的菜单过来的
		$("#btnStart").attr("disabled","true");
		$("#btnParse").removeAttr("disabled"); 
		$("#btnEnd").removeAttr("disabled");
		clearTime = true; 
		startCollection();
	}else{
		var RuleIdlist = CollectionDataRPC.getAllCollIsRuning();
		    RuleIdlist = List.toJSList(RuleIdlist);
		if(RuleIdlist.size()>0)
		{
			for(var i=0;i<RuleIdlist.size();i++){
				rule_id = RuleIdlist.get(i);
			}
			if(rule_id != null && rule_id != "")
			{
				clearTime = false;
				readCollLog(rule_id);
			}
			$("#btnStart").attr("disabled","true");
			$("#btnParse").removeAttr("disabled"); 
			$("#btnEnd").removeAttr("disabled"); 
		}else{
			$("#btnStart").attr("disabled","true");
			$("#btnParse").attr("disabled","true");
			$("#btnEnd").attr("disabled","true");
		}
	}
}
</script>
</head>
<body>
    <table class="table_option fromTabs" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<th style="text-align:left;width:5%">采集任务:</th>
			<td align="left" valign="middle">
				<span id="ruleName" style="color:red;"></span>
			</td>
			<td width="80%" style="text-align:right; padding-right:30px;">
				<span>
					<input type="button" name="btn1" id="btnStart"  value="开始" onclick="startColl()" />
					<input type="button" name="btn1" id="btnParse"  value="暂停" onclick="parseColl()" />
					<input type="button" name="btn1" id="btnEnd"  value="停止" onclick="stopColl()" />
				</span>
			</td>
		</tr>
	</table>
    <div style="width:100%;">
    	<table id="collLogInfo" class="table_form" border="0" cellpadding="0" cellspacing="0">
    		
    	</table>
    </div>
</body>
</html>
