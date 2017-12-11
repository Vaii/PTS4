$(document).ready(function () {
    $('#popupbutton').on('click', function (e) {
        var category = $('#newcategory').val();

            $.post(
                "utility/addcategory", {
                    "category": category
                },
                function(data){},
                "text"
            );
        }
    );
});