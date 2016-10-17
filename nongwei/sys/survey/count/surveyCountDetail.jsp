<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>访谈热度排行</title> 
	<jsp:include page="../../include/include_tools.jsp"/>
	<script src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>    
	<SCRIPT language="javascript">
	<!--
	var site_id = "<%=site_id%>";
   $(document).ready(function () {	
		$("#start_time").val(getCurrentDate());
		$("#end_time").val(getCurrentDate());

		initTable();			
		showList();	
		initButtomStyle();
	}); 

	var SurveyRPC = jsonrpc.SurveyRPC;		

	var beanList = null;
	var table = null;
	var con = "";
	function initTable(){
		table = new Table();	
		table.table_name = "count_table";
		var colsMap = new Map();	
		var colsList = new List();	
		
		colsList.add(setTitleClos("s_name","问卷名称","","","",""));　
		colsList.add(setTitleClos("c_name","问卷分类","20%","","",""));	　
		colsList.add(setTitleClos("subject_count","问题总数","20%","","",""));
		colsList.add(setTitleClos("answer_count","答卷数","20%","","",""));
		
		table.setColsList(colsList);
		table.setAllColsList(colsList);				
		table.enableSort=false;//禁用表头排序
		table.onSortChange = showList;
		table.show("table");//里面参数为外层div的id
	}

	function showList(){	
		var s_tiem = request.getParameter("st");
		var e_tiem = request.getParameter("et");	
		var time_type = request.getParameter("tt"); 
		var count_num = request.getParameter("cn");
		var category_name = request.getParameter("na");
		var ids = request.getParameter("ids");
		ids = "'"+ids.replace(/,/g,"','")+"'";

		var m = SurveyRPC.getHotCount(s_tiem,e_tiem,ids,count_num,time_type,site_id);
		
		if(m != null)
		{
			m = Map.toJSMap(m);
			beanList = m.get("count_list");	
			$("#excel_out").click(function(){
				window.open(m.get("file_path"));
			});	
		}
		
		beanList = List.toJSList(beanList);//把list转成JS的List对象		
		curr_bean = null;		
		table.setBeanList(beanList,"td_list");//设置列表内容的样式
		table.show();	

		table.getCol("c_name").each(function(i){
			if(i>0)
			{
				$(this).css({"text-align":"left"});	
			}
		});
		table.getCol("s_name").each(function(i){
			if(i>0)
			{
				$(this).css({"text-align":"left"});	
			}
		});	

		
		$("#current_time").text(getCurrentTime(m.get("file_path")));
		$("#times").text(s_tiem.substring(0,10) +" -- "+ e_tiem.substring(0,10));		

		$("#count_num").text(count_num);
		$("#category_names").text(category_name);

		var str = "按照创建时间统计";
		if(time_type == "publish_time")
			str = "按照发布时间统计";
		if(time_type == "start_time")
			str = "按照直播时间统计";
		$("#time_type").text(str);
		
		Init_InfoTable(table.table_name);	
	}
		
	//防两边时间不一致，当前时间从服务器返回的文件数上取
	function getCurrentTime(str)
	{
		return str.replace(/.*\/(.*?)\.xls/,"$1").replace(/^([\d]{4})([\d]{2})([\d]{2})([\d]{2})([\d]{2})([\d]{2})/,"$1-$2-$3 $4:$5:$6");
	}
	//-->
	</SCRIPT>
	
</head> 
<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>	
		<td class="fromTabs">
			<input id="addButton" name="btn1" type="button" onclick="window.location.href='surveyCount.jsp?site_id=<%=site_id%>'" value="统计条件" />	
			<input id="excel_out" name="btn1" type="button" value="导出" />
			<span class="blank3"></span>
		</td>
		<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
			
		</td>		
	</tr>
</table>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>
			<td height="20px" align="center" width="120px">报表生成时间：&#160;</td>
			<td  height="20px" align="left" >&#160;<span id="current_time"></span></td>
		 </tr>
		 <tr>
			<td height="20px" align="center" rowspan="4" width="120px">统计条件：&#160;</td>
			<td height="20px" align="left" >&#160;统计方式：&#160;<span id="time_type"></span>
			</td>
		 </tr>
		 <tr>			
			<td height="20px" align="left" >&#160;日期范围：&#160;<span id="times"></span>
			</td>
		 </tr>
		 <tr>
			<td height="20px" align="left" >&#160;显示条数：&#160;<span id="count_num"></span>					
			</td>
		 </tr>
		 <tr>
			<td height="20px" align="left" >&#160;问卷分类：<span id="category_names"></span>				
			</td>
		 </tr>
</table>	
<div id="table"></div>
<div id="turn" style="display:none"></div>
</div>
</body>

</html> 

