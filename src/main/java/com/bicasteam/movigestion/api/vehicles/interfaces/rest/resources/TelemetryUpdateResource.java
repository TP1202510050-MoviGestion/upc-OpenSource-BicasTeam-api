package com.bicasteam.movigestion.api.vehicles.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * Payload utilizado para actualizar la telemetría de un vehículo
 * (equivalente a los datos que envía la unidad IoT).
 */
public record TelemetryUpdateResource(
        Double temperature,
        Double humidity,
        Double latitude,
        Double longitude,
        Double altitudeMeters,
        Double speedKmph,
        LocalDateTime timestampUtc
) {}
