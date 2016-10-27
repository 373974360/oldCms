<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>依申请公开业务</title>


<jsp:include page="../../../include/include_tools.jsp"/>
<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
<script language="javascript" src="../../../js/jquery.uploadify.js"></script>
<script language="javascript" src="../../../js/uploadFile/swfobject.js"></script>
<script language="javascript" src="../../../js/uploadTools.js"></script>
<script type="text/javascript" src="../js/ysqgk.js"></script>
<script type="text/javascript">
var defaultBean;
var site_id = jsonrpc.SiteRPC.getSiteIDByAppID('<%=request.getParameter("app_id")%>');
$(document).ready(function(){
	initButtomStyle();
	init_input();

	//上传文档
	publicUploadDOC("uploadify",true,false,"",0,'',site_id,"saveFile_url");
			
	defaultBean = YsqgkRPC.getYsqgkConfigBean();
	if(defaultBean)
	{
			$("#YsqgkConfig_table").autoFill(defaultBean);
			
			$("#template_form_name").val(getTemplateName(defaultBean.template_form),site_id);
			$("#template_list_name").val(getTemplateName(defaultBean.template_list),site_id);
			$("#template_content_name").val(getTemplateName(defaultBean.template_content),site_id);
			$("#template_over_name").val(getTemplateName(defaultBean.template_over),site_id);
			$("#template_print_name").val(getTemplateName(defaultBean.template_print),site_id);
			$("#template_query_name").val(getTemplateName(defaultBean.template_query),site_id);		
	}
});
function saveFile_url(url)
{
	$("#file_url").val(url);
}
</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<div id="YsqgkConfig_table" style="width:700px;">
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>参与方式：</th>
			<td>
				<ul>
					<li><input id="must_member" name="must_member" type="checkbox" value="1" /><label>仅会员</label></li>
				</ul>
			</td>
			<th>发布属性：</th>
			<td>
			<ul>
					<li><input id="is_auto_publish" name="is_auto_publish" type="checkbox" value="1"/><label for="a">自动发布</label></li>
				</ul>
			</td>
			<th>提醒方式：</th>
			<td>
				<ul>
					<li><input id="remind_type" name="remind_type" type="radio" checked="true" value="email"/><label>Email</label></li>
					<li><input id="remind_type" name="remind_type" type="radio" value="sms" /><label>手机短信</label></li>
				</ul>
			</td>
			<td></td>
		</tr>
		<tr>
			<th>信件编码字头：</th>
			<td><input id="code_pre" type="text" class="width80" value="" onblur="checkInputValue('code_pre',true,20,'信件编码字头','')"/></td>
			<th>日期码：</th>
			<td>
				<select id="code_rule" class="width100" >
					<option id="" value="yyMMdd" selected="selected">yyMMdd</option>
					<option value="yyMM" >yyMM</option>
					<option value="yyyyMM" >yyyyMM</option>
					<option value="yyyyMMdd" >yyyyMMdd</option>
				</select>

			</td>
			<th class="width60">随机位数：</th>
			<td width="250">
				<select id="code_num"  name="code_num" class="width100" >
					<option id="" value="3" selected="selected">3 位</option>
					<option value="4" >4 位</option>
					<option value="5" >5 位</option>
					<option value="6" >6 位</option>
				</select>
			</td>
			<td></td>
		</tr>
     </tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>办理时限：</th>
			<td>
				<input id="time_limit" name ="time_limit" type="text" class="width80" value="" />天
			</td>
		</tr>	
		<tr>
			<th>查询密码位数：</th>
			<td colspan="7">
				<select id="query_num" class="width100" >
					<option id="" value="4" selected="selected">4 位</option>
					<option value="5" >5 位</option>
					<option value="6" >6 位</option>
					<option value="7" >7 位</option>
					<option value="8" >8 位</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>公开申请表：</th>
			<td>
				 <div style="float:left;margin:auto;"><input id="file_url" name="file_url" type="text" style="width:250px;" value=""/></div>
				 <div style="float:left"><input id="uploadify" name="uploadify" type="file" class="width300"/></div>
			</td>
		</tr>
		<tr>
			<th>表单页模板：</th>
			<td>
				<input type="text" id="template_form_name" class="width200" readOnly="readOnly"><input type="hidden" id="template_form" class="width200" value="0"/><input type="button" value="选择" onclick="openSelectTemplate('template_form','template_form_name',site_id)"/>
			</td>
		</tr>
		<tr>
			<th>回执页模板：</th>
			<td>
				<input type="text" id="template_over_name" class="width200" readOnly="readOnly"><input type="hidden" id="template_over" class="width200" value="0"><input type="button" value="选择" onclick="openSelectTemplate('template_over','template_over_name',site_id)"/>
			</td>
		</tr>
		<tr>
			<th>列表页模板：</th>
			<td>
				<input type="text" id="template_list_name" class="width200" readOnly="readOnly"><input type="hidden" id="template_list" class="width200" value="0"><input type="button" value="选择" onclick="openSelectTemplate('template_list','template_list_name',site_id)"/>
			</td>
		</tr>
		<tr>
			<th>查询页模板：</th>
			<td  colspan="7">
			<input type="text" id="template_query_name" class="width200" readOnly="readOnly"><input type="hidden" id="template_query" class="width200" value="0"><input type="button" value="选择" onclick="openSelectTemplate('template_query','template_query_name',site_id)"/>
			</td>
		</tr>
		<tr>
			<th>详细查看模板：</th>
			<td  colspan="7">
				<input type="text" id="template_content_name" class="width200" readOnly="readOnly"><input type="hidden" id="template_content" class="width200" value="0"><input type="button" value="选择" onclick="openSelectTemplate('template_content','template_content_name',site_id)"/>
			</td>
		</tr>
		<tr>
			<th>信件打印页模板：</th>
			<td  colspan="7">
				<input type="text" id="template_print_name" class="width200" readOnly="readOnly"><input type="hidden" id="template_print" class="width200" value="0"><input type="button" value="选择" onclick="openSelectTemplate('template_print','template_print_name',site_id)"/>
			</td>
		</tr>	
	</tbody>
</table>
</div>
<!-- 分隔线开始 -->
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<!-- 分隔线结束 -->

<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="addYsqgkConfig()" value="保存" />
			<!--input id="btnReset" name="btn1" type="button" onclick="formReSet('YsqgkConfig_table',"")" value="重置" /-->	
			<input id="btnCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>		
</html>