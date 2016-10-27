<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看相关信息</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/releInfoList.js"></script>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var sub_id = request.getParameter("sub_id");
		if(sub_id == "" || sub_id == null)
		{
			window.close();
		}

		var defaultBean = subjectReleInfo;
		$(document).ready(function () {				
			initButtomStyle();
			init_input();
			var a_id = request.getParameter("id");
			
			if(a_id != null && a_id.trim() != "")
			{
				defaultBean = SubjectRPC.getSubReleInfo(a_id);

				if(defaultBean)
				{
					$("#subReleInfo").autoFill(defaultBean);
				}
				
				$("#subButton").click(updateReleInfo);
			}
			else
			{
				$("#subButton").click(saveReleInfo);
			}
		}); 
		
	//-->
	</SCRIPT>	
</head> 
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="subReleInfo" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>标题：</th>
			<td>
				<input id="info_name" name="info_name" type="text" class="width300" value="" />				
			</td>			
		</tr>  
		<tr>
			<th>类型：</th>
			<td>
				<input type="radio" id="info_type" name="info_type" value="html" checked="checked" onClick="changeInfo_Type('html')"><label>HTML内容</label>
			    <input type="radio" id="info_type" name="info_type" value="url" onClick="changeInfo_Type('url')" ><label>URL址址</label>
			</td>			
		</tr>
		<tr id="urlcontent" height="30" >
		  <th>URL地址：</th>
		  <td id="curl"></td>			 
		 </tr>
		 <tr id="htmlcontent" >
		  <th style="vertical-align:top;">内容：</th>
		  <td style="vertical-align:top;"><div id="content" style="width:98%;height:270px;overflow:auto;line-height:18px"></div></td>			 
		 </tr>
 </tbody>
</table>

<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="userAddButton" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="关闭" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>

</html> 

