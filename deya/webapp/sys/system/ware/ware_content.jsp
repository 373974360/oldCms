<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String site_id = request.getParameter("site_id");
	site_id = (site_id==null) ? "" : site_id;
	
	String ware_id = request.getParameter("wareID");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>标签内容修改</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/easyui.css">
<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/wareContent.js"></script>
<script type="text/javascript">
	var res_type = "resouce";//资源少类型，作用于编辑器中的附件上传，有此标识的，取的资源都是ROOT目录下的，而不是素材库中的
	var winfos_map = new Map();
	var ware_id = "<%=ware_id%>";
	var site_id = "<%=site_id%>";
	var app_id = "cms";
	var ware_info_list;
	var val = new Validator();
    var contentId = "ware_content";
	var defaultBean;
	$(document).ready(function(){
        initUeditor(contentId);
		initButtomStyle();		
		init_input();
		if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
		
		
		initPageWare();
		Init_InfoTable("ware_info_table");
		initTitleClick();//初始小菜单事件
	});
</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<div id="div_talbe_1">
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>	
		<tr>			
			<td >
                <script id="ware_content" type="text/plain" style="width:98%;height:420px;"></script>
			</td>
		</tr>		
	</tbody>
</table>
<span class="blank3"></span>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>			
		<tr>
		   <td>宽度：<span id="ware_width"></span></td>
		</tr>
		<tr>
		   <td>备注：<span id="ware_memo"></span></td>
		</tr>
	</tbody>
</table>
</div>
<div id="div_talbe_2">
<table id="ware_info_table" class="table_border odd_even_list" border="0" cellpadding="0" cellspacing="0">
	<thead>
	  <tr>
		<td width="30px" style="text-align:center">行号</td>
		<td style="text-align:center">内容</td>
		<td width="100px" style="text-align:center">操作</td>
	  </tr>
	</thead>
    <tbody id="ware_info_list">	
	</tbody>
	<tfoot><tr><td colspan="3"></td></tr></tfoot>
</table>
</div>

<!--隔线开始-->
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<!--隔线结束-->
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="savaBtn" name="btn1" type="button" class="simpl_button" onclick="javascript:void(0);" value="保存" />
			<input id="userAddReset" name="btn1" type="button" class="simpl_button" onclick="window.location.reload()" value="重置" />

			<input name="btn1" class="ware_info_button hidden" type="button" onclick="createWarePage()" value="生成页面" />
			<input name="btn1" class="ware_info_button hidden" type="button" onclick="sortWareInfo()" value="保存排序" />
			<input name="btn1" class="ware_info_button hidden" type="button" onclick="addWareInfo()" value="新建行" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>

<!-- 小菜单区域　开始 -->
<div id="mouse_menu" class="easyui-menu" style="width:120px;height:130px">
		<div onclick="javascript:openView()">查看</div>
		<div onclick="javascript:updateWareInfos()">编辑</div>
		<div onclick="javascript:changeWareInfos()">替换</div>
		<div onclick="javascript:deleteWareInfos()">删除</div>
		<div onclick="javascript:moveWareInfos('left')">左移</div>
		<div onclick="javascript:moveWareInfos('right')">右移</div>
		<div class="menu-sep"></div>
		<div>关闭</div>
	</div>
<div id="ware_memo2_div" class="hidden">备注：<span id="ware_memo2"></span></div>
<!-- 小菜单区域　结束 -->
</body>
</html>
