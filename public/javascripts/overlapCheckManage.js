$(document).ready(function () {

    $('#dynamicInput').on('change', '#dates', function (e) {
        checkOverlap();
    });

    $('#dynamicInput').on('change', '#teacherId', function (e) {
        checkOverlap();
    });

    $('#duration').on('change', function (e) {
        checkOverlap();
    })
});


var checkInProgress = false;

function checkOverlap() {
    var teacher = getTeacherInputValues();
    var date = getDateInputValues();
    var duration = $('#duration').val();
    var dateIds = getDateIdInputValues();

    console.log(teacher);
    console.log(date);
    console.log(duration);
    console.log(dateIds);

    if (teacher !== "" && date !== "" && duration !== "" && checkInProgress !== true) {
        $('#loadingDiv').show();
        checkInProgress = true;

        $.post(
            "/utility/overlapcheck",
            {
                "teacher": teacher,
                "date": date,
                "duration": duration,
                'dateIds': dateIds
            },
            function (data) {
                checkInProgress = false;
                showOverlapResult(data);
            },
            "text"
        );
    }
}

function getDateInputValues() {
    var valueArray = $('.dateInput').map(function () {
        return this.value;
    }).get();

    return valueArray;
}

function getTeacherInputValues() {
    var valueArray = $('.teacherInput').map(function () {
        return this.value;
    }).get();

    return valueArray;
}

function getDateIdInputValues() {
    var valueArray = $('.dateIds').map(function () {
        return this.value;
    }).get();

    return valueArray;
}

function showOverlapResult(result) {
    console.log(result);
    $('#loadingDiv').hide();
    if (result.indexOf("overlap_detected") !== -1) {
        showError(true, result)
    } else {
        showError(false, result);
    }
}

function showError(disable, result) {
    $('.btn-default').prop("disabled", disable);
    $('.btn-primary').prop("disabled", disable);

    if (disable) {
        $('.row').removeClass('has-danger');

        $.each(JSON.parse(result), function( index, value ) {
            var rowNumber = parseInt(value.substring(17, 18));
            rowNumber++; // Increment by one to take account for row offset. See addInput.js.
            $('.row' + rowNumber).addClass('has-danger');
        });
        $('#validation_error_1').css("display", "block");

    } else {
        $('.row').removeClass('has-danger');

        $('#validation_error_1').css("display", "none");
    }
}