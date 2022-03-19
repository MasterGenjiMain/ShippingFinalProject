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
</head>
<body>

<form action="registration" method="post">
    <label> Username
        <input type="text" name="username"> <br>
    </label>
    <label> Email
        <input type="text" name="email"> <br>
    </label>
    <label> Password
        <input type="password" name="password"> <br>
    </label>

    <input type="submit" value="Registration">
    <a href="index.jsp">Back</a>

    <c:out value="${sessionScope.message}"/>
</form>
</body>
</html>
