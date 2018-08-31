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
    <script type="text/javascript">
        var id = request.getParameter("id");
        var site_id = '${param.site_id}';
        var SzbRPC = jsonrpc.SzbRPC;
        var SzbBean = new Bean("com.deya.project.dz_szb.SzbBean", true);
        $(document).ready(function () {
            initButtomStyle();
            init_FromTabsStyle();
            if ($.browser.msie && $.browser.version == "6.0" && $("html")[0].scrollHeight > $("html").height()) $("html").css("overflowY", "scroll");
        })
    </script>

    <style type="text/css">

        #title {
            font-size: 30px;
            line-height: 60px;
            text-align: center;
        }

        .imgMap {
            position: relative;
            /*text-align: center;*/
        }

        .imgMap .mask:hover {
            text-decoration: none;
        }

        #imgPages {
            float: left;
        }

        #imgPages img{
            width:421px;height: 622px;
        }

        #imgList, #linkList {
            float: left;
        }

        #imgPages .imgMap {
            display: none;
        }

        #imgList, #linkList {
            width: 180px;
            border: solid 1px #ccc;
            margin-left: 10px;
            padding: 10px;
        }

        #linkList {
            width: 380px;
            height: 800px;
        }

        #imgList li, #linkList li {
            height: 28px;
            line-height: 28px;
            font-size: 16px;

        }

        #imgList li a, #linkList li a {
            display: block;
            padding: 0px 10px;
        }

        #imgList li a:hover, #imgList li a.current {
            background: #00a2d4;
            font-width: bold;
            color: #fff;
        }

        #linkList li a:hover {
            background: #ddd;
        }

    </style>
</head>
<body>
<h1 id="title"></h1>

<div id="imgPages">

</div>

<div id="imgList">
    <div style="font-weight: bold;font-size: 18px;line-height: 30px;border-bottom: solid 1px #aaa;">版面导航</div>
    <ul>

    </ul>
</div>
<div id="linkList">
    <div id="linkTitle" style="font-weight: bold;font-size: 18px;line-height: 30px;border-bottom: solid 1px #aaa;">文章导航</div>
    <ul>

    </ul>
    <iframe id="newsFrame" name="newsFrame" src="about:blank" scrolling="auto" frameborder="0"
            style="width:100%;height:760px;margin:0;padding:0px;"></iframe>
</div>


<script type="text/javascript" language="javascript">

    $(function () {
        $(window).resize(function () {
            $('#linkList').width($(window).width() - $('#imgList').width() - 20 - $('#imgPages').width() - 60);
        });
        var areaJSON = [];
        var imgPages = $('#imgPages');
        if (id != null) {
            preview();
            var ji = location.hash.substring(1);
            $('#imgList li a').click(function () {
                $('#linkTitle').text("文章导航");
                $(this).addClass('current').parent().siblings().find('a').removeClass('current');
                var p = $('#imgPages .imgMap').eq($(this).parent().index());
                p.show().siblings().hide();
                $('#linkList').width($(window).width() - $('#imgList').width() - 20 - $('#imgPages').width() - 60);
                $('#linkList ul').empty().show();
                $('#newsFrame').hide();
                p.find('a.mask').each(function () {
                    $('#linkList ul').append(
                        '<li><a href="' + this.href + '" target="newsFrame" >' + $(this).text() + ' </a></li>'
                    );
                });
                $('#linkList ul').find('a').click(function () {
                    $('#linkList ul').hide();
                    $('#newsFrame').show();
                    $('#linkTitle').text($(this).text());
                });
            }).eq(ji ? ji : 0).click();
        }
        function preview() {
            //根据数据初始化编辑页面
            var szb = SzbRPC.getSzb(new Map().put("id", request.getParameter("id")));
            $('#title,title').html(szb.title);
            areaJSON = eval(szb.jsonData);
            var imgPages = $('#imgPages');
            for (var i = 0; i < areaJSON.length; i++) {
                //循环版面
                var obj = areaJSON[i];


                var id = "imgMap" + i;
                var areas = "";
                var links = "";
                //循环热区数据
                for (var j = 0; j < obj.areas.length; j++) {
                    var area = obj.areas[j];
                    areas += '<area style="cursor:hand;" shape="rect" coords="' + area.x + ',' + area.y + ',' + (area.x + area.w) + ',' + (area.y + area.h) + '" href="' + area.href + '" alt="' + area.t + '" />';
                    links += '<a id="' + area.x + '_' + area.y + '" class="mask" href="' + area.href + '" target="newsFrame" style="position: absolute;'
                        + 'left: ' + area.x + 'px;'
                        + 'top: ' + area.y + 'px;'
                        + 'width: ' + area.w + 'px;'
                        + 'height: ' + area.h + 'px;'
                        + 'background: #09c;'
                        + 'color: #fff;'
                        + 'opacity: .8;'
                        + 'overflow:hidden;'
                        + 'text-align: center;'
                        + 'font-size: ' + Math.sqrt(area.w * area.h / area.t.length * 0.4) + 'px;'

                        + 'display: none ;">' + area.t + '</a>';
                }

                var h = '<div id="' + id + '" class="imgMap">' +
                    '<img class="pic" src="' + obj.imgUrl + '" width="' + obj.width + '" height="' + obj.height + '" name="test" border="0" usemap="#Map' + i + '" ref="imageMaps"/>' +
                    '<map name="Map' + i + '" id="Map' + i + '">' + areas + '</map>' +
                    links +
                    '<div class="" style="clear:both;"></div>' +
                    '</div>';
                imgPages.append(h);

                $('#imgList ul').append('<li><a href="#' + i + '">' + obj.t + '  >>  </a></li>');

            }

            //鼠标离开 a 则隐藏
            imgPages.find('a.mask').mouseleave(function () {
                var _this = $(this);
                _this.hide();
            }).click(function () {
//选择版面和并打开文章
                $('#linkList > ul').hide();
                $('#newsFrame').show();
                $('#linkTitle').text($(this).text());

            });

            //鼠标经过热区显示出对应的a 标签用来给用户展示标题
            $('map area').mouseover(function () {
                var _this = $(this);
                var p = _this.attr('coords').split(',');
                var x = p[0];
                var y = p[1];
                var a = $('#' + x + '_' + y, _this.closest('.imgMap'));
                a.show().siblings('.mask').hide();
            });
        }
    });
</script>
</body>
</html>
