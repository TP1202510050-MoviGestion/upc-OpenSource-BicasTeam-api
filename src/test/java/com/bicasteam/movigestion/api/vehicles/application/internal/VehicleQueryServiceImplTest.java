// Archivo: src/test/java/com/bicasteam/movigestion/api/vehicles/application/internal/VehicleQueryServiceImplTest.java

package com.bicasteam.movigestion.api.vehicles.application.internal;

import com.bicasteam.movigestion.api.vehicles.domain.model.aggregates.Vehicle;
import com.bicasteam.movigestion.api.vehicles.domain.model.queries.GetAllVehiclesQuery;
import com.bicasteam.movigestion.api.vehicles.domain.model.queries.GetVehicleByIdQuery;
import com.bicasteam.movigestion.api.vehicles.domain.model.queries.GetVehicleByUserIdQuery;
import com.bicasteam.movigestion.api.vehicles.domain.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class VehicleQueryServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleQueryServiceImpl vehicleQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleGetAllVehiclesQuery() {
        // Arrange
        when(vehicleRepository.findAll()).thenReturn(List.of(new Vehicle(), new Vehicle()));

        // Act
        List<Vehicle> result = vehicleQueryService.handle(new GetAllVehiclesQuery());

        // Assert
        assertEquals(2, result.size());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void testHandleGetVehicleByIdQuery_Found() {
        // Arrange
        int vehicleId = 1;
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(new Vehicle()));

        // Act
        Optional<Vehicle> result = vehicleQueryService.handle(new GetVehicleByIdQuery(vehicleId));

        // Assert
        assertTrue(result.isPresent());
        verify(vehicleRepository, times(1)).findById(vehicleId);
    }

    @Test
    void testHandleGetVehicleByUserIdQuery() {
        // Arrange
        int userId = 101;
        when(vehicleRepository.findByAssignedDriverId(userId)).thenReturn(List.of(new Vehicle()));

        // Act
        List<Vehicle> result = vehicleQueryService.handle(new GetVehicleByUserIdQuery(userId));

        // Assert
        assertEquals(1, result.size());
        verify(vehicleRepository, times(1)).findByAssignedDriverId(userId);
    }
}