<%@ page contentType="text/html; charset=utf-8"%>
<%
	String app_id = request.getParameter("app_id");
	String siteid = request.getParameter("site_id");
	String ids = request.getParameter("ids");
	String isPublish = request.getParameter("is_Publish");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>信息推送</title>
		<jsp:include page="../include/include_info_tools.jsp"/>
		<script type="text/javascript">
var site_id = "<%=siteid%>";
var app_id = "<%=app_id%>";
var ids = "<%=ids%>";
var s_site_id = site_id;
var is_Publich = "<%= isPublish%>"
var CategoryRPC = jsonrpc.CategoryRPC;
var SiteRPC = jsonrpc.SiteRPC;
var infoToFwRPC = jsonrpc.infoToFwRPC;
var json_data;

$(document).ready(function() {
	initMenuTree();
	initButtomStyle();
	init_input();
	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");

	json_data = eval(CategoryRPC.getAllFWTreeJSONStr());	
	setLeftMenuTreeJsonData(json_data);	
	
});

function initMenuTree()
{
	$('#leftMenuTree').tree({	
		checkbox: true		
	});
}

function related_ok(){
	
	var c_ids = getSelectIds();
	if(c_ids != "")
	{
	    doAddInfos(c_ids,ids);			
	}else
		top.msgWargin("请选择要推送的节点");
}

function getSelectIds(){
	var s = "";	
	$("#leftMenuTree .tree-checkbox1").each(function(i){
		var o = $(this).parent();		
		s += "," + o.attr("node-id");
	});
	if(s != null && s != "")
		s = s.substring(1);
	return s;
}

function  doAddInfos(cat_ids,ids){
//	    alert("doAddInfos========="+cat_ids+"==="+ids+"====="+is_Publich);
	    
	if(infoToFwRPC.infoToFwCate(ids,site_id,app_id,cat_ids,1,is_Publich,top.LoginUserBean.user_id))
	{
		top.msgAlert("操作成功！");
		related_cancel();
		//top.getCurrentFrameObj().reloadInfoDataList();
	}else
	{
		top.msgWargin("操作失败,请重新操作");
	}
}

function related_cancel(){
	top.CloseModalWindow();
}
</script>
	</head>
	<body>
		<span class="blank3"></span>
		<form id="form1" name="form1" action="#" method="post">
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