var CmsCountRPC = jsonrpc.CmsCountRPC;

var beanList = null;
var mp = new Map();

var start_day ="";
var end_day ="";


// 显示统计结果列表
function showList(){
	
	start_day = $("#start_day").val();
	end_day = $("#end_day").val();

	// 判断栏目选择信息
	var statu = $("#all_dept_ids").is(':checked');
	if(statu) {
		
	}else{
		if(selected_ids== ""){
			top.msgWargin("部门信息不能为空!");
			return;
		}else{
			mp.put("dept_ids",selected_ids);
		}
	}
	
	mp.put("site_id",site_id);
    mp.put("info_status","8");
	mp.put("start_day",start_day +" 00:00:00");
	mp.put("end_day",end_day + " 23:59:59");
	
	beanList = CmsCountRPC.getDeptListByReleaseDtime(mp);
	beanList = List.toJSList(beanList);
	
	createTableCate();

}

// 处理全选事件
function allSelected(){
	var statu = $("#all_dept_ids").is(':checked');
	if(statu){
		$("#getDeptIDS").attr("disabled","disabled");
		$("#selectedDeptNames").attr("disabled","disabled");
	}else{
		$("#getDeptIDS").removeAttr("disabled");
		$("#selectedDeptNames").removeAttr("disabled");
	}
}

//生成内容表格
function createTableCate(){
	$("#countList").empty();
	var treeHtmls = "<thead>" +
    "<tr> " +
    "   <th width='60%'>部门名称</th>		" +
    "   <th width='40%' style='padding:0;'>总信息数</th>       " +
    " </tr>" +
    "</thead>" +
    "<tbody>" ;
	if(beanList.size() != 0) {
		for(var i=0;i<beanList.size();i++){
			treeHtmls += setTreeNode(beanList.get(i), "");
		}
		treeHtmls += "</tbody>"+
		"<tfoot>" +
 	 	"<td colspan=\"2\"></td>"+ " </tfoot>";
	} else {
		treeHtmls += "<tr><td colspan=\"2\">暂无数据...</td></tr>" + "</tbody>"+"<tfoot>" +"<td colspan=\"2\"></td>"+" </tfoot>";
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
		treeHtml+="<tr id='node-"+bean.dept_id+"' "+type_calss+" > <td><span class='file'>"+bean.dept_name+"</span></td>";
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
		treeHtml+="<tr id='node-"+bean.dept_id+"' "+type_calss+" > <td><span class='folder'>"+bean.dept_name+"</span></td>";
		treeHtml += setHandlList(bean);
		treeHtml+="</tr>";
		var child_list = bean.child_list;
		  child_list = List.toJSList(child_list);
		  if(child_list.size() > 0)
		  { 
			 for(var i=0;i<child_list.size();i++)
			 {						
				treeHtml += setTreeNode(child_list.get(i), bean.dept_id+"");
			 }
		  }
	}
	
	return treeHtml;
}


// 组织统计的数据
function setHandlList(bean)
{
	var str = "";
	if(bean)
	{
        if(bean.inputCount > 0){
            str+="<td><a href=\"javascript:openUInfoList("+bean.dept_id+")\" style=\"color:red;\">"+bean.inputCount+"</a></td>";
        }
        else {
            str+="<td>"+bean.inputCount+"</td>";
        }
		//str+="<td>"+bean.releasedCount +"</td>";
		//str+="<td>"+bean.picReleasedCount +"</td>";
		//str+="<td>"+bean.releaseRate +"</td>";
	}
	return str;
}

function openUInfoList(dept_id)
{
    top.OpenModalWindow("详细信息列表","/sys/cms/cmsCount/deptInfoList.jsp?site_id="+site_id+"&start_day="+start_day+"&end_day="+end_day+"&dept_id="+dept_id,1000,600);
}