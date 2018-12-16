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

@Service("subjectReasonStrategy")
public class SubjectReasonStrategyImpl extends SubjectStrategy {

    @Override
    public String[] scoreDetail(double[] scores,String[] options) {
        DecimalFormat df = new DecimalFormat("#0.00");
        double lessScore = 0;
        double moreScore = 0;
        double interestScore = 0;
        double targetScore = 0;
        for(int i=0;i<Constants.SUBJECT_REASON_LESS;i++){
            if("A".equalsIgnoreCase(options[i])){
                lessScore+=scores[i];
            }else if("B".equalsIgnoreCase(options[i])){
                moreScore+=scores[i];
            }
        }
        for(int i=Constants.SUBJECT_REASON_LESS;i<Constants.SUBJECT_REASON_MORE;i++){
            if("A".equalsIgnoreCase(options[i])){
                moreScore+=scores[i];
            }else if("B".equalsIgnoreCase(options[i])){
                lessScore+=scores[i];
            }
        }
        for(int i=Constants.SUBJECT_REASON_MORE;i<Constants.SUBJECT_REASON_INTEREST;i++){
            interestScore+=scores[i];
        }
        for(int i=Constants.SUBJECT_REASON_INTEREST;i<Constants.SUBJECT_REASON_TARGET;i++){
            targetScore+=scores[i];
        }
        return new String[]{
                Utils.generateDetailScore(lessScore,moreScore,interestScore,targetScore),
                df.format((lessScore+moreScore+interestScore+targetScore)/2)
        };
    }

    @Override
    public Template generateTemplate(Report report,Map<String,Object> dataMap) throws IOException {
        String tempFile = Constants.REPORT_FILE_PATH+"temp_1.xml";
        String[] scores = report.getScoreDetail().split(Constants.REPORT_SCORE_SPLIT);

        dataMap.put("name",report.getUserId());
        dataMap.put("date", DateUtils.formatDate(report.getExamTime(),"yyyy-MM-dd HH:mm:ss"));
        dataMap.put("weakScore",scores[0]);
        if(Double.parseDouble(scores[0])<2){
            dataMap.put("weakResult","是");
            dataMap.put("weakReport",Constants.REPORT_TYPE_1_1_0_2);
        }else{
            dataMap.put("weakResult","否");
            dataMap.put("weakReport",Constants.REPORT_TYPE_1_1_2_10);
        }
        dataMap.put("strongScore",scores[1]);
        if(Double.parseDouble(scores[1])<2){
            dataMap.put("strongResult","是");
            dataMap.put("strongReport",Constants.REPORT_TYPE_1_2_0_2);
        }else{
            dataMap.put("strongResult","否");
            dataMap.put("strongReport",Constants.REPORT_TYPE_1_2_2_10);
        }
        dataMap.put("intScore",scores[2]);
        if(Double.parseDouble(scores[2])<2){
            dataMap.put("intResult","是");
            dataMap.put("intReport",Constants.REPORT_TYPE_1_3_0_2);
        }else{
            dataMap.put("intResult","否");
            dataMap.put("intReport",Constants.REPORT_TYPE_1_3_2_10);
        }
        dataMap.put("tarScore",scores[3]);
        if(Double.parseDouble(scores[3])<2){
            dataMap.put("tarResult","是");
            dataMap.put("tarReport",Constants.REPORT_TYPE_1_4_0_2);
        }else{
            dataMap.put("tarResult","否");
            dataMap.put("tarReport",Constants.REPORT_TYPE_1_4_2_10);
        }
        dataMap.put("totalScore",df.format(report.getScore()));
        return ReportDocUtil.xml2XmlDoc(tempFile);
    }
}
