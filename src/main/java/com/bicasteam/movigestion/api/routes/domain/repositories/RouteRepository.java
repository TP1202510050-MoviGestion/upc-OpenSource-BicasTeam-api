// domain/repositories/RouteRepository.java
package com.bicasteam.movigestion.api.routes.domain.repositories;

import com.bicasteam.movigestion.api.routes.domain.model.aggregates.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Integer> {
    List<Route> findByDriverId(Integer driverId);
}
