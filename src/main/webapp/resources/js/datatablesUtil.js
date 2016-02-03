function makeEditable() {

    $('#add').click(function () {
        $('#id').val(0);
        $('#editRow').modal();
    });

    $('.delete').click(function () {
        deleteRow($(this).parent().parent().attr("id"));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $('#filtredForm').submit(function () {
        filtred();
        $('#isFiltred').attr('value', '1');
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function updateTable() {
    if ($('#isFiltred').attr('value') == 1) {
        filtred();
    } else {
        $.get(ajaxUrl, function (data) {
            oTable_datatable.clear();
            $.each(data, function (key, item) {
                oTable_datatable.row.add(item);
            });
            oTable_datatable.draw();
        });
    }
}

function save() {
    var form = $('#detailsForm');
    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

function filtred() {
    var form = $('#filtredForm');
    alert(form.serialize());
    $.ajax({
        type: "POST",
        url: ajaxUrl + "/filter",
        data: form.serialize(),
        success: function (data) {
            oTable_datatable.clear();
            $.each(data, function (key, item) {
                oTable_datatable.row.add(item);
            });
            oTable_datatable.draw();

            successNoty('Filtred');
        }
    })
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}

