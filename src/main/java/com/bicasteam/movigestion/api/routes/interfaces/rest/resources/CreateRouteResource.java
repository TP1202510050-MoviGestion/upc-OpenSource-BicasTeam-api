// interfaces/rest/resources/CreateRouteResource.java
package com.bicasteam.movigestion.api.routes.interfaces.rest.resources;

import java.time.LocalDateTime;

public record CreateRouteResource(
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
        String waypoints,         // JSON
        Double lastLatitude,
        Double lastLongitude,
        String companyName,
        String companyRuc
) {}