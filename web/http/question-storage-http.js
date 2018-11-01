function httpPostQuestion(data) {
    var url = serverAddress + "/subject/add";
    $.post(url, data,
        function (data, textStatus, jqXHR) {
            console.log("data" + data);
            console.log("status" + textStatus);
            console.log("jqXHR" + jqXHR);
        },
        "json"
    );
}