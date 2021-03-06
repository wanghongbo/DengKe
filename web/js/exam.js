layui.use('layer', function () {
    var layer = layui.layer;
    layer.config({
        offset: ['300px', '']
    });
});

var userName;
var type;
var query = {};
var examTime = 60 * 60;
var leftTime = 60 * 60;
var questions;
var total = 0;
var answers = [];
var index = 0;
var scoreMsg;

var timer;

$(document).ready(function () {
    if (initData()) {
        initUI();
        bindEvent();
        startExam();
    } else {
        window.location.assign("../html/index.html");
    }

    document.onkeydown = function (event) {
        var ele;
        if (event.keyCode == 13 || event.keyCode == 39) {  //回车或者➡️
            ele = document.getElementById("next");
        } else if (event.keyCode == 65) {   //A
            ele = document.getElementById("radio1");
        } else if (event.keyCode == 66) {   //B
            ele = document.getElementById("radio2");
        } else if (event.keyCode == 67) {   //C
            ele = document.getElementById("radio3");
        } else if (event.keyCode == 68) {   //D
            ele = document.getElementById("radio4");
        } else if (event.keyCode == 69) {   //E
            ele = document.getElementById("radio5");
        } else if (event.keyCode == 37) {   //⬅️
            ele = document.getElementById("previous");
        } else if (event.keyCode == 38) {   //⬆️
            ele = document.getElementById("first");
        } else if (event.keyCode == 40) {   //⬇️
            ele = document.getElementById("last");
        }
        if (ele != undefined && ele.getAttribute("disabled") == null && ele.getAttribute("hidden") == null) {
            //这里不能使用jQuery的click方法，否则会导致后面updateActionUI()获取到的selectedValue值为空
            ele.click();
        }
    }
});

function initData() {
    userName = $.cookie("userName");
    console.log("userName: " + userName);
    if (userName == undefined) {
        return false;
    }

    var search = window.location.search;
    if (search.indexOf("?") != -1) {
        search = search.substr(1);
        var keyValues = search.split("&");
        for (i = 0; i < keyValues.length; i++) {
            var keyValue = keyValues[i];
            if (keyValue.indexOf("=") != -1) {
                var array = keyValue.split("=");
                if (array.length == 2) {
                    var key = array[0];
                    var value = array[1];
                    query[key] = value;
                }
            }
        }
    }
    console.log("query: " + JSON.stringify(query));
    type = query.type;
    if (type == undefined) {
        return false;
    }

    var _leftTime = $.cookie("leftTime");
    if (_leftTime != undefined && !isNaN(_leftTime)) {
        leftTime = _leftTime;
    }
    if (leftTime > examTime) {
        leftTime = examTime;
    }

    var _index = $.cookie("index");
    if (_index != undefined && !isNaN(_index)) {
        index = Number(_index);
    }
    var _answers = $.cookie("answers");
    if (_answers != undefined) {
        answers = JSON.parse(_answers);
    }

    var _scoreMsg = $.cookie("scoreMsg");
    if (_scoreMsg != undefined) {
        scoreMsg = _scoreMsg;
    }

    return true;
}

function initUI() {
    //用户名
    $(".user-name").text(userName);

    //考试名
    var typeName = model.getQuestionTypeName(type);
    if (typeName != undefined) {
        $("#type-name").text(typeName);
    }

    //总时间
    $(".totalTime").text(getTotalTimeText(examTime));

    //剩余时间
    $("#left-time").text(getLeftTimeText(leftTime));

    if (scoreMsg != undefined) {
        disableRadio();
    }
}

function startTimer() {
    if (leftTime > 0) {
        timer = setInterval(function () {
            if (leftTime > 0) {
                leftTime--;
                $.cookie("leftTime", leftTime);
            }
            if (leftTime == 60 * 5) {
                layer.msg("还剩5分钟");
            }
            var leftTimeText = getLeftTimeText(leftTime);
            $("#left-time").text(leftTimeText);
            if (leftTime == 0) {
                setTimeout(function () {
                    // layer.msg("考试时间到了！");
                    alert("考试时间到了");
                }, 100);
                setTimeOverUI();
                clearInterval(timer);
            }
        }, "1000");
    }
}

function getTotalTimeText(interval) {
    var second = 0;
    var minute = 0;
    if (interval > 0) {
        second = parseInt(interval % 60);
        minute = parseInt(interval / 60);
    }
    var text = minute + "分";
    if (second != 0) {
        text += second + "秒";
    }
    return text;
}

function getLeftTimeText(interval) {
    var second = 0;
    var minute = 0;
    if (interval > 0) {
        second = parseInt(interval % 60);
        minute = parseInt(interval / 60);
    }
    return minute + "分" + second + "秒";
}

function bindEvent() {
    $("#exit").click(function (e) {
        cleanExamCookie();
        http.exitExam(function(success, msg) {
            window.location.assign("../html/index.html");
        })
    });
    $("#first").click(function (e) {
        index = 0;
        $.cookie("index", index);
        updateQuestionUI();
    });
    $("#previous").click(function (e) {
        if (index > 0) {
            index--;
            $.cookie("index", index);
        }
        updateQuestionUI();
    });
    $("#next").click(function (e) {
        if (index < total - 1) {
            index++;
            $.cookie("index", index);
        }
        updateQuestionUI();
    });
    $("#last").click(function (e) {
        if (answers.length == total) {
            index = total - 1;
        } else {
            index = answers.length;
        }
        $.cookie("index", index);
        updateQuestionUI();
    });
    $("#submit").click(function (e) {
        if (scoreMsg != undefined) {
            showDownload(scoreMsg);
        } else {
            http.commitExam(answers, function (success, msg, result) {
                if (success) {
                    var score = result.data.score;
                    if (Number(score) >= 0) {
                        scoreMsg = "您的得分是：" + score;
                    } else {
                        scoreMsg = "请下载报告后查看得分";
                    }
                    $.cookie("scoreMsg", scoreMsg);
                    showDownload(scoreMsg);
                    disableRadio();
                    clearInterval(timer);
                } else {
                    layer.msg(msg);
                }
            })
        }
    });
    $("input[name='answer']").each(function () {
        // $(this).click(function () {
        //     var answer = $(this).val();
        //     answers[index] = answer;
        //     $.cookie("answers", JSON.stringify(answers));
        //     updateActionUI();
        // });
        //这里不能使用jQuery的绑定方法，否则会导致后面updateActionUI()获取到的selectedValue值为空
        var id = $(this).prop("id");
        document.getElementById(id).onclick = function() {
            var answer = $(this).val();
            answers[index] = answer;
            $.cookie("answers", JSON.stringify(answers));
            updateActionUI();
        }
    });
}

function showDownload(scoreMsg) {
    layer.confirm(scoreMsg, {
        icon: 1, title: 'Wow~ 考试完了！',
        btn: ['下载报告', '关闭']
    }, function (index) {
        layer.close(index);
        var href = window.document.location.href;
        var pathName = window.document.location.pathname;
        var pos = href.indexOf(pathName)
        var host = href.substring(0, pos);
        var reportPath = host + "/subject/getReport";
        window.location.assign(reportPath);
    }, function (index) {
        layer.close(index);
    });
}

function disableRadio() {
    $(":radio").prop("disabled", true);
}

function startExam() {
    if (type != undefined) {
        http.startExam(userName, type, function (data, msg) {
            if (data != null) {
                questions = data.data;
                total = questions.length;
                updateQuestionUI();
                if (scoreMsg == undefined) {
                    startTimer();
                }
            } else {
                layer.msg(msg);
            }
        });
    }
}

function updateQuestionUI() {
    if (questions == undefined) {
        return;
    }
    var question = questions[index];
    if (question != undefined) {
        $("#question-index").text("第" + (index + 1) + "/" + total + "题");
        var title = question.title;
        $("#question-title").text(title);
        if (question.optionA != "") {
            $("#option1").text(question.optionA);
            $("#option-head1").removeAttr("hidden");
            $("#radio-head1").removeAttr("hidden");
            $("#radio1").removeAttr("hidden");
        } else {
            $("#option1").text("");
            $("#option-head1").attr("hidden", true);
            $("#radio-head1").attr("hidden", true);
            $("#radio1").attr("hidden", true);
        }
        if (question.optionB != "") {
            $("#option2").text(question.optionB);
            $("#option-head2").removeAttr("hidden");
            $("#radio-head2").removeAttr("hidden");
            $("#radio2").removeAttr("hidden");
        } else {
            $("#option2").text("");
            $("#option-head2").attr("hidden", true);
            $("#radio-head2").attr("hidden", true);
            $("#radio2").attr("hidden", true);
        }
        if (question.optionC != "") {
            $("#option3").text(question.optionC);
            $("#option-head3").removeAttr("hidden");
            $("#radio-head3").removeAttr("hidden");
            $("#radio3").removeAttr("hidden");
        } else {
            $("#option3").text("");
            $("#option-head3").attr("hidden", true);
            $("#radio-head3").attr("hidden", true);
            $("#radio3").attr("hidden", true);
        }
        if (question.optionD != "") {
            $("#option4").text(question.optionD);
            $("#option-head4").removeAttr("hidden");
            $("#radio-head4").removeAttr("hidden");
            $("#radio4").removeAttr("hidden");
        } else {
            $("#option4").text("");
            $("#option-head4").attr("hidden", true);
            $("#radio-head4").attr("hidden", true);
            $("#radio4").attr("hidden", true);
        }
        if (question.optionE != "") {
            $("#option5").text(question.optionE);
            $("#option-head5").removeAttr("hidden");
            $("#radio-head5").removeAttr("hidden");
            $("#radio5").removeAttr("hidden");
        } else {
            $("#option5").text("");
            $("#option-head5").attr("hidden", true);
            $("#radio-head5").attr("hidden", true);
            $("#radio5").attr("hidden", true);
        }
    }

    $("input[name='answer']").prop("checked", false);
    if (answers[index] != undefined) {
        $("input[name='answer'][value='" + answers[index] + "']").prop("checked", true);
    }

    updateActionUI();
}

function updateActionUI() {
    if (leftTime == 0) {
        setTimeOverUI();
    } else {
        var selectedValue = $("input[name='answer']:checked").val();
        if (index > 0) {
            $("#first").removeAttr("disabled");
            $("#previous").removeAttr("disabled");
        } else {
            $("#first").attr("disabled", true);
            $("#previous").attr("disabled", true);
        }
        if (index < total - 1 && selectedValue != undefined) {
            $("#next").removeAttr("disabled");
        } else {
            $("#next").attr("disabled", true);
        }
        if (index < total - 1 && index < answers.length) {
            $("#last").removeAttr("disabled");
        } else {
            $("#last").attr("disabled", true);
        }
        if (answers.length == total) {
            $("#submit").removeAttr("disabled");
        }
    }
}

function setTimeOverUI() {
    $("input[name='answer']").attr("disabled", true);
    $(".action-button").attr("disabled", true);
    $("#submit").removeAttr("disabled");
}