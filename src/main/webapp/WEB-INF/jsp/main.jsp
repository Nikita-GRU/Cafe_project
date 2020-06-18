<%--
  Created by IntelliJ IDEA.
  User: JJ93
  Date: 21.05.2020
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Welcome</title>
    <style>
        ul.nav {
            margin-left: 0px;
            padding-left: 0px;
            list-style: none;
        }

        .nav li {
            float: left;
        }

        ul.nav a {
            display: block;
            width: 5em;
            padding: 10px;
            margin: 0 5px;
            background-color: #f4f4f4;
            border: 1px dashed #333;
            text-decoration: none;
            color: #333;
            text-align: center;
        }

        ul.nav input {
            display: block;
            width: 5em;
            padding: 10px;
            margin: 0 5px;
            background-color: #f4f4f4;
            border: 1px dashed #333;
            text-decoration: none;
            color: #333;
            text-align: center;
        }

        ul.nav input:hover {
            background-color: #333;
            color: #f4f4f4;
        }

        ul.nav a:hover {
            background-color: #333;
            color: #f4f4f4;
        }
    </style>
</head>
<body>
<h3>Welcome, ${sessionScope.username}!</h3>
<p> It's ${sessionScope.role} page</p>
<hr/>

<ul class="nav">
    <li><a href="${pageContext.request.contextPath}">Главная</a></li>
    <li><a href="${pageContext.request.contextPath}\menu">Menu</a></li>
    <li><a href="${pageContext.request.contextPath}\signup">Sign up</a></li>
    <li><a href="${pageContext.request.contextPath}\login">Login</a></li>
</ul>
</body>
</html>