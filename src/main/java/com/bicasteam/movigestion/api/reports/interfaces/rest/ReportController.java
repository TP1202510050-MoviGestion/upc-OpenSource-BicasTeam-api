package com.bicasteam.movigestion.api.reports.interfaces.rest;

import com.bicasteam.movigestion.api.reports.domain.model.aggregates.Report;
import com.bicasteam.movigestion.api.reports.domain.model.commands.CreateReportCommand;
import com.bicasteam.movigestion.api.reports.domain.model.queries.GetAllReportsQuery;
import com.bicasteam.movigestion.api.reports.domain.model.queries.GetReportByIdQuery;
import com.bicasteam.movigestion.api.reports.domain.services.ReportCommandService;
import com.bicasteam.movigestion.api.reports.domain.services.ReportQueryService;
import com.bicasteam.movigestion.api.reports.interfaces.rest.resources.CreateReportResource;
import com.bicasteam.movigestion.api.reports.interfaces.rest.resources.ReportResource;
import com.bicasteam.movigestion.api.reports.interfaces.rest.transform.CreateReportCommandFromResourceAssembler;
import com.bicasteam.movigestion.api.reports.interfaces.rest.transform.ReportResourceFromEntityAssembler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportCommandService reportCommandService;
    private final ReportQueryService reportQueryService;

    public ReportController(ReportCommandService reportCommandService, ReportQueryService reportQueryService) {
        this.reportCommandService = reportCommandService;
        this.reportQueryService = reportQueryService;
    }

    @PostMapping
    public ResponseEntity<ReportResource> createReport(@RequestBody CreateReportResource resource) {
        CreateReportCommand command = CreateReportCommandFromResourceAssembler.toCommandFromResource(resource);
        Optional<Report> result = reportCommandService.handle(command);
        return result.map(report -> ResponseEntity.ok(new ReportResource(
                        report.getId(),
                        report.getType(),
                        report.getDescription(),
                        report.getUserId(),
                        report.getCreatedAt(),
                        report.getDriverName()
                )))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResource> getReportById(@PathVariable int id) {
        Optional<Report> result = reportQueryService.handle(new GetReportByIdQuery(id));
        return result.map(report -> ResponseEntity.ok(ReportResourceFromEntityAssembler.toResourceFromEntity(report)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<ReportResource>> getAllShipments() {
        List<Report> report = reportQueryService.handle(new GetAllReportsQuery());
        List<ReportResource> resources = report.stream()
                .map(ReportResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable int id) {
        reportCommandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
