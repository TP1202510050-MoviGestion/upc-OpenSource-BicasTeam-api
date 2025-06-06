package com.bicasteam.movigestion.api.vehicles.domain.repositories;

import com.bicasteam.movigestion.api.vehicles.domain.model.aggregates.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findByIdUser(int idUser);
}
