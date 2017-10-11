<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.deya.wcm.services.org.user.UserLogin" %>
<%@ page import="com.deya.wcm.services.org.user.SessionManager" %>
<%
    boolean isLogin = UserLogin.checkLoginBySession(request);
    if (isLogin) {
        out.println("<script>top.location.href = 'index.jsp';</script>");
    }

    String user_name = request.getParameter("username");
    String pass_word = request.getParameter("password");
    String auth_code = request.getParameter("auth_code");
    response.setCharacterEncoding("UTF-8");
    if (user_name != null && !"".equals(user_name)) {
        String res = com.deya.wcm.services.org.user.UserLoginRPC.checkUserLogin(user_name, pass_word, auth_code, request);
        SessionManager.remove(request,"valiCode");
        if ("auth_code_error".equals(res)) {
            response.getWriter().print("{\"loginCode\":-1}");
        } else {
            int msg = Integer.parseInt(res);
            response.getWriter().print("{\"loginCode\":" + msg + "}");
        }
    }
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>政务信息管理平台-登录</title>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <link rel="stylesheet" type="text/css" href="styles/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="styles/themes/icon.css">
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/java.js"></script>
    <script type="text/javascript" src="js/extend.js"></script>
    <script type="text/javascript" src="js/jquery.c.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript" src="js/validator.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/jquery-plugin/jquery.cookie.js?v=20161215"></script>
    <script type="text/javascript" src="js/md5.js"></script>
    <script type="text/javascript">
        var username,password,auth_code;
        $(document).ready(function () {
            $("#sysname").html('政务信息管理平台');
            if ($.browser.msie && ($.browser.version == "6.0") && !$.support.style) {
                $('#dd').show();
                $('#dd').dialog();
            } else {
                $('#dd').hide();
            }
            $("#username").focus();

            //2分钟更新一下验证码，以免session失效
            setInterval(function () {
                changeCreateImage();
            }, 1000 * 120);
        });

        function checkCookie() {
            username = $.trim($("#username").val());
            password = $.trim($("#password").val());
            auth_code = $.trim($("#auth_code").val());
            var loginErrorTimes = $.cookie("loginErrorTimes-" + $.md5(username));
            if (loginErrorTimes != null && loginErrorTimes >= 3) {
                var errorDate = $.cookie("loginErrorDate-" + $.md5(username));
                var now = new Date();
                if(now.getTime() - parseInt(errorDate) > 60*1000){
                    $.cookie("loginErrorTimes-" + $.md5(username),null,{expires:now});
                    $.cookie("loginErrorDate-" + $.md5(username),null,{expires:now});
                    login();
                }else{
                    alert("该帐号登录错误次数过多，已被限制登录1小时！");
                }
            } else {
                login();
            }
        }

        function login(){
            if (username == "") {
                alert("用户名不能为空！");
                $("#username").focus();
                return;
            }
            if (password == "") {
                alert("密码不能为空！");
                $("#password").focus();
                return;
            }
            if (auth_code == "") {
                alert("验证码不能为空！");
                $("#auth_code").focus();
                return;
            }

            $.post("login.jsp", {username: username, password: password, auth_code: auth_code}, function (data) {
                data = data.substring(0,data.indexOf("}") + 1);
                data = eval("("+data+")");
                switch (data.loginCode) {
                    case -1:
                        alert("验证码不正确");
                        changeCreateImage();
                        break;
                    case 0:
                        window.location.href = "index.jsp";
                        break;
                    case 1:
                        alert("该帐号还未开通");
                        changeCreateImage();
                        break;
                    case 2:
                        alert("该用户已被停用");
                        changeCreateImage();
                        break;
                    case 3:
                        alert("用户名密码不正确");
                        changeCreateImage();
                        setLoginErrorTimes(username);
                        break;
                    case 4:
                        alert("用户名密码不正确");
                        changeCreateImage();
                        setLoginErrorTimes(username);
                        break;
                    case 5:
                        alert("该用户不存在");
                        changeCreateImage();
                        setLoginErrorTimes(username);
                        break;
                    default:
                        alert("用户名密码不正确");
                        changeCreateImage();
                        setLoginErrorTimes(username);
                        break;
                }
            });
        }

        function changeCreateImage() {
            $('#img').attr('src', 'createImage.jsp?' + Math.random());
        }

        function setLoginErrorTimes(username) {
            var loginErrorTimes = $.cookie("loginErrorTimes-" + $.md5(username));
            if (loginErrorTimes == null || loginErrorTimes == undefined) {
                loginErrorTimes = 1;
            } else {
                loginErrorTimes = parseInt(loginErrorTimes) + 1;
            }
            var date = new Date();
            $.cookie("loginErrorTimes-" + $.md5(username), loginErrorTimes);
            $.cookie("loginErrorDate-" + $.md5(username), date.getTime());
        }
    </script>
    <style>
        body {
            font-family: "\5FAE\8F6F\96C5\9ED1", "\5B8B\4F53", "\534E\6587\9ED1\4F53", Arial Narrow, arial, serif;
            background: #dddddd;
            font-size: 14px;
        }

        html, body {
            height: 100%
        }

        body, div, ul, ol, form, input, textarea {
            padding: 0;
            margin: 0;
            color: #2b2b2b;
        }

        table, td, tr, th {
            font-size: 14px;
        }

        li {
            list-style-type: none;
            list-style: none;
        }

        table {
            margin: 0 auto;
            border-spacing: 0;
            border-collapse: collapse;
        }

        img {
            border: 0;
        }

        ol, ul {
            list-style: none;
        }

        .red {
            border: 1px solid red;
        }

        .blue {
            border: 1px solid blue;
        }

        a {
            color: #454545;
            text-decoration: none;
        }

        input {
            font-family: "\5FAE\8F6F\96C5\9ED1", "\5B8B\4F53", "\534E\6587\9ED1\4F53"
        }

        .main {
            width: 100%;
            height: 100%;
            margin: 0 auto;
        }

        .mbox {
            margin: 0 auto;
            width: 100%;
            min-height: 600px;
            height: auto;
            background: url(images/bgi.png) no-repeat center center;
        }

        .mbox .logo {
            background: url(images/logo.png) no-repeat center center;
            width: 224px;
            height: 450px;
            margin: 0 auto;
            padding-bottom: 20px;
            overflow: hidden;
        }

        .mbox .login {
            height: 45px;
            line-height: 45px;
            background: #b63100;
            width: 100%;
            margin: 0 auto;
            text-align: center;
            padding-top: 15px;
        }

        .mbox .login .tp {
            width: 1000px;
            margin: 0 auto;
            height: 26px;
            line-height: 26px;
            padding: 0 15px;
        }

        .tp_name {
            color: #ffffff;
            font-size: 14px;
            padding-left: 15px;
        }

        .left {
            float: left;
            line-height: 26px;
            height: 26px;
            vertical-align: middle;
        }

        .mbox .login .tp a {
            color: #ffffff;
        }

        input, img a {
            vertical-align: middle;
            magrin-right: 10px;
        }

        .loginInput {
            width: 110px;
            height: 24px;
            line-height: 24px;
            border: 1px solid #ededed;
            font-size: 14px;
            padding-left: 4px;
            letter-spacing: 0px;
            font-weight: bold;
        }

        .btn {
            width: 65px;
            height: 26px;
            line-height: 26px;
            border: none;
            background: #7c2100;
            margin: 0 15px;
            color: #ffffff;
        }

        .hidden {
            display: none;
        }

        .note {
            margin: 0px;
        }

        .note li {
            line-height: 22px;
            color: #CDE7FF;
            list-style-image: url(images/login_dot.gif);
        }

        .loginForm {
            color: #454545;
        }
    </style>
</head>
<body>
<div id="dd" style="padding:5px;width:320px;height:180px;color:#fff;display:none" title="温馨提示">
    <div style="font-size:13px;text-algin:left;line-height:24px;padding:10px;"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;检测到浏览器版本为IE6.0，为了更好的管理后台和操作，建议升级到IE7以上版本,<a
            href='http://download.microsoft.com/download/1/6/1/16174D37-73C1-4F76-A305-902E9D32BAC9/IE8-WindowsXP-x86-CHS.exe'
            target="_blank"><font style="color:red;">IE下载安装</font></a>,谢谢!
    </div>
</div>
<div class="main">
    <div class="mbox">
        <div class="logo"></div>

        <div class="login">
            <div class="tp">
                <form id="form1" name="form1" method="post" action="login.jsp" target="submitFrame">
                    <span class="left tp_name" style="padding-left:202px;">用户名：</span>
                    <span class="left"><input id="username" name="username" type="text" class="loginInput"
                                              maxlength="20" value=""/></span>
                    <span class="left tp_name">密码：</span>
                    <span class="left"><input id="password" name="password" type="password" class="loginInput"
                                              maxlength="20" value=""/></span>
                    <span class="left tp_name">验证码：</span>
                    <span class="left "><input id="auth_code" name="auth_code" type="text" class="loginInput"
                                               style="width:52px;" maxlength="6" value=""/>&nbsp;&nbsp;<img id="img"
                                                                                                            width="60"
                                                                                                            height="26"
                                                                                                            src="createImage.jsp"
                                                                                                            style=" height:26px; line-height:26px; overflow:hidden;vertical-align:middle;"/>
                        <!--&nbsp;&nbsp;<a href="javascript:changeCreateImage()">看不清，换一个</a>--></span>
                    <span class="left"><input id="btnSubmit" name="btnSubmit" type="submit" onClick="checkCookie()"
                                              class="btn" value="登 录"/></span>
                </form>
            </div>
        </div>

        <iframe id="submitFrame" name="submitFrame" src="" frameborder="0" width="0" height="0"></iframe>
    </div>
</div>
</body>
</html>
