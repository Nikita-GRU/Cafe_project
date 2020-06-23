<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
    <jsp:include page="../common/header.jsp"/>
</head>
<body>
<table class ="align-center table-content" >
    <tr>
        <th>ID</th>
        <th>DATE</th>
        <th>EMAIL</th>
        <th>PRODUCTS</th>
        <th>PRICE</th>
        <th>REVIEW</th>
        <th>ACCEPTED</th>
        <th>DELIVERED</th>
        <th colspan="2">MODERATION</th>
    </tr>
    <c:forEach items="${requestScope.orders}" var="order">

        <tr>
            <td>${order.id}</td>
            <td>${order.date}</td>
            <td>${order.account.email}</td>
            <td>
                <c:forEach items="${order.products.keySet()}" var="key">
                    | ${key.name}(${order.products.get(key)})
                </c:forEach>
            </td>
            <td>${order.price}</td>
            <td>${order.review}</td>
            <td>${order.accepted}</td>
            <td>${order.delivered}</td>
            <td>
                <form method="post" action="moderator">
                    <input type="hidden" name="command" value="order_set_accepted">
                    <input type="hidden" name="order_id_to_set_accepted" value="${order.id}">
                    <input type="submit" value="Set Accepted">
                </form>
            </td>
            <td>
                <form method="post" action="moderator">
                    <input type="hidden" name="command" value="order_set_delivered">
                    <input type="hidden" name="order_id_to_set_delivered" value="${order.id}">
                    <input type="submit" value="Set Delivered">
                </form>
            </td>
        </tr>
    </c:forEach>
${qwe}

</table>
</body>
</html>
