var callback;

var dialog = function() {};

dialog.showAddQuestion = function(closeCallback) {
    callback = closeCallback
    $.ajax({
        url: "../html/addquestion.html",
        success: function (result) {
            $("body").append(result);
        }
    });
}

dialog.closeAddQuestion = function(result) {
    if (result != null && callback != null) {
        callback(result);
    }
    $("#ad-bg").remove();
    callback = null;
}