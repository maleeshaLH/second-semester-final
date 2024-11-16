package lk.ijse.backend.dao;

import lk.ijse.backend.entity.impl.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffDao extends JpaRepository<StaffEntity,String > {
}
