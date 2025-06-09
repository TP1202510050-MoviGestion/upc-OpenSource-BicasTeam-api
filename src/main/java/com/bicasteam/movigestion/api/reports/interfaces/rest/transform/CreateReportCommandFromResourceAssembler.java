package com.bicasteam.movigestion.api.reports.interfaces.rest.transform;

import com.bicasteam.movigestion.api.reports.domain.model.commands.CreateReportCommand;
import com.bicasteam.movigestion.api.reports.interfaces.rest.resources.CreateReportResource;

public class CreateReportCommandFromResourceAssembler {
    public static CreateReportCommand toCommandFromResource(CreateReportResource r) {
        return new CreateReportCommand(
                r.userId(),
                r.type(),
                r.description(),
                r.driverName(),
                r.photoOrVideo(),
                r.status(),
                r.location(),
                r.vehiclePlate(),
                r.companyName(),
                r.companyRuc()
        );
    }
}
