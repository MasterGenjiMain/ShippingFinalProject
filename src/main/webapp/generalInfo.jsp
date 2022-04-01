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
</head>
<body>

<%@include file="parts/navigation.jspf" %>
<div class="container mt-5">
    <table class="table table-bordered table-responsive table-hover caption-top table-striped">
        <caption class="bg-light text-dark p-2 fs-5" style="border-radius: 30px 30px 0 0;"><span
                style="padding-left: 25px">Available delivery locations</span></caption>

        <thead>
        <tr>
            <th>Name</th>
            <th>City</th>
            <th>Active Status</th>
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
    <table class="table table-bordered table-responsive table-hover caption-top table-striped">
        <caption class="bg-light text-dark p-2 fs-5" style="border-radius: 30px 30px 0 0;"><span
                style="padding-left: 25px">Available tariffs</span></caption>

        <thead>
        <tr>
            <th>Tariff Name</th>
            <th>Tariff Price</th>
            <td>Tariff Info</td>
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
</body>
</html>
