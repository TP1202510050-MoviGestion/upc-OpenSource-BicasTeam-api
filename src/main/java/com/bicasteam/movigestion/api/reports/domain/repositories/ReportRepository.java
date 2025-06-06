package com.bicasteam.movigestion.api.reports.domain.repositories;


import com.bicasteam.movigestion.api.reports.domain.model.aggregates.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByUserId(int userId);
}
