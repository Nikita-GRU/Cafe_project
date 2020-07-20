
$(document).ready(function () {
    $(function() {

        $('form').submit(function (e) {
            if ($('input').val() == 0) {
                e.preventDefault();
            }
        });

    });
    $('#dtBasicExample').DataTable({
        "pagingType": "numbers",
        "language": {
            "info": "_START_ - _END_ / _TOTAL_",
            "lengthMenu": ' <select>' +
                '<option value="10">10</option>' +
                '<option value="20">20</option>' +
                '<option value="30">30</option>' +
                '<option value="40">40</option>' +
                '<option value="50">50</option>' +
                '</select> '
        }
    });


});