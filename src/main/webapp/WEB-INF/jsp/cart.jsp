<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Cart</title>
    <jsp:include page="common/header.jsp"/>
</head>
<body>
<ol class="rectangle">
    <c:forEach items="${sessionScope.cart}" var="product">
        ${product}
        <form method="POST" action="cart">
            <input type="hidden" name="command" value="delete_from_cart">
            <input type="hidden" name="product_to_delete" value="${product.name}">
            <input type="submit" value="delete">
        </form>
        <br>
    </c:forEach>
    <br>
    <a href="order">order this</a>
</ol>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
