<%@ page contentType="text/html; charset=utf-8"%>
<%
	String app_id = request.getParameter("app_id");
	String siteid = request.getParameter("site_id");
	String cat_id = request.getParameter("cat_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息获取</title>
<jsp:include page="../include/include_info_tools.jsp"/>

<script type="text/javascript">
var cat_id = "<%=cat_id%>";
var site_id = "<%=siteid%>";
var app_id = "<%=app_id%>";
var cat_id_for_get = cat_id;
var GKNodeRPC = jsonrpc.GKNodeRPC;
var CategoryRPC = jsonrpc.CategoryRPC;
var hj_sql = "";
var count = 0;
var total = 0;
var pageSize = 20;
var pageNum = 1;
var serarch_con = "";//查询条件
var info_list;
var info_map =  new Map();//存储info对象
var node_name_map =  new Map();//存储公开对象
var m = new Map();	
m.put("step_id","100");
m.put("info_status", "8");
m.put("final_status", "0");
//m.put("is_host", "0");

$(document).ready(function() {
	initButtomStyle();
	init_input();
	if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height())
		$("html").css("overflowY", "scroll");

	setUserPublishOperate();
	setScrollHandl();
	hj_sql = CategoryRPC.getCategoryBeanCatID(cat_id,site_id).hj_sql;
	m.put("searchString_regu", hj_sql);
	
	if(hj_sql != "" && hj_sql != null)
	{
		getInfoCount();
		getInfoList();
	}
});

function getInfoList()
{	
	m.remove("shared_cat_ids");
	m.put("page_size", pageSize);
	m.put("start_num", pageSize*(pageNum-1)+"");
	m.put("sort_name", "ci.id desc,ci.released_dtime");
	m.put("sort_type", "desc");			
	
	if(serarch_con != ""){
		m.put("searchString", serarch_con);
	}

	info_list = jsonrpc.GKInfoRPC.getGKInfoList(m);
	
	info_list = List.toJSList(info_list);
	if(info_list != null && info_list.size() > 0)
	{
		count += info_list.size();
		var str = "";
		
		for(var i=0;i<info_list.size();i++)
		{
			info_map.put(info_list.get(i).info_id,info_list.get(i));
			var node_name = "";
			var n_name = "";
			if(info_list.get(i).site_id.indexOf("GK") > -1)
			{
				n_name = "";
				if(node_name_map.containsKey(info_list.get(i).site_id))
				{
					n_name = node_name_map.get(info_list.get(i).site_id);
				}else
				{
					n_name = GKNodeRPC.getNodeNameByNodeID(info_list.get(i).site_id);
					node_name_map.put(info_list.get(i).site_id,n_name);
				}
				node_name = "<span style='color:red'>["+n_name+"]</span>";
			}
			
			str += '<li><input type="checkbox" name="infoList" value="'+info_list.get(i).info_id+'" />'+node_name+'<label url="'+info_list.get(i).content_url+'">'+info_list.get(i).title+'</label>&#160;&#160;&#160;&#160;<span>'+info_list.get(i).released_dtime.substring(5,16)+'</span></li>';
		}
		$("#data").append(str);
		init_input();	
	}
	$("#checked_count").html( count>total ? total:count +"/"+total);
}

function getInfoCount()
{	
	if(serarch_con != ""){
		m.put("searchString", serarch_con);
	}
	total = jsonrpc.GKInfoRPC.getGKInfoCount(m);
	$("#checked_count").html( count>total ? total:count +"/"+total);
}


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

function setUserPublishOperate()	
{
	var opt_ids = ","+top.getOptIDSByUser(app_id,site_id)+",";
	//判断是否是站点管理员或超级管理员
	if(top.isSiteManager(app_id,site_id) == true || opt_ids.indexOf(",302,") > -1)
	{
		$("#table_id #opt_302").show();		
	}
}

//点击搜索事件
function doSearchInfoForGet(){
	if(hj_sql == "")
		return;

	var words = $("#searchkey").val();
	if(words != "" && words != null)
	{
		m.put("con_name", "title");
		m.put("con_value", words);
	}

	$("#data").empty();
	pageNum = 1;
	count = 0;

	getInfoList();
	getInfoCount();	
}

//把选中的信息添加到表中
function related_ok()
{
	var idsTmp = $(":checked[name='infostatus']").val();
	var isPublish = $("#isPublish").is(':checked');
	if($("#data :checked").length == 0)
	{
		related_cancel();
		return;
	}
	
	var i_list = new List();
	$("#data :checked").each(function(){		
		i_list.add(info_map.get($(this).val()));
	});

	if(jsonrpc.InfoBaseRPC.infoGet(i_list,site_id,app_id,cat_id_for_get,idsTmp,isPublish,top.LoginUserBean.user_id))
	{
		top.msgAlert("获取成功");
		top.getCurrentFrameObj().reloadInfoDataList();
		top.CloseModalWindow();
	}
	else
	{
		top.msgWargin("获取失败,请重新操作");
	}
}

//取消
function related_cancel(){
	top.CloseModalWindow();
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
		<table id="table_id" width="100%" class="table_form" border="0" cellpadding="0" cellspacing="0">
			<tbody>
				<tr>					
					<th style="width:65px">获取方式：</th>
					<td width="160px">
						<ul id="status_ul">
						 <!-- <li style="float:left; padding-right:20px;"><input type="radio" id="d" name="infostatus" value="0" /><label for="d">克隆</label></li> -->
						 <li style="float:left; padding-right:20px;"><input type="radio" id="e" name="infostatus" value="1" checked="checked"/><label for="e">引用</label></li>
						 <li style="float:left; padding-right:20px;"><input type="radio" id="f" name="infostatus" value="2"/><label for="f">链接</label></li>
						</ul>	
					</td>
					<th style="width:65px" id="opt_302" class="hidden">获取结果：</th>
					<td width="90px" id="opt_302"  class="hidden">
						<ul><li ><input type="checkbox" name="isPublish" id="isPublish" /><label for="isPublish">直接发布</label></li></ul>
					</td>
					<td>
						<select id="searchTimes" class="input_select width60" onchange="changeTimes()">
							<option selected="selected" value="0b">日期</option> 
							<option value="1b">今日</option> 
							<option value="2b">昨日</option> 
							<option value="3b">一周内</option> 
							<option value="4b">一月内</option> 
						</select>
						<input type="text" name="searchkey" id="searchkey"/>
						<input type="button" name="" onclick="doSearchInfoForGet()" value="搜索"/>
						&#160;&#160;(<span id="checked_count"></span>)
					</td>
				</tr>
			</tbody>
		</table>
		<table class="table_form" border="0" cellpadding="0" cellspacing="0">			
			<tr>
			  <td valign="top" style="padding-left:12px;">	
					<div id="scroll_div" style="width:750px;height:400px;overflow:auto;background:#ffffff" class="border_color">
						<ul id="data" class="txt_list" style="width:1000px;">

						</ul>
					</div>
				</td>
			</tr>
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