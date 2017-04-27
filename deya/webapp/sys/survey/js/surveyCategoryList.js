	var SurveyCategoryRPC = jsonrpc.SurveyCategoryRPC;
	
	var SurveyCategory = new Bean("com.deya.wcm.bean.survey.SurveyCategory",true);

	var val=new Validator();
	var tp = new TurnPage();
	var beanList = null;
	var table = new Table();	
	table.table_name = "table";	

/********** 显示table begin*************/	

	function reloadCategoryList()
	{		
		showList();	
		showTurnPage();
	}

	function initTable(){
		table = new Table();	
		var colsMap = new Map();	
		var colsList = new List();	
		
		colsList.add(setTitleClos("category_id","ID","250px","","",""));
		colsList.add(setTitleClos("c_name","问卷分类名称","150px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
		colsList.add(setTitleClos("actionCol","操作","60px","","",""));	
		colsList.add(setTitleClos("publish_status","发布状态","80px","","",""));
		colsList.add(setTitleClos("publish_time","发布时间","120px","","",""));
		colsList.add(setTitleClos("add_time","创建时间","80px","","",""));		
		
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

		beanList = SurveyCategoryRPC.getSurveyCategoryList("",start,pageSize,site_id);//第一个参数为站点ID，暂时默认为空		
		beanList = List.toJSList(beanList);//把list转成JS的List对象		
		//alert(toJSON(beanList.get(0)));//然后就可以调用get(),add()等方法了
		//alert(beanList);
		curr_bean = null;		
		table.setBeanList(beanList,"td_list");//设置列表内容的样式
		table.show();	

		table.getCol("category_id").each(function(i){			
				$(this).css({"text-align":"left"});				
		});
		
		table.getCol("c_name").each(function(i){
			if(i>0)
			{
				$(this).css({"text-align":"left"});	
				$(this).html('<span onclick="showUpdatePage(\''+beanList.get(i-1).category_id+'\')" style="cursor:pointer">'+$(this).html()+'</span>');
			}
		});

		table.getCol("add_time").each(function(i){
			if(i>0)
			{				
				$(this).html(cutTimes(beanList.get(i-1).add_time));
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
				$(this).html('<img src="../images/update.png" alt="修改" onclick="showUpdatePage(\''+beanList.get(i-1).category_id+'\')">&nbsp;<img src="../images/delete2.png" alt="删除" onclick="deleteSurveyCategory('+beanList.get(i-1).id+')">');							
			}			
		});	
		
		Init_InfoTable(table.table_name);	
	}
	
	function showTurnPage(){
		tp.total = SurveyCategoryRPC.getSurveyCategoryCount("",site_id);
		tp.show("turn","");
		tp.onPageChange = showList;
	}
/********** 显示table end*************/

/**********************添加操作　开始*************************************/
function fnAddSurveyCategory()
{
	window.location.href = "add_surveyCategory.jsp?site_id="+site_id;
	//OpenModalWindow("修改问卷分类","/sys/survey/add_surveyCategory.jsp",440,305);
}

function saveSurveyCategory()
{
	var bean = BeanUtil.getCopy(SurveyCategory);
	$("#surveyCategory").autoBind(bean);
	
	var result = true;
	if(!standard_checkInputInfo("surveyCategory"))
	{
		result = false;
	}

    if($("#template_list_path_name").val()=='')
	{
		val.showError("template_list_path_name","请选择列表页模板");
		result = false;
	}

	if($("#template_content_path_name").val()=='')
	{
		val.showError("template_content_path_name","请选择内容页模板");
		result = false;
	}
    
	if($("#template_result_path_name").val()=='')
	{
		val.showError("template_result_path_name","请选择查看结果页模板");
		result = false;
	}
    if(!result){
		return;
	}

	if(bean.category_id.trim() == "")
	{		
		bean.add_user = parent.LoginUserBean.user_id;
		bean.site_id = site_id;
		if(SurveyCategoryRPC.insertSurveyCategory(bean))
		{
			parent.msgAlert("问卷分类"+WCMLang.Add_success);
			window.location.href='surveyCategoryList.jsp?site_id='+site_id
		}else
		{
            parent.msgWargin("问卷分类"+WCMLang.Add_fail);
		}
	}
	else
	{
		bean.update_user = LoginUserBean.user_id;
		if(SurveyCategoryRPC.updateSurveyCategory(bean))
		{
            parent.msgAlert("问卷分类"+WCMLang.Add_success);
			window.location.href='surveyCategoryList.jsp?site_id='+site_id
		}else
		{
            parent.msgWargin("问卷分类"+WCMLang.Add_fail);
		}
	}
}


/**********************添加操作　结束*************************************/

/**********************发布操作　开始*************************************/
function publishSurveyCategory(publish_status)
{
	var message = "发布";
	if(publish_status == -1)
		message = "撤销";

	if(SurveyCategoryRPC.updateSurveyCategoryPublishStatus(beanList.get(carent_cell_num).id,publish_status,LoginUserBean.user_id))
	{
		msgAlert(message+"成功");
		showList();	
	}
	else
		msgWargin(message+"失败，请重新"+message);
}

function batchPublishSurveyCategory(publish_status)
{
	var message = "发布";
	if(publish_status == -1)
		message = "撤销";

	var selectIDS = table.getSelecteCheckboxValue("id");
	
	if(SurveyCategoryRPC.updateSurveyCategoryPublishStatus(selectIDS,publish_status,LoginUserBean.user_id))
	{
		msgAlert(WCMLang.Publish_success);
		showList();	
	}
	else
		msgWargin(WCMLang.Publish_fail);
}
/**********************发布操作　结束*************************************/

/**********************删除操作　开始*************************************/
function deleteSurveyCategory(id)
{
	if(SurveyCategoryRPC.getSurveyCountByCategoryID(id) != 0)
	{
		msgWargin("该分类下有问卷记录<br>请先删除问卷记录再删除分类");
		return;
	}

	if(SurveyCategoryRPC.deleteSurveyCategory(id,LoginUserBean.user_id))
	{
		msgAlert("删除成功");
		reloadCategoryList();	
	}
	else
		msgWargin("删除失败，请重新删除");
}

function batchDelSurveyCategory()
{
	var selectIDS = table.getSelecteCheckboxValue("id");

	var tempA = selectIDS.split(",")
	for(var i=0;i<tempA.length;i++)
	{
		if(SurveyCategoryRPC.getSurveyCountByCategoryID(tempA[i]) != 0)
		{
			msgWargin("该分类下有问卷记录<br>请先删除问卷记录再删除分类");
			return;
		}
	}

	if(selectIDS == "")
	{
		msgAlert("请选择要删除的记录");
		return;
	}

	confirmN("确认删除选中记录？","batchDelSurveyCategoryHandl()");
}

function batchDelSurveyCategoryHandl()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	var tempA = selectIDS.split(",")
	for(var i=0;i<tempA.length;i++)
	{
		if(SurveyCategoryRPC.getSurveyCountByCategoryID(tempA[i]) != 0)
		{
			msgWargin("该分类下有问卷记录<br>请先删除问卷记录再删除分类");
			return;
		}
	}

	if(SurveyCategoryRPC.deleteSurveyCategory(selectIDS,LoginUserBean.user_id))
	{
		msgAlert("问卷分类"+WCMLang.Delete_success);
		reloadCategoryList();	
	}
	else
		msgWargin("问卷分类"+WCMLang.Delete_fail);
}
/**********************删除操作　结束*************************************/

/**********************修改操作　开始*************************************/
function showUpdatePage(c_id)
{
	//OpenModalWindow("修改问卷分类","/sys/survey/add_surveyCategory.jsp?c_id="+c_id,440,305);
	window.location.href = "/sys/survey/add_surveyCategory.jsp?c_id="+c_id+"&site_id="+site_id;
}

function fnUpdateSurveyCategory()
{
	var selectIDS = table.getSelecteCheckboxValue("category_id");	
	//OpenModalWindow("修改问卷分类","/sys/survey/add_surveyCategory.jsp?c_id="+selectIDS,440,305);
	window.location.href = "/sys/survey/add_surveyCategory.jsp?c_id="+selectIDS+"&site_id="+site_id;
}
/**********************修改操作　结束*************************************/


