package com.bicasteam.movigestion.api.shipments.domain.model.aggregates;

import com.bicasteam.movigestion.api.shipments.domain.model.commands.CreateShipmentCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;
    private String destiny;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime dateTime; // Campo dateTime
    private String status;
    private String driverName;  // Atributo nuevo

    // Agregar el campo idUserDestiny si falta
    private Integer idUserDestiny;  // Este es el campo que puede estar causando el problema

    public Shipment(CreateShipmentCommand command) {
        this.userId = 0; // Asignar valor por defecto o lógica de asignación.
        this.destiny = command.destiny();
        this.description = command.description();
        this.createdAt = LocalDateTime.now();
        this.dateTime = LocalDateTime.now(); // Asignar valor a dateTime
        this.status = command.status();
        this.driverName = command.driverName();
        this.idUserDestiny = command.idUserDestiny(); // Asegurarse de asignar este valor
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
