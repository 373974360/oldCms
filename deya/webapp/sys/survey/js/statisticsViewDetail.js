	var SurveyRPC = jsonrpc.SurveyRPC;
	
	var SurveyBean = new Bean("com.deya.wcm.bean.survey.SurveyBean",true);
	var SurveySub = new Bean("com.deya.wcm.bean.survey.SurveySub",true);
	var SurveySuvItem = new Bean("com.deya.wcm.bean.survey.SurveySuvItem",true);
	var ChildItem = new Bean("com.deya.wcm.bean.survey.SurveyChildItem",true);
	var StatisticsBean = new Bean("com.deya.wcm.bean.survey.StatisticsBean",true);

	var val=new Validator();
	var tp = new TurnPage();
	var beanList = null;
	var table = new Table();	
	table.table_name = "table";	

/********** 显示table begin*************/	

	function initTable(){
		table = new Table();	
		var colsMap = new Map();	
		var colsList = new List();	
		
		colsList.add(setTitleClos("user_name","用户名","100px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
		colsList.add(setTitleClos("answer_time","答卷时间","130px","","",""));
		colsList.add(setTitleClos("item_text","答案文本","","","",""));	
		colsList.add(setTitleClos("actionCol","查看答案","60px","","",""));		
		
		table.setColsList(colsList);
		table.setAllColsList(colsList);				
		table.enableSort=false;//禁用表头排序
		table.checkBox = false;
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

		beanList = SurveyRPC.getItemTextList(s_id, item_id, item_value, is_text,start,pageSize,search_con);//第一个参数为站点ID，暂时默认为空		
		beanList = List.toJSList(beanList);//把list转成JS的List对象		
		//alert(toJSON(beanList.get(0)));//然后就可以调用get(),add()等方法了
		//alert(beanList);
		curr_bean = null;		
		table.setBeanList(beanList,"td_list");//设置列表内容的样式
		table.show();
		
		table.getCol("item_text").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
			if(i>0)
			{	
				$(this).css("text-align","left");
							
			}			
		});	

		if(subject_type == "uploadfile")
		{
			table.getCol("item_text").each(function(i){
				if(i>0)
				{	
					$(this).html('<a href="'+$(this).text()+'" target="_blank">'+$(this).text()+'</a>');
								
				}			
			});	
		}

		table.getCol("actionCol").each(function(i){
			if(i>0)
			{					
				$(this).html('<span id="act_span_'+(i-1)+'" onclick="showAnswerDetail('+(i-1)+')" style="cursor:pointer;">查看&#160;</span>');								
			}			
		});	
		
		Init_InfoTable(table.table_name);	
	}
	
	function showTurnPage(){		
		tp.total = SurveyRPC.getItemTextCount(s_id, item_id, item_value, is_text,search_con);			
		tp.show("turn","");
		tp.onPageChange = showList;
	}
/********** 显示table end*************/
function showAnswerDetail(num)
{
	window.open("view_answerDetail.jsp?a_id="+beanList.get(num).answer_id+"&sid="+s_id);
}