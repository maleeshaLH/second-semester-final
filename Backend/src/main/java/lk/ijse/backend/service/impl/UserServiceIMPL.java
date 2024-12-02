package lk.ijse.backend.service.impl;


import lk.ijse.backend.dao.UserDao;
import lk.ijse.backend.dto.impl.UserDto;
import lk.ijse.backend.entity.impl.UserEntity;
import lk.ijse.backend.exception.DataPersistFailedException;
import lk.ijse.backend.exception.DublicateRecordException;
import lk.ijse.backend.service.UserService;
import lk.ijse.backend.util.Role;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceIMPL implements UserService {

    UserDao userDAO;
    ModelMapper modelMapper;

    public UserServiceIMPL(UserDao userDAO, ModelMapper modelMapper) {
        this.userDAO = userDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetailsService userDetailService() {
        return username -> userDAO.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Override
    public List<UserDto> getAllUser() {
        return userDAO.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
    }

    @Override
    public UserDto getUserDetails(String email, Role role) {
        if (!userDAO.existsByEmail(email)) {
            throw new DataPersistFailedException("User email :" + email + " Not Found!");
        }
        return modelMapper.map(userDAO.findByEmailAndRole(email, role), UserDto.class);
    }

    @Override
    public UserDto saveUser(UserDto userDTO) {
        if (userDAO.existsByEmail(userDTO.getEmail())) {
            throw new DublicateRecordException("This User " + userDTO.getEmail() + " already have an account.");
        }
        return modelMapper.map(userDAO.save(modelMapper.map(userDTO, UserEntity.class)), UserDto.class);
    }

    @Override
    public void updateUser(String email, UserDto userDTO) {
        UserEntity existingUser = userDAO.findByEmailAndRole(email, userDTO.getRole());

        if (existingUser.getPassword().isEmpty()) {
            throw new DataPersistFailedException("User email :" + email + "Not Found...");
        }

        existingUser.setPassword(userDTO.getPassword());
        existingUser.setRole(userDTO.getRole());

        userDAO.save(existingUser);
    }

    @Override
    public void deleteUser(String email) {
        if (!userDAO.existsByEmail(email)) {
            throw new DataPersistFailedException("User email :" + email + "Not Found...");
        }
        userDAO.deleteByEmail(email);
    }
}
