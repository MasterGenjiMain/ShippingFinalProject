<%--
  Created by IntelliJ IDEA.
  User: Nazar
  Date: 24.03.2022
  Time: 4:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <%@include file="parts/style.jspf"%>
    <title>Delivery Request</title>
</head>
<body>
<%@include file="parts/navigation.jspf" %>
<div class="container mt-5">
    <form action="delivery-request" method="post">
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="delivery-request.label.cargo-name"/>:</label>
            <div class="col-sm-3">
                <input type="text" name="cargoName" class="form-control" placeholder="<fmt:message key="delivery-request.placeholder.cargo-name"/>">
            </div>
        </div>
        <div class="mb-5 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="delivery-request.label.cargo-description"/>:</label>
            <div class="col-sm-3">
                <input type="text" name="cargoDescription" class="form-control" placeholder="<fmt:message key="delivery-request.placeholder.cargo-description"/>">
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="delivery-request.label.location-from"/>:</label>
            <div class="col-sm-3">
                <select class="form-select" aria-label="Default select example" name="locationFrom">
                    <option disabled selected>-----</option>
                    <c:forEach items="${requestScope.locations}" var="location">
                        <option>${location.locationName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="delivery-request.label.location-to"/>:</label>
            <div class="col-sm-3">
                <select class="form-select" aria-label="Default select example" name="locationTo">
                    <option disabled selected>-----</option>
                    <c:forEach items="${requestScope.locations}" var="location">
                        <option>${location.locationName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="delivery-request.label.address"/>:</label>
            <div class="col-sm-3">
                <input type="text" name="address" class="form-control" placeholder="<fmt:message key="delivery-request.placeholder.address"/>">
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="delivery-request.label.delivery-type"/>:</label>
            <div class="col-sm-3">
                <select class="form-select" aria-label="Default select example"  name="deliveryType">
                    <option disabled selected>-----</option>
                    <c:forEach items="${requestScope.deliveryTypes}" var="deliveryType">
                        <option>${deliveryType.typeName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="delivery-request.label.weight"/>:</label>
            <div class="col-sm-3">
                <input type="number" name="weight" class="form-control" placeholder="<fmt:message key="delivery-request.placeholder.weight"/>">
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="delivery-request.label.volume"/>:</label>
            <div class="col-sm-3">
                <input type="number" name="volume" class="form-control" placeholder="<fmt:message key="delivery-request.placeholder.volume"/>">
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="delivery-request.label.distance"/>:</label>
            <div class="col-sm-3">
                <input type="number" name="distance" class="form-control" placeholder="<fmt:message key="delivery-request.placeholder.distance"/>">
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="delivery-request.label.tariff"/>:</label>
            <div class="col-sm-3">
                <select class="form-select" aria-label="Default select example" name="tariff">
                    <option disabled selected>-----</option>
                    <c:forEach items="${requestScope.tariffs}" var="tariff">
                        <option>${tariff.tariffName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <button class="btn btn-primary" type="submit"><fmt:message key="delivery-request.label.button.create"/></button>
        <a class="btn btn-primary" href="<%=request.getContextPath()%>/index.jsp" role="button"><fmt:message key="delivery-request.label.button.back"/></a>
        <c:out value="${sessionScope.message}"/>
    </form>
</div>

</body>
</html>
