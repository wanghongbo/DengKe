var http = function () { };

http.getQuestions = function (userName, type, complete) {
    var url = "/subject/startExam?userName=" + userName + "&subjectType=" + type;
    // var url = "../resource/question-storage.json";
    $.ajax({
        type: "GET",
        url: url,
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            if (data.code == "1") {
                complete(data, "");
            } else {
                complete(null, data.msg);
            }
        },
        error: function (xhr, status, error) {
            console.log("err: " + error);
            complete(null, error);
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
        success: function (data) {
            console.log("data: " + JSON.stringify(data));
            if (data.code == "1") {
                complete(true, "");
            } else {
                complete(false, data.msg);
            }
        },
        error: function (xhr, status, error) {
            console.log("err: " + error);
            complete(false, error);
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
        success: function (data) {
            console.log("data: " + JSON.stringify(data));
            if (data.code == "1") {
                complete(true, "");
            } else {
                complete(false, data.msg);
            }
        },
        error: function (xhr, status, error) {
            console.log("err: " + error);
            complete(false, error);
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
        success: function (data) {
            console.log("data: " + JSON.stringify(data));
            if (data.code == "1") {
                complete(true, "");
            } else {
                complete(false, data.msg);
            }
        },
        error: function (xhr, status, error) {
            console.log("err: " + error);
            complete(false, error);
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
        success: function (data) {
            console.log("data: " + JSON.stringify(data));
            if (data.code == "1") {
                complete(true, "");
            } else {
                complete(false, data.msg);
            }
        },
        error: function (xhr, status, error) {
            console.log("err: " + error);
            complete(false, error);
        }
    })
}