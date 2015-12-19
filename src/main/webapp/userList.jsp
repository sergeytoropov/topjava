<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h3>Список зарегистрированных пользователей</h3>
<%--<form action="users" method="post">
    <p>
        <select size="10" multiple name="loggedUserId">
            <option disabled>Выберите пользователя</option>

            <c:set var="selected" value="0"/>
            <c:forEach items="${userList}" var="user">
                <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
                <option <c:if test="${selected == 0}">selected</c:if> value="${user.id}">${user.name}</option>
                <c:set var="selected" value="1"/>
            </c:forEach>
        </select>
    </p>
    <p><input type="submit" value="Войти"></p>--%>
</form>
</body>
</html>
