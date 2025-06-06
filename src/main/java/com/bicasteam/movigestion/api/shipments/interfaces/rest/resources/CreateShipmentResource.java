package com.bicasteam.movigestion.api.shipments.interfaces.rest.resources;

public record CreateShipmentResource(String destiny, String description, String driverName, String status) {
}
