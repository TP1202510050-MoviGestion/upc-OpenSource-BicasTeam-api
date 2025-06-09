package com.bicasteam.movigestion.api.reports.interfaces.rest.resources;

import java.time.LocalDateTime;

public record ReportResource(
        int          id,
        int          userId,
        String       type,
        String       description,
        String       driverName,
        LocalDateTime createdAt,
        String       photoOrVideo,
        String       status,
        String       location,
        String       vehiclePlate,
        String       companyName,
        String       companyRuc
) { }
