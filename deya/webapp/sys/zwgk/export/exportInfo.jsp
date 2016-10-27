<%@ page contentType="text/html; charset=utf-8"%>
<%
	String cat_type = request.getParameter("cat_type");
	if(cat_type == null || "".equals(cat_type) || "null".equals(cat_type))
		cat_type = "0";
	String site_id = request.getParameter("site_id");
	String top_index = request.getParameter("top_index");
	String class_id = request.getParameter("class_id");
	if(class_id == null || "".equals(class_id))
		class_id = "0";
	if(site_id == null || "".equals(site_id))
		site_id = "";
	
	String start_day = request.getParameter("start_day");
	String end_day = request.getParameter("end_day");

   String now = com.deya.util.DateUtil.getCurrentDate();
   String now1 = now.substring(0,7)+"-01";
   if(start_day == null || "".equals(start_day)) {
   		start_day = now1;
   }
   if(end_day == null || "".equals(end_day)) {
   		end_day = now;
   }
   
   String styleStr = "";
   if(!site_id.equals("")){
	   styleStr = "none";
   }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导出目录</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/exportInfo.js"></script>
<script type="text/javascript">
var cat_type = "<%=cat_type%>";//目录类型：0：普通栏目1：专题2：服务应用目录
var top_index = "<%=top_index%>";//父窗口ID
var class_id = "<%=class_id%>";//分类方式ID
var site_id = "<%=site_id%>";//站点ID
var app_id = "zwgk";//应用ID
var cat_id;
var CategoryGetRegu = new Bean("com.deya.wcm.bean.cms.category.CategoryGetRegu",true);
var defaultBean;
var SiteRPC = jsonrpc.SiteRPC;

var node_json_data;
$(document).ready(function(){
	//setModelInfoList();
	initButtomStyle();
	init_input();
	init_FromTabsStyle();
	
	initMenuTree("leftMenuTree2_regu",true);
	//alert(jsonrpc.GKNodeRPC.getGKNodeTreebyCateID(0));
	node_json_data = eval(jsonrpc.GKNodeRPC.getGKNodeTreebyCateID(0));
	getAllowSharedSite();

	changeCategoryId('zwgk');

	if(site_id!=''){
		s_site_id = site_id;
		//alert(node.text);
		$("#titlename").val("信息公开");
		var jdata = eval(CategoryRPC.getCategoryTreeBySiteID(s_site_id));
		//jdata = getTreeObjFirstNode(jdata);
		$("#leftMenuTree2_regu").empty();
		
		setAppointTreeJsonData("leftMenuTree2_regu",jdata);
    }

});
</script>
</head>

<body>

<span class="blank12"></span>
<form id="form1" name="form1" action="" target="iframe_target" method="post">
<input type="hidden" name="node_id" id="node_id" value=""/>
<input type="hidden" name="cat_ids" id="cat_ids" value=""/>
<div id="listTable_5">
<table id="table_id" width="100%" class="table_form" border="0" cellpadding="0" cellspacing="0" style="display:<%=styleStr%>">
	<tbody>
		<tr>
			<th style="width:80px">导出范围：</th>
			<td width="334px">
				<select class="width150" name="tsArea" id="tsArea" onchange="changeCategoryId(this.value)">
					<option value="zwgk">信息公开系统</option>
				</select>
			</td>	
			<td></td>
		</tr>
	</tbody>
</table>
<table class="table_form" border="0"  cellpadding="2" cellspacing="0" align="left">
	<tr>
	<%
	  if(site_id.equals("")){
		 %>
		 <td style="width:176px;padding-left:22px" valign="top" rowspan="2">
			<div id="cat_tree_div1" class="select_div border_color" style="width:176px; height:410px; overflow:scroll;border:1px #7f9db9 solid;" >
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft" style="height:300px;">
						<ul id="leftMenuTree_regu" class="easyui-tree" animate="true" style="width:176px;overflow:hidden">
						</ul>
						<span class="blank3"></span >
					</div>
				</div>
			</div>
		</td>
		 <% 
	  }
	%>
		<td id="tree_td_2" style="width:176px;" valign="top" rowspan="2">
			<div id="cat_tree_div2" class="select_div border_color" style="width:176px; height:410px; overflow:scroll;border:1px #7f9db9 solid;" >
				<div id="leftMenuBox">
					<div id="leftMenu2" class="contentBox6 textLeft" style="height:300px;">
						<ul id="leftMenuTree2_regu" class="easyui-tree" animate="true" style="width:176px;overflow:hidden">
						</ul>
						<span class="blank3"></span >
					</div>
				</div>
			</div>
		</td>
<td>&nbsp;&nbsp;</td>
	  <td valign="top">	
	  <table class="table_form" border="0"  cellpadding="2" cellspacing="0" align="left">
	<tr>
	<td>
				时间范围：
           		 <input class="Wdate" type="text" name="start_day" id="start_day" size="11" style="height:16px;line-height:16px;" 
					value="<%=start_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true" />
		         --
			     <input class="Wdate" type="text" name="end_day" id="end_day" size="11" style="height:16px;line-height:16px;" 
					 value="<%=end_day%>" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true"/>
			<br/>
			<br/>
			文件标题： <input type="text" id="titlename" name="titlename" value=""  style="width: 200px"/> 
			<br/>
			<br/>
			记录形式： <input type="radio" id="type1" name="extype" value="1" checked="checked"/><label>列表形式</label> 
			  &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="type2" name="extype" value="2" /><label>卡片形式</label>
			<br/>
			<br/>
			<div>
			  <input type="button" value="导出文件" class="btn1" onclick="choose_regu()" />
			</div>
			</td>
			</tr>
			</table>
		</td>
		</tr>
		<tr>
		<td style="height:200px;" >
		</td>
	</tr>
</table>
</div>

<span class="blank3"></span>
</form>
<iframe name="iframe_target" id="iframe_target" width="0" height="0"></iframe>
</body>
</html>

