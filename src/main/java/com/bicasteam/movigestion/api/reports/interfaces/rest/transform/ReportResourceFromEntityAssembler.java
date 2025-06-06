package com.bicasteam.movigestion.api.reports.interfaces.rest.transform;


import com.bicasteam.movigestion.api.reports.domain.model.aggregates.Report;
import com.bicasteam.movigestion.api.reports.interfaces.rest.resources.ReportResource;

public class ReportResourceFromEntityAssembler {
    public static ReportResource toResourceFromEntity(Report entity) {
        return new ReportResource(
                entity.getId(),
                entity.getType(),
                entity.getDescription(),
                entity.getUserId(),
                entity.getCreatedAt(),
                entity.getDriverName()
        );
    }
}
