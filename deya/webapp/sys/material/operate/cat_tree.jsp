<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>素材库目录</title>
<meta name="generator" content="cicro-Builder"/>
<meta name="author" content="cicro"/>
<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript">
var f_id = "1";
var parent_id ="0";
var jsonData;
var chold_jData;
var appList;
var site_id ="<%=request.getParameter("site_id")%>";
var user_id = top.LoginUserBean.user_id;
var MateFolderRPC = jsonrpc.MateFolderRPC;
$(document).ready(function(){	
	initButtomStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	showMateFolderTree();
});

function showMateFolderTree()
{
	json_data = eval(MateFolderRPC.getMateFolderTreeJsonStr(f_id,site_id,user_id));	
	setLeftMenuTreeJsonData(eval("[json_data[1]]"));
}

function returnDeptID()
{
	var node = $('#leftMenuTree').tree('getSelected');
	if(node != null)
	{		
		top.getCurrentFrameObj().MoveMateHandl(node.id);
		top.CloseModalWindow();
	}else
	{
		top.msgWargin("请选择素材目录");
	}
}
</script>
</head>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="table" class="" border="0" cellpadding="0" cellspacing="0" style="width:94%">
	<tbody>
		<tr>			
			<td align="center" >
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft" style="height:300px;overflow:auto;border:1px solid #828790;background:#ffffff">
						<ul id="leftMenuTree" class="easyui-tree" animate="true" >
						</ul>
					</div>
				</div>
			</td>		
			<td></td>
		</tr>		
	</tbody>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="returnDeptID()" value="保存" />
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
