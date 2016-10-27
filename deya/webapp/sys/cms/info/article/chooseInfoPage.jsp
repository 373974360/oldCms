<%@ page contentType="text/html; charset=utf-8"%>
<%
	String cid = request.getParameter("cat_id");
	String siteid = request.getParameter("site_id");
	String info_id = request.getParameter("info_id");
	String type = request.getParameter("t");
	if(type == null){
		type = "a";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>相关信息</title>
		<jsp:include page="../include/include_info_tools.jsp"/>

		<script type="text/javascript">
var InfoBaseRPC = jsonrpc.InfoBaseRPC;
var info_id = "<%=info_id%>";
var site_id = "<%=siteid%>";
var app = "0";
var cid = "<%=cid%>";
var count = 0;
var total = 0;
var p = 1;
var cat = "";
var words = "";
var ex = 0;

$(document).ready(function() {
	initButtomStyle();
	
	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");

	setScrollHandl();
	showSelectDiv2("cat_tree","cat_tree_div1",300);
	initCatTree();	
	getInfoList();
	getInfoCount();
});
	
function initCatTree(){
	var cat_jdata = eval(jsonrpc.CategoryRPC.getInfoCategoryTreeByUserID(site_id,LoginUserBean.user_id));
	setMenuTreeJsonData("leftMenuTree1",cat_jdata);
	initTree("leftMenuTree1");
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

function related_ok(){
	var idsTmp = $(":checkbox[name='infoList'][checked]");
	var ids = "";
	for(var i=0; i<idsTmp.length; i++){
		ids += "," + idsTmp[i].value;
	}
	if(ids.indexOf(",") != -1){
		ids = ids.substring(1);
	}
	top.getCurrentFrameObj().setLinkInfoIds(ids,"<%=type%>");
	//top.msgAlert("信息关联成功");
	top.CloseModalWindow();
}

function related_cancel(){
	top.CloseModalWindow();
}

function searchInfo(){
	words = $("#words").val();
	cat = showSelectIds1();
	if(cat != "" && cat != null)
	{
		if(cat.indexOf(",") == 0)
			cat = cat.substring(1);
		con_m.put("search_cat_ids", cat);
	}
	if(words != "" && words != null)
	{
		con_m.put("con_name", "title");
		con_m.put("con_value", words);
	}

	$("#data").empty();
	count = 0;
	total = 0;
	pageNum = 1;
	getInfoList();
	getInfoCount();
}

/****** 取信息　开始 ******/
var pageSize = 20;
var pageNum = 1;
var con_m = new Map();
con_m.put("site_id", site_id);	
con_m.put("page_size", pageSize);
con_m.put("info_status", "8");
con_m.put("final_status", "0");
con_m.put("is_host", "0");
con_m.put("sort_name", "released_dtime");
con_m.put("sort_type", "desc");

function setScrollHandl()
{
	$('#scroll_div').scroll(function(){
		var o = $('#scroll_div');
		if (o.scrollTop()+o.height() > o.get(0).scrollHeight - 90)
		{
			if (window.loading ) return;
			if (window.show_more_lock) return;
			if (count >= total) return;
	
			pageNum++;
			getInfoList();
		}
	});
}

function getInfoCount()
{
	total = InfoBaseRPC.getInfoCount(con_m);
	$("#checked_count").html( count>total ? total:count +"/"+total);
}

function getInfoList()
{	
	con_m.put("start_num", pageSize*(pageNum-1)+"");
	var list = InfoBaseRPC.getInfoList(con_m);
	list = List.toJSList(list);
	if(list != null && list.size() > 0)
	{
		count += list.size();
		var str = "";
		for(var i=0;i<list.size();i++)
		{
			str += '<li><input type="checkbox" name="infoList" value="'+list.get(i).info_id+'" /><label id="'+list.get(i).info_id+'">['+list.get(i).cat_cname+']'+list.get(i).title+'</label></li>';
		}
		$("#data").append(str);
		init_input();

		$("#data label").unbind("click").click(function(){
			var isChecked = $(this).prev().is(':checked') ? false:true;
			$(this).prev().attr("checked",isChecked);
			optSelectedInfoList(isChecked,$(this).prev().val(),$(this).text());
		});

		$("#data :checkbox").click(function(){
			optSelectedInfoList($(this).is(':checked'),$(this).val(),$(this).next().text())
		});

		$("#checked_count").html( count>total ? total:count +"/"+total);
	}
}

//根据是否选中处理选列表中的数据
function optSelectedInfoList(isChecked,info_id,info_name)
{
	if(isChecked)
	{		
		$("#list").append('<li style="float:none;height:20px;display:block;width:100%;both:clear" info_id="'+info_id+'"><span style="float:left">'+info_name+'</span><img onclick="deleteInfo(this,\''+info_id+'\')" src="../../../images/delete.png" width="15" height="15" alt="" style="float:left;padding-bottom:3px;padding-left:20px;cursor:pointer;"/></li>');	
	}else
	{
		$("#list li[Info_id='"+info_id+"']").remove();
	}
	$("#checked_count2").html($("#list li").size());
}

//删除选中的标签
function deleteInfo(obj,ids)
{
	$(obj).parent().remove();
	$("#data input[value='"+ids+"']").removeAttr("checked");
	$("#checked_count2").html($("#list li").size());
}

/*******取信息　结束 *****/

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
					<input type="text" name="words" id="words" />
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
				  <th>待选(<span id="checked_count">0</span>)</th>
				  <th></th>
				  <th>已选(<span id="checked_count2">0</span>)</th>
				</tr>
				<tr>
					<td align="top" >
						<div id="scroll_div" style="width:380px;height:370px;overflow:auto;background:#ffffff" class="border_color">
						  <div style="width:1000px;height:370px;overflow:auto">
							<ul id="data" class="txt_list">

							</ul>
						  </div>
						 </div>
					</td>
					<td class="width10"></td>
					<td valign="top">
						<div style="width:406px;height:370px;overflow:auto;background:#ffffff" class="border_color">
						  <div style="width:1000px;height:370px;overflow:auto">
							<ul id="list" class="txt_list">
							</ul>
						  </div>
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
	</body>
</html>