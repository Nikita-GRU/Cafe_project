<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <title><fmt:message bundle="${message}" key="header.order"/></title>
    <jsp:include page="common/header.jsp"/>
    <link href="${pageContext.request.contextPath}\css\order_auth.css" rel="stylesheet">
</head>
<body class="text-center">
<form class="form-signin" method="POST" action="${pageContext.request.contextPath}/controller?command=order">
    <input type="hidden" name="command" value="order"/>
    <div class="row">
        <div class="col-sm">
            Авторизация Заказа
            <label for="inputEmail" class="sr-only">Email address</label>
            <input value="${sessionScope.account.email}" name="email" type="email" id="inputEmail"
                   class="form-control" value="${sessionScope.account.email}"
                   placeholder="<fmt:message bundle="${message}" key="signup.email_address"/>" required
                   autofocus>

            <label for="inputPhoneNumber" class="sr-only">Phone Number in format +375 xx xxx xx xx</label>
            <input value="${sessionScope.account.phoneNumber}" name="phonenumber" type="tel"
                   pattern="^(\+375|80)(29|25|44|33)(\d{3})(\d{2})(\d{2})$" id="inputPhoneNumber"
                   class="form-control" placeholder="<fmt:message bundle="${message}" key="signup.phonenumber"/>"
                   title="+375 xx xxx xx xx" required>

            <label for="inputFirstName" class="sr-only">Firstname</label>
            <input name="firstname" type="text" id="inputFirstName" class="form-control"
                   value="${sessionScope.account.firstName}"
                   placeholder="<fmt:message bundle="${message}" key="signup.firstname"/>" required>
            <label for="inputReview" class="sr-only">review</label>

            <textarea name="review" cols="40" rows="2" id="inputReview" class="form-control"
                      placeholder="<fmt:message bundle="${message}" key="admin.order_table.review"/>"
            ></textarea>


            <input id="datePicker" name="delivery_date" type="datetime-local" class="form-control" required>


            ДОСТАВКА<br/>

            <label>
                <input type="radio" name="delivery_option" value="self_delivery" checked>
            </label>Samovivoz<br/>
            <label>
                <input type="radio" name="delivery_option" value="delivery" >
            </label> Dostavka
            <div class="block-text" id="block-delivery">
                <label for="street" class="sr-only">Firstname</label>
                <input name="street" type="text" id="street" class="form-control"
                       placeholder="street">
                <label for="building" class="sr-only">Building</label>
                <input name="building" type="text" id="building" class="form-control"
                       placeholder="building">
                <label for="apartment" class="sr-only">Flat</label>
                <input name="apartment" type="text" id="apartment" class="form-control"
                       placeholder="apartment">
            </div>
            ОПЛАТА<br/>
            <c:set var="order_price" value="${0}"/>
            <c:forEach items="${sessionScope.cart}" var="product">
                <c:set var="order_price" value="${order_price = (order_price*1000 + product.price*1000)/1000}"/>
            </c:forEach>
            <b> К оплате: ${order_price}</b>
            <c:if test="${sessionScope.account.bonusPoints!=null}">
                <b>Бонусы в наличии: ${sessionScope.account.bonusPoints}</b>
                <input type="number" name="bonus_to_pay" class="form-control" value="0" min="0"
                       max="${sessionScope.account.bonusPoints}" placeholder="Bonuses"/>
            </c:if>
            <br/>
            <input type="radio" name="payment" value="cash" checked> <fmt:message bundle="${message}"
                                                                                  key="order.cash"/>
            <c:if test="${sessionScope.account!=null}">
                <input type="radio" name="payment" value="client_balance"> с клиентского счета

                <input type="radio" name="payment" value="credit"> в кредит
            </c:if>

            <div class="block-payment" id="block-client_balance">
                Ваш баланс:${sessionScope.balance}
            </div>

            <div class="block-payment" id="block-credit">
<%--                <c:if test="${sessionScope.balance > order_price/2}"> Вы можете приобрести в кредит--%>
<%--                    <input type="radio" name="credit_option" value="month_3"/>--%>
<%--                    <input type="radio" name="credit_option" value="month_6"/>--%>
<%--                    <input type="radio" name="credit_option" value="month_9"/>--%>
<%--                </c:if>--%>
<%--                <c:if test="${sessionScope.credit_status==false}"> Извините, покупка в кредит запрещена вашему аккаунту</c:if>--%>
            </div>


            <br/>
            <button class="btn btn-lg btn-primary btn-block button-color" type="submit"><fmt:message
                    bundle="${message}"
                    key="order.order"/></button>
        </div>


        <div class="col-sm">
            YOU'VE ORDERING:
            <c:forEach items="${sessionScope.cart}" var="product">
                <div class="d-inline p-2 ">
                    <div class="row">
                        <div class="col">
                            <img src="${pageContext.request.contextPath}${product.imageUri}"
                                 class="img-fluid order-image"
                                 alt="Responsive image"><br/>
                        </div>
                        <div class="col">
                                ${product.name} <br/>
                                ${product.description}<br/>
                                ${product.price}<br/>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

    </div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>

<script>

    $('.block-payment').hide();
    $('.block-text').hide();
    $(function () {
            if (${sessionScope.balance + (sessionScope.account.bonusPoints * 0.01) < order_price}) {
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

</script>

${sessionScope.errorInputMessage}

</body>
</html>
