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
            $("#ad-option1").val(initData.optionA);
            $("#ad-score1").val(initData.scoreA);
        }
        if (initData.optionB != "") {
            $("#ad-option2").val(initData.optionB);
            $("#ad-score2").val(initData.scoreB);
        }
        if (initData.optionC != "") {
            $("#ad-option3").val(initData.optionC);
            $("#ad-score3").val(initData.scoreC);
        }
        if (initData.optionD != "") {
            $("#ad-option4").val(initData.optionD);
            $("#ad-score4").val(initData.scoreD);
        }
        if (initData.optionE != "") {
            $("#ad-option5").val(initData.optionE);
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
                    var optionId = "#ad-option" + i;
                    if (item.option != null) {
                        $(optionId).val(item.option);
                    }
                    //设置分数
                    var scoreId = "#ad-score" + i;
                    if (item.score != null) {
                        $(scoreId).val(item.score);
                    }
                }
            }
        }
    }
    $("#ad-title").focus();
    activeElement = $("#ad-title");
}

var ad_onkeydown;

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

    ad_onkeydown = document.onkeydown;
    document.onkeydown = function (event) {
        if (event.keyCode == 13) {
            console.log("--add enter");
            $("#ad-confirm").click();
        }
    }
}

function confirm() {
    var result = {};
    if (dialog.initData == null) {  //添加题目
        result.type = questionType;
    } else {    //编辑题目
        result = dialog.initData;
    }
    result.title = $("#ad-title").val().trim();
    result.optionA = $("#ad-option1").val().trim();
    result.scoreA = Number($("#ad-score1").val());
    result.optionB = $("#ad-option2").val().trim();
    result.scoreB = Number($("#ad-score2").val());
    result.optionC = $("#ad-option3").val().trim();
    result.scoreC = Number($("#ad-score3").val());
    result.optionD = $("#ad-option4").val().trim();
    result.scoreD = Number($("#ad-score4").val());
    result.optionE = $("#ad-option5").val().trim();
    result.scoreE = Number($("#ad-score5").val());
    dialog.closeAddQuestion(result);
}

function validate() {
    var pass = true;
    var emptyCount = 0;
    $($("#ad-bg").find("textarea, input").get().reverse()).each(function (e) {
        if ($(this).val().trim() == "") {
            emptyCount++;
            if (emptyCount > 3) {
                pass = false;
                $(this).css("cssText", "border-color: red !important;");
            } else {
                $(this).css("cssText", "border-color: lightgray;");
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