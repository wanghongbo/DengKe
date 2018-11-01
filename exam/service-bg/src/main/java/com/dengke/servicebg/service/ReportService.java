package com.dengke.servicebg.service;

import com.dengke.dao.ReportMapper;
import com.dengke.entity.Report;
import com.dengke.entity.ReportDetail;
import com.dengke.entity.common.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReportService {

    protected static Logger log = LoggerFactory.getLogger(ReportService.class);

    @Resource
    private ReportMapper reportMapper;

    public void addReport(Report report, List<ReportDetail> reportDetails){
        try {
            reportMapper.add(report);
            long reportId = report.getId();
            for(ReportDetail reportDetail:reportDetails){
                reportDetail.setReportId(reportId);
                reportMapper.addDetail(reportDetail);
            }
        }catch (Exception e){
            log.error("生成报告出错",e);
            throw  e;
        }
    }

    public Page.PagedData<Report> getReports(String status,int pageNo,int pageSize){
        try {
            int totalCount = reportMapper.getCount(status);
            Page.Paging paging = Page.getPaging(pageNo,pageSize,totalCount);
            List<Report> reports = reportMapper.getPage(status,paging.startIndex,paging.pagesize);
            return new Page.PagedData<Report>(paging,reports);
        }catch (Exception e){
            log.error("获取报告出错",e);
            return null;
        }
    }

    public List<ReportDetail> getReportDetails(long reportId){
        try {
            return reportMapper.getReportDetails(reportId);
        }catch (Exception e){
            log.error("获取报告详情出错",e);
            return null;
        }
    }
}
