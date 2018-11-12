layui.use('layer', function () {
    var layer = layui.layer;
    layer.config({
        offset: ['300px', '']
    });
});

$(document).ready(function () {
    $("#login").click(function (e) {
        if (validate()) {
            http.getLoginToken(function (token, error) {
                if (token != null) {
                    var loginInfo = {}
                    var userName = $("#input-account").val();
                    var pwd = $("#input-pwd").val();
                    var finalPwd = hex_md5(token + hex_md5(userName + hex_md5(pwd)));
                    loginInfo.userName = userName;
                    loginInfo.password = finalPwd;
                    http.login(loginInfo, function (success, error) {
                        if (success) {
                            window.location.assign("../html/administrator.html");
                        } else {
                            layer.msg(error);
                        }
                    })
                } else {
                    layer.msg(error);
                }
            });
        }
    });

    $("#input-account").focus();

    $(".container").find("input").each(function () {
        $(this).on("input propertychange", function () {
            if ($(this).val() != "") {
                $(this).css("border-color", "#393D49");
            }
        })
    });

    document.onkeydown = function (event) {
        if (event.keyCode == 13) {
            $("#login").click();
        }
    }
});

function validate() {
    var pass = true;
    var emptyCount = 0;
    $($(".container").find("input").get().reverse()).each(function (e) {
        if ($(this).val().trim() == "") {
            pass = false;
            $(this).val("");
            $(this).focus();
            $(this).css("border-color", "red");
        } else {
            $(this).css("border-color", "#393D49");
        }
    });
    if (pass) {
        return true;
    } else {
        return false;
    }
}