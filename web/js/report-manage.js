layui.use('table', function () {
    var table = layui.table;

    table.render({
        elem: '#report-table'
        , url: '/report/getPage'
        // , url: '../resource/report-manage.json'
        , method: "post"
        , contentType: "application/json"
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
        , request: {
            pageName: 'pageNo',
            limitName: 'pageSize'
        }
        , where: {
            type: '1,2,3'
        }
        , response: {
            statusCode: 1
        }
        , parseData: function (res) {
            return {
                "code": res.code,
                "msg": res.msg,
                "count": res.data.totalCount,
                "data": res.data.page
            };
        }
    });

    // table.reload('report-table', {
    //     where: {
    //         pageNo: 3,
    //         pageSize: 30,
    //         type: 3
    //     }
    //     , page: {
    //         curr: 1
    //     }
    // });
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
        reload: function () {
            var keywordEle = $('#keyword');
            table.reload('report-table', {
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