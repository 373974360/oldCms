<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>常用语维护</title>
<meta http-equiv="X-UA-Compatible" content="IE=8"/>
<meta name="generator" content="cicro-Builder"/>

<link type="text/css" rel="stylesheet" href="../../../styles/themes/default/tree.css"/>
<link type="text/css" rel="stylesheet" href="../../../styles/sq.css"/>

<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/phrasal.js"></script>
<script type="text/javascript">
var gph_id = "<%=request.getParameter("gph_id")%>";
var type = "<%=request.getParameter("type")%>";
var gph_type = "<%=request.getParameter("gph_type")%>";
var site_id = "<%=request.getParameter("site_id")%>";
var defaultBean = "";

$(document).ready(function(){

	initButtomStyle();
	init_FromTabsStyle();
	init_input();
    initUeditor("gph_content");
	iniAddPage();
	
	if(gph_id != "" && gph_id != "null" && gph_id != null)
	{
	    defaultBean = YsqgkRPC.getYsqgkPhrasalBean(gph_id);
	    if(defaultBean)
		{
			$("#Phrasal_table").autoFill(defaultBean);
            setV("gph_content",defaultBean.gph_content);
		}
		$("#saveBtn").click(updatePhrasal);
	}
	else
	{
		$("#gph_type").val(gph_type);
		$("#saveBtn").click(addPhrasal);
	}
});

function iniAddPage()
{
	$(".customLable li").click(function(){
        setV("gph_content",getV("gph_content") + $(this).attr("title"));
	});
}

</script>
<style> 
.customLable{}
.customLable li{ color:#333; cursor:pointer; text-decoration:underline;}
</style>

</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="Phrasal_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th></th>
			<td><input id="gph_id"  value="<%=request.getParameter("gph_id")%>" type="hidden"/></td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>所属操作类型：</th>
			<td>
				<select id="gph_type" class="input_select">
				    <option selected="selected"  value="0">登记回执</li>
					<option value="1">全部公开</option>
					<option value="2">部分公开</option>
					<option value="3">不予公开</option>
					<option value="4">非本单位信息</option>
					<option value="5">信息不存在</option>
				</select>
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>常用语短名：</th>
			<td>
				<input id="gph_title" name="gph_title" type="text" class="width300" value="" onblur="checkInputValue('gph_title',false,60,'常用语','')"/>
			</td>
		</tr>
		<tr>
			<th>可选标签：</th>
			<td>
				<ul class="customLable">
					<li title="{name}">姓名</li>
					<li title="{ysq_code}">申请单号</li>
					<li title="{put_dtime}">申请日期</li>
					<li title="{now_time}">当前日期</li>
					<li title="{node_name}">处理部门</li>
					<li title="{email}">申请人邮箱</li>
				</ul>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;"><span class="f_red">*</span>详细内容：</th>
			<td>
                <script id="gph_content" type="text/plain" style="width:620px;height:205px;"></script>
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
			<input id="saveBtn" name="saveBtn" type="button" onclick="" value="保存" />
			<!--input id="resetBtn" name="resetBtn" type="button" onclick="formReSet('Phrasal_table',"")" value="重置" /-->
			<input id="viewCancel" name="btn1" type="button" onclick="goBack()" value="关闭" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>