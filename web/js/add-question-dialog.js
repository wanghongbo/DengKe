var callback;

function showAddQuestionDialog(closeCallback) {
    callback = closeCallback
    $.ajax({
        url: "../html/addquestion.html",
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