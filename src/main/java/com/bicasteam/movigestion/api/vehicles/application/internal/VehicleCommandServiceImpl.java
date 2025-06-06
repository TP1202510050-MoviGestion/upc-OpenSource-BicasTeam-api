package com.bicasteam.movigestion.api.vehicles.application.internal;

import com.bicasteam.movigestion.api.vehicles.domain.model.aggregates.Vehicle;
import com.bicasteam.movigestion.api.vehicles.domain.model.commands.CreateVehicleCommand;
import com.bicasteam.movigestion.api.vehicles.domain.repositories.VehicleRepository;
import com.bicasteam.movigestion.api.vehicles.domain.services.VehicleCommandService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleCommandServiceImpl implements VehicleCommandService {

    private final VehicleRepository vehicleRepository;

    public VehicleCommandServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    @Transactional
    public Optional<Vehicle> handle(CreateVehicleCommand command) {
        Vehicle vehicle = new Vehicle(command);
        try {
            vehicleRepository.save(vehicle);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(vehicle);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

}
