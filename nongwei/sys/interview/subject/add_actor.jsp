<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>访谈主题分类添加</title> 
	<jsp:include page="../../include/include_tools.jsp"/>
	<script src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
	<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
	<script language="javascript" src="../../js/jquery.uploadify.js"></script>
	<script language="javascript" src="../../js/uploadFile/swfobject.js"></script>
	<script src="js/actorList.js"></script>	
	<SCRIPT LANGUAGE="JavaScript">
	<!--
	var topnum = request.getParameter("topnum");
	var site_id = request.getParameter("site_id");
	var sub_id = request.getParameter("sub_id");
		if(sub_id == "" || sub_id == null)
		{
			window.close();
		}

		var defaultBean = subjectActor;
		$(document).ready(function () {		
			initUPLoadImg("pic_path_file","pic_path");
			var a_id = request.getParameter("id");
			
			if(a_id != null && a_id.trim() != "")
			{
				defaultBean = SubjectRPC.getSubActor(a_id);
				
				if(defaultBean)
				{
					$("#subActor").autoFill(defaultBean);
					showPhoto(defaultBean.pic_path);
				}
				$("#addButton").click(saveActorBefore);
			}
			else
			{
				$("#addButton").click(saveActorBefore);
			}

			initButtomStyle();
			init_input();
		}); 		
		
		function showPhoto(url)
		{
			if(url.trim() != "")
				$("#photo_div").html("<img src='"+url+"' align='center' width='160px' height='190px'/>");
			else
				$("#photo_div").html("");
		}

	//-->
	</SCRIPT>	
</head> 
<body> 
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="subActor" class="table_form" border="0" cellpadding="0" cellspacing="0">
<!-- 隐含字段区域　开始 -->
			 <input type="hidden" id="id" name="id" value="0"/>
			 <input type="hidden" id="add_user" name="add_user"/> <!-- 登录人ID -->	 
			 <input type="hidden" id="user_name" name="user_name"/>
			 <!-- 隐含字段区域　结束 -->
	<tbody>
		<tr>
			<th rowspan="7"></th>
			<td rowspan="7" width="180px" align="center" valign="middle">
				<div id="photo_div" style="width:90%;height:190px;border:1px solid #D7E1F0;margin-top:10px;margin-bottom:10px"></div>
				<div><input type="text" id="pic_path" name="pic_path" style="width:160px" onblur="showPhoto(this.value)" onfocus="showPhoto(this.value)"></div>
				<div><input type="text" id="pic_path_file" name="pic_path_file" ></div>
			</td>
			
		 </tr>
		 <tr>			
			<th><span class="f_red">*</span>姓名：</th>
			<td width="110px"><input type="text"  id="actor_name" name="actor_name" style="width:100px" onblur="checkInputValue('actor_name',false,30,'姓名','')"></td>
			<th>年龄：</th>
			<td><input type="text"  id="age" name="age" style="width:100px" maxlength="3" onblur="checkInputValue('age',true,3,'年龄','checkInt')"></td>
		 </tr>
		 <tr>
			<th>性别：</th>
			<td>
				<input type="radio" id="sex" name="sex" value="男" checked="checked"><label>男</label>
				<input type="radio" id="sex" name="sex" value="女"><label>女</label>
			</td>
			<th>职务：</th>
			<td><input type="text"  id="a_post" name="a_post" style="width:100px" onblur="checkInputValue('a_post',true,80,'职务','')"></td>
		 </tr>
		 <tr>
			<th>邮箱：</th>
			<td colspan="4"><input type="text"  id="email" name="email" style="width:260px" onblur="checkInputValue('email',true,30,'邮箱','checkEmail')"></td>			
		 </tr>
		 <tr>
			<th>单位：</th>
			<td colspan="4"><input type="text"  id="company" name="company" style="width:260px" onblur="checkInputValue('company',true,80,'单位','')"></td>
		 </tr>
		 <tr>
			<th>地址：</th>
			<td colspan="4"><input type="text"  id="address" name="address" style="width:260px" onblur="checkInputValue('address',true,80,'地址','')"></td>			 
		 </tr>
		 <tr>
			<th style="vertical-align:top;">简介：</th>
			<td colspan="4"><textarea  id="description" name="description" style="width:260px;height:100px" onblur="checkInputValue('description',true,3000,'简介','')"></textarea></td>			 
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
			<input id="addReset" name="btn1" type="button" onclick="formReSet('table','id')" value="重置" />
			<input id="addCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex);" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>	
</body> 

</html> 

