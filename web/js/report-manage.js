layui.use('table', function () {
    var table = layui.table;

    table.render({
        elem: '#report-table'
        , url: '/report/getPage'
        , method: "post"
        , contentType: "application/json"
        , height: 600
        , limit: 30
        , limits: [10, 30, 60]
        , cols: [[
            { 
                field: 'date', title: '时间', width: 260, align: 'center', templet: function(d) {
                    var date = new Date(d.examTime);
                    var year = date.getFullYear();
                    var month = date.getMonth();
                    var day = date.getDate();
                    var hour = date.getHours();
                    var minute = date.getMinutes();
                    var second = date.getSeconds();
                    return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
                }
            }
            , { field: 'userId', title: '姓名', width: 180, align: 'center' }
            , { field: 'score', title: '分数', width: 120, align: 'center' }
            , {
                field: 'typeName', title: '类型', width: 260, align: 'center', templet: function (d) {
                    var typeName = model.getQuestionTypeName(d.type);
                    if (typeName != null) {
                        return typeName;
                    } else {
                        return "";
                    }
                }
            }
            , { title: '操作', width: 180, align: 'center', unresize: true, toolbar: '#table-toolbar' }
        ]]
        , page: true
        , request: {
            pageName: 'pageNo',
            limitName: 'pageSize'
        }
        , where: getSearchKey()
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
    
    //监听工具条
    table.on('tool(report-table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'download') {
            //下载报告
            var id = data.id;
            var href = window.document.location.href;
            var pathName = window.document.location.pathname;
            var pos = href.indexOf(pathName)
            var host = href.substring(0, pos);
            var reportPath = host + "/report/download?id=" + id;
            window.location.assign(reportPath);
        } else if (obj.event === 'del') {
            layer.confirm('确定删除报告么？', function (index) {
                http.deleteReport(data.id, function(success, msg) {
                    if (success) {
                        obj.del();
                    } else {
                        layer.msg(msg);
                    }
                })
                layer.close(index);
            });
        }
    });

    //搜索
    var $ = layui.$, active = {
        reload: function () {
            table.reload('report-table', {
                where: getSearchKey()
                , page: {
                    curr: 1
                }
            });
        }
    };
    $('#search').click(function (e) {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    $('#empty').click(function (e) {
        $('#keyword').val("");
        $('#search').click();
    });
});

function getSearchKey() {
    //layui的table请求数据时默认会把上次的搜索参数带上，
    //所以需要用默认值覆盖上次的搜索参数，服务端进行过滤处理
    var searchKey = {
        type: questionType,
        minScore: -1,
        maxScore: -1,
        name: ""
    }
    var value = $("#keyword").val();
    var keys = value.split(" ");
    for (var i = 0; i < keys.length; i++) {
        var key = keys[i].trim();
        if (key != "") {
            var components = getKeyComponents(key);
            for (var k in components) {
                searchKey[k] = components[k];
            }
        }
    }
    console.log(searchKey);
    return searchKey;
}

function getKeyComponents(value) {
    var components = {}
    var minScore;
    var maxScore;
    var name;
    var type;
    if (value === "") {
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