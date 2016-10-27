<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%	
	String nodcat_id = request.getParameter("nodcat_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公开节点分类维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/gkNodeCategoryList.js"></script>
<script type="text/javascript">

var nodcat_id = "<%=nodcat_id%>";

var addBean = BeanUtil.getCopy(GKNodeCategory);
var defaultBean;

$(document).ready(function(){
	if(nodcat_id != "" && nodcat_id != null && nodcat_id != "null")
	{
		defaultBean = GKNodeRPC.getNodeCategoryBean(nodcat_id);
		if(defaultBean)
		{
			$("#add_table").autoFill(defaultBean);
		}
		$("#addButton").click(updateCateClass);
		$("#btnReset").unbind("click");
		$("#btnReset").click(resetTable);
	}
	else
	{
		$("#addButton").click(addCateClass);
	}

	initButtomStyle();
	init_input();
});


function updateCateClass()
{
	$("#add_table").autoBind(addBean);
	addBean.nodcat_id = defaultBean.nodcat_id;
	
	if(!GKNodeRPC.updateGKNodeCategory(addBean))
	{
		top.msgWargin("公开节点分类"+WCMLang.Add_fail);
		return;
	}
	top.msgAlert("公开节点分类"+WCMLang.Add_success);
	top.getCurrentFrameObj().loadMenuTable();
	top.getCurrentFrameObj().updateMenuTree(addBean.nodcat_id,addBean.nodcat_name);
	top.CloseModalWindow();
}

function addCateClass()
{
	val.check_result = true;	
	$("#add_table").autoBind(addBean);

	if(!standard_checkInputInfo("add_table"))
	{
		return;
	}
	addBean.nodcat_id = GKNodeRPC.getNewNodCatID();
	addBean.parent_id = top.getCurrentFrameObj().nodcat_id;
	addBean.app_id = top.getCurrentFrameObj().app_id;

	if(!GKNodeRPC.insertGKNodeCategory(addBean))
	{
		top.msgWargin("公开节点分类"+WCMLang.Add_fail);
		return;
	}
	top.msgAlert("公开节点分类"+WCMLang.Add_success);
	top.getCurrentFrameObj().loadMenuTable();
	top.getCurrentFrameObj().insertMenuTree(addBean.nodcat_id,addBean.nodcat_name);
	top.CloseModalWindow();
}

// 修改状态下的重置
function resetTable()
{
	$("#add_table").autoFill(defaultBean);	
	$("#add_table").autoBind(addBean);
}

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="add_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>分类名称：</th>
			<td >
				<input id="nodcat_name" name="nodcat_name" 
					type="text" class="width300" value="" onblur="checkInputValue('nodcat_name',false,80,'分类名称','')"/>
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
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="btnReset" name="btn1" type="button" onclick="formReSet('add_table',nodcat_id)" value="重置" />	
			<input id="btnCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
