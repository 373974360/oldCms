<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<meta name="generator" content="featon-Builder" />
<meta name="author" content="featon" />
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="/wcm.files/js/jwplayer/jwplayer.js"></script>
<script type="text/javascript" src="js/zjzfList.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	<!--
var topnum = request.getParameter("topnum");
var id = request.getParameter("id");

var defaultBean;
$(document).ready(function () {		
	
	initButtomStyle();
	init_input();
	
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
				
	if(id != null && id.trim() != "")
	{
		defaultBean = ZJZFRPC.getGongMinBean(id);

		if(defaultBean)
		{
			$("#picviewdiv").autoFill(defaultBean);	
			
		}		
	}
	
}); 

	//-->
	</SCRIPT>	
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<div id="picviewdiv">

<table id="gq_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>姓名：</th>
			<td id="name">				
			</td>
		</tr>		
		<tr>
			<th>性别：</th>
			<td id="xb">				
				
			</td>
		</tr>		
		<tr>
			<th>出生年月：</th>
			<td id="csny">
			</td>
		</tr>
		<tr>
			<th >政治面貌：</th>
			<td id="zzmm">
				
			</td>
		</tr>	
		

		<tr>
			<th >身份证号码：</th>
			<td id="card">
				
			</td>
		</tr>
		<tr>
			<th >工作单位：</th>
			<td id="gzdw">
				
			</td>
		</tr>
		<tr>
			<th >职务：</th>
			<td id="zhiwu">
				
			</td>
		</tr>
		<tr>
			<th >职称：</th>
			<td id="zhicheng">
				
			</td>
		</tr>
		<tr>
			<th >户口所在地：</th>
			<td id="hkszd">
				
			</td>
		</tr>
		<tr>
			<th >手机号码：</th>
			<td id="phone">
				
			</td>
		</tr>
		<tr>
			<th >固定电话：</th>
			<td id="tel">
				
			</td>
		</tr>

		<tr>
			<th >常住地址：</th>
			<td id="address">
				
			</td>
		</tr>
		<tr>
			<th >通信地址：</th>
			<td id="txdz">
				
			</td>
		</tr>
		<tr>
			<th >邮编：</th>
			<td id="postcode">
				
			</td>
		</tr>
		<tr>
			<th >电子邮箱：</th>
			<td id="email">
				
			</td>
		</tr>
	</tbody>
</table>
</div>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="userAddCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex);" value="关闭" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>

</form>
</body>
</html>
