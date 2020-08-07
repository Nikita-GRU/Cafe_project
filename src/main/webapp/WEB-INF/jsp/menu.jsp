<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<meta http-equiv="Cache-Control" content="no-cache">
<head>
    <title><fmt:message bundle="${message}" key="header.menu"/></title>
</head>
<body class="text-center ">
<header>
    <jsp:include page="common/header.jsp"/>

</header>
<div class="d-flex">
    <div class="bg-light border-right" id="sidebar-wrapper">
        <div class="list-group list-group-flush">
            <c:forEach items="${requestScope.categories}" var="category">
                <a class="list-group-item list-group-item-action bg-light"
                   href="${pageContext.request.contextPath}\menu?category=${category.category}"> ${category} </a>
            </c:forEach>
        </div>
    </div>

    <div class="content">
        <div class="container ">
            <div class="row">
                <c:forEach items="${requestScope.products}" var="product">
                    <div class="card" style="width: 15rem; ">
                        <img src="${pageContext.request.contextPath}${product.imageUri}" class="card-img-top"
                             alt="${product.name}" style="height: 14rem">
                        <div class="card-body">
                            <h3 class="card-title " style="height: 4rem">${product.name}</h3>
                            <h4 class="card-subtitle">${product.price}</h4>
                            <button type="button" class="btn btn-link  " data-toggle="modal" style="color: #17a2b8"
                                    data-target="#exampleModalCenter" data-whatever="${product.description}">
                               Details
                            </button><br/>

                            <form method="post" class="to-cart"
                                  action="${pageContext.request.contextPath}\controller">
                                <input type="hidden" name="command" value="to_cart">
                                <input type="hidden" name="checked_product"
                                       value="<c:out value="${product.name}"/> "/>
                                <input type="hidden" name="redirect_uri"
                                       value="${pageContext.request.getAttribute("javax.servlet.forward.request_uri")}">
                                <input type="hidden" name="redirect_query"
                                       value="${pageContext.request.getAttribute("javax.servlet.forward.query_string")}">
                                <footer><input type="submit" class="my-color btn btn-primary btn-md btn-block"
                                               value="<fmt:message bundle="${message}" key="menu.add_to_cart"/>"/>
                                </footer>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <p id="product_description"></p>
                </div>
            </div>
        </div>
    </div>
</div>
<footer class="text-center fixed-bottom overflow-hidden">
    <ctg:copyright-tag/>
</footer>

</body>
<script>

    $('#exampleModalCenter').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find("#product_description").text(recipient)

    })
</script>
</html>
