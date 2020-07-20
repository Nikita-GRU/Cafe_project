<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <title><fmt:message bundle="${message}" key="header.admin"/></title>
    <jsp:include page="../common/header.jsp"/>
    <!-- Material Design Bootstrap -->
</head>
<body>
<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mdb.css">--%>


<div class="d-flex" id="wrapper">
    <!-- Sidebar -->
    <div class="bg-light border-right" id="sidebar-wrapper">
        <div class="list-group list-group-flush">

            <a class="list-group-item list-group-item-action bg-light"
               href="${pageContext.request.contextPath}\admin\accounts"><fmt:message bundle="${message}"
                                                                                     key="admin.manage_accounts"/></a>
            <a class="list-group-item list-group-item-action bg-light"
               href="${pageContext.request.contextPath}\admin\products"><fmt:message bundle="${message}"
                                                                                     key="admin.manage_products"/></a>
            <a class="list-group-item list-group-item-action bg-light"
               href="${pageContext.request.contextPath}\admin\orders"><fmt:message bundle="${message}"
                                                                                   key="admin.manage_orders"/></a>
            <a class="list-group-item list-group-item-action bg-light"
               href="${pageContext.request.contextPath}\admin\newproduct"><fmt:message bundle="${message}"
                                                                                       key="admin.add_new_product"/></a>
            <a class="list-group-item list-group-item-action bg-light"
               href="${pageContext.request.contextPath}\admin\newaccount"><fmt:message bundle="${message}"
                                                                                       key="admin.add_new_account"/></a>
        </div>
    </div>
    <!-- /#sidebar-wrapper -->

    <c:if test="${pageContext.request.getParameter('command') == 'show_accounts'}">
    <table class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th><fmt:message bundle="${message}" key="admin.account_table.id"/></th>
            <th><fmt:message bundle="${message}" key="admin.account_table.email"/></th>
            <th><fmt:message bundle="${message}" key="admin.account_table.firstname"/></th>
            <th><fmt:message bundle="${message}" key="admin.account_table.phone_number"/></th>
            <th><fmt:message bundle="${message}" key="admin.account_table.bonus"/></th>
            <th><fmt:message bundle="${message}" key="admin.account_table.role"/></th>
            <th></th>

        </tr>
        </thead>

        <c:forEach items="${requestScope.accounts}" var="user">
            <form method="post" action="${pageContext.request.contextPath}\admin\accounts">
                <input type="hidden" name="sub_command" value="update">
                <input type="hidden" name="email_to_update" value="${user.email}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.email}</td>
                    <td>${user.firstName}</td>
                    <td>${user.phoneNumber}</td>
                    <td><input size="5" type="text" name="new_bonus" value="${user.bonusPoints}"></td>
                    <td>${user.role.name()}
                        <br/>
                        <select name="new_role" size="4">
                            <option value="admin"><fmt:message bundle="${message}" key="admin.role.admin"/></option>
                            <option value="moderator"><fmt:message bundle="${message}" key="admin.role.moderator"/></option>
                            <option value="user"><fmt:message bundle="${message}" key="admin.role.user"/></option>
                            <option value="guest"><fmt:message bundle="${message}" key="admin.role.guest"/></option>
                        </select>
                    </td>
                    <td><input type="submit" value="<fmt:message bundle="${message}" key="admin.button.edit"/>"></td>
                </tr>
            </form>
        </c:forEach>
    </table>
</c:if>


        <c:if test="${pageContext.request.getParameter('command') == 'show_orders'}">
            <%--        <table frame="border" border="10px" cellpadding="10px">--%>
        <table id="dtBasicExample" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th class="th-sm"><fmt:message bundle="${message}" key="admin.order_table.id"/></th>
                <th class="th-sm"><fmt:message bundle="${message}" key="admin.order_table.date"/></th>
                <th class="th-sm"><fmt:message bundle="${message}" key="admin.order_table.email"/></th>
                <th class="th-sm"><fmt:message bundle="${message}" key="admin.order_table.products"/></th>
                <th class="th-sm"><fmt:message bundle="${message}" key="admin.order_table.price"/></th>
                <th class="th-sm"><fmt:message bundle="${message}" key="admin.order_table.review"/></th>
                <th class="th-sm"><fmt:message bundle="${message}" key="admin.order_table.accepted"/></th>
                <th class="th-sm"><fmt:message bundle="${message}" key="admin.order_table.delivered"/></th>
            </tr>
            </thead>
            <tbody>
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
            </tbody>
        </table>
        </c:if>


        <c:if test="${pageContext.request.getParameter('command') == 'show_products'}">
        <table class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th><fmt:message bundle="${message}" key="admin.product_table.id"/></th>
                <th><fmt:message bundle="${message}" key="admin.product_table.name"/></th>
                <th><fmt:message bundle="${message}" key="admin.product_table.price"/></th>
                <th><fmt:message bundle="${message}" key="admin.product_table.description"/></th>
                <th>Image Uri</th>
                <th>Bonus</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.products}" var="product">
                <form method="post" action="${pageContext.request.contextPath}\admin\products">
                    <input type="hidden" name="sub_command" value="update">
                    <input type="hidden" name="product_id_to_update" value="${product.id}">
                    <tr>
                        <td>${product.id}</td>
                        <td><input type="text" name="new_name" value="${product.name}"></td>
                        <td><input type="text" name="new_price" value="${product.price}"></td>
                        <td><input type="text" name="new_description" value="${product.description}"></td>
                        <td><input type="text" name="new_image_uri" value="${product.imageUri}"></td>
                        <td><input type="text" name="new_bonus" value="${product.bonus}"></td>
                        <td><input type="submit"
                                   value="<fmt:message bundle="${message}" key="admin.button.edit"/>">
                        </td>
                    </tr>
                </form>
            </c:forEach>
            </tbody>
        </table>
        </c:if>


        <c:if test="${pageContext.request.getParameter('command') == 'add_new_product'}">
        <form method="post" action="${pageContext.request.contextPath}\controller?command=create_product">
            <input type="hidden" name="command" value="create_product">
            <fmt:message bundle="${message}" key="admin.add_new_product.name"/>
            <br/>
            <input type="text" name="name" value=""><br/><br/>
            <fmt:message bundle="${message}" key="admin.add_new_product.price"/>
            <br/>
            <input type="text" name="price" value=""><br/><br/>
            <fmt:message bundle="${message}" key="admin.add_new_product.description"/>
            <br/>
            <input type="text" name="description" value=""> <br/><br/>
            Image Uri
            <br/>
            <input type="text" name="image_uri" value=""> <br/><br/>
            Bonus
            <br/>
            <input type="text" name="bonus" value=""> <br/><br/>
            <input type="submit"
                   value="<fmt:message bundle="${message}" key="admin.button.create_new_product"/>">
        </form>
        </c:if>

        <c:if test="${pageContext.request.getParameter('command') == 'add_new_account'}">
        <form method="post" action="${pageContext.request.contextPath}\controller?command=create_account">
            <input type="hidden" name="command" value="create_account">
            <fmt:message bundle="${message}" key="admin.add_new_account.email"/>
            <br/>
            <input type="email" name="email" value=""/>
            <br/>
            <br/>
            <fmt:message bundle="${message}" key="admin.add_new_account.phone_number"/>
            <br/>
            <input type="text" name="phonenumber" value=""/>
            <br/>
            <br/>
            <fmt:message bundle="${message}" key="admin.add_new_account.password"/>
            <br/>
            <input type="password" name="password" value=""/>
            <br/>
            <br/>
            <fmt:message bundle="${message}" key="admin.add_new_account.password_verify"/>
            <br/>
            <input type="password" name="password_verify" value=""/>
            <br/>
            <br/>
            <fmt:message bundle="${message}" key="admin.add_new_account.firstname"/>
            <br/>
            <input type="text" name="firstname" value=""/>
            <br/>
            <br/>
            <fmt:message bundle="${message}" key="admin.add_new_account.role"/>
            <br/>
            <select name="role" size="4">
                <option value="admin"><fmt:message bundle="${message}" key="admin.role.admin"/></option>
                <option value="moderator"><fmt:message bundle="${message}" key="admin.role.moderator"/></option>
                <option value="user"><fmt:message bundle="${message}" key="admin.role.user"/></option>
                <option value="guest"><fmt:message bundle="${message}" key="admin.role.guest"/></option>
            </select>
            <br/>
            <br/>
            <input type="submit"
                   value="<fmt:message bundle="${message}" key="admin.button.create_new_account"/>"/>
        </form>
        </c:if>

</body>
</html>