// src/main/java/com/bicasteam/movigestion/api/routes/domain/model/aggregates/Route.java
package com.bicasteam.movigestion.api.routes.domain.model.aggregates;

import com.bicasteam.movigestion.api.routes.domain.model.commands.CreateRouteCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // ——— Datos de negocio ———
    @Column(nullable = false)
    private String type;            // regular | backup ...
    @Column(nullable = false)
    private String customer;        // Gloria, Trupal…
    @Column(nullable = false)
    private String nameRoute;       // “SJL – Portón”, etc.
    private String status;          // scheduled | in_progress | completed
    private String shift;           // mañana / tarde / noche

    // ——— Asignaciones ———
    private Integer driverId;
    private String  driverName;
    private Integer vehicleId;
    private String  vehiclePlate;

    // ——— Horarios ———
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    // ——— Waypoints en JSON ———
    @Lob @Column(columnDefinition = "LONGTEXT")
    private String waypoints;       // JSON array

    // ——— Telemetría actual ———
    private Double lastLatitude;
    private Double lastLongitude;

    // ——— Auditoría ———
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ——— Empresa ———
    private String companyName;
    private String companyRuc;

    /* ===================== Constructor (Command) ===================== */
    public Route(CreateRouteCommand c) {
        this.type           = c.type();
        this.customer       = c.customer();
        this.nameRoute      = c.nameRoute();
        this.status         = c.status();
        this.shift          = c.shift();
        this.driverId       = c.driverId();
        this.driverName     = c.driverName();
        this.vehicleId      = c.vehicleId();
        this.vehiclePlate   = c.vehiclePlate();
        this.departureTime  = c.departureTime();
        this.arrivalTime    = c.arrivalTime();
        this.waypoints      = c.waypoints();
        this.lastLatitude   = c.lastLatitude();
        this.lastLongitude  = c.lastLongitude();
        this.createdAt      = LocalDateTime.now();
        this.updatedAt      = this.createdAt;
        this.companyName    = c.companyName();
        this.companyRuc     = c.companyRuc();
    }

    /* ===================== Setters para update ===================== */
    public void setType          (String v){ this.type = v;          }
    public void setCustomer      (String v){ this.customer = v;      }
    public void setNameRoute     (String v){ this.nameRoute = v;     }
    public void setStatus        (String v){ this.status = v;        }
    public void setShift         (String v){ this.shift = v;         }
    public void setDriverId      (Integer v){ this.driverId = v;     }
    public void setDriverName    (String v){ this.driverName = v;    }
    public void setVehicleId     (Integer v){ this.vehicleId = v;    }
    public void setVehiclePlate  (String v){ this.vehiclePlate = v;  }
    public void setDepartureTime (LocalDateTime v){ this.departureTime = v; }
    public void setArrivalTime   (LocalDateTime v){ this.arrivalTime = v;   }
    public void setWaypoints     (String v){ this.waypoints = v;     }
    public void setLastLatitude  (Double v){ this.lastLatitude = v;  }
    public void setLastLongitude (Double v){ this.lastLongitude = v; }
    public void setCompanyName   (String v){ this.companyName = v;   }
    public void setCompanyRuc    (String v){ this.companyRuc = v;    }

    public void touchUpdatedAt() { this.updatedAt = LocalDateTime.now(); }
}
