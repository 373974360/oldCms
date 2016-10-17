<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>节假日信息维护</title>

<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="js/snippetList.js"></script>
<script type="text/javascript"  src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
<script type="text/javascript">

 
var app = top.getCurrentFrameObj().app;
var site_id = top.getCurrentFrameObj().site_id;

var id = "<%=request.getParameter("id")%>";
var type = "<%=request.getParameter("type")%>";

var defaultBean;
$(document).ready(function(){

	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	
	//得到所属应用
	//getAppInfo();
	
	if(id != "" && id != "null" && id != null)
	{
	    defaultBean = SnippetRPC.getSnippetBean(id);
	    if(defaultBean)
		{
			$("#app_snippet_table").autoFill(defaultBean);
		}
		$("#saveBtn").click(updateSnippet);
	}
	else
	{
	    $("#app_id").val(app);
	    $("#site_id").val(site_id);
		$("#saveBtn").click(addSnippet);
	}
	
	function getAppInfo()
    {
		var appList = SnippetRPC.getAppList();
		appList = List.toJSList(appList);
		$("#app_id").addOptions(appList,"app_id","app_name");
    }
});
</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="app_snippet_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th ><span class="f_red">*</span>片段名称：</th>
			<td class="width250">
				<input id="sni_name" class="width300" name="sni_name" type="text" style="cursor: pointer;" value="" onblur="checkInputValue('sni_name',false,60,'片段名称','')"/>
			</td>
		</tr>
		<tr>
			<th >片段内容：</th>
			<td>
				<textarea id="sni_content" style="height: 100px" class="width300" name="sni_content" ></textarea>
			</td>
		</tr>
		<tr>
			<th style="display: none;">所属应用：</th>
			<td style="display: none;">
				<!-- select id="app_id" name="app_id" class="width305"></select> -->
			    <input id="app_id" name="app_id" value="" />
			</td>			
		</tr>
		<tr>
			<th style="display: none;">站点ID：</th>
			<td class="width250" style="display: none;">
				<input id="site_id" class="width300" name="site_id" type="text" style="cursor: pointer;" value="" />
			</td>
		</tr>
	</tbody>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="saveBtn" name="saveBtn" type="button" onclick="" value="保存" />
			<input id="resetBtn" name="resetBtn" type="button" onclick="form1.reset()" value="重置" />
			<input id="viewCancel" name="btn1" type="button" onclick="top.CloseModalWindow()" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>