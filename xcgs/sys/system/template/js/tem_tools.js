var cms_item = [["info_id","ID"],["pre_title","标题前缀"],["title","标题"],["subtitle","副标题"],["thumb_url","缩略图"],["author","作者"],["editor","编辑"],["source","来源"],["weight","权重"],["hits","总点击数"],["day_hits","日点击数"],["week_hits","周点击数"],["month_hits","月点击数"],["released_dtime","发布时间"],["cat_cname","所属栏目"],["description","摘要"]];
var ggfw_item = [["info_id","ID"],["title","标题"],["subtitle","副标题"],["thumb_url","缩略图"],["author","作者"],["source","来源"],["weight","权重"],["hits","总点击数"],["day_hits","日点击数"],["week_hits","周点击数"],["month_hits","月点击数"],["released_dtime","发布时间"],["cat_cname","所属栏目"],["description","摘要"]];
var zwgk_item = [["info_id","ID"],["title","标题"],["subtitle","副标题"],["author","作者"],["source","来源"],["weight","权重"],["hits","总点击数"],["day_hits","日点击数"],["week_hits","周点击数"],["month_hits","月点击数"],["released_dtime","发布时间"],["cat_cname","所属栏目"],["gk_index","索引号"],["doc_no","文号"],["generate_dtime","生成日期"],["effect_dtime","生效日期"],["gk_duty_dept","责任部门（科室）"],["gk_input_dept","发布机构"],["topic_id","主题分类ID"],["topic_name","主题分类名称"],["theme_id","体裁分类ID"],["theme_name","体裁分类名称"],["serve_id","服务对象分类ID"],["serve_name","服务对象分类名称"],["description","摘要"]];
var appeal_item = [["model_id","业务ID"],["sq_id","信件ID"],["sq_title2","信件标题"],["submit_name","提交部门"],["sq_dtime","提交时间"],["sq_status","信件状态"],["model_cname","业务名称"]];
var interview_item = [["sub_id","访谈主题ID"],["category_id","访谈分类ID"],["sub_name","访谈主题名称"],["start_time","开始时间"],["actor_name","访谈嘉宾名称"],["intro","访谈简介"],["s_for_pic","预告图片"]];
var survey_item = [["s_id","调查ID"],["s_name","主题名称"],["description","描述信息"],["start_time","开始时间"],["end_time","结束时间"],["is_end","结束标识"]];
//根据应用系统获取列表字段列表
function getItemByAppID()
{
	$("#all_item_list").empty();
	$("#select_item_list").empty();
	var arr = eval(app_id+"_item");
	
	for(var i=0;i<arr.length;i++)
	{
		$("#all_item_list").append('<li style="float:none;height:20px"><input type="checkbox" id="item_id" value="'+arr[i][0]+'" onclick="selectedItem(this,\''+arr[i][0]+'\',\''+arr[i][1]+'\')"><label onclick="lableClicks(this)">'+arr[i][1]+'</label></li>');
	}
	init_input();
}

function setModelInfoList()
{
	$("#model_id").empty();
	if(app_id == "ggfw")
	{
		var list = jsonrpc.ModelRPC.getCANModelListByAppID("cms");
		list = List.toJSList(list);
		$("#model_id").addOptionsSingl("","全部　　");
		$("#model_id").addOptions(list,"model_id","model_name");

		list = jsonrpc.ModelRPC.getCANModelListByAppID("zwgk");
		$("#model_id").addOptions(list,"model_id","model_name");
	}
	else
	{		
		var list = jsonrpc.ModelRPC.getCANModelListByAppID(app_id);
		list = List.toJSList(list);
		$("#model_id").addOptionsSingl("","全部　　");
		$("#model_id").addOptions(list,"model_id","model_name");
	}
}

//设置日期格式样式
function setFomatDate()
{
	showSelectDiv("format_date","format_select",110,"leftMenuBox");
	$("#format_selectList li").hover(
	  function () {
		$(this).css("background-color","#5199E6");
	  },
	  function () {
		$(this).css("background-color","#FFFFFF");
	  }
	);
}

//诉求业务
function setSQModelList()
{
	$("#model_id").empty();
	var list = jsonrpc.SQModelRPC.getAllSQModelList();
	list = List.toJSList(list);	
	$("#model_id").addOptionsSingl("sqAll","　　");
	$("#model_id").addOptions(list,"model_id","model_cname");
}

function resetForm()
{
	form1.reset();
	$("#all_item_list").empty();
	$("#select_item_list").empty();
	getItemByAppID();
}

function selectedItem(obj,item_id,item_name)
{
	
	if($(obj).is(':checked'))
	{
		$("#select_item_list").append('<li style="float:none;height:20px" item_id="'+item_id+'"><ul class=\"optUL\"><li style="width:100px">'+item_name+'</li><li class=\"opt_up ico_up\" title=\"上移\"></li><li class=\"opt_down ico_down\" title=\"下移\"></li><li class=\"opt_delete ico_delete\" title=\"删除\"></li></ul></li>');

		 resetNum();
	}else
	{
		$("#select_item_list li[item_id='"+item_id+"']").remove();
	}
}

function iniOpt()
{	
	$(".opt_up").live("click",function(){	
		var tmpObj = $(this).parent().parent();		
		moveUpTr(tmpObj);
	});
	
	$(".opt_down").live("click",function(){		
		var tmpObj = $(this).parent().parent();		
		moveDownTr(tmpObj);
	});
	
	$(".opt_delete").live("click",function(){		
		var tmpObj = $(this).parent().parent();		
		deleteTr(tmpObj);
	});
	
}

//删除
function deleteTr(obj)
{
	$("#all_item_list :checkbox[value='"+$(obj).attr("item_id")+"']").attr("checked",false);
	$(obj).remove();
	resetNum();
}

//上移
function moveUpTr(obj)
{
	if($(obj).index==0) return;	
	$($(obj).prev()).before($(obj));
	resetNum();
}

//下移
function moveDownTr(obj)
{	
	$($(obj).next()).after($(obj));
	resetNum();
}

//重排
function resetNum(){
	$("#select_item_list").find(".opt_up").addClass("ico_up");
	$("#select_item_list").find(".opt_down").addClass("ico_down");
	
	//首行、未行去除移动图标
	$("#select_item_list > li").first().find(".opt_up").removeClass("ico_up");	
	$("#select_item_list > li").last().find(".opt_down").removeClass("ico_down");
}


//点击lable触发事件
function lableClicks(obj)
{
	$(obj).prev().attr("checked",$(obj).prev().is(':checked') == false ? true:false);
	$(obj).prev().click();
}

//权限范围
function changeWeightSpan(obj)
{
	if($(obj).is(':checked'))
		$("#weight_span").show();
	else
		$("#weight_span").hide();
}

function infoPublicStr()
{
	var str = "";	
	var model_id = $("#model_id :selected").val();
	
	if(model_id != "" && model_id != null)
	{
		if(model_id == "sqAll")
		{
			str += ";model_id=$model_id";
		}
		else
			str += ";model_id="+model_id;
	}
	var page_size = $("#page_size").val();
	if(page_size != "" && page_size != null)
	{
		str += ";size="+page_size;
	}	
	var weight = $("#weight").val();
	if($("#weight_fw").is(':checked'))
	{
		var weight_end = $("#weight_end").val();
		if((weight == "" || weight == null) && (weight_end == "" || weight_end == null))
		{}else
			str += ";weight="+weight+","+weight_end;
	}else
	{
		if(weight != "" && weight != null)
		{
			str += ";weight="+weight;
		}
	}
	var order_name = $("#order_name :selected").val();
	var order_type = $("#order_type :selected").val();
	var order_name1 = $("#order_name1 :selected").val();
	var order_type1 = $("#order_type1 :selected").val();
	if(order_name1 != "")
	{
		str += ";orderby="+order_name+" "+order_type+","+order_name1+" "+order_type1;
	}else
	{
		str += ";orderby="+order_name+" "+order_type;
	}
	return str;
}