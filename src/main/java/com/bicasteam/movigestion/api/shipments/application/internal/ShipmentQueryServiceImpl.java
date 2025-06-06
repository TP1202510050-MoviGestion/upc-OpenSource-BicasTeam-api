package com.bicasteam.movigestion.api.shipments.application.internal;

import com.bicasteam.movigestion.api.shipments.domain.model.aggregates.Shipment;
import com.bicasteam.movigestion.api.shipments.domain.model.queries.GetAllShipmentsQuery;
import com.bicasteam.movigestion.api.shipments.domain.model.queries.GetShipmentByIdQuery;
import com.bicasteam.movigestion.api.shipments.domain.model.queries.GetShipmentByUserIdQuery;
import com.bicasteam.movigestion.api.shipments.domain.repositories.ShipmentRepository;
import com.bicasteam.movigestion.api.shipments.domain.services.ShipmentQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentQueryServiceImpl implements ShipmentQueryService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentQueryServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Optional<Shipment> handle(GetShipmentByIdQuery query) {
        return shipmentRepository.findById(query.id());
    }

    @Override
    public List<Shipment> handle(GetAllShipmentsQuery query) {
        return shipmentRepository.findAll();
    }

    @Override
    public List<Shipment> handle(GetShipmentByUserIdQuery query) {
        return shipmentRepository.findByUserId(query.userId());
    }

}

