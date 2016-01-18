<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="messages.app"/>

<header>
    <c:url value="/" var="urlHome"/>
    <a href="${urlHome}"><fmt:message key="app.title"/></a>
</header>
