<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <c:import url="header.jsp"/>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

                <h2 class="sub-header">Подсчет калорий</h2>

                <a class="btn btn-default" id="add" href="crud?action=create">Добавить еду</a>

                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th width="15%">Дата</th>
                                <th width="20%">Описание</th>
                                <th width="10%">Калории</th>
                                <th width="10%"></th>
                                <th width="10%"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${itemList}">
                                <c:choose>
                                    <c:when test="${item.exceed == true}">
                                        <tr class="exceed">
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="normal">
                                    </c:otherwise>
                                </c:choose>

                                    <td>${item.dateTimeToString()}</td>
                                    <td>${item.description}</td>
                                    <td>${item.calories}</td>
                                    <td><a class="btn btn-default" href="crud?action=update&id=${item.id}&datetime=${item.dateTimeToString()}&description=${item.description}&calories=${item.calories}">редактировать</a></td>
                                    <td><a class="btn btn-default" href="crud?action=delete&id=${item.id}">удалить</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
