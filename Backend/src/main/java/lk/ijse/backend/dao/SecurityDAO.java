package lk.ijse.backend.dao;

import lk.ijse.backend.entity.impl.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityDAO extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByEmail(String email);
}
