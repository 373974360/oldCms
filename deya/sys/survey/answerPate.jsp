<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 

	<title>问卷调查</title> 
	
	<link rel="stylesheet" type="text/css" href="../style/menu/easyui.css">
	<link rel="stylesheet" type="text/css" href="../js/jquery_rater/rater.css" /> 
	<script type="text/javascript"  src="../js/jsonrpc.js"></script>	
	<script type="text/javascript"  src="../js/util.js"></script>	
	<script type="text/javascript"  src="../js/jquery-1.4.2.min.js"></script>	
	<script type="text/javascript"  src="../js/java.js"></script>		
	<script type="text/javascript"  src="../js/jquery.c.js"></script>	
	<script type="text/javascript"  src="../js/validator.js"></script>		
	<script type="text/javascript"  src="../js/common.js"></script>
	<script type="text/javascript"  src="../js/extend.js"></script>	
	<script type="text/javascript"  src="js/surveyList.js"></script>
	<script type="text/javascript"  src="js/browserPage.js"></script>
	<script type="text/javascript"  src="/js/memberCheck.js"></script>
	
	<script type="text/javascript">
	<!--
		var AnswerRPC = jsonrpc.AnswerRPC;
		var s_id = request.getParameter("sid");
		var SurveyAnswer = new Bean("com.deya.gpps.bean.survey.SurveyAnswer",true);
		var SurveyAnswerItem = new Bean("com.deya.gpps.bean.survey.SurveyAnswerItem",true);
		var StatisticsBean = new Bean("com.deya.gpps.bean.survey.StatisticsBean",true);
		var m;
		

		$(document).ready(function () {		
			SurveyBean = SurveyRPC.getSurveyBean(s_id);
			
			$("#survey_div").html('<div id="design_div">'+SurveyBean.survey_content+'</div>');
			if(SurveyBean.is_show_result == 1)
			{
				$("#showResultButton").attr("href","statisticsResult.html?s_id="+s_id);
				$("#showResultButton").show();
			}

			setHTML();
			scaleReady();//设置量表题事件
			getVoteStatisData();//得到投票的统计数据
		
			if(SurveyBean.end_time.trim() != "")
			{
				if(!judgeDate(AnswerRPC.getCurrentDate(),SurveyBean.end_time))
				{
					$("#sub_button").hide();
				}
			}
			if(SurveyBean.is_register == 0)
			{
				$("#sub_button").hide();
				$("#message_div").text("您还没有登录，请先登录再进行提交");
			}
			
		})


		
	//-->
	</script>
	<style>

	BODY DIV{font-size:12px;}	
	.blankH5{height:2px;clear:both;overflow:hidden;display:block;}	
	#button_div{width:200px;margin:12px auto;cursor:pointer;}
	
	/*问卷框架div样式*/
	#survey_div{width:900px;margin:0 auto;text-align:center;height:100%;overflow:hidden;clear:both;}
	/*问卷里层div样式*/
	#design_div{width:90%;margin:0 auto;}

	
	/*问卷标题样式*/
	#s_name_show{font-weight:bold;font-size:24px}
	/*问卷描述内容样式*/
	#s_description_show{width:98%;text-align:left;text-indent:2em;}
	/*问卷选项标题外层div*/
	#title_divs{margin-top:13px;height:20px;text-align:left;font-weight:bold;HEIGHT: auto;line-height:20px}
	/*题目序号外层DIV样式*/
	.sort_num_div{WIDTH: 20px; FLOAT: left; FONT-WEIGHT: bold;}
	/*题目标题外层DIV样式*/
	#title_span{overflow: hidden;}
	/*问卷选项内容div样式，缩进２个字符*/
	#des_items_divs{margin-left:24px;text-align:left;}
	/*必选题的*号样式 id 为　req_span*/
	.wargin_span{color:red;cursor:pointer}	
	/*选项提示信息*/
	#description_div{text-align:left;text-indent:2em;margin-top:5px;clear:both;}
	
	/*单选，多选列表样式*/
	#item_ul{padding:0px;margin:0px;text-align:left;}
	#item_ul li{list-style-type:none;}
	.li_css1{float:left;width:90%}
	.li_css2{float:left;width:49%}
	.li_css3{float:left;width:31%}
	.li_css4{float:left;width:24%}
	.li_css5{float:left;width:19%}
	.li_css6{float:left;width:16%}
	.li_css7{float:left;width:14%}
	.li_css8{float:left;width:12%}
	.li_css9{float:left;width:11%}
	.li_css10{float:left;width:9%}

	/*选项中图片外层div*/
	#item_img_div{padding-bottom:5px;}
	/*选项中图片描述内容外层div*/
	#show_img_des_div{width:300px;position:absolute;background:#FFFFFF;padding:5px;border:1px solid #9FB2C7;text-indent:2em;line-height:20px;display:none;text-align:left}
	
	/*量表图样式*/
	.scale{margin-right:12px}
	.scale LI {LIST-STYLE-TYPE: none; FLOAT: left}
	.scale_li{WIDTH: 32px; BACKGROUND: url(../images/scale.gif) no-repeat; HEIGHT: 32px; CURSOR: pointer; TEXT-DECORATION: none;PADDING-LEFT: 3px}
	.scale_li_selected{WIDTH: 32px; BACKGROUND: url(../images/scale_selected.gif) no-repeat; HEIGHT: 32px; CURSOR: pointer; TEXT-DECORATION: none;PADDING-LEFT: 3px}
	.scale_li_radio{padding-right:12px}
	#items_divs TH{padding-top:12px}

	/*投票题*/
	.pro_back {background:transparent url(../images/pro_back.png) no-repeat scroll 0 0;float:left;height:14px;margin:3px 0 0 5px;padding:0 0 0 1px;width:150px;}
	.pro_back .pro_fore{overflow:hidden;display: block;}

	/*矩阵题横向字符样式*/
	.m_td{font-weight:bold;height:25px}
	</style>
</head> 
<body > 
<div id="survey_div" ></div>
<div id="button_div"><button id="sub_button" onclick="fnOK()">提交</button>　　<a href="" target="_blank" id="showResultButton">查看结果</a></div>
<div id="message_div"></div>
</body> 

</html> 

