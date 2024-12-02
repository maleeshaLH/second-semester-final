package lk.ijse.backend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.backend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "staff")
public class StaffEntity implements SuperEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String designation;
    private String gender;
    private Date  joinedDate;
    private Date  dob;
    private String role;
    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;
    private String contactNo;
    private String email;
    private String vehicleCode;

    @ManyToOne
    @JoinColumn(name = "vehicleCode", insertable = false, updatable = false) // Mark as non-insertable and non-updatable
    private VehicleEntity vehicles;

    @OneToMany(mappedBy = "staffs")
    private List<EquipmentEntity> equipmentList = new ArrayList<>();
    @OneToMany(mappedBy = "staffs")
    private List<LogMonitoringEntity> logMonitoringEntities = new ArrayList<>();
}
