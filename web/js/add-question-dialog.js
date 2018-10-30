var callback;

function showAddQuestionDialog(closeCallback) {
    callback = closeCallback
    $.ajax({
        url: "http://127.0.0.1:5500/html/addquestion.html",
        success: function (result) {
            $("body").append(result);
        }
    });
}

function closeAddQuestionDialog(result) {
    if (result != null && callback != null) {
        callback(result);
    }
    $("#ad-bg").remove();
    callback = null;
}