<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>

<html>
<head>
    <title><fmt:message bundle="${message}" key="header.moderation"/></title>
    <jsp:include page="../common/header.jsp"/>
</head>
<body class="text-center">
<c:if test="${pageContext.request.getParameter('command') == 'moderator_show_orders'}">
    <table class="table table-responsive table-striped table-bordered justify-content-center">
        <thead>
        <tr class="tr-color">
            <th><fmt:message bundle="${message}" key="admin.order_table.id"/></th>
            <th><fmt:message bundle="${message}" key="admin.order_table.date"/></th>
            <th>delivery date</th>
            <th><fmt:message bundle="${message}" key="admin.order_table.email"/></th>
            <th><fmt:message bundle="${message}" key="admin.order_table.products"/></th>
            <th><fmt:message bundle="${message}" key="admin.order_table.price"/></th>
            <th><fmt:message bundle="${message}" key="admin.order_table.review"/></th>
            <th>address</th>
            <th>paid by bonus</th>
            <th>set status</th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.orders}" var="order">

            <tr>
                <td>${order.id}</td>
                <td>${order.creationDate}</td>
                <td>${order.deliveryDate}</td>
                <td>${order.account.email}</td>
                <td>
                    <c:forEach items="${order.products.keySet()}" var="key">
                        ${key.name}(${order.products.get(key)})
                        <br/>
                    </c:forEach>
                </td>
                <td>${order.price}</td>
                <td>${order.note}</td>
                <td>
                        ${order.address} <br/>
                        ${order.apartment}<br/>
                </td>
                <td>${order.bonusToPay}</td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}\controller">
                        <input type="hidden" name="command" value="set_order_status">
                        <input type="hidden" name="order_id_to_update" value="${order.id}"/>
                            ${order.orderStatus.status}<br/>
                        <select name="order_status" required>
                            <c:forEach items="${requestScope.order_statuses}" var="order_status">
                                <option value=${order_status.status}> ${order_status.status}</option>
                            </c:forEach>
                        </select>
                        <input type="submit" value="set status">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${pageContext.request.getParameter('command') == 'moderator_add_order'}">
</c:if>
<footer class="text-center fixed-bottom overflow-hidden">
    <ctg:copyright-tag/>
</footer>
</body>
</html>
