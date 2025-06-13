// domain/services/RouteCommandService.java
package com.bicasteam.movigestion.api.routes.domain.services;

import com.bicasteam.movigestion.api.routes.domain.model.aggregates.Route;
import com.bicasteam.movigestion.api.routes.domain.model.commands.CreateRouteCommand;

import java.util.Optional;

public interface RouteCommandService {
    Optional<Route> handle(CreateRouteCommand cmd);
    void deleteById(int id);
    void save(Route route);
}


