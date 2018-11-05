layui.use('table', function () {
    var table = layui.table;

    table.render({
        elem: '#reporter-table'
        , url: '../resource/reporter-manage.json'
        , height: 600
        , limit: 10
        , limits: [10, 30, 60, 90]
        , cols: [[
            { field: 'id', title: '编号', width: 80, align: 'center' }
            , { field: 'time', title: '时间', width: 180, align: 'center' }
            , { field: 'name', title: '姓名', width: 100, align: 'center' }
            , { field: 'score', title: '得分', width: 80, align: 'center' }
            , { field: 'level', title: '评级', width: 80, align: 'center' }
            , { field: 'catalog', title: '类型', width: 200, align: 'center' }
            , { title: '操作', width: 180, align: 'center', unresize: true, toolbar: '#table-toolbar' }
        ]]
        , page: true
        // , response: {
        //     statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
        // }
        // , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
        //     return {
        //         "code": res.status, //解析接口状态
        //         "msg": res.message, //解析提示文本
        //         "count": res.total, //解析数据长度
        //         "data": res.rows.item //解析数据列表
        //     };
        // }
    });
});

layui.use('table', function () {
    var table = layui.table;
    //监听工具条
    table.on('tool(reporter-table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'download') {
            layer.msg('ID：' + data.id + ' 的下载操作');
        } else if (obj.event === 'del') {
            layer.confirm('确定删除报告么？', function (index) {
                obj.del();
                layer.close(index);
            });
        }
    });

    var $ = layui.$, active = {
        reload: function () {
            var keywordEle = $('#keyword');
            table.reload('reporter-table', {
                where: {
                    keyword: keywordEle.val()
                }
            });
        }
    };

    $('#search').click(function (e) {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
});