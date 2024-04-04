package com.example.profilemanagement.serviceImpl;

import com.example.profilemanagement.dto.ReportDto;
import com.example.profilemanagement.exception.ResourceNotFoundException;
import com.example.profilemanagement.mapper.ReportMapper;
import com.example.profilemanagement.model.Report;
import com.example.profilemanagement.model.User;
import com.example.profilemanagement.repository.ReportRepository;
import com.example.profilemanagement.repository.UserRepository;
import com.example.profilemanagement.response.ReportResponse;
import com.example.profilemanagement.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ReportServiceImpl implements ReportService {

    final
    UserRepository userRepository;

    final
    ReportRepository reportRepository;

    final
    ReportMapper reportMapper;

    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.reportMapper = reportMapper;
    }

    @Override
    public ReportResponse createReport(ReportDto reportDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found with this email:" + email)
        );

        Report report = reportMapper.reportDtoToReport(reportDto);
        report.setUser(user);

        return reportMapper.reportToReportResponse(reportRepository.save(report));
    }

    @Override
    public void archiveReport(Long id) {
        Report report = getReportRepository(id);
        report.setIsArchived(true);

        reportRepository.save(report);
    }

    @Override
    public ReportResponse getReportById(Long id) {
        return reportMapper.reportToReportResponse(getReportRepository(id));
    }

    @Override
    public List<ReportResponse> getAllReports() {

        List<Report> reports = reportRepository.findAll();
        return reportMapper.reportListToReportResponseList(reports);
    }

    @Override
    public List<ReportResponse> getArchivedReports() {
        return getReportResponsesByState(true);
    }

    @Override
    public List<ReportResponse> getNewReports() {
        return getReportResponsesByState(false);
    }

    Report getReportRepository(Long id){
        return reportRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("report not found with this id: "+id)
        );
    }


    public List<ReportResponse> getReportResponsesByState(boolean state){
        List<ReportResponse> reportResponses = new ArrayList<>();

        List<Report> reports = reportRepository.findAll();

        for (Report report:reports){
            if (report.getIsArchived()==state){
                reportResponses.add(reportMapper.reportToReportResponse(report));
            }
        }
        return reportResponses;
    }
}
