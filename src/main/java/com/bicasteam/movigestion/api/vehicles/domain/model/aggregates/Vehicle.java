package com.bicasteam.movigestion.api.vehicles.domain.model.aggregates;

import com.bicasteam.movigestion.api.vehicles.domain.model.commands.CreateVehicleCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Vehicle {

    /*────────────  Identidad  ────────────*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /*────────────  Datos básicos  ────────────*/
    @Column(nullable = false, unique = true)
    private String licensePlate;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    private int year;
    private String color;
    private int seatingCapacity;
    private LocalDateTime lastTechnicalInspectionDate;

    /*────────────  Sensores  ────────────*/
    private String gpsSensorId;
    private String speedSensorId;

    /*────────────  Estado y asignación  ────────────*/
    private String status;
    private String driverName;
    private Integer assignedDriverId;
    private LocalDateTime assignedAt;
    private String companyName;
    private String companyRuc;

    /*────────────  Documentos e imágenes  ────────────*/
    @Lob @Column(columnDefinition = "LONGTEXT")
    private String vehicleImage;
    @Lob @Column(columnDefinition = "LONGTEXT")
    private String documentSoat;
    @Lob @Column(columnDefinition = "LONGTEXT")
    private String documentVehicleOwnershipCard;
    private LocalDateTime dateToGoTheWorkshop;

    /*────────────  Telemetría – NUEVOS CAMPOS  ────────────*/
    private Double  lastTemperature;
    private Double  lastHumidity;

    private Double  lastLatitude;
    private Double  lastLongitude;
    private Double  lastAltitudeMeters;
    private LocalDateTime lastTelemetryTimestamp;   // timestamp_utc

    private Double  lastKmh;

    /*────────────  Constructores  ────────────*/
    public Vehicle(CreateVehicleCommand cmd) {
        this.licensePlate   = cmd.licensePlate();
        this.brand          = cmd.brand();
        this.model          = cmd.model();
        this.year           = cmd.year();
        this.color          = cmd.color();
        this.seatingCapacity= cmd.seatingCapacity();
        this.lastTechnicalInspectionDate = cmd.lastTechnicalInspectionDate();
        this.gpsSensorId    = cmd.gpsSensorId();
        this.speedSensorId  = cmd.speedSensorId();
        this.status         = cmd.status();
        this.driverName     = cmd.driverName();
        this.companyName    = cmd.companyName();
        this.companyRuc     = cmd.companyRuc();
        this.assignedDriverId = cmd.assignedDriverId();
        this.assignedAt     = cmd.assignedAt();
        this.vehicleImage   = cmd.vehicleImage();
        this.documentSoat   = cmd.documentSoat();
        this.documentVehicleOwnershipCard = cmd.documentVehicleOwnershipCard();
        this.dateToGoTheWorkshop = cmd.dateToGoTheWorkshop();

        /* Sin telemetría al crear */
        this.lastTemperature = null;
        this.lastHumidity    = null;
        this.lastLatitude    = null;
        this.lastLongitude   = null;
        this.lastAltitudeMeters = null;
        this.lastTelemetryTimestamp = null;
        this.lastKmh         = null;
    }

    /*────────────  Setters de actualización  ────────────*/
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public void setBrand(String brand)               { this.brand = brand; }
    public void setModel(String model)               { this.model = model; }
    public void setYear(int year)                    { this.year = year; }
    public void setColor(String color)               { this.color = color; }
    public void setSeatingCapacity(int seatingCapacity){ this.seatingCapacity = seatingCapacity; }
    public void setLastTechnicalInspectionDate(LocalDateTime d) { this.lastTechnicalInspectionDate = d; }
    public void setGpsSensorId(String gps)           { this.gpsSensorId = gps; }
    public void setSpeedSensorId(String spd)         { this.speedSensorId = spd; }
    public void setStatus(String status)             { this.status = status; }
    public void setDriverName(String dn)             { this.driverName = dn; }
    public void setAssignedDriverId(Integer id)      { this.assignedDriverId = id; }
    public void setAssignedAt(LocalDateTime at)      { this.assignedAt = at; }
    public void setVehicleImage(String img)          { this.vehicleImage = img; }
    public void setDocumentSoat(String soat)         { this.documentSoat = soat; }
    public void setDocumentVehicleOwnershipCard(String doc) { this.documentVehicleOwnershipCard = doc; }
    public void setDateToGoTheWorkshop(LocalDateTime d) { this.dateToGoTheWorkshop = d; }
    public void setCompanyName(String c)             { this.companyName = c; }
    public void setCompanyRuc(String r)              { this.companyRuc = r; }

    /*── Telemetría ─*/
    public void setLastTemperature(Double t)         { this.lastTemperature = t; }
    public void setLastHumidity(Double h)            { this.lastHumidity = h; }
    public void setLastLatitude(Double lat)          { this.lastLatitude = lat; }
    public void setLastLongitude(Double lon)         { this.lastLongitude = lon; }
    public void setLastAltitudeMeters(Double alt)    { this.lastAltitudeMeters = alt; }
    public void setLastTelemetryTimestamp(LocalDateTime ts){ this.lastTelemetryTimestamp = ts; }
    public void setLastKmh(Double kmh)               { this.lastKmh = kmh; }
}
