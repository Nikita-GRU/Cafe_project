<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: JJ93
  Date: 12.06.2020
  Time: 13:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order</title>
</head>
<body>
<c:set var="sum" value="${0}"/>
You are ordering this:
<c:forEach items="${sessionScope.cart}" var="product"><br>
    ${product}<br>
   summ= ${sum= sum + product.price}<br>
</c:forEach>
<br>
cost=${sum}

<form method="post" action="">
    <br/>
    <br/>
    Email
    <input type="email" name="email" value=""/>
    <br/>
    <br/>
    Firstname
    <input type="text" name="firstname" value=""/>
    <br/>
    <br/>
    Phone Number
    <input type="text" name="phonenumber" value=""/>
    <br/>
    <br/>
    <input type="radio" name="payment" value="cash">cash
    <input type="radio" name="payment" value="credit card">credit card
    <input type="radio" name="payment" value="bonus points">bonus points
    <input type="submit" value="Order this"/>
    <br/>
    <br/>
</form>
</body>
</html>
