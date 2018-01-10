$(document).ready(function () {
    $('.categoryLink').on('click', function (e) {
        if ($('#trainingMenu').css('display') === 'block') {
            $("#trainingContent").css('visibility', 'hidden');
            //fadeOutTrainingContent();
            toggleTrainingMenu(false);
        }
        setNoSelectTrainingContent();
        setActiveLink($(this));
        getTrainings($(this).attr('id'));
        toggleTrainingMenu(true);
    });
});

function getTrainings(category) {
    $.post('/utility/gettrainings/' + category, function (data) {
        var trainings;
        trainings = data;
        addTrainingsToDiv(trainings);
    });
}

function getDate(training) {
    $("#loadingImage").show();
    $.get('/utility/getdates/' + training.id, function (data) {
        var date;
        date = data;
        setTrainingDates(date, training);
    });
}

function toggleTrainingMenu(expanding) {
    $("#trainingMenu").animate({
        width: 'toggle'
    }, {
        complete: function () {
            if (expanding) {
                if ($('#trainingMenu').css('display') === 'block') {
                    $("#trainingContent").css('visibility', 'visible');
                    fadeInTrainingContent();
                }
            }
        }
    });
}

function addTrainingsToDiv(trainings) {
    var firstTim = false;
    if($('#trainingContent').children().length < 1) {
        firstTim = true;
    }
    $('#trainingContent').empty();
    $.each(trainings, function (index, element) {
        $('#trainingContent').append(
            $('<li>').css("opacity", "0").append(
                $('<a>').attr('href', '#').attr('id', element).addClass("trainingLink").addClass(element.name).append(
                    $('<span>').attr('class', 'tab').append(element.name)
                ).bind('click', function () {
                    setTrainingContent(element);
                })
            )
        );
    });
    if(firstTim) {
        fadeInTrainingContent();
    }
}

function setTrainingContent(training) {
    setActiveTraining(training);
    $("#noTrainingContent").css('display', 'none');
    deleteContentDates();
    getDate(training);
    console.log(training);
    $('#trainingContentName').text(training.name);
    $('#trainingContentCode').text(training.trainingCode);
    $('#trainingContentSummary').text(training.description);
    if (training.requiredMaterial === "") {
        $('#trainingContentMaterial').text("Geen benodigde materialen");
    } else {
        $('#trainingContentMaterial').text(training.requiredMaterial);
    }
    $('#trainingContentCosts').text("€" + Number(training.tuition).toFixed(2) + " per persoon");
    $('#trainingContentDuration').text(training.duration + " dagen");
    $("#trainingContentDiv").css('display', 'block');
}

function setTrainingDates(date, training) {
    var container = $('#trainingContentDates');
    $("#loadingImage").hide();
    deleteContentDates();
    if (date.length === 0) {
        container.append("<p> Geen datums beschikbaar <p>");
    } else {
        $.each(date, function (index, element) {
            console.log(element);
            var newContainer = $("<div>", {id: 'date-' + element.dateId}).addClass("row col-lg-12").appendTo(container);
            $('<p>', {
                html: element.dateOnlyString + ',<br>' + 'Starttijd: ' + element.tmeOnlyString
            }).addClass("col-lg-3").appendTo(newContainer);

            $('<p>', {
                html: element.location.city + ',<br>' + element.location.streetName + ' ' + element.location.streetNumber
            }).addClass("col-lg-3").appendTo(newContainer);

            var buttonContainer = $("<div>", {id: 'date-button-' + element.dateId}).addClass("col-lg-6").appendTo(newContainer);
            if (element.currentUserSignedUp) {
                $('<p>', {
                    html: 'Je bent al ingeschreven op deze datum.' + '<br>' + (training.capacity - element.signUps) + "/" + +training.capacity + " plaatsen beschikbaar"
                }).appendTo(buttonContainer);
            } else {
                if ((training.capacity - element.signUps) === 0) {
                    $('<p>', {
                        html: 'Inschrijven niet meer mogelijk.' + '<br>' + (training.capacity - element.signUps) + "/" + +training.capacity + " plaatsen beschikbaar"
                    }).appendTo(buttonContainer);
                } else {
                    $('<a>', {
                        text: 'Inschrijven',
                        title: 'Inschrijven',
                        href: "/training/signup/" + element.dateId,
                    }).addClass("btn btn-success btn-sm ").appendTo(buttonContainer);
                    $('<p>', {
                        html: (training.capacity - element.signUps) + "/" + +training.capacity + " plaatsen beschikbaar"
                    }).appendTo(buttonContainer);
                }
            }
            $('<hr>', {}).appendTo(container);
        });
    }
}

function deleteContentDates() {
    $("#trainingContentDates").empty();
}

function setActiveLink(newLink) {
    $('.categoryLink').each(function () {
        $(this).removeClass("active");
    });

    newLink.addClass("active");
}

function setActiveTraining(training) {
    $('.trainingLink').each(function () {
        $(this).removeClass("active");
        if($(this).hasClass(training.name)) {
            $(this).addClass("active");
        }
    });
}

function fadeInTrainingContent() {
    console.log("Fading in");
    $('#trainingContent').find('> li').each(function (index) {
        $(this).delay(100*index).animate({opacity: '1.0'});
    });
}

function fadeOutTrainingContent() {
    $('#trainingContent').find('> li').each(function () {
        $(this).delay(400).fadeOut(300);
    });
}

function setPredeterminedTraining(category, training) {
    if ($('#trainingMenu').css('display') === 'block') {
        $("#trainingContent").css('visibility', 'hidden');
        fadeOutTrainingContent();
        toggleTrainingMenu(false);
    }

    getTrainings(category);
    toggleTrainingMenu(true);

    setTrainingContent(training);
}

function unescapeHTML(string) {
    var elt = document.createElement("span");
    elt.innerHTML = string;
    return elt.innerText;
}

function setNoSelectTrainingContent() {
    $("#trainingContentDiv").css('display', 'none');
    $("#selectHeader").html("Selecteer een training");
    $("#noTrainingContent").css('display', 'block');
}