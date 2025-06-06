package com.bicasteam.movigestion.api.reports.domain.model.aggregates;

import com.bicasteam.movigestion.api.reports.domain.model.commands.CreateReportCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    private String type;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime dateTime;
    private String driverName;

    public Report(CreateReportCommand command) {
        this.userId = 1; // Asigna un valor por defecto o lógica específica si es necesario
        this.type = command.type();
        this.description = command.description();
        this.createdAt = LocalDateTime.now();
        this.dateTime = LocalDateTime.now();
        this.driverName = command.driverName();
    }
}
