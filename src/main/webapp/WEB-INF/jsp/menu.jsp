<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <title><fmt:message bundle="${message}" key="header.menu"/></title>
</head>
<body class="text-center demo">
<jsp:useBean id="product_manager" class="by.gruca.cafe.model.ProductManager"/>
<header>
    <jsp:include page="common/header.jsp"/>
</header>
<div class="content">
    <div class="container-fluid">
        <div class="row ">
            <c:forEach items="${product_manager.products}" var="product">
                <div class="col-4 product">
                    <form  method="post" action="${pageContext.request.contextPath}\menu">
<%--xss!!!--%>
                        <input type="hidden" name="checkedproduct"
                               value="<c:out value="${product.name}"/> "/>
                        <img src="${pageContext.request.contextPath}${product.imageUri}" class="img-fluid product-image"
                             alt="Responsive image"><br/>
                            ${product.name}<br>
                            ${product.price}<br>
                            ${product.description}<br>
                        <input type="submit" value="<fmt:message bundle="${message}" key="menu.add_to_cart"/>"/>
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>

</div>
<a href="cart"><fmt:message bundle="${message}" key="menu.go_to_cart"/></a>
<footer>
    <%--    <jsp:include page="common/footer.jsp"/>--%>
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/addons/pagination.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js"></script>
<script>
    $('#demo').pagination({
        dataSource: function(done){
            var result = [];
            for (var i = 1; i < 196; i++) {
                result.push(i);
            }
            done(result);
        },
    pageSize: 5,
        showPrevious: false,
        showNext: false,
        callback: function(data, pagination) {
        // template method of yourself
        var html = template(data);
        dataContainer.html(html);
    }
    })
</script>
</body>

</html>
