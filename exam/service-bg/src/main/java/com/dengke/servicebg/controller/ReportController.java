package com.dengke.servicebg.controller;


import com.alibaba.fastjson.JSON;
import com.dengke.entity.Report;
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
import java.util.List;

@RestController
@RequestMapping(value = "/report",produces = "application/json;charset=UTF-8")
public class ReportController extends BaseController {
    @Resource
    private SubjectService subjectService;

    @Resource
    private ReportService reportService;

    @RequestMapping("/get")
    public String getReports(HttpServletRequest request){
        try{
            int pageNo = ServletRequestUtils.getIntParameter(request,"pageNo",1);
            int pageSize = ServletRequestUtils.getIntParameter(request,"pageSize",10);
            Page.PagedData<Report> pagedData = reportService.getReports(Constants.SUBJECT_STATUS_OK,pageNo,pageSize);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"",pagedData);
        }catch (Exception e){
            log.error("查询报告出错",e);
            return  JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"","");
        }
    }


}
