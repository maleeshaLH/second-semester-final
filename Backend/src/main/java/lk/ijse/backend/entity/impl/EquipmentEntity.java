package lk.ijse.backend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.backend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "equipment")
public class EquipmentEntity implements SuperEntity {
    @Id
    private String equipmentId;
    private String equipmentName;
    private String equipmentType;
    private String equipmentStatus;

    @ManyToOne
    @JoinColumn(name = "fieldCode",  insertable = false, updatable = false)
    private FiledEntity fields;
    @ManyToOne
    @JoinColumn(name = "id",  insertable = false, updatable = false)
    private StaffEntity staffs;
}
