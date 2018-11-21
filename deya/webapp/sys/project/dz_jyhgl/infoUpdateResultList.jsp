<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>内容更新规则</title>
<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="js/infoUpdateResultList.js"></script>
<script type="text/javascript">
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
    loadSite();
	initTable();
	reloadDataList();
});
</script>
</head>
<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>
			<td align="left" width="180">
				<select id="site_id" class="input_select" style="width:200px;" onchange="reloadDataList()">
					<option value="">全部站点</option>
				</select>
			</td>
			<td width="10"></td>
		</tr>
		<tr style="height:10px; line-height:10px;overflow:hidden"><td  colspan="3" style="height:10px; line-height:10px;overflow:hidden"></td></tr>
	</table>
<div id="table"></div>
</div>
</body>
</html>
