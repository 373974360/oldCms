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
<jsp:include page="../include/include_info_tools.jsp"/>
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

	getAllowSharedSite();//得到共享目录
	if(app_id == "cms")
		changeSiteId("");
	else
		$("#tsArea option").eq(0).remove();
	
	setScrollHandl();

	var cat_jdata = eval(CategoryRPC.getAllowSharedSiteJSONStr(site_id));
	setLeftMenuTreeJsonData(cat_jdata);
	initCMSTreeClick();
	
});

function setUserPublishOperate()	
{
	var opt_ids = ","+getOptIDSByUser(app_id,site_id)+",";
	//判断是否是站点管理员或超级管理员
	if(isSiteManager(app_id,site_id) == true || opt_ids.indexOf(",302,") > -1)
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
		<table id="table_id" width="100%" class="table_form" border="0" cellpadding="0" cellspacing="0">
			<tbody>
				<tr>
					
				<!--	<th style="width:80px">获取范围：</th>
					<td width="150px">
						<select class="width150" name="tsArea" id="tsArea" onchange="changeSiteId(this.value)">
							<option value="">当前站点</option>
							<option value="cms">站点内容管理系统</option>
						</select>
					</td>-->

					<th style="width:80px">获取方式：</th>
					<td width="190px">
						<ul id="status_ul">
						 <li style="float:left; padding-right:20px;"><input type="radio" id="d" name="infostatus" value="0" /><label for="d">克隆</label></li>
						 <li style="float:left; padding-right:20px;"><input type="radio" id="e" name="infostatus" value="1" checked="checked"/><label for="e">引用</label></li>
						 <li style="float:left; padding-right:20px;"><input type="radio" id="f" name="infostatus" value="2"/><label for="f">链接</label></li>
						</ul>	
					</td>
					<th style="width:80px" id="opt_302" class="hidden">获取结果：</th>
					<td width="120px" id="opt_302"  class="hidden">
						<ul><li ><input type="checkbox" name="isPublish" id="isPublish" checked="checked" /><label for="isPublish">直接发布</label></li></ul>
					</td>
					<td></td>
				</tr>
			</tbody>
		</table>
		<table class="table_form" border="0" cellpadding="0" cellspacing="0">
			<tr>
				
				<!--	<td style="width:176px; padding-left:12px" valign="top" rowspan="2" >
						<div id="cat_tree_div1" class="select_div border_color" style="width:176px; height:400px; overflow:scroll;border:1px #7f9db9 solid;" >
							<div id="leftMenuBox">
								<div id="leftMenu" class="contentBox6 textLeft" style="height:400px;">
									<ul id="leftMenuTree" class="easyui-tree" animate="true" style="width:176px;overflow:hidden">
									</ul>
									<span class="blank3"></span >
								</div>
							</div>
						</div>
					</td>
				-->
				<td id="tree_td_2" style="width:200px;" valign="top" rowspan="2">
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
					<div id="scroll_div" style="width:100%;height:371px;overflow:auto;background:#ffffff" class="border_color">
						<ul id="data" class="txt_list" style="width:95%;">

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