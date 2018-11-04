// var serverAddress = "http://6058f2c0.ngrok.io";
var serverAddress = "";

var http = function () { };

http.getQuestions = function (type, complete) {
    // var url = serverAddress + "/subject/getPage?pageNo=1&pageSize=100&type=" + type;
    var url = "../resource/question-storage.json";
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

http.addQuestion = function (data, complete) {
    var url = serverAddress + "/subject/add";
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
    var url = serverAddress + "/subject/delete?id=" + id;
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
    var url = serverAddress + "/subject/update";
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