package com.bicasteam.movigestion.api.reports.interfaces.rest.resources;

public record CreateReportResource(
        int    userId,
        String type,
        String description,
        String driverName,
        String photoOrVideo,
        String status,
        String location,
        String vehiclePlate,
        String companyName,
        String companyRuc
) { }
