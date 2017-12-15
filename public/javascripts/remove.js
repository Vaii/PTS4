$(document).ready(function () {
    $('.btn-outline-danger').on('click', function (e) {
        e.preventDefault(); // Prevent the href from redirecting directly
        var linkURL = $(this).attr("href");
        warnBeforeRedirect(linkURL);
    });

    function warnBeforeRedirect(linkURL) {
        swal({
            title: "Verwijderen?",
            text: "Een verwijderde account kan niet teruggehaald worden!",
            icon: "warning",
            buttons: ["Annuleren", "Verwijderen"],
            dangerMode: true,
        }).then((willDelete) => {
            if (willDelete) {
                window.location.href = linkURL;
            }
        });
    }

});