<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.DateUtil"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.util.jconfig.*"%>
<%@page import="com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.appeal.count.CountUtil"%>
<%@page import="java.io.File"%>
<%@page import="com.deya.wcm.services.appeal.count.OutExcel"%>
<%@ page import="com.deya.wcm.services.appeal.sq.SQRPC" %>
<%@ page import="com.deya.wcm.bean.appeal.sq.SQBean" %>
<%
	String sq_status = request.getParameter("sq_status");
	String publish_status = request.getParameter("publish_status");
	String sq_flag = request.getParameter("sq_flag");
	String is_back = request.getParameter("is_back");
	String alarm_flag = request.getParameter("alarm_flag");
	String limit_flag = request.getParameter("limit_flag");
	String prove_type = request.getParameter("prove_type");
	String supervise_flag = request.getParameter("supervise_flag");
	String page_type = request.getParameter("page_type");
	String is_manager_page = request.getParameter("is_manager_page");//是否是信件管理页面




	String now = DateUtil.getCurrentDateTime();
	//now = now.substring(0,7);
	pageContext.setAttribute("now",now);

	Map map = new HashMap();

	map.put("start_num", 0);
	map.put("page_size",10000);
	map.put("sort_name", "sq_dtime");
	map.put("sort_type", "desc");
	map.put("sq_status", sq_status);
	map.put("publish_status", publish_status);
	map.put("sq_flag", sq_flag);
	map.put("is_back", is_back);
	map.put("alarm_flag", alarm_flag);
	map.put("limit_flag", limit_flag);
	map.put("prove_type", prove_type);
	map.put("supervise_flag", supervise_flag);
	map.put("page_type", page_type);
	map.put("is_manager_page", is_manager_page);

	//删除今天以前的文件夹
	String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
	String path = FormatUtil.formatPath(root_path + "/appeal/sq/");
	CountUtil.deleteFile(path);

	//创建今天的文件夹和xls文件
	String nowDate = CountUtil.getNowDayDate();
	String fileTemp2 = FormatUtil.formatPath(path+File.separator+nowDate);
	File file2 = new File(fileTemp2);
	if(!file2.exists()){
		file2.mkdir();
	}
	String nowTime = CountUtil.getNowDayDateTime();
	String xls = nowTime + CountUtil.getEnglish(1)+".xls";
	String xlsFile = fileTemp2+File.separator+xls;
	String urlFile = "/sys/appeal/sq/"+nowDate+File.separator+xls;



	List<SQBean> list = SQRPC.getSqList(map,request);
	pageContext.setAttribute("list",list);

	String[] head = {"信件编号","留言标题","办理单位","留言内容","来信日期","姓名","电话","邮箱","回复内容"};
	String[][] data = new String[list.size()][9];
	for(int i=0;i<list.size();i++){
		SQBean sqBean = list.get(i);
		data[i][0] = sqBean.getSq_code();//编号
		data[i][1] = sqBean.getSq_title2();//标题
		data[i][2] = sqBean.getDo_dept_name();//办理部门
		data[i][3] = sqBean.getSq_content();//来信内容
		data[i][4] = sqBean.getSq_dtime();//来信日期
		data[i][5] = sqBean.getSq_realname();//姓名
		data[i][6] = sqBean.getSq_phone();//电话
		data[i][7] = sqBean.getSq_email();//邮箱
		data[i][8] = sqBean.getSq_reply();//回复内容
	}

	OutExcel oe=new OutExcel("来信列表");
	oe.doOut(xlsFile,head,data);

	request.setAttribute("url",urlFile);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>诉求列表</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/sqList.js"></script>
<script type="text/javascript">
var table_type = ""
var m = new Map();
m.put("page_type","<%=page_type%>");//页面类型
var sq_status = "<%=sq_status%>";
if(sq_status != "" && sq_status != "null")
{
	m.put("sq_status",sq_status);//流程处理状态
}
var publish_status = "<%=publish_status%>";
if(publish_status != "" && publish_status != "null")
{
	m.put("publish_status",publish_status);//发布状态
}
var sq_flag = "<%=sq_flag%>";
if(sq_flag != "" && sq_flag != "null")
{
	m.put("sq_flag",sq_flag);//信件标识
}
var is_back = "<%=is_back%>";
if(is_back != "" && is_back != "null")
{
	m.put("is_back",is_back);//回退标识
}
var alarm_flag = "<%=alarm_flag%>";
if(alarm_flag != "" && alarm_flag != "null")
{
	m.put("alarm_flag",alarm_flag);//超期未办警示标识
}
var limit_flag = "<%=limit_flag%>";
if(limit_flag != "" && limit_flag != "null")
{
	m.put("limit_flag",limit_flag);//延时申请标识
}
var supervise_flag = "<%=supervise_flag%>";
if(supervise_flag != "" && supervise_flag != "null")
{
	m.put("supervise_flag",supervise_flag);//督办标识
}
var prove_type = "<%=prove_type%>";
if(prove_type != "" && prove_type != "null")
{
	m.put("prove_type",prove_type);//信件原始办理类型
}
var is_manager_page = "<%=is_manager_page%>";
if(is_manager_page != "" && is_manager_page != "null")
{
	m.put("is_manager_page","true");//是否是信件管理页面
}

var p_list;//业务列表
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	getOptIDSByUser();
	//得到业务列表
	getModelListSByUserID();
	getPurposeList();
	initTable();
	reloadSQList();	
	reloadTagList();
});


function dateSearch(vals)
{
	m.put("search_date",vals);
	reloadSQList();
}



function pcSearch()
{
	var s_val = $("#searchkey").val().trim();
	val.checkEmpty(s_val,"查询条件","searchkey");	
	if(!val.getResult()){		
		return;
	}

    $("#searchFields option").each(function(){
        m.remove($(this).val());
    });

    var text = $("#searchFields option:selected").val();
    m.put(text,s_val);

	m.remove("tag_ids");
	m.remove("search_date");		
	reloadSQList();
}

function sortSQList(val)
{
	table.sortCol = val.substring(0,val.indexOf(","));
	table.sortType = val.substring(val.indexOf(",")+1);
	reloadSQList();
}

function downFile(){
    window.open("<%=request.getAttribute("url")%>");
}

</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td class="fromTabs">
				渠道：
				<select id="model_id" class="input_select width70" onchange="modelSearch(this.value)">
					<option selected="selected" value="">全部</option>
				</select>
				<select id="submit_names" class="input_select width70" onchange="deptSearch(this.value)">
					<option selected="selected" value="">全部</option>
				</select>
				目地：
				<select id="pur_id" class="input_select width45" onchange="purSearch(this.value)">
					<option selected="selected" value="">全部</option>
				</select>
				状态：
				<select id="sq_status" class="input_select width55" onchange="statusSearch(this.value)">
					<option selected="selected" value="">全部</option>
					<option value="0">待处理</option>
					<option value="1">处理中</option>
					<option value="2">待审核</option>
					<option value="3">已办结</option>
				</select>
				时间：
				<select id="sq_dtime" class="input_select width60" onchange="dateSearch(this.value)">
					<option value="all">全部</option>
					<option value="day">当天</option>
					<option value="week">一周内</option>
					<option value="month">一月内</option>
					<option value="year">一年内</option>
				</select>
				<input id="btnExcel" type="button" value="Excel导出" onclick="downFile()"/>
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
				<select id="searchFields" class="width70" onchange="showTagSelect(this.value)">
					<option selected="selected" value="sq_title2">信件标题</option>
					<option value="sq_realname">来信人</option>
					<option value="submit_name">收信单位</option>
					<option value="sq_content2">信件内容</option>
					<option value="sq_code">信件编码</option>
					<option value="tag">特征标记</option>
				</select>
				<input id="searchkey" type="text" class="" value="" style="width:70px" /><input id="btnSearch" type="button" value="搜索" onclick="pcSearch()"/>
				<select id="tag_select" class="input_select hidden" onchange="pcSearchForTag(this.value)">
					<option value=""></option>
				</select>
				<select id="orderByFields" class="input_select" onchange="sortSQList(this.value)">
					<option selected="selected" value="sq_id,desc">时间倒序</option>
					<option value="sq_id,asc">时间顺序</option>
				</select>
				<span class="blank3"></span>
			</td>		
		</tr>
	</table>
	<span class="blank3"></span>
	<div id="table"></div><!-- 列表DIV -->
	<div id="turn"></div><!-- 翻页DIV -->
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<!-- <input id="btn1" name="btn1" type="button" onclick="window.open('add.jsp');" value="添加" /> -->
			<!-- <input id="btn1" name="btn1" type="button" onclick="SQRPC.getALlNotEndSqList()" value="超期计算" /> -->
			<input id="btn322" name="btn3" type="button" class="hidden" onclick="deleteRecord(table,'sq_id','deleteSQ()');" value="删除" />			
		</td>
	</tr>
   </table>	
</div>
</body>
</html>