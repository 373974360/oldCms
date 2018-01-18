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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>信息维护</title>
    <jsp:include page="include_info_tools.jsp"/>
    <script type="text/javascript" src="js/info_add.js?v=20180117"></script>
    <script type="text/javascript">
        var site_id = "<%=siteid%>";
        var app_id = "cms";
        var contentId = "contents";
        var topnum = "<%=topnum%>";
        var infoid = "<%=infoid%>";
        $(function(){
            initUeditor(contentId);
            init_input();
            initButtomStyle();
            reloadPublicInfo();
            if(infoid != "" && infoid != "null" && infoid != null){
                defaultBean = MessageRPC.getMessage(infoid);
                if(defaultBean){
                    $("#info_article_table").autoFill(defaultBean);
                    setV(contentId,defaultBean.contents);
                    reloadPublicUpdateInfo();
                }
                $("#addButton").click(updateInfoData);
            } else {
                $("#addButton").click(addInfoData);
            }
        });
    </script>
</head>
<body>
<span class="blank12"></span>
<form action="#" name="form1">
    <div id="info_article_table">
        <input type="hidden" id="id" value="0"/>
        <input type="hidden" id="input_user" value="0"/>
        <input type="hidden" id="modify_user" value="0"/>
        <input type="hidden" id="input_dtime" value=""/>
        <input type="hidden" id="modify_dtime" value=""/>
        <input type="hidden" id="info_status" value="1"/>
        <table class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th><span class="f_red">*</span>信息标题：</th>
                <td>
                    <input id="title" name="title" type="text" class="width400" value="" onblur="checkInputValue('title',false,240,'信息标题','')"/>
                </td>
            </tr>
            </tbody>
        </table>
        <table class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th>机密等级：</th>
                <td width="150">
                    <div id="a11">
                        <select id="jimi" style="width:145px; height:18px; overflow:hidden;">
                            <option value="无">请选择</option>
                            <option value="一般">一般</option>
                            <option value="机密">机密</option>
                            <option value="绝密">绝密</option>
                        </select>
                    </div>
                </td>
                <th style="width:89px;">紧急等级：</th>
                <td width="142">
                    <div id="a12">
                        <select id="jinji" style="width:145px; height:18px; overflow:hidden;">
                            <option value="无">请选择</option>
                            <option value="一般">一般</option>
                            <option value="加急">加急</option>
                        </select>
                    </div>
                </td>
                <td></td>
                <th style="width:66px;"><span class="f_red">*</span>发送时间：</th>
                <td>
                    <input id="released_dtime" name="released_dtime" type="text"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})"
                           readonly="readonly"  onblur="checkInputValue('title',false,240,'报送时间','')"/>
                </td>
            </tr>
            </tbody>
        </table>
        <table class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th style="vertical-align:top;">详细内容：</th>
                <td style="">
                    <script id="contents" type="text/plain" style="width:95%;height:400px;"></script>
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
<input id="addButton" name="btn1" type="button" value="保存" />
<input id="addReset" name="btn1" type="button" onclick="window.location.reload()" value="重置" />
<input id="addCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="取消" />
</td>
</tr>
</table>
<span class="blank3"></span>
    </div>
</form>
</body>
</html>