package com.bicasteam.movigestion.api.vehicles.interfaces.rest.transform;
import com.bicasteam.movigestion.api.vehicles.domain.model.commands.CreateVehicleCommand;
import com.bicasteam.movigestion.api.vehicles.interfaces.rest.resources.CreateVehicleResource;

public class CreateVehicleCommandFromResourceAssembler {
    public static CreateVehicleCommand toCommandFromResource(CreateVehicleResource r) {
        return new CreateVehicleCommand(
                r.licensePlate(), r.brand(), r.model(), r.year(), r.color(),
                r.seatingCapacity(), r.lastTechnicalInspectionDate(),
                r.gpsSensorId(), r.speedSensorId(),
                r.status(), r.driverName(), r.assignedDriverId(), r.assignedAt(),
                r.vehicleImage(), r.documentSoat(), r.documentVehicleOwnershipCard(),
                r.dateToGoTheWorkshop(),r.companyName(), r.companyRuc()
        );
    }
}