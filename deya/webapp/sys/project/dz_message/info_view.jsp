<%@ page contentType="text/html; charset=utf-8" %>
<%
    String siteid = request.getParameter("site_id");
    String topnum = request.getParameter("topnum");
    String infoid = request.getParameter("id");
    if(topnum == null || topnum.trim().equals("") || topnum.trim().equals("null") ){
        topnum = "0";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="include_info_tools.jsp"/>
    <script type="text/javascript" src="js/info_add.js?v=20180117"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>信息维护</title>
    <script type="text/javascript">
        var topnum = "<%=topnum%>";
        var site_id = "<%=siteid%>";
        var infoid = "<%=infoid%>";
        var app = "cms";
        $(function(){
            initButtomStyle();
            if(infoid != "" && infoid != "null" && infoid != null){
                defaultBean = MessageRPC.getMessage(infoid);
                if(defaultBean){
                    if(defaultBean.info_status==1){
                        $("#listTable_0").show();
                    }
                    $("h1").html(defaultBean.title);
                    $("#released_dtime").html(defaultBean.released_dtime);
                    $("#jimi").html(defaultBean.jimi);
                    $("#jinji").html(defaultBean.jinji);
                    $("#contents").html(defaultBean.contents);
                }
            }
        });
    </script>
    <style type="text/css">
        h1{font-size:22px;font-weight: bold;text-align: center;line-height: 50px;}
        .con_title_msg{width:90%;height:30px;margin:0 auto;border-bottom: #CCCCCC 1px solid; line-height:30px;font-size:14px;}
        .con_title_msg span{margin-right:30px;}
        .contents{font-size:16px; line-height:28px;width:90%;margin:0 auto;margin-top:20px;}
    </style>
</head>
<body>
<span class="blank12"></span>
<div style="widht:100%;min-height:600px;background:#FFF;border:1px solid #ececec;margin-top:10px;">
    <h1></h1>
    <div class="con_title_msg">
        发布时间：<span id="released_dtime"></span>
        机密类型：<span id="jimi"></span>
        紧急类型：<span id="jinji"></span>
    </div>
    <div class="contents" id="contents">
    </div>
</div>
</body>
</html>