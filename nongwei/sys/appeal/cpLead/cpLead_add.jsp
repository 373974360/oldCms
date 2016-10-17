<%@ page contentType="text/html; charset=utf-8"%>
<%
	String lead_id = request.getParameter("lead_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>维护领导信息</title>


<jsp:include page="../../include/include_tools.jsp"/>
<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
<script language="javascript" src="../../js/jquery.uploadify.js"></script>
<script language="javascript" src="../../js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="js/cpLead.js"></script>
<script type="text/javascript">

var lead_id = "<%=lead_id%>";
var site_id = jsonrpc.SiteRPC.getSiteIDByAppID("appeal");
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	initUPLoadImg("fileName","lead_img");
	if(lead_id != "" && lead_id != "null" && lead_id != null)
	{
		defaultBean = CpLeadRPC.getCpLeadById(lead_id);
		if(defaultBean)
		{
			$("#cpLead_table").autoFill(defaultBean);					
		}
		$("#addButton").click(updateCpLead);
	}
	else
	{
		$("#addButton").click(addCpLead);
	}
});



</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="cpLead_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>领导姓名：</th>
			<td colspan="3">
				<input id="lead_id" name="lead_id" type="hidden" value="<%=lead_id %>"/>
				<input id="lead_name" name="lead_name" type="text" class="width300" value="" onblur="checkInputValue('lead_name',false,240,'领导姓名','')"/>
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>所属部门：</th>
			<td colspan="3">
				<input id="dept_id" name="dept_id" type="hidden"  value=""/>
				<input id="dept_name" name="dept_name" type="text" class="width300" value="" readOnly/>
				<input id="btn3" name="btn3" type="button" value="选择部门" onclick="openSelectCpDeptPage('选择部门','setCpDept')"/>
			</td>
		</tr>
		<tr>
			<th>领导照片：</th>
			<td colspan="3">
				<div style="float:left"><input id="lead_img" name="lead_img" type="text" style="width:500px" value="" onblur="checkInputValue('lead_img',true,250,'领导照片','')"/></div>
				<div style="float:left"><input id="fileName" name="fileName" type="file" class="" value="浏览"/></div>
			</td>
		</tr>
		<tr>
			<th>领导职务：</th>
			<td colspan="3">
				<input id="lead_job" name="lead_job" type="text" class="width585" value="" onblur="checkInputValue('lead_job',true,240,'领导职务','')"/>
			</td>
		</tr>		
		<tr>
			<th>领导简介(URL)：</th>
			<td colspan="3">
				<input id="lead_url" name="lead_url" type="text" class="width585" value="http://" onblur="checkInputValue('lead_url',true,240,'领导简介','')"/>
			</td>
		</tr>
		
		<tr>
			<th style="vertical-align:top;">领导描述：</th>
			<td  colspan="3">
				<textarea id="lead_memo" name="lead_memo" style="width:585px;;height:80px;" onblur="checkInputValue('lead_memo',true,450,'领导描述','')"></textarea>
			</td>
		</tr>	
	</tbody>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('cpLead_table',lead_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="window.location.href = 'cpLeadList.jsp';" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
