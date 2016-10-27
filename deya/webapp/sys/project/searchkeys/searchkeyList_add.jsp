<%@page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/searchkeysList.js"></script>
<script type="text/javascript">
	var site_id = "${param.site_id}";
	var id = '${param.id}'; 
$(document).ready(function(){
	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	if(id){
        top.jsonrpc.SearchKeyRPC.getSearchKeyById(setDateResult,id);
	}else{
		$("#saveBtn").click(addData);
    }
});

function setDateResult(result,e){
    if(e != null){return;}
    bean = result;

    $(":radio[name='type'][value='"+bean.type+"']").attr("checked","checked");
    $("#form_table").autoFill(bean);
	$("#saveBtn").click(updateData);
}

</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="form_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>关键字：</th>
			<td >
				<input id="title" name="title" type="text" class="width300" value="" onblur="checkInputValue('title',false,60,'关键字','')"/>
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>所属应用：</th>
			<td >
				 <ul>
					 <li><input type="radio"  name="type" value="all" checked="checked"><label>全部</label></li>
					 <li><input type="radio"  name="type" value="info"><label>信息类</label></li>
					 <li><input type="radio"  name="type" value="zwgk"><label>公开类</label></li>
					 <li><input type="radio"  name="type" value="ggfw"><label>服务类</label></li>
				 </ul>
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
			<input id="viewCancel" name="btn1" type="button" onclick="top.CloseModalWindow()" value="关闭" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>