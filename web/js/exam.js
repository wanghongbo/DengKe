layui.use('layer', function () {
    var layer = layui.layer;
    layer.config({
        offset: ['300px', '']
    });
});

var userName;
var type;
var query = {};

$(document).ready(function () {
    userName = $.cookie("user_name");
    console.log("userName: " + userName);
    if (userName == undefined) {
        window.location.assign("../html/index.html");
    } else {
        initUI();
        bindEvent();
        initData();
    }
});

function initUI() {
    //用户名
    $(".user-name").text(userName);

    //考试名
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
                    console.log("query: " + JSON.stringify(query));
                }
            }
        }
    }
    type = query.type;
    var typeName = model.getQuestionTypeName(type);
    if (typeName != undefined) {
        $("#type-name").text(typeName);
    }

    //总时间
    var examTime = 60 * 60;
    $(".totalTime").text(getTotalTimeText);

    //剩余时间
    var timer = setInterval(() => {
        if (examTime > 0) {
            examTime--;
        }
        if (examTime == 60 * 5) {
            layer.msg("还剩5分钟");
        }
        var leftTimeText = getLeftTimeText(examTime);
        $("#left-time").text(leftTimeText);
        if (examTime == 0) {
            layer.msg("考试结束");
            clearInterval(timer)
        }
    }, 1000);
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
        window.location.assign("../html/index.html");
    });
}

function initData() {
    http.getQuestions(type, function (data, msg) {
        if (data != null) {
            
        } else {
            layer.msg(msg);
        }
    });
}