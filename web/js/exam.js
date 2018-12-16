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
        cleanCookie();
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
                    scoreMsg = "您的得分是：" + score;
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
        $(this).click(function () {
            var answer = $(this).val();
            answers[index] = answer;
            $.cookie("answers", JSON.stringify(answers));
            updateActionUI();
        });
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

function cleanCookie() {
    $.removeCookie("userName");
    $.removeCookie("leftTime");
    $.removeCookie("index");
    $.removeCookie("answers");
    $.removeCookie("scoreMsg");
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
            $("#option-head1").show();
            $("#radio-head1").show();
            $("#radio1").show();
        } else {
            $("#option1").text("");
            $("#option-head1").hide();
            $("#radio-head1").hide();
            $("#radio1").hide();
        }
        if (question.optionB != "") {
            $("#option2").text(question.optionB);
            $("#option-head2").show();
            $("#radio-head2").show();
            $("#radio2").show();
        } else {
            $("#option2").text("");
            $("#option-head2").hide();
            $("#radio-head2").hide();
            $("#radio2").hide();
        }
        if (question.optionC != "") {
            $("#option3").text(question.optionC);
            $("#option-head3").show();
            $("#radio-head3").show();
            $("#radio3").show();
        } else {
            $("#option3").text("");
            $("#option-head3").hide();
            $("#radio-head3").hide();
            $("#radio3").hide();
        }
        if (question.optionD != "") {
            $("#option4").text(question.optionD);
            $("#option-head4").show();
            $("#radio-head4").show();
            $("#radio4").show();
        } else {
            $("#option4").text("");
            $("#option-head4").hide();
            $("#radio-head4").hide();
            $("#radio4").hide();
        }
        if (question.optionE != "") {
            $("#option5").text(question.optionE);
            $("#option-head5").show();
            $("#radio-head5").show();
            $("#radio5").show();
        } else {
            $("#option5").text("");
            $("#option-head5").hide();
            $("#radio-head5").hide();
            $("#radio5").hide();
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