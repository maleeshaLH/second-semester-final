package lk.ijse.backend.service.impl;

import lk.ijse.backend.auth.request.SignInRequest;
import lk.ijse.backend.auth.request.SignUpRequest;
import lk.ijse.backend.auth.response.JWTAuthResponse;
import lk.ijse.backend.dao.SecurityDAO;
import lk.ijse.backend.dto.impl.UserDto;
import lk.ijse.backend.entity.impl.UserEntity;
import lk.ijse.backend.service.AuthenticationService;
import lk.ijse.backend.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityDAO securityDAO;
    private final ModelMapper mapper;
    private final JWTService jwtService;

    @Override
    public JWTAuthResponse signIn(SignInRequest signInRequest) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        // Fetch user details
        UserEntity user = securityDAO.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Generate tokens
        String accessToken = jwtService.generateToken(user);

        // Return both tokens
        return JWTAuthResponse.builder().token(accessToken).build();
    }

    @Override
    public JWTAuthResponse signUp(SignUpRequest signUpRequest) {
//        UserDto userDTO = UserDto.builder().email(signUpRequest.getEmail()).password(passwordEncoder.encode(signUpRequest.getPassword())).role(signUpRequest.getRole()).build();
        UserDto userDTO  =UserDto.builder().email(signUpRequest.getEmail()).password(passwordEncoder.encode(signUpRequest.getPassword())).role(signUpRequest.getRole()).build();
        UserEntity savedUser = securityDAO.save(mapper.map(userDTO, UserEntity.class));

        // Generate tokens
        String accessToken = jwtService.generateToken(savedUser);

        // Return both tokens
        return JWTAuthResponse.builder().token(accessToken).build();
    }
}
