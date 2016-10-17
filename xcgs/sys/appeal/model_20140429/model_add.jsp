<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String model_id = request.getParameter("model_id");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>业务管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/modelList.js"></script>
<script type="text/javascript">
var model_id = <%=model_id%>;
var defaultBean;
var site_id = jsonrpc.SiteRPC.getSiteIDByAppID("appeal");
$(document).ready(function(){
	initButtomStyle();
	init_input();
	getWorkFlowList();
	
	if(model_id != "" && model_id != "null" && model_id != null)
	{		
		defaultBean = SQModelRPC.getModelBean(model_id);
		if(defaultBean)
		{
			$("#model_table").autoFill(defaultBean);
			showDeptTR(defaultBean.relevance_type);
			if(defaultBean.relevance_type == 0)
				getSelectedDept();
			else
				getCurrentSelectedLeadID();

			$("#template_form_name").val(getTemplateName(defaultBean.template_form));
			$("#template_list_name").val(getTemplateName(defaultBean.template_list));
			$("#template_content_name").val(getTemplateName(defaultBean.template_content));
			$("#template_comment_name").val(getTemplateName(defaultBean.template_comment));
			$("#template_print_name").val(getTemplateName(defaultBean.template_print));
			$("#template_search_list_name").val(getTemplateName(defaultBean.template_search_list));
		}
		$("#addButton").click(updateModel);		
	}else
	{		
		initData();
		$("#addButton").click(insertModel);
	}
	
});

//得到已选过的部门
function getSelectedDept()
{
	var map = SQModelRPC.getModelDeptMapByMID(model_id);	
	map = Map.toJSMap(map);
	saveCPDept(map.keySet(),map.values());
}

//得到该业务下巳有的领导人
function getCurrentSelectedLeadID()
{
	var map = SQModelRPC.getModelLeadMapByMID(model_id);
	map = Map.toJSMap(map);
	saveCPDept(map.keySet(),map.values());
}

//初始加载数据
function initData()
{
	defaultBean = BeanUtil.getCopy(ModelBean);
	$("#model_table").autoFill(defaultBean);
}

//打开选择部门窗口
function openSelectCPDept(title,handl_name)
{
	top.OpenModalWindow(title,"/sys/appeal/cpDept/select_dept.jsp?handl_name="+handl_name,450,510);
}

//打开选择领导人窗口
function openSelectCPLead(title,handl_name)
{
	top.OpenModalWindow(title,"/sys/appeal/cpLead/select_lead.jsp?handl_name="+handl_name,450,510);
}

//获取选择的部门并写入控件
function saveCPDept(ids,names)
{
	$("#dept_ids").val(ids);
	$("#dept_names").val(names);
}

function getSelectedCPDept()
{
	return $("#dept_ids").val();
}

var rele_type_name = "关联部门";
function showDeptTR(flag)
{
	if(flag == 0)
	{
		$("#rele_span").text("关联部门");
		$("#rele_btn").val("选择部门");		
		rele_type_name = "关联部门";
	}
	else{
		$("#rele_span").text("领导人");
		$("#rele_btn").val("选择领导人");		
		rele_type_name = "领导人";
	}
	init_input();
}

function showSelectWin()
{
	if($(":radio[id='relevance_type'][checked=true]").val() == 0)
	{
		openSelectCPDept('选择部门','saveCPDept');
	}
	else
	{
		openSelectCPLead('选择领导人','saveCPDept');
	}
}
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="model_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>渠道名称：</th>
			<td colspan="7">
				<input id="model_cname" type="text" class="width200" value="" onblur="checkInputValue('model_cname',false,200,'业务名称','')"/>
			</td>
		</tr>
		
		<tr>
			<th><span class="f_red">*</span>投递类型：</th>
			<td colspan="7">
				<ul>
					<li><input id="relevance_type" name="relevance_type" type="radio" checked="true" value="0" onclick="showDeptTR(0)"/><label>部门</label></li>
					<li><input id="relevance_type" name="relevance_type" type="radio"  value="1"  onclick="showDeptTR(1)"/><label>领导人</label></li>
				</ul>
			</td>
		</tr>
		<tr id="dept_tr">
			<th><span class="f_red">*</span><span id="rele_span">关联部门<span>：</th>
			<td colspan="7">
				<input id="dept_ids" type="hidden" value="" />
				<textarea id="dept_names" name="dept_names" style="width:320px;height:50px" readOnly="readOnly"></textarea><input id="rele_btn" name="btn1" type="button" onclick="showSelectWin()" value="关联部门"/>
			</td>
		</tr>
		
		<tr>
			<th>分拣设置：</th>
			<td>
				<ul>
					<li><input id="is_sort" name="is_sort" type="checkbox"  value="1"/><label>手动分拣</label></li>
				</ul>
			</td>
			<th>是否直接发布：</th>
			<td>
			<ul>
					<li><input id="is_auto_publish" name="is_auto_publish" type="checkbox"  value="1"/><label>直接发布</label></li>
				</ul>
			</td>
			
			<td colspan="3"></td>
		</tr>
        <tr>
			<th>评论设置：</th>
			<td>
				<ul>
					<li><input id="is_allow_comment" name="is_allow_comment" type="checkbox"  value="1"/><label>允许评论</label></li>
				</ul>
			</td>
			<th>评论审核设置：</th>
			<td>
			<ul>
					<li><input id="is_comment_checked" name="is_comment_checked" type="checkbox"  value="1"/><label>启用审核</label></li>
				</ul>
			</td>
			
			<td colspan="3"></td>
		</tr>
		<tr>
			<th>参与方式：</th>
			<td>
				<ul>
					<li><input id="must_member" name="must_member" type="checkbox" value="1" /><label>仅会员</label></li>
				</ul>
			</td>
			<th>用户资料：</th>
			<td>
				<ul>
					<li><input id="user_secret" name="user_secret" type="checkbox"  value="1"/><label>保密</label></li>
				</ul>
			</td>
			<th>提醒方式：</th>
			<td>
				<ul>
					<li><input id="remind_type" name="remind_type" type="radio" checked="true" value="email"/><label>Email</label></li>
					<li><input id="remind_type" name="remind_type" type="radio" value="sms" /><label>手机短信</label></li>
				</ul>
			</td>
			<td></td>
		</tr>
		<tr>
			<th>工作流：</th>
			<td  colspan="7">
				<select id="wf_id" style="width:205px;" >
					<option value="0" selected="selected">不审核</option>					
				</select>
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>办理时限：</th>
			<td colspan="7">
				<input id="time_limit" type="text" class="width80" value="" onblur="checkInputValue('time_limit',false,4,'办理时限','checkInt')"/>天
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>提醒件：</th>
			<td width="110">
				<input id="warn_num" type="text" class="width80" value="" onblur="checkInputValue('warn_num',false,4,'提醒件','checkInt')"/>天
			</td>
			<th class="width60"><span class="f_red">*</span>黄牌件：</th>
			<td width="110">
				<input id="yellow_num" type="text" class="width80" value="" onblur="checkInputValue('yellow_num',false,4,'黄牌件','checkInt')"/>天
			</td>
			<th class="width60"><span class="f_red">*</span>红牌件：</th>
			<td width="250">
				<input id="red_num" type="text" class="width80" value="" onblur="checkInputValue('red_num',false,4,'红牌件','checkInt')"/>天
			</td>
			<td></td>
		</tr>
		<tr>
			<th>信件编码字头：</th>
			<td><input id="code_pre" type="text" class="width80" value="" onblur="checkInputValue('code_pre',true,20,'信件编码字头','')"/></td>

			<th>日 期 码：</th>
			<td>
				<select id="code_rule" class="width100" >
					<option id="" value="yyMMdd" selected="selected">YYMMDD</option>
					<option value="yyMM" >YYMM</option>
					<option value="yyyyMM" >YYYYMM</option>
					<option value="yyyyMMdd" >YYYYMMDD</option>
				</select>
			</td>
			<th>随机位数：</th>
			<td><select id="code_num" class="width100" >
					<option id="" value="3" selected="selected">3 位</option>
					<option value="4" >4 位</option>
					<option value="5" >5 位</option>
					<option value="6" >6 位</option>
				</select></td>
			<td></td>
		</tr>
		<tr>
			<th>查询密码位数：</th>
			<td colspan="7">
				<select id="query_num" class="width100" >
					<option id="" value="4" selected="selected">4 位</option>
					<option value="5" >5 位</option>
					<option value="6" >6 位</option>
					<option value="7" >7 位</option>
					<option value="8" >8 位</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>表单模板：</th>
			<td  colspan="7">
				<input type="text" id="template_form_name" class="width200" readOnly="readOnly"><input type="hidden" id="template_form" class="width200"/><input type="button" value="选择" onclick="openSelectTemplate('template_form','template_form_name',site_id)"/>
			</td>
		</tr>
		<tr>
			<th>列表页模板：</th>
			<td  colspan="7">
				<input type="text" id="template_list_name" class="width200" readOnly="readOnly"><input type="hidden" id="template_list" class="width200"><input type="button" value="选择" onclick="openSelectTemplate('template_list','template_list_name',site_id)"/>
			</td>
		</tr>
		<tr>
			<th>查询页模板：</th>
			<td  colspan="7">
				<input type="text" id="template_search_list_name" class="width200" readOnly="readOnly"><input type="hidden" id="template_search_list" class="width200"><input type="button" value="选择" onclick="openSelectTemplate('template_search_list','template_search_list_name',site_id)"/>
			</td>
		</tr>
		<tr>
			<th>详细查看页模板：</th>
			<td  colspan="7">
				<input type="text" id="template_content_name" class="width200" readOnly="readOnly"><input type="hidden" id="template_content" class="width200"><input type="button" value="选择" onclick="openSelectTemplate('template_content','template_content_name',site_id)"/>
			</td>
		</tr>
		<tr>
			<th>评论页模板：</th>
			<td  colspan="7">
				<input type="text" id="template_comment_name" class="width200" readOnly="readOnly"><input type="hidden" id="template_comment" class="width200"><input type="button" value="选择" onclick="openSelectTemplate('template_comment','template_comment_name',site_id)"/>
			</td>
		</tr>
		<tr>
			<th>信件打印模板：</th>
			<td  colspan="7">
				<input type="text" id="template_print_name" class="width200" readOnly="readOnly"><input type="hidden" id="template_print" class="width200"><input type="button" value="选择" onclick="openSelectTemplate('template_print','template_print_name',site_id)"/>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">备注：</th>
			<td colspan="7">
				<textarea id="model_memo" name="model_memo" style="width:620px;height:100px"></textarea>
			</td>
		</tr>
	</tbody>
</table>
<!--隔线开始-->
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<!--隔线结束-->
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('model_table',model_id);initData()" value="重置" />
			<input id="btn2" name="btn2" type="button" onclick="window.history.go(-1)" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
