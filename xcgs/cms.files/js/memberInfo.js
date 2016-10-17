var CommentManRPC = jsonrpc.CommentManRPC;
var SQRPC = jsonrpc.SQRPC;

$(document).ready(function() {
	showMemberName();
	initMemberInfoForm();
	initPassForm();

	 jQuery.validator.addMethod("passWordCheck", function(value, element) {
		
		return checkPassword(value);
	 }, "邮政编码格式错误!");

});

function checkPassword(pass)
{
	return MemberManRPC.passWordCheck(pass);
}

function initPassForm()
{
	var validator = $("#passForm").validate({
		rules: {			
			old_me_password:{
				required: true,
				rangelength: [6,18],
				passWordCheck:true
			},
			me_password:{
				required: true,
				rangelength: [6,18]
			},
			me_password2:{
				required: true,
				equalTo: "#me_password"
			}			
		},
		messages: {			
			old_me_password: {
				required: "请输入原始密码！",
				rangelength: jQuery.format("6-16位字符（字母、数字、符号），区分大小写。"),
				passWordCheck:jQuery.format("原始密码输入不正确。")

			},
			me_password: {
				required: "请输入密码！",
				rangelength: jQuery.format("6-16位字符（字母、数字、符号），区分大小写。")
			},
			me_password2: {
				required: "请输入确认密码！",
				equalTo: jQuery.format("请确认两次输入密码保持一致。")
			}		
		},
		
		errorPlacement: function(error, element) {
			error.appendTo( element.parent("td").next("td") );
		},

		submitHandler: function(form) {
			
			form.submit();//提交表单
		},

		success: function(label) {
			label.text("ok!").addClass("success");
		}
	});
}


function initMemberInfoForm()
{
	var validator = $("#regForm").validate({
		rules: {			
			me_realname:{
				required: true,
				rangelength: [2,20]
			},
			me_card_id:{
				required: true,
				rangelength: [15,19]
			},
			me_phone:{
				required: true,
				number:true,
				rangelength: [7,11]
			},
			me_email:{
				required: true,
				email: true
			},
			me_age:{
				number:true,
				range: [0,120]
			}
			
		},
		messages: {			
			me_realname:{
				required: "请输入真实姓名！",
				rangelength: jQuery.format("2-20位小写字母或汉字。")
			},
			me_card_id:{
				required: "请输入身份证号！",
				rangelength: jQuery.format("请正确输入15位或18位身份证号。")
			},
			me_phone:{
				required: "请输入移动电话号码！",
				rangelength: jQuery.format("请正确输入移动电话号码。"),
				number: jQuery.format("不能含有非数字字符。")
			},
			me_email:{
				required: "请输入Email地址！",
				email: jQuery.format("请正确输入Email地址")
			},
			me_age:{
				number: jQuery.format("不能含有非数字字符。"),
				range: jQuery.format("请正确输入年龄。")
			}			
		},
		
		errorPlacement: function(error, element) {
			error.appendTo( element.parent("td").next("td") );
		},

		submitHandler: function(form) {
			
			form.submit();//提交表单
		},

		success: function(label) {
			label.text("ok!").addClass("success");
		}
	});
}

TurnPage = function (){
	/**
	 * 每页显示条数
	 */
	this.pageSize=8;
	this.getPageSize=function(){
		return this.pageSize;
	};
	this.setPageSize=function(pageSize){
		this.pageSize=pageSize;
	};
	/**
	 * 总条数
	 */
	this.total=10;
	this.getTotal=function(){
		return this.total;
	};
	this.setTotal=function(total){
		this.total=total;
	};

	/**
	 * 当前显示页
	 */
	this.curr_page=1;
	this.pageCount = 1;
	/**
	 * 获得总页数
	 */
	this.getPageCount = function(){
		var pageCount=parseInt(this.total/this.pageSize+1);
		if(this.total%this.pageSize==0){
			pageCount=pageCount-1;
		}	
		return pageCount;
	};
	/**
	 * 当前页取数据的开始位置
	 */
	this.getStart=function(){
		return (this.curr_page-1)*this.pageSize;
	};
	/**
	 * 跳转到指定页
	 * @param {Object} page
	 */
	this.goPage=function (page){		
		if(page == 0)
			page = 1;
        var CheckData = /^\d+$/;
        var checkzero = /^0+$/;
      
		page=parseInt(page);		
		if(this.getPageCount()<page){			
			return;
		}		
		this.curr_page=page;
		this.onPageChange();
	};
} 

function showMemberName()
{
	memberBean = MemberManRPC.getMemberBySession();
	$("#me_realname_span").text(memberBean.me_realname);
	$("#me_nickname_span").text(memberBean.me_nickname);
}

function showMemInfo(app_name,action_type)
{
	$("#app_title_div").text(app_name);
	var contents = "";
	switch(action_type)
	{
		case "password":$("#sq_content_div").hide();
					$("#member_info_div").hide();
					$("#comment_content_div").hide();
					$("#member_pass_div").show();					
				    break;
		case "info":$("#sq_content_div").hide();
					$("#member_pass_div").hide();
					$("#comment_content_div").hide();
					$("#member_info_div").show();
					showMemberInfo();
				    break;
		case "sqxj":$("#sq_content_div").show();
					$("#member_pass_div").hide();
					$("#comment_content_div").hide();
					$("#member_info_div").hide();
					showSqxjInfo();
				    break;
		case "comment":$("#sq_content_div").hide();
					$("#member_pass_div").hide();
					$("#member_info_div").hide();
					$("#comment_content_div").show();
					tp.onPageChange = showComment;	
					showComment();
				    break;
	}	
}

var tp = new TurnPage();
function showSqxjInfo()
{
	var sq_list = SQRPC.getBroSQListByMemberID(memberBean.me_id);
	sq_list = List.toJSList(sq_list);
	
	var str = '';
		
	if(sq_list != null && sq_list.size() > 0)
	{		
		for(var i=0;i<sq_list.size();i++)
		{
			str += '<tr>';
			str += '<td style="text-align:left;" ><a href="/appeal/view.jsp?model_id='+sq_list.get(i).model_id+'&query_code='+sq_list.get(i).query_code+'&sq_code='+sq_list.get(i).sq_code+'" target="_blank">'+sq_list.get(i).sq_title2+'</a></td>';
			str += '<td>'+sq_list.get(i).submit_name+'</td>';
			str += '<td>'+sq_list.get(i).sq_dtime.substring(0,10)+'</td>';
			str += '<td>'+sq_list.get(i).sq_status_name+'</td>';
			str += '<td>'+sq_list.get(i).model_cname+'</td>';
			str += '</tr>';
		}		
	}	
	//str += '<tr>';
	//str += '<td colspan="5">共'+sq_list.size()+'条记录</td>';
	//str += '</tr>';	
	$("#sq_table_tbody").empty().html(str);
	OddEvenColor("mailList");
}

function showComment()
{
	tp.setTotal(CommentManRPC.getCommontCountByMemberID(memberBean.me_id));
	var com_list = CommentManRPC.getCommontListByMemberID(memberBean.me_id,tp.getStart(),tp.getPageSize());
	com_list = List.toJSList(com_list);
	var str = "";
	if(com_list != null && com_list.size() )
	{
		for(var i=0;i<com_list.size();i++)
		{
			str += '<div class="commnetArea contentBox12">';
			str += '<div class="cmt_banner f13B" style="padding:9px 0;"><span >评论时间：'+com_list.get(i).add_dtime+'</span></div>';			
			str += '<div class="cmt_content">'+com_list.get(i).cmt_content+'</div><div class="textRight"></div>';
			str += '</div>';
		}
	}
	str += '<div style="padding-top:12px">';
	str += '<ul class="page">';	
	str += '<li><a href="javascript:tp.goPage('+ tp.getPageCount() +')">未页</a></li>';
	str += '<li><a href="javascript:tp.goPage('+ (tp.curr_page + 1) +')">下一页</a></li>';
	str += '<li><a href="javascript:tp.goPage('+ (tp.curr_page - 1) +')">上一页</a></li>';
	str += '<li><a href="javascript:tp.goPage(1)">首页</a></li>';
	str += '<li>共'+tp.getTotal()+'条记录 共'+tp.getPageCount()+'页 当前第'+tp.curr_page+'页</li>';	
	str += '</ul>';
	str += '</div>';
	$("#comment_content_div").empty().html(str);
}

function showMemberInfo()
{
	$("#me_nickname").val(memberBean.me_nickname);
	$("#me_age").val(memberBean.me_age);
	$("#me_realname").val(memberBean.me_realname);
	$(":radio[name='me_sex'][value="+memberBean.me_sex+"]").attr("checked",true);
	$("#me_phone").val(memberBean.me_phone);
	$("#me_tel").val(memberBean.me_tel);
	$("#me_vocation option[value='"+memberBean.me_vocation+"']").val(memberBean.me_vocation);
	$("#me_email").val(memberBean.me_email);
	$("#me_address").val(memberBean.me_address);
	$("#passForm #me_id").val(memberBean.me_id);
	$("#regForm #me_id").val(memberBean.me_id);
	$("#me_card_id").val(memberBean.me_card_id);
}