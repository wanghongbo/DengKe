package com.dengke.servicebg.controller;


import com.alibaba.fastjson.JSON;
import com.dengke.common.service.SubjectStrategyFatory;
import com.dengke.entity.Report;
import com.dengke.entity.Subject;
import com.dengke.entity.SubjectType;
import com.dengke.entity.common.*;
import com.dengke.servicebg.service.ReportService;
import com.dengke.servicebg.service.SubjectService;
import freemarker.template.Template;
import org.apache.http.client.utils.DateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/report",produces = "application/json;charset=UTF-8")
public class ReportController extends BaseController {
    @Resource
    private SubjectService subjectService;

    @Resource
    private ReportService reportService;

    @RequestMapping("/getPage")
    public String getPage(HttpServletRequest request,@RequestBody QueryParam query){
        try{
//            int pageNo = ServletRequestUtils.getIntParameter(request,"pageNo",1);
//            int pageSize = ServletRequestUtils.getIntParameter(request,"pageSize",10);
            Page.PagedData<Report> pagedData = reportService.getReports(Constants.SUBJECT_STATUS_OK,query,query.getPageNo(),query.getPageSize());
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"",pagedData);
        }catch (Exception e){
            log.error("查询报告出错",e);
            return  JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"","");
        }
    }

    @RequestMapping("delete")
    public String delete(HttpServletRequest request){
        try {
            long id = ServletRequestUtils.getLongParameter(request,"id");
            reportService.delete(id);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"","");
        }catch (Exception e){
            log.error("删除报告出错",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"","");
        }
    }

    @RequestMapping("/download")
    public void download(HttpServletRequest request ,HttpServletResponse response){
        try {
            long reportId = ServletRequestUtils.getLongParameter(request,"id");

            Report report = reportService.get(reportId);
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
}
