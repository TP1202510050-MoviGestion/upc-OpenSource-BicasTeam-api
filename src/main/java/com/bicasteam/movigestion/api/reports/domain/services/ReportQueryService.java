package com.bicasteam.movigestion.api.reports.domain.services;


import com.bicasteam.movigestion.api.reports.domain.model.aggregates.Report;
import com.bicasteam.movigestion.api.reports.domain.model.queries.GetAllReportsQuery;
import com.bicasteam.movigestion.api.reports.domain.model.queries.GetReportByIdQuery;
import com.bicasteam.movigestion.api.reports.domain.model.queries.GetReportByUserIdQuery;
import java.util.List;
import java.util.Optional;

public interface ReportQueryService {
    Optional<Report> handle(GetReportByIdQuery query);
    List<Report> handle(GetAllReportsQuery query);
    List<Report> handle(GetReportByUserIdQuery query);
}
