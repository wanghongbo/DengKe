$(document).ready(function () {
    var userName = $.cookie("user_name");
    $("#user-name").text(userName);

    $("#exit").click(function (e) { 
        window.location.assign("../html/index.html");
    });
});