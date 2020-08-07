<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
    <title><fmt:message bundle="${message}" key="header.admin"/></title>
    <jsp:include page="../common/header.jsp"/>
    <!-- Material Design Bootstrap -->
</head>
<body>
${pageContext}
<div class="col-10">
    <c:if test="${pageContext.request.getParameter('command') == 'show_accounts'}">
    <div>
        <form method="post" action="${pageContext.request.contextPath}\admin\accounts">
            <label>items per page
                <input class="items-per-page-input" type="number" min="1" name="items_per_page"
                       value="${sessionScope.items_per_page}"/>
            </label>
            <input type="hidden" name="redirect_uri"
                   value="${pageContext.request.getAttribute("javax.servlet.forward.request_uri")}">
            <input type="hidden" name="redirect_query"
                   value="${pageContext.request.getAttribute("javax.servlet.forward.query_string")}">
        </form>
        <div class="container justify-content-center">
            <table class="table  table-responsive table-striped table-bordered ">
                <thead>
                <tr class="tr-color">
                    <th><fmt:message bundle="${message}" key="admin.account_table.id"/></th>
                    <th><fmt:message bundle="${message}" key="admin.account_table.email"/></th>
                    <th><fmt:message bundle="${message}" key="admin.account_table.first_name"/></th>
                    <th><fmt:message bundle="${message}" key="admin.account_table.phone_number"/></th>
                    <th><fmt:message bundle="${message}" key="admin.account_table.bonus"/></th>
                    <th><fmt:message bundle="${message}" key="admin.account_table.role"/></th>
                    <th>Balance</th>
                    <th></th>

                </tr>
                </thead>

                <c:forEach items="${requestScope.accounts}" var="user">
                    <form method="post" action="${pageContext.request.contextPath}\admin\accounts">
                        <input type="hidden" name="sub_command" value="update">
                        <input type="hidden" name="email_to_update" value="${user.email}">
                        <input type="hidden" name="redirect_uri"
                               value="${pageContext.request.getAttribute("javax.servlet.forward.request_uri")}">
                        <input type="hidden" name="redirect_query"
                               value="${pageContext.request.getAttribute("javax.servlet.forward.query_string")}">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.email}</td>
                            <td>${user.firstName}</td>
                            <td>${user.phoneNumber}</td>
                            <td><input size="5" type="text" name="new_bonus" value="${user.bonus}"></td>
                            <td>${user.balance}</td>
                            <td>${user.role.name()}
                                <br/>
                                <select name="new_role" required>
                                    <c:forEach items="${requestScope.roles}" var="role">
                                        <option value=${role.roleValue}> ${role.roleValue}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td><input type="submit"
                                       value="<fmt:message bundle="${message}" key="admin.button.edit"/>">
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </table>
            <nav>
                <ul class="pagination justify-content-center">

                    <c:forEach begin="1" end="${requestScope.pages_count}" var="loop_count">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}\admin\accounts?page_number=${loop_count}">${loop_count}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>
        </c:if>

    </div>
    <c:if test="${pageContext.request.getParameter('command') == 'show_orders'}">
        <div>
            <form method="post" action="${pageContext.request.contextPath}\admin\orders">
                <label>items per page
                    <input class="items-per-page-input" type="number" min="1" name="items_per_page"
                           value="${sessionScope.items_per_page}"/>
                </label>
                <input type="hidden" name="redirect_uri"
                       value="${pageContext.request.getAttribute("javax.servlet.forward.request_uri")}">
                <input type="hidden" name="redirect_query"
                       value="${pageContext.request.getAttribute("javax.servlet.forward.query_string")}">
            </form>


            <table class="table table-responsive table-striped table-bordered">
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
                    <th>feedback</th>
                    <th>score</th>
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
                        <td>${order.feedback}</td>
                        <td>${order.score}</td>
                        <td>
                            <button id="order_details">details</button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <nav>
                <ul class="pagination justify-content-center">

                    <c:forEach begin="1" end="${requestScope.pages_count}" var="loop_count">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}\admin\orders?page_number=${loop_count}">${loop_count}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>
    </c:if>


    <c:if test="${pageContext.request.getParameter('command') == 'show_products'}">
        <div>
            <form method="post" action="${pageContext.request.contextPath}\admin\products">
                <label>items per page
                    <input class="items-per-page-input" type="number" min="1" name="items_per_page"
                           value="${sessionScope.items_per_page}"/>
                </label>
                <input type="hidden" name="redirect_uri"
                       value="${pageContext.request.getAttribute("javax.servlet.forward.request_uri")}">
                <input type="hidden" name="redirect_query"
                       value="${pageContext.request.getAttribute("javax.servlet.forward.query_string")}">
            </form>

            <table class="table table-responsive table-striped table-bordered">
                <thead>
                <tr class="tr-color">
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
                    <form method="post" action="${pageContext.request.contextPath}\admin\products" accept-charset=«UTF-8»>
                        <input type="hidden" name="sub_command" value="update">
                        <input type="hidden" name="product_id_to_update" value="${product.id}">
                        <input type="hidden" name="redirect_uri"
                               value="${pageContext.request.getAttribute("javax.servlet.forward.request_uri")}">
                        <input type="hidden" name="redirect_query"
                               value="${pageContext.request.getAttribute("javax.servlet.forward.query_string")}">
                        <tr>
                            <td>${product.id}</td>
                            <td><input type="text" name="new_name" value="${product.name}"></td>
                            <td><input type="text" name="new_price" value="${product.price}"></td>
                            <td><textarea name="new_description" cols="30"
                                          rows="2">${product.description}</textarea>
                            </td>
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
            <nav>
                <ul class="pagination justify-content-center">

                    <c:forEach begin="1" end="${requestScope.pages_count}" var="loop_count">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}\admin\products?page_number=${loop_count}">${loop_count}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>
    </c:if>


    <c:if test="${pageContext.request.getParameter('command') == 'add_new_product'}">
        <form method="post" action="${pageContext.request.contextPath}\controller">
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
            Category
            <br/>
            <select name="category" required>
                <c:forEach items="${requestScope.categories}" var="category">
                    <option value=${category.category}> ${category.category}</option>
                </c:forEach>
            </select>
            <br/><br/>
            <input type="submit"
                   value="<fmt:message bundle="${message}" key="admin.button.create_new_product"/>">
        </form>
    </c:if>

    <c:if test="${pageContext.request.getParameter('command') == 'add_new_account'}">
        <form method="post" action="${pageContext.request.contextPath}\controller">
            <input type="hidden" name="command" value="create_account">
            <fmt:message bundle="${message}" key="admin.add_new_account.email"/>
            <br/>
            <input type="email" name="email" value=""/>
            <br/>
            <br/>
            <fmt:message bundle="${message}" key="admin.add_new_account.phone_number"/>
            <br/>
            <input type="text" name="phone_number" value=""/>
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
            <fmt:message bundle="${message}" key="admin.add_new_account.first_name"/>
            <br/>
            <input type="text" name="first_name" value=""/>
            <br/>
            <br/>
            <fmt:message bundle="${message}" key="admin.add_new_account.role"/>
            <br/>
            <select name="role">
                <c:forEach items="${requestScope.roles}" var="role">
                    <option value=${role.roleValue}> ${role.roleValue}</option>
                </c:forEach>
            </select>
            <br/>
            <br/>
            <input type="submit"
                   value="<fmt:message bundle="${message}" key="admin.button.create_new_account"/>"/>
        </form>
    </c:if>
</div>
<footer class="text-center fixed-bottom overflow-hidden">
    <ctg:copyright-tag/>
</footer>
</body>
</html>