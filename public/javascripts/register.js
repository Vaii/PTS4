$( document ).ready(function () {
    $('#email').on('input', function() {
        if($('#email').val() !== "") {
            $.post('/utility/emailCheck/' + $('#email').val(), function(data) {
                if(data=== "user_exists"){
                    disableControls(true);
                }else{
                    disableControls(false);
                }
            });
        } else {
            disableControls(false);
        }
    });

    $('#validation').on('input', function() {
        var pass = $('#password').val();
        var val = $('#validation').val();

        if(pass !== "" && val !== "") {
            checkPasswords(pass, val);
        }
    });

    function disableControls(disable) {
        $('.btn-default').prop("disabled",disable);
        if(disable) {
            $('#email_field').addClass('has-danger');
            $('#validation_error_0').css("display", "block");
        } else {
            $('#email_field').removeClass('has-danger');
            $('#validation_error_0').css("display", "none");
        }
    }

    function checkPasswords(pass, val) {
            if(pass !== val) {
                $('.btn-default').prop("disabled",true);
                $('#validation_field').addClass('has-danger');
                $('#validation_error_1').css("display", "block");
            } else {
                $('.btn-default').prop("disabled",false);
                $('#validation_field').removeClass('has-danger');
                $('#validation_error_1').css("display", "none");
            }
    }
});