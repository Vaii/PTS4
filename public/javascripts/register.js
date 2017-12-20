$( document ).ready(function () {
    $('#email').on('input', function() {
        if($('#email').val() != "") {
            $.post('/utility/emailCheck/' + $('#email').val(), function(data) {
                if(data== "user_exists"){
                    console.log("User already exists");
                    disableControls(true);
                }else{
                    console.log("User doesn't exists");
                    disableControls(false);
                }
            });
        } else {
            disableControls(false);
        }
    });

    $('#password').on('input', function() {
        comparePasswords();
    });

    $('#validation').on('input', function() {
        comparePasswords();
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
});

function showPassWordError(disable) {
    $('.btn-default').prop("disabled",disable);
    if(disable) {
        $('#validation_field').addClass('has-danger');
        $('#validation_error_1').css("display", "block");
    } else {
        $('#validation_field').removeClass('has-danger');
        $('#validation_error_1').css("display", "none");
    }
}

function comparePasswords() {
    var passInput = $("#password").val();
    var passVal = $("#validation").val();

    if(passInput !== "" && passVal !== "") {
        if(passInput === passVal) {
            showPassWordError(false);
        } else {
            showPassWordError(true);
        }
    }
}