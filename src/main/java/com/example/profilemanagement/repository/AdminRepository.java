package com.example.profilemanagement.repository;

import com.example.profilemanagement.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
