<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>回复留言板</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/guestBookList.js"></script>
<script type="text/javascript">

var gbs_id = "${param.gbs_id}";
var id = "${param.id}";
var topnum = "${param.topnum}";
var site_id = "${param.site_id}";
var cat_id = "${param.cat_id}";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
	
	if(id != "" && id != null)
	{
		defaultBean = GuestBookRPC.getGuestBookBean(id);
		if(defaultBean != null)
		{
			$("#guestbook_table").autoFill(defaultBean);	
		}

		$("#guestbook_table input").attr("disabled","disabled");
		$("#guestbook_table textarea").attr("disabled","disabled");
	}
	getGBClassList();
});



</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<input type="hidden" id="reply_time" name="reply_time" value="">
<table id="guestbook_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>		
		<tr>
			<th>标题：</th>
			<td colspan="3">
				<input id="title" name="title" type="text" class="width500" value="" onblur=""/>
			</td>			
		</tr>
		<tr>			
			<th>昵称：</th>
			<td class="width200">
				<input id="nickname" name="nickname" type="text" class="width200" value="" onblur=""/>
			</td>
			<th class="class_tr hidden">所属分类：</th>
			<td class="class_tr hidden">
				<select id="class_id" name="class_id" class="width200">
					<option value=""></option>
				</select>
			</td>
		</tr>
		<tr>
			<th>真实姓名：</th>
			<td class="width200">
				<input id="realname" name="realname" type="text" class="width200" value="" onblur=""/>
			</td>	
			<th>身份证号：</th>
			<td >
				<input id="idcard" name="idcard" type="text" class="width200" value="" onblur=""/>
			</td>	
		</tr>
		<tr>
			<th>年龄：</th>
			<td class="width200">
				<input id="age" name="age" type="text" class="width200" value="" onblur=""/>
			</td>	
			<th>性别：</th>
			<td >
				<select id="sex" name="sex" class="width155"><option value="1">男</option><option value="0">女</option></select>
			</td>	
		</tr>
		<tr>
			<th>移动电话：</th>
			<td class="width200">
				<input id="phone" name="phone" type="text" class="width200" value="" onblur=""/>
			</td>	
			<th>固定电话：</th>
			<td >
				<input id="tel" name="tel" type="text" class="width200" value="" onblur=""/>
			</td>	
		</tr>
		<tr>
			<th>职业：</th>
			<td class="width200">
				<input id="vocation" name="vocation" type="text" class="width200" value="" onblur=""/>
			</td>	
			<th>邮编：</th>
			<td >
				<input id="post_code" name="post_code" type="text" class="width200" value="" onblur=""/>
			</td>	
		</tr>
		<tr>			
			<th>联系地址：</th>
			<td colspan="3">
				<input id="address" name="address" type="text" class="width500" value="" onblur=""/>
			</td>	
		</tr>
		<tr>
			<th style="vertical-align:top;">内容：</th>
			<td  colspan="3">
				<textarea id="content" name="content" style="width:585px;;height:150px;"></textarea>		
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">回复内容：</th>
			<td  colspan="3">
				<textarea id="reply_content" name="reply_content" style="width:585px;;height:200px;"></textarea>		
			</td>
		</tr>
		<tr>
			<th>回复状态：</th>
			<td>
				<ul>
					 <li><input type="radio" id="reply_status" name="reply_status" value="0" checked="checked"><label>未回复</label></li>
					 <li><input type="radio" id="reply_status" name="reply_status" value="1"><label>已回复</label></li>
				 </ul>
			</td>	
			<th>回复时间：</th>
			<td>
				 <ul>
					 <input id="reply_time" name="reply_time" type="text" class="width200" value="" onblur="" readOnly="readOnly"/> 
				 </ul>
			</td>	
		</tr>
		<tr>			
			<th>发布状态：</th>
			<td>
				 <ul>
					 <li><input type="radio" id="publish_status" name="publish_status" value="0" checked="checked"><label>未发布</label></li>
					 <li><input type="radio" id="publish_status" name="publish_status" value="1" ><label>已发布</label></li>				 
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
			<input id="userAddCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex);" value="关闭" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
