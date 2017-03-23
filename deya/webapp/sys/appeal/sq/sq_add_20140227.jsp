<%@ page contentType="text/html; charset=utf-8"%>
<%
	String sq_id = request.getParameter("sq_id");
	String top_index = request.getParameter("top_index");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>诉求管理</title>


<link type="text/css" rel="stylesheet" href="../../styles/sq.css" />
<jsp:include page="../../include/include_tools.jsp"/>
<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
<script language="javascript" src="../../js/jquery.uploadify.js"></script>
<script language="javascript" src="../../js/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="js/sqList.js"></script>
<script type="text/javascript">
var sq_id = "<%=sq_id%>";
var top_index = "<%=top_index%>";
var is_admin = SQRPC.isAppealManager(LoginUserBean.user_id); //是否是管理员
var current_dept_id = CpUserRPC.getSQDeptIDbyUserID(LoginUserBean.user_id);//当前用户所在的部门
var defaultBean;
var model_bean;
var opt_ids = ","+jsonrpc.UserLoginRPC.getOptIDSByUserAPPSite(LoginUserBean.user_id,"appeal","")+",";
$(document).ready(function(){
	initButtomStyle();
	init_input();
	init_editer_min("pro_note");
	init_editer_min("sq_content2_r");

	if(sq_id != "" && sq_id != "null" && sq_id != null)
	{		
		defaultBean = SQRPC.getSqBean(sq_id);
		if(defaultBean)
		{
			getCPFormListByModel();//得到扩展字段及其内容
			model_bean = jsonrpc.SQModelRPC.getModelBean(defaultBean.model_id);
			$("#sq_div").autoFill(defaultBean);
			
			getSQAttInfo(sq_id,"0","sq_att_td");//得到诉求附件信息
			updateShowText();
            
			if(defaultBean.sq_reply != "")
			{

			   var DeptBean = jsonrpc.DeptRPC.getDeptBeanByID(defaultBean.do_dept);
			   var str = "</tr>";
				str += '<tr class="trList">';
				str += '<td align="right" style="width:80px;">回复时间:</td>';
				str += '<td style="float:left;text-align:left;">'+defaultBean.over_dtime+'</td>';
				str += "</tr>";
				str += '<td align="right" style="width:80px;">回复部门:</td>';
				str += '<td style="float:left;text-align:left;">'+DeptBean.dept_name+'</td>';
				str += "</tr>";
				str += '<td align="right" style="width:80px;">回复内容:</td>';
				str += '<td style="float:left;text-align:left;">'+defaultBean.sq_reply+'</td>';
				str += "</tr>";
				$("#sq_reply").append(str);
			}else{
			    $("#info_replayContent").hide();
			}
		}			
	}
	//得到该登录人所具有的权限ID，显示操作按钮
	getOptIDSByUser();
	getProcessList();
	iniSQbox();
	iniDoList();	
	initUPLoad();
	//得到相关信件，同一会员所发的事件
	if(defaultBean.me_id != 0)
		getXGXJList();
});

//得到相关信件，同一会员所发的事件
function getXGXJList()
{
	var xg_map = new Map();
	xg_map.put("me_id",defaultBean.me_id);
	xg_map.put("sq_flag","0");
	xg_map.put("start_num", "0");	
	xg_map.put("page_size", "1000");
	xg_map.put("sort_name", "sq_id");
	xg_map.put("sort_type", "desc");
	var xg_list = SQRPC.getSqList(xg_map);
	xg_list = List.toJSList(xg_list);
	if(xg_list != null && xg_list.size() > 0)
	{
		var str = "";
		for(var i=0;i<xg_list.size();i++)
		{
			if(xg_list.get(i).sq_id != defaultBean.sq_id)
			{
				str = "";
				str += '<tr class="trList">';
				str += '<td align="center">'+xg_list.get(i).sq_code+'</td>';
				str += '<td align="left"><a href="javascript:openSQManager('+xg_list.get(i).sq_id+')">'+xg_list.get(i).sq_title2+'</a></td>';
				str += '<td align="center">'+xg_list.get(i).do_dept_name+'</td>';
				str += '<td align="center">'+getSQState(xg_list.get(i).sq_status)+'</td>';
				str += '<td align="center">'+xg_list.get(i).sq_dtime+'</td>';
				str += '</tr>';
				$("#xg_sq_list").append(str);
			}
		}

	}
}

function getCPFormListByModel()
{
	var from_list = jsonrpc.SQModelRPC.getCPFormListByModel(defaultBean.model_id);
	from_list = List.toJSList(from_list);
	if(from_list != null && from_list.size() > 0)
	{
		var str = "";
		for(var i=0;i<from_list.size();i++)
		{
			str += '<tr><th>'+from_list.get(i).field_cname+'：</th><td colspan="5" id="'+from_list.get(i).field_ename+'"></td></tr>';
		}
		$("#sq_title2_info_tr").after(str);
	}

	var sq_custom_list = SQRPC.getSQCustomList(defaultBean.sq_id);//得到扩展字段的内容
	sq_custom_list = List.toJSList(sq_custom_list);
	if(sq_custom_list != null && sq_custom_list.size() > 0)
	{
		for(var i=0;i<sq_custom_list.size();i++)
		{
			$("#"+sq_custom_list.get(i).cu_key).text(sq_custom_list.get(i).cu_value);
		}
	}
}

function updateShowText()
{
	//投诉建议
	try{
		$("#pur_id").text(jsonrpc.PurposeRPC.getPurposeByID(defaultBean.pur_id).pur_name);
	}catch(e)
	{
		$("#pur_id").text("");	
	}
	//内容分类
	if(defaultBean.cat_id == 0)
	{
		$("#cat_id").text("");
	}else
	{
		try{
			$("#cat_id").text(jsonrpc.AppealCategoryRPC.getCategoryBean(defaultBean.cat_id).cat_cname);
		}catch(e)
		{
			$("#cat_id").text("");	
		}
	}
	//地区分类
	try{
		$("#area_id").text(jsonrpc.AreaRPC.getAreaBean(defaultBean.area_id).area_cname);
	}
	catch(e)
	{
		$("#area_id").text("");
	}
	//是否公开（公开意愿）
	 $(":radio[id='is_open_r'][value="+defaultBean.is_open+"]").attr("checked",true);
	 if(defaultBean.is_open == 1)
		 $("#is_open").text("公开");
	 else
		$("#is_open").text("不公开");
	
	if(defaultBean.sq_sex ==1)
	{
		$("#sq_sex").text("男");
	}else
		$("#sq_sex").text("女");

	if(defaultBean.sq_status == 0)
	{
		$("#sq_status_text").text("待处理");
	}
	if(defaultBean.sq_status == 1)
	{
		$("#sq_status_text").text("处理中");
	}
	if(defaultBean.sq_status == 2)
	{
		$("#sq_status_text").text("待审核");
	}
	if(defaultBean.sq_status == 3)
	{
		$("#sq_status_text").text("已办结");
	}
	$(":radio[id='publish_status'][value="+defaultBean.publish_status+"]").attr("checked",true);
	if(defaultBean.publish_status == 0)
	{
		$("#publish_status_text").text("未发布");
	}else
	{
		$("#publish_status_text").text("发布");
	}

	$("#model_id").text(model_bean.model_cname);
	$("#weight").val(defaultBean.weight);
}

//得到该登录人所具有的权限ID，显示操作按钮
function getOptIDSByUser()
{
	var opt_ids = jsonrpc.UserLoginRPC.getOptIDSByUserAPPSite(LoginUserBean.user_id,"appeal","");
	if(opt_ids != "" && opt_ids != null)
	{
		var tempA = opt_ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			$("#btn"+tempA[i]).show();
		}
	}
}

//得到流程列表
function getProcessList()
{
	var pro_ids = "";
	var pl = SQRPC.getProcessListBySqID(sq_id);
	pl = List.toJSList(pl);
	var str = "";
	if(pl != null && pl.size() > 0)
	{
		for(var i=0;i<pl.size();i++)
		{
			str += '<tr class="trList">';
			str += '<td align="center">'+(i+1)+'</td>';
			str += '<td align="left">'+pl.get(i).dept_name+'</td>';
			str += '<td align="center">'+getProyTypeName(pl.get(i).pro_type)+'</td>';
			str += '<td align="center">'+pl.get(i).to_dept_name+'</td>';
			str += '<td align="center">'+pl.get(i).pro_dtime+'</td>';
			str += '<td align="center">'+pl.get(i).user_realname+'</td>';
			str += '<td align="left"><a class="openContent" href="javascript:void(0)">展开详细</a></td></tr>';
			str += '<tr class="trContent hidden">';
			str += '<td colspan="6">';
			str += '<table class="table_noborder" border="0" cellpadding="0" cellspacing="0">';
			str += '<tr>';
			str += '<td width="80" align="right">处理内容:</td>';
			str += '<td><div id="pro_node_'+pl.get(i).pro_id+'">'+pl.get(i).pro_note.replace(/\n/,"</br>")+'</div>';
			//str += '<div><input id="" name="btn2" class="" type="button" onclick="openUpdateProcessPage('+pl.get(i).pro_id+')" value="编辑" /></div>';
			str += '</td>';
			str += '</tr>';
			str += '<tr>';
			str += '<td width="80" align="right">附件:</td>';
			str += '<td id="pro_attr_td_'+pl.get(i).pro_id+'">';			
			str += '</td>';
			str += '</tr>';
			str += '</table>';
			str += '</td>';
			str += '</tr>';

			pro_ids += ","+pl.get(i).pro_id;
		}
		$("#process_list").append(str);

		pro_ids = pro_ids.substring(1);
		getSQAttInfo(pro_ids,"1","pro_attr_td");//得到诉求附件信息
	}
}

//打开审核内容修改窗口
function openUpdateProcessPage(pro_id)
{
	OpenModalWindow("内容编辑","/sys/appeal/sq/update_process.jsp?pro_id="+pro_id+"&top_index="+curTabIndex,650,340);
}

function getProNoteValue(pro_id)
{
	return $("#pro_node_"+pro_id).html();
}

function setProNoteValue(pro_id,content)
{
	$("#pro_node_"+pro_id).html(content);
}

function getProyTypeName(pro_type)
{
	var str = "";
	switch(pro_type)
	{
		case 0:str = "受理";break;
		case 1:str = "回复";break;
		case 2:str = "转办";break;
		case 3:str = "交办";break;
		case 4:str = "呈办";break;
		case 5:str = "重复件";break;
		case 6:str = "无效件";break;
		case 7:str = "不予受理";break;
		case 8:str = "申请延时";break;
		case 9:str = "延时通过";break;
		case 10:str = "延时打回";break;
		case 11:str = "审核通过";break;
		case 12:str = "审核打回";break;
		case 13:str = "督办";break;
	}
	return str;
}

function iniSQbox()
{
	$(".sq_title_box").bind('click',function(){	
		if($(this).find(".sq_title_right").text()=="点击闭合")
		{
			$(this).find(".sq_title").removeClass("sq_title_minus").addClass("sq_title_plus");
			$(this).find(".sq_title_right").text("点击展开");
			$(this).parent().find(".sq_box_content").hide(300);
		}
		else
		{
			$(this).find(".sq_title").removeClass("sq_title_plus").addClass("sq_title_minus");
			$(this).find(".sq_title_right").text("点击闭合");
			$(this).parent().find(".sq_box_content").show(300);
		}
	})	
}

function iniDoList()
{	
	$(".openAllContent").bind('click',function(){	
		if($(this).text()=="处理内容:全部展开")
		{
			$(this).text("处理内容:全部闭合");
			$(".trContent").show(300);
			$(".openContent").text("闭合详细");
		}
		else
		{
			$(this).text("处理内容:全部展开");
			$(".trContent").hide(300);
			$(".openContent").text("展开详细");
		}	
	})
	
	$(".openContent").bind('click',function(){	
		var tmpObj = $(this).parent().parent().next(".trContent");
		if($(this).text()=="展开详细")
		{
			$(this).text("闭合详细");
			$(tmpObj).show(300);
		}
		else
		{
			$(this).text("展开详细");
			$(tmpObj).hide(300);
		}
	})
}


function doVoid(id,pro_type)
{
	$(".sq_box_do").hide();
	$("#"+id).show(5,function(){$(document).scrollTop($(document.body).height());});
	//alert($(document.body).height());
	$("#publish_status_tr").hide();
    $("#zbdw_tr").hide();
    $("#auto_status_tr").hide();
    $("#limit_data_tr").hide();
    $("#affix_tr").hide();	
    $("#supervise_flag_tr").hide();	
	$("#sq_title2_tr").hide();	
    $("#sq_content2_tr").hide();	
	$("#quick_content").show();	
	$("#gzyy_open_tr").hide();	
	$("#weight_tr").hide();
	//取得常用语信息
	getCommonLangListByType(pro_type);
	switch(pro_type)
	{
		case 0:$("#pro_title_th").text("受理告知：");				
			   $("div #sq_title_div").text("受理信件");			 
			   $("#affix_tr").show();
				break;
		case 1:$("#pro_title_th").text("回复内容：");				
			　 $("div #sq_title_div").text("回复信件");
			   if(opt_ids.indexOf(",175,") > -1)
				 $("#publish_status_tr").show();
			   if(opt_ids.indexOf(",178,") > -1)
				 $("#gzyy_open_tr").show();
			   KE.html("pro_note", defaultBean.sq_reply);
			   $("#affix_tr").show();
				break;
		case 2:$("#pro_title_th").text("转办意见：");				
			　 $("div #sq_title_div").text("转办信件");	
			   $("#zbdw_th").text("转办单位：");
			   $("#zbdw_tr").show();			  
			   $("#affix_tr").show();
			   getDisposeDeptList("zb");//得到转办部门
				break;
		case 3:$("#pro_title_th").text("交办要求：");				
			　 $("div #sq_title_div").text("交办信件");
			　 $("#zbdw_th").text("交办单位：");
			   $("#zbdw_tr").show();			  
			   $("#affix_tr").show();
			   getDisposeDeptList("jb");//得到交办部门
				break;
		case 4:$("#pro_title_th").text("呈办理由：");				
			　 $("div #sq_title_div").text("呈办信件");
			   $("#zbdw_th").text("呈办单位");
			   $("#zbdw_tr").show();			   
			   $("#affix_tr").show();	
			   getDisposeDeptList("cb");//得到呈办部门
			   break;
		case 7:$("#pro_title_th").text("不受理原因：");				
			　 $("div #sq_title_div").text("不予受理");			  
			   $("#affix_tr").show();			   
				break;
		case 100:$("#pro_title_th").text("审核意见：");				
			　 $("div #sq_title_div").text("内容审核");
			   $("#auto_status_tr").show();		
			   if(opt_ids.indexOf(",175,") > -1)
				 $("#publish_status_tr").show();
			   if(opt_ids.indexOf(",178,") > -1)
				 $("#gzyy_open_tr").show();
				$(":radio[id=auto_status]").eq(0).val(11);
				$(":radio[id=auto_status]").eq(1).val(12);
				$(":radio[id=auto_status]").eq(0).click();
				break;
		case 8:$("#pro_title_th").text("延期理由：");				
			　 $("div #sq_title_div").text("申请延期");
			   $("#affix_tr").show();			  
				break;
		case 101:$("#pro_title_th").text("审核意见：");				
			　 $("div #sq_title_div").text("延期审核");			  
			   $("#auto_status_tr").show();
			   $("#limit_data_tr").show();			   
				$(":radio[id=auto_status]").eq(0).val(9);
				$(":radio[id=auto_status]").eq(1).val(10);
				$(":radio[id=auto_status]").eq(0).click();
				break;
		case 13:$("#pro_title_th").text("督办意见：");				
			　 $("div #sq_title_div").text("信件督办");
			   if(defaultBean.supervise_flag == 1)
					$(":radio[id=supervise_flag]").eq(0).click();
			   $("#affix_tr").show();	
			   $("#supervise_flag_tr").show();					
				break;
		case 102:$("#pro_title_th").text("回复内容：");				
			　 $("div #sq_title_div").text("信件发布");
			   if(opt_ids.indexOf(",175,") > -1)
				$("#publish_status_tr").show();
			   $("#sq_title2_tr").show();	
			   $("#sq_content2_tr").show();	
			   $("#quick_content").hide();
			   $("#gzyy_open_tr").show();
			   KE.html("sq_content2_r", defaultBean.sq_content2);
			   KE.html("pro_note", defaultBean.sq_reply);
			   $(":radio[id='publish_status'][value="+defaultBean.publish_status+"]").attr("checked",true);
			   $("#sq_title2_r").val(defaultBean.sq_title2);
			   $("#weight_tr").show();
				break;
		default:break;
	}

	$("#submitButton").unbind("click").click(function(){
		insertProcess(pro_type);
	});
	init_input();
}

//得到转办部门
function getDisposeDeptList(dis_name)
{	
	var dept_list;
	if(dis_name == "zb")//转办
		dept_list= CpUserRPC.getBrotherDeptListByUserID(LoginUserBean.user_id);
	if(dis_name == "jb")//交办
		dept_list= CpUserRPC.getChildDeptListByUserID(LoginUserBean.user_id);
	if(dis_name == "cb")//呈办
		dept_list= CpUserRPC.getParentDeptListByUserID(LoginUserBean.user_id);

	dept_list = List.toJSList(dept_list);
	var str = "";
	if(dept_list != null && dept_list.size() > 0)
	{		
		for(var i=0;i<dept_list.size();i++)
		{
			if(current_dept_id != dept_list.get(i).dept_id)
				str += '<li style="width:120px;overflow:hidden;display:block;height:20px;padding-right:5px" title="'+dept_list.get(i).dept_name+'"><input type="radio" id="do_dept" name="do_dept" value="'+dept_list.get(i).dept_id+'"><label>'+dept_list.get(i).dept_name+'</label></li>';
		}		
	}
	$("#dept_list").html(str);
}

//信息判重
function isReduplicate()
{
	addTab(true,"/sys/appeal/sq/sq_panchong.jsp?top_index="+curTabIndex,"信件判重");
}

//置为无效件
function setWuxiao()
{		
	msgConfirm("确定要将此信件置为无效？","insertProcess(6)");
}

//信件编辑
function updateSQ()
{		
	addTab(true,"/sys/appeal/sq/sq_update.jsp?sq_id="+sq_id+"&top_index="+curTabIndex,"信件编辑");
}

function openPrintPage()
{
	window.open("print.jsp?st=m&sq_id="+sq_id+"&model_id="+defaultBean.model_id);
}
function creatWord()
{
   var url="saveprint.jsp?st=m&sq_id="+sq_id+"&model_id="+defaultBean.model_id;  
   window.location.href=url;
}
  

</script>
</head>

<body>
<div id="sq_div">
<span class="blank6"></span>
<!--来信人信息-->
<div class="sq_box">
	<div class="sq_title_box" >
		<div class="sq_title sq_title_minus">来信人信息</div>
		<div class="sq_title_right">点击闭合</div>
	</div>
	<div class="sq_box_content">
	<table id="" class="table_view" border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
				<th>姓名：</th>
				<td id="sq_realname"></td>
				<th>性别：</th>
				<td id="sq_sex"></td>
				<th>身份证：</th>
				<td id="sq_card_id"></td>
				<th>手机：</th>
				<td id="sq_phone"></td>
			</tr>
			<tr>
				<th>职业：</th>
				<td id="sq_vocation"></td>
				<th>年龄：</th>
				<td id="sq_age"></td>
				<th>邮箱：</th>
				<td id="sq_email"></td>
				<th>固话：</th>
				<td id="sq_tel"></td>
			</tr>
			<tr>
				<th>住址：</th>
				<td colspan="7"  id="sq_address">
					
				</td>
			</tr>
		</tbody>
	</table>
	</div>
</div>


<span class="blank6"></span>
<!--信件信息-->
<div class="sq_box">
	<div class="sq_title_box" >
		<div class="sq_title sq_title_minus">信件信息</div>
		<div class="sq_title_right">点击闭合</div>
	</div>
	<div class="sq_box_content">
	<table id="sq_content_table" class="table_view" border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
				<th>处理状态：</th>
				<td id="sq_status_text"></td>
				<th>信件编码：</th>
				<td id="sq_code"></td>
				<th>查询密码：</th>
				<td id="query_code">
					
				</td>
			</tr>
			<tr>
				<th>收信单位：</th>
				<td id="submit_name"></td>
				<th>来信时间：</th>
				<td id="sq_dtime"></td>
				<th>信件ID：</th>
				<td id="sq_id"></td>
			</tr>
			<tr>
				<th>诉求目的：</th>
				<td id="pur_id"></td>
				<th>发生地区：</th>
				<td id="area_id"></td>
				<th>内容分类：</th>
				<td id="cat_id"></td>
			</tr>
			<tr>
				<th>递交渠道：</th>
				<td id="model_id"></td>
				<th>发布状态：</th>
				<td id="publish_status_text">
					
				</td>
				<th>公开意愿：</th>
				<td id="is_open">
					公开
				</td>
			</tr>
			<tr id="sq_title2_info_tr">
				<th>标题：</th>
				<td colspan="5" id="sq_title2"></td>
			</tr>
		</tbody>
	</table>
	<table id="" class="table_view" border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
				<th style="vertical-align:top;">内容：</th>
				<td style="line-height:20px;font-size:14px" id="sq_content2"></td>
			</tr>
			<tr>
				<th>附件：</th>
				<td id="sq_att_td">
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<input id="btn176" name="btn1" class="hidden" type="button" onclick="isReduplicate()" value="信件判重" />
					<input id="btn177" name="btn2" class="hidden" type="button" onclick="setWuxiao();" value="置为无效" />
					<input id="btn178" name="btn3" class="hidden" type="button" onclick="updateSQ()" value="信件编辑" />
					<input id="btn408" name="btn3" type="button" onclick="creatWord()" value="生成Word" />
					<input id="btn180" name="btn3" class="hidden" type="button" onclick="openPrintPage()" value="信件打印" />
				</td>
			</tr>
		</tbody>
	</table>
	</div>
</div>
</div>

<span class="blank6"></span>
<!--相关信件-->
<div class="sq_box">
	<div class="sq_title_box" >
		<div class="sq_title sq_title_minus">相关信件</div>
		<div class="sq_title_right">点击闭合</div>
	</div>
	<div class="sq_box_content">
<table id="infoList" class="table_dolist" border="0" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<td width="100" align="center">信件编码</td>
			<td align="left" width="100">信件标题</td>
			<td width="100" align="center">处理部门</td>
			<td width="100" align="center">信件状态</td>
			<td width="120" align="center">来件时间</td>			
		</tr>
	</thead>
	<tbody id="xg_sq_list">
		
	</tbody>
</table>
	</div>
</div>

<span class="blank6"></span>
<!--处理记录-->
<div class="sq_box">
	<div class="sq_title_box" >
		<div class="sq_title sq_title_minus">处理记录</div>
		<div class="sq_title_right">点击闭合</div>
	</div>
	<div class="sq_box_content">
		<table id="infoList" class="table_dolist" border="0" cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td width="25" align="center">步聚</td>
					<td align="left" width="100">处理部门</td>
					<td width="100" align="center">处理方式</td>
					<td width="100" align="center">移交部门</td>
					<td width="120" align="center">处理时间</td>
					<td width="120" align="center">处理人</td>
					<td align="left"><a class="openAllContent" href="javascript:void(0)">处理内容:全部展开</a></td>
				</tr>
			</thead>
			<tbody id="process_list">
				
			</tbody>
		</table>
	</div>
</div>

<span class="blank6"></span>

<!--回复结果-->
<div class="sq_box" id="info_replayContent">
	<div class="sq_title_box" >
		<div class="sq_title sq_title_minus">回复内容</div>
		<div class="sq_title_right">点击闭合</div>
	</div>
	<div class="sq_box_content">
		<table id="sq_reply" class="table_dolist" border="0" cellpadding="0" cellspacing="0"> 
		</table>
	</div>
</div>
<span class="blank6"></span>
<!--操作按扭区-->
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:10px;">
			<input id="btn165" name="btn1" class="hidden" type="button" onclick="doVoid('do_0',0)" value="受理" />
			<input id="btn166" name="btn2" class="hidden" type="button" onclick="doVoid('do_0',1);" value="回复" />
			<input id="btn167" name="btn3" class="hidden" type="button" onclick="doVoid('do_0',2);" value="转办" />
			<input id="btn168" name="btn4" class="hidden" type="button" onclick="doVoid('do_0',3);" value="交办" />
			<input id="btn169" name="btn5" class="hidden" type="button" onclick="doVoid('do_0',4);" value="呈办" />
			<input id="btn170" name="btn6" class="hidden" type="button" onclick="doVoid('do_0',7);" value="不予受理" />
			<input id="btn171" name="btn6" class="hidden" type="button" onclick="doVoid('do_0',100);" value="内容审核" />			
			<input id="btn172" name="btn6" class="hidden" type="button" onclick="doVoid('do_0',8);" value="申请延期" />
			<input id="btn173" name="btn6" class="hidden" type="button" onclick="doVoid('do_0',101);" value="延期审核" />
			<input id="btn174" name="btn6" class="hidden" type="button" onclick="doVoid('do_0',13);" value="督办" />
			<input id="btn175" name="btn6" class="hidden" type="button" onclick="doVoid('do_0',102);" value="发布" />
		</td>
	</tr>
</table>
<span class="blank6"></span>

<!--受理-->
<div id="do_0" class="sq_box_do hidden">
	<div class="sq_title_box" >
		<div id="sq_title_div" class="sq_title sq_title_minus">受理信件</div>
	</div>
	<div class="sq_box_content">
	<table id="appeal_table" class="table_view" border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr id="auto_status_tr">
				<th>审核状态：</th>
				<td>
					<ul>
					<li><input id="auto_status" name="auto_status" type="radio" checked="true" value="11" onclick="getCommonLangListByType(this.value)"/><label>通过</label></li>
					<li><input id="auto_status" name="auto_status" type="radio" value="12"  onclick="getCommonLangListByType(this.value)"/><label>未通过</label></li>
				</ul>
				</td>
			</tr>
			<tr id="gzyy_open_tr">
				<th>公开意愿：</th>
				<td>
				<ul>
					<li><input id="is_open_r" name="is_open_r" type="radio"  value="1"/><label>公开</label></li>
					<li><input id="is_open_r" name="is_open_r" type="radio" checked="checked"  value="0" /><label>不公开</label></li>
				</ul>
				</td>
			</tr>
			<tr id="publish_status_tr">
				<th>发布状态：</th>
				<td>
				<ul>					
					<li><input id="publish_status" name="publish_status" type="radio" checked="checked"  value="0" /><label>未发布</label></li>
					<li><input id="publish_status" name="publish_status" type="radio"  value="1"/><label>发布</label></li>
				</ul>
				</td>
			</tr>
			<tr id="weight_tr" class="hidden">
				<th>权重：</th>
				<td>
					<input id="weight" name="weight" type="text" style="width:50px;" value="60"  maxlength="2" onblur="checkInputValue('weight',true,2,'权重','checkNumber')"/>默认值：60，取值范围（0-99）
				</td>
			</tr>
			<tr id="zbdw_tr">
				<th id="zbdw_th">转办单位：</th>
				<td>
					<ul id="dept_list">					
				</ul>
				</td>
			</tr>			
			<tr id="supervise_flag_tr">
				<th>督办状态：</th>
				<td>
					<ul>
					<li><input id="supervise_flag" name="supervise_flag" type="radio" value="1"/><label>督办</label></li>
					<li><input id="supervise_flag" name="supervise_flag" type="radio" checked="true" value="0"/><label>未督办</label></li>
				</ul>
				</td>
			</tr>
			<tr id="limit_data_tr">
				<th>延期天数：</th>
				<td>
					<input type="text" id="limit_data" class="width50" value="3" maxlength="3" onblur="checkInputValue('limit_data',true,3,'延期天数','checkInt')">天
				</ul>
				</td>
			</tr>
			<tr id="sq_title2_tr">
				<th>信件标题 ：</th>
				<td><input id="sq_title2_r" name="sq_title2_r" type="text" class="width200" value="" /></td>
			</tr>
			<tr id="sq_content2_tr">
				<th>信件内容 ：</th>
				<td>
					<textarea id="sq_content2_r" name="sq_content2_r" style="width:620px;height:200px"></textarea>
				</td>
			</tr>
			<tr>
				<th id="pro_title_th">受理告知：</th>
				<td>
					<select id="quick_content" style="width:200px;" onchange="setSelectedCommonLang(this.value)" >
					</select>
					<span class="blank3"></span>
					<textarea id="pro_note" name="pro_note" style="width:620px;height:200px"></textarea>
				</td>
			</tr>
			<tr id="affix_tr">
				<th>附件：</th>
				<td>
					<div id="fileQueue"></div>
					<input type="file" name="uploadify" id="uploadify" />
				</td>
			</tr>			
			<tr>
				<th></th>
				<td>
					<input id="submitButton" name="btn1" type="button" onclick="javascript:void(0);" value="提交" />
					<input id="btn2" name="btn2" type="button" onclick="tab_colseOnclick(curTabIndex)" value="取消" />
				</td>
			</tr>			
		</tbody>
	</table>	
	</div>
</div>

<span class="blank12"></span>
</body>
</html>
