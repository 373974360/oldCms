var CategoryRPC = jsonrpc.CategoryRPC;
var CategoryBean = new Bean("com.deya.wcm.bean.cms.category.CategoryBean",true);
var CmsCountRPC = jsonrpc.CmsCountRPC;

var beanList = null;
var con_m = new Map();

var isContainRef = false; // 初始化目录树标记
var isContainLink = false; // 初始化"查看方式"改变事件标记

// 显示统计结果列表
function showList(){
	$("#countList").empty();
	$("#countList").append("<tr style=\"height:32px;line-height:32px;\"><td colspan=\"3\">数据正在统计中，请稍候...</td></tr>");
	var mp = new Map();
	var str = "";
	
	// 判断栏目选择信息
	var statu = false;
	$("[name='all_cat_ids']").each(function(){
		if($(this).is(':checked'))
		{
			statu = true;
			str += "," + $(this).val();  
		}
	});
	if(statu) {
		/*---------渭南专用-------*/
		str = str.substring(1);
		var group = str.split(",");
		var group_ids = "";
		for(var i = 0; i < group.length; i++)
		{
			if(group[i] == 1)
			{
				group_ids += "2377,3288,2158,13740,15786,15792,4643,4664,4697,4703,4743,4718,16553,15062,15872,15686,12744,12674,1616,9778,11621,1578,15604,16922";	//第一组修改栏目
			}else if(group[i] == 2)
			{
				group_ids += ",2163,6946,15693,15826,4690,12792,13249,4749,4681,13889,13661,13819";	//第二组修改栏目
			}else
			{
				group_ids += ",16302,1812,4738,4652,4681,1315,2156,13264,4757,4944,15814,1492,3510,15766";	//第三组修改栏目
			}
		}
		mp.put("group_ids",group_ids);
		/*---------渭南专用-------*/
	}else{
		if(selected_ids== ""){
			msgWargin("目录信息不能为空!");
			return;
		}else{
			mp.put("cat_ids",selected_ids);
		}
	}
	mp.put("info_status", "8"); // 取得已发信息
	beanList = CmsCountRPC.getInfoUpdateListByCate(mp);
	beanList = List.toJSList(beanList);
	
	createTable();
	
	$("#outFileBtn").show();  
}


//导出
function  outFileBtn(){
	if(beanList.size() != 0) {
		alert("正在导出，请稍等！");
		var listHeader = new List();
		listHeader.add("栏目名称");
		listHeader.add("栏目ID");
		listHeader.add("最后更新时间");
		url = CmsCountRPC.cmsUpdateInfoOutExcel(beanList,listHeader);
		setTimeout(function(){location.href=url;},15000); 
	}
}

//生成内容表格
function createTable(){
	$("#countList").empty();
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th>栏目名称</th>		" +
    "   <th>栏目ID</th>       " +
    "   <th>最后更新时间</th>   " +
    " </tr>" +
    "</thead>" +
    "<tbody>" ;
	if(beanList.size() != 0) {
		for(var i=0;i<beanList.size();i++){
			//treeHtmls += stuffTable(beanList.get(i));
			treeHtmls += setTreeNode(beanList.get(i), "");
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
 	 	"<td colspan=\"3\"></td>"+ " </tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"3\" width=\"1032\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"5\"></td>"+" </tfoot>";
	}
	$("#countList").append(treeHtmls);
	iniTreeTable("countList");
}


//组织树列表选项字符串
function setTreeNode(bean, parent_id)
{			
	//var index = pieChartDataList.size() + 1;
	var treeHtml = "";
	
	if(bean.is_leaf)
	{		
		var type_calss= "";
		if(parent_id !=""){  
		      type_calss = "class='child-of-node-"+parent_id+"'"
		}
		//alert("parent_id=="+parent_id+":::is_leaf==" + bean.cat_id);
		treeHtml+="<tr id='node-"+bean.cat_id+"' "+type_calss+" > <td><span class='file'>"+bean.cat_name+"</span></td>";
        treeHtml += setHandlList(bean);
		treeHtml+="</tr>";
	}
	else 
	{
		var type_calss = "";
		if(parent_id !=""){  
		      type_calss = "class='child-of-node-"+parent_id+"'"
		}
		//alert("parent_id=="+parent_id+":::not is_leaf==" + bean.cat_id);
		treeHtml+="<tr id='node-"+bean.cat_id+"' "+type_calss+" > <td><span class='folder'>"+bean.cat_name+"</span></td>";
		treeHtml += setHandlList(bean);
		treeHtml+="</tr>";
		var child_list = bean.child_list;
		  child_list = List.toJSList(child_list);
		  if(child_list.size() > 0)
		  { 
			 for(var i=0;i<child_list.size();i++)
			 {						
				treeHtml += setTreeNode(child_list.get(i), bean.cat_id+"");
			 }
		  }
	}
	
	return treeHtml;
}

//组织统计的数据
function setHandlList(bean)
{
	var str = "";
	if(bean)
	{
		str+="<td>"+bean.cat_id +"</td>";
		if(bean.time == null || bean.time == "null")
		{
			str+="<td></td>";
		}
		else
		{
			str+="<td>"+bean.time +"</td>";
		}
	}
	return str;
}

// 处理全选事件
function allSelected(){
	var statu = false;
	$("[name='all_cat_ids']").each(function(){
		if($(this).is(':checked'))
		{
			statu = true;
		}
	});
	if(statu){
		$("#getCateIDS").attr("disabled","disabled");
		$("#selectedCateNames").attr("disabled","disabled");
	}else{
		$("#getCateIDS").removeAttr("disabled");
		$("#selectedCateNames").removeAttr("disabled");
	}
}

