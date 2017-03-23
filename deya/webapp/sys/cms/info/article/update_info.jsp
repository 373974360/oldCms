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
<script type="text/javascript">
var InfoBean = new Bean("com.deya.wcm.bean.cms.info.InfoBean",true);
var topnum = "<%=topnum%>";
var site_id = "<%=siteid%>";
var cid = "<%=cid%>";
var app_id = "<%=app_id%>";
var infoid = "<%=infoid%>";
var linksInfo = "";
var focusInfo = "";
var mFlag = false;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	publicUploadButtomLoad("uploadify",true,false,"",0,5,site_id,"savePicUrl");

	if(infoid != "" && infoid != "null" && infoid != null){		
		defaultBean = InfoBaseRPC.getInfoById(infoid,site_id);
		if(defaultBean){
			$("#info_article_table").autoFill(defaultBean);			
			publicReloadUpdateInfos();			
		}
		$("#addButton").click(updateInfoData);		
		mFlag = true;
		
		if(defaultBean.is_host == 1)
		{//引用的信息显示编辑源信息按钮
			$("#from_info_button").show();
		}
	}
	reloadPublicInfo();	
});

//修改
function updateInfoData()
{	
	//var bean = BeanUtil.getCopy(InfoBean);	
	$("#info_article_table").autoBind(defaultBean);

	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	}
	
	if(InfoBaseRPC.updateInfo(defaultBean))
	{
		msgAlert("信息"+WCMLang.Add_success);
		gotoListPage();
	}else
		msgWargin("信息"+WCMLang.Add_fail);
}

function selectPageHandle()
{
	openSelectMaterialPage('savePicUrl',site_id,'radio');	
}

function savePicUrl(url)
{
	$("#thumb_url").val(url);
}

function openFromInfoPage()
{
	var from_info_bean = InfoBaseRPC.getInfoById(defaultBean.from_id);	
	if(from_info_bean != null)
	{
		window.location.href = "/sys/cms/info/article/"+getAddPagebyModel(from_info_bean.model_id)+"?cid="+from_info_bean.cat_id+"&info_id="+defaultBean.from_id+"&site_id="+from_info_bean.site_id+"&app_id="+from_info_bean.app_id+"&topnum="+topnum;
	}
}

function getAddPagebyModel(model_id)
{
	var add_page = jsonrpc.ModelRPC.getModelAddPage(model_id);
	if(add_page == "" || add_page == null)
		add_page = "m_article.jsp";
	return add_page;
}
</script>
</head>

<body>
<span class="blank12"></span>
<form action="#" name="form1">
<div id="info_article_table">
<input id="model_id" type="hidden" class="width200" value="<%=model%>" />
<input id="app_id" type="hidden" class="width200" value="<%=app_id%>" />
<jsp:include page="../include/include_public.jsp"/>

<!-- 内容主体不同部分　开始 -->
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>		
		<tr>
			<th>缩略图：</th>
			<td>
				<div style="float:left;margin:auto;"><input id="thumb_url" name="thumb_url" type="text" style="width:250px;" value="" /></div>				
				<div style="float:left"><input type="file" name="uploadify" id="uploadify"/></div>
				<div style="float:left">&nbsp;<input id="i005" name="i005" type="button" onclick="selectPageHandle()" value="图片库" /></div>
			</td>
		</tr>
	  </tbody>
	</table>
<!-- 内容主体不同部分　结束 -->

<jsp:include page="../include/include_public_high.jsp"/>

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
			<input id="addCancel" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex)" value="取消" />&nbsp;&nbsp;&nbsp;
			<input id="from_info_button" class="hidden" name="btn1" type="button" onclick="openFromInfoPage()" value="编辑源信息" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
