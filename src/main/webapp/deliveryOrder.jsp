<%--
  Created by IntelliJ IDEA.
  User: Nazar
  Date: 10.04.2022
  Time: 21:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <%@include file="parts/style.jspf"%>
    <title>Delivery orders</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css">
</head>
<body>
<%@include file="parts/navigation.jspf" %>
<div class="container mt-5">
    <table id="table" class="table table-bordered table-responsive table-hover caption-top table-striped">
        <caption class="bg-light text-dark p-2 fs-5" style="border-radius: 30px 30px 0 0;"><span
                style="padding-left: 25px"><fmt:message key="delivery-order.label.delivery-orders"/></span></caption>

        <thead>
        <tr>
            <th><fmt:message key="delivery-order.label.table.delivery-orders.id"/></th>
            <th><fmt:message key="delivery-order.label.table.delivery-orders.location-from"/></th>
            <th><fmt:message key="delivery-order.label.table.delivery-orders.location-to"/></th>
            <th><fmt:message key="delivery-order.label.table.delivery-orders.cargo-name"/></th>
            <th><fmt:message key="delivery-order.label.table.delivery-orders.cargo-description"/></th>
            <th><fmt:message key="delivery-order.label.table.delivery-orders.address"/></th>
            <th><fmt:message key="delivery-order.label.table.delivery-orders.delivery-type"/></th>
            <th><fmt:message key="delivery-order.label.table.delivery-orders.weight"/></th>
            <th><fmt:message key="delivery-order.label.table.delivery-orders.volume"/></th>
            <th><fmt:message key="delivery-order.label.table.delivery-orders.receiving-date"/></th>
            <th><fmt:message key="delivery-order.label.table.delivery-orders.tariff"/></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${requestScope.deliveryOrders}" var="deliveryOrder">
            <tr>
                <td>${deliveryOrder.id}</td>
                <td>${deliveryOrder.locationFromId}</td>
                <td>${deliveryOrder.locationToId}</td>
                <td>${deliveryOrder.cargoName}</td>
                <td>${deliveryOrder.cargoDescription}</td>
                <td>${deliveryOrder.address}</td>
                <td>${deliveryOrder.deliveryTypeId}</td>
                <td>${deliveryOrder.weight}</td>
                <td>${deliveryOrder.volume}</td>
                <td>${deliveryOrder.receivingDate}</td>
                <td>${deliveryOrder.tariffId}</td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</div>
<%@include file="parts/pagination.jspf"%>
</body>
</html>
