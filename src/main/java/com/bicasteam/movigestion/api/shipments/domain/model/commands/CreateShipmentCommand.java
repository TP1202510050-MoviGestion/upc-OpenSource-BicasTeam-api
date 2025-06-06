package com.bicasteam.movigestion.api.shipments.domain.model.commands;



public record CreateShipmentCommand(String destiny, String description, String driverName, String status, Integer idUserDestiny) { }
