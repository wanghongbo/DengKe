

layui.use('table', function () {
    var table = layui.table;

    table.render({
        elem: '#report-table'
        , url: '../resource/report1.json'
        , height: 600
        , limit: 10
        , limits: [10,30,60,90]
        , cols: [[
            { field: 'id', title: '编号', width: 60, align: 'center' } 
            , { field: 'time', title: '时间', width: 200, align: 'center' }
            , { field: 'name', title: '姓名', width: 100, align: 'center' }
            , { field: 'score', title: '得分', width: 100, align: 'center' }
            , { field: 'level', title: '评级', width: 100, align: 'center' }
            , { field: 'catalog', title: '类型', width: 180, align: 'center' }
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
    table.on('tool(report-table)', function (obj) {
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
        getCheckData: function () { //获取选中数据
            var checkStatus = table.checkStatus('idTest')
                , data = checkStatus.data;
            layer.alert(JSON.stringify(data));
        }
        , getCheckLength: function () { //获取选中数目
            var checkStatus = table.checkStatus('idTest')
                , data = checkStatus.data;
            layer.msg('选中了：' + data.length + ' 个');
        }
        , isAll: function () { //验证是否全选
            var checkStatus = table.checkStatus('idTest');
            layer.msg(checkStatus.isAll ? '全选' : '未全选')
        }
    };

    $('.layui-btn').on('click', function () {
    });
});