<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公开应用目录汇聚规则设置</title>
<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/catalogList.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>

<script type="text/javascript">
var cata_id = "${param.cata_id}";
var app_id = "cms";
var choose_type = "";
var CategoryRPC = jsonrpc.CategoryRPC;
var AppCatalogRPC = jsonrpc.AppCatalogRPC;
var RegulationBean = new Bean("com.deya.wcm.bean.zwgk.appcatalog.RegulationBean",true);
var node_json_data = "";

$(document).ready(function() {
	initButtomStyle();
	init_input();
	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");	
	getAllowSharedSite();

	node_json_data = eval(jsonrpc.SiteRPC.getSiteTreeJsonStr());
	initMenuTree("leftMenuTree2",true);
	changeCategoryId("cms");
	getSourceRegulationList();//得到已选过的规则
});

function getSourceRegulationList()
{
	var l = AppCatalogRPC.getAppCataReguList(cata_id);
	l = List.toJSList(l);
	if(l != null && l.size() > 0)
	{
		for(var i=0;i<l.size();i++)
			setIDsInDivList(l.get(i).regu_type,l.get(i).cat_ids,l.get(i).cat_id_names,l.get(i).site_ids,l.get(i).site_id_names);
	}
}
//得到共享信息的站点
function getAllowSharedSite()
{		
	//$("#tsArea").addOptionsSingl("zwgk","信息公开");
	$("#tsArea").addOptionsSingl("cms","站点内容管理");
	//共享目录的树
	var sharedCategoryList = CategoryRPC.getCateClassListByApp(app_id);
	sharedCategoryList =List.toJSList(sharedCategoryList);
	for(var i=0;i<sharedCategoryList.size();i++)
	{
		if(sharedCategoryList.get(i).class_type == 1)
		{
			$("#tsArea").addOptionsSingl(sharedCategoryList.get(i).class_id,sharedCategoryList.get(i).class_cname);
		}
	}	
}

function changeCategoryId(val)
{
	choose_type = val;
	if(val == "cms")
	{
		$("#leftMenuTree2").empty();
		initMenuTree("leftMenuTree",false);
		setLeftMenuTreeJsonData(node_json_data);
		initCMSTree();
	}
	else
	{
		initMenuTree("leftMenuTree",true);		
		setLeftMenuTreeJsonData(eval(CategoryRPC.getCategoryTreeByClassID(val)));
		setAppointTreeJsonData("leftMenuTree2",node_json_data);
	}
}

function initCMSTree()
{
	$('#leftMenuTree').tree({
		onClick:function(node){
			s_site_id = node.id;
			var jdata = eval(CategoryRPC.getCategoryTreeBySiteID(node.id));
			jdata = getTreeObjFirstNode(jdata);
			$("#leftMenuTree2").empty();
			
			setAppointTreeJsonData("leftMenuTree2",jdata);
		}
	});	
}

//得到树中的第一个节点的所有子节点,为了不显示根节点
function getTreeObjFirstNode(cat_jdata)
{
	if(cat_jdata != null)
	{
		return cat_jdata[0].children;
	}
}

function initMenuTree(div_name,is_checkbox)
{
	$('#'+div_name).tree({	
		checkbox: is_checkbox		
	});
}

function choose_regu()
{
	var node_ids = "";
	var node_names = "";
	var cat_ids = "";
	var cat_names = "";
	if(choose_type == "cms")
	{
		var temp_ids = getLeafTreeNode("leftMenuTree2");
		if(temp_ids == "|")
		{
			top.msgWargin("请选择信息公开目录");
			return;
		}
		cat_ids = temp_ids.split("|")[0];
		cat_names = temp_ids.split("|")[1];
		node_ids = $('#leftMenuTree').tree('getSelected').id;
		node_names = $('#leftMenuTree').tree('getSelected').text;
	
		setIDsInDivList(1,cat_ids,cat_names,node_ids,node_names);
	}else
	{
		var temp_ids = getLeafTreeNode("leftMenuTree");
		if(temp_ids == "|")
		{
			top.msgWargin("请选择共享目录");
			return;
		}
		cat_ids = temp_ids.split("|")[0];
		cat_names = temp_ids.split("|")[1];

		var temp_nodes = getLeafTreeNode("leftMenuTree2");
		if(temp_ids != "|")
		{
			node_ids = temp_nodes.split("|")[0];
			node_names = temp_nodes.split("|")[1];
		}
		setIDsInDivList(0,cat_ids,cat_names,node_ids,node_names);
	}
}

function setIDsInDivList(regu_type,cat_ids,cat_names,node_ids,node_names)
{
	if(regu_type == 0)
	{
		$("#data_cat").append('<li style="float:none;height:20px" cat_ids="'+cat_ids+'" node_ids="'+node_ids+'"><img onclick="deleteLi(this)" src="../../images/delete.png" width="15" height="15" alt=""/>&nbsp;'+cat_names+'&nbsp;&nbsp;&nbsp;&nbsp;'+node_names+'</li>');
	}else
	{
		$("#data_node").append('<li style="float:none;height:20px" cat_ids="'+cat_ids+'" node_ids="'+node_ids+'"><img onclick="deleteLi(this)" src="../../images/delete.png" width="15" height="15" alt=""/>&nbsp;'+node_names+'&nbsp;&nbsp;&nbsp;&nbsp;'+cat_names+'</li>');
	}
}

function deleteLi(obj)
{
	$(obj).parent().remove();
}

function getLeafTreeNode(div_name){
	var ids = "";
	var texts = "";
	$('#'+div_name+' span.tree-file+span.tree-checkbox1').each(function(i){
		if(i>0)
		{
			ids +=",";
			texts +=",";
		}
		if(div_name == "leftMenuTree2" && choose_type != "cms")
		{
			var node = $('#'+div_name).tree('find',$(this).parent().attr("node-id"));
			ids += node.attributes.t_node_id;
		}
		else
			ids += $(this).parent().attr("node-id");
		texts += $(this).parent().text();
	});
	return ids+"|"+texts;
}

function related_ok()
{
	var reg_list = new List();
	$("#data_cat li").each(function(){
		var bean = BeanUtil.getCopy(RegulationBean);
		bean.cata_id = cata_id;
		bean.site_ids = $(this).attr("node_ids");
		bean.cat_ids = $(this).attr("cat_ids");
		bean.regu_type = 0;
		reg_list.add(bean);
	});

	$("#data_node li").each(function(){
		var bean = BeanUtil.getCopy(RegulationBean);
		bean.cata_id = cata_id;
		bean.site_ids = $(this).attr("node_ids");
		bean.cat_ids = $(this).attr("cat_ids");
		bean.regu_type = 1;
		reg_list.add(bean);
	});

	if(AppCatalogRPC.insertAppCateRegu(reg_list,cata_id))
	{
		top.msgAlert("目录汇聚规则"+WCMLang.Set_success);
		top.CloseModalWindow();
	}else
	{
		top.msgWargin("目录汇聚规则"+WCMLang.set_fail);
	}
}
</script>
<style type="text/css">
h3{height:20px;}

.main{padding:0px; margin:auto;width:660px; border:0px #abadb3 solid;}

.topMain{width:660px; height:30px;}
.topMain .leftA{float:left;}
.topMain .rightB{float:right;}

.leftDiv{border:1px #abadb3 solid; float:left;}

.rightDiv{border:1px #abadb3 solid; float:left;}

.clear{clear:both;}

.footMain{padding-top:5px; text-align: center;}

.txt_list{padding-left:8px; padding-top:10px; line-height:20px; padding-right:10px;}

.txt_list li{height:24px; font-size:13px; width:100%; vertical-align: middle;}

.r_s{float:right;}
.l_s{float:left;}
</style>
</head>
	<body>
		<span class="blank3"></span>
		<form id="form1" name="form1" action="#" method="post">
		<table id="table_id" width="100%" class="table_form" border="0" cellpadding="0" cellspacing="0">
			<tbody>
				<tr>
					<th style="width:80px">汇聚范围：</th>
					<td width="334px">
						<select class="width150" name="tsArea" id="tsArea" onchange="changeCategoryId(this.value)">
							
						</select>
					</td>	
					<td>站点目录</td>
				</tr>
			</tbody>
		</table>
		<table class="" border="0" cellpadding="2" cellspacing="0">
			<tr>
				<td style="width:176px; padding-left:12px" valign="top" rowspan="2" >
					<div id="cat_tree_div1" class="select_div border_color" style="width:176px; height:470px; overflow:scroll;border:1px #7f9db9 solid;" >
						<div id="leftMenuBox">
							<div id="leftMenu" class="contentBox6 textLeft" style="height:400px;">
								<ul id="leftMenuTree" class="easyui-tree" animate="true" style="width:176px;overflow:hidden">
								</ul>
								<span class="blank3"></span >
							</div>
						</div>
					</div>
				</td>
				<td id="tree_td_2" style="width:176px;" valign="top" rowspan="2">
					<div id="cat_tree_div2" class="select_div border_color" style="width:176px; height:470px; overflow:scroll;border:1px #7f9db9 solid;" >
						<div id="leftMenuBox">
							<div id="leftMenu2" class="contentBox6 textLeft" style="height:400px;">
								<ul id="leftMenuTree2" class="easyui-tree" animate="true" style="width:176px;overflow:hidden">
								</ul>
								<span class="blank3"></span >
							</div>
						</div>
					</div>
				</td>
				<td>
					<input type="button" value="添加" class="btn1" onclick="choose_regu()" />
				</td>
			  <td valign="top">	
					<div id="scroll_div" style="width:397px;height:224px;overflow:auto;background:#ffffff" class="border_color">
						<ul id="data_node" class="txt_list" style="width:1000px;">

						</ul>
					</div><br/>
					<span style="color:#32609E">共享目录</span>
					<div id="scroll_div" style="width:397px;height:210px;overflow:auto;background:#ffffff;padding-top:6px" class="border_color">
						<ul id="data_cat" class="txt_list" style="width:1000px;">

						</ul>
					</div>
				</td>
			</tr>
		</table>		
		<span class="blank12"></span>
		<div class="line2h"></div>
		<span class="blank3"></span>
		<table class="table_option" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left" valign="middle" style="text-indent:100px;">
					<input type="button" value="确定" class="btn1" onclick="related_ok()" />
					<input type="button" value="取消" class="btn1" onclick="top.CloseModalWindow();" />
				</td>
			</tr>
		</table>
		<span class="blank3"></span>
		</form>
	</body>
</html>
