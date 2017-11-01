console.log(locJson);
console.log(teachJson);

var lOptions = "";
var tOptions = "";

// Iterate trough list of Locations
for (var i = 0; i < locJson.length; i++) {
    var loc = locJson[i];

    var opt = "<option value=\"" + loc._id + "\">" + loc.StreetName + "</option>\n";

    lOptions += opt;
}

// Iterate trough list of Teachers
for (var i = 0; i < teachJson.length; i++) {
    var t = teachJson[i];

    var opt = "<option value=\"" + t._id + "\">" + t.FirstName + "</option>\n";

    tOptions += opt;
}

function deleteInput(divName) {

}

var counter = 1;
// var limit = 3;
function addInput(divName){
   /* if (counter == limit)  {
        alert("You have reached the limit of adding " + counter + " inputs");
    } */
        var numItems = $('.row').length;
        var newdiv = document.createElement('div');
        newdiv.className = "row row" + numItems;
        newdiv.innerHTML = "<div class=\"col-lg-3\"><label class=\"control-label\" for=\"Dates[]\">Datum:</label>" +
            "<input type=\"date\" id=\"Dates\" name=\"Dates[]\" value=\"\" required=\"true\" class=\"form-control\"></div>" +

             // Location box
            "<div class=\"col-lg-4\"><label class=\"form-control-label\" for=\"LocationID[]\">Locatie:</label>" +
            "<select id=\"LocationID\" name=\"LocationID[]\" required=\"true\" class=\"form-control form-control\">" +
            "<option value=\"\" class=\"blank\" selected=\"true\" disabled=\"disabled\">Selecteer een locatie</option>" +
            lOptions +
            "</select></div>" +

            // Teacher box
            "<div class=\"col-lg-4\">\n" +
            "<label class=\"form-control-label\" for=\"TeacherID[]\">Docent:</label>\n" +
            "<select id=\"TeacherID\" name=\"TeacherID[]\"  required=\"true\" class=\"form-control form-control\">\n" +
            "<option value=\"\" class=\"blank\" selected=\"true\" disabled=\"disabled\">Selecteer een docent</option>\n" +
            tOptions +
            "</select>\n" +
            "</div>";



        // <label class=\"control-label\" for=\"Dates[]\">Datum " + (counter + 1) + "" + "</label> <br><input type=\"date\"" +
  //  " id=\"Date\" name=\"Dates[]\" value=\"\" " + "class=\"form-control form-control-inline\">" + "
        document.getElementById(divName).appendChild(newdiv);
        counter++;
}
