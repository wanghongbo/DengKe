$(document).ready(function () {
    var userName = $.cookie("userName");
    $("#cpw-input-account").val(userName);

    $("#cpw-login").click(function (e) {
        if (validate()) {
            var userName = $.cookie("userName");
            var pwd = $("#cpw-input-pwd-new").val();
            var data = {};
            data.userName = userName;
            data.password = hex_md5(userName + hex_md5(pwd));
            http.changePwd(data, function(success, msg) {
                if (success) {
                    layer.confirm('修改密码成功，请重新登录', {
                        icon: 1, title: '提示',
                        btn: ['确定']
                    }, function (index) {
                        layer.close(index);
                        $("#exit").click();
                    });
                } else {
                    layer.msg(msg);
                }
            })
        }
    });

    $("#cpw-cancel").click(function (e) {
        window.location.reload();
    });

    $(".cpw-container").find("input").each(function () {
        $(this).on("input propertychange", function () {
            if ($(this).val() != "") {
                $(this).css("border-color", "#393D49");
            }
        })
    });
});

function validate() {
    var pass = true;
    var pwd = $("#cpw-input-pwd-new").val();
    var pwdConfirm = $("#cpw-input-pwd-confirm").val();
    if (pwd != pwdConfirm) {
        $("#cpw-input-pwd-new").css("cssText", "border-color: red !important;");
        $("#cpw-input-pwd-confirm").css("cssText", "border-color: red !important;");
        pass = false;
    }
    if (pass) {
        $($(".cpw-container").find("input").get().reverse()).each(function (e) {
            if ($(this).val().trim() == "") {
                pass = false;
                $(this).val("");
                $(this).focus();
                $(this).css("cssText", "border-color: red !important;");
            } else {
                $(this).css("cssText", "border-color: lightgray;");
            }
        });
    }
    if (pass) {
        return true;
    } else {
        return false;
    }
}