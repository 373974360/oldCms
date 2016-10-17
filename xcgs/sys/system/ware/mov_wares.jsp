<%@ page contentType="text/html; charset=utf-8"%>
<%	
String site_id = request.getParameter("site_id");
site_id = (site_id==null) ? "" : site_id;
String app_id = request.getParameter("app_id");
String ids = request.getParameter("ids");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择标签分类</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css" />
<link rel="stylesheet" type="text/css" href="../../styles/themes/icon.css" />
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/wareList.js"></script>
<script type="text/javascript">
	var app_id = "<%=app_id%>";
	var site_id = "<%=site_id%>";
	var ids = "<%=ids%>";
		
	con_m.put("app_id",app_id);
	con_m.put("site_id",site_id);

	var json_data;

$(document).ready(function(){
	initButtomStyle();
	json_data = eval(WareRPC.getJSONTreeStr(con_m));
	setLeftMenuTreeJsonData(json_data);	
});


function returnWareCategoryID()
{				
	var node = $('#leftMenuTree').tree('getSelected');
	if(node == null)
	{
		top.msgWargin(WCMLang.selected_ware_category);
		return;
	}else
	{		
		//alert(node.id+"|==="+ids);
		eval("top.getCurrentFrameObj().updateMove('"+ids+"','"+node.id+"')");
		top.CloseModalWindow();
	}	
}
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="table" class="" border="0" cellpadding="0" cellspacing="0" style="width:94%">
	<tbody>
		<tr>			
			<td align="center" >
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft" style="height:390px;overflow:auto;border:1px solid #828790;background:#ffffff">
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
			<input id="addButton" name="btn1" type="button" onclick="returnWareCategoryID()" value="保存" />
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>