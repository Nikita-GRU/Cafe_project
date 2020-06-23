<%--
  Created by IntelliJ IDEA.
  User: JJ93
  Date: 18.06.2020
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GRATS</title>
    <jsp:include page="common/header.jsp"/>
</head>
<body>
Order success
${account}
<br/>
${payment}
<br/>
${email}
<br/>
${firstname}
<br/>
${phonenumber}
<br/>
${map}
<br/>


<c:forEach items="${sessionScope.cart}" var="product"><br>
    ${product}<br>
</c:forEach>
<a href="${pageContext.request.contextPath}"> Go to main page </a>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
