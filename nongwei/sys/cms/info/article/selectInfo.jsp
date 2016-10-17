<%@ page contentType="text/html; charset=utf-8"%>
<%
	String app_id = request.getParameter("app_id");
	String site_id = request.getParameter("site_id");
	String cat_id = request.getParameter("cat_id");
	String top_index = request.getParameter("top_index");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息获取</title>
<jsp:include page="../include/include_info_tools.jsp"/>
<script type="text/javascript" src="js/GetInfo.js"></script>

<script type="text/javascript">
var top_index = "<%=top_index%>";
var cat_id = "<%=cat_id%>";
var site_id = "<%=site_id%>";
var app_id = "<%=app_id%>";
var s_site_id = site_id;
var CategoryRPC = jsonrpc.CategoryRPC;
var SiteRPC = jsonrpc.SiteRPC;
var cat_jdata = "";
var input_type = "radio";

$(document).ready(function() {	
	m.remove("is_host");//显示信息时要选择所有的信息,不光是只信息,还有引用的(服务中的),所以去掉这项条件

	initButtomStyle();
	init_input();
	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");
	
	initReleSite();
	//initCatTree(site_id);	
	//treeNodeSelected(cat_id);

	setScrollHandl();
	//getInfoList();
	//getInfoCount();	
});

//得到关联的站点ID
function initReleSite()
{
	var c_site_id = "";
	if(app_id != "cms")
	{
		c_site_id = SiteRPC.getSiteIDByAppID(app_id);
	}else
	{
		c_site_id = site_id;
	}
	s_site_id = c_site_id;
	$("#tsArea").addOptionsSingl(c_site_id,SiteRPC.getSiteBeanBySiteID(c_site_id).site_name);
	getInfoCategoryTree(c_site_id);
	getAllowSharedSite();
	$("#tsArea").addOptionsSingl("ggfw","公共服务");
}

function getInfoCategoryTree(s_id)
{	
	site_type = s_id;
	if(s_id == "zwgk" || isNaN(s_id) == false || s_id == "cms" || s_id.indexOf("shared_") > -1)
	{
		initCatTree(s_id);
	}
	else
	{
		$("#tree_td_2").hide();
		$("#scroll_div").css("width","575px");
		s_site_id = s_id;
		if(s_id == "ggfw")
		{
			cat_jdata = eval(CategoryRPC.getAllFWTreeJSONStr());
		}else
			cat_jdata = eval(CategoryRPC.getCategoryTreeBySiteID(s_id));
		setLeftMenuTreeJsonData(cat_jdata);
		initLastTreeClick();
	}
}


function initLastTreeClick()
{
	$('#leftMenuTree').tree({
		onClick:function(node){
			clickCategoryNode(node.id); 
		}
	});
}

function select_ok()
{
	var url = $("#data :checked").parent().find("label").attr("url");
	if(url == "" || url == null)
	{
		top.msgWargin("请选择信息");
		return;
	}
	top.getCurrentFrameObj(top_index).insertContentUrl(url);
	top.CloseModalWindow();
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
					<th style="width:80px">获取范围：</th>
					<td width="150px">
						<select class="width150" name="tsArea" id="tsArea" onchange="getInfoCategoryTree(this.value)">
							
						</select>
					</td>					
					<td></td>
				</tr>
			</tbody>
		</table>
		<table class="table_form" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td style="width:176px; padding-left:12px" valign="top" rowspan="2" >
					<div id="cat_tree_div1" class="select_div border_color" style="width:176px; height:400px; overflow:scroll;border:1px #7f9db9 solid;" >
						<div id="leftMenuBox">
							<div id="leftMenu" class="contentBox6 textLeft" style="height:400px;">
								<ul id="leftMenuTree" class="easyui-tree" animate="true" style="width:176px;overflow:hidden ">
								</ul>
							</div>
						</div>
					</div>
				</td>
				<td id="tree_td_2" style="width:176px;" valign="top" rowspan="2" class="hidden">
					<div id="cat_tree_div2" class="select_div border_color" style="width:176px; height:400px; overflow:scroll;border:1px #7f9db9 solid;" >
						<div id="leftMenuBox">
							<div id="leftMenu2" class="contentBox6 textLeft" style="height:400px;">
								<ul id="leftMenuTree2" class="easyui-tree" animate="true" style="width:176px;overflow:hidden">
								</ul>
								<span class="blank3"></span >
							</div>
						</div>
					</div>
				</td>
				<td valign="top">					
					<select id="searchTimes" class="input_select width60" onchange="changeTimes()">
						<option selected="selected" value="0b">日期</option> 
						<option value="1b">今日</option> 
						<option value="2b">昨日</option> 
						<option value="3b">一周内</option> 
						<option value="4b">一月内</option> 
					</select>
					<input type="text" name="searchkey" id="searchkey"/>
					<input type="button" name="" onclick="doSearchInfoForGet()" value="搜索"/>
					&#160;&#160;(<span id="checked_count"></span>)
				</td>				
			</tr>
			<tr>
			  <td valign="top">	
					<div id="scroll_div" style="width:575px;height:371px;overflow:auto;background:#ffffff" class="border_color">
						<ul id="data" class="txt_list" style="width:1000px;">

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
					<input type="button" value="确定" class="btn1" onclick="select_ok()" />
					<input type="button" value="取消" class="btn1" onclick="related_cancel()" />
				</td>
			</tr>
		</table>
		<span class="blank3"></span>
		</form>
	</body>
</html>