package lk.ijse.backend.dto.impl;

import lk.ijse.backend.dto.SuperDto;
import lk.ijse.backend.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto  {
    private String email;
    private String password;
    private Role role;
}
