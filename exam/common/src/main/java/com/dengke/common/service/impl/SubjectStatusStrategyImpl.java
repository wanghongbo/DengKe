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

@Service("subjectStatusStrategy")
public class SubjectStatusStrategyImpl extends SubjectStrategy {

    @Override
    public String[] scoreDetail(double[] scores,String[] options) {
        double knowScore = 0;
        double emotionScore = 0;
        double actionScore = 0;
        double interestScore = 0;
        double reasonScore = 0;
        double attentionScore = 0;
        double beliefScore = 0;
        for(int i=0;i<Constants.SUBJECT_STATUS_KNOW;i++){
            knowScore+=scores[i];
        }
        for(int i=Constants.SUBJECT_STATUS_KNOW;i<Constants.SUBJECT_STATUS_EMOTION;i++){
            emotionScore+=scores[i];
        }
        for(int i=Constants.SUBJECT_STATUS_EMOTION;i<Constants.SUBJECT_STATUS_ACTION;i++){
            actionScore+=scores[i];
        }
        for(int i=Constants.SUBJECT_STATUS_ACTION;i<Constants.SUBJECT_STATUS_INTEREST;i++){
            interestScore+=scores[i];
        }
        for(int i=Constants.SUBJECT_STATUS_INTEREST;i<Constants.SUBJECT_STATUS_REASON;i++){
            reasonScore+=scores[i];
        }
        for(int i=Constants.SUBJECT_STATUS_REASON;i<Constants.SUBJECT_STATUS_ATTENTION;i++){
            attentionScore+=scores[i];
        }
        for(int i=Constants.SUBJECT_STATUS_ATTENTION;i<Constants.SUBJECT_STATUS_BELIEF;i++){
            beliefScore+=scores[i];
        }
        return new String[]{
                Utils.generateDetailScore(knowScore,emotionScore,actionScore,interestScore,
                        reasonScore,attentionScore,beliefScore),
                df.format(((knowScore+emotionScore+actionScore)/3+interestScore+reasonScore+
                attentionScore+beliefScore)/5)
        };
    }

    @Override
    public Template generateTemplate(Report report,Map<String,Object> dataMap) throws IOException {
        String tempFile = Constants.REPORT_FILE_PATH+"temp_4.xml";
        String[] scores = report.getScoreDetail().split(Constants.REPORT_SCORE_SPLIT);

        dataMap.put("name",report.getUserId());
        dataMap.put("date", DateUtils.formatDate(report.getExamTime(),"yyyy-MM-dd HH:mm:ss"));
        double attScore = Double.parseDouble(scores[0])+Double.parseDouble(scores[1])+Double.parseDouble(scores[2]);
        attScore = attScore/3;
        dataMap.put("attScore",df.format(attScore));
        dataMap.put("intScore",scores[3]);
        dataMap.put("reaScore",scores[4]);
        dataMap.put("atnScore",scores[5]);
        dataMap.put("belScore",scores[6]);
        if(attScore<3){
            dataMap.put("attLevel1","不合格");
        }else if(attScore<3.75){
            dataMap.put("attLevel1","合格");
        }else {
            dataMap.put("attLevel1","优秀");
        }
        if(attScore<3.26){
            dataMap.put("attLevel2","低于平均分");
        }else if(attScore>3.26){
            dataMap.put("attLevel2","高于平均分");
        }else {
            dataMap.put("attLevel2","达到平均水平");
        }
        dataMap.put("attScore1",scores[0]);
        dataMap.put("attScore2",scores[1]);
        dataMap.put("attScore3",scores[2]);

        double intScore = Double.parseDouble(scores[3]);
        if(intScore<3){
            dataMap.put("intLevel1","不合格");
        }else if(intScore<3.75){
            dataMap.put("intLevel1","合格");
        }else {
            dataMap.put("intLevel1","优秀");
        }
        if(intScore<3.40){
            dataMap.put("intLevel2","低于平均分");
        }else if(intScore>3.40){
            dataMap.put("intLevel2","高于平均分");
        }else {
            dataMap.put("intLevel2","达到平均水平");
        }

        double reaScore = Double.parseDouble(scores[4]);
        if(reaScore<3){
            dataMap.put("reaLevel1","不合格");
        }else if(reaScore<3.75){
            dataMap.put("reaLevel1","合格");
        }else {
            dataMap.put("reaLevel1","优秀");
        }
        if(reaScore<3.40){
            dataMap.put("reaLevel2","低于平均分");
        }else if(reaScore>3.40){
            dataMap.put("reaLevel2","高于平均分");
        }else {
            dataMap.put("reaLevel2","达到平均水平");
        }


        double atnScore = Double.parseDouble(scores[5]);
        if(atnScore<3){
            dataMap.put("atnLevel1","不合格");
        }else if(atnScore<3.75){
            dataMap.put("atnLevel1","合格");
        }else {
            dataMap.put("atnLevel1","优秀");
        }
        if(atnScore<3.49){
            dataMap.put("atnLevel2","低于平均分");
        }else if(atnScore>3.49){
            dataMap.put("atnLevel2","高于平均分");
        }else {
            dataMap.put("atnLevel2","达到平均水平");
        }

        double belScore = Double.parseDouble(scores[6]);
        if(belScore<3){
            dataMap.put("belLevel1","不合格");
        }else if(belScore<3.75){
            dataMap.put("belLevel1","合格");
        }else {
            dataMap.put("belLevel1","优秀");
        }
        if(belScore<2.88){
            dataMap.put("belLevel2","低于平均分");
        }else if(belScore>2.88){
            dataMap.put("belLevel2","高于平均分");
        }else {
            dataMap.put("belLevel2","达到平均水平");
        }

        dataMap.put("totalScore",report.getScore());
        return ReportDocUtil.xml2XmlDoc(tempFile);
    }
}
