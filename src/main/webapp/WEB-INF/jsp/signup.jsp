<%--
  Created by IntelliJ IDEA.
  User: JJ93
  Date: 26.05.2020
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <title><fmt:message bundle="${message}" key="header.signup"/></title>
    <jsp:include page="common/header.jsp"/>
    <link href="${pageContext.request.contextPath}\css\signin.css" rel="stylesheet">
</head>


<body class="text-center">
${errorInputMessage}
<form class="form-signin" method="POST" action="${pageContext.request.contextPath}/controller?command=signup" >
    <input type="hidden" name="command" value="signup"/>
    <h1 class="h3 mb-3 font-weight-normal"><fmt:message bundle="${message}"
                                                        key="signup.registration"/></h1>

    <label for="input_email" class="sr-only">Email address</label>
    <input name="email" type="email" id="input_email" class="form-control"
           placeholder="<fmt:message bundle="${message}" key="signup.email_address"/>" required
           autofocus>

    <label for="input_password" class="sr-only">Password</label>
    <input name="password" type="password" onchange="form.password_verify.pattern = this.value;" id="input_password" class="form-control"
           placeholder="<fmt:message bundle="${message}" key="signup.password"/>" required>

    <label for="input_password_verify" class="sr-only">Verify password</label>
    <input name="password_verify" type="password" id="input_password_verify" class="form-control"
           placeholder="<fmt:message bundle="${message}" key="signup.verify_password"/>" title="passwords must be equal" required>

    <label for="input_phonenumber" class="sr-only">Phone Number in format +375 xx xxx xx xx</label>
    <input name="phonenumber" type="tel" pattern="^(\+375|80)(29|25|44|33)(\d{3})(\d{2})(\d{2})$" id="input_phonenumber"
           class="form-control" placeholder="<fmt:message bundle="${message}" key="signup.phonenumber"/>"
           title="+375 xx xxx xx xx" required>

    <label for="input_firstname" class="sr-only">Firstname</label>
    <input name="firstname" type="text" pattern="[A-Za-z]{1,32}" id="input_firstname" class="form-control"
           placeholder="<fmt:message bundle="${message}" key="signup.firstname"/>" title="use latin letters less than 32 symbols" required>
    <br/>
    <button class="btn btn-lg btn-primary btn-block button-color" type="submit"><fmt:message bundle="${message}"
                                                                                             key="signup.signup"/></button>

</form>
</body>

</html>
