$(document).ready(function () {
    $('#newcategorybtn').on('click', function (e) {
        var category = $('#newcategory').val();
        resetBorders();
        if(category !==""){
            $.post(
                "utility/addcategory", {
                    "category": category
                },
                function (data) {
                    if (data === "Categorie Bestaat Al") {
                        addErrorMessage(data);
                    }
                    else {
                        addSuccesMessage();
                        addNewContent(data);
                    }
                },
                "json"
            );
        }
        else{
            emptyTextBox();
        }
        });

    $('#popupbutton').on('click', function (e) {
        $('.popup').toggle(200);
        if($('#popupbutton').val() === 'Categorie Beheer'){
            $("#popupbutton").prop('value', 'Annuleren');
            $("#popupbutton").prop('class', 'btn btn-danger')
        }
        else{
            $("#popupbutton").prop('value', 'Categorie Beheer');
            $("#popupbutton").prop('class', 'btn btn-primary')
        }
    })

    $('#deletecategorybtn').on('click', function(e){
        var category = $('#newcategory').val();
        $('.popup').removeClass('has-danger');
        $('.popup').removeClass('has-success');
        if(category !== ""){
            $.post(
                "utility/deletecategory", {
                    "category": category
                },
                function(data){
                    if(data === "Categorie in gebruik"){
                        deleteErrorMessage();
                    }
                    else{
                        addNewContent(data);
                        deleteSuccesMessage();
                    }
                }
            )
        }
        else{
            emptyTextBox();
        }
    });

    $('[data-toggle="tooltip"]').tooltip();
});

function addNewContent(data) {
    var parentContainer = $('#categorycontainer');
    parentContainer.empty();
    $.each(data, function (index, element) {

        $('#categorycontainer')
            .append($("<option></option>")
                .attr("value", element._id)
                .text(element.name)
            )
    })
}

function addErrorMessage() {
    resetBorders();
    $('.outline').addClass('has-danger');
    $('#errormessage').text("Categorie bestaat al en is niet toegevoegd aan de lijst");
    $('.errordisplay').css("display", "block");
    $('.errordisplay').css("color", "#d9534f");
}

function addSuccesMessage(){
    resetBorders();
    $('.outline').addClass('has-success');
    $('#errormessage').text("Categorie is succesvol toegevoegd aan de lijst");
    $('.errordisplay').css("display", "block");
    $('.errordisplay').css("color", "#5cb85c");
}

function deleteErrorMessage(){
    resetBorders();
    $('.outline').addClass('has-danger');
    $('#errormessage').text("Deze categorie is in gebruik en kan niet verwijdert worden");
    $('.errordisplay').css("display", "block");
    $('.errordisplay').css("color", "#d9534f");
}
function deleteSuccesMessage(){
    resetBorders();
    $('.outline').addClass('has-success');
    $('#errormessage').text("Categorie is succesvol verwijderd");
    $('.errordisplay').css("display", "block");
    $('.errordisplay').css("color", "#5cb85c");
}
function emptyTextBox(){
    resetBorders();
    $('.outline').addClass('has-danger');
    $('#errormessage').text("Geen categorie ingevuld");
    $('.errordisplay').css("display", "block");
    $('.errordisplay').css("color", "#d9534f");
}

function resetBorders(){
    $('.errordisplay').css("display", "none");
    $('.outline').removeClass('has-danger');
    $('.outline').removeClass('has-success');
}