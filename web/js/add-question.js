$(document).ready(function () {
    $("#ad-cancel").click(function (e) {
        closeAddQuestionDialog(null);
    });

    $("#ad-confirm").click(function (e) {
        if (validate()) {
            var result = {};
            result.question = $("#ad-question").val();
            result.optionA = $("#ad-optionA").val();
            result.answerA = $("#ad-answerA").val();
            result.optionB = $("#ad-optionB").val();
            result.answerB = $("#ad-answerB").val();
            result.optionC = $("#ad-optionC").val();
            result.answerC = $("#ad-answerC").val();
            result.optionD = $("#ad-optionD").val();
            result.answerD = $("#ad-answerD").val();
            result.optionE = $("#ad-optionE").val();
            result.answerE = $("#ad-answerE").val();
            closeAddQuestionDialog(result);
        }
    });

    $("#ad-bg").find("input, textarea").each(function () {
        $(this).on("input propertychange", function () {
            if ($(this).val() != "") {
                $(this).css("border-color", "#ffa07a7a");
            }
        })
        $(this).focus(function (e) { 
            activeElement = $(this);
        });
    });
});

var activeElement;

function validate() {
    var pass = true;
    $($("#ad-bg").find("input, textarea").get().reverse()).each(function () {
        if ($(this).val() == "") {
            pass = false;
            $(this).css("border-color", "red");
            if (activeElement != undefined) {
                activeElement.focus();
            } else {
                $(this).focus();
            }
        }
    });
    if (pass) {
        return true;
    } else {
        return false;
    }
}