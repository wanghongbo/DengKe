layui.use('element', function () {
});

function showTab(url) {
    $.ajax({
        url: url,
        success: function (result) {
            $("#body-container").html(result);
        }
    });
}

function showTab1() {
    showTab("http://127.0.0.1:5500/html/reportermanage/learningstate.html");
}

function showTab3() {
    showTab("http://127.0.0.1:5500/html/questionstorage/learningmotion.html")
}

$(document).ready(function () {
    $("#tab1").click(function (e) { 
        showTab1();
    });

    $("#tab3").click(function (e) { 
        showTab3();
    });
    
    $("#tab1").trigger("click");
});