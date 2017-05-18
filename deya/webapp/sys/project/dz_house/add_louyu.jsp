<%@ page contentType="text/html; charset=utf-8" %>
<%
    String lpcode = request.getParameter("lpcode");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>维护楼盘信息</title>
    <jsp:include page="../../include/include_tools.jsp"/>
    <script type="text/javascript" src="js/louyuList.js"></script>
    <SCRIPT LANGUAGE="JavaScript">
        lpcode = "<%=lpcode%>";
        if(lpcode==null){
            lpcode="";
        }
        var id = request.getParameter("id");
        if ( id== "" || id == null) {
            window.close();
        }
        var defaultBean = louyuBena;
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            init_input();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");
            if (id != null && id.trim() != "") {
                defaultBean = LouYuRPC.getLouYuById(id);
                if (defaultBean) {
                    $("#louyu").autoFill(defaultBean);
                }
                $("#addButton").click(insertLouYu);
            }else {
                $("#addButton").click(insertLouYu);
            }
        });
    </SCRIPT>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
    <div id="louyu">
        <input type="hidden" id="id" name="id" value="0"/>
        <input type="hidden" id="lycode" name="lycode" value="<%=lpcode%>"/>
        <table id="jcxx_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th><span class="f_red">*</span>楼栋号：</th>
                <td>
                    <input id="ldh" name="ldh" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>单元数：</th>
                <td>
                    <input id="dys" name="dys" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>房间总数：</th>
                <td>
                    <input id="fjzs" name="fjzs" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>建筑面积：</th>
                <td>
                    <input id="jzmj" name="jzmj" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>占地面积：</th>
                <td>
                    <input id="zdmj" name="zdmj" type="text" class="width200" value=""/>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <span class="blank12"></span>
    <div class="line2h"></div>
    <span class="blank3"></span>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle" style="text-indent:100px;">
                <input id="addButton" name="btn1" type="button" onclick="" value="保存"/>
                <input id="userAddReset" name="btn1" type="button" onclick="formReSet('loupan',id);" value="重置"/>
                <input id="userAddCancel" name="btn1" type="button"  onclick="window.location.href='louyuList.jsp'" value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>

</form>
</body>
</html>
