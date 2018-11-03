var model = function () { }

model.defaultQuestionOptionData = [
    {
        "type": "1",
        "typeName": "学习动机测评",
        "options": [{
            "option": "完全不符合",
            "score": "1"
        }, {
            "option": "不太符合",
            "score": "2"
        }, {
            "option": "一般",
            "score": "3"
        }, {
            "option": "比较符合",
            "score": "4"
        }, {
            "option": "完全符合",
            "score": "5"
        }]
    },
    {
        "type": "2",
        "typeName": "学习压力测评",
        "options": [{
            "option": "从不",
            "score": "5"
        }, {
            "option": "很少",
            "score": "4"
        }, {
            "option": "有时",
            "score": "3"
        }, {
            "option": "经常",
            "score": "2"
        }, {
            "option": "总是",
            "score": "1"
        }]
    },
    {
        "type": "3",
        "typeName": "学习拖延测评",
        "options": [{
            "option": "完全不符合",
            "score": "1"
        }, {
            "option": "不太符合",
            "score": "2"
        }, {
            "option": "一般",
            "score": "3"
        }, {
            "option": "比较符合",
            "score": "4"
        }, {
            "option": "完全符合",
            "score": "5"
        }]
    },
    {
        "type": "4",
        "typeName": "学习状态测评",
        "options": [{
            "option": "从不",
            "score": "5"
        }, {
            "option": "很少",
            "score": "4"
        }, {
            "option": "有时",
            "score": "3"
        }, {
            "option": "经常",
            "score": "2"
        }, {
            "option": "总是",
            "score": "1"
        }]
    },
    {
        "type": "5",
        "typeName": "学习风格测评",
        "options": null
    },
    {
        "type": "6",
        "typeName": "学习焦虑测评",
        "options": [{
            "option": "是",
            "score": "0"
        }, {
            "option": "否",
            "score": "5"
        }]
    },
    {
        "type": "7",
        "typeName": "学习方法与技能测评",
        "options": [{
            "option": "很不符合自己的情况",
            "score": "1"
        }, {
            "option": "不太符合自己的情况",
            "score": "2"
        }, {
            "option": "一般",
            "score": "3"
        }, {
            "option": "比较符合自己的情况",
            "score": "4"
        }, {
            "option": "很符合自己的情况",
            "score": "5"
        }]
    },
    {
        "type": "8",
        "typeName": "学习能力检测",
        "options": [{
            "option": "完全不符合",
            "score": "1"
        }, {
            "option": "不太符合",
            "score": "2"
        }, {
            "option": "一般",
            "score": "3"
        }, {
            "option": "比较符合",
            "score": "4"
        }, {
            "option": "完全符合",
            "score": "5"
        }]
    },
    {
        "type": "9",
        "typeName": "时间管理能力测评",
        "options": [{
            "option": "完全不符合",
            "score": "1"
        }, {
            "option": "不太符合",
            "score": "2"
        }, {
            "option": "一般",
            "score": "3"
        }, {
            "option": "比较符合",
            "score": "4"
        }, {
            "option": "完全符合",
            "score": "5"
        }]
    },
    {
        "type": "10",
        "typeName": "自主学习能力测评",
        "options": [{
            "option": "完全不是这样",
            "score": "1"
        }, {
            "option": "很少这样或是偶尔有一二次是这样",
            "score": "2"
        }, {
            "option": "有时是这样，有时不是这样",
            "score": "3"
        }, {
            "option": "经常是这样或是多数情况是这样",
            "score": "4"
        }, {
            "option": "完全是这样",
            "score": "5"
        }]
    }
]

model.questionOptionArray = new Array();

(function initQuestionOptionArray() {
    for (index in model.defaultQuestionOptionData) {
        var item = model.defaultQuestionOptionData[index];
        var type = item.type;
        model.questionOptionArray[type] = item;
    }
})();

model.getQuestionTypeName = function (questionType) {
    if (questionType == undefined) {
        return undefined;
    } else {
        return this.questionOptionArray[questionType].typeName;
    }
}

model.getQuestionOptions = function (questionType) {
    if (questionType == undefined) {
        return undefined;
    } else {
        return this.questionOptionArray[questionType].options;
    }
}

model.parseQuestions = function (questionsData) {
    var questions = questionsData.data.page;

}