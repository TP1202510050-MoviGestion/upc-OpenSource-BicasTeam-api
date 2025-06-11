package com.bicasteam.movigestion.api.vehicles.domain.model.commands;

import java.time.LocalDateTime;

public record CreateVehicleCommand(
        String licensePlate,
        String brand,
        String model,
        int year,
        String color,
        int seatingCapacity,
        LocalDateTime lastTechnicalInspectionDate,
        String gpsSensorId,
        String speedSensorId,
        String status,
        String driverName,
        Integer assignedDriverId,
        LocalDateTime assignedAt,
        String vehicleImage,
        String documentSoat,
        String documentVehicleOwnershipCard,
        LocalDateTime dateToGoTheWorkshop,
        String companyName,
        String companyRuc

) {}
