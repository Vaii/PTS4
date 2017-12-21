$(document).ready(function () {
    $('#phoneNumber').blur(function() {
        checkPhoneNumber($('#phoneNumber').val())
    });

    $('[data-toggle="tooltip"]').tooltip({html: true});
});

function checkPhoneNumber(phoneNumber) {
    if(phoneNumber !== "") {
        console.log("Checking number: " + phoneNumber);
        $.post('http://apilayer.net/api/validate?access_key=125187601f64a067b0735ac4d73dc447&number='+phoneNumber+'&country_code=&format=1', function (data) {
            processResult(data);
        });
    }
}

function processResult(data) {
    console.log(data);
    if(data.valid) {
        console.log("Valid phonenumber");
        setPhoneError(false);
    } else {
        console.log("Invalid phonenumber");
        setPhoneError(true);
    }
}

function setPhoneError(setError) {
    $('.btn-default').prop("disabled",setError);

    if(setError){
        $('#phoneNumber_field').addClass('has-danger');
        $('#validation_error_2').css("display", "block");

    } else {
        $('#phoneNumber_field').removeClass('has-danger');
        $('#validation_error_2').css("display", "none");
    }
}