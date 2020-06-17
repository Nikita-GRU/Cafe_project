<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<ol class="rectangle">
    <c:forEach items="${sessionScope.cart}" var="product">
        ${product}<br>
    </c:forEach>
    <br>
    <a href="order">order this</a>
</ol>
</body>
</html>
