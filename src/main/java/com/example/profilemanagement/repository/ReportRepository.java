package com.example.profilemanagement.repository;

import com.example.profilemanagement.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
}
