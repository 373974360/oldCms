$(document).ready(function() {
	init_input();
	iniLoginForm();
});

function showIndexPageMemeber()
{
	var memberBean = jsonrpc.MemberManRPC.getMemberBySession();
	if(memberBean != null)
	{
		$("#loginForm").hide();
		$("#member_info td").eq(0).text(memberBean.me_realname);
		$("#member_info td").eq(1).text(memberBean.me_nickname);
		$("#member_info").show();
	}
}

function iniLoginForm()
{
	var validator = $("#loginForm").validate({
		rules: {
			me_account: {
				required: true
			},
			me_password:{
				required: true
			},
			auth_code:{
				required: true,
				remote:{
                            url:"/member/userCheked.jsp"
                            ,type:"post"
                            ,data:{"action_type":"security_code","me_account":function (){
                                return $("#me_account").val() ;
                            }
						}
				}
			}
		},
		messages: {
			me_account: {
				required: "请输入帐号！"
			},
			me_password: {
				required: "请输入密码！"
			},
			auth_code: {
				required: "请输入验证码！",
				remote: jQuery.format("验证码不正确。")
			}
			
		},
		
		errorPlacement: function(error, element) {
			error.appendTo( element.parent("td").next("td") );
		},

		submitHandler: function(form) {
			//alert("验证成功!");
			memberLoginChekced($("#me_account").val(),$("#me_password").val());
			return;
		},

		success: function(label) {
			//label.text("ok!").addClass("success");
		}
	});	
}

function memberLoginChekced(me_account,me_password)
{
	var MemberManRPC = jsonrpc.MemberManRPC;
	var login_result = MemberManRPC.memberLogin(me_account,me_password);
	if(login_result == "true")
	{
		var url = MemberManRPC.getUrlFoSesson();
		if(url != "" && url != null)
			window.location.href = url;
		else
			window.location.href = "/";
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
	if(jsonrpc.MemberManRPC.logout())
	{
		$("#member_info").hide();
		$("#loginForm input").val();
		$("#loginForm").show();
	}
}