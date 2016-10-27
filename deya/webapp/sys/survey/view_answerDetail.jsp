<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 

	<title>查看答卷详细内容</title> 
	<jsp:include page="../include/include_tools.jsp"/>
	<script src="js/statisticsList.js"></script>
	<style>
	body{background:#F5FAFE}
	#subjectItem{overflow:auto;height:520px}
	#subjectItem div{padding:5px}
	#subjectItem .answer_div{padding-left:12px}
	</style>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var div_height = 0;		
		var a_id = request.getParameter("a_id");
		var s_id = request.getParameter("sid");
		var types = request.getParameter("te");
		
		$(document).ready(function () {	
			setSubject();//设置所有题目
			setItemValue();

			if(types == "iframe")
			{
				$("#closeButton").click(function(){
					parent.closeModelWin();
				})
			}
			else
			{
				$("#closeButton").click(function(){
					window.close();
				})
			}
		}); 

		function setSubject()
		{
			var str = "";
			var subList = SurveyRPC.getSurveySubjectSingle(s_id);	 
			subList = List.toJSList(subList);
			if(subList != null && subList.size() > 0)
			{
				for(var i=0;i<subList.size();i++)
				{
					SurveySub = subList.get(i);
					
					str += '<div ><strong>第'+(i+1)+'题：</strong>'+SurveySub.sub_name+'['+getSubjectTypeName(SurveySub.subject_type)+']</div>';
					str += '<div class="answer_div"><strong>答案：</strong><span id="'+SurveySub.subject_id+'" type="'+SurveySub.subject_type+'"></span></div>';
				}
			}
			$("#subjectItem").html(str);
		}

		function setItemValue()
		{
			var itemList = SurveyRPC.getAnswerItemDetail(a_id);	
			itemList = List.toJSList(itemList);
			if(itemList != null && itemList.size() > 0)
			{
				for(var i=0;i<itemList.size();i++)
				{
					var m = itemList.get(i);
					m = Map.toJSMap(m);

					var subject_id = m.get("subject_id");
					var item_name = m.get("item_name");
					var item_text = m.get("item_text");
					if(item_name == null)
						item_name = "";
					var an_str = item_name;
					if(item_text != "" && item_text != null)
					{
						if($("#"+subject_id).attr("type") == "uploadfile")
							an_str += '[<a href="'+item_text+'" target="_blank">'+item_text+'</a>]';
						else
							an_str += "["+item_text+"]";
					}
					if($("#"+subject_id).html() != "")
						an_str = "&nbsp;|&nbsp;"+an_str;

					$("#"+subject_id).append(an_str);
				}
			}
		}
		
		
		
	//-->
	</SCRIPT>	
</head> 
<body> 
	<input type="hidden" id="handleId" name="handleId" value="H32001">
	<table border="0" cellpadding="0" cellspacing="0" width="98%" align="center">
	  <tr>
	   <td>
	    <div id="subjectItem" style="padding-top:10px;padding-left:8px"></div>
	   </td>
	  </tr>
	  <tr>
	   <td align="center">
		<div style="width:80px;height:24px">
			 <a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="closeButton" onclick="window.close()">&nbsp;关闭</span></a>			
			 </div>
	   </td>
	  </tr>
	 </table> 


</body> 

</html> 

