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

		<script type="text/javascript">
var site_id = "<%=siteid%>";
var app = "<%=app_id%>";

$(document).ready(function() {
	initButtomStyle();
	init_FromTabsStyle();
	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");

	initTree();
	initCatTree();
});
	
function initCatTree(){
	/*
	var cat_jdata = jsonrpc.CategoryRPC.getInfoCategoryTreeByUserID(site_id,LoginUserBean.user_id);
	var zt_jdata = jsonrpc.CategoryRPC.getZTCategoryTreeJsonStr(site_id,LoginUserBean.user_id);
	var new_jdata = "";
	if(app == "cms" && (zt_jdata != "" && zt_jdata != null && zt_jdata != "[]"))
	{
		cat_jdata = cat_jdata.substring(1,cat_jdata.length-1);
		zt_jdata = zt_jdata.substring(1,zt_jdata.length-1);
		new_jdata = eval("["+cat_jdata+","+zt_jdata+"]");
	}else
	{
		new_jdata = eval(cat_jdata);
	}
	setLeftMenuTreeJsonData(new_jdata);
	*/
	$('#leftMenuTree').empty();
	$('#leftMenuTree').tree({   
 
		 url: '/sys/cms/category/category.jsp?site_id=' + site_id + '&user_id=' + LoginUserBean.user_id + '&pid=0',
		 onBeforeExpand:function(node,param){
			 $('#leftMenuTree').tree('options').url = '/sys/cms/category/category.jsp?site_id=' + site_id + '&user_id=' + LoginUserBean.user_id + '&pid=' + node.id;	// change the url
			 //param.myattr = 'test';    // or change request parameter
		 },               
		 onClick:function(node){     
			 $(this).tree('expand',node.target);
			 /*
			 if(node.attributes!=undefined)
			 {				
				setMainIframeUrl(node.attributes.url,node.text.replace(/\(\d*\)$/ig,""),node.attributes.handls); 
			 }
			 */
		 }   
	 });   
}

	
function related_ok(){
	var cId = "";
	var node = $('#leftMenuTree').tree('getSelected');
	if(node)
	{
		var children = $("#leftMenuTree").tree('getChildren',node.target);
		if(children != null && children != "")
		{
			top.msgAlert("请选择最末级栏目");		
			return;
		}
		else
		{
			cId = node.id;
		}
	}
	/*
	$("#leftMenuTree .tree-checkbox1").each(function(i){
		var o = $(this).parent();		
		cId = o.attr("node-id");
	});
	*/

	if(cId == "" || cId == 0){
		top.msgAlert("请选择转移的目标栏目");		
		return;
	}

	top.getCurrentFrameObj().moveInfoHandl(cId);	
	top.msgAlert("转移成功");
	top.getCurrentFrameObj().reloadInfoDataList();
	top.CloseModalWindow();
}

function related_cancel(){
	top.CloseModalWindow();
}


function initTree(){
	$("#leftMenuTree .tree-file").after('<SPAN class="tree-checkbox tree-checkbox0"></SPAN>');
	$("#leftMenuTree .tree-checkbox").click(function(){
		if($(this).attr("class").indexOf("tree-checkbox0") > -1)
		{
			$("#leftMenuTree .tree-checkbox").removeClass("tree-checkbox1");			
			$(this).addClass("tree-checkbox1");
		}
	});	
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
					<div id="cat_tree_div1" class="border_color" style="margin:0 auto;width:382px; height:360px; overflow:hidden;" >
						<div id="leftMenuBox">
							<div id="leftMenu" class="contentBox6 textLeft" style="height:365px;overflow:auto">
								<ul id="leftMenuTree" class="easyui-tree" animate="true" style="width:382px;overflow:hidden">
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