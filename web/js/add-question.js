var activeElement;

$(document).ready(function () {
    init();

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
});

function init() {
    var typeName = "";
    switch(questionType) {
        case 1:
        typeName = "学习动机测评";
        break;
        case 2:
        typeName = "学习压力测评";
        break;
        case 3:
        typeName = "学习拖延测评";
        break;
        case 4:
        typeName = "学习状态测评";
        break;
        case 5:
        typeName = "学习风格测评";
        break;
        case 6:
        typeName = "学习焦虑测评";
        break;
        case 7:
        typeName = "学习方法与技能测评";
        break;
        case 8:
        typeName = "学习能力检测";
        break;
        case 9:
        typeName = "时间管理能力测评";
        break;
        case 10:
        typeName = "自主学习能力测评";
        break;
        default:
        typeName = "";
    }
    $("#ad-type").text(typeName);
}

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

function confirm() {
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