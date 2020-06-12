<%--
  Created by IntelliJ IDEA.
  User: JJ93
  Date: 30.05.2020
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${login} profile</title>
</head>
<body>
<form name="updateUserForm" method="POST" action="controller">
    <input type="hidden" name="command" value="updateProfile"/>
    firstName:
    <br/>
    <input type="text" name="firstName" value=${firstName}/> <a href="">check login</a>
    <br/>
    <br/>
    lastName:
    <br/>
    <input type="text" name="lastName" value= ${lastName}/>
    <br/>
    <br/>
    login:
    <br/>
    <input type="text" name="login" value= ${login}/>
    <br/>
    <br/>
    <a href=""><h3> Order list</h3></a>
</form>
</body>
</html>
