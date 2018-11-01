layui.use('table', function () {
    var table = layui.table;

    table.render({
        elem: '#question-table'
        , url: '../resource/report2.json'
        , height: 600
        , limit: 10
        , limits: [10,30,60,90]
        , cols: [[
            { field: 'id', title: '题号', width: 100, align: 'center' }
            , { field: 'title', title: '题目', width: 400, align: 'center' }
            , { field: 'options', title: '选项', width: 400, align: 'center' }
            , { field: 'catalog', title: '类型', width: 180, align: 'center' }
            , { title: '操作', width: 180, align: 'center', unresize: true, toolbar: '#table-toolbar' }
        ]]
        , page: false
    });
});

$(document).ready(function () {
    $("#add-question").click(function (e) { 
        showAddQuestionDialog(function(result) {
            console.log(JSON.stringify(result));
            if (result != null) {
                http.postQuestion(result);
            }
        });
    });
});