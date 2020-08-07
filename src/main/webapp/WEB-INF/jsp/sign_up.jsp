<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <title><fmt:message bundle="${message}" key="header.sign_up"/></title>
    <jsp:include page="common/header.jsp"/>
    <link href="${pageContext.request.contextPath}\css\signin.css" rel="stylesheet">
</head>


<body class="text-center">
<form class="form-sign_in" method="POST" action="${pageContext.request.contextPath}/controller?command=sign_up" >
    <input type="hidden" name="command" value="sign_up"/>
    <h1 class="h3 mb-3 font-weight-normal"><fmt:message bundle="${message}"
                                                        key="sign_up.registration"/></h1>

    <label for="input_email" class="sr-only">Email accountAddress</label>
    <input name="email" type="email" id="input_email" class="form-control"
           placeholder="<fmt:message bundle="${message}" key="sign_up.email_address"/>" required
           autofocus>

    <label for="input_password" class="sr-only">Password</label>
    <input name="password" type="password" onchange="form.password_verify.pattern = this.value;" id="input_password" class="form-control"
           placeholder="<fmt:message bundle="${message}" key="sign_up.password"/>" required>

    <label for="input_password_verify" class="sr-only">Verify password</label>
    <input name="password_verify" type="password" id="input_password_verify" class="form-control"
           placeholder="<fmt:message bundle="${message}" key="sign_up.verify_password"/>" title="passwords must be equal" required>

    <label for="input_phone_number" class="sr-only">Phone Number in format +375 xx xxx xx xx</label>
    <input name="phone_number" type="tel" pattern="^(\+375|80)(29|25|44|33)(\d{3})(\d{2})(\d{2})$" id="input_phone_number"
           class="form-control" placeholder="<fmt:message bundle="${message}" key="sign_up.phone_number"/>"
           title="+375 xx xxx xx xx" required>

    <label for="input_first_name" class="sr-only">Firstname</label>
    <input name="first_name" type="text" pattern="[A-Za-z]{1,32}" id="input_first_name" class="form-control"
           placeholder="<fmt:message bundle="${message}" key="sign_up.first_name"/>" title="use latin letters less than 32 symbols" required>
    <br/>
    <button class="btn btn-lg btn-primary btn-block button-color" type="submit"><fmt:message bundle="${message}"
                                                                                             key="sign_up.sign_up"/></button>

</form>
<footer class="text-center fixed-bottom overflow-hidden">
    <ctg:copyright-tag/>
</footer>
</body>

</html>
