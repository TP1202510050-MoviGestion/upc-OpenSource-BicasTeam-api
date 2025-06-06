package com.bicasteam.movigestion.api.vehicles.application.internal;

import com.bicasteam.movigestion.api.vehicles.domain.model.aggregates.Vehicle;
import com.bicasteam.movigestion.api.vehicles.domain.model.queries.GetAllVehiclesQuery;
import com.bicasteam.movigestion.api.vehicles.domain.model.queries.GetVehicleByIdQuery;
import com.bicasteam.movigestion.api.vehicles.domain.model.queries.GetVehicleByUserIdQuery;
import com.bicasteam.movigestion.api.vehicles.domain.repositories.VehicleRepository;
import com.bicasteam.movigestion.api.vehicles.domain.services.VehicleQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleQueryServiceImpl implements VehicleQueryService {

    private final VehicleRepository vehicleRepository;

    public VehicleQueryServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<Vehicle> handle(GetVehicleByIdQuery query) {
        return vehicleRepository.findById(query.id());
    }

    @Override
    public List<Vehicle> handle(GetAllVehiclesQuery query) {
        return vehicleRepository.findAll();
    }

    @Override
    public List<Vehicle> handle(GetVehicleByUserIdQuery query) {
        return vehicleRepository.findByIdUser(query.userId());
    }
}
