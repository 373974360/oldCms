
sDesigner = function(){
	this.name = ObjectPool.put(this);
	this.designerObj;//设计区域对象
	this.wareObj;//组件区域对象
	this.sort_num = 1;//题目序号
	this.item_num = 1;//选项序号
	this.current_designer_obj;//当前编辑框的主题区域
	this.required = false;//选项题是否必须，全局
	this.current_item_num;//当前选项序号
	this.default_img_width = 100;
	this.default_img_height = 120;
	this.htmleditor_filed_name = "";//打开当前编辑器窗口的文本域名称

	//更改问题主题名称和标题
	this.setValue = function(obj)
	{
		$("#"+$(obj).attr("id")+"_show").html($(obj).val());//.replace(/\n/g,"<br>")
	};

	//更改选项标题值
	this.setSubjectTitle = function(val)
	{
		this.current_designer_obj.find("#title_span").html(val.replace(/\n/g,"<br>")+'&nbsp;<span id="req_span" class="wargin_span">'+this.current_designer_obj.find("#req_span").text()+'</span>');
	}

	//更改选项显示值
	this.setItem_value = function(vals,num)
	{
		this.current_designer_obj.find("ul li:nth-child("+(num+1)+") span").html(vals);		
	}

	//设置当前选中选项的主题对象,设计区域点击事件
	this.setCurrentDesigner = function(obj)
	{
		//删除其它DIV选中样式
		this.selectCurrentDesigner();
	
		this.current_designer_obj = $(obj);
		this.current_designer_obj.addClass("disigner_div_checked");		

		//显示功能按钮区
		this.current_designer_obj.append(this.getToolsButtonStr());
		//显示题目属性
		this.show_subject_arrt_html();
	}

	//当前设计区域选中处理，删除其它区域的样式和按钮区域
	this.selectCurrentDesigner = function()
	{
		this.designerObj.find(" > div").removeClass("disigner_div_checked");
		$(this.designerObj).find(" > div #tools_button_div").remove();
	}

	//更改列表显示的行列样式
	this.setItemShowCSS = function(num)
	{	
		this.current_designer_obj.find("li").each(function(){
			$(this).removeClass();
			$(this).addClass("li_css"+num);
		});		
	}

	//得到当前主题的序号，根据设计区域里第一级div的个数
	this.getSubjectSortNum = function()
	{
		return this.designerObj.find(" > div").length;		
	}

	//向设计区域中添加题目
	this.showItemHtml = function(itemName)
	{
		$("#show_cell_num").val("1");//先清空横向排列选项列表的值，不然添加的数据会按上一次的选择排列

		var sort_num_str = "";	
		if($("#is_show_subsort").is(':checked') == false)
		{
			sort_num_str = 'style="display:none"';
		}		
		
		var htmls = '<div id="'+itemName+'_'+this.sort_num+'_divs" sort_num="'+this.sort_num+'" is_required="'+this.required+'" item_id="'+this.item_num+'" type="'+itemName+'" c_least="" c_maximum="" onclick="ObjectPool['+this.name+'].setCurrentDesigner(this)" class="disigner_div_default disigner_div_checked" >';
		htmls += '<div id="title_divs" ><div id="sort_num" class="sort_num_div" '+sort_num_str+'>'+this.getSubjectSortNum()+'.</div><div id="title_span">请在此输入问题标题&nbsp;<span id="req_span" class="wargin_span">';
		if(this.required)
			htmls += "*";
		htmls += '</span></div><DIV style="CLEAR:both"></DIV></div>';
		htmls += '<div id="des_items_divs" >';
		
		if(itemName == "radioList")
		{
			htmls += '<ul id="item_ul">';
			htmls += this.radioList_disigner_item_str(this.sort_num,"",1,"initial");		
			htmls += this.radioList_disigner_item_str(this.sort_num,"",2,"initial");
			htmls += '</ul>';
			this.item_num += 1;
		}
		if(itemName == "checkboxList")
		{
			htmls += '<ul id="item_ul">';
			htmls += this.checkboxList_disigner_item_str(this.sort_num,"",1,"initial");		
			htmls += this.checkboxList_disigner_item_str(this.sort_num,"",2,"initial");
			htmls += '</ul>';
			this.item_num += 1;
		}
		if(itemName == "matrix")
		{
			htmls += this.matrix_disigner_item_str(this.sort_num,"");	
		}
		if(itemName == "selectOnly" || itemName == "scale" || itemName == "textareas" || itemName == "uploadfile")
		{
			htmls += eval('this.'+itemName+'_disigner_item_str(this.sort_num,"")');	
			this.item_num += 1;
		}				
		if(itemName == "voteRadio")
		{
			htmls += this.voteRadio_disigner_item_str("radio");	
			this.item_num += 1;
		}
		if(itemName == "voteCheckbox")
		{
			htmls += this.voteRadio_disigner_item_str("checkbox");	
			this.item_num += 1;
		}
		
		htmls += '</div>';
		htmls += '<div class="blankH5"></div>';
		htmls += this.getToolsButtonStr();//工具按钮
		htmls += "</div>";
		//删除其它DIV选中样式
		this.selectCurrentDesigner();
		
		$(this.designerObj).append(htmls);		

		//给div加上鼠标事件		
		$("#"+itemName+"_"+this.sort_num+"_divs").hover(
		  function () {
			$(this).addClass("disigner_div_move");
		  },
		  function () {
			$(this).removeClass("disigner_div_move");
		  }
		);
		//给全局对象付上当前题目对象
		this.current_designer_obj = $("#"+itemName+"_"+this.sort_num+"_divs");
		this.sort_num += 1;
		
		this.show_subject_arrt_html();
		init_input();
	}
	

	//设计区域中选项字符串
	this.radioList_disigner_item_str = function(sortnum,title_value,nums,initial_flag)
	{
		var cell_num = 1;
		if($("#show_cell_num").val())
			cell_num = $("#show_cell_num").val();

		var titles = "";
		if(title_value != "")
			titles = title_value;
		else
			titles = '选项'+nums;

		var ids = "";
		
		//如果是初始设置，选项ID使用this.item_num
		if(initial_flag == "initial")
		{
			ids = 'item_'+this.item_num;
		}
		else
		{//否则，从外层div属性中获取  
			ids = 'item_'+this.current_designer_obj.attr("item_id");			
		}		
	
		var str = '<li class="li_css'+cell_num+'"><div><input type="radio" class="checkbox" onclick="ObjectPool['+this.name+'].setThisInputCheck(this)" id="'+ids+'" name="'+ids+'" value="'+nums+'"><span>'+titles+'</span></div></li>';
		
		return str;
	}
	

	//显示题目属性标签
	this.show_subject_arrt_html = function()
	{
		$("#ware_lab_div div:nth-child(2)").click();
	}

	//向题目属性中付值
	this.show_attr_html = function()
	{
		try{	

			if(this.current_designer_obj.attr("type") == "voteCheckbox")
				var htmls = this.voteRadio_attr_html();
			else
				var htmls = eval("this."+this.current_designer_obj.attr("type")+"_attr_html()");
			
			$("#subject_main_attr_div").hide();
			$("#subject_attr_div").html(htmls);
			$("#subject_attr_div").show();
			init_input();
		}catch(e)
		{
			//清空属性区域
			$("#subject_attr_div").html("");
			//显示默认的主题的属性
			$("#subject_main_attr_div").show();

		}
	}

	
	//向选项中添加输入框
	this.addInuptWidget = function(obj,num)
	{
		if($(obj).is(':checked'))
		{
			var current_sort_num = this.current_designer_obj.attr("sort_num");			
			this.current_designer_obj.find("ul li:nth-child("+(num+1)+") div").append('<input type="text" class="input_border" maxlength="1000px" id="'+current_sort_num+'_input" name="'+current_sort_num+'_input">');
		}else
			this.current_designer_obj.find("ul li:nth-child("+(num+1)+") :text").remove();
	}

	//删除单选列表选项
	this.deleteItem_radioList = function(num)
	{
		if(this.current_designer_obj.find("ul li:nth-child("+(num+1)+") :input").attr("is_update") == "false")
		{
			this.checkUpdatestatus_delete();
			return;			
		}
		this.current_designer_obj.find("ul li:nth-child("+(num+1)+")").remove();
		//再在题目属性中添加
		this.current_designer_obj.click();	
	}

	//删除单选列表选项
	this.options_table_reload = function()
	{
		$("#list_option_table").find("tr").each(function(i){
			if(i>0)
				$(this).remove();
		})
		$("#list_option_table").find("tr:nth-child(1)").after(this.radioList_attr_item_str());
	}

	//选项排序
	this.sortItem_radioList = function(sortype,num)
	{
		if(this.current_designer_obj.find("ul li:nth-child("+(num+1)+") :input").attr("is_update") == "false")
		{			
				this.checkUpdatestatus_sort();
				return;			
		}

		if(sortype == "up" && num > 0)
		{
			this.current_designer_obj.find("ul li:nth-child("+(num+1)+")").after(this.current_designer_obj.find("ul li:nth-child("+(num)+")"));
		}
		if(sortype == "down" && this.current_designer_obj.find("ul li").length-1 > num)
		{
			this.current_designer_obj.find("ul li:nth-child("+(num+2)+")").after(this.current_designer_obj.find("ul li:nth-child("+(num+1)+")"));
		}
		this.current_designer_obj.click();
	}	

	//默认选中项
	this.checkedItem_radioList = function(obj,num)
	{
		if($(obj).is(':checked'))
		{
			this.current_designer_obj.find("ul li:nth-child("+(num+1)+") input").attr("checked","true");
			this.current_designer_obj.find("ul li:nth-child("+(num+1)+") input").attr("CHECKED","true");
						
			this.wareObj.find("input[id=default_checked]").each(function(i){				
				if(i != num)
					$(this).attr("checked","");
			})
		}
		else
		{
			this.current_designer_obj.find("ul li:nth-child("+(num+1)+") input").removeAttr("checked");
			this.current_designer_obj.find("ul li:nth-child("+(num+1)+") input").removeAttr("CHECKED");
		}
	}

	
	
	//得到单选列表属性字符串
	this.radioList_attr_html = function()
	{					
		 var str = '<div id="radio_attr_div">';
			 str += this.public_attr_html_title();
			 str += this.public_attr_html_bathAdd();

			 str += '<table border="0" cellpadding="4" cellspacing="0" width="100%" id="list_option_table">';
			  str += '<tr>';
			   str += '<td class="itemattr_title_td" align="" height="21px">　选项文字</td>';			   
			   str += '<td class="itemattr_title_td" width="30px" align="center">默认</td>';
			  str += '</tr>';			 
			
			  str += this.radioList_attr_item_str();
			  
			 str += '</table>';
			 str += this.public_attr_html_rank();
			 str += this.public_attr_html();
			str += '</div>';
		return str;
	}

	//属性中选项字符串
	this.radioList_attr_item_str = function()
	{
		var names = this.name;
		var str = "";
		this.current_designer_obj.find("li").each(function(i){
			    str += '<tr>';
			     str += '<td class="itemattr_td" valign="middle">';
				 str += '<input type="text" class="input_border" id="" name="" style="width:70px;height:18px" value="'+$(this).find("span").html()+'" onkeyUP="ObjectPool['+names+'].setItem_value(this.value,'+i+')">&nbsp;';
				  str += '<IMG SRC="../images/jia.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].addItem_radioList('+i+')">';
				  str += '<IMG SRC="../images/jian.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].deleteItem_radioList('+i+')">';
				  str += '<IMG SRC="../images/up.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].sortItem_radioList(\'up\','+i+')">';
				  str += '<IMG SRC="../images/down.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].sortItem_radioList(\'down\','+i+')">';
			     str += '</td>';			     
			     str += '<td class="itemattr_td" valign="middle" style="padding-top:10px"><input type="checkbox" class="checkbox" id="default_checked" name="default_checked" value="" ';

				 if($(this).find(":radio").is(':checked'))
				   str += 'checked="true"';

				 str += 'onclick="ObjectPool['+names+'].checkedItem_radioList(this,'+i+')""></td>';				 
		        str += '</tr>';	
				str += '<tr><td colspan="2" class="itemattr_td_spa"><strong><input type="checkbox" class="checkbox" onclick="ObjectPool['+names+'].addInuptWidget(this,'+i+')" ';
				
				if($(this).find(":text").length > 0)
				   str += 'checked="true"';

				str += '><label>文本框</label><strong>&nbsp;&nbsp;<span class="imitation_a" onclick="ObjectPool['+names+'].selectImage('+i+')"><label>图片</label></span></td></tr>';
		 });
		 return str;
	}

	//添加单选列表选项
	this.addItem_radioList = function(num)
	{
		if(this.current_designer_obj.find("ul li:nth-child("+(num+1)+") :radio").attr("is_update") == "false")
		{
			if(num+1 < this.current_designer_obj.find("ul li input[is_update=false]").length)
			{
				this.checkUpdatestatus_add();
				return;
			}
		}

		//首先在设计区域添加	
		this.current_designer_obj.find("ul li:nth-child("+(num+1)+")").after(this.radioList_disigner_item_str(this.current_designer_obj.attr("sort_num"),"",this.current_designer_obj.find("ul li").length+1));
		
		//再在题目属性中添加
		this.current_designer_obj.click();
	}
	//修改时的插入
	this.checkUpdatestatus_add = function()
	{
		alert("该问卷已有答卷，不允许在选项中间插入值");
	}

	this.checkUpdatestatus_delete = function()
	{
		alert("该问卷已有答卷，不允许删除原记录");
	}

	this.checkUpdatestatus_sort = function()
	{
		alert("该问卷已有答卷，不允许对原记录进行排序");
	}
	
	/************ 投票单选题区域　开始 *************/
	//设计区域中选项字符串
	this.voteRadio_disigner_item_str = function(input_type)
	{
		var str =  '<table border="0" style="width:100%" cellpadding="0" cellspacing="0" input_type="'+input_type+'">';
			for(var i=1;i<6;i++)
		    {
				str += this.voteRadio_disigner_tr_str(this.item_num,i,"",input_type);
			}
			str += '</table>';	
		return str;
	}

	this.voteRadio_disigner_tr_str = function(item_num,ni,text_val,input_type)
	{
		if(text_val == "" || text_val == null)
			text_val = "选项"+ni;
	
		var str = "";
		str += '<tr>';
		 str += '<td align="left" width="" height="20px" nowrap><input onclick="ObjectPool['+this.name+'].setThisInputCheck(this)" type="'+input_type+'" id="item_'+item_num+'" name="item_'+item_num+'" value="'+ni+'"><span>'+text_val+'</span></td>';
		 str += '<td align="left">';
		  str += '<div id="pic_pro" class="pro_back"><div class="pro_fore" style="width:20%"><img height="13" width="149px" alt="" src="../images/pro_fore.png"></div></div>';
		  str += '<div style="float:left;padding-top:4px;padding-left:8px;width:120px"><span id="vote_count">5票</span><span id="vote_pro">(20%)</span></div>';	
		 str += '</td>';		 
		str += '</tr>';
		return str;
	}

	//得到单选投票属性字符串
	this.voteRadio_attr_html = function()
	{				
		var widthItem = ["30%","40%","50%","60%","70%","80%"];
		 var str = '<div id="voteRadio_attr_div">';
			 str += this.public_attr_html_title();
			 str += this.public_attr_html_bathAdd();

			 str += '<table border="0" cellpadding="1" cellspacing="0" width="100%" id="list_option_table">';
			  str += '<tr>';
			   str += '<td height="21px" class="itemattr_title_td">　选项文字</td>';			   
			   str += '<td width="30px" align="center" class="itemattr_title_td">默认</td>';
			  str += '</tr>';			 
			
			  str += this.voteRadio_attr_item_str();
			  
			 str += '</table>';

			 str += '<div class="text_div" style="height:20px">选项宽度：<select onchange="ObjectPool['+this.name+'].setTdWidth_voteRadio(this.value)">';
			 str += '<option value="20%">默认</option>';
			
			 var w = this.current_designer_obj.find("tr:first td:first").attr("width");
			 
			 for(var i=0;i<widthItem.length;i++)
			 {
				 if(widthItem[i] == w)
					str += '<option value="'+widthItem[i]+'" selected="true">'+widthItem[i]+'</option>';
				 else
					str += '<option value="'+widthItem[i]+'">'+widthItem[i]+'</option>';
			 }
			 str +='</select></div>';
			 str +='<div class="blankH5"></div>';
			 str += '<div class="text_div" style="height:20px"><input type="checkbox" class="checkbox" checked="true" onclick="ObjectPool['+this.name+'].setShowSpan_voteRadio(this,\'pic_pro\')">&nbsp;显示条形图百分比</div>';
			 str += '<div class="text_div" style="height:20px"><input type="checkbox" class="checkbox" checked="true" onclick="ObjectPool['+this.name+'].setShowSpan_voteRadio(this,\'vote_count\')">&nbsp;显示投票数</div>';
			 str += '<div class="text_div" style="height:20px"><input type="checkbox" class="checkbox" checked="true" onclick="ObjectPool['+this.name+'].setShowSpan_voteRadio(this,\'vote_pro\')">&nbsp;显示百分比</div>';
			 
			 str += this.public_attr_html();
			str += '</div>';
		return str;
	}

	this.setShowSpan_voteRadio = function(obj,span_name)
	{
		if($(obj).is(':checked'))
		{
			this.current_designer_obj.find("#"+span_name).show();
		}
		else
			this.current_designer_obj.find("#"+span_name).hide();
	}

	this.setTdWidth_voteRadio = function(widths)
	{
		this.current_designer_obj.find("tr td:first").attr("width",widths);
	}

	//属性中选项字符串
	this.voteRadio_attr_item_str = function()
	{
		var names = this.name;
		var str = "";
		this.current_designer_obj.find("tr").each(function(i){
			    str += '<tr>';
			     str += '<td valign="top" class="itemattr_td_spa"><input type="text" class="input_border" id="" name="" style="width:100px;height:18px" value="'+$(this).find("td:first span").html()+'" onkeyUP="ObjectPool['+names+'].setItem_voteRadio_value(this.value,'+i+')">&nbsp;';
				  str += '<IMG SRC="../images/jia.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].addItem_voteRadio(\'\','+i+')">';
				  str += '<IMG SRC="../images/jian.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].deleteItem_voteRadio('+i+')">';
				  str += '<IMG SRC="../images/up.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].sortItem_voteRadio(\'up\','+i+')">';
				  str += '<IMG SRC="../images/down.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].sortItem_voteRadio(\'down\','+i+')">';
			     str += '</td>';
				 
			     str += '<td class="itemattr_td_spa" style="padding-top:8px"><input type="checkbox" class="checkbox" id="default_checked" name="default_checked" value="" ';

				 if($(this).find(":radio").is(':checked') || $(this).find(":checkbox").is(':checked'))
				   str += 'checked="true"';

				 str += 'onclick="ObjectPool['+names+'].checkedItem_voteRadio(this,'+i+')""></td>';				 
		        str += '</tr>';	
				/*  简单点，不放文本框了
				str += '<tr><td colspan="2"><strong><input type="checkbox" class="checkbox" onclick="ObjectPool['+names+'].addInuptWidget(this,'+i+')" ';
				
				if($(this).find(":text").length > 0)
				   str += 'checked="true"';

				str += '>文本框<strong></td></tr>';*/
		 });
		 return str;
	}

	this.setItem_voteRadio_value = function(vals,num)
	{
		this.current_designer_obj.find("tr:nth-child("+(num+1)+") span:first(1)").html(vals);
	}

	this.checkedItem_voteRadio = function(obj,num)
	{
		if($(obj).is(':checked'))
		{
			var input_type = this.current_designer_obj.attr("type");
			this.current_designer_obj.find("tr:nth-child("+(num+1)+") input").attr("CHECKED","true");
			//复选框不用取消其它选项的默认选中
			
			if(input_type == "voteRadio")
			{
				this.wareObj.find("input[id=default_checked]").each(function(i){				
					if(i != num)
						$(this).attr("checked","");
				});	
			}
		}
		else
			this.current_designer_obj.find("tr:nth-child("+(num+1)+") input").removeAttr("checked");
	}

	this.addItem_voteRadio = function(text_val,num)
	{	
		
		var input_type = this.current_designer_obj.find("table").attr("input_type");
		if(this.current_designer_obj.find("tr").length == 0)
		{
			this.current_designer_obj.find("table").append(this.voteRadio_disigner_tr_str(this.current_designer_obj.attr("item_id"),1,text_val,input_type));
		}
		else
		{
			if(this.current_designer_obj.find("tr:nth-child("+(num+1)+") input").attr("is_update") == "false")
			{
				if(num+1 < this.current_designer_obj.find("td input[is_update=false]").length)
				{
					this.checkUpdatestatus_add();
					return;
				}
			}					
			
			this.current_designer_obj.find("tr:nth-child("+(num+1)+")").after(this.voteRadio_disigner_tr_str(this.current_designer_obj.attr("item_id"),this.current_designer_obj.find("tr").length+1,text_val,input_type));
		}
		
		this.current_designer_obj.click();
	}
	
	this.deleteItem_voteRadio = function(num)
	{
		if(this.current_designer_obj.find("tr:nth-child("+(num+1)+") input").attr("is_update") == "false")
		{
			this.checkUpdatestatus_delete();
			return;
			
		}
		this.current_designer_obj.find("tr:nth-child("+(num+1)+")").remove();
		this.current_designer_obj.click();
	}

	this.sortItem_voteRadio = function(sortype,num)
	{	
		if(this.current_designer_obj.find("tr:nth-child("+(num+1)+") input").attr("is_update") == "false")
		{			
				this.checkUpdatestatus_sort();
				return;			
		}

		if(sortype == "up" && num > 0)
		{			
				this.current_designer_obj.find("tr:nth-child("+(num)+")").before(this.current_designer_obj.find("tr:nth-child("+(num)+")").next());	
		}

		if(sortype == "down" && num < this.current_designer_obj.find("tr").length)
		{	
				this.current_designer_obj.find("tr:nth-child("+(num+1)+")").next().after(this.current_designer_obj.find("tr:nth-child("+(num+1)+")"));				
		}
		this.current_designer_obj.click();
	}	


	/************ 投票单选题区域　结束 *************/

	/************ 上传文件题区域　开始 *************/
	this.uploadfile_disigner_item_str = function(sortnum,title_value,nums,initial_flag)
	{	
	
		var str = '<form id="form1" name="form1" action="/InterviewLivePicSubmit" target="targetFrame" method="post" enctype="multipart/form-data">';
		str += '<input type="file" name="fileName" id="item_'+this.item_num+'" style="width:80%;" file_type="all"/>';
		str += '</form><input type="hidden" name="file_path" id="file_path"/>';
		str += '<iframe name="targetFrame" width="0" height="0" frameborder="0" ></iframe>';
		return str;
	}

	//得到属性字符串
	this.uploadfile_attr_html = function()
	{			
		 var widthItem = ["50px","100px","200px","300px","400px","500px","600px"];	
		 var fileTypeItem = [];		
		 fileTypeItem[0] = ["all","任意文件"];	
		 fileTypeItem[1] = ["pic","图片"];	
		 fileTypeItem[2] = ["video","音频,视频"];	
		 fileTypeItem[3] = ["flash","flash"];	
		 fileTypeItem[4] = ["custom","自定义"];	

		 var str = '<div id="uploadfile_attr_div">';
			 str += this.public_attr_html_title();
			
			 str += '<div class="itemattr_td_spa">上传文件框宽度：<select onchange="ObjectPool['+this.name+'].setUploadFileWidth(this.value)">';
			  str += '<option value="80%">默认</option>';
			  str += '<option value="80%">默认</option>';
			  var w = this.current_designer_obj.find("input").css("width");
			 for(var i=0;i<widthItem.length;i++)
			 {
				 if(widthItem[i] == w)
					str += '<option value="'+widthItem[i]+'" selected="true">'+widthItem[i]+'</option>';
				 else
					str += '<option value="'+widthItem[i]+'">'+widthItem[i]+'</option>';
			 }		
			 str += '</select></div>';	
			 
			 str += '<div class="itemattr_td_spa">上传文件框宽度：<select onchange="ObjectPool['+this.name+'].setUploadFileType(this.value)">';

			  var selected_type = this.current_designer_obj.find("input").attr("file_type");
			  for(var i=0;i<fileTypeItem.length;i++)
			  {
				  if(fileTypeItem[i][0] == selected_type)
					str += '<option value="'+fileTypeItem[i][0]+'" selected="true">'+fileTypeItem[i][1]+'</option>';
				  else
					str += '<option value="'+fileTypeItem[i][0]+'">'+fileTypeItem[i][1]+'</option>';
			  }
			 str += '</select></div>';
			 
			 str += '<div id="custom_type_div" ';
			 if(selected_type != "custom")
				str += 'style="display:none"';
			 
			 str += '>允许上传的文件类型：<input onfocus="ObjectPool['+this.name+'].reloadInputValue(this,this.value)" title="多种类型请用 | 分隔" type="text" class="input_border" id="custom_type" value="';

			 if(selected_type != "custom" || this.current_designer_obj.find("input").attr("ext_type") == null)
				str += '多种类型请用 | 分隔';
			 else
				str += this.current_designer_obj.find("input").attr("ext_type");

			 str += '" onblur="ObjectPool['+this.name+'].setCustomFileType(this.value)" maxlength="200px"></div>';
			 str += this.public_attr_html();
			str += '</div>';	
		return str;
	}

	this.setUploadFileWidth = function(widths)
	{
		this.current_designer_obj.find("input").css("width",widths);
	}

	this.setUploadFileType = function(ftype)
	{
		this.current_designer_obj.find("input").attr("file_type",ftype);
		if(ftype != 'custom')
		{			
			$("#custom_type_div").hide();
		}
		else
		{
			$("#custom_type_div").show();
		}
	}

	this.setCustomFileType = function(ext_types)
	{
		this.current_designer_obj.find("input").attr("ext_type",ext_types);
	}

	this.reloadInputValue = function(obj,val)
	{
		if(val == "多种类型请用 | 分隔")
		{
			$(obj).val("");
		}
	}

	/************ 上传文件题区域　结束 *************/
	/************ 文本题区域　开始 *************/
	this.textareas_disigner_item_str = function()
	{		
		var str = '<textarea id="item_'+this.item_num+'" name="item_'+this.item_num+'" style="width:80%;height:100px"></textarea>';		
		return str;
	}

	//得到属性字符串
	this.textareas_attr_html = function()
	{			
		 var widthItem = ["50px","100px","200px","300px","400px","500px","600px"];		
		 var str = '<div id="textareas_attr_div">';
			 str += this.public_attr_html_title();		 	 

			 str += '<div class="itemattr_td_spa">文本框高度：<img src="../images/jia.gif"  onclick="ObjectPool['+this.name+'].setTextAreaHeight(\'plus\')" align="center">&nbsp;<img src="../images/jian.gif" onclick="ObjectPool['+this.name+'].setTextAreaHeight(\'decrease\')" align="center"></div>';
			
			 str += '<div class="itemattr_td_spa">文本框宽度：<select onchange="ObjectPool['+this.name+'].setTextAreaWidth(this.value)">';
			 str += '<option value="80%">默认</option>';
			 for(var i=0;i<widthItem.length;i++)
			 {
				 if(widthItem[i] == this.current_designer_obj.find("textarea").css("width"))
					str += '<option value="'+widthItem[i]+'" selected="true">'+widthItem[i]+'</option>';
				 else
					str += '<option value="'+widthItem[i]+'">'+widthItem[i]+'</option>';
			 }			
			 str += '</select></div>';	
			 
			 str += this.public_attr_html();
			str += '</div>';	
		return str;
	}

	//设置文本框的高度
	this.setTextAreaHeight = function(flag)
	{
		var h = this.current_designer_obj.find("textarea").css("height");
		if(flag == "plus")
		{
			this.current_designer_obj.find("textarea").css("height",(parseInt(h)+20)+"px");
		}
		if(flag == "decrease" && parseInt(h) > 20)
		{
			this.current_designer_obj.find("textarea").css("height",(parseInt(h)-20)+"px");
		}
	}

	this.setTextAreaWidth = function(widths)
	{
		this.current_designer_obj.find("textarea").css("width",widths);
	}
	/************ 文本题区域　开始 *************/

	/************ 多选题区域　开始 *************/
	//设计区域中选项字符串
	this.checkboxList_disigner_item_str = function(sortnum,title_value,nums,initial_flag)
	{
		var cell_num = 1;
		if($("#show_cell_num").val())
			cell_num = $("#show_cell_num").val();

		var titles = "";
		if(title_value != "")
			titles = title_value;
		else
			titles = '选项'+nums;

		var ids = "";
		
		//如果是初始设置，选项ID使用this.item_num
		if(initial_flag == "initial")
		{
			ids = 'item_'+this.item_num;
		}
		else
		{//否则，从外层div属性中获取  
			ids = 'item_'+this.current_designer_obj.attr("item_id");			
		}		
	
		var str = '<li class="li_css'+cell_num+'"><div><input type="checkbox" class="checkbox" onclick="ObjectPool['+this.name+'].setThisInputCheck(this)" id="'+ids+'" name="'+ids+'" value="'+nums+'"><span>'+titles+'</span></div></li>';
		
		return str;
	}

	//得到单选列表属性字符串
	this.checkboxList_attr_html = function()
	{					
		 var str = '<div id="radio_attr_div">';
			 str += this.public_attr_html_title();
			 str += this.public_attr_html_bathAdd();

			 str += '<table border="0" cellpadding="1" cellspacing="0" width="100%" id="list_option_table">';
			  str += '<tr>';
			   str += '<td height="21px" class="itemattr_title_td">　选项文字</td>';
			  str += '</tr>';			 
			
			  str += this.checkboxList_attr_item_str();
			  
			 str += '</table>';
			 str += this.public_attr_html_rank();
			 str += this.public_attr_html();
			str += '</div>';
		return str;
	}

	//属性中选项字符串
	this.checkboxList_attr_item_str = function()
	{
		var names = this.name;
		var str = "";
		this.current_designer_obj.find("li").each(function(i){
			    str += '<tr>';
			     str += '<td valign="middle" class="itemattr_td" ><input type="text" class="input_border" id="" name="" style="width:70px;height:18px" value="'+$(this).find("span").html()+'" onkeyUP="ObjectPool['+names+'].setItem_value(this.value,'+i+')"/>&nbsp;';
				  str += '<IMG SRC="../images/jia.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle"  ALT="" onclick="ObjectPool['+names+'].addItem_checkboxList('+i+')"/>';
				  str += '<IMG SRC="../images/jian.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].deleteItem_radioList('+i+')"/>';
				  str += '<IMG SRC="../images/up.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].sortItem_radioList(\'up\','+i+')"/>';
				  str += '<IMG SRC="../images/down.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].sortItem_radioList(\'down\','+i+')"/>';
			     str += '</td>';
		        str += '</tr>';	
				str += '<tr><td colspan="2" class="itemattr_td_spa"><strong><input type="checkbox" class="checkbox" align="absmiddle" onclick="ObjectPool['+names+'].addInuptWidget(this,'+i+')" ';
				
				if($(this).find(":text").length > 0)
				   str += 'checked="true"';

				str += '/><label>文本框</label><strong>&nbsp;&nbsp;<span class="imitation_a" onclick="ObjectPool['+names+'].selectImage('+i+')">图片</span></td></tr>';
		 });
		 return str;
	}

	//添加单选列表选项
	this.addItem_checkboxList = function(num)
	{
		if(this.current_designer_obj.find("ul li:nth-child("+(num+1)+") :checkbox").attr("is_update") == "false")
		{
			if(num+1 < this.current_designer_obj.find("ul li input[is_update=false]").length)
			{
				this.checkUpdatestatus_add();
				return;
			}
		}
		//首先在设计区域添加	
		this.current_designer_obj.find("ul li:nth-child("+(num+1)+")").after(this.checkboxList_disigner_item_str(this.current_designer_obj.attr("sort_num"),"",this.current_designer_obj.find("ul li").length+1));
		
		//再在题目属性中添加
		//this.options_table_reload();
		this.current_designer_obj.click();
	}
	/************ 多选题区域　结束 *************/
	/************ 量表题区域　开始 *************/
	this.scale_disigner_item_str = function(sortnum,title_value)
	{
		var str = '<table border="0" cellpadding="0" cellspacing="0" style="width:100%">';
			str += '<tr>';
			 str += '<th>很不满意</th>';
			 str += '<td>';

			  str += '<UL class="scale" stype="img">';
			   str += '<LI class="scale_li" title="很不满意" value="1" score="1" id="item_'+this.item_num+'"></LI>';
			   str += '<LI class="scale_li" title="不满意" value="2" score="2" id="item_'+this.item_num+'"></LI>';
			   str += '<LI class="scale_li" title="一般" value="3" score="3" id="item_'+this.item_num+'"></LI>';
			   str += '<LI class="scale_li" title="满意" value="4" score="4" id="item_'+this.item_num+'"></LI>';
			   str += '<LI class="scale_li" title="很满意" value="5" score="5" id="item_'+this.item_num+'"></LI>';
			  str += '</UL>';

			str += '</td>';
			str += '<th>很满意</th>';
			str += '</tr>';
			str += '</table>';

		return str;
	}

	this.scale_attr_html = function()
	{
		var str = '<div id="scale_attr_div">';
			 str += '<div class="text_div">请选择选项样式</div>';
			 str += '<div style="margin:3 0 3 8"><img src="../images/scale_selected.gif" width="20px" height="16px" onclick="ObjectPool['+this.name+'].choose_scale_style(\'img\')">　<img src="../images/num.gif" width="20px" height="16px" onclick="ObjectPool['+this.name+'].choose_scale_style(\'num\')"> 　<img src="../images/radio.gif" width="13px" height="13px" onclick="ObjectPool['+this.name+'].choose_scale_style(\'radio\')" align="center" style="padding-bottom:10px"></div>';
			 str += this.public_attr_html_title();
			 
			 str += this.public_attr_html_bathAdd2();

			 str += '<table border="0" cellpadding="1" cellspacing="0" width="100%" id="matrix_option_table">';
			  str += '<tr>';
			   str += '<td height="21px" class="itemattr_title_td">　选项文字</td>';
			   str += '<td align="center"  class="itemattr_title_td">分值</td>';
			  str += '</tr>';			 
			
			  str += this.scale_attr_item_str();
			  
			 str += '</table>';

			 str += this.public_attr_html();
			str += '</div>';			
		return str;
	}

	//设置量表题样式
	this.choose_scale_style = function(stype)
	{
		var ULObj = this.current_designer_obj.find("table UL");
		ULObj.attr("stype",stype);

		if(stype == "radio" || stype == "num")
		{
			ULObj.find("LI").removeClass("scale_li");
			ULObj.find("LI").addClass("scale_li_radio");
		}else
		{
			ULObj.find("LI").empty();
			ULObj.find("LI").removeClass("scale_li_radio");
			ULObj.find("LI").addClass("scale_li");
		}
		
		if(stype == "radio")
		{
			this.current_designer_obj.find("th").remove();
			ULObj.find("LI").each(function(i){				
				$(this).html('<input type="radio" class="checkbox" id="'+$(this).attr("id")+'" name="'+$(this).attr("id")+'" value="'+$(this).attr("value")+'" score="'+$(this).attr("score")+'"/>'+$(this).attr("title"));
			})
		}
		if(stype == "num")
		{			
			ULObj.find("LI").each(function(i){				
				$(this).html('<input type="radio" class="checkbox" id="'+$(this).attr("id")+'" name="'+$(this).attr("id")+'" value="'+$(this).attr("value")+'" score="'+$(this).attr("score")+'"/>'+(i+1));
			})			
		}		

		if(stype == "num" || stype == "img")
		{
			if(this.current_designer_obj.find("table th").length == 0)
			{
				this.current_designer_obj.find("table td").before("<th>"+ULObj.find("LI:first-child").attr("title")+"</th>");
				this.current_designer_obj.find("table td").after("<th>"+ULObj.find("LI:last-child").attr("title")+"</th>");
			}
		}
	}

	this.scale_attr_item_str = function()
	{
		var names = this.name;
		var str = "";
				
		var tdObjs = this.current_designer_obj.find("table UL LI");
		tdObjs.each(function(i){			
			str += '<tr>';
			 str += '<td class="itemattr_td_spa" valign="middle"><input type="text" class="input_border" id="" name="" maxlength="80" style="width:70px;height:18px" value="'+$(this).attr("title")+'" onkeyUP="ObjectPool['+names+'].setScaleItem_value(this.value,'+i+')">&nbsp;';
			  str += '<IMG SRC="../images/jia.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].addItem_Scale(\'\','+i+')">';
			  str += '<IMG SRC="../images/jian.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].deleteItem_Scale('+i+')">';
			  str += '<IMG SRC="../images/up.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].sortItem_Scale(\'up\','+i+')">';
			  str += '<IMG SRC="../images/down.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].sortItem_Scale(\'down\','+i+')">';
			 str += '</td>';
			 str += '<td class="itemattr_td_spa"><input type="text" class="input_border" style="width:30px;height:18px" value="'+$(this).attr("score")+'" maxlength="5" onkeyUP="ObjectPool['+names+'].setScaleItem_score(this,this.value,'+i+')"/></td>';
			str += '</tr>';				
			
		 });
		 return str;
	}

	this.setScaleItem_value = function(vals,num)
	{
		num = num+1;
		this.current_designer_obj.find("table LI:nth-child("+(num)+")").attr("title",vals);
		
		if(num == 1)
		{
			this.current_designer_obj.find("table th:first-child").text(vals);
		}
		if(num == this.current_designer_obj.find("table LI").length)
		{
			this.current_designer_obj.find("table th:last-child").text(vals);
		}
	}

	//设置分值
	this.setScaleItem_score = function(obj,vals,num)
	{
		if(!this.checkIntFormat(obj,vals))
		{
			return;
		}
		$(obj).removeClass("error_input_back");
		this.current_designer_obj.find("table LI:nth-child("+(num+1)+")").attr("score",vals);
	}

	this.addItem_Scale = function(text,num)
	{
		if(this.current_designer_obj.find("table LI:nth-child("+(num+1)+")").attr("is_update") == "false")
		{
			if(num+1 < this.current_designer_obj.find("table LI[is_update=false]").length)
			{
				this.checkUpdatestatus_add();
				return;
			}
		}

		if(text == "")
			text = "选项";
		var values = this.current_designer_obj.find("table LI").length+1;
		
		this.current_designer_obj.find("table LI:nth-child("+(num+1)+")").after('<LI class="scale_li" title="'+text+'" value="'+values+'" score="'+values+'" id="'+this.current_designer_obj.find("table LI:nth-child(1)").attr("id")+'"></LI>');
		//修改前后显示的TH值
		this.updateTHValue(num+1,this.current_designer_obj.find("table LI").length);
		//根据所选类型改变其样式
		this.choose_scale_style(this.current_designer_obj.find("table UL").attr("stype"));

		this.current_designer_obj.click();
	}

	this.addItem_common_Scale = function(commonArr)
	{
		var ULObj = this.current_designer_obj.find("table UL");
		var item_id = ULObj.find("LI:first-child").attr("id");

		ULObj.empty();
		for(var i=0;i<commonArr.length;i++)
		{
			ULObj.append('<LI class="scale_li" title="'+commonArr[i]+'" value="'+(i+1)+'" score="'+(i+1)+'" id="'+item_id+'"></LI>');
		}
		
		//修改前后显示的TH值
		this.updateTHValue(0,ULObj.find("LI").length);
		this.updateTHValue(ULObj.find("LI").length-1,ULObj.find("LI").length);
		//根据所选类型改变其样式
		this.choose_scale_style(ULObj.attr("stype"));

		this.current_designer_obj.click();
	}

	this.deleteItem_Scale = function(num)
	{		
		if(this.current_designer_obj.find("table LI:nth-child("+(num+1)+")").attr("is_update") == "false")
		{
			this.checkUpdatestatus_delete();
			return;
			
		}
		var len = this.current_designer_obj.find("table LI").length;
		this.current_designer_obj.find("table LI:nth-child("+(num+1)+")").remove();
		
		this.updateTHValue(num,len);
		this.current_designer_obj.click();
	}

	this.sortItem_Scale = function(flag,num)
	{
		if(this.current_designer_obj.find("table LI:nth-child("+(num+1)+")").attr("is_update") == "false")
		{			
				this.checkUpdatestatus_sort();
				return;			
		}
		if(flag == "up" && num > 0)
		{
			this.current_designer_obj.find("ul li:nth-child("+(num+1)+")").after(this.current_designer_obj.find("ul li:nth-child("+(num)+")"));
		}
		if(flag == "down" && this.current_designer_obj.find("ul li").length-1 > num)
		{
			this.current_designer_obj.find("ul li:nth-child("+(num+2)+")").after(this.current_designer_obj.find("ul li:nth-child("+(num+1)+")"));
		}

		this.updateTHValue(num,this.current_designer_obj.find("table LI").length);
		this.current_designer_obj.click();
	}

	//删除或移动时要修改最右两边的文字选项
	this.updateTHValue = function(num,len)
	{
		if(num == 0)
		{
			this.current_designer_obj.find("table th:first-child").text(this.current_designer_obj.find("table LI:first-child").attr("title"));
		}
		if(num+1 == len)
		{
			this.current_designer_obj.find("table th:last-child").text(this.current_designer_obj.find("table LI:last-child").attr("title"));
		}
	}
	
	/************ 量表题区域　结束 *************/
	
	/************ 矩阵题区域　开始 *************/
	//添加矩阵题选项
	this.matrix_disigner_item_str = function(sortnum,title_value)
	{
		var str = '<table border="0" cellpadding="0" cellspacing="0" width="100%">';
			str += '<tr>';
			 str += '<td height="25px" width="14%"></td>';
			 str += '<td align="center" width="14%" score="1" nowrap>很不满意</td>';
			 str += '<td align="center" width="14%" score="2" nowrap>不满意</td>';
			 str += '<td align="center" width="14%" score="3" nowrap>一般</td>';
			 str += '<td align="center" width="14%" score="4" nowrap>满意</td>';
			 str += '<td align="center" width="14%" score="5" nowrap>很满意</td>';
			 str += '<td width="14%">&nbsp;</td>';
			str += '</tr>';
			str += '<tr>';
			 str += '<td class="m_td">外观</td>';
			 str += '<td align="center"><input type="radio" class="checkbox" id="item_'+this.item_num+'" name="item_'+this.item_num+'" value="1" ></td>';
			 str += '<td align="center"><input type="radio" class="checkbox" id="item_'+this.item_num+'" name="item_'+this.item_num+'" value="2" ></td>';
			 str += '<td align="center"><input type="radio" class="checkbox" id="item_'+this.item_num+'" name="item_'+this.item_num+'" value="3" ></td>';
			 str += '<td align="center"><input type="radio" class="checkbox" id="item_'+this.item_num+'" name="item_'+this.item_num+'" value="4" ></td>';
			 str += '<td align="center"><input type="radio" class="checkbox" id="item_'+this.item_num+'" name="item_'+this.item_num+'" value="5" ></td>';
			 str += '<td class="m_td"></td>';
			str += '</tr>';
			this.item_num += 1;
			str += '<tr>';
			 str += '<td class="m_td">样式</td>';
			 str += '<td align="center"><input type="radio" class="checkbox" id="item_'+this.item_num+'" name="item_'+this.item_num+'" value="1" ></td>';
			 str += '<td align="center"><input type="radio" class="checkbox" id="item_'+this.item_num+'" name="item_'+this.item_num+'" value="2" ></td>';
			 str += '<td align="center"><input type="radio" class="checkbox" id="item_'+this.item_num+'" name="item_'+this.item_num+'" value="3" ></td>';
			 str += '<td align="center"><input type="radio" class="checkbox" id="item_'+this.item_num+'" name="item_'+this.item_num+'" value="4" ></td>';
			 str += '<td align="center"><input type="radio" class="checkbox" id="item_'+this.item_num+'" name="item_'+this.item_num+'" value="5" ></td>';
			 str += '<td class="m_td"></td>';
			str += '</tr>';
		   str += '</table>';
		   this.item_num += 1;
		return str;	
	}
	//添加行数据，横向添加一条数据
	this.setMatrix_cell_text = function(vals,flag)
	{	
		var arr = vals.split("\n");
		if(vals.trim() == "")
		{
			var tableObj = this.current_designer_obj.find("table");
			tableObj.find("tr").remove();
		}
		else
		{
			if(flag == "left")
			{
				//首先清空table里所有内容
				var tableObj = this.current_designer_obj.find("table");
				tableObj.find("tr").remove();				
				
				var optInputObj = $("#matrix_option_table input[id=item_name]");
				
				var str = "";
				str += '<tr>';	
				 str += '<td height="25px" width="14%"></td>';
				 optInputObj.each(function(i){					 
					str += '<td align="center" score="'+(i+1)+'">'+$(this).val()+'</td>';
				 })
				str += '<td width="14%">&nbsp;</td>';
				str += '</tr>';
				
				var sort_num = this.current_designer_obj.attr("sort_num");
				for(var i=0;i<arr.length;i++)
				{
					if(arr[i].trim() != "")
					{
						str += '<tr>';
						str += '<td class="m_td">'+arr[i]+'</td>';
						for(var j=0;j<optInputObj.length;j++)
						{
							str += '<td align="center"><input type="radio" class="checkbox" id="item_'+this.item_num+'" name="item_'+this.item_num+'" value="'+(j+1)+'"></td>';
						}	
						this.item_num += 1;
						str += '<td class="m_td"></td>';
						str += '</tr>';
					}
				}
				tableObj.append(str);
				this.setMatrix_rightCellText("");

			}
			if(flag == "right")
			{
				this.setMatrix_rightCellText(vals);
			}		
		}
		this.getMatrix_width();
	}

	//得到选项个数，算出平均宽度
	this.getMatrix_width = function()
	{		
		//算出宽度
		var width = parseInt(100/this.current_designer_obj.find("table tr:first-child td").length)+"%";		
		this.current_designer_obj.find("table tr:first td").attr("width",width);		
	}

	this.setMatrix_rightCellText = function(vals)
	{
		if(vals == "")
		{
			vals = $("#rightCellText").val();
		}
		var arr = vals.split("\n");
		for(var i=0;i<arr.length;i++)
		{
			var tdObj = this.current_designer_obj.find("table tr:nth-child("+(i+2)+") td:last");
			tdObj.text(arr[i]);
		}
	}

	this.matrix_attr_html = function()
	{
		var disabled_str = '';
		if(this.current_designer_obj.attr("is_update") == "false")
			disabled_str = 'disabled="disabled"';

		var str = '<div id="matrix_attr_div">';
			 str += this.public_attr_html_title();		 

			 str += '<table border="0" cellpadding="1" cellspacing="0" width="100%">';
			  str += '<tr>';
			   str += '<td height="17px">左行标题</td>';			   
			   str += '<td>右行标题</td>';
			  str += '</tr>';			 
			
			   str += '<tr>';
			    str += '<td><textarea style="width:98%;height:100px" onblur="ObjectPool['+this.name+'].setMatrix_cell_text(this.value,\'left\')" '+ disabled_str +'>';
				//左侧文本框付值
				this.current_designer_obj.find("table tr").each(function(i){
					if(i>0)
					{
						str += $(this).find("td:first").text()+'\n';
					}
				});
				str += '</textarea></td>';			   
			    str += '<td><textarea style="width:98%;height:100px" id="rightCellText" name="rightCellText" onblur="ObjectPool['+this.name+'].setMatrix_cell_text(this.value,\'right\')">';
				//右侧文本框付值
				this.current_designer_obj.find("table tr").each(function(i){
					if(i>0)
					{
						if($(this).find("td:last").text() != "")
							str += $(this).find("td:last").text()+'\n';
					}
				});
				str += '</textarea></td>';
			  str += '</tr>';			  
			 str += '</table>';	
			 
			 str += this.public_attr_html_bathAdd2();

			 str += '<table border="0" cellpadding="1" cellspacing="0" width="100%" id="matrix_option_table">';
			  str += '<tr>';
			   str += '<td height="21px" class="itemattr_title_td">　选项文字</td>';
			   str += '<td align="center" class="itemattr_title_td">分值</td>';
			  str += '</tr>';			 
			
			  str += this.matrix_attr_item_str();
			  
			 str += '</table>';

			 str += this.public_attr_html();
			str += '</div>';			
		return str;
	}

	this.matrix_attr_item_str = function()
	{
		var names = this.name;
		var str = "";
				
		var tdObjs = this.current_designer_obj.find("table tr:first td");
		tdObjs.each(function(i){
			if(i>0 && i < tdObjs.length-1)
			{
			    str += '<tr>';
			     str += '<td class="itemattr_td_spa" valign="middle"><input type="text" class="input_border" id="item_name" name="item_name" maxlength="80" style="width:70px;height:18px" value="'+$(this).text()+'" onkeyUP="ObjectPool['+names+'].setMatrixItem_value(this.value,'+i+')">&nbsp;';
				  str += '<IMG SRC="../images/jia.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].addItem_Matrix(\'\','+i+')">';
				  str += '<IMG SRC="../images/jian.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].deleteItem_Matrix('+i+')">';
				  str += '<IMG SRC="../images/up.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].sortItem_Matrix(\'up\','+i+')">';
				  str += '<IMG SRC="../images/down.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].sortItem_Matrix(\'down\','+i+')">';
			     str += '</td>';
				 str += '<td class="itemattr_td_spa"><input type="text" class="input_border" style="width:30px;height:18px" value="'+$(this).attr("score")+'" maxlength="5" onkeyUP="ObjectPool['+names+'].setMatrixItem_score(this,this.value,'+i+')"/></td>';
		        str += '</tr>';				
			}
		 });
		 return str;
	}
	//设置分值
	this.setMatrixItem_score = function(obj,vals,num)
	{
		if(!this.checkIntFormat(obj,vals))
		{
			return;
		}
		$(obj).removeClass("error_input_back");
		this.current_designer_obj.find("table tr:first td:nth-child("+(num+1)+")").attr("score",vals);
	}

	this.setMatrixItem_value = function(vals,num)
	{
		this.current_designer_obj.find("table tr:first td:nth-child("+(num+1)+")").text(vals);
	}

	this.addItem_Matrix = function(text,num)
	{
		if(this.current_designer_obj.find("tr:nth-child(2) td:nth-child("+(num+1)+") input").attr("is_update") == "false")
		{
			if(num < this.current_designer_obj.find("tr:nth-child(2) td input[is_update=false]").length)
			{
				this.checkUpdatestatus_add();
				return;
			}
		}

		if(text == "")
			text = "选项";
		this.current_designer_obj.find("table tr:first td:nth-child("+(num+1)+")").after('<td align="center" score="1">'+text+'</td>');

		var sort_num = this.item_num;
		var item_nums = this.current_designer_obj.find("table tr:first td").length-2;
		
		//添加选项
		this.current_designer_obj.find("table tr").each(function(i){
			if(i>0)
			{
				var ids = $(this).find("td:nth-child("+(num+1)+") input").attr("id");
				$(this).find("td:nth-child("+(num+1)+")").after('<td align="center"><input type="radio" class="checkbox" id="'+ids+'" name="'+ids+'" value="'+item_nums+'"/></td>');			
			}
		});
		this.item_num += 1;
		//算出宽度
		this.getMatrix_width();	
		this.current_designer_obj.click();
	}
	//使用常用量添加
	this.addItem_common_Matrix = function(commonArr)
	{
		this.current_designer_obj.find("table tr").each(function(i){
				var str = "";
				if(i == 0)
				{
					$(this).empty();
					str = '<td height="25px" width="14%"></td>';
					for(var j=0;j<commonArr.length;j++)
					{
						str += '<td align="center" score="'+(j+1)+'">'+commonArr[j]+'</td>';
					}
					str += '<td class="m_td" width="14%"></td>';
					
				}
				else
				{
					var item_id = $(this).find("input:first").attr("id");
					var left_title = $(this).find("td:first").text();	
					
					$(this).empty();

					str = '<td class="m_td">'+left_title+'</td>';					
					for(var j=0;j<commonArr.length;j++)
					{
						str += '<td align="center"><input type="radio" class="checkbox" id="'+item_id+'" name="'+item_id+'" value="'+(j+1)+'"/></td>';
					}
					str += '<td class="m_td"></td>';
				}
				$(this).append(str);
		});

		//算出宽度
		this.getMatrix_width();
		this.current_designer_obj.click();

	}

	this.deleteItem_Matrix = function(num)
	{		
		if(this.current_designer_obj.find("tr:nth-child(2) td:nth-child("+(num+1)+") input").attr("is_update") == "false")
		{
			this.checkUpdatestatus_delete();
			return;
			
		}
		//添加选项
		this.current_designer_obj.find("table tr").each(function(i){
				$(this).find("td:nth-child("+(num+1)+")").remove();	
		});
		//算出宽度
		this.getMatrix_width();
		this.current_designer_obj.click();
	}

	this.sortItem_Matrix = function(flag,num)
	{
		if(this.current_designer_obj.find("tr:nth-child(2) td:nth-child("+(num+1)+") input").attr("is_update") == "false")
		{
			this.checkUpdatestatus_sort();
			return;
			
		}
		if(flag == "up" && num > 1)
		{
			this.current_designer_obj.find("table tr").each(function(){
				$(this).find("td:nth-child("+(num)+")").before($(this).find("td:nth-child("+(num)+")").next());	
				
			});			
		}

		if(flag == "down" && num < $("#matrix_option_table input").length/2)
		{
			this.current_designer_obj.find("table tr").each(function(){
				
				$(this).find("td:nth-child("+(num+1)+")").next().after($(this).find("td:nth-child("+(num+1)+")"));	
			});
		}
		this.current_designer_obj.click();
	}
	
	/************ 矩阵题区域　结束 *************/

	/************ 单个下拉列表区域　开始 *************/
	//添加单组下拉框样式
	this.selectOnly_disigner_item_str = function(sortnum,num)
	{	

		var str = '<table border="0" style="width:98%" cellpadding="0" cellspacing="0" ><tr><td>&nbsp;<select id="item_'+this.item_num+'" name="item_'+this.item_num+'">';
		     str += '<option value="">请选择</option>';
			 for(var i=1;i<3;i++)
			 {
				str += '<option value="'+i+'">选项'+i+'</option>';
			 }
			 str +='</select></tr></td></table>';
		this.item_num += 1;
		return str;
	}

	this.selectOnly_attr_html = function()
	{
		var str = '<div id="selectOnly_attr_div">';
			 str += this.public_attr_html_title();
			 str += this.public_attr_html_bathAdd();

			 str += '<table border="0" cellpadding="1" cellspacing="0" width="100%" id="list_option_table">';
			  str += '<tr>';
			   str += '<td height="21px" class="itemattr_title_td">　选项文字</td>';			   
			   str += '<td width="30px" align="center" class="itemattr_title_td">默认</td>';
			  str += '</tr>';			 
			
			  str += this.selectOnly_attr_item_str();
			  
			 str += '</table>';			
			 str += this.public_attr_html();
			str += '</div>';
		return str;
	}
	//属性中选项字符串
	this.selectOnly_attr_item_str = function()
	{
		var names = this.name;
		var str = "";
		
		this.current_designer_obj.find("table select option").each(function(i){
			if(i>0)
			{
			    str += '<tr>';
			     str += '<td class="itemattr_td_spa" valign="middle"><input type="text" class="input_border" id="" name="" maxlength="80" style="width:70px;height:18px" value="'+$(this).text()+'" onkeyUP="ObjectPool['+names+'].setSelectItem_value(this.value,'+i+')">&nbsp;';
				  str += '<IMG SRC="../images/jia.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].addItem_selectOnly(\'\','+i+')">';
				  str += '<IMG SRC="../images/jian.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].deleteItem_selectOnly('+i+')">';
				  str += '<IMG SRC="../images/up.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].sortItem_selectOnly(\'up\','+i+')">';
				  str += '<IMG SRC="../images/down.gif" WIDTH="21" HEIGHT="20" BORDER="0" align="absmiddle" ALT="" onclick="ObjectPool['+names+'].sortItem_selectOnly(\'down\','+i+')">';
			     str += '</td>';			     
			     str += '<td class="itemattr_td_spa" style="padding-top:6px"><input type="radio" class="checkbox" id="default_checked" name="default_checked" value="" ';

				 if($(this).attr("selected"))
				   str += 'checked="true"';

				 str += 'onclick="ObjectPool['+names+'].selectedItem_selectOnly('+i+')""></td>';				 
		        str += '</tr>';
			}
		 });
		 return str;
	}
	
	//更改选项显示值及数据值
	this.setSelectItem_value = function(vals,num)
	{
		this.current_designer_obj.find("table select option").each(function(i){
			if(i == num)
				$(this).text(vals)
		});		
	}

	//添加选项值
	this.addItem_selectOnly = function(text,num)
	{		
		if(this.current_designer_obj.find("table select option:nth-child("+(num+1)+")").attr("is_update") == "false")
		{
			if(num+1 < this.current_designer_obj.find("table select option[is_update=false]").length)
			{
				this.checkUpdatestatus_add();
				return;
			}
		}

		var optionObj = this.current_designer_obj.find("table select option");
		if(text == "")
			text = "选项"+optionObj.length;
		optionObj.each(function(i){
			if(i == num)
				$(this).after('<option value="'+optionObj.length+'">'+text+'</option>');
		});

		//再在题目属性中添加
		this.current_designer_obj.click();	
	}
	//删除下拉列表选项
	this.deleteItem_selectOnly = function(num)
	{		
		if(this.current_designer_obj.find("table select option:nth-child("+(num+1)+")").attr("is_update") == "false")
		{
			this.checkUpdatestatus_delete();
			return;
			
		}

		this.current_designer_obj.find("table select option").each(function(i){
			if(i == num)
				$(this).remove();
		});
		//再在题目属性中添加
		this.current_designer_obj.click();
	}

	//默认选中
	this.selectedItem_selectOnly = function(num)
	{
		this.current_designer_obj.find("table select option").each(function(i){
			if(i == num)
				$(this).attr("selected","true");
		});
	}

	//选项排序
	this.sortItem_selectOnly = function(sortype,num)
	{
		if(this.current_designer_obj.find("table select option:nth-child("+(num+1)+")").attr("is_update") == "false")
		{
			this.checkUpdatestatus_sort();
			return;
			
		}
		if(sortype == "up" && num > 1)
		{
			this.current_designer_obj.find("table select option").each(function(i){
				if(i == num)
				{
					var ops = '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';					
					$(this).prev().before(ops);
					$(this).remove();					
				}
			});
		}
		
		if(sortype == "down" && this.current_designer_obj.find("table select option").length-1 > num)
		{
			this.current_designer_obj.find("table select option").each(function(i){
				if(i == num)
				{
					var ops = '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';					
					$(this).next().after(ops);
					$(this).remove();					
				}
			});
		}
		this.current_designer_obj.click();
	}

	//重载
	this.options_table_reload_selectOnly = function()
	{
		$("#list_option_table").find("tr").each(function(i){
			if(i>0)
				$(this).remove();
		})
		$("#list_option_table").find("tr:nth-child(1)").after(this.selectOnly_attr_item_str());
	}

	/************ 单个下拉列表区域　结束 *************/

	/************ 公有字符串区域　开始 *************/
	this.public_attr_html_title = function()
	{
		var str = '<div class="text_div">问题标题</div>';
		   str += '<div><textarea id="radio_lab_input" name="radio_lab_input" style="width:98%;height:40px;overflow:hidden" onkeyUP="ObjectPool['+this.name+'].setSubjectTitle(this.value)" >'+this.current_designer_obj.find("#title_span").html().replace(/&nbsp;<span id=\"?req_span\"? class=\"?wargin_span\"?>.*?$/ig,"")+'</textarea></div>';//replace(/&nbsp;<span id=req_span .*?$/ig,"")
		   str += '<div class="blankH5"></div>';
		   str += '<div ><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true" style="float:left"><span onclick="ObjectPool['+this.name+'].openHTMLEditor(\'radio_lab_input\')">&nbsp;HTML编辑器</span></a></div>';
		   str += '<div class="blankH5"></div>';
		return str;
	}
	
	this.public_attr_html_bathAdd = function()
	{
		var  str = '<div class="">';
			 if(this.current_designer_obj.attr("is_update") != "false") 
				str += '<a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true" style="float:left"><span id="subButton" onclick="ObjectPool['+this.name+'].openCommonDialog()">&nbsp;添加常用选项</span></a>';
			 str += '<a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].bathAddItem(\'show\')">&nbsp;批量添加选项</span></a></div>';
			 str += this.getBathItemHTMLStr();
		return str;
	}

	//打开常用量窗口
	this.openCommonDialog = function()
	{
		$('#common_dialog').dialog('open');
	}

	//批量添加第２种，用于量表题，多一种常用量选项
	this.public_attr_html_bathAdd2 = function()
	{
		var  str = '<div class="blankH5"></div>';
			 str += '<div class="items_divs">';
			 if(this.current_designer_obj.attr("is_update") != "false") 
				str += '<a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].bathAddItem_common(\'show\')">&nbsp;使用常用量表</span></a>';
			 str += '<a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].bathAddItem(\'show\')">&nbsp;批量添加选项</span></a>';
			 str += '</div>';
			 str += '<div class="blankH5"></div>';
			 str += '<div class="items_divs" id="batch_item_common_div" style="display:none;text-align:center;height:20px;">';
			 str += '<ul>'
			 str += '<li onmouseover="this.style.color=\'#A94B3B\'" onmouseout="this.style.color=\'#355286\'" onclick="ObjectPool['+this.name+'].setCommonValue(\'myd\')">满意度</li>';
			 str += '<li onmouseover="this.style.color=\'#A94B3B\'" onmouseout="this.style.color=\'#355286\'" onclick="ObjectPool['+this.name+'].setCommonValue(\'rtd\')">认同度</li>';
			 str += '<li onmouseover="this.style.color=\'#A94B3B\'" onmouseout="this.style.color=\'#355286\'" onclick="ObjectPool['+this.name+'].setCommonValue(\'fhd\')">符合度</li>';
			 str += '<li onmouseover="this.style.color=\'#A94B3B\'" onmouseout="this.style.color=\'#355286\'" onclick="ObjectPool['+this.name+'].setCommonValue(\'zyd\')">重要度</li>';
			 str += '<li onmouseover="this.style.color=\'#A94B3B\'" onmouseout="this.style.color=\'#355286\'" onclick="ObjectPool['+this.name+'].setCommonValue(\'yyd\')">愿意度</li>';
			 str += '</div>';
			 str += '</div>';
			 str += this.getBathItemHTMLStr();
		return str;
	}

	this.getBathItemHTMLStr = function()
	{
		var str = '<div id="batch_item_div" style="display:none;" >';
			 str += '<div class="blankH5"></div>';
			 str += '<div ><textarea id="batch_item_area" name="batch_item_area" class="textarea_defaule" value="请在每行输入一个选项，然后点击下面的按钮"></textarea></div>';
			 str += '<div class="blankH5" style="clear:both;"></div>';
			 str += '<div style="padding-left:50px;width:180px;margin:auto 0" ><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].bathGetItemValue()">&nbsp;添加选项</span></a><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].bathAddItem(\'hide\')">&nbsp;取消</span></a></div>';
			 str += '</div>';			 
			 str += '<div class="blankH5"></div>'; 
		return str;
	}

	//设置常用选项值
	this.setCommonValue = function(stype)
	{
		var typeArr = [];
		typeArr["myd"] = ["很不满意","不满意","一般","满意","很满意"];
		typeArr["rtd"] = ["很不同意","不同意","一般","同意","很同意"];
		typeArr["fhd"] = ["很不符合","不符合","一般","符合","很符合"];
		typeArr["zyd"] = ["很不重要","不重要","一般","重要","很重要"];
		typeArr["yyd"] = ["很不愿意","不愿意","一般","愿意","很愿意"];

		if(this.current_designer_obj.attr("type") == "scale")
		{
			this.addItem_common_Scale(typeArr[stype]);
		}
		if(this.current_designer_obj.attr("type") == "matrix")
		{
			this.addItem_common_Matrix(typeArr[stype]);
		}
		
	}

	//选项排列个数
	this.public_attr_html_rank = function()
	{
		var c_n = this.current_designer_obj.find("li").attr("class").replace(/[^\d]/ig,"");//得到当前选项横向排列个数
		var str = '<div class="blankH5"></div>'; 
			str += '<div class="items_divs">横向排列选项，每行<select id="show_cell_num" name="show_cell_num" onchange="ObjectPool['+this.name+'].setItemShowCSS(this.value)">';
			  for(var i=1;i<11;i++)
			  {
				  if(c_n == i)
					str += '<option value="'+i+'" selected="true">'+i+'</option>';
				  else
					str += '<option value="'+i+'">'+i+'</option>';
			  }
			 str += '</select>个</div>';
			 if(this.current_designer_obj.attr("type") == "checkboxList")
			{
				str += '<div class="blankH5"></div>';
				str += '<div class="items_divs">限制选中项数：</div>';
				str += '　最少选中<input type="text" id="checkbox_least" name="checkbox_least" style="width:30px" value="'+this.current_designer_obj.attr("c_least")+'" onkeyup="ObjectPool['+this.name+'].setCheckboxItemNumber(this,\'least\')"/>　最多选中<input type="text" id="checkbox_maximum" name="checkbox_maximum" style="width:30px" value="'+this.current_designer_obj.attr("c_maximum")+'" onkeyup="ObjectPool['+this.name+'].setCheckboxItemNumber(this,\'maximum\')"/>';
				str += '<div class="blankH5"></div>';
			 }
		return str;
	}

	//设置复选框的选中限制
	this.setCheckboxItemNumber = function(obj,types)
	{
		var vals = $(obj).val().trim();
		if(vals != "")
		{
			var rUnsignedInt = /^\d*$/;
			if(!rUnsignedInt.test(vals) || vals == 0)
			{
				$(obj).val("");
				parent.alertN("该输入框只能输入大于0的整数，请重新输入");
				return;
			}
			else
			{
				if(parseInt(vals) > this.current_designer_obj.find("li").length)
				{
					$(obj).val("");
					parent.alertN("填写的数字不能大于选项个数，请重新输入");
					return;
				}
				if($("#checkbox_least").val().trim() != "" && $("#checkbox_maximum").val().trim() != "" && parseInt($("#checkbox_maximum").val()) < parseInt($("#checkbox_least").val()) )
				{
					$(obj).val("");
					parent.alertN("最大数不能小于最小数，请重新输入");
					return;
				}
			}
		}		
		this.current_designer_obj.attr("c_"+types,vals);		
	}

	//提示
	this.public_attr_html = function()
	{
		 var str = '<div class="blankH5"></div>';
			 str += '<div class="items_divs" >验证选项：<input type="checkbox" class="checkbox" id="is_required" name="is_required" value="1" onclick="ObjectPool['+this.name+'].setItemIsRequired(this)"';
			 if($(this.current_designer_obj).attr("is_required") == true)
				 str += 'checked="true"';
			 str += '/>&nbsp;<label>必答题</label></div>';
			 str += '<div class="blankH5"></div>';
		     str += '<div class="text_div">将所有题目设为：';
			 str += '<input type="radio" class="checkbox" id="all_item_req" name="all_item_req" value="true" ';
				if(this.required)
					str += 'checked="true"';
			 str += ' onclick="ObjectPool['+this.name+'].setAllItemIsRequired(true)">&nbsp;<label>必答</label>&nbsp;&nbsp;';
			 str += '<input type="radio" class="checkbox" id="all_item_req" name="all_item_req" value="false" onclick="ObjectPool['+this.name+'].setAllItemIsRequired(false)" ';
				if(!this.required)
					str += 'checked="true"';
			 str += '>&nbsp;<label>非必答</label></div>';
		     str += '<div class="blankH5"></div>';
			 str += '<div class="text_div">填写提示</div>';
		     str += '<div ><textarea id="prompt_area" name="prompt_area" class="textarea_defaule" onkeyUP="ObjectPool['+this.name+'].setSubjectDescription(this.value)">';
			 if($(this.current_designer_obj).find("#description_div").is("div"))
				str += $(this.current_designer_obj).find("#description_div").html();
			 str += '</textarea></div>';
			 str += '<div class="blankH5" ></div>';
			 str += '<div ><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true" style="float:left"><span onclick="ObjectPool['+this.name+'].openHTMLEditor(\'prompt_area\')">&nbsp;HTML编辑器</span></a></div>';
		return str;
	}

	//设置所有的题为必答题
	this.setAllItemIsRequired = function(boole)
	{
		this.required = boole;		
		$(this.designerObj).find(" > div").each(function(i){
			if(i > 0)
			{
				$(this).attr("is_required",boole);
				if(boole == true)
					$(this).find("#req_span").text("*");
				else
					$(this).find("#req_span").text("");
			}
		});
		$("#is_required").attr("checked",boole);		
	}

	//设置当前题为必答题 
	this.setItemIsRequired = function(obj)
	{
		if($(obj).is(':checked'))
		{
			this.current_designer_obj.attr("is_required",true);
			this.current_designer_obj.find("#req_span").text("*");
		}else
		{
			this.current_designer_obj.attr("is_required",false);
			this.current_designer_obj.find("#req_span").text("");
		}
	}

	

	//设置提示信息
	this.setSubjectDescription = function(vals)
	{
		this.current_designer_obj.find("#description_div").remove();
		if(vals.trim() != "")
		{
			this.current_designer_obj.find("#tools_button_div").before("<div id='description_div'>"+vals.replace(/\n/g,"<br>")+"</div>");
		}		
	}

	//显示批量添加窗口
	this.bathAddItem = function(flag)
	{
		$("#batch_item_area").val("");

		if(flag == "show")
			$("#batch_item_div").show();
		else
			$("#batch_item_div").hide();
	}

	//显示常用量窗口
	this.bathAddItem_common = function(flag)
	{
		if(flag == "show")
			$("#batch_item_common_div").show();
		else
			$("#batch_item_common_div").hide();
	}

	//批量添加值
	this.bathGetItemValue = function(vals)
	{
		var str = "";
		
		if(vals != null && vals.trim() != "")
		{
			str = vals.split("\n");	
		}
		else
		{
			str = $("#batch_item_area").val().split("\n");
		}
		if(str != "")
		{		
			if(this.current_designer_obj.attr("type") == "radioList")
			{		
				if(vals != null && vals.trim() != "")
					this.current_designer_obj.find("ul").html("");
				for(var i=0;i<str.length;i++)
				{
					if(str[i].trim() != "")
						this.current_designer_obj.find("ul").append(this.radioList_disigner_item_str(this.current_designer_obj.attr("sort_num"),str[i],this.current_designer_obj.find("ul li").length+1));
				}

				this.current_designer_obj.click();
			}
			if(this.current_designer_obj.attr("type") == "checkboxList")
			{				
				if(vals != null && vals.trim() != "")
					this.current_designer_obj.find("ul").html("");
				for(var i=0;i<str.length;i++)
				{
					if(str[i].trim() != "")	
						this.current_designer_obj.find("ul").append(this.checkboxList_disigner_item_str(this.current_designer_obj.attr("sort_num"),str[i],this.current_designer_obj.find("ul li").length+1));
				}
				this.current_designer_obj.click();
			}
			if(this.current_designer_obj.attr("type") == "selectOnly")
			{
				if(vals != null && vals.trim() != "")
				{
					this.current_designer_obj.find("table select option").each(function(i){
						if(i>0)
						{
							$(this).remove();
						}
					})
				}
				for(var i=0;i<str.length;i++)
				{
					if(str[i].trim() != "")
						this.addItem_selectOnly(str[i],this.current_designer_obj.find("table select option").length-1);
				}
				
			}
			if(this.current_designer_obj.attr("type") == "matrix")
			{
				for(var i=0;i<str.length;i++)
				{
					if(str[i].trim() != "")
						this.addItem_Matrix(str[i],this.current_designer_obj.find("table tr:first td").length-2);
				}
				
			}
			if(this.current_designer_obj.attr("type") == "scale")
			{
				for(var i=0;i<str.length;i++)
				{
					if(str[i].trim() != "")
						this.addItem_Scale(str[i],this.current_designer_obj.find("table LI").length-1);
				}				
			}
			if(this.current_designer_obj.attr("type") == "voteRadio" || this.current_designer_obj.attr("type") == "voteCheckbox" )
			{		
				if(vals != null && vals.trim() != "")
					this.current_designer_obj.find("table").empty();
				for(var i=0;i<str.length;i++)
				{
					if(str[i].trim() != "")
						this.addItem_voteRadio(str[i],this.current_designer_obj.find("table tr").length-1);
				}				
			}
			
		}
	}

	//验证整型，如分数
	this.checkIntFormat = function(obj,vals)
	{
		if(vals.trim() == "")
		{
			vals = "0";
			$(obj).val(vals);
		}
		else
		{
			var rDigit = /^(\+|-)?\d*$/;
			if(!rDigit.test(vals))
			{
				$(obj).addClass("error_input_back");
				parent.alertN("分值只能是整数，请修改红色输入区域!");
				return false;
			}
		}
		return true;
	}

	//给当前控件加上check属性，因IE8无法设置checked属性，所以用check替代，在前台页面进行特殊处理
	this.setThisInputCheck = function(obj)
	{
		$("input[id="+$(obj).attr("id")+"]").remove("CHECKED");
		$(obj).attr("CHECKED","true");
	}
	
	/************ 公有字符串区域　结束 *************/

	/************ 主题功能操作区域　结束 *************/
	//得到功能按钮字符串
	this.getToolsButtonStr = function()
	{
		var str =  '<div id="tools_button_div"><div style="width:250px;float:right">';
		     str += '<a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span onclick="ObjectPool['+this.name+'].deleteSubject(this)">删除</span></a>';
			 str += '<a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span onclick="ObjectPool['+this.name+'].subject_sort(this,\'up\')">上移</span></a>';
			 str += '<a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span onclick="ObjectPool['+this.name+'].subject_sort(this,\'down\')">下移</span></a>';
			 str += '<a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span onclick="ObjectPool['+this.name+'].subject_sort(this,\'first\')">最前</span></a>';
			 str += '<a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span onclick="ObjectPool['+this.name+'].subject_sort(this,\'last\')">最后</span></a>';
			str += '</div></div>';
		return str;
	}
	//打开图片选择窗口
	this.selectImage = function(num)
	{
		this.current_item_num = num;

		var img_obj = this.current_designer_obj.find("ul li:nth-child("+(this.current_item_num+1)+") img");
		if(img_obj.is("img"))
		{
			$("#img_path").val(img_obj.attr("src"));			
			$("#img_width").val(img_obj.attr("width"));
			$("#img_height").val(img_obj.attr("height"));
		}
		else
			$("#img_path").val("");		
		
		var des_obj = this.current_designer_obj.find("ul li:nth-child("+(this.current_item_num+1)+")").find("#item_img_describe");
		if(des_obj.is("div"))
			$("#img_desc_textarea").val(des_obj.html());
		else
		{
			$("#img_desc_textarea").val("");
		}
		$('#img_dialog').dialog('open');		
	}

	//删除主题
	this.deleteSubject = function(obj)
	{
		this.getToolsButtonParentDiv(obj).remove();
		//重新设置主题的序号
		this.setSubjectSortNum();
	}

	//得到操作按钮当前主题div对象
	this.getToolsButtonParentDiv = function(obj)
	{
		return $(obj).parent().parent().parent().parent();
	}

	//主题排序
	this.subject_sort = function(obj,order_type)
	{
		var thisObj = this.getToolsButtonParentDiv(obj);
		var s_sort = parseInt(thisObj.find("#sort_num").text());
		if(order_type == "up" || order_type == "first")
		{						
			if(s_sort > 1)
			{
				if(order_type == "first") 
				{
					this.designerObj.find(" > div:nth-child("+1+")").after(thisObj);
				}
				else
					thisObj.after(this.designerObj.find(" > div:nth-child("+(s_sort)+")"));
			}
		}
		if(order_type == "down" || order_type == "last")
		{
			var total_num = this.designerObj.find(" > div").length;
			if(s_sort < total_num-1)
			{
				if(order_type == "last")
				{
					this.designerObj.find(" > div:nth-child("+total_num+")").after(thisObj);
				}
				else
					this.designerObj.find(" > div:nth-child("+(s_sort+2)+")").after(thisObj);
			}
		}
		
		//重新设置主题的序号
		this.setSubjectSortNum();
	}

	//重新设置主题的序号
	this.setSubjectSortNum = function()
	{
		this.designerObj.find(" > div").each(function(i){
			if(i > 0)
				$(this).find("#sort_num").text(i+".");
		});
	}

	//点击设计区域标题，显示问卷属性
	this.showSurveyAttr = function(obj)
	{
		$(".ware_lab_check").attr("class","ware_lab_default");		
		$("#attrs_div > div").hide();

		$("#ware_lab_div div:last-child").attr("class","ware_lab_check");
		$("#survey_attr").show();

		this.current_designer_obj = null;

	}
	/************ 主题功能操作区域　结束 *************/	
	/*---------------------------------------组件框架操作区域　开始---------------------*/
	this.showWareArea = function(ware_div)
	{
		var str = '<div id="ware_lab_div">';
			 str += '<div id="menu_survey" class="ware_lab_check" onclick="ObjectPool['+this.name+'].menu_click(this,\'new_subject\')">添加新题</div>';			
			 str += '<div id="menu_survey" class="ware_lab_default" onclick="ObjectPool['+this.name+'].menu_click(this,\'subject_attr\')">题目属性</div>';			 
			 str += '<div id="menu_survey" class="ware_lab_default" onclick="ObjectPool['+this.name+'].menu_click(this,\'survey_attr\')">问卷属性</div>';			  
		   str += '</div>';
			str += '<div id="attrs_div" style="text-align:center">';			 
			 str += '<div id="new_subject" class="attrs_div">';
			  str += '<div class="blankH9"></div>';
			  str += '<div class="text_div">全部题型</div>';
			  str += '<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">';
			   str += '<tr>';
			    str += '<td  height="30px"><div class="radioList_button" onclick="ObjectPool['+this.name+'].showItemHtml(\'radioList\')"></div></td>';
				str += '<td ><div class="checkboxList_button" onclick="ObjectPool['+this.name+'].showItemHtml(\'checkboxList\')"></div></td>';
			   str += '</tr>';
			   str += '<tr>';
			    str += '<td  height="30px"><div class="selectOnly_button" onclick="ObjectPool['+this.name+'].showItemHtml(\'selectOnly\')"></div></td>';
				str += '<td><div class="matrix_button" onclick="ObjectPool['+this.name+'].showItemHtml(\'matrix\')"></div></td>';
			   str += '</tr>';
			   str += '<tr>';
			    str += '<td  height="30px"><div class="textareas_button" onclick="ObjectPool['+this.name+'].showItemHtml(\'textareas\')"></div></td>';
				str += '<td><div class="uploadfile_button" onclick="ObjectPool['+this.name+'].showItemHtml(\'uploadfile\')"></div></td>';
			   str += '</tr>';
			   str += '<tr>';
			    str += '<td  height="30px"><div class="voteRadio_button" onclick="ObjectPool['+this.name+'].showItemHtml(\'voteRadio\')"></div></td>';
				str += '<td><div class="voteCheckbox_button" onclick="ObjectPool['+this.name+'].showItemHtml(\'voteCheckbox\')"></div></td>';
			   str += '</tr>';
			   str += '<tr>';
			    str += '<td  height="30px"><div class="scale_button" onclick="ObjectPool['+this.name+'].showItemHtml(\'scale\')"></div></td>';
				str += '<td><div class=""></div></td>';
			   str += '</tr>';
			  str += '</table>';	     
			  str += '</div>';

		      str += '<div id="subject_attr" class="attrs_div">';
			   str += '<div class="blankH9"></div>';
			   str += '<div id="subject_attr_div" style="display:none"></div>'; 
			   str += '<div id="subject_main_attr_div">';
			    str += '<div class="div_default">问卷调查分类：<select id="category_id" name="category_id" style="width:120px"></select></div>';
			    str += '<div class="div_default">开始时间：<input class="Wdate" type="text" class="input_border" name="start_time" id="start_time"  onfocus="WdatePicker({dateFmt:\'yyyy-MM-dd\',isShowClear:true,readOnly:true})" readonly="true" style="width:120"></div>';
		        str += '<div class="div_default">结束时间：<input class="Wdate" type="text" class="input_border" name="end_time" id="end_time" onfocus="WdatePicker({dateFmt:\'yyyy-MM-dd\',isShowClear:true,readOnly:true})" readonly="true" style="width:120"></div>';
		        str += '<div class="div_default">重复提交间隔时间：<input type="text" class="spacing_time" id="spacing_time" name="ip_fre" style="width:50px" maxlength="5"><select id="spacing_type" name="spacing_type">';
				str += '<option value="d">天</option><option value="h">小时</option><option value="m">分钟</option>';
				str += '</select></div>';
				str += '<div class="div_default">同一IP重复提交次数：<input type="text" class="input_border" id="ip_fre" name="ip_fre" style="width:50px" value="1" maxlength="3"></div>';
			    str += '<div class="text_div" style="color:#708BAA">　注：输入0值不限制重复提交次数</div>';
				str += '<div class="div_default">只允许注册会员参与：<input type="radio" class="checkbox" id="is_register" name="is_register" value="0">&nbsp;<label>是</label>&nbsp;&nbsp;<input type="radio" class="checkbox" id="is_register" name="is_register" checked=true value="1">&nbsp;<label>否</label></div>';
		        str += '<div class="div_default">显示题目序号：<input type="checkbox" class="checkbox" checked="true" id="is_show_subsort" name="is_show_subsort" onclick="ObjectPool['+this.name+'].isShowSubjectSort(this)" value="1"></div>';
				str += '<div class="div_default">是否显示标题：<input type="checkbox" class="checkbox" checked="true" id="is_show_title" name="is_show_title" onclick="ObjectPool['+this.name+'].isShowSubjectTitle(this)" value="1" checked="true"></div>';
				str += '<div class="div_default">是否展示调查结果：<input type="checkbox" class="checkbox" checked="true" id="is_show_result" name="is_show_result" value="1" checked="true"></div>';
				str += '<div class="text_div hidden">前台展示模板：</div>';
			    str += '<div class="div_default hidden"><input type="text" class="input_border" id="model_path" name="model_path" style="width:98%" maxlength="80"></div>';
		       str += '</div>';
			  str += '</div>';
			  str += '<div id="survey_attr"  class="attrs_div">';
			   str += '<div class="blankH9"></div>';
			   str += '<div id="s_name_div">';
				str += '<div class="text_div">请输入您的问卷标题（必填）</div>';
				str += '<div id=""><input type="text" class="input_border" id="s_name" name="s_name" value="请输入您的问卷的标题" style="width:98%" onkeyUP="ObjectPool['+this.name+'].setValue(this)"></div>';
				str += '<div class="blankH5" ></div>';
				str += '<div ><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true" style="float:left"><span onclick="ObjectPool['+this.name+'].openHTMLEditor(\'s_name\')">&nbsp;HTML编辑器</span></a></div>';
				str += '<div class="blankH5"></div>';
				str += '<div class="text_div">问卷说明</div>';
				str += '<div id="">';
				 str += '<textarea id="s_description" name="s_description" style="height:180px;width:98%" onkeyUP="ObjectPool['+this.name+'].setValue(this)">请填写关于此问卷的说明</textarea>';
				 str += '<div class="blankH5" ></div>';
				 str += '<div ><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true" style="float:left"><span onclick="ObjectPool['+this.name+'].openHTMLEditor(\'s_description\')">&nbsp;HTML编辑器</span></a></div>';
				str += '</div>';
				str += '</div>';
			  str += '</div>';
			 str += '</div>';
		this.wareObj.append(str);	
		init_input();

		str = "";
		str += '<div id="subject_head"  onclick="ObjectPool['+this.name+'].showSurveyAttr(this)">';
		 str += '<div id="s_name_show">请输入您的问卷的标题</div>';
		 str += '<div id="s_description_show">请填写关于此问卷的说明</div>';
		 str += '<input type="hidden" id="max_item_num" name="max_item_num"/><input type="hidden" id="max_sort_num" name="max_sort_num"/><div id="show_img_des_div" onmouseover="show_img_desc(this)" onmouseout="this.style.display=\'none\'"></div>';
		str += '</div>';
		this.designerObj.append(str);			
				
		//$(".ware_lab_check").click();
	}

	//设置是否显示题目序号
	this.isShowSubjectSort = function(obj)
	{
		if($(obj).is(':checked'))
		{
			this.designerObj.find("#sort_num").show();
		}
		else
		{			
			this.designerObj.find("#sort_num").hide();
		}
	}

	//是否显示标题
	this.isShowSubjectTitle = function(obj)
	{
		if($(obj).is(':checked'))
		{
			this.designerObj.find("#subject_head").show();
		}
		else
		{			
			this.designerObj.find("#subject_head").hide();
		}
	}
	
	this.menu_click = function(obj,div_id)
	{		
		$(".ware_lab_check").attr("class","ware_lab_default");		
		$(obj).attr("class","ware_lab_check");

		$("#attrs_div > div").hide();
		
		$("#"+div_id).show();

		if(div_id == "subject_attr")
		{
			sd.show_attr_html();
		}
	}
	/*---------------------------------------组件框架操作区域  结束---------------------*/
	
	/*---------------------------------------编辑器窗口　开始---------------------*/
	this.showHTMLEditor = function()
	{		
		   $("#htmleditor_dialog").css("display","");
		   $(function(){
				$("#htmleditor_dialog").dialog({
					resizable: false,
					width:690,
					height:476,
					modal: true,
					title:'编辑'
				});
				closeHTMLEditor();
			});
			
	}
	this.showHTMLEditor();
    var is_show = false;
	this.openHTMLEditor = function(fileName)
	{
		this.htmleditor_filed_name = fileName;
		/*
		var oEditor = FCKeditorAPI.GetInstance('contents'); 
		oEditor.SetHTML($("#"+fileName).val());*/
		$('#htmleditor_dialog').dialog('open');
        if(is_show)
        {
            distoryUeditor("contents");
        }
        initUeditor("contents");
        setV("contents",$("#"+fileName).val());
        is_show = true;
		//KE.html("contents",$("#"+fileName).val())
		//KE.util("contents").fullscreen();
		//KE.util("contents").fullscreen();
	}
	/*---------------------------------------编辑器窗口　结束---------------------*/

	/*---------------------------------------选择图片　开始---------------------*/
	this.showImgSelect = function()
	{		
		var str = '<div id="img_dialog" title="选择图片" style="width:500px;height:185px;background:#ECF4FC;">';
			str += '<form id="upoloadform" name="upoloadform" action="/InterviewLivePicSubmit" target="targetFrame" method="post" enctype="multipart/form-data">';
			 str += '<table id="" border="0" cellpadding="0" cellspacing="0" width="98%" style="margin-top:20px">';
			  str += '<tr>';
			   str += '<td height="25px" width="28px"><textarea id="img_desc_textarea" name="img_desc_textarea" style="display:none"></textarea></td>';
			   str += '<td width="60px" align="right">图片地址：</td>';
			   str += '<td width="240px"><input type="text" class="input_border" id="img_path" name="img_path" style="width:230px"/></td>';		
			  str += '</tr>';
			  str += '<tr>';
			   str += '<td height="25px" width="28px"></td>';
			   str += '<td width="60px" align="right"></td>';
			   str += '<td ><div style="float:left;width:100px"><input type="file" id="fileName_img" name="fileName_img" onchange="ObjectPool['+this.name+'].changeImgPathValue(this.value)" size="20" style="width:220px"/></div><div style="float:left"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span onclick="ObjectPool['+this.name+'].openHTMLEditor(\'img_desc_textarea\')">&nbsp;描述信息</span></a></div></td>';
			 str += '</tr>';
			  str += '<tr>';
			   str += '<td height="25px"></td>';
			   str += '<td align="right">宽度：</td>';
			   str += '<td><input type="text" class="input_border" id="img_width" name="img_width" style="width:50px" value="'+this.default_img_width+'" maxlength="3">　　高度：<input maxlength="3" type="text" class="input_border" id="img_height" name="img_height" style="width:50px" value="'+this.default_img_height+'"></td>';
			  str += '</tr>';
			  str += '<tr>';
			   str += '<td colspan="3"><div id="fileQueue"></div></td>';
			  str += '</tr>';
			 str += '</table>';
			 str += '<br />';
			 str += '<table border="0" cellpadding="0" cellspacing="0" width="98%" class="content_lab_table" align="center">';
			  str += '<tr>';
			   str += '<td class="b_bottom_l"></td>';
			   str += '<td class="b_bottom_c" align="center">';
				str += '<div style="width:100px;height:24px">';
				 str += '<a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span onclick="ObjectPool['+this.name+'].showImageToItem()">&nbsp;确定</span></a>';				 
				 str += '<a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span onclick="ObjectPool['+this.name+'].closeImgDialog()">&nbsp;关闭</span></a>';
				 str += '<input id="reset_input" type="reset" style="display:none"/>';
				str += '</div>';	 
			   str += '</td>';
			  str += '</tr>';
			 str += '</table>';
			str += '</form>';
			str += '<iframe name="targetFrame" width="0" height="0" frameborder="0" ></iframe>';
		   str += '</div>';

		   $("body").append(str);
		   $(function(){
			   $("#img_dialog").dialog({
					resizable: false,
					width:430,
					height:265,
					modal: true,
					title:'编辑'
				});
			   $("#img_dialog").dialog("close");
			});
			initUPLoadImg("fileName_img","img_path");
	}
	//选择图片后将值写入input
	this.changeImgPathValue = function(path)
	{
		$("#img_path").val(path);
	}	

	//将图片值写入选项中
	this.showImageToItem = function()
	{		
		var img_path = $("#img_path").val().trim();
		var img_width = $("#img_width").val();
		var img_height = $("#img_height").val();
		if(img_path != "")
		{
			var img_obj = this.current_designer_obj.find("ul li:nth-child("+(this.current_item_num+1)+") img");
			if(img_obj.is("img"))
			{
				img_obj.attr("src",img_path);
				img_obj.attr("width",img_width);
				img_obj.attr("height",img_height);
			}
			else
			{
				this.current_designer_obj.find("ul li:nth-child("+(this.current_item_num+1)+")").prepend('<div id="item_img_div"><img onmouseover="showImgDescribe(this)" onmouseout="closeImgDescribe()" src="'+img_path+'" width="'+img_width+'px" height="'+img_height+'px"/><div id="item_img_describe"></div></div>');
				//this.current_designer_obj.find("ul li:nth-child("+(this.current_item_num+1)+")").prepend('<div id="item_img_div"><img src="'+img_path+'" width="'+img_width+'px" height="'+img_height+'px"/><div style="display:none" id="item_img_describe"></div></div>');  
				this.current_designer_obj.find("ul li:nth-child("+(this.current_item_num+1)+") div").addClass("item_div_img");
			}
			this.default_img_width = img_width;
			this.default_img_height = img_height;
		}
		else
		{
			this.current_designer_obj.find("ul li:nth-child("+(this.current_item_num+1)+") img").remove();
			this.current_designer_obj.find("ul li:nth-child("+(this.current_item_num+1)+") div").removeClass("item_div_img");
		}
		
		this.current_designer_obj.find("ul li:nth-child("+(this.current_item_num+1)+")").find("#item_img_describe").html($("#img_desc_textarea").val());
		this.closeImgDialog();
		
	}
	//关闭图片窗口
	this.closeImgDialog = function()
	{		
		$("#reset_input").click();//模拟清空操作
		$('#img_dialog').dialog('close');
	}

	//上传图片
	this.uploadImg = function()
	{
		if($("#fileName").val() != "")
		{
			if(!checkImgFile($("#fileName").val()))
			{
				return; 
			}
			$("#upoloadform").submit();
		}
	}

	this.showImgSelect();

	//关闭图片窗口
	this.closeCommonDialog = function()
	{		
		//$("#reset_input").click();//模拟清空操作
		$('#common_dialog').dialog('close');
	}
		

	/*---------------------------------------选择图片　结束---------------------*/

	/*---------------------------------------常用选项　开始---------------------*/
	this.showCommonItem_dialog = function()
	{		
		var str = '<div id="common_dialog" title="添加常用选项" style="width:500px;height:350px;background:#ECF4FC;">';			
			 str += '<table id="" border="0" cellpadding="0" cellspacing="0" width="98%" style="margin-top:20px">';
			  str += '<tr>';
			   str += '<td height="25px" width="28px"></td>';
			   str += '<td colspan="2">请点击左边的按钮选择选项，也可以直接在文本框里修改选项<td>';		 
			  str += '</tr>';
			  str += '<tr>';
			   str += '<td height="25px" width="28px"></td>';	
			   str += '<td valign="top" width="120px">';
			    str += '<div class="common_button_frame_div">';
			     str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'shifou\')">&nbsp;是　　否</span></a></div>';
				 str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'myd\')">&nbsp;满 意 度</span></a></div>';
			     str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'rtd\')">&nbsp;认 同 度</span></a></div>';
			     str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'fhd\')">&nbsp;符 合 度</span></a></div>';
			     str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'zyd\')">&nbsp;重 要 度</span></a></div>';
				 str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'yyd\')">&nbsp;愿 意 度</span></a></div>';
				 str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'zcd\')">&nbsp;支 持 度</span></a></div>';
				 str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'ljd\')">&nbsp;了 解 度</span></a></div>';
				 str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'xq\')">&nbsp;星　　期</span></a></div>';
			     str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'yf\')">&nbsp;月　　份</span></a></div>';
			     str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'sj\')">&nbsp;时　　间</span></a></div>';
			     str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'gzsj\')">&nbsp;工作时间</span></a></div>';
				 str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'pl\')">&nbsp;频　　率</span></a></div>';
				 str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'shif\')">&nbsp;是　　否</span></a></div>';
			     str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'sz\')">&nbsp;数　　字</span></a></div>';
			     str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'dwxz\')">&nbsp;单位性质</span></a></div>';
			     str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'xb\')">&nbsp;性　　别</span></a></div>';
				 str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'hyzk\')">&nbsp;婚姻状况</span></a></div>';
				 str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'sf\')">&nbsp;省　　份</span></a></div>';
			     str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'cs\')">&nbsp;城　　市</span></a></div>';
			     str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'nnd\')">&nbsp;年 龄 段</span></a></div>';
			     str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'xl\')">&nbsp;学　　历</span></a></div>';
				 str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'gznx\')">&nbsp;工作年限</span></a></div>';
				 str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'ysr\')">&nbsp;月 收 入</span></a></div>';
			     str += '<div class="common_button_div"><a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton" onclick="ObjectPool['+this.name+'].selectCommonValue(\'nsr\')">&nbsp;年 收 入</span></a></div>';

			    str += '</div>';
			   str += '</td>';
			   str += '<td><textarea id="common_textarea" name="common_textarea" style="width:250px;height:220px"></textarea></td>';
			  str += '</tr>';
			 str += '</table>';
			 str += '<br />';
			 str += '<table border="0" cellpadding="0" cellspacing="0" width="98%" class="content_lab_table" align="center">';
			  str += '<tr>';
			   str += '<td class="b_bottom_l"></td>';
			   str += '<td class="b_bottom_c" align="center">';
				str += '<div style="width:100px;height:24px">';
				 str += '<a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span onclick="ObjectPool['+this.name+'].getCommonValueToItem()">&nbsp;确定</span></a>';
				 str += '<a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span onclick="ObjectPool['+this.name+'].closeCommonDialog()">&nbsp;关闭</span></a>';
				 str += '<input id="reset_input" type="reset" style="display:none"/>';
				str += '</div>';	 
			   str += '</td>';
			  str += '</tr>';
			 str += '</table>';			
		   str += '</div>';

		   $("body").append(str);
		   $(function(){
				$("#common_dialog").dialog({
					resizable: false,
					width:430,
					height:350,
					modal: true,
					title:'编辑'
				});
				$("#common_dialog").dialog("close");
			});
		   
	}

	//关闭图片窗口
	this.closeCommonDialog = function()
	{	
		$('#common_dialog').dialog('close');
	}

	this.selectCommonValue = function(vals)
	{
		var typeArr = [];		
		typeArr["shifou"] = ["是","否"];
		typeArr["myd"] = ["很不满意","不满意","一般","满意","很满意"];
		typeArr["rtd"] = ["很不同意","不同意","一般","同意","很同意"];
		typeArr["fhd"] = ["很不符合","不符合","一般","符合","很符合"];
		typeArr["zyd"] = ["很不重要","不重要","一般","重要","很重要"];
		typeArr["yyd"] = ["很不愿意","不愿意","一般","愿意","很愿意"];
		typeArr["zcd"] = ["反对","无所谓","支持"];
		typeArr["ljd"] = ["不了解","大概知道，但是认识比较模糊","了解","比较了解","完全了解"];
		typeArr["xq"] = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
		typeArr["yf"] = ["1月份","2月份","3月份","4月份","5月份","6月份","7月份","8月份","9月份","10月份","11月份","12月份"];
		typeArr["sj"] = ["上午","中午","下午","晚上"];
		typeArr["gzsj"] = ["8小时以下","8-10小时","10-12小时","12-14小时","14小时以上","不固定"];
		typeArr["pl"] = ["每天","每周3～4次","每周一次","每月2～3次","每月一次","不定期"];
		typeArr["shif"] = ["是","否"];
		typeArr["sz"] = ["1","2","3","4","5"];
		typeArr["dwxz"] = ["政府机关","事业单位","国营企业","外资企业","民营企业","个体企业","无工作"];
		typeArr["xb"] = ["男","女"];
		typeArr["hyzk"] = ["未婚","已婚"];
		typeArr["sf"] = ["北京","安徽","重庆","福建","甘肃","广东","广西","贵州","海南","河北","黑龙江","河南","香港","湖北","湖南","江苏","江西","吉林","辽宁","澳门","内蒙古","宁夏","青海","山东","上海","山西","陕西","四川","台湾","天津","新疆","西藏","云南","浙江","海外"];
		typeArr["cs"] = ["北京","上海","重庆","香港","杭州","武汉","长沙","广州","深圳","南宁","贵阳","海口","石家庄","哈尔滨","郑州","福州","兰州","南京","南昌","长春","沈阳","呼和浩特","银川","西宁","济南","太原","合肥","西安","成都","天津","乌鲁木齐","拉萨","昆明","澳门","台湾","海外","其他"];
		typeArr["nnd"] = ["16岁以下","17~20","21~25","26~30","31~40","41~50","51~60","60以上"];
		typeArr["xl"] = ["小学以下","初中","高中","中专","大专","大学本科","硕士研究生","博士研究生","博士以上"];
		typeArr["gznx"] = ["1年以下","1~2年","2~3年","3~5年","5~10年","10~20年","20年以上"];
		typeArr["ysr"] = ["还没有收入","2000以下","2000-3000","3001-5000","5001-8000","8001-15000","15001-50000","50000以上"];
		typeArr["nsr"] = ["还没有收入","1万以下","1-3万","3-5万","5-8万","8-15万","15-30万","30-100万","100万以上"];
		
		$("#common_textarea").val(typeArr[vals].toString().replace(/,/g,"\n"));
	}
		
	this.getCommonValueToItem = function()
	{
		var vals = $("#common_textarea").val();
		if(vals != "")
			this.bathGetItemValue(vals);
		this.closeCommonDialog();
	}

	

	
	this.showCommonItem_dialog();
		
	
	/*---------------------------------------常用选项　结束---------------------*/
	
}

//得到上传图片地址后插入信息
function returnUploadValue(furl)
{
	if(furl != "" && furl != null)
	{
		$("#img_path").val(furl);		
	}
	else
		parent.alertWar("上传失败，请重新提交");
}
//编辑器代码保存
function subHTMLEditor()
{
	/*
	var oEditor = FCKeditorAPI.GetInstance('contents'); 
	var c = oEditor.GetXHTML(true);*/

	$("#"+sd.htmleditor_filed_name).val(getV("contents"));
	$("#"+sd.htmleditor_filed_name).keyup();
	closeHTMLEditor();
}
//关闭编辑器窗口
function closeHTMLEditor()
{
	$('#htmleditor_dialog').dialog('close');
}

function showImgDescribe(obj)
{
	var htmls = $(obj).parent().find("#item_img_describe").html();
	if(htmls.trim() != "")
	{
		$("#show_img_des_div").html(htmls);
	
		if(!document.all){
			var position_mouse = GetAbsoluteLocationEx(obj);
			$("#show_img_des_div").css("left",position_mouse.left+position_mouse.width);
			$("#show_img_des_div").css("top",position_mouse.top);
		}else
		{
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