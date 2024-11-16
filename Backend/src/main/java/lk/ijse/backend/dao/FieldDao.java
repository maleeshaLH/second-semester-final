package lk.ijse.backend.dao;

import lk.ijse.backend.entity.FiledEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDao extends JpaRepository<FiledEntity, String > {
}
