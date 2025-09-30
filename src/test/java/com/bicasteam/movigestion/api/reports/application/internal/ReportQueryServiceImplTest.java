// Archivo: src/test/java/com/bicasteam/movigestion/api/reports/application/internal/ReportQueryServiceImplTest.java

package com.bicasteam.movigestion.api.reports.application.internal;

import com.bicasteam.movigestion.api.reports.domain.model.aggregates.Report;
import com.bicasteam.movigestion.api.reports.domain.model.queries.GetAllReportsQuery;
import com.bicasteam.movigestion.api.reports.domain.model.queries.GetReportByIdQuery;
import com.bicasteam.movigestion.api.reports.domain.repositories.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReportQueryServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportQueryServiceImpl reportQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleGetAllReportsQuery() {
        // 1. PREPARACIÓN (Arrange)
        // Creamos una lista falsa de reportes que el repositorio debería devolver.
        List<Report> mockReports = List.of(new Report(), new Report());

        // Configuramos el mock: "Cuando se llame a 'findAll', devuelve nuestra lista falsa."
        when(reportRepository.findAll()).thenReturn(mockReports);

        // 2. EJECUCIÓN (Act)
        List<Report> result = reportQueryService.handle(new GetAllReportsQuery());

        // 3. VERIFICACIÓN (Assert)
        // Verificamos que el resultado tenga el mismo tamaño que nuestra lista falsa.
        assertEquals(2, result.size());

        // Verificamos que el método 'findAll' del repositorio fue llamado exactamente 1 vez.
        verify(reportRepository, times(1)).findAll();
    }

    @Test
    void testHandleGetReportByIdQuery_Found() {
        // 1. PREPARACIÓN (Arrange)
        int reportId = 1;
        Report mockReport = new Report(); // Un reporte vacío es suficiente para la prueba

        // Configuramos el mock: "Cuando se llame a 'findById' con el ID 1, devuelve nuestro reporte falso."
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(mockReport));

        // 2. EJECUCIÓN (Act)
        Optional<Report> result = reportQueryService.handle(new GetReportByIdQuery(reportId));

        // 3. VERIFICACIÓN (Assert)
        // Verificamos que el resultado contiene un reporte.
        assertEquals(true, result.isPresent());

        // Verificamos que se llamó a 'findById' con el ID correcto.
        verify(reportRepository, times(1)).findById(reportId);
    }

    @Test
    void testHandleGetReportByIdQuery_NotFound() {
        // 1. PREPARACIÓN (Arrange)
        int reportId = 99; // Un ID que no existe

        // Configuramos el mock: "Cuando se llame a 'findById' con el ID 99, devuelve un Optional vacío."
        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        // 2. EJECUCIÓN (Act)
        Optional<Report> result = reportQueryService.handle(new GetReportByIdQuery(reportId));

        // 3. VERIFICACIÓN (Assert)
        // Verificamos que el resultado es un Optional vacío.
        assertEquals(false, result.isPresent());

        // Verificamos que se intentó buscar.
        verify(reportRepository, times(1)).findById(reportId);
    }
}