// domain/services/RouteQueryService.java
package com.bicasteam.movigestion.api.routes.domain.services;

import com.bicasteam.movigestion.api.routes.domain.model.aggregates.Route;
import com.bicasteam.movigestion.api.routes.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface RouteQueryService {
    Optional<Route> handle(GetRouteByIdQuery q);
    List<Route>     handle(GetAllRoutesQuery q);
    List<Route>     handle(GetRoutesByDriverIdQuery q);
}