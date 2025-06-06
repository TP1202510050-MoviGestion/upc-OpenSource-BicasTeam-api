package com.bicasteam.movigestion.api.shipments.interfaces.rest.transform;


import com.bicasteam.movigestion.api.shipments.domain.model.commands.CreateShipmentCommand;
import com.bicasteam.movigestion.api.shipments.interfaces.rest.resources.CreateShipmentResource;

public class CreateShipmentCommandFromResourceAssembler {
    public static CreateShipmentCommand toCommandFromResource(CreateShipmentResource resource) {
        return new CreateShipmentCommand(
                resource.destiny(),
                resource.description(),
                resource.driverName(),
                resource.status(),
                null // O un valor por defecto para idUserDestiny si es necesario
        );
    }
}

