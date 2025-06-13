// interfaces/rest/transform/RouteResourceFromEntityAssembler.java
package com.bicasteam.movigestion.api.routes.interfaces.rest.transform;

import com.bicasteam.movigestion.api.routes.domain.model.aggregates.Route;
import com.bicasteam.movigestion.api.routes.interfaces.rest.resources.RouteResource;

public class RouteResourceFromEntityAssembler {
    public static RouteResource toResource(Route r){
        return new RouteResource(
                r.getId(), r.getType(), r.getCustomer(), r.getNameRoute(),
                r.getStatus(), r.getShift(),
                r.getDriverId(), r.getDriverName(),
                r.getVehicleId(), r.getVehiclePlate(),
                r.getDepartureTime(), r.getArrivalTime(),
                r.getWaypoints(), r.getLastLatitude(), r.getLastLongitude(),
                r.getCreatedAt(), r.getUpdatedAt(),
                r.getCompanyName(), r.getCompanyRuc()
        );
    }
}