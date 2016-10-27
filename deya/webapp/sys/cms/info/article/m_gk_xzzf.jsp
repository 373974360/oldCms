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
<script type="text/javascript" src="js/m_gk_xzzf.js"></script>
<script type="text/javascript">
<!--
var topnum = "<%=topnum%>";
var site_id = "<%=siteid%>";
var cid = "<%=cid%>";
var app_id = "<%=app_id%>";
var infoid = "<%=infoid%>";

var gk_xzxw = "gk_xzxw";
var gk_xzcl = "gk_xzcl";
var gk_flyj = "gk_flyj";

var linksInfo = "";
var focusInfo = "";
var mFlag = false;
var rela_site_id = "";//该节点所关联的站点

	$(document).ready(function(){			
		reloadPublicGKInfo();
		getDataList("gk_zflb","gk_zflb");
		publicUploadButtomLoad("uploadify",true,false,"",0,5,site_id,"savePicUrl");
		if(infoid != "" && infoid != "null" && infoid != null){		
			defaultBean = ModelUtilRPC.select(infoid,site_id,"gkfxzzf");
			if(defaultBean){
				$("#info_article_table").autoFill(defaultBean);
				setV(gk_xzxw,defaultBean.gk_xzxw);
				setV(gk_xzcl,defaultBean.gk_xzcl);
				setV(gk_flyj,defaultBean.gk_flyj);
				
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

	init_editer(gk_xzxw);
	init_editer(gk_xzcl);
	init_editer(gk_flyj);
//-->
</script>
</head>
<body>
<span class="blank12"></span>
<form action="#" name="form1">
<div id="info_article_table">
<jsp:include page="../include/include_public_gk.jsp"/>
<!-- 内容主体不同部分　开始 -->
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
        <tr>
			<th style="vertical-align:top;">执法类别：</th>
			<td>
				<select class="input_select"  id="gk_zflb" name="gk_zflb" style="width:150px;">
				
				</select>               
			</td>
		</tr>
</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th style="vertical-align:top;">行政行为：</th>
			<td style="">
				<textarea id="gk_xzxw" name="gk_xzxw" style="width:620px;height:200px;visibility:hidden;"></textarea>
			<span class="blank9"></span>
			</td>
		</tr>
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th style="vertical-align:top;">行政处理：</th>
			<td style="">
				<textarea id="gk_xzcl" name="gk_xzcl" style="width:620px;height:200px;visibility:hidden;"></textarea>
			<span class="blank9"></span>
			</td>
		</tr>
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th style="vertical-align:top;">法律依据：</th>
			<td style="">
				<textarea id="gk_flyj" name="gk_flyj" style="width:620px;height:200px;visibility:hidden;"></textarea>
			<span class="blank9"></span>
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