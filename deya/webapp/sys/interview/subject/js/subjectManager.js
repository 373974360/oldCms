	var SubjectRPC = jsonrpc.SubjectRPC;
	var subjectBean = new Bean("com.deya.wcm.bean.interview.SubjectBean",true);

	var tp = new TurnPage();
	var beanList = null;
	var val=new Validator();
	var table = new Table();	
	table.table_name = "table";
	var con = "";

/********** 显示table begin*************/	

	function initTable(){
			
		var colsMap = new Map();	
		var colsList = new List();	
		
		//colsList.add(setTitleClos("sub_id","ID","250px","","",""));
		colsList.add(setTitleClos("sub_name","访谈主题","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
		colsList.add(setTitleClos("actionCol","操作","50px","","",""));	　
		colsList.add(setTitleClos("start_time","直播时间","100px","","",""));
		colsList.add(setTitleClos("category_name","访谈模型","100px","","",""));		
		colsList.add(setTitleClos("status2","访谈状态","60px","","",""));	
		colsList.add(setTitleClos("audit_status","审核状态","60px","","",""));
		colsList.add(setTitleClos("publish_status","发布状态","60px","","",""));		
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

		beanList = SubjectRPC.getSubjectManagerList(con,start,pageSize,site_id);//第一个参数为站点ID，暂时默认为空		
		beanList = List.toJSList(beanList);//把list转成JS的List对象		
		//alert(toJSON(beanList.get(0)));//然后就可以调用get(),add()等方法了
		//alert(beanList);
		curr_bean = null;		
		table.setBeanList(beanList,"td_list");//设置列表内容的样式
		table.show();	

		table.getCol("sub_name").each(function(i){
			if(i>0)
			{
				$(this).css({"text-align":"left"});	
				$(this).html('<span onclick="showUpdatePate(\'admin\','+beanList.get(i-1).id+')" style="cursor:pointer">'+$(this).html()+'</span>');
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

		table.getCol("status2").each(function(i){
			if(i>0)
			{
				if(beanList.get(i-1).status == 0)
					$(this).html("预告状态&#160;");
				if(beanList.get(i-1).status == 1)
					$(this).html("直播状态&#160;");
				if(beanList.get(i-1).status == 2)
					$(this).html("历史状态&#160;");
			}
		});

		table.getCol("audit_status").each(function(i){
			if(i>0)
			{
				if(beanList.get(i-1).audit_status == 0)
					$(this).html("待审核&#160;");
				if(beanList.get(i-1).audit_status == 1)
					$(this).html("审核通过&#160;");
				if(beanList.get(i-1).audit_status == -1)
					$(this).html("审核不通过&#160;");
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
				if(beanList.get(i-1).publish_status == 2)
					$(this).html("已归档&#160;");
			}
		});

		table.getCol("actionCol").each(function(i){
			if(i>0)
			{	
				$(this).html('<img src="../../images/update.png" alt="修改" onclick="showUpdatePate(\'main\','+beanList.get(i-1).id+','+beanList.get(i-1).audit_status+')">&nbsp;<img src="../../images/delete2.png" alt="删除" onclick="deleteSubject('+beanList.get(i-1).id+','+beanList.get(i-1).audit_status+')">');				
			}			
		});	
		
		Init_InfoTable(table.table_name);
	}
	
	function showTurnPage(){	
		
		tp.total = SubjectRPC.getSubjectManagerCount(con,site_id);		
		tp.show("turn","");
		tp.onPageChange = showList;
	}
/********** 显示table end*************/


/**********************发布操作　开始*************************************/
function updateSubjectStatus(operName,message,fields,status_flag)
{
	var current_status_flag = eval("beanList.get(carent_cell_num)."+fields);
	
	if(current_status_flag != status_flag)
	{		
		if(fields == "publish_status" && beanList.get(carent_cell_num).audit_status != 1)
		{
			msgWargin("该主题未通过审核，不能进行发布状态操作");
			return;
		}

		if(fields == "status" && beanList.get(carent_cell_num).audit_status != 1)
		{
			msgWargin("该主题未通过审核，不能进行访谈状态操作");
			return;
		}

		if(fields == "audit_status"  && beanList.get(carent_cell_num).publish_status > 0)
		{
			msgWargin("该主题已设置发布状态，不能进行审核状态操作");
			return;
		}

		if(fields == "audit_status"  && beanList.get(carent_cell_num).status != 0)
		{
			msgWargin("该主题已设置访谈状态，不能进行审核状态操作");
			return;
		}


		if(SubjectRPC.updateSubjectStatus(beanList.get(carent_cell_num).id,fields,status_flag,operName,message,LoginUserBean.user_id,LoginUserBean.user_id))
		{
			msgAlert(operName+"状态设置成功");
			eval("beanList.get(carent_cell_num)."+fields+" = "+status_flag);
			if(fields == "status") 
				fields = "status2";
			
			$(table.getCol(fields)[carent_cell_num+1]).html(message+"&#160;");
		}
		else
			msgWargin(operName+"状态设置失败，请重新设置");
	}
	else
	{
		msgAlert("该主题已是"+message+"状态");
	}
}
/**********************发布操作　结束*************************************/


/**********************撤消操作　开始*************************************/

/**********************撤消操作　结束*************************************/



/**********************删除操作　开始*************************************/
//批量删除分类
function batchDelSubject()
{
	if (table.getSelecteCount() < 1) {
			msgAlert("请选择要删除的记录！");
			return;
	}

	msgConfirm("确认删除选中记录？","batchDelSubjects()");
	
}

function batchDelSubjects()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	
	if(SubjectRPC.deleteSubject(selectIDS,LoginUserBean.user_id))
	{
		msgAlert("访谈主题"+WCMLang.Delete_success);
		showTurnPage();
		showList();	
	}
	else
		msgWargin("访谈主题"+WCMLang.Delete_fail);
}


//删除分类
function deleteSubject(id,audit_status)
{
	msgConfirm("确认删除选中记录？","deleteSubjectOper("+id+")")
}

function deleteSubjectOper(id)
{
	if(SubjectRPC.deleteSubject(id,LoginUserBean.user_id))
	{
		msgAlert("访谈主题"+WCMLang.Delete_success);
		showTurnPage();
		showList();	
	}
	else
		msgWargin("访谈主题"+WCMLang.Delete_fail);
}
/**********************删除操作　结束*************************************/
function fnOpenLiveManager()
{
	var ls = table.getSelecteBeans();
	if(ls == null || ls.size() == 0)
	{
		msgAlert("请选择要操作的记录");
		return;
	}
	else
	{
		if(ls.size() > 1)
		{
			msgAlert("只能选择一条记录进行管理操作");
			return;
		}
		window.open("../live/liveManager.jsp?site_id="+site_id+"&id="+ls.get(0).id+"&sub_id="+ls.get(0).sub_id);
	}	
}

function openLiveManager()
{
	window.open("../live/liveManager.jsp?id="+beanList.get(carent_cell_num).id+"&sub_id="+beanList.get(carent_cell_num).sub_id);
}
/**********************推荐操作　结束*************************************/
function updateRecommend(flag)
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	if(SubjectRPC.updateSubjectRecommend(selectIDS,flag))
	{
		msgAlert("推荐状态设置成功");
		showTurnPage();
		showList();	
	}
	else
		msgWargin("推荐状态设置失败，请重新设置");
}
/**********************推荐操作　结束*************************************/

/**********************历史记录维护　结束*************************************/
function fnHistoryRecord()
{
	var ls = table.getSelecteBeans();
	if(ls.get(0).status != 2)
	{
		msgWargin("该主题未设置为历史状态，不能进行历史记录维护");
		return;
	}
	else
		window.location.href = 'history_record.jsp?sub_id='+ls.get(0).sub_id +'&site_id=' + site_id;
}
/**********************历史记录维护　结束*************************************/

//搜索
function roleSearchHandl(obj)
{
	var con_value = $(obj).parent().find("#searchkey").val();
	if(con_value.trim() == "" ||  con_value == null)
	{
		msgAlert(WCMLang.Search_empty);
		return;
	}
	con = " and sub_name like '%"+con_value+"%'";	
	showList();
	showTurnPage();
}