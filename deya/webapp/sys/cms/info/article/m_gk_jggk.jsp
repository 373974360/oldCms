<%@ page contentType="text/html; charset=utf-8"%>
<%
String cid = request.getParameter("cid");
String siteid = request.getParameter("site_id");
String infoid = request.getParameter("info_id");
String app_id = request.getParameter("app_id");
String model = request.getParameter("model");
if(cid == null || cid.equals("null")){
	cid = "0";
}
if(app_id == null || app_id.equals("null")){
	app_id = "0";
}
if(model == null || !model.matches("[0-9]*")){
	model = "0";
}
String topnum = request.getParameter("topnum");
if(topnum == null || topnum.trim().equals("") || topnum.trim().equals("null") ){
	topnum = "0";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息维护</title>
<jsp:include page="../include/include_info_tools.jsp"/>
<script type="text/javascript" src="js/m_gk_jggk.js"></script>
<script type="text/javascript">
<!--
var topnum = "<%=topnum%>";
var site_id = "<%=siteid%>";
var cid = "<%=cid%>";
var app_id = "<%=app_id%>";
var infoid = "<%=infoid%>";
var linksInfo = "";
var focusInfo = "";
var mFlag = false;

	$(document).ready(function(){		
		reloadPublicGKInfo();

		if(infoid != "" && infoid != "null" && infoid != null){		
			defaultBean = ModelUtilRPC.select(infoid,site_id,"gkfjggk");
			if(defaultBean){
				$("#gk_f_jggk_table").autoFill(defaultBean);	
				setV("gk_nsjg",defaultBean.gk_nsjg);
				setV("gk_gzzz",defaultBean.gk_gzzz);
				setV("gk_xmzw",defaultBean.gk_xmzw);
				publicReloadUpdateGKInfos();		
			}
			$("#addButton").click(updateInfoData);		
			mFlag = true;		
		}
		else
		{				
			$("#addButton").click(addInfoData);
			mFlag = false;
		}
	});
	init_editer("gk_nsjg");	
	init_editer("gk_gzzz");
	init_editer("gk_xmzw");
//-->
</script>
</head>

<body>
<span class="blank12"></span>
<form action="#" name="form1">
<div id="gk_f_jggk_table">
<jsp:include page="../include/include_public_gk.jsp"/>

<!-- 内容主体不同部分　开始 -->
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th style="vertical-align:top;">内设机构：</th>
			<td style="">
				<textarea id="gk_nsjg" name="gk_nsjg" style="width:620px;;height:160px;visibility:hidden;"></textarea>
			<span class="blank9"></span>
			</td>
		</tr>
        <tr>
			<th style="vertical-align:top;">工作职责：</th>
			<td style="">
				<textarea id="gk_gzzz" name="gk_gzzz" style="width:620px;;height:160px;visibility:hidden;"></textarea>
			<span class="blank9"></span>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">姓名职务：</th>
			<td style="">
				<textarea id="gk_xmzw" name="gk_xmzw" style="width:620px;;height:160px;visibility:hidden;">
<p>
<table style="width:420px;" border="1" cellspacing="0" bordercolor="#666666" cellpadding="2">
<tbody>
<tr>
<td align="middle"><strong>&nbsp;姓名</strong></td>
<td align="middle"><strong>&nbsp;职务</strong></td>
</tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
</tbody>
</table>
</p>
</textarea>
			<span class="blank9"></span>
			</td>
		</tr>
		<!--<tr>
			<th style="vertical-align:top;">内容概述：</th>
			<td>
				<textarea id="gk_nrgs" name="content10" style="width:620px;height:70px"></textarea>
			</td>
		</tr>-->
        <tr>
			<th style="vertical-align:top;">地址：</th>
			<td>
				<input id="gk_dz" name="gk_dz" type="text" style="width:350px;" value="" />
			</td>
		</tr>
        <tr>
			<th style="vertical-align:top;">邮政编码：</th>
			<td>
				<input id="gk_yzbm" name="gk_yzbm" type="text" style="width:350px;" value="" />
			</td>
		</tr>
        <tr>
			<th style="vertical-align:top;">值班电话：</th>
			<td>
				<input id="gk_zbdh" name="gk_zbdh" type="text" style="width:350px;" value="" />
			</td>
		</tr>
        <tr>
			<th style="vertical-align:top;">传真：</th>
			<td>
				<input id="gk_chzh" name="gk_chzh" type="text" style="width:350px;" value="" />
			</td>
		</tr>
         <tr>
			<th style="vertical-align:top;">Email：</th>
			<td>
				<input id="gk_email" name="gk_email" type="text" style="width:350px;" value="" />
			</td>
		</tr>
        <tr>
			<th style="vertical-align:top;">网站地址：</th>
			<td>
				<input id="gk_weburl" name="gk_weburl" type="text" style="width:350px;" value="" />
			</td>
		</tr>
        <tr>
			<th style="vertical-align:top;">受理投诉部门：</th>
			<td>
				<input id="gk_sltsbm" name="gk_sltsbm" type="text" style="width:350px;" value="" />
			</td>
		</tr>
        <tr>
			<th style="vertical-align:top;">受理投诉电话：</th>
			<td>
				<input id="gk_sltsdh" name="gk_sltsdh" type="text" style="width:350px;" value="" />
			</td>
		</tr>
	</tbody>
</table>
<!-- 内容主体不同部分　结束 -->

<jsp:include page="../include/include_public_high_gk.jsp"/>

</div>

<!--隔线开始-->
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<!--隔线结束-->
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />
			<input id="addReset" name="btn1" type="button" onclick="window.location.reload()" value="重置" />
			<input id="addCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</div>
</form>
</body>
</html>
