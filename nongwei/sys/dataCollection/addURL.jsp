<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String hand_name = request.getParameter("hand_name");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>  
<title>添加采集网址</title>  
<style type="text/css"> 
</style>
<link type="text/css" rel="stylesheet" href="/sys/styles/main.css" />
<link type="text/css" rel="stylesheet" href="/sys/styles/sub.css" />
<script type="text/javascript" src="/sys/js/jquery.js"></script>

<script type="text/javascript" src="js/pageNumRule.js"></script>
<script type="text/javascript">
var hand_name = "<%=hand_name%>";

//添加URL
function returnStartURL()
{
	var saveURL = $("#save_collURL").val();
	if(saveURL==null || saveURL==""){
		top.msgAlert("请先点击\"添加按钮\"完成连接地址格式化!");
		return;
	}
	eval("top.getCurrentFrameObj()."+hand_name+"('"+saveURL+"','addURL')");
	top.CloseModalWindow();
}

</script>
</head>
  
<body>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>		
		<td class="fromTabs">
			<input type="radio" name="chooseAddUrl" class="input_radio" checked="checked" value="0" onclick="showChooseAddUrlDiv(this.value)" >单条网址
			<input type="radio" name="chooseAddUrl" class="input_radio" value="1" onclick="showChooseAddUrlDiv(this.value)" >批量/多页				
			<span class="blank3"></span>
		</td>	
	</tr>
</table>

<div id="oneURLDiv" style="display:block;">
	<table class="table_form">
		<tr><th>单条网址添加</th></tr>
		<tr>
			<th>起始网址:</th>
			<td>
				<input type="text" value="http://" class="width450 input_text" id="addurlByOne" name="addurl" /> &nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" id="addOneCollURL" name="addOneCollURL" value="添加" onclick="addOneURL()" />
		    </td>
		</tr>
	</table>
</div>

<div id="manyURLDiv" style="display:none;">
	<table class="table_form">
		<tr>
			<td style="text-align:right; width:20%;">地址格式:</td>
			<td colspan="3" style="width:80%;">
				<input type="text" value="http://" class="width450 input_text" id="addurlByMany" name="addurl" />
				<input type="button" onclick="previewAddUrl()" value="(*)" style=" width:50px;" />
			</td>
		</tr>
		<tr>
			<td style="text-align:right;">
				<input type="radio" name="urlRuleradio" class="input_radio" checked="checked" value="0" onchange="getEqualGradeorRatio(this.value)"/>等差数列
			</td>
			<td colspan="3">
				首项<input type="text" id="firstTermBad" oninput="addURLInfotopreview()" class="input_text" value="1" size="10">
				项数<input type="text" id="TermNumBad" oninput="addURLInfotopreview()" class="input_text" value="3" size="10">
				公差<input type="text" id="toleranceBad" oninput="addURLInfotopreview()" class="input_text" value="1" size="10">
			</td>
		</tr>
		<tr>
			<td style="text-align:right;">
				<input type="radio" name="urlRuleradio" class="input_radio" value="1" onchange="getEqualGradeorRatio(this.value)"/>等比数列
			</td>
			<td colspan="3">
				首项<input type="text" id="firstTerm" oninput="addURLInfotopreview()" class="input_text" value="1" size="10">
				项数<input type="text" id="TermNum" oninput="addURLInfotopreview()" class="input_text" value="3" size="10">
				公比<input type="text" id="tolerance" oninput="addURLInfotopreview()" class="input_text" value="2" size="10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" id="addManyCollURL" name="addManyCollURL" value="添加" onclick="addManyURL()" />
			</td>
		</tr>
		<tr>
			<td style="text-align:center;"><span>预览</span></td>
		</tr>
		<tr>
			<td colspan="4" style="text-align:center;">
				<div style="display:block;border:1px solid #ccc; margin:0 auto; width:600px; height:150px;overflow-y:auto;" id="previewURL">
				</div>
			</td>
		</tr>
	</table>
</div>
<div id="saveURLDiv" align="center">
	<textarea id="save_collURL" name="save_collURL" style="width:600px;height:100px;resize:none;"></textarea>
</div>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option">
	<tr>
		<td colspan="2" align="center">
			<input type="button" id="addURLBtn" value="完成" width="20px;" onclick="returnStartURL()"  />
			<input type="button" id="cancelBtn" value="取消" width="20px;" onclick="top.CloseModalWindow();"  />
		</td>
	</tr>
</table>
</body>
</html>