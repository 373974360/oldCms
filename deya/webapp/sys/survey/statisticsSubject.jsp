<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>问卷调查管理列表页面</title>


<jsp:include page="../include/include_tools.jsp"/>
<script src="js/statisticsList.js"></script>
<style>
.sub_div{text-align:left;font-weight:bold;padding:5px}
#table{overflow:auto;}
#table table{margin-top:5px;margin-bottom:10px}
#table td{height:20px;text-align:center}

div.pro_back {background:transparent url(../images/pro_back.png) no-repeat scroll 0 0;float:left;height:14px;margin:3px 0 0 5px;padding:0 0 0 1px;width:150px;}
div.pro_back .pro_fore{overflow:hidden;display: block;}

.detail_hover{color:#A94B3B;cursor:pointer}
#text_detail_span{color:#A94B3B;cursor:pointer}

.table_border{border-top:1px solid #9FB2C7;border-left:1px solid #9FB2C7;}
#table TD{border-bottom:1px solid #9FB2C7;border-right:1px solid #9FB2C7;}
</style>
<SCRIPT LANGUAGE="JavaScript">
	<!--
		var div_height = 0;		
		var s_id = request.getParameter("sid");
		var con = "";
		var m;
		var answer_count = 0;//答卷总数
		$(document).ready(function () {	
				searchHandl("");
				initButtomStyle();
		}); 

		
		function searchHandl(str)
		{
			con = str;
			setSubjectList();
			setShowDetailClick();
		}
			
		function setSubjectList()
		{
			//取出所有统计数据，为考虑性能，没有分批按题目取数据，而是一次取出所有选项的统计值，再根据item_id插入到相应的表格中，还需要单独计算其分数或余数
			m = SurveyRPC.getStatisticsDataBySurvey(s_id,con);
			m = Map.toJSMap(m);
			answer_count = parseInt(m.get("answer_count"));
			var dataList = m.get("dataList");//统计数据

			var str = "";
			var subList = SurveyRPC.getSurveySubjectBean(s_id);	 
			subList = List.toJSList(subList);
			if(subList != null && subList.size() > 0)
			{
				for(var i=0;i<subList.size();i++)
				{
					SurveySub = subList.get(i);
					
					str += '<div class="sub_div">第'+(i+1)+'题：'+SurveySub.sub_name+'['+getSubjectTypeName(SurveySub.subject_type)+']</div>';
					str += setSubjectItem(SurveySub.subject_type,SurveySub.itemList);
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
					str += '<table border="0" class="table_border" cellpadding="2" cellspacing="0" width="98%" id="'+SurveySuvItem.item_id+'" type="'+subject_type+'">';
					SurveySuvItem = itemList.get(0);
					var childList = SurveySuvItem.childList;
					childList = List.toJSList(childList);
					 str += '<tr>';
					  str += '<td width="150px"><strong>选项</strong></td>';
					  str += '<td width="120px"><strong>小计</strong></td>';
					  str += '<td colspan="2"><strong>比例</strong></td>';
					 str += '</tr>';									 
					 if(childList != null && childList.size() > 0)
					 {
						for(var j=0;j<childList.size();j++)
						{
							var ids = SurveySuvItem.item_id+'_'+childList.get(j).item_num;
							var is_text = childList.get(j).is_text;
							var text_str = "";
							if(is_text == 1)
							{
								text_str = '　<span id="text_detail_span" onclick="openTextDetailWindow(\''+SurveySuvItem.item_id+'\',\''+childList.get(j).item_num+'\',\'\')">[详细]</span>';
							}


							str += '<tr>';
							 str += '<td style="text-align:left" score="'+childList.get(j).score+'">'+childList.get(j).item_name+text_str+'</td>';
							 str += '<td id="'+ids+'">'+getStatisBean(ids).counts+'</td>';
							 str += '<td id="pic_'+ids+'" style="text-align:left" width="150px"><div class="pro_back"><div class="pro_fore" style="width:'+getStatisBean(ids).proportion+'"><img height="13" width="149px" alt="" src="../images/pro_fore.png"></div></div></td>'
							 str += '<td id="pro_'+ids+'" style="text-align:left" >'+getStatisBean(ids).proportion+'</td>';
							str += '</tr>';
						}
					 }
					 str += '<tr>';
					  str += '<td id="singlnList" style="text-align:left">(空)</td>';/*只有单独列表形式的题型才有空这一项，设定它的ID为singlnList*/
					  str += '<td id="'+SurveySuvItem.item_id+'_0">&nbsp;</td>';
					  str += '<td style="text-align:left" width="165px"><div class="pro_back"><div class="pro_fore" style="width:0%"><img height="13" width="158" alt="" src="../images/pro_fore.png"></div></div></td>'
					  str += '<td style="text-align:left" id="pro_'+SurveySuvItem.item_id+'_0">&nbsp;</td>';
					 str += '</tr>';				
					 str += '<tr>';
					  str += '<td ><strong>本题有效填写人次</strong></td>';
					  str += '<td>'+answer_count+'</td>';
					  str += '<td colspan="2">&nbsp;</td>';
					 str += '</tr>';					 
					str += '</table>';
				}

				if(subject_type == "matrix")
				{
				   str += '<div id="'+SurveySuvItem.item_id+'_div">';
				    str += '<div style="text-align:left;padding-top:5px;width:98%;margin:0 auto"><strong>该矩阵题平均分：<span id="average_span">0</span>　<span id="showDetail_span">查看详细数据</span></strong></div>'
				    str += '<table border="0" class="table_border" cellpadding="2" cellspacing="0" width="98%" id="'+SurveySuvItem.item_id+'" type="'+subject_type+'">';
					 str += '<tr>';
					  str += '<td width="120px"><strong>题目\选项</strong></td>';
					  SurveySuvItem = itemList.get(0);
					  var childList = SurveySuvItem.childList;
					  childList = List.toJSList(childList);
					  for(var i=0;i<childList.size();i++)
					  {
				 		 str += '<td><strong>'+childList.get(i).item_name+'</strong></td>';
					  }
					  str += '</tr>';
					  for(var i=0;i<itemList.size();i++)
					  {
					  	  SurveySuvItem = itemList.get(i);
						  var childList = SurveySuvItem.childList;
						  childList = List.toJSList(childList);
						 
						  str += '<tr item_id="'+SurveySuvItem.item_id+'">';
						   str += '<td>'+SurveySuvItem.item_name+'</td>';
						   if(childList != null && childList.size() > 0)
						   {
							  for(var j=0;j<childList.size();j++)
							  {
								  var ids = SurveySuvItem.item_id+'_'+childList.get(j).item_num;
							  	  str += '<td id="'+ids+'" score="'+childList.get(j).score+'">'+getStatisBean(ids).counts+'</td>';
							  }
						   }
						  str += '</tr>';						   
					  }
					  str += '</table>';
					  /*矩阵题的详细信息*/	
					  str += '<div id="detail_div" style="clear:both;border:1px soild red;text-align:left;padding:5px;width:98%;margin:0 auto;display:none" >';
					     for(var i=0;i<itemList.size();i++)
					     {
							SurveySuvItem = itemList.get(i);
						    var childList = SurveySuvItem.childList;
						    childList = List.toJSList(childList);
					        
							str += '<div>['+(i+1)+']'+SurveySuvItem.item_name+'</div>';
							str += '<div style="text-align:left;padding-top:5px;width:98%;margin:0 auto"><strong>本题平均分：<span id="'+SurveySuvItem.item_id+'_average_span">0</span></strong></div>';
							str += '<table border="0" class="table_border" cellpadding="2" cellspacing="0" width="98%" id="'+SurveySuvItem.item_id+'">';
							str += '<tr>';
							 str += '<td width="150px"><strong>选项</strong></td>';
							 str += '<td width="120px"><strong>小计</strong></td>';
							 str += '<td colspan="2"><strong>比例</strong></td>';
						    str += '</tr>';	
							if(childList != null && childList.size() > 0)
							{
							    for(var j=0;j<childList.size();j++)
								{
									var ids = SurveySuvItem.item_id+'_'+childList.get(j).item_num;
								    str += '<tr item_id="'+SurveySuvItem.item_id+'">';
								     str += '<td >'+childList.get(j).item_name+'</td>';
									 str += '<td id="'+ids+'" score="'+childList.get(j).score+'">'+getStatisBean(ids).counts+'</td>';									
									 str += '<td id="pic_'+ids+'" style="text-align:left" width="150px"><div class="pro_back"><div class="pro_fore" style="width:0%"><img height="13" width="149" alt="" src="../images/pro_fore.png"></div></div></td>'
							 str += '<td id="pro_'+ids+'" style="text-align:left" >&nbsp;</td>';
									str += '</tr>';
								}
							}
							str += '<tr>';
							 str += '<td ><strong>本题有效填写人次</strong></td>';
							 str += '<td id="'+SurveySuvItem.item_id+'_count"></td>';
							 str += '<td colspan="2">&nbsp;</td>';
							 str += '</tr>';
							str += '</table>';
						 }
					  str += '</div>';	
					 str += '</div>';
				}	
				if(subject_type == "textareas" || subject_type == "uploadfile")
				{
						str += '<div class="sub_div" style="cursor:pointer">　　<span onclick="openTextDetailWindow(\''+itemList.get(0).item_id+'\',1,\''+subject_type+'\')" >主观题：[显示详细]</span></div>';
				}
			}
			return str;
		}
		
		//计算余值
		function calculateRemainder()
		{
			//根据td名为singlnList的对象找到它的父节点table对象

			$("td[id=singlnList]").parent().parent().parent().each(function(){
			
				var trNum = $(this).find("tr").length;
				var counts = 0;
				var pro = 0;
				$(this).find("tr").each(function(i){
				
					if(i>0 && i<trNum-2)
					{						
							counts += parseInt(getStatisBean($(this).find("td:nth-child(2)").attr("id")).counts);
							
							pro += parseInt($(this).find("td:nth-child(4)").text());																		
					}
					if(i == trNum-2)
					{
						if(answer_count-counts > 0)
						{
							$(this).find("td:nth-child(2)").text(answer_count-counts);							
							$(this).find("td:nth-child(4)").text((100-pro)+"%");
							$(this).find(".pro_fore").css("width",(100-pro)+"%");
						}
						else
						{
							$(this).find("td:nth-child(3)").html("&nbsp;");
						}
					}
				})	
			})

			//计算矩阵题的平均分			
			$("#table table[type=matrix]").each(function(){
				var average = 0;//总的平均分
				var all_score =0;//分数总和
				var all_counts = 0;//总的小计合数
				var tmp_ave = 0;
				$(this).find("tr").each(function(i){
					if(i>0)
					{	
						var item_score = 0;//分项分数总和
						var item_average = 0;//分项平均分
						var item_counts = 0;//分项小计合数
						
						$(this).find("td").each(function(j){
							if(j>0)
							{							
								var score = $(this).attr("score");
								var counts = getStatisBean($(this).attr("id")).counts;
								
								item_counts += parseInt(counts);
								item_score += parseInt(score)*parseInt(counts);
							}
						})
						tmp_ave = parseFloat(item_score/item_counts);
						if(tmp_ave > 0)
						{
							item_average = tmp_ave.toFixed(2);
						}						
						$("#"+$(this).attr("item_id")+"_average_span").text(item_average);
						$("#"+$(this).attr("item_id")+"_count").text(item_counts);
					
						all_score += item_score;
						all_counts += item_counts;


					}
				})
				tmp_ave = parseFloat(all_score/all_counts);
				if(tmp_ave > 0)
					average = tmp_ave.toFixed(2);
				
				$(this).parent().find("#average_span").text(average);
			})

			//计算量表题的平均分			
			$("#table table[type=scale]").each(function(){			
				var trNum = $(this).find("tr").length;
				var item_counts = 0;
				var item_score = 0;
				$(this).find("tr").each(function(i){
					if(i>0 && i< trNum-2)
					{	
						var score = $(this).find("td:nth-child(1)").attr("score");
						var counts = getStatisBean($(this).find("td:nth-child(2)").attr("id")).counts;
						item_score += parseInt(score)*parseInt(counts);
						item_counts += parseInt(counts);
					}
				})
				average = parseFloat(item_score/item_counts).toFixed(2);
				
				$(this).before('<div style="text-align:left;padding-top:5px;width:98%;margin:0 auto"><strong>本题平均分：'+average+'</strong></div>');
			})

			//处理矩阵题中，明细内容的数据
			$("div[id=detail_div]").each(function(){
				$(this).find("table").each(function(){
					var trNum = $(this).find("tr").length;
					
					$(this).find("tr").each(function(i){
						if(i > 0 && i< trNum-1)
						{
							//计算每项所占百分比
							var counts = getStatisBean($(this).find("td:nth-child(2)").attr("id")).counts;							
							var all_count = $("#"+$(this).attr("item_id")+"_count").text();
							var pro = parseFloat((counts/all_count)*100).toFixed(2);
					
							$(this).find("td:nth-child(4)").text(pro+"%");
							$(this).find(".pro_fore").css("width",pro+"%");
							
						}
					})
				})
			})
		}
		//查看详细数据
		function setShowDetailClick()
		{
			$("span[id=showDetail_span]").hover(
			  function () {
				$(this).addClass("detail_hover");
			  },
			  function () {
				$(this).removeClass("detail_hover");
			  }
			);
			
			$("span[id=showDetail_span]").toggle(
			  function () {
				$(this).text("查看总体数据");
				$(this).parent().parent().parent().find(" > table").hide();
				$(this).parent().parent().parent().find("#detail_div").show();
			  },
			  function () {
				$(this).text("查看详细数据");
				$(this).parent().parent().parent().find(" > table").show();
				$(this).parent().parent().parent().find("#detail_div").hide();
			  }
			);
		}

		function openTextDetailWindow(item_ids,item_nums,s_type)
		{
			//parent.fnModelWin('查看详细信息','survey/view_itemDetail.jsp?item_id='+item_ids+"&item_num="+item_nums+"&s_id="+s_id+"&s_type="+s_type,800,490);
			top.OpenModalWindow("查看详细信息","/sys/survey/view_itemDetail.jsp?item_id="+item_ids+"&item_num="+item_nums+"&s_id="+s_id+"&s_type="+s_type,800,490);	
		}

		function goOtherPage()
		{
			window.location.href = "answerList.jsp?sid="+s_id;
		}

		function openSearch()
		{
			//parent.fnModelWin('数据统计查询','survey/search_statistics.jsp?s_id='+s_id,800,490);
			top.OpenModalWindow("数据统计查询","/sys/survey/search_statistics.jsp?s_id="+s_id,800,490);
		}
	//-->
	</SCRIPT>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>	
			<td class="fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="goOtherPage()" value="所有答卷" />
				<input id="btn1" name="btn1" type="button" onclick="openSearch()" value="查询" />	
				<span class="blank3"></span>
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
				
			</td>		
		</tr>
	</table>
	<span class="blank3"></span>
	<div id="table"></div><!-- 列表DIV -->
	<div id="turn"></div><!-- 翻页DIV -->
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn1" name="btn1" type="button" onclick="goOtherPage()" value="所有答卷" />
			<input id="btn1" name="btn1" type="button" onclick="openSearch()" value="查询" />				
		</td>
	</tr>
   </table>	
</div>
</body>
</html>