var activeElement;

$(document).ready(function () {
    initUI();
    bindEvent();
});

function initUI() {
    if (typeof (questionType) != "undefined") {
        var typeName = getQuestionTypeName(questionType)
        if (typeName != undefined) {
            $("#ad-type").text(typeName);
        }

        var options = getQuestionOptions(questionType);
        if (options != null) {
            for (index in options) {
                //设置答案
                var item = options[index];
                var i = Number(index) + 1;
                var answerId = "#ad-answer" + i;
                $(answerId).val(item.answer);
                //设置分数
                var scoreId = "#ad-score" + i;
                $(scoreId).val(item.score);
            }
        }
    }
    $("#ad-question").focus();
    activeElement = $("#ad-question");
}

function bindEvent() {
    $("#ad-cancel").click(function (e) {
        closeAddQuestionDialog(null);
    });

    $("#ad-confirm").click(function (e) {
        if (validate()) {
            confirm();
        }
    });

    $("#ad-bg").find("input, textarea").each(function () {
        $(this).on("input propertychange", function () {
            if ($(this).val() != "") {
                $(this).css("border-color", "#ffd8c8");
            }
        })
        $(this).focus(function (e) {
            activeElement = $(this);
        });
    });
}

function confirm() {
    var result = {};
    result.question = $("#ad-question").val();
    result.answerA = $("#ad-answer1").val();
    result.scoreA = $("#ad-score1").val();
    result.answerB = $("#ad-answer2").val();
    result.scoreB = $("#ad-score2").val();
    result.answerC = $("#ad-answer3").val();
    result.scoreC = $("#ad-score3").val();
    result.answerD = $("#ad-answer4").val();
    result.scoreD = $("#ad-score4").val();
    result.answerE = $("#ad-answer5").val();
    result.scoreE = $("#ad-score5").val();
    closeAddQuestionDialog(result);
}

function validate() {
    var pass = true;
    var emptyCount = 0;
    $($("#ad-bg").find("textarea, input").get().reverse()).each(function (e) {
        if ($(this).val() == "") {
            emptyCount++;
            if (emptyCount > 3) {
                pass = false;
                $(this).css("border-color", "red");
            } else {
                $(this).css("border-color", "#ffd8c8");
            }
            if (activeElement != undefined) {
                activeElement.focus();
            } else {
                $(this).focus();
            }
        } else {
            if (emptyCount < 4) {
                emptyCount = 4;
            }
        }
    });
    if (pass) {
        return true;
    } else {
        return false;
    }
}