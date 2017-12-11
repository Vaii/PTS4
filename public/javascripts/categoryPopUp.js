$(document).ready(function () {
    $('#popupbutton').on('click', function (e) {
        var category = $('#newcategory').val();

            $.post(
                "utility/addcategory", {
                    "category": category
                },
                function(data){
                    addNewContent(data);
                    },
                "json"
            );
        }
    );
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