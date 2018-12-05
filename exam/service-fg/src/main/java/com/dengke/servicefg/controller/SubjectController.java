package com.dengke.servicefg.controller;


import com.alibaba.fastjson.JSON;
import com.dengke.entity.Report;
import com.dengke.entity.ReportDetail;
import com.dengke.entity.Subject;
import com.dengke.entity.SubjectType;
import com.dengke.entity.common.Constants;
import com.dengke.entity.common.JsonObjectUtil;
import com.dengke.entity.common.RtnConstants;
import com.dengke.entity.common.Utils;
import com.dengke.servicefg.service.ReportService;
import com.dengke.servicefg.service.SubjectService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"题库为空","");
            }
            if(answer.length!=subjects.size()){
                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"答题数量错误","");
            }
            List<ReportDetail> reportDetails = new ArrayList<>();
            int totalScore = 0;
            for(int i=0;i<answer.length;i++){
                ReportDetail reportDetail = new ReportDetail();
                String option = answer[i].toUpperCase();
                reportDetail.setSubjectId(subjects.get(i).getId());
                reportDetail.setOption(option);
                reportDetails.add(reportDetail);
                totalScore+=getScore(subjects.get(i),option);
            }
            Report report = new Report();
            report.setExamTime(new Date());
            report.setScore(totalScore);
            report.setType(subjects.get(0).getType());
            report.setStatus(Constants.SUBJECT_STATUS_OK);
            report.setUserId((String)request.getSession().getAttribute(Constants.SESSION_USER_NAME));
            reportService.addReport(report,reportDetails);
            request.getSession().setAttribute(Constants.SESSION_REPORT,report);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"",report);
        }catch (Exception e){
            log.error("提交试卷错误",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"提交试卷出错","");
        }
    }

    @RequestMapping("/getReport")
    public ResponseEntity<FileSystemResource> getReport(HttpServletRequest request){
        try {
//            Report report = (Report)request.getSession().getAttribute(Constants.SESSION_REPORT);
//            if(report==null){
//                return null;
//            }
            int x = RandomUtils.nextInt(1,5);
            File file = new File(new ApplicationHome(this.getClass()).getSource().getParentFile().getParentFile().getPath()+"/report/"+x+".txt");
            return export(file);
        }catch (Exception e){
            log.error("获取报告出错",e);
            return null;
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

    public static void main(String[] args){
        System.out.println(JSON.toJSONString(new String[]{"A","B","C"}));
    }
}
