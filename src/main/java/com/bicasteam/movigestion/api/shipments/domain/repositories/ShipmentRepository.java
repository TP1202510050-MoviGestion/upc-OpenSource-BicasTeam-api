package com.bicasteam.movigestion.api.shipments.domain.repositories;

import com.bicasteam.movigestion.api.shipments.domain.model.aggregates.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
    List<Shipment> findByUserId(int userId);
}
