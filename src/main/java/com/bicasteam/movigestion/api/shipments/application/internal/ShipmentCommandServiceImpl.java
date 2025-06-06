package com.bicasteam.movigestion.api.shipments.application.internal;

import com.bicasteam.movigestion.api.shipments.domain.model.aggregates.Shipment;
import com.bicasteam.movigestion.api.shipments.domain.model.commands.CreateShipmentCommand;
import com.bicasteam.movigestion.api.shipments.domain.repositories.ShipmentRepository;
import com.bicasteam.movigestion.api.shipments.domain.services.ShipmentCommandService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipmentCommandServiceImpl implements ShipmentCommandService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentCommandServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    @Transactional
    public Optional<Shipment> handle(CreateShipmentCommand command) {
        Shipment shipment = new Shipment(command);
        try {
            shipmentRepository.save(shipment);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(shipment);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        shipmentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateStatus(int id, String status) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found with id: " + id));
        shipment.setStatus(status);
        shipmentRepository.save(shipment);
    }

}
