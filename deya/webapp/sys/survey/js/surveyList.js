	var SurveyRPC = jsonrpc.SurveyRPC;
	var SurveyCategoryRPC = jsonrpc.SurveyCategoryRPC;
	
	var SurveyCategory = new Bean("com.deya.wcm.bean.survey.SurveyCategory",true);
	var SurveyBean = new Bean("com.deya.wcm.bean.survey.SurveyBean",true);
	var SurveySub = new Bean("com.deya.wcm.bean.survey.SurveySub",true);
	var SurveySuvItem = new Bean("com.deya.wcm.bean.survey.SurveySuvItem",true);
	var ChildItem = new Bean("com.deya.wcm.bean.survey.SurveyChildItem",true);

	var search_con = "";//查询条件
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
		
		colsList.add(setTitleClos("s_name","标题","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
		colsList.add(setTitleClos("actionCol","操作","40px","","",""));
		colsList.add(setTitleClos("c_name","分类名称","100px","","",""));
		colsList.add(setTitleClos("survey_status","调查状态","60px","","",""));
		colsList.add(setTitleClos("start_time","起始时间","80px","","",""));
		colsList.add(setTitleClos("end_time","结束时间","80px","","",""));
		colsList.add(setTitleClos("publish_status","发布状态","60px","","",""));	
		//colsList.add(setTitleClos("add_time","创建时间","80px","","",""));		
		colsList.add(setTitleClos("recommend_flag","推荐","30px","","",""));
		
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

		beanList = SurveyRPC.getSurveyList(search_con,start,pageSize,site_id);//第一个参数为站点ID，暂时默认为空		
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
				$(this).html('<span onclick="view_survey(\''+beanList.get(i-1).s_id+'\')" style="cursor:pointer">'+$(this).text()+'</span>');
			}
		});

		table.getCol("add_time").each(function(i){
			if(i>0)
			{				
				$(this).html(cutTimes(beanList.get(i-1).add_time));
			}
		});

		table.getCol("recommend_flag").each(function(i){
			if(i>0)
			{
				if(beanList.get(i-1).recommend_flag == 0)
					$(this).html("&#160;");
				if(beanList.get(i-1).recommend_flag == 1)
					$(this).html("是");
			}
		});

		table.getCol("survey_status").each(function(i){
			if(i>0)
			{
				if(beanList.get(i-1).publish_status == 1)
				{
					if(beanList.get(i-1).end_time == "")
					{
						$(this).html("进行中");
					}
					else
					{
						if(!judgeDateTime(current_date+" 00:00",beanList.get(i-1).end_time+" 23:59"))
						{
							$(this).html("已截止");
						}
						else
							$(this).html("进行中");
					}
				}
				else
					$(this).html("设计中");
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
				$(this).html('<img src="../images/update.png" alt="修改" onclick="showUpdatePage(\''+beanList.get(i-1).s_id+'\')">&nbsp;<img src="../images/delete2.png" alt="删除" onclick="deleteSurveyA('+beanList.get(i-1).id+')">');								
			}			
		});	
		
		Init_InfoTable(table.table_name);	
	}
	
	function showTurnPage(){		
		tp.total = SurveyRPC.getSurveyCount(search_con,site_id);		
		tp.show("turn","");
		tp.onPageChange = showList;
	}
/********** 显示table end*************/

/**********************添加操作　开始*************************************/
function fnAddSurvey()
{
	window.location.href = "add_survey.jsp?site_id="+site_id;
}

function saveSurvey()
{
	$("#max_item_num").val(sd.item_num);//设置当前选项最大值
	$("#max_sort_num").val(sd.sort_num);//设置当前题目最大值
	var bean = BeanUtil.getCopy(SurveyBean);
	$("#survey").autoBind(bean);
	$("#tools_button_div").remove();
	
	bean.survey_content = $("#design_div").html();
	bean.description = $("#s_description").val();

	var val=new Validator();	
	$("div #menu_survey:eq(2)").click();
	val.checkEmpty(bean.s_name,"问卷标题","s_name");
	val.checkStrLength(bean.s_name,"问卷标题",300,"s_name");
	if(!val.getResult()){		
		return;
	}
	
	$("div #subject_head").click();
	$("div #menu_survey:eq(1)").click();
	val.checkUnsignedInt($("#spacing_time").val(),"重复提交间隔时间",true,"spacing_time");	
	val.checkUnsignedInt(bean.ip_fre,"同一IP提交次数",false,"ip_fre");	
	val.checkStrLength(bean.ip_fre,"同一IP提交次数",3,"ip_fre");
	val.checkDataAfter(bean.start_time,bean.end_time,"有效时间","start_time");
	
	if(!val.getResult()){		
		return;
	}	

	if($("#spacing_time").val().trim() != "")
	{
		bean.spacing_interval = $("#spacing_type").val()+$("#spacing_time").val();
	}

	if(bean.s_id.trim() == "")
	{
		bean.add_user = LoginUserBean.user_id;
		bean.site_id = site_id;
		if(SurveyRPC.insertSurvey(bean,getSurveySubList()))
		{
			msgAlert("保存成功");
			window.location.href="surveyList.jsp?site_id="+site_id
		}
	}
	else
	{
		bean.update_user = LoginUserBean.user_id;
		if(SurveyRPC.updateSurvey(bean,getSurveySubList()))
		{
			msgAlert("保存成功");
			window.location.href="surveyList.jsp?site_id="+site_id
		}
	}
}

function getSurveySubList()
{
	var subList = new List();
	
	$("#design_div > div").each(function(i){
		
		if(i > 0)
		{
			var sSub = BeanUtil.getCopy(SurveySub);
			sSub.subject_type = $(this).attr("id").replace(/(.*?)_.*?$/,"$1");			
			sSub.sort = $(this).find("#sort_num").text().replace(/[^\d]/ig,"");
			sSub.sub_name = $(this).find("#title_span").html();

			if($(this).find("#description_div").length > 1)
				sSub.explains = $(this).find("#description_div").html();

			var is_required = $(this).attr("is_required");	
			if(is_required == "true")
				sSub.is_required = 1;

			//取选项值
			if(sSub.subject_type == "radioList" || sSub.subject_type == "checkboxList")
			{
				var itemList = new List();
				var subItem = BeanUtil.getCopy(SurveySuvItem);
				var input_type = sSub.subject_type.replace("List","").toLowerCase();

				subItem.item_id = $(this).find(":"+input_type).attr("id");				
				
				var childList = new List();
				$(this).find("li").each(function(i){
					var childItem = BeanUtil.getCopy(ChildItem);
					
					childItem.item_id = subItem.item_id;
					childItem.item_num = $(this).find(":"+input_type).val();
					childItem.item_name = $(this).find("span").html();
					childItem.sort = i;
				
					if($(this).find(":text").length > 0)
					{
						childItem.is_text = 1;
						childItem.text_value = $(this).find(":text").val();
					}
					childList.add(childItem);
				});
				subItem.childList = childList;
				itemList.add(subItem);
				sSub.itemList = itemList;
				
			}				
			if(sSub.subject_type == "selectOnly")
			{
				var itemList = new List();
				var subItem = BeanUtil.getCopy(SurveySuvItem);
				subItem.item_id = $(this).find("select").attr("id");				

				var childList = new List();
				$(this).find("select option").each(function(i){
					if(i>0)
					{
						var childItem = BeanUtil.getCopy(ChildItem);						
						childItem.item_id = subItem.item_id;
						childItem.item_num = $(this).attr("value");
						childItem.item_name = $(this).text();
						childItem.sort = i;						
						childList.add(childItem);
					}
				});
				subItem.childList = childList;
				itemList.add(subItem);
				sSub.itemList = itemList;
				
			}
			if(sSub.subject_type == "matrix")
			{
				var itemList = new List();
				var first_tr_obj = $(this).find("tr:first");
				$(this).find("tr").each(function(i){					
					if(i>0)
					{//取出横向的数据
						var subItem = BeanUtil.getCopy(SurveySuvItem);
						subItem.item_id = $(this).find("input").attr("id");
						subItem.item_name = $(this).find("td:first").text();
						subItem.sort = i;	

						var childList = new List();
						$(this).find("td").each(function(j){
							if(j>0 && j<first_tr_obj.find("td").length-1)
							{//取出纵向的数据  子选项	
							    var childItem = BeanUtil.getCopy(ChildItem);

								childItem.item_id = subItem.item_id;
								childItem.item_num = $(this).find("input").val();
								childItem.item_name = first_tr_obj.find("td:nth-child("+(j+1)+")").text();
								childItem.score = first_tr_obj.find("td:nth-child("+(j+1)+")").attr("score");
								
								childItem.sort = j;
								childList.add(childItem);
							}
						})
						subItem.childList = childList;
						itemList.add(subItem);
					}
				})
				sSub.itemList = itemList;
			}
			if(sSub.subject_type == "scale")
			{
				var itemList = new List();
				var subItem = BeanUtil.getCopy(SurveySuvItem);
				subItem.item_id = $(this).find("LI").attr("id");				

				var childList = new List();
				$(this).find("li").each(function(i){
					var childItem = BeanUtil.getCopy(ChildItem);
					
					childItem.item_id = subItem.item_id;
					childItem.item_num = $(this).attr("value");
					childItem.item_name = $(this).attr("title");
					childItem.score = $(this).attr("score");
					childItem.sort = i;				
					
					childList.add(childItem);
				});
				subItem.childList = childList;
				itemList.add(subItem);
				sSub.itemList = itemList;
			}
			if(sSub.subject_type == "textareas")
			{
				var itemList = new List();
				var subItem = BeanUtil.getCopy(SurveySuvItem);
				subItem.item_id = $(this).find("textarea").attr("id");	
				var childList = new List();
				var childItem = BeanUtil.getCopy(ChildItem);
				childItem.item_id = subItem.item_id;
				childItem.is_text = 1;
				childItem.sort = 0;	
				childList.add(childItem);

				subItem.childList = childList;
				itemList.add(subItem);
				sSub.itemList = itemList;

			}
			if(sSub.subject_type == "uploadfile")
			{
				var itemList = new List();
				var subItem = BeanUtil.getCopy(SurveySuvItem);
				subItem.item_id = $(this).find(":file").attr("id");	
				var childList = new List();
				var childItem = BeanUtil.getCopy(ChildItem);
				childItem.item_id = subItem.item_id;
				childItem.is_text = 1;
				childItem.sort = 0;	
				childList.add(childItem);

				subItem.childList = childList;
				itemList.add(subItem);
				sSub.itemList = itemList;
			}
			if(sSub.subject_type == "voteRadio" || sSub.subject_type == "voteCheckbox")
			{
				var itemList = new List();
				var subItem = BeanUtil.getCopy(SurveySuvItem);
				var input_type = sSub.subject_type.replace("vote","").toLowerCase();

				subItem.item_id = $(this).find(":"+input_type).attr("id");				

				var childList = new List();
				$(this).find("tr").each(function(i){
					var childItem = BeanUtil.getCopy(ChildItem);
					
					childItem.item_id = subItem.item_id;
					childItem.item_num = $(this).find(":"+input_type).val();
					childItem.item_name = $(this).find("td:first span").html();
					childItem.sort = i;
				
					if($(this).find(":text").length > 0)
					{
						childItem.is_text = 1;
						childItem.text_value = $(this).find(":text").val();
					}
					childList.add(childItem);
				});
				subItem.childList = childList;
				itemList.add(subItem);
				sSub.itemList = itemList;
				
			}	
			subList.add(sSub);			
		}
	});
	
	return subList;
}
/**********************添加操作　结束*************************************/

/**********************发布操作　开始*************************************/
function publishSurvey(publish_status)
{
	var message = "发布";
	if(publish_status == -1)
		message = "撤销";

	if(SurveyRPC.publishSurvey(beanList.get(carent_cell_num).id,publish_status,LoginUserBean.user_id))
	{
		msgAlert(message+"成功");
		showList();	
	}
	else
		msgWargin(message+"失败，请重新"+message);
}

function batchPublishSurvey(publish_status)
{
	var message = "发布";
	if(publish_status == -1)
		message = "撤销";

	var selectIDS = table.getSelecteCheckboxValue("id");
	if(selectIDS == "")
	{
		msgWargin("请选择要"+message+"的记录");
		return;
	}

	var selectIDS = table.getSelecteCheckboxValue("id");
	
	if(SurveyRPC.publishSurvey(selectIDS,publish_status,LoginUserBean.user_id))
	{
		msgAlert(WCMLang.Publish_success);
		
		showList();	
	}
	else
		msgWargin(WCMLang.Publish_success);
}
/**********************发布操作　结束*************************************/

/**********************删除操作　开始*************************************/
function deleteSurveyA(id)
{
	msgConfirm("确认删除选中记录？","deleteSurvey("+id+")");
}

function deleteSurvey(id)
{
	if(SurveyRPC.deleteSurvey(id,LoginUserBean.user_id))
	{
		msgAlert("问卷调查主题"+WCMLang.Delete_success);
		showTurnPage();
		showList();		
	}
	else
		msgWargin("问卷调查主题"+WCMLang.Delete_fail);
}

function batchDelSurvey()
{
	if (table.getSelecteCount() < 1) {
			msgWargin("请选择要删除的记录！");
			return;
	}

	msgConfirm("确认删除选中记录？","batchDelSurveyHandl()");
}

function batchDelSurveyHandl()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	
	if(SurveyRPC.deleteSurvey(selectIDS,LoginUserBean.user_id))
	{
		msgAlert("问卷调查主题"+WCMLang.Delete_success);
		showTurnPage();
		showList();	
	}
	else
		msgWargin("问卷调查主题"+WCMLang.Delete_fail);
}
/**********************删除操作　结束*************************************/

/**********************修改操作　开始*************************************/
function showUpdatePage(s_id)
{
	window.location.href = "add_survey.jsp?site_id="+site_id+"&s_id="+s_id;
}

function fnUpdateSurvey()
{
	var selectIDS = table.getSelecteCheckboxValue("s_id");
	if(selectIDS == "")
	{
		msgWargin("请选择要修改的记录");
		return;
	}
	else
	{
		if(selectIDS.split(",").length > 1)
		{
			msgWargin("只能选择一条记录进行修改操作");
			return;
		}
		else
		{			
			window.location.href = "add_survey.jsp?site_id="+site_id+"&s_id="+selectIDS;
		}
	}
}


/**********************修改操作　结束*************************************/
function showAnswer()
{
	var selectIDS = table.getSelecteCheckboxValue("s_id");
	window.open('/sys/survey/answerPage.jsp?sid='+selectIDS);	
}

function view_survey(s_id)
{
	window.open("preview_survey.jsp?sid="+s_id);
}

function openSubject()
{
	//window.location.href = "statisticsSubject.jsp?sid="+beanList.get(carent_cell_num).s_id;
	var selectIDS = table.getSelecteCheckboxValue("s_id");
	addTab(true,'/sys/survey/statisticsSubject.jsp?sid='+selectIDS,'数据统计');
}

function openAnswer()
{
	//window.location.href = "answerList.jsp?sid="+beanList.get(carent_cell_num).s_id;
	var selectIDS = table.getSelecteCheckboxValue("s_id");
	addTab(true,'/sys/survey/answerList.jsp?sid='+selectIDS,'查看答卷');
}
/**********************属性设置操作　开始*************************************/
function showSurveyAttr(flag)
{
	if(flag == "bath")
	{
		var selectIDS = table.getSelecteCheckboxValue("s_id");
		addTab(true,'/sys/survey/setSurveyAttr.jsp?s_id='+selectIDS+"&top_index="+curTabIndex,'问卷调查属性设置');
	}	
}

function setSurveyAttr()
{
	var bean = BeanUtil.getCopy(SurveyBean);
	$("#survey").autoBind(bean);
	
	var val=new Validator();	
	val.checkDataAfter(bean.start_time,bean.end_time,"有效时间",3000);
	val.checkUnsignedInt($("#spacing_time").val(),"重复提交间隔时间",true);	
	val.checkUnsignedInt(bean.ip_fre,"同一IP提交次数",false);		
	val.checkStrLength(bean.model_path,"前台展示模板",240);
	val.checkStrLength(bean.pic_path,"焦点图片",240);
	
	if(!val.getResult()){
		val.showError("error_div");
		return;
	}		

	if($("#fileName").val() != "")
	{
		if(!checkImgFile($("#fileName").val()))
		{
			return; 
		}
		$("#upoloadform").submit();
	}else
	{
		setSurveyAttrHandl();
	}
}



function setSurveyAttrHandl()
{
	getFckeditorValue();
	var bean = BeanUtil.getCopy(SurveyBean);
	$("#survey").autoBind(bean);

	if(!standard_checkInputInfo("survey"))
	{
		return;
	}
	
	val.checkDataAfter(bean.start_time,bean.end_time,"结束时间","end_time");
	if(!val.getResult()){		
		return;
	}

	if($("#spacing_time").val().trim() != "")
	{
		bean.spacing_interval = $("#spacing_type").val()+$("#spacing_time").val();
	}

	//bean.verdict = $("#verdict").val();
	bean.update_user = LoginUserBean.user_id;
	if(SurveyRPC.setSurveyAttr(bean))
	{
		msgAlert("保存成功");
		getCurrentFrameObj(top_index).showList();
		tab_colseOnclick(curTabIndex)
	}else
	{
		msgAlert("保存失败,请重新提交");
		return;
	}	
}

function getFckeditorValue()
{/*
	var oEditor = FCKeditorAPI.GetInstance('verdict'); 
	var c = oEditor.GetXHTML(true);
	$("#verdict").val(c);*/
	//$("#verdict").val(KE.html("verdict"));
}

/**********************属性设置操作　结束*************************************/
//得到问卷分类名称
function getSurveyCategoryName()
{
	var cList = SurveyCategoryRPC.getAllSurveyCategoryName(site_id);
	cList = List.toJSList(cList);
	if(cList != null && cList.size() > 0)
	{
		$("#category_id").addOptions(cList,"category_id","c_name");
	}
	else
	{
		history.go(-1);
		msgWargin("请先添加问卷调查分类");
		
	}
}

/**********************查询操作　开始*************************************/
function fnSearch()
{	
	//addTab(true,'/sys/survey/search_survey.jsp','问卷调查查询');
	OpenModalWindow("问卷调查查询","/sys/survey/search_survey.jsp",455,245);
}

function searchHandl(str)
{
	search_con = str;
	showTurnPage();
	showList();	
}
/**********************查询操作　结束*************************************/
/**********************推荐操作　结束*************************************/
function updateRecommend(flag)
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	if(SurveyRPC.updateSurveyRecommend(selectIDS,flag))
	{
		msgAlert("推荐状态设置成功");
		showList();	
	}
	else
		msgWargin("推荐状态设置失败，请重新设置");
}
/**********************推荐操作　结束*************************************/

//搜索
function roleSearchHandl(obj)
{
	var con_value = $(obj).parent().find("#searchkey").val();
	if(con_value.trim() == "" ||  con_value == null)
	{
		msgAlert(WCMLang.Search_empty);
		return;
	}
	search_con = " and cs.s_name like '%"+con_value+"%'";	
	showList();
	showTurnPage();
}
