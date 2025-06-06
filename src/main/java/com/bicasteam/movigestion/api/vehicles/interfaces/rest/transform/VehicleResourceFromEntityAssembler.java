package com.bicasteam.movigestion.api.vehicles.interfaces.rest.transform;

import com.bicasteam.movigestion.api.vehicles.domain.model.aggregates.Vehicle;
import com.bicasteam.movigestion.api.vehicles.interfaces.rest.resources.VehicleResource;

public class VehicleResourceFromEntityAssembler {
    public static VehicleResource toResourceFromEntity(Vehicle entity) {
        return new VehicleResource(
                entity.getId(),
                entity.getIdUser(),
                entity.getLicensePlate(),
                entity.getModel(),
                entity.getEngine(),
                entity.getFuel(),
                entity.getTires(),
                entity.getElectricalSystem(),
                entity.getTransmissionTemperature(),
                entity.getDriverName(),
                entity.getVehicleImage(),
                entity.getColor(),
                entity.getLastTechnicalInspectionDate(),
                entity.getCreatedAt()
        );
    }
}
