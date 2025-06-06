package com.bicasteam.movigestion.api.vehicles.interfaces.rest.resources;

import java.time.LocalDateTime;

public record VehicleResource(
        int id,
        int userId,
        String licensePlate,
        String model,
        int engine,
        int fuel,
        int tires,
        int electricalSystem,
        int transmissionTemperature,
        String driverName,
        String vehicleImage,
        String color,
        LocalDateTime lastTechnicalInspectionDate,
        LocalDateTime createdAt
) {}
