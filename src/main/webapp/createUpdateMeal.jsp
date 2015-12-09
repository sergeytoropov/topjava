<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <c:import url="header.jsp"/>
    <link href="css/bootstrap-datetimepicker.css" rel="stylesheet">
    <script src="js/bootstrap-datetimepicker.js"></script>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-md-8 col-sm-8 col-sx-8">
                <h2 class="sub-header">
                    <c:if test="${isAddMeal}">Добавить еду</c:if>
                    <c:if test="${!isAddMeal}">Редактировать еду</c:if>
                </h2>
                <form method="post" action="execute">
                    <div class="form-group">
                        <label for="inputDatetime">Дата</label>
                        <div class='input-group date' id='datetimepicker'>
                            <input type="text" class="form-control" name="inputDatetime" id="inputDatetime" placeholder="Datetime" value="${datetime}">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputDescription">Описание</label>
                        <input type="input" class="form-control" name="inputDescription" id="inputDescription" placeholder="Description" value="${description}">
                    </div>
                    <div class="form-group">
                        <label for="inputCalories">Калории</label>
                        <input type="number" class="form-control" name="inputCalories" id="inputCalories" placeholder="Calories" value="${calories}">
                    </div>
                    <input type="hidden" name="inputMealId" id="inputMealId" value="${mealId}">
                    <button type="submit" class="btn btn-default">
                        <c:if test="${isAddMeal}">Добавить</c:if>
                        <c:if test="${!isAddMeal}">Изменить</c:if>
                    </button>
                </form>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        $(function () {
            $('#datetimepicker').datetimepicker({
                locale: 'ru'
            });
        });
    </script>
</body>
</html>
