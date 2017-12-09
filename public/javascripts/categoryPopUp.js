$(document).ready(function () {
    $('#popupbutton').on('click', function (e) {
        var category = $('#newcategory').val();
        if(category !=""){
            $.post(
                "utility/addcategory",{
                    "category":category
            },
                function(data){},
                "json"
            )
        }
    })
})