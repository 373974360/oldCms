<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title>禁言管理</title> 	
	<jsp:include page="../../include/include_tools.jsp"/>
	<script type="text/javascript" src="js/liveManager.js"></script>
	
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var id = parent.id;
		var sub_id = parent.sub_id;
		
		$(document).ready(function () {	
			subjectBean = parent.subjectBean;
			initButtomStyle();
			//主题状态为直播状态
			if(subjectBean.status == 1)
			{
				var p_type = ChatRPC.getProhibitType(sub_id);				
				$("input").each(function(){
				
					if($(this).val() == p_type)
					{
						$(this).attr("checked",true)
					}
				})
				
				initTable();	
				showList();
				setInterval("showList()",6000);
			}
		});

		//设置禁言类型
		function setProhibitType(p_type)
		{
			ChatRPC.setProhibitType(sub_id,p_type);
		}
	//-->
	</SCRIPT>	
</head> 
<body topmargin="0" leftmargin="0" style="background: #ECF4FC;"> 
	<input type="hidden" id="handleId" name="handleId" value="H23001">
	<table id="tables" border="0" cellpadding="0" cellspacing="0" width="98%" class="table_form" align="center">
	  <tr><td>禁言控制：<input type="radio" name="pro_type" name="pro_type" value="uname" onclick="setProhibitType('uname')">昵称控制&nbsp;&nbsp;<input type="radio" name="pro_type" name="pro_type" value="ip"  onclick="setProhibitType('ip')">IP控制</td></tr>
	  <tr><td><div id="table"></div></td></tr>
	</table>
	
</body>  
</html>