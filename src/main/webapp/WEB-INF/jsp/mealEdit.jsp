<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <c:url value="/" var="urlHome"/>
    <h2><a href="${urlHome}">Home</a></h2>
    <h3>Edit meal</h3>
    <hr>
<%--    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.UserMeal" scope="request"/>
    <form method="post" action="meals/save">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description"></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories"></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Cancel</button>
    </form>--%>

    <c:url value="/meals/save" var="urlSave"/>
    <form:form method="post" commandName="meal" action="${urlSave}">

        <form:hidden path="id"/>
        <dl>
            <dt>DateTime:</dt>
            <dd><form:input path="dateTime"/></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><form:input path="description"/></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><form:input path="calories"/></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Cancel</button>
    </form:form>
</section>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
