layui.use('element', function () {
    var element = layui.element;
});

layui.use('table', function () {
    var table = layui.table;

    table.render({
        elem: '#question_table'
        , height: 800
        , limit: 10
        , limits: [10,30,60,90]
        , cols: [[
            { field: 'id', title: '题号', width: 120, fixed: 'left', align: 'center' }
            , { field: 'question_title', title: '题目', width: 500, align: 'center' }
            , { field: 'options', title: '选项', width: 500, align: 'center' }
            , { title: '操作', width: 120, align: 'center', unresize: true, toolbar: '#table_toolbar' }
        ]]
        , page: true
    });
});