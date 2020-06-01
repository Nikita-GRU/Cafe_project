<%--
  Created by IntelliJ IDEA.
  User: JJ93
  Date: 26.05.2020
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign in</title>
</head>
<body>
<form name="signInForm" method="POST" action="controller">
    <input type="hidden" name="command" value="signin"/>
    Login:
    <br/>
    <input type="text" name="login" value=""/> <a href="">check login</a>
    <br/>
    <br/>
    Password:
    <br/>
    <input type="password" name="password" value=""/>
    <br/>
    <br/>
    Verify password
    <br/>
    <input type="password" name="password_verify" value=""/>
    <br/>
    <br/>
    Email
    <br/>
    <input type="email" name="email" value=""/>
    <br/>
    <br/>
    Firstname
    <br/>
    <input type="text" name="firstname" value=""/>
    <br/>
    <br/>
    Lastname
    <br/>
    <input type="text" name="lastname" value=""/>
    <br/>
    <br/>
    <input type="submit" value="Sign up"/>
    ${errorLoginPassMessage}
    <br/>
    ${wrongAction}
    <br/>
    ${nullPage}
    <br/>
</form>
</body>
</html>
