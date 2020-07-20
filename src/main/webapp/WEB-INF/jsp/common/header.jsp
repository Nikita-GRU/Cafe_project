<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--<fmt:setLocale value="ru-RU"/>--%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <meta http-equiv="Cache-Control" content="private">
    <title><fmt:message bundle="${message}" key="header.welcome"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css"/>
    <link href="${pageContext.request.contextPath}/css/addons/datatables.min.css" rel="stylesheet">
    <!-- Material Design Bootstrap -->
    <%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mdb.min.css">--%>


</head>
<body>
<nav class="navbar navbar-expand-lg">
    <div class="collapse navbar-collapse " id="navbarSupportedContent">
        <ul class="navbar-nav mr-4">
            <%--            ${sessionScope.role}--%>
            <li class="nav-item"><a class="nav-link index-font"
                                    href="${pageContext.request.contextPath}">EPAM CAFE</a></li>
            <li class="nav-item"><a class="nav-link nav-font"
                                    href="${pageContext.request.contextPath}\menu"><fmt:message
                    bundle="${message}" key="header.menu"/></a></li>
                <c:if test="${sessionScope.cart_count >0 }">
            <li class="nav-item" ><a class="nav-link cart" href="${pageContext.request.contextPath}\cart">
                <fmt:message
                        bundle="${message}" key="header.cart"/>
                (${sessionScope.cart_count})</a></li></c:if>

                <c:if test="${sessionScope.role=='moderator'}">
                <li class="nav-item"><a class="nav-link"
                                        href="${pageContext.request.contextPath}\moderator"><fmt:message
                        bundle="${message}"
                        key="header.moderation"/></a>
                </li>
            </c:if>

            <c:if test="${sessionScope.role== 'admin'}">
                <li class="nav-item"><a class="nav-link"
                                        href="${pageContext.request.contextPath}\moderator"><fmt:message
                        bundle="${message}" key="header.moderation"/></a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\admin"><fmt:message
                        bundle="${message}" key="header.admin"/></a></li>
            </c:if>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\signup"><fmt:message
                    bundle="${message}" key="header.signup"/></a></li>

            <c:if test="${sessionScope.role =='guest' || sessionScope.role=='null'}">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\login"><fmt:message
                        bundle="${message}" key="header.login"/></a></li>
            </c:if>

            <c:if test="${sessionScope.role !='guest'}">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\profile"> <fmt:message
                        bundle="${message}" key="header.profile"/></a>
                </li>

                <li class="nav-item">
                    <form method="post"
                          action="${pageContext.request.contextPath}\controller?command=logout">
                        <input type="hidden" name="command" value="logout"/>
                        <input type="submit" value="logout">
                    </form>
                </li>
            </c:if>

            <%--            ${requestScope ['javax.servlet.forward.request_uri']}?locale=ru-RU"--%>
            <%--            ${requestScope ['javax.servlet.forward.request_uri']}?locale=en-GB--%>
            <%--                ${pageContext.request.contextPath}/${requestScope ['javax.servlet.forward.request_uri']}/--%>
            <li class="nav-item">
                <form method="post"
                      action="${pageContext.request.contextPath}\controller?command=change_locale">
                    <input type="hidden" name="command" value="change_locale"/>
                    <input type="hidden" name="new_locale" value="en-GB">
                    <input type="submit" value="eng">
                </form>
            </li>
            <li class="nav-item">
                <form method="post"
                      action="${pageContext.request.contextPath}\controller?command=change_locale">
                    <input type="hidden" name="command" value="change_locale"/>
                    <input type="hidden" name="new_locale" value="ru-RU">
                    <input type="submit" value="ru">
                </form>
            </li>
        </ul>
    </div>
</nav>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<!-- Bootstrap tooltips -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<!-- Bootstrap core JavaScript -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<!-- MDB core JavaScript -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/mdb.min.js"></script>
<!-- Your custom scripts (optional) -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/addons/datatables.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/table-script.js"></script>

</body>

</html>
