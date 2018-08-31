<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String app_id = request.getParameter("app");
    String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>电子报管理</title>
    <jsp:include page="../../include/include_tools.jsp"/>
    <script src="js/jquery.image-maps.js"></script>
    <script type="text/javascript" src="../../js/jquery.uploadify.js"></script>
    <script type="text/javascript" src="../../js/uploadFile/swfobject.js"></script>
    <script type="text/javascript" src="../../js/uploadTools.js"></script>

    <script type="text/javascript">
        var id = request.getParameter("id");
        var site_id = '${param.site_id}';
        var SzbRPC = jsonrpc.SzbRPC;
        var SzbBean = new Bean("com.deya.project.dz_szb.SzbBean", true);
        var MateFolderRPC = jsonrpc.MateFolderRPC;
        var MateInfoRPC = jsonrpc.MateInfoRPC;
        var InfoBaseRPC = jsonrpc.InfoBaseRPC;
        var MateInfoBean = new Bean("com.deya.wcm.bean.material.MateInfoBean",true);
        var user_id = jsonrpc.UserLoginRPC.getUserBySession().user_id;
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");
        })
        function getAddPagebyModel(model_id)
        {
            var add_page = jsonrpc.ModelRPC.getModelAddPage(model_id);
            if(add_page == "" || add_page == null)
                add_page = "m_article.jsp";
            return add_page;
        }
    </script>

    <style type="text/css">
        map area {
            background: #f00;
        }

        .image-maps-container {

        }

        .imgMap {
            padding:20px;
            border:solid 1px #777;
            position: relative;
            /*text-align: center;*/
        }
        .imgMap .pic{
            width:421px;
            height:622px;
        }

        .imgMap .mask:hover {
            text-decoration: none;
        }

        .link-container {

            margin-left: 14px;
        }

        .link-container .button-container {
            height: 30px;
            line-height: 30px;
        }

        .link-container .button-container input {

            height: 36px;
            width: 100px;
            line-height: 36px;
            font-size: 16px;
            margin-right: 10px;
        }

        .link-container .map-link {
            line-height: 34px;
        }

        .link-container .map-link input {
            font-size: 14px;
            line-height: 26px;
            height: 26px;
        }

        .link-number-text {
            color: #000;
            /*line-height: 100%;*/
            background: #fff;
        }

        .map-position {
            background: rgba(138, 255, 100, 0.6);
        }
    </style>
</head>
<body>
<form id="form1" name="form1" action="#" method="post" style="width:650px;">
    <input id="app_id" name="app_id" type="hidden" class="width200" value="<%=app_id%>"/>
    <input id="site_id" name="site_id" type="hidden" class="width200" value="<%=site_id%>"/>

    <div>
        <label>数字报标题：</label> <input id="title" name="title" type="text" class="width200" value=""/> <span style="color:red">上传的图片尺寸为<span style="font-size: 20px;">421px(宽) x 622px（高）</span>，双击修改文章内容</span>
    </div>

    <div>
        <label >刊发日期：</label> <input id="pubDate" name="pubDate" type="text" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="width200" value=""/>  <span style="color:red">格式为 yyyy-MM-dd，用来前台显示当期日期</span>
    </div>


    <div id="imgPages">

    </div>
    <span class="blank12"></span>
    <div class="line2h"></div>
    <span class="blank3"></span>
    <table class="table_option" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="middle" style="text-indent:100px;">
                <input id="addPageButton" name="btn1" type="button"
                       value="添加版面"/>
                <input id="addButton" name="btn1" type="button" onclick="" value="保存"/>
                <input id="userAddReset" name="btn1" type="button" onclick="formReSet('templateCategory_table',tcat_id)"
                       value="重置"/>
                <input id="userAddCancel" name="btn1" type="button" onclick="closeTemplateCategoryPage();" value="取消"/>
            </td>
        </tr>
    </table>
    <span class="blank3"></span>
</form>


<script type="text/javascript" language="javascript">
    var areaJSON = [];
    $(function () {


        var imgPages = $('#imgPages');
        if (id != null) {
            edit();
        }
        function edit() {
            //根据数据初始化编辑页面
            var szb = SzbRPC.getSzb(new Map().put("id", id));
            areaJSON = eval(szb.jsonData);
            $('#title').val(szb.title);

            var d = new Date(szb.pubDate.time);
            $('#pubDate').val(d.getFullYear() + "-" + (1+d.getMonth()) + '-' + d.getDate());
            //初始化现有数据
            for (var i = 0; i < areaJSON.length; i++) {
                createPage(areaJSON[i], i);
            }
        }


        /**
         * 创建版面
         * @param obj
         * @param i
         */
        function createPage(obj, i) {
//            var map = $().children('div').clone();
            var id = "imgMap" + i;
            var uploadButtonId = id+"_upload";
            var delButtonId = id + "_del";
            imgPages.append(
                '<div id="' + id + '" class="imgMap">' +
                '<div > 版面标题：<input type="text" name="t" placeholder="请输入版面标题" value="' + obj.t + '"/> <input id="'+delButtonId+'" type="button" value="删除版面" /><input name="import" type="button" value="导入信息" /> <br />  版面图片：<input  id="'+uploadButtonId+'" type="button" value="上传图片" /> </div>' +
                '<img class="pic" src="' + obj.imgUrl + '"  border="0" usemap="#Map' + i + '" ref="imageMaps"/>' +
                '<map name="Map' + i + '" id="Map' + i + '"></map>' +
                '<div class="" style="clear:both;"></div>' +
                '</div>');

            $('#' + id).imageMaps({
                areas: obj.areas
            });
            $('#' + id).find('input[name=import]').click(function () {
                //调出文章选择窗口
                top.ModalWindowCallback = (function(window,data){
                    return function(list){
                        var i=0;
                        var length = list.size();
                        for(i=0;i<length;i++) {
                            var arti = list.get(i);
                            $('#' + id).imageMaps('addArea', {
                                id:arti.id,
                                title:arti.title,
                                href:arti.content_url,
                                model_id:arti.model_id,
                                cid:arti.cat_id
                            });
                        }
                    }
                })(window,{
                    imgMap:id
                });
                top.OpenModalWindow("信息获取","/sys/project/dz_szb/infoGet.jsp?site_id="+site_id+"&app_id=cms",800,530);
            });

            $('#'+delButtonId).click(function () {
                $(this).closest('.imgMap').remove();
            });

            window[uploadButtonId+"_callback"] = function(url){
                $('#' + id).find('.pic').attr('src', url);
            };

            //初始化上传图片的按钮
            publicUploadButtomLoad(uploadButtonId,true,false,"",0,5,site_id,uploadButtonId+"_callback");
        };

        //添加页面按钮
        $('#addPageButton').click(function () {
//            var img = prompt("请输入图片地址：");
//            if (img != null) {
                var obj = {
                    t: '',
                    imgUrl: '',
                    areas: []
                };
                areaJSON.push(obj);
                createPage(obj, areaJSON.length);
//            }
        });

        //保存按钮
        $("#addButton").click(function () {
            var jsonData = [];
            $('.imgMap').each(function () {
                var _this = $(this);
                var arr = [];
                //循环每个区块将数据保存到数组中
                var mapDiv = $('.map-position', _this);
                mapDiv.each(function () {
                    var _this = $(this);
                    var pos = $(this).position();
                    var a = _this.find('.link-number-text');
                    var obj = {
                        id:a.data('id'),
                        w: parseInt($(this).width()),
                        h: parseInt($(this).height()),
                        x: parseInt(pos.left),
                        y: parseInt(pos.top),
                        t: a.text(),
                        href: a.attr('href')
                    };
                    arr.push(obj);
                });

                var result = {
                    t: _this.find('input[name=t]').val(),
                    imgUrl: _this.find('.pic').attr('src'),
                    areas: arr
                };
                jsonData.push(result);
            });

            var map = new Map();
            map.put("title", $('#title').val());
            map.put("jsonData", JSON.stringify(jsonData));
            map.put("pubDate", $('#pubDate').val());

            if (id) {
                map.put("id", id);
                try {
                    var ok = SzbRPC.updateSzb(map);
                    if (ok) {
                        //关闭 tab
                        top.tab_colseOnclick(top.curTabIndex);
                    } else {
                        alert("修改失败");
                    }
                } catch (e) {
                    alert(e.message);
                }
            } else {
                try {
                    var ok = SzbRPC.addSzb(map);
                    if (ok) {
                        //关闭 tab
                        top.tab_colseOnclick(top.curTabIndex);
                    } else {
                        alert("添加失败");
                    }
                } catch (e) {
                    alert(e.message);
                }
            }
        });
    });


</script>
</body>


</html>
