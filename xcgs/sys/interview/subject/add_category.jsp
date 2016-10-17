<%@ page contentType="text/html; charset=utf-8"%>
<%
String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>访谈模型添加</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/subjectCategory.js"></script>
<script type="text/javascript">
var site_id = "<%=site_id%>";

var defaultBean = subjectCategory;
$(document).ready(function(){
initButtomStyle();
init_input();

var s_id = request.getParameter("id");			
if(s_id != null && s_id.trim() != "")
{
	defaultBean = SubjectRPC.getSubjectCategoryBeanByID(s_id);
	
	if(defaultBean)
	{
		$("#subCategory").autoFill(defaultBean);
		if(defaultBean.is_comment == 1)
			showCommitTD(1);

		$("#m_forecast_path_name").val(getTemplateName(defaultBean.m_forecast_path));
		$("#m_hlist_path_name").val(getTemplateName(defaultBean.m_hlist_path));
		$("#m_on_path_name").val(getTemplateName(defaultBean.m_on_path));
		$("#m_rlist_path_name").val(getTemplateName(defaultBean.m_rlist_path));
		$("#m_rcontent_list_name").val(getTemplateName(defaultBean.m_rcontent_list));
		$("#m_h_path_name").val(getTemplateName(defaultBean.m_h_path));
	}
	$("#addButton").click(updateSCategory);
}
else
{
	$("#addButton").click(saveSCategory);
}

init_FromTabsStyle();
});

function showCommitTD(flag)
{
	if(flag == 1)
	{
		$("#comment_0").find("TD").removeClass();			
		$("#comment_1").show();
		$("#comment_2").show();

	}
	else
	{
		$("#comment_0").find("TD").addClass("content_td");
		$("#comment_1").hide();
		$("#comment_2").hide();
	}
}
</script>
</head>

<body>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" align="left">
	<tr>
		<td align="left" class="fromTabs width10" style="">	
			
			<span class="blank3"></span>
		</td>
		<td align="left" width="100%">
			<ul class="fromTabs">
				<li class="list_tab list_tab_cur">
					<div class="tab_left">
						<div class="tab_right" >基础信息</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" >配置信息</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" >展现信息</div>
					</div>
				</li>
			</ul>
		</td> 	
		
	</tr>
</table>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<div id="subCategory">
<div class="infoListTable" id="listTable_0">
<table id="jcxx_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>访谈模型名称：</th>
			<td>
				<input id="category_name" name="category_name" type="text" class="width300" value="" onblur="checkInputValue('category_name',false,80,'访谈模型名称','')"/>
				<input type="hidden" id="id" name="id" value="0"/>
			</td>			
		</tr>		
		<tr>
			<th>访谈模型说明：</th>
			<td colspan="3">
				<textarea id="description" name="description" style="width:585px;height:50px;" onblur="checkInputValue('description',true,1000,'访谈模型说明','')"></textarea>		
			</td>
		</tr>
		<tr style="display:none">
			<th>是否允许评分：</th>
			<td>
				<ul>
					<li><input id="is_grade" name="is_grade" type="radio" value="1" /><label>是</label></li>
					<li><input id="is_grade" name="is_grade" type="radio" value="0" checked="true" /><label>否</label></li>
				</ul>
			</td>			
		</tr>
		<tr id="comment_0"  style="display:none">
			  <th>是否允许评论：</th>
		      <td>
				<ul>
					<li><input type="radio" id="is_comment" name="is_comment" value="1" onclick="showCommitTD(1)"><label>是</label></li>
					<li><input type="radio" id="is_comment" name="is_comment" value="0" checked="checked"  onclick="showCommitTD(0)"><label>否</label></li>
				</ul>
			  </td>
		 </tr>
		 <tr id="comment_1" style="display:none">
		  <th></th>
		  <td>评论是否需要审核才能发布：
		   <ul>
			 <li><input type="radio" id="is_com_audit" name="is_com_audit" value="1"><label>是</label></li>
			 <li><input type="radio" id="is_com_audit" name="is_com_audit" value="0" checked="checked"><label>否</label></li>
		   </ul>
		  </td>
		 </tr>
		 <tr  id="comment_2" style="display:none">
		  <th></th>
		  <td >评论是否开启关键词过滤：
			<ul>
			   <li><input type="radio" id="is_com_filter" name="is_com_filter" value="1"><label>是</label></li>
			   <li><input type="radio" id="is_com_filter" name="is_com_filter" value="0" checked="checked"><label>否</label></li>
			</ul>
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

<div class="infoListTable hidden" id="listTable_1">
<table id="pzxx_tab" class="table_form" border="0" cellpadding="0" cellspacing="0" >
	<tbody>		
		<tr>
		   <th><strong>图文直播区</strong></th>
		   <td>
		   
		   </td>
		</tr>
		<tr>			
			<th>启用审核：</th>
			<td>
			   <ul>
				<li><input type="radio" id="is_p_audit" name="is_p_audit" value="1"><label>是</label></li>
				<li><input type="radio" id="is_p_audit" name="is_p_audit" value="0" checked="checked"><label>否</label></li>
			   </ul>
			</td>
		</tr>
		<tr>			
			<th>启用关键词过滤：</th>
			<td>
				<ul>
					<li><input type="radio" id="is_p_filter" name="is_p_filter" value="1"><label>是</label></li>
					<li><input type="radio" id="is_p_filter" name="is_p_filter" value="0" checked="checked"><label>否</label></li>
				</ul>
			</td>
		</tr>
		<tr>
		   <th><strong>文字互动区</strong></th>
		   <td>
		   
		   </td>
		</tr>
		<tr>
			<th>启用文字互动：</th>
			<td>
				<ul>
					<li><input type="radio" id="is_show_text" name="is_show_text" value="1" checked="checked"><label>是</label></li>
					<li><input type="radio" id="is_show_text" name="is_show_text" value="0" ><label>否</label></li>
				</ul>
			</td>
		</tr>
		<tr>		
			<th>发言限制：</th>
			<td>
				<ul>
					<li><input type="radio" id="is_register" name="is_register" value="1"><label>ALL</label>
					<li><input type="radio" id="is_register" name="is_register" value="0" checked="checked"><label>仅会员</label></li>
				</ul>
			</td>
		</tr>
		<tr>			
			<th>发言是否审核：</th>
			<td>
				<ul>
					<li><input type="radio" id="is_t_audit" name="is_t_audit" value="1" checked="checked"><label>是</label></li>
					<li><input type="radio" id="is_t_audit" name="is_t_audit" value="0" ><label>否</label></li>
				</ul>
			</td>
		</tr>
		<tr>			
			<th>启用关键词过滤：</th>
			<td>
				<ul>
					<li><input type="radio" id="is_t_keyw" name="is_t_keyw" value="1"><label>是</label></li>
					<li><input type="radio" id="is_t_keyw" name="is_t_keyw" value="0" checked="checked"><label>否</label></li>
				</ul>
			</td>
		</tr>
		<tr>
			<th>启用昵称过滤：</th>
			<td>
				<ul>
					<li><input type="radio" id="is_t_flink" name="is_t_flink" value="1"><label>是</label></li>
					<li><input type="radio" id="is_t_flink" name="is_t_flink" value="0" checked="checked"><label>否</label></li>
				</ul>
			</td>
		</tr>
	</tbody>
</table>
</div>

<div class="infoListTable hidden" id="listTable_2">
<table id="zxxx_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>			
			<th>预告新闻稿模板：</th>
			<td><input type="text" class="input_border" id="m_forecast_path_name" name="m_forecast_path_name" style="width:365px" readOnly="readOnly">
				<input type="hidden" id="m_forecast_path" class="width200"><input type="button" value="选择" onclick="openSelectTemplate('m_forecast_path','m_forecast_path_name',site_id)"/>
			</td>
		</tr>
		<tr>		
			<th>历史列表模板：</th>
			<td><input type="text" class="input_border" id="m_hlist_path_name" name="m_hlist_path_name" style="width:365px" readOnly="readOnly">
				<input type="hidden" id="m_hlist_path" class="width200"><input type="button" value="选择" onclick="openSelectTemplate('m_hlist_path','m_hlist_path_name',site_id)"/>
			</td>
		</tr>
		<tr>		
			<th>历史内容模板：</th>
			<td><input type="text" class="input_border" id="m_h_path_name" name="m_h_path_name" style="width:365px" readOnly="readOnly">
				<input type="hidden" id="m_h_path" class="width200"><input type="button" value="选择" onclick="openSelectTemplate('m_h_path','m_h_path_name',site_id)"/>
			</td>
		</tr>
		<tr>
			
			<th>访谈模板：</th>
			<td><input type="text" class="input_border" id="m_on_path_name" name="m_on_path_name" style="width:365px" readOnly="readOnly">
				<input type="hidden" id="m_on_path" class="width200"><input type="button" value="选择" onclick="openSelectTemplate('m_on_path','m_on_path_name',site_id)"/>
			</td>
		</tr>
		<tr>
			<th>信息列表模板：</th>
			<td><input type="text" class="input_border" id="m_rlist_path_name" name="m_rlist_path_name" style="width:365px" readOnly="readOnly">
				<input type="hidden" id="m_rlist_path" class="width200"><input type="button" value="选择" onclick="openSelectTemplate('m_rlist_path','m_rlist_path_name',site_id)"/>	
			</td>
		</tr>
		<tr>
			<th>信息内容模板：</th>
			<td><input type="text" class="input_border" id="m_rcontent_list_name" name="m_rcontent_list_name" style="width:365px" readOnly="readOnly">
				<input type="hidden" id="m_rcontent_list" class="width200"><input type="button" value="选择" onclick="openSelectTemplate('m_rcontent_list','m_rcontent_list_name',site_id)"/>
			</td>
		</tr>
	</tbody>
</table>
</div>
</div>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="window.location.reload()" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="history.go(-1)" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>

</form>
</body>
</html>
