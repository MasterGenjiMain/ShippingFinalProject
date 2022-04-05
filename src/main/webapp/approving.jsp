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
    <%@include file="parts/style.jspf"%>
    <title>Approving</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css">
</head>
<body>
<%@include file="parts/navigation.jspf" %>
<div class="container mt-5">
    <table id="table" class="table table-bordered table-responsive table-hover caption-top table-striped">
        <caption class="bg-light text-dark p-2 fs-5" style="border-radius: 30px 30px 0 0;"><span
                style="padding-left: 25px">Receipts</span></caption>

        <thead>
        <tr>
            <th>Id</th>
            <th>User Id</th>
            <th>Manager</th>
            <th>Price</th>
            <th>Receipt Status</th>
            <th>Delivery Order Id</th>
            <th>Approving</th>
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
                            <a class="btn btn-secondary" href="<%=request.getContextPath()%>/manager/approving/approve?id=<c:out value='${receipt.id}'/>">Approve</a>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${receipt.receiptStatusName != 'Canceled' && receipt.receiptStatusName != 'Closed'}">
                                    <a class="btn btn-secondary" href="<%=request.getContextPath()%>/manager/approving/next-status?id=<c:out value='${receipt.id}'/>">Next status</a>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="btn btn-secondary" disabled>Next status</button>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <c:choose>
                        <c:when test="${receipt.receiptStatusName != 'Canceled' && receipt.receiptStatusName != 'Closed'}">
                            <a class="btn btn-secondary" href="<%=request.getContextPath()%>/manager/approving/cancel?id=<c:out value='${receipt.id}'/>">Cancel</a>
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

<%@include file="parts/pagination.jspf"%>
</body>
</html>
