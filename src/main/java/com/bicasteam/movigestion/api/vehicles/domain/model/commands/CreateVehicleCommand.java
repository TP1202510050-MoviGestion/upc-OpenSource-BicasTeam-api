package com.bicasteam.movigestion.api.vehicles.domain.model.commands;

import java.time.LocalDateTime;

public record CreateVehicleCommand(
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
        LocalDateTime lastTechnicalInspectionDate
) {}
