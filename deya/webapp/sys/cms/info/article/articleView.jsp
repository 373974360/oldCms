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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息查看</title>
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

<script type="text/javascript" src="js/info.js"></script>
<script type="text/javascript" src="js/addInfo.js"></script>
<script type="text/javascript">

var site_id = "<%=siteid%>";
var cid = "<%=cid%>";
var app_id = "<%=app_id%>";
var infoid = "<%=infoid%>";
var contetnid = "info_content";
var linksInfo = "";
var focusInfo = "";
var mFlag = false;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	
	//initTree();
	showCatId(cid);
	publicUploadButtomLoad("uploadify",true,false,"",0,5,site_id,"savePicUrl");

	$("#theUser").val(LoginUserBean.user_realname);
	
	if(infoid != "" && infoid != "null" && infoid != null){		
		defaultBean = ArticleRPC.getArticle(infoid,site_id);
		if(defaultBean){
			$("#info_article_table").autoFill(defaultBean);	
			setV(defaultBean.info_content);
			$(":radio[name='info_status'][value='"+defaultBean.info_status+"']").attr("checked","checked");
			showRelatedInfos(infoid);
		}
		$("#addButton").click(updateInfoData);
		$("#t1").hide();
		$("#t2").hide();
		$("#t3").hide();
		$("#t4").hide();
		showStringLength('title','wordnum');
		showStringLength('subtitle','wordnum2');
		$("#modify_user").val(LoginUserBean.user_id);
		mFlag = true;
		$("#app_id").val(app_id);
		showFocusWares(infoid);
	}
	else
	{
		$("#cat_id").val(cid);
		$("#site_id").val(site_id);
		$("#addButton").click(addInfoData);
		$("#input_user").val(LoginUserBean.user_id);
		mFlag = false;
	}
});

function selectPageHandle()
{
	openSelectMaterialPage('savePicUrl',site_id,'radio');	
}

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
				<div id="title"></div>
			</td>
		</tr>
		<tr id="subTitleTr" style="display:none;">
			<th>副标题：</th>
			<td>
				<div id="subtitle"></div>
			</td>
		</tr>
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>来源：</th>
			<td width="135">
				<div id="source">
				</div>
			</td>
			<th style="width:40px;">作者：</th>
			<td width="135">
				<div id="author">
				</div>
			</td>
			<th style="width:40px;">编辑：</th>
			<td width="135">
				<div id="theUser">
				</div>
			</td>
			<td></td>
		</tr>
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th style="vertical-align:top;">详细内容：</th>
			<td style="">
				<div id="info_content"></div>
			</td>
		</tr>
		<tr>
			<th>焦点图片：</th>
			<td>
				<input id="thumb_url" name="thumb_url" type="text" style="width:250px;" value="" />
			</td>
		</tr>
		
		<tr>
			<th style="vertical-align:top;">内容摘要：</th>
			<td>
				<div id="description" ></div>
			</td>
		</tr>
		
		<tr>
			<th>发布状态：</th>
			<td>
				<div id="info_status"></div>
			</td>
		</tr>
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th style="vertical-align:top;">META关键词：</th>
			<td>
				<div id="info_keywords" style="width:620px;height:70px"></div>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">META网页描述：</th>
			<td>
				<div id="info_description" style="width:620px;height:70px"></div>
			</td>
		</tr>
		<tr>
			<th>评论：</th>
			<td>
				<div id="is_allow_comment"></div>
			</td>
		</tr>
		<tr>
			<th>权重：</th>
			<td>
				<div id="weight"></div>
			</td>
		</tr>
		<tr>
			<th>发布时间：</th>
			<td>
				<div id="released_dtime" ></div>
			</td>
		</tr>
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>上线：</th>
			<td style="width:100px;">
				<div id="up_dtime" ></div>
			</td>
			<th>下线：</th>
			<td style="width:100px;">
				<div id="down_dtime" ></div>
			</td>
			<td></td>
		</tr>
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>相关文章：</th>
			<td>
				<div id="relatedInfo">相关文章</div>
			</td>
		</tr>
		<tr>
			<th></th>
			<td align="left">
				<table id="relateInfos" class="" style="width:100%;" border="0" cellpadding="0" cellspacing="0">
				</table>
			</td>
		</tr>
	</tbody>
</table>
<input id="model_id" type="hidden" class="width200" value="<%=model%>" />
<input id="app_id" type="hidden" class="width200" value="<%=app_id%>" />
<jsp:include page="hiddenInputs.jsp"/>
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
			<input id="addButton" name="btn1" type="button" onclick="" value="编辑" />
			<input id="addButton" name="btn1" type="button" onclick="" value="审核" />
			<input id="addButton" name="btn1" type="button" onclick="" value="发布" />
			<input id="addButton" name="btn1" type="button" onclick="" value="删除" />
			<input id="addButton" name="btn1" type="button" onclick="closeWindowToListPage()" value="关闭" />
			<input id="addButton" name="btn1" type="button" onclick="" value="推送" />
			<input id="addButton" name="btn1" type="button" onclick="" value="生成静态页" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</div>
</form>
</body>
</html>
