var YsqgkRPC = jsonrpc.YsqgkRPC;
var YsqgkBean = new Bean("com.deya.wcm.bean.zwgk.ysqgk.YsqgkBean",true);

function init_input()
{
	$(":text").addClass("input_text");
	$(":text").blur( function () { $(this).removeClass("input_text_focus"); } );
	$(":text").focus( function () { $(this).addClass("input_text_focus"); } );
	
	$(":password").addClass("input_text");
	$(":password").blur( function () { $(this).removeClass("input_text_focus"); } );
	$(":password").focus( function () { $(this).addClass("input_text_focus"); } );
	
	$(":checkbox").addClass("input_checkbox");
	$("textarea").addClass("input_textarea");
	$("textarea").blur( function () { $(this).removeClass("input_textarea_focus"); } );
	$("textarea").focus( function () { $(this).addClass("input_textarea_focus"); } );
	
	$(":radio").addClass("input_radio");
	
	$("select").addClass("input_select");

	$("label").click(function(){		
		if($(this).prev("input").is(':checked'))
			$(this).prev("input").removeAttr("checked");
		else
			$(this).prev("input").attr("checked",true);
	});
}

function initYSQGKForm()
{
	/*
	var validator = $("#ysqForm").validate({
		rules: {
			name: {
				required: true
			},
			tel:{
				required: true
			},
			address:{
				required: true
			},
			gk_index:{
				required: true
			},
			node_id:{
				required: true
			}
		},
		messages: {
			name: {
				required: "请输入姓名！"
			},
			tel: {
				required: "请输入联系电话！"
			},
			address: {
				required: "请输入通讯地址！"
			},
			gk_index:{
				required: "请输入所需的信息索引号！"
			},
			node_id:{
				required: "请选择所需信息的机关单位！"
			}
			
		},		
		errorPlacement: function(error, element) {
			error.appendTo(getErrorObj(element));
		},

		submitHandler: function(form) {
			addYsqgkInfo();
		},

		success: function(label) {
			//label.text("ok!").addClass("success");
		}
	});
	*/
}

function getErrorObj(element)
{
	if(element.attr("type") == "radio")
	{ 
		return element.parent().parent().parent().find("div.cError");
	}
	else
	{
		return element.parent("td").find("div.cError");
	}
}

function SubmitInfo(n){	
	addYsqgkInfo();
}

//依申请公开信息处理开始
function addYsqgkInfo()
{
	var ysqgk_bean = BeanUtil.getCopy(YsqgkBean);
	$("#ysq_info").autoBind(ysqgk_bean);

	//if(!standard_checkInputInfo("ysq_info"))
	//{
	//	return;
	//}
	ysqgk_bean.node_id = $(":checked[id='node_id']").val();
	ysqgk_bean.content= $("#content").val();	
	
	var m = YsqgkRPC.insertYsqgkInfoForBro(ysqgk_bean);

	if(m != null )
	{
		m = Map.toJSMap(m);
		$("#ysq_code_span").text(m.get("ysq_code"));
		$("#query_code_span").text(m.get("query_code"));	

		$("#ysq_info").hide();
		$("#ysq_result_info").show();
	}
	else
	{
		alert("保存失败,请重新保存");
		return;
	}
}
