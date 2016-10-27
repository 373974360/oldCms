$(document).ready(function() {
	initRegForm();
});

function initRegForm()
{
	var validator = $("#regForm").validate({
		rules: {
			me_account: {
				required: true,
				rangelength: [4,18],
				remote:{
                            url:"/member/userCheked.jsp"
                            ,type:"post"
                            ,data:{"action_type":"account","me_account":function (){
                                return $("#me_account").val() ;
                            }
						}
				}
			},
			me_password:{
				required: true,
				rangelength: [6,18]
			},
			me_password2:{
				required: true,
				equalTo: "#me_password"
			},
			me_realname:{
				required: true,
				rangelength: [2,20]
			},
			me_phone:{
				required: true,
				number:true,
				rangelength: [7,11]
			},
			me_email:{
				required: true,
				email: true
			},
			me_age:{
				number:true,
				range: [0,120]
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
				required: "请输入帐号！",
				rangelength: jQuery.format("4-18位之间，请用英文小写、数字组成。"),
				remote: jQuery.format("{0} 已被人注册。")
			},
			me_password: {
				required: "请输入密码！",
				rangelength: jQuery.format("6-16位字符（字母、数字、符号），区分大小写。")
			},
			me_password2: {
				required: "请输入确认密码！",
				equalTo: jQuery.format("请确认两次输入密码保持一致。")
			},
			me_realname:{
				required: "请输入真实姓名！",
				rangelength: jQuery.format("2-20位小写字母或汉字。")
			},
			me_phone:{
				required: "请输入移动电话号码！",
				rangelength: jQuery.format("请正确输入移动电话号码。"),
				number: jQuery.format("不能含有非数字字符。")
			},
			me_email:{
				required: "请输入Email地址！",
				email: jQuery.format("请正确输入Email地址")
			},
			me_age:{
				number: jQuery.format("不能含有非数字字符。"),
				range: jQuery.format("请正确输入年龄。")
			},
			auth_code:{
				required: jQuery.format("请输入验证码。"),
				remote: jQuery.format("验证码不正确。")
			}
			
		},
		
		errorPlacement: function(error, element) {
			error.appendTo( element.parent("td").next("td") );
		},

		submitHandler: function(form) {
			
			form.submit();//提交表单
		},

		success: function(label) {
			label.text("ok!").addClass("success");
		}
	});
}

function passSubmit(obj)
{
	if($(obj).is(':checked'))
		$('input[type="submit"]').removeAttr("disabled");
	else
		$('input[type="submit"]').attr("disabled",true);
}