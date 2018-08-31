<%@ page import="com.deya.project.dz_szb.SzbBean" %>
<%@ page import="com.deya.project.dz_szb.SzbData" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%--
  Created by IntelliJ IDEA.
  User: yangyan
  Date: 2017/2/16
  Time: 下午7:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    SzbData szbData = new SzbData();


    //显示期
    String id = request.getParameter("id");
    //显示版面
    String i = request.getParameter("i");

    SzbBean szbBean = null;
    if (StringUtils.isBlank(id)) {
        // 最新一期的 id
        szbBean = szbData.getNewestSzb();
        if (szbBean != null) {
            id = String.valueOf(szbBean.getId());
        }
    } else {
        Map<String, String> params = new HashMap();
        params.put("id", id);
        szbBean = szbData.getSzb(params);
    }


    if (szbBean != null) {
        request.setAttribute("bean", szbBean);
        //显示第几个版面
        int show = 0;
        if (StringUtils.isNotBlank(i)) {
            show = Integer.parseInt(i);
            request.setAttribute("show", show);
        } else {
            request.setAttribute("show", 0);
        }
    } else {
        //id 有误
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="css/group.css"/>
    <link rel="stylesheet" type="text/css" href="css/index.css"/>
    <script src="/sys/js/jquery.js"/>
    <script src="/sys/project/dz_szb/js/jquery.image-maps.js"></script>
    <style>
        #imgPages img {
            width: 421px;
            height: 622px;
        }

        .imgMap {
            position: relative;
            /*text-align: center;*/
        }

        .imgMap .mask:hover {
            text-decoration: none;
        }

    </style>
</head>
<body>

<jsp:include page="include_header.jsp"/>


<div class="mbox">
    <div class="con">
        <div class="con_left left">
            <div id="imgPages" class="con_left1"></div>
            <div class="con_left2">
                <div class="left">${bean.json[show].t}</div>
                <div class="right">
                    <c:choose>
                        <c:when test="${show>0}">
                            <span class="span1"><a href="./page.jsp?id=${bean.id}&i=${show-1}">上一版</a></span>
                        </c:when>
                        <c:otherwise>
                            <span class="span1"><a href="javascript:;">上一版</a></span>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${(f:length(bean.json)>(show+1))}">

                    <span class="span2"><a
                            href="./page.jsp?id=${bean.id}&i=${show+1}">下一版</a></span>
                        </c:when>
                        <c:otherwise>
            <span class="span2"><a
                    href="javascript:;">下一版</a></span>
                        </c:otherwise>

                    </c:choose>
                </div>
            </div>
        </div>
        <div class="con_right left">
            <p>陕西中烟报图文数据库</p>
            <div class="con_right1 left">
                <p>${bean.json[show].t}</p>
                <div>
                    <ul>
                        <c:if test="${bean.json[show].areas!=null &&not empty bean.json[show].areas}">
                            <c:forEach items="${bean.json[show].areas}" var="a" varStatus="s">
                                <li class="${s.index%2==0?'li_bg':''}"><a
                                        href="./article.jsp?id=${bean.id}&i=${show}&j=${s.index}&&a=${a.id}" class="rp">${a.t}</a>
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                </div>
            </div>
            <div class="con_right2 right">
                <p>版面目录</p>
                <div>
                    <ul>
                        <c:if test="${bean.json!=null &&not empty bean.json}">
                            <c:forEach items="${bean.json}" var="img" varStatus="s">
                                <li><a href="./page.jsp?id=${bean.id}&i=${s.index}">${img.t}</a><span>></span></li>
                            </c:forEach></c:if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    $(function () {
//        $(window).resize(function () {
//            $('#linkList').width($(window).width() - $('#imgList').width() - 20 - $('#imgPages').width() - 60);
//        });
        var areaJSON = ${bean.jsonData};
        var imgPages = $('#imgPages');
        preview();
//        var ji = location.hash.substring(1);
//        $('#imgList li a').click(function () {
//            $('#linkTitle').text("文章导航");
//            $(this).addClass('current').parent().siblings().find('a').removeClass('current');
//            var p = $('#imgPages .imgMap').eq($(this).parent().index());
//            p.show().siblings().hide();
//            $('#linkList').width($(window).width() - $('#imgList').width() - 20 - $('#imgPages').width() - 60);
//            $('#linkList ul').empty().show();
//            $('#newsFrame').hide();
//            p.find('a.mask').each(function () {
//                $('#linkList ul').append(
//                    '<li><a href="' + this.href + '" target="newsFrame" >' + $(this).text() + ' </a></li>'
//                );
//            });
//            $('#linkList ul').find('a').click(function () {
//                $('#linkList ul').hide();
//                $('#newsFrame').show();
//                $('#linkTitle').text($(this).text());
//            });
//        }).eq(ji ? ji : 0).click();
        function preview() {
            //根据数据初始化编辑页面

            var imgPages = $('#imgPages');
//            for (var i = 0; i < areaJSON.length; i++) {
            //循环版面
            var i = ${show};
            var obj = areaJSON[i];


            var id = "imgMap" + i;
            var areas = "";
            var links = "";
            //循环热区数据
            for (var j = 0; j < obj.areas.length; j++) {
                var area = obj.areas[j];
                areas += '<area style="cursor:hand;" shape="rect" coords="' + area.x + ',' + area.y + ',' + (area.x + area.w) + ',' + (area.y + area.h) + '" href="' + area.href + '" alt="' + area.t + '" />';
                links += '<a id="' + area.x + '_' + area.y + '" class="mask" href="./article.jsp?id=${bean.id}&i=' + i + '&j='+j+'&a=' + area.id + '" style="position: absolute;'
                    + 'left: ' + area.x + 'px;'
                    + 'top: ' + area.y + 'px;'
                    + 'width: ' + area.w + 'px;'
                    + 'height: ' + area.h + 'px;'
                    + 'background: #09c;'
                    + 'color: #fff;'
                    + 'opacity: .3;'
                    + 'overflow:hidden;'
                    + 'text-align: center;'
                    + 'font-size: ' + Math.sqrt(area.w * area.h / area.t.length * 0.4) + 'px;'

                    + 'display: none ;"></a>';
            }

            var h = '<div id="' + id + '" class="imgMap">' +
                '<img class="pic" src="' + obj.imgUrl + '" width="' + obj.width + '" height="' + obj.height + '" name="test" border="0" usemap="#Map' + i + '" ref="imageMaps"/>' +
                '<map name="Map' + i + '" id="Map' + i + '">' + areas + '</map>' +
                links +
                '<div class="" style="clear:both;"></div>' +
                '</div>';
            imgPages.append(h);

//                $('#imgList ul').append('<li><a href="#' + i + '">' + obj.t + '  >>  </a></li>');

//            }

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
<div class="clearBoth"></div>
<jsp:include page="include_footer.jsp"/>

</body>
</html>
