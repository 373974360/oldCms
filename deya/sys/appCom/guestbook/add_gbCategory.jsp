<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>留言分类</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/gbCategoryList.js"></script>
<script type="text/javascript">

var cat_id = "${param.cat_id}";
var site_id = "${param.site_id}";
var topnum = "${param.topnum}";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
	if(cat_id != "" && cat_id != null)
	{
		defaultBean = GuestBookRPC.getGuestBookCategoryBean(cat_id);
		if(defaultBean)
		{
			$("#guestbook_table").autoFill(defaultBean);	
			reloadTemplate();			
		}
		
		$("#addButton").click(updateGuestbookCategory);
	}else
		$("#addButton").click(insertGuestbookCategory);
});

function reloadTemplate()
{
	$("#template_index_name").val(getTemplateName(defaultBean.template_index));
	$("#template_list_name").val(getTemplateName(defaultBean.template_list));
	$("#template_form_name").val(getTemplateName(defaultBean.template_form));
	$("#template_content_name").val(getTemplateName(defaultBean.template_content));
}
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="guestbook_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>分类名称：</th>
			<td class="width200">
				<input id="title" name="title" type="text" class="width200" value="" onblur="checkInputValue('title',false,80,'分类名称','')"/>
			</td>	
			<th>发布状态：</th>
			<td>
				 <ul>
					 <li><input type="radio" id="publish_status" name="publish_status" value="1"><label>已发布</label></li>
					 <li><input type="radio" id="publish_status" name="publish_status" value="0" checked="checked"><label>未发布</label></li>
				 </ul>
			</td>	
		</tr>
		<tr>
			<th>直接发布：</th>
			<td>
				<ul>
					 <li><input type="radio" id="direct_publish" name="direct_publish" value="1"><label>是</label></li>
					 <li><input type="radio" id="direct_publish" name="direct_publish" value="0" checked="checked"><label>否</label></li>
				 </ul>
			</td>	
			<th>参与方式：</th>
			<td>
				 <ul>
					 <li><input type="checkbox" id="is_member" name="is_member" value="1"><label>仅会员</label></li>					 
				 </ul>
			</td>	
		</tr>
		<tr>
			<th>仅回复后显示：</th>
			<td>
				<ul>
					 <li><input type="radio" id="is_receive_show" name="is_receive_show" value="1"><label>是</label></li>
					 <li><input type="radio" id="is_receive_show" name="is_receive_show" value="0" checked="checked"><label>否</label></li>
				 </ul>
			</td>	
			<th>验证码开启：</th>
			<td>
				 <ul>
					 <li><input type="radio" id="is_auth_code" name="is_auth_code" value="1" checked="checked"><label>是</label></li>
					 <li><input type="radio" id="is_auth_code" name="is_auth_code" value="0" ><label>否</label></li>				 
				 </ul>
			</td>	
		</tr>
		<tr>
			<th>主题列表模板：</th>
			<td colspan="3">
				<input id="template_index_name" name="template_index_name" type="text" class="width200" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_index" name="template_id_hidden" class="width200" value="0"/><input type="button" value="选择" onclick="openSelectTemplate('template_index','template_index_name',site_id)"/>
				&#160;<input type="button" value="清空" onclick="clearTemplate(this)"/>
			</td>			
		</tr>
		<tr>
			<th>留言列表页模板：</th>
			<td colspan="3">
				<input id="template_list_name" name="template_list_name" type="text" class="width200" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_list" name="template_id_hidden" class="width200" value="0"/><input type="button" value="选择" onclick="openSelectTemplate('template_list','template_list_name',site_id)"/>
				&#160;<input type="button" value="清空" onclick="clearTemplate(this)"/>
			</td>			
		</tr>
		<tr>
			<th>留言内容模板：</th>
			<td colspan="3">
				<input id="template_content_name" name="template_content_name" type="text" class="width200" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_content" name="template_id_hidden" class="width200" value="0"/><input type="button" value="选择" onclick="openSelectTemplate('template_content','template_content_name',site_id)"/>
				&#160;<input type="button" value="清空" onclick="clearTemplate(this)"/>
			</td>			
		</tr>
		<tr>
			<th>表单页模板：</th>
			<td colspan="3">
				<input id="template_form_name" name="template_form_name" type="text" class="width200" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_form" name="template_id_hidden" class="width200" value="0"/><input type="button" value="选择" onclick="openSelectTemplate('template_form','template_form_name',site_id)"/>
				&#160;<input type="button" value="清空" onclick="clearTemplate(this)"/>
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">备注：</th>
			<td  colspan="3">
				<textarea id="description" name="description" style="width:585px;;height:80px;"></textarea>		
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
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('guestbook_table',cat_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex);" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
