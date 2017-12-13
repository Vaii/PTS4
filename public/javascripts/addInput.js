console.log(locJson);
console.log(teachJson);

var lOptions = "";
var tOptions = "";

// Iterate trough list of Locations
for (var i = 0; i < locJson.length; i++) {
    var loc = locJson[i];

    var opt = "<option value=\"" + loc.id + "\">" + loc.streetName + "</option>\n";

    lOptions += opt;
}

// Iterate trough list of Teachers
for (var i = 0; i < teachJson.length; i++) {
    var t = teachJson[i];

    var opt = "<option value=\"" + t.id + "\">" + t.firstName + "</option>\n";

    tOptions += opt;
}

function deleteInput(className) {
    var name = className.className;
    console.log(className);
    var number = name.substring(18);
    console.log(number);
    var row = document.getElementsByClassName("row" + number);
    console.log(row);
    row[0].parentNode.removeChild(row[0]);
}

function addInput(divName){
    console.log($('#dynamicInput .row').length);
    var numItems = $('#dynamicInput .row').length;
    var newdiv = document.createElement('div');
    newdiv.className = "row col-lg-12 row" + numItems;
    newdiv.innerHTML = "<div class=\"col-lg-3\"><label class=\"form-control-label\" for=\"dates[]\">Datum:</label>" +
        "<input type=\"datetime-local\" id=\"dates\" name=\"date[]\" value=\"\" required=\"true\" class=\"form-control dateInput\"></div>" +

        // Location box
        "<div class=\"col-lg-4\"><label class=\"form-control-label\" for=\"locationId[]\">Locatie:</label>" +
        "<select id=\"locationId\" name=\"locationId[]\" required=\"true\" class=\"form-control form-control\">" +
        "<option value=\"\" class=\"blank\" selected=\"true\" disabled=\"disabled\">Selecteer een locatie</option>" +
        lOptions +
        "</select></div>" +

        // Teacher box
        "<div class=\"col-lg-4\">" +
        "<label class=\"form-control-label\" for=\"teacherId[]\">Docent:</label>\n" +
        "<select id=\"teacherId\" name=\"teacherId[]\"  required=\"true\" class=\"form-control form-control teacherInput\">\n" +
        "<option value=\"\" class=\"blank\" selected=\"true\" disabled=\"disabled\">Selecteer een docent</option>\n" +
        tOptions +
        "</select>\n" +
        "</div>" +
        "<div class=\"col-lg-1\">\n" +
        "<input class=\"btn btn-danger btn" + numItems + "\" type=\"button\" value=\"X\" onClick=\"deleteInput(this);\">\n" +
        "</div>";

    document.getElementById(divName).appendChild(newdiv);
}

function addInputEdit(divName){
    var numItems = $('#dynamicInput .row').length + 1;
    var newdiv = document.createElement('div');
    newdiv.className = "row col-lg-12 row" + numItems;
    newdiv.innerHTML = "<div class=\"col-lg-5\"><label class=\"form-control-label\" for=\"dates[]\">Datum:</label>" +
        "<input type=\"datetime-local\" id=\"dates\" name=\"date[]\" value=\"\" required=\"true\" class=\"form-control dateInput\"></div>" +

        // Location box
        "<div class=\"col-lg-3\"><label class=\"form-control-label\" for=\"locationId[]\">Locatie:</label>" +
        "<select id=\"locationId\" name=\"locationId[]\" required=\"true\" class=\"form-control form-control\">" +
        "<option value=\"\" class=\"blank\" selected=\"true\" disabled=\"disabled\">Selecteer een locatie</option>" +
        lOptions +
        "</select></div>" +

        // Teacher box
        "<div class=\"col-lg-3\">\n" +
        "<label class=\"form-control-label\" for=\"teacherId[]\">Docent:</label>\n" +
        "<select id=\"teacherId\" name=\"teacherId[]\"  required=\"true\" class=\"form-control form-control teacherInput\">\n" +
        "<option value=\"\" class=\"blank\" selected=\"true\" disabled=\"disabled\">Selecteer een docent</option>\n" +
        tOptions +
        "</select>\n" +
        "</div>" +
        "<div class=\"col-lg-1\">\n" +
        "<input class=\"btn btn-danger btn" + numItems + "\" type=\"button\" value=\"X\" onClick=\"deleteInput(this);\">\n" +
        "</div>" +
        "</div>" +
        "</div>";

    document.getElementById(divName).appendChild(newdiv);
}