package com.bicasteam.movigestion.api.reports.interfaces.rest.transform;



import com.bicasteam.movigestion.api.reports.domain.model.commands.CreateReportCommand;
import com.bicasteam.movigestion.api.reports.interfaces.rest.resources.CreateReportResource;

public class CreateReportCommandFromResourceAssembler {
    public static CreateReportCommand toCommandFromResource(CreateReportResource resource) {
        return new CreateReportCommand(
                resource.type(),
                resource.description(),
                resource.driverName()
        );
    }
}


