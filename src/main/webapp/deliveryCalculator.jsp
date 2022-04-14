<%--
  Created by IntelliJ IDEA.
  User: Nazar
  Date: 22.03.2022
  Time: 1:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <%@include file="parts/style.jspf"%>
    <title>Tariffs</title>

</head>
<body>
<%@include file="parts/navigation.jspf"%>
<div class="container mt-5">
    <form action="delivery-calculator" method="post">
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="tariff.label.height"/>:</label>
            <div class="col-sm-3">
                <input type="number" name="height" class="form-control" placeholder="<fmt:message key="tariff.placeholder.height"/>" required>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="tariff.label.width"/>:</label>
            <div class="col-sm-3">
                <input type="number" name="width" class="form-control" placeholder="<fmt:message key="tariff.placeholder.width"/>" required>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="tariff.label.length"/>:</label>
            <div class="col-sm-3">
                <input type="number" name="length" class="form-control" placeholder="<fmt:message key="tariff.placeholder.length"/>" required>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="tariff.label.distance"/>:</label>
            <div class="col-sm-3">
                <input type="number" name="distance" class="form-control" placeholder="<fmt:message key="tariff.placeholder.distance"/>" required>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="tariff.label.weight"/>:</label>
            <div class="col-sm-3">
                <input type="number" name="weight" class="form-control" placeholder="<fmt:message key="tariff.placeholder.weight"/>" required>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="tariff.label.tariff"/>:</label>
            <div class="col-sm-3">
                <select class="form-select" aria-label="Default select example" name="tariff" required>
                    <option disabled selected value="">-----</option>
                    <c:forEach items="${requestScope.tariffs}" var="tariff">
                        <option>${tariff.tariffName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <button class="btn btn-primary" type="submit"><fmt:message key="tariff.label.button.calculate"/></button>
        <a class="btn btn-primary" href="index.jsp" role="button"><fmt:message key="tariff.label.button.back"/></a>
        <c:if test="${requestScope.message != null}">
            <div class="alert alert-danger mt-3" role="alert">
                <c:out value="${requestScope.message}"/>
            </div>
        </c:if>
        <c:if test="${sessionScope.price != null}">
        <div class="alert alert-success mt-3 alert-dismissible fade show" role="alert">
            <c:out value="Your calculated price: ${sessionScope.price} UAH"/>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        </c:if>
    </form>
</div>
<%@include file="parts/refreshPageCookie.jspf"%>
</body>
</html>
