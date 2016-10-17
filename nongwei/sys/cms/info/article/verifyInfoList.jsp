<%@ page contentType="text/html; charset=utf-8"%>
<%
String cid = request.getParameter("cat_id");
String siteid = request.getParameter("site_id");
String app_id = request.getParameter("app_id");
if(siteid == null || siteid.equals("null")){
	siteid = "GK";
}
if(app_id == null || app_id.trim().equals("")){
	app_id = "cms";
}
String snum = request.getParameter("snum");
if(snum == null || snum.trim().equals("") || snum.trim().equals("null") ){
	snum = "0";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息管理</title>
<link type="text/css" rel="stylesheet" href="../../../styles/themes/default/tree.css" />
<link type="text/css" rel="stylesheet" href="../../../styles/sq.css" />


<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../../js/indexjs/indexList.js"></script>
<script type="text/javascript" src="../../../js/indexjs/tools.js"></script>

<script type="text/javascript">

var site_id = "<%=siteid%>";
var app = "<%=app_id%>";
var opt_ids = ","+top.getOptIDSByUser(app,site_id)+",";//登录人所拥有管理权限ID
var gk_article = false;//特殊栏目标识，在政务公开中使用的是内容管理中的文章模型

var UserManRPC = jsonrpc.UserManRPC;
var InfoBaseRPC = jsonrpc.InfoBaseRPC;
var GKNodeRPC = jsonrpc.GKNodeRPC;
var model_map = jsonrpc.ModelRPC.getModelMap();
model_map = Map.toJSMap(model_map);
var current_page_num = 1;
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "table";

var con_map = new Map();
con_map.put("user_id", LoginUserBean.user_id+"");
con_map.put("info_status", "2");
con_map.put("final_status", "0");
con_map.put("site_id", site_id);
con_map.put("app_id", app);
con_map.put("sort_name", "ci.opt_dtime");
con_map.put("sort_type", "desc");

$(document).ready(function(){
	setUserOperate();	
	initButtomStyle();
	reloadInfoDataList();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
});

function reloadInfoDataList()
{
	initTable();
	tp.curr_page = current_page_num;	
	showList();		
	showTurnPage();
}

function initTable(){
	var colsMap = new Map();
	var colsList = new List();	
	
	if(app == "zwgk" && gk_article == false)
		colsList.add(setTitleClos("gk_index","索引码","150px","","",""));
	colsList.add(setTitleClos("title","标题","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("cat_cname","所属栏目","100px","","",""));	
	colsList.add(setTitleClos("actions","管理操作","90px","","",""));
	colsList.add(setTitleClos("weight","权重","30px","","",""));
	colsList.add(setTitleClos("input_user","录入人","60px","","",""));
	colsList.add(setTitleClos("input_dtime","录入时间","100px","","",""));
    colsList.add(setTitleClos("modify_user","操作人员","100px","","",""));
	colsList.add(setTitleClos("opt_dtime","送审时间","100px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
	
}

function showList(){
	
	con_map.put("start_num", tp.getStart());	
	con_map.put("page_size", tp.pageSize);
	
	tp.total = 0;
	var return_map = InfoBaseRPC.getWaitVerifyInfoList(con_map);
	return_map = Map.toJSMap(return_map);
	if(return_map.get("info_count") != null)
		tp.total = return_map.get("info_count");

	beanList = return_map.get("info_List");//第一个参数为站点ID，暂时默认为空	
		
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
		
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("title").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{	
			var title_flag = "";
			var title_end_str = "";
			if(beanList.get(i-1).is_host == 1)
			{
				title_flag = "<span class='f_red'>[引]</span>";
			}
			if(beanList.get(i-1).is_host == 2)
			{
				title_flag = "<span class='f_red'>[链]</span>";
			}

			if(beanList.get(i-1).pre_title != "")
			{
				title_flag += "<span >["+beanList.get(i-1).pre_title+"]</span>";
			}

			if(beanList.get(i-1).is_pic == 1)
				title_end_str = "(图)";

			//var model_ename = ModelRPC.getModelEName(beanList.get(i-1).model_id);
			$(this).addClass("ico_"+model_map.get(beanList.get(i-1).model_id).model_ename);
			$(this).css("padding-left","20px");
			$(this).html('<a href="javascript:openViewPage('+beanList.get(i-1).info_id+')">'+title_flag+beanList.get(i-1).title+'</a>'+title_end_str);
		}
	});

	table.getCol("input_user").each(function(i){		
		if(i>0)
		{	
			if(beanList.get(i-1).input_user != 0)
				$(this).text(UserManRPC.getUserRealName(beanList.get(i-1).input_user));	
			else
				$(this).text("");	
		}
	});

    table.getCol("modify_user").each(function(i){
        if(i>0)
        {
            if(beanList.get(i-1).modify_user != 0)
                $(this).text(UserManRPC.getUserRealName(beanList.get(i-1).modify_user));
            else
                $(this).text("");
        }
    });
	
	table.getCol("actions").each(function(i){
		if(i>0)
		{	
			$(this).css({"text-align":"center"});
			
			var str = "<ul class=\"optUL\">";
			str += "<li id='303' class='ico_pass'><a title='通过' href='javascript:doPass("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li><li id='303' class='ico_nopass'><a  title='退稿' href='javascript:noPassDesc("+beanList.get(i-1).info_id+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li> <li id='301' class='ico_delete' ><a title='删除' href='javascript:doDelete("+(i-1)+")' style='width:16px;height:16px;'>&nbsp;&nbsp;&nbsp;&nbsp;</a></li> ";
			$(this).html(str+"</ul>");
		}
	});

	table.getCol("opt_dtime").each(function(i){
		if(i>0)
		{
			var t = beanList.get(i-1).opt_dtime;
			$(this).text(t.substring(0,t.length-3));
		}
	});

	current_page_num = tp.curr_page;
	if(gk_article == false && app != "ggfw")//特殊的公开栏目不使用权限判断
		setUserOperateLI(table.table_name);

	Init_InfoTable(table.table_name);
}

function setUserOperateLI(table_name)
{	
	$("#"+table_name+" li[id]").hide();
	$("#"+table_name+" li[id]").each(function(){
		var o_id = ","+$(this).attr("id")+",";
		if(opt_ids.indexOf(o_id) > -1)
			$(this).show();
	});
}

function showTurnPage(){	
	tp.show("turn","");	
	tp.onPageChange = showList;		
}

function setUserOperate()
{
	$("#btn299").hide();
	$(":button[id!='btn']").hide();
	
	if(opt_ids.indexOf(",299,") > -1)
		$("#btn299").show();

	$(":button[id!='btn']").each(function(){
		var o_id = ","+$(this).attr("id").replace("btn","")+",";
		if(opt_ids.indexOf(o_id) > -1)
			$(this).show();
	});
	$("#btnSearch").show();
	
}

function openViewPage(i_id)
{
	//window.location.href = "/sys/cms/info/article/infoView.jsp?info_id="+i_id+"&site_id="+site_id+"&snum="+snum;
	top.addTab(true,"/sys/cms/info/article/infoView.jsp?info_id="+i_id+"&site_id="+site_id+"&topnum="+top.curTabIndex,"查看信息");
}

//通过
function onPass(){
	var selectList = table.getSelecteBeans();
	if(InfoBaseRPC.passInfoStatus(selectList,LoginUserBean.user_id)){
		top.msgAlert("审核操作成功");	
	}else{
		top.msgWargin("审核操作失败");
	}
	reloadInfoDataList();
}

//不通过
function noPass(desc){
	var selectIDS = "";
	if(temp_info_id != "" && temp_info_id != null)
		selectIDS = temp_info_id;
	else
		selectIDS = table.getSelecteCheckboxValue("info_id");	
	
	if(InfoBaseRPC.noPassInfoStatus(selectIDS,desc)){
		top.msgAlert("退回操作成功");		
	}else{
		top.msgWargin("退回操作失败");
	}
	temp_info_id = null;
	reloadInfoDataList();
}

var temp_info_id;
function noPassDesc(id)
{
	if(id != null && id != "")
		temp_info_id = id;
	top.OpenModalWindow("退稿意见","/sys/cms/info/article/noPassDesc.jsp",520,235);	
}

//单条信息通过
function doPass(num){
	var list = new List();
	list.add(beanList.get(num));
	if(InfoBaseRPC.passInfoStatus(list,LoginUserBean.user_id)){
		top.msgAlert("审核操作成功");
	}else{		
		top.msgWargin("审核操作失败");
	}
	reloadInfoDataList();
}

//单条信息不通过
function doNoPass(id){
	
	if(InfoBaseRPC.noPassInfoStatus(id)){
		top.msgAlert("退回操作成功");		
	}else{
		top.msgWargin("退回操作失败");
	}
	reloadInfoDataList();
}

//还原
function rebackInfo(){
	var selectList = table.getSelecteBeans();
	if(InfoBaseRPC.goBackInfo(selectList)){
		top.msgAlert("还原操作成功");	
		reloadInfoDataList();
	}else{
		top.msgWargin("还原操作失败");
	}	
}

//信息删除，逻辑删
function deleteInfoData(){
	var selectList = table.getSelecteBeans();

	if(InfoBaseRPC.deleteInfo(selectList)){
		top.msgAlert("信息"+WCMLang.Delete_success);
		reloadInfoDataList();
	}else{
		top.msgWargin("信息"+WCMLang.Delete_fail);
	}
}
</script>
</head>

<body>
<div>	
	<span class="blank3"></span>
	<div id="table"></div>
	<div id="turn"></div>
 
<div class="infoListTable"> 
<table class="table_option" border="0" cellpadding="0" cellspacing="0"> 
	<tr> 
		<td align="left" valign="middle"> 
			<input id="btn303" name="btn1" type="button" onclick="publicSelectCheckbox(table,'info_id','onPass()');" value="通过" /> 
			<input id="btn303" name="btn2" type="button" onclick="publicSelectCheckbox(table,'info_id','noPassDesc()');" value="退稿" />
            <input id="btn301" name="btn3" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />			
		</td> 
	</tr> 
</table> 
</div>  
</div>
</body>
</html>