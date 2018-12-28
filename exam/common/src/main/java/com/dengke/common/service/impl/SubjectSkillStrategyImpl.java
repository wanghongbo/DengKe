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

@Service("subjectSkillStrategy")
public class SubjectSkillStrategyImpl extends SubjectStrategy {

    @Override
    public String[] scoreDetail(double[] scores,String[] options) {
        double manageScore = 0;
        double reviewScore = 0;
        double thinkScore = 0;
        double assistScore = 0;
        double readScore = 0;
        double writeScore = 0;
        double inScore = 0;
        double habitScore = 0;
        for(int i=0;i<Constants.SUBJECT_SKILL_HABIT;i++){
            if(i<Constants.SUBJECT_SKILL_MANAGE){
                manageScore+=scores[i];
            }else if(i<Constants.SUBJECT_SKILL_REVIEW){
                reviewScore+=scores[i];
            }else if(i<Constants.SUBJECT_SKILL_THINK){
                thinkScore+=scores[i];
            }else if(i<Constants.SUBJECT_SKILL_ASSIST){
                assistScore+=scores[i];
            }else if(i<Constants.SUBJECT_SKILL_READ){
                readScore+=scores[i];
            }else if(i<Constants.SUBJECT_SKILL_WRITE){
                writeScore+=scores[i];
            }else if(i<Constants.SUBJECT_SKILL_IN){
                inScore+=scores[i];
            }else {
                habitScore+=scores[i];
            }
        }
        return new String[]{
                Utils.generateDetailScore(manageScore,reviewScore,thinkScore,
                        assistScore,readScore,writeScore,inScore,habitScore),
                df.format((manageScore+reviewScore+thinkScore+assistScore+readScore+writeScore+inScore+habitScore)/4)
        };
    }

    @Override
    public Template generateTemplate(Report report,Map<String,Object> dataMap) throws IOException {
        String tempFile = Constants.REPORT_FILE_PATH+"temp_7.xml";
        String[] scores = report.getScoreDetail().split(Constants.REPORT_SCORE_SPLIT);

        dataMap.put("name",report.getUserId());
        dataMap.put("date", DateUtil.formatDate(report.getExamTime(),"yyyy-MM-dd HH:mm:ss"));
        double totalScore = report.getScore();
        if(totalScore<=3){
            dataMap.put("level","较差");
            dataMap.put("totalReport",Constants.REPORT_TYPE_7_0_3);
        }else if(totalScore<=7){
            dataMap.put("level","一般");
            dataMap.put("totalReport",Constants.REPORT_TYPE_7_3_7);
        }else {
            dataMap.put("level","优秀");
            dataMap.put("totalReport",Constants.REPORT_TYPE_7_7_10);
        }
        if(totalScore<2){
            dataMap.put("star","☆");
        }else if(totalScore<4){
            dataMap.put("star","☆☆");
        }else if(totalScore<6){
            dataMap.put("star","☆☆☆");
        }else if(totalScore<8){
            dataMap.put("star","☆☆☆☆");
        }else{
            dataMap.put("star","☆☆☆☆☆");
        }

        dataMap.put("magScore",scores[0]);
        double magScore = Double.parseDouble(scores[0]);
        if(magScore<=1.5){
            dataMap.put("magLevel","较差");
            dataMap.put("magReport",Constants.REPORT_TYPE_71_0_15);
        }else if(magScore<=4.5){
            dataMap.put("magLevel","一般");
            dataMap.put("magReport",Constants.REPORT_TYPE_71_15_45);
        }else{
            dataMap.put("magLevel","优秀");
            dataMap.put("magReport",Constants.REPORT_TYPE_71_45_10);
        }

        dataMap.put("revScore",scores[1]);
        double revScore = Double.parseDouble(scores[1]);
        if(revScore<=1.5){
            dataMap.put("revLevel","较差");
            dataMap.put("revReport",Constants.REPORT_TYPE_72_0_15);
        }else if(revScore<=4.5){
            dataMap.put("revLevel","一般");
            dataMap.put("revReport",Constants.REPORT_TYPE_72_15_45);
        }else{
            dataMap.put("revLevel","优秀");
            dataMap.put("revReport",Constants.REPORT_TYPE_72_45_10);
        }

        dataMap.put("thiScore",scores[2]);
        double thiScore = Double.parseDouble(scores[2]);
        if(thiScore<=1.5){
            dataMap.put("thiLevel","较差");
            dataMap.put("thiReport",Constants.REPORT_TYPE_73_0_15);
        }else if(thiScore<=4.5){
            dataMap.put("thiLevel","一般");
            dataMap.put("thiReport",Constants.REPORT_TYPE_73_15_45);
        }else{
            dataMap.put("thiLevel","优秀");
            dataMap.put("thiReport",Constants.REPORT_TYPE_73_45_10);
        }

        dataMap.put("assScore",scores[3]);
        double assScore = Double.parseDouble(scores[3]);
        if(assScore<=1.5){
            dataMap.put("assLevel","较差");
            dataMap.put("assReport",Constants.REPORT_TYPE_74_0_15);
        }else if(assScore<=4.5){
            dataMap.put("assLevel","一般");
            dataMap.put("assReport",Constants.REPORT_TYPE_74_15_45);
        }else{
            dataMap.put("assLevel","优秀");
            dataMap.put("assReport",Constants.REPORT_TYPE_74_45_10);
        }

        dataMap.put("readScore",scores[4]);
        double readScore = Double.parseDouble(scores[4]);
        if(assScore<=1.5){
            dataMap.put("readLevel","较差");
            dataMap.put("readReport",Constants.REPORT_TYPE_75_0_15);
        }else if(readScore<=4.5){
            dataMap.put("readLevel","一般");
            dataMap.put("readReport",Constants.REPORT_TYPE_75_15_45);
        }else{
            dataMap.put("readLevel","优秀");
            dataMap.put("readReport",Constants.REPORT_TYPE_75_45_10);
        }

        dataMap.put("writeScore",scores[5]);
        double writeScore = Double.parseDouble(scores[5]);
        if(writeScore<=1.5){
            dataMap.put("writeLevel","较差");
            dataMap.put("writeReport",Constants.REPORT_TYPE_76_0_15);
        }else if(writeScore<=4.5){
            dataMap.put("writeLevel","一般");
            dataMap.put("writeReport",Constants.REPORT_TYPE_76_15_45);
        }else{
            dataMap.put("writeLevel","优秀");
            dataMap.put("writeReport",Constants.REPORT_TYPE_76_45_10);
        }


        dataMap.put("skillScore",scores[6]);
        double skillScore = Double.parseDouble(scores[6]);
        if(skillScore<=1.5){
            dataMap.put("skillLevel","较差");
            dataMap.put("skillReport",Constants.REPORT_TYPE_77_0_15);
        }else if(skillScore<=4.5){
            dataMap.put("skillLevel","一般");
            dataMap.put("skillReport",Constants.REPORT_TYPE_77_15_45);
        }else{
            dataMap.put("skillLevel","优秀");
            dataMap.put("skillReport",Constants.REPORT_TYPE_77_45_10);
        }

        dataMap.put("habScore",scores[7]);
        double habScore = Double.parseDouble(scores[7]);
        if(habScore<=1.5){
            dataMap.put("habLevel","较差");
            dataMap.put("habReport",Constants.REPORT_TYPE_78_0_15);
        }else if(habScore<=4.5){
            dataMap.put("habLevel","一般");
            dataMap.put("habReport",Constants.REPORT_TYPE_78_15_45);
        }else{
            dataMap.put("habLevel","优秀");
            dataMap.put("habReport",Constants.REPORT_TYPE_78_45_10);
        }
        dataMap.put("totalScore",df.format(report.getScore()));
        return ReportDocUtil.xml2XmlDoc(tempFile);
    }

}
