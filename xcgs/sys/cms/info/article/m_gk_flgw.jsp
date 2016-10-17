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
<script type="text/javascript" src="js/m_gk_flgw.js"></script>
<script type="text/javascript">
<!--
var topnum = "<%=topnum%>";
var site_id = "<%=siteid%>";
var cid = "<%=cid%>";
var app_id = "<%=app_id%>";
var infoid = "<%=infoid%>";
var contetnid = "gk_content";
var linksInfo = "";
var focusInfo = "";
var mFlag = false;
var rela_site_id = "";//该节点所关联的站点

	$(document).ready(function(){
		
		reloadPublicGKInfo();

		if(infoid != "" && infoid != "null" && infoid != null){		
			defaultBean = ModelUtilRPC.select(infoid,site_id,"gkfflgw");
			if(defaultBean){
				$("#info_article_table").autoFill(defaultBean);
				
				setV(defaultBean.gk_content);
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
	init_editer(contetnid);	
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
			<th>统一登记号：</th>
			<td>
				<input id="gk_uniform_num" name="gk_uniform_num" type="text" class="width350" maxlength="80"/>
			</td>
		</tr>
   </tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
        <tr>
			<th>签署日期：</th>
			<td width="150">
			    <input id="gk_qsrq" name="gk_qsrq" type="text" class="width100" maxlength="80" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"/>
			</td>
			<th style="width:85px;">登记日期：</th>
			<td width="135">
				<input id="gk_djrq" name="gk_djrq" type="text" class="width100" maxlength="80" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"/>
			</td>
			<td></td>
		</tr>
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>信息时效性：</th>
			<td>
				<input id="gk_xxsxx" name="gk_xxsxx" type="text" class="width350" maxlength="80"/>
			</td>
		</tr>
   </tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th style="vertical-align:top;">信息内容：</th>
			<td style="">
				<textarea id="gk_content" name="gk_content" style="width:620px;;height:300px;visibility:hidden;"></textarea>
			<span class="blank9"></span>
			<input id="ddd" name="" type="checkbox" checked="false" /><label for="ddd">远程图片本地化</label>
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