layui.use('table', function () {
    var table = layui.table;

    table.render({
        elem: '#report_table'
        , url: 'http://localhost/DengKe/resource/table_data2.json'
        , title: '用户数据表'
        , height: 600
        , limit: 10
        , limits: [10,30,60,90]
        , totalRow: true
        , cols: [[
            { field: 'id', title: 'ID', width: 80, fixed: 'left', unresize: true, sort: true, totalRowText: '合计行' }
            , { field: 'username', title: '用户名', width: 120, edit: 'text' }
            , { field: 'experience', title: '积分', width: 80, sort: true, totalRow: true }
            , { field: 'sex', title: '性别', width: 80, edit: 'text', sort: true }
            , { field: 'logins', title: '登入次数', width: 100, sort: true, totalRow: true }
            , { field: 'sign', title: '签名' }
            , { field: 'city', title: '城市', width: 100 }
            , { fixed: 'right', width: 178, align: 'center', toolbar: '#table_toolbar' }
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
    //监听表格复选框选择
    table.on('checkbox(report_table)', function (obj) {
        console.log(obj)
    });
    //监听工具条
    table.on('tool(report_table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'detail') {
            layer.msg('ID：' + data.id + ' 的查看操作');
        } else if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            layer.alert('编辑行：<br>' + JSON.stringify(data))
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
        alert('测评');
    });
});