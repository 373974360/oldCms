<%@ page import="com.deya.project.dz_szb.SzbData" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.deya.project.dz_szb.SzbBean" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: yangyan
  Date: 2017/2/16
  Time: 下午7:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    SzbData szbData = new SzbData();


    int total = szbData.getSzbCount();
    int pageSize = 12;
    int pageCount = (total + pageSize - 1) / pageSize;
    String currentPage = request.getParameter("page");
    if (StringUtils.isBlank(currentPage)) {
        currentPage = "1";
    }
    int currentPageInt = Integer.valueOf(currentPage);
    if (currentPageInt > pageCount) {
        currentPageInt = 1;
    }

    List<SzbBean> szbList = szbData.getSzbList(currentPageInt, pageSize);

    request.setAttribute("total", total);
    request.setAttribute("list", szbList);
    request.setAttribute("page", currentPageInt);
    request.setAttribute("pageSize", pageSize);
    request.setAttribute("pageCount", pageCount);
    if (pageCount > currentPageInt) {
        request.setAttribute("nextPage", currentPageInt + 1);
    } else if (pageCount > 1 && currentPageInt > 1) {
        request.setAttribute("prevPage", currentPageInt - 1);
    }

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="css/group.css"/>
    <link rel="stylesheet" type="text/css" href="css/index.css"/>
</head>
<body>
<jsp:include page="include_header.jsp"/>
<div class="mbox">
    <div class="con">
        <div class="con_right2 left">
            <p>版面目录</p>
            <div class="con_right2_1">
                <ul>
<c:if test="${list!=null &&  not empty list}">
    <c:forEach items="${list}" var="bean" varStatus="s">
        <c:forEach items="${bean.json}" var="img" varStatus="ss">
            <%--<c:set value="${odd+1}" var="odd"/>--%>
            <li class="${s.index%2==0?'li_bg':''}"><a href="./page.jsp?id=${bean.id}&i=${ss.index}">${img.t}</a><i><a href="./page.jsp?id=${bean.id}">${bean.title}</a></i></li>
        </c:forEach>
    </c:forEach>
</c:if>
                </ul>
            </div>
        </div>
        <div class="index1_con right">
            <c:if test="${list!=null &&  not empty list}">
                <c:forEach items="${list}" var="bean">
                    <div>
                        <div style="overflow: hidden;"><a href="./page.jsp?id=${bean.id}"><img
                                src="${bean.json[0].imgUrl}" style="width:100%"/></a></div>
                        <span><a href="./page.jsp?id=${bean.id}">${bean.title}</a></span>
                    </div>
                </c:forEach>
            </c:if>
            <p><a class="span3" href="${prevPage!=null?'?page='+prevPage:'#'}">上一页</a><a class="span4" href="${nextPage!=null?'?page='+nextPage:'#'}">下一页</a></p>
        </div>
    </div>


</div>


<div class="clearBoth"></div>
<jsp:include page="include_footer.jsp"/>

</body>
</html>
