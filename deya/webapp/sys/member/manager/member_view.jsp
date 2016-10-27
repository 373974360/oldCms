<%@ page contentType="text/html; charset=utf-8"%>
<%
	String type = request.getParameter("type");
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

	var type = "<%=type%>";
	var me_id = "<%=member_id%>";
	var oldregisterBean = MemberManRPC.getMemberRegisterBeanByID(me_id);
	var oldmemberBean = MemberManRPC.getMemberBeanByID(me_id);
	var defaultBean;

$(document).ready(function(){
	getMemberCategoryList();
	initButtomStyle();
	init_input();	
	init_page();
});

function init_page()
{
	if(type=="view")
	{
		fillBean();
		$("#pwd_table").hide();
		disabledWidget();
	}
	else if(type=="add") 
	{
		$("#memAddButton").click(saveMember);
		$("#viewType").hide();
		$("#addType").show();
	}
	else if(type=="update") 
	{
		fillBean();
		$("#viewType").hide();
		$("#addType").show();
		setCloseType();
	}
}

function fillBean()
{
	$("#me_account").val(oldregisterBean.me_account);
	$("#me_password").val(oldregisterBean.me_password);
	$("#member_table").autoFill(oldmemberBean);
}

function setCloseType()
{
	$("#memAddButton").click(saveUpdate);
	
	$("#memAddReset").unbind("click");
	$("#memAddReset").click(fillBean);
	$("#me_account").attr("disabled","disabled");
}

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="member_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>会员分类：</th>
			<td>
				<select id="mcat_id" name="mcat_id" class="width205"><option value=""></option></select>
			</td>
			
		</tr>
		<tr id>
			<th><span class="f_red">*</span>真实姓名：</th>
			<td >
				<input id="me_realname" name="me_realname" type="text" class="width200" value="" onblur="checkInputValue('me_realname',false,60,'真实姓名','')"/>
			</td>
			<th>性别：</th>
			<td >
				<ul>
				<li>
				<input id="me_sex" name="me_sex" type="radio" value="0" checked="true"/><label>女</label>
				</li>
				<li>
				<input id="me_sex" name="me_sex" type="radio" value="1"/><label>男</label>
				</li>
				</ul>
			</td>
		</tr>
		<tr>
			<th>昵称：</th>
			<td>
				<input id="me_nickname" name="me_nickname" type="text" class="width200" value="" onblur="checkInputValue('me_nickname',true,60,'昵称','')"/>
			</td>
			<th>年龄：</th>
			<td >
				<input id="me_age" name="me_age" type="text" class="width200" maxlength="2" onkeypress="checkNumberKey()" value="" />
			</td>
		</tr>		
		<tr>
			<th>身份证号：</th>
			<td >
				<input id="me_card_id" name="me_card_id" type="text" class="width200" value="" />
			</td>
		</tr>		
		<tr>			
			<th>Email：</th>
			<td >
				<input id="me_email" name="me_email" type="text" class="width200" value="" />
			</td>
			<th>职业：</th>
			<td >
				<input id="me_vocation" name="me_vocation" type="text" class="width200" value="" onblur="checkInputValue('me_vocation',true,60,'职业','')"/>
			</td>
		</tr>
		<tr>
			<th>移动电话：</th>
			<td >
				<input id="me_phone" name="me_phone" type="text" class="width200" value="" />
			</td>
			<th>固定电话：</th>
			<td >
				<input id="me_tel" name="me_tel" type="text" class="width200" value=""  />
			</td>
		</tr>
		<tr>
			<th >地址：</th>
			<td colspan="3">
				<input id="me_address" name="me_address" type="text" class="width585" value="" onblur="checkInputValue('me_address',true,250,'地址','')"/>
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>会员帐号：</th>
			<td class="width250">
				<input id="me_account" name="me_account" type="text" class="width200" value="" onblur="checkInputValue('me_account',false,60,'会员帐号','')"/>
			</td>
			<th><span class="f_red">*</span>密码：</th>
			<td >
				<input id="me_password" name="me_password" type="password" class="width200" value="" onblur="checkInputValue('me_password',false,20,'密码','')"/>
			</td>
		</tr>
		<tr>
			
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
			<th>帐号类型：</th>
			<td >
			<ul>
				<li>
				<input id="register_type" name="register_type" type="radio" value="0" checked="true"/><label>登录名</label>
				</li>
				<li>
				<input id="register_type" name="register_type" type="radio" value="1"/><label>手机</label>
				</li>
				<li>
				<input id="register_type" name="register_type" type="radio" value="2"/><label>邮箱</label>
				</li>
			</ul>
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
			<div id ="viewType">
			<input id="AddCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="关闭" />	
			</div>
			<div id="addType" style="display:none" >
			<input id="memAddButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="memAddReset" name="btn1" type="button" onclick="formReSet('member_table',me_id)" value="重置" />
			<input id="memAddCancel" name="btn1" type="button" onclick="window.history.go(-1)" value="取消" />	
			</div>
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
