package com.example.profilemanagement.mapper;


import com.example.profilemanagement.dto.ReportDto;
import com.example.profilemanagement.model.Report;
import com.example.profilemanagement.response.ReportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {


    @Mapping(target = "firstName",source = "user.firstName")
    @Mapping(target = "lastName",source = "user.lastName")
    @Mapping(target = "email",source = "user.email")
    ReportResponse reportToReportResponse(Report report);

    List<ReportResponse> reportListToReportResponseList(List<Report> report);


    @Mapping(target = "isArchived",ignore = true)
    @Mapping(target = "user",ignore = true)
    Report reportDtoToReport(ReportDto reportDto);

}
