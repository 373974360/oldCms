var CategoryRPC = jsonrpc.CategoryRPC;
var CategoryBean = new Bean("com.deya.wcm.bean.cms.category.CategoryBean",true);
var InfoAccessBean = new Bean("com.deya.wcm.bean.cms.count.InfoAccessBean",true);
var AccessCountRPC = jsonrpc.AccessCountRPC;

var beanList = null;
var con_m = new Map();
var initTree = false;   // 初始化目录树标记
var initType = false;   // 初始化"查看方式"改变事件标记
var mp = new Map();
var type_name="栏目名称";
var row_count = "";
var type_radio="";      //用户判断选中的单选按钮的值 ：默认(mr) 栏目排行(cat) 信息排行(info) 


//点击统计btn时触发的函数
function searchBtn()
{
    showList();
	$("#outFileBtn").show();
}

// 显示统计结果列表
function showList()
{
	var start_day = $("#start_day").val();
	var end_day = $("#end_day").val();
	var type = $(":radio[name='tj_type'][checked]").val();
	
	mp.put("site_id",site_id);      //site_id = HTWCMdemo
	mp.put("start_day",start_day);  //start_day = 2013-07-01
	mp.put("end_day",end_day+" 23:59:59");	
	mp.put("cat_id",cat_id);   //cat_id = 0
	
	if(type == 1)
	{   
		type_name = "栏目名称";
		type_radio = "mr";
		
		beanList = AccessCountRPC.getSiteCateAccessList(mp);
		beanList = List.toJSList(beanList);
		createTableSite();
	}else if(type == 2){ //统计每个子栏目的信息访问量
		type_name = "栏目名称";
		type_radio = "cat_name";
		row_count = $("#row_count").val(); //每页显示条数
		mp.put("row_count",row_count);
		
		beanList = AccessCountRPC.getCatOrderListByCat_id(mp);
		beanList = List.toJSList(beanList);
		createTableSite();
	}else if(type == 3){ //统计每条信息的访问量
		type_name = "标题名称";
		type_radio = "info_name";
		row_count = $("#row_count").val(); //每页显示条数
		mp.put("row_count",row_count);
		
		beanList = AccessCountRPC.getInfoOrderListByInfo_id(mp);
		beanList = List.toJSList(beanList);	
		createTableSite();
	}
}
//导出
function  outFileBtn()
{
	if(type_radio == "mr"){
		if(beanList.size() != 0) {
			var listHeader = new List();
			listHeader.add("栏目名称");
			listHeader.add("访问量");
			var url = AccessCountRPC.CateAccessCountsOutExcel(beanList,listHeader);
			location.href=url;  
		}
	}else if(type_radio == "cat_name"){
		if(beanList.size() != 0){
			var listHeader = new List();
			listHeader.add("栏目名称");
			listHeader.add("访问量");
			var url = AccessCountRPC.CateAccessOrderCountsOutExcel(beanList,listHeader);
			location.href=url;
		}
	}else if(type_radio == "info_name"){
		if(beanList.size() != 0){
			var listHeader = new List();
			listHeader.add("信息标题");
			listHeader.add("访问量");
			var url = AccessCountRPC.InfoAccessOrderCountsOutExcel(beanList,listHeader);
			location.href=url;
		}
	}
	
}
//------------------------------------
function createTableSite()
{    
	$("#countList").empty();
	$("#cateOrderList_data").empty();
	$("#infoOrderList_data").empty();
	
	if(type_radio == "info_name"){
		var treeHtmls = "<thead>" +
	    "<tr> " +
	    "   <th width='60%'>"+type_name+"</th>"+
	    "   <th width='30%'>访问量</th>"+
	   " </tr>" +
	  "</thead>" +
	  "<tbody>" ;
	}else{
		var treeHtmls = "<thead>" +
	    "<tr> " +
	    "   <th width='60%'>"+type_name+"</th>"+
	    "   <th width='30%'>访问量</th>"+
	    "	<th width='10%'>详细</th>"+
	   " </tr>" +
	  "</thead>" +
	  "<tbody>" ;
	}
  if(beanList.size() != 0) 
  {
	for(var i=0;i<beanList.size();i++){
		treeHtmls += setTreeNodeUser(beanList.get(i));
	}
	treeHtmls += "</tbody>"+
	"<tfoot>" +
  	"<td colspan=\"3\"></td>"+ " </tfoot>";
 	} else {
		treeHtmls += "<tr><td colspan=\"3\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"3\"></td>"+" </tfoot>";
	}
    if(type_radio == "mr"){
  		$("#countList").append(treeHtmls);
  		iniTreeTable("countList");
  	}else if(type_radio == "cat_name"){
  		$("#cateOrderList_data").append(treeHtmls);
  		iniTreeTable("cateOrderList_data");
  	}else if(type_radio == "info_name"){
  		$("#infoOrderList_data").append(treeHtmls);
  		iniTreeTable("infoOrderList_data");
  	}
    
}
//填充每一行的具体数据
//添加数据的聚合,显示信息总数
//site_id = HIWCMdemo
//bean.cat_name 栏目名称
function setTreeNodeUser(bean)
{			
	var treeHtml = "";
	var type_calss= "";
	if(type_radio == "mr")
	{
		treeHtml +="<tr><td>"+bean.cat_name+"</td>";
		treeHtml +="<td>"+bean.icount+"</td>"; //访问量
		treeHtml +="<td><a href='javascript:openCateAccessInfos(\""+bean.cat_id+"\",\""+site_id+"\",\""+bean.cat_name+"\")'>"+"查看"+"</a></td>";
		treeHtml+="</tr>";
		return treeHtml;
	}else if(type_radio == "cat_name"){
		treeHtml +="<tr><td>"+bean.cat_name+"</td>";
		treeHtml +="<td>"+bean.icount+"</td>"; //访问量
		treeHtml +="<td><a href='javascript:openCateAccessInfos(\""+bean.cat_id+"\",\""+site_id+"\",\""+bean.cat_name+"\")'>"+"查看"+"</a></td>";
		treeHtml+="</tr>";
		return treeHtml;
	}else if(type_radio == "info_name")
	{
		 var infob = jsonrpc.InfoBaseRPC.getInfoById(bean.info_id+"",site_id);
		 if(infob !="" || infob != null || infob != "null")
		 {
			 treeHtml+="<td><a href="+infob.content_url+" target=\"_blank\">"+infob.title+"</a></td>";
		 }	
		treeHtml +="<td>"+bean.icount+"</td>"; //访问量
		treeHtml+="</tr>";
		return treeHtml;
	}
}

function openCateAccessInfos(cat_id,site_id,cateCname) { 
	top.OpenModalWindow("查看"+cateCname+" 栏目详细信息","/sys/cms/cmsCount/CateAccessInfoList.jsp?site_id="+site_id+"&cat_id="+cat_id,1000,530);
}

//添加目录树的点击事件
function initCategoryTree()
{
	if(initTree){
		return;
	}else{
		initTree = true;
	}
	$('#leftMenuTree').tree({
		onClick:function(node)
		{
			if(node.id == 0 || node.id == -1){
				changeCategoryListTable(node.id,node.id); 
			}else{
			    changeCategoryListTable(node.id,$('#leftMenuTree').tree('getParent',node.target).id); 
			}          
		}
	});
}
//点击树节点,修改目录列表数据
function changeCategoryListTable(c_id,pid)
{
	var p_id = pid;
	if(p_id == -1)
	{
		mp.put("p_id",p_id+"");
	}else{
	    mp.put("p_id","0");
	}
	cat_id = c_id+"";
	showList();	
}