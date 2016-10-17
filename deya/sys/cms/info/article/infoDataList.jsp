<%@ page contentType="text/html; charset=utf-8"%>
<%
String cid = request.getParameter("cat_id");
String siteid = request.getParameter("site_id");
String app_id = request.getParameter("app_id");
if(app_id == null || app_id.trim().equals("")){
	app_id = "cms";
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
<script type="text/javascript" src="../../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/info.js"></script>

<script type="text/javascript">

var site_id = "<%=siteid%>";
var app = "<%=app_id%>";
var cid = "<%=cid%>";

$(document).ready(function(){	
	Init_InfoTable("infoList1");
	Init_InfoTable("infoList2");
	Init_InfoTable("infoList3");
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	initTable();
	reloadInfoDataList();	
});


</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td align="left" class="fromTabs width180" style="">
				<select id="pageGoNum" name="pageSize"
					class="input_select width80""  >
					<option value="0" selected="selected">
						全部
					</option>
					<option value="1">
						文章
					</option>
					<option value="2">
						图组
					</option>
					<option value="3">
						链接
					</option>
					<option value="4">
						视频
					</option>
				</select>
				<input id="btn1" name="btn1" class="x_add" type="button"
					onclick="openAddInfoPage();" />
				<span class="blank3"></span>
			</td>
			<td align="left" width="50%">
				<ul class="fromTabs">
					<li class="list_tab list_tab_cur">
						<div class="tab_left">
							<div class="tab_right">
								已发
							</div>
						</div>
					</li>
					<li class="list_tab">
						<div class="tab_left">
							<div class="tab_right">
								待发
							</div>
						</div>
					</li>
					<li class="list_tab">
						<div class="tab_left">
							<div class="tab_right">
								已撤
							</div>
						</div>
					</li>
					<li class="list_tab">
						<div class="tab_left">
							<div class="tab_right">
								待审
							</div>
						</div>
					</li>
					<li class="list_tab">
						<div class="tab_left">
							<div class="tab_right">
								退稿
							</div>
						</div>
					</li>
					<li class="list_tab">
						<div class="tab_left">
							<div class="tab_right">
								草稿
							</div>
						</div>
					</li>
					<li class="list_tab">
						<div class="tab_left">
							<div class="tab_right">
								回收站
							</div>
						</div>
					</li>
				</ul>
			</td>
			<td align="right" valign="middle" class="fromTabs">
				<select id="searchFields" class="input_select width60">
					<option selected="selected" value="">
						日期
					</option>
					<option value="">
						今日
					</option>
					<option value="">
						昨日
					</option>
					<option value="">
						一周内
					</option>
					<option value="">
						一月内
					</option>
				</select>
				<input id="searchkey" type="text" class="input_text" value="" />
				<input id="btnSearch" type="button" class="btn x2" value="搜索" />
				<select id="orderByFields" class="input_select width50">
					<option selected="selected" value="">
						倒序
					</option>
					<option value="">
						顺序
					</option>

				</select>
				<span class="blank3"></span>
			</td>
		</tr>
	</table>
	<span class="blank3"></span>
	<div id="table"></div>
	<div id="turn"></div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0"> 
		<tr> 
			<td align="left" valign="middle"> 
				<input id="btn1" name="btn1" type="button" onclick="javascript:void(0);" value="获取" /> 
				<input id="btn2" name="btn2" type="button" onclick="javascript:void(0);" value="推送" /> 
				<input id="btn3" name="btn3" type="button" onclick="javascript:void(0);" value="移动" /> 
				<input id="btn4" name="btn4" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" /> 
				<input id="btn5" name="btn5" type="button" onclick="cancleInfo()" value="撤销" /> 
				<input id="btn6" name="btn6" type="button" onclick="javascript:void(0);" value="生成静态页" /> 
			</td> 
		</tr> 
	</table>
<%--	<table class="table_option" border="0" cellpadding="0" cellspacing="0">--%>
<%--		<tr>		--%>
<%--			<td align="left" valign="middle" id="dept_search" class="search_td fromTabs" >--%>
<%--				<input id="btn1" name="btn1" type="button" onclick="openAddInfoPage();" value="添加" />--%>
<%--				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'info_id','openUpdateInfoDataPage()');" value="修改" />--%>
<%--				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />			--%>
<%--				&nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--				<input id="btn3" name="btn3" type="button" onclick="publishInfo()" value="发布" />--%>
<%--				<input id="btn3" name="btn3" type="button" onclick="cancleInfo()" value="撤销" />--%>
<%--				<input id="btn3" name="btn3" type="button" onclick="backInfo()" value="归档" />--%>
<%--				<span class="blank3"></span>--%>
<%--			</td>		--%>
<%--		</tr>--%>
<%--   </table>	--%>
</div>
</body>
</html>