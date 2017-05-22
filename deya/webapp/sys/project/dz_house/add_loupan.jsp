<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>维护楼盘信息</title>
    <jsp:include page="../../include/include_tools.jsp"/>
    <script type="text/javascript" src="js/loupanList.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=IaSjbPQY5jjbaGaWFLuLUvRnn6EgPdhP"></script>
    <style type="text/css">
        #allmap {overflow: hidden;margin:0;font-family:"微软雅黑";}
    </style>
    <SCRIPT LANGUAGE="JavaScript">
        var id = request.getParameter("id");
        if ( id== "" || id == null) {
            window.close();
        }
        var defaultBean = loupanBena;
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            init_input();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");
            if (id != null && id.trim() != "") {
                defaultBean = LouPanRPC.getLouPanById(id);
                if (defaultBean) {
                    $("#loupan").autoFill(defaultBean);
                }
                $("#addButton").click(insertLouPan);
            }
            else {
                $("#addButton").click(insertLouPan);
            }
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
                <td>
                    <input id="name" name="name" type="text" class="width200" value=""/>
                </td>
                <td>
                    搜索:<input type="text" id="suggestId" size="20" class="width200"/>
                    <div id="searchResultPanel" style="border:1px solid #C0C0C0;width:200px;height:auto; display:none;"></div>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>联系电话：</th>
                <td>
                    <input id="tel" name="tel" type="text" class="width200" value=""/>
                </td>
                <td rowspan="21">
                    <div id="allmap"></div>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>地理坐标：</th>
                <td colspan="2">
                    <input id="coordinate" name="coordinate" type="text" readonly="readonly" class="width200"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>地理位置：</th>
                <td colspan="2">
                    <input id="address" name="address" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>开盘时间：</th>
                <td colspan="2">
                    <input id="opentime" name="opentime" type="text" class="width200" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>开发商：</th>
                <td colspan="2">
                    <input id="developers" name="developers" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>物业公司：</th>
                <td colspan="2">
                    <input id="property" name="property" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>物业类型：</th>
                <td colspan="2">
                    <select id="propertytype" name="propertytype" class="width200">
                        <option value="">请选择物业类型</option>
                        <option value="住宅">住宅</option>
                        <option value="商业">商业</option>
                        <option value="公寓">公寓</option>
                        <option value="商住两用">商住两用</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>建筑类型：</th>
                <td colspan="2">
                    <select id="jzlx" name="jzlx" class="width200">
                        <option value="">请选择建筑类型</option>
                        <option value="多层">多层</option>
                        <option value="小高层">小高层</option>
                        <option value="高层">高层</option>
                        <option value="别墅">别墅</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>装修状况：</th>
                <td colspan="2">
                    <select id="zxzk" name="zxzk" class="width200">
                        <option value="">请选择装修类型</option>
                        <option value="毛坯">毛坯</option>
                        <option value="精装修">精装修</option>
                        <option value="豪华装修">豪华装修</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>预售证号：</th>
                <td colspan="2">
                    <input id="yszh" name="yszh" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>房屋总套数：</th>
                <td colspan="2">
                    <input id="zts" name="zts" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>物业费：</th>
                <td colspan="2">
                    <input id="wyf" name="wyf" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>绿化率：</th>
                <td colspan="2">
                    <input id="lhl" name="lhl" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>容积率：</th>
                <td colspan="2">
                    <input id="rjl" name="rjl" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>车位比列：</th>
                <td colspan="2">
                    <input id="cwbl" name="cwbl" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>土地使用年限：</th>
                <td colspan="2">
                    <input id="tdsynx" name="tdsynx" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>建筑结构：</th>
                <td colspan="2">
                    <select id="jzjg" name="jzjg" class="width200">
                        <option value="">请选择建筑结构</option>
                        <option value="板式楼">板式楼</option>
                        <option value="砖混">砖混</option>
                        <option value="砖木">砖木</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>建筑面积：</th>
                <td colspan="2">
                    <input id="jzmj" name="jzmj" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>占地面积：</th>
                <td colspan="2">
                    <input id="zdmj" name="zdmj" type="text" class="width200" value=""/>
                </td>
            </tr>
            <tr>
                <th><span class="f_red">*</span>施工单位：</th>
                <td colspan="2">
                    <input id="sgdw" name="sgdw" type="text" class="width200" value=""/>
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
