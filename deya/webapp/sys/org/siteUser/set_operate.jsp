<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String user_id = request.getParameter("user_id");
	String priv_type = request.getParameter("priv_type");
	String handl_name = request.getParameter("handl_name");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择推荐信息</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<style>
.table_form2 td{ text-align:left; color:#32609E; border-collapse:collapse; vertical-align:middle; vertical-align:middle;}
.table_form2 td li{ float:left; padding-right:20px;}
</style>
<script type="text/javascript">
var handl_name = "<%=handl_name%>";
var priv_type = <%=priv_type%>;
var user_id = <%=user_id%>;
var app_id = parent.getCurrentFrameObj().appId;
var site_id = parent.getCurrentFrameObj().siteId;
if(site_id == "" || site_id == "null" || site_id == null){
    site_id = "CMSmsgjj";
}
var CategoryRPC = jsonrpc.CategoryRPC;
var json_data;
var selected_category_ids = "";

$(document).ready(function(){	
	if(app_id == "ggfw")
	{
		$(".list_tab").eq(1).hide();
		$(".list_tab").eq(2).hide();
	}
	else
	{
		if(site_id.indexOf("GK") > -1)
		{
			$(".list_tab").eq(2).hide();
			$(".list_tab").eq(3).hide();
		}else
		{
            $(".list_tab").eq(2).hide();
			$(".list_tab").eq(3).hide();
		}
	}
	
	initButtomStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	init_FromTabsStyle();
	
	getRoleList();
	getSelectedRoleID();
	selected_category_ids = ","+CategoryRPC.getCategoryIDSByUser(priv_type,user_id,site_id)+",";//得到能管理的栏目ID

       	var category_b = false;
	var zt_b = false;
	var fw_b = false;
	showCategoryLabelInfo("cate");
	showCategoryLabelInfo("zt");
	showCategoryLabelInfo("fw")

});

function getSelectedRoleID()
{
	var role_ids = getCurrentFrameObj().getCurrentSelectedRoleID(user_id);
	
	if(role_ids != "" && role_ids != null)
	{
		var tempA = role_ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			$(":checkbox[value="+tempA[i]+"]").attr("checked",true);
		}
	}
}

function getRoleList()
{
	var RoleRPC = jsonrpc.RoleRPC;
	var role_list = RoleRPC.getRoleListByAPPAndSite(app_id,site_id);
	role_list = List.toJSList(role_list);
	if(role_list != null && role_list.size() > 0)
	{
		for(var i=0;i<role_list.size();i++)
		{
			if(role_list.get(i).role_id != 11)
				$("#role_list").append('<li style="float:none;height:20px"><input type="checkbox" id="role_id_checkbox" value="'+role_list.get(i).role_id+'"><label>'+role_list.get(i).role_name+'</label></li>');
		}
	}
	init_input();
}

var category_b = false;
var zt_b = false;
var fw_b = false;
function showCategoryLabelInfo(type)
{
	if(type == "cate" && category_b == false)
	{
		json_data = eval(CategoryRPC.getCategoryTreeBySiteID(site_id));		
		json_data = json_data[0].children;		
		setLeftMenuTreeJsonData(json_data);
		initMenuClick("leftMenuTree");
		setSelectedNode("leftMenuTree");
		category_b = true;
	}
	if(type == "zt" && zt_b == false)
	{
		json_data = eval(CategoryRPC.getZTCategoryTreeJsonStr(site_id));		
		setAppointTreeJsonData("zt_leftMenuTree",json_data);
		initMenuClick("zt_leftMenuTree");
		setSelectedNode("zt_leftMenuTree");
		zt_b = true;
	}
	if(type == "fw" && fw_b == false)
	{
		json_data = eval(CategoryRPC.getFWCategoryTreeByUserID(site_id));		
		setAppointTreeJsonData("fw_leftMenuTree",json_data);
		initMenuClick("fw_leftMenuTree");
		setSelectedNode("fw_leftMenuTree");
		fw_b = true;
	}
}

//选中已选　过的栏目
function setSelectedNode(div_name)
{
	$("#"+div_name+" div[node-id]").each(function(){
		if(selected_category_ids.indexOf(","+$(this).attr("node-id")+",") > -1)
		{
			$(this).find(".tree-checkbox").click();
		}
	});
}

//初始化复选框树
function initMenuClick(div_name)
{	
	$("#"+div_name+" .tree-icon").after('<SPAN class="tree-checkbox tree-checkbox0"></SPAN>');

	$("#"+div_name+" .icon-category").next().remove();//icon-category 表示该节点是zt的分类，不属于节点，所以删除掉
	$("#"+div_name+" .tree-checkbox").click(function(){
		if($(this).attr("class").indexOf("tree-checkbox0") > -1)
		{
			$(this).parent().parent().find(".tree-checkbox").removeClass("tree-checkbox0");
			$(this).parent().parent().find(".tree-checkbox").removeClass("tree-checkbox1");
			$(this).parent().parent().find(".tree-checkbox").addClass("tree-checkbox3");

			$(this).removeClass("tree-checkbox3");
			$(this).addClass("tree-checkbox1");
		}
		else
		{
			if($(this).attr("class").indexOf("tree-checkbox1") > -1)
			{
				$(this).parent().parent().find(".tree-checkbox").removeClass("tree-checkbox3");		
				$(this).parent().parent().find(".tree-checkbox").addClass("tree-checkbox0");

				$(this).removeClass("tree-checkbox1");
				$(this).addClass("tree-checkbox0");
			}
		}
	});
}


function saveUserOperate()
{	
	var cate_ids = getSelectedNodeIDS("leftMenuTree") + getSelectedNodeIDS("zt_leftMenuTree") + getSelectedNodeIDS("fw_leftMenuTree");
	if(cate_ids != "" && cate_ids != null)
		cate_ids = cate_ids.substring(1);

	var role_ids = "";	
	$("#role_list :checkbox[checked]").each(function(){
		role_ids += ","+$(this).val();
	});
	if(role_ids != "")
		role_ids = role_ids.substring(1);
	
	var bools = false;
	if(priv_type == 0)
	{
		var RoleUserBean = new Bean("com.deya.wcm.bean.org.role.RoleUserBean",true);
		var bean = BeanUtil.getCopy(RoleUserBean);	
		bean.role_id = role_ids;
		bean.app_id = app_id;
		bean.site_id = site_id;
		bean.user_id = user_id;	
		bools = jsonrpc.RoleRPC.insertRoleUserByUser(bean) && CategoryRPC.insertCategoryReleUser(cate_ids,priv_type,user_id,site_id);
	}
	if(priv_type == 1)
	{
		var RoleUGroupBean = new Bean("com.deya.wcm.bean.org.role.RoleUGroupBean",true);
		var bean = BeanUtil.getCopy(RoleUGroupBean);
		bean.role_id = role_ids;
		bean.app_id = app_id;
		bean.site_id = site_id;
		bean.group_id = user_id;
		bools = jsonrpc.RoleRPC.insertRoleUGroupByGroup(bean) && CategoryRPC.insertCategoryReleUser(cate_ids,priv_type,user_id,site_id);
	}		

	if(bools)
	{
		parent.msgAlert("权限设置"+WCMLang.Add_success);
		eval("getCurrentFrameObj()."+handl_name+"()");
        parent.CloseModalWindow();
	}else
	{
        parent.msgAlert("权限设置"+WCMLang.Add_fail);
	}
}

function getSelectedNodeIDS(div_name)
{
	var ids = "";
	$("#"+div_name+" .tree-checkbox1").each(function(){		
		ids += ","+$(this).parent().attr("node-id");
	});
	return ids;
}

function checkAllTree()
{
	if($("#checkAll").is(':checked'))
	{
		$("#checkAll").attr("checked",false);		

		var allObj = $("#leftMenuTree .tree-checkbox");
		allObj.removeClass("tree-checkbox1");
		allObj.removeClass("tree-checkbox3");
		allObj.addClass("tree-checkbox0");		
	}else
	{
		$("#checkAll").attr("checked",true);

		var allObj = $("#leftMenuTree .tree-checkbox");
		allObj.removeClass("tree-checkbox1");
		allObj.removeClass("tree-checkbox0");
		allObj.addClass("tree-checkbox3");		

		var obj = $("#leftMenuTree > li > div > .tree-checkbox");		
		obj.removeClass("tree-checkbox0");
		obj.removeClass("tree-checkbox3");
		obj.addClass("tree-checkbox1");

	}
}

function checkAllTree2(obj)
{
	if(!$(obj).is(':checked'))
	{
		var allObj = $("#leftMenuTree .tree-checkbox");
		allObj.removeClass("tree-checkbox1");
		allObj.removeClass("tree-checkbox3");
		allObj.addClass("tree-checkbox0");		
	}else
	{
		

		var allObj = $("#leftMenuTree .tree-checkbox");
		allObj.removeClass("tree-checkbox1");
		allObj.removeClass("tree-checkbox0");
		allObj.addClass("tree-checkbox3");		

		var obj = $("#leftMenuTree > li > div > .tree-checkbox");		
		obj.removeClass("tree-checkbox0");
		obj.removeClass("tree-checkbox3");
		obj.addClass("tree-checkbox1");

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
		
				<li class="list_tab list_tab_over list_tab_cur ">
					<div class="tab_left">
						<div class="tab_right" >角色</div>
					</div>
				</li>
				<li class="list_tab list_tab_over list_tab_cur">
					<div class="tab_left">
						<div class="tab_right" onclick="showCategoryLabelInfo('cate')">栏目</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" onclick="showCategoryLabelInfo('zt')">专题</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" onclick="showCategoryLabelInfo('fw')">服务目录</div>
					</div>
				</li>
			</ul>
		</td> 	
		
	</tr>
</table>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">

<div class="infoListTable" id="listTable_0">
<table id="table" class="table_form2" border="0" cellpadding="0" cellspacing="0" style="width:94%">
	<tbody>
		<tr>			
			<td align="center" >
				<div style="width:100%;height:395px;overflow:auto;border:1px solid #828790;background:#FFFFFF;">
					<ul id="role_list" style="margin:10px">
					</ul>
				</div>
			</td>
		</tr>		
	</tbody>
</table>
<span class="blank12"></span>
</div>

<div class="infoListTable hidden" id="listTable_1">
<table id="table" class="" border="0" cellpadding="0" cellspacing="0" style="width:94%">
	<tbody>
		<tr>			
			<td align="center" >
				<div style="text-align:left"><input type="checkbox" id="checkAll" onclick="checkAllTree2(this)"><label onclick="checkAllTree()">全选</label></div>
				<div id="leftMenuBox" style="width:100%;height:381px;overflow:auto;border:1px solid #828790;background:#FFFFFF;">
					<div id="leftMenu" class="contentBox6 textLeft" >
						<ul id="leftMenuTree" class="easyui-tree" animate="true">
						</ul>
					</div>
				</div>
			</td>
		</tr>		
	</tbody>
</table>
<span class="blank12"></span>
</div>

<div class="infoListTable hidden" id="listTable_2">
<table id="table" class="" border="0" cellpadding="0" cellspacing="0" style="width:94%">
	<tbody>
		<tr>			
			<td align="center" >
				<div id="zt_leftMenuBox" style="width:100%;height:395px;overflow:auto;border:1px solid #828790;background:#FFFFFF;">
					<div id="zt_leftMenu" class="contentBox6 textLeft" >
						<ul id="zt_leftMenuTree" class="easyui-tree" animate="true">
						</ul>
					</div>
				</div>
			</td>
		</tr>		
	</tbody>
</table>
<span class="blank12"></span>
</div>

<div class="infoListTable hidden" id="listTable_3">
<table id="table" class="" border="0" cellpadding="0" cellspacing="0" style="width:94%">
	<tbody>
		<tr>			
			<td align="center" >
				<div id="fw_leftMenuBox" style="width:100%;height:395px;overflow:auto;border:1px solid #828790;background:#FFFFFF;">
					<div id="fw_leftMenu" class="contentBox6 textLeft" >
						<ul id="fw_leftMenuTree" class="easyui-tree" animate="true">
						</ul>
					</div>
				</div>
			</td>
		</tr>		
	</tbody>
</table>
<span class="blank12"></span>
</div>

<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="saveUserOperate()" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="form1.reset()" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="CloseModalWindow();" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>

</form>
</body>
</html>
