<%--
  Created by IntelliJ IDEA.
  User: Nazar
  Date: 19.03.2022
  Time: 1:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
    <%@include file="parts/style.jspf"%>
</head>
<body>
<%@include file="parts/navigation.jspf"%>
<div class="container mt-5">
    <form action="registration" method="post">
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="registration.label.username"/>:</label>
            <div class="col-sm-3">
                <input type="text" name="username" class="form-control" placeholder="<fmt:message key="registration.placeholder.username"/>" required>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="registration.label.email"/>:</label>
            <div class="col-sm-3">
                <input type="email" name="email" class="form-control" placeholder="<fmt:message key="registration.placeholder.email"/>" required>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="registration.label.password"/>:</label>
            <div class="col-sm-3">
                <input type="password" name="password" class="form-control" placeholder="<fmt:message key="registration.placeholder.password"/>" required>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="registration.label.repeat-password"/>:</label>
            <div class="col-sm-3">
                <input type="password" name="repeat-password" class="form-control" placeholder="<fmt:message key="registration.placeholder.repeat-password"/>" required>
            </div>
        </div>
        <button class="btn btn-primary" type="submit"><fmt:message key="registration.label.button.registration"/></button>
        <a class="btn btn-primary" href="index.jsp" role="button"><fmt:message key="registration.label.button.back"/></a>
        <c:if test="${requestScope.message != null}">
            <div class="alert alert-danger mt-3 alert-dismissible fade show" role="alert" role="alert">
                <c:out value="${requestScope.message}"/>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
    </form>
</div>
</body>
</html>
