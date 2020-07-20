<%--
  Created by IntelliJ IDEA.
  User: JJ93
  Date: 21.05.2020
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <jsp:include page="common/header.jsp"/>
</head>
<body>

<h1 class="h1 mb-3 font-weight-normal text-xl-center"><b>Welcome to EPAM Cafe</b></h1>
<h2 class="h2 mb-3 font-weight-normal text-xl-center">there will be landing</h2>

</body>
</html>