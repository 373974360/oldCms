<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String site_id = request.getParameter("site_id");
	String cat_id = request.getParameter("cat_id");
	if(cat_id == null || "".equals(cat_id) || "null".equals(cat_id))
		cat_id = "0";
	String cat_type = request.getParameter("cat_type");
	if(cat_type == null || "".equals(cat_type) || "null".equals(cat_type))
		cat_type = "0";
	if(site_id == null || "".equals(site_id) || "null".equals(site_id))
		site_id = "";
%>
<html>
<head>
<title>数据采集-添加规则</title>
<style type="text/css">
.hover{ cursor: default;}	 
</style>
<link rel="stylesheet" type="text/css" href="../styles/themes/default/tree.css">
<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/add_rule.js"></script>
<script type="text/javascript" src="../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
var tab_index = "${param.tab_index}";
var rcat_id =  "${param.rcat_id}";
var type = "${param.type}";
var id = "${param.id}";
var val=new Validator();
var site_id = "<%=site_id%>";
var cat_id = "<%=cat_id%>";
var cat_type = "<%=cat_type%>";
var jsonData;
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	init_FromTabsStyle();
	
	if(id != "" && id != "null" && id != null)
	{
		defaultBean = CollectionDataRPC.getCollRuleBeanByID(id);
		if(defaultBean)
		{
			$("#title").val(defaultBean.title);
//			$("#stop_time").val(defaultBean.stop_time);
			$("#infotitle_start").val(defaultBean.infotitle_start);
			$("#infotitle_end").val(defaultBean.infotitle_end);
			$("#listUrl_start").val(defaultBean.listUrl_start);
			$("#listUrl_end").val(defaultBean.listUrl_end);
			$("#content_start").val(defaultBean.content_start);
			$("#content_end").val(defaultBean.content_end);
			$("#author_start").val(defaultBean.author_start);
			$("#author_end").val(defaultBean.author_end);
			$("#addTime_start").val(defaultBean.addTime_start);
			$("#addTime_end").val(defaultBean.addTime_end);
			$("#source_start").val(defaultBean.source_start);
			$("#source_end").val(defaultBean.source_end);
			$("#keywords_start").val(defaultBean.keywords_start);
			$("#keywords_end").val(defaultBean.keywords_end);
			$("#catidhidden").val(defaultBean.cat_id);
			$("#cat_cname").val(defaultBean.cat_name);

//			$("#coll_interval").val(defaultBean.coll_interval);
			if(defaultBean.coll_interval!=null&&defaultBean.coll_interval!="0"&&defaultBean.coll_interval!=""){
				$("input[name='coll_intervalBox']").attr("checked",true); 
			}
			
			if(defaultBean.coll_url != "")
			{
				appendCollURL();
			}
			$("input[type=radio][value="+defaultBean.pic_isGet+"]").attr("checked",'checked');
			$("select[name=pageEncoding] option[value="+defaultBean.pageEncoding+"]").attr("selected","selected");
			$("select[name=model_id] option[value="+defaultBean.model_id+"]").attr("selected","selected");
//			$("select[name=timeFormatType] option[value="+defaultBean.timeFormatType+"]").attr("selected","selected");  
		}
		$("#addButton").click(updateCollectionRule);	
	}else{
		$("#addButton").click(addCollectionRule);
	}
});

function appendCollURL()
{
	var table = document.getElementById("urlTab");
	if(defaultBean.coll_url.indexOf("$")>0)
	{
		var arrURL = defaultBean.coll_url.split("$");
		for(var i=0;i<arrURL.length;i++)
		{
			var MaxRowIndex = table.rows.length;  //获取最大行的索引值
			var	rowId = "row_"+MaxRowIndex;
			var htmlStr = "<tr id='"+rowId+"' class=\"hover\">";
				htmlStr += "<td style='width:10%'>"+arrURL[i]+"</td>";
				htmlStr += "</tr>";
			$("#urlTab").append(htmlStr);
		}
	}else{
		var MaxRowIndex = table.rows.length;  //获取最大行的索引值
		var	rowId = "row_"+MaxRowIndex;
		var htmlStr = "<tr id='"+rowId+"' class=\"hover\">";
			htmlStr += "<td style='width:10%'>"+defaultBean.coll_url+"</td>";
			htmlStr += "</tr>";
		$("#urlTab").append(htmlStr);
	}
}

function setCatName(cat_id)
{
	if(cat_id != null && cat_id != "")
	{
		var bean = CategoryRPC.getCategoryBean(cat_id);
		if(bean)
		{
			$("#cat_cname").val(bean.cat_cname);
		}
		else
		{
			$("#cat_cname").val("");
		}
	}
	else
	{
		$("#cat_cname").val("");
	}
}
function initHandName()
{
	addStartURL("saveStartURL");
}

</script> 
</head>
  
<body>
<form id="form1" name="form1" action="#" method="post">
<table class="table_form" id="collRuleTab">
	<tr>
		<th style="width:10%"><span style="color: red">*</span>选择内容模型:</th>
		<td colspan="3" style="width:90%" >
			<select id="model_id" name="model_id">
				<option value="11" selected="selected">新闻模型</option>
				<option value="14">公开模型</option>
			</select>
		</td>
 	</tr>
 	<tr>
		<th style="width:10%"><span style="color: red">*</span>采集名称:</th>
		<td style="width:10%" ><input type="text" class="input_text" id="title" name="title" value="" onblur="checkInputValue('title',false,200,'采集名称','')" /></td>
		<th style="width:10%"><span style="color: red">*</span>栏目ID:</th>
		<td style="width:70%">
			<input type="text" id="catidhidden" name="catidhidden" value="" onblur="setCatName(this.value)"/>
			栏目名称：<input type="text" id="cat_cname" name="cat_cname" value="" readonly="readonly" />
		</td>
 	</tr>
 	<tr>
		<th style="width:10%"><span style="color: red">*</span>页面编码:</th>
		<td style="width:10%" >
			<select name="pageEncoding" id="pageEncoding">
				<option value="utf-8" selected="selected">utf-8</option>
				<option value='gb2312'>gb2312</option>
				<option value='GBK'>GBK</option>
	 		</select>
		</td>
		<th style="width:10%"><span style="color: red">*</span>是否采集图片:</th>
		<td style="font-size:13px;color:#32609E; width:25%">
			<input type="radio" name="isCollectImg"  value="0" checked="checked" />否
			<input type="radio" name="isCollectImg"  value="1" />是&nbsp;&nbsp;
			<span>(采集图片会影响采集速度)</span>
		</td>
	</tr>

	<tr>
		<th><span style="color: red">*</span>采集地址:</th>
		<td colspan="3" >
			<div style="overflow-y:auto; overflow-x:auto; width:600px; height:90px; border:1px solid #ccc;" id="coll_url">
				<table id="urlTab" style="margin:7px;" onclick="selRow(event)">
					<tbody>
					</tbody>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<th><span style="color: red">*</span>列表页地址规则:</th>
		<td colspan="3">
			<textarea style="width:300px; height:40px; resize:none;overflow-y:visible;" id="listUrl_start" name="listUrl_start" class="input_textarea"></textarea>
			<textarea style="width:300px; height:40px; resize:none;overflow-y:visible;" id="listUrl_end" name="listUrl_end" class="input_textarea"></textarea>
		</td>
	</tr>
	<tr>
		<th></th>
		<td style="width:10%" colspan="3">
			<input type="button" name="btn1" value="添加" onclick="initHandName()" >
			<input type="button" name="btn1" value="删除" onclick="{if(confirm('是否删除此条记录?')){delRow()}}" >
			<input type="button" name="btn1" value="修改" onclick="UpdateStartURL()" >
			<input type="button" name="btn1" value="清空" onclick="{if(confirm('是否清空记录?')){emptyurlTab()}}">
			<input type="button" name="btn1" value="测试采集地址" onclick="checkCollUrl()">
		    (一个地址一行)
		</td>
	</tr>
	<tr>
		<th>采集内容规则:</th>
		<td colspan="3">
			<table style="margin:6px;" width="35%">
				<tr>
					<td style="font-size:13px;color:#32609E; text-align:left; width:15%">标签名</td>
					<td style="font-size:13px;color:#32609E; text-align:center; width:20%">开始标签</td>
					<td style="font-size:13px;color:#32609E; text-align:center; width:20%">结束标签</td>
				</tr>
				<tr>
					<th style="text-align:left">标&nbsp;&nbsp;&nbsp;题:</th>
					<td><textarea style="width:300px; height:40px; resize:none;overflow-y:visible;" id="infotitle_start" name="infotitle_start" class="input_textarea" onblur="checkInputValue('infotitle_start',false,200,'标题开始规则','')"></textarea></td>
					<td><textarea style="width:300px; height:40px; resize:none;overflow-y:visible;" id="infotitle_end" name="infotitle_end" class="input_textarea" onblur="checkInputValue('infotitle_end',false,200,'标题结束规则','')"></textarea></td>
				</tr>
				<tr>
					<th style="text-align:left">内&nbsp;&nbsp;&nbsp;容:</th>
					<td><textarea style="width:300px; height:40px; resize:none;overflow-y:visible;" id="content_start" name="content_start" class="input_textarea" onblur="checkInputValue('content_start',false,200,'内容开始规则','')"></textarea></td>
					<td><textarea style="width:300px; height:40px; resize:none;overflow-y:visible;" id="content_end" name="content_end" class="input_textarea" onblur="checkInputValue('content_end',false,200,'内容结束规则','')"></textarea></td>
				</tr>
				<tr>
					<th style="text-align:left">作&nbsp;&nbsp;&nbsp;者:</th>
					<td><textarea style="width:300px; height:40px; resize:none;overflow-y:visible;" id="author_start" name="author_start" class="input_textarea"></textarea></td>
					<td><textarea style="width:300px; height:40px; resize:none;overflow-y:visible;" id="author_end" name="author_end" class="input_textarea"></textarea></td>
				</tr>
				<tr>
					<th style="text-align:left">时&nbsp;&nbsp;&nbsp;间:</th>
					<td><textarea style="width:300px; height:40px; resize:none;overflow-y:visible;" id="addTime_start" name="addTime_start" class="input_textarea"></textarea></td>
					<td><textarea style="width:300px; height:40px; resize:none;overflow-y:visible;" id="addTime_end" name="addTime_end" class="input_textarea"></textarea></td>
				</tr>
				<tr>
					<th style="text-align:left">来&nbsp;&nbsp;&nbsp;源:</th>
					<td><textarea style="width:300px; height:40px; resize:none;overflow-y:visible;" id="source_start" name="source_start" class="input_textarea"></textarea></td>
					<td><textarea style="width:300px; height:40px; resize:none;overflow-y:visible;" id="source_end" name="source_end" class="input_textarea"></textarea></td>
				</tr>
				<tr>
					<th style="text-align:left">关键字:</th>
					<td><textarea style="width:300px; height:40px; resize:none;overflow-y:visible;" id="keywords_start" name="keywords_start" class="input_textarea"></textarea></td>
					<td><textarea style="width:300px; height:40px; resize:none;overflow-y:visible;" id="keywords_end" name="keywords_end" class="input_textarea"></textarea></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<th>定时采集:</th>
		<td style="width:20%">
			<%--<input type="text" id="coll_interval" name="coll_interval" size="11" class="input_text" value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'HH'})" readonly="true">(整点)--%>
			<input type="checkbox" id="coll_interval" name="coll_intervalBox" value="0" />是否定时采集(默认零点采集)
		</td>
	</tr>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" type="button" name="btn1" onclick="" value="保存" />
			<input id="userAddReset" type="reset" name="btn1" value="重置" />
			<input id="userAddCancel" type="button" name="btn1" onclick="top.tab_colseOnclick(top.curTabIndex);" value="取消">
		</td>
	</tr>
</table>
</form>
</body>
</html>