<%@ page import="com.deya.wcm.bean.cms.category.CategoryBean" %>
<%@ page import="com.deya.wcm.services.cms.category.CategoryManager" %>
<%@ page contentType="text/html; charset=utf-8"%>
<%
	String cid = request.getParameter("cid");
	String siteid = request.getParameter("site_id");
	String infoid = request.getParameter("info_id");
	String app_id = request.getParameter("app_id");
	String model = request.getParameter("model");
	CategoryBean cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(cid));
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
<script type="text/javascript" src="js/m_gk_ldcy.js"></script>
<script type="text/javascript">
<!--
var topnum = "<%=topnum%>";
var site_id = "<%=siteid%>";
var cid = "<%=cid%>";
var app_id = "<%=app_id%>";
var infoid = "<%=infoid%>";

var gk_ldzw ="gk_ldzw";
var gk_grjl ="gk_grjl";
var gk_gzfg ="gk_gzfg";

var linksInfo = "";
var focusInfo = "";
var mFlag = false;
var rela_site_id = "";//该节点所关联的站点


	$(document).ready(function(){		
		reloadPublicGKInfo();
        initUeditor(gk_ldzw);
        initUeditor(gk_grjl);
        initUeditor(gk_gzfg);
		publicUploadButtomLoad("uploadify",true,false,"",0,5,getReleSiteID(),"savePicUrl");
		
		if(infoid != "" && infoid != "null" && infoid != null){		
			defaultBean = ModelUtilRPC.select(infoid,site_id,"gkfldcy");
			if(defaultBean){
				$("#info_article_table").autoFill(defaultBean);
                setV(gk_ldzw,defaultBean.gk_ldzw);
                setV(gk_grjl,defaultBean.gk_grjl);
                setV(gk_gzfg,defaultBean.gk_gzfg);
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

	function savePicUrl(url)
	{
		//$("#thumb_url").val(url);gk_pic
		$("#gk_pic").val(url);
	}
//-->
</script>
</head>
<body>
<span class="blank12"></span>
<form action="#" name="form1">
<div id="info_article_table">
<%
if(cb.getMlsx()==1){
%>
	<jsp:include page="../include/include_public.jsp"/>
<%
}else if(cb.getMlsx()==2){
%>
	<jsp:include page="../include/include_public_gk.jsp"/>
<%
}
%>
<!-- 内容主体不同部分　开始 -->

<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th style="vertical-align:top;">领导职务：</th>
			<td style="">
				<script id="gk_ldzw" type="text/plain" style="width:95%;height:200px;"></script>
			</td>
		</tr>
        <tr>
			<th style="vertical-align:top;">个人简历：</th>
			<td style="">
				<script id="gk_grjl" type="text/plain" style="width:95%;height:200px;"></script>
			<span class="blank9"></span>
			</td>
		</tr>
        <tr>
			<th style="vertical-align:top;">工作分工：</th>
			<td style="">
				<script id="gk_gzfg" type="text/plain" style="width:95%;height:200px;"></script>
			<span class="blank9"></span>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">照片：</th>
			<td>
				<div style="float:left;margin:auto;"><input id="gk_pic" name="gk_pic" type="text" style="width:250px;" value="" /></div>
				<div style="float:left"><input type="file" name="uploadify" id="uploadify"/></div>
				<div style="float:left">&nbsp;<input id="i005" name="i005" type="button" onclick="selectPageHandle()" value="图片库" /></div>
			</td>
		</tr>
        
        <tr>
			<th style="vertical-align:top;">地址：</th>
			<td style="">
				<input id="gk_dz" name="gk_dz" type="text" class="width350" maxlength="80" value="" />
			</td>
		</tr>
        
        <tr>
			<th style="vertical-align:top;">电话：</th>
			<td style="">
				<input id="gk_tel" name="gk_tel" type="text" class="width350" maxlength="80" value="" />
			</td>
		</tr>
        
        <tr>
			<th style="vertical-align:top;">Email：</th>
			<td style="">
				<input id="gk_email" name="gk_email" type="text" class="width350" maxlength="80" value="" onblur="checkInputValue('gk_email',true,40,'Email','checkEmail')"/>
			</td>
		</tr>
</tbody>
</table>
<!-- 内容主体不同部分　结束 --><%
if(cb.getMlsx()==1){
    %>
                <jsp:include page="../include/include_public_high.jsp"/>
                <%
            }else if(cb.getMlsx()==2){
                %>
                <jsp:include page="../include/include_public_high_gk.jsp"/>
                <%
            }
            %>
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
