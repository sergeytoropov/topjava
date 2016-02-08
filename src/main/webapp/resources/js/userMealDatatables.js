var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: function (data) {
            updateTableByData(data);
        }
    });
    return false;
}

function init() {
    jQuery('#dateTime').datetimepicker({
        format:'Y-m-d\\TH:i'
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
}

$(function () {
    datatableApi = $('#datatable').DataTable({
        "sAjaxSource": ajaxUrl,
        "sAjaxDataProp": "",
        "bPaginate": false,
        "bInfo": false,
        "aoColumns": [
            {
                "mData": "dateTime",
                "mRender": function (date, type, row) {
                    if (type == 'display') {
                        var dateObject = new Date(date);
                        return '<span>' + dateObject.toISOString().substring(0, 16).replace('T', ' ') + '</span>';
                    }
                    return date;
                }
            },
            {
                "mData": "description"
            },
            {
                "mData": "calories"
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderEditBtn
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderDeleteBtn
            }
        ],
        "aaSorting": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).toggleClass(data.exceed ? "exceeded" : "normal");
        },
        "initComplete": makeEditable
    });

    $('#filter').submit(function () {
        updateTable();
        return false;
    });

    init();
});