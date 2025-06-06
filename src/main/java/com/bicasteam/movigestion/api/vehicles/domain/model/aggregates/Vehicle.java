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

    @Column(nullable = false)
    private int idUser;

    @Column(nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    private String model;

    private int engine;
    private int fuel;
    private int tires;
    private int electricalSystem;
    private int transmissionTemperature;
    private String driverName;
    private String vehicleImage;
    private String color;
    private LocalDateTime lastTechnicalInspectionDate;

    private LocalDateTime createdAt;

    public Vehicle(CreateVehicleCommand command) {
        this.idUser = 1; // Asigna un valor por defecto o lógica específica si es necesario
        this.licensePlate = command.licensePlate();
        this.model = command.model();
        this.engine = command.engine();
        this.fuel = command.fuel();
        this.tires = command.tires();
        this.electricalSystem = command.electricalSystem();
        this.transmissionTemperature = command.transmissionTemperature();
        this.driverName = command.driverName();
        this.vehicleImage = command.vehicleImage();
        this.color = command.color();
        this.lastTechnicalInspectionDate = command.lastTechnicalInspectionDate();
        this.createdAt = LocalDateTime.now();
    }

    // Setters
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public void setTires(int tires) {
        this.tires = tires;
    }

    public void setElectricalSystem(int electricalSystem) {
        this.electricalSystem = electricalSystem;
    }

    public void setTransmissionTemperature(int transmissionTemperature) {
        this.transmissionTemperature = transmissionTemperature;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setVehicleImage(String vehicleImage) {
        this.vehicleImage = vehicleImage;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setLastTechnicalInspectionDate(LocalDateTime lastTechnicalInspectionDate) {
        this.lastTechnicalInspectionDate = lastTechnicalInspectionDate;
    }
}
