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
        if($('#newcategorybtn').val() === 'Nieuwe Categorie'){
            $("#newcategorybtn").prop('value', 'Annuleren');
            $("#newcategorybtn").prop('class', 'btn btn-outline-danger')
        }
        else{
            $("#newcategorybtn").prop('value', 'Nieuwe Categorie');
            $("#newcategorybtn").prop('class', 'btn btn-outline-primary')
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

function showErrorMessage(data) {
    $('#newcategory').addClass('has-danger');
    $('.errordisplay').css("display", "block");
}

function addSuccesMessage(){
    $('#errormessage').text("Categorie is succesvol toegevoegd aan de lijst");
    $('.errordisplay').css("display", "block");
}

function deleteErrorMessage(){
    $('#errormessage').text("Categorie is niet verwijderd");
    $('.errordisplay').css("display", "block");
}
function deleteSuccesMessage(){
    $('#errormessage').text("Categorie is succesvol verwijderd");
    $('.errordisplay').css("display", "block");
}