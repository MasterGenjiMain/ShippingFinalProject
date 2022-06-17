<%--
  Created by IntelliJ IDEA.
  User: Nazar
  Date: 29.03.2022
  Time: 7:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Approving</title>
    <%@include file="parts/style.jspf"%>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css">
</head>
<body>
<%@include file="parts/navigation.jspf" %>
<div class="container mt-5">
    <table id="table" class="table table-bordered table-responsive table-hover caption-top table-striped">
        <caption class="bg-light text-dark p-2 fs-5" style="border-radius: 30px 30px 0 0;"><span
                style="padding-left: 25px"><fmt:message key="approving.label.receipts"/></span></caption>

        <thead>
        <tr>
            <th><fmt:message key="approving.label.table.receipts.id"/></th>
            <th><fmt:message key="approving.label.table.receipts.user-id"/></th>
            <th><fmt:message key="approving.label.table.receipts.manager"/></th>
            <th><fmt:message key="approving.label.table.receipts.price"/></th>
            <th><fmt:message key="approving.label.table.receipts.receipt-status"/></th>
            <th><fmt:message key="approving.label.table.receipts.delivery-order-id"/></th>
            <th><fmt:message key="approving.label.table.receipts.action"/></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${requestScope.receipts}" var="receipt">
            <tr>
                <td>${receipt.id}</td>
                <td>${receipt.userId}</td>
                <td>${receipt.managerName}</td>
                <td>${receipt.price}</td>
                <td>${receipt.receiptStatusName}</td>
                <td>${receipt.deliveryOrderId}</td>

                <td class="text-center">
                    <c:choose>
                        <c:when test="${receipt.receiptStatusName == 'New'}">
                            <a class="btn btn-secondary" href="<%=request.getContextPath()%>/manager/approving/approve?id=<c:out value='${receipt.id}'/>">
                                <fmt:message key="approving.label.table.receipts.button.approve"/></a>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${receipt.receiptStatusName == 'Waiting for payment'}">
                                    <a class="btn btn-secondary" href="<%=request.getContextPath()%>/manager/approving/next-status?id=<c:out value='${receipt.id}'/>">
                                        <fmt:message key="approving.label.table.receipts.button.paid"/></a>
                                </c:when>
                                <c:when test="${receipt.receiptStatusName == 'Paid'}">
                                    <a class="btn btn-secondary" href="<%=request.getContextPath()%>/manager/approving/next-status?id=<c:out value='${receipt.id}'/>">
                                        <fmt:message key="approving.label.table.receipts.button.preparing"/></a>
                                </c:when>
                                <c:when test="${receipt.receiptStatusName == 'Preparing'}">
                                    <a class="btn btn-secondary" href="<%=request.getContextPath()%>/manager/approving/next-status?id=<c:out value='${receipt.id}'/>">
                                        <fmt:message key="approving.label.table.receipts.button.delivering"/></a>
                                </c:when>
                                <c:when test="${receipt.receiptStatusName == 'Delivering'}">
                                    <a class="btn btn-secondary" href="<%=request.getContextPath()%>/manager/approving/next-status?id=<c:out value='${receipt.id}'/>">
                                        <fmt:message key="approving.label.table.receipts.button.delivered"/></a>
                                </c:when>
                                <c:when test="${receipt.receiptStatusName == 'Delivered'}">
                                    <a class="btn btn-secondary" href="<%=request.getContextPath()%>/manager/approving/next-status?id=<c:out value='${receipt.id}'/>">
                                        <fmt:message key="approving.label.table.receipts.button.closed"/></a>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="btn btn-secondary" disabled><fmt:message key="approving.label.table.receipts.button.closed"/></button>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <c:choose>
                        <c:when test="${receipt.receiptStatusName != 'Canceled' && receipt.receiptStatusName != 'Closed'}">
                            <a class="btn btn-secondary" href="<%=request.getContextPath()%>/manager/approving/cancel?id=<c:out value='${receipt.id}'/>">
                                <fmt:message key="approving.label.table.receipts.button.cancel"/></a>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="btn btn-secondary" disabled><fmt:message key="approving.label.table.receipts.button.cancel"/></button>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</div>

<%@include file="parts/pagination.jspf"%>
</body>
</html>
