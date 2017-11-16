function showDiv() {
    var list = document.getElementById("listDiv");
    var cal = document.getElementById("calenderDiv");
    if (list.style.display === "none") {
        cal.style.display = "none";
        list.style.display = "inherit";
    } else {
        cal.style.display = "inherit";
        list.style.display = "none";
    }
}