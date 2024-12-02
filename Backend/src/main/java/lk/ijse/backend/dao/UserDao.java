package lk.ijse.backend.dao;

import lk.ijse.backend.dto.impl.UserDto;
import lk.ijse.backend.entity.impl.UserEntity;
import lk.ijse.backend.util.Role;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<UserEntity, String> {
    Boolean existsByEmail(String email);
    UserEntity findByEmailAndRole(String email, Role role);
    void deleteByEmail(String email);
    Optional<UserEntity> findByEmail(String email);

}
