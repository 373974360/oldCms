<%@ page contentType="text/html; charset=utf-8"%>
<%
	String app_id = request.getParameter("app_id");
	String siteid = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>信息推送</title>
		<jsp:include page="../include/include_info_tools.jsp"/>
		<script type="text/javascript" src="js/toInfo.js?v=20150812"></script>

		<script type="text/javascript">
var site_id = "<%=siteid%>";
var app_id = "<%=app_id%>";
var s_site_id = site_id;
var CategoryRPC = jsonrpc.CategoryRPC;
var SiteRPC = jsonrpc.SiteRPC;

$(document).ready(function() {
	initButtomStyle();
	init_input();
	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");
	
	//根据权限控制获取结果是否显示
	setUserPublishOperate();
	
	if(app_id == "cms")
	{
		getAllowReceiveSite();//得到可接收的站点
		initCatTree(s_site_id);	
	}
	else
	{
		$("#type_ul li").eq(0).remove();//不能使用克隆方式
		$("#type_ul input").eq(0).attr("checked","checked");
		
		//公开系统下，可以给主站报送，也可以给关联的站点报送，主站是公开系统关联的站点，关联的站点是节点注册时关联的站点
		$("#cat_tree_div1").css("height","330");
		$("#leftMenuTree").css("height","330");
		var GKNodeBean = jsonrpc.GKNodeRPC.getGKNodeBeanByNodeID(site_id);	
		s_site_id = GKNodeBean.rela_site_id;
		getNodeRealSiteCategory(s_site_id);
		$("#tsArea").empty();
		$("#tsArea").addOptionsSingl(s_site_id,SiteRPC.getSiteBeanBySiteID(s_site_id).site_name);
		//主站ID
		var main_site_id = SiteRPC.getSiteIDByAppID(app_id);
		if(main_site_id != s_site_id)
		{
			$("#tsArea").addOptionsSingl(main_site_id,SiteRPC.getSiteBeanBySiteID(main_site_id).site_name);
		}
	}
	
	
});


function setUserPublishOperate()	
{
	var opt_ids = ","+top.getOptIDSByUser(app_id,site_id)+",";
	//判断是否是站点管理员或超级管理员
	if(top.isSiteManager(app_id,site_id) == true || opt_ids.indexOf(",302,") > -1)
	{
		$(".tsResult").show();
	}else{
	    $("#isPublish").prop("checked", false);
	}
}


</script>
	</head>
	<body>
		<span class="blank3"></span>
		<form id="form1" name="form1" action="#" method="post">
		<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0">
			<tbody>
				<tr id="tsArea_tr">
					<th>推送范围：</th>
					<td>
						<select name="tsArea" id="tsArea" onchange="changeSiteId(this.value)">
							<option value="">当前站点</option>
						</select>
					</td>
					<th class="hidden tsResult" style="width: 80px;">推送结果：</th>
					<td width="120px" class="hidden tsResult" id="opt_302">
						<ul><li><input type="checkbox" id="isPublish" name="isPublish" class="input_checkbox" checked="checked"><label for="isPublish">直接发布</label></li></ul>
					</td>
				</tr>
				<tr>
					<th>推送方式：</th>
					<td colspan="3">
						<ul id="type_ul">
						 <li style="float:left; padding-right:20px;"><input type="radio" id="d" name="infostatus" value="0" /><label for="d">克隆</label></li>
						 <li style="float:left; padding-right:20px;"><input type="radio" id="e" name="infostatus" value="1" checked="checked"/><label for="e">引用</label></li>
						 <li style="float:left; padding-right:20px;"><input type="radio" id="f" name="infostatus" value="2" /><label for="f">链接</label></li>
						</ul>	
					</td>
				</tr>				
			</tbody>
		</table>
		<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0">
			<tbody>
				<tr>
				 <td style="text-align:center" align="center">
					<div id="cat_tree_div1" class="border_color" style="margin:0 auto;width:362px; height:300px; overflow:hidden;" >
						<div id="leftMenuBox">
							<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
								<ul id="leftMenuTree" class="easyui-tree" animate="true" style="width:362px; height:300px;">
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
					<input type="button" value="确定" class="btn1" onclick="related_ok()" />
					<input type="button" value="取消" class="btn1" onclick="related_cancel()" />
				</td>
			</tr>
		</table>
		<span class="blank3"></span>
		</form>
	</body>
</html>