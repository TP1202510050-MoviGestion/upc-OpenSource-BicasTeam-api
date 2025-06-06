package com.bicasteam.movigestion.api.shipments.interfaces.rest.transform;

import com.bicasteam.movigestion.api.shipments.domain.model.aggregates.Shipment;
import com.bicasteam.movigestion.api.shipments.interfaces.rest.resources.ShipmentResource;

public class ShipmentResourceFromEntityAssembler {
    public static ShipmentResource toResourceFromEntity(Shipment entity) {
        return new ShipmentResource(entity.getId(), entity.getUserId(), entity.getDestiny(),
                entity.getDescription(), entity.getCreatedAt(), entity.getStatus(), entity.getDriverName());
    }
}

