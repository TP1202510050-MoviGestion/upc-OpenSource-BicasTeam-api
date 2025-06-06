package com.bicasteam.movigestion.api.shipments.domain.services;

import com.bicasteam.movigestion.api.shipments.domain.model.aggregates.Shipment;
import com.bicasteam.movigestion.api.shipments.domain.model.commands.CreateShipmentCommand;

import java.util.Optional;

public interface ShipmentCommandService {
    Optional<Shipment> handle(CreateShipmentCommand command);
    void deleteById(int id);
    void updateStatus(int id, String status);

}
