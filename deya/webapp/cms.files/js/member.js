var MemberManRPC = jsonrpc.MemberManRPC;
var memberBean = null;
$(document).ready(function() {	
	showMemberInfoDiv();	
});

function showMemberInfoDiv()
{
	memberBean = MemberManRPC.getMemberBySession();
	if(memberBean != null)
	{
		$("#member_info_div #me_nickname").text(memberBean.me_nickname);
		$("#member_info_div").show();
		$("#member_login_div").hide();
	}
	return memberBean;
}

function memberLogin(name_input,pass_input)
{
	if($("#"+name_input).val() == "")
	{
		alert("请输入登录名");
		return;
	}
	if($("#"+pass_input).val() == "")
	{
		alert("请输入密码");
		return;
	}

	var login_result = MemberManRPC.memberLogin($("#"+name_input).val(),$("#"+pass_input).val());
	if(login_result == "true")
	{
		$("#"+name_input).val("");
		$("#"+pass_input).val("");
		showMemberInfoDiv();
	}
	if(login_result == "false")
	{
		alert("登录名或密码错误");		
		return;
	}
	if(login_result == "0")
	{
		alert("该会员申请还在审核中，静请等待");
		return;
	}
	if(login_result == "-1")
	{
		alert("该帐号已被停用");
		return;
	}
}

function memberLogout()
{
	if(MemberManRPC.logout())
	{
		$("#member_info_div").hide();
		$("#member_login_div").show();
	}
}