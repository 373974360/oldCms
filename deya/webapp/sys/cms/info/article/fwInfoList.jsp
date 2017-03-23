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
<script type="text/javascript" src="js/info.js"></script>

<script type="text/javascript">

var site_id = "ggfw";
var app = "<%=app_id%>";
var cid = "<%=cid%>";
var snum = "<%=snum%>";
var gk_article = false;//特殊栏目标识，在政务公开中使用的是内容管理中的文章模型
$(document).ready(function(){	
	initButtomStyle();
	initTabAndStatus();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	isSubNode(cid);
	initTable();
	$(".list_tab").eq(snum).click();
	
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
		$("#btn404").each(function(){
			$(this).hide()
		});
	}
});

function getFWInfo()
{
	is_save_first_page = true;
	OpenModalWindow("信息高级获取","/sys/cms/info/article/fwInfoGet.jsp?cat_id="+cid+"&app_id="+app,800,530);
}

</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" > 
	<tr> 
		<td align="left" class="fromTabs" width="130"> 
			<select id="pageGoNum" name="pageSize" class="input_select width60" onchange="changeFactor()"> 
			
			</select> 
			<select id="searchTimes" class="input_select width60" onchange="changeFactor2()"> 
				<option selected="selected" value="0b">日期</option> 
				<option value="1b">今日</option> 
				<option value="2b">昨日</option> 
                <option value="3b">一周内</option> 
				<option value="4b">一月内</option> 
			</select> 
		</td> 
		<td align="left" class="fromTabs" width="80"> 
			<ul class="infoadd_area" id="btn299"> 
				<li class="x_add" value="0" > 
					<ul class="MUL" id="addLabList"> 
						<li class="ico_link" value="12" >链接</li>
					</ul> 
				</li> 
			</ul> 
			<span class="blank3"></span> 
		</td> 
		<td align="left" width=""> 
			<ul class="fromTabs"> 
				<li class="list_tab list_tab_cur"> 
					<div class="tab_left"> 
						<div class="tab_right">已发</div> 
					</div> 
				</li> 
				<li class="list_tab" > 
					<div class="tab_left"> 
						<div class="tab_right">待发</div> 
					</div> 
				</li> 
				<li class="list_tab"> 
					<div class="tab_left"> 
						<div class="tab_right">已撤</div> 
					</div> 
				</li>                 
                <li class="list_tab" num="6"> 
					<div class="tab_left" > 
						<div class="tab_right">回收站</div> 
					</div> 
				</li> 
             </ul> 
		</td> 
		<td align="right" valign="middle" class="fromTabs">
			
			<input id="searchkey" type="text" class="input_text" style="width:80px;" value=""  /><input id="btn" type="button" value="搜索" onclick="searchInfo()"/>
			<select id="orderByFields" class="input_select" onchange="changeTimeSort(this.value)"> 
				<option selected="selected" value="1">时间倒序</option> 
				<option value="2">时间正序</option> 
				<option value="3">权重倒序</option> 
				<option value="4">权重正序</option>
			</select> 
			<span class="blank3"></span> 
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
			<input id="btn404" name="btn1" type="button" onclick="getFWInfo();" value="高级获取" />
			<!-- <input id="btn306" name="btn3" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />  -->
			<input id="btn307" name="btn5" type="button" onclick="cancleInfo()" value="撤销" /> 
			<input id="btn301" name="btn4" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" /> 			
			<input id="btn" name="btn6" type="button" onclick="publicSelectCheckbox(table,'info_id','createStaticContentHtml()');" value="生成静态页" />
			&nbsp;&nbsp;
			<input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索" /> 
		</td> 
	</tr> 
</table> 
</div> 
 
<div class="infoListTable hidden" id="listTable_1"> 
<table class="table_option" border="0" cellpadding="0" cellspacing="0"> 
	<tr> 
		<td align="left" valign="middle"> 
			<input id="btn302" name="btn1" type="button" onclick="publicSelectCheckbox(table,'info_id','publishInfo()')" value="发布" /> 
			<input id="btn404" name="btn1" type="button" onclick="getFWInfo();" value="高级获取" /> 
			
			<!-- <input id="btn306" name="btn3" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />  -->
			<input id="btn301" name="btn4" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />
			&nbsp;&nbsp;
			<input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索" />
		</td> 
	</tr> 
</table> 
</div> 
 
<div class="infoListTable hidden" id="listTable_2"> 
<table class="table_option" border="0" cellpadding="0" cellspacing="0"> 
	<tr> 
		<td align="left" valign="middle"> 
			<input id="btn302" name="btn1" type="button" onclick="publicSelectCheckbox(table,'info_id','publishInfo()')" value="发布" />
			<!-- <input id="btn306" name="btn3" type="button" onclick="publicSelectCheckbox(table,'info_id','openWindowForMov()');" value="移动" />  -->
            <input id="btn301" name="btn3" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />
			&nbsp;&nbsp;
			<input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索" />
		</td> 
	</tr> 
</table> 
</div>
 
<div class="infoListTable hidden" id="listTable_6"> 
<table class="table_option" border="0" cellpadding="0" cellspacing="0"> 
	<tr> 
		<td align="left" valign="middle"> 
			<input id="btn308" name="btn1" type="button" onclick="publicSelectCheckbox(table,'info_id','rebackInfo()');" value="还原" /> 
			<input id="btn309" name="btn3" type="button" onclick="deleteRecord(table,'info_id','realDelInfo()');" value="彻底删除" /> 
            <input id="btn310" name="btn3" type="button" onclick="clearAllInfo();" value="清空回收站" />
			&nbsp;&nbsp;
			<input id="btnSearch" name="btn6" type="button" onclick="openHighSearchPage()" value="高级搜索" />
		</td> 
	</tr> 
</table> 
</div>

</div>
</body>
</html>