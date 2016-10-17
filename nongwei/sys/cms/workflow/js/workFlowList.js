var WorkFlowRPC = jsonrpc.WorkFlowRPC;
var WorkFlowBean = new Bean("com.deya.wcm.bean.cms.workflow.WorkFlowBean",true);
var WorkFlowStepBean = new Bean("com.deya.wcm.bean.cms.workflow.WorkFlowStepBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "workFlow_table";;

function reloadWorkFlowList()
{
	showList();	
	showTurnPage();	
}

function locationWorkFlow()
{
	window.location.href = "workFlowList.jsp?app_id="+app_id;
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("wf_id","ID","30px","","",""));	
	colsList.add(setTitleClos("wf_name","工作流名称","250px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("wf_steps","流程个数","100px","","",""));
	colsList.add(setTitleClos("blank_col","&#160;","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){	

	beanList = WorkFlowRPC.getWorkFlowList();//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();

	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("wf_name").each(function(i){
		$(this).css({"text-align":"left"});	
		if(i>0)
		{	
			$(this).html('<a href="javascript:openUpdateWorkFlowPage('+beanList.get(i-1).wf_id+')">'+beanList.get(i-1).wf_name+'</a>');
		}
	});	

	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
				
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

//打开查看窗口
function openViewWorkFlowPage(r_id)
{	
	window.location.href = "/sys/cms/workflow/workFlow_view.jsp?wf_id="+r_id;
}

//打开添加窗口
function openAddWorkFlowPage()
{
	window.location.href = "/sys/cms/workflow/workFlow_add.jsp?app_id="+app_id;
}

//打开修改窗口
function openUpdateWorkFlowPage(wf_id)
{
	var selectIDS = "";
	if(wf_id == null || wf_id == "")
		selectIDS = table.getSelecteCheckboxValue("wf_id");
	else
		selectIDS = wf_id;
	window.location.href = "/sys/cms/workflow/workFlow_add.jsp?wf_id="+selectIDS+"&app_id="+app_id;
}

//添加工作流
function addWorkFlow()
{
	var bean = BeanUtil.getCopy(WorkFlowBean);	
	$("#workflow_table").autoBind(bean);

	if(!standard_checkInputInfo("workflow_table"))
	{
		return;
	}
	
	bean.wf_steps = $("#wf_step_table tr").length;
	if(bean.wf_steps == 0)
	{
		top.msgWargin("请添加流程步骤");
		return;
	}

	var isEmpty = true;
	var stepList = new List();
	$("#wf_step_table tr").each(function(i){
		var wfsb = BeanUtil.getCopy(WorkFlowStepBean);
		var step_name_input = $(this).find("input[id^='step_name']");
		var role_name_input = $(this).find("input[id^='wf_role_name']");
        var required_input = $(this).find("input[id^='required']").is(':checked') ? 1 : 0;
		wfsb.step_name = step_name_input.val()
        wfsb.required = required_input;
		wfsb.role_id = $(this).find("#wf_role_id").val();
		wfsb.step_id = i+1;	
		if(wfsb.step_name == "" || wfsb.step_name == "步骤名称")
		{
			jQuery.simpleTips(step_name_input.attr("id"),"请输入步骤名称",2000);
			isEmpty = false;
			return false;
		}
		if(wfsb.role_id == "" || wfsb.role_id == null)
		{
			jQuery.simpleTips(role_name_input.attr("id"),"请选择角色",2000);
			isEmpty = false;
			return false;
		}
		stepList.add(wfsb);
	});
	bean.workFlowStep_list = stepList;
	
	if(!isEmpty)
		return;
	
	if(WorkFlowRPC.insertWorkFlow(bean))
	{
		top.msgAlert("工作流信息"+WCMLang.Add_success);			
		locationWorkFlow();
	}
	else
	{
		top.msgWargin("工作流信息"+WCMLang.Add_fail);
	}
}
//修改工作流
function updateWorkFlow()
{
	var bean = BeanUtil.getCopy(WorkFlowBean);	
	$("#workflow_table").autoBind(bean);

	if(!standard_checkInputInfo("workflow_table"))
	{
		return;
	}
	
	bean.wf_steps = $("#wf_step_table tr").length;
	if(bean.wf_steps == 0)
	{
		top.msgWargin("请添加流程步骤");
		return;
	}

	var isEmpty = true;
	var stepList = new List();
	$("#wf_step_table tr").each(function(i){
		var wfsb = BeanUtil.getCopy(WorkFlowStepBean);
		var step_name_input = $(this).find("input[id^='step_name']");
		var role_name_input = $(this).find("input[id^='wf_role_name']");
        var required_input = $(this).find("input[id^='required']").is(':checked') ? 1 : 0;
        wfsb.required = required_input;
		wfsb.step_name = step_name_input.val();
		wfsb.role_id = $(this).find("#wf_role_id").val();
		wfsb.step_id = i+1;
		if(wfsb.step_name == "" || wfsb.step_name == "步骤名称")
		{
			jQuery.simpleTips(step_name_input.attr("id"),"请输入步骤名称",2000);
			isEmpty = false;
			return false;
		}
		if(wfsb.role_id == "" || wfsb.role_id == null)
		{
			jQuery.simpleTips(role_name_input.attr("id"),"请选择角色",2000);
			isEmpty = false;
			return false;
		}
		stepList.add(wfsb);
	});
	bean.workFlowStep_list = stepList;
	
	if(!isEmpty)
		return;
  
	bean.wf_id = wf_id;
	if(WorkFlowRPC.updateWorkFlow(bean))
	{
		top.msgAlert("工作流信息"+WCMLang.Add_success);			
		locationWorkFlow();
	}
	else
	{
		top.msgWargin("工作流信息"+WCMLang.Add_fail);
	}
}

//删除工作流
function deleteWorkFlow()
{
	var selectIDS = table.getSelecteCheckboxValue("wf_id");
	if(WorkFlowRPC.deleteWorkFlow(selectIDS))
	{
		top.msgAlert("工作流信息"+WCMLang.Delete_success);
		reloadWorkFlowList();
	}else
	{
		top.msgWargin("工作流信息"+WCMLang.Delete_fail);
	}
}





