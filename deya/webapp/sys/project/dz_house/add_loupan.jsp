<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
    String site_id = request.getParameter("site_id");
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>维护楼盘信息</title>
    <jsp:include page="../../include/include_tools.jsp"/>

    <script type="text/javascript" src="../../js/uploadTools.js"></script>
    <script type="text/javascript" src="../../js/jquery.uploadify.js"></script>
    <script type="text/javascript" src="../../js/uploadFile/swfobject.js"></script>
    <script type="text/javascript" src="js/loupanList.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=IaSjbPQY5jjbaGaWFLuLUvRnn6EgPdhP"></script>
    <style type="text/css">
        #allmap {overflow: hidden;margin:0;font-family:"微软雅黑";}
        a{cursor: pointer}
    </style>
    <SCRIPT LANGUAGE="JavaScript">
        var site_id = "<%=site_id%>";
        var id = request.getParameter("id");
        if ( id== "" || id == null) {
            window.close();
        }
        var defaultBean = loupanBena;
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            init_input();
            initUeditor("remark");
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");
            if (id != null && id.trim() != "") {
                defaultBean = LouPanRPC.getLouPanById(id);
                if (defaultBean) {
                    $("#loupan").autoFill(defaultBean);
                    setV("remark",defaultBean.remark);
                }
                $("#addButton").click(insertLouPan);
            }
            else {
                $("#addButton").click(insertLouPan);
            }

            $(".propertytype a").click(function(){
                var propertytype = $("#propertytype").val();
                if(propertytype==""){
                    propertytype = $(this).html();
                }else{
                    propertytype+=","+$(this).html();
                }
                $("#propertytype").val(propertytype);
            });
            $(".jzlx a").click(function(){
                var jzlx = $("#jzlx").val();
                if(jzlx==""){
                    jzlx = $(this).html();
                }else{
                    jzlx+=","+$(this).html();
                }
                $("#jzlx").val(jzlx);
            });
            $(".zxzk a").click(function(){
                var zxzk = $("#zxzk").val();
                if(zxzk==""){
                    zxzk = $(this).html();
                }else{
                    zxzk+=","+$(this).html();
                }
                $("#zxzk").val(zxzk);
            });
            $(".jzjg a").click(function(){
                var jzjg = $("#jzjg").val();
                if(jzjg==""){
                    jzjg = $(this).html();
                }else{
                    jzjg+=","+$(this).html();
                }
                $("#jzjg").val(jzjg);
            });
        });
    </SCRIPT>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
    <div id="loupan">
        <input type="hidden" id="id" name="id" value="0"/>
        <table id="jcxx_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th><span class="f_red">*</span>楼盘名称：</th>
                <td class="width200">
                    <input id="name" name="name" type="text" class="width200" value=""/>
                </td>
                <th><span class="f_red">*</span>联系电话：</th>
                <td class="width200">
                    <input id="tel" name="tel" type="text" class="width200" value=""/>
                </td>
                <td>
                    搜索:<input type="text" id="suggestId" size="20" class="width200"/>
                    <div id="searchResultPanel" style="border:1px solid #C0C0C0;width:200px;height:auto; display:none;"></div>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>地理坐标：</th>
                <td>
                    <input id="coordinate" name="coordinate" type="text" readonly="readonly" class="width200"/>
                </td>
                <th><span class="f_red">*</span>地理位置：</th>
                <td>
                    <input id="address" name="address" type="text" class="width200" value=""/>
                </td>
                <td rowspan="12" valign="top">
                    <div id="allmap"></div>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>开盘时间：</th>
                <td>
                    <input id="opentime" name="opentime" type="text" class="width200" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
                </td>
                <th><span class="f_red">*</span>开发商：</th>
                <td>
                    <input id="developers" name="developers" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>参考单价：</th>
                <td>
                    <input id="ckdj" name="ckdj" type="text" class="width200" value=""/>
                </td>
                <th><span class="f_red">*</span>物业公司：</th>
                <td>
                    <input id="property" name="property" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>置业类型：</th>
                <td colspan="3" class="propertytype">
                    <input id="propertytype" name="propertytype" type="text" class="width200" value=""/>
                    [<a>商品房</a>]
                    [<a>商业用房</a>]
                    [<a>廉租房</a>]
                    [<a>限价房</a>]
                    [<a>保障性住房</a>]
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>建筑类型：</th>
                <td colspan="3" class="jzlx">
                    <input id="jzlx" name="jzlx" type="text" class="width200" value=""/>
                    [<a>底层</a>]
                    [<a>多层</a>]
                    [<a>小高层</a>]
                    [<a>高层</a>]
                    [<a>超高层</a>]
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>装修状况：</th>
                <td colspan="3" class="zxzk">
                    <input id="zxzk" name="zxzk" type="text" class="width200" value=""/>
                    [<a>毛坯</a>]
                    [<a>精装修</a>]
                    [<a>豪华装修</a>]
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>建筑结构：</th>
                <td colspan="3" class="jzjg">
                    <input id="jzjg" name="jzjg" type="text" class="width200" value=""/>
                    [<a>板式楼</a>]
                    [<a>砖混</a>]
                    [<a>钢混</a>]
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>房屋总套数：</th>
                <td>
                    <input id="zts" name="zts" type="text" class="width200" value=""/>
                </td>
                <th><span class="f_red">*</span>物业费：</th>
                <td>
                    <input id="wyf" name="wyf" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>绿化率：</th>
                <td>
                    <input id="lhl" name="lhl" type="text" class="width200" value=""/>
                </td>
                <th><span class="f_red">*</span>容积率：</th>
                <td>
                    <input id="rjl" name="rjl" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>车位比列：</th>
                <td>
                    <input id="cwbl" name="cwbl" type="text" class="width200" value=""/>
                </td>
                <th><span class="f_red">*</span>土地使用年限：</th>
                <td>
                    <input id="tdsynx" name="tdsynx" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>预售证号：</th>
                <td>
                    <input id="yszh" name="yszh" type="text" class="width200" value=""/>
                </td>
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
                <th><span class="f_red">*</span>施工单位：</th>
                <td>
                    <input id="sgdw" name="sgdw" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th>楼盘描述：</th>
                <td colspan="4">
                    <script id="remark" type="text/plain" style="width:1050px;height:400px;"></script>
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
                <input id="userAddCancel" name="btn1" type="button"  onclick="window.location.href='loupanList.jsp'" value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</form>
</body>
<script type="text/javascript" src="js/baiduMap.js"></script>
</html>
