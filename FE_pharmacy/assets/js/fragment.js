$(document).ready(function () {
    $("#header").load("./header.html", function (response, status, xhr) {
        console.log(status)
        if (status == "error") {
            var msg = "An error occurred: ";
            $("#header").html(msg + xhr.status + " " + xhr.statusText);
        }
    });
    $("#footer").load("/user/partials/footer.html", function (response, status, xhr) {
        console.log(status)
        if (status == "error") {
            var msg = "An error occurred: ";
            $("#header").html(msg + xhr.status + " " + xhr.statusText);
        }
    });
    $("#sidebar").load("./sidebar.html", function (response, status, xhr) {
        console.log(status)
        if (status == "error") {
            var msg = "An error occurred: ";
            $("#header").html(msg + xhr.status + " " + xhr.statusText);
        }
    });
});