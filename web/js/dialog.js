var dialog = function() {};

dialog.initData = null;
dialog.closeCallback = null;

dialog.showAddQuestion = function(_initData, _closeCallback) {
    this.initData = _initData;
    this.closeCallback = _closeCallback
    $.ajax({
        url: "../html/addquestion.html",
        success: function (result) {
            $("body").append(result);
        }
    });
}

dialog.closeAddQuestion = function(result) {
    if (result != null && this.closeCallback != null) {
        this.closeCallback(result);
    }
    $("#ad-bg").remove();
    this.initData = null;
    this.closeCallback = null;
}