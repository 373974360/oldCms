<%@ page contentType="text/html; charset=utf-8"%>
<%
String app_id = request.getParameter("app_id");
if(app_id == null){
	app_id = "0";
}

String site_id = request.getParameter("site_id");
if(site_id == null){
	site_id = "0";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title> 附件设置</title>
	
	
	<jsp:include page="../../../include/include_tools.jsp"/>
	<script type="text/javascript" src="../../../js/uploadTools.js"></script>
	<script type="text/javascript" src="../../../js/jquery.uploadify.js"></script>
	<script type="text/javascript" src="../../../js/uploadFile/swfobject.js"></script>
	<script type="text/javascript" src="js/attachment.js">
</script>
<style rel="stylesheet" type="text/css">
.sq_box{border:0px solid #C5DDF1;}
.sq_title_box{ height:22px; font-weight:bold; padding-left:10px; line-height:22px; background:url(../../../images/sq_title_bg.gif) 0 -4px repeat-x; cursor:pointer;}
.sq_title{ width:300px; height:22px; float:left; font-weight:bold; color:#606AA3; padding-left:18px; line-height:22px;cursor:pointer;}
.sq_title_plus{ background:url(../../../images/sq_title_plus.gif)  0 -3px no-repeat;}
.sq_title_minus{ background:url(../../../images/sq_title_minus.gif)  0 -3px no-repeat;}
.sq_title_right{ width:120px; height:22px; line-height:22px; float:right;font-weight:bold; color:#606AA3; text-align:right; padding-right:10px; cursor:pointer;}
.sq_box_content{ padding:3px; text-align:left; padding-left:80px;}
</style>
		<script type="text/javascript">

var site_id = "<%=site_id%>";
var app_id = "<%=app_id%>";

$(document).ready(function(){
	initButtomStyle();
	init_input();
	initAttachConfig();
	$("#addButton").click(saveAttachConfig);
	publicUploadButtomLoad("uploadify",true,false,"",0,5,site_id,"saveWarePicUrl");

});

function saveWarePicUrl(url)
{
	if(url != "" && url != null)
	{
		//url = url.substring(7);
		$("#water_pic").val(url.substring(url.indexOf("/")));
	}
}
</script>
	</head>
	<body>
	<div style="text-align:left;">
		<form id="form1" name="form1" action="#" method="post">
		<input type="hidden" name="group" id="group" value="attachment" />
			<div class="sq_box">
				<div class="sq_title_box">
					<div class="sq_title sq_title_minus">基本设置</div>
				</div>
				<div class="sq_box_content">
					<table class="table_form" border="0"
						cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<th style="vertical-align:top;">允许上传的基本附件类型：</th>
								<td>									
									<textarea id="upload_allow" name="upload_allow" style="width:400px;height:80px">gif,jpg,jpeg,bmp,png,txt,zip,rar,doc,docx,xls,ppt,pdf,swf,flv,php</textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="sq_title_box">
					<div class="sq_title sq_title_minus">缩略图</div>
				</div>
				<div class="sq_box_content">
					<table class="table_form" border="0"
						cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<th >缩略图大小：</th>
								<td>
									<ul>
										<li><input id="thumb_width" name="thumb_width" type="text" value="100" onkeypress="var k=event.keyCode; return k>=48&&k<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" style="width:30px;ime-mode:Disabled"/>X<input id="thumb_height" name="thumb_height" type="text" value="100" onkeypress="var k=event.keyCode; return k>=48&&k<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" style="width:30px;ime-mode:Disabled"/></li>
									</ul>
								</td>
							</tr>
							<tr>
								<th >正文图片压缩：</th>
								<td>
									<ul>
										<li><input id="dd" name="is_compress" type="radio" value="1" class="input_checkbox" checked="checked"/><label for="dd">压缩</label></li>
										<li><input id="ee" name="is_compress" type="radio" value="0" class="input_checkbox" /><label for="ee">不压缩</label></li>
									</ul>
								</td>
							</tr>
							<tr>
								<th >正文图片大小：</th>
								<td>
									<ul>
										<li><input id="normal_width" name="normal_width" type="text" value="600" onkeypress="var k=event.keyCode; return k>=48&&k<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" style="width:30px;ime-mode:Disabled"/>X<input id="normal_height" name="normal_height" type="text" value="480" onkeypress="var k=event.keyCode; return k>=48&&k<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" style="width:30px;ime-mode:Disabled"/></li>
									</ul>
								</td>
							</tr>
							<tr class="hidden">
								<th >缩略图质量：</th>
								<td>
									<ul>
										<li><input id="thumb_quality" name="thumb_quality" type="text" value="100" style="width:30px;"/></li>
									</ul>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="sq_title_box">
					<div class="sq_title sq_title_minus">图片水印</div>
				</div>
				<div class="sq_box_content">
					<table class="table_form" border="0"
						cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<th >图片水印：</th>
								<td>
									<ul>
										<li><input id="d" name="watermark" type="radio" value="1" class="input_checkbox" checked="checked"/><label for="d">启用</label></li>
										<li><input id="e" name="watermark" type="radio" value="0" class="input_checkbox" /><label for="e">禁用</label></li>
									</ul>
								</td>
							</tr>
							<tr>
								<th style="vertical-align:middle;">水印位置：</th>
								<td>
									<input name="water_location" type="radio" value="1"/>#1&nbsp;&nbsp;<input name="water_location" type="radio" value="2"/>#2&nbsp;&nbsp;<input name="water_location" type="radio" value="3"/>#3<br />
									<input name="water_location" type="radio" value="4"/>#4&nbsp;&nbsp;<input name="water_location" type="radio" value="5"/>#5&nbsp;&nbsp;<input name="water_location" type="radio" value="6"/>#6<br />
									<input name="water_location" type="radio" value="7"/>#7&nbsp;&nbsp;<input name="water_location" type="radio" value="8"/>#8&nbsp;&nbsp;<input name="water_location" type="radio" value="9" checked="checked"/>#9<br />
								</td>
							</tr>
							<tr>
								<th >水印添加条件：</th>
								<td>
									<ul>
										<li><input id="water_width" name="water_width" type="text" value="300" class="input_checkbox" onkeypress="var k=event.keyCode; return k>=48&&k<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" style="width:30px;ime-mode:Disabled"/>X
										<input id="water_height" name="water_height" type="text" value="150" class="input_checkbox" onkeypress="var k=event.keyCode; return k>=48&&k<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" style="width:30px;ime-mode:Disabled"/></li>
									</ul>
								</td>
							</tr>
							<tr>
								<th >水印透明度：</th>
								<td>									
									<select id="water_transparent" style="width:50px">									  
									  <option value="0.1">10%</option>
									  <option value="0.2">20%</option>
									  <option value="0.3">30%</option>
									  <option value="0.4">40%</option>
									  <option value="0.5">50%</option>
									  <option value="0.6">60%</option>
									  <option value="0.7">70%</option>
									  <option value="0.8" selected="true">80%</option>
									  <option value="0.9">90%</option>
									  <option value="1.0">100%</option>
									</select>
								</td>
							</tr>
							<tr>
								<th >水印图片：</th>
								<td>
									<ul>
										<div style="float:left;margin:auto;"><input id="water_pic" name="water_pic" type="text" value="" class="input_checkbox" style="width:400px;" readOnly="readOnly"/></div>
										<div style="float:left"><input type="file" name="uploadify" id="uploadify"/></div>
									</ul>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<span class="blank12"></span>
			<div class="line2h"></div>
			<span class="blank3"></span>
			<table class="table_option" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td align="left" valign="middle" style="text-indent: 100px;">
						<input id="addButton" name="btn1" type="button" onclick=""
							value="保存" />
					</td>
				</tr>
			</table>
			<span class="blank3"></span>
		</form>
		</div>
	</body>
</html>