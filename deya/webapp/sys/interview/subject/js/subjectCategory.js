	var SubjectRPC = jsonrpc.SubjectRPC;
	var subjectCategory = new Bean("com.deya.wcm.bean.interview.SubjectCategory",true);

	var tp = null;
	var beanList = null;
	var val=new Validator();
	var table = new Table();	
	table.table_name = "table";;
	var turn_page=15;
	

/********** 显示table begin*************/	

	function initTable(){
		
		var colsMap = new Map();	
		var colsList = new List();	
		
		//colsList.add(setTitleClos("category_id","ID","250px","","",""));
		colsList.add(setTitleClos("category_name","访谈模型","250px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
		colsList.add(setTitleClos("actionCol","操作","60px","","",""));	　
		colsList.add(setTitleClos("publish_status","发布状态","80px","","",""));	
		colsList.add(setTitleClos("add_time","创建时间","130px","","",""));
		colsList.add(setTitleClos("sort_col","排序","100px","","",""));
		colsList.add(setTitleClos("blank_col","&#160;","","","",""));		
		
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

		beanList = SubjectRPC.getSubCategoryList("",start,pageSize,site_id);//第一个参数为站点ID，暂时默认为空		
		beanList = List.toJSList(beanList);//把list转成JS的List对象		
		//alert(toJSON(beanList.get(0)));//然后就可以调用get(),add()等方法了
		//alert(beanList);
		curr_bean = null;		
		table.setBeanList(beanList,"td_list");//设置列表内容的样式
		table.show();	
		

		table.getCol("category_name").each(function(i){
			if(i>0)
			{
				$(this).css({"text-align":"left"});	
				$(this).html('<span onclick="showUpdatePate('+beanList.get(i-1).id+')" style="cursor:pointer">'+$(this).html()+'</span>');
			}
		});

		table.getCol("publish_status").each(function(i){
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

		table.getCol("actionCol").each(function(i){
			if(i>0)
			{	
				
				$(this).html('<img src="../../images/update.png" alt="修改" onclick="showUpdatePate('+beanList.get(i-1).id+')">&nbsp;<img src="../../images/delete2.png" alt="删除" onclick="deleteSubCategory('+beanList.get(i-1).id+')">');				
				//publishSubCategory()已发布 cancelSubCategory()已撤消
			}			
		});	

		table.getCol("sort_col").each(function(i){	
			if(i>0)
			{			
				$(this).html(getSortColStr());			
			}
		});
		Init_InfoTable(table.table_name);
	}
	
	function showTurnPage(){
		tp = new TurnPage();		
		tp.total = SubjectRPC.getSubCategoryCount("",site_id);
				
		tp.show("turn","simple");	
	}
/********** 显示table end*************/
/**********************添加操作　开始*************************************/
function saveSCategory()
{
	var bean = BeanUtil.getCopy(subjectCategory);
	$("#subCategory").autoBind(bean);	
		
	if(!standard_checkInputInfo("jcxx_tab"))
	{
		return;
	}	
	bean.site_id = site_id;
	if(SubjectRPC.insertSubCategory(bean))
	{
		msgAlert("访谈模型"+WCMLang.Add_success);
		window.location.href = "subjectCategory.jsp?site_id="+site_id;
	}
	else
	{
		msgWargin("访谈模型"+WCMLang.Add_fail);
	}
}
/**********************添加操作　结束*************************************/


/**********************修改操作　开始*************************************/
function fnUpdate()
{
	var selectIDS = table.getSelecteCheckboxValue("id");	
	window.location.href = "add_category.jsp?site_id="+site_id+"&id="+selectIDS;	
}

function showUpdatePate(id)
{
	window.location.href = "add_category.jsp?site_id="+site_id+"&id="+id;
}

function updateSCategory()
{
	var bean = BeanUtil.getCopy(subjectCategory);
	$("#subCategory").autoBind(bean);		
	
	
	if(!standard_checkInputInfo("jcxx_tab"))
	{
		return;
	}
	
	bean.update_user = "";
	if(SubjectRPC.updateSubCategory(bean))
	{
		msgAlert("访谈模型"+WCMLang.Add_success);
		window.location.href = "subjectCategory.jsp?site_id="+site_id;
	}
	else
	{
		msgWargin("访谈模型"+WCMLang.Add_fail);
	}
}
/**********************修改操作　结束*************************************/

/**********************发布操作　开始*************************************/
function publishSubCategory()
{
	if(beanList.get(carent_cell_num).publish_status != 1)
	{
		if(SubjectRPC.updateSubCategoryState(beanList.get(carent_cell_num).id,1,""))
		{
			msgAlert("发布状态设置成功");
			beanList.get(carent_cell_num).publish_status = 1;
			$(table.getCol("publish_status")[carent_cell_num+1]).html("已发布&#160;");
		}
		else
			msgWargin("发布状态设置失败，请重新设置");
	}
	else
	{
		msgAlert("该分类已是已发布状态");
	}
}

function batchPubSubCategorys()
{
	var selectIDS = table.getSelecteCheckboxValue("id");

	/*过滤已发布过的信息 开始*/
	selectIDS = fnCheckRepeatStatus(selectIDS,1);
	/*过滤已发布过的信息 结束*/

	if(selectIDS == "")
	{
		msgAlert("所选的记录已是发布状态，无需再发布");
		return;
	}
	
	if(SubjectRPC.updateSubCategoryState(selectIDS,1,""))
	{
		msgAlert("发布状态设置成功");
		showTurnPage();
		showList();	
	}
	else
		msgWargin("发布状态设置失败，请重新设置");
}

//验证是否有状态重复的ID
function fnCheckRepeatStatus(selectIDS,flag)
{
	selectIDS = ","+selectIDS+",";
	var tempA = selectIDS.split(",");
	for(var i=0;i<tempA.length;i++)
	{
		for(var j=0;j<beanList.size();j++)
		{
			if(beanList.get(j).id == tempA[i] && beanList.get(j).publish_status == flag)
			{
				var reg = "/,"+tempA[i]+",/";
				selectIDS = selectIDS.replace(eval(reg),",");
			}
		}
	}

	var tempB = selectIDS.split(",");
	var tempStr = "";
	for(var i=0;i<tempB.length;i++)
	{
		if(tempB[i] != "" && tempB[i] != null)
			tempStr += ","+tempB[i];
	}

	return tempStr.substring(1);
}
/**********************发布操作　结束*************************************/


/**********************撤消操作　开始*************************************/
function cancelSubCategory()
{	
	if(beanList.get(carent_cell_num).publish_status == 1)
	{
		if(SubjectRPC.updateSubCategoryState(beanList.get(carent_cell_num).id,-1,""))
		{
			msgAlert("发布状态设置成功");
			beanList.get(carent_cell_num).publish_status = 0;
			$(table.getCol("publish_status")[carent_cell_num+1]).html("已撤消&#160;");
		}
		else
			msgWargin("发布状态设置失败，请重新设置");
	}	
}

function batchCelSubCategorys()
{
	var selectIDS = table.getSelecteCheckboxValue("id");

	/*过滤已撤消过的信息 开始*/
	selectIDS = fnCheckRepeatStatus(selectIDS,-1);
	/*过滤已撤消过的信息 结束*/

	if(selectIDS.replace(",,","") == "")
	{
		msgAlert("所选的记录已是撤消状态，无需再撤消");
		return;
	}
	
	if(SubjectRPC.updateSubCategoryState(selectIDS,-1,""))
	{
		msgAlert("发布状态设置成功");
		showTurnPage();
		showList();	
	}
	else
		msgWargin("发布状态设置失败，请重新设置");
}
/**********************撤消操作　结束*************************************/



/**********************删除操作　开始*************************************/
//批量删除分类
function batchDelSubCategorys()
{
	var selectIDS = table.getSelecteCheckboxValue("id");

	var tempA = selectIDS.split(",")
	for(var i=0;i<tempA.length;i++)
	{
		if(SubjectRPC.getSubjectCountByCategoryID(tempA[i]) != 0)
		{
			msgWargin("该模型下有主题记录，请先删除主题记录再删除模型");
			return;
		}
	}
	
	if(SubjectRPC.deleteSubCategory(selectIDS,""))
	{
		msgAlert("访谈模型"+WCMLang.Delete_success);
		showTurnPage();
		showList();	
	}
	else
		msgWargin("访谈模型"+WCMLang.Delete_fail);
}

//删除分类
function deleteSubCategory(id)
{
	msgConfirm("确认删除选中记录？","delSubCategory("+id+")")
}

function delSubCategory(id)
{
	if(SubjectRPC.getSubjectCountByCategoryID(id) != 0)
	{
		msgWargin("该模型下有主题记录，请先删除主题记录再删除模型");
		return;
	}

	if(SubjectRPC.deleteSubCategory(id,""))
	{
		msgAlert("访谈模型"+WCMLang.Delete_success);
		showTurnPage();
		showList();	
	}
	else
		msgWargin("访谈模型"+WCMLang.Delete_fail);
}
/**********************删除操作　结束*************************************/


/**********************保存排序　开始*************************************/
function saveSort()
{
	var ids = table.getAllCheckboxValue("id");

	if(ids != "")
	{
		if(SubjectRPC.saveSubCategorySort(ids))
		{
			msgAlert(WCMLang.Sort_success);
		}else{
			msgWargin(WCMLang.Sort_fail);
		}
		
	}
}
/**********************保存排序　结束*************************************/

//查看分类
function view_categoryByID(id)
{		
	//addTab(true,'/sys/interview/subject/view_category.jsp?id='+id,'访谈模型信息');
	window.location.href = '/sys/interview/subject/add_category.jsp?id='+id;
}