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
			getSurveySubjectBean();//得到问卷所有的题型	
			initButtomStyle();
			init_input();
		}); 

		function getSurveySubjectBean()
		{
			var sub_list = SurveyRPC.getSurveySubjectBean(s_id);
			var str = "";
			if(sub_list != null)
			{
				sub_list = List.toJSList(sub_list);				
				
				for(var i=0;i<sub_list.size();i++)
				{
					if(sub_list.get(i).subject_type == "radioList" || sub_list.get(i).subject_type == "selectOnly" || sub_list.get(i).subject_type == "voteRadio" || sub_list.get(i).subject_type == "scale" || sub_list.get(i).subject_type == "checkboxList" || sub_list.get(i).subject_type == "voteCheckbox" || sub_list.get(i).subject_type == "textareas" || sub_list.get(i).subject_type == "matrix")
					{
						str += '<tr>';
						str += '<td height="32px" width="28px" class="content_td">&nbsp;</td>';
						str += '<td align="right" width="150px" class="content_td">'+sub_list.get(i).sub_name.replace(/&nbsp;<SPAN id=req_span class=wargin_span>\*?<\/SPAN>/ig,"").replace(/(<[^<>]+>)/g, "")+'：<td>';
						str += '<td class="content_td" subject_type="'+sub_list.get(i).subject_type+'">';
						
						try
						{
							if(sub_list.get(i).itemList != null)
							{
								var itemlist = sub_list.get(i).itemList;
								itemlist = List.toJSList(itemlist);								
								if(sub_list.get(i).subject_type == "checkboxList")
								{
									str += '<div style="width:90%;height:100px;overflow:auto">';
									var childList = itemlist.get(0).childList;
									childList = List.toJSList(childList);								
									if(childList != null && childList.size() > 0)
									{
										for(var j=0;j<childList.size();j++)
										{
											str += '<div style="height:20px;padding-top:3px"><input type="checkbox" id="'+itemlist.get(0).item_id+'" name="'+itemlist.get(0).item_id+'" value='+childList.get(j).item_num+'>'+childList.get(j).item_name+'</div>';
										}
									}
									str += '</div>';
									continue;
								}
								if(sub_list.get(i).subject_type == "textareas")
								{
									str += '<input type="text" id="'+itemlist.get(0).item_id+'" name="'+itemlist.get(0).item_id+'" style="width:200px">';
									continue;
								}
								if(sub_list.get(i).subject_type == "matrix")
								{
									for(var j=0;j<itemlist.size();j++)
									{
										str += '<div style="height:20px;padding-top:3px;padding-bottom:3px">';
										 str += itemlist.get(j).item_name+'<select id="'+itemlist.get(j).item_id+'" name="'+itemlist.get(j).item_id+'">';
										 str += '<option value="">--请选择--</option>';								
										 var childList = itemlist.get(0).childList;
										 childList = List.toJSList(childList);								
										 if(childList != null && childList.size() > 0)
										 {
											for(var k=0;k<childList.size();k++)
											{
												str += '<option value="'+childList.get(k).item_num+'">'+childList.get(k).item_name+'</option>';	
											}
										 }
										 str += '</select>';
										str +='</div>';
									}
									continue;
								}
								else
								{
									str += '<select id="'+itemlist.get(0).item_id+'" name="'+itemlist.get(0).item_id+'">';
									str += '<option value="">--请选择--</option>';								
									var childList = itemlist.get(0).childList;
									childList = List.toJSList(childList);								
									if(childList != null && childList.size() > 0)
									{
										for(var j=0;j<childList.size();j++)
										{
											str += '<option value="'+childList.get(j).item_num+'">'+childList.get(j).item_name+'</option>';	
										}
									}
									str += '</select>';
								}
							}
						}
						catch(e){}
						str += '</td>';
						str += '</tr>';
					}
				}
				$("#jcxx_tab").append(str);
			}
		}
		
		function subSearch()
		{
			var con = "";
			$("#jcxx_tab td[subject_type]").each(function(){
				if($(this).attr("subject_type") == "checkboxList" || $(this).attr("subject_type") == "voteCheckbox")
				{					
					if($(this).find(":checked").length > 0)
					{
						con += " and  (item_id = '"+$(this).find(":checkbox").attr("id")+"' ";
						$(this).find(":checked").each(function(){
							con += " and item_value = '"+$(this).val()+"' ";
						})

						con += ") ";
					}
				}
				if($(this).attr("subject_type") == "textareas")
				{			
					if($(this).find(":text").val().trim() != "")
					{
						con += " and  (item_id = '"+$(this).find(":text").attr("id")+"' and  item_text like '%"+$(this).find(":text").val()+"%')";
					}
				}
				if($(this).attr("subject_type") == "radioList" || $(this).attr("subject_type") == "selectOnly" || $(this).attr("subject_type") == "voteRadio" || $(this).attr("subject_type") == "scale" || $(this).attr("subject_type") == "radioList" || $(this).attr("subject_type") == "matrix")
				{
					$(this).find("select").each(function(){
						if($(this).find(":selected").val() != "")
							con += " and (item_id = '"+$(this).attr("id")+"' and item_value = '"+$(this).find(":selected").val()+"')";
					})					
				}
			});			
			
			top.CloseModalWindow();	
			top.getCurrentFrameObj().searchHandl(con);
					
		}
	//-->
	</SCRIPT>	
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<div id="surveyCategory">
<table  class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody id="jcxx_tab">
		
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
