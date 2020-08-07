<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head><title><fmt:message bundle="${message}" key="header.log_in"/></title>
    <jsp:include page="common/header.jsp"/>
    <link href="${pageContext.request.contextPath}\css\signin.css" rel="stylesheet">
</head>
<%--<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/loginstyle.css"--%>


<body class="text-center">
<div class="page-container">
    <form id="login_form" class="form-sign_in" method="POST" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="log_in">
        <h1 class="h3 mb-3 font-weight-normal"><fmt:message bundle="${message}" key="log_in.please_log_in"/></h1>

        <label for="input_email" class="sr-only">Email</label>
        <input id ="login_email" name="email" type="text" id="input_email" class="form-control error"
               placeholder="<fmt:message bundle="${message}" key="log_in.email_address"/>" required
               autofocus>

        <label for="input_password" class="sr-only">Password</label>
        <input id ="login_password" name="password" type="password" id="input_password" class="form-control error"
               placeholder="<fmt:message bundle="${message}" key="log_in.password"/>" required>

<%--        <div class="checkbox mb-3">--%>
<%--            <label>--%>
<%--                <input type="checkbox" value="remember-me"> <fmt:message bundle="${message}" key="login.remember_me"/>--%>
<%--            </label>--%>
<%--        </div>--%>
        <button id="login_submit" class="btn btn-lg btn-primary btn-block button-color" type="submit"><fmt:message bundle="${message}"
                                                                                                 key="log_in.log_in"/></button>
    </form>
</div>
${sessionScope.errorMessage}
<footer class="text-center fixed-bottom overflow-hidden">
    <ctg:copyright-tag/>
</footer>
</body>
<script>
    $(function() {
        $('#login_form').validate({
            rules:{
                email:{
                    required: true,
                    email:true,
                    minlength: 4,
                    maxlength: 32,
                },
                password:{
                    required: true,
                    minlength: 3,
                    maxlength: 16,
                },
            },
            messages:{
                email:{
                    required: "Это поле обязательно для заполнения",
                    email: "not email",
                    minlength: "Email должен быть минимум 4 символа",
                    maxlength: "Максимальное число символов - 16",
                },
                password:{
                    required: "Это поле обязательно для заполнения",
                    minlength: "Пароль должен быть минимум 8 символов",
                    maxlength: "Пароль должен быть максимум 16 символов",
                },
            }

        });
    })
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/footer.js"></script>
</html>
