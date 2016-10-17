<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%	
	String node_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公开节点维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/gkNodeList.js"></script>
<script type="text/javascript">
var app_id = "zwgk";
var node_id = "<%=node_id%>";
var addBean = BeanUtil.getCopy(GKNodeBean);
var defaultBean;

$(document).ready(function(){
	if(node_id != "" && node_id != null && node_id != "null")
	{
		defaultBean = GKNodeRPC.getGKNodeBeanByNodeID(node_id);
		if(defaultBean)
		{
			$("#add_table").autoFill(defaultBean);
			$("#dept_name").val(DeptRPC.getDeptBeanByID(defaultBean.dept_id).dept_name);
			
			$("#rela_site_name").val(jsonrpc.SiteRPC.getSiteBeanBySiteID(defaultBean.rela_site_id).site_name);
			$("#template_index_name").val(getTemplateNameByReleApp(defaultBean.index_template_id));
		}
		$("#node_id").attr("disabled",true);
		$("#addButton").click(updateNode);
	}

	initButtomStyle();
	init_input();
});


function updateNode()
{
	$("#add_table").autoBind(addBean);
	addBean.node_id = defaultBean.node_id;
	
	if(!GKNodeRPC.updateGKNodeSimple(addBean))
	{
		top.msgWargin("公开节点"+WCMLang.Add_fail);
		return;
	}
	top.msgAlert("公开节点"+WCMLang.Add_success);
}

// 修改状态下的重置
function resetTable()
{
	$("#template_index_name").val(getTemplateNameByReleApp(defaultBean.index_template_id));
}

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="add_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>		
		<tr>
			<th><span class="f_red">*</span>公开机构编码：</th>
			<td >
				<input id="dept_code" name="dept_code" 
					type="text" class="width300" value="" onblur="checkInputValue('dept_code',false,80,'公开机构编码','')"/>
			</td>			
		</tr>
		<tr">
			<th>首页模板：</th>
			<td>				
				<input id="template_index_name" name="template_index_name" type="text" class="width300" value="" readOnly="readOnly"/>	
				<input type="hidden" id="index_template_id" class="width200" value="0"/><input type="button" value="选择" onclick="openTemplate('index_template_id','template_index_name')"/>
			</td>			
		</tr>
		<tr>
			<th>参与依申请公开：</th>
			<td >
				<ul>
					<li><input id="is_apply" name="is_apply" type="radio"  value="0" checked="true"/><label>不参与</label></li>
					<li><input id="is_apply" name="is_apply" type="radio"  value="1" /><label>参与</label></li>
				</ul>
			</td>			
		</tr>
		<tr>
			<th>依申请公开名称：</th>
			<td >
				<input id="apply_name" name="apply_name" 
					type="text" class="width300" value="" onblur="checkInputValue('apply_name',true,80,'依申请公开名称','')"/>
			</td>			
		</tr>
		<tr>
			<th>办公地址：</th>
			<td >
				<input id="address" name="address" 
					type="text" class="width300" value="" onblur="checkInputValue('address',true,240,'办公地址','')"/>
			</td>			
		</tr>
		<tr>
			<th>邮政编码：</th>
			<td >
				<input id="postcode" name="postcode" 
					type="text" class="width300" value="" onblur="checkInputValue('postcode',true,6,'邮政编码','checkZip')"/>
			</td>			
		</tr>
		<tr>
			<th>办公时间：</th>
			<td >
				<input id="office_dtime" name="office_dtime" 
					type="text" class="width300" value="" onblur="checkInputValue('office_dtime',true,240,'办公时间','')"/>
			</td>			
		</tr>
		<tr>
			<th>联系电话：</th>
			<td >
				<input id="tel" name="tel" 
					type="text" class="width300" value="" onblur="checkInputValue('tel',true,80,'联系电话','checkTelephone')"/>
			</td>			
		</tr>
		<tr>
			<th>传真：</th>
			<td >
				<input id="fax" name="fax" type="text" class="width300" value="" onblur="checkInputValue('fax',true,80,'传真','checkFax')"/>
			</td>			
		</tr>
		<tr>
			<th>电子邮箱：</th>
			<td >
				<input id="email" name="email" type="text" class="width300" value="" onblur="checkInputValue('email',true,80,'电子邮箱','checkEmail')"/>
			</td>			
		</tr>		
		<tr>
			<th>备注：</th>
			<td >
				<textarea id="node_demo" name="node_demo" style="width:585px;;height:50px;" onblur="checkInputValue('node_demo',true,900,'备注','')"></textarea>		
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
			<input id="btnReset" name="btn1" type="button" onclick="formReSet('add_table',node_id);resetTable()" value="重置" />	
			<input id="btnCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
