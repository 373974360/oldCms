<%@ page contentType="text/html; charset=utf-8"%>
<%
	String parent_id = request.getParameter("parentID");
	String id = request.getParameter("id");	
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
<script id="src-dict" type="text/javascript" src="../../js/pinyin/pinyin.dict.src.js"></script>
<script type="text/javascript" src="../../js/pinyin/pinyin.js"></script>
<script type="text/javascript" src="js/categoryList.js"></script>
<script type="text/javascript">
var top_index = "<%=top_index%>";
var parent_id = "<%=parent_id%>";
var id = "<%=id%>";
var class_id = "<%=class_id%>";
var app_id = "cms";
var site_id = "<%=site_id%>";
var default_siteid = jsonrpc.SiteRPC.getSiteIDByAppID("system");
var defaultBean;

$(document).ready(function(){
	setModelInfoList_shared();
	initButtomStyle();
	init_input();
	getWorkFlowList();
	if(id != "" && id != "null" && id != null)
	{		
		defaultBean = CategoryRPC.getCategoryBean(id);
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
			reloadTemplate_shared();
			changeWorkFlowHandl(defaultBean.wf_id);
			checkedCategoryModelInfo_shared(defaultBean.cat_id);//选中已关联的内容模型
		}
		$("#addButton").click(updateCategory);
	}
	else
	{
		if(parent_id != 0)
		{//有些数据需从父级继承过来
			defaultBean = CategoryRPC.getCategoryBeanCatID(parent_id,site_id);
			defaultBean.cat_cname = "";
			defaultBean.parent_id = parent_id;
			defaultBean.cat_ename = "";
			defaultBean.cat_code = "";
			defaultBean.cat_description = "";
			defaultBean.cat_memo = "";
			$("#category_table").autoFill(defaultBean);	
			reloadTemplate_shared();
			checkedCategoryModelInfo(parent_id);//选中已关联的内容模型
		}
		$("#addButton").click(addCategory);
	}

	if(class_id == 0)//为0表示是从站点中添加的，要打开共享目录的窗口，否则打开选择基础目录的窗口
		$("#selectCateClassButton").click(function(){openSelectSingleShareCateClass('选择类目','releCateClass','cms')});
	else
		$("#selectCateClassButton").click(function(){openSelectSingleCateClass('选择类目','releCateClass')});

	$("#cat_cname").blur(function(){		
		if($("#cat_ename").val() == "")
		{
			var strs= new Array();
			var result="";
			var cat_ename = pinyin(this.value, true, ",").toLowerCase();
			strs = cat_ename.split(",");
			for (i=0;i<strs.length ;i++ )   
		    {   
		        result += strs[i].substr(0,1);
		    } 
			$("#cat_ename").val(result);
		}
	});
});

function getWorkFlowList()
{
	var WorkFlowRPC = jsonrpc.WorkFlowRPC;
	var list = WorkFlowRPC.getWorkFlowList();
	list = List.toJSList(list);
	$("#wf_id").addOptions(list,"wf_id","wf_name");
}

function addCategory()
{
	var bean = BeanUtil.getCopy(CategoryBean);	
	$("#category_table").autoBind(bean);

	if(!standard_checkInputInfo("category_table"))
	{
		return;
	}

	if(CategoryRPC.categoryIsExist(parent_id,$("#cat_ename").val(),app_id,site_id))
	{
		top.msgWargin("该英文名称已存在此目录下，请更换英文名称");
		return;
	}

	bean.cat_id = CategoryRPC.getNewCategoryID();
	bean.id = bean.cat_id;
	bean.app_id = "system";
	
	//得到选择的内容模型
	var cat_model_list = getSelectCategoryModelList(bean.id);

	if(CategoryRPC.insertCategory(bean,false))
	{
		CategoryRPC.insertCategoryModel(cat_model_list);
		top.msgAlert("目录信息"+WCMLang.Add_success);	
		try{
			top.getCurrentFrameObj(top_index).changeCategoryListTable(parent_id);
			top.getCurrentFrameObj(top_index).insertCategoryTree(bean.cat_id,bean.cat_cname);
		}catch(e){}
		top.tab_colseOnclick(top.curTabIndex);
	}
	else
	{
		top.msgWargin("目录信息"+WCMLang.Add_fail);
	}
}

function updateCategory()
{
	var bean = BeanUtil.getCopy(CategoryBean);	
	$("#category_table").autoBind(bean);
	if(!standard_checkInputInfo("category_table"))
	{
		return;
	}

	bean.id = id;

	if(defaultBean.cat_ename != bean.cat_ename)
	{
		if(CategoryRPC.categoryIsExist(parent_id,bean.cat_ename,app_id,site_id))
		{
			top.msgWargin("该英文名称已存在此目录下，请更换英文名称");
			return;
		}
	}

	//得到选择的内容模型
	var cat_model_list = getSelectCategoryModelList(bean.id);
	
	if(CategoryRPC.updateCategory(bean))
	{
		CategoryRPC.updateCategoryModel(cat_model_list,bean.id,site_id);
		top.msgAlert("目录信息"+WCMLang.Add_success);	
		try{
			top.getCurrentFrameObj(top_index).changeCategoryListTable(parent_id);
			top.getCurrentFrameObj(top_index).updateCategoryTree(bean.id,bean.cat_cname);
		}catch(e){}
		top.tab_colseOnclick(top.curTabIndex);
	}
	else
	{
		top.msgWargin("目录信息"+WCMLang.Add_fail);
	}
}

//列出内容模型
function setModelInfoList_shared()
{
	
		$("#model_select_table").append('<tr><td colspan="4">以下内容模型只关联模板，不添加此模型数据</td></tr>');		
		setModelInfoListHandl(jsonrpc.ModelRPC.getCANModelListByAppID("cms"),default_siteid,"a");
		setModelInfoListHandl(jsonrpc.ModelRPC.getCANModelListByAppID("zwgk"),default_siteid,"b");	
}

function reloadTemplate_shared()
{
	$("#template_index_name").val(getTemplateNameForTSiteID(defaultBean.template_index,default_siteid));
	$("#template_list_name").val(getTemplateNameForTSiteID(defaultBean.template_list,default_siteid));
}

//选中已关联的内容模型
function checkedCategoryModelInfo_shared(cat_id)
{
	$("#model_select_table :checked").removeAttr("checked");
	var cm_list = CategoryRPC.getCategoryReleModelList(cat_id,site_id);		
	if(cm_list != null)
	{
		cm_list = List.toJSList(cm_list);
		for(var i=0;i<cm_list.size();i++)
		{
			var c_obj = $("#model_select_table :checkbox[value='"+cm_list.get(i).model_id+"']");
			$(c_obj).attr("checked",true);
			if(cm_list.get(i).isAdd == 1)
			{
				$(c_obj).parent().parent().find("#is_add").attr("checked",true);
			}
			$(c_obj).parent().parent().find("input[name='template_content_name']").val(getTemplateNameForTSiteID(cm_list.get(i).template_content,default_siteid));
			$(c_obj).parent().parent().find(":hidden").val(cm_list.get(i).template_content);
		}
	}
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
				<input id="cat_cname" name="cat_cname" type="text" class="width300" value="" onblur="checkInputValue('cat_cname',false,120,'目录名称','')"/>
				<input type="hidden" id="class_id" name="class_id" value="<%=class_id%>">
				<input type="hidden" id="parent_id" name="parent_id" value="<%=parent_id%>">
				<input type="hidden" id="site_id" name="site_id" value="<%=site_id%>">
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>英文名称：</th>
			<td>
				<input id="cat_ename" name="cat_ename" type="text" class="width300" onblur="checkInputValue('cat_ename',false,15,'英文名称','checkLower')"/>
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
				<select id="wf_id" name="wf_id" class="width305" onchange="changeWorkFlowHandl(this.value)">
				 <option value="0"></option>
				</select>
			</td>			
		</tr>
		<tr>
			<th>公开主体：</th>
			<td>
				<input id="gkzt" name="gkzt" type="text" class="width300"/>
			</td>
		</tr>
		<tr>
			<th>公开时限：</th>
			<td>
				<input id="gksx" name="gksx" type="text" class="width300"/>
			</td>
		</tr>
		<tr>
			<th>事项依据：</th>
			<td>
				<input id="sxyj" name="sxyj" type="text" class="width300"/>
			</td>
		</tr>
		<tr>
			<th>公开方式：</th>
			<td>
				<input id="gkfs" name="gkfs" type="text" class="width300"/>
			</td>
		</tr>
		<tr>
			<th>公开格式：</th>
			<td>
				<input id="gkgs" name="gkgs" type="text" class="width300"/>
			</td>
		</tr>
		<tr>
			<th>公开备注：</th>
			<td>
				<input id="gkbz" name="gkbz" type="text" class="width300"/>
			</td>
		</tr>
		<tr id="wf_publish_tr" class="hidden">
			<th>终审后自动发布：</th>
			<td>
				<input id="is_wf_publish" name="is_wf_publish" type="radio" value="1" /><label>是</label>
				<input id="is_wf_publish" name="is_wf_publish" type="radio" value="0" checked="true"/><label>否</label>
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">内容模型：</th>
			<td>
				<table border="0" cellpadding="0" cellspacing="0" align="left" id="model_select_table" width="505px">
				  <td width="30px">选择</td>
				  <td width="100px" align="center">内容</td>
				  <td width="" align="center">内容页模板</td>
				  <td width="" align="center">是否添加</td>
				</table>
			</td>			
		</tr>
		<tr>
			<th>栏目首页模板：</th>
			<td>				
				<input id="template_index_name" name="template_index_name" type="text" class="width200" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_index"  name="template_id_hidden" value="0"/><input type="button" value="选择" onclick="openSelectTemplate('template_index','template_index_name',default_siteid)"/>&#160;<input type="button" value="清空" onclick="clearTemplate(this)"/>
			</td>			
		</tr>
		<tr>
			<th>列表页模板：</th>
			<td>				
				<input id="template_list_name" name="template_list_name" type="text" class="width200" value="" readOnly="readOnly"/>	
				<input type="hidden" id="template_list"  name="template_id_hidden" value="0"/><input type="button" value="选择" onclick="openSelectTemplate('template_list','template_list_name',default_siteid)"/>&#160;<input type="button" value="清空" onclick="clearTemplate(this)"/>
			</td>			
		</tr>
		<tr>
			<th>生成栏目首页：</th>
			<td>
				<input id="is_generate_index" name="is_generate_index" type="radio" value="1" /><label>是</label>
				<input id="is_generate_index" name="is_generate_index" type="radio" value="0" checked="true"/><label>否</label>
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
			<th>前台导航显示：</th>
			<td>
				<input id="is_show" name="is_show" type="radio" value="1" checked="true"/><label>是</label>
				<input id="is_show" name="is_show" type="radio" value="0" /><label>否</label>
			</td>			
		</tr>
		<tr>
			<th>前台栏目树显示：</th>
			<td>
				<input id="is_show_tree" name="is_show_tree" type="radio" value="1" checked="true"/><label>是</label>
				<input id="is_show_tree" name="is_show_tree" type="radio" value="0" /><label>否</label>
			</td>			
		</tr>
		<tr>
			<th>单页信息目录：</th>
			<td>
				<input id="is_mutilpage" name="is_mutilpage" type="radio" value="1" /><label>是</label>
				<input id="is_mutilpage" name="is_mutilpage" type="radio" value="0" checked="true"/><label>否</label>
			</td>			
		</tr>
		<tr>
			<th>目录跳转地址：</th>
			<td>
				<input id="jump_url" name="jump_url" type="text" class="width300" onblur="checkInputValue('jump_url',true,200,'类目编号','')"/>
			</td>			
		</tr>
		<tr class="hidden">
			<th>关联分类法类目：</th>
			<td>
				<input id="cat_class_name" name="cat_class_name" type="text" class="width300" readOnly="readOnly"/>
				<input id="cat_class_id" name="cat_class_id" type="hidden" value="0"/>
				<input id="selectCateClassButton" name="btn1" type="button" onclick="" value="选择类目" />	
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
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('category_table',id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex);" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
