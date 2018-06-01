	var SurveyRPC = jsonrpc.SurveyRPC;
	
	var SurveyAnswer = new Bean("com.deya.wcm.bean.survey.SurveyAnswer",true);

	var val=new Validator();
	var tp = new TurnPage();
	var beanList = null;
	var table = new Table();	
	table.table_name = "table";	
	var con = "";
	var source = "";

/********** 显示table begin*************/	

	function initTable(){
		table = new Table();	
		var colsMap = new Map();	
		var colsList = new List();	
		colsList.add(setTitleClos("ip","来源IP","120px","","",""));		
		colsList.add(setTitleClos("user_name","用户名","150px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
		colsList.add(setTitleClos("answer_time","答卷时间","120px","","",""));　		
		colsList.add(setTitleClos("actionCol","查看答卷","120px","","",""));
		colsList.add(setTitleClos("blankCol","&#160;","","","",""));
		
		table.setColsList(colsList);
		table.setAllColsList(colsList);				
		table.enableSort=false;//禁用表头排序
		table.onSortChange = showList;
		table.show("table");//里面参数为外层div的id
	}

	function showList(){
		start = tp.getStart();
		var pageSize = tp.pageSize;		
		var sortCol = table.sortCol;
		var sortType = table.sortType;	
		
		if(sortCol == "" || sortCol == null)
		{
			sortCol = "id";
			sortType = "desc";
		}

		beanList = SurveyRPC.getAnswerList(s_id,source,con,start,pageSize);//第一个参数为站点ID，暂时默认为空
		beanList = List.toJSList(beanList);//把list转成JS的List对象		
		//alert(toJSON(beanList.get(0)));//然后就可以调用get(),add()等方法了
		//alert(beanList);
		curr_bean = null;		
		table.setBeanList(beanList,"td_list");//设置列表内容的样式
		table.show();	
	
		

		table.getCol("actionCol").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
			if(i>0)
			{					
				$(this).html('<span id="act_span_'+(i-1)+'" onclick="showAnswerDetail(\''+beanList.get(i-1).answer_id+'\')" style="cursor:pointer;">查看答卷&#160;</span>');							
			}			
		});	
		
		Init_InfoTable(table.table_name);
	}
	
	function showTurnPage(){		
		tp.total = SurveyRPC.getAnswerListCount(s_id,source,con);
		tp.show("turn","");
		tp.onPageChange = showList;
	}
/********** 显示table end*************/
function showAnswerDetail(answer_id)
{
	window.open('/sys/survey/view_answerDetail.jsp?a_id='+answer_id+'&sid='+s_id);
}