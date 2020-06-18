<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Menu</title>
    <jsp:useBean id="productmanager" class="by.gruca.cafe.model.ProductManager"/>
</head>
<body>
<!DOCTYPE HTML>
<html>
<body>

<c:forEach items="${productmanager.products}" var="product">
    <form method="post" action="">
        <input type="hidden" name="checkedproduct" value="${product.name}"/>${product.name}
            <input type="submit" value="add to cart"/>
    </form>
</c:forEach>
<a href="cart">go to cart</a>
</body>
</html>
</body>
</html>
