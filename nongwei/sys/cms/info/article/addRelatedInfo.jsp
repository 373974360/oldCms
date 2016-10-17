<%@ page contentType="text/html; charset=utf-8"%>
<%
String id = request.getParameter("id");
String siteid = request.getParameter("site_id");
String infoid = request.getParameter("info_id");

if(id == null || id.equals("null")){
	id = "0";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息维护</title>
<jsp:include page="../include/include_info_tools.jsp"/>
<script type="text/javascript">

var site_id = "<%=siteid%>";
var id = "<%=id%>";
var infoid = "<%=infoid%>";
var contetnid = "info_content";
var linksInfo = "";

$(document).ready(function(){
	initButtomStyle();
	init_input();
	
	$("#site_id").val(site_id);
	$("#addButton").click(addRelatedInfo);
	
});

function addRelatedInfo(){
	var relateBean = BeanUtil.getCopy(RelatedBean);	
	$("#info_related_table").autoBind(relateBean);
	
	if(infoid == null || infoid == "null" || infoid == ""){
		var tp = top.getCurrentFrameObj().getInfoGoble();
		if(tp == 0){
			tp = InfoBaseRPC.getInfoId();
			top.getCurrentFrameObj().setInfoGoble(tp);
		}
		relateBean.info_id = tp;
	}else{
		relateBean.info_id = infoid;
	}
	
	relateBean.related_info_id = InfoBaseRPC.getReleInfoID();
	
	if(InfoBaseRPC.addRelatedInfo(relateBean)){
		//top.msgAlert("添加成功");
		top.getCurrentFrameObj().showRelatedInfos(relateBean.info_id);
		top.CloseModalWindow();
	}else{
		top.msgAlert("添加失败");
	}
}

function updateRelatedInfo(){
	var relateBean = BeanUtil.getCopy(RelatedBean);	
	$("#info_related_table").autoBind(relateBean);
	if(InfoBaseRPC.updateRelatedInfo(relateBean)){
		//top.msgAlert("修改成功");
		top.CloseModalWindow();
	}else{
		top.msgAlert("修改失败");
	}
}

</script>
<style type="text/css">
.width300{width:300px;}
</style>
</head>

<body>
<span class="blank12"></span>
<form action="#" name="form1" >
<div id="info_related_table">
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>标题：</th>
			<td>
				<input id="title" name="title" type="text" class="width300" maxlength="80" value="" onblur="checkInputValue('title',false,160,'信息标题','')"/>
			</td>
		</tr>
		<tr>
			<th>连接：</th>
			<td>
				<input id="content_url" name="content_url" type="text" class="width300" maxlength="80" value="" />
			</td>
		</tr>
		<tr>
			<th>缩略图：</th>
			<td>
				<input id="thumb_url" name="thumb_url" type="text" class="width300" maxlength="80" value="" />
			</td>
		</tr>
	</tbody>
</table>
</div>
<input id="sort_id" name="sort_id" type="hidden" class="width300" maxlength="80" value="0" />
<input id="model_id" name="model_id" type="hidden" class="width300" maxlength="80" value="0" />

<!--隔线开始-->
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<!--隔线结束-->
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />
			<input id="addReset" name="btn1" type="button" onclick="formReSet('info_related_table',title)" value="重置" />
			<input id="addCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
</form>
<span class="blank3"></span>
</body>
</html>
