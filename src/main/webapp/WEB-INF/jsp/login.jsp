<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="ru-RU"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head><title><fmt:message bundle="${message}" key="header.login"/></title>
    <jsp:include page="common/header.jsp"/>
    <link href="${pageContext.request.contextPath}\css\signin.css" rel="stylesheet">
</head>
<%--<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/loginstyle.css"--%>


<%--<form name="loginForm" method="POST" action="main">--%>
<%--    <input type="hidden" name="command" value="login"/>--%>
<%--    Login:<br/>--%>
<%--    <input type="text" name="email" value=""/>--%>
<%--    <br/>Password:<br/>--%>
<%--    <input type="password" name="password" value=""/>--%>
<%--    <br/>--%>
<%--    <input type="submit" value="Log in"/>--%>

<body class="text-center">
<form class="form-signin" method="POST" action="main">
    <h1 class="h3 mb-3 font-weight-normal"><fmt:message bundle="${message}" key="login.please_login"/></h1>
    <label for="inputEmail" class="sr-only">Email address</label>
    <input name="email" type="email" id="inputEmail" class="form-control" placeholder="<fmt:message bundle="${message}" key="login.email_address"/>" required
           autofocus>
    <label for="inputPassword" class="sr-only">Password</label>
    <input name="password" type="password" id="inputPassword" class="form-control" placeholder="<fmt:message bundle="${message}" key="login.password"/>" required>
    <div class="checkbox mb-3">
        <label>
            <input type="checkbox" value="remember-me"> <fmt:message bundle="${message}" key="login.remember_me"/>
        </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block button-color" type="submit"><fmt:message bundle="${message}" key="login.log_in"/></button>
</form>
</body>

<br/>
${errorLoginPassMessage}
<br/>
${wrongAction}
<br/>
${nullPage}
<br/>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
