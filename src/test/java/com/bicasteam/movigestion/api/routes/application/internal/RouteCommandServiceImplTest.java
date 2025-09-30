// Archivo: src/test/java/com/bicasteam/movigestion/api/routes/application/internal/RouteCommandServiceImplTest.java

package com.bicasteam.movigestion.api.routes.application.internal;

import com.bicasteam.movigestion.api.routes.domain.model.aggregates.Route;
import com.bicasteam.movigestion.api.routes.domain.model.commands.CreateRouteCommand;
import com.bicasteam.movigestion.api.routes.domain.repositories.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RouteCommandServiceImplTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteCommandServiceImpl routeCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleCreateRouteCommand_Success() {
        // 1. PREPARACIÓN (Arrange)
        // Creamos un comando de ejemplo para una nueva ruta.
        CreateRouteCommand command = new CreateRouteCommand(
                "regular", "Cliente A", "Lima - Arequipa", "asignado",
                "Mañana", 101, "Juan Perez", 201, "ABC-123",
                LocalDateTime.now(), LocalDateTime.now().plusHours(8), "[]",
                -12.0, -77.0, "Mi Empresa", "12345678901"
        );

        // Configuramos el mock para que, al guardar, devuelva el mismo objeto que se le pasó.
        when(routeRepository.save(any(Route.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. EJECUCIÓN (Act)
        // Llamamos al método que queremos probar.
        Optional<Route> result = routeCommandService.handle(command);

        // 3. VERIFICACIÓN (Assert)
        // Verificamos que la operación fue exitosa y se devolvió una ruta.
        assertTrue(result.isPresent(), "El resultado no debería ser vacío en un caso de éxito.");

        // Verificamos que algunos datos clave se hayan asignado correctamente.
        Route createdRoute = result.get();
        assertEquals(command.customer(), createdRoute.getCustomer());
        assertEquals(command.driverName(), createdRoute.getDriverName());

        // Verificamos que el método 'save' del repositorio fue llamado exactamente 1 vez.
        verify(routeRepository, times(1)).save(any(Route.class));
    }

    @Test
    void testHandleCreateRouteCommand_RepositoryFailure() {
        // 1. PREPARACIÓN (Arrange)
        CreateRouteCommand command = new CreateRouteCommand(
                "express", "Cliente B", "Cusco - Puno", "asignado",
                "Tarde", 102, "Maria Gomez", 202, "XYZ-789",
                LocalDateTime.now(), LocalDateTime.now().plusHours(5), "[]",
                -13.5, -71.9, "Mi Empresa", "12345678901"
        );

        // Configuramos el mock para simular un fallo en la base de datos.
        when(routeRepository.save(any(Route.class))).thenThrow(new RuntimeException("Error de conexión"));

        // 2. EJECUCIÓN (Act)
        Optional<Route> result = routeCommandService.handle(command);

        // 3. VERIFICACIÓN (Assert)
        // Verificamos que, debido al fallo, el método devolvió un Optional vacío.
        assertFalse(result.isPresent(), "El resultado debería ser vacío cuando el repositorio falla.");

        // Verificamos que, a pesar del error, se intentó llamar al método 'save'.
        verify(routeRepository, times(1)).save(any(Route.class));
    }
}