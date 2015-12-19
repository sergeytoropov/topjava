<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Добро пожаловать на проект Top Java</title>
</head>
<body>
<h1>Проект "<a href="https://github.com/JavaWebinar/topjava05" target="_blank">Top Java</a>"</h1>
<hr>
<h2>Вход</h2>
<hr>
<form action="login" method="post">
    <table border="0" cellpadding="10" cellspacing="0">
        <tbody>
        <tr>
            <td>Имя пользователя:</td>
            <td>
                <select name="loggedUserId">
                    <option disabled>Выберите пользователя</option>

                    <c:set var="selected" value="0"/>
                    <c:forEach items="${userList}" var="user">
                        <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
                        <option <c:if test="${selected == 0}">selected</c:if> value="${user.id}">${user.name}</option>
                        <c:set var="selected" value="1"/>
                    </c:forEach>
                </select>
            </td>
        </tr>
        </tbody>
    </table>
    <hr>
    <p><input type="submit" value="Войти"></p>
</form>
</body>
</html>
