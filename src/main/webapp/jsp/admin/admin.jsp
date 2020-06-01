<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head><title>Admin page</title></head>
<body>
<c:set var="admin" value="Blinov" scope="request"/>
<c:import url="fragment\header.jsp" />
<form action="${ pageContext.request.contextPath }/index.jsp">
    Content admin page<br/>
    <input type="submit" value="to Index">
</form>
<c:import url="..\common\footer.jsp" /> </body></
html>