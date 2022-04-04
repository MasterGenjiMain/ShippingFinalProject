<%--
  Created by IntelliJ IDEA.
  User: Nazar
  Date: 23.03.2022
  Time: 3:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <%@include file="parts/style.jspf"%>
    <title>Account</title>
</head>
<body>
<%@include file="parts/navigation.jspf"%>
<div class="container mt-5">
    <table class="table table-bordered table-responsive table-hover caption-top table-striped">
        <caption class="bg-light text-dark p-2 fs-5" style="border-radius: 30px 30px 0 0;"><span
                style="padding-left: 25px">Current receipts</span></caption>

        <thead>
        <tr>
            <th>Id</th>
            <th>Manager</th>
            <th>Price</th>
            <th>Receipt Status</th>
            <th>Delivery Order Id</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${requestScope.receipts}" var="receipt">
            <tr>
                <td>${receipt.id}</td>
                <td>${receipt.managerName}</td>
                <td>${receipt.price}</td>
                <td>${receipt.receiptStatusName}</td>
                <td>${receipt.deliveryOrderId}</td>

                <td class="text-center">
                <c:choose>
                    <c:when test="${receipt.receiptStatusName == 'Waiting for payment'}">
                        <a class="btn btn-secondary" href="<%=request.getContextPath()%>/user/account/pay?id=<c:out value='${receipt.id}'/>">Pay</a>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn btn-secondary" disabled>Pay</button>
                    </c:otherwise>
                </c:choose>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <c:choose>
                    <c:when test="${receipt.receiptStatusName == 'New' || receipt.receiptStatusName == 'Waiting for payment' || receipt.receiptStatusName == 'Paid'}">
                        <a class="btn btn-secondary" href="<%=request.getContextPath()%>/user/account/cancel?id=<c:out value='${receipt.id}'/>">Cancel</a>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn btn-secondary" disabled>Cancel</button>
                    </c:otherwise>
                </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</div>
</body>
</html>