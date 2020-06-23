<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../common/header.jsp"/>
<%--    <nav>--%>
<%--        <ul class="top-menu">--%>
<%--            <li>--%>
<%--                <form method="POST" action="admin">--%>
<%--                    <input type="hidden" name="command" value="show_accounts"/>--%>
<%--                    <input type="submit" value="Show users"/>--%>
<%--                </form>--%>
<%--            </li>--%>
<%--        </ul>--%>
<%--    </nav>--%>
</head>
<body>

<nav class="navbar navbar-expand-lg fixed-top ">
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav mr-auto sidenav" id="navAccordion">
            <li class="nav-item">
                <a class="nav-link" href="#">Manage Accounts</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#"
                >Manage Orders</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Manage Products</a>
            </li>
            </li>
        </ul>
    </div>
</nav>

<%--<main class="content-wrapper">--%>
<%--    <div class="container-fluid">--%>
<%--        <h1>Main Content</h1>--%>
<%--    </div>--%>
<%--</main>--%>

<table frame="border" align="center" border="10px" cellpadding="10px">
    <tr>
        <td>ID</td>
        <td>EMAIL</td>
        <td>FIRSTNAME</td>
        <td>PHONE</td>
        <td>BONUS</td>
        <td>ROLE</td>
    </tr>
    <c:forEach items="${requestScope.accounts}" var="user">

        <tr>
            <td>${user.id}</td>
            <td>${user.email}</td>
            <td>${user.firstName}</td>
            <td>${user.phoneNumber}</td>
            <td>${user.bonusPoints}</td>
            <td>${user.role.name()}</td>

        </tr>
    </c:forEach>


</table>
</body>
</html>