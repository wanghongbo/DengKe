package com.dengke.common.service.impl;

import com.dengke.common.service.SubjectStrategy;
import com.dengke.common.utils.DateUtil;
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

@Service("subjectSelfStrategy")
public class SubjectSelfStrategyImpl extends SubjectStrategy {

    @Override
    public String[] scoreDetail(double[] scores,String[] options) {

        double offenScore = 0;
        double effectScore = 0;
        double loveScore = 0;
        double openScore = 0;
        double stillScore = 0;
        for(int i=0;i<Constants.SUBJECT_SELF_OFFEN;i++){
            offenScore+=scores[i];
        }
        for(int i=Constants.SUBJECT_SELF_OFFEN;i<Constants.SUBJECT_SELF_EFFECT;i++){
            effectScore+=scores[i];
        }
        for(int i=Constants.SUBJECT_SELF_EFFECT;i<Constants.SUBJECT_SELF_LOVE;i++){
            loveScore+=scores[i];
        }
        for(int i=Constants.SUBJECT_SELF_LOVE;i<Constants.SUBJECT_SELF_OPEN;i++){
            openScore+=scores[i];
        }
        openScore = openScore*2;
        for(int i=Constants.SUBJECT_SELF_OPEN;i<Constants.SUBJECT_SELF_STILL;i++){
            stillScore+=scores[i];
        }
        stillScore = stillScore*10/7;
        return new String[]{
                Utils.generateDetailScore(offenScore,effectScore,loveScore,
                        openScore,stillScore),
                df.format((offenScore+effectScore+loveScore+openScore+stillScore) * 5 / 21)
        };
    }
    @Override
    public Template generateTemplate(Report report,Map<String,Object> dataMap) throws IOException {
        String tempFile = Constants.REPORT_FILE_PATH+"temp_10.xml";
        String[] scores = report.getScoreDetail().split(Constants.REPORT_SCORE_SPLIT);

        dataMap.put("name",report.getUserId());
        dataMap.put("date", DateUtil.formatDate(report.getExamTime(),"yyyy-MM-dd HH:mm:ss"));
        double totalScore = report.getScore();
        dataMap.put("totalScore",df.format(totalScore));
        if(totalScore<4){
            dataMap.put("level","低");
            dataMap.put("totalReport",Constants.REPORT_TYPE_10_0_4);
        }else if(totalScore<7){
            dataMap.put("level","一般");
            dataMap.put("totalReport",Constants.REPORT_TYPE_10_4_7);
        }else {
            dataMap.put("level","高");
            dataMap.put("totalReport",Constants.REPORT_TYPE_10_7_10);
        }

        double actScore = Double.parseDouble(scores[0]);
        dataMap.put("actScore",scores[0]);
        if(actScore<4){
            dataMap.put("actLevel","低");
        }else if(actScore<7){
            dataMap.put("actLevel","一般");
        }else {
            dataMap.put("actLevel","高");
        }

        double effScore = Double.parseDouble(scores[1]);
        dataMap.put("effScore",scores[1]);
        if(effScore<4){
            dataMap.put("effLevel","低");
        }else if(effScore<7){
            dataMap.put("effLevel","一般");
        }else {
            dataMap.put("effLevel","高");
        }

        double loveScore = Double.parseDouble(scores[2]);
        dataMap.put("loveScore",scores[2]);
        if(loveScore<4){
            dataMap.put("loveLevel","低");
        }else if(loveScore<7){
            dataMap.put("loveLevel","一般");
        }else {
            dataMap.put("loveLevel","高");
        }

        double openScore = Double.parseDouble(scores[3]);
        dataMap.put("openScore",scores[2]);
        if(openScore<4){
            dataMap.put("openLevel","低");
        }else if(openScore<7){
            dataMap.put("openLevel","一般");
        }else {
            dataMap.put("openLevel","高");
        }

        double stillScore = Double.parseDouble(scores[4]);
        dataMap.put("stillScore",scores[2]);
        if(stillScore<4){
            dataMap.put("stillLevel","低");
        }else if(stillScore<7){
            dataMap.put("stillLevel","一般");
        }else {
            dataMap.put("stillLevel","高");
        }


        return ReportDocUtil.xml2XmlDoc(tempFile);
    }
}
