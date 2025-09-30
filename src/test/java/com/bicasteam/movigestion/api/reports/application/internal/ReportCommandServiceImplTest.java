// Archivo: src/test/java/com/bicasteam/movigestion/api/reports/application/internal/ReportCommandServiceImplTest.java

package com.bicasteam.movigestion.api.reports.application.internal;

import com.bicasteam.movigestion.api.reports.domain.model.aggregates.Report;
import com.bicasteam.movigestion.api.reports.domain.model.commands.CreateReportCommand;
import com.bicasteam.movigestion.api.reports.domain.repositories.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReportCommandServiceImplTest {

    // @Mock crea una versión simulada del repositorio. No tocará la base de datos.
    @Mock
    private ReportRepository reportRepository;

    // @InjectMocks crea una instancia real del servicio, pero le inyecta el repositorio simulado.
    @InjectMocks
    private ReportCommandServiceImpl reportCommandService;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks antes de cada prueba.
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleCreateReportCommand_Success() {
        // 1. PREPARACIÓN (Arrange)

        // Creamos un comando de ejemplo para crear un reporte.
        CreateReportCommand command = new CreateReportCommand(1, "Accidente", "Choque leve",
                "Juan Perez", "base64image", "Pendiente", "Av. Arequipa", "ABC-123",
                "Empresa XYZ", "12345678901");

        // Configuramos el comportamiento del mock:
        // Le decimos a Mockito: "Cuando se llame al método 'save' del repositorio
        // con CUALQUIER objeto de tipo Report, simplemente devuelve ese mismo objeto."
        when(reportRepository.save(any(Report.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. EJECUCIÓN (Act)

        // Llamamos al método que queremos probar.
        Optional<Report> result = reportCommandService.handle(command);

        // 3. VERIFICACIÓN (Assert)

        // Verificamos que el resultado no sea nulo y contenga un reporte.
        assertTrue(result.isPresent(), "El resultado no debería ser vacío en un caso de éxito.");

        // Verificamos que los datos del reporte creado coincidan con los del comando.
        Report createdReport = result.get();
        assertEquals(command.type(), createdReport.getType());
        assertEquals(command.driverName(), createdReport.getDriverName());

        // La verificación más importante:
        // Confirmamos que el método 'save' del repositorio fue llamado exactamente 1 vez.
        verify(reportRepository, times(1)).save(any(Report.class));
    }

    @Test
    void testHandleCreateReportCommand_RepositoryFailure() {
        // 1. PREPARACIÓN (Arrange)
        CreateReportCommand command = new CreateReportCommand(1, "Falla", "Motor fundido",
                "Ana Torres", "base64image2", "Pendiente", "Vía Expresa", "XYZ-789",
                "Empresa ABC", "09876543210");

        // Configuramos el mock para que falle:
        // Le decimos: "Cuando se llame a 'save', lanza una excepción."
        when(reportRepository.save(any(Report.class))).thenThrow(new RuntimeException("Error de base de datos"));

        // 2. EJECUCIÓN (Act)
        Optional<Report> result = reportCommandService.handle(command);

        // 3. VERIFICACIÓN (Assert)

        // Verificamos que, como el 'save' falló, el método devolvió un Optional vacío.
        assertFalse(result.isPresent(), "El resultado debería ser vacío cuando el repositorio falla.");

        // Verificamos que se intentó llamar a 'save'
        verify(reportRepository, times(1)).save(any(Report.class));
    }
}