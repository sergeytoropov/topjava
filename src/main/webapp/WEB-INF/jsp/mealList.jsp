<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<fmt:setBundle basename="messages.app"/>--%>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<link rel="stylesheet" href="webjars/datatables/1.10.9/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="webjars/datetimepicker/2.3.4/jquery.datetimepicker.css">

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><fmt:message key="meals.title"/></h3>

            <form class="form-horizontal" method="post" action="ajax/meals/filter" id="filtredForm">
                <input type="text" hidden="hidden" id="isFiltred" name="isFiltred" value="0">
                <div class="form-group">
                    <label for="startDate" class="col-sm-7 control-label">Date: from</label>
                    <div class="col-sm-2">
                        <input type="text" name="startDate" class="form-control" id="startDate" value="${startDate}" placeholder="">
                    </div>
                    <label for="endDate" class="col-sm-1 control-label">to</label>
                    <div class="col-sm-2">
                        <input type="text" name="endDate" class="form-control" id="endDate" value="${endDate}" placeholder="">
                    </div>
                </div>
                <div class="form-group">
                    <label for="startTime" class="col-sm-7 control-label">Time: from</label>
                    <div class="col-sm-offset-1 col-sm-1">
                        <input type="text" name="startTime" class="form-control" id="startTime" value="${startTime}" placeholder="">
                    </div>
                    <label for="endTime" class="col-sm-1 control-label">to</label>
                    <div class="col-sm-1">
                        <input type="text" name="endTime" class="form-control" id="endTime" value="${endTime}" placeholder="">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-10 col-sm-2 text-left">
                        <button type="submit" class="btn btn-primary" id="btnFiltred">Filter</button>
                        <a href="meals" class="btn btn-primary">Clear</a>
                    </div>
                </div>
            </form>

            <div class="view-box">
                <a class="btn btn-sm btn-info" id="add"><fmt:message key="meals.add"/></a>

                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Description</th>
                        <th>Calories</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${mealList}" var="meal">
                            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
                            <tr class="${meal.exceed ? 'exceeded' : 'normal'}" id="${meal.id}">
                                <td>
                                    <%--&lt;%&ndash;<fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDate"/>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<fmt:formatDate value="${parsedDate}" pattern="yyyy.MM.dd HH:mm" />&ndash;%&gt;--%>
                                    <%=TimeUtil.toString(meal.getDateTime())%>
                                </td>
                                <td>${meal.description}</td>
                                <td>${meal.calories}</td>
                                <td><a class="btn btn-xs btn-primary edit">Edit</a></td>
                                <td><a class="btn btn-xs btn-danger delete">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="meals.edit"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id" value="0">

                    <div class="form-group">
                        <label for="dateTime" class="control-label col-xs-3">DateTime</label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="dateTime" name="dateTime" placeholder="dateTime">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3">Description</label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description" placeholder="description">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3">Calories</label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="calories" name="calories" placeholder="calories">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>

<script type="text/javascript" src="webjars/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datetimepicker/2.3.4/jquery.datetimepicker.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.9/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.2.4/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript">

    var ajaxUrl = 'ajax/meals/';
    var oTable_datatable;
    var oTable_datatable_params;

    //            $(document).ready(function () {
    $(function () {
        //oTable_datatable = $('#datatable');
        oTable_datatable_params = {
            "bPaginate": false,
            "bInfo": false,
            "aoColumns": [
                {
                    "mData": "dateTime"
                },
                {
                    "mData": "description"
                },
                {
                    "mData": "calories"
                },
                {
                    "sDefaultContent": "",
                    "bSortable": false
                },
                {
                    "sDefaultContent": "",
                    "bSortable": false
                }
            ],
            "aaSorting": [
                [
                    0,
                    "asc"
                ]
            ]
        };

        oTable_datatable = $('#datatable').DataTable(oTable_datatable_params);
        makeEditable();
    });

    jQuery('#dateTime').datetimepicker({
        format:'Y-m-d\\TH:i',
    });

    jQuery('#startDate').datetimepicker({
        timepicker:false,
        format:'Y-m-d'
    });

    jQuery('#endDate').datetimepicker({
        timepicker:false,
        format:'Y-m-d'
    });

    jQuery('#startTime').datetimepicker({
        datepicker:false,
        format:'H:i'
    });

    jQuery('#endTime').datetimepicker({
        datepicker:false,
        format:'H:i'
    });
</script>
</html>
