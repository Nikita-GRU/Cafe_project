<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="ru-RU"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <meta http-equiv="Cache-Control" content="private">
    <title><fmt:message bundle="${message}" key="header.welcome"/></title>
</head>
<body>
<c:set var="role" value="admin"/>

<link rel="stylesheet" href="${pageContext.request.contextPath}\css\bootstrap.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}\css\styles.css" type="text/css"/>
<nav class="navbar navbar-expand-lg">
    <div class="collapse navbar-collapse " id="navbarSupportedContent">
        <ul class="navbar-nav mr-4">
            <li class="nav-item"><a class="nav-link font-italic font-weight-bolder" href="${pageContext.request.contextPath}">EPAM CAFE</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\menu"><fmt:message
                    bundle="${message}" key="header.menu"/></a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\signup"><fmt:message
                    bundle="${message}" key="header.signup"/></a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\login"><fmt:message
                    bundle="${message}" key="header.login"/></a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\cart"> <fmt:message
                    bundle="${message}" key="header.cart"/>(${pageContext.session.getAttribute("cart_count")})

                <c:if test="${role=='moderator'}">
            <li class="nav-item"><a class="nav-link"
                                    href="${pageContext.request.contextPath}\moderator"><fmt:message bundle="${message}"
                                                                                                     key="header.moderation"/></a>
            </li>
            </c:if>

            <c:if test="${role== 'admin'}">
                <li class="nav-item"><a class="nav-link"
                                        href="${pageContext.request.contextPath}\moderator"><fmt:message
                        bundle="${message}" key="header.moderation"/></a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\admin"><fmt:message
                        bundle="${message}" key="header.admin"/></a></li>
            </c:if>
        </ul>
    </div>
</nav>

</body>

</html>
