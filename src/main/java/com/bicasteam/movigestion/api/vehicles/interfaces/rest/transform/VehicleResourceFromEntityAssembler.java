package com.bicasteam.movigestion.api.vehicles.interfaces.rest.transform;

import com.bicasteam.movigestion.api.vehicles.domain.model.aggregates.Vehicle;
import com.bicasteam.movigestion.api.vehicles.interfaces.rest.resources.VehicleResource;

public class VehicleResourceFromEntityAssembler {

    public static VehicleResource toResourceFromEntity(Vehicle v) {
        return new VehicleResource(
                v.getId(),
                v.getLicensePlate(), v.getBrand(), v.getModel(), v.getYear(), v.getColor(),
                v.getSeatingCapacity(), v.getLastTechnicalInspectionDate(),
                v.getGpsSensorId(), v.getSpeedSensorId(),
                v.getStatus(), v.getDriverName(), v.getAssignedDriverId(), v.getAssignedAt(),
                v.getVehicleImage(), v.getDocumentSoat(), v.getDocumentVehicleOwnershipCard(),
                v.getDateToGoTheWorkshop(), v.getCompanyName(), v.getCompanyRuc(),
                v.getLastTemperature(), v.getLastHumidity(),
                v.getLastLatitude(), v.getLastLongitude(), v.getLastAltitudeMeters(),
                v.getLastKmh(), v.getLastTelemetryTimestamp()
        );
    }
}
