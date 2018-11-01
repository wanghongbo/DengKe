layui.use('element', function () {
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
var questionType = 1;

/*
1：学习状态报告
2：学习能力报告
*/
var reporterType = 1;

function showPage(url) {
    $.ajax({
        url: url,
        success: function (result) {
            $("#body-container").html(result);
        }
    });
}

function showReporterManagePage() {
    showPage("../html/reportermanage.html");
}

function showQuestionStoragePage() {
    showPage("..//html/questionstorage.html")
}

$(document).ready(function () {
    $(".reporter-manage-item").click(function (e) { 
        reporterType = Number($(e.target).attr("type"));
        showReporterManagePage();
    });

    $(".question-storage-item").click(function (e) { 
        questionType = Number($(e.target).attr("type"));
        showQuestionStoragePage();
    });
    
    $(".reporter-manage-item:first").trigger("click");

    $("#exit").click(function() {
        window.location.assign("../html/login.html");
    })
});