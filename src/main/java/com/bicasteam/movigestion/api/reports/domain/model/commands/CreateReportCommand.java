package com.bicasteam.movigestion.api.reports.domain.model.commands;

public record CreateReportCommand(
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
