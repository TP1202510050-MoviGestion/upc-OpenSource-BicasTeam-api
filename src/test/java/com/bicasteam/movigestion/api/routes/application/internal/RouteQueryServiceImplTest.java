// Archivo: src/test/java/com/bicasteam/movigestion/api/routes/application/internal/RouteQueryServiceImplTest.java

package com.bicasteam.movigestion.api.routes.application.internal;

import com.bicasteam.movigestion.api.routes.domain.model.aggregates.Route;
import com.bicasteam.movigestion.api.routes.domain.model.queries.GetAllRoutesQuery;
import com.bicasteam.movigestion.api.routes.domain.model.queries.GetRouteByIdQuery;
import com.bicasteam.movigestion.api.routes.domain.model.queries.GetRoutesByDriverIdQuery;
import com.bicasteam.movigestion.api.routes.domain.repositories.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class RouteQueryServiceImplTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteQueryServiceImpl routeQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleGetAllRoutesQuery() {
        // 1. Arrange
        // Creamos una lista falsa de rutas.
        List<Route> mockRoutes = List.of(new Route(), new Route());
        when(routeRepository.findAll()).thenReturn(mockRoutes);

        // 2. Act
        List<Route> result = routeQueryService.handle(new GetAllRoutesQuery());

        // 3. Assert
        assertEquals(2, result.size());
        verify(routeRepository, times(1)).findAll();
    }

    @Test
    void testHandleGetRouteByIdQuery_Found() {
        // 1. Arrange
        int routeId = 1;
        Route mockRoute = new Route(); // Usamos un objeto simple, no necesitamos datos reales.
        when(routeRepository.findById(routeId)).thenReturn(Optional.of(mockRoute));

        // 2. Act
        Optional<Route> result = routeQueryService.handle(new GetRouteByIdQuery(routeId));

        // 3. Assert
        assertTrue(result.isPresent());
        verify(routeRepository, times(1)).findById(routeId);
    }

    @Test
    void testHandleGetRoutesByDriverIdQuery() {
        // 1. Arrange
        int driverId = 101;
        List<Route> mockRoutes = List.of(new Route()); // Simulamos que el conductor tiene 1 ruta.
        when(routeRepository.findByDriverId(driverId)).thenReturn(mockRoutes);

        // 2. Act
        List<Route> result = routeQueryService.handle(new GetRoutesByDriverIdQuery(driverId));

        // 3. Assert
        assertEquals(1, result.size());
        verify(routeRepository, times(1)).findByDriverId(driverId);
    }
}