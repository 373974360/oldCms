<%@ page contentType="text/html; charset=utf-8"%>
<%
	String ph_id = request.getParameter("ph_id");
	String type = request.getParameter("type");
	String ph_type = request.getParameter("ph_type");
    String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>常用语管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/commonLang_add.js"></script>
<script type="text/javascript">

var ph_id = "<%=ph_id%>";
var type = "<%=type%>";// 区分添加和修改
var ph_type = "<%=ph_type%>"// 常用语类型
var site_id = "<%=site_id%>"// 常用语类型

$(document).ready(function(){
	initButtomStyle();
	init_input();
    initUeditor("ph_content");
	iniPage();
});

</script>
<style>
.customLable{}
.customLable li{ color:#333; cursor:pointer; text-decoration:underline;}
</style>
</head>
<body>

<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="add_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>所属操作类型：</th>
			<td colspan="7">
				<select id="ph_type" class="input_select">
					<option selected="selected" value="0">受理</option>
					<option value="1">回复</option>
					<option value="2">转办</option>
					<option value="3">交办</option>
					<option value="4">呈办</option>
					<option value="5">重复件</option>
					<option value="7">不予受理</option>
					<option value="8">申请延时</option>
					<option value="9">延时通过</option>
					<option value="10">延时打回</option>
					<option value="11">审核通过</option>
					<option value="12">审核打回</option>
					<option value="13">督办</option>
				</select>
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>常用语短名：</th>
			<td>
				<input id="ph_title" name="ph_title" type="text" class="width300" value="" onblur="checkInputValue('ph_title',false,60,'标题','')"/>
			</td>
		</tr>
		<tr>
			<th>可选标签：</th>
			<td>
				<ul class="customLable">
					<li title="{sq_code}">信件编码</li>
					<li title="{sq_realname}">投信人姓名</li>
					<li title="{sq_email}">投信人Email</li>
					<li title="{sq_title2}">信件标题</li>
					<li title="{submit_name}">收信部门</li>
					<li title="{sq_dtime}">递交时间</li>
					<li title="{model_cname}">递交渠道</li>
					<li title="{dept_name}">处理部门</li>
					<li title="{dtime}">当前时间</li>
				</ul>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;"><span class="f_red">*</span>详细内容：</th>
			<td>
                <script id="ph_content" type="text/plain" style="width:620px;height:200px;"></script>
			</td>
		</tr>
	</tbody>
</table>
<!--隔线开始-->
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<!--隔线结束-->
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="btn1" name="btn1" type="button" onclick="" value="保存" />
			
			<input id="btn3" name="btn3" type="button" onclick="backCommonLang()" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>

</body>
</html>

