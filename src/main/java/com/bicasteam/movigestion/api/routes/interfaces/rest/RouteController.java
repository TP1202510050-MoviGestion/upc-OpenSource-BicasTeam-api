// interfaces/rest/RouteController.java
package com.bicasteam.movigestion.api.routes.interfaces.rest;

import com.bicasteam.movigestion.api.routes.domain.model.aggregates.Route;
import com.bicasteam.movigestion.api.routes.domain.model.commands.CreateRouteCommand;
import com.bicasteam.movigestion.api.routes.domain.model.queries.*;
import com.bicasteam.movigestion.api.routes.domain.services.RouteCommandService;
import com.bicasteam.movigestion.api.routes.domain.services.RouteQueryService;
import com.bicasteam.movigestion.api.routes.interfaces.rest.resources.CreateRouteResource;
import com.bicasteam.movigestion.api.routes.interfaces.rest.resources.RouteResource;
import com.bicasteam.movigestion.api.routes.interfaces.rest.transform.CreateRouteCommandFromResourceAssembler;
import com.bicasteam.movigestion.api.routes.interfaces.rest.transform.RouteResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteCommandService cmdSvc;
    private final RouteQueryService   qrySvc;

    public RouteController(RouteCommandService c, RouteQueryService q){
        this.cmdSvc = c; this.qrySvc = q;
    }

    // ---------- CREATE ----------
    @PostMapping
    public ResponseEntity<RouteResource> create(@RequestBody CreateRouteResource r){
        CreateRouteCommand cmd = CreateRouteCommandFromResourceAssembler.toCommand(r);
        Optional<Route> saved = cmdSvc.handle(cmd);
        return saved
                .map(route -> new ResponseEntity<>(RouteResourceFromEntityAssembler.toResource(route), HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    // ---------- READ ----------
    @GetMapping("/{id}")
    public ResponseEntity<RouteResource> getById(@PathVariable int id){
        return qrySvc.handle(new GetRouteByIdQuery(id))
                .map(r -> ResponseEntity.ok(RouteResourceFromEntityAssembler.toResource(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RouteResource>> getAll(){
        List<RouteResource> list = qrySvc.handle(new GetAllRoutesQuery())
                .stream().map(RouteResourceFromEntityAssembler::toResource)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/by-driver/{driverId}")
    public ResponseEntity<List<RouteResource>> getByDriver(@PathVariable int driverId){
        List<RouteResource> list = qrySvc.handle(new GetRoutesByDriverIdQuery(driverId))
                .stream().map(RouteResourceFromEntityAssembler::toResource)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // ---------- UPDATE ----------
    @PutMapping("/{id}")
    public ResponseEntity<RouteResource> update(@PathVariable int id,
                                                @RequestBody CreateRouteResource r){
        Optional<Route> opt = qrySvc.handle(new GetRouteByIdQuery(id));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Route route = opt.get();
        // ——— actualiza campos permitidos ———
        route.setType(r.type());
        route.setCustomer(r.customer());
        route.setNameRoute(r.nameRoute());
        route.setStatus(r.status());
        route.setShift(r.shift());
        route.setDriverId(r.driverId());
        route.setDriverName(r.driverName());
        route.setVehicleId(r.vehicleId());
        route.setVehiclePlate(r.vehiclePlate());
        route.setDepartureTime(r.departureTime());
        route.setArrivalTime(r.arrivalTime());
        route.setWaypoints(r.waypoints());
        route.setLastLatitude(r.lastLatitude());
        route.setLastLongitude(r.lastLongitude());
        route.setCompanyName(r.companyName());
        route.setCompanyRuc(r.companyRuc());
        route.touchUpdatedAt();

        cmdSvc.save(route);
        return ResponseEntity.ok(RouteResourceFromEntityAssembler.toResource(route));
    }

    // ---------- DELETE ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        cmdSvc.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
