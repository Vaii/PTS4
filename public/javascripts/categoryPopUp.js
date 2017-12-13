$(document).ready(function () {
    $('#popupbutton').on('click', function (e) {
        var category = $('#newcategory').val();
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
        }
    );

    $('#newcategorybtn').on('click', function (e) {
        $('.popup').toggle(200);
        if($('#newcategorybtn').val() === 'Categorie Beheer'){
            $("#newcategorybtn").prop('value', 'Annuleren');
            $("#newcategorybtn").prop('class', 'btn btn-danger')
        }
        else{
            $("#newcategorybtn").prop('value', 'Categorie Beheer');
            $("#newcategorybtn").prop('class', 'btn btn-primary')
        }
    })

    $('#deletecategorybtn').on('click', function(e){
        var category = $('#newcategory').val();
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
    });

});

function addNewContent(data) {
    var parentContainer = $('#categorycontainer');
    parentContainer.empty();
    $.each(data, function (index, element) {

        $('#categorycontainer')
            .append($("<option></option>")
                .attr("value", element._id)
                .text(element.category)
            )
    })
}

function addErrorMessage() {
    $('#newcategory').addClass('has-danger');
    $('#errormessage').text("Categorie bestaat al en is niet toegevoegd aan de lijst");
    $('.errordisplay').css("display", "block");
    $('.errordisplay').css("display", "block");
    $('.errordisplay').css("color", "red");
}

function addSuccesMessage(){
    $('#errormessage').text("Categorie is succesvol toegevoegd aan de lijst");
    $('.errordisplay').css("display", "block");
    $('.errordisplay').css("color", "green");
}

function deleteErrorMessage(){
    $('#newcategory').addClass('has-danger');
    $('#errormessage').text("Deze categorie is in gebruik en kan niet verwijdert worden");
    $('.errordisplay').css("display", "block");
    $('.errordisplay').css("color", "red");
}
function deleteSuccesMessage(){
    $('#errormessage').text("Categorie is succesvol verwijderd");
    $('.errordisplay').css("display", "block");
    $('.errordisplay').css("color", "green");
}