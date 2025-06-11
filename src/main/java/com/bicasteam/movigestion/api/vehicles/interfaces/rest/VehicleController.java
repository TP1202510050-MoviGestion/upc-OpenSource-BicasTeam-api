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

    private final VehicleCommandService commandService;
    private final VehicleQueryService   queryService;

    public VehicleController(VehicleCommandService cs, VehicleQueryService qs) {
        this.commandService = cs;
        this.queryService   = qs;
    }

    @PostMapping
    public ResponseEntity<VehicleResource> create(@RequestBody CreateVehicleResource r) {
        CreateVehicleCommand cmd = CreateVehicleCommandFromResourceAssembler.toCommandFromResource(r);
        Optional<Vehicle> saved = commandService.handle(cmd);
        return saved
                .map(v -> new ResponseEntity<>(VehicleResourceFromEntityAssembler.toResourceFromEntity(v), HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResource> getById(@PathVariable int id) {
        return queryService.handle(new GetVehicleByIdQuery(id))
                .map(v -> ResponseEntity.ok(VehicleResourceFromEntityAssembler.toResourceFromEntity(v)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<VehicleResource>> getAll() {
        List<VehicleResource> list = queryService.handle(new GetAllVehiclesQuery())
                .stream()
                .map(VehicleResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResource> update(
            @PathVariable int id,
            @RequestBody CreateVehicleResource r
    ) {
        Optional<Vehicle> opt = queryService.handle(new GetVehicleByIdQuery(id));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Vehicle v = opt.get();
        // -- actualizamos todos los campos permitidos --
        v.setLicensePlate(r.licensePlate());
        v.setBrand(r.brand());
        v.setModel(r.model());
        v.setYear(r.year());
        v.setColor(r.color());
        v.setSeatingCapacity(r.seatingCapacity());
        v.setLastTechnicalInspectionDate(r.lastTechnicalInspectionDate());
        v.setGpsSensorId(r.gpsSensorId());
        v.setSpeedSensorId(r.speedSensorId());
        v.setStatus(r.status());
        v.setDriverName(r.driverName());
        v.setCompanyName(r.companyName());
        v.setCompanyRuc(r.companyRuc());
        v.setAssignedDriverId(r.assignedDriverId());
        v.setAssignedAt(r.assignedAt());
        v.setVehicleImage(r.vehicleImage());
        v.setDocumentSoat(r.documentSoat());
        v.setDocumentVehicleOwnershipCard(r.documentVehicleOwnershipCard());
        v.setDateToGoTheWorkshop(r.dateToGoTheWorkshop());
        commandService.save(v);

        return ResponseEntity.ok(VehicleResourceFromEntityAssembler.toResourceFromEntity(v));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        commandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
