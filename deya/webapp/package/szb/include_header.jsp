<%@ page import="com.deya.project.dz_szb.SzbData" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.deya.project.dz_szb.SzbBean" %>
<%@ page import="com.deya.project.dz_szb.SzbManager" %><%--
  Created by IntelliJ IDEA.
  User: yangyan
  Date: 2017/2/16
  Time: 下午8:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    //版面导航内容，根据期刊 id 查询版面列表
    String i = request.getParameter("i");
    if (StringUtils.isNoneBlank(i)) {
        request.setAttribute("i", Integer.valueOf(i));
    }


    String id = request.getParameter("id");
    if (id != null) {
        try {
            //上一期
            SzbBean prevSzb = SzbManager.getPrevSzb(Integer.valueOf(id));
            //下一期
            SzbBean nextSzb = SzbManager.getNextSzb(Integer.valueOf(id));

            if (prevSzb != null) {
                request.setAttribute("prev", prevSzb);
            }

            if (nextSzb != null) {
                request.setAttribute("next", nextSzb);
            }
        }catch(NumberFormatException e){

        }
    }



%>
<div class="logo_bg">
    <div class="logo">
        <div>
            <%--<div class="button right">--%>
                <%--<input type="text" class="input1" placeholder="请输入关键字"/>--%>
                <%--<input type="button" class="input2"/>--%>
            <%--</div>--%>
        </div>
    </div>
</div>
<div class="nav_bg">
    <div class="nav" style="position:relative">
            <div><a href="./page.jsp">首页</a></div>
            <span>|</span>
            <div><a href="./history.jsp">往期回顾</a></div>
            <c:if test="${bean!=null}">
                <span>|</span>
                <div class="nav_a">
                    <a href="#" >版面导航</a>
                    <div>
                        <ul>
                            <c:if test="${bean.json!=null &&not empty bean.json}">
                                <c:forEach items="${bean.json}" var="img" varStatus="s">
                                    <li class="${s.index==i?'li_bg':''}"><a  href="./page.jsp?id=${bean.id}&i=${s.index}">${img.t}</a><span> &gt;</span></li>
                                </c:forEach>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </c:if>
        <c:if test="${bean!=null}">
        <div style="position:absolute;right:0px;width:760px;">
                <i class="margin_left">陕西中烟报</i>
                <i><fmt:formatDate value="${bean.pubDate}" pattern="yyyy年MM月dd日"/> </i>
                <i><fmt:formatDate value="${bean.pubDate}" pattern="EEEE"/></i>

                <c:choose>
                    <c:when test="${prev!=null}">
                        <a href="./page.jsp?id=${prev.id}" class="margin_left1">
                            &lt;上一期
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="#" class="margin_left1">
                            &lt;上一期
                        </a>
                    </c:otherwise>
                </c:choose>

                <c:choose>
                    <c:when test="${next!=null}">
                        <a href="./page.jsp?id=${next.id}" class="margin_left1">下一期 &gt;</a>
                    </c:when>
                    <c:otherwise>
                        <a href="#" class="margin_left1">
                            下一期 &gt;
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>

        </c:if>


    </div>
</div>
<div style="clear:both"></div>
