	var SurveyRPC = jsonrpc.SurveyRPC;
	
	var SurveyBean = new Bean("com.deya.wcm.bean.survey.SurveyBean",true);
	var SurveySub = new Bean("com.deya.wcm.bean.survey.SurveySub",true);
	var SurveySuvItem = new Bean("com.deya.wcm.bean.survey.SurveySuvItem",true);
	var ChildItem = new Bean("com.deya.wcm.bean.survey.SurveyChildItem",true);
	var StatisticsBean = new Bean("com.deya.wcm.bean.survey.StatisticsBean",true);

	var tp = null;
	var beanList = null;
	var table = null;
	var turn_page=15;
	var user_name = "";
	var user_id = "";

/********** 显示table begin*************/	

	function initTable(){
		table = new Table();	
		var colsMap = new Map();	
		var colsList = new List();	
		
		colsList.add(setTitleClos("s_name","标题","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
		colsList.add(setTitleClos("actionCol","操作","60px","","",""));	
		colsList.add(setTitleClos("answer_counts","答卷(份)","100px","","",""));　
		colsList.add(setTitleClos("start_time","起始时间","120px","","",""));
		colsList.add(setTitleClos("end_time","结束时间","120px","","",""));
		colsList.add(setTitleClos("publish_status","发布状态","80px","","",""));	
			
		
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

		beanList = SurveyRPC.getStatisticsList("",start,pageSize);//第一个参数为站点ID，暂时默认为空		
		beanList = List.toJSList(beanList);//把list转成JS的List对象		
		//alert(toJSON(beanList.get(0)));//然后就可以调用get(),add()等方法了
		//alert(beanList);
		curr_bean = null;		
		table.setBeanList(beanList,"td_list");//设置列表内容的样式
		table.show();	

		table.getCol("s_name").each(function(i){
			if(i>0)
			{
				$(this).css({"text-align":"left"});	
				$(this).html('<span onclick="view_survey(\''+beanList.get(i-1).s_id+'\')" style="cursor:pointer">'+$(this).html()+'</span>');
			}
		});

		table.getCol("publish_status").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
			if(i>0)
			{
				if(beanList.get(i-1).publish_status == 0)
					$(this).html("未发布&#160;");
				if(beanList.get(i-1).publish_status == 1)
					$(this).html("已发布&#160;");		
				if(beanList.get(i-1).publish_status == -1)
					$(this).html("已撤消&#160;");	
			}
		});	
			

		table.getCol("actionCol").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
			if(i>0)
			{	
				$(this).parent().mouseover(changeListStyle);//该行的鼠标事件
				$(this).parent().mouseout(changeListStyleU);
				$(this).html('<span id="act_span_'+(i-1)+'" onclick="showOperateMenu('+(i-1)+',this)" style="cursor:pointer;">操作&#160;</span>');
				$("#act_span_"+(i-1)).click(function(e){
				   showOperateMenu(e,i-1);
				})				
			}			
		});	
		
		$("#contents_list").css("height",div_height);
	}
	
	function showTurnPage(){
		tp = new TurnPage();
		tp.pageSize = turn_page;
		tp.total = SurveyRPC.getStatisticsCount("");		
		tp.show("turn","");
		tp.onPageChange = showList;
	}
/********** 显示table end*************/
function openSubject()
{
	window.location.href = "statisticsSubject.html?sid="+beanList.get(carent_cell_num).s_id;
}

function openAnswer()
{
	window.location.href = "answerList.html?sid="+beanList.get(carent_cell_num).s_id;
}

function view_survey(s_id)
{
	window.open("preview_survey.html?sid="+s_id);
}

//得到题型名称
function getSubjectTypeName(types)
{
	var arr_name = [];
	arr_name["radioList"] = "列表单选题";
	arr_name["checkboxList"] = "多选题";
	arr_name["selectOnly"] = "下拉框";
	arr_name["matrix"] = "矩阵题";
	arr_name["textareas"] = "文本框";
	arr_name["uploadfile"] = "上传文件";
	arr_name["voteRadio"] = "投票单选题";
	arr_name["voteCheckbox"] = "投票多选题";
	arr_name["scale"] = "量表题";
	
	return arr_name[types];
}
