<%@ page contentType="text/html; charset=utf-8"%>
<%
	String member_id = request.getParameter("member_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>政务信息管理平台</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/memberList.js"></script>
<script type="text/javascript">

	var me_id = "<%=member_id%>";
	var oldregisterBean = MemberManRPC.getMemberRegisterBeanByID(me_id);
	var oldmemberBean = MemberManRPC.getMemberBeanByID(me_id);
	
	$(document).ready(function(){
	initButtomStyle();
	init_input();
	initPage();
	
});

function initPage()
{
	$("#me_realname").val(oldregisterBean.me_realname);
	$("#me_account").val(oldregisterBean.me_account);
	$("#member_table").autoFill(oldmemberBean);
}

function saveStatus()
{
	$("#member_table").autoBind(oldmemberBean);
	if(!MemberManRPC.updateMember(oldmemberBean))
	{
		top.msgWargin("会员信息"+WCMLang.Set_fail);
		return;
	}
	top.msgAlert("会员信息"+WCMLang.Set_success);
	top.getCurrentFrameObj().reloadMemberList();
	top.CloseModalWindow();
}

</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="member_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>真实姓名：</th>
			<td>
				<input id="me_realname" name="me_realname" type="text" class="width200" value="" disabled="disabled"/>
			</td>
		</tr>
		<tr>
			<th>登录名：</th>
			<td>
				<input id="me_account" name="me_account" type="text" class="width200" value="" disabled="disabled"/>
			</td>
		</tr>
		<tr>
			<th>用户状态：</th>
			<td >
			<ul>
				<li>
				<input id="me_status" name="user_status" type="radio" value="1" checked="true"/><label>正常</label>
				</li>
				<li>
				<input id="me_status" name="user_status" type="radio" value="0"/><label>待审</label>
				</li>
				<li>
				<input id="me_status" name="user_status" type="radio" value="-1"/><label>禁用</label>
				</li>
			</ul>
			</td>
		</tr>
	</tbody>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
	<table class="table_option" class="table_form" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="middle" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="saveStatus()" value="保存" />
				<input id="btn2" name="btn6" type="button" onclick="initPage()" value="重置" />
				<input id="btn4" name="btn4" type="button" onclick="top.CloseModalWindow()" value="取消" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>
