package lk.ijse.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "vehicles")
    private List<StaffEntity> staff = new ArrayList<>();
}
