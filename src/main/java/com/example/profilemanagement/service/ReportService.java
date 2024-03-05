package com.example.profilemanagement.service;

import com.example.profilemanagement.dto.ReportDto;
import com.example.profilemanagement.response.ReportResponse;

import java.util.List;

public interface ReportService {

    void createReport(ReportDto reportDto, String email);

    void archiveReport(Long id);

    ReportResponse getReportById(Long id);

    List<ReportResponse> getAllReports();

    List<ReportResponse> getArchivedReports();

    List<ReportResponse> getNewReports();


}
