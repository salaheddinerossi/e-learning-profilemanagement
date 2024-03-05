package com.example.profilemanagement.serviceImpl;

import com.example.profilemanagement.dto.ReportDto;
import com.example.profilemanagement.exception.ReportNotFoundException;
import com.example.profilemanagement.exception.UserNotFoundException;
import com.example.profilemanagement.model.Report;
import com.example.profilemanagement.model.User;
import com.example.profilemanagement.repository.ReportRepository;
import com.example.profilemanagement.repository.UserRepository;
import com.example.profilemanagement.response.ReportResponse;
import com.example.profilemanagement.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReportServiceImpl implements ReportService {

    final
    UserRepository userRepository;

    final
    ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createReport(ReportDto reportDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                UserNotFoundException::new
        );

        Report report = new Report();

        report.setTitle(reportDto.getTitle());
        report.setText(reportDto.getText());
        report.setUser(user);
        report.setReportDate(LocalDateTime.now());
        report.setIsArchived(false);

        reportRepository.save(report);
    }

    @Override
    public void archiveReport(Long id) {
        Report report = getReportRepository(id);
        report.setIsArchived(true);

        reportRepository.save(report);
    }

    @Override
    public ReportResponse getReportById(Long id) {
        return setReportResponse(getReportRepository(id));
    }

    @Override
    public List<ReportResponse> getAllReports() {

        List<Report> reports = reportRepository.findAll();
        List<ReportResponse> reportResponses = new ArrayList<>();

        for (Report report:reports){
            reportResponses.add(setReportResponse(report));
        }

        return reportResponses;
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
                ReportNotFoundException::new
        );
    }

    ReportResponse setReportResponse(Report report){
        ReportResponse reportResponse = new ReportResponse();

        reportResponse.setId(report.getId());
        reportResponse.setTitle(report.getTitle());
        reportResponse.setText(report.getText());
        reportResponse.setReportDate(report.getReportDate());
        reportResponse.setIsArchived(report.getIsArchived());
        reportResponse.setFirstName(report.getUser().getFirstName());
        reportResponse.setLastName(report.getUser().getLastName());
        reportResponse.setEmail(report.getUser().getEmail());

        return reportResponse;
    }

    public List<ReportResponse> getReportResponsesByState(boolean state){
        List<ReportResponse> reportResponses = new ArrayList<>();

        List<Report> reports = reportRepository.findAll();

        for (Report report:reports){
            if (report.getIsArchived()==state){
                reportResponses.add(setReportResponse(report));
            }
        }
        return reportResponses;
    }
}
