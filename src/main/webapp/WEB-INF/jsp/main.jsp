
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <jsp:include page="common/header.jsp"/>
</head>
<body>

<h1 class="h1 mb-3 font-weight-normal text-xl-center"><b>Welcome to EPAM Cafe</b></h1>
<div class="col-lg-12 d-flex justify-content-center">
<div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner" >
        <div class="carousel-item active">
            <div class="card mb-3" >
                <div class="row no-gutters">
                    <div class="col-md-5">
                        <img src="${pageContext.request.contextPath}/images/main/drinks-main.jpg" class="d-block w-100"
                             alt="..."/>
                    </div>
                    <div class="col-md-7">
                        <div class="card-body">
                            <h5 class="card-title">Enjoy best drinks</h5>
                            <p class="card-text">Phasellus volutpat, metus eget egestas mollis, lacus lacus blandit dui, id egestas quam mauris ut lacus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vivamus euismod mauris. Donec elit libero, sodales nec, volutpat a, suscipit non, turpis. Praesent metus tel

                                Curabitur nisi. Phasellus nec sem in justo pellentesque facilisis. Sed a libero. Phasellus consectetuer vestibulum elit. Donec orci lectus, aliquam ut, faucibus non, euismod id, nulla. Vestibulum rutrum, mi nec elementum vehicula, eros quam gravida nisl, id fringilla neque ante vel mi. Cum sociis natoque penatibus</p>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div class="carousel-item">
            <div class="card mb-3" >
                <div class="row no-gutters">
                    <div class="col-md-5">
                        <img src="${pageContext.request.contextPath}/images/main/pizza-main.jpg" class="d-block w-100" alt="...">
                    </div>
                    <div class="col-md-7">
                        <div class="card-body">
                            <h5 class="card-title">Enjoy best pizzas</h5>
                            <p class="card-text">Phasellus volutpat, metus eget egestas mollis, lacus lacus blandit dui, id egestas quam mauris ut lacus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vivamus euismod mauris. Donec elit libero, sodales nec, volutpat a, suscipit non, turpis. Praesent metus tel

                                Curabitur nisi. Phasellus nec sem in justo pellentesque facilisis. Sed a libero. Phasellus consectetuer vestibulum elit. Donec orci lectus, aliquam ut, faucibus non, euismod id, nulla. Vestibulum rutrum, mi nec elementum vehicula, eros quam gravida nisl, id fringilla neque ante vel mi. Cum sociis natoque penatibus.</p>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div class="carousel-item">
            <div class="card mb-3" >
                <div class="row no-gutters">
                    <div class="col-md-5">
                        <img src="${pageContext.request.contextPath}/images/main/salad-main.jpg" class="d-block w-100" alt="...">
                    </div>
                    <div class="col-md-7">
                        <div class="card-body">
                            <h5 class="card-title">Enjoy best salads</h5>
                            <p class="card-text">Phasellus volutpat, metus eget egestas mollis, lacus lacus blandit dui, id egestas quam mauris ut lacus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vivamus euismod mauris. Donec elit libero, sodales nec, volutpat a, suscipit non, turpis. Praesent metus tel

                                Curabitur nisi. Phasellus nec sem in justo pellentesque facilisis. Sed a libero. Phasellus consectetuer vestibulum elit. Donec orci lectus, aliquam ut, faucibus non, euismod id, nulla. Vestibulum rutrum, mi nec elementum vehicula, eros quam gravida nisl, id fringilla neque ante vel mi. Cum sociis natoque penatibus</p>

                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>
</div>

<footer class="text-center fixed-bottom overflow-hidden">
    <ctg:copyright-tag/>
</footer>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</html>