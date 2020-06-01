<%--
  Created by IntelliJ IDEA.
  User: JJ93
  Date: 22.05.2020
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Menu</title>
</head>
<body>
<ol class="rectangle">


    <c:forEach items="${products}" var="product">
        <li><a href="#">${product.name}, ${product.price}, ${product.description}</a></li>
        <br>
    </c:forEach>

</ol>
</body>
</html>
