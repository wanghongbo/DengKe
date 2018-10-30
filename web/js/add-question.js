$(document).ready(function () {
    $("#ad-cancel").click(function (e) {
        closeAddQuestionDialog(null);
    });

    $("#ad-confirm").click(function (e) {
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
    });
});