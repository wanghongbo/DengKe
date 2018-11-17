layui.use('element', function () {
});

layui.use('layer', function () {
    var layer = layui.layer;
    layer.config({
        offset: ['300px', '']
    });
});

/*
1：学习动机测评
2：学习压力测评
3：学习拖延测评
4：学习状态测评
5：学习风格测评
6：学习焦虑测评
7：学习方法与技能测评
8：学习能力检测
9：时间管理能力测评
10：自主学习能力检测
*/
var questionType = "1";

/*
1：学习状态报告
2：学习能力报告
*/
var reportType = "1";

function showPage(url) {
    $.ajax({
        url: url,
        success: function (result) {
            $("#body-container").html(result);
        }
    });
}

function showReportManagePage() {
    showPage("../html/reportmanage.html");
}

function showQuestionStoragePage() {
    showPage("../html/questionstorage.html")
}

$(document).ready(function () {
    $(".report-manage-item").click(function (e) {
        reportType = $(e.target).attr("type");
        showReportManagePage();
        var index = Number(reportType) - 1;
        setChooseItemCookie("report-manage-item", index);
    });

    $(".question-storage-item").click(function (e) {
        questionType = $(e.target).attr("type");
        showQuestionStoragePage();
        var index = Number(questionType) - 1;
        setChooseItemCookie("question-storage-item", index);
    });

    $("#changepwd").click(function() {
        showPage("../html/changepwd.html");
    });

    $("#exit").click(function () {
        $.removeCookie("userName");
        $.removeCookie("chooseClass");
        $.removeCookie("chooseIndex");
        http.logout(function(success, error) {
            window.location.assign("../html/login.html");
        })
    })

    http.getUserName(function(userName) {
        if (userName != null) {
            $("#userName").text("欢迎：" + userName);
            $.cookie("userName", userName);
            setChooseItemUI();
        } else {
            window.location.assign("../html/login.html");
        }
    })
});

function setChooseItemCookie(chooseClass, chooseIndex) {
    $.cookie("chooseClass", chooseClass);
    $.cookie("chooseIndex", chooseIndex);
}

function setChooseItemUI() {
    var chooseClass = $.cookie("chooseClass");
    var chooseIndex = $.cookie("chooseIndex");
    if (chooseClass != undefined && chooseIndex != undefined) {
        var selector = "." + chooseClass + ":eq(" + chooseIndex + ")";
        $(selector).click();
    } else {
        $(".report-manage-item:first").click();
    }
}