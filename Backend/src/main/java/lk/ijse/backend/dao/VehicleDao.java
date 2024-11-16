package lk.ijse.backend.dao;

import lk.ijse.backend.entity.impl.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleDao extends JpaRepository<VehicleEntity, String> {
}
