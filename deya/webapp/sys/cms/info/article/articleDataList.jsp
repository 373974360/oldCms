<%@ page contentType="text/html; charset=utf-8"%>
<%
String cid = request.getParameter("cat_id");
String siteid = request.getParameter("site_id");
String app_id = request.getParameter("app_id");
if(siteid == null || siteid.equals("null")){
	siteid = "GK";
}
if(app_id == null || app_id.trim().equals("")){
	app_id = "cms";
}
String snum = request.getParameter("snum");
if(snum == null || snum.trim().equals("") || snum.trim().equals("null") ){
	snum = "0";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息管理</title>
<link type="text/css" rel="stylesheet" href="../../../styles/themes/default/tree.css" />
<link type="text/css" rel="stylesheet" href="../../../styles/sq.css" />


<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../../js/indexjs/indexList.js"></script>
<script type="text/javascript" src="../../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/info.js?v=20151019"></script>

<script type="text/javascript">

var site_id = "<%=siteid%>";
var app = "<%=app_id%>";
var cid = "<%=cid%>";
var snum = "<%=snum%>";
var opt_ids = ","+top.getOptIDSByUser(app,site_id)+",";//登录人所拥有管理权限ID
var gk_article = false;//特殊栏目标识，在政务公开中使用的是内容管理中的文章模型

$(document).ready(function(){
	if(cid == 10 || cid == 11 || cid == 12 || app == "ggfw")
	{
		gk_article = true;
		//特殊栏目，不需要设置权限
		$("#btn305").hide();
		$("#btnSearch").hide();
		//$(".list_tab").eq(2).hide();
		$(".list_tab").eq(3).hide();
		$(".list_tab").eq(4).hide();
		$(".list_tab").eq(5).hide();
	}
	else
		setUserOperate();

	isSubNode(cid);

	initButtomStyle();
	initTabAndStatus();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");

	$(".list_tab").eq(snum).click();

	showModels();
	if(subNode == false)
	{
		$(".x_add").click(
				function (event) {
					openAddInfoPage($(event.target).attr("value"));
				}
		);
	}else
	{
		$(".infoadd_area").hide();
		$(":button[name='btn404']").hide();
		$(":button[name='btn304']").hide();
	}

	if(app == "zwgk")
	{
		//$(":button[id='btn306']").hide();
		$(":button[name='btn404']").hide();
		$(":button[name='btn304']").hide();
	}
});

function setUserOperate()
{
	$("#btn299").hide();
    $("#btnzpxx").hide();
	$(":button[name!='btn']").hide();

	if(opt_ids.indexOf(",299,") > -1)
        $("#btn299").show();

    if(opt_ids.indexOf(",531,") > -1){
        $("#btnzpxx").show();
    }

	$(":button[id!='btn']").each(function(){
		var o_id = ","+$(this).attr("id").replace("btn","")+",";
		if(opt_ids.indexOf(o_id) > -1)
			$(this).show();
	});
	$("#btnSearch").show();
    var isChangeDelete = false;
    var temp_wf_id = CategoryRPC.getWFIDByCatID(cid,site_id);
    //判断是否是站点管理员或超级管理员
    if(top.isSiteManager(app,site_id))
    {
        isChangeDelete = true;
    }else
    {
        if(temp_wf_id == 0)
        {
            //是否有发布权限
            if(opt_ids.indexOf(",302,") > -1)
            {
                isChangeDelete = true;
            }
        }
        else if(opt_ids.indexOf(",303,") > -1){
            var step_id = getMaxStepIDByUserID(temp_wf_id,app,site_id);
            //得到管理员审核流程中最大的步骤ID, 只有最终审核的人才有终审通过权限
            if(step_id == 100)
            {
                isChangeDelete = true;
            }/*else{
                $(":button[name='btn302']").hide();
                $(":button[name='btn303']").hide();
			}*/
        }else{

        }
    }
    /*if(!isChangeDelete)
    {
        $(":button[name='btn332']").hide();
        $(":button[name='btn307']").hide();
        $(":button[name='btn306']").hide();
        $(":button[name='btn305']").hide();
    }*/
}

function openSendInfoPage()
{
	top.OpenModalWindow("信息报送","/sys/cms/info/article/getReceiveSite.jsp?site_id="+site_id+"&app_id="+app,570,420);
}

function getSelectInfoBeans()
{
	return table.getSelecteBeans();
}
function blogTS(){
    var selectIDS = table.getSelecteCheckboxValue("info_id");
    if(selectIDS.split(",").length>1){
        alert('只能选择一条信息！！');
	}else{
        $.post("/sys/cms/info/article/blog.jsp",{id:selectIDS},function(data){
            var mess=JSON.parse(data);
            if(mess.data=='ok')
                alert('推送成功！！');
        });
	}
}
function weChatTS(){
    var selectIDS = table.getSelecteCheckboxValue("info_id");
    if(selectIDS.split(",").length>8){
        alert('最多选择8条信息！！');
    }else {
        $.post("/sys/cms/info/article/WeChat.jsp", {id: selectIDS,siteid:site_id}, function (data) {
            var mess = JSON.parse(data);
            if (mess.data == 'ok') {
                alert('推送成功！！');
            } else {
                alert('推送失败！！');
            }
        });
    }
}
</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="left" width="180">
			<select id="pageGoNum" name="pageSize" class="input_select width80" onchange="changeFactor()">

			</select>
			<select id="searchTimes" class="input_select width80" onchange="changeFactor2()">
				<option selected="selected" value="0b">日期</option>
				<option value="1b">今日</option>
				<option value="2b">昨日</option>
                <option value="3b">一周内</option>
				<option value="4b">一月内</option>
			</select>
		</td>
		<td align="left" width="80">
			<ul class="infoadd_area" id="btn299">
				<li class="x_add" value="0" >
					<ul class="MUL" id="addLabList">
					</ul>
				</li>
			</ul>

		</td>

		<td align="left" valign="middle" >

			<input id="searchkey" type="text" class="input_text" style="width:240px;" value=""  /><input id="btn" name="btn" type="button" value="搜索" onclick="searchInfo()"/>
			<select id="orderByFields" class="input_select" onchange="changeTimeSort(this.value)">
				<option selected="selected" value="1">时间倒序</option>
				<option value="2">时间正序</option>
				<option value="3">权重倒序</option>
				<option value="4">权重正序</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索" />
		</td>
	</tr>

	<tr style="height:10px; line-height:10px;overflow:hidden"><td  colspan="3" style="height:10px; line-height:10px;overflow:hidden"></td></tr>
	<tr>
	<td align="left" width="" colspan="3">
			<ul class="fromTabs">
				<li class="list_tab list_tab_cur">
					<div class="tab_left">
						<div class="tab_right">已发</div>
					</div>
				</li>
                <!--
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right">待发</div>
					</div>
				</li>
				 -->
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right">已撤</div>
					</div>
				</li>
                <li class="list_tab">
					<div class="tab_left">
						<div class="tab_right">待审</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right">审核中</div>
					</div>
				</li>
                <li class="list_tab">
					<div class="tab_left">
						<div class="tab_right">退稿</div>
					</div>
				</li>
                <li class="list_tab">
					<div class="tab_left">
						<div class="tab_right">草稿</div>
					</div>
				</li>
                <li class="list_tab">
					<div class="tab_left">
						<div class="tab_right">回收站</div>
					</div>
				</li>
             </ul>
		</td>
		</tr>
</table>
	<span class="blank3"></span>
	<div id="table"></div>
	<div id="turn"></div>
<div class="infoListTable" id="listTable_0">
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn404" name="btn404" type="button" onclick="getInfoFromOtherCat();" value="获取" />
			<input id="btn305" name="btn305" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForPush()');" value="推送" />
			<input id="btn424" name="btn424" type="button" onclick="publicSelectCheckbox(table,'info_id','openSendInfoPage()');" value="站群报送" />
			<input id="btn306" name="btn306" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />
			<input id="btn307" name="btn307" type="button" onclick="cancleInfo()" value="撤销" />
			<input id="btn332" name="btn332" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />
			<input id="btn" name="btn" type="button" onclick="publicSelectCheckbox(table,'info_id','createStaticContentHtml()');" value="生成静态页" />
			<input id="btn520" name="btn520" type="button" onclick="blogTS()" value="推送微博" />
			<input id="btn521" name="btn521" type="button" onclick="weChatTS()" value="推送微信" />
            <input id="btnzpxx" name="btnzpxx" type="button" onclick="viewJl()" value="查看简历" />
        </td>
	</tr>
</table>
</div>
<!--
<div class="infoListTable hidden" id="listTable_1">
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn302" name="btn1" type="button" onclick="publicSelectCheckbox(table,'info_id','publishInfo()')" value="发布" />
			<input id="btn404" name="btn1" type="button" onclick="getInfoFromOtherCat();" value="获取" />
			<input id="btn305" name="btn2" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForPush()');" value="推送" />
			<input id="btn424" name="btn2" type="button" onclick="publicSelectCheckbox(table,'info_id','openSendInfoPage()');" value="站群报送" />
			<input id="btn306" name="btn3" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />
			<input id="btn332" name="btn4" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />
			&nbsp;&nbsp;
			<input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索" />
		</td>
	</tr>
</table>
</div>
-->
<div class="infoListTable hidden" id="listTable_1">
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn302" name="btn302" type="button" onclick="publicSelectCheckbox(table,'info_id','publishInfo()')" value="发布" />
			<input id="btn305" name="btn305" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForPush()');" value="推送" />
			<input id="btn306" name="btn306" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />
            <input id="btn332" name="btn332" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />
			&nbsp;&nbsp;
			<input id="btnSearch" name="btnSearch" type="button" onclick="openHighSearchPage()" value="高级搜索" />
		</td>
	</tr>
</table>
</div>

<div class="infoListTable hidden" id="listTable_2">
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn303" name="btn303" type="button" onclick="publicSelectCheckbox(table,'info_id','onPass()');" value="通过" />
			<input id="btn303_1" name="btn303" type="button" onclick="publicSelectCheckbox(table,'info_id','noPassDesc()');" value="退稿" />
			<input id="btn305" name="btn305" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForPush()');" value="推送" />
			<input id="btn306" name="btn306" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />
            <input id="btn332" name="btn332" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />
			&nbsp;&nbsp;
			<input id="btnSearch" name="btnSearch" type="button" onclick="openHighSearchPage()" value="高级搜索" />
		</td>
	</tr>
</table>
</div>

<div class="infoListTable hidden" id="listTable_3">
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">

			<input id="btnSearch" name="btnSearch" type="button" onclick="openHighSearchPage()" value="高级搜索" />
		</td>
	</tr>
</table>
</div>

<div class="infoListTable hidden" id="listTable_4">
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn" name="btn" type="button" onclick="publicSelectCheckbox(table,'info_id','toPass()');" value="送审" />
			<input id="btn306" name="btn306" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />
            <input id="btn332" name="btn332" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />
			&nbsp;&nbsp;
			<input id="btnSearch" name="btnSearch" type="button" onclick="openHighSearchPage()" value="高级搜索" />
		</td>
	</tr>
</table>
</div>

<div class="infoListTable hidden" id="listTable_5">
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn" name="btn" type="button" onclick="publicSelectCheckbox(table,'info_id','toPass()');" value="送审" />
			<input id="btn305" name="btn305" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForPush()');" value="推送" />
            <input id="btn306" name="btn306" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />
			<input id="btn332" name="btn332" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />
			&nbsp;&nbsp;
			<input id="btnSearch" name="btnSearch" type="button" onclick="openHighSearchPage()" value="高级搜索" />
		</td>
	</tr>
</table>
</div>

<div class="infoListTable hidden" id="listTable_6">
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn308" name="btn308" type="button" onclick="publicSelectCheckbox(table,'info_id','rebackInfo()');" value="还原" />
			<input id="btn309" name="btn309" type="button" onclick="deleteRecord(table,'info_id','realDelInfo()');" value="彻底删除" />
            <input id="btn310" name="btn310" type="button" onclick="clearAllInfo();" value="清空回收站" />
			&nbsp;&nbsp;
			<input id="btnSearch" name="btnSearch" type="button" onclick="openHighSearchPage()" value="高级搜索" />
		</td>
	</tr>
</table>
</div>

</div>
</body>
</html>