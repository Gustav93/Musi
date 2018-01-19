$(document).ready(function() {
    table = $("#datatable").DataTable({
        ajax:{url:'/users_list_json',dataSrc:""},
        columns: [
            { "data": "dni" },
            { "data": "nombre" },
            { "data": "fechaDenacimiento" }
        ]
    });
} );

$("#boton").click(function() {
        table.ajax.reload();
});