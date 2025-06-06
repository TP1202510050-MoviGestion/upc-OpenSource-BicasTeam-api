package com.bicasteam.movigestion.api.shipments.interfaces.rest;

import com.bicasteam.movigestion.api.shipments.domain.model.aggregates.Shipment;
import com.bicasteam.movigestion.api.shipments.domain.model.commands.CreateShipmentCommand;
import com.bicasteam.movigestion.api.shipments.domain.model.queries.GetAllShipmentsQuery;
import com.bicasteam.movigestion.api.shipments.domain.model.queries.GetShipmentByIdQuery;
import com.bicasteam.movigestion.api.shipments.domain.services.ShipmentCommandService;
import com.bicasteam.movigestion.api.shipments.domain.services.ShipmentQueryService;
import com.bicasteam.movigestion.api.shipments.interfaces.rest.resources.CreateShipmentResource;
import com.bicasteam.movigestion.api.shipments.interfaces.rest.resources.ShipmentResource;
import com.bicasteam.movigestion.api.shipments.interfaces.rest.resources.UpdateShipmentStatusResource;
import com.bicasteam.movigestion.api.shipments.interfaces.rest.transform.CreateShipmentCommandFromResourceAssembler;
import com.bicasteam.movigestion.api.shipments.interfaces.rest.transform.ShipmentResourceFromEntityAssembler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentCommandService shipmentCommandService;
    private final ShipmentQueryService shipmentQueryService;

    public ShipmentController(ShipmentCommandService shipmentCommandService, ShipmentQueryService shipmentQueryService) {
        this.shipmentCommandService = shipmentCommandService;
        this.shipmentQueryService = shipmentQueryService;
    }

    @PostMapping
    public ResponseEntity<ShipmentResource> createShipment(@RequestBody CreateShipmentResource resource) {
        CreateShipmentCommand command = CreateShipmentCommandFromResourceAssembler.toCommandFromResource(resource);
        Optional<Shipment> result = shipmentCommandService.handle(command);
        return result.map(shipment -> new ResponseEntity<>(
                        ShipmentResourceFromEntityAssembler.toResourceFromEntity(shipment), HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentResource> getShipmentById(@PathVariable int id) {
        Optional<Shipment> result = shipmentQueryService.handle(new GetShipmentByIdQuery(id));
        return result.map(shipment -> ResponseEntity.ok(ShipmentResourceFromEntityAssembler.toResourceFromEntity(shipment)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<ShipmentResource>> getAllShipments() {
        List<Shipment> shipments = shipmentQueryService.handle(new GetAllShipmentsQuery());
        List<ShipmentResource> resources = shipments.stream()
                .map(ShipmentResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable int id) {
        shipmentCommandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateShipmentStatus(
            @PathVariable int id,
            @Valid @RequestBody UpdateShipmentStatusResource resource) {
        shipmentCommandService.updateStatus(id, resource.status());
        return ResponseEntity.noContent().build();
    }

}
