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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Datos básicos
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

    // Sensores
    private String gpsSensorId;
    private String speedSensorId;

    // Estado y asignación
    private String status;
    private String driverName;
    private Integer assignedDriverId;
    private LocalDateTime assignedAt;
    private String companyName;
    private String companyRuc;


    // Documentos e imágenes
    @Lob @Column(columnDefinition = "LONGTEXT")
    private String vehicleImage;
    @Lob @Column(columnDefinition = "LONGTEXT")
    private String documentSoat;
    @Lob @Column(columnDefinition = "LONGTEXT")
    private String documentVehicleOwnershipCard;
    private LocalDateTime dateToGoTheWorkshop;



    // Telemetría
    private Double lastLatitude;
    private Double lastLongitude;
    private LocalDateTime lastLocationTimestamp;

    private Double lastKmh;
    private LocalDateTime lastSpeedTimestamp;

    // Constructor desde comando
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
        this.companyName  = cmd.companyName();
        this.companyRuc   = cmd.companyRuc();
        this.assignedDriverId = cmd.assignedDriverId();
        this.assignedAt     = cmd.assignedAt();
        this.vehicleImage   = cmd.vehicleImage();
        this.documentSoat   = cmd.documentSoat();
        this.documentVehicleOwnershipCard = cmd.documentVehicleOwnershipCard();
        this.dateToGoTheWorkshop = cmd.dateToGoTheWorkshop();

        // inicialmente sin telemetría
        this.lastLatitude   = null;
        this.lastLongitude  = null;
        this.lastLocationTimestamp = null;
        this.lastKmh        = null;
        this.lastSpeedTimestamp    = null;
    }

    // ——————————————————————— Setters para actualización ———————————————————————
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public void setBrand(String brand)                 { this.brand = brand; }
    public void setModel(String model)                 { this.model = model; }
    public void setYear(int year)                      { this.year = year; }
    public void setColor(String color)                 { this.color = color; }
    public void setSeatingCapacity(int seatingCapacity){ this.seatingCapacity = seatingCapacity; }
    public void setLastTechnicalInspectionDate(LocalDateTime d) { this.lastTechnicalInspectionDate = d; }
    public void setGpsSensorId(String gps)             { this.gpsSensorId = gps; }
    public void setSpeedSensorId(String spd)           { this.speedSensorId = spd; }
    public void setStatus(String status)               { this.status = status; }
    public void setDriverName(String dn)               { this.driverName = dn; }
    public void setAssignedDriverId(Integer id)        { this.assignedDriverId = id; }
    public void setAssignedAt(LocalDateTime at)        { this.assignedAt = at; }
    public void setVehicleImage(String img)            { this.vehicleImage = img; }
    public void setDocumentSoat(String soat)           { this.documentSoat = soat; }
    public void setDocumentVehicleOwnershipCard(String doc) { this.documentVehicleOwnershipCard = doc; }
    public void setDateToGoTheWorkshop(LocalDateTime d) { this.dateToGoTheWorkshop = d; }

    public void setCompanyName(String companyName)   { this.companyName = companyName; }
    public void setCompanyRuc(String companyRuc)     { this.companyRuc = companyRuc; }


    public void setLastLatitude(Double lat)            { this.lastLatitude = lat; }
    public void setLastLongitude(Double lon)           { this.lastLongitude = lon; }
    public void setLastLocationTimestamp(LocalDateTime ts) { this.lastLocationTimestamp = ts; }
    public void setLastKmh(Double kmh)                 { this.lastKmh = kmh; }
    public void setLastSpeedTimestamp(LocalDateTime ts){ this.lastSpeedTimestamp = ts; }
}
