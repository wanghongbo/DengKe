package com.dengke.common.service;

import com.dengke.common.utils.DateUtil;
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

    protected DecimalFormat df = new DecimalFormat("#0.00");

    public String[] scoreDetail(double[] scores,String[] options){
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
        if(totalScore < 4){
            tempFile = "temp_"+type+"_0_40.xml";
        }else if(totalScore<6){
            tempFile = "temp_"+type+"_40_60.xml";
        }else if(totalScore<8){
            tempFile = "temp_"+type+"_60_80.xml";
        }else {
            tempFile = "temp_"+type+"_80_100.xml";
        }
        dataMap.put("name",report.getUserId());
        dataMap.put("date", DateUtil.formatDate(report.getExamTime(),"yyyy-MM-dd HH:mm:ss"));
        dataMap.put("totalScore",totalScore);
        return ReportDocUtil.xml2XmlDoc(Constants.REPORT_FILE_PATH+tempFile);
    }

}
