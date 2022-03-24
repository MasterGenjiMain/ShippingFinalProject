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
            <th>User Id</th>
            <th>Manager Id</th>
            <th>Price</th>
            <th>Receipt Status</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${requestScope.receipts}" var="receipt">
            <tr>
                <td>${receipt.userId}</td>
                <td>${receipt.managerId}</td>
                <td>${receipt.price}</td>
                <td>${receipt.receiptStatusId}</td>

                <td class="text-center"><a class="btn btn-secondary" onclick="" href="#">Pay</a></td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</div>
</body>
</html>