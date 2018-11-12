var http = function () { };

http.getQuestions = function (userName, type, complete) {
    var url = "/subject/startExam?userName=" + userName + "&subjectType=" + type;
    // var url = "../resource/question-storage.json";
    $.ajax({
        type: "GET",
        url: url,
        contentType: "application/json",
        dataType: "json",
        success: function (result) {
            if (result.code == "1") {
                complete(result, "");
            } else {
                complete(null, result.msg);
            }
        },
        error: function (xhr, status, error) {
            complete(null, xhr.status + ": " + error);
        }
    })
}

http.commitExam = function (data, complete) {
    var url = "/subject/commitExam";
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (result) {
            console.log("result: " + JSON.stringify(result));
            if (result.code == "1") {
                complete(true, "");
            } else {
                complete(false, result.msg);
            }
        },
        error: function (xhr, status, error) {
            complete(false, xhr.status + ": " + error);
        }
    })
}

http.getLoginToken = function (complete) {
    var url = "/user/getToken";
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        dataType: "json",
        success: function (result) {
            console.log("result: " + JSON.stringify(result));
            if (result.code == "1") {
                complete(result.data, "");
            } else {
                complete(null, result.msg);
            }
        },
        error: function (xhr, status, error) {
            complete(null, xhr.status + ": " + error);
        }
    })
}

http.login = function (data, complete) {
    var url = "/user/login";
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (result) {
            console.log("result: " + JSON.stringify(result));
            if (result.code == "1") {
                complete(true, "");
            } else {
                complete(false, result.msg);
            }
        },
        error: function (xhr, status, error) {
            complete(false, xhr.status + ": " + error);
        }
    })
}

http.logout = function (complete) {
    var url = "/user/logout";
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        dataType: "json",
        success: function (result) {
            console.log("result: " + JSON.stringify(result));
            if (result.code == "1") {
                complete(true, "");
            } else {
                complete(false, result.msg);
            }
        },
        error: function (xhr, status, error) {
            console.log("err: " + error);
            complete(false, xhr.status + ": " + error);
        }
    })
}

http.getUserName = function (complete) {
    var url = "/user/getUserName";
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        dataType: "json",
        success: function (result) {
            console.log("result: " + JSON.stringify(result));
            if (result.code == "1") {
                complete(result.data, "");
            } else {
                complete(null, result.msg);
            }
        },
        error: function (xhr, status, error) {
            complete(null, xhr.status + ": " + error);
        }
    })
}

http.addQuestion = function (data, complete) {
    var url = "/subject/add";
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (result) {
            console.log("result: " + JSON.stringify(result));
            if (result.code == "1") {
                complete(true, "");
            } else {
                complete(false, result.msg);
            }
        },
        error: function (xhr, status, error) {
            complete(false, xhr.status + ": " + error);
        }
    })
}

http.deleteQuestion = function (id, complete) {
    var url = "/subject/delete?id=" + id;
    $.ajax({
        type: "GET",
        url: url,
        contentType: "application/json",
        dataType: "json",
        success: function (result) {
            console.log("result: " + JSON.stringify(result));
            if (result.code == "1") {
                complete(true, "");
            } else {
                complete(false, result.msg);
            }
        },
        error: function (xhr, status, error) {
            complete(false, xhr.status + ": " + error);
        }
    })
}

http.updateQuestion = function (data, complete) {
    var url = "/subject/update";
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (result) {
            console.log("result: " + JSON.stringify(result));
            if (result.code == "1") {
                complete(true, "");
            } else {
                complete(false, result.msg);
            }
        },
        error: function (xhr, status, error) {
            complete(false, xhr.status + ": " + error);
        }
    })
}

http.emptyQuestionStorage = function (type, complete) {
    var url = "/subject/empty?type=" + type;
    $.ajax({
        type: "GET",
        url: url,
        contentType: "application/json",
        dataType: "json",
        success: function (result) {
            console.log("result: " + JSON.stringify(result));
            if (result.code == "1") {
                complete(true, "");
            } else {
                complete(false, result.msg);
            }
        },
        error: function (xhr, status, error) {
            complete(false, xhr.status + ": " + error);
        }
    })
}