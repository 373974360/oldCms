<%@ page contentType="text/html; charset=utf-8"%>
<%
	String siteid = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>信息管理</title>
	<link type="text/css" rel="stylesheet" href="../../styles/themes/default/tree.css" />
	<link type="text/css" rel="stylesheet" href="../../styles/sq.css" />
	<jsp:include page="../../include/include_tools.jsp"/>
	<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../../js/indexjs/indexList.js"></script>
	<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
	<script type="text/javascript" src="js/info.js?v=20180117"></script>
	<script type="text/javascript">
        var site_id = "<%=siteid%>";
        var app = "cms";
        var opt_ids = ","+top.getOptIDSByUser(app,site_id)+",";//登录人所拥有管理权限ID
		$(function(){
            setUserOperate();
            initButtomStyle();
            reloadInfoDataList();
		});

        function setUserOperate()
        {
            $("#btn536").hide();
            $("#btn538").hide();
            $("#btn539").hide();
            $("#btn540").hide();
            //判断是否是站点管理员或超级管理员
            if(top.isSiteManager(app,site_id))
            {
                $("#btn536").show();
                $("#btn538").show();
                $("#btn539").show();
                $("#btn540").show();
            }else{
                if(opt_ids.indexOf(",536,") > -1){
                    $("#btn536").show();
                }
                if(opt_ids.indexOf(",538,") > -1){
                    $("#btn538").show();
                }
                if(opt_ids.indexOf(",539,") > -1){
                    $("#btn539").show();
                }
                if(opt_ids.indexOf(",540,") > -1){
                    $("#btn540").show();
                }
			}
        }
	</script>
</head>
<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>
			<td align="left" width="80">
				<input type="button" class="x_add" id="btn536" style="cursor: pointer;" onclick="openAddInfoPage()"/>
			</td>
			<td align="left" valign="middle" >
				<input id="searchkey" type="text" class="input_text" style="width:240px;" value=""/>
				<select id="orderByFields" class="input_select">
					<option selected="selected" value="1">时间倒序</option>
					<option value="2">时间正序</option>
				</select>
				<input id="btn" type="button" value="搜索" onclick="searchInfo()"/>
			</td>
		</tr>
		<tr style="height:10px; line-height:10px;overflow:hidden"><td  colspan="3" style="height:10px; line-height:10px;overflow:hidden"></td></tr>
		<tr>
			<td align="left" width="" colspan="3">
				<ul class="fromTabs">
					<li class="list_tab list_tab_cur">
						<div class="tab_left">
							<div class="tab_right">未采用</div>
						</div>
					</li>
					<li class="list_tab">
						<div class="tab_left">
							<div class="tab_right">已采用</div>
						</div>
					</li>
					<li class="list_tab">
						<div class="tab_left">
							<div class="tab_right">已退稿</div>
						</div>
					</li>
				</ul>
			</td>
		</tr>
	</table>
	<span class="blank3"></span>
	<div id="table"></div>
	<div id="turn"></div>
	<div class="infoListTable" id="listTable_0">
		<table class="table_option" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left" valign="middle">
					<input id="btn539" name="btn1" type="button" value="采用"/>
					<input id="btn540" name="btn2" type="button" value="退稿"/>
					<input id="btn538" name="btn3" type="button" value="删除"/>
				</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>