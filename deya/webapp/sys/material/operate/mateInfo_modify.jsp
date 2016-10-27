<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>素材信息维护</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css"/>
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../js/mateList.js"></script>
<script type="text/javascript">
var att_id = "<%=request.getParameter("att_id")%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	
	if(att_id =="")
	{	
	    return;
	}else{
		defaultBean = MateInfoRPC.getMateInfoBean(att_id);
	}
	if(defaultBean)
	{
		$("#MateInfo_table").autoFill(defaultBean);					
	}
	$("#addButton").click(updateMateInfo);
});
</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="MateInfo_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>素材名称：</th>
			<td class="width250">
				<input id="alias_name" name="alias_name" type="text" class="width250" value="" onblur="checkInputValue('alias_name',false,80,'素材名称','')"/>
				<input type="hidden" id="cnode_id" name="cnode_id" value="<%=request.getParameter("cnode_id")%>"/>
				<input type="hidden" id="cnode_type" name="cnode_type" value="<%=request.getParameter("cnode_type")%>"/>		
				<input type="hidden" id="site_id" name="site_id" value="<%=request.getParameter("site_id")%>"/>	
			</td>			
		</tr>
		<tr>
			<th>素材描述：</th>
			<td class="width250">
				<textarea id="att_description" name="att_description" style="width:340px;height:60px;" onblur="checkInputValue('att_description',true,500,'素材描述','')"></textarea>
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('MateFolder_table',f_id)" value="重置"/>	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
<span class="blank3"></span>
</form>
</body>
</html>