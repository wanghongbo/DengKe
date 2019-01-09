package com.dengke.common.service.impl;

import com.dengke.common.service.SubjectStrategy;
import com.dengke.common.utils.DateUtil;
import com.dengke.entity.Report;
import com.dengke.entity.common.Constants;
import com.dengke.entity.common.ReportDocUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service("subjectSlowStrategy")
public class SubjectSlowStrategyImpl extends SubjectStrategy {
    @Override
    public Template generateTemplate(Report report, Map<String, Object> dataMap) throws IOException, TemplateException {
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
