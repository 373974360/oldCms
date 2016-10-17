<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>链接地址工具</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript">
var node_id = "";
var site_id = top.current_site_id;
var app_id = "cms";
var link_url = "";
var CategoryRPC = jsonrpc.CategoryRPC;
var PageRPC = jsonrpc.PageRPC;
var GKNodeRPC = jsonrpc.GKNodeRPC;
$(document).ready(function() {
	initButtomStyle();
	init_input();

	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");
	initMenuTree();
	changeLinkType("page");
});

//初始树点击事件
function initMenuTree()
{
	$('#leftMenuTree').tree({
		onClick:function(node){
			clickTreeEvent(node);
		}
	});
	$('#leftMenuTree2').tree({
		onClick:function(node){
			clickZWGKTreeEvent(node);
		}
	});
}

//点击政务树事件
function clickZWGKTreeEvent(node)
{	
	link_url = "/info/nIndex.jsp?node_id="+node_id+"&cat_id="+node.id;
}

//点击树事件
function clickTreeEvent(node)
{
	var type = $("#link_type :selected").val();
	var page_type = $("#page_type_td :checked").val();
	if(type == "page")
	{
		link_url = node.attributes.url;
	}
	if(type == "category" || type == "zt")
	{
		if(type == "zt" && node.iconCls == "icon-category")
		{
			link_url = "";
			return;
		}
		
		var cgb = CategoryRPC.getCategoryBeanCatID(node.id,site_id);
		if(cgb != null)
		{
			if(page_type == "list")
			{
				link_url = "/info/iList.jsp?cat_id="+node.id;
			}
			if(page_type == "index")
			{
				//link_url = CategoryRPC.getFoldePathByCategoryID(node.id,app_id,site_id)+"index.htm";
				link_url = "/info/iIndex.jsp?cat_id="+node.id;
			}
		}
	}
	if(type == "zwgk")
	{
		if(node.iconCls == "icon-gknode")
		{
			node_id = node.attributes.t_node_id;
			link_url = "/info/nIndex.jsp?node_id="+node_id;
			showZWGKTree();
		}	
	}
	if(type == "ggfw")
	{
		if(page_type == "list")
		{
			link_url = "/ggfw/fwList.jsp?cat_id="+node.id;
		}
		if(page_type == "index")
		{
			link_url = "/ggfw/fwIndex.jsp?cat_id="+node.id;
		}
	}
}

//展示信息公开的栏目树
function showZWGKTree()
{
	var cat_jdata = eval(CategoryRPC.getCategoryTreeBySiteID(node_id));
	cat_jdata = getTreeObjFirstNode(cat_jdata);
	$("#leftMenuTree2").empty();
	setAppointTreeJsonData("leftMenuTree2",cat_jdata);
}

//选择链接类型
function changeLinkType(val)
{
	var cat_jdata = "";
	$("#status_tr").hide();
	$("#model_id_tr").hide();
	$("#tree_table").hide();
	$("#page_type_td").hide();
	$("#sq_page_type_td").hide();
	$("#leftMenuTree").empty();
	link_url = "";
	node_id = "";
	switch(val)
	{
		case "page" :$("#cat_tree_div1").css("width","600px");
					 $("#cat_tree_div2").hide();
					 $("#tree_table").show();
					cat_jdata = eval(PageRPC.getPageJSONTreeStr(app_id,site_id));break
		case "category" :$("#cat_tree_div1").css("width","600px");
						 $("#cat_tree_div2").hide();
						 $("#tree_table").show();
						cat_jdata = eval(CategoryRPC.getCategoryTreeBySiteID(site_id)); 
						cat_jdata = getTreeObjFirstNode(cat_jdata);
						$("#page_type_td").show();
						$("#tree_table").show();
						break;
		case "zt" : $("#cat_tree_div1").css("width","600px");
					$("#cat_tree_div2").hide();					
					cat_jdata = eval(CategoryRPC.getZTCategoryTreeJsonStr(site_id));
					$("#page_type_td").show();
					$("#tree_table").show();
						break;
		case "ggfw" : $("#cat_tree_div1").css("width","600px");
					$("#cat_tree_div2").hide();					
					cat_jdata = eval(CategoryRPC.getAllFWTreeJSONStr());
					$("#page_type_td").show();
					$("#tree_table").show();
						break;
		case "zwgk" :$("#cat_tree_div1").css("width","300px");
					$("#tree_table").show();
					$("#cat_tree_div2").show();
					cat_jdata = eval(GKNodeRPC.getGKNodeTreebyCateID(0));					
						break;
		case "appeal" :	setSQModelList();
						$("#model_id_tr").show();
						$("#sq_page_type_td").show();
						$("#model_id_tr span").text("诉求业务：");
						break;
		case "interview" :setInterviewSubCategory();
						$("#model_id_tr").show();
						$("#status_tr").show();
						$("#model_id_tr span").text("访谈分类：");
						break;
		case "survey" :setSurveyCategory();
						$("#model_id_tr").show();	
						$("#model_id_tr span").text("调查分类：");
						break;
	}
	setLeftMenuTreeJsonData(cat_jdata);
}

//诉求业务
function setSQModelList()
{
	$("#model_id").empty();
	var list = jsonrpc.SQModelRPC.getAllSQModelList();
	list = List.toJSList(list);	
	$("#model_id").addOptions(list,"model_id","model_cname");
}

//访谈分类
function setInterviewSubCategory()
{
	$("#model_id").empty();
	var list = jsonrpc.SubjectRPC.getSubCategoryAllName(site_id);
	list = List.toJSList(list);	
	$("#model_id").addOptionsSingl("","　　");	
	$("#model_id").addOptions(list,"category_id","category_name");
}

//调查分类
function setSurveyCategory()
{
	$("#model_id").empty();
	var list = jsonrpc.SurveyCategoryRPC.getAllSurveyCategoryName(site_id);
	list = List.toJSList(list);	
	$("#model_id").addOptionsSingl("","　　");
	$("#model_id").addOptions(list,"category_id","c_name");
}


//得到树中的第一个节点的所有子节点,为了不显示根节点
function getTreeObjFirstNode(cat_jdata)
{
	if(cat_jdata != null)
	{
		return cat_jdata[0].children;
	}
}

//得到诉求的
function getAppealLinkUrl()
{
	var sq_page_type = $("#sq_page_type_td :checked").val();
	var model_id = $("#model_id :selected").val();
	if(sq_page_type == "list")
	{
		link_url = "/appeal/list.jsp?model_id="+model_id;
	}
	if(sq_page_type == "form")
	{
		link_url = "/appeal/form.jsp?model_id="+model_id;
	}
	if(sq_page_type == "search")
	{
		link_url = "/appeal/list2.jsp";//搜索页这里不带参数，主要用的form中的action提交路径
	}
}

//得到访谈的
function getInterviewLinkUrl()
{	
	var cat_id = $("#model_id :selected").val();
	var status = $("#status :selected").val();
	link_url = "/interview/list.jsp?status="+status;
	if(cat_id != "")
		link_url += "&cat_id="+cat_id;
}

//得到调查的
function getSurveyLinkUrl()
{	
	var cat_id = $("#model_id :selected").val();
	link_url = "/survey/list.jsp";	
	if(cat_id != "")
		link_url += "?cat_id="+cat_id
}

//返回路径
function saveCreateLink()
{
	var type = $("#link_type :selected").val();
	if(type == "appeal")
	{
		getAppealLinkUrl();
	}
	if(type == "interview")
	{
		getInterviewLinkUrl();
	}	
	if(type == "survey")
	{
		getSurveyLinkUrl();
	}
	if(link_url == "")
	{
		top.msgWargin("未获取到路径地址，请重新选择节点");
		return;
	}
	top.getCurrentFrameObj().editAreaLoader.replaceSelection(link_url);
	top.CloseModalWindow();
}
</script>

</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="Template_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>			
			<td style="width:180px" >　链接类型：
				<select id="link_type" onchange="changeLinkType(this.value)">
					<option value="page" selected="true">页面地址</option>
					<option value="category">内容管理目录</option>
					<option value="zt">专题目录</option>
					<option value="zwgk">信息公开目录</option>
					<option value="ggfw">公共服务目录</option>
					<option value="appeal">诉求业务</option>
					<option value="interview">访谈系统</option>
					<option value="survey">问卷调查</option>
				</select>
			</td>
			<td id="page_type_td" class="hidden">
			 <ul>
			  <li><input type="radio" id="page_type" name="page_type" value="index"><label>目录首页</label></li>
			  <li><input type="radio" id="page_type" name="page_type" value="list" checked="true"><label>目录列表页</label></li>
			  <li><label class="f_red">请先选择页面类型</label></li>
			 </ul>
			</td>
			<td id="sq_page_type_td" class="hidden">
			 <ul>			  
			  <li><input type="radio" id="page_type" name="page_type" value="list"><label>诉求列表页</label></li>
			  <li><input type="radio" id="page_type" name="page_type" value="form"><label>诉求表单页</label></li>
			  <li><input type="radio" id="page_type" name="page_type" value="search"><label>诉求搜索列表页</label></li>
			 </ul>
			</td>
		</tr>
		<tr id="model_id_tr">			
			<td colspan="2">　<span>诉求业务：</span>
				<select id="model_id" >
					
				</select>
			</td>
		</tr>
		<tr id="status_tr" class="hidden">			
			<td colspan="2">　访谈状态：
				<select id="status" >
					<option value="0" selected="true">历史状态</option>
					<option value="1">直播状态</option>
					<option value="2">历史状态</option>
				</select>
			</td>
		</tr>
	</tbody>
</table>
<table id="tree_table" class="" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
		 <td style="text-align:center" align="center">
			<div id="cat_tree_div1" class="border_color" style="margin:0 auto;width:300px; height:360px; overflow:hidden;" >
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
						<ul id="leftMenuTree" class="easyui-tree" animate="true" style="width:382px; height:345px;">
						</ul>
					</div>
				</div>
			</div>
		 </td>
		 <td class="width10"></td>
		 <td style="text-align:center" align="center">
			<div id="cat_tree_div2" class="border_color hidden" style="margin:0 auto;width:300px; height:360px; overflow:hidden;" >
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
						<ul id="leftMenuTree2" class="easyui-tree" animate="true" style="width:382px; height:360px;">
						</ul>
					</div>
				</div>
			</div>
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
			<input id="addButton" name="btn1" type="button" onclick="saveCreateLink()" value="插入" />
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
