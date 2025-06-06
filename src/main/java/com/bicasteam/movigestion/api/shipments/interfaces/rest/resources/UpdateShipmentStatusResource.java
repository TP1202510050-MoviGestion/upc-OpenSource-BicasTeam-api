package com.bicasteam.movigestion.api.shipments.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateShipmentStatusResource(
        @NotBlank String status
) {}
