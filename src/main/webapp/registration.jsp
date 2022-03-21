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
    <%@include file="parts/style.jspf"%>
    <title>Registration</title>
</head>
<body>
<%@include file="parts/navigation.jspf"%>
<div class="container mt-5">
    <form action="registration" method="post">
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Username:</label>
            <div class="col-sm-3">
                <input type="text" name="username" class="form-control" placeholder="Username">
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Email:</label>
            <div class="col-sm-3">
                <input type="text" name="email" class="form-control" placeholder="Email">
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-3">
                <input type="password" name="password" class="form-control" placeholder="Password">
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Register</button>
        <a class="btn btn-primary" href="index.jsp" role="button">Back</a>
        <c:out value="${requestScope.message}"/>
    </form>
</div>
</body>
</html>
