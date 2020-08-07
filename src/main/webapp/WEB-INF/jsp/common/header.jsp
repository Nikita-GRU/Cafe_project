<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <meta http-equiv="Cache-Control" content="private">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
    <title><fmt:message bundle="${message}" key="header.welcome"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css"/>
</head>
<body>

<nav class="navbar navbar-expand ">
    <div class="collapse navbar-collapse " id="navbarSupportedContent">
        <ul class="nav nav-pills">
            <li class="nav-item"><a class="nav-link index-font"
                                    href="${pageContext.request.contextPath}">KOTYA CAFE</a></li>
            <li class="nav-item"><a class="nav-link nav-font"
                                    href="${pageContext.request.contextPath}\menu"><fmt:message
                    bundle="${message}" key="header.menu"/></a></li>
            <c:if test="${sessionScope.cart_count >0 }">
                <li class="nav-item"><a class="nav-link cart" href="${pageContext.request.contextPath}\cart">
                    <fmt:message
                            bundle="${message}" key="header.cart"/>
                    <span class="badge badge-pill badge-light">${sessionScope.cart_count}</span></a></li>
            </c:if>

            <c:if test="${sessionScope.role=='moderator'}">


            </c:if>

            <c:if test="${sessionScope.role== 'admin'}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><fmt:message
                            bundle="${message}" key="header.moderation"/></a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="${pageContext.request.contextPath}\moderator\orders"><fmt:message bundle="${message}" key="moderation.show_orders"/></a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><fmt:message
                            bundle="${message}" key="header.admin"/></a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item"  href="${pageContext.request.contextPath}\admin\accounts"><fmt:message bundle="${message}"
                                                                                                                        key="admin.manage_accounts"/></a>
                        <a class="dropdown-item"  href="${pageContext.request.contextPath}\admin\products"><fmt:message bundle="${message}"
                                                                                                                        key="admin.manage_products"/></a>
                        <a class="dropdown-item"  href="${pageContext.request.contextPath}\admin\orders"><fmt:message bundle="${message}"
                                                                                                                      key="admin.manage_orders"/></a>
                        <a class="dropdown-item"  href="${pageContext.request.contextPath}\admin\newproduct"><fmt:message bundle="${message}"
                                                                                                                          key="admin.add_new_product"/></a>
                        <a class="dropdown-item"  href="${pageContext.request.contextPath}\admin\newaccount"><fmt:message bundle="${message}"
                                                                                                                          key="admin.add_new_account"/></a>
                    </div>
                </li>
            </c:if>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\signup"><fmt:message
                    bundle="${message}" key="header.sign_up"/></a></li>

            <c:if test="${sessionScope.role =='guest' || sessionScope.role=='null'}">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}\login"><fmt:message
                        bundle="${message}" key="header.log_in"/></a></li>
            </c:if>

            <c:if test="${sessionScope.role !='guest'}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><fmt:message
                            bundle="${message}" key="header.profile"/></a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item"  href="${pageContext.request.contextPath}\profile\info"><fmt:message bundle="${message}"
                                                                                                                      key="profile.my_profile"/></a>
                        <a class="dropdown-item"  href="${pageContext.request.contextPath}\profile\orders"><fmt:message bundle="${message}"
                                                                                                                        key="profile.show_orders"/></a>
                    </div>
                </li>

                <li class="nav-item">
                    <form method="post"
                          action="${pageContext.request.contextPath}\controller">
                        <input type="hidden" name="command" value="log_out"/>
                        <input type="submit" value="logout">
                    </form>
                </li>
            </c:if>

            <%--            ${requestScope ['javax.servlet.forward.request_uri']}?locale=ru-RU"--%>
            <%--            ${requestScope ['javax.servlet.forward.request_uri']}?locale=en-GB--%>
            <%--                ${pageContext.request.contextPath}/${requestScope ['javax.servlet.forward.request_uri']}/--%>
            <li class="nav-item">
                <form method="post"
                      action="${pageContext.request.contextPath}\controller">
                    <input type="hidden" name="command" value="change_locale"/>
                    <input type="hidden" name="new_locale" value="en-GB">
                    <input type="hidden" name="redirect_uri"
                           value="${pageContext.request.getAttribute("javax.servlet.forward.request_uri")}">
                    <input type="hidden" name="redirect_query"
                           value="${pageContext.request.getAttribute("javax.servlet.forward.query_string")}">
                    <input type="submit" value="eng">
                </form>
            </li>
            <li class="nav-item">
                <form method="post"
                      action="${pageContext.request.contextPath}\controller">
                    <input type="hidden" name="command" value="change_locale"/>
                    <input type="hidden" name="new_locale" value="ru-RU">
                    <input type="hidden" name="redirect_uri"
                           value="${pageContext.request.getAttribute("javax.servlet.forward.request_uri")}">
                    <input type="hidden" name="redirect_query"
                           value="${pageContext.request.getAttribute("javax.servlet.forward.query_string")}">
                    <input type="submit" value="ru">
                </form>
            </li>
        </ul>
    </div>
</nav>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<!-- Bootstrap tooltips -->
<script type = "text/javascript "src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>



<!-- Bootstrap core JavaScript -->

<!-- MDB core JavaScript -->
</html>
