<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%	
	String id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>专题分类维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/ztCateList.js"></script>
<script type="text/javascript">

var id = "<%=id%>";
var val = new Validator();
var addBean = BeanUtil.getCopy(ZTCategoryBean);
var defaultBean;

$(document).ready(function(){
	if(id != "" && id != null && id != "null")
	{
		defaultBean = CategoryRPC.getZRCategoryBean(id);
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
	addBean.id = defaultBean.id;
	
	if(!CategoryRPC.updateZTCategory(addBean))
	{
		top.msgWargin("专题分类"+WCMLang.Add_fail);
		return;
	}
	top.msgAlert("专题分类"+WCMLang.Add_success);
	top.getCurrentFrameObj().reloadZTCateList();
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
	addBean.app_ids = "cms";
	addBean.site_id = top.getCurrentFrameObj().site_id;
	if(!CategoryRPC.insertZTCategory(addBean))
	{
		top.msgWargin("专题分类"+WCMLang.Add_fail);
		return;
	}
	top.msgAlert("专题分类"+WCMLang.Add_success);
	top.getCurrentFrameObj().reloadZTCateList();
	top.CloseModalWindow();
}

// 修改状态下的重置
function resetTable()
{
	$("#add_table").autoFill(defaultBean);	
	$("#add_table").autoBind(addBean);
}

function checkEname()
{
	var value = $("#class_ename").val();
	val.checkLowerLetter(value,'英文名称',false,'class_ename');
}

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="add_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>分类名称:</th>
			<td >
				<input id="zt_cat_name" name="zt_cat_name" 
					type="text" class="width300" value="" onblur="checkInputValue('zt_cat_name',false,80,'分类名称','')"/>
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
			<input id="btnReset" name="btn1" type="button" onclick="formReSet('add_table',id)" value="重置" />	
			<input id="btnCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
