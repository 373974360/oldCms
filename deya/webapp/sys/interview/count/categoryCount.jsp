<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
String site_id = request.getParameter("site_id");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>访谈类型统计</title> 
	<jsp:include page="../../include/include_tools.jsp"/>
	<script src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>	
    
	<SCRIPT language="javascript">
	<!--
	var site_id = "<%=site_id%>";
   $(document).ready(function () {	
		$("#start_time").val(getCurrentDate());
		$("#end_time").val(getCurrentDate());
		
		initTable();			
		//fnSetC();	
		initButtomStyle();
	}); 	
	

	var SubjectRPC = jsonrpc.SubjectRPC;		

	var beanList = null;
	var table = null;
	var con = "";
	function initTable(){
		table = new Table();
		table.table_name = "count_table";	
		var colsMap = new Map();	
		var colsList = new List();	
		
		colsList.add(setTitleClos("category_name","访谈模型","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
		colsList.add(setTitleClos("sub_count","创建主题数","15%","","",""));	　
		colsList.add(setTitleClos("publish_count","发布主题数","15%","","",""));
		colsList.add(setTitleClos("recommend_count","推荐主题数","15%","","",""));
		colsList.add(setTitleClos("user_count","访问人数","15%","","",""));
		colsList.add(setTitleClos("chat_count","网友发言数","15%","","",""));
		
		table.setColsList(colsList);
		table.setAllColsList(colsList);				
		table.enableSort=false;//禁用表头排序
		table.onSortChange = showList;
		table.show("table");//里面参数为外层div的id

		Init_InfoTable(table.table_name);
	}

	function showList(){	
		var m = SubjectRPC.getSubjectCategoryCount($("#start_time").val(),$("#end_time").val(),$("#time_type").val(),site_id);
		if(m != null)
		{
			m = Map.toJSMap(m);
			beanList =  new List();
			for(var i=0;i<m.keySet().length;i++)
			{
				if(m.keySet()[i] != "file_path")
					beanList.add(m.get(m.keySet()[i]));
			}

			$("#excel_out").click(function(){
				window.open(m.get("file_path"));
			});	
		}
		
		beanList = List.toJSList(beanList);//把list转成JS的List对象		
		curr_bean = null;		
		table.setBeanList(beanList,"td_list");//设置列表内容的样式
		table.show();	

		table.getCol("category_name").each(function(i){
			if(i>0)
			{
				$(this).css({"text-align":"left"});	
			}
		});
			
	
		Init_InfoTable(table.table_name);
	}
		
	function fnSetC()
	{
		var s_time = $("#start_time").val();
		var e_time = $("#end_time").val();
		if(s_time != "" && e_time != "")
		{
			s_time = s_time+" 00:00";
			e_time = e_time+" 23:29";

			var val=new Validator();
			val.checkDataTimeAfter(s_time,e_time,"统计时间","start_time");	
			if(!val.getResult()){
				return;
			}				
			showList();
		}
		else
		{
			top.msgWargin("请输入时间范围");
		}
	}
	//-->
	</SCRIPT>
	
</head> 
<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>	
			<td>
			
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
				<select id="time_type" name="time_type"><option value="apply_time">按照创建时间统计</option><option value="publish_time">按照发布时间统计</option><option value="start_time">按照直播时间统计</option></select>
		   <input class="Wdate" type="text" name="start_time" id="start_time" size="11" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="true" style="width:100">&nbsp;--&nbsp;<input class="Wdate" type="text" name="end_time" id="end_time" size="11" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="true" style="width:100">
			<input id="btn1" name="btn1" type="button" onclick="fnSetC()" value="统计" />
			<input id="excel_out" name="btn1" type="button" value="导出" />
			<span class="blank3"></span>
			</td>		
		</tr>
	</table>
	<span class="blank3"></span>
	<div id="table"></div><!-- 列表DIV -->
	
</div>
</body>

</html> 

