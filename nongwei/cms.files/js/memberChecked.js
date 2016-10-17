/*
	会员登录验证
*/
var MemberManRPC = jsonrpc.MemberManRPC;
var memberBean = null;
$(document).ready(function() {	
		memberBean = MemberManRPC.getMemberBySession();
		if(memberBean == null)
		{//未登录，记录当前的地址，并写到sesson,页面跳转到登录页面
			MemberManRPC.setUrlToSession(window.location.href);
			window.location.href = "/member";
		}
});