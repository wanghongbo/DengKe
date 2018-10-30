function showAddQuestionDialog() {
    $.ajax({
        url: "http://127.0.0.1:5500/html/addquestion.html",
        success: function (result) {
            $("body").append(result);
        }
    });
}

function closeAddQuestionDialog() {

}