// Archivo: src/test/java/com/bicasteam/movigestion/api/vehicles/application/internal/VehicleCommandServiceImplTest.java

package com.bicasteam.movigestion.api.vehicles.application.internal;

import com.bicasteam.movigestion.api.vehicles.domain.model.aggregates.Vehicle;
import com.bicasteam.movigestion.api.vehicles.domain.model.commands.CreateVehicleCommand;
import com.bicasteam.movigestion.api.vehicles.domain.repositories.VehicleRepository;
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

class VehicleCommandServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleCommandServiceImpl vehicleCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleCreateVehicleCommand_Success() {
        // 1. PREPARACIÓN (Arrange)
        // Creamos un comando de ejemplo con datos válidos.
        CreateVehicleCommand command = new CreateVehicleCommand(
                "ABC-123", "Volvo", "FH16", 2022, "Rojo", 2, LocalDateTime.now(),
                "gps1", "spd1", "Activo", "Juan Perez", 101, LocalDateTime.now(),
                "img_base64", "soat_base64", "card_base64", null,
                "Empresa XYZ", "12345678901"
        );

        // Configuramos el mock: cuando se guarde, devolverá el objeto vehículo.
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. EJECUCIÓN (Act)
        // Llamamos al método a probar.
        Optional<Vehicle> result = vehicleCommandService.handle(command);

        // 3. VERIFICACIÓN (Assert)
        // Verificamos que la operación fue exitosa.
        assertTrue(result.isPresent(), "El resultado no debería ser vacío.");

        // Verificamos que los datos se asignaron correctamente.
        Vehicle createdVehicle = result.get();
        assertEquals(command.licensePlate(), createdVehicle.getLicensePlate());
        assertEquals(command.brand(), createdVehicle.getBrand());

        // Verificamos que el método 'save' del repositorio fue invocado.
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void testHandleCreateVehicleCommand_RepositoryFailure() {
        // 1. PREPARACIÓN (Arrange)
        CreateVehicleCommand command = new CreateVehicleCommand(
                "XYZ-789", "Scania", "R450", 2023, "Azul", 2, LocalDateTime.now(),
                "gps2", "spd2", "Activo", "Maria Gomez", 102, LocalDateTime.now(),
                "img_base64", "soat_base64", "card_base64", null,
                "Empresa ABC", "09876543210"
        );

        // Simulamos un fallo en la base de datos al intentar guardar.
        when(vehicleRepository.save(any(Vehicle.class))).thenThrow(new RuntimeException("Error de base de datos"));

        // 2. EJECUCIÓN (Act)
        Optional<Vehicle> result = vehicleCommandService.handle(command);

        // 3. VERIFICACIÓN (Assert)
        // Verificamos que el método manejó la excepción y devolvió un Optional vacío.
        assertFalse(result.isPresent(), "El resultado debería ser vacío cuando el guardado falla.");

        // Verificamos que aun así se intentó guardar.
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }
}