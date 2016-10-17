<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>接收配置</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/receiveConfList.js"></script>
<script type="text/javascript">
var site_id = "${param.site_id}";
var topnum = "${param.topnum}";
var defaultBean;

$(document).ready(function(){
	reloadBefore();
	initButtomStyle();
	init_input();
	
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
	if(site_id != "" && site_id != null)
	{
		var bean_m = SendInfoRPC.getReceiveConfigForSiteID(site_id);
		bean_m = Map.toJSMap(bean_m);
		defaultBean = bean_m.get("rcfBean");
		if(defaultBean)
		{
			$("#receiveConfig_table").autoFill(defaultBean);	
			$("#site_id").parent().html(defaultBean.site_name);
			showCategoryTreeForSite(site_id);
			var catList = bean_m.get("catList");
			if(catList != null)
			{
				catList = List.toJSList(catList);			
				defaultSelectCategory(catList);			
			}
		}
		
		$("#addButton").click(updateReceiveConfig);
	}else
	{
		getAllSiteList();
		$("#addButton").click(insertReceiveConfig);
	}

	reloadAfter();
});

//得到所有站点列表
function getAllSiteList()
{
	var sitelist = jsonrpc.SiteRPC.getSiteList();
	sitelist = List.toJSList(sitelist);
	var site_ids = top.getCurrentFrameObj(topnum).site_ids;
	if(sitelist != null && sitelist.size() > 0)
	{
		for(var i=0;i<sitelist.size();i++)
		{
			if(site_ids.indexOf(sitelist.get(i).site_id) == -1)
			{
				$("#site_id").addOptionsSingl(sitelist.get(i).site_id,sitelist.get(i).site_name);
			}
		}
	}
	
}
var json_data;
var CategoryRPC = jsonrpc.CategoryRPC;
function showCategoryTreeForSite(s_site_id)
{
	if(s_site_id != "" && s_site_id != null)
	{
		try{
			$("#cat_list").empty();
			var json_data = eval(CategoryRPC.getCategoryTreeBySiteID(s_site_id));		
			setAppointTreeJsonData("leftMenuTree",json_data);
			initMenuClick(s_site_id,"leftMenuTree","cat_list","site_id");
		}catch(e){
			
		}
	}
}

//初始加载树节点 
function initMenuClick(t_site_id,div_name,cat_list_id,select_id)
{	
	$("#"+div_name+" li").css("float","none");
	var t_site_name =  $("#"+select_id+" option:selected").text();

	$("#"+div_name+" .tree-file").after('<SPAN class="tree-checkbox tree-checkbox0"></SPAN>');
	$("#"+div_name+" .tree-checkbox").click(function(){		
		if($(this).attr("class").indexOf("tree-checkbox0") > -1)
		{
			$(this).removeClass("tree-checkbox0");
			$(this).addClass("tree-checkbox1");
			selectCategory(t_site_id,t_site_name,$(this).parent().attr("node-id"),$(this).parent().find(".tree-title").text(),cat_list_id,div_name);
		}else
		{
			$(this).removeClass("tree-checkbox1");
			$(this).addClass("tree-checkbox0");
			//删除栏目列表中的数据
			$("#"+cat_list_id+" li[cat_id='"+$(this).parent().attr("node-id")+"']").remove();
		}
	});
	init_input();
}

//展开站点栏目树时默认选中已选 节点
function defaultSelectCategory(select_cat_list)
{
	if(select_cat_list != null && select_cat_list.size() > 0)
	{
		for(var i=0;i<select_cat_list.size();i++)
		{
			$("#leftMenuTree div[node-id='"+select_cat_list.get(i).cat_id+"'] .tree-checkbox").click();
		}
	}
}

//选择栏目
function selectCategory(t_site_id,t_site_name,shared_cat_id,shared_cat_name,cat_list_id,tree_div_id)
{	
	$("#"+cat_list_id).append('<li style="float:none;height:20px" site_id="'+t_site_id+'" cat_id="'+shared_cat_id+'"><div class="width150 left">'+shared_cat_name+'</div><div class="width20 left"><img onclick="deleteSharedCategoryList(this,'+shared_cat_id+',\''+tree_div_id+'\')" src="../../images/delete.png" width="15" height="15" alt="" align="right"/></div></li>');
}
//删除栏目
function deleteSharedCategoryList(obj,shared_cat_id,tree_div_id)
{
	$(obj).parent().parent().remove();
	$("#"+tree_div_id+" div[node-id='"+shared_cat_id+"'] .tree-checkbox").removeClass("tree-checkbox1").addClass("tree-checkbox0");
}
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="receiveConfig_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>站点：</th>
			<td >
				<select class="width200" id="site_id" onchange="showCategoryTreeForSite(this.value);">
					<option value=""></option>
				</select>
			</td>	
		</tr>
		<tr>
		   <th></th>
		   <td>
		    <table class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:530px" align="left">
			  <tbody>
			   <tr>
			    <th style="text-align:left">可选栏目：</th>
				<th></th>
				<th style="text-align:left">已选栏目：</th>
			   </tr>
			   <tr>
			    <td>
				 <div id="leftMenuBox" style="width:250px;height:260px;overflow:auto;" class="border_color">
					<div id="leftMenu" class="contentBox6 textLeft no_zhehang" style="overflow:auto">
						<ul id="leftMenuTree" class="easyui-tree no_zhehang" animate="true">
						</ul>
					</div>
				 </div>
				</td>
				<td>
				 >>
				</td>
				<td >
				 <div style="width:260px;height:260px;overflow:auto;" class="border_color">
					<ul id="cat_list" style="margin:10px">
					</ul>
				 </div>
				</td>
			   </tr>
			  </tbody>
			</table>
		   </td>
		</tr>
		<tr>
			<th>报送接口用户名：</th>
			<td >
				<input id="user_name" name="user_name" type="text" class="width300" onblur="checkInputValue('user_name',true,50,'报送接口用户名','')"/>　注：此用户名和密码仅用于第三方系统报送功能使用
			</td>	
		</tr>
		<tr>
			<th>报送接口密码：</th>
			<td >
				<input id="pass_word" name="pass_word" type="text" class="width300" onblur="checkInputValue('pass_word',true,50,'报送接口密码','')"/>
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
			<input id="addButton" name="btn1" type="button" onclick="insertSendConfig()" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('receiveConfig_table',id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex);" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
