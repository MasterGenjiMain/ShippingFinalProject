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
    <form action="tariffs" method="post">
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Height:</label>
            <div class="col-sm-3">
                <input type="number" name="height" class="form-control" placeholder="Height (cm)">
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Width:</label>
            <div class="col-sm-3">
                <input type="number" name="width" class="form-control" placeholder="Width (cm)">
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Length:</label>
            <div class="col-sm-3">
                <input type="number" name="length" class="form-control" placeholder="Length (cm)">
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Distance:</label>
            <div class="col-sm-3">
                <input type="number" name="distance" class="form-control" placeholder="Distance (km)">
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Weight:</label>
            <div class="col-sm-3">
                <input type="number" name="weight" class="form-control" placeholder="Weight (kg)">
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Calculate</button>
        <a class="btn btn-primary" href="index.jsp" role="button">Back</a>
        <c:out value="${requestScope.message}"/>
        <c:if test="${sessionScope.price != null}">
            <c:out value="${sessionScope.price} UAH"/>
        </c:if>
    </form>
</div>

</body>
</html>
