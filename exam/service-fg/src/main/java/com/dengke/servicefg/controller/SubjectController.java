package com.dengke.servicefg.controller;


import com.alibaba.fastjson.JSON;
import com.dengke.common.service.SubjectStrategyFatory;
import com.dengke.entity.Report;
import com.dengke.entity.ReportDetail;
import com.dengke.entity.Subject;
import com.dengke.entity.SubjectType;
import com.dengke.entity.common.*;
import com.dengke.servicefg.service.ReportService;
import com.dengke.servicefg.service.SubjectService;
import freemarker.template.Template;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping(value = "/subject",produces = "application/json;charset=UTF-8")
public class SubjectController extends BaseController {
    @Resource
    private SubjectService subjectService;

    @Resource
    private ReportService reportService;

    @RequestMapping("/getTypes")
    public String getTypes(HttpServletRequest request){
        try {
            List<SubjectType> subjectTypes = subjectService.getAllTypes();
            if(CollectionUtils.isEmpty(subjectTypes)){
                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"","");
            }
            log.info(JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"",subjectTypes));
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"",subjectTypes);
        }catch (Exception e){
            log.error("获取题目类型出错",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"","");
        }
    }

    @RequestMapping("/startExam")
    public String startExam(HttpServletRequest request){
        try {
            String userName = request.getParameter("userName");
            String subjectType = request.getParameter("subjectType");
            if(Utils.existsBlank(userName,subjectType)){
                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"参数不能为空","");
            }
            request.getSession().setAttribute(Constants.SESSION_USER_NAME,userName.trim());
            request.getSession().setAttribute(Constants.SESSION_START_EXAM_TIME,new Date());
            List<Subject> subjects = subjectService.getSubjects(subjectType.trim(),Constants.SUBJECT_STATUS_OK);
            if(CollectionUtils.isEmpty(subjects)){
                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"题库为空","");
            }
            request.getSession().setAttribute(Constants.SESSION_SUBJECTS,subjects);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"",subjects);
        }catch (Exception e){
            log.error("开始答题出错",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"开始答题出错","");
        }
    }

    @RequestMapping("/commitExam")
    public String commitExam(HttpServletRequest request,@RequestBody String[] answer){
        try {
            List<Subject> subjects = (List<Subject>) request.getSession().getAttribute(Constants.SESSION_SUBJECTS);
            if(CollectionUtils.isEmpty(subjects)){
                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"题库为空,请重新开始考试","");
            }
            if(answer.length!=subjects.size()){
                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"答题数量错误","");
            }
            List<ReportDetail> reportDetails = new ArrayList<>();
            double[] detailScore = new double[answer.length];
            for(int i=0;i<answer.length;i++){
                ReportDetail reportDetail = new ReportDetail();
                String option = answer[i].toUpperCase();
                reportDetail.setSubjectId(subjects.get(i).getId());
                reportDetail.setOption(option);
                reportDetails.add(reportDetail);
                double score = getScore(subjects.get(i),option);
                detailScore[i] = score;
            }
            String type = subjects.get(0).getType().trim();
            String[] result = SubjectStrategyFatory.getStrategy(type).scoreDetail(detailScore,answer);
            Date startExam = (Date)request.getSession().getAttribute(Constants.SESSION_START_EXAM_TIME);
            Report report = new Report();
            report.setExamTime(startExam);
            report.setScore(Double.parseDouble(result[1]));
            report.setType(type);
            report.setScoreDetail(result[0]);
            report.setStatus(Constants.SUBJECT_STATUS_OK);
            report.setUserId((String)request.getSession().getAttribute(Constants.SESSION_USER_NAME));
            reportService.addReport(report,reportDetails);
            request.getSession().setAttribute(Constants.SESSION_REPORT,report);
            request.getSession().setAttribute(Constants.SESSION_SUBJECTS,null);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"",report);
        }catch (Exception e){
            log.error("提交试卷错误",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"提交试卷出错","");
        }
    }

    @RequestMapping("/getReport")
    public void getReport(HttpServletRequest request ,HttpServletResponse response){
        try {
            Report report = (Report)request.getSession().getAttribute(Constants.SESSION_REPORT);
            if(report==null){
                return;
            }
            // 输出 word 内容文件流，提供下载
            response.reset();
            response.setContentType("application/x-msdownload;charset=UTF-8");
            String fileName = URLEncoder.encode(SubjectTypeEnum.getByType(report.getType()).getDesc()
                    +"测评报告_"+report.getUserId()+"_"
                    +DateUtils.formatDate(report.getExamTime(),"yyyyMMdd"),"UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename=\""+ fileName + ".doc\"");
            Map<String,Object> dataMap = new HashMap<>();
            Template template = SubjectStrategyFatory.getStrategy(report.getType()).generateTemplate(report,dataMap);
            Writer out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
            //生成文件
            template.process(dataMap, out);
            out.flush();
            out.close();
        }catch (Exception e){
            log.error("获取报告出错",e);
        }finally {
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        try {
            request.getSession().invalidate();
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"","");
        }catch (Exception e){
            log.error("退出系统出错",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"","");
        }
    }


    private double getScore(Subject subject,String option) throws Exception{
        switch (option){
            case "A":
                return subject.getScoreA();
            case "B":
                return subject.getScoreB();
            case "C":
                return subject.getScoreC();
            case "D":
                return subject.getScoreD();
            case "E":
                return subject.getScoreE();
            case "F":
                return subject.getScoreF();
            default:
                throw new Exception("选项错误");
        }
    }

//    public static void main(String[] args){
//        System.out.println(JSON.toJSONString(new String[]{"A","B","C"}));
//    }
}
