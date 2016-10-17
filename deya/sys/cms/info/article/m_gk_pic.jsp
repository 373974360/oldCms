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
<script type="text/javascript" src="js/m_gk_pic.js"></script>
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
var seq_num = 1;//自增序列

	$(document).ready(function(){
		$("#is_pic").hide();
		$("#is_pic").attr("checked",true);
		$("#is_pic").next().hide();

		initButtomStyle();
		init_input();
		
		reloadPublicGKInfo();

		publicUploadButtomLoad("uploadify",true,false,"",0,5,getReleSiteID(),"saveThumbUrl");
		publicUploadButtomLoad("uploadify_pic",true,true,"",1,0,getReleSiteID(),"savePicUrl");

		if(infoid != "" && infoid != "null" && infoid != null){		
			defaultBean = ModelUtilRPC.select(infoid,site_id,"gkpic");
			if(defaultBean){
				$("#info_article_table").autoFill(defaultBean);	
				KE.html("pic_content", defaultBean.pic_content);
				setSelectImgToTable();
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
	init_editer("pic_content");	

//获取已添加过的图片
function setSelectImgToTable()
{
	cur_sort_tablename = "add_pic_table";
	var list = defaultBean.item_list;
	list = List.toJSList(list);
	if(list != null && list.size() > 0)
	{
		for(var i=0;i<list.size();i++)
			addStep(list.get(i).pic_path,list.get(i).pic_note,list.get(i).pic_title);
	}
	init_input();
	cur_sort_tablename = "add_pic_table";
	resetNum();
}	

//打开图片选择窗口
function selectPageHandlePic(handlName,input_type)
{
	openSelectMaterialPage(handlName,getReleSiteID(),input_type);	
}
//将返回的图片地址写入到控件中
function savePicUrl(url,title)
{
	var titleA = title.split(",");
	var tempA = url.split(",");
	for(var i=0;i<tempA.length;i++)
	{
		addStep(tempA[i],"",titleA[i].substring(0,titleA[i].indexOf(".")));
	}
	init_input();
	cur_sort_tablename = "add_pic_table";
	resetNum();
}

//添加一张图片
function addStep(img,p_note,title)
{		    
	    var trLength = $("#add_pic_table tr").length+1;
		
		var tmpTR = "";
		tmpTR  = "<tr>"
		tmpTR +="	<td style=\"width:120px; text-align:center;\">";
		tmpTR +="	<img src=\""+img+"\" width=\"100\" height=\"100\" />";
		tmpTR +="	<div ><a href=\"#\" onclick=\"changeImgPathHandle(this)\">重新上传</a></div>";
		tmpTR +="	</td>";
		tmpTR +="	<td style=\"width:460px;\"><div>标题：<input type='text' id='pic_title' name='pic_title' class='width400' value='"+title+"'/></div>";
		tmpTR +="	<div style='margin-top:3px;'>简介：<textarea id=\"pic_note_"+seq_num+"\" name=\"pic_note_"+seq_num+"\" style=\"width:400px;height:80px\" class=\"lineHeight140\">"+p_note+"</textarea></div></td>";
		tmpTR +="	<td>";
		tmpTR +="		<ul class=\"optUL\">";
		tmpTR +="			<li class=\"opt_up ico_up\" title=\"上移\"></li>";
		tmpTR +="			<li class=\"opt_down ico_down\" title=\"下移\"></li>";
		tmpTR +="			<li class=\"opt_delete ico_delete\" title=\"删除\"></li>";
		tmpTR +="		</ul>";
		tmpTR +="	</td>";
		tmpTR +="</tr>";		
		
		$("#add_pic_table").append(tmpTR);		
		seq_num += 1;
}

//图片重新上传
var current_change_img;
function changeImgPathHandle(obj)
{
	current_change_img = $(obj).parent().prev();	
	selectPageHandlePic('changeImgPath','radio');
}

function changeImgPath(path)
{
	$(current_change_img).attr("src",path);
}

//缩略图自动获取
function getFirstPic()
{
	$("#thumb_url").val($("#add_pic_table img").first().attr("src"));
}

//设置选中的图片到缩略图输入框中
function saveThumbUrl(path)
{
	$("#thumb_url").val(path);
}
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
			<th style="vertical-align:top;"><span class="f_red">*</span>图组：<br />
			<td style="" align="left">
				<table id="add_pic_table" class="" style="width:100%" border="0" cellpadding="0" cellspacing="0">
                	
                </table>
			</td>
		</tr>
        <tr>
			<th></th>
			<td>
				<div class="left"><input type="file" name="uploadify_pic" id="uploadify_pic"/></div>
				<div class="left">&#160;<input id="i005" name="i005" type="button" onclick="javascript:selectPageHandlePic('savePicUrl','checkbox');" value="图片库" /></div>
                <!-- <div class="left">&#160;<input id="i005" name="i005" type="button" onclick="javascript:void(0);" value="远程采集" /></div> -->
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">详细内容：</th>
			<td style="">
				<textarea id="pic_content" name="pic_content" style="width:620px;;height:300px;visibility:hidden;"></textarea>
			<!-- <span class="blank9"></span>
			<input id="is_remote" name="is_remote" type="checkbox" /><label for="is_remote">远程图片本地化</label> -->
			</td>
		</tr>
		<tr>
			<th>缩略图：</th>
			<td>
				<div style="float:left;margin:auto;"><input id="thumb_url" name="thumb_url" type="text" style="width:250px;" value="" /></div>
				<div style="float:left">&#160;<input id="i005" name="i005" type="button" onclick="getFirstPic()" value="自动获取" /></div>
				<div style="float:left">&#160;<input type="file" name="uploadify" id="uploadify"/></div>
				<div style="float:left">&#160;<input id="i005" name="i005" type="button" onclick="selectPageHandlePic('saveThumbUrl','radio')" value="图片库" /></div>
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
