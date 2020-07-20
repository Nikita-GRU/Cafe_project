<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>

<html>
<head>
    <title><fmt:message bundle="${message}" key="header.moderation"/></title>
    <jsp:include page="../common/header.jsp"/>
</head>
<body>
<div class="d-flex" id="wrapper">
    <!-- Sidebar -->
    <div class="bg-light border-right" id="sidebar-wrapper">
        <div class="list-group list-group-flush">
            <a class="list-group-item list-group-item-action bg-light"
               href="${pageContext.request.contextPath}\moderator\orders"><fmt:message bundle="${message}"
                                                                                       key="moderation.show_orders"/></a>
            <a class="list-group-item list-group-item-action bg-light"
               href=""><fmt:message bundle="${message}" key="moderation.add_order"/></a>
        </div>
    </div>

    <c:if test="${pageContext.request.getParameter('command') == 'moderator_show_orders'}">
    <table class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
        <tr>
            <th><fmt:message bundle="${message}" key="moderation.order_table.id"/></th>
            <th><fmt:message bundle="${message}" key="moderation.order_table.date"/></th>
            <th><fmt:message bundle="${message}" key="moderation.order_table.email"/></th>
            <th><fmt:message bundle="${message}" key="moderation.order_table.products"/></th>
            <th><fmt:message bundle="${message}" key="moderation.order_table.price"/></th>
            <th><fmt:message bundle="${message}" key="moderation.order_table.review"/></th>
            <th><fmt:message bundle="${message}" key="moderation.order_table.accepted"/></th>
            <th><fmt:message bundle="${message}" key="moderation.order_table.delivered"/></th>
            <th colspan="3"><fmt:message bundle="${message}" key="moderation.order_table.moderation"/></th>
        </tr>
        <c:forEach items="${requestScope.orders}" var="order">
            <tr>
                <td>${order.id}</td>
                <td>${order.date}</td>
                <td>${order.account.email}</td>
                <td>
                    <c:forEach items="${order.products.keySet()}" var="key">
                        ${key.name}(${order.products.get(key)})
                        <br/>
                    </c:forEach>
                </td>
                <td>${order.price}</td>
                <td>${order.review}</td>
                <td>${order.accepted}</td>
                <td>${order.delivered}</td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}\moderator\orders">
                        <input type="hidden" name="command" value="order_set_accepted">
                        <input type="hidden" name="order_id_to_set_accepted" value="${order.id}">
                        <input type="submit"
                               value="<fmt:message bundle="${message}" key="moderation.button.set_accepted"/>">
                    </form>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}\moderator\orders">
                        <input type="hidden" name="command" value="order_set_delivered">
                        <input type="hidden" name="order_id_to_set_delivered" value="${order.id}">
                        <input type="submit"
                               value="<fmt:message bundle="${message}" key="moderation.button.set_delivered"/>">
                    </form>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}\moderator\orders">
                        <input type="hidden" name="command" value="order_delete">
                        <input type="hidden" name="order_id_to_delete" value="${order.id}">
                        <input type="submit" value="<fmt:message bundle="${message}" key="moderation.button.delete"/>">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    </c:if>

    <c:if test="${pageContext.request.getParameter('command') == 'moderator_add_order'}">
    </c:if>
</body>
</html>
