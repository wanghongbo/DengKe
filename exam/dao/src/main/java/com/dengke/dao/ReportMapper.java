package com.dengke.dao;

import com.dengke.entity.Report;
import com.dengke.entity.ReportDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportMapper {
    void add(Report report);
    void addDetail(ReportDetail reportDetail);
    List<Report> getReports();
    List<ReportDetail> getReportDetails(@Param("reportId")long reportId);
}
