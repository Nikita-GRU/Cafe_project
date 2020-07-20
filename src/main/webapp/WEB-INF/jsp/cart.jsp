<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <title><fmt:message bundle="${message}" key="header.cart"/></title>
    <jsp:include page="common/header.jsp"/>
</head>
<body>
<table class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
    <thead>
    <tr>
        <th><fmt:message bundle="${message}" key="admin.product_table.name"/></th>
        <th><fmt:message bundle="${message}" key="admin.product_table.price"/></th>
        <th><fmt:message bundle="${message}" key="admin.product_table.description"/></th>
        <th><fmt:message bundle="${message}" key="admin.account_table.bonus"/></th>
        <th></th>
    </tr>
    </thead>

    <c:forEach items="${sessionScope.cart}" var="product">
        <tr>
            <td>${product.name}</td>
            <td>${product.price}</td>
            <td>${product.description}</td>

            <td>${product.bonus}</td>
            <td>
                <form method="POST" action="${pageContext.request.contextPath}/controller?command=delete_from_cart">
                    <input type="hidden" name="product_to_delete" value="${product.name}">
                    <input type="submit" value="delete">
                </form>
            </td>
        </tr>
    </c:forEach>

</table>
<a href="${pageContext.request.contextPath}\order"><fmt:message bundle="${message}" key="cart.order_this"/></a>
<%--<jsp:include page="common/footer.jsp"/>--%>
</body>
</html>
