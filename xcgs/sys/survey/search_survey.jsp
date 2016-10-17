<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>问卷调查查询</title>


<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript"  src="../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/surveyList.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	<!--
		var s_id = request.getParameter("s_id");
		
		$(document).ready(function () {	
			getSurveyCategoryName();//得到问卷分类名称		
			initButtomStyle();
			init_input();
		}); 
		
		function subSearch()
		{
			var current_date = SurveyRPC.getCurrentDate();
			var str = "";
			if($("#key_word").val().trim() != "")
			{
				var v = new Validator();
				if(!v.checkSpecialStr($("#key_word").val(),"问卷调查名称"))
				{
					return;
				}
				str += " and cs.s_name like '%"+$("#key_word").val()+"%'";
			}
			if($("#category_id").val() != "")
			{
				str += " and cs.category_id = '"+$("#category_id").val()+"'";
			}

			var s_time = $("#s_time").val();
			var e_time = $("#e_time").val();
			if(e_time != "")
			{
				str += " and  cs.end_time < '"+e_time+"'";
			}
			if(s_time != "")
			{
				if(e_time == "")
				{//如果没有结束时间，需要把结束时间为空的算进去
					str += " and  (cs.end_time > '"+s_time+"' or cs.end_time is null)";
				}
				else
					str += " and  cs.end_time > '"+s_time+"'";				
			}
			
			if(s_time != "" && e_time != "" && s_time != e_time)
			{
				if(!judgeDate(s_time,e_time))
				{
					parent.alertN("结束日期不能小于开始日期");
					return;
				}
			}

			if($("#publish_status").val() != "")
			{
				str += " and cs.publish_status = "+$("#publish_status").val()+"";
			}
			
			var survey_status = $("#survey_status").val();
			if(survey_status != "")
			{
				if(survey_status == 0)
				{
					str += " and cs.publish_status != 1";
				}
				if(survey_status == 1)
				{// end_time 为空的话表示不限制结束时间　
					str += " and cs.publish_status = 1 and (cs.end_time is null or cs.end_time > '"+current_date+"')";
				}
				if(survey_status == 2)
				{
					str += " and (cs.end_time is not null and cs.end_time < '"+current_date+"')";
				}
			}
			
//alert(str)
		
			top.getCurrentFrameObj().searchHandl(str);
			top.CloseModalWindow();
			
		}
	//-->
	</SCRIPT>	
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<div id="surveyCategory">
<table id="jcxx_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>		  
		  <th>问卷调查名称：</th>
		  <td class="content_td"><input type="text" class="input_border" id="key_word" name="key_word" style="width:155px" maxlength="100"/></td>
		 </tr>
		 <tr>		  
		  <th>问卷调查分类：</th>
		  <td class="content_td"><select id="category_id" name="category_id" style="width:155px"><option value="">--请选择--</option></select></td>
		 </tr>
		 <tr>		  
		  <th>结束时间：</th>
		  <td class="content_td">
		    <input class="Wdate" type="text" class="input_border" name="s_time" id="s_time" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="true" style="width:120">
			--
			<input class="Wdate" type="text" class="input_border" name="e_time" id="e_time" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="true" style="width:120">
		  </td>
		 </tr>
		 <tr>
		  <th>发布状态：</th>
		  <td class="content_td">
		    <select id="publish_status" name="publish_status" style="width:155px">
			 <option value="">--请选择--</option>
			 <option value="0">未发布</option>
			 <option value="1">已发布</option>
			 <option value="-1">已撤消</option>
			</select>
		  </td>
		 </tr>
		 <tr>		 
		  <th>问卷调查状态：</th>
		  <td class="content_td">
		    <select id="survey_status" name="survey_status" style="width:155px">
			 <option value="">--请选择--</option>
			 <option value="0">设计中</option>
			 <option value="1">进行中</option>
			 <option value="2">已截止</option>
			</select>
		  </td>
		 </tr>
	</tbody>
</table>
</div>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="subSearch()" value="确定" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow()" value="关闭" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>

</form>
</body>
</html>
