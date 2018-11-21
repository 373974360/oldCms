var InfoUpdateResultRPC = jsonrpc.InfoUpdateResultRPC;
var SiteRPC = jsonrpc.SiteRPC;

var beanList = null;
var table = new Table();
table.table_name = "result_table";


function initTable(){

	var colsList = new List();	
	  
	colsList.add(setTitleClos("cat_name","栏目名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
    colsList.add(setTitleClos("gz_day","监测周期(天)","100px","","",""));
    colsList.add(setTitleClos("end_update_time","开始日期","100px","","",""));
    colsList.add(setTitleClos("check_time","截止日期","100px","","",""));
	colsList.add(setTitleClos("gz_count","应更新条数","150px","","",""));
    colsList.add(setTitleClos("update_count","实际更新条数","150px","","",""));
    colsList.add(setTitleClos("update_desc","是否合格","100px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id

}
function reloadDataList()
{
	showList();
}
function showList(){
	var m = new Map();
    m.put("site_id", $("#site_id").val());
    m.put("update_desc","");
	beanList = InfoUpdateResultRPC.getInfoUpdateResultList(m);//第一个参数为站点ID，暂时默认为空
	beanList = List.toJSList(beanList);//把list转成JS的List对象
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	Init_InfoTable(table.table_name);
}

function loadSite(){
    var siteList = SiteRPC.getSiteChildListByID("HIWCMcgroup");
    siteList = List.toJSList(siteList);
    for(var i=0;i<siteList.size();i++){
        $("#site_id").append("<option value=\""+siteList.get(i).site_id+"\">"+siteList.get(i).site_name+"</option>");
    }
}
