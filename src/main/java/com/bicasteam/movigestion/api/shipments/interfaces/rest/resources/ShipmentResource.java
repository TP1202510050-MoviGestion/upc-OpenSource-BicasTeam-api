package com.bicasteam.movigestion.api.shipments.interfaces.rest.resources;

import java.time.LocalDateTime;

public record ShipmentResource(int id, int userId, String destiny, String description, LocalDateTime createdAt, String status, String driverName) {
}
