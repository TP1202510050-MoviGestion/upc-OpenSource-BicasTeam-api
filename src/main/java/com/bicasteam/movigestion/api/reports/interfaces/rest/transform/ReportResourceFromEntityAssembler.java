package com.bicasteam.movigestion.api.reports.interfaces.rest.transform;

import com.bicasteam.movigestion.api.reports.domain.model.aggregates.Report;
import com.bicasteam.movigestion.api.reports.interfaces.rest.resources.ReportResource;

public class ReportResourceFromEntityAssembler {
    public static ReportResource toResourceFromEntity(Report e) {
        return new ReportResource(
                e.getId(),
                e.getUserId(),
                e.getType(),
                e.getDescription(),
                e.getDriverName(),
                e.getCreatedAt(),
                e.getPhotoOrVideo(),
                e.getStatus(),
                e.getLocation(),
                e.getVehiclePlate(),
                e.getCompanyName(),
                e.getCompanyRuc()
        );
    }
}
