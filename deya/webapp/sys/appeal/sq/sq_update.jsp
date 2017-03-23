<%@ page contentType="text/html; charset=utf-8"%>
<%
	String sq_id = request.getParameter("sq_id");
	String top_index = request.getParameter("top_index");
    String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>诉求管理</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link type="text/css" rel="stylesheet" href="../../styles/sq.css" />
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/sqList.js"></script>
<script type="text/javascript">

var sq_id = "<%=sq_id%>";
var top_index = "<%=top_index%>";
var site_id = "<%=site_id%>";
var current_dept_id = getCurrentFrameObj(top_index).current_dept_id;//当前用户所在的部门
var sq_custom_list;//用于存储扩展字段的列表
$(document).ready(function(){
	initButtomStyle();

    initUeditor("sq_content2");
	//得到诉求目地列表
	getPurposeList();
	//得到特征标记列表
	getTagList();
	
	if(sq_id != "" && sq_id != "null" && sq_id != null)
	{		
		//defaultBean = SQRPC.getSqBean(sq_id);
		defaultBean = getCurrentFrameObj(top_index).defaultBean;
		getCPFormListByModel();//得到扩展字段及其内容
		if(defaultBean)
		{
			$("#sq_table").autoFill(defaultBean);
			updateShowText();
		}		
	}
	
	showSelectDiv("cat_name","cat_tree_div",300,"leftMenu");
	showCategoryTree();

	showSelectDiv("area_name","area_tree_div",300,"areaMenu");
	showAreaTree();

	//得到已选过的特征标记
	getSelectTagList();

	init_input();	
	getOptIDSByUser();
});

//得到该登录人所具有的权限ID，显示发布按钮
function getOptIDSByUser()
{
	var opt_ids = ","+jsonrpc.UserLoginRPC.getOptIDSByUserAPPSite(LoginUserBean.user_id,"appeal","")+",";
	if(opt_ids.indexOf(",175,") > -1)
	{
		$("#publish_status_tr").show();
	}
	
}

function getCPFormListByModel()
{
	var from_list = jsonrpc.SQModelRPC.getCPFormListByModel(defaultBean.model_id);
	from_list = List.toJSList(from_list);
	if(from_list != null && from_list.size() > 0)
	{
		var str = "";
		for(var i=0;i<from_list.size();i++)
		{			
			str += '<tr><th>'+from_list.get(i).field_cname+'：</th><td><input id="'+from_list.get(i).field_ename+'" name="'+from_list.get(i).field_ename+'" type="text" class="width200"/></td></tr>';
		}
		$("#sq_title2_info_tr").after(str);
	}

	sq_custom_list = SQRPC.getSQCustomList(defaultBean.sq_id);//得到扩展字段的内容
	sq_custom_list = List.toJSList(sq_custom_list);
	if(sq_custom_list != null && sq_custom_list.size() > 0)
	{
		for(var i=0;i<sq_custom_list.size();i++)
		{
			$("#"+sq_custom_list.get(i).cu_key).val(sq_custom_list.get(i).cu_value);
		}
	}
}

//显示出收信人列表
function showDoDeptInfo()
{	
	var d_list = SQModelRPC.getAppealDeptList(defaultBean.model_id);
	d_list = List.toJSList(d_list);
	if(d_list != null && d_list.size() > 0)
	{
		for(var i=0;i<d_list.size();i++)
		{
			$("#do_dept").addOptionsSingl(d_list.get(i).dept_id,d_list.get(i).dept_name);
			if(d_list.get(i).dept_name == defaultBean.submit_name)
			{
				$("#do_dept option[value='"+d_list.get(i).dept_id+"']").attr("selected",true);
			}
		}
		
	}
}

function updateShowText()
{	
	if(defaultBean.sq_status == 0)
	{
		//如果是未处理的，显示出收信人，并可以更改
		showDoDeptInfo();		
		$("#do_dept_tr").show();
	}
	//内容分类	
	if(defaultBean.cat_id == 0)
	{
		$("#cat_name").val("");
	}else
	{
		try{
			$("#cat_name").val(jsonrpc.AppealCategoryRPC.getCategoryBean(defaultBean.cat_id).cat_cname);
		}catch(e)
		{
			$("#cat_name").val("");	
		}
	}
	//地区分类
	try{
		$("#area_name").val(jsonrpc.AreaRPC.getAreaBean(defaultBean.area_id).area_cname);
	}
	catch(e)
	{
		$("#area_name").val("");
	}	
}

//得到特征标记列表
function getTagList()
{
	var t_list = jsonrpc.AssistRPC.getTagListByAPPSite("appeal","");
	t_list = List.toJSList(t_list); 
	if(t_list != null && t_list.size() > 0)
	{
		for(var i=0;i<t_list.size();i++)
		{
			$("#tag_list").append('<li><input type="checkbox" id="tag_id" name="tag_id" value="'+t_list.get(i).tag_id+'"><label>'+t_list.get(i).tag_name+'</label></li>');
		}
	}
}

//得到已选过的特征标记
function getSelectTagList(){
	var t_list = SQRPC.getSQTagList(sq_id);
	t_list = List.toJSList(t_list); 
	if(t_list != null && t_list.size() > 0)
	{
		for(var i=0;i<t_list.size();i++)
		{
			$(":checkbox[id='tag_id'][value='"+t_list.get(i).tag_id+"']").attr("checked",true);
		}
	}
}

//得到诉求目地列表
function getPurposeList(){
	var p_list = jsonrpc.PurposeRPC.getPurposeList();
	p_list = List.toJSList(p_list);
	$("#pur_id").addOptions(p_list,"pur_id","pur_name");
}

//显示地区分类树
function showAreaTree()
{
	json_data = eval(jsonrpc.AreaRPC.getAreaTreeJsonStr());
	setAppointTreeJsonData("areaTree",json_data);
	initAreaTree();
}
//初始化地区分类树点击事件 
function initAreaTree()
{
	$('#areaTree').tree({		
		onClick:function(node){			
				selectedArea(node.id,node.text);            
		}
	});
}

//点击树节点,修改菜单列表数据 地区
function selectedArea(id,text){
	$("#area_name").val(text);
	$("#area_id").val(id);
	$("#area_tree_div").hide();
}

//显示内容分类树
function showCategoryTree()
{
	json_data = eval(jsonrpc.AppealCategoryRPC.getCategoryTreeJsonStr());
	setLeftMenuTreeJsonData(json_data);
	initMenuTree();
}
//初始化内容分类树点击事件 
function initMenuTree()
{
	$('#leftMenuTree').tree({		
		onClick:function(node){			
				selectedCat(node.id,node.text);            
		}
	});
}

//点击树节点,修改菜单列表数据 内容
function selectedCat(id,text){
	$("#cat_name").val(text);
	$("#cat_id").val(id);
	$("#cat_tree_div").hide();
}

function updateSQ()
{
	var bean = BeanUtil.getCopy(SQBean);	
	$("#sq_table").autoBind(bean);

	if(!standard_checkInputInfo("sq_table"))
	{
		return;
	}

	var tag_ids = $(":checkbox[id='tag_id']").getCheckValues()+"";
	bean.sq_id = sq_id;

	if(defaultBean.sq_status == 0)
	{
		//是在未处理状态，有可能修改收信人的值，这里要处理一下
		bean.submit_name = $("#do_dept :selected").text();
		if(defaultBean.do_dept == 0)
		{
			bean.do_dept = defaultBean.do_dept;
		}else
			bean.do_dept = $("#do_dept :selected").val();
	}
	else
	{//如果不是在未处理状态，直接使用原值
		bean.do_dept = defaultBean.do_dept;
		bean.submit_name = defaultBean.submit_name;
	}
	bean.sq_content2 = getV("sq_content2");
	
	if(SQRPC.updateSQ(bean,tag_ids))
	{
		//处理扩展字段
		if(sq_custom_list != null && sq_custom_list.size() > 0)
		{
			for(var i=0;i<sq_custom_list.size();i++)
			{
				sq_custom_list.get(i).cu_value = $("#"+sq_custom_list.get(i).cu_key).val();
			}
			SQRPC.updateSQCustom(sq_custom_list);
		}

		msgAlert(WCMLang.Add_success);
		getCurrentFrameObj(top_index).location.reload();//刷新上一个列表
		tab_colseOnclick(curTabIndex);
	}else
	{
		msgAlert(WCMLang.Add_fail);
	}
}

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="sq_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr id="do_dept_tr" class="hidden">
			<th>收信单位/人：</th>
			<td>
				<select id="do_dept" class="width205" onchange="" >						
					
				</select>
			</td>
		</tr>
		<tr>
			<th>诉求目地：</th>
			<td>
				<select id="pur_id" class="width205" onchange="" >						
					
				</select>
			</td>
		</tr>
		<tr>
			<th>内容分类：</th>
			<td>
				<input type="text" id="cat_name" value="" class="width200" readOnly="readOnly"/><input type="hidden" id="cat_id" value="" />
				<div id="cat_tree_div" class="select_div tip hidden border_color">
					<div id="leftMenuBox">
						<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
							<ul id="leftMenuTree" class="easyui-tree tree_list_none" animate="true">
							</ul>
						</div>
					</div>
				</div>
			</td>
		</tr>	
		<tr>
			<th>发生地区：</th>
			<td>
				<input type="text" id="area_name" value="" class="width200" readOnly="readOnly"/><input type="hidden" id="area_id" value="" />
				<div id="area_tree_div" class="select_div tip hidden border_color">
					<div id="areaBox">
						<div id="areaMenu" class="contentBox6 textLeft" style="overflow:auto">
							<ul id="areaTree" class="easyui-tree tree_list_none" animate="true">
							</ul>
						</div>
					</div>
				</div>
			</td>
		</tr>	
		<tr id="gzyy_open_tr">
			<th>公开意愿：</th>
			<td>
			<ul>
				<li><input id="is_open" name="is_open" type="radio"  value="1"/><label>公开</label></li>
				<li><input id="is_open" name="is_open" type="radio" checked="checked"  value="0" /><label>不公开</label></li>
			</ul>
			</td>
		</tr>
		<tr id="publish_status_tr" class="hidden">
			<th>发布状态：</th>
			<td>
			<ul id="a123">
				<li><input id="publish_status" name="publish_status" type="radio" checked="checked"  value="0" /><label>未发布</label></li>
				<li><input id="publish_status" name="publish_status" type="radio"  value="1"/><label>发布</label></li>				
			</ul>
			</td>
		</tr>
		<tr id="sq_title2_info_tr">
			<th>信件标题 ：</th>
			<td><input id="sq_title2" name="sq_title2" type="text" class="width200" value="" onblur="checkInputValue('sq_title2',false,200,'信件标题','')"/></td>
		</tr>
		<tr>
			<th>信件内容 ：</th>
			<td>
                <script id="sq_content2" type="text/plain" style="width:620px;height:200px;"></script>
			</td>
		</tr>
		<tr>
			<th>权重：</th>
			<td>
				<input id="weight" name="weight" type="text" style="width:50px;" value="60"  maxlength="2" onblur="checkInputValue('weight',true,2,'权重','checkNumber')"/>默认值：60，取值范围（0-99）
			</td>
		</tr>
		<tr>
			<th>特征标记：</th>
			<td>
				<ul id="tag_list">
				</ul>
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
			<input id="userAddButton" name="btn1" type="button" onclick="updateSQ()" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('sq_table',sq_id)" value="重置" />
			<input id="userAddCancel" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex)" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
