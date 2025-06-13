// application/internal/RouteQueryServiceImpl.java
package com.bicasteam.movigestion.api.routes.application.internal;

import com.bicasteam.movigestion.api.routes.domain.model.aggregates.Route;
import com.bicasteam.movigestion.api.routes.domain.model.queries.*;
import com.bicasteam.movigestion.api.routes.domain.repositories.RouteRepository;
import com.bicasteam.movigestion.api.routes.domain.services.RouteQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteQueryServiceImpl implements RouteQueryService {

    private final RouteRepository repo;
    public RouteQueryServiceImpl(RouteRepository r){ this.repo = r; }

    @Override
    public Optional<Route> handle(GetRouteByIdQuery q){ return repo.findById(q.id()); }

    @Override
    public List<Route> handle(GetAllRoutesQuery q){ return repo.findAll(); }

    @Override
    public List<Route> handle(GetRoutesByDriverIdQuery q){ return repo.findByDriverId(q.driverId()); }
}
