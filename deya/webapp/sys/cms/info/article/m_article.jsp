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
<script type="text/javascript" src="js/m_article.js"></script>
<script type="text/javascript">

var topnum = "<%=topnum%>";
var site_id = "<%=siteid%>";
var cid = "<%=cid%>";
var app_id = "<%=app_id%>";
var infoid = "<%=infoid%>";
var contentId = "info_content";
var linksInfo = "";
var focusInfo = "";
var mFlag = false;

$(document).ready(function(){
    initUeditor(contentId);
	init_input();	
	reloadPublicGKInfo();
	publicUploadButtomLoad("uploadify",true,false,"",0,5,getReleSiteID(),"savePicUrl");

	if(infoid != "" && infoid != "null" && infoid != null){		
		defaultBean = ArticleRPC.getArticle(infoid,site_id);
		if(defaultBean){
			$("#info_article_table").autoFill(defaultBean);	
			setV(contentId,defaultBean.info_content);
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
	initButtomStyle();

	// 判断是否是站点管理员或超级管理员
    //if (top.isSiteManager(app_id,site_id)) {
    //	$(".isShow").show();
    //} else {
    //	if ($("#released_dtime").val() == "" || $("#released_dtime").val() == ''){
    //		$("#released_dtime").val(new Date().format("yyyy-MM-dd hh:mm:ss"))
    //	}
    //}

});

function savePicUrl(url)
{
	$("#thumb_url").val(url);
}
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
}else{
    %>
	<jsp:include page="../include/include_public_gk.jsp"/>
		<%
}
%>

<!-- 内容主体不同部分　开始 -->
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th style="vertical-align:top;">详细内容：</th>
			<td style="">
				<script id="info_content" type="text/plain" style="width:95%;height:400px;"></script>
				<div class="hidden" id="word_count_div"></div>
				<span class="blank9"></span>
				<input id="is_desc" name="is_desc" type="checkbox" checked/><label for="is_desc">自动截取前200字为摘要</label>&nbsp;&nbsp;
				<input id="is_remote" name="is_remote" type="checkbox" /><label for="is_remote">远程图片本地化</label>&nbsp;&nbsp;
				<input id="is_am_tupage" name="is_am_tupage" type="checkbox" value="1"/><label for="is_am_tupage">自动翻页</label>&nbsp;&nbsp;
				翻页字数<input id="tupage_num" name="tupage_num" type="text" value="1000" onblur="checkInputValue('tupage_num',true,5,'翻页字数','checkInt')"/>
				<input type="button" onclick="errorWordCompare()" value="错别字识别" />
			</td>
		</tr>
		<tr>
			<th>缩略图：</th>
			<td>
				<div style="float:left;margin:auto;"><input id="thumb_url" name="thumb_url" type="text" style="width:250px;" value="" /></div>
				<div style="float:left">&#160;<input id="i005" name="i005" type="button" onclick="getContentThumb()" value="自动获取" /></div>
				<div style="float:left">&#160;<input type="file" name="uploadify" id="uploadify"/></div>
				<div style="float:left">&nbsp;<input id="i005" name="i005" type="button" onclick="selectPageHandle()" value="图片库" /></div>
			</td>
		</tr>
		<tr>
			<th></th>
			<td>
				<div id="thumburldiv" style="float:left;margin:auto; border:1px solid #ccc">
					<ul id="thumburlList">
					</ul>
				</div>
			</td>
		</tr>
	  </tbody>
	</table>
<!-- 内容主体不同部分　结束 -->
<%
if(cb.getMlsx()==1){
    %>
	<jsp:include page="../include/include_public_high.jsp"/>
	<%
}else if(cb.getMlsx()==2){
    %>
    <jsp:include page="../include/include_public_high_gk.jsp"/>
    <%
}else{
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
