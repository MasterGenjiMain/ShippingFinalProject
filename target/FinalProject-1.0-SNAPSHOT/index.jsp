<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>CDS</title>
</head>
<body>
<h1><%= "Cargo Delivery System" %>
</h1>
<br/>
<a href="login">login</a>
<a href="registration">registration</a>

<p>
    Logged user -> <c:out value="${sessionScope.user.username}"/>
</p>
</body>
</html>