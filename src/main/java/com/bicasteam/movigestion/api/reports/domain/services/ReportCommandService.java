package com.bicasteam.movigestion.api.reports.domain.services;

import com.bicasteam.movigestion.api.reports.domain.model.aggregates.Report;
import com.bicasteam.movigestion.api.reports.domain.model.commands.CreateReportCommand;

import java.util.Optional;

public interface ReportCommandService {
    Optional<Report> handle(CreateReportCommand command);
    void deleteById(int id); // Nuevo método
}
