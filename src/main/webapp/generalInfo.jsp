<%--
  Created by IntelliJ IDEA.
  User: Nazar
  Date: 21.03.2022
  Time: 2:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <%@include file="parts/style.jspf"%>
    <title>General info</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css">
</head>
<body>

<%@include file="parts/navigation.jspf" %>
<div class="container mt-5">
    <table id="table" class="table table-bordered table-responsive table-hover caption-top table-striped">
        <caption class="bg-light text-dark p-2 fs-5" style="border-radius: 30px 30px 0 0;"><span
                style="padding-left: 25px"><fmt:message key="general-info.label.locations"/></span></caption>

        <thead>
        <tr>
            <th><fmt:message key="general-info.label.table.location.name"/></th>
            <th><fmt:message key="general-info.label.table.location.city"/></th>
            <th><fmt:message key="general-info.label.table.location.active-status"/></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${requestScope.locations}" var="location">
            <tr>
                <td>${location.locationName}</td>
                <td>${location.cityName}</td>
                <td>${location.activeStatus}</td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</div>
<div class="container mt-5">
    <table id="table2" class="table table-bordered table-responsive table-hover caption-top table-striped">
        <caption class="bg-light text-dark p-2 fs-5" style="border-radius: 30px 30px 0 0;"><span
                style="padding-left: 25px"><fmt:message key="general-info.label.tariffs"/></span></caption>

        <thead>
        <tr>
            <th><fmt:message key="general-info.label.table.tariff.name"/></th>
            <th><fmt:message key="general-info.label.table.tariff.price"/></th>
            <th><fmt:message key="general-info.label.table.tariff.info"/></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${requestScope.tariffs}" var="tariff">
            <tr>
                <td>${tariff.tariffName}</td>
                <td>${tariff.tariffPrice}</td>
                <td>${tariff.tariffInfo}</td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</div>

<%@include file="parts/pagination.jspf"%>
<%@include file="parts/refreshPageCookie.jspf"%>
</body>
</html>
