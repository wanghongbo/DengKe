var activeElement;

$(document).ready(function () {
    initUI();
    bindEvent();
});

function initUI() {
    var initData = dialog.initData;
    console.log("dialogInitData: " + JSON.stringify(initData));
    if (initData != null) {
        $("#ad-top-name").text("编辑题目");
        var typeName = model.getQuestionTypeName(initData.type);
        if (typeName != undefined) {
            $("#ad-type").text(typeName);
        }
        $("#ad-title").val(initData.title);
        if (initData.optionA != "") {
            $("#ad-answer1").val(initData.optionA);
            $("#ad-score1").val(initData.scoreA);
        }
        if (initData.optionB != "") {
            $("#ad-answer2").val(initData.optionB);
            $("#ad-score2").val(initData.scoreB);
        }
        if (initData.optionC != "") {
            $("#ad-answer3").val(initData.optionC);
            $("#ad-score3").val(initData.scoreC);
        }
        if (initData.optionD != "") {
            $("#ad-answer4").val(initData.optionD);
            $("#ad-score4").val(initData.scoreD);
        }
        if (initData.optionE != "") {
            $("#ad-answer5").val(initData.optionE);
            $("#ad-score5").val(initData.scoreE);
        }
    } else {
        if (typeof (questionType) != "undefined") {
            var typeName = model.getQuestionTypeName(questionType)
            if (typeName != undefined) {
                $("#ad-type").text(typeName);
            }
            var options = model.getQuestionOptions(questionType);
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
    }
    $("#ad-title").focus();
    activeElement = $("#ad-title");
}

function bindEvent() {
    $("#ad-cancel").click(function (e) {
        dialog.closeAddQuestion(null);
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
    if (dialog.initData == null) {  //添加题目
        result.type = questionType;
    } else {    //编辑题目
        result = dialog.initData;
    }
    result.title = $("#ad-title").val();
    result.optionA = $("#ad-answer1").val();
    result.scoreA = Number($("#ad-score1").val());
    result.optionB = $("#ad-answer2").val();
    result.scoreB = Number($("#ad-score2").val());
    result.optionC = $("#ad-answer3").val();
    result.scoreC = Number($("#ad-score3").val());
    result.optionD = $("#ad-answer4").val();
    result.scoreD = Number($("#ad-score4").val());
    result.optionE = $("#ad-answer5").val();
    result.scoreE = Number($("#ad-score5").val());
    dialog.closeAddQuestion(result);
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