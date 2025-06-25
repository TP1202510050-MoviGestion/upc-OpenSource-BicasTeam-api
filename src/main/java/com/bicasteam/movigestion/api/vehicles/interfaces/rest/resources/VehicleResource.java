package com.bicasteam.movigestion.api.vehicles.interfaces.rest.resources;

import java.time.LocalDateTime;

public record VehicleResource(
        /*── Identificación ─*/
        int id,
        String licensePlate,
        String brand,
        String model,
        int year,
        String color,
        int seatingCapacity,
        LocalDateTime lastTechnicalInspectionDate,
        /*── Sensores ─*/
        String gpsSensorId,
        String speedSensorId,
        /*── Estado y asignación ─*/
        String status,
        String driverName,
        Integer assignedDriverId,
        LocalDateTime assignedAt,
        /*── Documentos e imágenes ─*/
        String vehicleImage,
        String documentSoat,
        String documentVehicleOwnershipCard,
        LocalDateTime dateToGoTheWorkshop,
        /*── Empresa ─*/
        String companyName,
        String companyRuc,
        /*── Telemetría ─*/
        Double lastTemperature,
        Double lastHumidity,
        Double lastLatitude,
        Double lastLongitude,
        Double lastAltitudeMeters,
        Double lastKmh,
        LocalDateTime lastTelemetryTimestamp
) {}
