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

@Service("subjectStyleStrategy")
public class SubjectStyleStrategyImpl extends SubjectStrategy {

    @Override
    public String[] scoreDetail(double[] scores,String[] options) {
        double thinkScore = 0;
        double offenScore = 0;
        double feelScore = 0;
        double straightScore = 0;
        double viewScore = 0;
        double hearScore = 0;
        double slowScore = 0;
        double wholeScore = 0;
        for(int i=0;i<Constants.SUBJECT_STYLE_WORK;i++){
            if("A".equalsIgnoreCase(options[i])){
                thinkScore+=scores[i];
            }else if("B".equalsIgnoreCase(options[i])){
                offenScore+=scores[i];
            }
        }
        for(int i=Constants.SUBJECT_STYLE_WORK;i<Constants.SUBJECT_STYLE_FEEL;i++){
            if("A".equalsIgnoreCase(options[i])){
                feelScore+=scores[i];
            }else if("B".equalsIgnoreCase(options[i])){
                straightScore+=scores[i];
            }
        }
        for(int i=Constants.SUBJECT_STYLE_FEEL;i<Constants.SUBJECT_STYLE_INPUT;i++){
            if("A".equalsIgnoreCase(options[i])){
                viewScore+=scores[i];
            }else if("B".equalsIgnoreCase(options[i])){
                hearScore+=scores[i];
            }
        }
        for(int i=Constants.SUBJECT_STYLE_INPUT;i<Constants.SUBJECT_STYLE_UNDERSTAND;i++){
            if("A".equalsIgnoreCase(options[i])){
                slowScore+=scores[i];
            }else if("B".equalsIgnoreCase(options[i])){
                wholeScore+=scores[i];
            }
        }
        return new String[]{
                Utils.generateDetailScore(thinkScore,offenScore,feelScore,straightScore,
                        viewScore,hearScore,slowScore,wholeScore),
                "-1"    //学习风格没有总分
        };
    }


    @Override
    public Template generateTemplate(Report report,Map<String,Object> dataMap) throws IOException {
        String tempFile = Constants.REPORT_FILE_PATH+"temp_5.xml";
        String[] scores = report.getScoreDetail().split(Constants.REPORT_SCORE_SPLIT);

        dataMap.put("name",report.getUserId());
        dataMap.put("date", DateUtils.formatDate(report.getExamTime(),"yyyy-MM-dd HH:mm:ss"));

        dataMap.put("actScore",scores[1]);
        dataMap.put("thiScore",scores[0]);
        dataMap.put("feelScore",scores[2]);
        dataMap.put("strScore",scores[3]);
        dataMap.put("viewScore",scores[4]);
        dataMap.put("lisScore",scores[5]);
        dataMap.put("sloScore",scores[6]);
        dataMap.put("whoScore",scores[7]);

        if(Double.parseDouble(scores[1])>Double.parseDouble(scores[0])){
            dataMap.put("atResult","积极主动型");
            dataMap.put("atReport",Constants.REPORT_TYPE_5_1_1);
        }else {
            dataMap.put("atResult","反思型");
            dataMap.put("atReport",Constants.REPORT_TYPE_5_1_2);
        }
        if(Double.parseDouble(scores[2])>Double.parseDouble(scores[3])){
            dataMap.put("fsResult","感觉型");
            dataMap.put("fsReport",Constants.REPORT_TYPE_5_2_1);
        }else {
            dataMap.put("fsResult","直觉型");
            dataMap.put("fsReport",Constants.REPORT_TYPE_5_2_2);
        }
        if(Double.parseDouble(scores[4])>Double.parseDouble(scores[5])){
            dataMap.put("vlResult","视觉型");
            dataMap.put("vlReport",Constants.REPORT_TYPE_5_3_1);
        }else {
            dataMap.put("vlResult","听觉型");
            dataMap.put("vlReport",Constants.REPORT_TYPE_5_3_2);
        }

        if(Double.parseDouble(scores[6])>Double.parseDouble(scores[7])){
            dataMap.put("swResult","循序型");
            dataMap.put("swReport",Constants.REPORT_TYPE_5_4_1);
        }else {
            dataMap.put("swResult","总体型");
            dataMap.put("swReport",Constants.REPORT_TYPE_5_4_2);
        }

        dataMap.put("totalScore","请参考下文");
        return ReportDocUtil.xml2XmlDoc(tempFile);
    }
}
