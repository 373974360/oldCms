<%@ page contentType="text/html; charset=utf-8"%>
<%
String cid = request.getParameter("cid");
String siteid = request.getParameter("site_id");
String infoid = request.getParameter("info_id");
String app_id = request.getParameter("app_id");

if(siteid == null || siteid.equals("null")){
	siteid = "GK";
}
if(app_id == null || app_id.equals("null")){
	app_id = "";
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
<title>信息修改</title>
 
<meta http-equiv="X-UA-Compatible" content="IE=8" />


<link type="text/css" rel="stylesheet" href="style/main.css" />
<link type="text/css" rel="stylesheet" href="style/sub.css" />
<link type="text/css" rel="stylesheet" href="style/content.css" />
<link rel="stylesheet" type="text/css" href="../../../styles/uploadify.css"/>
<link rel="stylesheet" type="text/css" href="../../../styles/themes/default/tree.css" />
<script type="text/javascript" src="../../../js/jquery.js"></script>
<script type="text/javascript" src="../../../js/jsonrpc.js"></script>
<script type="text/javascript" src="../../../js/java.js"></script>
<script type="text/javascript" src="../../../js/extend.js"></script>
<script type="text/javascript" src="../../../js/jquery.c.js"></script>
<script type="text/javascript" src="../../../js/common.js"></script>
<script type="text/javascript" src="../../../js/validator.js"></script>
<script type="text/javascript" src="../../../js/lang/zh-cn.js"></script>
<script type="text/javascript" src="../../../js/sysUI.js"></script>
<script type="text/javascript" src="../../../js/jquery-plugin/iColorPicker.js"></script>
<script type="text/javascript" src="../../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../../js/indexjs/indexList.js"></script>
<script type="text/javascript" src="../../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../../../js/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="../../../js/uploadTools.js"></script>
<script type="text/javascript" src="../../../js/jquery.uploadify.js"></script>
<script type="text/javascript" src="../../../js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="js/public.js"></script>
<script type="text/javascript" src="js/m_gk_gkzn.js"></script>
<script type="text/javascript"> 
 
var topnum = <%=topnum%>;
var site_id = "<%=siteid%>";
var cid = "<%=cid%>";
var app_id = "<%=app_id%>";
var infoid = "<%=infoid%>";
var contetnid = "info_content";
var mFlag = false;
 
$(document).ready(function(){
	initButtomStyle();
	init_input();
	
	iniSQbox();
	showGKCatName();
	$("#input_user").val(LoginUserBean.user_id);

	if(cid == 10)
	{
		reloadGKZNInfo();
		return;
	}
	if(cid == 11)
	{
		reloadGKNBInfo();
		return;
	}
	if(cid == 12)
	{
		reloadGKZYInfo();
		return;
	}
	reloadGKZYInfo();
});
init_editer(contetnid);

//年报
function reloadGKNBInfo()
{
	if(infoid != "" && infoid != "null" && infoid != null){		
		defaultBean = ArticleRPC.getArticle(infoid,site_id);
		if(defaultBean){
			$(":radio[value='"+defaultBean.info_status+"']").click();
			$("#info_article_table").autoFill(defaultBean);	
			setV(defaultBean.info_content);		
			$("#addButton").click(updateInfoData);	
			showStringLength('title','wordnum');
			mFlag = true;
		}
		$("#addButton").click(updateInfoData);		
		mFlag = true;		
	}
	else
	{			
		$("#addButton").click(addInfoData);		
		mFlag = false;
	}
}

//公开指南的初始方法
function reloadGKZNInfo()
{
	if(topnum == 0)//表示不是从列表页点过来的
		$("#addCancel").hide();
	defaultBean = ArticleRPC.getArticleBeanForCatSiteID(cid,site_id);
	if(defaultBean)
	{		
		$(":radio[value='"+defaultBean.info_status+"']").click();
		$("#info_article_table").autoFill(defaultBean);	
		setV(defaultBean.info_content);		
		$("#addButton").click(updateInfoData);	
		showStringLength('title','wordnum');
		mFlag = true;
	}
	else
	{
		$("#addButton").click(addInfoData);		
		mFlag = false;
	}
}

//公开指引的初始方法
function reloadGKZYInfo()
{
	if(infoid != "" && infoid != "null" && infoid != null){		
		defaultBean = ArticleRPC.getArticle(infoid,site_id);
		if(defaultBean){
			showStringLength('title','wordnum');
			$("#info_article_table").autoFill(defaultBean);	
			setV(defaultBean.info_content);	
			showStringLength('title','wordnum');
			showInfoStatusText();
		}
		$("#addButton").click(updateInfoData);		
		mFlag = true;		
	}
	else
	{			
		$("#addButton").click(addInfoData);		
		mFlag = false;
	}
}

function showGKCatName()
{
	if(cid == 12)
	{
		$("#showCatId").text("公开指引");
		return;
	}
	if(cid == 10)
	{
		$("#showCatId").text("公开指南");
		return;
	}
	if(cid == 11)
	{
		$("#showCatId").text("公开年报");
		return;
	}

	$("#showCatId").text(CategoryRPC.getCategoryCName(cid,site_id));
}

</script>
</head>
 
<body>
<span class="blank12"></span>
<form action="#" name="form1">
<div id="info_article_table">

 
<table id="" class="table_form table_option" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>所属栏目：</th>
			<td style=" width:100px;">
				<span class="f_red" id="showCatId">分类ID</span>
			</td>
			<td></td>
		</tr>
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>标题：</th>
			<td>
				<input id="title" name="title" type="text" class="width350" maxlength="80" value="" onkeypress="showStringLength('title','wordnum')" onkeyup="showStringLength('title','wordnum')"  onblur="checkInputValue('title',false,160,'信息标题','')"/>
				<span id="wordnum">0</span>字				
			</td>
		</tr>		
	</tbody>
</table>
<input id="model_id" type="hidden" class="width200" value="11" />
<input id="app_id" type="hidden" class="width200" value="<%=app_id%>" />
<input id="info_id" type="hidden" class="width200" value="<%=infoid%>" />
<input id="cat_id" type="hidden" class="width200" value="<%=cid%>" />
<input id="site_id" type="hidden" class="width200" value="<%=siteid%>" />
<input id="from_id" type="hidden" class="width200" value="0" />
<input id="content_url" type="hidden" class="width200" value="" />
<input id="wf_id" type="hidden" class="width200" value="0" />
<input id="step_id" type="hidden" class="width200" value="0" />
<input id="final_status" type="hidden" class="width200" value="0" />
<input id="hits" type="hidden" class="width200" value="0" />
<input id="day_hits" type="hidden" class="width200" value="0" />
<input id="week_hits" type="hidden" class="width200" value="0" />
<input id="month_hits" type="hidden" class="width200" value="0" />
<input id="lasthit_dtime" type="hidden" class="width200" value="" />
<input id="comment_num" type="hidden" class="width200" value="0" />
<input id="input_user" type="hidden" class="width200" value="0" />
<input id="input_dtime" type="hidden" class="width200" value="" />
<input id="modify_user" type="hidden" class="width200" value="0" />
<input id="modify_dtime" type="hidden" class="width200" value="" />
<input id="opt_dtime" type="hidden" class="width200" value="" />
<input id="is_auto_up" type="hidden" class="width200" value="0" />
<input id="is_auto_down" type="hidden" class="width200" value="0" />
<input id="is_host" type="hidden" class="width200" value="0" />
<input id="title_hashkey" type="hidden" class="width200" value="0" />
<input id="i_ver" type="hidden" class="width200" value="0" />
<input id="page_count" type="hidden" class="width200" value="0" />
<input id="prepage_wcount" type="hidden" class="width200" value="0" />
<input id="word_count" type="hidden" class="width200" value="0" />
 
<!-- 内容主体不同部分　开始 -->
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th style="vertical-align:top;">详细内容：</th>
			<td style="">
				<textarea id="info_content" name="info_content" style="width:620px;;height:300px;visibility:hidden;"></textarea>
			<span class="blank9"></span>
			<input id="is_remote" name="is_remote" type="checkbox"/><label for="is_remote">远程图片本地化</label>
			</td>
		</tr>		
	  </tbody>
	</table>
<!-- 内容主体不同部分　结束 -->
 
 
 
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>		
		<tr id="info_staus_tr">
			<th>发布状态：</th>
			<td >
				<ul class="flagClass">								
					<li id="opt_303" ><input id="f" name="info_status" type="radio" value="4" checked="true"/><label for="f">待发</label></li>
					<li id="opt_302" ><input id="g" name="info_status" type="radio" value="8" /><label for="g">发布</label></li>
				</ul>
			</td>
		</tr>        
	</tbody>
</table>
<div class="sq_box">
	<div class="sq_title_box" >
		<div class="sq_title sq_title_plus">高级属性</div>
		<div class="sq_title_right">点击展开</div>
	</div>
	<div class="sq_box_content hidden ">
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>		
		<tr>
			<th>权重：</th>
			<td>
				<input id="weight" name="weight" type="text" style="width:50px;" value="60" maxlength="2" onblur="checkInputValue('weight',true,2,'权重','checkNumber')"/>默认值：60，取值范围（0-99）
			</td>
		</tr>
		<tr>
			<th>发布时间：</th>
			<td>
				<input id="released_dtime" name="released_dtime" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" />
			</td>
		</tr>
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>上线：</th>
			<td style="width:100px;">
				<input id="up_dtime" name="up_dtime" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" />
			</td>
			<th>下线：</th>
			<td style="width:100px;">
				<input id="down_dtime" name="down_dtime" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" />
			</td>
			<td></td>
		</tr>
	</tbody>
</table>
	</div>
</div>
 
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
			<input id="addCancel" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex)" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</div>
</form>
</body>
</html>

