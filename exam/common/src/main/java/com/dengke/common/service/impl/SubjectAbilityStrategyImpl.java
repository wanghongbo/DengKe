package com.dengke.common.service.impl;

import com.dengke.common.service.SubjectStrategy;
import com.dengke.entity.Report;
import com.dengke.entity.common.Constants;
import com.dengke.entity.common.ReportDocUtil;
import com.dengke.entity.common.Utils;
import freemarker.template.Template;
import org.apache.http.client.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

@Service("subjectAbilityStrategy")
public class SubjectAbilityStrategyImpl extends SubjectStrategy {

    private DecimalFormat df = new DecimalFormat("#0.00");
    @Override
    public String[] scoreDetail(double[] scores,String[] options) {
        double preScore = 0;
        double reviewScore = 0;
        double listenScore = 0;
        double examScore = 0;
        double timeScore = 0;
        for(int i=0;i<Constants.SUBJECT_ABL_PRE;i++){
            preScore+=scores[i];
        }
        for(int i=Constants.SUBJECT_ABL_PRE;i<Constants.SUBJECT_ABL_REVIEW;i++){
            reviewScore+=scores[i];
        }
        for(int i=Constants.SUBJECT_ABL_REVIEW;i<Constants.SUBJECT_ABL_LISTEN;i++){
            listenScore+=scores[i];
        }
        for(int i=Constants.SUBJECT_ABL_LISTEN;i<Constants.SUBJECT_ABL_EXAM;i++){
            examScore+=scores[i];
        }
        examScore = examScore*5/8;
        for(int i=Constants.SUBJECT_ABL_EXAM;i<Constants.SUBJECT_ABL_TIME;i++){
            timeScore+=scores[i];
        }
        return new String[]{
                Utils.generateDetailScore(preScore,reviewScore,listenScore,
                        examScore,timeScore),
                df.format((preScore+reviewScore+listenScore+examScore+timeScore)*4/5)
        };
    }

    @Override
    public Template generateTemplate(Report report,Map<String,Object> dataMap) throws IOException {
        String tempFile = Constants.REPORT_FILE_PATH+"temp_8.xml";
        String[] scores = report.getScoreDetail().split(Constants.REPORT_SCORE_SPLIT);

        dataMap.put("name",report.getUserId());
        dataMap.put("date", DateUtils.formatDate(report.getExamTime(),"yyyy-MM-dd HH:mm:ss"));
        double totalScore = report.getScore();
        dataMap.put("ablScore",df.format(totalScore));

        double preScore = Double.parseDouble(scores[0]);
        dataMap.put("preScore",scores[0]);
        if(preScore<3){
            dataMap.put("preLevel1","不合格");
        }else if(preScore<3.75){
            dataMap.put("preLevel1","合格");
        }else {
            dataMap.put("preLevel1","优秀");
        }
        if(preScore<2.8){
            dataMap.put("preLevel2","低于平均水平");
        }else if(preScore<=3.2){
            dataMap.put("preLevel2","约等于平均水平");
        }else {
            dataMap.put("preLevel2","高于平均水平");
        }

        double revScore = Double.parseDouble(scores[1]);
        dataMap.put("revScore",scores[1]);
        if(revScore<3){
            dataMap.put("revLevel1","不合格");
        }else if(revScore<3.75){
            dataMap.put("revLevel1","合格");
        }else {
            dataMap.put("revLevel1","优秀");
        }
        if(revScore<2.8){
            dataMap.put("revLevel2","低于平均水平");
        }else if(revScore<=3.2){
            dataMap.put("revLevel2","约等于平均水平");
        }else {
            dataMap.put("revLevel2","高于平均水平");
        }

        double lisScore = Double.parseDouble(scores[2]);
        dataMap.put("lisScore",scores[2]);
        if(lisScore<3){
            dataMap.put("lisLevel1","不合格");
        }else if(lisScore<3.75){
            dataMap.put("lisLevel1","合格");
        }else {
            dataMap.put("lisLevel1","优秀");
        }
        if(lisScore<3){
            dataMap.put("lisLevel2","低于平均水平");
        }else if(lisScore<=3.2){
            dataMap.put("lisLevel2","约等于平均水平");
        }else {
            dataMap.put("lisLevel2","高于平均水平");
        }

        double examScore = Double.parseDouble(scores[3]);
        dataMap.put("examScore",scores[3]);
        if(examScore<3){
            dataMap.put("examLevel1","不合格");
        }else if(examScore<3.75){
            dataMap.put("examLevel1","合格");
        }else {
            dataMap.put("examLevel1","优秀");
        }
        if(examScore<3.2){
            dataMap.put("examLevel2","低于平均水平");
        }else if(examScore<=3.3){
            dataMap.put("examLevel2","约等于平均水平");
        }else {
            dataMap.put("examLevel2","高于平均水平");
        }

        double timeScore = Double.parseDouble(scores[4]);
        dataMap.put("timeScore",scores[4]);
        if(timeScore<3){
            dataMap.put("timeLevel1","不合格");
        }else if(timeScore<3.75){
            dataMap.put("timeLevel1","合格");
        }else {
            dataMap.put("timeLevel1","优秀");
        }
        if(timeScore<2.8){
            dataMap.put("timeLevel2","低于平均水平");
        }else if(timeScore<=3){
            dataMap.put("timeLevel2","约等于平均水平");
        }else {
            dataMap.put("timeLevel2","高于平均水平");
        }

//        dataMap.put("totalScore",df.format(report.getScore()));
        return ReportDocUtil.xml2XmlDoc(tempFile);
    }
}
