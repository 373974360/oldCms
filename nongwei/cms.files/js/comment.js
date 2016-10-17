var commentSet = jsonrpc.CommentSetRPC.getCommentSetBySiteIdAndAppID(jsonrpc.SiteDomainRPC.getSiteIDByUrl(),"cms");

  var Comment = new function(){
    this.comment_parent_id = 0;
    this.replay = function(p_id,obj){
    var parent_obj = $(obj).parents(".operate");
      if($(parent_obj).find(".comment_replay").length > 0)
      {
        $(parent_obj).find(".comment_replay").remove();
      }else
      {
        $(".commentTop .comment_replay").remove();
        this.comment_parent_id = p_id;
        $(parent_obj).append($(".comment_form").html());
      }
      $(parent_obj).find("textarea").focus();  
    };
    this.replaySubmit = function(){
	  if(commentSet.time_spacer != 0)
	  {
			//如果提交有时间控制，判断时间是否超过了上次评论的时间
			var pre_time = request.getCookie("cicro_comment_time");
			if(pre_time != "" && pre_time != null)
		    {
				var confine_time = parseInt(pre_time)+commentSet.time_spacer*60*1000;
				if(new Date().getTime() < confine_time)
				{
					alert("对不起，您两次发表间隔小于"+commentSet.time_spacer+"分钟，请不要灌水");
					return;
				}
			}
	  }

      var content = $("#content").val();
      if(content == "" || content == null)
      {
        alert("评论内容不能为空");
        return;
      }

      var bean = BeanUtil.getCopy(new Bean("com.deya.wcm.bean.comment.CommentBean",true));
    try{
      if(commentSet.is_public == 0 && memberBean == null)
      {
      alert("请登录后发贴");
      return;
      }
      if(memberBean != null)
      {
      bean.member_id = memberBean.me_id;
      bean.nick_name = memberBean.me_nickname;
      }else
      bean.nick_name = $("#nick_name").val();
    }catch(e)
    {
     bean.nick_name = $("#nick_name").val();     
    }
    if(bean.nick_name == "" || bean.nick_name == null)
     bean.nick_name = '匿名';
      
      bean.info_type = 1;
      bean.content = content;
      
      var tel = "";
      var email = "";
      if($("#tel").val())
        tel = $("#tel").val();
      if($("#email").val())
        email = $("#email").val();
      bean.tel = tel;
      bean.email = email;
      bean.parent_id = this.comment_parent_id;
      bean.app_id = "cms";
      bean.info_id = request.getParameter("id");
      var url = window.location.href;
      if(url.indexOf(".htm")>0)
     {
		var info_id = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf(".")).replace(/_.*/ig,"");
		bean.info_id = info_id;
     }
	  var returnVal = jsonrpc.CommentSetRPC.insertCommentMain(bean);
	  if(returnVal == "timeout")
	  {
		 alert("对不起，您两次发表间隔小于"+commentSet.ip_time+"分钟，请不要灌水");
		 this.comment_parent_id = 0;
		 $(".textarea_content").val("");
		 $(".commentTop .comment_replay").remove();
		 return;
	  }
      if(returnVal == "true")
      {
        if(commentSet.is_need == 1)
        {
          alert("提交成功，请静待管理员审核");
        }
        else
        {
          alert("提交成功");
        }
		request.setCookie("cicro_comment_time",new Date().getTime());
		this.comment_parent_id = 0;
		$(".textarea_content").val("");
		$(".commentTop .comment_replay").remove();
      }else
        alert("提交失败，请重新提交")
    };
    this.commentSubmit = function(){
      var content = $("#content").val();
      if(content == "" || content == null)
      {
        alert("评论内容不能为空");
        return;
      }
      var bean = BeanUtil.getCopy(new Bean("com.deya.wcm.bean.comment.CommentBean",true));
      if(commentSet.is_public == 0 && memberBean == null)
      {
        alert("请登录后发贴");
        return;
      }
      if(memberBean != null)
      {
        bean.member_id = memberBean.me_id;
        bean.nick_name = memberBean.me_nickname;
      }else
        bean.nick_name = $("#nick_name").val();

      bean.info_type = 1;
      bean.content = content;
      var tel = "";
      var email = "";
      if($("#tel").val())
        tel = $("#tel").val();
      if($("#email").val())
        email = $("#email").val();
      bean.tel = tel;
      bean.email = email;
      bean.app_id = "cms";
      bean.info_id = request.getParameter("id");
      if(jsonrpc.CommentSetRPC.insertCommentMain(bean))
      {
        if(commentSet.is_need == 1)
        {
          alert("提交成功，请静待管理员审核");
        }
        else
        {
          alert("提交成功");
        }
        window.location.reload();
      }else
        alert("提交失败，请重新提交")
    };
    this.showBackList = function(obj){
      var p = $(obj).parent();  
      var top_obj = $(obj).parents("#commentTop");
      $(p).remove();
      $(top_obj).find(":hidden").show();
      return false;
    };
    this.supportComment = function(id,obj)
    {
		 if(commentSet.time_spacer != 0)
		  {
				//如果提交有时间控制，判断时间是否超过了上次评论的时间
				var pre_time = request.getCookie("cicro_comment_support_time");
				if(pre_time != "" && pre_time != null)
				{
					var confine_time = parseInt(pre_time)+commentSet.time_spacer*60*1000;
					if(new Date().getTime() < confine_time)
					{
						alert("您已经支持过了");
						return;
					}
				}
		  }
       var text = $(obj).text();
      var sup = $(obj).find("em").text();
      var m = new Map();
      m.put("support_count","");
      m.put("id",id);
      jsonrpc.CommentSetRPC.supportComment(m);
      $(obj).find("em").text(parseInt(sup)+1);
      $(obj).after("<span>"+$(obj).html()+"</span>");
      $(obj).remove();
	  request.setCookie("cicro_comment_support_time",new Date().getTime());
      return true
    }
  }

/**
 * 对页面request对象的实现,可以得到页面地址,参数,读写cookie等
 * @param {Object} name
 */
request = {
	/**
	 * 得到参数	如在aaa.html?id=1中request.getParameter("id")得到1
	 * @param {Object} name	参数名
	 */
	getParameter : function(name) {
		var GetUrl = this.getUrl();
		var Plist = [];
		if (GetUrl.indexOf('?') > 0) {
			Plist = GetUrl.split('?')[1].split('&');
		} else if (GetUrl.indexOf('#') > 0) {
			Plist = GetUrl.split('#')[1].split('&');
		}
		if (GetUrl.length > 0) {
			for (var i = 0; i < Plist.length; i++) {
				var GetValue = Plist[i].split('=');
				if (GetValue[0].toUpperCase() == name.toUpperCase()) {
					return GetValue[1];
					break;
				}
			}
			return;
		}
	},
	/**
	 * 如果有多个值的参数,返回包含全部值的Array对象
	 * @param {Object} name
	 */
	getParameters : function(name) {
		var param=this.getParameter(name);
		if(param){
			return param.split(",");
		}
	},
	/** 获取浏览器URL */
	getUrl : function() {
		return location.href;
	},
	/**得到应用的context*/
	getContextPath : function() {
		return context;
	},
//	getSession : function(){
//		return jsonrpc.jsonUtil.getSession();
//	},
	/*
	 * cookies设置函数 @name Cookies名称 @value 值
	 */
	setCookie : function(name, value) {
		try {
			var longTimes = 0;
			var argv = this.setCookie.arguments;
			var argc = this.setCookie.arguments.length;
			var expires = (argc > 2) ? argv[2] : null;			
			if (expires != null) {
				var str1=expires.substring(1,expires.length)*1; 
				var str2=expires.substring(0,1); 
				if (str2=="s"){
					longTimes = str1*1000;
				}else if (str2=="h"){
					longTimes = str1*60*60*1000;
				}else if (str2=="d"){
					longTimes = str1*24*60*60*1000;
				}
				var LargeExpDate = new Date();
				LargeExpDate.setTime(LargeExpDate.getTime()
						+ (longTimes));
			}
			document.cookie = name
					+ "="
					+ escape(value)
					+ ((expires == null) ? "" : ("; expires=" + LargeExpDate
							.toGMTString()));
			return true;
		} catch (e) {
			alert(e.description);
			return false;
		}
	},
	/*
	 * cookies读取函数 @Name Cookies名称 返回值 Cookies值
	 */
	getCookie : function(Name) {
		var search = Name + "="
		if (document.cookie.length > 0) {
			offset = document.cookie.indexOf(search)
			if (offset != -1) {
				offset += search.length
				end = document.cookie.indexOf(";", offset)
				if (end == -1)
					end = document.cookie.length
				return unescape(document.cookie.substring(offset, end))
			} else {
				return;
			}
		}
	},
	removeCookie: function(name){
		if (this.getCookie(name)) {
			document.cookie = name + "=;expires=Thu, 01-Jan-1970 00:00:01 GMT";
		}
	}
};
