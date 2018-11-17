$(document).ready(function () {
    var userName = $.cookie("userName");
    $("#cpw-input-account").val(userName);

    $("#cpw-login").click(function (e) {
        if (validate()) {
            console.log("confirm");
        }
    });

    $("#cpw-cancel").click(function (e) {
        window.location.reload();
    });

    $(".cpw-container").find("input").each(function () {
        $(this).on("input propertychange", function () {
            if ($(this).val() != "") {
                $(this).css("border-color", "#393D49");
            }
        })
    });
});

function validate() {
    var pass = true;
    var emptyCount = 0;
    $($(".cpw-container").find("input").get().reverse()).each(function (e) {
        if ($(this).val().trim() == "") {
            pass = false;
            $(this).val("");
            $(this).focus();
            $(this).css("cssText", "border-color: red !important;");
        } else {
            $(this).css("cssText", "border-color: lightgray;");
        }
    });
    if (pass) {
        return true;
    } else {
        return false;
    }
}