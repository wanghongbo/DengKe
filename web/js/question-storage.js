layui.use('table', function () {
    var table = layui.table;

    table.render({
        elem: '#question-table'
        , url: '/subject/getPage'
        , where: { type: questionType }
        , height: 600
        , limit: 100
        , cols: [[
            { field: 'index', title: '题号', width: 60, align: 'center', type: 'numbers' }
            , { field: 'title', title: '题目', width: 400, align: 'center' }
            , {
                field: 'option', title: '选项', width: 360, align: 'center', templet: function (d) {
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
                    if (typeName != null) {
                        return typeName;
                    } else {
                        return "";
                    }
                }
            }
            , { title: '操作', width: 180, align: 'center', unresize: true, toolbar: '#table-toolbar' }
        ]]
        , request: {
            pageName: 'pageNo'
            , limitName: 'pageSize'
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

    table.on('tool(question-table)', function (obj) {
        var data = obj.data;
        var event = obj.event;
        if (event === 'edit') {
            dialog.showAddQuestion(data, function (result) {
                console.log("result: " + JSON.stringify(result));
                http.updateQuestion(result, function (success, msg) {
                    if (success) {
                        // obj.update(result);
                        var selector = '.question-storage-item[type="' + questionType + '"]';
                        $(selector)[0].click();
                    } else {
                        layer.msg(msg);
                    }
                });
            });
        } else if (event === 'del') {
            layer.confirm('确定删除题目么？', {icon: 3, title: '提示'}, function (index) {
                layer.close(index);
                http.deleteQuestion(data.id, function (success, msg) {
                    if (success) {
                        // obj.del();
                        // var selector = '.question-storage-item[type="' + questionType + '"]';
                        // $(selector)[0].click();
                        table.reload('question-table');
                    } else {
                        layer.msg(msg);
                    }
                });
            });
        }
    });
});

$(document).ready(function () {
    $("#add-question").click(function () {
        dialog.showAddQuestion(null, function (result) {
            console.log(JSON.stringify(result));
            http.addQuestion(result, function (success, msg) {
                if (success) {
                    reloadTable();
                } else {
                    layer.msg(msg);
                }
            });
        });
    });

    $("#empty-question-storage").click(function() {
        layer.confirm("确定删除此题库么？此操作不可逆！", {icon: 0, title: "警告"}, function (index) {
            layer.close(index);
            layer.confirm("请再次确定删除此题库！", {icon: 0, title: "警告"}, function(index) {
                layer.close(index);
                http.emptyQuestionStorage(questionType, function(success, error) {
                    if (success) {
                        reloadTable();
                    } else {
                        layer.msg(error);
                    }
                })
            });
        });
    });

    document.onkeydown = function (event) {
        if (event.keyCode == 13) {
            $("#add-question").click();
        }
    }
});

function reloadTable() {
    var selector = '.question-storage-item[type="' + questionType + '"]';
    $(selector)[0].click();
}