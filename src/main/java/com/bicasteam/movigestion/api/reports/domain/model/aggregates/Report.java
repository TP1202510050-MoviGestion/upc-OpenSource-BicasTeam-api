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

    @Lob
    @Column(name = "photo_or_video", columnDefinition = "LONGTEXT")
    private String photoOrVideo;

    private String status;
    private String location;
    private String vehiclePlate;

    // Campos de empresa
    private String companyName;
    private String companyRuc;

    public Report(CreateReportCommand command) {
        this.userId        = command.userId();
        this.type          = command.type();
        this.description   = command.description();
        this.createdAt     = LocalDateTime.now();
        this.dateTime      = LocalDateTime.now();
        this.driverName    = command.driverName();
        this.photoOrVideo  = command.photoOrVideo();
        this.status        = command.status();
        this.location      = command.location();
        this.vehiclePlate  = command.vehiclePlate();
        this.companyName   = command.companyName();
        this.companyRuc    = command.companyRuc();
    }

    // --- Setters para los campos que pueden actualizarse ---

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setPhotoOrVideo(String photoOrVideo) {
        this.photoOrVideo = photoOrVideo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyRuc(String companyRuc) {
        this.companyRuc = companyRuc;
    }
}
