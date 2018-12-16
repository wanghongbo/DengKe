package com.dengke.common.service.impl;

import com.dengke.common.service.SubjectStrategy;
import com.dengke.entity.Report;
import com.dengke.entity.common.Constants;
import com.dengke.entity.common.ReportDocUtil;
import com.dengke.entity.common.Utils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.http.client.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

@Service("subjectTimeStrategy")
public class SubjectTimeStrategyImpl extends SubjectStrategy {

    @Override
    public String[] scoreDetail(double[] scores,String[] options) {
        double valueScore = 0;
        double monitorScore = 0;
        double effectScore = 0;
        for(int i=0;i<Constants.SUBJECT_TIME_EFFECT;i++){
            if(i<Constants.SUBJECT_TIME_VALUE){
                valueScore+=scores[i];
            }else if(i<Constants.SUBJECT_TIME_MONITOR){
                monitorScore+=scores[i];
            }else {
                effectScore+=scores[i];
            }
        }
        return new String[]{
                Utils.generateDetailScore(valueScore,monitorScore,effectScore),
            df.format((valueScore+monitorScore+effectScore)/3)
        };

    }

    @Override
    public Template generateTemplate(Report report,Map<String,Object> dataMap) throws IOException, TemplateException {
        String tempFile = Constants.REPORT_FILE_PATH+"temp_9.xml";
        String[] scores = report.getScoreDetail().split(Constants.REPORT_SCORE_SPLIT);

        dataMap.put("name",report.getUserId());
        dataMap.put("date", DateUtils.formatDate(report.getExamTime(),"yyyy-MM-dd HH:mm:ss"));
        double totalScore = report.getScore();
        dataMap.put("totalScore",df.format(totalScore));
        if(totalScore<4){
            dataMap.put("level","低");
        }else if(totalScore<7){
            dataMap.put("level","一般");
        }else {
            dataMap.put("level","高");
        }

        double valScore = Double.parseDouble(scores[0]);
        dataMap.put("valScore",scores[0]);
        if(valScore<4){
            dataMap.put("valLevel","低");
        }else if(valScore<7){
            dataMap.put("valLevel","一般");
        }else {
            dataMap.put("valLevel","高");
        }

        double monScore = Double.parseDouble(scores[1]);
        dataMap.put("monScore",scores[1]);
        if(monScore<4){
            dataMap.put("monLevel","低");
        }else if(monScore<7){
            dataMap.put("monLevel","一般");
        }else {
            dataMap.put("monLevel","高");
        }

        double ablScore = Double.parseDouble(scores[2]);
        dataMap.put("ablScore",scores[2]);
        if(ablScore<4){
            dataMap.put("ablLevel","低");
        }else if(ablScore<7){
            dataMap.put("ablLevel","一般");
        }else {
            dataMap.put("ablLevel","高");
        }


        return ReportDocUtil.xml2XmlDoc(tempFile);
    }


}
