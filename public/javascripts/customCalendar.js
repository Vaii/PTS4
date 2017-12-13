function unescapeHTML(string) {
    var elt = document.createElement("span");
    elt.innerHTML = string;
    return elt.innerText;
}

var dateJson;

// Create object array from the JsonNode.
function passDates(newDates) {
    dateJson = newDates;
    dateJson = JSON.parse(unescapeHTML(dateJson));
}

//console.log(JSON.stringify(dateJson, null , 4));

var events = [];

// Loop through the elements in the object array and create events.
// For events see : https://fullcalendar.io/docs/event_data/Event_Object/
function getElements(start, end, timezone, callback) {
    events = [];
    $.each(dateJson, function (index, element) {
        console.log(element);
        // Create date according to ISO 8601 standard.
        // See: https://en.wikipedia.org/wiki/ISO_8601
        var startDate = new Date(element.date.date);
        var endDate = new Date(element.date.date);

        // Add the duration to find the end date.
        endDate.setDate(startDate.getDate() + parseInt(element.training.duration));

        var location = element.date.location.city + ", " + element.date.location.streetName + ", " + element.date.location.streetNumber +
            ", " + element.date.location.room;


        // Set all event properties and push it to the calendar.
        events.push({
            title: element.training.name,
            start: startDate,
            end: endDate,
            description : element.training.description,
            location: location
        });
    });
    // Callback to the calendar.
    callback(events);
}

function deleteEvents() {
    $("#calendar").fullCalendar('removeEvents');
}

$(document).ready(function () {
    // page is now ready, initialize the calendar...
    $('#calendar').fullCalendar({
        // Event that fires on an event click.
        // For more information on the event see:
        eventRender: function (event, element) {
            element.qtip({
                content: event.title + "<br><br>" + " From: " + event.start.format('MMM Do h:mm A') +"<br>" + " untill: " + event.end.format('MMM Do h:mm A') + "<br><br>" + event.description +
                "<br><br>" + event.location,
                style: {classes: 'qtip-light'},
                position: {
                    my: 'top right',  // Position my top left...
                    at: 'bottom right', // at the bottom right of...
                }
            })
        },
        // Set elements in the calendar.
        events: function (start, end, timezone, callback) {
            getElements(start, end, timezone, callback);
        }
    })
});