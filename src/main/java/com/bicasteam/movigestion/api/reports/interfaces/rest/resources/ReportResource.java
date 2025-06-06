package com.bicasteam.movigestion.api.reports.interfaces.rest.resources;


import java.time.LocalDateTime;

public record ReportResource(
        int id,
        String type,
        String description,
        int userId,
        LocalDateTime createdAt,
        String driverName
) { }