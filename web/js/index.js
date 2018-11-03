layui.use('layer', function () {
});

$(document).ready(function () {
    $(".menu-item").click(function () {
        var type = $(this).attr("type");
        console.log("type: " + type);
        layer.prompt({
            formType: 0,
            value: '',
            title: '请输入您的姓名开始测试',
            offset: ['300px', '']
        }, function (value, index) {
            $.cookie("user_name", value);
            layer.close(index);
            window.location.assign("../html/exam.html?type=" + type);
        });
    })
});