<%@ page contentType="text/html; charset=utf-8"%>
<%
	String parent_id = request.getParameter("parentID");
	String cat_id = request.getParameter("cat_id");	
	String site_id = request.getParameter("site_id");
	String top_index = request.getParameter("top_index");
	String class_id = request.getParameter("class_id");
	if(class_id == null || "".equals(class_id))
		class_id = "0";
	if(site_id == null || "".equals(site_id))
		site_id = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>目录维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/categoryList.js"></script>
<script type="text/javascript">
var top_index = "<%=top_index%>";
var parent_id = "<%=parent_id%>";
var cat_id = "<%=cat_id%>";
var class_id = "<%=class_id%>";

var site_id = "<%=site_id%>";

var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	getWorkFlowList();
	if(cat_id != "" && cat_id != "null" && cat_id != null)
	{		
		defaultBean = CategoryRPC.getCategoryBean(cat_id);
		if(defaultBean)
		{
			$("#category_table").autoFill(defaultBean);	
			parent_id = defaultBean.parent_id;
			if(defaultBean.cat_class_id != 0)
			{
				var n_bean = CategoryRPC.getCategoryBean(defaultBean.cat_class_id);
				if(n_bean != null && n_bean.cat_cname != "")
					$("#cat_class_name").val(n_bean.cat_cname);
			}
		}
		$("#addButton").click(updateCategory);

		disabledWidget();
	}
	
});

function getWorkFlowList()
{
	var WorkFlowRPC = jsonrpc.WorkFlowRPC;
	var list = WorkFlowRPC.getWorkFlowList();
	list = List.toJSList(list);
	$("#wf_id").addOptions(list,"wf_id","wf_name");
}


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="category_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>目录名称：</th>
			<td>
				<input id="cat_cname" name="cat_cname" type="text" class="width300" value="" onblur="checkInputValue('cat_cname',false,80,'目录名称','')"/>
				<input type="hidden" id="class_id" name="class_id" value="<%=class_id%>">
				<input type="hidden" id="parent_id" name="parent_id" value="<%=parent_id%>">
				<input type="hidden" id="site_id" name="site_id" value="<%=site_id%>">
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>英文名称：</th>
			<td>
				<input id="cat_ename" name="cat_ename" type="text" class="width300" onblur="checkInputValue('cat_ename',false,10,'英文名称','checkLowerLetter')"/>
			</td>			
		</tr>
		<tr>
			<th>类目编号：</th>
			<td>
				<input id="cat_code" name="cat_code" type="text" class="width300" onblur="checkInputValue('cat_code',true,20,'类目编号','')"/>
			</td>			
		</tr>
		<tr>
			<th>工作流：</th>
			<td>
				<select id="wf_id" name="wf_id" class="width305">
				 <option value="0"></option>
				</select>
			</td>			
		</tr>
		<tr>
			<th>终审后自动发布：</th>
			<td>
				<input id="is_wf_publish" name="is_wf_publish" type="radio" value="1" /><label>是</label>
				<input id="is_wf_publish" name="is_wf_publish" type="radio" value="0" checked="true"/><label>否</label>
			</td>			
		</tr>
		<tr>
			<th>内容模型：</th>
			<td>
				
			</td>			
		</tr>
		<tr>
			<th>栏目首页模板：</th>
			<td>
				<input id="template_index" name="template_index" type="text" class="width300" value="0" />
			</td>			
		</tr>
		<tr>
			<th>列表页模板：</th>
			<td>
				<input id="template_list" name="template_list" type="text" class="width300" value="0" />
			</td>			
		</tr>
		<tr>
			<th>生成栏目首页：</th>
			<td>
				<input id="is_generate_index" name="is_generate_index" type="radio" value="1" checked="true"/><label>是</label>
				<input id="is_generate_index" name="is_generate_index" type="radio" value="0"/><label>否</label>
			</td>			
		</tr>
		<tr>
			<th>允许用户投稿：</th>
			<td>
				<input id="is_allow_submit" name="is_allow_submit" type="radio" value="1" /><label>是</label>
				<input id="is_allow_submit" name="is_allow_submit" type="radio" value="0" checked="true"/><label>否</label>
			</td>			
		</tr>
		<tr>
			<th>允许评论：</th>
			<td>
				<input id="is_allow_comment" name="is_allow_comment" type="radio" value="1" /><label>是</label>
				<input id="is_allow_comment" name="is_allow_comment" type="radio" value="0" checked="true"/><label>否</label>
			</td>			
		</tr>
		<tr>
			<th>评论需要审核：</th>
			<td>
				<input id="is_comment_checked" name="is_comment_checked" type="radio" value="1" /><label>是</label>
				<input id="is_comment_checked" name="is_comment_checked" type="radio" value="0" checked="true"/><label>否</label>
			</td>			
		</tr>
		<tr>
			<th>导航是否显示：</th>
			<td>
				<input id="is_show" name="is_show" type="radio" value="1" /><label>是</label>
				<input id="is_show" name="is_show" type="radio" value="0" checked="true"/><label>否</label>
			</td>			
		</tr>
		<tr>
			<th>关联分类法类目：</th>
			<td>
				<input id="cat_class_name" name="cat_class_name" type="text" class="width300" readOnly="readOnly"/>
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">关键词：</th>
			<td colspan="3">
				<textarea id="cat_keywords" name="cat_keywords" style="width:585px;;height:50px;" onblur="checkInputValue('cat_keywords',true,1000,'关键词','')"></textarea>		
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">描述：</th>
			<td colspan="3">
				<textarea id="cat_description" name="cat_description" style="width:585px;;height:50px;" onblur="checkInputValue('cat_description',true,1000,'描述','')"></textarea>		
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">备注：</th>
			<td colspan="3">
				<textarea id="cat_memo" name="cat_memo" style="width:585px;;height:50px;" onblur="checkInputValue('cat_memo',true,1000,'备注','')"></textarea>		
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
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="关闭" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
