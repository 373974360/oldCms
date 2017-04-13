	var SubjectRPC = jsonrpc.SubjectRPC;
	var subjectReleInfo = new Bean("com.deya.wcm.bean.interview.SubReleInfo",true);
	
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
		
		colsList.add(setTitleClos("info_name","信息标题","","","",""));//英文名，显示名，宽，高，样式名，点击事件　		
		colsList.add(setTitleClos("actionCol","操作","60px","","",""));		
		colsList.add(setTitleClos("publish_flag","发布状态","120px","","",""));	
		//colsList.add(setTitleClos("add_user","创建人","80px","","",""));	
		colsList.add(setTitleClos("sort_col","排序","100px","","",""));		
		
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

		beanList = SubjectRPC.getReleInfoListBySub_id("",start,pageSize,sub_id);//第一个参数为站点ID，暂时默认为空		
		beanList = List.toJSList(beanList);//把list转成JS的List对象		
		//alert(toJSON(beanList.get(0)));//然后就可以调用get(),add()等方法了
		//alert(beanList);
		curr_bean = null;		
		table.setBeanList(beanList,"td_list");//设置列表内容的样式
		table.show();	

		table.getCol("info_name").each(function(i){
			if(i>0)
			{
				$(this).css({"text-align":"left"});		
				$(this).html('<span onclick="view_releInfo2('+beanList.get(i-1).id+')" style="cursor:pointer">'+$(this).html()+'</span>');
			}
		});

		table.getCol("add_time").each(function(i){
			if(i>0)
			{				
				$(this).html(cutTimes(beanList.get(i-1).add_time));
			}
		});
		
		table.getCol("publish_flag").each(function(i){
			if(i>0)
			{
				if(beanList.get(i-1).publish_flag == 0)
					$(this).html("未发布&#160;");
				if(beanList.get(i-1).publish_flag == 1)
					$(this).html("已发布&#160;");
				if(beanList.get(i-1).publish_flag == -1)
					$(this).html("已撤消&#160;");
			}
		});

		table.getCol("actionCol").each(function(i){
			if(i>0)
			{	
				$(this).html('<img src="../../images/update.png" alt="修改" onclick="showUpdatePate('+beanList.get(i-1).id+')">&nbsp;<img src="../../images/delete2.png" alt="删除" onclick="deleteSubReleInfo('+beanList.get(i-1).id+')">');
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
		
		tp.total = SubjectRPC.getReleInfoCountBySub_id(sub_id);	
		tp.show("turn","simple");
		tp.onPageChange = showList;
	}	
/********** 显示table end*************/

/********** 获取FCK值 start*************/
	function getFckeditorValue()
	{	/*
		var oEditor = FCKeditorAPI.GetInstance('content'); 
		var c = oEditor.GetXHTML(true);
		$("#content").val(c);*/

	}

/********** 获取FCK值e end*************/

/**********************添加操作　开始*************************************/
function addReleInfo()
{
	if(subm_sta == 1 && (tn == "" || tn == null))
	{
		msgWargin("该主题已通过审核，不能再进行操作！");
		return;
	}
	//window.location.href = "add_releInfo.jsp?sub_id="+sub_id;
	addTab(true,"/sys/interview/subject/add_releInfo.jsp?sub_id="+sub_id+"&topnum="+curTabIndex,"修改信息");
}


//表单完整性验正
function validateFn(obj)
{
	var val=new Validator();
	val.checkEmpty(obj.info_name,"标题");
	val.checkStrLength(obj.info_name,"标题",150);
	if(obj.info_type=="html")
	{
		val.checkEmpty(obj.content,"HTML内容");
	}
	else
	{
		val.checkEmpty(obj.curl,"URL地址");
	}
	
	if(!val.getResult()){
		val.showError("error_div");
		return false;
	}
	return true;
}

function saveReleInfo()
{
	var bean = BeanUtil.getCopy(subjectReleInfo);
	$("#subReleInfo").autoBind(bean);
    bean.content = getV(contentId);
	bean.sub_id = sub_id;
	
	if(!standard_checkInputInfo("subReleInfo"))
	{
		return;
	}
	bean.add_user = LoginUserBean.user_id;
	if(SubjectRPC.insertReleInfo(bean))
	{
		msgAlert("信息"+WCMLang.Add_success);
		getCurrentFrameObj(topnum).showTurnPage();
		getCurrentFrameObj(topnum).showList();
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("信息"+WCMLang.Add_fail);
	}
}
/**********************添加操作　结束*************************************/


/**********************修改操作　开始*************************************/
function fnUpdateReleInfo()
{
	if(subm_sta == 1 && (tn == "" || tn == null))
	{
		msgWargin("该主题已通过审核，不能再进行修改操作！");
		return;
	}
	var selectIDS = table.getSelecteCheckboxValue("id");
	if(selectIDS == "")
	{
		msgAlert("请选择要修改的记录");
		return;
	}
	else
	{
		if(selectIDS.split(",").length > 1)
		{
			msgAlert("只能选择一条记录进行修改操作");
			return;
		}
		//window.location.href = "add_releInfo.jsp?id="+selectIDS;
		addTab(true,"/sys/interview/subject/add_releInfo.jsp?id="+selectIDS+"&topnum="+curTabIndex,"修改信息");
	}
}

function showUpdatePate(id)
{	
	if(subm_sta == 1 && (tn == "" || tn == null))
	{
		msgWargin("该主题已通过审核，不能再进行修改操作！");
		return;
	}
	//window.location.href = "add_releInfo.jsp?id="+id;
	addTab(true,"/sys/interview/subject/add_releInfo.jsp?id="+id+"&topnum="+curTabIndex,"修改信息");
}

function updateReleInfo()
{
	var bean = BeanUtil.getCopy(subjectReleInfo);
	$("#subReleInfo").autoBind(bean);
    bean.content = getV(contentId);
		
	if(!standard_checkInputInfo("subReleInfo"))
	{
		return;
	}	
	bean.update_user = LoginUserBean.user_id;
	if(SubjectRPC.updateReleInfo(bean))
	{
		msgAlert("信息"+WCMLang.Add_success);
		getCurrentFrameObj(topnum).showTurnPage();
		getCurrentFrameObj(topnum).showList();
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("信息"+WCMLang.Add_fail);
	}
}
/**********************修改操作　结束*************************************/

/**********************删除操作　开始*************************************/
//批量删除信息
function batchDelSubReleInfo()
{
	if(subm_sta == 1 && (tn == "" || tn == null))
	{
		msgWargin("该主题已通过审核，不能再进行删除操作！");
		return;
	}
	if (table.getSelecteCount() < 1) {
			msgAlert("请选择要删除的记录！");
			return;
	}

	msgConfirm("确认删除选中记录？","batchDelSubReleInfos()");
	
}

function batchDelSubReleInfos()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	
	if(SubjectRPC.deleteReleInfo(selectIDS,LoginUserBean.user_id))
	{
		msgAlert("删除成功");
		
		showList();	
	}
	else
		msgWargin("删除失败，请重新删除");
}

//删除信息
function deleteSubReleInfo(id)
{
	if(subm_sta == 1 && (tn == "" || tn == null))
	{
		msgWargin("该主题已通过审核，不能再进行删除操作！");
		return;
	}
	msgConfirm("确认删除选中记录？","deleteSubReleInfos("+id+")")
}

function deleteSubReleInfos(id)
{
	if(SubjectRPC.deleteReleInfo(id,LoginUserBean.user_id))
	{
		msgAlert("删除成功");
		showList();	
	}
	else
		msgWargin("删除失败，请重新删除");
}
/**********************删除操作　结束*************************************/
/**********************发布操作　开始*************************************/
//批量发布
function batchPublishSubReleInfo(publish_flag,msg)
{
	if(subm_sta == 1 && (tn == "" || tn == null))
	{
		msgWargin("该主题已通过审核，不能再进行"+msg+"操作！");
		return;
	}
	var selectIDS = table.getSelecteCheckboxValue("id");
	if(selectIDS == "")
	{
		msgWargin("请选择要"+msg+"的记录");
		return;
	}
	
	if(SubjectRPC.publishReleInfo(selectIDS,LoginUserBean.user_id,publish_flag))
	{
		msgAlert(msg+"成功");
		
		showList();	
	}
	else
		msgWargin(msg+"失败，请重新"+msg);
}


//单条发布
function publishSubReleInfo(publish_flag,msg)
{
	if(SubjectRPC.publishReleInfo(beanList.get(carent_cell_num).id,LoginUserBean.user_id,publish_flag))
	{
		msgAlert(msg+"成功");
		//showList();	
		eval("beanList.get(carent_cell_num).publish_flag = "+publish_flag);
		if(publish_flag==1)
		{
			$(table.getCol("publish_flag")[carent_cell_num+1]).html("已发布&#160;");
		}
		else
		{
			$(table.getCol("publish_flag")[carent_cell_num+1]).html("已撤消&#160;");
		}
		
	}
	else
		msgWargin(msg+"失败，请重新"+msg);
}


/**********************发布排序　结束*************************************/


/**********************保存排序　开始*************************************/
function saveSort()
{
	if(subm_sta == 1 && (tn == "" || tn == null))
	{
		msgWargin("该主题已通过审核，不能再进行排序操作！");
		return;
	}

	var ids = table.getAllCheckboxValue("id");
	if(ids != "" && ids != null)
	{
		if(SubjectRPC.saveReleInfoSort(ids))
		{
			msgAlert(WCMLang.Sort_success);
		}
		else
		{
			msgWargin(WCMLang.Sort_fail);
			return;
		}
	}
}
/**********************保存排序　结束*************************************/

function view_releInfo2(id)
{	
	//addTab(true,'/sys/interview/subject/view_releInfo.jsp?id='+id,'查看信息内容');
	//window.location.href = '/sys/interview/subject/add_releInfo.jsp?id='+id;
	addTab(true,"/sys/interview/subject/add_releInfo.jsp?id="+id+"&topnum="+curTabIndex,"查看信息");
}