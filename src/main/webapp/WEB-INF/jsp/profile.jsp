<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="message"/>
<html>
<head>
    <title>${sessionScope.account.firstName} profile</title>
    <jsp:include page="common/header.jsp"/>
</head>
<body>
<div class="d-flex" id="wrapper">
    <c:if test="${pageContext.request.getParameter('command') == 'show_profile'}">
        <table class="table table-responsive table-striped table-bordered">
            <thead>
            <tr class="tr-color">
                <th><fmt:message bundle="${message}" key="admin.account_table.email"/></th>
                <th><fmt:message bundle="${message}" key="admin.account_table.first_name"/></th>
                <th><fmt:message bundle="${message}" key="admin.account_table.phone_number"/></th>
                <th><fmt:message bundle="${message}" key="admin.account_table.bonus"/></th>
                <th><fmt:message bundle="${message}" key="admin.account_table.role"/></th>
                <th></th>
            </tr>
            </thead>
            <form method="post" action="${pageContext.request.contextPath}\profile\info">
                <input type="hidden" name="sub_command" value="update">
                <input type="hidden" name="email_to_update" value="${user.email}">
                <tr>
                    <td>${sessionScope.account.email}</td>
                    <td>${sessionScope.account.firstName}</td>
                    <td>${sessionScope.account.phoneNumber}</td>
                    <td>${sessionScope.account.bonus}"</td>
                    <td>${sessionScope.account.role.name()}</td>
                    <td><input type="submit" value="<fmt:message bundle="${message}" key="admin.button.edit"/>"></td>
                </tr>
            </form>
        </table>


    </c:if>


    <c:if test="${pageContext.request.getParameter('command') == 'show_profile_orders'}">
        <table class="table table-responsive table-striped table-bordered">
            <thead>
            <tr class="tr-color">
                <th>delivery date</th>
                <th><fmt:message bundle="${message}" key="admin.order_table.products"/></th>
                <th><fmt:message bundle="${message}" key="admin.order_table.price"/></th>
                <th>paid by bonus</th>
                <th>status</th>
                <th>feedback</th>
                <th>score</th>
            </tr>
            </thead>
            <c:forEach items="${requestScope.orders}" var="order">

                <tr>
                    <td>${order.deliveryDate}</td>
                    <td>
                        <c:forEach items="${order.products.keySet()}" var="key">
                            ${key.name}(${order.products.get(key)})
                            <br/>
                        </c:forEach>
                    </td>
                    <td>${order.price}</td>
                    <td>${order.bonusToPay}</td>
                    <td>${order.orderStatus}</td>
                    <td>${order.feedback}</td>
                    <td>${order.score}</td>
                    <td>
                        <button type="button" class="btn btn-primary my-color" data-toggle="modal"
                                data-target="#exampleModalCenter" data-whatever="${order.id}">
                            set review
                        </button>

                    </td>

                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog " role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form action="${pageContext.request.contextPath}/controller" method="POST">
                <input type="hidden" name="command" value="set_order_feedback">
                <input type="hidden" name="order_id" id="hidden_order_id">
                <div class="modal-body">
                    <label for="feedback_text">Did you enjoy the order?</label><br/>
                    <textarea id="feedback_text" name="feedback"></textarea><br/>
                    <label for="score_value">Give a score!</label><br/>
                    <input name="score" type="number" value="" min="1" max="5" id="score_value"><br/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                    </button>
                    <button class="btn btn-primary" type="submit">Save changes</button>
                </div>
            </form>

        </div>
    </div>
</div>

<footer class="text-center fixed-bottom overflow-hidden">
    <ctg:copyright-tag/>
</footer>
</body>


</html>
<script>
    $('#exampleModalCenter').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find("#hidden_order_id").val(recipient)
        modal.find('.modal-title').text('New message to ' + recipient)
        modal.find('.modal-body input').val(recipient)
    })
</script>