<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>访谈模型添加</title>


<link rel="stylesheet" type="text/css" href="/sys/styles/themes/default/tree.css">
<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript" src="/sys/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/sys/js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/design_util.js"></script>
<script type="text/javascript">
var action_type = "${param.a_t}";
var site_id = "${param.site_id}";
var DesignRPC = jsonrpc.DesignRPC;

var frameList = null;
var styleList = null;
var sel_module_id = "";
var module_styles_id = "";
var sel_frame_id = "";
var sel_style_id = "";
var sel_class_name = "";

var default_frame_id = "";//修改时记录原始ID
var default_style_id = "";//修改时记录原始ID
var old_class_name = "";
var sel_datasouce_type = 100;
var datasouce_str = "";
var sel_attr_ids = "";
var temp_cat_id;//临时获取点击的目录节点ID
var temp_cat_name = "";
var css_ename = "";

init_editer("content");
$(document).ready(function(){
	initButtomStyle();
	init_FromTabsStyle();
	
	if(top.default_css_id == "" || top.default_css_id == null)
	{
		alert("请先选择主题风格");
		top.CloseModalWindow();
		return;
	}
	else
		css_ename = DesignRPC.getDesignCssBean(top.default_css_id).css_ename


	$("label").live("click",function(){
		$(this).prev().click();
	});
	
	if(action_type == "update")
	{
		$(".fromTabs li:first").hide();
		$(".fromTabs li:last").click();

		sel_module_id = top.currnet_module.find(".module_body > div").attr("module_id");
		var moduleBean = DesignRPC.getDesignModuleBean(sel_module_id);
		module_styles_id = moduleBean.style_ids;
		sel_datasouce_type = moduleBean.datasouce_type;

		sel_attr_ids = moduleBean.attr_ids;
		getDataSouce();

		if(sel_datasouce_type == 0)
		{
			selectedCatTreeNode(top.currnet_module.find(".module_body > div").attr("cat_ids"));
		}
		if(sel_datasouce_type == 1)
		{
			$("#content").val(top.currnet_module.find(".module_body > div").html());
			KE.html("content",top.currnet_module.find(".module_body > div").html());
			
		}

		default_style_id = top.currnet_module.find(".module_body > div").attr("style_id");
		default_frame_id = top.currnet_module.find(" > div").attr("frame_id");

		//处理属性设置列表
		getAttrList();
		var attr_str = top.currnet_module.find(".module_body > div").attr("attr_str");
		if(attr_str != "" && attr_str != null)
		{
			var tempA = attr_str.split(",");
			for(var i=0;i<tempA.length;i++)
			{
				var ename = tempA[i].substring(0,tempA[i].indexOf("*"));
				var value = tempA[i].substring(tempA[i].indexOf("*")+1);
				if($("#"+ename+"_input").attr("type") == "text")
				{
					$("#"+ename+"_input").val(value);
				}
				if($("#"+ename+"_input").attr("type") == "checkbox")
				{
					if(value == 1)
						$("#"+ename+"_input").attr("checked","true");
					else
						$("#"+ename+"_input").removeAttr("checked");
				}
				if($("#"+ename+"_input").attr("type") == "radio")
				{
					$("#"+ename+"_input[value='"+value+"']").attr("checked","true");
				}
			}
		}
	}else
	{
		loadModuleList();
	}
	init_input();
});



//初始加载模块列表
function loadModuleList()
{
	var m = new Map();
	m.put("start_num", 0);	
	m.put("page_size", 20);

	var beanList = DesignRPC.getDesignModuleList(m);
	beanList = List.toJSList(beanList);
	for(var i=0;i<beanList.size();i++)
	{
		var tmpPicLi = "<li style='width:125px;height:130px;padding:0px 5px'><div style='width:100%;text-align:ceter'><img src='"+beanList.get(i).thumb_url+"' style='width:120px;height:100px' align='center'/></div>";

		tmpPicLi += "<div><input id='layout_radio' name='layout_radio' type='radio' onclick='checkedModule("+beanList.get(i).module_id+",\""+beanList.get(i).style_ids+"\","+beanList.get(i).datasouce_type+",\""+beanList.get(i).attr_ids+"\")'";		
		tmpPicLi += "/><label>"+beanList.get(i).module_name+"</label></div></li>";

		$("#moduleList").append(tmpPicLi);
	}
}

//选中模块事件
function checkedModule(id,style_ids,d_type,attr_ids)
{
	sel_module_id = id;
	module_styles_id = style_ids;
	sel_datasouce_type = d_type;
	sel_attr_ids = attr_ids;
}

//选中外框样式事件
function checkedFrame(id)
{
	sel_frame_id = id;
}

//选中内容样式事件
function checkedStyle(id,class_name)
{
	sel_style_id = id;
	sel_class_name = class_name;
}

//得到外框样式列表
function getFrameList()
{
	if(frameList == null)
	{
		var m = new Map();
		m.put("start_num", 0);	
		m.put("page_size", 20);

		frameList = DesignRPC.getDesignFrameList(m);
		frameList = List.toJSList(frameList);
		for(var i=0;i<frameList.size();i++)
		{
			var tmpPicLi = "<li style='width:125px;height:130px;padding:0px 5px'><div style='width:100%;text-align:ceter'><img src='/cms.files/design/theme/"+css_ename+"/thumb/"+frameList.get(i).thumb_url+"' style='width:120px;height:100px' align='center'/></div>";

			tmpPicLi += "<div><input id='frame_radio' name='frame_radio' type='radio' onclick='checkedFrame("+frameList.get(i).frame_id+")'";
			
			if(default_frame_id == frameList.get(i).frame_id)
			{
				tmpPicLi += "checked='true'";
			}
			tmpPicLi +="/><label>"+frameList.get(i).frame_name+"</label></div></li>";

			$("#frameList").append(tmpPicLi);
		}
	}
	init_input();
}

//得到内容样式列表
function getStyleList()
{	
	$("#styleList").empty();
	var m = new Map();
	m.put("start_num", 0);	
	m.put("page_size", 100);
	if(module_styles_id == "" || module_styles_id == null)
		m.put("style_ids", "0");//没有关联的不取出内容样式
	else
		m.put("style_ids", module_styles_id);

	styleList = DesignRPC.getDesignStyleList(m);
	styleList = List.toJSList(styleList);
	for(var i=0;i<styleList.size();i++)
	{
		var tmpPicLi = "<li style='width:125px;height:130px;padding:0px 5px'><div style='width:100%;text-align:ceter'><img src='"+styleList.get(i).thumb_url+"' style='width:120px;height:100px' align='center'/></div>";

		tmpPicLi += "<div><input id='style_radio' name='style_radio' type='radio' onclick='checkedStyle("+styleList.get(i).style_id+",\""+styleList.get(i).class_name+"\")' value='"+styleList.get(i).class_name+"'";
		if(default_style_id == styleList.get(i).style_id)
		{
			tmpPicLi += "checked='true'";
			old_class_name = styleList.get(i).class_name;
		}
		tmpPicLi += "/><label>"+styleList.get(i).style_name+"</label></div></li>";

		$("#styleList").append(tmpPicLi);
	}
	
	init_input();
}

//得到数据源
function getDataSouce()
{		
	$("#leftMenuBox").hide();
	$("#htmleditDiv").hide();
	if(sel_datasouce_type == 0)
	{
		$("#leftMenuBox").show();
		var cat_jdata = jsonrpc.CategoryRPC.getCategoryTreeBySiteID(site_id);
		var zt_jdata = jsonrpc.CategoryRPC.getZTCategoryTreeJsonStr(site_id);
		var new_jdata = "";
		if(zt_jdata != "" && zt_jdata != null)
		{
			cat_jdata = cat_jdata.substring(1,cat_jdata.length-1);
			zt_jdata = zt_jdata.substring(1,zt_jdata.length-1);
			new_jdata = eval("["+cat_jdata+","+zt_jdata+"]");
		}else
		{
			new_jdata = eval(cat_jdata);			
		}
		
		setAppointTreeJsonData("leftMenuTree",new_jdata);
		initMenuTree();
	}
	if(sel_datasouce_type == 1)
	{
		$("#htmleditDiv").show();
	}
}

//得到属性列表
function getAttrList()
{
	$("#attr_tbody").empty();
	if(sel_attr_ids != "")
	{
		var tempA = sel_attr_ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			var o = eval("new "+tempA[i]+"()");
			
			$("#attr_tbody").append('<tr><th>'+o.name+'</th><td>'+o.input_text+'</td></tr>');
		}	
		init_input();
	}
}

//得到属性设置，并将对象放入到list中
function getAttrValue()
{
	var attr_list = new List();
	if(sel_attr_ids != "")
	{
		var tempA = sel_attr_ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			var o = eval("new "+tempA[i]+"()");
			var input_obj = $("input[id="+tempA[i]+"_input"+"]");	
			if(input_obj.is("input"))
			{
				if(input_obj.attr("type") == "text")
				{
					var val = input_obj.val();	
					if(val != "" && val != null)
						o.value = val;
				}else
				{
					var val = $(":checked[id="+tempA[i]+"_input"+"]").val();
					if(val != "" && val != null)
						o.value = val;
					else
						o.value = 0;
				}
			}
			attr_list.add(o);
		}
	}
	return attr_list;
}

//初始化栏目树
function initMenuTree()
{
	
	$("#leftMenuTree .tree-icon").after('<SPAN class="tree-checkbox tree-checkbox0"></SPAN>');
	$("#leftMenuTree .tree-checkbox").click(function(){
		if($(this).attr("class").indexOf("tree-checkbox0") > -1)
		{
			$("#leftMenuTree .tree-checkbox").removeClass("tree-checkbox1");			
			$(this).addClass("tree-checkbox1");
			temp_cat_id = $(this).parent().attr("node-id");
			temp_cat_name = $(this).parent().text();
		}
	});

	$("#leftMenuTree > li > div > .tree-checkbox").remove();

	if(temp_cat_id != null)
	{
		$("#leftMenuTree div[node-id="+temp_cat_id+"] .tree-checkbox").addClass("tree-checkbox1");
	}	
}

//选中树节点
function selectedCatTreeNode(cat_ids)
{
	if(cat_ids != "" && cat_ids != null)
	{
		var tempA = cat_ids.split(",");
		if(tempA != null && tempA.length > 0)
		{
			for(var i=0;i<tempA.length;i++)
			{
				$("#leftMenuTree div[node-id="+tempA[i]+"]").find(".tree-checkbox").click();
			}
		}
	}
}

//保存返回事件
function returnValues()
{
	if(sel_datasouce_type == 0)
	{
		datasouce_str = temp_cat_id;
	}
	if(sel_datasouce_type == 1)
	{
		datasouce_str = getV();
	}

	if(action_type == "update")
	{
		var frameBean = null;
		if(sel_frame_id != "" && sel_frame_id != null)
			frameBean = DesignRPC.getDesignFrameBean(sel_frame_id);

		top.updateModuleEvent(frameBean,sel_style_id,sel_class_name,old_class_name,sel_datasouce_type,datasouce_str,temp_cat_name,getAttrValue());
	}
	else
	{
		if(sel_module_id == "")
		{
			alert("模块不能为空，请选择模块");
			return;
		}
		var moduleBean = DesignRPC.getDesignModuleBean(sel_module_id);
		if(sel_frame_id == "")
		{
			alert("外框样式不能为空，请选择外框样式");
			return;
		}
		var frameBean = DesignRPC.getDesignFrameBean(sel_frame_id);
		top.insertModuleEvent(moduleBean,frameBean,sel_style_id,sel_class_name,datasouce_str,temp_cat_name,action_type,getAttrValue());
	}
	top.CloseModalWindow();
}

function getV(){
	return KE.html("content");
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
						<div class="tab_right" >模块资源</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" onclick="getFrameList()">外框样式</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" onclick="getStyleList()">内容样式</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" onclick="getDataSouce()">数据源</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" onclick="getAttrList()">属性设置</div>
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
<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:98%">
	<tbody>
		<tr>			
			<td align="center" >
				<div style="width:100%;height:435px;overflow:auto;background:#FFFFFF;" class="border_color">
					<ul id="moduleList" class="imgList">
					</ul>
				</div>
			</td>
		</tr>		
	</tbody>
</table>
</div>

<div class="infoListTable hidden" id="listTable_1">
<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:98%">
	<tbody>
		<tr>			
			<td align="center" >
				<div style="width:100%;height:435px;overflow:auto;background:#FFFFFF;" class="border_color">
					<ul id="frameList" class="imgList">
					</ul>
				</div>
			</td>
		</tr>		
	</tbody>
</table>
</div>

<div class="infoListTable hidden" id="listTable_2">
<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:98%">
	<tbody>
		<tr>			
			<td align="center" >
				<div style="width:100%;height:435px;overflow:auto;background:#FFFFFF;" class="border_color">
					<ul id="styleList" class="imgList">
					</ul>
				</div>
			</td>
		</tr>		
	</tbody>
</table>
</div>

<div class="infoListTable hidden" id="listTable_3">
<table id="table" class="" border="0" cellpadding="0" cellspacing="0" style="width:98%">
	<tbody>
		<tr>			
			<td align="center" >
				<div id="cat_tree_div" class="select_div border_color" style="width:100%; height:435px; overflow:auto;border:1px #7f9db9 solid;" >
					<div id="leftMenuBox" class="hidden">
						<div id="leftMenu" class="contentBox6 textLeft" style="height:400px;">
							<ul id="leftMenuTree" class="easyui-tree" animate="true" style="width:176px;overflow:hidden">
							</ul>
							<span class="blank3"></span >
						</div>
					</div>
					<div id="htmleditDiv" class="hidden">
						<textarea id="content" name="content" style="width:100%;height:400px;visibility:hidden;"></textarea>
					</div>
				</div>				
			</td>
		</tr>		
	</tbody>
</table>
</div>

<div class="infoListTable hidden" id="listTable_4">
<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:98%" >
	<tbody id="attr_tbody">
		
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
			<input id="addButton" name="btn1" type="button" onclick="returnValues()" value="保存" />			
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>

</form>
</body>
</html>
