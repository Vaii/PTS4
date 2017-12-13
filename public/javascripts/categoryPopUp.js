$(document).ready(function () {
    $('#popupbutton').on('click', function (e) {
        var category = $('#newcategory').val();

            $.post(
                "utility/addcategory", {
                    "category": category
                },
                function (data) {
                    if (data === "Categorie Bestaat Al") {
                        showErrorMessage(data)
                    }
                    else {
                        addNewContent(data);
                    }
                },
                "json"
            );
        }
    );

    $('#newcategorybtn').on('click', function(e){
        $('.popup').toggle(200);
    })
});

function addNewContent(data){
    var parentContainer =$('#categorycontainer');
    parentContainer.empty();
    $.each(data, function (index, element){

        $('#categorycontainer')
            .append($("<option></option>")
                .attr("value", element._id)
                .text(element.category)
            )
    })
}

function showErrorMessage(data){
    $('#newcategory').addClass('has-danger');
    $('.errordisplay').css("display", "block")
}