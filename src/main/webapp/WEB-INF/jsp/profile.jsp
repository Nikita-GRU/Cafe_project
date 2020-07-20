<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <title>${sessionScope.account.firstName} profile</title>
    <jsp:include page="common/header.jsp"/>
</head>
<body>
<div class="d-flex" id="wrapper">
    <!-- Sidebar -->
    <div class="bg-light border-right" id="sidebar-wrapper">
        <div class="list-group list-group-flush">

            <a class="list-group-item list-group-item-action bg-light"
               href="${pageContext.request.contextPath}\profile\info"><fmt:message bundle="${message}"
                                                                                   key="profile.my_profile"/></a>
            <a class="list-group-item list-group-item-action bg-light"
               href="${pageContext.request.contextPath}\profile\orders"><fmt:message bundle="${message}"
                                                                                     key="profile.show_orders"/></a>
        </div>
    </div>
    <!-- /#sidebar-wrapper -->

    <c:if test="${pageContext.request.getParameter('command') == 'show_profile'}">
    <table class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
        <tr>
            <th><fmt:message bundle="${message}" key="admin.account_table.email"/></th>
            <th><fmt:message bundle="${message}" key="admin.account_table.firstname"/></th>
            <th><fmt:message bundle="${message}" key="admin.account_table.phone_number"/></th>
            <th><fmt:message bundle="${message}" key="admin.account_table.bonus"/></th>
            <th><fmt:message bundle="${message}" key="admin.account_table.role"/></th>
            <th></th>

        </tr>
        <form method="post" action="${pageContext.request.contextPath}\profile\info">
            <input type="hidden" name="sub_command" value="update">
            <input type="hidden" name="email_to_update" value="${user.email}">
            <tr>
                <td>${sessionScope.account.email}</td>
                <td>${sessionScope.account.firstName}</td>
                <td>${sessionScope.account.phoneNumber}</td>
                <td>${sessionScope.account.bonusPoints}"</td>
                <td>${sessionScope.account.role.name()}</td>
                <td><input type="submit" value="<fmt:message bundle="${message}" key="admin.button.edit"/>"></td>
            </tr>
        </form>

        </c:if>


        <c:if test="${pageContext.request.getParameter('command') == 'show_profile_orders'}">
        <table class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
            <tr>
                <th><fmt:message bundle="${message}" key="admin.order_table.id"/></th>
                <th><fmt:message bundle="${message}" key="admin.order_table.date"/></th>
                <th><fmt:message bundle="${message}" key="admin.order_table.email"/></th>
                <th><fmt:message bundle="${message}" key="admin.order_table.products"/></th>
                <th><fmt:message bundle="${message}" key="admin.order_table.price"/></th>
                <th><fmt:message bundle="${message}" key="admin.order_table.review"/></th>
                <th><fmt:message bundle="${message}" key="admin.order_table.accepted"/></th>
                <th><fmt:message bundle="${message}" key="admin.order_table.delivered"/></th>
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

            </tr>
            </c:forEach>
            </c:if>
</body>
</html>
