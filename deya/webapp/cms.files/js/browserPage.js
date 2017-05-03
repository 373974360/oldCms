/******************* 量表题设置　开始 *****************************/
//设置量表题事件
function scaleReady()
{
	$("UL[stype=img] LI").hover(
	  function () {
		changeScaleLIClass(this);
	  },
	  function () {
		var ULObj = $(this).parent();
		
		if(ULObj.find("LI:checked").length >0)
		{
			changeScaleLIClass(ULObj.find("LI:checked"));
		}else{
			ULObj.find("LI").removeClass("scale_li_selected");
			ULObj.find("LI").addClass("scale_li");
		}
	  }
	);

	$("UL[stype=img] LI").click(function(){
		changeScaleLIClass(this);
		setCheckedToLI(this);
	});
}
//设置鼠标移入后的样式
function changeScaleLIClass(obj)
{
	var ULObj = $(obj).parent();
	ULObj.find("LI").removeClass("scale_li_selected");
	ULObj.find("LI").addClass("scale_li");

	for(var i=0;i<ULObj.find("LI").length;i++)
	{				
		ULObj.find("li:nth-child("+(i+1)+")").toggleClass("scale_li_selected");
		
		if(ULObj.find("li:nth-child("+(i+1)+")").attr("value") == $(obj).attr("value"))
		{
			break;
		}
	}
}
//设置选中的值
function setCheckedToLI(obj)
{
	 $(obj).parent().find("LI").removeAttr("checked");
	$(obj).attr("checked","true");
}
/******************* 量表题设置　结束 *****************************/
//得到投票的统计数据
function getVoteStatisData()
{
	$("div[sort_num]").each(function(){
		if($(this).attr("type") == "voteRadio" || $(this).attr("type") == "voteCheckbox")
		{
			var item_id = $(this).find("input:first").attr("id");
			m = AnswerRPC.getVoteTotalBySurveyItem(s_id,item_id);
			m = Map.toJSMap(m);
			$(this).find("tr").each(function(){
				var i_num = $(this).find("input").val();
				
				StatisticsBean = getStatisBean(item_id+"_"+i_num);
				
				$(this).find(".pro_fore").css("width",StatisticsBean.proportion);
				$(this).find("#vote_count").text(StatisticsBean.counts+"票");
				$(this).find("#vote_pro").text("("+StatisticsBean.proportion+")");
			})
		}
	});
}
//得到统计对象
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

function setHTML()
{			
	$("#survey_div div").each(function(){
		$(this).removeAttr("onclick");
	})

	$("#survey_div input").each(function(){
		$(this).removeAttr("onclick");
	});

	$("input[check=true]").each(function(){
		$(this).attr("checked","true");
	})
}

/*** 问卷提交处理　开始 **/
/* 当然存储上传文件地址隐藏域对象 */
var currnet_upload_hidden;
/* 是否允许提交 */
var is_submit = true;
var spacing_times = "";
function getSTimes(t)
{
	return t.replace(/d([\d]*)/,"$1天").replace(/h([\d]*)/,"$1小时").replace(/m([\d]*)/,"$1分钟");	
}

function fnOK()
{			
	if(SurveyBean.ip_fre != "" && SurveyBean.ip_fre != 0)
	{
        var count = AnswerRPC.getAnswerCountByIP(s_id);
		if(parseInt(count) > parseInt(SurveyBean.ip_fre)-1)
		{
			alert("该IP提交次数已超过最大限制，无法再提交");
			return;
		}
	}
	if(SurveyBean.spacing_interval != "" && SurveyBean.spacing_interval != null)
	{
		spacing_times = getSTimes(SurveyBean.spacing_interval);
		if(!AnswerRPC.isSubmitTimeout(s_id,SurveyBean.spacing_interval))
		{
			alert("该IP已提交过数据，请于"+spacing_times+"后再进行提交");
			return;
		}
	}

	is_submit = true;
	SurveyAnswer.s_id = s_id;
	var itemList = new List();
	//alert($("#survey_div").html())
	//$("#design_div > div").each(function(i){  sort_num

	$("div[sort_num]").each(function(i){		
			var type = $(this).attr("type");
			
			var is_required = $(this).attr("is_required");
			if(type == "radioList" || type == "voteRadio")
			{
				var item_id = $(this).find(":radio:checked").attr("id");
				var value = $(this).find(":radio:checked").val();
				var itemB = BeanUtil.getCopy(SurveyAnswerItem);
				
				if(value != null)
				{
					itemB.item_id = item_id;
					itemB.item_value = value;

					var textObj =  $(this).find(":radio:checked").parent().find(":text");
					if(textObj.is("input"))
					{
						var text = textObj.val();
						if(text.trim() != "")
							itemB.item_text = text;	
					}

					itemList.add(itemB);
				}else
				{
					if(is_required == "true")
					{
						alert("第"+(i+1)+"为必答题");
						is_submit = false;
						return false;
					}
				}
			}
			if(type == "checkboxList" || type == "voteCheckbox")
			{	
				var c_count = $(this).find(":checked").length;
				if($(this).attr("c_least") != "" && c_count < parseInt($(this).attr("c_least")))
				{
					alert("第"+(i+1)+"题最少要选择"+$(this).attr("c_least")+"个选项");
					is_submit = false;
					return false;
				}
				if($(this).attr("c_maximum") != "" && c_count > parseInt($(this).attr("c_maximum")))
				{
					alert("第"+(i+1)+"题最多只能选择"+$(this).attr("c_maximum")+"个选项");
					is_submit = false;
					return false;
				}

				if(c_count > 0)
				{				

					$(this).find(":checked").each(function(i){
						var itemB = BeanUtil.getCopy(SurveyAnswerItem);							
					
						itemB.item_id = $(this).attr("id");
						itemB.item_value = $(this).val();

						var textObj =  $(this).parent().find(":text");
						if(textObj.is("input"))
						{
							var text = textObj.val();
							if(text.trim() != "")
								itemB.item_text = text;	
						}

						itemList.add(itemB);
					});
				}else
				{
					if(is_required == "true")
					{
						alert("第"+(i+1)+"为必答题");
						is_submit = false;
						return false;
					}
				}
		
				var item_id = $(this).find(":radio:checked").attr("id");
				var value = $(this).find(":radio:checked").val();
				var itemB = BeanUtil.getCopy(SurveyAnswerItem);						
			}
			if(type == "selectOnly")
			{
				var item_id = $(this).find("select").attr("id");
				var value = $(this).find("select option[selected=true]").val();
				var itemB = BeanUtil.getCopy(SurveyAnswerItem);
				if(value != null && value != "")
				{
					itemB.item_id = item_id;
					itemB.item_value = value;
					itemList.add(itemB);
				}else
				{
					if(is_required == "true")
					{
						alert("第"+(i+1)+"为必答题");
						is_submit = false;
						return false;
					}
				}
			}
			if(type == "matrix")
			{
				$(this).find("tr").each(function(j){
					if(j>0)
					{
						var item_id = $(this).find(":radio:checked").attr("id");
						var value = $(this).find(":radio:checked").val();
						var itemB = BeanUtil.getCopy(SurveyAnswerItem);
						if(value != null)
						{
							itemB.item_id = item_id;									
							itemB.item_value = value;									
							itemList.add(itemB);
							
						}else
						{
							if(is_required == "true")
							{
								alert("第"+(i+1)+"为必答题");
								is_submit = false;
								return false;
							}
						}								
					}
				})	
				//跳出当前循环
				return is_submit;
			}
			if(type == "scale")
			{
			
				var item_id = "";
				var value = "";
				var itemB = BeanUtil.getCopy(SurveyAnswerItem);

				if($(this).find("UL").attr("stype") == "img")
				{
					item_id = $(this).find("LI:checked").attr("id");
					value = $(this).find("LI:checked").attr("value");
				}
				else
				{
					item_id = $(this).find(":radio:checked").attr("id");
					value = $(this).find(":radio:checked").val();
				}
		
				if(value != null)
				{
					itemB.item_id = item_id;
					itemB.item_value = value;
					
					itemList.add(itemB);
				}else
				{
					if(is_required == "true")
					{
						alert("第"+(i+1)+"为必答题");
						is_submit = false;
						return false;
					}
				}
			}					
			if(type == "textareas")
			{
				var item_id = $(this).find("textarea").attr("id");
				var value = $(this).find("textarea").val();
				var itemB = BeanUtil.getCopy(SurveyAnswerItem);
				if(value.trim() != null && value.trim() != "")
				{
					var val=new Validator();
					if(!val.checkStrLength(value,"",3000))
					{
						alert("第"+(i+1)+"题：只能输入1000个汉字或3000个英文字符，请重新输入");
						is_submit = false;
						return false;
					}						
					itemB.item_id = item_id;
					itemB.item_value = 1;
					itemB.item_text = value;
					itemList.add(itemB);
				}else
				{
					if(is_required == "true")
					{
						alert("第"+(i+1)+"为必答题");
						is_submit = false;
						return false;
					}
				}
			}
			if(type == "uploadfile")
			{
				if($(this).find(":file").val().trim() != "")
				{
					//判断隐含域中是否有值，如果有表示上传成功了，没有就提交该题的表单
					if($(this).find(":hidden").val().trim() != "")
					{								
						var item_id = $(this).find(":file").attr("id");
						var value = $(this).find(":hidden").val();
						var itemB = BeanUtil.getCopy(SurveyAnswerItem);
						itemB.item_id = item_id;
						itemB.item_text = value;
						itemB.item_value = 1;
						itemList.add(itemB);
					}
					else
					{
						currnet_upload_hidden = $(this).find(":hidden");
						$(this).find("form").submit();
						
						is_submit = false;
						
						return false;								
					}
				}else
				{
					if(is_required == "true")
					{
						alert("第"+(i+1)+"为必答题");
						is_submit = false;
						return false;
					}
				}
			}		
	});

	if(is_submit)
	{		
		SurveyAnswer.item_list = itemList;
		var message = "";
		if(SurveyBean.spacing_interval != "" && SurveyBean.spacing_interval != null)
		{
			spacing_times = getSTimes(SurveyBean.spacing_interval);
			message = "，"+spacing_times+"后才能进行再次提交";
		}
/*
		if(memberA != null){
			SurveyAnswer.user_name = memberA.user_name;
		}
	*/
		if(AnswerRPC.insertSurveyAnswer(SurveyAnswer,""))
		{
			alert("提交成功，感谢您的参与"+message);
			return;
		}
	}
}

function returnUploadValue(file_path)
{
	if(file_path == "error file type")
	{
		alert("该类型文件不允许上传，如需上传，请先使用压缩工具打包再上传");
		return;
	}
	if(file_path == "upload fail")
	{
		alert("上传失败，请查看文件是否存在，重新提交");
		return;
	}
	/*将值写入对应的隐含域中*/
	currnet_upload_hidden.val(file_path);
	
	/*重新提交*/
	fnOK();
}

/*** 问卷提交处理　结束 **/

/*****************　选项图片中的鼠标事件　开始　**********************/
function showImgDescribe(obj,e)
{
	var htmls = $(obj).parent().find("#item_img_describe").html();
	if(htmls.trim() != "")
	{
		$("#show_img_des_div").html(htmls);
		if(!document.all){
			var position_mouse = GetAbsoluteLocationEx(obj);
			$("#show_img_des_div").css("left",position_mouse.left+position_mouse.width);
			$("#show_img_des_div").css("top",position_mouse.top);
		}
		else{
			$("#show_img_des_div").css("left",event.clientX);
			$("#show_img_des_div").css("top",event.clientY+getScrollTop());
		}		
		$("#show_img_des_div").show();
	}
}

function GetAbsoluteLocationEx(element) {   

	 if (arguments.length != 1 || element == null) {   
		   return null;   
	 }   
	 var elmt = element;   
	 var offsetTop = elmt.offsetTop;  
	 var offsetLeft = elmt.offsetLeft;
	 var offsetWidth = elmt.offsetWidth;
	 var offsetHeight = elmt.offsetHeight; 
    while (elmt = elmt.offsetParent) { 
	 if (elmt.style.position == 'absolute' || elmt.style.position == 'relative' 
		|| (elmt.style.overflow != 'visible' && elmt.style.overflow != '')) { 
		break;   
	 }   
		offsetTop += elmt.offsetTop;
		offsetLeft += elmt.offsetLeft; 
	}   
    return { top: offsetTop, left: offsetLeft, width: offsetWidth, height: offsetHeight };   
}  

function getScrollTop()
{
    var scrollTop=0;
    if(document.documentElement&&document.documentElement.scrollTop)
    {
        scrollTop=document.documentElement.scrollTop;
    }
    else if(document.body)
    {
        scrollTop=document.body.scrollTop;
    }
    return scrollTop;
} 

function closeImgDescribe()
{
	$("#show_img_des_div").css("display","none");
}

function show_img_desc(obj)
{
	$(obj).show();
}
/*****************　选项图片中的鼠标事件　结束　**********************/


/*****************　显示调查结果　开始　**********************/
function setSubjectList()
{
	//取出所有统计数据，为考虑性能，没有分批按题目取数据，而是一次取出所有选项的统计值，再根据item_id插入到相应的表格中，还需要单独计算其分数或余数
	m = SurveyRPC.getStatisticsDataBySurvey(s_id);
	
	m = Map.toJSMap(m);
	answer_count = parseInt(m.get("answer_count"));
	
	var dataList = m.get("dataList");//统计数据

	var str = '<div class="s_name_show">'+SurveyBean.s_name+'</div>';
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
	$("#survey_div").html("<div class='anwserContent'>"+str+"</div>");

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
			str += '<table border="0" cellpadding="0" cellspacing="0"  id="'+SurveySuvItem.item_id+'" type="'+subject_type+'">';
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
					 str += '<td width="220px" style="text-align:left" score="'+childList.get(j).score+'">'+childList.get(j).item_name+'</td>';
					 str += '<td id="'+ids+'"  style="display:none">'+getStatisBean(ids).counts+'</td>';
					 str += '<td id="pic_'+ids+'" style="text-align:left" width="155px"><div class="pro_back"><div class="pro_fore" style="width:'+getStatisBean(ids).proportion+'"><img height="13" width="149px" alt="" src="/sys/images/pro_fore.png"></div></div></td>'
					 str += '<td id="pro_'+ids+'" style="text-align:left" >'+getStatisBean(ids).counts+'票('+getStatisBean(ids).proportion+')</td>';
					str += '</tr>';
				}
			 }			 
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
					str += '<table border="0" cellpadding="0" cellspacing="0" id="'+SurveySuvItem.item_id+'">';
					if(childList != null && childList.size() > 0)
					{
						for(var j=0;j<childList.size();j++)
						{
							var ids = SurveySuvItem.item_id+'_'+childList.get(j).item_num;
							str += '<tr item_id="'+SurveySuvItem.item_id+'">';
							 str += '<td width="220px" style="text-align:left">'+childList.get(j).item_name+'</td>';
							 str += '<td id="'+ids+'" score="'+childList.get(j).score+'" style="display:none">'+getStatisBean(ids).counts+'</td>';									
							 str += '<td id="pic_'+ids+'" style="text-align:left" width="155px"><div class="pro_back"><div class="pro_fore" style="width:0%"><img height="13" width="149" alt="" src="/sys/images/pro_fore.png"></div></div></td>'
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
/*****************　显示调查结果　结束　**********************/



