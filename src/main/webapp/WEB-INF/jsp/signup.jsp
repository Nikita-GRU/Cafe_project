<%--
  Created by IntelliJ IDEA.
  User: JJ93
  Date: 26.05.2020
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="ru-RU"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <title><fmt:message bundle="${message}" key="header.signup"/></title>
    <jsp:include page="common/header.jsp"/>
    <link href="${pageContext.request.contextPath}\css\signin.css" rel="stylesheet">
</head>


<body class="text-center">
<form class="form-signin" method="POST" action="main">
    <input type="hidden" name="command" value="signup"/>
    <%--    <img class="mb-4" src="https://getbootstrap.com/docs/4.0/assets/brand/bootstrap-solid.svg" alt="" width="72"--%>
    <%--         height="72">--%>
    <h1 class="h3 mb-3 font-weight-normal"><fmt:message bundle="${message}"
                                                        key="signup.registration"/></h1>

    <label for="inputEmail" class="sr-only">Email address</label>
    <input name="email" type="email" id="inputEmail" class="form-control"
           placeholder="<fmt:message bundle="${message}" key="signup.email_address"/>" required
           autofocus>

    <label for="inputPassword" class="sr-only">Password</label>
    <input name="password" type="password" id="inputPassword" class="form-control"
           placeholder="<fmt:message bundle="${message}" key="signup.password"/>" required>

    <label for="inputPasswordVerify" class="sr-only">Verify password</label>
    <input name="password_verify" type="password" id="inputPasswordVerify" class="form-control"
           placeholder="<fmt:message bundle="${message}" key="signup.verify_password"/>" required>

    <label for="inputPhoneNumber" class="sr-only">Phone Number in format +375 xx xxx xx xx</label>
    <input name="phonenumber" type="tel" pattern="^(\+375|80)(29|25|44|33)(\d{3})(\d{2})(\d{2})$" id="inputPhoneNumber"
           class="form-control" placeholder="<fmt:message bundle="${message}" key="signup.phonenumber"/>"
           title="+375 xx xxx xx xx" required>

    <label for="inputFirstName" class="sr-only">Firstname</label>
    <input name="firstname" type="text" id="inputFirstName" class="form-control"
           placeholder="<fmt:message bundle="${message}" key="signup.firstname"/>" required>
    <br/>
    <button class="btn btn-lg btn-primary btn-block button-color" type="submit"><fmt:message bundle="${message}"
                                                                                             key="signup.signup"/></button>

</form>

</body>
${errorLoginPassMessage}
<br/>
${wrongAction}
<br/>
${nullPage}
<%--<form name="signUpForm" method="POST" action="main">--%>

<%--    Phone Number:--%>
<%--    <br/>--%>
<%--    <input type="number" name="phonenumber" value=""/>--%>
<%--    <br/>--%>
<%--    <br/>--%>
<%--    Password:--%>
<%--    <br/>--%>
<%--    <input type="password" name="password" value=""/>--%>
<%--    <br/>--%>
<%--    <br/>--%>
<%--    Verify password--%>
<%--    <br/>--%>
<%--    <input type="password" name="password_verify" value=""/>--%>
<%--    <br/>--%>
<%--    <br/>--%>
<%--    Firstname--%>
<%--    <br/>--%>
<%--    <input type="text" name="firstname" value=""/>--%>
<%--    <br/>--%>
<%--    <input type="submit" value="Sign up"/>--%>

<br/>

</html>
