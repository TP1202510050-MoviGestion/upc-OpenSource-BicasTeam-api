package com.bicasteam.movigestion.api.reports.application.internal;

import com.bicasteam.movigestion.api.reports.domain.model.aggregates.Report;
import com.bicasteam.movigestion.api.reports.domain.model.queries.GetAllReportsQuery;
import com.bicasteam.movigestion.api.reports.domain.model.queries.GetReportByIdQuery;
import com.bicasteam.movigestion.api.reports.domain.model.queries.GetReportByUserIdQuery;
import com.bicasteam.movigestion.api.reports.domain.repositories.ReportRepository;
import com.bicasteam.movigestion.api.reports.domain.services.ReportQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportQueryServiceImpl implements ReportQueryService {

    private final ReportRepository reportRepository;

    public ReportQueryServiceImpl(ReportRepository reportRepository) {

        this.reportRepository = reportRepository;
    }

    @Override
    public Optional<Report> handle(GetReportByIdQuery query) {
        return reportRepository.findById(query.id());
    }

    @Override
    public List<Report> handle(GetAllReportsQuery query) {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> handle(GetReportByUserIdQuery query) {
        return reportRepository.findByUserId(query.userId());
    }
}
