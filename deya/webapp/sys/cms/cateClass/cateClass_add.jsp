<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
	String type = request.getParameter("type");
	String class_id = request.getParameter("class_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分类方法添加，修改</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/cateClassList.js"></script>
<script type="text/javascript">

var type = "<%=type%>";
var class_id = "<%=class_id%>";
var val = new Validator();
var addBean = BeanUtil.getCopy(CateClassBean);
var defaultBean;

$(document).ready(function(){	
	getAppList();

	if(type == "update")
	{
		defaultBean = CategoryRPC.getCateClassBeanById(class_id);
		if(defaultBean)
		{
			$("#add_table").autoFill(defaultBean);
			setAppIDS(defaultBean.app_ids);
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

function setAppIDS(ids)
{
	var tempA = ids.split(",");
	for(var i=0;i<tempA.length;i++)
	{
		$(":checkbox[value='"+tempA[i]+"']").attr("checked",true);
	}
}

function updateCateClass()
{
	$("#add_table").autoBind(addBean);
	addBean.class_id = defaultBean.class_id;
	if(!standard_checkInputInfo("add_table"))
	{
		return;
	}
	addBean.app_ids = getAppIDS();
	if(!CategoryRPC.updateCateClass(addBean))
	{
		top.msgWargin("分类方式"+WCMLang.Add_fail);
		return;
	}
	top.msgAlert("分类方式"+WCMLang.Add_success);
	top.getCurrentFrameObj().reloadCateClassList();
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
	addBean.app_ids = getAppIDS();
		
	if(!CategoryRPC.insertCateClass(addBean))
	{
		top.msgWargin("分类方式"+WCMLang.Add_fail);
		return;
	}
	top.msgAlert("分类方式"+WCMLang.Add_success);
	top.getCurrentFrameObj().reloadCateClassList();
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
			<th><span class="f_red">*</span>中文名称：</th>
			<td >
				<input id="class_cname" name="class_cname" 
					type="text" class="width300" value="" onblur="checkInputValue('class_cname',false,80,'中文名称','')"/>
			</td>			
		</tr>
		<tr >
			<th><span class="f_red">*</span>英文名称：</th>
			<td >
				<input id="class_ename" name="class_ename" 
					type="text" class="width300" value="" onblur="checkEname()"/>
			</td>			
		</tr>
		<tr>
			<th>域代码：</th>
			<td >
				<input id="class_codo" name="class_codo" 
					type="text" class="width300" value="" onblur="checkInputValue('class_codo',true,80,'域代码','')"/>
			</td>			
		</tr>
		<tr>
			<th>目录分类类型：</th>
			<td >				
				<input id="class_type" name="class_type" type="radio" value="1"checked="true" /><label>共享目录</label>
				<input id="class_type" name="class_type" type="radio" value="0" /><label>基础分类法</label>
		</tr>
		<tr>
			<th>所属应用：</th>
			<td >				
				<div style="border:1px solid #9DBFDD;width:303px;height:100px;overflow:auto;background:#FFFFFF;">
					<ul id="app_list" style="margin:10px">
					</ul>
				</div>
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">定义：</th>
			<td class="width250">
				<textarea id="class_define" name="class_define" 
					style="width:300px;;height:50px;" onblur="checkInputValue('class_define',true,333,'定义','')"></textarea>		
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
			<input id="btnReset" name="btn1" type="button" onclick="formReSet('add_table',class_cname)" value="重置" />	
			<input id="btnCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
