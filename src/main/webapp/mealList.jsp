<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {color: green;}
        .exceeded {color: red;}
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <hr>
    <h3>${loggedUserName}</h3>
    <hr>
    <form method="post" action="meals?userId=${userId}" name="filterForm">
        <input type="hidden" name="formName" value="filterForm">
        <input type="hidden" name="userId" value="${userId}">
        <table border="0" cellpadding="10" cellspacing="0">
            <tbody>
                <tr>
                    <td>с даты</td>
                    <td><input type="date" value="${beginDate}" name="beginDate"></td>
                    <td>по дату</td>
                    <td><input type="date" value="${endDate}" name="endDate"></td>
                </tr>
                <tr>
                    <td>с времени</td>
                    <td><input type="time" value="${beginTime}" name="beginTime"></td>
                    <td>по время</td>
                    <td><input type="time" value="${endTime}" name="endTime"></td>
                </tr>
            </tbody>
        </table>
        <button type="submit">Фильтровать</button>
    </form>
    <hr>
    <h3>Meal list</h3>
    <a href="meals?action=create&userId=${userId}">Add Meal</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.UserMealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                   <%--<fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDate"/>--%>
                   <%--<fmt:formatDate value="${parsedDate}" pattern="yyyy.MM.dd HH:mm" />--%>
                    <%=TimeUtil.toString(meal.getDateTime())%>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}&userId=${userId}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}&userId=${userId}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>