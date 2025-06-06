package com.bicasteam.movigestion.api.reports.domain.model.commands;

public record CreateReportCommand(String type, String description, String driverName) { }
