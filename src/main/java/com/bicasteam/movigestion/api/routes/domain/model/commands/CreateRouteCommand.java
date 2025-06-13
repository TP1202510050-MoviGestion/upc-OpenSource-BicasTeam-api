// domain/model/commands/CreateRouteCommand.java
package com.bicasteam.movigestion.api.routes.domain.model.commands;

import java.time.LocalDateTime;

public record CreateRouteCommand(
        String type,
        String customer,
        String nameRoute,
        String status,
        String shift,
        Integer driverId,
        String driverName,
        Integer vehicleId,
        String vehiclePlate,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        String waypoints,           // JSON
        Double lastLatitude,
        Double lastLongitude,
        String companyName,
        String companyRuc
) {}
