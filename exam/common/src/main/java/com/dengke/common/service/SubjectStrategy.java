package com.dengke.common.service;

import com.dengke.entity.Report;
import com.dengke.entity.common.Constants;
import com.dengke.entity.common.ReportDocUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.http.client.utils.DateUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

public abstract class SubjectStrategy {

    public String[] scoreDetail(double[] scores,String[] options){
        DecimalFormat df = new DecimalFormat("#0.00");
        double totalScore = 0;
        for(int i=0;i<scores.length;i++){
            totalScore+=scores[i];
        }
        return new String[]{
                "",
                df.format(totalScore)
        };
    }

    public Template generateTemplate(Report report,Map<String,Object> dataMap) throws IOException, TemplateException {
        double totalScore = report.getScore();
        String type = report.getType();
        String tempFile = "";
        if(totalScore < 2.5){
            tempFile = "temp_"+type+"_0_25.xml";
        }else if(totalScore<5){
            tempFile = "temp_"+type+"_25_50.xml";
        }else if(totalScore<7.5){
            tempFile = "temp_"+type+"_50_75.xml";
        }else {
            tempFile = "temp_"+type+"_75_100.xml";
        }
        dataMap.put("name",report.getUserId());
        dataMap.put("date", DateUtils.formatDate(report.getExamTime(),"yyyy-MM-dd HH:mm:ss"));
        return ReportDocUtil.xml2XmlDoc(Constants.REPORT_FILE_PATH+tempFile);
    }

}
