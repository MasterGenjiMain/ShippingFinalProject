<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <%@include file="parts/style.jspf"%>
    <title>CDS</title>
</head>
<body>

<%@include file="parts/navigation.jspf"%>
<div class="container mt-5">
    <div class="text-center">
        <h1>Welcome to Cargo Delivery System,
            <c:if test="${sessionScope.user == null}">
                Guest
            </c:if>
            <c:out value="${sessionScope.user.username}"/>
        </h1>
    </div>
</div>


</body>
</html>