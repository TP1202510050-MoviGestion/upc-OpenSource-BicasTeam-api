package com.bicasteam.movigestion.api.vehicles.interfaces.rest.transform;

import com.bicasteam.movigestion.api.vehicles.domain.model.commands.CreateVehicleCommand;
import com.bicasteam.movigestion.api.vehicles.interfaces.rest.resources.CreateVehicleResource;

public class CreateVehicleCommandFromResourceAssembler {
    public static CreateVehicleCommand toCommandFromResource(CreateVehicleResource resource) {
        return new CreateVehicleCommand(
                resource.licensePlate(),
                resource.model(),
                resource.engine(),
                resource.fuel(),
                resource.tires(),
                resource.electricalSystem(),
                resource.transmissionTemperature(),
                resource.driverName(),
                resource.vehicleImage(),
                resource.color(),
                resource.lastTechnicalInspectionDate()
        );
    }
}
