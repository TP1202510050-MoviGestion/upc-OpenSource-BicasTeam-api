// interfaces/rest/transform/CreateRouteCommandFromResourceAssembler.java
package com.bicasteam.movigestion.api.routes.interfaces.rest.transform;

import com.bicasteam.movigestion.api.routes.domain.model.commands.CreateRouteCommand;
import com.bicasteam.movigestion.api.routes.interfaces.rest.resources.CreateRouteResource;

public class CreateRouteCommandFromResourceAssembler {
    public static CreateRouteCommand toCommand(CreateRouteResource r){
        return new CreateRouteCommand(
                r.type(), r.customer(), r.nameRoute(), r.status(), r.shift(),
                r.driverId(), r.driverName(), r.vehicleId(), r.vehiclePlate(),
                r.departureTime(), r.arrivalTime(),
                r.waypoints(), r.lastLatitude(), r.lastLongitude(),
                r.companyName(), r.companyRuc()
        );
    }
}