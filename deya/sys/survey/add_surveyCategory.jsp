<%@ page contentType="text/html; charset=utf-8"%>
<%
String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>维护问卷分类</title>


<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript" src="js/surveyCategoryList.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	<!--
var site_id = "<%=site_id%>";
var c_id = request.getParameter("c_id");
if(c_id == "" || c_id == null)
{
	window.close();
}

var defaultBean = SurveyCategory;
$(document).ready(function () {				
	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
				
	if(c_id != null && c_id.trim() != "")
	{
		defaultBean = SurveyCategoryRPC.getSurveyCategoryBean(c_id);

		if(defaultBean)
		{
			$("#surveyCategory").autoFill(defaultBean);	
			reloadTemplate();			
		}
		
		$("#addButton").click(saveSurveyCategory);
	}
	else
	{
		$("#addButton").click(saveSurveyCategory);
	}
}); 		

function reloadTemplate()
{
	$("#template_list_path_name").val(getTemplateName(defaultBean.template_list_path));
	$("#template_content_path_name").val(getTemplateName(defaultBean.template_content_path));
	$("#template_result_path_name").val(getTemplateName(defaultBean.template_result_path));
}
		

	//-->
	</SCRIPT>	
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<div id="surveyCategory">
<!-- 隐含字段区域　开始 -->
 <input type="hidden" id="id" name="id" value="0"/>
 <input type="hidden" id="add_user" name="add_user"/>
 <input type="hidden" id="category_id" name="category_id"/>
 <input type="hidden" id="publish_time" name="publish_time"/>
 <!-- 隐含字段区域　结束 -->
<table id="jcxx_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>分类名称：</th>
			<td>
				<input id="c_name" name="c_name" type="text" class="width200" value="" onblur="checkInputValue('c_name',false,80,'问卷分类名称','')"/>
				<input type="hidden" id="id" name="id" value="0"/>
			</td>			
		</tr>		
		<tr>
			<th style="vertical-align:top;">问卷分类说明：</th>
			<td colspan="3">
				<textarea id="description" name="description" style="width:300px;height:50px;" onblur="checkInputValue('description',true,1000,'问卷分类说明','')"></textarea>		
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>列表页模板：</th>
			<td>
				<input id="template_list_path_name" name="template_list_path_name" type="text" class="width200" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_list_path" class="width200"/><input type="button" value="选择" onclick="openSelectTemplate('template_list_path','template_list_path_name',site_id)"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>内容页模板：</th>
			<td>
				<input id="template_content_path_name" name="template_content_path_name" type="text" class="width200" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_content_path" class="width200"/><input type="button" value="选择" onclick="openSelectTemplate('template_content_path','template_content_path_name',site_id)"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>查看结果页模板：</th>
			<td>
				<input id="template_result_path_name" name="template_result_path_name" type="text" class="width200" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_result_path" class="width200"/><input type="button" value="选择" onclick="openSelectTemplate('template_result_path','template_result_path_name',site_id)"/>
			</td>			
		</tr>
		 <tr>		 
		  <th>发布状态：</th>
		  <td >
			<ul>
				<li><input type="radio" id="publish_status" name="publish_status" value="1"><label>已发布</label></li>
				<li><input type="radio" id="publish_status" name="publish_status" value="0" checked="checked"><label>未发布</label></li>
				<li><input type="radio" id="publish_status" name="publish_status" value="-1" ><label>已撤消</label></li>
			</ul>
		  </td>
		 </tr>
	</tbody>
</table>
</div>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('surveyCategory',c_id);reloadTemplate();" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="window.location.href='surveyCategoryList.jsp?site_id=<%=site_id%>'" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>

</form>
</body>
</html>
