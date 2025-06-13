// application/internal/RouteCommandServiceImpl.java
package com.bicasteam.movigestion.api.routes.application.internal;

import com.bicasteam.movigestion.api.routes.domain.model.aggregates.Route;
import com.bicasteam.movigestion.api.routes.domain.model.commands.CreateRouteCommand;
import com.bicasteam.movigestion.api.routes.domain.repositories.RouteRepository;
import com.bicasteam.movigestion.api.routes.domain.services.RouteCommandService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RouteCommandServiceImpl implements RouteCommandService {

    private final RouteRepository repo;
    public RouteCommandServiceImpl(RouteRepository r){ this.repo = r; }

    @Override
    @Transactional
    public Optional<Route> handle(CreateRouteCommand cmd) {
        Route route = new Route(cmd);
        try { repo.save(route); }
        catch (Exception e){ return Optional.empty(); }
        return Optional.of(route);
    }

    @Override @Transactional
    public void deleteById(int id){ repo.deleteById(id); }

    @Override @Transactional
    public void save(Route r){ r.touchUpdatedAt(); repo.save(r); }
}

