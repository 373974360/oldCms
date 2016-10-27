<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
  <head>
    
    <title>审计日志列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<jsp:include page="../../include/include_tools.jsp"/>
	<script src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
	<script type="text/javascript" src="js/csLog.js"></script>
	<script type="text/javascript">

	var site_id = "";
	$(document).ready(function(){	
		initButtomStyle();
		init_FromTabsStyle();
		if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
			initTable();
			reloadLogSettingList();	
	});

	</script>
  </head>
  
  <body>
    <div>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
				<input class="Wdate width100" type="text" name="start_day" id="start_day" size="11" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="true" >&nbsp;-
				 <input class="Wdate width100" type="text" name="end_day" id="end_day" size="11" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="true" >
				<select id="searchFields" class="input_select width70" >
					<option value="user_name" selected="selected">用  户</option>
					<option value="audit_des">操  作</option>
					<option value="ip">IP</option>
				</select>
				<input id="searchkey" type="text" class="input_text" value=""  /><input id="btnSearch" type="button" class="btn x2" value="搜索" onclick="roleSearchHandl(this)"/>
				<select id="orderByFields" class="input_select" onchange="csLogSortHandl(this.value)">
					<option value="audit_time,desc" selected="selected">按时间倒序(默认)</option>
					<option value="audit_time,asc" >按时间顺序</option>
				</select>
				<span class="blank3"></span>
			</td>		
		</tr>
	</table>
	<span class="blank3"></span>
    <div id="table"></div>
	<div id="turn"></div>
		
    </div>
  </body>
</html>
