package com.bicasteam.movigestion.api.shipments.domain.services;


import com.bicasteam.movigestion.api.shipments.domain.model.aggregates.Shipment;
import com.bicasteam.movigestion.api.shipments.domain.model.queries.GetAllShipmentsQuery;
import com.bicasteam.movigestion.api.shipments.domain.model.queries.GetShipmentByIdQuery;
import com.bicasteam.movigestion.api.shipments.domain.model.queries.GetShipmentByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface ShipmentQueryService {
    Optional<Shipment> handle(GetShipmentByIdQuery query);
    List<Shipment> handle(GetAllShipmentsQuery query);
    List<Shipment> handle(GetShipmentByUserIdQuery query);
}
