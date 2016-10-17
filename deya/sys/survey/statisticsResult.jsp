<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 

	<title>统计结果查看页面</title> 
	<jsp:include page="../include/include_tools.jsp"/>
	<script src="js/statisticsList.js"></script>
	<style>
	BODY DIV TD{font-size:12px;}	
	
	#table{width:500px;margin:0 auto;}
	#table table{margin-top:5px;margin-bottom:10px}
	#table td{height:20px;text-align:center}
	.sub_div{text-align:left;font-weight:bold;padding:5px}
	.item_div{margin-left:24px;text-align:left;}
	.child_title_div{font-weight:bold;font-size:12px;}
	/*问卷标题样式*/
	.s_name_show{font-weight:bold;font-size:24px;text-align:center}
	#button_div{width:100px;margin:12px auto;cursor:pointer;}

	div.pro_back {background:transparent url(../images/pro_back.png) no-repeat scroll 0 0;float:left;height:14px;margin:3px 0 0 5px;padding:0 0 0 1px;width:150px;}
	div.pro_back .pro_fore{overflow:hidden;display: block;}

	.detail_hover{color:#A94B3B;cursor:pointer}
	#text_detail_span{color:#A94B3B;cursor:pointer}
	</style>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var div_height = 0;		
		var s_id = request.getParameter("s_id");
		var m;
		var answer_count = 0;//答卷总数
		$(document).ready(function () {				
			setSubjectList();				
		}); 
			
		function setSubjectList()
		{
			//取出所有统计数据，为考虑性能，没有分批按题目取数据，而是一次取出所有选项的统计值，再根据item_id插入到相应的表格中，还需要单独计算其分数或余数
			m = SurveyRPC.getStatisticsDataBySurvey(s_id);
			m = Map.toJSMap(m);
			answer_count = parseInt(m.get("answer_count"));
			
			var dataList = m.get("dataList");//统计数据

			var str = '<div class="s_name_show">'+opener.SurveyBean.s_name+'</div>';
			var subList = SurveyRPC.getSurveySubjectBean(s_id);	 
			subList = List.toJSList(subList);
			if(subList != null && subList.size() > 0)
			{
				for(var i=0;i<subList.size();i++)
				{
					SurveySub = subList.get(i);
					if(SurveySub.subject_type != "textareas" && SurveySub.subject_type != "uploadfile")
					{
						str += '<div class="sub_div">第'+(i+1)+'题：'+SurveySub.sub_name+'</div><div class="item_div">';
						str += setSubjectItem(SurveySub.subject_type,SurveySub.itemList);
						str += '</div>';
					}
				}
			}
			$("#table").html(str);

			//计算余数，补齐为空的数据
			calculateRemainder();
		}

		function getStatisBean(key)
		{
			if( m.get(key) != null)
				return m.get(key)
			else
			{
				var sbean = BeanUtil.getCopy(StatisticsBean);
				sbean.item_id = "";
				sbean.item_num = "";
				sbean.counts = 0;
				sbean.proportion = "0%";

				return sbean;
			}
		}

		function setSubjectItem(subject_type,SurveySub_itemList)
		{
			var str = "";
			var itemList = SurveySub_itemList;
			itemList = List.toJSList(itemList);
			if(itemList != null && itemList.size() > 0)
			{	
				
				if(subject_type == "radioList" || subject_type == "selectOnly" || subject_type == "checkboxList" || subject_type == "voteRadio" || subject_type == "voteCheckbox" || subject_type == "scale")
				{
					str += '<table border="1" cellpadding="0" cellspacing="0"  id="'+SurveySuvItem.item_id+'" type="'+subject_type+'">';
					SurveySuvItem = itemList.get(0);
					var childList = SurveySuvItem.childList;
					childList = List.toJSList(childList);					 								 
					 if(childList != null && childList.size() > 0)
					 {
						for(var j=0;j<childList.size();j++)
						{
							var ids = SurveySuvItem.item_id+'_'+childList.get(j).item_num;
							var is_text = childList.get(j).is_text;
							var text_str = "";
							
							str += '<tr>';
							 str += '<td width="150px" style="text-align:left" score="'+childList.get(j).score+'">'+childList.get(j).item_name+'</td>';
							 str += '<td id="'+ids+'"  style="display:none">'+getStatisBean(ids).counts+'</td>';
							 str += '<td id="pic_'+ids+'" style="text-align:left" width="150px"><div class="pro_back"><div class="pro_fore" style="width:'+getStatisBean(ids).proportion+'"><img height="13" width="149px" alt="" src="../images/pro_fore.png"></div></div></td>'
							 str += '<td id="pro_'+ids+'" style="text-align:left" >'+getStatisBean(ids).counts+'票('+getStatisBean(ids).proportion+')</td>';
							str += '</tr>';
						}
					 }
					 /*只有单独列表形式的题型才有空这一项，设定它的ID为singlnList
					 str += '<tr>';
					  str += '<td id="singlnList" style="text-align:left">(空)</td>';
					  str += '<td id="'+SurveySuvItem.item_id+'_0"  style="display:none">&nbsp;</td>';
					  str += '<td style="text-align:left" width="165px"><div class="pro_back"><div class="pro_fore" style="width:0%"><img height="13" width="158" alt="" src="../images/pro_fore.png"></div></div></td>'
					  str += '<td style="text-align:left" id="pro_'+SurveySuvItem.item_id+'_0">&nbsp;</td>';
					 str += '</tr>';	
					 
					 str += '<tr>';
					  str += '<td ><strong>本题有效填写人次</strong></td>';
					  str += '<td>'+answer_count+'</td>';
					  str += '<td colspan="2">&nbsp;</td>';
					 str += '</tr>';	*/				 
					str += '</table>';
				}

				if(subject_type == "matrix")
				{
				   str += '<div id="'+SurveySuvItem.item_id+'_div">';
				    
					  /*矩阵题的详细信息*/	
					  str += '<div id="detail_div" style="clear:both;border:1px soild red;text-align:left;padding:5px;width:98%;margin:0 auto;" >';
					     for(var i=0;i<itemList.size();i++)
					     {
							SurveySuvItem = itemList.get(i);
						    var childList = SurveySuvItem.childList;
						    childList = List.toJSList(childList);
					        
							str += '<div class="child_title_div">'+SurveySuvItem.item_name+'</div>';
							str += '<table border="1" cellpadding="0" cellspacing="0" id="'+SurveySuvItem.item_id+'">';
							if(childList != null && childList.size() > 0)
							{
							    for(var j=0;j<childList.size();j++)
								{
									var ids = SurveySuvItem.item_id+'_'+childList.get(j).item_num;
								    str += '<tr item_id="'+SurveySuvItem.item_id+'">';
								     str += '<td width="150px" style="text-align:left">'+childList.get(j).item_name+'</td>';
									 str += '<td id="'+ids+'" score="'+childList.get(j).score+'" style="display:none">'+getStatisBean(ids).counts+'</td>';									
									 str += '<td id="pic_'+ids+'" style="text-align:left" width="150px"><div class="pro_back"><div class="pro_fore" style="width:0%"><img height="13" width="149" alt="" src="../images/pro_fore.png"></div></div></td>'
							 str += '<td id="pro_'+ids+'" style="text-align:left" >&nbsp;</td>';
									str += '</tr>';
								}
							}							
							str += '</table>';
						 }
					  str += '</div>';	
					 str += '</div>';
				}	
				
			}
			return str;
		}
		
		//计算余值
		function calculateRemainder()
		{
			//根据td名为singlnList的对象找到它的父节点table对象
			/*
			$("td[id=singlnList]").parent().parent().parent().each(function(){
			
				var trNum = $(this).find("tr").length;
				var counts = 0;
				var pro = 0;
				$(this).find("tr").each(function(i){
				
					if(i<trNum-2)
					{						
							counts += parseInt(getStatisBean($(this).find("td:nth-child(2)").attr("id")).counts);
							
							pro += parseInt(getStatisBean($(this).find("td:nth-child(2)").attr("id")).proportion);																		
					}
					if(i == trNum-2)
					{
						if(answer_count-counts > 0)
						{
							//$(this).find("td:nth-child(2)").text(answer_count-counts);							
							$(this).find("td:nth-child(4)").text((answer_count-counts)+"票("+(100-pro)+"%)");
							$(this).find(".pro_fore").css("width",(100-pro)+"%");
						}
						else
						{
							$(this).find("td:nth-child(3)").html("&nbsp;");
						}
					}
				})	
			})	*/

			//处理矩阵题中，明细内容的数据
			$("div[id=detail_div]").each(function(){
				$(this).find("table").each(function(){
					var trNum = $(this).find("tr").length;
					var all_count = 0;
					
					$(this).find("tr").each(function(i){						
						all_count += getStatisBean($(this).find("td:nth-child(2)").attr("id")).counts;						
					})					

					$(this).find("tr").each(function(i){						
						//计算每项所占百分比
						var counts = getStatisBean($(this).find("td:nth-child(2)").attr("id")).counts;								
		
						var pro = parseFloat((counts/all_count)*100).toFixed(2);
				
						$(this).find("td:nth-child(4)").text(counts+"票("+pro+"%)");
						$(this).find(".pro_fore").css("width",pro+"%");						
					})
				})
			})
		}
		
	//-->
	</SCRIPT>	
</head> 
<body> 
	
<div id="table"></div>		
<div id="button_div"><button onclick="window.close()">关闭</button></div>
</body> 

</html> 

