<%@ page contentType="text/html; charset=utf-8"%>
<%
	String app_id = request.getParameter("app_id");
	String siteid = request.getParameter("site_id");
	String cat_id = request.getParameter("cat_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息获取</title>
	<jsp:include page="../../include/include_tools.jsp"/>

<%--<jsp:include page="../../cms/info/include/include_info_tools.jsp"/>--%>
	<link type="text/css" rel="stylesheet" href="../../cms/info/article/style/main.css" />
	<link type="text/css" rel="stylesheet" href="../../cms/info/article/style/sub.css" />
	<link type="text/css" rel="stylesheet" href="../../cms/info/article/style/content.css" />
	<link rel="stylesheet" type="text/css" href="../../styles/uploadify.css"/>
	<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css" />


	<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../../js/indexjs/indexList.js"></script>
	<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
	<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>

	<script type="text/javascript" src="../../js/uploadTools.js"></script>
	<script type="text/javascript" src="../../js/jquery.uploadify.js"></script>
	<script type="text/javascript" src="../../js/uploadFile/swfobject.js"></script>
	<script type="text/javascript" src="../../cms/info/article/js/public.js"></script>

<script type="text/javascript" src="js/GetInfo.js?v=20151109"></script>

<script type="text/javascript">
var cat_id = "<%=cat_id%>";
var cat_id_for_get = cat_id;
var site_id = "<%=siteid%>";
var app_id = "<%=app_id%>";
var s_site_id = site_id;
var CategoryRPC = jsonrpc.CategoryRPC;
var input_type = "checkbox";

$(document).ready(function() {
	initButtomStyle();
	init_input();
	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");

	//根据权限控制获取结果是否显示
	setUserPublishOperate();

//	getAllowSharedSite();//得到共享目录
//	if(app_id == "cms")
		changeSiteId("");
//	else
//		$("#tsArea option").eq(0).remove();

	setScrollHandl();

//	var cat_jdata = eval(CategoryRPC.getAllowSharedSiteJSONStr(site_id));
//	setLeftMenuTreeJsonData(cat_jdata);
	initCMSTreeClick();

});

function setUserPublishOperate()
{
	var opt_ids = ","+top.getOptIDSByUser(app_id,site_id)+",";
	//判断是否是站点管理员或超级管理员
	if(top.isSiteManager(app_id,site_id) == true || opt_ids.indexOf(",302,") > -1)
	{
		$("#table_id #opt_302").show();
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

		<table class="table_form" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td id="tree_td_2" style="width:176px;" valign="top" rowspan="2">
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
					<div id="scroll_div" style="width:397px;height:371px;overflow:auto;background:#ffffff" class="border_color">
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
					<input type="button" value="确定" class="btn1" onclick="related_ok()" />
					<input type="button" value="取消" class="btn1" onclick="related_cancel()" />
				</td>
			</tr>
		</table>
		<span class="blank3"></span>
		</form>
	</body>
</html>
