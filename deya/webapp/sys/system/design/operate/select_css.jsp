<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择主题风格</title>


<link rel="stylesheet" type="text/css" href="/sys/styles/themes/default/tree.css">
<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript" src="/sys/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/sys/js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/design_util.js"></script>
<script type="text/javascript">
var DesignRPC = jsonrpc.DesignRPC;
var css_id="${param.css_id}";

$(document).ready(function(){
	initButtomStyle();
	init_input();
	$("label").live("click",function(){
		$(this).prev().click();
	});
	getCssList();
	
});

function getCssList()
{	
	var m = new Map();
	m.put("start_num", 0);	
	m.put("page_size", 20);

	var cssList = DesignRPC.getDesignCssList(m);
	cssList = List.toJSList(cssList);
	for(var i=0;i<cssList.size();i++)
	{
		var tmpPicLi = "<li><div style='width:125px;height:130px;padding:0px 5px'><div style='width:100%;text-align:ceter'><img src='"+cssList.get(i).thumb_url+"' width='120px' height='100px' align='center'/></div>";

		tmpPicLi += "<div><input id='css_radio' name='css_radio' type='radio' value='"+cssList.get(i).css_id+"'";
		if(cssList.get(i).css_id == css_id)	
			tmpPicLi += "checked='checked'";
		tmpPicLi +="/><label>"+cssList.get(i).css_name+"</label></div></li>";

		$("#cssList").append(tmpPicLi);
	}
	
	init_input();
}

function returnValues()
{
	top.loadCssObject($(":checked").val());
	top.CloseModalWindow();
}
</script>
</head>

<body>
<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:98%">
	<tbody>		
		<tr>
			<td colspan="2" height="410px">
				<div id="css_div" style="width:100%;height:410px;overflow:auto;background:#FFFFFF;" class="border_color">
					<ul id="cssList" class="imgList">
					</ul>
				</div>				
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
			<input id="addButton" name="btn1" type="button" onclick="returnValues()" value="确定" />			
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>

</form>
</body>
</html>
