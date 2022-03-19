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
    <title>Login</title>
</head>
<body>

<form action="login" method="post">
    <label> Login
        <input type="text" name="login">
    </label> <br>
    <label> Password
        <input type="password" name="password">
    </label> <br>

    <input type="submit" value="Login">
    <a href="index.jsp">Back</a>

    <c:out value="${sessionScope.message}"/>
</form>
</body>
</html>
