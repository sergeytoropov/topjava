<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Подсчет калорий</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
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
