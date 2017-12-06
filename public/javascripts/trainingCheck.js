$( document ).ready(function () {
    $('#trainingCode').on('input', function() {
        var code = $('#trainingCode').val();
        if (code !== "") {
            $.post('/utility/trainingcodecheck/' + code, function (data) {
                if (data === "code_exists") {
                    disableControls(true);
                } else {
                    disableControls(false);
                }
            });
        } else {
            disableControls(false);
        }
    });

    function disableControls(disable) {
        $('.btn-primary').prop("disabled",disable);
        if(disable) {
            $('#trainingCode_field').addClass('has-danger');
            $('#validation_error_0').css("display", "block");
        } else {
            $('#trainingCode_field').removeClass('has-danger');
            $('#validation_error_0').css("display", "none");
        }
    }
});