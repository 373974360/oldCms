<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>域名管理</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/domainList.js"></script>
<script type="text/javascript">

var site_id = "${param.site_id}"; 
var domain_id = "${param.domain_id}"; 
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
    
	if(domain_id != "" && domain_id != "null" && domain_id != null)
	{
		defaultBean = SiteDomainRPC.getSiteDomainBeanByID(domain_id);
		$("#site_table").autoFill(defaultBean);	
		$("#addButton").click(updateSiteDomain);
	}else
	{		
		$("#addButton").click(insertSiteDomain);
	}
});

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="site_table" class="table_form" border="0" cellpadding="0" cellspacing="0">  
	<tbody>		
		<tr>
			<th><span class="f_red">*</span>站点域名：</th>
			<td >
				<input id="site_domain" name="site_domain" type="text" class="width300" maxlength="50" onblur="checkInputValue('site_domain',false,50,'站点域名','checkDomain')"/>
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
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('site_table',site_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
