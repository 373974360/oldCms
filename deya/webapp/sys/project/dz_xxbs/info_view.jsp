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
                defaultBean = XxbsRPC.getXxbs(infoid);
                if(defaultBean){
                    if(defaultBean.info_status==1){
                        $("#listTable_0").show();
                    }
                    $("h1").html(defaultBean.title);
                    $("#released_dtime").html(defaultBean.released_dtime);
                    $("#do_dept_name").html(defaultBean.do_dept_name);
                    $("#editor").html(defaultBean.editor);
                    $("#contents").html(defaultBean.contents);
                    if(defaultBean.auto_desc.length>0){
                        $("#auto_desc_view").show();
                        $("#auto_desc").html(defaultBean.auto_desc);
                    }else{
                        $("#auto_desc_view").hide();
                    }
                }
            }
        });


        //退稿
        var noPassList;
        function noPass(desc) {
            var selectList;
            if (noPassList != null){
                selectList = noPassList;
            } else {
                top.msgWargin("获取信息失败");
                return;
            }
            if (XxbsRPC.updateNoPassStatus(selectList, desc)) {
                top.msgAlert("退回操作成功");
                top.getCurrentFrameObj(topnum).reloadInfoDataList();
                top.tab_colseOnclick(top.curTabIndex);
            } else {
                top.msgWargin("退回操作失败");
            }
            noPassList = null;
        }
        function noPassDesc(bean) {
            noPassList = new List();
            noPassList.add(bean);
            top.OpenModalWindow("退稿意见", "/sys/project/dz_xxbs/noPassDesc.jsp", 520, 235);
        }


        //采用
        var passList;
        function openWindowForMov(bean) {
            passList = new List();
            passList.add(bean);
            top.OpenModalWindow("信息采用", "/sys/project/dz_xxbs/infoMove.jsp?site_id=" + site_id + "&app_id=" + app, 400, 460);
        }
        function moveInfoHandl(cat_id) {
            var selectList;
            if (passList != null){
                selectList = passList;
            } else {
                top.msgWargin("获取信息失败");
                return;
            }
            if(XxbsRPC.infoPass(selectList, cat_id, app, site_id)){
                top.msgAlert("采用成功");
                top.getCurrentFrameObj(topnum).reloadInfoDataList();
                top.tab_colseOnclick(top.curTabIndex);
            }else{
                top.msgWargin("采用失败");
            }
        }
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
<div class="infoListTable hidden" id="listTable_0">
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle">
                <input id="btn539" name="btn1" type="button" value="采用" onclick="openWindowForMov(defaultBean)"/>
                <input id="btn540" name="btn2" type="button" value="退稿" onclick="noPassDesc(defaultBean)"/>
            </td>
        </tr>
    </table>
</div>
<div style="widht:100%;min-height:600px;background:#FFF;border:1px solid #ececec;margin-top:10px;">
    <h1></h1>
    <div class="con_title_msg">
        报送时间：<span id="released_dtime"></span>
        报送单位：<span id="do_dept_name"></span>
        报送人：<span id="editor"></span>
        <span id="auto_desc_view" class="hidden">退稿意见：<span id="auto_desc" style="color:red;font-weight: bold;"></span></span>
    </div>
    <div class="contents" id="contents">
    </div>
</div>
</body>
</html>