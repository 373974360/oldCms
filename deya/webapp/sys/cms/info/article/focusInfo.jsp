<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.*"%>
<%
	String cid = request.getParameter("cat_id");
	String siteid = request.getParameter("site_id");
	String info_id = FormatUtil.formatNullString(request.getParameter("info_id"));
	if(info_id == null || "null".equals(info_id))
		info_id = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>信息推荐</title>
		<jsp:include page="../include/include_info_tools.jsp"/>
		<script type="text/javascript" src="js/m_article.js"></script>
		
		<script type="text/javascript">
var info_id = "<%=info_id%>";
var site_id = "<%=siteid%>";
var app_id = "cms";
var cid = "<%=cid%>";
var count = 0;
var total = 1000;
var p = 1;
var cat = "";
var words = "";
var total = 0;
var ex = 0;
var WareRPC = jsonrpc.WareRPC;

$(document).ready(function() {
	initButtomStyle();
	init_input();
	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");

	showSelectDiv("cat_tree","cat_tree_div1",300);
	initCatTree();	
	showTotal();
	getSelctWareInfos();
});
	
//初始加载标签分类树
function initCatTree(){
	var con_m = new Map();
	con_m.put("app_id",app_id);
	con_m.put("site_id",site_id);
	var cat_jdata = eval(jsonrpc.WareRPC.getJSONTreeStr(con_m));
	setMenuTreeJsonData("leftMenuTree1",cat_jdata);
	initTree();
}

function initTree()
{
	$('#leftMenuTree1').tree({
		onClick:function(node){			
			cat = node.id;
			$("#cat_tree").val(node.text);
			//$("#cat_tree_div1").hide();
		}
	});	
}
	
function chooseInfo(o){
	var fg = $(o).is(':checked');
	if(fg){
		$("#list").append("<li id='"+$(o).val()+"_li' class='l_s'>"+$("#"+$(o).val()).html()+"</li>");
	}else{
		$("#"+$(o).val()+"_li").remove();
	}
	showTotal();
}

//保存事件
function related_ok(){
	var ids = "";
	$("#list li").each(function(i){
		if(i > 0)
			ids += ",";
		ids += $(this).attr("ware_id");
	});
	
	getCurrentFrameObj().setFocusInfoIds(ids);
	
	CloseModalWindow();
}

//取消事件
function related_cancel(){
	CloseModalWindow();
}

//查询事件
function searchInfo(){
	words = $("#words").val();	
	$("#data").html("");
	if(cat == "")
	{
		showTotal();
	}
	else
	{
		var m = new Map();		
		//cat = cat.substring(1);
		m.put("id",cat+"");
		m.put("site_id",site_id);
		m.put("app_id",app_id);
		var list = WareRPC.getHandWareListByCateID(m);
		setWareListInDiv(list);
		setSelctWareInfos();
	}
}

//
function setSelctWareInfos()
{
	$("#list li").each(function(i){		
		$("#data :checkbox[value='"+$(this).attr("ware_id")+"']").attr("checked",true);
	});
}

//得到曾经选择过的标签
function getSelctWareInfos()
{
	var ids = getCurrentFrameObj().getSelctWareInfo();
	if(ids != "" && ids != null)
	{
		var tempA = ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			$("#data label[id='"+tempA[i]+"']").click();
		}
	}
}

//显示所有的手动标签
function showTotal(){
	var list = WareRPC.getHandWareList(site_id);
	setWareListInDiv(list);
}

//将数据写入到div中
function setWareListInDiv(list)
{
	list = List.toJSList(list);
	var str = "";
	if(list != null && list.size() > 0)
	{
		for(var i=0;i<list.size();i++)
		{
			if(list.get(i).receive_recom == 1)
				str += '<li><input type="checkbox" name="infoList" value="'+list.get(i).ware_id+'" /><label id="'+list.get(i).ware_id+'">'+list.get(i).ware_name+'</label></li>';
		}
	}
	$("#data").append(str);
	init_input();

	$("#data label").unbind("click").click(function(){
		var isChecked = $(this).prev().is(':checked') ? false:true;
		$(this).prev().attr("checked",isChecked);
		optSelectedWareList(isChecked,$(this).prev().val(),$(this).text());
	});

	$("#data :checkbox").click(function(){
		optSelectedWareList($(this).is(':checked'),$(this).val(),$(this).next().text())
	});
}
//根据是否选中处理选列表中的数据
function optSelectedWareList(isChecked,ware_id,ware_name)
{
	if(isChecked)
	{		
		$("#list").append('<li style="float:none;height:20px;display:block;width:100%;both:clear" ware_id="'+ware_id+'"><span style="float:left">'+ware_name+'</span><img onclick="deleteWare(this,\''+ware_id+'\')" src="../../../images/delete.png" width="15" height="15" alt="" style="float:right;padding-bottom:3px"/></li>');	
	}else
	{
		$("#list li[ware_id='"+ware_id+"']").remove();
	}
}

//删除选中的标签
function deleteWare(obj,ids)
{
	$(obj).parent().remove();
	$("#data input[value='"+ids+"']").removeAttr("checked");
}

</script>
<style type="text/css">
h3{height:20px;}

.main{padding:0px; margin:auto;width:660px; border:0px #abadb3 solid;}

.topMain{width:660px; height:30px;}
.topMain .leftA{float:left;}
.topMain .rightB{float:right;}

.leftDiv{border:1px #abadb3 solid; float:left;}

.rightDiv{border:1px #abadb3 solid; float:left;}

.clear{clear:both;}

.footMain{padding-top:5px; text-align: center;}

.txt_list{padding-left:8px; padding-top:10px; line-height:20px; padding-right:10px;}

.txt_list li{height:24px; font-size:13px; width:100%; vertical-align: middle;}

.r_s{float:right;}
.l_s{float:left;}
</style>
	</head>
	<body>
		<span class="blank3"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="table" class="" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
		  <td colspan="3">
			<input type="text" id="cat_tree" value="" style="width:176px; height:18px; overflow:hidden;" readonly="readonly"/>
			<div id="cat_tree_div1" class="select_div tip hidden border_color" style="width:176px; height:300px; overflow:hidden;border:1px #7f9db9 solid;" >
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
						<ul id="leftMenuTree1" class="easyui-tree" animate="true" style="width:176px; height:280px;">
						</ul>
					</div>
				</div>
			</div>
			<input type="button" value="检索" class="btn1" onclick="searchInfo()" />
			<span class="blank3"></span>
		  </td>
		</tr>	
		<tr>
			<td align="top" >
				<div style="width:300px;height:385px;overflow:auto;background:#ffffff" class="border_color">
					<ul id="data" class="txt_list">

					</ul>
				 </div>
			</td>
			<td class="width10"></td>
			<td valign="top">
				<div style="width:320px;height:385px;overflow:auto;background:#ffffff" class="border_color">
					<ul id="list" class="txt_list">
					</ul>
				 </div>
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
			<input type="button" value="确定" class="btn1" onclick="related_ok()" />
			<input type="button" value="取消" class="btn1" onclick="related_cancel()" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</html>