var ErrorReportBean = new Bean("com.deya.wcm.bean.system.error.ErrorReportBean",true);

$(document).ready(function(){
	$("#adderror").click(adderror);
})

function adderror(){
	  var n = 0;
	  var value = "";
	  var len = $(":checkbox[name='err_type'][checked]").length;
	  $(":checkbox[name='err_type'][checked]").each(function(){ 
	  	   n++;
	  	   if(n==len){
		   	  value += $(this).val();
		   }else{
		      value += $(this).val() + ",";
		   }
     });
	if(value==''){
		alert("请选择错误类型！");
		return;
	}
	
	if($.trim($("#info_url").val())==''){
		alert("请填写出错页面的地址！");
		return;
	}
	ErrorReportBean.info_id = $("#info_id").val();
	ErrorReportBean.err_type = value;
	ErrorReportBean.info_url = $("#info_url").val();
	ErrorReportBean.info_title = $("#info_title").html();
	ErrorReportBean.err_content = $("#err_content").val();
	ErrorReportBean.err_name = $("#err_name").val();
	ErrorReportBean.err_name_tel = $("#err_name_tel").val();
	
	if(jsonrpc.ErrorReportRPC.addErrorReport(ErrorReportBean)){
		alert("提交成功！");
		window.close();
	}else{
		alert("提交失败！");
	}

}