<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String winfo_id = request.getParameter("winfo_id");	
	String sort_id = request.getParameter("sort_id");	
	String action_type = request.getParameter("action_type");	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择推荐信息</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="../../styles/uploadify.css"/>
<jsp:include page="../../include/include_tools.jsp"/>
<script src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
<script type="text/javascript" src="../../js/jquery-plugin/iColorPicker.js"></script>
<script type="text/javascript" src="../../js/uploadTools.js"></script>
<script type="text/javascript" src="../../js/jquery.uploadify.js"></script>
<script type="text/javascript" src="../../js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="js/wareInfo.js"></script>
<script type="text/javascript">

var ware_id = getCurrentFrameObj().ware_id;
var app_id = getCurrentFrameObj().app_id;
var site_id = getCurrentFrameObj().site_id;
var winfo_id = <%=winfo_id%>;
var sort_id = "<%=sort_id%>";
var action_type = "<%=action_type%>";

var InfoBaseRPC = jsonrpc.InfoBaseRPC;
var WareRPC = jsonrpc.WareRPC;
var ArticleRPC = jsonrpc.ArticleRPC;
var WareInfos = new Bean("com.deya.wcm.bean.system.ware.WareInfos",true);
var val=new Validator();
var tp = new TurnPage();
tp.pageSize = 10;
var info_ref_m = new Map();
info_ref_m.put("ware_id", ware_id);
info_ref_m.put("app_id", app_id);
info_ref_m.put("site_id", site_id);

$(document).ready(function(){
	initButtomStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	init_FromTabsStyle();
	showTurnPage();
	getWareInfoRefList();
	Init_InfoTable("info_list_table");
	getModelList();//得到模板列表
	publicUploadButtomLoad("uploadify",true,false,"",0,5,site_id,"savePicUrl");

	setPreTitle();
	showSelectDiv("pre_title","select4",120);
});

function getModelList()
{
	var list = jsonrpc.ModelRPC.getCANModelListByAppID(app_id);
	list = List.toJSList(list);
	$("#model_id").addOptions(list,"model_id","model_name");
}

function getWareInfoRefList()
{
	$("#info_list_table").empty();
	info_ref_m.put("start_num", tp.getStart());	
	info_ref_m.put("page_size", tp.pageSize);
	var info_list = WareRPC.getWareInfoRefList(info_ref_m);
	info_list =  List.toJSList(info_list);
	if(info_list != null && info_list.size() > 0)
	{
		var str = "";
		for(var i=0;i<info_list.size();i++)
		{
			str += '<tr>';
			str += '<td style="text-align:center"><input type="radio" id="info_id" name="info_id" value="'+info_list.get(i).info_id+'" onclick="showInfoToInput('+info_list.get(i).info_id+')"></td>';
			str += '<td >'+info_list.get(i).title+'</td>';
			str += '<td >'+info_list.get(i).author+'</td>';
			str += '<td >'+info_list.get(i).input_dtime+'</td>';
			str += '<td style="text-align:center"><img src="../../images/delete.png" title="删除" onclick="deleteInfoRef(this,'+info_list.get(i).info_id+')"></td>';
			str += '</tr>';
		}
		//<ul class="optUL" style="text-align:center"><li class="opt_delete ico_delete" title="删除" onclick="deleteInfoRef(this,'+info_list.get(i).info_id+')"></li></ul>
		$("#info_list_table").append(str);
		Init_InfoTable("jcxx_tab");
	}
}

function showTurnPage()
{
	tp.total = WareRPC.getWareInfoRefCount(info_ref_m);
	tp.show("turn","");
	tp.onPageChange = getWareInfoRefList;
	init_input();
}

function deleteInfoRef(obj,info_id)
{
	if(WareRPC.deleteWareInfoRef(info_id,ware_id,app_id,site_id))
	{
		$(obj).parent().parent().remove();
	}
}

function showInfoToInput(info_id)
{
	var defaultBean = InfoBaseRPC.getInfoById(info_id);
	if(defaultBean){
		$("#zxxx_tab").autoFill(defaultBean);	
	}
}

function showStringLength(inId, showId){
	$("#"+showId).html($("#"+inId).val().length);
}

function saveWareInfos()
{
	if(!standard_checkInputInfo("zxxx_tab"))
	{
		return;
	}
	var bean = BeanUtil.getCopy(WareInfos);	
	$("#zxxx_tab").autoBind(bean);
	bean.id = WareRPC.getNewWareInfosID();
	bean.ware_id = ware_id;
	bean.winfo_id = winfo_id;
	bean.app_id = app_id;
	bean.site_id = site_id;
	if(sort_id != null && sort_id != "" && sort_id != "null")
		bean.sort_id = parseInt(sort_id);
	if(WareRPC.insertWareInfos(bean))
	{
		msgAlert("信息"+WCMLang.Add_success);
		if(action_type == "change")
			getCurrentFrameObj().changeInfoToUL(bean);
		else
			getCurrentFrameObj().setInfoToUL(bean,winfo_id);
		CloseModalWindow();
	}else
	{
		msgWargin("信息"+WCMLang.Add_fail);
	}
}

function setPreTitle(){
	var beanList = jsonrpc.DataDictRPC.getDataDictList("pre_title");//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	var str = "";
	for(var i=0; i<beanList.size(); i++){
		str += '<li onclick="showValue(\'pre_title\',this)" style="cursor:default;float:none">'+beanList.get(i).dict_name+'</li>'
	}
	$("#selectList_pre").html(str);
}

function showValue(id,o){
	$("#"+id).val($(o).html());
}
/******************** 搜索信息处理　开始 **********************/
var search_tp = new TurnPage();
search_tp.pageSize = 10;
var search_ref_m = new Map();
search_ref_m.put("ware_id", ware_id);
search_ref_m.put("app_id", app_id);
search_ref_m.put("site_id", site_id);
search_ref_m.put("info_status", 8);
search_ref_m.put("final_status", "0");
search_ref_m.put("sort_name", "info_id");
search_ref_m.put("sort_type", "desc");

function searchInfo()
{
	search_ref_m.remove("searchString");	
	var search_key = $("#searchkey").val();
	if(search_key != null && search_key != null)
	{
		search_ref_m.put("searchString", " title like '%"+search_key+"%'");		
	}
	
	search_ref_m.remove("model_id");
	var model_id = $("#model_id").val();
	if(model_id != null && model_id != null)
	{
		search_ref_m.put("model_id", model_id);		
	}
	showTurnPage_search();
	getSearchInfoRefList();
}

//搜索出信息
function getSearchInfoRefList()
{
	$("#search_list_table").empty();
	search_ref_m.put("start_num", search_tp.getStart());
	search_ref_m.put("page_size", search_tp.pageSize);
	var info_list = InfoBaseRPC.getInfoList(search_ref_m);
	info_list =  List.toJSList(info_list);
	if(info_list != null && info_list.size() > 0)
	{
		var str = "";
		for(var i=0;i<info_list.size();i++)
		{
			str += '<tr>';
			str += '<td style="text-align:center"><input type="radio" id="info_id" name="info_id" value="'+info_list.get(i).info_id+'" onclick="showInfoToInput('+info_list.get(i).info_id+')"></td>';
			str += '<td >'+info_list.get(i).title+'</td>';
			str += '<td >'+info_list.get(i).author+'</td>';
			str += '<td >'+info_list.get(i).input_dtime+'</td>';			
			str += '</tr>';
		}		
		$("#search_list_table").append(str);
		Init_InfoTable("pzxx_tab");
	}
}

//显示搜索的翻页信息
function showTurnPage_search()
{
	search_tp.total = InfoBaseRPC.getInfoCount(search_ref_m);
	search_tp.show("search_turn","");
	search_tp.onPageChange = getSearchInfoRefList;
	init_input();
}
/******************** 搜索信息处理　结束 **********************/

function savePicUrl(url)
{
	$("#thumb_url").val(url);
}
</script>
<style>
/*信息添加页面*/
.selectDiv{border-bottom:1px #63c21c solid; font-size:12px;border:1px #7f9db9 solid;}
.selectDiv .listLi li{text-align: left; line-height:15px; height:15px;padding: 0; padding-left:3px; font-size:12px; color:#000; text-decoration:none;;margin:3px 0}
.selectDiv .listLi li:hover {color:#000;text-decoration:none; height:15px; width:100%; background-color: #3399ff; font-size:12px; border:1px #eee dotted;}
.selectDiv .listLi li:active {color:#000;text-decoration:none; height:15px; background-color: #3399ff; font-size:12px;}
.selectDiv .listLi li::visited {color:#000;text-decoration:none; height:15px; font-size:12px;}
</style>
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
						<div class="tab_right" >推荐</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" >搜索</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" >录入</div>
					</div>
				</li>
			</ul>
		</td> 	
		
	</tr>
</table>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<div id="subCategory">
<div class="infoListTable" id="listTable_0">
<div style="overflow:auto;height:280px">
<table id="jcxx_tab" class="table_border odd_even_list" border="0" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<td width="30px" style="text-align:center"></td>
			<td style="text-align:center">标题</td>
			<td width="100px" style="text-align:center">作者</td>
			<td width="120px" style="text-align:center">时间</td>
			<td width="40px" style="text-align:center">删除</td>
		</tr>
	</thead>	
	<tbody id="info_list_table">
	</tbody>	
	<tfoot><tr><td colspan="5"></td></tr></tfoot>
</table>
</div>
<div id="turn"></div>
</div>

<div class="infoListTable hidden" id="listTable_1">
<table id="" class="table_form" border="1" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>			
			<td>
				<input id="searchkey" name="searchkey" type="text" maxlength="80" value=""/>
				<select id="model_id" name="model_id">
					<option value="">请选择</option>
				</select>
				<input id="btnSearch" type="button" class="btn x2" value="搜索" onclick="searchInfo()"/>			
			</td>
		</tr>
	</tbody>
</table>
<div style="overflow:auto;height:280px;">
<table id="pzxx_tab" class="table_border odd_even_list" border="0" cellpadding="0" cellspacing="0" >
	<thead>
		<tr>
			<td width="30px" style="text-align:center"></td>
			<td style="text-align:center">标题</td>
			<td width="100px" style="text-align:center">作者</td>
			<td width="120px" style="text-align:center">时间</td>
		</tr>
	</thead>	
	<tbody id="search_list_table">
	</tbody>	
	<tfoot><tr><td colspan="4"></td></tr></tfoot>
</table>
</div>
<div id="search_turn"></div>
</div>
<div class="infoListTable hidden" id="listTable_2">
<table id="zxxx_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>标题：</th>
			<td>
				<input type="text" id="pre_title" value="" style="width:60px; height:18px; overflow:hidden;" />
				<div id="select4" class="select_div tip hidden border_color selectDiv" style="width:134px; height:30px; ;overflow-x:hidden; overflow:auto; " >
					<div id="leftMenuBox">
						<ul id="selectList_pre" class="listLi"  style="width:134px; height:30px; text-align: left;">
						</ul>
					</div>
				</div>
				<input id="title" name="title" type="text" class="width250" maxlength="80" value="" onkeypress="showStringLength('title','wordnum')" onkeyup="showStringLength('title','wordnum')"  onblur="checkInputValue('title',false,160,'信息标题','')"/>
				<span id="wordnum">0</span>字
				<input id="title_color" name="title_color" type="text" style="width:60px;" class="iColorPicker" onchange="changeTitleColor(this)" value="" /> 				
			</td>
		</tr>
		<tr>
			<th>副标题：</th>
			<td>
				<input id="subtitle" name="subtitle" type="text" class="width300" maxlength="80" value="" onkeypress="showStringLength('subtitle','wordnum2')" onkeyup="showStringLength('subtitle','wordnum2')"/>
				<span id="wordnum2">0</span>字
			</td>
		</tr>
		<tr>
			<th>链接：</th>
			<td>
				<input id="content_url" name="content_url" type="text" class="width300" maxlength="200" value="" />
			</td>
		</tr>
		<tr>
			<th>焦点图片：</th>
			<td>
				<div style="float:left;margin:auto;"><input id="thumb_url" name="thumb_url" type="text" style="width:250px;" value="" /></div>
				<div style="float:left"><input type="file" name="uploadify" id="uploadify"/></div>
				<div style="float:left">&nbsp;<input id="i005" name="i005" type="button" onclick="openSelectMaterialPage('savePicUrl',site_id,'radio')" value="图片库" /></div>
				<div style="float:left">&nbsp;<input id="i005" name="i005" type="button" onclick="changeThumbUrl('thumb_url')" value="使用原图" /></div>
			</td>
		</tr>
		
		<tr>
			<th style="vertical-align:top;">内容摘要：</th>
			<td>
				<textarea id="description" name="description" style="width:300px;height:80px"></textarea>
			</td>
		</tr>
		<tr>
			<th>显示时间：</th>
			<td>
				<input class="Wdate width150" type="text" name="publish_dtime" id="publish_dtime" size="11" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="true" >
			</td>
		</tr>
		<tr>			
			<td colspan="2">
				<div id="fileQueue"></div>
			</td>
		</tr>
	</tbody>
</table>
</div>
</div>

<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="saveWareInfos()" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="form1.reset()" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="CloseModalWindow();" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>

</form>
</body>
</html>
