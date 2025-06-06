package com.bicasteam.movigestion.api.reports.application.internal;

import com.bicasteam.movigestion.api.reports.domain.model.aggregates.Report;
import com.bicasteam.movigestion.api.reports.domain.model.commands.CreateReportCommand;
import com.bicasteam.movigestion.api.reports.domain.repositories.ReportRepository;
import com.bicasteam.movigestion.api.reports.domain.services.ReportCommandService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportCommandServiceImpl implements ReportCommandService {

    private final ReportRepository reportRepository;

    public ReportCommandServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    @Transactional
    public Optional<Report> handle(CreateReportCommand command) {
        Report report = new Report(command);
        try {
            reportRepository.save(report);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(report);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        reportRepository.deleteById(id);
    }
}
