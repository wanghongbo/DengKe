var questionIndex = 0;

layui.use('table', function () {
    var table = layui.table;

    table.render({
        elem: '#question-table'
        , url: '/subject/getPage'
        , where: { type: questionType }
        , height: 600
        , limit: 100
        , cols: [[
            {
                field: 'index', title: '题号', width: 60, align: 'center', templet: function (d) {
                    return ++questionIndex;
                }
            }
            , { field: 'title', title: '题目', width: 400, align: 'center' }
            , {
                field: 'option', title: '选项', width: 300, align: 'center', templet: function (d) {
                    var option = "";
                    if (d.optionA != "") {
                        option += "A: " + d.optionA + "(" + d.scoreA + ")" + " ";
                    }
                    if (d.optionB != "") {
                        option += "B: " + d.optionB + "(" + d.scoreB + ")" + " ";
                    }
                    if (d.optionC != "") {
                        option += "C: " + d.optionC + "(" + d.scoreC + ")" + " ";
                    }
                    if (d.optionD != "") {
                        option += "D: " + d.optionD + "(" + d.scoreD + ")" + " ";
                    }
                    if (d.optionE != "") {
                        option += "E: " + d.optionE + "(" + d.scoreE + ")";
                    }
                    return option;
                }
            }
            , {
                field: 'typeName', title: '类型', width: 160, align: 'center', templet: function (d) {
                    var typeName = model.getQuestionTypeName(questionType);
                    return typeName;
                }
            }
            , { title: '操作', width: 180, align: 'center', unresize: true, toolbar: '#table-toolbar' }
        ]]
        , request: {
            pageName: 'pageNo'
            , limitName: 'pageSize'
        }
        , parseData: function (res) {
            return {
                "code": res.code,
                "msg": res.msg,
                "count": res.data.totalCount,
                "data": res.data.page
            };
        }
        , response: {
            statusCode: 1
        }
        , done: function (res, curr, count) {
            questionIndex = 0;
        }
    });

    table.on('tool(question-table)', function (obj) {
        var data = obj.data;
        console.log("data: " + JSON.stringify(data));
        if (obj.event === 'edit') {
        } else if (obj.event === 'del') {
            layer.confirm('确定删除题目么？', function (index) {
                layer.close(index);
                http.deleteQuestion(data.id, function (success, msg) {
                    if (success) {
                        obj.del();
                    } else {
                        layer.msg(msg);
                    }
                })
            });
        }
    });
});

$(document).ready(function () {
    $("#add-question").click(function (e) {
        dialog.showAddQuestion(function (result) {
            console.log(JSON.stringify(result));
            http.addQuestion(result, function (success, msg) {
                if (success) {
                    var selector = '.question-storage-item[type="' + questionType + '"]';
                    $(selector)[0].click();
                } else {
                    layer.msg(msg);
                }
            });
        });
    });
});