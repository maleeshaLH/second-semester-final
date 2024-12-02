package lk.ijse.backend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.backend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "crop")
public class CropEntity implements SuperEntity {
    @Id
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    private String category;
    private String cropSeason;
    @Column(columnDefinition = "LONGTEXT")
    private String cropImage;
    private String fieldCode;
    @ManyToOne
    @JoinColumn(name = "fieldCode", insertable = false, updatable = false)
    private FiledEntity fields;
    @OneToMany(mappedBy = "crops")
    private List<LogMonitoringEntity> logMonitoringEntities = new ArrayList<>();

}
