<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Cache-Control" content="private">
    <title>Welcome</title>
</head>
<body>
<c:set var="role" value="admin"/>

<link rel="stylesheet" href="${pageContext.request.contextPath}\css\bootstrap.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}\css\styles.css" type="text/css"/>
<nav class="navbar navbar-expand-lg">
    <div class="collapse navbar-collapse " id="navbarSupportedContent">
        <ul class="navbar-nav mr-4">
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}">HOME</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\menu">Menu</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\signup">Sign up</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\login">Login</a></li>

            <c:if test="${role=='moderator'}">
                <li class="nav-item"><a class="nav-link"
                                        href="${pageContext.request.contextPath}\moderator">Moderation</a></li>
            </c:if>

            <c:if test="${role== 'admin'}">
                <li class="nav-item"><a class="nav-link"
                                        href="${pageContext.request.contextPath}\moderator">Moderation</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\admin">Admin</a></li>
            </c:if>
        </ul>
    </div>
</nav>

</body>

</html>
