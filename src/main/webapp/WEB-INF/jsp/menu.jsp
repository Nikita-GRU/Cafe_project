<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="ru-RU"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <title>Menu</title>
    <jsp:useBean id="productmanager" class="by.gruca.cafe.model.ProductManager"/>
</head>
<body>
<header>
    <jsp:include page="common/header.jsp"/>
</header>
<div class="content">
    <div class="container-fluid">
        <div class="row">
            <c:forEach items="${productmanager.products}" var="product">
                <div class="col-xs-12">
                    <form method="post" action="">
                        <input type="hidden" name="checkedproduct"
                               value="${product.name}"/>
                        <img src="${pageContext.request.contextPath}\images\sample.jpg" class="img-fluid product-image" alt="Responsive image"><br/>
                            ${product.name}<br>
                            ${product.price}<br>
                            ${product.description}<br>
                        <input type="submit" value="add to cart"/>
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>

</div>
<a href="cart">go to cart</a>
<footer>
<%--    <jsp:include page="common/footer.jsp"/>--%>
</footer>
</body>
</html>
