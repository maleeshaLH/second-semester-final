package lk.ijse.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vehicle")
public class VehicleEntity implements SuperEntity{
    @Id
    private String vehicleCode;
    private String licensePlateNumber;
    private String category;
    private String fuelType;
    private String status;
    private String remarks;
}
