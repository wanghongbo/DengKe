package com.dengke.entity.common;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class QueryParam {

    private double minScore = -1;
    private double maxScore = -1;
    private String name;
    private String type;
    private int pageNo=1;
    private int pageSize=10;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public double getMinScore() {
        return minScore;
    }

    public void setMinScore(double minScore) {
        this.minScore = minScore;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public String getName() {
        return StringUtils.isBlank(name)?null:name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
//        if(ArrayUtils.isEmpty(type)){
//            return null;
//        }
//        StringBuilder sb = new StringBuilder("(");
//        for(String s:type){
//            sb.append("'").append(s).append("',");
//        }
//        sb.replace(sb.length()-1,sb.length(),")");
//        return sb.toString();
        return type;
    }

//    public static void main(String[] args){
//        QueryParam qp = new QueryParam();
////        qp.setType(new String[]{"1","2"});
//        System.out.println(JSON.toJSONString(qp));
//    }

    public void setType(String type) {
        this.type = type;
    }
}
