<%@ page contentType="text/html; charset=utf-8" %>
<%
    String code = request.getParameter("code");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>维护房间信息</title>
    <jsp:include page="../../include/include_tools.jsp"/>
    <script type="text/javascript" src="js/houseList.js"></script>
    <SCRIPT LANGUAGE="JavaScript">
        code = "<%=code%>";
        if(code==null){
            code="";
        }
        var id = request.getParameter("id");
        if ( id== "" || id == null) {
            window.close();
        }
        var defaultBean = houseBena;
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            init_input();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");
            if (id != null && id.trim() != "") {
                defaultBean = HouseRPC.getHouseById(id);
                if (defaultBean) {
                    $("#house").autoFill(defaultBean);
                }
                $("#addButton").click(insertHouse);
            }else {
                $("#addButton").click(insertHouse);
            }
        });
    </SCRIPT>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
    <div id="house">
        <input type="hidden" id="id" name="id" value="0"/>
        <input type="hidden" id="housecode" name="housecode" value="<%=code%>"/>
        <table id="jcxx_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th><span class="f_red">*</span>房间号：</th>
                <td>
                    <input id="fjh" name="fjh" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>所在单元：</th>
                <td>
                    <input id="szdy" name="szdy" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>所在楼层：</th>
                <td>
                    <input id="szlc" name="szlc" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>建筑面积：</th>
                <td>
                    <input id="jzmj" name="jzmj" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>使用面积：</th>
                <td>
                    <input id="symj" name="symj" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>房间朝向：</th>
                <td>
                    <input id="cx" name="cx" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>房间类型：</th>
                <td>
                    <input id="fjlx" name="fjlx" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>房间状态：</th>
                <td>
                    <input id="fjzt" name="fjzt" type="text" class="width200" value=""/>
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
                <input id="userAddCancel" name="btn1" type="button"  onclick="window.location.href='houseList.jsp'" value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>

</form>
</body>
</html>
