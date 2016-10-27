var WareRPC = jsonrpc.WareRPC;
var WareBean = new Bean("com.deya.wcm.bean.system.ware.WareBean", true);

function initPageWare()
{		
	if(ware_id != "" && ware_id != null)
	{
		defaultBean = WareRPC.getWareBeanByWareID(ware_id,site_id);	
		if(defaultBean.ware_type == 2)
		{
			$("#div_talbe_1").hide();
			$(".simpl_button").hide();
			$("#div_talbe_2").show();
			$(".ware_info_button").show();	
			$("#ware_memo2").text(defaultBean.ware_memo);
			$("#ware_memo2_div").show();
			showWareInfoList();
		}else
		{
			$("#div_talbe_2").hide();
			$("#ware_width").text(defaultBean.ware_width);
			$("#ware_memo").text(defaultBean.ware_memo);
			$("#ware_content").val(defaultBean.ware_content);				
			$("#savaBtn").click(updateWareContent);

            setV(contentId,defaultBean.ware_content);
		}
	}	
}

//修改标签内容 
function updateWareContent()
{
	defaultBean.ware_content = getV(contentId);
	if(WareRPC.updateWareContent(defaultBean))
	{
		top.msgAlert("信息标签"+WCMLang.Add_success);
	}
	else{
		top.msgAlert("信息标签"+WCMLang.Add_fail);
	}
}

function showWareInfoList()
{
	var str = "";
	var m = new Map();
	m.put("ware_id",defaultBean.ware_id);
	m.put("app_id",defaultBean.app_id);
	m.put("site_id",defaultBean.site_id);
	ware_info_list = WareRPC.getWareInfoList(m);
	ware_info_list =  List.toJSList(ware_info_list);
	if(ware_info_list != null && ware_info_list.size() > 0)
	{
		for(var i=0;i<ware_info_list.size();i++)
		{
			str += '<tr winfo_id="'+ware_info_list.get(i).winfo_id+'">';
			str += '<td class="c_index" style="text-align:center">'+(i+1)+'</td>';
			str += '<td id="list_'+ware_info_list.get(i).winfo_id+'">'+showWareInfos(ware_info_list.get(i).infos_list)+'</td>';
			str += '<td>';
			str += ' <ul class="optUL">';
			str +='	  <li class="opt_add ico_add1" title="添加"></li>';
			str +='	  <li class="opt_up ico_up" title="上移"></li>';
			str +='	  <li class="opt_down ico_down" title="下移"></li>';
			str +='	  <li class="opt_delete ico_delete" title="删除"></li>';
			str +='	  </ul>';
			str += '</td>';
			str += '</tr>';
		}
		$("#ware_info_list").append(str);
		resetNum();
		iniOpt();
	}	
}

//显示信息
function showWareInfos(infos_list)
{
	str = "";
	infos_list =  List.toJSList(infos_list);
	if(infos_list != null && infos_list.size() > 0)
	{
		str += "<ul>";
		for(var i=0;i<infos_list.size();i++)
		{
			var title_flag = "";
			if(infos_list.get(i).pre_title != "")
			{
				title_flag += "<span >["+infos_list.get(i).pre_title+"]</span>";
			}

			str += '<li style="float:left">'+title_flag+'<a href="#" id="info_a" infos_id="'+infos_list.get(i).id+'">'+infos_list.get(i).title+'</a><span class="width50">&nbsp;&nbsp;</span></li>';
			winfos_map.put(infos_list.get(i).id,infos_list.get(i));
		}
		str += "</ul>";		
	}else
	{
		str = "<ul></ul>";
	}
	return str;
}

//添加信息到行中
function setInfoToUL(WareInfos,winfo_id)
{
	var str = '<li style="float:left" id="info_a" infos_id="'+WareInfos.id+'"><a href="#" id="info_a" infos_id="'+WareInfos.id+'">'+WareInfos.title+'</a><span class="width50">&nbsp;&nbsp;</span></li>';
	$("#list_"+winfo_id+" ul").append(str);
	winfos_map.put(WareInfos.id,WareInfos);
	initTitleClick();
}

//修改信息到行中
function updateInfoToUL(WareInfos)
{
	$('li #info_a[infos_id="'+WareInfos.id+'"]').text(WareInfos.title);
	winfos_map.put(WareInfos.id,WareInfos);
	initTitleClick();
}
//替换行中的信息
function changeInfoToUL(WareInfos)
{	
	$('li a[infos_id="'+currnet_info_id+'"]').text(WareInfos.title);
	$('li a[infos_id="'+currnet_info_id+'"]').attr("infos_id",WareInfos.id);
	winfos_map.put(WareInfos.id,WareInfos);
	initTitleClick();
	//删除原信息ID
	WareRPC.deleteWareInfosByID(currnet_info_id);
}

//重排序号
function resetNum()
{	
	$(".c_index").each(function(i){
		$(this).text(i+1);
	});
	$("#ware_info_list tr").find(".opt_up").addClass("ico_up");
	$("#ware_info_list tr").find(".opt_down").addClass("ico_down");
	
	//首行、未行去除移动图标
	$("#ware_info_list tr").first().find(".opt_up").removeClass("ico_up");
	$("#ware_info_list tr").last().find(".opt_down").removeClass("ico_down");
 
}

//添加信息
function addInfo(obj)
{
	var winfo_id = $(obj).attr("winfo_id");	
	var sort_id = $("#list_"+winfo_id+" ul li").length;
	if(sort_id > 0)
	{
		//如果此行中有信息，取得它最后的信息排序号
		var infos_id = $("#list_"+winfo_id+" ul li a").last().attr("infos_id");
		sort_id = parseInt(winfos_map.get(infos_id).sort_id);
	}
	sort_id += 1;
	top.OpenModalWindow("选择信息","/sys/system/ware/select_info_ref.jsp?winfo_id="+winfo_id+"&sort_id="+sort_id,630,460);
}

//删除
function deleteTr(obj)
{
	var winfo_id = $(obj).attr("winfo_id");	
	var count = $("tr[winfo_id]").length - 1;//得到目前的行数
	
	if(WareRPC.deleteWareInfoByWInfoID(ware_id,winfo_id,count,app_id,site_id))
	{
		$(obj).remove();
		resetNum();
	}else
	{
		top.msgWargin(WCMLang.Delete_fail);
	}
}
//上移
function moveUpTr(obj)
{
	if($(obj).index==0) return;
	$($(obj).prev()).before($(obj));
	resetNum();
	Init_InfoTable("ware_info_table");
}
//下移
function moveDownTr(obj)
{
	if($(obj).index==$("#ware_info_list tr").length-1) return;
	$($(obj).next()).after($(obj));
	resetNum();
	Init_InfoTable("ware_info_table");
}
//上、下移、删除事件绑定
function iniOpt()
{
	$(".opt_add").live("click",function(){
		var tmpObj = $(this).parent().parent().parent();
		addInfo(tmpObj);
	});
	$(".opt_up").live("click",function(){
		var tmpObj = $(this).parent().parent().parent();
		moveUpTr(tmpObj);
	});
	$(".opt_down").live("click",function(){
		var tmpObj = $(this).parent().parent().parent();
		moveDownTr(tmpObj);
	});
	$(".opt_delete").live("click",function(){
		var tmpObj = $(this).parent().parent().parent();
		deleteTr(tmpObj);
	});	
}

function addWareInfo()
{
	var count = $("tr[winfo_id]").length - 1;//得到目前的行数
	var sort_id = 1;
	if(ware_info_list != null && ware_info_list.size() > 0)
		sort_id = ware_info_list.get(ware_info_list.size()-1).sort_id + 1;//排序ID，得到最后一个行数的排序值并加1

	var winfo_id = WareRPC.insertWareInfo(ware_id,sort_id,count,app_id,site_id);
	if(winfo_id > 0)
	{
		var str = "";		
		str += '<tr winfo_id="'+winfo_id+'">';
		str += '<td class="c_index" style="text-align:center">'+(count+1)+'</td>';
		str += '<td id="list_'+winfo_id+'"><ul></ul></td>';
		str += '<td>';
		str += ' <ul class="optUL">';
		str +='	  <li class="opt_add ico_add1" title="添加"></li>';
		str +='	  <li class="opt_up ico_up" title="上移"></li>';
		str +='	  <li class="opt_down ico_down" title="下移"></li>';
		str +='	  <li class="opt_delete ico_delete" title="删除"></li>';
		str +='	  </ul>';
		str += '</td>';
		str += '</tr>';
		
		$("#ware_info_list").append(str);
		resetNum();
		iniOpt();
		Init_InfoTable("ware_info_table");
	}else
	{
		top.msgWargin("新建行失败，请重新新建");
	}
}

function sortWareInfo()
{
	var ware_infos = "";
	$("#ware_info_list tr").each(function(){
		ware_infos += ","+$(this).attr("winfo_id");
	});
	if(ware_infos != "")
	{
		ware_infos = ware_infos.substring(1);
		if(WareRPC.sortWareInfo(ware_infos))
		{
			top.msgAlert(WCMLang.Sort_success);
		}
		else
		{
			top.msgWargin(WCMLang.Sort_fail);
		}
	}
}

/******************** 菜单按钮处理　结束 **********************/
var currnet_info_id;
var currnet_winfo_id;
var current_li_obj;
function initTitleClick()
{	
	$("a[id='info_a']").click(function(e){
		$('#mouse_menu').menu('show',{
			left: e.pageX,
			top: e.pageY
		});	
		currnet_info_id = $(this).attr("infos_id");
		currnet_winfo_id = $(this).parent().parent().parent().parent().attr("winfo_id");
		current_li_obj = $(this).parent();
	});
	
}

//查看操作
function openView()
{
	if(winfos_map.get(currnet_info_id).content_url != null && winfos_map.get(currnet_info_id).content_url != "")
		window.open(winfos_map.get(currnet_info_id).content_url);
}

//编辑操作
function updateWareInfos()
{
	top.OpenModalWindow("编辑信息","/sys/system/ware/update_ware_infos.jsp?id="+currnet_info_id,630,340);
}

//替换操作
function changeWareInfos()
{		
	var sort_id = winfos_map.get(currnet_info_id).sort_id;
	top.OpenModalWindow("选择信息","/sys/system/ware/select_info_ref.jsp?action_type=change&winfo_id="+currnet_winfo_id+"&sort_id="+sort_id,630,460);
}

//删除信息
function deleteWareInfos()
{
	if(WareRPC.deleteWareInfosByID(currnet_info_id))
	{
		$("a[infos_id='"+currnet_info_id+"']").parent().remove();
	}
}

//左移,右移
function moveWareInfos(mType)
{
	if(mType == "left")
	{
		var prev_obj = $(current_li_obj).prev();
		$(current_li_obj).after(prev_obj);
	}else
	{
		var next_obj = $(current_li_obj).next();
		$(current_li_obj).before(next_obj);	
	}
	var ids = "";
	$(current_li_obj).parent().find("li").each(function(){
		ids += ","+$(this).find("a").attr("infos_id");
	})
	ids = ids.substring(1);	
	WareRPC.sortWInfos(ids);
}

/******************** 菜单按钮处理　结束 **********************/

function createWarePage()
{
	if(WareRPC.createHtmlPage(ware_id,site_id))
	{
		top.msgAlert("页面生成成功");	
	}else{
		top.msgWargin("静态页面生成失败");
	}
}