<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<title>依申请公开</title>
<meta http-equiv="X-UA-Compatible" content="IE=8"/>
<meta name="generator" content="cicro-Builder"/>

<link type="text/css" rel="stylesheet" href="../../../styles/themes/default/tree.css"/>
<link type="text/css" rel="stylesheet" href="../../../styles/sq.css"/>
<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../../js/indexjs/indexList.js"></script>
<script type="text/javascript" src="../../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="../js/ysqgk.js"></script>
<script type="text/javascript">
var ysq_id =<%=request.getParameter("ysq_id")%>;
var node_id ="<%=request.getParameter("node_id")%>";
var site_id ="<%=request.getParameter("site_id")%>";
var app_id = "zwgk";
var GKNodeBean = "";
var YsqgkConfigBean ="";

var do_state="";
var defaultBean="";
$(document).ready(function(){
	initButtomStyle();
	init_input();
    initUeditor("deal_content");
	iniSQbox();
	initTabAndStatus();
	YsqgkConfigBean = jsonrpc.YsqgkRPC.getYsqgkConfigBean();
	
	if(ysq_id != "" && ysq_id != "null" && ysq_id != null)
    {		
		defaultBean = YsqgkRPC.getYsqgkBean(ysq_id);
		do_state = defaultBean.do_state;
		if(defaultBean.final_status == -1){
			$("#dealButton").hide();
		}
		check_do_state(do_state);
		if(defaultBean){
			$("#ysqgk_infos_table").autoFill(defaultBean);
			
			if(do_state == 0)
			{				
				$("#deal_contentDIV").hide();
			}else if(do_state == 1)
			{	
			    $("#deal_contentDIV").show();
				$("#accept_user").html(UserManRPC.getUserRealName(defaultBean.accept_user));		
				$("#shouLiReply").html(defaultBean.accept_content);
			}else if(do_state == 2){				
				setV("deal_content",defaultBean.reply_content);

			}else if(do_state == -1){

				$("#dealButton").hide();
				$("#shouLiReply").html("无效申请");
			}

			showYsq_type_table(defaultBean.ysq_type);
			showYsq_card_name(defaultBean.card_name);
			
			$(":radio[name='is_derate'][value='"+defaultBean.is_derate+"']").attr("checked","checked");
			$(":radio[name='get_method'][value='"+defaultBean.get_method+"']").attr("checked","checked");
			$(":radio[name='is_other'][value='"+defaultBean.is_other+"']").attr("checked","checked");
			$(":radio[name='publish_state'][value='"+defaultBean.publish_state+"']").attr("checked","checked");
			$(":radio[name='is_third'][value='"+defaultBean.is_third+"']").attr("checked","checked");
			$(":radio[name='is_extend'][value='"+defaultBean.is_extend+"']").attr("checked","checked");
			$(":radio[name='input_select'][value='"+defaultBean.input_select+"']").attr("checked","checked");
			$(":radio[name='is_mail'][value='"+defaultBean.is_mail+"']").attr("checked","checked");
		}
    }
});
function showYsq_type_table(type)
{
	if(type == 0)
	{
		$("#ysq_type").html('公民');
		$("#Ysq_type_table_fr").hide();
		$("#Ysq_type_table_gm").show();
	}else{
		$("#ysq_type").html('法人或其他组织');
		$("#Ysq_type_table_fr").show();
		$("#Ysq_type_table_gm").hide();	
	}	
}
function showYsq_card_name(type)
{
	if(type == 0){
		$("#card_name").html('身份证');
	}else{
		$("#card_name").html('军官证');
	}	
}
function check_do_state(do_state){
	//处理常用语信息
	var pro_type = $('#reply_type').val();
	getYsqgkPhrasaListByType(pro_type);
	if(do_state == 1)
	{
		$("#btn165sl").hide();
		$("#SubmitButtons").hide();
		//$(".sq_box_do").show();
		$("#Quick_content_div").hide();
		
		//$("#do_0").show(2,function(){$(document).scrollTop($(document.body).height());});
		
		$("#publish_status_tr").hide();
	    $("#replay_type_tr").hide();
	    $("#is_mail_tr").hide();
	    	
	}else if(do_state == 2){
		$("#dealButton").hide();
		$("#SubmitButtons").hide();
		$("#Quick_content_div").hide();
		$(".sq_box_do").show();
		
		$("#do_0").show(2,function(){$(document).scrollTop($(document.body).height());});
		
		$("#publish_status_tr").show();
	    $("#replay_type_tr").show();
	    $("#is_mail_tr").show();
	}
	switch(do_state)
	{
		case 1:$("#sq_title").text("受理情况");	
			   $("#common_deal_title").text("受理内容:");	   
				break;
		case 2:$("#sq_title").text("回复情况");
		       $("#common_deal_title").text("回复内容:");
			   $("#publish_status_tr").show();
			   $("#replay_type_tr").show();
			   $("#is_mail_tr").show();
			   break;
		default:break;
	}
}
//信件处理开始
function doVoid(id,pro_type)
{
	$(".sq_box_do").hide();
	$("#"+id).show(2,function(){$(document).scrollTop($(document.body).height());});
	
	$("#publish_status_tr").hide();
    $("#replay_type_tr").hide();
    $("#is_mail_tr").hide();
    $("#SubmitButtons").show();
    // $("#affix_tr").show();	
	
	switch(pro_type)
	{
		case 0:$("div #sq_title_div").text("受理信件");	
			   $("#common_deal_title").text("受理内容:");			   
				break;
		case 1:$("div #sq_title_div").text("回复信件");
		       $("#common_deal_title").text("回复内容:");	
		       
			   $("#publish_status_tr").show();
			   $("#replay_type_tr").show();
			   $("#is_mail_tr").show();

			   $("select[@name=reply_type] option").each(function(){
					 if($(this).val() == 0)
					 {
						$(this).remove();
					 }
					 getType(1);					 
				});
			   break;
		default:break;
	}
    $("#submitButton").unbind("click").click(function(){
		insertProcess(pro_type,node_id,ysq_id);
	});
	init_input();
}

function openPrintPage()
{
	window.open("print.jsp?ysq_id="+ysq_id+"&tm_id="+YsqgkConfigBean.template_print);
}

</script>
</head>
<body>
<div id="ysqgk_infos_table" name="ysqgk_infos_table">
<span class="blank6"></span>
<!--来信人信息-->
<div class="sq_box">
	<div class="sq_title_box">
		<div class="sq_title sq_title_minus">申请单信息</div>
		<div class="sq_title_right">点击闭合</div>
	</div>
	<div class="sq_box_content">
	<table id="" class="table_view" border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
	            <th><nobr>申请单类型：</nobr></th>
				<td id="ysq_type"></td>            
            </tr>
		</tbody>
		<tbody id="Ysq_type_table_gm">
        	<tr>
				<th>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</th>
				<td id="name"></td>
				<th>工作单位：</th>
				<td id="company"></td>				
			</tr>
             <tr>
				<th>证件名称：</th>
				<td id="card_name"></td>
				<th>证件号码：</th>
				<td id="card_code"></td>
			</tr> 			
		</tbody>
		<tbody id="Ysq_type_table_fr">
             <tr>
				<th><nobr>组织机构代码：</nobr></th>
				<td id="org_code"></td>
				<th><nobr>营业执照代码：</nobr></th>
				<td id="licence"></td>
			</tr> 
            <tr>
				<th>法人代表：</th>
				<td id="legalperson"></td>
            	<th>联系人：</th>
				<td id="linkman"></td>		
			</tr>   			
		</tbody>
		<tbody>        	
            <tr>
				<th>联系电话：</th>
				<td id="tel"></td>
                <th>联系传真：</th>
				<td id="fax"></td>					
			</tr>
             <tr>
				<th>手机号码：</th>
				<td id="phone"></td>
				<th>电子邮箱：</th>
				<td id="email"></td>
			</tr> 
            <tr>
				<th>申请时间：</th>
				<td id="put_dtime"></td>
                <th>邮政编码：</th>
				<td id="postcode"></td>
            </tr>
        </tbody>
        <tbody> 
            <tr>
            	<th>通讯地址：</th>
				<td id="address" colspan="3"></td>		
			</tr>   			
		</tbody>
	</table>
	</div>
</div>
<span class="blank6"></span>
<!--信件信息-->
<div class="sq_box">
	<div class="sq_title_box">
		<div class="sq_title sq_title_minus">所需信息情况</div>
		<div class="sq_title_right">点击闭合</div>
	</div>
	<div class="sq_box_content">
	<table id="" class="table_view" border="0" cellpadding="0" cellspacing="0">
		<tbody>          
           
           <tr>
				<th><nobr>所需信息内容描述：</nobr></th>
				<td id="content" >
					<!--textarea id="content" name="content" style="width:550px;height:150px;visibility:hidden;">所需信息内容描述
					</textarea-->
				</td>
			</tr>
             <tr>
				<th><nobr>所需信息的索引号：</nobr></th>
				<td id="gk_index"></td>
			</tr>
            <tr>
				<th>用途描述：</th>
				<td id="description"></td>
			</tr>
            <tr>
				<th><nobr>是否申请减免费用：</nobr></th>
				<td>
				  <ul id="a123">
					<li><input id="is_derate" name="is_derate" type="radio" value="0"/><label for="e">是</label></li>
					<li><input id="is_derate" name="is_derate" type="radio" value="1" checked="true"/><label for="f">否</label></li>
				  </ul>
				</td>				
			</tr>			
			<!--<tr>
				<th><nobr>信息的指定提供方式：</nobr></th>
				<td><ul id="a123">
					<li><input id="offer_type" name="offer_type" type="checkbox" value="0" checked="true"/><label for="e">纸质</label></li>
                    <li><input id="offer_type" name="offer_type" type="checkbox" value="1" /><label for="e">电子邮件</label></li>
                    <li><input id="offer_type" name="offer_type" type="checkbox" value="2" /><label for="e">光盘</label></li>
                    <li><input id="offer_type" name="offer_type" type="checkbox" value="3" /><label for="e">磁盘</label></li>					
				</ul></td>
            </tr>-->
			<tr>
				<th><nobr>获取信息方式：</nobr></th>
				<td><ul id="a43">
					<li><input id="get_method" name="get_method" type="checkbox" value="0" checked="true" /><label for="e">电子邮件</label></li>
                    <li><input id="get_method" name="get_method" type="checkbox" value="1" /><label for="e">邮寄</label></li>
                    <li><input id="get_method" name="get_method" type="checkbox" value="2" /><label for="e">传真</label></li>
                    <li><input id="get_method" name="get_method" type="checkbox" value="3" /><label for="e">自行领取</label></li>					
				</ul></td>
            </tr>			
			<tr>
				<th><nobr>是否接受其他方式：</nobr></th>
				<td><ul id="a123">
					<li><input id="is_other" name="is_other" type="radio"  value="0" /><label for="e">是</label></li>
					<li><input id="is_other" name="is_other" type="radio"  value="1" checked="true" /><label for="f">否</label></li>
				</ul></td>
			</tr>
		</tbody>
	</table>
	</div>
</div>
<span class="blank6"></span>
<div class="sq_box" id="deal_contentDIV">
	<div class="sq_title_box">
		<div class="sq_title sq_title_minus">受理情况</div>
		<div class="sq_title_right">点击闭合</div>
	</div>
	<div class="sq_box_content">
	   <table id="" class="table_view" border="0" cellpadding="0" cellspacing="0">
            <tbody>
                <tr>
					<th><nobr>申请单号：</nobr></th>
					<td id="ysq_code"></td>
					<th><nobr>查 询 码：</nobr></th>
					<td id="query_code"></td>
                </tr>
			    <tr>
					<th><nobr>受理时间：</nobr></th>
					<td id="accept_dtime"></td>
					<th><nobr>受 理 人：</nobr></th>
					<td id="accept_user"></td>
                </tr>
                <tr>
                    <th><nobr>受理内容：</nobr></th>
                    <td colspan="3"> 
                       <div id="shouLiReply"> </div>
                    </td>
                </tr>
            </tbody>
	   </table>
	</div>
</div>

<span class="blank6"></span>
<div class="line2h"></div>
<span class="blank6"></span>
<!--操作按扭区-->
<table  id="dealButton" class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:10px;">
			<input id="btn165sl" name="btn1" type="button" onclick="doVoid('do_0',0)" value="受理" />
			<input id="btn166hf" name="btn2" type="button" onclick="doVoid('do_0',1);" value="回复" />
			<input id="btn167wx" name="btn3" type="button" onclick="setWuxiao(node_id,ysq_id)" value="置为无效" />
			<input id="btn167hf" name="btn2" type="button" onclick="openPrintPage()" value="打印" />
			<input id="" name="btn4" type="button" onclick="tab_colseOnclick(curTabIndex)" value="返回" />
		</td>
	</tr>
</table>
<span class="blank6"></span>
<span class="blank6"></span>
<!--受理-->
<div id="do_0" class="sq_box_do hidden">
	<div class="sq_title_box">
		<div class="sq_title sq_title_minus">处理情况</div>
		<div class="sq_title_right">点击闭合</div>
	</div>
	<div class="sq_box_content">
	<table id="" class="table_view" border="0" cellpadding="0" cellspacing="0">
		<tbody>    
			<tr id="publish_status_tr">
				<th>是否发布：</th>
				<td>
				<ul id="a123">
					<li><input id="publish_state" name="publish_state" type="radio" checked="true"  value="0"/><label for="e">否</label></li>
					<li><input id="publish_state" name="publish_state" type="radio"  value="1"/><label for="f">是</label></li>
				</ul>
				</td>
			</tr>           
            <tr>
				<th><nobr>征询第三方意见：</nobr></th>
				<td>
				<ul id="a123">
					<li><input id="is_third" name="is_third" type="radio"  value="0" checked="true"/><label for="e">否</label></li>
					<li><input id="is_third" name="is_third" type="radio"  value="1"  /><label for="f">是</label></li>
				</ul>
				</td>
			</tr>
             <tr>
				<th><nobr>延长答复期限：</nobr></th>
				<td>
				<ul id="a123">
					<li><input id="is_extend"  name="is_extend" type="radio" value="0" checked="true"/><label for="e">否</label></li>
					<li><input id="is_extend"  name="is_extend" type="radio" value="1"  /><label for="f">是</label></li>
				</ul>
				</td>
			</tr>
			<tr id="is_mail_tr">
				<th><nobr>发送电子邮件：</nobr></th>
				<td>
					<ul id="a123">
						<li>
                          <input id="is_mail" name="is_mail" type="radio" value="0" checked="checked"/><label for="e">否</label></li>
                        <li>
                        <input id="is_mail" name="is_mail" type="radio" value="1" /><label for="e">是</label>
                        </li>
					</ul>
				</td>
			</tr>
			<tr id="weight_tr">
				<th>权重：</th>
				<td>
					<input id="weight" name="weight" type="text" style="width:50px;" value="60"  maxlength="2" onblur="checkInputValue('weight',true,2,'权重','checkNumber')"/>默认值：60，取值范围（0-99）
				</td>
			</tr>
            <tr id="replay_type_tr">
				<th>回复方式：</th>
				<td colspan="7">
					<select class="input_select"  id="reply_type" name="reply_type" style="width:150px;" onchange ="getType(this.value)">
						<option selected="selected"  value="0">登记回执</li>
					    <option value="1">全部公开</option>
						<option value="2">部分公开</option>
						<option value="3">不予公开</option>
						<option value="4">非本单位公开信息</option>
						<option value="5">信息不存在</option>
					</select>
				</td>
			</tr>
			<tr>
				<th id="common_deal_title">受理内容：</th>
				<td>
					<div id="Quick_content_div" style="display:none">
						<select id="quick_content" style="width:200px;" onchange="setSelectedCommonLang(this.value)" >
					    </select>
                        <input id="btn1" name="btn1" type="button" onclick="insertAddress()" value="插入联系方式" /> 
					</div>
					<span class="blank3"></span>
                        <script id="deal_content" type="text/plain" style="width:620px;height:180px;"></script>
					</textarea>
				</td>
			</tr>
			<tr id="SubmitButtons">
				<th></th>
				<td>
					<input id="submitButton" name="submitButton" type="button" onclick="javascript:void(0);" value="提交" />					
                    <input id="btn2" name="btn2" type="button" onclick="tab_colseOnclick(curTabIndex)" value="取消" />
				</td>
			</tr>
		</tbody>
	</table>
	</div>	
</div>
<span class="blank12"></span>
</div>
</body>
</html>