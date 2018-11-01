// var serverAddress = "http://6058f2c0.ngrok.io";
var serverAddress = "";

var http = function () {};

http.postQuestion = function(data) {
    var url = serverAddress + "/subject/add";
    $.ajax({
        type: "POST",
        url: url,
        headers: {
            "Content-Type": "application/json"
        },
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(data),
        success: function(data) {
            console.log("data: " + data);
        },
        error: function(err) {
            console.log("err: " + err);
        },
        complete: function(XMLHttpRequest, status) {
            console.log("status: " + status);
        }
    })
}