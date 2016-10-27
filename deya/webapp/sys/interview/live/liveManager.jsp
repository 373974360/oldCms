<%@ page contentType="text/html; charset=utf-8"%>
<%
String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title>访谈管理</title> 
	<jsp:include page="../../include/include_tools.jsp"/>
	<script src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>	
	<script type="text/javascript" src="js/liveManager.js"></script>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var site_id = "<%=site_id%>";
		var id = request.getParameter("id");
		var sub_id = request.getParameter("sub_id");
		var page_height = 600;
		var window_width = $(window).height();
		var site_id = parent.site_id;
		$(document).ready(function () {	
			page_height = document.body.clientHeight-55;

		    subjectBean = SubjectRPC.getSubjectObj(id);
			subjectCategory = SubjectRPC.getSubjectCategoryBeanByCID(subjectBean.category_id);

			init_FromTabsStyle();

			$("#zbgl_frame").attr("src","main.jsp?id="+id+"&sub_id="+sub_id);
			$("#zbgl_frame").attr("height",page_height);

			//为以免读出的数据太多，只加载第一个iframe的页面内容
			//$("#jctp_frame").attr("src","picManager.jsp?id="+id+"&sub_id="+sub_id);
			$("#ftsz_frame").attr("src","setManager.jsp?id="+id+"&sub_id="+sub_id);
			//$("#jygl_frame").attr("src","prohibitManager.jsp?id="+id+"&sub_id="+sub_id);
		}); 

		function setFrameHeight(wname,heig)
		{
			$("#"+wname).attr("height",heig);
			page_height = heig;
		}

	    function changeContentLable(obj)
		{	
			var c_id = $(obj).attr("id")+"_div";
			
			if(c_id == "jctp_div")
			{
				$("#jctp_frame").attr("height",page_height);

				if($("#jctp_frame").attr("src") == "")
					$("#jctp_frame").attr("src","picManager.jsp");
			}
			if(c_id == "ftsz_div")
			{
				$("#ftsz_frame").attr("height",page_height);
				if($("#ftsz_frame").attr("src") == "")
					$("#ftsz_frame").attr("src","setManager.jsp");
			}
			if(c_id == "jygl_div")
			{
				$("#jygl_frame").attr("height",page_height);
				if($("#jygl_frame").attr("src") == "")
					$("#jygl_frame").attr("src","prohibitManager.jsp");
			}
			if(c_id == "wyly_div")
			{
				$("#wyly_frame").attr("height",page_height);
				if($("#wyly_frame").attr("src") == "")
					$("#wyly_frame").attr("src","chatBrowse.jsp");
			}

			$("#content_area").find("DIV").hide();
			$("#"+c_id).show();
		}

		
	//-->
	</SCRIPT>
	
</head> 
<body> 	
<table class="table_option" border="0" cellpadding="0" cellspacing="0" align="left">
	<tr>
		<td align="left" class="fromTabs width10" style="">	
			
			<span class="blank3"></span>
		</td>
		<td align="left" width="100%">
			<ul class="fromTabs">
				<li class="list_tab list_tab_cur">
					<div class="tab_left">
						<div class="tab_right"  id="zbgl" onclick="changeContentLable(this)">直播管理</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right"  id="jctp" onclick="changeContentLable(this)">精彩图片</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right"  id="ftsz" onclick="changeContentLable(this)">访谈设置</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right"  id="jygl" onclick="changeContentLable(this)">禁言管理</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right"  id="wyly" onclick="changeContentLable(this)">网友留言</div>
					</div>
				</li>
			</ul>
		</td> 	
		
	</tr>
</table>
<span class="blank12"></span>
<div id="subject">
<div class="infoListTable" id="listTable_0">
<iframe id="zbgl_frame" name="zbgl_frame" src="" width="100%" height="563px" scrolling="no" frameborder="0"></iframe>
</div>

<div class="infoListTable hidden" id="listTable_1">
<iframe id="jctp_frame" name="jctp_frame" src="" width="100%" height="563px" scrolling="no" frameborder="0"></iframe>
</div>
<div class="infoListTable hidden" id="listTable_2">
<iframe id="ftsz_frame" name="ftsz_frame" src="" width="100%" height="563px" scrolling="no" frameborder="0"></iframe>
</div>
<div class="infoListTable hidden" id="listTable_3">
<iframe id="jygl_frame" name="jygl_frame" src="" width="100%" height="563px" scrolling="no" frameborder="0"></iframe>
</div>
<div class="infoListTable hidden" id="listTable_4">
<iframe id="wyly_frame" name="wyly_frame" src="" width="100%" height="563px" scrolling="no" frameborder="0"></iframe>
</div>
</div>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>

</body> 

</html> 

