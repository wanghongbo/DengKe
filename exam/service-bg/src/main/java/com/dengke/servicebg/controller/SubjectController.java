package com.dengke.servicebg.controller;


import com.alibaba.fastjson.JSON;
import com.dengke.entity.Report;
import com.dengke.entity.ReportDetail;
import com.dengke.entity.Subject;
import com.dengke.entity.SubjectType;
import com.dengke.entity.common.*;
import com.dengke.servicebg.service.ReportService;
import com.dengke.servicebg.service.SubjectService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @RequestMapping("/getPage")
    public String getPage(HttpServletRequest request){
        try {
            String type = request.getParameter("type");
            int pageNo = ServletRequestUtils.getIntParameter(request,"pageNo",1);
            int pageSize = ServletRequestUtils.getIntParameter(request,"pageSize",10);
            if(Utils.existsBlank(type)){
                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"参数不能为空","");
            }
            Page.PagedData<Subject> subjects = subjectService.getSubjects(type,Constants.SUBJECT_STATUS_OK,pageNo,pageSize);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"",subjects);
        }catch (Exception e){
            log.error("查询题目出错",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"","");
        }
    }

    @RequestMapping("/add")
    public String add(@RequestBody Subject subject){
        try {
            log.info(JSON.toJSONString(subject));
            subjectService.addSubject(subject);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"","");
        }catch (Exception e){
            log.error("新增题目出错",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"","");
        }
    }

    @RequestMapping("/delete")
    public String delete(HttpServletRequest request){
        try {
            long id = ServletRequestUtils.getLongParameter(request,"id");
            subjectService.deleteSubject(id);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"","");
        }catch (Exception e){
            log.error("删除题目出错",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"","");
        }
    }

    @RequestMapping("/update")
    public String update(@RequestBody Subject subject){
        try {
            log.info(JSON.toJSONString(subject));
            subjectService.updateSubject(subject);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"","");
        }catch (Exception e){
            log.error("编辑题目出错",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"","");
        }
    }

}
