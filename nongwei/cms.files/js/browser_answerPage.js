var AnswerRPC = jsonrpc.AnswerRPC;
var SurveyRPC = jsonrpc.SurveyRPC;
var s_id = request.getParameter("s_id");
var SurveyAnswer = new Bean("com.deya.wcm.bean.survey.SurveyAnswer",true);
var SurveyAnswerItem = new Bean("com.deya.wcm.bean.survey.SurveyAnswerItem",true);
var StatisticsBean = new Bean("com.deya.wcm.bean.survey.StatisticsBean",true);
var m;
var answer_count = 0;//答卷总数
var SurveyBean;
var is_show_title = true;
$(document).ready(function () {		
	SurveyBean = SurveyRPC.getSurveyBean(s_id);

	//判断结束时间是否为空
	if(SurveyBean.end_time != "" && SurveyBean.end_time != null)
	{
		//从服务器得到当前日期
		var current_date = SurveyRPC.getCurrentDate();
		//判断时间是否到期
		if(!judgeDateTime(current_date+" 00:00",SurveyBean.end_time+" 23:59"))
		{
			//截止后判断是否要显示结果
			if(SurveyBean.show_result_status == 0)
			{//不显示调查结果
				$("#survey_div").html('<div id="design_div">'+SurveyBean.survey_content+'</div>');
				setHTML();
				scaleReady();//设置量表题事件
				getVoteStatisData();//得到投票的统计数据
				
				$("#button_div").hide();
				$("#message_div").html("调查已经结束，感谢您的参与");
				$("#message_div").addClass("message_div")
			}
			if(SurveyBean.show_result_status == 1)
			{//显示调查结果 			
				window.location.href = "answerResult.jsp?s_id="+s_id;
			}
			if(SurveyBean.show_result_status == 2)
			{//显示文字说明
				$("#button_div").hide();
				$("#message_div").hide();
				$("#survey_div").html('<div id="s_name_show">'+SurveyBean.s_name+'</div><div id="s_description_show">'+SurveyBean.verdict+'</div>');
				
				return;
			}
		}
		else
		{
			showSurvey();
		}
	}
	else
	{		
		showSurvey();
	}
	if(!is_show_title)
		$("#s_name_show").hide();//不显示标题

});

function showSurvey()
{
	$("#survey_div").html('<div id="design_div">'+SurveyBean.survey_content+'</div>');
	if(SurveyBean.is_show_result == 1)
	{
		$("#showResultButton").attr("href","answerResult.jsp?cat_id="+SurveyBean.category_id+"&s_id="+s_id);
		$("#showResultButton").show();
	}

	setHTML();
	scaleReady();//设置量表题事件
	getVoteStatisData();//得到投票的统计数据
		
	//if(SurveyBean.is_register == 0 && memberA == null)
	if(SurveyBean.is_register == 0)
	{
		$("#sub_button").hide();
		$("#message_div").text("您还没有登录，请先登录再进行提交");
	}
}

//判断时间大小
function judgeDateTime(start_date,end_date)
{
	var reg_ymd=/^([\d]{4})-([\d]{1,2})-([\d]{1,2})\s([\d]{1,2}):([\d]{1,2})$/;
	var arr_dt1,arr_dt2,dt1,dt2;

	arr_dt1=start_date.match(reg_ymd);
	arr_dt2=end_date.match(reg_ymd);

	dt1=new Date(arr_dt1[1],arr_dt1[2],arr_dt1[3],arr_dt1[4],arr_dt1[5]);
	dt2=new Date(arr_dt2[1],arr_dt2[2],arr_dt2[3],arr_dt2[4],arr_dt2[5]);

	return dt2 > dt1;
}

//判断日期大小
function judgeDate(start_date,end_date)
{
	var reg_ymd=/^([\d]{4})-([\d]{1,2})-([\d]{1,2})$/;
	var arr_dt1,arr_dt2,dt1,dt2;

	arr_dt1=start_date.match(reg_ymd);
	arr_dt2=end_date.match(reg_ymd);

	dt1=new Date(arr_dt1[1],arr_dt1[2],arr_dt1[3]);
	dt2=new Date(arr_dt2[1],arr_dt2[2],arr_dt2[3]);

	return dt2 > dt1;
}
