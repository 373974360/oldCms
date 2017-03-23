	var SubjectRPC = jsonrpc.SubjectRPC;
	var subjectActor = new Bean("com.deya.wcm.bean.interview.SubjectActor",true);

	var val=new Validator();
	var tp = new TurnPage();
	var beanList = null;
	var table = new Table();	
	table.table_name = "table";		

/********** 显示table begin*************/	

	function initTable(){
		var colsMap = new Map();	
		var colsList = new List();	
		
		colsList.add(setTitleClos("actor_name","嘉宾名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　		
		colsList.add(setTitleClos("actionCol","操作","60px","","",""));	　
		colsList.add(setTitleClos("sex","性别","60px","","",""));	
		colsList.add(setTitleClos("a_post","职务","100px","","",""));
		colsList.add(setTitleClos("sort_col","排序","100px","","",""));				
		
		table.setColsList(colsList);
		table.setAllColsList(colsList);				
		table.enableSort=false;//禁用表头排序
		table.onSortChange = showList;
		table.show("table");//里面参数为外层div的id
	}

	function showList(){			
		var sortCol = table.sortCol;
		var sortType = table.sortType;	
		
		if(sortCol == "" || sortCol == null)
		{
			sortCol = "id";
			sortType = "desc";
		}

		beanList = SubjectRPC.getActorList("",sub_id);//第一个参数为站点ID，暂时默认为空		
		beanList = List.toJSList(beanList);//把list转成JS的List对象		
		//alert(toJSON(beanList.get(0)));//然后就可以调用get(),add()等方法了
		//alert(beanList);
		curr_bean = null;		
		table.setBeanList(beanList,"td_list");//设置列表内容的样式
		table.show();	
		tp.total = beanList.size();	

		table.getCol("actor_name").each(function(i){
			if(i>0)
			{
				$(this).css({"text-align":"left"});	
				$(this).html('<span onclick="view_actor2('+beanList.get(i-1).id+')" style="cursor:pointer">'+$(this).html()+'</span>');
			}
		});		

		table.getCol("add_time").each(function(i){
			if(i>0)
			{				
				$(this).html(cutTimes(beanList.get(i-1).add_time));
			}
		});

		table.getCol("actionCol").each(function(i){
			if(i>0)
			{	
				$(this).html('<img src="../../images/update.png" alt="修改" onclick="showUpdatePate('+beanList.get(i-1).id+')">&nbsp;<img src="../../images/delete2.png" alt="删除" onclick="deleteSubActor('+beanList.get(i-1).id+')">');				
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
			
		tp.show("turn","simple");
		tp.onPageChange = showList;
	}
	
	
/********** 显示table end*************/
/**********************添加操作　开始*************************************/
function addActor()
{
	if(subm_sta == 1 && (tn == "" || tn == null))
	{
		msgWargin("该主题已通过审核，不能再进行操作！");
		return;
	}
	//window.location.href = "add_actor.jsp?sub_id="+sub_id;
	addTab(true,"/sys/interview/subject/add_actor.jsp?sub_id="+sub_id+"&topnum="+curTabIndex,"维护嘉宾信息");
}



function saveActorBefore()
{	
	if(!checkImgFile($("#pic_path").val()))
	{
		return; 
	}
	
	if($("#id").val() == 0)
		saveActor()
	else
		updateActor();	
}

//得到上传图片地址后插入信息
function returnUploadValue(furl)
{
	if(furl != "" && furl != null)
	{
		$("#pic_path").val(furl);
		if($("#id").val() == 0)
			saveActor()
		else
			updateActor();
	}
	else
		alertWar("上传失败，请重新提交");
}

//判断图片格式
function checkImgFile(files) 
{
	if(files.replace(/(^\s*)|(\s*$)/g,"") != "")
	{ 
		if(files.indexOf(".") == -1) 
		{ 			
			msgWargin("上传的文档图片格式不对，只允许上传 jpg，jpeg，gif，png 等格式的文件，请重新上传！");
			return false; 
		}
		var strtype = files.match(/\.([^\.]+)(\?|$)/)[1];
		
		if (strtype.toLowerCase()=="jpg"||strtype.toLowerCase()=="gif"||strtype.toLowerCase()=="bmp"||strtype.toLowerCase()=="png") 
		{ 
			return true; 
		} 
		else
		{ 		
			msgWargin("上传的文档图片格式不对，只允许上传 jpg，jpeg，gif，png 等格式的文件，请重新上传！");
			return false;
		}
	}
	return true;
}

function saveActor()
{
	var bean = BeanUtil.getCopy(subjectActor);
	$("#subActor").autoBind(bean);
	bean.sub_id = sub_id;
	
	if(!standard_checkInputInfo("subActor"))
	{
		return;
	}

	bean.add_user = LoginUserBean.user_id;
	if(SubjectRPC.insertSubActor(bean))
	{
		msgAlert("嘉宾资料"+WCMLang.Add_success);
		getCurrentFrameObj(topnum).showTurnPage();
		getCurrentFrameObj(topnum).showList();
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("嘉宾资料"+WCMLang.Add_fail);
	}
}
/**********************添加操作　结束*************************************/


/**********************修改操作　开始*************************************/
function fnUpdateActor()
{
	if(subm_sta == 1 && (tn == "" || tn == null))
	{
		msgWargin("该主题已通过审核，不能再进行操作！");
		return;
	}

	var selectIDS = table.getSelecteCheckboxValue("id");
	
	//window.location.href = "add_actor.jsp?id="+selectIDS;
	addTab(true,"/sys/interview/subject/add_actor.jsp?id="+selectIDS+"&topnum="+curTabIndex,"维护嘉宾信息");
}

function showUpdatePate(id)
{	
	if(subm_sta == 1 && (tn == "" || tn == null))
	{
		msgWargin("该主题已通过审核，不能再进行操作！");
		return;
	}
	//window.location.href = "add_actor.jsp?id="+id;
	addTab(true,"/sys/interview/subject/add_actor.jsp?id="+id+"&topnum="+curTabIndex,"维护嘉宾信息");
}

function updateActor()
{
	var bean = BeanUtil.getCopy(subjectActor);
	$("#subActor").autoBind(bean);		
		
	
	if(!standard_checkInputInfo("subActor"))
	{
		return;
	}
	
	bean.update_user = LoginUserBean.user_id;
	if(SubjectRPC.updateSubActor(bean))
	{
		msgAlert("嘉宾资料"+WCMLang.Add_success);
		getCurrentFrameObj(topnum).showTurnPage();
		getCurrentFrameObj(topnum).showList();
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("嘉宾资料"+WCMLang.Add_fail);
	}
}
/**********************修改操作　结束*************************************/

/**********************删除操作　开始*************************************/
//批量删除分类
function batchDelSubActor()
{
	if(subm_sta == 1 && (tn == "" || tn == null))
	{
		alertN("该主题已通过审核，不能再进行操作！");
		return;
	}
	if (table.getSelecteCount() < 1) {
			alertN("请选择要删除的记录！");
			return;
	}

	msgConfirm("确认删除选中记录？","batchDelSubActors()");
	
}

function batchDelSubActors()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	
	if(SubjectRPC.deleteSubActor(selectIDS,LoginUserBean.user_id))
	{
		msgAlert("嘉宾资料"+WCMLang.Delete_success);
		showList();	
		showTurnPage();
	}
	else
		msgWargin("嘉宾资料"+WCMLang.Delete_fail);
}

//删除分类
function deleteSubActor(id)
{
	if(subm_sta == 1 && (tn == "" || tn == null))
	{
		msgWargin("该主题已通过审核，不能再进行操作！");
		return;
	}
	msgConfirm("确认删除选中记录？","deleteSubActors("+id+")")
}

function deleteSubActors(id)
{
	if(SubjectRPC.deleteSubActor(id,LoginUserBean.user_id))
	{
		msgAlert("嘉宾资料"+WCMLang.Delete_success);
		showList();	
		showTurnPage();
	}
	else
		msgWargin("嘉宾资料"+WCMLang.Delete_fail);
}
/**********************删除操作　结束*************************************/


/**********************保存排序　开始*************************************/
function saveSort()
{
	if(subm_sta == 1 && (tn == "" || tn == null))
	{
		msgWargin("该主题已通过审核，不能再进行操作！");
		return;
	}

	var ids = table.getAllCheckboxValue("id");
	if(ids != "" && ids != null)
	{
		if(SubjectRPC.saveSubActorSort(ids))
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
//查看参与者
function view_actor2(id)
{	
	//addTab(true,'/sys/interview/subject/view_actor.jsp?id='+id,'查看嘉宾信息');
	//window.location.href = '/sys/interview/subject/add_actor.jsp?id='+id;
	addTab(true,"/sys/interview/subject/add_actor.jsp?id="+id+"&topnum="+curTabIndex,"查看嘉宾信息");
}