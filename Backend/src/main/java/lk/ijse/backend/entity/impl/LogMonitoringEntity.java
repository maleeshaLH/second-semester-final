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
@Table(name = "log_monitoring")
public class LogMonitoringEntity implements SuperEntity {
    @Id
    private String log_code;
    private String log_date;
    private String Observation;
    @Column(columnDefinition = "LONGTEXT")
    private String log_image;
    @ManyToOne
    @JoinColumn(name = "fieldCode", nullable = false)
    private FiledEntity fields;
    @ManyToOne
    @JoinColumn(name = "cropCode", nullable = false)
    private CropEntity crops;
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private StaffEntity staffs;
}
