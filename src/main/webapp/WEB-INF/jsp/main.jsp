<%--
  Created by IntelliJ IDEA.
  User: JJ93
  Date: 21.05.2020
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="ru-RU"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <jsp:include page="common/header.jsp"/>
</head>
<body>

<form method="post" action="${pageContext.request.contextPath}\image" enctype="multipart/form-data">
    <input type="file" /><br/><br/>
    <input type="submit" value="Отправить"><
</form>

<jsp:include page="common/footer.jsp"/>
</body>
</html>