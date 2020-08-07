<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <title><fmt:message bundle="${message}" key="header.order"/></title>
    <jsp:include page="common/header.jsp"/>
    <link href="${pageContext.request.contextPath}\css\order_auth.css" rel="stylesheet">
</head>
<body class="text-center">
<div class="page-container">
    <form class="form-sign_in" method="POST" action="${pageContext.request.contextPath}/controller" accept-charset=«UNICODE»>
        <input type="hidden" name="command" value="order"/>
        <input type="hidden" name="price" value="${requestScope.price}"/>

        <div class="row">
            <div class="col-sm">
                Авторизация Заказа
                <label for="inputEmail" class="sr-only"></label>
                <input value="${sessionScope.account.email}" name="email" type="email" id="inputEmail"
                       class="form-control"
                       placeholder="<fmt:message bundle="${message}" key="sign_up.email_address"/>" required
                autofocus>

                <label for="inputPhoneNumber" class="sr-only">Phone Number in format +375 xx xxx xx xx</label>
                <input value="${sessionScope.account.phoneNumber}" name="phone_number" type="tel"
                       pattern="^(\+375|80)(29|25|44|33)(\d{3})(\d{2})(\d{2})$" id="inputPhoneNumber"
                       class="form-control" placeholder="<fmt:message bundle="${message}" key="sign_up.phone_number"/>"
                       title="+375 xx xxx xx xx" required>

                <label for="inputFirstName" class="sr-only">First name</label>
                <input name="first_name" type="text" id="inputFirstName" class="form-control"
                       value="${sessionScope.account.firstName}"
                       placeholder="<fmt:message bundle="${message}" key="sign_up.first_name"/>" required>

                <label for="inputReview" class="sr-only">review</label>
                <textarea name="review" cols="40" rows="2" id="inputReview" class="form-control"
                          placeholder="<fmt:message bundle="${message}" key="admin.order_table.review"/>"
                ></textarea>

             ДАТА И ВРЕМЯ ДОСТАВКИ<br/>
                <label for="datePicker" class="sr-only"></label>
                <input id="datePicker" name="delivery_date" type="datetime-local" class="form-control" required>

                ДОСТАВКА<br/>
                <div class="btn-group btn-group-toggle " data-toggle="buttons">
                    <label class="btn  btn-outline-info">
                        <input type="radio" name="delivery_option" value="self_delivery" checked>Samovivoz
                    </label>
                    <label class="btn  btn-outline-info ">
                        <input type="radio" name="delivery_option" value="delivery">Dostavka
                    </label>
                </div>
                <br/>

                <div class="block-text " id="block-delivery">
                    <label for="suggest1" class="sr-only">Street</label>
                    <input name="address" type="text" id="suggest1" class="form-control"
                           placeholder="address">
                    <label for="apartment" class="sr-only">Flat</label>
                    <input name="apartment" type="text" id="apartment" class="form-control"
                           placeholder="apartment">
                </div>

                ОПЛАТА
                <p class="text-black-50"><b> К оплате: ${requestScope.price}</b></p>
                <c:if test="${sessionScope.account.bonus !=null && sessionScope.account.bonus>0}">
                <p class="text-black-50"><b>Бонусы в наличии: ${sessionScope.account.bonus}. Задействовать бонусы: </b></p>
                    <label for="bonus_payment" class="sr-only"> use bonuses</label>
                    <input id="bonus_payment" type="number" name="bonus_to_pay" class="form-control" value="0" min="0"
                           max="${sessionScope.account.bonus}" placeholder="Bonuses"/>
                </c:if>
                <br/>

                <div class="btn-group btn-group-toggle " data-toggle="buttons">
                    <label class="btn  btn-outline-info">
                        <input type="radio" name="payment" value="cash" checked> <fmt:message bundle="${message}"
                                                                                              key="order.cash"/>
                    </label>
                    <c:if test="${sessionScope.account!=null}">
                        <label class="btn  btn-outline-info ">
                            <input type="radio" name="payment" value="balance"> с клиентского счета
                        </label>
                    </c:if>
                </div>

                <div class="block-payment" id="block-client_balance">
                    Ваш баланс:${sessionScope.account.balance}
                </div>
                <br/>
                <br/>
                <button class="btn btn-lg btn-primary btn-block button-color" type="submit"><fmt:message
                        bundle="${message}"
                        key="order.order"/></button>
            </div>
            <div class="col-sm">
<c:forEach items="${sessionScope.cart.keySet()}" var="product">
            <div class="card mb-1" >
                <div class="row no-gutters">
                    <div class="col-md-4">
                        <img src="${pageContext.request.contextPath}${product.imageUri}"
                             class="img-fluid "
                             alt="Responsive image">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title " style="height: 2rem">${product.name}</h5>
                            <h7 class="card-subtitle">${product.price} * ${sessionScope.cart.get(product)}</h7>
                            <p class="card-text text-secondary" >${product.description}.</p>
                        </div>
                    </div>
                </div>
            </div>
</c:forEach>
            </div>
        </div>
    </form>
</div>
<footer class="text-center fixed-bottom overflow-hidden">
    <ctg:copyright-tag/>
</footer>
</body>
<script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU&amp;apikey=<f97a619a-70b2-45fd-976f-fcf4a0232fa5>" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/address_suggest.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/js/provider.js" type="text/javascript"></script>
<script>
    $('.block-payment').hide();
    $('.block-text').hide();
    $(function () {
            if (${sessionScope.account.balance + (sessionScope.account.bonus * 0.01) < requestScope.price}) {
                $('input[name="payment"][value="client_balance"]').attr("disabled", true);
            }
        }
    )
    $('input[name="delivery_option"]').click(function () {
        var target = $('#block-' + $(this).val());

        $('.block-text').not(target).hide(0);
        target.fadeIn(500);
    });
    $('input[name="payment"]').click(function () {
        var target = $('#block-' + $(this).val());

        $('.block-payment').not(target).hide(0);
        target.fadeIn(500);
    });
    $(function () {
    })
</script>

${sessionScope.errorInputMessage}



</html>
