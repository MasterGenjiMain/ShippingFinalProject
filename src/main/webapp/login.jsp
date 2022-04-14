<%--
  Created by IntelliJ IDEA.
  User: Nazar
  Date: 19.03.2022
  Time: 1:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="parts/style.jspf"%>
    <title>Login</title>
</head>
<body>
<%@include file="parts/navigation.jspf"%>
<div class="container mt-5">
    <form action="login" method="post">
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="login.label.login"/>:</label>
            <div class="col-sm-3">
                <input type="text" name="login" class="form-control" placeholder="<fmt:message key="login.placeholder.login"/>" required>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label"><fmt:message key="login.label.password"/></label>
            <div class="col-sm-3">
                <input type="password" name="password" class="form-control" placeholder="<fmt:message key="login.placeholder.password"/>" required>
            </div>
        </div>
        <button class="btn btn-primary" type="submit"><fmt:message key="login.label.button.sign-in"/></button>
        <a class="btn btn-primary" href="index.jsp" role="button"><fmt:message key="login.label.button.back"/></a>
        <c:if test="${requestScope.message != null}">
        <div class="alert alert-danger mt-3 alert-dismissible fade show" role="alert">
            <c:out value="${requestScope.message}"/>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        </c:if>
    </form>
</div>
</body>
</html>
