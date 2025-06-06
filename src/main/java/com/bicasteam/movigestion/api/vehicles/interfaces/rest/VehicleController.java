package com.bicasteam.movigestion.api.vehicles.interfaces.rest;

import com.bicasteam.movigestion.api.vehicles.domain.model.aggregates.Vehicle;
import com.bicasteam.movigestion.api.vehicles.domain.model.commands.CreateVehicleCommand;
import com.bicasteam.movigestion.api.vehicles.domain.model.queries.GetAllVehiclesQuery;
import com.bicasteam.movigestion.api.vehicles.domain.model.queries.GetVehicleByIdQuery;
import com.bicasteam.movigestion.api.vehicles.domain.services.VehicleCommandService;
import com.bicasteam.movigestion.api.vehicles.domain.services.VehicleQueryService;
import com.bicasteam.movigestion.api.vehicles.interfaces.rest.resources.CreateVehicleResource;
import com.bicasteam.movigestion.api.vehicles.interfaces.rest.resources.VehicleResource;
import com.bicasteam.movigestion.api.vehicles.interfaces.rest.transform.CreateVehicleCommandFromResourceAssembler;
import com.bicasteam.movigestion.api.vehicles.interfaces.rest.transform.VehicleResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleCommandService vehicleCommandService;
    private final VehicleQueryService vehicleQueryService;

    public VehicleController(VehicleCommandService vehicleCommandService, VehicleQueryService vehicleQueryService) {
        this.vehicleCommandService = vehicleCommandService;
        this.vehicleQueryService = vehicleQueryService;
    }

    @PostMapping
    public ResponseEntity<VehicleResource> createVehicle(@RequestBody CreateVehicleResource resource) {
        CreateVehicleCommand command = CreateVehicleCommandFromResourceAssembler.toCommandFromResource(resource);
        Optional<Vehicle> result = vehicleCommandService.handle(command);
        return result.map(vehicle -> ResponseEntity.ok(VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicle)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResource> getVehicleById(@PathVariable int id) {
        Optional<Vehicle> result = vehicleQueryService.handle(new GetVehicleByIdQuery(id));
        return result.map(vehicle -> ResponseEntity.ok(VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicle)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<VehicleResource>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleQueryService.handle(new GetAllVehiclesQuery());
        List<VehicleResource> resources = vehicles.stream()
                .map(VehicleResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable int id) {
        vehicleCommandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResource> updateVehicle(@PathVariable int id, @RequestBody CreateVehicleResource resource) {
        Optional<Vehicle> existingVehicle = vehicleQueryService.handle(new GetVehicleByIdQuery(id));
        if (existingVehicle.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Vehicle updatedVehicle = existingVehicle.get();
        updatedVehicle.setLicensePlate(resource.licensePlate());
        updatedVehicle.setModel(resource.model());
        updatedVehicle.setEngine(resource.engine());
        updatedVehicle.setFuel(resource.fuel());
        updatedVehicle.setTires(resource.tires());
        updatedVehicle.setElectricalSystem(resource.electricalSystem());
        updatedVehicle.setTransmissionTemperature(resource.transmissionTemperature());
        updatedVehicle.setDriverName(resource.driverName());
        updatedVehicle.setVehicleImage(resource.vehicleImage());
        updatedVehicle.setColor(resource.color());
        updatedVehicle.setLastTechnicalInspectionDate(resource.lastTechnicalInspectionDate());

        vehicleCommandService.save(updatedVehicle);

        return ResponseEntity.ok(VehicleResourceFromEntityAssembler.toResourceFromEntity(updatedVehicle));
    }

}
