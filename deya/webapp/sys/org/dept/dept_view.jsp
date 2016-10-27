<%@ page contentType="text/html; charset=utf-8"%>
<%
	String dept_id = request.getParameter("dept_id");
	String parent_id = request.getParameter("parent_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>部门查看</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/deptList.js"></script>
<script type="text/javascript">

var dept_id = "<%=dept_id%>";
var parent_id = <%=parent_id%>;
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	getDeptLevelList();

	if(dept_id != "" && dept_id != "null" && dept_id != null)
	{
		defaultBean = DeptRPC.getDeptBeanByID(dept_id);
		if(defaultBean)
		{
			$("#dept_table").autoFill(defaultBean);					
		}
		disabledWidget();
	}	
});



</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="dept_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>部门名称：</th>
			<td colspan="3">
				<input id="dept_name" name="dept_name" type="text" class="width585" value="" onblur="checkInputValue('dept_name',false,240,'部门名称','')"/>
			</td>
		</tr>
		<tr>
			<th>部门全称：</th>
			<td colspan="3">
				<input id="dept_fullname" name="dept_fullname" type="text" class="width585" value="" onblur="checkInputValue('dept_fullname',true,240,'部门全称','')"/>
			</td>
		</tr>
		<tr>
			<th>部门级别：</th>
			<td style="width:269px">
				<select id="deptlevel_value" name="deptlevel_value" class="width205"><option value="0"></option></select>
			</td>
			<th>是否发布：</th>
			<td >
				<input type="checkbox" id="is_publish" name="is_publish" value="1"><label>发布到公务员名录</label>
			</td>			
		</tr>
		<tr>
			<th>地区代码：</th>
			<td >
				<input id="area_code" name="area_code" type="text" class="width200" value="" onblur="checkInputValue('area_code',true,240,'地区代码','')"/>
			</td>
			<th>电话：</th>
			<td >
				<input id="tel" name="tel" type="text" class="width200" value="" onblur="checkInputValue('tel',true,100,'电话','checkTelephone')"/>
			</td>			
		</tr>
		<tr>
			<th>机构代码：</th>
			<td >
				<input id="dept_code" name="dept_code" type="text" class="width200" value="" onblur="checkInputValue('dept_code',true,240,'机构代码','')"/>
			</td>
			<th>传真：</th>
			<td >
				<input id="fax" name="fax" type="text" class="width200" value="" onblur="checkInputValue('fax',true,100,'传真','checkTelephone')"/>
			</td>
		</tr>
		<tr>
			<th>邮编：</th>
			<td >
				<input id="postcode" name="postcode" type="text" class="width200" value="" onblur="checkInputValue('postcode',true,8,'邮编','')"/>
			</td>
			<th>Email：</th>
			<td >
				<input id="email" name="email" type="text" class="width200" value="" onblur="checkInputValue('email',true,20,'Email','checkEmail')"/>
			</td>
		</tr>				
		<tr>
			<th>办公地址：</th>
			<td colspan="3">
				<input id="address" name="address" type="text" class="width585" value="" onblur="checkInputValue('address',true,240,'地址','')"/>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">机构职能：</th>
			<td  colspan="3">
				<textarea id="functions" name="functions" style="width:585px;;height:80px;" onblur="checkInputValue('functions',true,450,'部门职能','')">
				</textarea>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">部门描述：</th>
			<td  colspan="3">
				<textarea id="dept_memo" name="dept_memo" style="width:585px;;height:50px;" onblur="checkInputValue('dept_memo',true,900,'部门职能','')"></textarea>		
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
			<input id="userAddButton" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="关闭" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
