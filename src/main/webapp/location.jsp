<%@ page import="java.util.List" %>
<%@ page import="com.my.deliverysystem.db.entity.Location" %>
<%@ page import="com.my.deliverysystem.dao.implementation.LocationDAOImplementation" %>
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
    <title>CDS</title>
</head>
<body>

<%@include file="parts/navigation.jspf" %>
<div class="container mt-5">
    <table class="table table-bordered table-responsive table-hover caption-top table-striped">
        <caption class="bg-light text-dark p-2 fs-5" style="border-radius: 30px 30px 0px 0px;"><span
                style="padding-left: 25px">Available delivery locations</span></caption>

        <thead>
        <tr>
            <th>Name</th>
            <th>City Id</th>
            <th>Is active</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${requestScope.locations}" var="location">
            <tr>
                <td>${location.locationName}</td>
                <td>${location.cityId}</td>
                <td>${location.isActive}</td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</div>
</body>
</html>
