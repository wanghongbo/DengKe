package com.dengke.entity.common;

public enum SubjectTypeEnum {
    REASON ("1","Reason","学习动机"),
    PRESS ("2","Press","学习压力"),
    SLOW ("3","Slow","学习拖延"),
    STATUS ("4","Status","学习状态"),
    STYLE ("5","Style","学习风格"),
    WORRY ("6","Worry","学习焦虑"),
    SKILL ("7","Skill","学习方法与技能"),
    ABILITY ("8","Ability","学习能力"),
    TIME ("9","Time","时间管理能力"),
    SELF ("10","Self","自主学习能力");

    private String type;
    private String strategy;
    private String desc;

    SubjectTypeEnum(String type, String strategy,String desc){
        this.type = type;
        this.strategy = strategy;
        this.desc = desc;
    }

    public static String getStrategy(String type){
        for(SubjectTypeEnum subjectType:SubjectTypeEnum.values()){
            if(subjectType.type.equals(type)){
                return subjectType.strategy;
            }
        }
        return "";
    }

    public static SubjectTypeEnum getByType(String type){
        for(SubjectTypeEnum subjectType:SubjectTypeEnum.values()){
            if(subjectType.type.equals(type)){
                return subjectType;
            }
        }
        return SubjectTypeEnum.PRESS;
    }

    public String getType() {
        return type;
    }

    public String getStrategy() {
        return strategy;
    }

    public String getDesc() {
        return desc;
    }
}
