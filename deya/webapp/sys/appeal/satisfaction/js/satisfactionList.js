var SatisfactionRPC = jsonrpc.SatisfactionRPC;
var SatisfactionBean = new Bean("com.deya.wcm.bean.appeal.satisfaction.SatisfactionBean",true);

var val= new Validator();
var tp = new TurnPage();

var option_list = new List();

var table = new Table();	
	table.table_name = "add_zbstep_table";;

function reloadSatisfactionList()
{
	
	SatisfactionBean.SatisfactionOptions_list = SatisfactionRPC.getSatisfactionList();//第一个参数为站点ID，暂时默认为空
	if(SatisfactionBean)
	{
		$("#add_zbstep_table").autoFill(SatisfactionBean.SatisfactionOptions_list);	
		option_list = List.toJSList(SatisfactionBean.SatisfactionOptions_list);
		if(option_list != null && option_list.size() > 0)
		{
			for(var i=0;i<option_list.size();i++)
			{
				addStep();
				$("#add_zbstep_table tr").eq(i).find(".itemWidth").val(option_list.get(i).sat_item);
				$("#add_zbstep_table tr").eq(i).find(".valueWidth").val(option_list.get(i).sat_score);
			}
		}
	}	
}
//添加满意度指标
function addSatisfaction()
{
	var bean = BeanUtil.getCopy(SatisfactionBean);	
	$("#add_zbstep_table").autoBind(bean);

	if(!standard_checkInputInfo("add_zbstep_table"))
	{
		return;
	}
	var optionList = new List();
	$("#add_zbstep_table tr").each(function(i){
		
		var satisfaction = BeanUtil.getCopy(SatisfactionBean);
		var sat_item_input = $(this).find(".itemWidth");
		var sat_score_input = $(this).find(".valueWidth");
		
		satisfaction.sat_item = sat_item_input.val();
		satisfaction.sat_score = sat_score_input.val();
		optionList.add(satisfaction);
	});
	if(SatisfactionRPC.insertSatisfaction(optionList))
	{
		msgAlert("满意度指标"+WCMLang.Add_success);
		locationSatisfaction();
	}
	else
	{
		msgWargin("满意度指标"+WCMLang.Add_fail);
	}
	init_input();
	initButtomStyle();
}
function locationSatisfaction()
{
	window.location.href = "satisfaction.jsp";
}
