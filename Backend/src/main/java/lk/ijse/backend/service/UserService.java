package lk.ijse.backend.service;


import lk.ijse.backend.dto.impl.UserDto;
import lk.ijse.backend.util.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailService();

    List<UserDto> getAllUser();

    UserDto getUserDetails(String email, Role role);

    UserDto saveUser(UserDto userDTO);

    void updateUser(String email, UserDto userDTO);

    void deleteUser(String email);
}
