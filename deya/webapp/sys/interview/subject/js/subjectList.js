	var SubjectRPC = jsonrpc.SubjectRPC;
	var subjectBean = new Bean("com.deya.wcm.bean.interview.SubjectBean",true);

	var tp = null;
	var beanList = null;
	var val=new Validator();
	var table = new Table();	
	table.table_name = "table";
	
	var con = "";

/********** 显示table begin*************/	

	function initTable(){
		var colsMap = new Map();	
		var colsList = new List();	
		
		colsList.add(setTitleClos("sub_id","ID","250px","","",""));
		colsList.add(setTitleClos("sub_name","访谈主题","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
		colsList.add(setTitleClos("actionCol","操作","60px","","",""));	　
		colsList.add(setTitleClos("start_time","直播时间","100px","","",""));
		colsList.add(setTitleClos("category_name","访谈模型","120px","","",""));	
		colsList.add(setTitleClos("submit_status","提交状态","80px","","",""));　
		colsList.add(setTitleClos("audit_status","审核状态","80px","","",""));
		colsList.add(setTitleClos("publish_status","发布状态","80px","","",""));	
		colsList.add(setTitleClos("apply_time","创建时间","80px","","",""));		
		
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

		beanList = SubjectRPC.getSubjectList(con,start,pageSize,LoginUserBean.user_id);//第一个参数为站点ID，暂时默认为空
		beanList = List.toJSList(beanList);//把list转成JS的List对象		
		curr_bean = null;		
		table.setBeanList(beanList,"td_list");//设置列表内容的样式
		table.show();	

		table.getCol("sub_name").each(function(i){
			if(i>0)
			{
				$(this).css({"text-align":"left"});	
				$(this).html('<span onclick="view_subject(\'\','+beanList.get(i-1).id+')" style="cursor:pointer">'+$(this).html()+'</span>');
			}
		});

		table.getCol("apply_time").each(function(i){
			if(i>0)
			{				
				$(this).html(cutTimes(beanList.get(i-1).apply_time));
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
		
		table.getCol("submit_status").each(function(i){
			if(i>0)
			{
				if(beanList.get(i-1).submit_status == 0)
					$(this).html("未提交&#160;");
				if(beanList.get(i-1).submit_status == 1)
					$(this).html("已提交&#160;");				
			}
		});		

		table.getCol("actionCol").each(function(i){
			if(i>0)
			{	
				$(this).html('<img src="../../images/update.png" alt="修改" onclick="showUpdatePate(\'\','+beanList.get(i-1).id+','+beanList.get(i-1).audit_status+')">&nbsp;<img src="../../images/delete2.png" alt="删除" onclick="deleteSubject('+beanList.get(i-1).id+','+beanList.get(i-1).audit_status+')">');				
							
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
		Init_InfoTable(table.table_name);
	}
	
	function showTurnPage(){
		tp = new TurnPage();
	
		tp.total = SubjectRPC.getSubjectCount(con,LoginUserBean.user_id);
		tp.show("turn","");
		tp.onPageChange = showList;
	}
/********** 显示table end*************/


/**********************提交操作　开始*************************************/
function fnSubjectSubmit()
{
	if(beanList.get(carent_cell_num).submit_status == 0)
	{
		if(SubjectRPC.subjectSubmit(beanList.get(carent_cell_num).id,1,LoginUserBean.user_id))
		{
			alertN("提交成功");
			beanList.get(carent_cell_num).submit_status = 0;
			$(table.getCol("submit_status")[carent_cell_num+1]).html("已提交&#160;");
		}
		else
			msgWargin("提交失败，请重新提交");
	}
	else
		msgAlert("该记录已经提交");
}

function bathSubjectSubmits()
{
	var selectIDS = table.getSelecteCheckboxValue("id");

	selectIDS = fnCheckRepeatStatus(selectIDS,"submit_status",1);

	if(selectIDS == "")
	{
		msgAlert("所选的记录已是提交状态，无需再提交");
		return;
	}

	if(SubjectRPC.subjectSubmit(selectIDS,1,LoginUserBean.user_id))
	{
		msgAlert("提交成功");
		showTurnPage();
		showList();	
	}
	else
		msgWargin("提交失败，请重新提交");
}

/**********************提交操作　结束*************************************/



/**********************删除操作　开始*************************************/
//批量删除分类
function batchDelSubjects()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	selectIDS = fnCheckRepeatStatus(selectIDS,"audit_status",1);

	if(selectIDS == "")
	{
		msgWargin("所选的记录已通过审核，不能删除");
		return;
	}
	
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
	if(audit_status != 1)
	{
		msgConfirm("确认删除选中记录？","deleteSubjectOper("+id+")");
	}
	else
		msgWargin("该记录已通过审核，不能再删除");
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
