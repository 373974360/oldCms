var div_height = 0;		
var s_id = request.getParameter("s_id");
var m;
var answer_count = 0;//答卷总数
var SurveyRPC = jsonrpc.SurveyRPC;
var SurveySuvItem = new Bean("com.deya.wcm.bean.survey.SurveySuvItem",true);
var ChildItem = new Bean("com.deya.wcm.bean.survey.SurveyChildItem",true);
var StatisticsBean = new Bean("com.deya.wcm.bean.survey.StatisticsBean",true);
$(document).ready(function () {	
	SurveyBean = SurveyRPC.getSurveyBean(s_id);
	setSubjectList();				
}); 