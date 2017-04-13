//初始化按钮样式
function initButtomStyle()
{
	$(":submit,:reset,:button").each(function(){	
		var n = parseInt($(this).val().replace(/[^\x00-\xff]/g,"").length/2)+$(this).val().replace(/[\x00-\xff]/g,"").length;

		$(this).addClass("btn");
		$(this).addClass("x"+n);
		$(this).hover(function(){$(this).addClass("btn-over");},function(){$(this).removeClass("btn-over");});
		$(this).mousedown(function(){$(this).addClass("btn-click");$(this).removeClass("btn-over");})
		$(this).mouseup(function(){$(this).addClass("btn-over");$(this).removeClass("btn-click");})
	})
}

//初始化页面内标签
function init_FromTabsStyle()
{	
	$(".fromTabs > li").each(function(){	
		$(this).hover(
		  function () {
			$(this).addClass("list_tab_over");
		  },
		  function () {
			$(this).removeClass("list_tab_over");
		  }
		);
		
		$(this).click(
		  function () {
			$(".fromTabs > li").removeClass("list_tab_cur");
			$(this).addClass("list_tab_cur");
			$(".infoListTable").addClass("hidden");			
			$("#listTable_"+$(this).index()).removeClass("hidden");
		  }
		);
	})	
}

function init_input()
{
	$(":text").addClass("input_text");
	$(":text").blur( function () { $(this).removeClass("input_text_focus"); } );
	$(":text").focus( function () { $(this).addClass("input_text_focus"); } );
	
	$(":password").addClass("input_text");
	$(":password").blur( function () { $(this).removeClass("input_text_focus"); } );
	$(":password").focus( function () { $(this).addClass("input_text_focus"); } );
	
	$(":checkbox").addClass("input_checkbox");
	$("textarea").addClass("input_textarea");
	$("textarea").blur( function () { $(this).removeClass("input_textarea_focus"); } );
	$("textarea").focus( function () { $(this).addClass("input_textarea_focus"); } );
	
	$(":radio").addClass("input_radio");
	
	$("select").addClass("input_select");

	$("label").click(function(){		
		if($(this).prev("input").is(':checked'))
			$(this).prev("input").removeAttr("checked");
		else
			$(this).prev("input").attr("checked",true);
	});
}

//得到infoList内所有选中的input复选框的ID串，如”1,2,3,4“
function getInputBoxSelected(tableName)
{
	var ids = "";
	$("#"+tableName+" tbody .inputHeadCol:checked").each(function(){	
		ids += $(this).attr("id")+",";
	})
	ids = ids.substring(0,ids.length-1);	
}


function clickInputCheckBox(tableName)
{
	//alert("=="+tableName);
	if($("#"+tableName+" tbody .inputHeadCol:checked").length == $("#"+tableName+" tbody .inputHeadCol:checkbox").length)
	{
		//$("#selectAll").attr("checked",true);
		$("#"+tableName+" thead :checkbox").attr("checked",true);
		
	}
	else
	{
		//$("#selectAll").attr("checked",false);
		$("#"+tableName+" thead :checkbox").attr("checked",false);
	}
}

function Init_InfoTable(tableName)
{
	$("#"+tableName+".odd_even_list tbody tr:odd").removeClass().addClass("tr_odd");
    $("#"+tableName+".odd_even_list tbody tr:even").removeClass().addClass("tr_even");
	
	$("#"+tableName+" tbody tr").each(function(){
		var $_tr=$(this);
		//alterRowColor($_tr);
		$(this).hover(
		  function () {
			$(this).addClass("tr_hover");
		  },
		  function () {
			$(this).removeClass("tr_hover");
		  }
		);
		
		if($(this).find(".inputHeadCol:checkbox").is(':checked'))
		{
			$(this).addClass("tr_selected");
		}

		$(this).click( 
			function (event) {
				if(!(("INPUT,A,SPAN,IMG").indexOf($(event.target)[0].tagName.toUpperCase())>-1))
				{
					$(this).find(".inputHeadCol:checkbox").attr("checked",!($(this).find(".inputHeadCol:checkbox").is(':checked')));
					
				}
				
				if($(this).find(".inputHeadCol:checkbox").is(':checked'))
				{
					$(this).addClass("tr_selected");
				}
				else
				{
					$(this).removeClass("tr_selected");
				}
				clickInputCheckBox(tableName);
			}
		);

	})
	

	$("#"+tableName+" tbody .inputHeadCol:checkbox").each(function(){	
		$(this).click( 
			function () { 
				clickInputCheckBox(tableName);
			}
		);
	})
	
	$("#"+tableName+" thead :checkbox").click(
		function () { 
			if($(this).is(':checked')){
				//alert($("#"+tableName+" tbody .inputHeadCol").txt());
				$("#"+tableName+" tbody .inputHeadCol:checkbox").attr("checked",true);
				$("#"+tableName+" tbody tr").addClass("tr_selected");
			}
			else
			{
				$("#"+tableName+" tbody .inputHeadCol:checkbox").attr("checked",false);
				$("#"+tableName+" tbody tr").removeClass("tr_selected");
			}
		}				  
	);
}

function setLeftTreeHeight()
{
	$("#leftMenu").css("height",(parseInt($("#leftMenu").css("height"))+27)+"px");
}

/*公共选择框事件
@param table_obj  table对象
@param value_name 要提取的ID属性名称
@param handl_name 成功后要执行的函数名
*/
function publicSelectCheckbox(table_obj,value_name,handl_name)
{
	var selectIDS = table_obj.getSelecteCheckboxValue(value_name);
	
	if(selectIDS == "" || selectIDS == null)
	{
		try{
            msgWargin(WCMLang.Select_empty);
            return false;
		}catch (e){
            parent.msgWargin(WCMLang.Select_empty);
            return false;
		}
	}else
	{
		eval(handl_name);
	}
}

/*公共选择框事件
@param table_obj  table对象
@param value_name 要提取的ID属性名称
@param handl_name 成功后要执行的函数名
*/
function publicSelectSinglCheckbox(table_obj,value_name,handl_name)
{
	var selectIDS = table_obj.getSelecteCheckboxValue(value_name);
	
	if(selectIDS == "" || selectIDS == null)
	{
        try{
            msgWargin(WCMLang.Select_empty);
            return false;
        }catch (e){
            parent.msgWargin(WCMLang.Select_empty);
            return false;
        }
	}else
	{
		if(selectIDS.split(",").length > 1)
		{
            try{
                msgWargin(WCMLang.Select_singl);
                return false;
            }catch (e){
                parent.msgWargin(WCMLang.Select_singl);
                return false;
            }
		}
		else{
			eval(handl_name);
		}
	}
}

/*删除所调用的方法,
@param table_obj  table对象
@param value_name 要提取的ID属性名称
@param handl_name 成功后要执行的函数名
*/
function deleteRecord(table_obj,value_name,handl_name)
{
	var selectIDS = table_obj.getSelecteCheckboxValue(value_name);
	
	if(selectIDS == "" || selectIDS == null)
	{
        try{
            msgWargin(WCMLang.Select_empty);
            return false;
        }catch (e){
            parent.msgWargin(WCMLang.Select_empty);
            return false;
        }
	}else
	{
        try{
            msgConfirm(WCMLang.Delete_confirm,handl_name);
            return false;
        }catch (e){
            parent.msgConfirm(WCMLang.Delete_confirm,handl_name);
            return false;
        }
	}
}

function deleteSinglRecord(table_obj,value_name,handl_name)
{
	var selectIDS = table_obj.getSelecteCheckboxValue(value_name);
	
	if(selectIDS == "" || selectIDS == null)
	{
        try{
            msgWargin(WCMLang.Select_empty);
            return false;
        }catch (e){
            parent.msgWargin(WCMLang.Select_empty);
            return false;
        }
	}else
	{
		if(selectIDS.split(",").length > 1)
		{
            try{
                msgWargin(WCMLang.Select_singl);
                return false;
            }catch (e){
                parent.msgWargin(WCMLang.Select_singl);
                return false;
            }
		}
        try{
            msgConfirm(WCMLang.Delete_confirm,handl_name);
            return false;
        }catch (e){
            parent.msgConfirm(WCMLang.Delete_confirm,handl_name);
            return false;
        }
	}
}

/*修改所调用的方法,
@param table_obj  table对象
@param value_name 要提取的ID属性名称
@param handl_name 成功后要执行的函数名
*/
function updateRecord(table_obj,value_name,handl_name)
{
	var selectIDS = table_obj.getSelecteCheckboxValue(value_name);
	if(selectIDS == "" || selectIDS == null)
	{
        try{
            msgWargin(WCMLang.Select_empty);
            return false;
        }catch (e){
            parent.msgWargin(WCMLang.Select_empty);
            return false;
        }
	}else
	{
		if(selectIDS.split(",").length > 1)
		{
            try{
                msgWargin(WCMLang.Select_singl);
                return false;
            }catch (e){
                parent.msgWargin(WCMLang.Select_singl);
                return false;
            }
		}
		else{
			eval(handl_name);
		}
	}
}

/*保存排序调用的方法,
@param table_obj  table对象
@param value_name 要提取的ID属性名称
@param handl_name 成功后要执行的函数名
*/
function sortRecord(table_obj,value_name,handl_name)
{
	var selectIDS = table_obj.getAllCheckboxValue(value_name);
	
	if(selectIDS == "" || selectIDS == null)
	{
        try{
            msgWargin(WCMLang.Select_empty);
            return false;
        }catch (e){
            parent.msgWargin(WCMLang.Select_empty);
            return false;
        }
	}else
	{
		eval(handl_name);
	}
}

//排序字符
function getSortColStr()
{
	return '<span onclick="listSortHandl(this,\'up\')" style="cursor:pointer">上移</span>&#160;&#160;<span onclick="listSortHandl(this,\'down\')" style="cursor:pointer">下移</span>';
}

//排序方法
function listSortHandl(obj,sort_type)
{
	var trObj = $(obj).parent().parent();	
	if(sort_type == "up")
	{
		if(trObj.prev().is("tr"))
		{
			trObj.prev().before(trObj);		
			Init_InfoTable(trObj.parent().parent().attr("id"));
		}
	}
	if(sort_type == "down")
	{
		if(trObj.next().is("tr"))
		{
			trObj.next().after(trObj);
			Init_InfoTable(trObj.parent().parent().attr("id"));
		}
	}
}

//标准表单验证方法
function standard_checkInputInfo(table_name)
{
	isFocus = true;
	val.check_result = true;
	$("#"+table_name+" input[type=text]").each(function(){
		$(this).blur();
	});
	$("#"+table_name+" textarea").each(function(){
		$(this).blur();
	});	

	if(!val.getResult()){		
		return false;
	}
	return true;
}

//表单重置
function formReSet(table_name,data_id)
{
	//form1.reset();
	$("#form1")[0].reset();
	if(data_id != "" && data_id != "null" && data_id != null)
	{
		$("#"+table_name).autoFill(defaultBean);
	}
}

//禁用表单
function disabledWidget()
{
	$("input[type!=button]").attr("disabled",true);
	$("select").attr("disabled",true);
	$("textarea").attr("disabled",true);	
}

/*
 *素材库选择窗口
 * @param title 窗口标题显示信息
 * @param handl_name 选择完部门后需要执行的方法名称
 * @param i_type 显示框类型，是chekbox or radio
 */
function openSelectMaterialPage(handl_name,site_id,i_type)
{
	window.showModalDialog("/sys/material/operate/select_material.jsp?i_type="+i_type+"&site_id="+site_id+"&handl_name="+handl_name,window,'dialogWidth:700px;dialogHeight:550px;status:no;center:yes');
}

/*
 *打开部门选择窗口
 * @param title 窗口标题显示信息
 * @param handl_name 选择完部门后需要执行的方法名称
 * @param show_type 显示部门类型,为空根据登录用户的部门管理权限显示部门节点, all显示所有的部门节点
 */
function openSelectSingleDept(title,handl_name,show_type)
{
	try{
        OpenModalWindow(title,"/sys/org/dept/select_single_dept.jsp?handl_name="+handl_name+"&stype="+show_type,450,510);
	}catch (e){
        parent.OpenModalWindow(title,"/sys/org/dept/select_single_dept.jsp?handl_name="+handl_name+"&stype="+show_type,450,510);
	}
}

//打开站点选择窗口
function openSelectSingleSite(title,handl_name)
{
    try{
    	OpenModalWindow(title,"/sys/control/site/select_single_site.jsp?handl_name="+handl_name,450,510);
    }catch (e){
        parent.OpenModalWindow(title,"/sys/control/site/select_single_site.jsp?handl_name="+handl_name,450,510);
    }
}

//打开人员选择窗口
function openSelectUserPage(title,handl_name)
{
    try{
    	OpenModalWindow(title,"/sys/org/dept/select_user.jsp?handl_name="+handl_name,720,515);
    }catch (e){
		parent.OpenModalWindow(title,"/sys/org/dept/select_user.jsp?handl_name="+handl_name,720,515);
    }
}

//打开站点下的用户和用户组选择窗口
function openSelectSiteUserPage(title,handl_name,site_id)
{
    try{
    	OpenModalWindow(title,"/sys/org/siteUser/select_site_user.jsp?app_id="+app_id+"&site_id="+site_id+"&handl_name="+handl_name,520,515);
    }catch (e){
		parent.OpenModalWindow(title,"/sys/org/siteUser/select_site_user.jsp?app_id="+app_id+"&site_id="+site_id+"&handl_name="+handl_name,520,515);
    }
}

//打开站点下的用户
function openSelectOnlySiteUserPage(title,handl_name,site_id)
{
    try{
    	OpenModalWindow(title,"/sys/org/siteUser/select_site_only_user.jsp?site_id="+site_id+"&handl_name="+handl_name,520,505);
    }catch (e){
		parent.OpenModalWindow(title,"/sys/org/siteUser/select_site_only_user.jsp?site_id="+site_id+"&handl_name="+handl_name,520,505);
    }
}


//打开诉求人员选择窗口
function openSelectSQUserPage(title,handl_name)
{
    try{
    	OpenModalWindow(title,"/sys/appeal/cpUser/select_user.jsp?handl_name="+handl_name,720,515);
    }catch (e){
		parents.OpenModalWindow(title,"/sys/appeal/cpUser/select_user.jsp?handl_name="+handl_name,720,515);
    }
}

//打开诉求部门选择窗口
function openSelectCpDeptPage(title,handl_name)
{
    try{
    	OpenModalWindow(title,"/sys/appeal/cpDept/select_cpDept.jsp?handl_name="+handl_name,610,510);
    }catch (e){
		parent.OpenModalWindow(title,"/sys/appeal/cpDept/select_cpDept.jsp?handl_name="+handl_name,610,510);
    }
}

//打开权限选择窗口
function openSelectOperatePage(title,handl_name,update_status)
{
    try{
    	OpenModalWindow(title,"/sys/org/operate/select_operate.jsp?handl_name="+handl_name+"&update_status="+update_status,450,510);
    }catch (e){
		parent.OpenModalWindow(title,"/sys/org/operate/select_operate.jsp?handl_name="+handl_name+"&update_status="+update_status,450,510);
    }
}

/*
 *打开角色选择窗口
 * @param title 窗口标题显示信息
 * @param handl_name 选择完部门后需要执行的方法名称
 * @param app_id 应用系统ID
 * @param site_id 站点ID
 */
function openSelectRolePage(title,handl_name,app_id,site_id)
{
    try{
    	OpenModalWindow(title,"/sys/org/role/select_role.jsp?app_id="+app_id+"&site_id="+site_id+"&handl_name="+handl_name,450,510);
    }catch (e){
		parent.OpenModalWindow(title,"/sys/org/role/select_role.jsp?app_id="+app_id+"&site_id="+site_id+"&handl_name="+handl_name,450,510);
    }
}

/*
 *打开权限管理窗口
 * @param title 窗口标题显示信息
 * @param handl_name 选择完部门后需要执行的方法名称
 * @param app_id 应用系统ID
 * @param site_id 站点ID
 * @param priv_type 权限类型　0为用户　1为组
 */
function openSetOperate(title,handl_name,app_id,site_id,priv_type,u_id)
{
	var user_id;
	if(u_id == null)
	{
		if(priv_type == 0)
			var user_id = table.getSelecteCheckboxValue("user_id");
		if(priv_type == 1)
			var user_id = table.getSelecteCheckboxValue("group_id");
	}else
		user_id = u_id;
    try{
    	OpenModalWindow(title,"/sys/org/siteUser/set_operate.jsp?app_id="+app_id+"&priv_type="+priv_type+"&user_id="+user_id+"&handl_name="+handl_name,550,530);
    }catch (e){
		parent.OpenModalWindow(title,"/sys/org/siteUser/set_operate.jsp?app_id="+app_id+"&priv_type="+priv_type+"&user_id="+user_id+"&handl_name="+handl_name,550,530);
    }
}

//打开目录选择窗口
function openSelectSingleCategory(title,handl_name,site_id)
{
    try{
    	OpenModalWindow(title,"/sys/cms/category/select_single_category.jsp?site_id="+site_id+"&handl_name="+handl_name,450,510);
    }catch (e){
		parent.OpenModalWindow(title,"/sys/cms/category/select_single_category.jsp?site_id="+site_id+"&handl_name="+handl_name,450,510);
    }
}

//打开基础分类法分类方式类目选择窗口
function openSelectSingleCateClass(title,handl_name)
{
    try{
    	OpenModalWindow(title,"/sys/cms/cateClass/select_single_cateClass.jsp?class_type=0&handl_name="+handl_name,450,510);
    }catch (e){
		parent.OpenModalWindow(title,"/sys/cms/cateClass/select_single_cateClass.jsp?class_type=0&handl_name="+handl_name,450,510);
    }
}

//打开共享目录分类方式类目选择窗口
function openSelectSingleShareCateClass(title,handl_name,app_id)
{
    try{
    	OpenModalWindow(title,"/sys/cms/cateClass/select_single_cateClass.jsp?class_type=1&app_id="+app_id+"&handl_name="+handl_name,450,510);
    }catch (e){
		parent.OpenModalWindow(title,"/sys/cms/cateClass/select_single_cateClass.jsp?class_type=1&app_id="+app_id+"&handl_name="+handl_name,450,510);
    }
}

//打开共享目录分类方式类目选择窗口
function openSelectShareCateClass(title,handl_name,app_id)
{
    try{
    	OpenModalWindow(title,"/sys/cms/cateClass/select_cateClass.jsp?app_id="+app_id+"&handl_name="+handl_name,450,500);
    }catch (e){
		parent.OpenModalWindow(title,"/sys/cms/cateClass/select_cateClass.jsp?app_id="+app_id+"&handl_name="+handl_name,450,500);
    }
}

//打开公开节点分类选择窗口
function openSelectGKNodeCategory(title,handl_name,show_tree_type)
{
    try{
    	OpenModalWindow(title,"/sys/zwgk/node/select_node_category.jsp?stt="+show_tree_type+"&handl_name="+handl_name,450,500);
    }catch (e){
    	parent.OpenModalWindow(title,"/sys/zwgk/node/select_node_category.jsp?stt="+show_tree_type+"&handl_name="+handl_name,450,500);
    }
}


//得到所有站点列表
function getAllSiteList()
{	
	var list = jsonrpc.SiteRPC.getSiteList();
	list = List.toJSList(list);
	return list;
}

//得到流程列表
function getWorkFlowList()
{
	var WorkFlowRPC = jsonrpc.WorkFlowRPC;
	var list = WorkFlowRPC.getWorkFlowList();
	list = List.toJSList(list);
	$("#wf_id").addOptions(list,"wf_id","wf_name");
}

/*****************截取时间字符串 开始*****************************/
function cutTimes(times)
{
	if(times != "" && times != null)
	{
		return times.substring(0,10);
	}
	else
		return "";
}
/*****************截取时间字符串 开始*****************************/

//验证是否有状态重复的ID  selectIDS 所选ID值,fields　匹配字段名,flag　匹配字段值
function fnCheckRepeatStatus(selectIDS,fields,flag)
{
	selectIDS = ","+selectIDS+",";
	
	var tempA = selectIDS.split(",");
	for(var i=0;i<tempA.length;i++)
	{
		for(var j=0;j<beanList.size();j++)
		{			  
			if(beanList.get(j).id == tempA[i] && eval("beanList.get("+j+")."+fields) == flag)
			{				
				var reg = "/,"+tempA[i]+",/";		
				selectIDS = selectIDS.replace(eval(reg),",");
			}
		}
	}	

	var tempB = selectIDS.split(",");
	var tempStr = "";
	for(var i=0;i<tempB.length;i++)
	{
		if(tempB[i] != "" && tempB[i] != null)
			tempStr += ","+tempB[i];
	}

	return tempStr.substring(1);
}


//判断图片格式
function checkImgFile(files) 
{ 
	if(files.replace(/(^\s*)|(\s*$)/g,"") != "")
	{    		  
		if(files.indexOf(".") == -1) 
		{ 					
			return false; 
		}
		var strtype = files.match(/\.([^\.]+)(\?|$)/)[1];
		if (strtype.toLowerCase()=="jpg"||strtype.toLowerCase()=="gif"||strtype.toLowerCase()=="bmp"||strtype.toLowerCase()=="png") 
		{ 
			return true; 
		} 
		else
		{ 			
			alertWar("<span style='color:red'>上传的文档图片格式不对，只允许上传 jpg，jpeg，gif，png 等格式的文件，请重新上传！</span>");
			return false;
		}
	}
	return true;
}

/*
 *显示模拟的下拉选项
 * @param input_name 输入框的ID
 * @param div_name 显示div的ID
 * @param height div的高度
 */
function showSelectDiv(input_name,div_name,height,tree_div_name)
{
	$("input[id='"+input_name+"']").addClass("select_input_default");
	$("input[id='"+input_name+"']").hover(function(){			
		$(this).removeClass("select_input_default");
		$(this).addClass("select_input_selected");			
	},function(){
		$(this).removeClass("select_input_selected");
		$(this).addClass("select_input_default");			
	});

	$("input[id='"+input_name+"']").click(function(event){
		$("div.select_div").hide();//先关闭其它的div

		var thePos = $(this).position();
		$("#"+div_name).show();
		$("#"+div_name).css("background","#FFFFFF");
		$("#"+div_name).css("left",thePos.left);
		$("#"+div_name).css("top",thePos.top+19);
		$("#"+div_name).css("width",$(this).width()+3);
		$("#"+div_name).css("height",height+"px");
		if(tree_div_name != null)
			$("#"+tree_div_name).css("height",(height-12)+"px");
		else
			$("#leftMenu").css("height",(height-12)+"px");
		
		event.stopPropagation();
	});

	$("html").click(function(event){		
		//alert(event.currentTarget)
		$("div.select_div").hide();
	});
}

function iniTreeTable(tableID)
{
	 $(".treeTableCSS").treeTable({
		 initialState: "expanded"
    });
    
    // Make visible that a row is clicked
    $("table#"+tableID+" tbody tr").mousedown(function() {
      $("tr.selected").removeClass("selected"); // Deselect currently selected rows
      $(this).addClass("selected");
    });
    
    // Make sure row is selected when span is clicked
    $("table#"+tableID+" tbody tr span").mousedown(function() {
      $($(this).parents("tr")[0]).trigger("mousedown");
    });
}

function getCurrentDateTime()
{
	var today=new Date();
	var y = today.getFullYear();
	//if($.browser.mozilla){
	//   y = y + 1900;
	//}
	var M = today.getMonth()+1;
	var d = today.getDate();

	if(M < 10)
		M = "0"+M;
	if(d < 10)
		d = "0"+d;

	var h=today.getHours(); 
	var m=today.getMinutes(); 
	var s=today.getSeconds(); 


	if(h < 10)
		h = "0"+h;
	if(m < 10)
		m = "0"+m;
	if(s < 10)
		s = "0"+s;

	return y+"-"+M+"-"+d+" "+h+":"+m+":"+s;
}

//是到当前页面sessionid
function getSessionID()
{
	return jsonrpc.UserLoginRPC.getSessionID();
}

//reload turnPage
function reloadTurnPage()
{
	tp.curr_page = 1;	
}

//选择模板功能
var temp_template_id="";
var temp_template_name="";
function openSelectTemplate(template_id,template_name,site_id)
{
	temp_template_id = template_id;	
	temp_template_name = template_name;
	OpenModalWindow("选择模板","/sys/system/template/select_template.jsp?site_id="+site_id+"&handl_name=setSelectTemplate",520,500);
}

function setSelectTemplate(id,name)
{
	$("#"+temp_template_id).val(id);
	$("#"+temp_template_name).val(name);
}

function getTemplateName(t_id)
{
	if(t_id == 0) return "";
	try{
		return jsonrpc.TemplateRPC.getTemplateNameById(t_id,site_id,"");
	}catch(e){
		return "";
	}
}

function getTemplateNameForTSiteID(t_id,t_site_id)
{
	if(t_id == 0) return "";
	try{
		return jsonrpc.TemplateRPC.getTemplateNameById(t_id,t_site_id,"");
	}catch(e){
		return "";
	}
}

function getTemplateNameByReleApp(t_id)
{
	if(t_id == 0) return "";
	var temp_site_id = jsonrpc.SiteRPC.getSiteIDByAppID(app_id);
	try{
		return jsonrpc.TemplateRPC.getTemplateNameById(t_id,temp_site_id,"");
	}catch(e){
		return "";
	}
}

//公用上传图片加载方法
function initUPLoadImg(file_name,text_input)
{
	var sizeLimit = jsonrpc.SQRPC.getAppealFileSize();
	var uploadDomain = jsonrpc.MateInfoRPC.getImgDomain(site_id);
	 $("#"+file_name).uploadify( {//初始化函数
		'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
		'script' :uploadDomain+'/servlet/UploadFileIfy',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
		'folder' :'uploads',//您想将文件保存到的路径
		'queueID' :'fileQueue',//与下面的上传文件列表id对应
		'queueSizeLimit' :1,//上传文件的数量
		//'scriptData' :{'is_press':0,'press_osition':0,'site_id':site_id},//向后台传的数据
		'fileDesc' :'gif,jpg,jpeg,png',//上传文件类型说明
		'fileExt' :'*.gif;*.jpg;*.jpeg;*.png', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
		'method':'get',//如果向后台传输数据，必须是get
		'sizeLimit':sizeLimit,//文件上传的大小限制，单位是字节
		'auto' :true,//是否自动上传
		'multi' :false,
		'simUploadLimit' :1,//同时上传文件的数量
		'buttonText' :'BROWSE',//浏览按钮图片
		'auto' : true,//点击选择直接上传图片
		'onSelect':function(event, queueID, fileObj){//选择完后触发的事件			
			if(fileObj.size > sizeLimit)
			{
				alert("附件过大，不允许上传！");
				return;
			}	
			else
			{
				$("#"+file_name).uploadifySettings('scriptData',{'is_press':0,'press_osition':0,'site_id':site_id,'sid':jsonrpc.MateInfoRPC.getUploadSecretKey()});
			}
		},		
		'onError':function(event,queueID,fileObj,errorObj){
			alert("文件:" +fileObj.name + "上传失败！");	
		},		
		'onComplete': function(event, queueID, fileObj,response,data) {//当上传完成后的回调函数		 
			 var objPath =  jQuery.parseJSON(response);			
			 $("#"+text_input).val(uploadDomain+objPath.att_path+objPath.att_ename);
			 $("#"+text_input).focus();
		}
   });
}

function initUPLoadMedia(file_name,text_input)
{
	var sizeLimit = 524288000;
	var uploadDomain = jsonrpc.MateInfoRPC.getImgDomain(site_id);
	 $("#"+file_name).uploadify( {//初始化函数
		'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
		'script' :uploadDomain+'/servlet/UploadFileIfy',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
		'folder' :'uploads',//您想将文件保存到的路径
		'queueID' :'fileQueue',//与下面的上传文件列表id对应
		'queueSizeLimit' :1,//上传文件的数量
		//'scriptData' :{'is_press':0,'press_osition':0,'site_id':site_id},//向后台传的数据
		'fileDesc' :'asf,avi,mpg,mpeg,mpe,mov,rmvb,wmv,wav,mid,midi,mp3,mpa,mp2,ra,ram,rm,wma,flv',//上传文件类型说明
		'fileExt' :'*.asf;*.avi;*.mpg;*.mpeg;*.mpe;*.mov;*.rmvb;*.wmv;*.wav;*.mid;*.midi;*.mp3;*.mpa;*.mp2;*.ra;*.ram;*.rm;*.wma;*.flv', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
		'method':'get',//如果向后台传输数据，必须是get
		'sizeLimit':sizeLimit,//文件上传的大小限制，单位是字节
		'auto' :true,//是否自动上传
		'multi' :false,
		'simUploadLimit' :1,//同时上传文件的数量
		'buttonText' :'BROWSE',//浏览按钮图片
		'auto' : true,//点击选择直接上传图片
		'onSelect':function(event, queueID, fileObj){//选择完后触发的事件
			if(fileObj.size > sizeLimit)
			{
				alert("附件超过500M，不允许上传！");
				return;
			}	
			else
			{
				$("#"+file_name).uploadifySettings('scriptData',{'is_press':0,'press_osition':0,'site_id':site_id,'sid':jsonrpc.MateInfoRPC.getUploadSecretKey()});
			}
		},		
		'onError':function(event,queueID,fileObj,errorObj){
			alert("文件:" +fileObj.name + "上传失败！");	
		},		
		'onComplete': function(event, queueID, fileObj,response,data) {//当上传完成后的回调函数		 
			 var objPath =  jQuery.parseJSON(response);			
			 $("#"+text_input).val(uploadDomain+objPath.att_path+objPath.att_ename);
		}
   });
}



//判断是否是站点管理员或超级管理员
function isSiteManager(app_id,site_id)
{
	return jsonrpc.UserLoginRPC.isSiteManager(LoginUserBean.user_id,app_id,site_id);
}	

//得到管理员审核流程中最大的步骤ID
function getMaxStepIDByUserID(wf_id,app_id,site_id)
{
	return jsonrpc.WorkFlowRPC.getMaxStepIDByUserID(wf_id,LoginUserBean.user_id,app_id,site_id);
}

//修改缩略图为原图
function changeThumbUrl(input_name)
{
	var old_src = $("#"+input_name).val();
	var uploadDomain = jsonrpc.MateInfoRPC.getImgDomain(site_id);
	if(old_src != "" && old_src != null && old_src.indexOf(uploadDomain) > -1 && old_src.indexOf("_b") == -1)
	{
		var name = old_src.substring(0,old_src.lastIndexOf("."));
		var ex = old_src.substring(old_src.lastIndexOf("."));
		
		//$("#"+input_name).val(name+"_b"+ex);
		$("body").append('<img src="'+name+"_b"+ex+'" alt="" onerror="changeThumbUrlHandl(\''+input_name+'\',\''+old_src+'\')" onload="changeThumbUrlHandl(\''+input_name+'\',\''+name+"_b"+ex+'\')" style="display:none"/>');
	}
}

function changeThumbUrlHandl(input_name,img_src)
{
	$("#"+input_name).val(img_src);
}

//清空已选模板
function clearTemplate(obj)
{
	var p_obj = $(obj).parent();
	$(p_obj).find("input[name]").val("");	
	$(p_obj).find("input[name='template_id_hidden']").val("0");	
}

//js加载之前处理事件
function reloadBefore(){
	waitMsgWindow();
}

//js加载之后处理事件
function reloadAfter(){
	closeWaitMsgWindow();
}


function initUeditor(input_id)
{
    UE.getEditor(input_id);
}

function distoryUeditor(input_id)
{
    UE.getEditor(input_id).destroy();
}

function getV(input_id){
    return UE.getEditor(input_id).getContent();
}
function setV(input_id,v){
    UE.getEditor(input_id).ready(function(){
        UE.getEditor(input_id).setContent(v);//这里给编辑器添加内容
    });
}

function insertV(input_id,v)
{
    var ucontent = getV(input_id);
    setV(input_id,ucontent + v);
}