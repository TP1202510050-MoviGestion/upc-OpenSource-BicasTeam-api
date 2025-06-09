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

    private final ReportCommandService commandService;
    private final ReportQueryService   queryService;

    public ReportController(ReportCommandService commandService,
                            ReportQueryService queryService) {
        this.commandService = commandService;
        this.queryService   = queryService;
    }

    @PostMapping
    public ResponseEntity<ReportResource> createReport(
            @RequestBody CreateReportResource resource) {
        CreateReportCommand cmd =
                CreateReportCommandFromResourceAssembler.toCommandFromResource(resource);

        Optional<ReportResource> saved = commandService
                .handle(cmd)
                .map(ReportResourceFromEntityAssembler::toResourceFromEntity);

        return saved
                .map(r -> ResponseEntity.status(HttpStatus.CREATED).body(r))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResource> getById(@PathVariable int id) {
        return queryService
                .handle(new GetReportByIdQuery(id))
                .map(ReportResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ReportResource>> getAll() {
        List<ReportResource> list = queryService.handle(new GetAllReportsQuery())
                .stream()
                .map(ReportResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        commandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportResource> updateReport(
            @PathVariable int id,
            @RequestBody CreateReportResource resource) {

        // 1) verificamos que exista
        Optional<Report> opt = commandService.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // 2) actualizamos los campos
        Report report = opt.get();
        report.setType(          resource.type());
        report.setDescription(   resource.description());
        report.setDriverName(    resource.driverName());
        report.setPhotoOrVideo(  resource.photoOrVideo());
        report.setStatus(        resource.status());
        report.setLocation(      resource.location());
        report.setVehiclePlate(  resource.vehiclePlate());
        report.setCompanyName(   resource.companyName());
        report.setCompanyRuc(    resource.companyRuc());
        // no tocamos createdAt ni userId

        Report updated = commandService.save(report);

        // 3) devolvemos el recurso actualizado
        ReportResource out =
                ReportResourceFromEntityAssembler.toResourceFromEntity(updated);
        return ResponseEntity.ok(out);
    }

}
