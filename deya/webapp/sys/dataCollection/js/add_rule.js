var CollectionDataRPC = jsonrpc.CollectionDataRPC;
var collRuleBean = new Bean("com.deya.wcm.dataCollection.bean.CollRuleBean",true);
var CategoryRPC = jsonrpc.CategoryRPC;

//添加url地址
function addStartURL(hand_name)
{
	top.OpenModalWindow("添加采集网址","/sys/dataCollection/addURL.jsp?hand_name="+hand_name,800,500);
}

function saveStartURL(urlStr,rowindex)
{
	var table = document.getElementById("urlTab");
	if(rowindex != "addURL")
	{
		table.deleteRow(rowindex); //删除索引为rowindex的行
		var	rowId = "row_"+rowindex;
		var htmlStr = "<tr id='"+rowId+"' class=\"hover\">";
			htmlStr += "<td style='width:10%'>"+urlStr+"</td>";
			htmlStr += "</tr>";
		$("#urlTab").append(htmlStr);
	}else{
		var MaxRowNum = table.rows.length;  //获取最大行的索引值
		var	rowId = "row_"+MaxRowNum;
		var htmlStr = "<tr id='"+rowId+"' class=\"hover\">";
			htmlStr += "<td style='width:10%'>"+urlStr+"</td>";
			htmlStr += "</tr>";
		$("#urlTab").append(htmlStr);
	}
}

//删除行
function delRow()
{
	if(selectRow)
	{
		var table=document.getElementById("urlTab");
		table.deleteRow(selectRow.sectionRowIndex);
		resetRowId();
	}
}

//鼠标所选的行
function selRow(e)
{
	var me = e.target||e.srcElement;
	var selRowId = me.parentElement.id;
//	var selRow = me.parentElement;  //鼠标选中的上一个节点的对象(tr对象)
	if(selRowId)
	{
		changeColor(me.parentElement);
	}
}
var selectRow;
function changeColor(trObj)
{
	if(selectRow)
	{
		selectRow.style.background="#F0F3F8";
	}
	trObj.style.background="#D2E4F7"; //点击选中的颜色
	selectRow=trObj;
}

//修改采集地址
function UpdateStartURL()
{
	if(selectRow)
	{
		var urlstr = encodeURIComponent(encodeURIComponent(selectRow.cells[0].innerHTML)); //获取所选行的第一列的值
		var selRowIndex = selectRow.sectionRowIndex; //获取所选行的索引值
		top.OpenModalWindow("修改采集网址","/sys/dataCollection/updateURL.jsp?hand_name=saveStartURL"+"&urlStr="+urlstr+"&rowIndex="+selRowIndex,800,500);
	}else{
		top.msgAlert("请选择要修改的记录!");
	}
}

//重新设置tr的ID
function resetRowId()
{
	var table=document.getElementById("urlTab");
	var MaxRowIndex = table.rows.length;
	for(var i=0;i<= MaxRowIndex;i++)
	{
		table.rows[i].id="row_"+i;
//		table.rows[i].setAttribute("id","row_"+i); 
	}
}

//清空采集地址
function emptyurlTab()
{
	$("#urlTab").empty();
}

function getCollTabValue()
{
	var table=document.getElementById("urlTab");
	var TabMaxIndex = table.rows.length;
	var coll_url = "";
	for(var i=0;i<TabMaxIndex;i++)
	{	
		coll_url += table.rows[i].cells[0].innerHTML+"$";
	}
	coll_url = coll_url.substring(0,coll_url.length-1)
	collRuleBean.coll_url=coll_url;
}

function getCollRuleValue()
{
	getCollTabValue();
	getisCollectImg();
	collRuleBean.title= $("#title").val();
	
	collRuleBean.pageEncoding= $("#pageEncoding").val();
//	if($("#stop_time").val()==""){
//		collRuleBean.stop_time= 0;
//	}else{
//		collRuleBean.stop_time= $("#stop_time").val();
//	}
//	collRuleBean.timeFormatType= $("#timeFormatType").val();
    collRuleBean.rcat_id = rcat_id;
	collRuleBean.infotitle_start= $("#infotitle_start").val();
	collRuleBean.infotitle_end= $("#infotitle_end").val();
	collRuleBean.listUrl_start= $("#listUrl_start").val();
	collRuleBean.listUrl_end= $("#listUrl_end").val();
	collRuleBean.content_start= $("#content_start").val();
	collRuleBean.content_end= $("#content_end").val();
	collRuleBean.author_start= $("#author_start").val();
	collRuleBean.author_end= $("#author_end").val();
	collRuleBean.addTime_start= $("#addTime_start").val();
	collRuleBean.addTime_end= $("#addTime_end").val();
	collRuleBean.source_start=$("#source_start").val();
	collRuleBean.source_end= $("#source_end").val();
	collRuleBean.keywords_start=$("#keywords_start").val();
	collRuleBean.keywords_end=$("#keywords_end").val();
    collRuleBean.docNo_start=$("#docNo_start").val();
    collRuleBean.docNo_end=$("#docNo_end").val();
	
	if($("#cat_cname").val()!=""){
		collRuleBean.cat_name = $("#cat_cname").val();
		collRuleBean.cat_id = $("#catidhidden").val();
	}else{
		top.msgAlert("请输入栏目信息!");
	}
	
	var s = $("#coll_interval").is(':checked');
	if(s){
		collRuleBean.coll_interval = "24";
	}else{
		collRuleBean.coll_interval = "0";
	}
	
	collRuleBean.site_id = site_id;
	collRuleBean.model_id = $("#model_id").val();
}

//是否采集图片
function getisCollectImg()
{
	var isImg = $("input[name='isCollectImg']:checked").val();
	collRuleBean.pic_isGet=isImg;
}

//添加规则
function addCollectionRule()
{
	getCollRuleValue();
	if(!standard_checkInputInfo("collRuleTab")) //标准表单验证
	{
		return;
	}
	if(CollectionDataRPC.addCollectionRule(collRuleBean))
	{
		top.msgAlert("采集规则"+WCMLang.Add_success);
		top.getCurrentFrameObj(tab_index).loadRuleTable(); 
		top.tab_colseOnclick(top.curTabIndex);
	}
}

//测试采集地址
function checkCollUrl()
{
	getCollTabValue();
	var collURL = encodeURIComponent(encodeURIComponent(collRuleBean.coll_url));
	var listUrlstart= $("#listUrl_start").val();
	var listUrlend= $("#listUrl_end").val();
	var encode = $("#pageEncoding").val();

	if(collURL == ""){
		top.msgAlert("您还没有填写\"采集地址\"!");
		return;
	}else{
		if(listUrlstart == "" || listUrlend == ""){
			top.msgAlert("您还没有填写\"列表页地址规则\"!");
			return;
		}
	}
	top.OpenModalWindow("测试采集地址","/sys/dataCollection/checkCollURL.jsp?collURL="+collURL+"&ListStart="+listUrlstart+"&ListEnd="+listUrlend+"&encode="+encode,800,550);
}

//修改规则
function updateCollectionRule()
{
	getCollRuleValue();
	if(!standard_checkInputInfo("collRuleTab")) //标准表单验证
	{
		return;
	}
	id = parseInt(id);
	if(CollectionDataRPC.updateRuleInfo(collRuleBean,id))
	{
		top.msgAlert("修改成功!");
		top.getCurrentFrameObj(tab_index).loadRuleTable(); 
		top.tab_colseOnclick(top.curTabIndex);
	}else{
		top.msgAlert("修改失败!");
	}
}
