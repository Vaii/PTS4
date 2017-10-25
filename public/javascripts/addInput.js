alert(json);
console.log(json);

function initLocationBox() {

}

var counter = 1;
// var limit = 3;
function addInput(divName){
   /* if (counter == limit)  {
        alert("You have reached the limit of adding " + counter + " inputs");
    } */
        var newdiv = document.createElement('div');
        newdiv.innerHTML = "<label class=\"control-label\" for=\"Dates[]\">Datum " + (counter + 1) + "" + "</label> <br><input type=\"date\"" +
            " id=\"Date\" name=\"Dates[]\" value=\"\" " + "class=\"form-control form-control-inline\">" + "";
        document.getElementById(divName).appendChild(newdiv);
        counter++;
}
