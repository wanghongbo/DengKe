layui.use('layer', function () {
});

$(document).ready(function () {
    $(".menu-item").click(function () {
        layer.prompt({
            formType: 0,
            value: '',
            title: '请输入您的姓名开始测试',
            offset: ['200px', '']
        }, function (value, index) {
            $.cookie("user_name", value);
            layer.close(index);
            window.location.assign("../html/exam.html");
        });
    })
});