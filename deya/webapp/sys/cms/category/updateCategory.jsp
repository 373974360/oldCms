<%@ page contentType="text/html; charset=utf-8"%>
<%
	String site_id = request.getParameter("site_id");
	String app_id = request.getParameter("app_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>目录维护</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/categoryList.js"></script>
<script type="text/javascript">
var app_id = "<%=app_id%>";
var site_id = "<%=site_id%>";
var CategoryRPC = jsonrpc.CategoryRPC;
$(document).ready(function(){
	reloadBefore();
	initButtomStyle();	
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
		
	initMenuTree();
	setLeftTreeHeight();
	showCategoryTree();	
	getWorkFlowList();
	setModelInfoList();
	$(":checkbox[id='model_id']").removeAttr("checked");
	init_input();
	reloadAfter();
});

function showCategoryTree()
{	
	if(app_id == "ggfw")
	{
		site_id = "ggfw";
		json_data = eval(CategoryRPC.getAllFWTreeJSONStr());	
	}else
	{		
		json_data = eval(CategoryRPC.getCategoryTreeBySiteID(site_id));	
		if(app_id == "cms")
		{
			var zt_jdata = eval(CategoryRPC.getZTCategoryTreeJsonStr(site_id));//如果是本站,加入所有专题的目录
			for(var i=0;i<zt_jdata.length;i++)
			{
				json_data[0].children[json_data[0].children.length] = zt_jdata[i];
			}
		}
	}
	setLeftMenuTreeJsonData(json_data);	
}

function initMenuTree()
{
	$('#leftMenuTree').tree({	
		checkbox: true		
	});
}

function openTemplate(n1,n2)
{	
	var temp_site_id = "";
	if(app_id != "cms")
	{
		temp_site_id = jsonrpc.SiteRPC.getSiteIDByAppID(app_id);
	}
	else
		temp_site_id = site_id;
	openSelectTemplate(n1,n2,temp_site_id);
}

function updateCate()
{
	var map = new Map();
	if($("#is_wf_id").is(':checked'))
	{
		map.put("wf_id",$("#wf_id :selected").val());
	}
	if($("#is_publish").is(':checked'))
	{
		map.put("is_wf_publish",$(":checked[id='is_wf_publish']").val());
	}
	if($("#is_template_index").is(':checked'))
	{
		map.put("template_index",$("#template_index").val());
	}
	if($("#is_template_list").is(':checked'))
	{
		map.put("template_list",$("#template_list").val());
	}
	if($("#is_comment").is(':checked'))
	{
		map.put("is_allow_comment",$(":checked[id='is_allow_comment']").val());
	}
	if($("#is_show_checkbox").is(':checked'))
	{
		map.put("is_show",$(":checked[id='is_show']").val());
	}
    if($("#mlsx_checkbox").is(':checked'))
    {
        map.put("mlsx",$(":checked[id='mlsx']").val());
    }
	
	var model_list = getSelectCategoryModelList(0);
	var cat_ids = getLeftMenuChecked();
	
	if(cat_ids == "")
	{
		top.msgWargin("请选择目录");
		return;
	}

	if(CategoryRPC.batchUpdateCategory(map,model_list,cat_ids,site_id))
	{
		top.msgAlert("目录信息"+WCMLang.Add_success);
	}else
		top.msgWargin("目录信息"+WCMLang.Add_fail);
}
</script>
</head>

<body>
<span class="blank12"></span>
<table style="width:100%" border="0" cellpadding="0" cellspacing="0" >
	<tr>
	 <td width="200px" valign="top">
		<div id="leftMenuBox">
			<div id="leftMenu" class="contentBox6 textLeft width200" style="overflow:auto">
				<ul id="leftMenuTree" class="easyui-tree" animate="true">
				</ul>
			</div>
		</div>
	 </td>
	 <td class="width10"></td>
	 <td valign="top">
		<span class="blank12"></span>
		<table class="table_form" border="0" cellpadding="0" cellspacing="0" align="left">
		<tr>		 
			<td align="center"><input type="checkbox" id="is_wf_id" name="is_wf_id"></td>
			<th>工作流：</th>
			<td>
				<select id="wf_id" name="wf_id" class="width305" onchange="changeWorkFlowHandl(this.value)">
				 <option value="0"></option>
				</select>
			</td>
		</tr>
		<tr id="wf_publish_tr" class="hidden">		 
			<td align="center"><input type="checkbox" id="is_publish" name="is_publish"></td>
			<th>终审后自动发布：</th>
			<td>
				<input id="is_wf_publish" name="is_wf_publish" type="radio" value="1" /><label>是</label>
				<input id="is_wf_publish" name="is_wf_publish" type="radio" value="0" checked="true"/><label>否</label>
			</td>			
		</tr>
		<tr>
			<td colspan="3">
				<table border="0" cellpadding="0" cellspacing="0" align="left" id="model_select_table" width="460px">
				  <td width="30px">选择</td>
				  <td width="100px" align="center">内容</td>
				  <td width="" align="center">内容页模板</td>
				</table>
			</td>
		</tr>
		<tr>		 
			<td align="center"><input type="checkbox" id="is_template_index" name="is_template_index"></td>
			<th>栏目首页模板：</th>
			<td>				
				<input id="template_index_name" name="template_index_name" type="text" class="width200" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_index" name="template_id_hidden" value="0"/><input type="button" value="选择" onclick="openTemplate('template_index','template_index_name')"/>&#160;<input type="button" value="清空" onclick="clearTemplate(this)"/>
			</td>		
		</tr>
		<tr>		 
			<td align="center"><input type="checkbox" id="is_template_list" name="is_template_list"></td>
			<th>列表页模板：</th>
			<td>				
				<input id="template_list_name" name="template_list_name" type="text" class="width200" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_list" name="template_id_hidden" value="0"/><input type="button" value="选择" onclick="openTemplate('template_list','template_list_name')"/>&#160;<input type="button" value="清空" onclick="clearTemplate(this)"/>
			</td>		
		</tr>
		<tr>		 
			<td align="center"><input type="checkbox" id="is_comment" name="is_comment"></td>
			<th>允许评论：</th>
			<td>
				<input id="is_allow_comment" name="is_allow_comment" type="radio" value="1" /><label>是</label>
				<input id="is_allow_comment" name="is_allow_comment" type="radio" value="0" checked="true"/><label>否</label>
			</td>		
		</tr>
		<tr>		 
			<td align="center"><input type="checkbox" id="is_show_checkbox" name="is_show"></td>
			<th>前台导航显示：</th>
			<td>
				<input id="is_show" name="is_show" type="radio" value="1" checked="true"/><label>是</label>
				<input id="is_show" name="is_show" type="radio" value="0" /><label>否</label>
			</td>		
		</tr>
			<tr>
				<td align="center"><input type="checkbox" id="mlsx_checkbox" name="is_show"></td>
				<th>目录属性：</th>
				<td>
					<input id="mlsx" name="mlsx" type="radio" value="1" checked="true"/><label>新闻目录</label>
					<input id="mlsx" name="mlsx" type="radio" value="2" /><label>公开目录</label>
				</td>
			</tr>
		<tr>
			<td align="left" valign="middle" colspan="2">
				<span class="blank12"></span>	
				<input id="create_button" name="btn1" type="button" onclick="updateCate();" value="保存" />
				<input id="create_button" name="btn1" type="button" onclick="window.location.reload();" value="重置" />
				<span class="hidden" id="loadImg">&nbsp;&nbsp;<img width="12px" height="12px" src="../../../images/loading.gif"/></span>
			</td>
		</tr> 
	</table>
	 </td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
