<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <title></title>
    <jsp:include page="common/header.jsp"/>
</head>
<body>
<h1 class="h1 mb-3 font-weight-normal text-xl-center"><b>Order success page</b></h1>

</body>
<footer class="text-center fixed-bottom overflow-hidden">
    <ctg:copyright-tag/>
</footer>
</html>
