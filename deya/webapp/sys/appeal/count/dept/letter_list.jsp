<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String appId = request.getParameter("appId");
	String siteId = request.getParameter("site_id");
	
	   String s = (String)request.getParameter("s");
	   String e = (String)request.getParameter("e");
	   String b_ids = (String)request.getParameter("b_ids");
	   String dep_id = (String)request.getParameter("dep_id");
	   String type = (String)request.getParameter("type");

	   String user_id = (String)request.getParameter("user_id");

	   pageContext.setAttribute("s",s);
	   pageContext.setAttribute("e",e);
	   pageContext.setAttribute("b_ids",b_ids);
	   if(dep_id !=""){
		    pageContext.setAttribute("dep_id",dep_id);
	   }
	   
	   if(user_id !=""){
		    pageContext.setAttribute("user_id",user_id);
	   }
	   pageContext.setAttribute("type",type);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信件列表</title>


<jsp:include page="../../../include/include_tools.jsp" />
<script type="text/javascript" src="js/letter_list.js"></script>
<script type="text/javascript">
	var appId = "<%=appId%>";
	var siteId = "<%=siteId%>";

	var model_id = "${b_ids}";
	var s = "${s}";
	var e = "${e}";
	var do_dept = "${dep_id}";
	var sq_status = "${type}";
	var user_id = "${user_id}";
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	reloadList();

	  $('#btn306').click(function(){
		exportWordLists();
	});
});
function exportWordLists(){
	var url="";
	if(do_dept !="")
	{
		url ="exportWord.jsp?model_id="+model_id+"&s="+s+"&e="+e+"&do_dept="+do_dept+"&sq_status="+sq_status;  
	}else{
		url ="exportWord.jsp?model_id="+model_id+"&s="+s+"&e="+e+"&user_id="+user_id+"&sq_status="+sq_status;
	}
    window.location.href=url;
}
</script>
</head>
<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td align="left" valign="middle">
				<input id="btn306" name="btn6" type="button" onclick="" value="导出文档" />		
			</td>		
		</tr>
	</table>
<span class="blank3"></span>
<div id="table"></div>
<div id="turn"></div>
</div>
</body>
</html>