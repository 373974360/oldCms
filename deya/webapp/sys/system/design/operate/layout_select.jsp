<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String app_id = request.getParameter("app_id");
	String site_id = request.getParameter("site_id");
	String handl_name = request.getParameter("handl_name");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择布局</title>


<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript">


var position = "${param.position}";
var DesignRPC = jsonrpc.DesignRPC;

$(document).ready(function(){
	initButtomStyle();
	loadLayoutList();
	init_input();
});

function loadLayoutList()
{
	var m = new Map();
	m.put("start_num", 0);	
	m.put("page_size", 20);

	var beanList = DesignRPC.getDesignLayoutList(m);
	beanList = List.toJSList(beanList);
	for(var i=0;i<beanList.size();i++)
	{
		var tmpPicLi = "<li><div style='width:100%;text-align:ceter'><img src='"+beanList.get(i).thumb_url+"' width='70px' height='80px' align='center'/></div>";

		tmpPicLi += "<div><input id='layout_radio' name='layout_radio' type='radio' onclick='selctLayout("+beanList.get(i).layout_id+")'/><label>"+beanList.get(i).layout_name+"</label></div></li>";

		$("#layoutList").append(tmpPicLi);
	}
}

function selctLayout(id)
{
	var layout_code = DesignRPC.getDesignLayoutBean(id).layout_content;
	top.insertLayoutHandl(layout_code,position);
	top.CloseModalWindow();
}

</script>
</head>

<body>
<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:98%">
	<tbody>
		<tr>			
			<td align="center" >
				<div style="width:100%;height:485px;overflow:auto;background:#FFFFFF;" class="border_color">
					<ul id="layoutList" class="imgList">
					</ul>
				</div>
			</td>
		</tr>		
	</tbody>
</table>
<span class="blank12"></span>
<!--  
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="returnRoleID()" value="保存" />
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
-->
</body>
</html>