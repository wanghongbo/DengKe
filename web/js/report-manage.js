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
            , { field: 'score', title: '分数', width: 80, align: 'center' }
            // , { field: 'level', title: '评级', width: 80, align: 'center' }
            , { field: 'catalog', title: '类型', width: 200, align: 'center' }
            , { title: '操作', width: 180, align: 'center', unresize: true, toolbar: '#table-toolbar' }
        ]]
        , page: true
        , request: {
            pageName: 'pageNo',
            limitName: 'pageSize'
        }
        // , where: {
        //     type: '1,2,3'
        // }
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

    //搜索
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

function getSearchData() {
    var searchData = {}
    var value = $("#keyword").val();
    var keys = value.split(" ");
    for (var i = 0; i < keys.length; i++) {
        var key = keys[i].trim();
        if (key != "") {
            var components = getKeyComponents(key);
            for (var k in components) {
                searchData[k] = components[k];
            }
        }
    }
    return searchData;
}

function getKeyComponents(value) {
    var components = {}
    var minScore;
    var maxScore;
    var name;
    var type;
    if (value == "") {
        return components;
    }
    if (!isNaN(value) || value.indexOf("~") > -1 || value.indexOf("-") > -1) {    //分数
        if (!isNaN(value)) {
            minScore = parseFloat(value);
            maxScore = parseFloat(value);
        } else {
            var arr;
            if (value.indexOf("~") > -1) {
                arr = value.split("~");
            } else if (value.indexOf("-") > -1) {
                arr = value.split("-");
            }
            if (arr.length == 2) {
                var min = arr[0];
                var max = arr[1];
                if (!isNaN(min) && !isNaN(max)) {
                    minScore = parseFloat(min);
                    maxScore = parseFloat(max);
                }
            }
        }
    } else {
        var isTypeSearch = false;
        var arr = questionType.split(",");
        for (var i = 0; i < arr.length; i++) {
            var t = arr[i];
            var typeName = model.getQuestionTypeName(t);
            if (typeName.indexOf(value) > -1) {  //类型
                if (type == undefined) {
                    type = t;
                } else {
                    type += "," + t;
                }
                isTypeSearch = true;
            }
        }
        if (!isTypeSearch) {    //姓名
            name = value;
        }
    }
    if (minScore != undefined) {
        components.minScore = minScore;
    }
    if (maxScore != undefined) {
        components.maxScore = maxScore;
    }
    if (name != undefined) {
        components.name = name;
    }
    if (type != undefined) {
        components.type = type;
    }
    return components;
}