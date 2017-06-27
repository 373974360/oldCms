<%@ page contentType="text/html; charset=utf-8" %>
<%
    String code = request.getParameter("code");
    String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>维护房间信息</title>
    <jsp:include page="../../include/include_tools.jsp"/>
    <script type="text/javascript" src="../../js/uploadTools.js"></script>
    <script type="text/javascript" src="../../js/jquery.uploadify.js"></script>
    <script type="text/javascript" src="../../js/uploadFile/swfobject.js"></script>
    <script type="text/javascript" src="js/houseList.js"></script>
    <SCRIPT LANGUAGE="JavaScript">
        code = "<%=code%>";
        var site_id = "<%=site_id%>";
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
            initUeditor("hxt");
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
                <th><span class="f_red">*</span>户型：</th>
                <td>
                    <select id="hx" name="hx" class="width200">
                        <option value="">请选择户型</option>
                        <option value="一室">一室</option>
                        <option value="二室">二室</option>
                        <option value="三室">三室</option>
                        <option value="四室">四室</option>
                        <option value="四室以上">四室以上</option>
                        <option value="跃层">跃层</option>
                        <option value="错层">错层</option>
                        <option value="独立开间">独立开间</option>
                        <option value="其他">其他</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>房间朝向：</th>
                <td>
                    <select id="cx" name="cx" class="width200">
                        <option value="">请选择房间朝向</option>
                        <option value="朝南">朝南</option>
                        <option value="朝北">朝北</option>
                        <option value="朝东">朝东</option>
                        <option value="朝西">朝西</option>
                        <option value="南北通透">南北通透</option>
                        <option value="东西通透">东西通透</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>房间类型：</th>
                <td>
                    <select id="fjlx" name="fjlx" class="width200">
                        <option value="">请选择房间类型</option>
                        <option value="商品房">商品房</option>
                        <option value="商业用房">商业用房</option>
                        <option value="二手房">二手房</option>
                        <option value="限价房">限价房</option>
                        <option value="租赁型保障房">租赁型保障房</option>
                        <option value="购置型保障房">购置型保障房</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>房间状态：</th>
                <td>
                    <select id="fjzt" name="fjzt" class="width200">
                        <option value="">请选择房间状态</option>
                        <option value="已售">已售</option>
                        <option value="待售">待售</option>
                        <option value="不可售">不可售</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>户型图：</th>
                <td>
                    <script id="hxt" type="text/plain" style="width:600px;height:400px;"></script>
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