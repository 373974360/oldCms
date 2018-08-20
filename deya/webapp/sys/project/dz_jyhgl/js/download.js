var InfoUpdateRPC = jsonrpc.InfoUpdateRPC;
var SiteRPC = jsonrpc.SiteRPC;

var val=new Validator();
var beanList = null;
var table = new Table();
table.table_name = "infoupate_table";


function initTable(){

	var colsList = new List();	
	  
	colsList.add(setTitleClos("file_name","文件名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
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
}
function showList(){
	beanList = InfoUpdateRPC.getDownloadFile(gz_id,site_id);//第一个参数为站点ID，暂时默认为空
	beanList = List.toJSList(beanList);//把list转成JS的List对象
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	table.getCol("action_col").each(function(i){
		if(i>0)
		{
			$(this).html("<a href='"+beanList.get(i-1).file_path+"'>结果下载</a>");
		}
	})
	Init_InfoTable(table.table_name);
}