var InfoUpdateRPC = jsonrpc.InfoUpdateRPC;
var SiteRPC = jsonrpc.SiteRPC;
var InfoUpdateBean = new Bean("com.deya.project.dz_jyhgl.InfoUpdateBean",true);

var val=new Validator();
var tp = new TurnPage();
var beanList = null;
var table = new Table();
table.table_name = "infoupate_table";


function initTable(){

	var colsList = new List();	
	  
	colsList.add(setTitleClos("gz_name","规则名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("gz_day","间隔天数","100px","","",""));
    colsList.add(setTitleClos("gz_count","更新条数","100px","","",""));
	colsList.add(setTitleClos("gz_type","监测类型","150px","","",""));
    colsList.add(setTitleClos("gz_nexttime","下次检查时间","150px","","",""));
    colsList.add(setTitleClos("action_col","管理操作","100px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id

}
function reloadDataList()
{
	showList();
	showTurnPage();
}
function showList(){
	var m = new Map();
    m.put("start_num", tp.getStart());
    m.put("page_size", tp.pageSize);
    m.put("site_id", $("#site_id").val());
	beanList = InfoUpdateRPC.getInfoUpdateList(m);//第一个参数为站点ID，暂时默认为空
	beanList = List.toJSList(beanList);//把list转成JS的List对象
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	table.getCol("action_col").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openCategoryPage(\''+beanList.get(i-1).gz_id+'\',\''+beanList.get(i-1).site_id+'\')">配置栏目</a>');
		}
	})
    table.getCol("gz_type").each(function(i){
        if(i>0)
        {
            if(beanList.get(i-1).gz_type==1){
                $(this).html('首页');
            }
            if(beanList.get(i-1).gz_type==2){
                $(this).html('列表页');
            }
        }
    })
	Init_InfoTable(table.table_name);
}
function showTurnPage(){
    var m = new Map();
    m.put("site_id", $("#site_id").val());
    tp.total = InfoUpdateRPC.getInfoUpdateCount(m);
    tp.show("turn","");
    tp.onPageChange = showList;
}

function loadSite(){
    var siteList = SiteRPC.getSiteChildListByID("HIWCMcgroup");
    siteList = List.toJSList(siteList);
    for(var i=0;i<siteList.size();i++){
        $("#site_id").append("<option value=\""+siteList.get(i).site_id+"\">"+siteList.get(i).site_name+"</option>");
    }
}

//打开添加页面
function openAddPage(){
	var site_id=$("#site_id").val();
    if(site_id==""){
        top.msgWargin("请选择站点");
        return;
	}else{
        top.OpenModalWindow("新建更新规则","/sys/project/dz_jyhgl/infoUpdate_add.jsp?site_id="+site_id,500,215);
	}
}

//添加
function funAdd(){
    if(!standard_checkInputInfo(table.table_name))
    {
        return;
    }
    $("#"+table.table_name).autoBind(InfoUpdateBean);
    if(InfoUpdateRPC.insertInfoUpdate(InfoUpdateBean))
    {
        top.msgAlert("更新规则"+WCMLang.Add_success);
        top.CloseModalWindow();
        top.getCurrentFrameObj().reloadDataList();
    }
    else
    {
        top.msgWargin("更新规则"+WCMLang.Add_fail);
    }
}

//打开修改页面
function openUpdatePage(){
    var selectIDS = table.getSelecteCheckboxValue("gz_id");
    top.OpenModalWindow("修改更新规则","/sys/project/dz_jyhgl/infoUpdate_add.jsp?gz_id="+selectIDS,500,215);
}

//修改
function funUpdate(){
    if(!standard_checkInputInfo(table.table_name))
    {
        return;
    }
    InfoUpdateBean = defaultBean;
    $("#"+table.table_name).autoBind(InfoUpdateBean);
    if(InfoUpdateRPC.updateInfoUpdate(InfoUpdateBean))
    {
        top.msgAlert("更新规则"+WCMLang.Add_success);
        top.CloseModalWindow();
        top.getCurrentFrameObj().reloadDataList();
    }
    else
    {
        top.msgWargin("更新规则"+WCMLang.Add_fail);
    }
}
//删除
function funDelete(){
    var selectIDS = table.getSelecteCheckboxValue("gz_id");
    if(InfoUpdateRPC.deleteInfoUpdate(selectIDS)){
        top.msgAlert("更新规则"+WCMLang.Delete_success);
        top.getCurrentFrameObj().reloadDataList();
    }else
    {
        top.msgWargin("更新规则"+WCMLang.Delete_fail);
    }
}

//打开配置栏目页面
function openCategoryPage(gz_id,site_id){
    top.OpenModalWindow("配置栏目","/sys/project/dz_jyhgl/infoUpdate_category.jsp?gz_id="+gz_id+"&site_id="+site_id,500,530);
}