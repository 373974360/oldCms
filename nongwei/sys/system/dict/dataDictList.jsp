<%@ page contentType="text/html; charset=utf-8"%>
<%
String app_id = request.getParameter("app");
if(app_id == null){
	app_id = "0";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>数据字典</title>
		
		
		<jsp:include page="../../include/include_tools.jsp"/>
		<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css" />
		<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
		<script type="text/javascript" src="js/dict.js">
</script>
		<script type="text/javascript">

var site_id = "0";
var app = "<%=app_id%>";

var num = 0;
var selectFlag = false;
var dcFlag = false;
var isExistFlag = false;
var dc = "";
var submitFlag = true;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	iniLeftMenuTree();
	setLeftTreeHeight();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	$("#site_id").val(site_id);
	addDict();
	setTimeout('selectKZ()',200);
});

</script>
	</head>
	<body>
		<table class="table_option fromTabs" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="left" valign="middle">
					<input id="i006" name="i006" type="button" onclick="addNodeKZ()" value="添加字典项" />
					<input id="i006" name="i006" type="button" onclick="deleteDC()" value="删除" />
					<span class="blank3"></span>
				</td>
				<td align="right" valign="middle" id="user_search" class="search_td">
					<span class="blank3"></span>
				</td>
				<td style="width: 20px;"></td>
			</tr>
		</table>

		<table style="width: 100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="160px" valign="top">
					<div id="leftMenuBox">
						<div id="leftMenu" class="contentBox6 textLeft"
							style="overflow: auto">
							<ul id="leftMenuTree" class="easyui-tree" animate="true">
							</ul>
						</div>
					</div>
				</td>
				<td class="width10"></td>
				<td valign="top">
					<table id="dc_table" class="fromTabs table_form" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<th>
								<span class="f_red">*</span>字典名称：
							</th>
							<td colspan="3">
								<input id="dict_cat_name" name="dict_cat_name" type="text" class="width200" value=""   onblur="checkInputValue('dict_cat_name',false,30,'字典名称','')"/>
							</td>
						</tr>

						<tr>
							<th>
								<span class="f_red">*</span>字典代码：
							</th>
							<td colspan="3">
								<input id="dict_cat_id" name="dict_cat_id" type="text" class="width200" value=""   onblur="checkInputValue('dict_cat_id',false,30,'字典代码','');checkExist('dict_cat_id',this)"/>
								<span>系统唯一</span>
							</td>

						</tr>

						<tr>
							<th style="vertical-align: top;">
								字典描述：
							</th>
							<td colspan="3">
								<textarea id="dict_cat_memo" name="dict_cat_memo" style="width: 400px; height: 45px">
<%--该数据字典用于信息公开的公开范围选择。--%>
								</textarea>
							</td>
							<td>
								<input id="app_id" name="app_id" type="hidden" class="width200" value="<%=app_id%>"/>
								<input id="site_id" name="site_id" type="hidden" class="width200" value=""/>
								<input id="is_sys" name="is_sys" type="hidden" class="width200" value="0"/>
							</td>
						</tr>
						<tr>
							<td align="right" class="fromTabs width100" style="">
								<input id="btnAdd" name="btnAdd" type="button"
									onclick="javascript:addDict();" value="添加数据项" />
								<span class="blank3"></span>
							</td>
							<td align="left" valign="middle" class="fromTabs textIndent2em"
								colspan="3">
								注意：只能设置一个数据项为默认
								<span class="blank3"></span>
							</td>
						</tr>
						<span class="blank3"></span>
					</table>
					<div class="textLeft" style="padding-left: 10px;">
						<table id="add_zbstep_table" class="table_option"
							style="width: 500px; margin: 0 0;" border="0" cellpadding="0"
							cellspacing="0">
							<span class="blank3"></span>
							<tr>
								<td class="width40" style="height:18px;">
									序号
								</td>
								<td class="width200"  style="height:18px; padding-left:0px; width:205px;">
									数据项
								</td>
								<td class="width100" style="height:18px; padding-left:5px; width:105px;">
									编码
								</td>
								<td align="center" class="width40"  style="height:18px; padding-left:2px; width:40px;">
									默认
								</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							</tr>
						</table>
						<table id="dictData" class="table_option"
							style="width: 500px; margin: 0 0;" border="0" cellpadding="0"
							cellspacing="0">
						</table>
					</div>
					<!--隔线开始-->
					<span class="blank12"></span>
					<div class="line2h"></div>
					<span class="blank3"></span>
					<!--隔线结束-->
					<table class="table_option " border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="left" valign="middle" style="text-indent: 100px;">
								<input id="btn1" name="btn1" type="button"
									onclick="javascript:fnOK();" value="保存" />
							</td>
						</tr>
					</table>
					<span class="blank3"></span>
				</td>
			</tr>
		</table>
	</body>
</html>